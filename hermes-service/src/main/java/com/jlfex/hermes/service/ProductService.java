package com.jlfex.hermes.service;
import java.util.List;
import org.springframework.data.domain.Page;
import com.jlfex.hermes.model.Product;
import com.jlfex.hermes.service.pojo.ProductInfo;
import com.jlfex.hermes.service.pojo.SimpleProduct;

/**
 * 产品业务接口
 */
public interface ProductService {

	/**
	 * 查询所有产品
	 * 
	 * @return
	 */
	public List<ProductInfo> findAll();

	public Product loadByCode(String code);

	public Product loadById(String id);

	/**
	 * 读取所有产品<br>
	 * 从缓存加载数据
	 * 
	 * @return
	 */
	public List<ProductInfo> getAll();

	/**
	 * 读取所有产品
	 */
	public Page<Product> find(String code, String name, String purpose, String status, int page, int size);

	/**
	 * 对象持久化
	 */
	public Product save(Product product);

	/**
	 * 根据 状态 获取 产品信息
	 * 
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public List<Product> findByStatusIn(String... status) throws Exception;

	public Product editProduct(Product p, SimpleProduct product) throws Exception;

	/**
	 * 获取产品数量
	 * 
	 * @return
	 */
	public Long countProductNum();

	/**
	 * 生成产品标识数
	 * 
	 * @return
	 */
	public String generateProductCode();
}
