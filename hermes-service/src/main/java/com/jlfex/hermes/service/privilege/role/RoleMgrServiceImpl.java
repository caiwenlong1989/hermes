package com.jlfex.hermes.service.privilege.role;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.common.constant.HermesConstants;
import com.jlfex.hermes.common.utils.Strings;
import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.User;
import com.jlfex.hermes.repository.role.RoleRepository;
import com.jlfex.hermes.service.RoleService;
import com.jlfex.hermes.service.UserService;
import com.jlfex.hermes.service.common.Pageables;

/**
 * 
 * 权限： 角色管理
 *
 */
@Service
@Transactional
public class RoleMgrServiceImpl implements RoleMgrService {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@Override
	public Page<Role> findRoleList(final String code, final String name, String page, String size, final User creator) {
		Pageable pageable = Pageables.pageable(Integer.valueOf(Strings.empty(page, "0")), Integer.valueOf(Strings.empty(size, HermesConstants.DEFAULT_PAGE_SIZE)),new Sort(Direction.ASC,"createTime"));
		Page<Role> roleList = roleRepository.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> p = new ArrayList<Predicate>();
				if (StringUtils.isNotEmpty(code)) {
					p.add(cb.like(root.<String> get("code"), "%" + code + "%"));
				}
				if (StringUtils.isNotEmpty(name)) {
					p.add(cb.like(root.<String> get("name"), "%" + name + "%"));
				}

				if(creator != null) {
					if(!creator.getAccount().equals(HermesConstants.PLAT_MANAGER)) {
						p.add(cb.equal(root.<String> get("creator"), creator.getId()));
					}
				}
			
				p.add(cb.equal(root.<String> get("status"), Role.Status.ENABLED));
				p.add(cb.equal(root.<String> get("type"), Role.Type.SYS_AUTH));
				return cb.and(p.toArray(new Predicate[p.size()]));
			}
		}, pageable);
		if(roleList!=null && roleList.getContent() !=null && roleList.getContent().size() > 0){
			for(Role role :roleList){
				if(Strings.notEmpty(role.getCreator())){
					 User creatorUser = userService.loadById(role.getCreator());
					 if(creatorUser!=null){
						 role.setCreatorName(creatorUser.getAccount());
					 }
				}
			}
		}
		return roleList;
	}

	@Override
	public boolean isValidCode(String addOrEdit, String code) {
		if (addOrEdit.equals(HermesConstants.OBJECT_NOT_EXIST)) {
			Role role = roleService.findByCode(code);
			if (role != null) {
				return false;
			}
		} else {
			Role role = roleService.findOne(addOrEdit);
			if (role != null && !role.getCode().equals(code)) {
				return false;
			}
		}

		return true;
	}
}
