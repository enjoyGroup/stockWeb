
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.RefconstantcodeBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Refconstantcode;
import th.go.stock.app.enjoy.model.RefconstantcodePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class RefconstantcodeDao extends DaoControl{
	
	public RefconstantcodeDao(){
		setLogger(EnjoyLogger.getLogger(RefconstantcodeDao.class));
		super.init();
	}
	
	public List<RefconstantcodeBean> searchByCriteria(RefconstantcodeBean refconstantcodeBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		RefconstantcodeBean				bean					= null;
		List<RefconstantcodeBean> 		refconstantcodeList 	= new ArrayList<RefconstantcodeBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql	= "select * from refconstantcode where tin = :tin";
			
			//Set Criteria
			param.put("tin", refconstantcodeBean.getTin());
			
			if(!"".equals(refconstantcodeBean.getTypeTB())){
				hql += " and typeTB = :typeTB";
				param.put("typeTB", refconstantcodeBean.getTypeTB());
			}
			
			//Column select
			columnList.add("id");
			columnList.add("tin");
			columnList.add("codeDisplay");
			columnList.add("codeNameTH");
			columnList.add("codeNameEN");
			columnList.add("flagYear");
			columnList.add("flagEdit");
			columnList.add("typeTB");
			
			resultList = getResult(hql, param, columnList);
		    
			for(HashMap<String, Object> row:resultList){
				bean 	= new RefconstantcodeBean();
				
				bean.setId					(EnjoyUtils.nullToStr(row.get("id")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setCodeDisplay			(EnjoyUtils.nullToStr(row.get("codeDisplay")));
				bean.setCodeNameTH			(EnjoyUtils.nullToStr(row.get("codeNameTH")));
				bean.setCodeNameEN			(EnjoyUtils.nullToStr(row.get("codeNameEN")));
				bean.setFlagYear			(EnjoyUtils.nullToStr(row.get("flagYear")));
				bean.setFlagEdit			(EnjoyUtils.nullToStr(row.get("flagEdit")));
				bean.setTypeTB				(EnjoyUtils.nullToStr(row.get("typeTB")));
				
				refconstantcodeList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return refconstantcodeList;
		
	}
	
	public void insertRefconstantcode(RefconstantcodeBean refconstantcodeBean) throws EnjoyException{
		getLogger().info("[insertRefconstantcode][Begin]");
		
		Refconstantcode		refconstantcode		= new Refconstantcode();
		RefconstantcodePK 	id 					= new RefconstantcodePK();
		
		try{
			
			id.setId	(EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			id.setTin	(refconstantcodeBean.getTin());
			
			refconstantcode.setId			(id);
			refconstantcode.setCodeDisplay	(refconstantcodeBean.getCodeDisplay());
			refconstantcode.setCodeNameTH	(refconstantcodeBean.getCodeNameTH());
			refconstantcode.setCodeNameEN	(refconstantcodeBean.getCodeNameEN());
			refconstantcode.setFlagYear		(refconstantcodeBean.getFlagYear());
			refconstantcode.setFlagEdit		(refconstantcodeBean.getFlagEdit());
			refconstantcode.setTypeTB		(refconstantcodeBean.getTypeTB());
			
			insertData(refconstantcode);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertRefconstantcode");
		}finally{
			
			refconstantcode = null;
			getLogger().info("[insertRefconstantcode][End]");
		}
	}
	
	public void updateRefconstantcode(RefconstantcodeBean refconstantcodeBean) throws EnjoyException{
		getLogger().info("[updateRefconstantcode][Begin]");
		
		String	hql			= null;
		Query 	query 		= null;
		
		try{
			hql				= "update  Refconstantcode t set t.codeDisplay 	= :codeDisplay"
														+ ", t.codeNameTH	= :codeNameTH"
														+ ", t.codeNameEN	= :codeNameEN"
														+ ", t.flagYear		= :flagYear"
														+ ", t.flagEdit		= :flagEdit"
										+ " where t.id.id 		= :id"
										+ "		and t.id.tin 	= :tin";
			
			query = createQuery(hql);
			query.setParameter("codeDisplay"		, refconstantcodeBean.getCodeDisplay());
			query.setParameter("codeNameTH"			, refconstantcodeBean.getCodeNameTH());
			query.setParameter("codeNameEN"			, refconstantcodeBean.getCodeNameEN());
			query.setParameter("flagYear"			, refconstantcodeBean.getFlagYear());
			query.setParameter("flagEdit"			, refconstantcodeBean.getFlagEdit());
			query.setParameter("id"					, EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			query.setParameter("tin"				, refconstantcodeBean.getTin());
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateRefconstantcode");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateRefconstantcode][End]");
		}
	}
	
	public void updateCodeDisplay(RefconstantcodeBean 		refconstantcodeBean) throws EnjoyException{
		getLogger().info("[updateCodeDisplay][Begin]");
		
		String	hql			= null;
		Query 	query 		= null;
		
		try{
			hql		= "update  Refconstantcode t"
					+ "	set t.codeDisplay 	= :codeDisplay"
					+ "	  , t.flagYear 		= :flagYear"
					+ " where t.id.id 		= :id"
					+ "		and t.id.tin 	= :tin";
			
			query = createQuery(hql);
			query.setParameter("codeDisplay"	, refconstantcodeBean.getCodeDisplay());
			query.setParameter("flagYear"		, refconstantcodeBean.getFlagYear());
			query.setParameter("id"				, EnjoyUtils.parseInt(refconstantcodeBean.getId()));
			query.setParameter("tin"			, refconstantcodeBean.getTin());
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateCodeDisplay");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateCodeDisplay][End]");
		}
	}
	
	public String getCodeDisplay(String id, String tin) throws EnjoyException{
		getLogger().info("[getCodeDisplay][Begin]");
		
		String							hql					= null;
		String							codeDisplay			= null;
		String							currDate			= "";
		String							year				= "";
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			currDate		= EnjoyUtils.currDateThai();
			year			= currDate.substring(2, 4);
			
			hql				= "select case"
							+ "			WHEN flagYear = 'Y' THEN"
							+ "				CONCAT(codeDisplay, :year)"
							+ "			ELSE"
							+ "				codeDisplay"
							+ "		  END as codeDisplay"
							+ "	from refconstantcode"
							+ "	where id 	= :id"
							+ "		and tin = :tin";
			
			//Criteria
			param.put("year"	,year);
			param.put("id"		,id);
			param.put("tin"		,tin);
			
			resultList = getResult(hql, param, "codeDisplay", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				codeDisplay = EnjoyUtils.nullToStr(resultList.get(0));
			}
			
			getLogger().info("[getCodeDisplay] codeDisplay 			:: " + codeDisplay);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql				= null;
			getLogger().info("[getCodeDisplay][End]");
		}
		
		return codeDisplay;
	}
	
	public List<RefconstantcodeBean> templateDefaultList() throws EnjoyException{
		getLogger().info("[templateDefaultList][Begin]");
		
		String							hql						= null;
		RefconstantcodeBean				bean					= null;
		List<RefconstantcodeBean> 		refconstantcodeList 	= new ArrayList<RefconstantcodeBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select * from templateconstantcode";
			
			//Column select
			columnList.add("id");
			columnList.add("codeDisplay");
			columnList.add("codeNameTH");
			columnList.add("codeNameEN");
			columnList.add("flagYear");
			columnList.add("flagEdit");
			columnList.add("typeTB");
			
			resultList = getResult(hql, param, columnList);
		    
			for(HashMap<String, Object> row:resultList){
				bean 	= new RefconstantcodeBean();
				
				bean.setId					(EnjoyUtils.nullToStr(row.get("id")));
				bean.setCodeDisplay			(EnjoyUtils.nullToStr(row.get("codeDisplay")));
				bean.setCodeNameTH			(EnjoyUtils.nullToStr(row.get("codeNameTH")));
				bean.setCodeNameEN			(EnjoyUtils.nullToStr(row.get("codeNameEN")));
				bean.setFlagYear			(EnjoyUtils.nullToStr(row.get("flagYear")));
				bean.setFlagEdit			(EnjoyUtils.nullToStr(row.get("flagEdit")));
				bean.setTypeTB				(EnjoyUtils.nullToStr(row.get("typeTB")));
				
				refconstantcodeList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error templateDefaultList");
		}finally{
			hql						= null;
			getLogger().info("[templateDefaultList][End]");
		}
		
		return refconstantcodeList;
		
	}
	
	public RefconstantcodeBean getDetail(String id, String tin) throws EnjoyException{
		getLogger().info("[getDetail][Begin]");
		
		String							hql						= null;
		RefconstantcodeBean				bean					= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select * from refconstantcode where id = :id and tin = :tin";
			
			//Set Criteria
			param.put("id", id);
			param.put("tin", tin);
			
			//Column select
			columnList.add("id");
			columnList.add("tin");
			columnList.add("codeDisplay");
			columnList.add("codeNameTH");
			columnList.add("codeNameEN");
			columnList.add("flagYear");
			columnList.add("flagEdit");
			columnList.add("typeTB");
			
			resultList = getResult(hql, param, columnList);
		    
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new RefconstantcodeBean();
					
					bean.setId					(EnjoyUtils.nullToStr(row.get("id")));
					bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setCodeDisplay			(EnjoyUtils.nullToStr(row.get("codeDisplay")));
					bean.setCodeNameTH			(EnjoyUtils.nullToStr(row.get("codeNameTH")));
					bean.setCodeNameEN			(EnjoyUtils.nullToStr(row.get("codeNameEN")));
					bean.setFlagYear			(EnjoyUtils.nullToStr(row.get("flagYear")));
					bean.setFlagEdit			(EnjoyUtils.nullToStr(row.get("flagEdit")));
					bean.setTypeTB				(EnjoyUtils.nullToStr(row.get("typeTB")));
					
				}	
			}
			
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getDetail");
		}finally{
			hql						= null;
			getLogger().info("[getDetail][End]");
		}
		
		return bean;
		
	}
	
}

