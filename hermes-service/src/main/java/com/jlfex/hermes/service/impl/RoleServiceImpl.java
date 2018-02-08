package com.jlfex.hermes.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jlfex.hermes.model.Role;
import com.jlfex.hermes.model.RoleResource;
import com.jlfex.hermes.model.UserRole;
import com.jlfex.hermes.repository.RoleResourceRepository;
import com.jlfex.hermes.repository.UserRoleRepository;
import com.jlfex.hermes.repository.role.RoleRepository;
import com.jlfex.hermes.service.RoleService;

/**
 * 角色业务实现
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	/** 角色资源关系仓库 */
	@Autowired
	private RoleResourceRepository roleResourceRepository;

	/** 用户角色仓库 */
	@Autowired
	private UserRoleRepository userRoleRepository;

	/** 角色仓库 **/
	@Autowired
	private RoleRepository roleRepository;

	/*
	 * (non-Javadoc)
	 * 
	
	@Autowired
	private RoleRepository roleRepository;
	
	/* (non-Javadoc)
>>>>>>> refs/heads/master
	 * @see com.jlfex.hermes.service.RoleService#findByUserId(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> findByUserId(String userId) {
		return getRolesFromUserRoles(userRoleRepository.findByUserId(userId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.RoleService#findByResourceAndType(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Role> findByResourceAndType(String resource, String type) {
		return getRolesFromRoleResources(roleResourceRepository.findByResourceAndType(resource, type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.RoleService#findRoleResourceByType(java.lang
	 * .String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<RoleResource> findRoleResourceByType(String type) {
		return roleResourceRepository.findByType(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jlfex.hermes.service.RoleService#findResourceByUserIdAndType(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<String> findResourceByUserIdAndType(String userId, String type) {
		List<Role> roles = getRolesFromUserRoles(userRoleRepository.findByUserId(userId));
		return getResourcesFromRoleResources(roleResourceRepository.findByRoleInAndType(roles, type));
	}

	/**
	 * 从角色资源关系列表中读取角色列表
	 * 
	 * @param roleResources
	 * @return
	 */
	protected List<Role> getRolesFromRoleResources(List<RoleResource> roleResources) {
		Map<String, Role> map = new HashMap<String, Role>(roleResources.size());
		for (RoleResource roleResource : roleResources)
			map.put(roleResource.getRole().getId(), roleResource.getRole());
		return new ArrayList<Role>(map.values());
	}

	/**
	 * 从角色资源关系列表中读取资源列表
	 * 
	 * @param roleResources
	 * @return
	 */
	protected List<String> getResourcesFromRoleResources(List<RoleResource> roleResources) {
		Map<String, String> map = new HashMap<String, String>(roleResources.size());
		for (RoleResource roleResource : roleResources)
			map.put(roleResource.getResource(), roleResource.getResource());
		return new ArrayList<String>(map.values());
	}

	/**
	 * 从用户角色关系列表中读取角色列表
	 * 
	 * @param userRoles
	 * @return
	 */
	protected List<Role> getRolesFromUserRoles(List<UserRole> userRoles) {
		Map<String, Role> map = new HashMap<String, Role>(userRoles.size());
		for (UserRole userRole : userRoles)
			map.put(userRole.getRole().getId(), userRole.getRole());
		return new ArrayList<Role>(map.values());
	}

	/**
	 * 保存角色
	 */
	@Override
	public Role save(Role role) {
		Role role2 = roleRepository.save(role);

		return role2;
	}

	/**
	 * 查询角色
	 */
	@Override
	public Role findOne(String id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role findByCode(String code) {
		return roleRepository.findOneByCode(code);
	}
	
	/**
	 * 根据类型+状态  获取角色列表
	 */
	@Override
	@Transactional(readOnly = true)
	public  List<Role>  findByTypeAndStatus(String type,String status) {
		return roleRepository.findByTypeAndStatus(type,status);
	}
	
	@Override
	@Transactional(readOnly = true)
	public  List<Role>  findByTypeAndStatusAndCreator(String type,String status,String creator) {
		return roleRepository.findByTypeAndStatusAndCreator(type,status,creator);
	}
    /**
     * 根据Id获取角色
     */
	@Override
	public Role findById(String id) {
		return roleRepository.findOne(id);
	}
}
