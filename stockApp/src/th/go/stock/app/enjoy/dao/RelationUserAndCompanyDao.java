
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.RelationUserAndCompanyBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Relationuserncompany;
import th.go.stock.app.enjoy.model.RelationuserncompanyPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class RelationUserAndCompanyDao extends DaoControl{
	
	public RelationUserAndCompanyDao(){
		setLogger(EnjoyLogger.getLogger(RelationUserAndCompanyDao.class));
		super.init();
	}
	
	public List<RelationUserAndCompanyBean> searchByCriteria(RelationUserAndCompanyBean relationUserAndCompanyBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String								hql							= null;
		RelationUserAndCompanyBean			bean						= null;
		List<RelationUserAndCompanyBean> 	relationUserAndCompanyList 	= new ArrayList<RelationUserAndCompanyBean>();
		int									seq							= 0;
		HashMap<String, Object>				param						= new HashMap<String, Object>();
		List<String>						columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>		resultList					= null;
		
		try{	
			hql					= "select a.*, b.userEmail, CONCAT(b.userName, ' ', b.userSurname) userFullName, b.userStatus, c.userStatusName"
								+ "	from relationuserncompany a, userdetails b, refuserstatus c"
								+ "	where b.userUniqueId 		= a.userUniqueId"
									+ " and c.userStatusCode	= b.userStatus"
									+ " and a.tin 				= :tin";
			
			//Criteria
			param.put("tin"	, relationUserAndCompanyBean.getTin());
			
			if(!relationUserAndCompanyBean.getUserFullName().equals("")){
				hql += " and CONCAT(b.userName, ' ', b.userSurname) LIKE CONCAT(:userName, '%')";
				param.put("userName"	, relationUserAndCompanyBean.getUserFullName());
			}
			
			//Column select
			columnList.add("userUniqueId");
			columnList.add("tin");
			columnList.add("userEmail");
			columnList.add("userFullName");
			columnList.add("userStatus");
			columnList.add("userStatusName");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new RelationUserAndCompanyBean();
				
				bean.setUserUniqueId	(EnjoyUtils.nullToStr(row.get("userUniqueId")));
				bean.setTin				(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setUserEmail		(EnjoyUtils.nullToStr(row.get("userEmail")));
				bean.setUserFullName	(EnjoyUtils.nullToStr(row.get("userFullName")));
				bean.setUserStatus		(EnjoyUtils.nullToStr(row.get("userStatus")));
				bean.setUserStatusName	(EnjoyUtils.nullToStr(row.get("userStatusName")));
				bean.setSeq				(EnjoyUtils.nullToStr(seq));
				
				relationUserAndCompanyList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return relationUserAndCompanyList;
		
	}
	
	public int checkDupUser(String 	userUniqueId, String tin) throws EnjoyException{
		getLogger().info("[checkDupUser][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql				= "select count(*) cou from relationuserncompany"
								+ " where userUniqueId 	= :userUniqueId"
								+ "		and tin = :tin";
			
			//Criteria
			param.put("userUniqueId", userUniqueId);
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[checkDupUser] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
		}finally{
			hql				= null;
			getLogger().info("[checkDupUser][End]");
		}
		
		return result;
	}
	
	public void insertRelationUserAndCompany(RelationUserAndCompanyBean relationUserAndCompanyBean) throws EnjoyException{
		getLogger().info("[insertRelationUserAndCompany][Begin]");
		
		Relationuserncompany	relationuserncompany	= null;
		RelationuserncompanyPK	id						= null;
		
		try{
			id						= new RelationuserncompanyPK();
			relationuserncompany 	= new Relationuserncompany();
			
			id.setTin			(relationUserAndCompanyBean.getTin());
			id.setUserUniqueId	(EnjoyUtils.parseInt(relationUserAndCompanyBean.getUserUniqueId()));
			
			relationuserncompany.setId(id);
			
			insertData(relationuserncompany);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertRelationUserAndCompany");
		}finally{
			relationuserncompany = null;
			getLogger().info("[insertRelationUserAndCompany][End]");
		}
	}
	
	public void deleteRelationUserAndCompany(String tin) throws EnjoyException{
		getLogger().info("[deleteRelationUserAndCompany][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			getLogger().info("[deleteRelationUserAndCompany] tin :: " + tin);
			
			hql				= "delete Relationuserncompany t where t.id.tin	 = :tin";
			
			query = createQuery(hql);
			query.setParameter("tin", tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteRelationUserAndCompany][End]");
		}
	}
	
	public void deleteRelationUserAndCompany(String userUniqueId, String tin) throws EnjoyException{
		getLogger().info("[deleteRelationUserAndCompany][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			getLogger().info("[deleteRelationUserAndCompany] userUniqueId :: " + userUniqueId);
			getLogger().info("[deleteRelationUserAndCompany] tin :: " + tin);
			
			hql				= "delete Relationuserncompany t where t.id.userUniqueId = :userUniqueId and t.id.tin	 = :tin";
			
			query = createQuery(hql);
			query.setParameter("userUniqueId", userUniqueId);
			query.setParameter("tin", tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteRelationUserAndCompany][End]");
		}
	}
	
	public int countForCheckLogin(int userUniqueId) throws EnjoyException{
		getLogger().info("[countForCheckLogin][Begin]");
		
		String							hql					= null;
		int 							result				= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql				= "select count(*) cou from relationuserncompany where userUniqueId = :userUniqueId";
			
			//Criteria
			param.put("userUniqueId"	, userUniqueId);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[countForCheckLogin] result 			:: " + result);
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
		}finally{
			hql				= null;
			getLogger().info("[countForCheckLogin][End]");
		}
		
		return result;
	}
	
	public List<ComboBean> getCompanyList(int userUniqueId) throws EnjoyException{
		getLogger().info("[getCompanyList][Begin]");
		
		String							hql					= null;
		List<ComboBean>					comboList			= new ArrayList<ComboBean>();
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			
			if(userUniqueId==1){
				hql		= "select tin, companyName from company where companyStatus = 'A'";
			}else{
				hql		= "select a.tin, b.companyName"
						+ "	from relationuserncompany a"
						+ "		INNER JOIN company b on a.tin = b.tin"
						+ "	where a.userUniqueId = :userUniqueId";
				
				param.put("userUniqueId"	, userUniqueId);
			}
			
			//Column select
			columnList.add("tin");
			columnList.add("companyName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboList.add(new ComboBean(EnjoyUtils.nullToStr(row.get("tin")), EnjoyUtils.nullToStr(row.get("companyName"))));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[getCompanyList][End]");
		}
		
		return comboList;
	}
	
	public ComboBean getCompanyForAdminEnjoy(int userUniqueId) throws EnjoyException{
		getLogger().info("[getCompanyForAdminEnjoy][Begin]");
		
		String							hql					= null;
		ComboBean						comboBean			= null;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<String>					columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList			= null;
		
		try{
			
			hql		= "select a.tin, b.companyName"
					+ "	from relationuserncompany a"
					+ "		INNER JOIN company b on a.tin = b.tin"
					+ "	where a.userUniqueId = :userUniqueId";
			
			param.put("userUniqueId"	, userUniqueId);
			
			//Column select
			columnList.add("tin");
			columnList.add("companyName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean(EnjoyUtils.nullToStr(row.get("tin")), EnjoyUtils.nullToStr(row.get("companyName")));
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[getCompanyForAdminEnjoy][End]");
		}
		
		return comboBean;
	}
	
}
