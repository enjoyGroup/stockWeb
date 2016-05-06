package th.go.stock.app.enjoy.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
	
	public void createSession(){
		sessionFactory 		= HibernateUtil.getSessionFactory();
		session 			= sessionFactory.openSession();
	}
	
	public void destroySession(){
		session.flush();
		session.clear();
		session.close();
	}

	public EnjoyLogger getLogger() {
		return logger;
	}

	public void setLogger(EnjoyLogger logger) {
		this.logger = logger;
	}
	
}
