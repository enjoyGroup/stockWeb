
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Company;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class CompanyDetailsDao extends DaoControl {
	
	public CompanyDetailsDao(){
		setLogger(EnjoyLogger.getLogger(CompanyDetailsDao.class));
		super.init();
	}
	
	public List<CompanyDetailsBean> searchByCriteria(CompanyDetailsBean companyDetailsBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		CompanyDetailsBean				bean					= null;
		List<CompanyDetailsBean> 		companyDetailsBeanList 	= new ArrayList<CompanyDetailsBean>();
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
			hql					= "select a.*, b.companyStatusName "
								+ "	from company a, refcompanystatus b"
								+ "	where b.companyStatusCode = a.companyStatus"
								+ "		and a.tin <> '9999999999999'";
			
			if(!companyDetailsBean.getCompanyName().equals("")){
				hql += " and a.companyName LIKE CONCAT(:companyName, '%')";
				param.put("companyName"	, companyDetailsBean.getCompanyName());
			}
			
			if(!companyDetailsBean.getTin().equals("")){
				hql += " and a.tin LIKE CONCAT(:tin, '%')";
				param.put("tin"	, companyDetailsBean.getTin());
			}
			
			if(!companyDetailsBean.getCompanyStatus().equals("")){
				hql += " and a.companyStatus = :companyStatus";
				param.put("companyStatus"	, companyDetailsBean.getCompanyStatus());
			}
			
			columnList.add("tin");
			columnList.add("companyName");
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
			columnList.add("companyStatus");
			columnList.add("companyStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new CompanyDetailsBean();
				
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setCompanyName			(EnjoyUtils.nullToStr(row.get("companyName")));
				bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
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
				bean.setCompanyStatus		(EnjoyUtils.nullToStr(row.get("companyStatus")));
				bean.setCompanyStatusName	(EnjoyUtils.nullToStr(row.get("companyStatusName")));
				
				companyDetailsBeanList.add(bean);
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
		
		return companyDetailsBeanList;
		
	}

	
	public CompanyDetailsBean getCompanyDetail(CompanyDetailsBean 	companyDetailsBean) throws EnjoyException{
		getLogger().info("[getCompanyDetail][Begin]");
		
		String							hql						= null;
		CompanyDetailsBean				bean					= null;
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
			
			hql					= "select * "
								+ "	from company"
								+ "	where tin = :tin";
			
			param.put("tin"	, companyDetailsBean.getTin());
			
			columnList.add("tin");
			columnList.add("companyName");
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
			columnList.add("companyStatus");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new CompanyDetailsBean();
					
					bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setCompanyName			(EnjoyUtils.nullToStr(row.get("companyName")));
					bean.setBranchName			(EnjoyUtils.nullToStr(row.get("branchName")));
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
					bean.setCompanyStatus		(EnjoyUtils.nullToStr(row.get("companyStatus")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().info("[getCompanyDetail] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getCompanyDetail");
		}finally{
			hql				= null;
			addressDao.destroySession();
			getLogger().info("[getCompanyDetail][End]");
		}
		
		return bean;
		
	}
	
	public List<ComboBean> getCompanystatusCombo() throws EnjoyException{
		getLogger().info("[getCompanystatusCombo][Begin]");
		
		String							hql						= null;
		ComboBean						comboBean				= null;
		List<ComboBean> 				comboList				= new ArrayList<ComboBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			
			hql	= "select * from refcompanystatus";

			columnList.add("companyStatusCode");
			columnList.add("companyStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean();
				
				comboBean.setCode(EnjoyUtils.nullToStr(row.get("companyStatusCode")));
				comboBean.setDesc(EnjoyUtils.nullToStr(row.get("companyStatusName")));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info("[getCompanystatusCombo] " + e.getMessage());
			throw new EnjoyException("Error getCompanystatusCombo");
		}finally{
			hql				= null;
			getLogger().info("[getCompanystatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	
	public int checkDupTin(String tin) throws EnjoyException{
		getLogger().info("[checkDupTin][Begin]");
		
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		
		try{
			hql				= "select count(*) cou from company where tin = :tin";
			
			param.put("tin"	, tin);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupTin] result 			:: " + result);
			
			
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			getLogger().info("[checkDupTin][End]");
		}
		
		return result;
	}

	
	public void insertCompanyDetail(CompanyDetailsBean companyDetailsBean) throws EnjoyException{
		getLogger().info("[insertCompanyDetail][Begin]");
		
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
			
			insertData(company);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertCompanyDetail");
		}finally{
			
			company = null;
			getLogger().info("[insertCompanyDetail][End]");
		}
	}
	
	public void updateCompanyDetail(CompanyDetailsBean companyDetailsBean) throws EnjoyException{
		getLogger().info("[updateCompanyDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		
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
			
			query = createQuery(hql);
			
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
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error updateCompanyDetail");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateCompanyDetail][End]");
		}
	}
	
	public List<ComboBean> companyNameList(String companyName, int userUniqueId){
		getLogger().info("[companyNameList][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			comboList	=  new ArrayList<ComboBean>();
			
			if(userUniqueId==1){
				hql = "select tin, companyName "
					+ "	from company "
					+ "	where companyName LIKE CONCAT(:companyName, '%')"
					+ "		and companyStatus = 'A'"
					+ "		and tin <> '9999999999999'"
					+ "	order by companyName asc limit 10 ";
				
				param.put("companyName"		, companyName);
			}else{
				hql = "select a.tin, b.companyName"
					+ "	from relationuserncompany a"
					+ "		inner join company b on b.tin = a.tin"
					+ "	where a.userUniqueId = :userUniqueId"
					+ "		and b.companyName LIKE CONCAT(:companyName, '%')"
					+ "		and tin <> '9999999999999'";
				
				param.put("userUniqueId"	, userUniqueId);
				param.put("companyName"		, companyName);
			}
			
			//Column select
			columnList.add("tin");
			columnList.add("companyName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode	(EnjoyUtils.nullToStr(row.get("tin")));
				comboBean.setDesc	(EnjoyUtils.nullToStr(row.get("companyName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[companyNameList][End]");
		}
		
		return comboList;
	}
	
	public String getTin(String companyName){
		getLogger().info("[getTin][Begin]");
		
		String 							hql			 		= null;
        String							tin					= null;
        HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<Object>					resultList			= null;
		
		try{
			hql 	= "select tin "
					+ "	from company "
					+ "	where companyStatus = 'A'"
					+ "		and tin <> '9999999999999'"
					+ "		and companyName = :companyName";
			
			//Criteria
			param.put("companyName"	, companyName);
			
			//Column select
			columnList.add("tin");
			
			resultList = getResult(hql, param, "tin", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				tin = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[getTin][End]");
		}
		
		return tin;
	}
	
	public List<ComboBean> getCompanyCombo() throws EnjoyException{
		getLogger().info("[getCompanyCombo][Begin]");
		
		String							hql					= null;
		ComboBean						comboBean			= null;
		List<ComboBean> 				comboList			= new ArrayList<ComboBean>();
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			
			hql	= "select tin,companyName from company where companyStatus = 'A' and tin <> '9999999999999'";

			//Column select
			columnList.add("tin");
			columnList.add("companyName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean();
				
				comboBean.setCode(EnjoyUtils.nullToStr(row.get("tin")));
				comboBean.setDesc(EnjoyUtils.nullToStr(row.get("companyName")));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info("[getCompanyCombo] " + e.getMessage());
			throw new EnjoyException("Error getCompanyCombo");
		}finally{
			hql				= null;
			getLogger().info("[getCompanyCombo][End]");
		}
		
		return comboList;
		
	}
	
	public List<ComboBean> tinListForAutoComplete(String tin){
		getLogger().info("[tinListForAutoComplete][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		String							criteria			= "";
		String							orderBy				= "";
		
		try{
			comboList			=  new ArrayList<ComboBean>();
			hql 				= " select distinct tin from company where tin <> '9999999999999'";
			
			
			//Criteria
			if(tin!=null && !"".equals(tin)){
				criteria = " and tin LIKE CONCAT(:tin, '%')";
				param.put("tin"	, tin);
			}
			
			orderBy = "	order by tin asc limit 10";
			hql		= hql + criteria + orderBy;
			
			resultList = getResult(hql, param, "tin", Constants.STRING_TYPE);
			
			for(Object row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[tinListForAutoComplete][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> companyNameListForAutoComplete(String companyName){
		getLogger().info("[companyNameListForAutoComplete][Begin]");
		
		String 							hql			 		= null;
		List<ComboBean>					comboList 			= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		String							criteria			= "";
		String							orderBy				= "";
		
		try{
			comboList	=  new ArrayList<ComboBean>();
			hql 		= "select distinct companyName "
						+ "	from company "
						+ "	where tin <> '9999999999999'";
			
			//Criteria
			if(companyName!=null && !"".equals(companyName)){
				criteria = " and companyName LIKE CONCAT(:companyName, '%')";
				param.put("companyName"	, companyName);
			}
			
			orderBy = "	order by companyName asc limit 10";
			hql		= hql + criteria + orderBy;
			
			resultList = getResult(hql, param, "companyName", Constants.STRING_TYPE);
			
			for(Object row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode	(EnjoyUtils.nullToStr(row));
				comboBean.setDesc	(EnjoyUtils.nullToStr(row));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[companyNameListForAutoComplete][End]");
		}
		
		return comboList;
	}
	
}
