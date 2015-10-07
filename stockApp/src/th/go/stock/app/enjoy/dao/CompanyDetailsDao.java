
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
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Company;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class CompanyDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyDetailsDao.class);
	
	public List<CompanyDetailsBean> searchByCriteria(	Session 					session, 
														CompanyDetailsBean 			companyDetailsBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CompanyDetailsBean			bean					= null;
		List<CompanyDetailsBean> 	companyDetailsBeanList 	= new ArrayList<CompanyDetailsBean>();
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.companyStatusName "
								+ "	from company a, refcompanystatus b"
								+ "	where b.companyStatusCode = a.companyStatus ";
			
			if(!companyDetailsBean.getCompanyName().equals("***")){
				if(companyDetailsBean.getCompanyName().equals("")){
					hql += " and (a.companyName is null or a.companyName = '')";
				}else{
					hql += " and a.companyName like ('" + companyDetailsBean.getCompanyName() + "%')";
				}
			}
			if(!companyDetailsBean.getTin().equals("***")){
				if(companyDetailsBean.getTin().equals("")){
					hql += " and (a.tin is null or a.tin = '')";
				}else{
					hql += " and a.tin like ('" + companyDetailsBean.getTin() + "%')";
				}
			}
			if(!companyDetailsBean.getCompanyStatus().equals("")){
				hql += " and a.companyStatus = '" + companyDetailsBean.getCompanyStatus() + "'";
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("tin"				, new StringType());
			query.addScalar("companyName"		, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("buildingName"		, new StringType());
			query.addScalar("houseNumber"		, new StringType());
			query.addScalar("mooNumber"			, new StringType());
			query.addScalar("soiName"			, new StringType());
			query.addScalar("streetName"		, new StringType());
			query.addScalar("provinceCode"		, new StringType());
			query.addScalar("districtCode"		, new StringType());
			query.addScalar("subdistrictCode"	, new StringType());
			query.addScalar("postCode"			, new StringType());
			query.addScalar("tel"				, new StringType());
			query.addScalar("fax"				, new StringType());
			query.addScalar("email"				, new StringType());
			query.addScalar("remark"			, new StringType());
			query.addScalar("companyStatus"		, new StringType());
			query.addScalar("companyStatusName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CompanyDetailsBean();
				
				bean.setTin					(row[0].toString());
				bean.setCompanyName			(row[1].toString());
				bean.setBranchName			(row[2].toString());
				bean.setBuildingName		(row[3].toString());
				bean.setHouseNumber			(row[4].toString());
				bean.setMooNumber			(row[5].toString());
				bean.setSoiName				(row[6].toString());
				bean.setStreetName			(row[7].toString());
				
				provinceCode 		= EnjoyUtils.nullToStr(row[8].toString());
				districtCode 		= EnjoyUtils.nullToStr(row[9].toString());
				subdistrictCode 	= EnjoyUtils.nullToStr(row[10].toString());
				
				if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
					provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
					districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
					subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
				}else{
					provinceName		= "";
					districtName		= "";
					subdistrictName		= "";
				}
				
				
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(row[11].toString());
				bean.setTel					(row[12].toString());
				bean.setFax					(row[13].toString());
				bean.setEmail				(row[14].toString());
				bean.setRemark				(row[15].toString());
				bean.setCompanyStatus		(row[16].toString());
				bean.setCompanyStatusName	(row[17].toString());
				
				companyDetailsBeanList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return companyDetailsBeanList;
		
	}

	
	public CompanyDetailsBean getCompanyDetail(	Session 					session, 
												CompanyDetailsBean 			companyDetailsBean) throws EnjoyException{
		logger.info("[getCompanyDetail][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CompanyDetailsBean			bean					= null;
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		
		try{		
			addressDao 			= new AddressDao();
			hql					= "select * "
								+ "	from company"
								+ "	where tin = '" + companyDetailsBean.getTin() + "'";
			
			logger.info("[getCompanyDetail] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("tin"				, new StringType());
			query.addScalar("companyName"		, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("buildingName"		, new StringType());
			query.addScalar("houseNumber"		, new StringType());
			query.addScalar("mooNumber"			, new StringType());
			query.addScalar("soiName"			, new StringType());
			query.addScalar("streetName"		, new StringType());
			query.addScalar("provinceCode"		, new StringType());
			query.addScalar("districtCode"		, new StringType());
			query.addScalar("subdistrictCode"	, new StringType());
			query.addScalar("postCode"			, new StringType());
			query.addScalar("tel"				, new StringType());
			query.addScalar("fax"				, new StringType());
			query.addScalar("email"				, new StringType());
			query.addScalar("remark"			, new StringType());
			query.addScalar("companyStatus"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getCompanyDetail] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new CompanyDetailsBean();
					
					bean.setTin					(row[0].toString());
					bean.setCompanyName			(row[1].toString());
					bean.setBranchName			(row[2].toString());
					bean.setBuildingName		(row[3].toString());
					bean.setHouseNumber			(row[4].toString());
					bean.setMooNumber			(row[5].toString());
					bean.setSoiName				(row[6].toString());
					bean.setStreetName			(row[7].toString());
					
					provinceCode 		= EnjoyUtils.nullToStr(row[8].toString());
					districtCode 		= EnjoyUtils.nullToStr(row[9].toString());
					subdistrictCode 	= EnjoyUtils.nullToStr(row[10].toString());
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					
					bean.setPostCode			(row[11].toString());
					bean.setTel					(row[12].toString());
					bean.setFax					(row[13].toString());
					bean.setEmail				(row[14].toString());
					bean.setRemark				(row[15].toString());
					bean.setCompanyStatus		(row[16].toString());
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getCompanyDetail] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCompanyDetail");
		}finally{
			hql						= null;
			logger.info("[getCompanyDetail][End]");
		}
		
		return bean;
		
	}
	
	public List<ComboBean> getCompanystatusCombo(Session session) throws EnjoyException{
		logger.info("[getCompanystatusCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		
		try{
			
			hql	= "select * from refcompanystatus";

			logger.info("[getCompanystatusCombo] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("companyStatusCode"		, new StringType());
			query.addScalar("companyStatusName"		, new StringType());
			
			list		 	= query.list();
			
			comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getCompanystatusCombo] companyStatusCode :: " + row[0].toString());
				logger.info("[getCompanystatusCombo] companyStatusName :: " + row[1].toString());
				
				comboBean.setCode(row[0].toString());
				comboBean.setDesc(row[1].toString());
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[getCompanystatusCombo] " + e.getMessage());
			throw new EnjoyException("Error getCompanystatusCombo");
		}finally{
			hql						= null;
			logger.info("[getCompanystatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	
	public int checkDupTin(Session session, String tin) throws EnjoyException{
		logger.info("[checkDupTin][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "Select count(*) cou from company where tin = '" + tin + "'";
			
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
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[checkDupTin][End]");
		}
		
		return result;
	}

	
	public void insertCompanyDetail(Session session, CompanyDetailsBean companyDetailsBean) throws EnjoyException{
		logger.info("[insertCompanyDetail][Begin]");
		
		Company						company						= null;
		
		try{
			
			company = new Company();
			
			company.setTin				(companyDetailsBean.getTin());
			company.setCompanyName		(companyDetailsBean.getCompanyName());
			company.setBranchName		(companyDetailsBean.getBranchName());
			company.setBuildingName		(companyDetailsBean.getBuildingName());
			company.setHouseNumber		(companyDetailsBean.getHouseNumber());
			company.setMooNumber		(companyDetailsBean.getMooNumber());
			company.setSoiName			(companyDetailsBean.getSoiName());
			company.setStreetName		(companyDetailsBean.getStreetName());
			company.setProvinceCode		(companyDetailsBean.getProvinceCode());
			company.setDistrictCode		(companyDetailsBean.getDistrictCode());
			company.setSubdistrictCode	(companyDetailsBean.getSubdistrictCode());
			company.setPostCode			(companyDetailsBean.getPostCode());
			company.setTel				(companyDetailsBean.getTel());
			company.setFax				(companyDetailsBean.getFax());
			company.setEmail			(companyDetailsBean.getEmail());
			company.setRemark			(companyDetailsBean.getRemark());
			company.setCompanyStatus	(companyDetailsBean.getCompanyStatus());
			
			session.saveOrUpdate(company);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertCompanyDetail");
		}finally{
			
			company = null;
			logger.info("[insertCompanyDetail][End]");
		}
	}
	
	public void updateCompanyDetail(Session session, CompanyDetailsBean companyDetailsBean) throws EnjoyException{
		logger.info("[updateCompanyDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		int 							result								= 0;
		
		
		try{
			hql				= "update  Company set companyName 			= :companyName"
												+ ", branchName			= :branchName"
												+ ", buildingName		= :buildingName"
												+ ", houseNumber		= :houseNumber"
												+ ", mooNumber			= :mooNumber"
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
												+ ", companyStatus 		= :companyStatus"
										+ " where tin = :tin";
			
			query = session.createQuery(hql);
			query.setParameter("tin"				, companyDetailsBean.getTin());
			query.setParameter("companyName"		, companyDetailsBean.getCompanyName());
			query.setParameter("branchName"			, companyDetailsBean.getBranchName());
			query.setParameter("buildingName"		, companyDetailsBean.getBuildingName());
			query.setParameter("houseNumber"		, companyDetailsBean.getHouseNumber());
			query.setParameter("mooNumber"			, companyDetailsBean.getMooNumber());
			query.setParameter("soiName"			, companyDetailsBean.getSoiName());
			query.setParameter("streetName"			, companyDetailsBean.getStreetName());
			query.setParameter("provinceCode"		, companyDetailsBean.getProvinceCode());
			query.setParameter("districtCode"		, companyDetailsBean.getDistrictCode());
			query.setParameter("subdistrictCode"	, companyDetailsBean.getSubdistrictCode());
			query.setParameter("postCode"			, companyDetailsBean.getPostCode());
			query.setParameter("tel"				, companyDetailsBean.getTel());
			query.setParameter("fax"				, companyDetailsBean.getFax());
			query.setParameter("email"				, companyDetailsBean.getEmail());
			query.setParameter("remark"				, companyDetailsBean.getRemark());
			query.setParameter("companyStatus"		, companyDetailsBean.getCompanyStatus());
			
			result = query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateCompanyDetail");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateCompanyDetail][End]");
		}
	}
	
	public List<ComboBean> companyNameList(String companyName){
		logger.info("[companyNameList][Begin]");
		
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
			comboList			=  new ArrayList<ComboBean>();
			hql 				= " select tin, companyName from company where companyName like ('"+companyName+"%') and companyStatus = 'A' order by companyName asc limit 10 ";
			
			logger.info("[companyNameList] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("tin"			, new StringType());
			query.addScalar("companyName"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getProductTypeList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				comboBean 	= new ComboBean();
				
				logger.info("tin 		:: " + row[0].toString());
				logger.info("companyName 		:: " + row[1].toString());
				
				comboBean.setCode				(row[0].toString());
				comboBean.setDesc				(row[1].toString());
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[companyNameList][End]");
		}
		
		return comboList;
	}
	
	public String getTin(String companyName){
		logger.info("[getTin][Begin]");
		
		String 						hql			 		= null;
        SessionFactory 				sessionFactory		= null;
		Session 					session				= null;
		SQLQuery 					query 				= null;
		List<String>			 	list				= null;
        String						tin					= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql 		= " select tin from company where companyStatus = 'A' and companyName = '" + companyName + "'";
			
			logger.info("[getTin] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("tin"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				tin = list.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[getTin][End]");
		}
		
		return tin;
	}
	
	public List<ComboBean> getCompanyCombo(Session session) throws EnjoyException{
		logger.info("[getCompanyCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		
		try{
			
			hql	= "select tin,companyName from company where companyStatus = 'A'";

			logger.info("[getCompanyCombo] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("tin"		, new StringType());
			query.addScalar("companyName"		, new StringType());
			
			list		 	= query.list();
			
			comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getCompanyCombo] tin :: " + row[0].toString());
				logger.info("[getCompanyCombo] companyName :: " + row[1].toString());
				
				comboBean.setCode(row[0].toString());
				comboBean.setDesc(row[1].toString());
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[getCompanyCombo] " + e.getMessage());
			throw new EnjoyException("Error getCompanyCombo");
		}finally{
			hql						= null;
			logger.info("[getCompanyCombo][End]");
		}
		
		return comboList;
		
	}
	
}
