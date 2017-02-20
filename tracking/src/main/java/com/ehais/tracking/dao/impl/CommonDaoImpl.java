package com.ehais.tracking.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.ehais.tools.ReturnObject;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ehais.tracking.dao.CommonDao;



@Repository("commonDao")
public class CommonDaoImpl<T> implements CommonDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;
	
//	@Autowired
//	private HibernateTemplate hibernateTemplate;
	
	public void begin(){
		sessionFactory.getCurrentSession().beginTransaction();
	}
	public void commin(){
		sessionFactory.getCurrentSession().getTransaction().commit();
	}
	
	@Transactional(readOnly=false,rollbackFor=RuntimeException.class)
	public void insert(T t) throws Exception{
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(t);
	}
	
	public void merge(T t) throws Exception {
		sessionFactory.getCurrentSession().merge(t);
	}

	public void update(T t) throws Exception{
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(t);
	}


	public T findById(Class<T> entityClass, Serializable id) throws Exception{
		return this.findById(entityClass, id, true);
	}
	public T findById(Class<T> entityClass, Serializable id,boolean clear_session) throws Exception{
		// TODO Auto-generated method stub
		T ret = (T) sessionFactory.getCurrentSession().get(entityClass, id);
		if(clear_session)sessionFactory.getCurrentSession().clear();
		return ret;
	}
	
	public T findById(Class<T> entityClass,String idName,Integer idValue) throws Exception{
		return this.findById(entityClass, idName, idValue, true);
	}
	public T findById(Class<T> entityClass,String idName,Integer idValue,boolean clear_session) throws Exception{
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(idName, idValue));		
		T ret = (T) criteria.uniqueResult();
		if(clear_session)sessionFactory.getCurrentSession().clear();
		return ret;
	}
	
	public void delete(Class<T> entityClass,Integer id) throws Exception{
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(this.findById(entityClass, id));
	}


	public List<T> selectAll(Class<T> entityClass,Map<String,Object> map) throws Exception {
		// TODO Auto-generated method stub
		return this.selectAll(entityClass, map, true);
	}


	public List<T> selectAll(Class<T> entityClass, Map<String, Object> map,
			boolean clear_session) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		
		List result = criteria.list();
		if(clear_session)sessionFactory.getCurrentSession().clear();
		return result;
	}


	public List<T> selectAll(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return this.selectAll(entityClass, pageNo, pageSize, map, mapIn, mapOrder,true);
	}
	
	public List<T> selectAll(Class<T> entityClass,Integer pageNo, 
			Integer pageSize,Map<String,Object> map,
			Map<String,Object[]> mapIn,Map<String,Object> mapOrder,
			boolean clear_session) throws Exception{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		
		if(mapIn!=null && mapIn.size()>0){
			for(Map.Entry<String, Object[]> entry:mapIn.entrySet()){ 
			     criteria.add(Restrictions.in(entry.getKey(), entry.getValue()));
			}   
		}
		
		
		if(mapOrder!=null && mapOrder.size()>0){
			for(Map.Entry<String, Object> entry:mapOrder.entrySet()){ 
			     if(entry.getValue().equals("asc")){
			    	 criteria.addOrder(Order.asc(entry.getKey())); 
			     }else{
			    	 criteria.addOrder(Order.desc(entry.getKey())); 
			     }
			     
			     
			}   
		}
		
		
		if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
		
		
		List result = criteria.list();
		if(clear_session)sessionFactory.getCurrentSession().clear();
		
		return result;
	}


	public List<T> selectAll(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapLike,
			Map<String, Object> mapOrder) throws Exception {
		// TODO Auto-generated method stub
		return this.selectAll(entityClass, pageNo, pageSize,map,  mapIn ,mapLike, mapOrder, true);
	}
	
	public List<T> selectAll(Class<T> entityClass,Integer pageNo, 
			Integer pageSize,Map<String,Object> map,
			Map<String,Object[]> mapIn,Map<String,Object> mapLike,
			Map<String,Object> mapOrder,boolean clear_session) throws Exception{
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		
		if(mapIn!=null && mapIn.size()>0){
			for(Map.Entry<String, Object[]> entry:mapIn.entrySet()){ 
			     criteria.add(Restrictions.in(entry.getKey(), entry.getValue()));
			}   
		}
		
		if(mapLike!=null && mapLike.size()>0){
			for(Map.Entry<String, Object> entry:mapLike.entrySet()){ 
			     criteria.add(Restrictions.like(entry.getKey(), String.valueOf(entry.getValue()),MatchMode.ANYWHERE));
			}   
		}
		
		
		if(mapOrder!=null && mapOrder.size()>0){
			for(Map.Entry<String, Object> entry:mapOrder.entrySet()){ 
			     if(entry.getValue().equals("asc")){
			    	 criteria.addOrder(Order.asc(entry.getKey())); 
			     }else{
			    	 criteria.addOrder(Order.desc(entry.getKey())); 
			     }
			     
			     
			}   
		}
		
		
		if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
		
		
		List result = criteria.list();
		if(clear_session)sessionFactory.getCurrentSession().clear();
		
		return result;
	}
	
	
	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize)
			throws Exception {
		// TODO Auto-generated method stub
//		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
		return this.select(entityClass, pageNo, pageSize,true);
	}
	
	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,boolean clear_session)
			throws Exception {
		// TODO Auto-generated method stub
//		DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
//		List list = sessionFactory.getCurrentSession().findByCriteria(criteria, (pageNo - 1) * pageSize, pageSize);
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);

        if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
        
        List result = criteria.list();
        ReturnObject<T> ro = new ReturnObject<T>();
        ro.setTotal(Integer.valueOf(rowCount+""));
        ro.setRows(result);
        if(clear_session)sessionFactory.getCurrentSession().clear();
		return ro;
	}
	
	
	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,
			Map<String,Object> map)throws Exception {
		// TODO Auto-generated method stub
		return this.select(entityClass, pageNo, pageSize, map,true);
	}
	
	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,
			Map<String,Object> map,boolean clear_session)throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);

        if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
        
        List result = criteria.list();
        ReturnObject<T> ro = new ReturnObject<T>();
        ro.setTotal(Integer.valueOf(rowCount+""));
        ro.setRows(result);
        if(clear_session)sessionFactory.getCurrentSession().clear();
		return ro;
	}


	public Long count(Class<T> entityClass, Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		
		
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(rowCount==null)rowCount = 0l;
		return rowCount;
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object> mapOrder) throws Exception {
		// TODO Auto-generated method stub
		return this.select(entityClass, pageNo, pageSize, map, mapOrder, true);
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object> mapOrder,boolean clear_session) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        
        if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
        
        if(mapOrder!=null && mapOrder.size()>0){
			for(Map.Entry<String, Object> entry:mapOrder.entrySet()){ 
			     if(entry.getValue().equals("asc")){
			    	 criteria.addOrder(Order.asc(entry.getKey())); 
			     }else{
			    	 criteria.addOrder(Order.desc(entry.getKey())); 
			     }
			     
			     
			}   
		}
        
        List result = criteria.list();
        ReturnObject<T> ro = new ReturnObject<T>();
        ro.setTotal(Integer.valueOf(rowCount+""));
        ro.setRows(result);
        if(clear_session)sessionFactory.getCurrentSession().clear();
		return ro;
	}


	public Long count(Class<T> entityClass, Map<String, Object> map, Map<String,Object[]> mapIn) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		
		if(mapIn!=null && mapIn.size()>0){
			for(Map.Entry<String, Object[]> entry:mapIn.entrySet()){ 
			     criteria.add(Restrictions.in(entry.getKey(), entry.getValue()));
			}   
		}
		
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		if(rowCount==null)rowCount = 0l;
		return rowCount;
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapOrder)
			throws Exception {
		// TODO Auto-generated method stub
		return this.select(entityClass, pageNo, pageSize, map, mapIn, mapOrder, true);
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapOrder,boolean clear_session)
			throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		if(mapIn!=null && mapIn.size()>0){
			for(Map.Entry<String, Object[]> entry:mapIn.entrySet()){ 
			     criteria.add(Restrictions.in(entry.getKey(), entry.getValue()));
			}   
		}
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        
        if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
        
        if(mapOrder!=null && mapOrder.size()>0){
			for(Map.Entry<String, Object> entry:mapOrder.entrySet()){ 
			     if(entry.getValue().equals("asc")){
			    	 criteria.addOrder(Order.asc(entry.getKey())); 
			     }else{
			    	 criteria.addOrder(Order.desc(entry.getKey())); 
			     }
			     
			     
			}   
		}
        
        List result = criteria.list();
        ReturnObject<T> ro = new ReturnObject<T>();
        ro.setTotal(Integer.valueOf(rowCount+""));
        ro.setRows(result);
        if(clear_session)sessionFactory.getCurrentSession().clear();
		return ro;
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapLike,
			Map<String, Object> mapOrder) throws Exception {
		// TODO Auto-generated method stub		
		return this.select(entityClass, pageNo, pageSize, map, mapIn, mapLike, mapOrder, true);
	}


	public ReturnObject<T> select(Class<T> entityClass, Integer pageNo,
			Integer pageSize, Map<String, Object> map,
			Map<String, Object[]> mapIn, Map<String, Object> mapLike,
			Map<String, Object> mapOrder,boolean clear_session) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		if(map!=null && map.size()>0){
			for(Map.Entry<String, Object> entry:map.entrySet()){ 
			     criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
			}   
		}
		if(mapIn!=null && mapIn.size()>0){
			for(Map.Entry<String, Object[]> entry:mapIn.entrySet()){ 
			     criteria.add(Restrictions.in(entry.getKey(), entry.getValue()));
			}   
		}
		
		if(mapLike!=null && mapLike.size()>0){
			for(Map.Entry<String, Object> entry:mapLike.entrySet()){ 
				criteria.add(Restrictions.like(entry.getKey(), String.valueOf(entry.getValue()),MatchMode.ANYWHERE));
			}   
		}
		
		
		Long rowCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        criteria.setProjection(null);
        
        if(pageNo!=null && pageSize!=null){
			criteria.setFirstResult((pageNo - 1) * pageSize);
	        criteria.setMaxResults(pageSize);
		}
        
        if(mapOrder!=null && mapOrder.size()>0){
			for(Map.Entry<String, Object> entry:mapOrder.entrySet()){ 
			     if(entry.getValue().equals("asc")){
			    	 criteria.addOrder(Order.asc(entry.getKey())); 
			     }else{
			    	 criteria.addOrder(Order.desc(entry.getKey())); 
			     }
			     
			     
			}   
		}
        
        List result = criteria.list();
        ReturnObject<T> ro = new ReturnObject<T>();
        ro.setTotal(Integer.valueOf(rowCount+""));
        ro.setRows(result);
        if(clear_session)sessionFactory.getCurrentSession().clear();
		return ro;
	}


	public T findByKey(Class<T> entityClass, String keyName, Object objValue)
			throws Exception {
		// TODO Auto-generated method stub
		return this.findByKey(entityClass, keyName, objValue,true);
	}


	public T findByKey(Class<T> entityClass, String keyName, Object objValue,
			boolean clear_session) throws Exception {
		// TODO Auto-generated method stub
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq(keyName, objValue));
		T ret = (T) criteria.uniqueResult();
		if(clear_session)sessionFactory.getCurrentSession().clear();
		return ret;
	}



		
}