
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Companyvendor;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class CompanyVendorDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyVendorDao.class);
	
	
	public List<CompanyVendorBean> searchByCriteria(	Session 			session, 
														CompanyVendorBean 	companyVendorBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CompanyVendorBean			bean					= null;
		List<CompanyVendorBean> 	companyVendorBeanList 	= new ArrayList<CompanyVendorBean>();
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= "";
		String						districtName			= "";
		String						subdistrictName			= "";
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select a.* "
								+ "	from companyvendor a"
								+ "	where 1=1 ";
			
			if(!companyVendorBean.getVendorName().equals("***")){
				if(companyVendorBean.getVendorName().equals("")){
					hql += " and (a.vendorName is null or a.vendorName = '')";
				}else{
					hql += " and a.vendorName like ('" + companyVendorBean.getVendorName() + "%')";
				}
			}
			
			if(!companyVendorBean.getBranchName().equals("***")){
				if(companyVendorBean.getBranchName().equals("")){
					hql += " and (a.branchName is null or a.branchName = '')";
				}else{
					hql += " and a.branchName like ('" + companyVendorBean.getBranchName() + "%')";
				}
			}
			
			if(!companyVendorBean.getTin().equals("***")){
				if(companyVendorBean.getTin().equals("")){
					hql += " and (a.tin is null or a.tin = '')";
				}else{
					hql += " and a.tin like ('" + companyVendorBean.getTin() + "%')";
				}
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("vendorCode"			, new StringType());
			query.addScalar("tin"					, new StringType());
			query.addScalar("vendorName"			, new StringType());
			query.addScalar("branchName"			, new StringType());
			query.addScalar("buildingName"			, new StringType());
			query.addScalar("houseNumber"			, new StringType());
			query.addScalar("mooNumber"				, new StringType());
			query.addScalar("soiName"				, new StringType());
			query.addScalar("streetName"			, new StringType());
			query.addScalar("provinceCode"			, new StringType());
			query.addScalar("districtCode"			, new StringType());
			query.addScalar("subdistrictCode"		, new StringType());
			query.addScalar("postCode"				, new StringType());
			query.addScalar("tel"					, new StringType());
			query.addScalar("fax"					, new StringType());
			query.addScalar("email"					, new StringType());
			query.addScalar("remark"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CompanyVendorBean();
				
				logger.info("vendorCode 				:: " + row[0]);
				logger.info("vendorName 				:: " + row[1]);
				logger.info("tin		 				:: " + row[2]);
				logger.info("branchName 				:: " + row[3]);
				logger.info("buildingName 				:: " + row[4]);
				logger.info("houseNumber 				:: " + row[5]);
				logger.info("mooNumber 					:: " + row[6]);
				logger.info("soiName 					:: " + row[7]);
				logger.info("streetName 				:: " + row[8]);
				logger.info("provinceCode 				:: " + row[9]);
				logger.info("districtCode 				:: " + row[10]);
				logger.info("subdistrictCode 			:: " + row[11]);
				logger.info("postCode 					:: " + row[12]);
				logger.info("tel 						:: " + row[13]);
				logger.info("fax 						:: " + row[14]);
				logger.info("email 						:: " + row[15]);
				logger.info("remark 					:: " + row[16]);
				
				provinceCode 		= EnjoyUtils.nullToStr(row[9]);
				districtCode 		= EnjoyUtils.nullToStr(row[10]);
				subdistrictCode 	= EnjoyUtils.nullToStr(row[11]);
				
				if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
					provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
					districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
					subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
				}else{
					provinceName		= "";
					districtName		= "";
					subdistrictName		= "";
				}
				
				bean.setVendorCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setTin					(EnjoyUtils.nullToStr(row[1]));
				bean.setVendorName			(EnjoyUtils.nullToStr(row[2]));
				bean.setBranchName			(EnjoyUtils.nullToStr(row[3]));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row[4]));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row[5]));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row[6]));
				bean.setSoiName				(EnjoyUtils.nullToStr(row[7]));
				bean.setStreetName			(EnjoyUtils.nullToStr(row[8]));
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(EnjoyUtils.nullToStr(row[12]));
				bean.setTel					(EnjoyUtils.nullToStr(row[13]));
				bean.setFax					(EnjoyUtils.nullToStr(row[14]));
				bean.setEmail				(EnjoyUtils.nullToStr(row[15]));
				bean.setRemark				(EnjoyUtils.nullToStr(row[16]));
				
				companyVendorBeanList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return companyVendorBeanList;
		
	}
	
	public CompanyVendorBean getCompanyVendor	(	Session 			session, 
													CompanyVendorBean 	companyVendorBean) throws EnjoyException{
		logger.info("[getCompanyVendor][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CompanyVendorBean			bean					= null;
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		
		try{		
			addressDao 			= new AddressDao();
			hql					= "select *"
								+ "	from companyvendor"
								+ "	where vendorCode 	= '" + companyVendorBean.getVendorCode() + "'";
			
			logger.info("[getReciveOrderMaster] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("vendorCode"			, new StringType());
			query.addScalar("tin"					, new StringType());
			query.addScalar("vendorName"			, new StringType());
			query.addScalar("branchName"			, new StringType());
			query.addScalar("buildingName"			, new StringType());
			query.addScalar("houseNumber"			, new StringType());
			query.addScalar("mooNumber"				, new StringType());
			query.addScalar("soiName"				, new StringType());
			query.addScalar("streetName"			, new StringType());
			query.addScalar("provinceCode"			, new StringType());
			query.addScalar("districtCode"			, new StringType());
			query.addScalar("subdistrictCode"		, new StringType());
			query.addScalar("postCode"				, new StringType());
			query.addScalar("tel"					, new StringType());
			query.addScalar("fax"					, new StringType());
			query.addScalar("email"					, new StringType());
			query.addScalar("remark"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getCompanyVendor] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new CompanyVendorBean();
					
					logger.info("vendorCode 				:: " + row[0]);
					logger.info("vendorName 				:: " + row[1]);
					logger.info("tin		 				:: " + row[2]);
					logger.info("branchName 				:: " + row[3]);
					logger.info("buildingName 				:: " + row[4]);
					logger.info("houseNumber 				:: " + row[5]);
					logger.info("mooNumber 					:: " + row[6]);
					logger.info("soiName 					:: " + row[7]);
					logger.info("streetName 				:: " + row[8]);
					logger.info("provinceCode 				:: " + row[9]);
					logger.info("districtCode 				:: " + row[10]);
					logger.info("subdistrictCode 			:: " + row[11]);
					logger.info("postCode 					:: " + row[12]);
					logger.info("tel 						:: " + row[13]);
					logger.info("fax 						:: " + row[14]);
					logger.info("email 						:: " + row[15]);
					logger.info("remark 					:: " + row[16]);
					
					provinceCode 		= EnjoyUtils.nullToStr(row[9]);
					districtCode 		= EnjoyUtils.nullToStr(row[10]);
					subdistrictCode 	= EnjoyUtils.nullToStr(row[11]);
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					bean.setVendorCode			(EnjoyUtils.nullToStr(row[0]));
					bean.setTin					(EnjoyUtils.nullToStr(row[1]));
					bean.setVendorName			(EnjoyUtils.nullToStr(row[2]));
					bean.setBranchName			(EnjoyUtils.nullToStr(row[3]));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row[4]));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row[5]));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row[6]));
					bean.setSoiName				(EnjoyUtils.nullToStr(row[7]));
					bean.setStreetName			(EnjoyUtils.nullToStr(row[8]));
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(EnjoyUtils.nullToStr(row[12]));
					bean.setTel					(EnjoyUtils.nullToStr(row[13]));
					bean.setFax					(EnjoyUtils.nullToStr(row[14]));
					bean.setEmail				(EnjoyUtils.nullToStr(row[15]));
					bean.setRemark				(EnjoyUtils.nullToStr(row[16]));
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getCompanyVendor] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCompanyVendor");
		}finally{
			hql						= null;
			logger.info("[getCompanyVendor][End]");
		}
		
		return bean;
		
	}
	
	public void insertCompanyVendor(Session session, CompanyVendorBean 		companyVendorBean) throws EnjoyException{
		logger.info("[insertCompanyVendor][Begin]");
		
		Companyvendor	companyvendor						= null;
		
		try{
			
			companyvendor = new Companyvendor();
			
//			companyvendor.setVendorCode		(companyVendorBean.getVendorCode());
			companyvendor.setVendorName		(companyVendorBean.getVendorName());
			companyvendor.setTin			(companyVendorBean.getTin());
			companyvendor.setBranchName		(companyVendorBean.getBranchName());
			companyvendor.setBuildingName	(companyVendorBean.getBuildingName());
			companyvendor.setHouseNumber	(companyVendorBean.getHouseNumber());
			companyvendor.setMooNumber		(companyVendorBean.getMooNumber());
			companyvendor.setSoiName		(companyVendorBean.getSoiName());
			companyvendor.setStreetName		(companyVendorBean.getStreetName());
			companyvendor.setProvinceCode	(companyVendorBean.getProvinceCode());
			companyvendor.setDistrictCode	(companyVendorBean.getDistrictCode());
			companyvendor.setSubdistrictCode(companyVendorBean.getSubdistrictCode());
			companyvendor.setPostCode		(companyVendorBean.getPostCode());
			companyvendor.setTel			(companyVendorBean.getTel());
			companyvendor.setFax			(companyVendorBean.getFax());
			companyvendor.setEmail			(companyVendorBean.getEmail());
			companyvendor.setRemark			(companyVendorBean.getRemark());
			
			session.saveOrUpdate(companyvendor);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertCompanyVendor");
		}finally{
			
			companyvendor = null;
			logger.info("[insertCompanyVendor][End]");
		}
	}
	
	public void updateCompanyvendor(Session session, CompanyVendorBean 		companyVendorBean) throws EnjoyException{
		logger.info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Companyvendor set vendorName 	= :vendorName"
												+ ", tin				= :tin"
												+ ", branchName			= :branchName"
												+ ", buildingName		= :buildingName"
												+ ", houseNumber		= :houseNumber"
												+ ", mooNumber 			= :mooNumber"
												+ ", soiName			= :soiName"
												+ ", streetName			= :streetName"
												+ ", provinceCode 		= :provinceCode"
												+ ", districtCode 		= :districtCode"
												+ ", subdistrictCode 	= :subdistrictCode"
												+ ", postCode 			= :postCode"
												+ ", tel 				= :tel"
												+ ", fax 				= :fax"
												+ ", email 				= :email"
												+ ", remark 			= :remark"
										+ " where vendorCode = :vendorCode";
			
			query = session.createQuery(hql);
			query.setParameter("vendorName"		, companyVendorBean.getVendorName());
			query.setParameter("tin"			, companyVendorBean.getTin());
			query.setParameter("branchName"		, companyVendorBean.getBranchName());
			query.setParameter("buildingName"	, companyVendorBean.getBuildingName());
			query.setParameter("houseNumber"	, companyVendorBean.getHouseNumber());
			query.setParameter("mooNumber"		, companyVendorBean.getMooNumber());
			query.setParameter("soiName"		, companyVendorBean.getSoiName());
			query.setParameter("streetName"		, companyVendorBean.getStreetName());
			query.setParameter("provinceCode"	, companyVendorBean.getProvinceCode());
			query.setParameter("districtCode"	, companyVendorBean.getDistrictCode());
			query.setParameter("subdistrictCode", companyVendorBean.getSubdistrictCode());
			query.setParameter("postCode"		, companyVendorBean.getPostCode());
			query.setParameter("tel"			, companyVendorBean.getTel());
			query.setParameter("fax"			, companyVendorBean.getFax());
			query.setParameter("email"			, companyVendorBean.getEmail());
			query.setParameter("remark"			, companyVendorBean.getRemark());
			query.setParameter("vendorCode"		, EnjoyUtils.parseInt(companyVendorBean.getVendorCode()));
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateReciveOrderMaster][End]");
		}
	}
	
	public int checkDupVendorName(String vendorName, String branchName, String vendorCode) throws EnjoyException{
		logger.info("[checkDupVendorName][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			hql	= "select count(*) cou from companyvendor where vendorName = '" + vendorName + "' and branchName = '" + branchName + "'";
			
			if(!vendorCode.equals("")){
				hql += " and vendorCode <> '" + vendorCode + "'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupVendorName] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupVendorName][End]");
		}
		
		return result;
	}
	
	public int checkDupTin(String tin, String vendorCode) throws EnjoyException{
		logger.info("[checkDupTin][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			hql	= "select count(*) cou from companyvendor where tin = '" + tin + "' and tin is not null";
			
			if(!vendorCode.equals("")){
				hql += " and vendorCode <> '" + vendorCode + "'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupTin] result 			:: " + result);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[checkDupTin][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> tinList(String tin){
		logger.info("[tinList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<Object[]>				list				= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			= new ArrayList<ComboBean>();
			hql 				= "select tin from companyvendor where tin like ('"+tin+"%') order by tin asc limit 10 ";
			
			logger.info("[tinList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("tin"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[tinList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("tin 		:: " + row[0]);
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row[0]));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[tinList][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> vendorNameList(String vendorName){
		logger.info("[vendorNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<Object[]>				list				= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			= new ArrayList<ComboBean>();
			hql 				= "select vendorCode, vendorName from companyvendor where vendorName like ('"+vendorName+"%') order by vendorName asc limit 10 ";
			
			logger.info("[vendorNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("vendorCode"			, new StringType());
			query.addScalar("vendorName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[vendorNameList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("vendorCode 		:: " + row[0]);
				logger.info("vendorName 		:: " + row[1]);
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[vendorNameList][End]");
		}
		
		return comboList;
	}

	public List<ComboBean> branchNameList(String vendorName, String branchName){
		logger.info("[branchNameList][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<Object[]>				list				= null;
		List<ComboBean>				comboList 			= null;
		ComboBean					comboBean			= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			comboList			= new ArrayList<ComboBean>();
			hql 				= "select vendorCode, branchName from companyvendor"
									+ " where vendorName = '"+ vendorName + "'"
										+ " and branchName like ('"+branchName+"%')"
									+ " order by vendorName asc limit 10 ";
			
			logger.info("[branchNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("vendorCode"			, new StringType());
			query.addScalar("branchName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[branchNameList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("vendorCode 		:: " + row[0]);
				logger.info("branchName 		:: " + row[1]);
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[branchNameList][End]");
		}
		
		return comboList;
	}
	
	public List<CompanyVendorBean> getCompanyVendorByName( String 		vendorName
														   ,String 		branchName) throws EnjoyException{
		logger.info("[getCompanyVendorByName][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		CompanyVendorBean			bean						= null;
		AddressDao					addressDao					= null;
		String						provinceCode				= null;
		String						districtCode				= null;
		String						subdistrictCode				= null;
		String						provinceName				= null;
		String						districtName				= null;
		String						subdistrictName				= null;
		List<CompanyVendorBean> 	companyVendorList 			= new ArrayList<CompanyVendorBean>();
		String						lv_where					= "";
		String						lv_orderBy					= "";
		SessionFactory 				sessionFactory				= null;
		Session 					session						= null;
		
		try{	
			
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql					= "select * from companyvendor";
			
			lv_where = " where vendorName = '" + vendorName + "'";
			
			if(!branchName.equals("")){
				lv_where += " and branchName = '" + branchName + "'";
			}
			
			lv_orderBy = " order by vendorName asc, branchName asc";
			
			hql += lv_where + lv_orderBy;
			
			logger.info("[getCompanyVendorByName] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("vendorCode"			, new StringType());
			query.addScalar("vendorName"			, new StringType());
			query.addScalar("tin"					, new StringType());
			query.addScalar("branchName"			, new StringType());
			query.addScalar("buildingName"			, new StringType());
			query.addScalar("houseNumber"			, new StringType());
			query.addScalar("mooNumber"				, new StringType());
			query.addScalar("soiName"				, new StringType());
			query.addScalar("streetName"			, new StringType());
			query.addScalar("provinceCode"			, new StringType());
			query.addScalar("districtCode"			, new StringType());
			query.addScalar("subdistrictCode"		, new StringType());
			query.addScalar("postCode"				, new StringType());
			query.addScalar("tel"					, new StringType());
			query.addScalar("fax"					, new StringType());
			query.addScalar("email"					, new StringType());
			query.addScalar("remark"				, new StringType());
			
			list		 	= query.list();
			addressDao		= new AddressDao();
			
			logger.info("[getCompanyVendorByName] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CompanyVendorBean();
				
				logger.info("vendorCode 				:: " + row[0]);
				logger.info("vendorName 				:: " + row[1]);
				logger.info("tin 						:: " + row[2]);
				logger.info("branchName 				:: " + row[3]);
				logger.info("buildingName 				:: " + row[4]);
				logger.info("houseNumber 				:: " + row[5]);
				logger.info("mooNumber 					:: " + row[6]);
				logger.info("soiName 					:: " + row[7]);
				logger.info("streetName 				:: " + row[8]);
				logger.info("provinceCode 				:: " + row[9]);
				logger.info("districtCode 				:: " + row[10]);
				logger.info("subdistrictCode 			:: " + row[11]);
				logger.info("postCode 					:: " + row[12]);
				logger.info("tel 						:: " + row[13]);
				logger.info("fax 						:: " + row[14]);
				logger.info("email 						:: " + row[15]);
				logger.info("remark 					:: " + row[16]);
				
				provinceCode 		= EnjoyUtils.nullToStr(row[9]);
				districtCode 		= EnjoyUtils.nullToStr(row[10]);
				subdistrictCode 	= EnjoyUtils.nullToStr(row[11]);
				provinceName		= addressDao.getProvinceName(provinceCode);
				districtName		= addressDao.getDistrictName(districtCode);
				subdistrictName		= addressDao.getSubdistrictName(subdistrictCode);
				
				bean.setVendorCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setVendorName			(EnjoyUtils.nullToStr(row[1]));
				bean.setTin					(EnjoyUtils.nullToStr(row[2]));
				bean.setBranchName			(EnjoyUtils.nullToStr(row[3]));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row[4]));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row[5]));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row[6]));
				bean.setSoiName				(EnjoyUtils.nullToStr(row[7]));
				bean.setStreetName			(EnjoyUtils.nullToStr(row[8]));
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(EnjoyUtils.nullToStr(row[12]));
				bean.setTel					(EnjoyUtils.nullToStr(row[13]));
				bean.setFax					(EnjoyUtils.nullToStr(row[14]));
				bean.setEmail				(EnjoyUtils.nullToStr(row[15]));
				bean.setRemark				(EnjoyUtils.nullToStr(row[16]));
				
				companyVendorList.add(bean);
				
			}	
			
		}catch(Exception e){
			logger.info("[getCompanyVendorByName] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrdeDetailList");
		}finally{
			session.close();
			sessionFactory			= null;
			session					= null;
			hql						= null;
			logger.info("[getCompanyVendorByName][End]");
		}
		
		return companyVendorList;
		
	}
	
}

