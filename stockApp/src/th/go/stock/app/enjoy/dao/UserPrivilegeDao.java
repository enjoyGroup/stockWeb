package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import th.go.stock.app.enjoy.bean.PagesDetailBean;
import th.go.stock.app.enjoy.bean.UserPrivilegeBean;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class UserPrivilegeDao extends DaoControl{
	
	public UserPrivilegeDao(){
		setLogger(EnjoyLogger.getLogger(UserPrivilegeDao.class));
		super.init();
	}
	
	public List<UserPrivilegeBean> userPrivilegeListSelect(String privilegeCode){
		getLogger().info("[UserPrivilegeDao][userPrivilegeListSelect][Begin]");
		
		List<UserPrivilegeBean> 		userPrivilegeBeanList	= null;
		UserPrivilegeBean 				userPrivilegeBean 		= null;
		String							hql						= null;
		String[]						arrPrivilegeCode		= null;				
		int								maxArrPrivilegeCode		= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			arrPrivilegeCode	  = privilegeCode.split("\\,");
			maxArrPrivilegeCode   = arrPrivilegeCode.length;
			userPrivilegeBeanList = new ArrayList<UserPrivilegeBean>();
			
			for(int i=0;i<maxArrPrivilegeCode;i++){
				param				= new HashMap<String, Object>();
				columnList			= new ArrayList<String>();
				hql					= "select * from userprivilege where privilegeCode = :privilegeCode";  // จะได้ 1 รายการเสมอ
				
				//Criteria
				param.put("privilegeCode", arrPrivilegeCode[i]);
				
				//Column select
				columnList.add("privilegeCode");
				columnList.add("privilegeName");
				columnList.add("pagesCode");
				
				
				resultList = getResult(hql, param, columnList);		
				
				for(HashMap<String, Object> row:resultList){
					userPrivilegeBean	= new UserPrivilegeBean();
					
					userPrivilegeBean.setPrivilegeCode(EnjoyUtils.nullToStr(row.get("privilegeCode")));
					userPrivilegeBean.setPrivilegeName(EnjoyUtils.nullToStr(row.get("privilegeName")));
					userPrivilegeBean.setPagesDetail((ArrayList<PagesDetailBean>) pagesDetailListSelect(EnjoyUtils.nullToStr(row.get("pagesCode"))));
					userPrivilegeBeanList.add(userPrivilegeBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
		}finally{
			hql				= null;
			getLogger().info("[UserPrivilegeDao][userPrivilegeListSelect][End]");
		}
		
		return userPrivilegeBeanList;
	}
	
	public List<PagesDetailBean> pagesDetailListSelect(String pageCodes){
		getLogger().info("[UserPrivilegeDao][pagesDetailListSelect][Begin]");
		
		List<PagesDetailBean> 			pagesDetailBeanList		= null;
		PagesDetailBean 				pagesDetailBean 		= null;
		String							hql						= null;
		String[]						arrPageCodes			= null;				
		int								maxArrPageCodes			= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			arrPageCodes		  = pageCodes.split("\\,");
			maxArrPageCodes   	  = arrPageCodes.length;
			pagesDetailBeanList   = new ArrayList<PagesDetailBean>();
			
			for(int i=0;i<maxArrPageCodes;i++){
				param				= new HashMap<String, Object>();
				columnList			= new ArrayList<String>();
				hql					= "select * from refpagedetails where pageCodes = :pageCodes";  // จะได้ 1 รายการเสมอ
				
				//Criteria
				param.put("pageCodes", arrPageCodes[i]);
				
				//Column select
				columnList.add("pageCodes");
				columnList.add("pageNames");
				columnList.add("pathPages");
				
				resultList = getResult(hql, param, columnList);		
				
				for(HashMap<String, Object> row:resultList){
					pagesDetailBean		= new PagesDetailBean();
					
					pagesDetailBean.setPageCodes(EnjoyUtils.nullToStr(row.get("pageCodes")));
					pagesDetailBean.setPageNames(EnjoyUtils.nullToStr(row.get("pageNames")));
					pagesDetailBean.setPathPages(EnjoyUtils.nullToStr(row.get("pathPages")));
					
					pagesDetailBeanList.add(pagesDetailBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
		}finally{
			pagesDetailBean 		= null;
			hql						= null;
			arrPageCodes			= null;				
			getLogger().info("[UserPrivilegeDao][pagesDetailListSelect][End]");
		}
		
		return pagesDetailBeanList;
	}
	
}
