
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
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CustomerDetailsLookUpForm;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.model.Customer;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class CustomerDetailsDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CustomerDetailsDao.class);
	
	public List<CustomerDetailsBean> searchByCriteria(	Session 					session, 
														CustomerDetailsBean 		customerDetailsBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CustomerDetailsBean			bean					= null;
		List<CustomerDetailsBean> 	customerDetailsBeanList = new ArrayList<CustomerDetailsBean>();
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		String						fullName				= null;
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.customerStatusName "
								+ "	from customer a, refcustomerstatus b"
								+ "	where b.customerStatusCode = a.cusStatus ";
			
			if(!customerDetailsBean.getCusCode().equals("***")){
				if(customerDetailsBean.getCusCode().equals("")){
					hql += " and (a.cusCode is null or a.cusCode = '')";
				}else{
					hql += " and a.cusCode like ('" + customerDetailsBean.getCusCode() + "%')";
				}
			}
			if(!customerDetailsBean.getFullName().equals("***")){
				if(customerDetailsBean.getFullName().equals("")){
					hql += " and CONCAT(a.cusName, ' ', a.cusSurname) = ''";
				}else{
					hql += " and CONCAT(a.cusName, ' ', a.cusSurname) like ('" + customerDetailsBean.getFullName() + "%')";
				}
			}
			if(!customerDetailsBean.getCusStatus().equals("")){
				hql += " and a.cusStatus = '" + customerDetailsBean.getCusStatus() + "'";
			}
			if(!customerDetailsBean.getIdNumber().equals("***")){
				if(customerDetailsBean.getIdNumber().equals("")){
					hql += " and (a.idNumber is null or a.idNumber = '')";
				}else{
					hql += " and a.idNumber like ('" + customerDetailsBean.getIdNumber() + "%')";
				}
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("cusCode"			, new StringType());
			query.addScalar("cusName"			, new StringType());
			query.addScalar("cusSurname"		, new StringType());
			query.addScalar("sex"				, new StringType());
			query.addScalar("idType"			, new StringType());
			query.addScalar("idNumber"			, new StringType());
			query.addScalar("birthDate"			, new StringType());
			query.addScalar("religion"			, new StringType());
			query.addScalar("job"				, new StringType());
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
			query.addScalar("cusStatus"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("point"				, new StringType());
			query.addScalar("remark"			, new StringType());
			query.addScalar("customerStatusName", new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("cusGroupCode"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CustomerDetailsBean();
				
				logger.info("cusCode 			:: " + row[0]);
				logger.info("cusName 			:: " + row[1]);
				logger.info("cusSurname 		:: " + row[2]);
				logger.info("sex 				:: " + row[3]);
				logger.info("idType 			:: " + row[4]);
				logger.info("idNumber 			:: " + row[5]);
				logger.info("birthDate 			:: " + row[6]);
				logger.info("religion 			:: " + row[7]);
				logger.info("job 				:: " + row[8]);
				logger.info("buildingName 		:: " + row[9]);
				logger.info("houseNumber 		:: " + row[10]);
				logger.info("mooNumber 			:: " + row[11]);
				logger.info("soiName 			:: " + row[12]);
				logger.info("streetName 		:: " + row[13]);
				logger.info("provinceCode 		:: " + row[14]);
				logger.info("districtCode 		:: " + row[15]);
				logger.info("subdistrictCode 	:: " + row[16]);
				logger.info("postCode 			:: " + row[17]);
				logger.info("tel 				:: " + row[18]);
				logger.info("fax 				:: " + row[19]);
				logger.info("email 				:: " + row[20]);
				logger.info("cusStatus 			:: " + row[21]);
				logger.info("startDate 			:: " + row[22]);
				logger.info("expDate 			:: " + row[23]);
				logger.info("point 				:: " + row[24]);
				logger.info("remark 			:: " + row[25]);
				logger.info("customerStatusName :: " + row[26]);
				logger.info("branchName			:: " + row[27]);
				logger.info("cusGroupCode		:: " + row[28]);
				
				bean.setCusCode				(EnjoyUtils.nullToStr(row[0]));
				bean.setCusName				(EnjoyUtils.nullToStr(row[1]));
				bean.setCusSurname			(EnjoyUtils.nullToStr(row[2]));
				bean.setSex					(EnjoyUtils.nullToStr(row[3]));
				bean.setIdType				(EnjoyUtils.nullToStr(row[4]));
				bean.setIdNumber			(EnjoyUtils.nullToStr(row[5]));
				bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row[6]));
				bean.setReligion			(EnjoyUtils.nullToStr(row[7]));
				bean.setJob					(EnjoyUtils.nullToStr(row[8]));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row[9]));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row[10]));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row[11]));
				bean.setSoiName				(EnjoyUtils.nullToStr(row[12]));
				bean.setStreetName			(EnjoyUtils.nullToStr(row[13]));
				
				provinceCode 		= EnjoyUtils.nullToStr(row[14]);
				districtCode 		= EnjoyUtils.nullToStr(row[15]);
				subdistrictCode 	= EnjoyUtils.nullToStr(row[16]);
				
				if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
					provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
					districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
					subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
				}else{
					provinceName		= "";
					districtName		= "";
					subdistrictName		= "";
				}
				
				fullName			= EnjoyUtils.nullToStr(row[1]) + " " + EnjoyUtils.nullToStr(row[2]);
				
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(EnjoyUtils.nullToStr(row[17]));
				bean.setTel					(EnjoyUtils.nullToStr(row[18]));
				bean.setFax					(EnjoyUtils.nullToStr(row[19]));
				bean.setEmail				(EnjoyUtils.nullToStr(row[20]));
				bean.setCusStatus			(EnjoyUtils.nullToStr(row[21]));
				bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row[22]));
				bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row[23]));
				bean.setPoint				(EnjoyUtils.nullToStr(row[24]));
				bean.setRemark				(EnjoyUtils.nullToStr(row[25]));
				bean.setCustomerStatusName	(EnjoyUtils.nullToStr(row[26]));
				bean.setFullName			(fullName);
				bean.setBranchName			(EnjoyUtils.nullToStr(row[27]));
				bean.setCusGroupCode		(EnjoyUtils.nullToStr(row[28]));
				
				customerDetailsBeanList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return customerDetailsBeanList;
		
	}
	
	public CustomerDetailsBean getCustomerDetail(CustomerDetailsBean 		customerDetailsBean) throws EnjoyException{
		logger.info("[getCustomerDetail][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CustomerDetailsBean			bean					= null;
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		String						fullName				= "";
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{		
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.groupSalePrice"
									+ " from customer a LEFT JOIN relationgroupcustomer b"
									+ " 	ON a.cusGroupCode = b.cusGroupCode and b.cusGroupStatus = 'A'"
								+ "	where a.cusCode = '" + customerDetailsBean.getCusCode() + "'";
			
			logger.info("[getCustomerDetail] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("cusCode"			, new StringType());
			query.addScalar("cusName"			, new StringType());
			query.addScalar("cusSurname"		, new StringType());
			query.addScalar("sex"				, new StringType());
			query.addScalar("idType"			, new StringType());
			query.addScalar("idNumber"			, new StringType());
			query.addScalar("birthDate"			, new StringType());
			query.addScalar("religion"			, new StringType());
			query.addScalar("job"				, new StringType());
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
			query.addScalar("cusStatus"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("point"				, new StringType());
			query.addScalar("remark"			, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("cusGroupCode"		, new StringType());
			query.addScalar("groupSalePrice"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getCustomerDetail] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new CustomerDetailsBean();
					
					bean.setCusCode				(EnjoyUtils.nullToStr(row[0]));
					bean.setCusName				(EnjoyUtils.nullToStr(row[1]));
					bean.setCusSurname			(EnjoyUtils.nullToStr(row[2]));
					bean.setSex					(EnjoyUtils.nullToStr(row[3]));
					bean.setIdType				(EnjoyUtils.nullToStr(row[4]));
					bean.setIdNumber			(EnjoyUtils.nullToStr(row[5]));
					bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row[6]));
					bean.setReligion			(EnjoyUtils.nullToStr(row[7]));
					bean.setJob					(EnjoyUtils.nullToStr(row[8]));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row[9]));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row[10]));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row[11]));
					bean.setSoiName				(EnjoyUtils.nullToStr(row[12]));
					bean.setStreetName			(EnjoyUtils.nullToStr(row[13]));
					
					provinceCode 		= EnjoyUtils.nullToStr(row[14]);
					districtCode 		= EnjoyUtils.nullToStr(row[15]);
					subdistrictCode 	= EnjoyUtils.nullToStr(row[16]);
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					fullName			= EnjoyUtils.nullToStr(row[1]) + " " + EnjoyUtils.nullToStr(row[2]);
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(EnjoyUtils.nullToStr(row[17]));
					bean.setTel					(EnjoyUtils.nullToStr(row[18]));
					bean.setFax					(EnjoyUtils.nullToStr(row[19]));
					bean.setEmail				(EnjoyUtils.nullToStr(row[20]));
					bean.setCusStatus			(EnjoyUtils.nullToStr(row[21]));
					bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row[22]));
					bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row[23]));
					bean.setPoint				(EnjoyUtils.nullToStr(row[24]));
					bean.setRemark				(EnjoyUtils.nullToStr(row[25]));
					bean.setBranchName			(EnjoyUtils.nullToStr(row[26]));
					bean.setCusGroupCode		(EnjoyUtils.nullToStr(row[27]));
					bean.setGroupSalePrice		(EnjoyUtils.nullToStr(row[28]));
					bean.setFullName			(fullName);
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getCustomerDetail] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCustomerDetail");
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getCustomerDetail][End]");
		}
		
		return bean;
		
	}
	
	public List<ComboBean> getStatusCombo() throws EnjoyException{
		logger.info("[getStatusCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			
			hql	= "select * from refcustomerstatus";

			logger.info("[getStatusCombo] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("customerStatusCode"		, new StringType());
			query.addScalar("customerStatusName"		, new StringType());
			
			list		 	= query.list();
			
			//comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getStatusCombo] customerStatusCode :: " + row[0]);
				logger.info("[getStatusCombo] customerStatusName :: " + row[1]);
				
				comboBean.setCode(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[getStatusCombo] " + e.getMessage());
			throw new EnjoyException("Error getStatusCombo");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getStatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	public void insertCustomerDetails(Session session, CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		logger.info("[insertCustomerDetails][Begin]");
		
		Customer	customer	= null;
		String		cusCode		= "";
		
		try{
			
			customer 	= new Customer();
			cusCode		= this.genCusCode();
			
			customer.setCusCode				(cusCode);
			customer.setCusName				(customerDetailsBean.getCusName());
			customer.setCusSurname			(customerDetailsBean.getCusSurname());
			customer.setBranchName			(customerDetailsBean.getBranchName());
			customer.setCusGroupCode		(EnjoyUtils.parseInt(customerDetailsBean.getCusGroupCode()));
			customer.setSex					(customerDetailsBean.getSex());
			customer.setIdType				(customerDetailsBean.getIdType());
			customer.setIdNumber			(customerDetailsBean.getIdNumber());
			customer.setBirthDate			(customerDetailsBean.getBirthDate());
			customer.setReligion			(customerDetailsBean.getReligion());
			customer.setJob					(customerDetailsBean.getJob());
			customer.setBuildingName		(customerDetailsBean.getBuildingName());
			customer.setHouseNumber			(customerDetailsBean.getHouseNumber());
			customer.setMooNumber			(customerDetailsBean.getMooNumber());
			customer.setSoiName				(customerDetailsBean.getSoiName());
			customer.setStreetName			(customerDetailsBean.getStreetName());
			customer.setSubdistrictCode		(customerDetailsBean.getSubdistrictCode());
			customer.setDistrictCode		(customerDetailsBean.getDistrictCode());
			customer.setProvinceCode		(customerDetailsBean.getProvinceCode());
			customer.setPostCode			(customerDetailsBean.getPostCode());
			customer.setTel					(customerDetailsBean.getTel());
			customer.setFax					(customerDetailsBean.getFax());
			customer.setEmail				(customerDetailsBean.getEmail());
			customer.setCusStatus			(customerDetailsBean.getCusStatus());
			customer.setStartDate			(customerDetailsBean.getStartDate());
			customer.setExpDate				(customerDetailsBean.getExpDate());
			customer.setPoint				(EnjoyUtils.parseInt(customerDetailsBean.getPoint()));
			customer.setRemark				(customerDetailsBean.getRemark());
			
			session.saveOrUpdate(customer);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertCustomerDetails");
		}finally{
			
			customer = null;
			logger.info("[insertCustomerDetails][End]");
		}
	}
	
	public void updateCustomerDetail(Session session, CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		logger.info("[updateCustomerDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Customer set cusName 			= :cusName"
												+ ", cusSurname			= :cusSurname"
												+ ", branchName			= :branchName"
												+ ", cusGroupCode		= :cusGroupCode"
												+ ", sex				= :sex"
												+ ", idType				= :idType"
												+ ", idNumber			= :idNumber"
												+ ", birthDate			= :birthDate"
												+ ", religion			= :religion"
												+ ", job 				= :job"
												+ ", buildingName 		= :buildingName"
												+ ", houseNumber 		= :houseNumber"
												+ ", mooNumber 			= :mooNumber"
												+ ", soiName 			= :soiName"
												+ ", streetName 		= :streetName"
												+ ", subdistrictCode 	= :subdistrictCode"
												+ ", districtCode 		= :districtCode"
												+ ", provinceCode 		= :provinceCode"
												+ ", postCode 			= :postCode"
												+ ", tel 				= :tel"
												+ ", fax 				= :fax"
												+ ", email 				= :email"
												+ ", cusStatus 			= :cusStatus"
												+ ", startDate 			= :startDate"
												+ ", expDate 			= :expDate"
												+ ", point 				= :point"
												+ ", remark 			= :remark"
										+ " where cusCode = :cusCode";
			
			query = session.createQuery(hql);
			query.setParameter("cusName"			, customerDetailsBean.getCusName());
			query.setParameter("cusSurname"			, customerDetailsBean.getCusSurname());
			query.setParameter("branchName"			, customerDetailsBean.getBranchName());
			query.setParameter("cusGroupCode"		, EnjoyUtils.parseInt(customerDetailsBean.getCusGroupCode()));
			query.setParameter("sex"				, customerDetailsBean.getSex());
			query.setParameter("idType"				, customerDetailsBean.getIdType());
			query.setParameter("idNumber"			, customerDetailsBean.getIdNumber());
			query.setParameter("birthDate"			, customerDetailsBean.getBirthDate());
			query.setParameter("religion"			, customerDetailsBean.getReligion());
			query.setParameter("job"				, customerDetailsBean.getJob());
			query.setParameter("buildingName"		, customerDetailsBean.getBuildingName());
			query.setParameter("houseNumber"		, customerDetailsBean.getHouseNumber());
			query.setParameter("mooNumber"			, customerDetailsBean.getMooNumber());
			query.setParameter("soiName"			, customerDetailsBean.getSoiName());
			query.setParameter("streetName"			, customerDetailsBean.getStreetName());
			query.setParameter("subdistrictCode"	, customerDetailsBean.getSubdistrictCode());
			query.setParameter("districtCode"		, customerDetailsBean.getDistrictCode());
			query.setParameter("provinceCode"		, customerDetailsBean.getProvinceCode());
			query.setParameter("postCode"			, customerDetailsBean.getPostCode());
			query.setParameter("tel"				, customerDetailsBean.getTel());
			query.setParameter("fax"				, customerDetailsBean.getFax());
			query.setParameter("email"				, customerDetailsBean.getEmail());
			query.setParameter("cusStatus"			, customerDetailsBean.getCusStatus());
			query.setParameter("startDate"			, customerDetailsBean.getStartDate());
			query.setParameter("expDate"			, customerDetailsBean.getExpDate());
			query.setParameter("point"				, EnjoyUtils.parseInt(customerDetailsBean.getPoint()));
			query.setParameter("remark"				, customerDetailsBean.getRemark());
			query.setParameter("cusCode"			, customerDetailsBean.getCusCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateCustomerDetail");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateCustomerDetail][End]");
		}
	}
	
	public String genCusCode() throws EnjoyException{
		logger.info("[genCusCode][Begin]");
		
		String				hql						= null;
		List<Integer>		list					= null;
		SQLQuery 			query 					= null;
		SessionFactory 		sessionFactory			= null;
		Session 			session					= null;
		String				newId					= "";
		String				codeDisplay				= null;
		RefconstantcodeDao	refconstantcodeDao		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			refconstantcodeDao 	= new RefconstantcodeDao();
			codeDisplay			= refconstantcodeDao.getCodeDisplay("1");
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(cusCode, '-', -1)) + 1) AS newId"
							+ "	FROM customer"
							+ "	WHERE"
							+ "		SUBSTRING_INDEX(cusCode, '-', 1) = '" + codeDisplay + "'";
			query			= session.createSQLQuery(hql);
			
			
			query.addScalar("newId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingCusCode(), list.get(0));
			}else{
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingCusCode(), 1);
			}
			
			logger.info("[genCusCode] newId 			:: " + newId);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[genCusCode][End]");
		}
		
		return newId;
	}
	
	public int checkDupIdNumber(String idNumber, String cusCode) throws EnjoyException{
		logger.info("[checkDupIdNumber][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			hql	= "select count(*) cou from customer where idNumber = '" + idNumber + "' and idNumber is not null";
			
			if(!cusCode.equals("")){
				hql += " and cusCode <> '" + cusCode + "'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupIdNumber] result 			:: " + result);
			
			
			
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
			logger.info("[checkDupIdNumber][End]");
		}
		
		return result;
	}
	
	public int checkDupCusName(String cusName, String cusSurname, String branchName, String cusCode) throws EnjoyException{
		logger.info("[checkDupIdNumber][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		SessionFactory 					sessionFactory						= null;
		Session 						session								= null;
		
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			
			hql	= "select count(*) cou from customer where cusName = '" + cusName + "' and cusSurname = '"+cusSurname+"'";
			
			if(!branchName.equals("")){
				hql += " and branchName = '" + cusCode + "'";
			}
			
			if(!cusCode.equals("")){
				hql += " and cusCode <> '" + cusCode + "'";
			}
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[checkDupIdNumber] result 			:: " + result);
			
			
			
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
			logger.info("[checkDupIdNumber][End]");
		}
		
		return result;
	}
	
	public List<CustomerDetailsBean> getCustomerDetailsLookUpList(	Session 					session, 
																	CustomerDetailsLookUpForm 	form) throws EnjoyException{
		logger.info("[getCustomerDetailsLookUpList][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CustomerDetailsBean 		bean					= null;
		List<CustomerDetailsBean> 	listData 				= new ArrayList<CustomerDetailsBean>();
		String						find					= null;
		AddressDao					addressDao				= null;
		String						provinceCode			= null;
		String						districtCode			= null;
		String						subdistrictCode			= null;
		String						provinceName			= null;
		String						districtName			= null;
		String						subdistrictName			= null;
		String						fullName				= null;
		String						column					= "";
		String						orderBy					= "";
		
		try{
			find				= form.getFind();
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.groupSalePrice"
									+ " from customer a LEFT JOIN relationgroupcustomer b"
									+ " 	ON a.cusGroupCode = b.cusGroupCode and b.cusGroupStatus = 'A'"
									+ " where a.cusStatus = 'A'";
			
			if(find!=null && !find.equals("")){
				
				column 	= form.getColumn().equals("fullName")?"CONCAT(a.cusName, ' ', a.cusSurname)":"a." + form.getColumn();
				hql 	+= " and " + column;
				
				if(form.getLikeFlag().equals("Y")){
					hql += " like ('" + find + "%')";
				}else{
					hql += " = '" + find + "'";
				}
				
			}
			orderBy = form.getOrderBy().equals("fullName")?"CONCAT(a.cusName, ' ', a.cusSurname)":"a." + form.getOrderBy();
			hql 	+= " order by " + orderBy + " " + form.getSortBy();
			
			logger.info("[getCustomerDetailsLookUpList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("cusCode"			, new StringType());
			query.addScalar("cusName"			, new StringType());
			query.addScalar("cusSurname"		, new StringType());
			query.addScalar("sex"				, new StringType());
			query.addScalar("idType"			, new StringType());
			query.addScalar("idNumber"			, new StringType());
			query.addScalar("birthDate"			, new StringType());
			query.addScalar("religion"			, new StringType());
			query.addScalar("job"				, new StringType());
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
			query.addScalar("cusStatus"			, new StringType());
			query.addScalar("startDate"			, new StringType());
			query.addScalar("expDate"			, new StringType());
			query.addScalar("point"				, new StringType());
			query.addScalar("remark"			, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("cusGroupCode"		, new StringType());
			query.addScalar("groupSalePrice"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getCustomerDetailsLookUpList] list :: " + list);
			
			if(list!=null){
				logger.info("[getCustomerDetailsLookUpList] list.size() :: " + list.size());
				
				for(Object[] row:list){
					bean 	= new CustomerDetailsBean();
					
					logger.info("cusCode 			:: " + row[0]);
					logger.info("cusName 			:: " + row[1]);
					logger.info("cusSurname 		:: " + row[2]);
					logger.info("sex 				:: " + row[3]);
					logger.info("idType 			:: " + row[4]);
					logger.info("idNumber 			:: " + row[5]);
					logger.info("birthDate 			:: " + row[6]);
					logger.info("religion 			:: " + row[7]);
					logger.info("job 				:: " + row[8]);
					logger.info("buildingName 		:: " + row[9]);
					logger.info("houseNumber 		:: " + row[10]);
					logger.info("mooNumber 			:: " + row[11]);
					logger.info("soiName 			:: " + row[12]);
					logger.info("streetName 		:: " + row[13]);
					logger.info("provinceCode 		:: " + row[14]);
					logger.info("districtCode 		:: " + row[15]);
					logger.info("subdistrictCode 	:: " + row[16]);
					logger.info("postCode 			:: " + row[17]);
					logger.info("tel 				:: " + row[18]);
					logger.info("fax 				:: " + row[19]);
					logger.info("email 				:: " + row[20]);
					logger.info("cusStatus 			:: " + row[21]);
					logger.info("startDate 			:: " + row[22]);
					logger.info("expDate 			:: " + row[23]);
					logger.info("point 				:: " + row[24]);
					logger.info("remark 			:: " + row[25]);
					logger.info("branchName			:: " + row[26]);
					logger.info("cusGroupCode		:: " + row[27]);
					logger.info("groupSalePrice		:: " + row[28]);
					
					bean.setCusCode				(EnjoyUtils.nullToStr(row[0]));
					bean.setCusName				(EnjoyUtils.nullToStr(row[1]));
					bean.setCusSurname			(EnjoyUtils.nullToStr(row[2]));
					bean.setSex					(EnjoyUtils.nullToStr(row[3]));
					bean.setIdType				(EnjoyUtils.nullToStr(row[4]));
					bean.setIdNumber			(EnjoyUtils.nullToStr(row[5]));
					bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row[6]));
					bean.setReligion			(EnjoyUtils.nullToStr(row[7]));
					bean.setJob					(EnjoyUtils.nullToStr(row[8]));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row[9]));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row[10]));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row[11]));
					bean.setSoiName				(EnjoyUtils.nullToStr(row[12]));
					bean.setStreetName			(EnjoyUtils.nullToStr(row[13]));
					
					provinceCode 		= EnjoyUtils.nullToStr(row[14]);
					districtCode 		= EnjoyUtils.nullToStr(row[15]);
					subdistrictCode 	= EnjoyUtils.nullToStr(row[16]);
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					fullName			= EnjoyUtils.nullToStr(row[1]) + " " + EnjoyUtils.nullToStr(row[2]);
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(EnjoyUtils.nullToStr(row[17]));
					bean.setTel					(EnjoyUtils.nullToStr(row[18]));
					bean.setFax					(EnjoyUtils.nullToStr(row[19]));
					bean.setEmail				(EnjoyUtils.nullToStr(row[20]));
					bean.setCusStatus			(EnjoyUtils.nullToStr(row[21]));
					bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row[22]));
					bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row[23]));
					bean.setPoint				(EnjoyUtils.nullToStr(row[24]));
					bean.setRemark				(EnjoyUtils.nullToStr(row[25]));
					bean.setFullName			(fullName);
					bean.setBranchName			(EnjoyUtils.nullToStr(row[26]));
					bean.setCusGroupCode		(EnjoyUtils.nullToStr(row[27]));
					bean.setGroupSalePrice		(EnjoyUtils.nullToStr(row[28]));
					
					listData.add(bean);
				}	
			}
			
		}catch(Exception e){
			logger.info("[getCustomerDetailsLookUpList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			logger.info("[getCustomerDetailsLookUpList][End]");
		}
		
		return listData;
		
	}
	
	public List<CustomerDetailsBean> getCusFullName(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		logger.info("[getCusFullName][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		CustomerDetailsBean			bean					= null;
		List<CustomerDetailsBean> 	customerDetailsBeanList = new ArrayList<CustomerDetailsBean>();
		String						fullName				= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{	
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql					= "select * "
								+ "	from customer"
								+ "	where cusStatus = 'A'";
			
			if(!customerDetailsBean.getFullName().equals("")){
				hql += " and CONCAT(cusName, ' ', cusSurname) like ('" + customerDetailsBean.getFullName() + "%')";
			}
			
			hql += " order by CONCAT(cusName, ' ', cusSurname) asc limit 10";
			
			logger.info("[getCusFullName] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("cusCode"			, new StringType());
			query.addScalar("cusName"			, new StringType());
			query.addScalar("cusSurname"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getCusFullName] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CustomerDetailsBean();
				
				logger.info("[getCusFullName] cusCode 			:: " + row[0]);
				logger.info("[getCusFullName] cusName 			:: " + row[1]);
				logger.info("[getCusFullName] cusSurname 		:: " + row[2]);
				
				bean.setCusCode				(EnjoyUtils.nullToStr(row[0]));
				
				fullName			= EnjoyUtils.nullToStr(row[1]) + " " + EnjoyUtils.nullToStr(row[2]);
				
				bean.setFullName			(fullName);
				
				customerDetailsBeanList.add(bean);
			}	
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			throw new EnjoyException("error getCusFullName");
		}finally{
			session.close();
			sessionFactory		= null;
			session				= null;
			hql					= null;
			logger.info("[getCusFullName][End]");
		}
		
		return customerDetailsBeanList;
		
	}
	
}
