package th.go.stock.app.enjoy.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class DaoControl {
	private EnjoyLogger 	logger;
	private SessionFactory 	sessionFactory;
	private Session 		session;
	
	public void init(){
		createSession();
	}
	
	public void setCriteria(SQLQuery query, HashMap<String, Object>	param) throws Exception{
		
		try{
			Iterator<String> it = param.keySet().iterator();
	        while(it.hasNext()){
	            String key		= it.next();
	            Object value	= param.get(key);
	            
	            logger.info(key + " :: " + value);
	            
	            query.setParameter(key			, value);
	        }
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<HashMap<String, Object>> getResult(String hql, HashMap<String, Object> param, List<String> columnList) throws Exception{
		List<Object[]>					list			= null;
		SQLQuery 						query 			= null;
		HashMap<String, Object> 		result			= null;
		List<HashMap<String, Object>>	resultList		= new ArrayList<HashMap<String, Object>>();
		
		try{
			logger.info("hql 		  :: " + hql);
			
			query			= session.createSQLQuery(hql);
			setCriteria(query, param);
			
			for(String columns:columnList){
	            query.addScalar(columns);
	        }
	        
	        list		 	= query.list();
	        
	        logger.info("total record :: " + list.size());
	        
	        for(Object[] row:list){
	        	result			= new HashMap<String, Object>();
	        	
	        	for(int i=0;i<columnList.size();i++){
	        		logger.info(columnList.get(i) + " :: " + row[i]);
	        		
	        		result.put(columnList.get(i), row[i]);
	        		
	        	}
	        	
	        	resultList.add(result);
	        	
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return resultList;
	}
	
	public List<Object> getResult(String hql, HashMap<String, Object> param, String column, int type) throws Exception{
		List<Object>					list			= null;
		SQLQuery 						query 			= null;
		
		try{
			logger.info("hql 		  :: " + hql);
			
			query			= session.createSQLQuery(hql);
			setCriteria(query, param);
			
			if(type==Constants.INT_TYPE){
				query.addScalar(column			, new IntegerType());
			}else if(type==Constants.STRING_TYPE){
				query.addScalar(column			, new StringType());
			}
	        
	        list		 	= query.list();
	        
	        logger.info("list.size :: " + list.size());
	        
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public void createSession(){
		sessionFactory 		= HibernateUtil.getSessionFactory();
		session 			= sessionFactory.openSession();
		
		session.beginTransaction();
	}
	
	public void destroySession(){
		session.flush();
		session.clear();
		session.close();
	}
	
	public void commit(){
		session.getTransaction().commit();
	}
	
	public void insertData(Object obj) throws Exception{
		session.saveOrUpdate(obj);
	}
	
	public void rollback(){
		session.getTransaction().rollback();
	}
	
	public Query createQuery(String hql) throws Exception{
		Query 	query 	= null;
		
		try{
			query = session.createQuery(hql);
		}catch(Exception e){
			throw e;
		}
		return query;
	}

	public EnjoyLogger getLogger() {
		return logger;
	}

	public void setLogger(EnjoyLogger logger) {
		this.logger = logger;
	}
	
}
