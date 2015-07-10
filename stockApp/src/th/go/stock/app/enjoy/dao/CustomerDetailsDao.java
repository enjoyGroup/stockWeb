
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Customer;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

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
			
			if(!customerDetailsBean.getCusCode().equals("")){
				hql += " and a.cusCode like ('" + customerDetailsBean.getCusCode() + "%')";
			}
			if(!customerDetailsBean.getFullName().equals("")){
				hql += " and CONCAT(a.cusName, ' ', a.cusSurname) like ('" + customerDetailsBean.getFullName() + "%')";
			}
			if(!customerDetailsBean.getCusStatus().equals("")){
				hql += " and a.cusStatus = '" + customerDetailsBean.getCusStatus() + "'";
			}
			if(!customerDetailsBean.getIdNumber().equals("")){
				hql += " and a.idNumber like ('" + customerDetailsBean.getIdNumber() + "%')";
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
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new CustomerDetailsBean();
				
				logger.info("cusCode 			:: " + row[0].toString());
				logger.info("cusName 			:: " + row[1].toString());
				logger.info("cusSurname 		:: " + row[2].toString());
				logger.info("sex 				:: " + row[3].toString());
				logger.info("idType 			:: " + row[4].toString());
				logger.info("idNumber 			:: " + row[5].toString());
				logger.info("birthDate 			:: " + row[6].toString());
				logger.info("religion 			:: " + row[7].toString());
				logger.info("job 				:: " + row[8].toString());
				logger.info("buildingName 		:: " + row[9].toString());
				logger.info("houseNumber 		:: " + row[10].toString());
				logger.info("mooNumber 			:: " + row[11].toString());
				logger.info("soiName 			:: " + row[12].toString());
				logger.info("streetName 		:: " + row[13].toString());
				logger.info("provinceCode 		:: " + row[14].toString());
				logger.info("districtCode 		:: " + row[15].toString());
				logger.info("subdistrictCode 	:: " + row[16].toString());
				logger.info("postCode 			:: " + row[17].toString());
				logger.info("tel 				:: " + row[18].toString());
				logger.info("fax 				:: " + row[19].toString());
				logger.info("email 				:: " + row[20].toString());
				logger.info("cusStatus 			:: " + row[21].toString());
				logger.info("startDate 			:: " + row[22].toString());
				logger.info("expDate 			:: " + row[23].toString());
				logger.info("point 				:: " + row[24].toString());
				logger.info("remark 			:: " + row[25].toString());
				logger.info("customerStatusName :: " + row[26].toString());
				
				bean.setCusCode				(row[0].toString());
				bean.setCusName				(row[1].toString());
				bean.setCusSurname			(row[2].toString());
				bean.setSex					(row[3].toString());
				bean.setIdType				(row[4].toString());
				bean.setIdNumber			(row[5].toString());
				bean.setBirthDate			(EnjoyUtils.dateFormat(row[6].toString(), "yyyyMMdd", "dd/MM/yyyy"));
				bean.setReligion			(row[7].toString());
				bean.setJob					(row[8].toString());
				bean.setBuildingName		(row[9].toString());
				bean.setHouseNumber			(row[10].toString());
				bean.setMooNumber			(row[11].toString());
				bean.setSoiName				(row[12].toString());
				bean.setStreetName			(row[13].toString());
				
				provinceCode 		= EnjoyUtils.nullToStr(row[14].toString());
				districtCode 		= EnjoyUtils.nullToStr(row[15].toString());
				subdistrictCode 	= EnjoyUtils.nullToStr(row[16].toString());
				provinceName		= addressDao.getProvinceName(provinceCode);
				districtName		= addressDao.getDistrictName(districtCode);
				subdistrictName		= addressDao.getSubdistrictName(subdistrictCode);
				fullName			= EnjoyUtils.nullToStr(row[1].toString()) + " " + EnjoyUtils.nullToStr(row[2].toString());
				
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(row[17].toString());
				bean.setTel					(row[18].toString());
				bean.setFax					(row[19].toString());
				bean.setEmail				(row[20].toString());
				bean.setCusStatus			(row[21].toString());
				bean.setStartDate			(EnjoyUtils.dateFormat(row[22].toString(), "yyyyMMdd", "dd/MM/yyyy"));
				bean.setExpDate				(EnjoyUtils.dateFormat(row[23].toString(), "yyyyMMdd", "dd/MM/yyyy"));
				bean.setPoint				(row[24].toString());
				bean.setRemark				(row[25].toString());
				bean.setCustomerStatusName	(row[26].toString());
				bean.setFullName			(fullName);
				
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

	
	public CustomerDetailsBean getCustomerDetail(	Session 					session, 
													CustomerDetailsBean 		customerDetailsBean) throws EnjoyException{
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
		
		try{		
			addressDao 			= new AddressDao();
			hql					= "select * "
								+ "	from customer"
								+ "	where cusCode = '" + customerDetailsBean.getCusCode() + "'";
			
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
			
			list		 	= query.list();
			
			logger.info("[getCustomerDetail] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new CustomerDetailsBean();
					
					bean.setCusCode				(row[0].toString());
					bean.setCusName				(row[1].toString());
					bean.setCusSurname			(row[2].toString());
					bean.setSex					(row[3].toString());
					bean.setIdType				(row[4].toString());
					bean.setIdNumber			(row[5].toString());
					bean.setBirthDate			(EnjoyUtils.dateFormat(row[6].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setReligion			(row[7].toString());
					bean.setJob					(row[8].toString());
					bean.setBuildingName		(row[9].toString());
					bean.setHouseNumber			(row[10].toString());
					bean.setMooNumber			(row[11].toString());
					bean.setSoiName				(row[12].toString());
					bean.setStreetName			(row[13].toString());
					
					provinceCode 		= EnjoyUtils.nullToStr(row[14].toString());
					districtCode 		= EnjoyUtils.nullToStr(row[15].toString());
					subdistrictCode 	= EnjoyUtils.nullToStr(row[16].toString());
					provinceName		= addressDao.getProvinceName(provinceCode);
					districtName		= addressDao.getDistrictName(districtCode);
					subdistrictName		= addressDao.getSubdistrictName(subdistrictCode);
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(row[17].toString());
					bean.setTel					(row[18].toString());
					bean.setFax					(row[19].toString());
					bean.setEmail				(row[20].toString());
					bean.setCusStatus			(row[21].toString());
					bean.setStartDate			(EnjoyUtils.dateFormat(row[22].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setExpDate				(EnjoyUtils.dateFormat(row[23].toString(), "yyyyMMdd", "dd/MM/yyyy"));
					bean.setPoint				(row[24].toString());
					bean.setRemark				(row[25].toString());
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getCustomerDetail] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCustomerDetail");
		}finally{
			hql						= null;
			logger.info("[getCustomerDetail][End]");
		}
		
		return bean;
		
	}
	
	public List<ComboBean> getStatusCombo(Session session) throws EnjoyException{
		logger.info("[getStatusCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		
		try{
			
			hql	= "select * from refcustomerstatus";

			logger.info("[getStatusCombo] hql :: " + hql);
			
			query			= session.createSQLQuery(hql);
			query.addScalar("customerStatusCode"		, new StringType());
			query.addScalar("customerStatusName"		, new StringType());
			
			list		 	= query.list();
			
			comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getStatusCombo] customerStatusCode :: " + row[0].toString());
				logger.info("[getStatusCombo] customerStatusName :: " + row[1].toString());
				
				comboBean.setCode(row[0].toString());
				comboBean.setDesc(row[1].toString());
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info("[getStatusCombo] " + e.getMessage());
			throw new EnjoyException("Error getStatusCombo");
		}finally{
			hql						= null;
			logger.info("[getStatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	public void insertCustomerDetails(Session session, CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		logger.info("[insertCustomerDetails][Begin]");
		
		Customer	customer						= null;
		
		try{
			
			customer = new Customer();
			
			customer.setCusName				(customerDetailsBean.getCusName());
			customer.setCusSurname			(customerDetailsBean.getCusSurname());
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
			query.setParameter("cusCode"			, EnjoyUtils.parseInt(customerDetailsBean.getCusCode()));
			
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
	
	public int lastId(Session session) throws EnjoyException{
		logger.info("[lastId][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			
			hql				= "select max(cusCode) lastId from customer";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0);
			}
			
			logger.info("[lastId] result 			:: " + result);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[lastId][End]");
		}
		
		return result;
	}
	
}
