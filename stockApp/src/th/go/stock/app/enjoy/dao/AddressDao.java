package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.AddressBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class AddressDao {	
	
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(AddressDao.class);
	
	public List<String> provinceList(String province){
		logger.info("[provinceList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 				= " select provinceName from province where provinceId <> 00 and provinceName like ('"+province+"%') order by provinceName asc limit 10 ";
			
			logger.info("[provinceList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("provinceName"			, new StringType());
			
			list		 	= query.list();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[provinceList][End]");
		}
		
		return list;
	}
	
	public List<String> districtList(String province, String district){
		logger.info("[districtList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
		List<String>			 	listReturn			= new ArrayList<String>();
        String						provinceId			= null;
		
		try{
			
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = '"+province+"'";
			
			logger.info("[districtList] Check Province hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("provinceId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				provinceId = list.get(0);
			}
		    /*End check province section*/
		    
		    if(provinceId==null){
		    	listReturn.add("กรุณาระบุจังหวัด");
		    }else{
		    	hql 		= "select districtName from district where districtId <> 0000 and provinceId <> 00 and districtName like ('"+district+"%') and provinceId = "+provinceId+" order by districtName asc limit 10";
				
		    	logger.info("[districtList] hql :: " + hql);
		    	
		    	query			= session.createSQLQuery(hql);
				
				query.addScalar("districtName"			, new StringType());
				
				list		 	= query.list();
				
				for(String districtName:list){
					listReturn.add(districtName);
				}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[districtList][End]");
		}
		
		return listReturn;
	}
	
	public List<String> subdistrictList(String province, String district, String subdistrict){
		logger.info("[subdistrictList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
		List<String>			 	listReturn			= new ArrayList<String>();
        String						provinceId			= null;
        String						districtId			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = '"+province+"'";
			
			logger.info("[subdistrictList] Check Province hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("provinceId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				provinceId = list.get(0);
			}
		    /*End check province section*/
		    
		    /*Begin check district section*/
			hql 		= "select districtId from district where districtId <> 0000 and districtName = '"+district+"'";
			
			logger.info("[subdistrictList] Check District hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("districtId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				districtId = list.get(0);
			}
		    /*End check district section*/
		    
		    if(provinceId==null){
		    	listReturn.add("กรุณาระบุจังหวัด");
		    }else if(districtId==null){
		    	listReturn.add("กรุณาระบุอำเภอ");
		    }else{
		    	hql 		= "select subdistrictName" 
								   + " from subdistrict "
								   + "  where subdistrictId <> 000000 "
								   + " and provinceId <> 00 "
								   + " and districtId <> 0000 "
								   + " and subdistrictName like ('"+subdistrict+"%') "
								   + " and provinceId = "+provinceId
								   + " and districtId = "+districtId
								   + " order by subdistrictName asc limit 10";
				
		    	logger.info("[subdistrictList] subdistrict hql :: " + hql);
				
		    	query			= session.createSQLQuery(hql);
				
				query.addScalar("subdistrictName"			, new StringType());
				
				list		 	= query.list();
				
				for(String subdistrictName:list){
					listReturn.add(subdistrictName);
				}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[subdistrictList][End]");
		}
		
		return listReturn;
	}
	
	public AddressBean validateAddress(String province, String district, String subdistrict){
		logger.info("[validateAddress][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						provinceId			= null;
        String						districtId			= null;
        String						subdistrictId		= null;
        String						errMsg				= null;
        AddressBean					addressBean			= new AddressBean();
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = '"+province+"'";
			
			logger.info("[validateAddress] Check Province hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("provinceId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				provinceId = list.get(0);
			}
		    if(provinceId==null)throw new EnjoyException("ระบุจังหวัดผิด");
		    /*End check province section*/
		    
		    /*Begin check district section*/
		    hql 		= "select districtId from district where districtId <> 0000 and provinceId = "+provinceId+" and districtName = '"+district+"'";
			
			logger.info("[validateAddress] Check District hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("districtId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				districtId = list.get(0);
			}
		    if(districtId==null)throw new EnjoyException("ระบุอำเภอผิด");
		    /*End check district section*/
		    
		    /*Begin check subDistrict section*/
		    hql 		= "select subdistrictId from subdistrict where subdistrictId <> 000000 and provinceId = "+provinceId+" and districtId = "+districtId+" and subdistrictName = '"+subdistrict+"'";
			
			logger.info("[validateAddress] Check SubDistrict hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("subdistrictId"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				subdistrictId = list.get(0);
			}
		    if(subdistrictId==null)throw new EnjoyException("ระบุตำบลผิด");
		    /*End check subDistrict section*/
		    
		    logger.info("[validateAddress] " + provinceId + ", " + districtId + ", " + subdistrictId);
		    
		    addressBean.setProvinceId(provinceId);
		    addressBean.setDistrictId(districtId);
		    addressBean.setSubdistrictId(subdistrictId);
		    
		}catch(EnjoyException e){
			errMsg = e.getMessage();
			addressBean.setErrMsg(errMsg);
			e.printStackTrace();
		}catch(Exception e){
			errMsg = e.getMessage();
			addressBean.setErrMsg(errMsg);
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[validateAddress][End]");
		}
		return addressBean;
	}
	
	public String getProvinceName(String provinceId){
		logger.info("[getProvinceName][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						provinceName		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select provinceName from province where provinceId <> 00 and provinceId = '" + provinceId + "'";
			
			logger.info("[getProvinceName] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("provinceName"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				provinceName = list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getProvinceName][End]");
		}
		
		return provinceName;
	}
	
	public String getSubdistrictName(String subdistrictId){
		logger.info("[getSubdistrictName][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						subdistrictName		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 				= " select subdistrictName from subdistrict where subdistrictId <> 00 and subdistrictId = '" + subdistrictId + "'";
			
			logger.info("[getSubdistrictName] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("subdistrictName"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				subdistrictName = list.get(0);
			}
		    
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getSubdistrictName][End]");
		}
		
		return subdistrictName;
	}
	
	public String getDistrictName(String districtId){
		logger.info("[getDistrictName][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						districtName		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select districtName from district where districtId <> 00 and districtId = '" + districtId + "'";
			
			logger.info("[getDistrictName] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("districtName"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				districtName = list.get(0);
			}
		    
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getDistrictName][End]");
		}
		
		return districtName;
	}
	
}















