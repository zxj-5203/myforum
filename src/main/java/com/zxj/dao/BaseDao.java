package com.zxj.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.Assert;

// Dao基类，其他Dao类直接继承这个Dao复用共用的方法，还可以获得泛型的好处；
// Dao子类使用泛型技术绑定特定类型的PO类，避免强制类型转换带来的麻烦；
public class BaseDao<T> {
	// 实体类
	private Class<T> entityClass;
	
	private HibernateTemplate hibernateTemplate;

	// 在构造函数中，通过反射获取子类确定的泛型类；
	// getGenericSuperclass获取带有泛型的父类，方法的参数是子类Class，
	// 返回值是子类继承父类时给父类传入的泛型参数，并将其转换为ParameterizedType类型；
	// 简言之就是获取超类的泛型参数的实际类型；
	// Type是Java编程语言中所有类型的公共高级接口:包括原始类型、参数化类型、数组类型、类型变量和基本类型
	public BaseDao() {
		Type genericType = this.getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genericType).getActualTypeArguments();// 获取参数化类型；
		entityClass = (Class) params[0];
	}

	/**
	 * 根据Id延迟加载PO实例； <br/>
	 * 延迟加载(懒加载)，在第一次使用非ID或class属性时执行SQL语句，获取到的是子类代理对象，若数据不存在，则抛异常；
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T load(Serializable id) {
		return (T) getHibernateTemplate().load(entityClass, id);
	}

	/**
	 * 根据Id立即获取PO实例； <br/>
	 * 立即加载，马上执行SQL语句，获取到的是真是对象，若数据不存在，则返回null，不会报错；
	 * 
	 * @param id
	 * @return 返回相应的持久化PO实例
	 */
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 延迟加载PO的所有对象；
	 * 
	 * @return
	 */
	public List<T> loadAll() {
		return getHibernateTemplate().loadAll(entityClass);
	}

	/**
	 * 保存PO实体
	 * 
	 * @param entity
	 */
	public void save(T entity) {
		getHibernateTemplate().save(entity);
//		getHibernateTemplate().getSessionFactory().getCurrentSession().save(entity);
//		Serializable i = getHibernateTemplate().getSessionFactory().openSession().save(entity);
	}

	/**
	 * 删除PO实体
	 * 
	 * @param entity
	 */
	public void remove(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据tableName删除表数据；<br>
	 * truncate：删除表中数据，但是表格体还在，可以重新插入数据；
	 * 
	 * @param tableName
	 */
	public void removeAll(String tableName) {
		getSession().createSQLQuery("truncate TABLE " + tableName + "").executeUpdate();
	}

	/**
	 * 更新PO实体
	 * 
	 * @param entity
	 */
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param hql
	 * @return 查询结果
	 */
	public List find(String hql) {
		return createQuery(hql, null).list();
	}

	/**
	 * 执行带参数的HQL查询
	 * 
	 * @param hql
	 * @param params
	 * @return 查询结果
	 */
	public List find(String hql, Object[] params) {
		return createQuery(hql, params).list();
	}

	/**
	 * 对延迟加载的实体PO执行初始化
	 * 
	 * @param entity
	 */
	public void initialize(Object entity) {
		this.getHibernateTemplate().initialize(entity);
	}

	/**
	 * 去除hql的orderby子句，用于分页查询；
	 * 
	 * @param hql
	 * @return
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql, "hql不能为空！"); // 断言，判hql不能为空，并且至少包含一个非空白字符；
		// 编译正则表达式：将正则规则进行对象的封装，设置不区分大小写；
		Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		// 通过正则对象的matcher方法与字符串相关联，返回要对字符串进行操作的匹配器对象Matcher；
		Matcher matcher = pattern.matcher(hql);
		// 通过匹配器对象的方法对字符串进行操作；boolean b = matcher.matches();
		StringBuffer sb = new StringBuffer();
		// find：寻找符合的字符串；
		while (matcher.find()) {
			matcher.appendReplacement(sb, ""); // 将匹配到的字符串替换的为""，然后添加到sb中；
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 去除hql的select子句，用于分页查询；
	 * 
	 * @param hql
	 * @return
	 */
	private static String removeSelect(String hql) {
		Assert.hasText(hql, "hql不能为空！");
		int beginPos = hql.toLowerCase().indexOf("from"); // 指针定位到from
		Assert.isTrue(beginPos != -1, "hql: " + hql + "必须包含from关键字！");
		return hql.substring(beginPos);
	}

	/**
	 * 使用hql进行分页查询；
	 * 
	 * @param hql
	 * @param pageNo 页号，从1开始；
	 * @param pageSize 每页的记录数；
	 * @param values 当前页显示的数据；
	 * @return
	 */
	public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {
		Assert.hasText(hql, "hql语句不能为空！");
		Assert.isTrue(pageNo >= 1, "页号必须从大于1！");
		// hql语句去除order子句、select子句，然后组合成Count查询的hql语句
		String countQueryString = "select count(*) " + removeSelect(removeOrders(hql));
		List countList = find(countQueryString,values); // 查询总记录数
		long totalCount = (long) countList.get(0);
		if (totalCount < 1)
			return new Page();
		// 实际查询返回分页对象；
		int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql, values);
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		return new Page(startIndex, totalCount, pageSize, list);
	}

	/**
	 * 创建query对象；
	 * 
	 * @param hql
	 * @param values 可变参数；
	 * @return
	 */
	public Query createQuery(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values == null)
			return query;
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	public Session getSession() {
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
