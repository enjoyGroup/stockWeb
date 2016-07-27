
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Companyvendor;
import th.go.stock.app.enjoy.model.CompanyvendorPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class CompanyVendorDao extends DaoControl {
	
	public CompanyVendorDao(){
		setLogger(EnjoyLogger.getLogger(CompanyVendorDao.class));
		super.init();
	}
	
	
	public List<CompanyVendorBean> searchByCriteria(CompanyVendorBean companyVendorBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		CompanyVendorBean				bean					= null;
		List<CompanyVendorBean> 		companyVendorBeanList 	= new ArrayList<CompanyVendorBean>();
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= "";
		String							districtName			= "";
		String							subdistrictName			= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select a.* "
								+ "	from companyvendor a"
								+ "	where a.tinCompany = :tinCompany ";
			
			//Criteria
			param.put("tinCompany"	, companyVendorBean.getTinCompany());
			
			if(!companyVendorBean.getVendorName().equals("***")){
				if(companyVendorBean.getVendorName().equals("")){
					hql += " and (a.vendorName is null or a.vendorName = '')";
				}else{
					hql += " and a.vendorName LIKE CONCAT(:vendorName, '%')";
					param.put("vendorName"	, companyVendorBean.getVendorName());
				}
			}
			
			if(!companyVendorBean.getBranchName().equals("***")){
				if(companyVendorBean.getBranchName().equals("")){
					hql += " and (a.branchName is null or a.branchName = '')";
				}else{
					hql += " and a.branchName LIKE CONCAT(:branchName, '%')";
					param.put("branchName"	, companyVendorBean.getBranchName());
				}
			}
			
			if(!companyVendorBean.getTin().equals("***")){
				if(companyVendorBean.getTin().equals("")){
					hql += " and (a.tin is null or a.tin = '')";
				}else{
					hql += " and a.tin LIKE CONCAT(:tin, '%')";
					param.put("tin"	, companyVendorBean.getTin());
				}
			}
			
			if(!companyVendorBean.getTel().equals("***")){
				if(companyVendorBean.getTel().equals("")){
					hql += " and (a.tel is null or a.tel = '')";
				}else{
					hql += " and a.tel LIKE CONCAT(:tel, '%')";
					param.put("tel"	, companyVendorBean.getTel());
				}
			}
			
			//Column select
			columnList.add("vendorCode");
			columnList.add("tinCompany");
			columnList.add("tin");
			columnList.add("vendorName");
			columnList.add("branchName");
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
			columnList.add("remark");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new CompanyVendorBean();
				
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
				
				bean.setVendorCode			(EnjoyUtils.nullToStr(row.get("vendorCode")));
				bean.setTinCompany			(EnjoyUtils.nullToStr(row.get("tinCompany")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setVendorName			(EnjoyUtils.nullToStr(row.get("vendorName")));
				bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
				bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
				bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
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
				bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				
				companyVendorBeanList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			addressDao.destroySession();
			getLogger().info("[searchByCriteria][End]");
		}
		
		return companyVendorBeanList;
		
	}
	
	public CompanyVendorBean getCompanyVendor(CompanyVendorBean companyVendorBean) throws EnjoyException{
		getLogger().info("[getCompanyVendor][Begin]");
		
		String							hql						= null;
		CompanyVendorBean				bean					= null;
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= null;
		String							districtName			= null;
		String							subdistrictName			= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{		
			addressDao 			= new AddressDao();
			hql					= "select *"
								+ "	from companyvendor"
								+ "	where vendorCode 	= :vendorCode"
								+ "		and tinCompany	= :tinCompany";
			
			param.put("vendorCode"	, companyVendorBean.getVendorCode());
			param.put("tinCompany"	, companyVendorBean.getTinCompany());
	
			columnList.add("vendorCode");
			columnList.add("tinCompany");
			columnList.add("tin");
			columnList.add("vendorName");
			columnList.add("branchName");
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
			columnList.add("remark");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new CompanyVendorBean();
					
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
					
					bean.setVendorCode			(EnjoyUtils.nullToStr(row.get("vendorCode")));
					bean.setTinCompany			(EnjoyUtils.nullToStr(row.get("tinCompany")));
					bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setVendorName			(EnjoyUtils.nullToStr(row.get("vendorName")));
					bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
					bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
					bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
					bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
					bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
					bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
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
					bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				}	
			}
			
		}catch(Exception e){
			getLogger().info("[getCompanyVendor] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCompanyVendor");
		}finally{
			hql						= null;
			addressDao.destroySession();
			getLogger().info("[getCompanyVendor][End]");
		}
		
		return bean;
		
	}
	
	public void insertCompanyVendor(CompanyVendorBean companyVendorBean) throws EnjoyException{
		getLogger().info("[insertCompanyVendor][Begin]");
		
		Companyvendor		companyvendor 	= new Companyvendor();
		CompanyvendorPK 	id 				= new CompanyvendorPK();
		String 				tinCompany		= null;
		
		try{
			tinCompany = companyVendorBean.getTinCompany();
			
			id.setVendorCode(genId(tinCompany));
			id.setTinCompany(tinCompany);
			
			companyvendor.setId				(id);
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
			
			insertData(companyvendor);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertCompanyVendor");
		}finally{
			
			companyvendor = null;
			getLogger().info("[insertCompanyVendor][End]");
		}
	}
	
	public int genId(String tinCompany) throws EnjoyException{
		getLogger().info("[genId][Begin]");
		
		String							hql				= null;
		int 							result			= 1;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select (max(vendorCode) + 1) newId"
					+ "	from companyvendor "
					+ "	where tinCompany = :tinCompany";
			
			//Criteria
			param.put("tinCompany"		, tinCompany);
			
			resultList = getResult(hql, param, "newId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0))==0?1:EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[genId] newId 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("genId error");
		}finally{
			hql									= null;
			getLogger().info("[genId][End]");
		}
		
		return result;
	}
	
	public void updateCompanyvendor(CompanyVendorBean companyVendorBean) throws EnjoyException{
		getLogger().info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Companyvendor t set t.vendorName = :vendorName"
												+ ", t.tin				= :tin"
												+ ", t.branchName		= :branchName"
												+ ", t.buildingName		= :buildingName"
												+ ", t.houseNumber		= :houseNumber"
												+ ", t.mooNumber 		= :mooNumber"
												+ ", t.soiName			= :soiName"
												+ ", t.streetName		= :streetName"
												+ ", t.provinceCode 	= :provinceCode"
												+ ", t.districtCode 	= :districtCode"
												+ ", t.subdistrictCode 	= :subdistrictCode"
												+ ", t.postCode 		= :postCode"
												+ ", t.tel 				= :tel"
												+ ", t.fax 				= :fax"
												+ ", t.email 			= :email"
												+ ", t.remark 			= :remark"
										+ " where t.id.vendorCode 	= :vendorCode"
										+ "		and t.id.tinCompany = :tinCompany";
			
			query = createQuery(hql);
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
			query.setParameter("tinCompany"		, companyVendorBean.getTinCompany());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateReciveOrderMaster][End]");
		}
	}
	
	public int checkDupVendorName(String vendorName, String branchName, String vendorCode, String tinCompany) throws EnjoyException{
		getLogger().info("[checkDupVendorName][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql	= "select count(*) cou "
				+ "	from companyvendor "
				+ "	where vendorName 	= :vendorName "
				+ "		and branchName 	= :branchName"
				+ "		and tinCompany	= :tinCompany";
			
			//Criteria
			param.put("vendorName"	, vendorName);
			param.put("branchName"	, branchName);
			param.put("tinCompany"	, tinCompany);
			
			if(!vendorCode.equals("")){
				hql += " and vendorCode <> :vendorCode";
				param.put("vendorCode"	, vendorCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupVendorName] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql									= null;
			getLogger().info("[checkDupVendorName][End]");
		}
		
		return result;
	}
	
	public int checkDupTin(String tin, String vendorCode, String tinCompany) throws EnjoyException{
		getLogger().info("[checkDupTin][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql	= "select count(*) cou "
				+ "	from companyvendor "
				+ "	where tin 			= :tin "
				+ "		and tin is not null"
				+ "		and tinCompany 	= :tinCompany";
			
			//Criteria
			param.put("tin"			, tin);
			param.put("tinCompany"	, tinCompany);
			
			if(!vendorCode.equals("")){
				hql += " and vendorCode <> :vendorCode";
				param.put("vendorCode"	, vendorCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupTin] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[checkDupTin][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> tinList(String tin, String tinCompany){
		getLogger().info("[tinList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList	= new ArrayList<ComboBean>();
			hql 		= "select tin "
						+ "	from companyvendor "
						+ "	where tin LIKE CONCAT(:tin, '%')"
						+ "		and tinCompany = :tinCompany"
						+ "	order by tin asc limit 10 ";
			
			//Criteria
			param.put("tin"			, tin);
			param.put("tinCompany"	, tinCompany);
			
			//Column select
			columnList.add("tin");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("tin")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("tin")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[tinList][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> vendorNameList(String vendorName, String tinCompany){
		getLogger().info("[vendorNameList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList	= new ArrayList<ComboBean>();
			hql 		= "select vendorCode, vendorName "
						+ "	from companyvendor "
						+ "	where vendorName LIKE CONCAT(:vendorName, '%')"
						+ "		and tinCompany = :tinCompany"
						+ "	order by vendorName asc limit 10 ";
			
			//Criteria
			param.put("vendorName"	, vendorName);
			param.put("tinCompany"	, tinCompany);
			
			//Column select
			columnList.add("vendorCode");
			columnList.add("vendorName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("vendorCode")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("vendorName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[vendorNameList][End]");
		}
		
		return comboList;
	}

	public List<ComboBean> branchNameList(String vendorName, String branchName, String tinCompany){
		getLogger().info("[branchNameList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList			= new ArrayList<ComboBean>();
			hql 				= "select vendorCode, branchName from companyvendor"
									+ " where vendorName = :vendorName"
										+ " and branchName LIKE CONCAT(:branchName, '%')"
										+ "	and tinCompany	= :tinCompany"
									+ " order by vendorName asc limit 10 ";
			
			//Criteria
			param.put("vendorName"	, vendorName);
			param.put("branchName"	, branchName);
			param.put("tinCompany"	, tinCompany);
			
			//Column select
			columnList.add("vendorCode");
			columnList.add("branchName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("vendorCode")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("branchName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[branchNameList][End]");
		}
		
		return comboList;
	}
	
	public List<CompanyVendorBean> getCompanyVendorByName(String vendorName, String branchName, String tinCompany) throws EnjoyException{
		getLogger().info("[getCompanyVendorByName][Begin]");
		
		String							hql						= null;
		CompanyVendorBean				bean					= null;
		AddressDao						addressDao				= null;
		String							provinceCode			= null;
		String							districtCode			= null;
		String							subdistrictCode			= null;
		String							provinceName			= null;
		String							districtName			= null;
		String							subdistrictName			= null;
		List<CompanyVendorBean> 		companyVendorList 		= new ArrayList<CompanyVendorBean>();
		String							lv_where				= "";
		String							lv_orderBy				= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			addressDao 			= new AddressDao();
			hql					= "select * from companyvendor";
			
			lv_where = " where vendorName = :vendorName and tinCompany = :tinCompany";
			param.put("vendorName"	, vendorName);
			param.put("tinCompany"	, tinCompany);
			
			if(!branchName.equals("")){
				lv_where += " and branchName = :branchName";
				param.put("branchName"	, branchName);
			}
			
			lv_orderBy = " order by vendorName asc, branchName asc";
			
			hql += lv_where + lv_orderBy;
			
			//Column select
			columnList.add("vendorCode");
			columnList.add("vendorName");
			columnList.add("tinCompany");
			columnList.add("tin");
			columnList.add("branchName");
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
			columnList.add("remark");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new CompanyVendorBean();
				
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
				
				bean.setVendorCode			(EnjoyUtils.nullToStr(row.get("vendorCode")));
				bean.setTinCompany			(EnjoyUtils.nullToStr(row.get("tinCompany")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setVendorName			(EnjoyUtils.nullToStr(row.get("vendorName")));
				bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
				bean.setBuildingName		(EnjoyUtils.nullToStr(row.get("buildingName")));
				bean.setHouseNumber			(EnjoyUtils.nullToStr(row.get("houseNumber")));
				bean.setMooNumber			(EnjoyUtils.nullToStr(row.get("mooNumber")));
				bean.setSoiName				(EnjoyUtils.nullToStr(row.get("soiName")));
				bean.setStreetName			(EnjoyUtils.nullToStr(row.get("streetName")));
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
				bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				
				companyVendorList.add(bean);
				
			}	
			
		}catch(Exception e){
			getLogger().info("[getCompanyVendorByName] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrdeDetailList");
		}finally{
			hql						= null;
			addressDao.destroySession();
			getLogger().info("[getCompanyVendorByName][End]");
		}
		
		return companyVendorList;
		
	}
	
}

