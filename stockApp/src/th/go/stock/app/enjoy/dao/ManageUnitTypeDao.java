
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ManageUnitTypeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Unittype;
import th.go.stock.app.enjoy.model.UnittypePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ManageUnitTypeDao extends DaoControl{
	
	public ManageUnitTypeDao(){
		setLogger(EnjoyLogger.getLogger(ManageUnitTypeDao.class));
		super.init();
	}
	
	public List<ManageUnitTypeBean> getUnitTypeList(String tin) throws EnjoyException{
		getLogger().info("[getUnitTypeList][Begin]");
		
		String							hql							= null;
		ManageUnitTypeBean				bean						= null;
		List<ManageUnitTypeBean> 		manageUnitTypeList 			= new ArrayList<ManageUnitTypeBean>();
		int								seq							= 0;
		HashMap<String, Object>			param						= new HashMap<String, Object>();
		List<String>					columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList					= null;
		
		try{	
			hql					= "select * "
								+ "	from unittype"
								+ "	where unitStatus 	= 'A'"
								+ "		and tin 		= :tin"
								+ "	order by unitCode asc";
			
			//Criteria
			param.put("tin"	, tin);

			//Column select
			columnList.add("unitCode");
			columnList.add("tin");
			columnList.add("unitName");
			columnList.add("unitStatus");
			
			resultList = getResult(hql, param, columnList);

			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ManageUnitTypeBean();
				
				bean.setUnitCode	(EnjoyUtils.nullToStr(row.get("unitCode")));
				bean.setTin			(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setUnitName	(EnjoyUtils.nullToStr(row.get("unitName")));
				bean.setUnitStatus	(EnjoyUtils.nullToStr(row.get("unitStatus")));
				bean.setSeq			(EnjoyUtils.nullToStr(seq));
				
				manageUnitTypeList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getUnitTypeList");
		}finally{
			hql						= null;
			getLogger().info("[getUnitTypeList][End]");
		}
		
		return manageUnitTypeList;
		
	}
	
	public void insertUnitType(ManageUnitTypeBean manageUnitTypeBean) throws EnjoyException{
		getLogger().info("[insertUnitType][Begin]");
		
		Unittype	unittype	= new Unittype();
		UnittypePK 	id			= new UnittypePK();
		
		try{
			
//			id.setUnitCode	(this.genId(manageUnitTypeBean.getTin()));
			id.setUnitCode	(EnjoyUtils.parseInt(manageUnitTypeBean.getUnitCode()));
			id.setTin		(manageUnitTypeBean.getTin());
			
			unittype.setId			(id);
			unittype.setUnitName	(manageUnitTypeBean.getUnitName());
			unittype.setUnitStatus	(manageUnitTypeBean.getUnitStatus());
			
			insertData(unittype);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertUnitType");
		}finally{
			
			unittype = null;
			getLogger().info("[insertUnitType][End]");
		}
	}
	
	public void updateUnitType(ManageUnitTypeBean manageUnitTypeBean) throws EnjoyException{
		getLogger().info("[updateUnitType][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update Unittype set unitName 		= :unitName"
											+ "	,unitStatus 		= :unitStatus"
										+ " where unitCode 	= :unitCode"
										+ "		and tin		= :tin";
			
			query = createQuery(hql);
			query.setParameter("unitName"		, manageUnitTypeBean.getUnitName());
			query.setParameter("unitStatus"		, manageUnitTypeBean.getUnitStatus());
			query.setParameter("unitCode"		, Integer.parseInt(manageUnitTypeBean.getUnitCode()));
			query.setParameter("tin"			, manageUnitTypeBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateUnitType");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateUnitType][End]");
		}
	}
	
	public int checkDupUnitName(String unitName, String unitCode, String tin) throws EnjoyException{
		getLogger().info("[checkDupUnitName][Begin]");
		
		String							hql				= null;
		int 							result			= 0;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select count(*) cou "
					+ "	from unittype "
					+ "	where unitName 	= :unitName"
					+ "		and tin		= :tin"
					+ "		and unitStatus = 'A'";
			
			//Criteria
			param.put("unitName", unitName);
			param.put("tin"		, tin);
			
			if(unitCode!=null && !unitCode.equals("")){
				hql += " and unitCode <> :unitCode";
				param.put("unitCode", unitCode);
			}
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupUnitName] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("checkDupUnitName error");
		}finally{
			hql									= null;
			getLogger().info("[checkDupUnitName][End]");
		}
		
		return result;
	}
	
	public int genId(String tin) throws EnjoyException{
		getLogger().info("[genId][Begin]");
		
		String							hql				= null;
		int 							result			= 1;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select (max(unitCode) + 1) newId"
					+ "	from unittype "
					+ "	where tin		= :tin";
			
			//Criteria
			param.put("tin"		, tin);
			
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
	
	public List<ComboBean> unitNameList(String unitName, String tin) throws EnjoyException{
		getLogger().info("[unitNameList][Begin]");
		
		String 							hql			 	= null;
		List<ComboBean>					comboList 		= null;
		ComboBean						comboBean		= null;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<String>					columnList		= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList		= null;
		
		try{
			comboList	= new ArrayList<ComboBean>();
			hql 		= "select unitCode, unitName "
						+ "	from unittype "
						+ "	where unitName LIKE CONCAT(:unitName, '%')"
						+ "		and tin 		= :tin"
						+ "		and unitStatus  = 'A' "
						+ "	order by unitName asc limit 10 ";
			
			//Criteria
			param.put("unitName", unitName);
			param.put("tin"		, tin);

			//Column select
			columnList.add("unitCode");
			columnList.add("unitName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean 	= new ComboBean();
				
				comboBean.setCode				(EnjoyUtils.nullToStr(row.get("unitCode")));
				comboBean.setDesc				(EnjoyUtils.nullToStr(row.get("unitName")));
				
				comboList.add(comboBean);
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("error unitNameList");
		}finally{
			getLogger().info("[unitNameList][End]");
		}
		
		return comboList;
	}
	
	public String getUnitCode(String unitName, String tin) throws EnjoyException{
		getLogger().info("[getUnitCode][Begin]");
		
		String 							hql			 	= null;
        String							unitCode		= null;
        HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql 	= "select unitCode "
					+ "	from unittype "
					+ "	where unitStatus 	= 'A' "
					+ "		and unitName 	= :unitName"
					+ "		and	tin			= :tin";
			
			//Criteria
			param.put("unitName", unitName);
			param.put("tin"		, tin);

			resultList = getResult(hql, param, "unitCode", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				unitCode = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("error getUnitCode");
		}finally{
			getLogger().info("[getUnitCode][End]");
		}
		
		return unitCode;
	}

}
