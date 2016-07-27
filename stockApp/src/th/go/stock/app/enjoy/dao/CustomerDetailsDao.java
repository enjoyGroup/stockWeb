
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CustomerDetailsLookUpForm;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Customer;
import th.go.stock.app.enjoy.model.CustomerPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class CustomerDetailsDao extends DaoControl{
	
	public CustomerDetailsDao(){
		setLogger(EnjoyLogger.getLogger(CustomerDetailsDao.class));
		super.init();
	}
	
	public List<CustomerDetailsBean> searchByCriteria(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		CustomerDetailsBean				bean					= null;
		List<CustomerDetailsBean> 		customerDetailsBeanList = new ArrayList<CustomerDetailsBean>();
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= null;
		String							districtName			= null;
		String							subdistrictName			= null;
		String							fullName				= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.customerStatusName "
								+ "	from customer a, refcustomerstatus b"
								+ "	where b.customerStatusCode = a.cusStatus"
								+ "		and a.tin = :tin";
			
			//Criteria
			param.put("tin"	, customerDetailsBean.getTin());
			
			if(!customerDetailsBean.getCusCode().equals("***")){
				if(customerDetailsBean.getCusCode().equals("")){
					hql += " and (a.cusCode is null or a.cusCode = '')";
				}else{
					hql += " and a.cusCode LIKE CONCAT(:cusCode, '%')";
					param.put("cusCode"	, customerDetailsBean.getCusCode());
				}
			}
			if(!customerDetailsBean.getFullName().equals("***")){
				if(customerDetailsBean.getFullName().equals("")){
					hql += " and CONCAT(a.cusName, ' ', a.cusSurname) = ''";
				}else{
					hql += " and CONCAT(a.cusName, ' ', a.cusSurname) LIKE CONCAT(:cusName, '%')";
					param.put("cusName"	, customerDetailsBean.getFullName());
				}
			}
			if(!customerDetailsBean.getCusStatus().equals("")){
				hql += " and a.cusStatus = :cusStatus";
				param.put("cusStatus"	, customerDetailsBean.getCusStatus());
			}
			if(!customerDetailsBean.getIdNumber().equals("***")){
				if(customerDetailsBean.getIdNumber().equals("")){
					hql += " and (a.idNumber is null or a.idNumber = '')";
				}else{
					hql += " and a.idNumber LIKE CONCAT(:idNumber, '%')";
					param.put("idNumber"	, customerDetailsBean.getIdNumber());
				}
			}
			
			//Column select
			columnList.add("cusCode");
			columnList.add("tin");
			columnList.add("cusName");
			columnList.add("cusSurname");
			columnList.add("sex");
			columnList.add("idType");
			columnList.add("idNumber");
			columnList.add("birthDate");
			columnList.add("religion");
			columnList.add("job");
			columnList.add("buildingName");
			columnList.add("houseNumber");
			columnList.add("mooNumber");
			columnList.add("soiName");
			columnList.add("streetName");
			columnList.add("provinceCode");
			columnList.add("districtCode");
			columnList.add("subdistrictCode");
			columnList.add("postCode");
			columnList.add("tel");
			columnList.add("fax");
			columnList.add("email");
			columnList.add("cusStatus");
			columnList.add("startDate");
			columnList.add("expDate");
			columnList.add("point");
			columnList.add("remark");
			columnList.add("customerStatusName");
			columnList.add("branchName");
			columnList.add("cusGroupCode");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new CustomerDetailsBean();
				
				bean.setCusCode				(EnjoyUtils.nullToStr(row.get("cusCode")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setCusName				(EnjoyUtils.nullToStr(row.get("cusName")));
				bean.setCusSurname			(EnjoyUtils.nullToStr(row.get("cusSurname")));
				bean.setSex					(EnjoyUtils.nullToStr(row.get("sex")));
				bean.setIdType				(EnjoyUtils.nullToStr(row.get("idType")));
				bean.setIdNumber			(EnjoyUtils.nullToStr(row.get("idNumber")));
				bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row.get("birthDate")));
				bean.setReligion			(EnjoyUtils.nullToStr(row.get("religion")));
				bean.setJob					(EnjoyUtils.nullToStr(row.get("job")));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
				bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
				bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
				
				provinceCode 		= EnjoyUtils.nullToStr(row.get("provinceCode"));
				districtCode 		= EnjoyUtils.nullToStr(row.get("districtCode"));
				subdistrictCode 	= EnjoyUtils.nullToStr(row.get("subdistrictCode"));
				
				if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
					provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
					districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
					subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
				}else{
					provinceName		= "";
					districtName		= "";
					subdistrictName		= "";
				}
				
				fullName			= EnjoyUtils.nullToStr(row.get("cusName")) + " " + EnjoyUtils.nullToStr(row.get("cusSurname"));
				
				bean.setProvinceCode		(provinceCode);
				bean.setDistrictCode		(districtCode);
				bean.setSubdistrictCode		(subdistrictCode);
				bean.setProvinceName		(provinceName);
				bean.setDistrictName		(districtName);
				bean.setSubdistrictName		(subdistrictName);
				bean.setPostCode			(EnjoyUtils.nullToStr(row.get("postCode")));
				bean.setTel					(EnjoyUtils.nullToStr(row.get("tel")));
				bean.setFax					(EnjoyUtils.nullToStr(row.get("fax")));
				bean.setEmail				(EnjoyUtils.nullToStr(row.get("email")));
				bean.setCusStatus			(EnjoyUtils.nullToStr(row.get("cusStatus")));
				bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row.get("startDate")));
				bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row.get("expDate")));
				bean.setPoint				(EnjoyUtils.nullToStr(row.get("point")));
				bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				bean.setCustomerStatusName	(EnjoyUtils.nullToStr(row.get("customerStatusName")));
				bean.setFullName			(fullName);
				bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
				bean.setCusGroupCode		(EnjoyUtils.nullToStr(row.get("cusGroupCode")));
				
				customerDetailsBeanList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			addressDao.destroySession();
			getLogger().info("[searchByCriteria][End]");
		}
		
		return customerDetailsBeanList;
		
	}
	
	public CustomerDetailsBean getCustomerDetail(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		getLogger().info("[getCustomerDetail][Begin]");
		
		String							hql						= null;
		CustomerDetailsBean				bean					= null;
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= null;
		String							districtName			= null;
		String							subdistrictName			= null;
		String							fullName				= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{		
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.groupSalePrice"
									+ " from customer a LEFT JOIN relationgroupcustomer b"
									+ " 	ON a.cusGroupCode = b.cusGroupCode and b.cusGroupStatus = 'A' and a.tin = b.tin"
								+ "	where a.cusCode = :cusCode"
								+ "		and a.tin 	= :tin";
			
			//Criteria
			param.put("cusCode"	,customerDetailsBean.getCusCode());
			param.put("tin"		,customerDetailsBean.getTin());
			
			//Column select
			columnList.add("cusCode");
			columnList.add("tin");
			columnList.add("cusName");
			columnList.add("cusSurname");
			columnList.add("sex");
			columnList.add("idType");
			columnList.add("idNumber");
			columnList.add("birthDate");
			columnList.add("religion");
			columnList.add("job");
			columnList.add("buildingName");
			columnList.add("houseNumber");
			columnList.add("mooNumber");
			columnList.add("soiName");
			columnList.add("streetName");
			columnList.add("provinceCode");
			columnList.add("districtCode");
			columnList.add("subdistrictCode");
			columnList.add("postCode");
			columnList.add("tel");
			columnList.add("fax");
			columnList.add("email");
			columnList.add("cusStatus");
			columnList.add("startDate");
			columnList.add("expDate");
			columnList.add("point");
			columnList.add("remark");
			columnList.add("branchName");
			columnList.add("cusGroupCode");
			columnList.add("groupSalePrice");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new CustomerDetailsBean();
					
					bean.setCusCode				(EnjoyUtils.nullToStr(row.get("cusCode")));
					bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setCusName				(EnjoyUtils.nullToStr(row.get("cusName")));
					bean.setCusSurname			(EnjoyUtils.nullToStr(row.get("cusSurname")));
					bean.setSex					(EnjoyUtils.nullToStr(row.get("sex")));
					bean.setIdType				(EnjoyUtils.nullToStr(row.get("idType")));
					bean.setIdNumber			(EnjoyUtils.nullToStr(row.get("idNumber")));
					bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row.get("birthDate")));
					bean.setReligion			(EnjoyUtils.nullToStr(row.get("religion")));
					bean.setJob					(EnjoyUtils.nullToStr(row.get("job")));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
					bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
					bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
					
					provinceCode 		= EnjoyUtils.nullToStr(row.get("provinceCode"));
					districtCode 		= EnjoyUtils.nullToStr(row.get("districtCode"));
					subdistrictCode 	= EnjoyUtils.nullToStr(row.get("subdistrictCode"));
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					fullName			= EnjoyUtils.nullToStr(row.get("cusName")) + " " + EnjoyUtils.nullToStr(row.get("cusSurname"));
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(EnjoyUtils.nullToStr(row.get("postCode")));
					bean.setTel					(EnjoyUtils.nullToStr(row.get("tel")));
					bean.setFax					(EnjoyUtils.nullToStr(row.get("fax")));
					bean.setEmail				(EnjoyUtils.nullToStr(row.get("email")));
					bean.setCusStatus			(EnjoyUtils.nullToStr(row.get("cusStatus")));
					bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row.get("startDate")));
					bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row.get("expDate")));
					bean.setPoint				(EnjoyUtils.nullToStr(row.get("point")));
					bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
					bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
					bean.setCusGroupCode		(EnjoyUtils.nullToStr(row.get("cusGroupCode")));
					bean.setGroupSalePrice		(EnjoyUtils.nullToStr(row.get("groupSalePrice")));
					bean.setFullName			(fullName);
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getCustomerDetail");
		}finally{
			hql				= null;
			addressDao.destroySession();
			getLogger().info("[getCustomerDetail][End]");
		}
		
		return bean;
		
	}
	
	public List<ComboBean> getStatusCombo() throws EnjoyException{
		getLogger().info("[getStatusCombo][Begin]");
		
		String							hql						= null;
		ComboBean						comboBean				= null;
		List<ComboBean> 				comboList				= new ArrayList<ComboBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			hql	= "select * from refcustomerstatus";

			//Column select
			columnList.add("customerStatusCode");
			columnList.add("customerStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean();
				
				comboBean.setCode(EnjoyUtils.nullToStr(row.get("customerStatusCode")));
				comboBean.setDesc(EnjoyUtils.nullToStr(row.get("customerStatusName")));
				
				comboList.add(comboBean);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error getStatusCombo");
		}finally{
			hql				= null;
			getLogger().info("[getStatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	public void insertCustomerDetails(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		getLogger().info("[insertCustomerDetails][Begin]");
		
		Customer	customer	= new Customer();
		String		cusCode		= "";
		CustomerPK 	id 			= new CustomerPK();
		String		tin			= null;
		
		try{
			tin			= customerDetailsBean.getTin();
			cusCode		= this.genCusCode(tin);
			
			id.setCusCode(cusCode);
			id.setTin(tin);
			
			customer.setId					(id);
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
			
			insertData(customer);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertCustomerDetails");
		}finally{
			
			customer = null;
			getLogger().info("[insertCustomerDetails][End]");
		}
	}
	
	public void updateCustomerDetail(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		getLogger().info("[updateCustomerDetail][Begin]");
		
		String	hql			= null;
		Query 	query 		= null;
		
		try{
			hql				= "update  Customer t set t.cusName 		= :cusName"
												+ ", t.cusSurname		= :cusSurname"
												+ ", t.branchName		= :branchName"
												+ ", t.cusGroupCode		= :cusGroupCode"
												+ ", t.sex				= :sex"
												+ ", t.idType			= :idType"
												+ ", t.idNumber			= :idNumber"
												+ ", t.birthDate		= :birthDate"
												+ ", t.religion			= :religion"
												+ ", t.job 				= :job"
												+ ", t.buildingName 	= :buildingName"
												+ ", t.houseNumber 		= :houseNumber"
												+ ", t.mooNumber 		= :mooNumber"
												+ ", t.soiName 			= :soiName"
												+ ", t.streetName 		= :streetName"
												+ ", t.subdistrictCode 	= :subdistrictCode"
												+ ", t.districtCode 	= :districtCode"
												+ ", t.provinceCode 	= :provinceCode"
												+ ", t.postCode 		= :postCode"
												+ ", t.tel 				= :tel"
												+ ", t.fax 				= :fax"
												+ ", t.email 			= :email"
												+ ", t.cusStatus 		= :cusStatus"
												+ ", t.startDate 		= :startDate"
												+ ", t.expDate 			= :expDate"
												+ ", t.point 			= :point"
												+ ", t.remark 			= :remark"
										+ " where t.id.cusCode 	= :cusCode"
										+ "		and t.id.tin	= :tin";
			
			query = createQuery(hql);
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
			query.setParameter("tin"				, customerDetailsBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateCustomerDetail");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateCustomerDetail][End]");
		}
	}
	
	public String genCusCode(String tin) throws EnjoyException{
		getLogger().info("[genCusCode][Begin]");
		
		String							hql						= null;
		String							newId					= "";
		String							codeDisplay				= null;
		RefconstantcodeDao				refconstantcodeDao		= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			refconstantcodeDao 	= new RefconstantcodeDao();
			codeDisplay			= refconstantcodeDao.getCodeDisplay("1", tin);
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(cusCode, '-', -1)) + 1) AS newId"
							+ "	FROM customer"
							+ "	WHERE SUBSTRING_INDEX(cusCode, '-', 1) 	= :codeDisplay"
							+ "		and tin 							= :tin";
			
			//Criteria
			param.put("codeDisplay"	, codeDisplay);
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "newId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0 && resultList.get(0)!=null){
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingCusCode(), EnjoyUtils.parseInt(resultList.get(0)));
			}else{
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingCusCode(), 1);
			}
			
			getLogger().info("[genCusCode] newId 			:: " + newId);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			refconstantcodeDao.destroySession();
			
			getLogger().info("[genCusCode][End]");
		}
		
		return newId;
	}
	
	public int checkDupIdNumber(String idNumber, String cusCode, String tin) throws EnjoyException{
		getLogger().info("[checkDupIdNumber][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql	= "select count(*) cou from customer where idNumber = :idNumber and idNumber is not null and tin = :tin";
			
			//Criteria
			param.put("idNumber", idNumber);
			param.put("tin"		, tin);
			
			if(!cusCode.equals("")){
				hql += " and cusCode <> :cusCode";
				param.put("cusCode"	, cusCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupIdNumber] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[checkDupIdNumber][End]");
		}
		
		return result;
	}
	
	public int checkDupCusName(String cusName, String cusSurname, String branchName, String cusCode, String tin) throws EnjoyException{
		getLogger().info("[checkDupIdNumber][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql	= "select count(*) cou from customer where cusName = :cusName and cusSurname = :cusSurname and tin = :tin";
			
			//Criteria
			param.put("cusName"		,cusName);
			param.put("cusSurname"	,cusSurname);
			param.put("tin"			,tin);
			
			if(!branchName.equals("")){
				hql += " and branchName = :branchName";
				param.put("branchName"	,branchName);
			}
			
			if(!cusCode.equals("")){
				hql += " and cusCode <> :cusCode";
				param.put("cusCode"	,cusCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupIdNumber] result 			:: " + result);
			
			
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[checkDupIdNumber][End]");
		}
		
		return result;
	}
	
	public List<CustomerDetailsBean> getCustomerDetailsLookUpList(CustomerDetailsLookUpForm form, String tin) throws EnjoyException{
		getLogger().info("[getCustomerDetailsLookUpList][Begin]");
		
		String							hql						= null;
		CustomerDetailsBean 			bean					= null;
		List<CustomerDetailsBean> 		listData 				= new ArrayList<CustomerDetailsBean>();
		String							find					= null;
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= null;
		String							districtName			= null;
		String							subdistrictName			= null;
		String							fullName				= null;
		String							column					= "";
		String							orderBy					= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			find				= form.getFind();
			addressDao 			= new AddressDao();
			hql					= "select a.*, b.groupSalePrice"
									+ " from customer a LEFT JOIN relationgroupcustomer b"
									+ " 	ON a.cusGroupCode = b.cusGroupCode and b.cusGroupStatus = 'A' and a.tin = b.tin"
									+ " where a.cusStatus = 'A'"
									+ "		and a.tin = :tin";
			
			//Criteria
			param.put("tin"			, tin);
			
			if(find!=null && !find.equals("")){
				
				column 	= form.getColumn().equals("fullName")?"CONCAT(a.cusName, ' ', a.cusSurname)":"a." + form.getColumn();
				hql 	+= " and " + column;
				
				if(form.getLikeFlag().equals("Y")){
					hql += " LIKE CONCAT(:find, '%')";
				}else{
					hql += " = :find";
				}
				
				param.put("find"	, find);
				
			}
			orderBy = form.getOrderBy().equals("fullName")?"CONCAT(a.cusName, ' ', a.cusSurname)":"a." + form.getOrderBy();
			hql 	+= " order by " + orderBy + " " + form.getSortBy();
			
			columnList.add("cusCode");
			columnList.add("cusName");
			columnList.add("cusSurname");
			columnList.add("sex");
			columnList.add("idType");
			columnList.add("idNumber");
			columnList.add("birthDate");
			columnList.add("religion");
			columnList.add("job");
			columnList.add("buildingName");
			columnList.add("houseNumber");
			columnList.add("mooNumber");
			columnList.add("soiName");
			columnList.add("streetName");
			columnList.add("provinceCode");
			columnList.add("districtCode");
			columnList.add("subdistrictCode");
			columnList.add("postCode");
			columnList.add("tel");
			columnList.add("fax");
			columnList.add("email");
			columnList.add("cusStatus");
			columnList.add("startDate");
			columnList.add("expDate");
			columnList.add("point");
			columnList.add("remark");
			columnList.add("branchName");
			columnList.add("cusGroupCode");
			columnList.add("groupSalePrice");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList!=null){
				
				for(HashMap<String, Object> row:resultList){
					bean 	= new CustomerDetailsBean();
					
					bean.setCusCode				(EnjoyUtils.nullToStr(row.get("cusCode")));
					bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setCusName				(EnjoyUtils.nullToStr(row.get("cusName")));
					bean.setCusSurname			(EnjoyUtils.nullToStr(row.get("cusSurname")));
					bean.setSex					(EnjoyUtils.nullToStr(row.get("sex")));
					bean.setIdType				(EnjoyUtils.nullToStr(row.get("idType")));
					bean.setIdNumber			(EnjoyUtils.nullToStr(row.get("idNumber")));
					bean.setBirthDate			(EnjoyUtils.dateToThaiDisplay(row.get("birthDate")));
					bean.setReligion			(EnjoyUtils.nullToStr(row.get("religion")));
					bean.setJob					(EnjoyUtils.nullToStr(row.get("job")));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
					bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
					bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
					
					provinceCode 		= EnjoyUtils.nullToStr(row.get("provinceCode"));
					districtCode 		= EnjoyUtils.nullToStr(row.get("districtCode"));
					subdistrictCode 	= EnjoyUtils.nullToStr(row.get("subdistrictCode"));
					
					if(!provinceCode.equals("") && !districtCode.equals("") && !subdistrictCode .equals("")){
						provinceName		= EnjoyUtils.nullToStr(addressDao.getProvinceName(provinceCode));
						districtName		= EnjoyUtils.nullToStr(addressDao.getDistrictName(districtCode));
						subdistrictName		= EnjoyUtils.nullToStr(addressDao.getSubdistrictName(subdistrictCode));
					}else{
						provinceName		= "";
						districtName		= "";
						subdistrictName		= "";
					}
					
					fullName			= EnjoyUtils.nullToStr(row.get("cusName")) + " " + EnjoyUtils.nullToStr(row.get("cusSurname"));
					
					bean.setProvinceCode		(provinceCode);
					bean.setDistrictCode		(districtCode);
					bean.setSubdistrictCode		(subdistrictCode);
					bean.setProvinceName		(provinceName);
					bean.setDistrictName		(districtName);
					bean.setSubdistrictName		(subdistrictName);
					bean.setPostCode			(EnjoyUtils.nullToStr(row.get("postCode")));
					bean.setTel					(EnjoyUtils.nullToStr(row.get("tel")));
					bean.setFax					(EnjoyUtils.nullToStr(row.get("fax")));
					bean.setEmail				(EnjoyUtils.nullToStr(row.get("email")));
					bean.setCusStatus			(EnjoyUtils.nullToStr(row.get("cusStatus")));
					bean.setStartDate			(EnjoyUtils.dateToThaiDisplay(row.get("startDate")));
					bean.setExpDate				(EnjoyUtils.dateToThaiDisplay(row.get("expDate")));
					bean.setPoint				(EnjoyUtils.nullToStr(row.get("point")));
					bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
					bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
					bean.setCusGroupCode		(EnjoyUtils.nullToStr(row.get("cusGroupCode")));
					bean.setGroupSalePrice		(EnjoyUtils.nullToStr(row.get("groupSalePrice")));
					bean.setFullName			(fullName);
					
					listData.add(bean);
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			addressDao.destroySession();
			getLogger().info("[getCustomerDetailsLookUpList][End]");
		}
		
		return listData;
		
	}
	
	public List<CustomerDetailsBean> getCusFullName(CustomerDetailsBean customerDetailsBean) throws EnjoyException{
		getLogger().info("[getCusFullName][Begin]");
		
		String							hql						= null;
		CustomerDetailsBean				bean					= null;
		List<CustomerDetailsBean> 		customerDetailsBeanList = new ArrayList<CustomerDetailsBean>();
		String							fullName				= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select * "
								+ "	from customer"
								+ "	where cusStatus = 'A'"
								+ "		and tin = :tin";
			
			//Criteria
			param.put("tin"	, customerDetailsBean.getTin());
			
			if(!customerDetailsBean.getFullName().equals("")){
				hql += " and CONCAT(cusName, ' ', cusSurname) LIKE CONCAT(:cusName, '%')";
				param.put("cusName"	, customerDetailsBean.getFullName());
			}
			
			hql += " order by CONCAT(cusName, ' ', cusSurname) asc limit 10";
			
			//Column select
			columnList.add("cusCode");
			columnList.add("cusName");
			columnList.add("cusSurname");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 		= new CustomerDetailsBean();
				fullName	= EnjoyUtils.nullToStr(row.get("cusName")) + " " + EnjoyUtils.nullToStr(row.get("cusSurname"));
				
				bean.setCusCode				(EnjoyUtils.nullToStr(row.get("cusCode")));
				bean.setFullName			(fullName);
				
				customerDetailsBeanList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getCusFullName");
		}finally{
			hql					= null;
			getLogger().info("[getCusFullName][End]");
		}
		
		return customerDetailsBeanList;
		
	}
	
}


