
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.RelationGroupCustomerBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Relationgroupcustomer;
import th.go.stock.app.enjoy.model.RelationgroupcustomerPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class RelationGroupCustomerDao extends DaoControl{
	
	public RelationGroupCustomerDao(){
		setLogger(EnjoyLogger.getLogger(RelationGroupCustomerDao.class));
		super.init();
	}
	
	public List<RelationGroupCustomerBean> searchByCriteria(RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql							= null;
		RelationGroupCustomerBean		bean						= null;
		List<RelationGroupCustomerBean> relationGroupCustomerList 	= new ArrayList<RelationGroupCustomerBean>();
		int								seq							= 0;
		HashMap<String, Object>			param						= new HashMap<String, Object>();
		List<String>					columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList					= null;
		
		try{				
			hql	= "select * "
				+ "	from relationgroupcustomer"
				+ "	where cusGroupStatus 	= 'A'"
				+ "		and tin				= :tin"
				+ "	order by cusGroupCode asc";
			
			//Criteria
			param.put("tin"			, relationGroupCustomerBean.getTin());
			
			//Column select
			columnList.add("cusGroupCode");
			columnList.add("tin");
			columnList.add("cusGroupName");
			columnList.add("groupSalePrice");
			columnList.add("cusGroupStatus");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new RelationGroupCustomerBean();
				
				bean.setCusGroupCode			(EnjoyUtils.nullToStr(row.get("cusGroupCode")));
				bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setCusGroupName			(EnjoyUtils.nullToStr(row.get("cusGroupName")));
				bean.setGroupSalePrice			(EnjoyUtils.nullToStr(row.get("groupSalePrice")));
				bean.setCusGroupStatus			(EnjoyUtils.nullToStr(row.get("cusGroupStatus")));
				bean.setSeq						(EnjoyUtils.nullToStr(seq));
				
				relationGroupCustomerList.add(bean);
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
		
		return relationGroupCustomerList;
		
	}

	public void updateRelationGroupCustomer(RelationGroupCustomerBean relationGroupCustomerBean) throws EnjoyException{
		getLogger().info("[updateRelationGroupCustomer][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql	= "update  Relationgroupcustomer t "
				+ "	set t.cusGroupName 		= :cusGroupName"
				+ "		,t.groupSalePrice	= :groupSalePrice"
				+ " where t.id.cusGroupCode = :cusGroupCode"
				+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
			query.setParameter("cusGroupName"	, relationGroupCustomerBean.getCusGroupName());
			query.setParameter("groupSalePrice"	, EnjoyUtils.parseInt(relationGroupCustomerBean.getGroupSalePrice()));
			query.setParameter("cusGroupCode"	, EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			query.setParameter("tin"			, relationGroupCustomerBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateRelationGroupCustomer");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateRelationGroupCustomer][End]");
		}
	}
	
	public void rejectRelationGroupCustomer(RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		getLogger().info("[rejectRelationGroupCustomer][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql	= "update Relationgroupcustomer t"
				+ "	set t.cusGroupStatus 	= 'R'"
				+ " where t.id.cusGroupCode = :cusGroupCode"
				+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
			query.setParameter("cusGroupCode"	, EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			query.setParameter("tin"			, relationGroupCustomerBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error rejectRelationGroupCustomer");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[rejectRelationGroupCustomer][End]");
		}
	}
	
	public void insertRelationGroupCustomer(RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		getLogger().info("[insertRelationGroupCustomer][Begin]");
		
		Relationgroupcustomer		relationgroupcustomer	= new Relationgroupcustomer();
		RelationgroupcustomerPK 	id 						= new RelationgroupcustomerPK();
		String						tin						= null;
		
		try{
			tin = relationGroupCustomerBean.getTin();
			id.setCusGroupCode(EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			id.setTin(tin);
//			id.setCusGroupCode(genId(tin));
			
			relationgroupcustomer.setId				(id);
			relationgroupcustomer.setCusGroupName	(relationGroupCustomerBean.getCusGroupName());
			relationgroupcustomer.setGroupSalePrice	(EnjoyUtils.parseInt(relationGroupCustomerBean.getGroupSalePrice()));
			relationgroupcustomer.setCusGroupStatus	("A");
			
			insertData(relationgroupcustomer);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertRelationGroupCustomer");
		}finally{
			relationgroupcustomer 	= null;
			getLogger().info("[insertRelationGroupCustomer][End]");
		}
	}
	
	public int genId(String tin) throws EnjoyException{
		getLogger().info("[genId][Begin]");
		
		String							hql				= null;
		int 							result			= 1;
		HashMap<String, Object>			param			= new HashMap<String, Object>();
		List<Object>					resultList		= null;
		
		try{
			hql		= "select (max(cusGroupCode) + 1) newId"
					+ "	from relationgroupcustomer "
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

	public int getlastId(String tin) throws EnjoyException{
		getLogger().info("[getlastId][Begin]");
	
		String							hql						= null;
		int 							result					= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			hql	= "select max(cusGroupCode) lastId from relationgroupcustomer where tin = :tin";
			
			//Criteria
			param.put("tin"	, tin);
			
			resultList = getResult(hql, param, "lastId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				result = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			result++;
			
			getLogger().info("[getlastId] result 			:: " + result);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			getLogger().info("[genId][End]");
		}
		
		return result;
	}	
}
