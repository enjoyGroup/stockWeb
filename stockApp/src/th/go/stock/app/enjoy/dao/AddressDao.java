package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.AddressBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class AddressDao extends DaoControl{	
	
	
	public AddressDao(){
		setLogger(EnjoyLogger.getLogger(AddressDao.class));
		super.init();
	}
	
	public List<ComboBean> provinceList(String province){
		getLogger().info("[provinceList][Begin]");
		
		String 							hql			 	= "";
		String 							where			= "";
		String 							order			= "";
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<String>					columnList		= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList		= null;
		List<ComboBean>					comboList 		= new ArrayList<ComboBean>();
		ComboBean						comboBean		= null;
		
		try{
			hql 	= "select provinceId, provinceName "
					+ "	from province "
					+ "	where provinceId <> 00 ";
			
			//Criteria
			if(!"".equals(province)){
				where = " and provinceName LIKE CONCAT(:provinceName, '%')";
				param.put("provinceName"	, province);
			}
			order = " order by provinceName asc limit 10";
			
			hql = hql + where + order;
			
			//Column select
			columnList.add("provinceId");
			columnList.add("provinceName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode	(EnjoyUtils.nullToStr(row.get("provinceId")));
				comboBean.setDesc	(EnjoyUtils.nullToStr(row.get("provinceName")));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[provinceList][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> districtList(String province, String district){
		getLogger().info("[districtList][Begin]");
		
		String 							hql			 	= "";
		String 							where			= "";
		String 							order			= "";
        String							provinceId		= null;
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<String>					columnList		= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList		= null;
		List<Object>					listObj			= null;
		List<ComboBean>					comboList 		= new ArrayList<ComboBean>();
		ComboBean						comboBean		= null;
		
		try{
			
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = :provinceName";
			
			//Criteria
			param.put("provinceName"		, province);
			
			listObj = getResult(hql, param, "provinceId", Constants.STRING_TYPE);
			
			if(listObj!=null && listObj.size() > 0){
				provinceId = EnjoyUtils.nullToStr(listObj.get(0));
			}
		    /*End check province section*/
			
			getLogger().info("[districtList] provinceId :: " + provinceId);
		    
		    if(provinceId==null){
		    	comboList.add(new ComboBean("", "กรุณาระบุจังหวัด"));
		    }else{
		    	param		= new HashMap<String, Object>();
		    	columnList	= new ArrayList<String>();
		    	hql 		= "select districtId, districtName "
		    				+ "	from district "
		    				+ "	where districtId <> 0000 "
		    				+ "		and provinceId <> 00 "
		    				+ "		and provinceId = :provinceId";
				
		    	//Criteria
		    	param.put("provinceId"		, provinceId);
		    	
		    	if(!"".equals(district)){
		    		where = " and districtName LIKE CONCAT(:districtName, '%')";
		    		param.put("districtName"	, district);
		    	}
		    	
		    	order = " order by districtName asc limit 10";
		    	
		    	hql = hql + where + order;
				
				//Column select
				columnList.add("districtId");
				columnList.add("districtName");
				
				resultList = getResult(hql, param, columnList);
				
				for(HashMap<String, Object> row:resultList){
					comboBean 	= new ComboBean();
					
					comboBean.setCode	(EnjoyUtils.nullToStr(row.get("districtId")));
					comboBean.setDesc	(EnjoyUtils.nullToStr(row.get("districtName")));
					
					comboList.add(comboBean);
				}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[districtList][End]");
		}
		
		return comboList;
	}
	
	public List<ComboBean> subdistrictList(String province, String district, String subdistrict){
		getLogger().info("[subdistrictList][Begin]");
		
		String 							hql			 	= "";
		String 							where			= "";
		String 							order			= "";
        String							provinceId		= null;
        String							districtId		= null;
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<String>					columnList		= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList		= null;
		List<Object>					listObj			= null;
		List<ComboBean>					comboList 		= new ArrayList<ComboBean>();
		ComboBean						comboBean		= null;
		
		try{
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = :provinceName";
			
			//Criteria
			param.put("provinceName"		, province);
			
			listObj = getResult(hql, param, "provinceId", Constants.STRING_TYPE);
			
			if(listObj!=null && listObj.size() > 0){
				provinceId = EnjoyUtils.nullToStr(listObj.get(0));
			}
		    /*End check province section*/
		    
		    /*Begin check district section*/
			param		= new HashMap<String, Object>();
			hql 		= "select districtId from district where districtId <> 0000 and districtName = :districtName";
			
			//Criteria
			param.put("districtName"		, district);
			
			listObj = getResult(hql, param, "districtId", Constants.STRING_TYPE);
			
			if(listObj!=null && listObj.size() > 0){
				districtId = EnjoyUtils.nullToStr(listObj.get(0));
			}
		    /*End check district section*/
		    
		    if(provinceId==null){
		    	comboList.add(new ComboBean("", "กรุณาระบุจังหวัด"));
		    }else if(districtId==null){
		    	comboList.add(new ComboBean("", "กรุณาระบุอำเภอ"));
		    }else{
		    	param		= new HashMap<String, Object>();
		    	columnList	= new ArrayList<String>();
		    	hql 		= "select subdistrictId, subdistrictName" 
								   + " from subdistrict "
								   + "  where subdistrictId <> 000000 "
								   + " 		and provinceId <> 00 "
								   + " 		and districtId <> 0000 "
								   + " 		and SUBSTR(subdistrictId, 5, 2) <> 00"
								   + " 		and provinceId = :provinceId"
								   + " 		and districtId = :districtId";
				
		    	//Criteria
		    	param.put("provinceId"		, provinceId);
				param.put("districtId"		, districtId);
				
		    	if(!"".equals(subdistrict)){
		    		where = " and subdistrictName LIKE CONCAT(:subdistrictName, '%')";
		    		param.put("subdistrictName"	, subdistrict);
		    	}
		    	
		    	order = " order by subdistrictName asc limit 10";
		    	
		    	hql = hql + where + order;
				
				//Column select
				columnList.add("subdistrictId");
				columnList.add("subdistrictName");
				
				resultList = getResult(hql, param, columnList);
				
				for(HashMap<String, Object> row:resultList){
					comboBean 	= new ComboBean();
					
					comboBean.setCode	(EnjoyUtils.nullToStr(row.get("subdistrictId")));
					comboBean.setDesc	(EnjoyUtils.nullToStr(row.get("subdistrictName")));
					
					comboList.add(comboBean);
				}
		    }
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[subdistrictList][End]");
		}
		
		return comboList;
	}
	
	public AddressBean validateAddress(String province, String district, String subdistrict){
		getLogger().info("[validateAddress][Begin]");
		
		String 							hql			 	= null;
        String							provinceId		= null;
        String							districtId		= null;
        String							subdistrictId	= null;
        String							errMsg			= null;
        AddressBean						addressBean		= new AddressBean();
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			/*Begin check province section*/
			hql 		= "select provinceId from province where provinceId <> 00 and provinceName = :provinceName";
			
			//Criteria
			param.put("provinceName"		, province);
			
			resultList = getResult(hql, param, "provinceId", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				provinceId = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		    if(provinceId==null)throw new EnjoyException("ระบุจังหวัดผิด");
		    /*End check province section*/
		    
		    /*Begin check district section*/
		    param		= new HashMap<String, Object>();
		    hql 		= "select districtId from district where districtId <> 0000 and provinceId = :provinceId and districtName = :districtName";
			
		    //Criteria
			param.put("provinceId"		, provinceId);
			param.put("districtName"		, district);
			
			resultList = getResult(hql, param, "districtId", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				districtId = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		    if(districtId==null)throw new EnjoyException("ระบุอำเภอผิด");
		    /*End check district section*/
		    
		    /*Begin check subDistrict section*/
		    param		= new HashMap<String, Object>();
		    hql 		= "select subdistrictId from subdistrict where subdistrictId <> 000000 and provinceId = :provinceId and districtId = :districtId and subdistrictName = :subdistrictName";
			
		    //Criteria
			param.put("provinceId"		, provinceId);
			param.put("districtId"		, districtId);
			param.put("subdistrictName"	, subdistrict);
			
			resultList = getResult(hql, param, "subdistrictId", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				subdistrictId = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		    if(subdistrictId==null)throw new EnjoyException("ระบุตำบลผิด");
		    /*End check subDistrict section*/
		    
		    getLogger().info("[validateAddress] " + provinceId + ", " + districtId + ", " + subdistrictId);
		    
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
			getLogger().info("[validateAddress][End]");
		}
		return addressBean;
	}
	
	public String getProvinceName(String provinceId){
		getLogger().info("[getProvinceName][Begin]");
		
		String 							hql			 	= null;
        String							provinceName	= null;
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql 		= " select provinceName from province where provinceId <> 00 and provinceId = :provinceId";
			
			//Criteria
			param.put("provinceId"		, provinceId);
			
			resultList = getResult(hql, param, "provinceName", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				provinceName = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[getProvinceName][End]");
		}
		
		return provinceName;
	}
	
	public String getSubdistrictName(String subdistrictId){
		getLogger().info("[getSubdistrictName][Begin]");
		
		String 							hql			 	= null;
        String							subdistrictName	= null;
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql 				= " select subdistrictName from subdistrict where subdistrictId <> 00 and subdistrictId = :subdistrictId";
			
			//Criteria
			param.put("subdistrictId"		, subdistrictId);
			
			resultList = getResult(hql, param, "subdistrictName", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				subdistrictName = EnjoyUtils.nullToStr(resultList.get(0));
			}
		    
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[getSubdistrictName][End]");
		}
		
		return subdistrictName;
	}
	
	public String getDistrictName(String districtId){
		getLogger().info("[getDistrictName][Begin]");
		
		String 						hql			 	= null;
        String						districtName	= null;
        HashMap<String, Object>		param			= new HashMap<String, Object>();
		List<Object>				resultList		= null;
		
		try{
			hql 		= " select districtName from district where districtId <> 00 and districtId = :districtId";
			
			//Criteria
			param.put("districtId"		, districtId);
			
			resultList = getResult(hql, param, "districtName", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				districtName = EnjoyUtils.nullToStr(resultList.get(0));
			}
		    
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			getLogger().info("[getDistrictName][End]");
		}
		
		return districtName;
	}
	
}















