package com.ehais.tracking.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.ehais.tools.ReturnObject;



@SuppressWarnings("hiding")
public interface CommonDao<T> {

	public void begin();
	public void commin();
	
  public void insert(T t)throws Exception;
  
  public void merge(T t) throws Exception;

  public void update(T t)throws Exception;
  
  public List<T> selectAll(Class<T> entityClass,Map<String,Object> map) throws Exception;  
  public List<T> selectAll(Class<T> entityClass,Map<String,Object> map,boolean clear_session) throws Exception;
  
  
  public List<T> selectAll(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapOrder) throws Exception;
  public List<T> selectAll(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapOrder,boolean clear_session) throws Exception;

  
  public List<T> selectAll(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapLike,Map<String,Object> mapOrder) throws Exception;
  public List<T> selectAll(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapLike,Map<String,Object> mapOrder,boolean clear_session) throws Exception;

  
  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo , Integer pageSize)throws Exception;
  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo , Integer pageSize,boolean clear_session)throws Exception;
  
  
  
  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,Map<String,Object> map)throws Exception;
  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,Map<String,Object> map,boolean clear_session)throws Exception;
  
  

  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object> mapOrder)throws Exception;
  public ReturnObject<T> select(Class<T> entityClass, Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object> mapOrder,boolean clear_session)throws Exception;
  
  

  public ReturnObject<T> select(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapOrder)throws Exception;
  public ReturnObject<T> select(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapOrder,boolean clear_session)throws Exception;
  
  

  public ReturnObject<T> select(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapLike,Map<String,Object> mapOrder)throws Exception;
  public ReturnObject<T> select(Class<T> entityClass,Integer pageNo, Integer pageSize,Map<String,Object> map,Map<String,Object[]> mapIn,Map<String,Object> mapLike,Map<String,Object> mapOrder,boolean clear_session)throws Exception;
  

  
  public T findById(Class<T> entityClass,Serializable id) throws Exception;
  public T findById(Class<T> entityClass,Serializable id,boolean clear_session) throws Exception;
  
  
  //根据定自义的主键与值找了对象
  public T findById(Class<T> entityClass,String idName,Integer idValue) throws Exception;
  public T findById(Class<T> entityClass,String idName,Integer idValue,boolean clear_session) throws Exception;
  
  //根据某字段去查询对象
  public T findByKey(Class<T> entityClass,String keyName,Object objValue) throws Exception;
  public T findByKey(Class<T> entityClass,String keyName,Object objValue,boolean clear_session) throws Exception;
  
  
  
  public void delete(Class<T> entityClass,Integer id)throws Exception;
  
  public Long count(Class<T> entityClass,Map<String,Object> map)throws Exception;
  
  public Long count(Class<T> entityClass,Map<String,Object> map,Map<String,Object[]> mapIn)throws Exception;
  

}