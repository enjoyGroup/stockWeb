
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.RelationGroupCustomerBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Relationgroupcustomer;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class RelationGroupCustomerDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(RelationGroupCustomerDao.class);
	
	
	public List<RelationGroupCustomerBean> searchByCriteria(Session 					session, 
															RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String							hql							= null;
		SQLQuery 						query 						= null;
		List<Object[]>					list						= null;
		RelationGroupCustomerBean		bean						= null;
		List<RelationGroupCustomerBean> relationGroupCustomerList 	= new ArrayList<RelationGroupCustomerBean>();
		int								seq							= 0;
		
		try{				
			hql					= "select * from relationgroupcustomer where cusGroupStatus = 'A' order by cusGroupCode asc";
			
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("cusGroupCode"		, new StringType());
			query.addScalar("cusGroupName"		, new StringType());
			query.addScalar("groupSalePrice"	, new StringType());
			query.addScalar("cusGroupStatus"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new RelationGroupCustomerBean();
				
				logger.info("cusGroupCode 	:: " + row[0]);
				logger.info("cusGroupName 	:: " + row[1]);
				logger.info("groupSalePrice :: " + row[2]);
				logger.info("cusGroupStatus :: " + row[3]);
				logger.info("seq 			:: " + seq);
				
				bean.setCusGroupCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setCusGroupName			(EnjoyUtils.nullToStr(row[1]));
				bean.setGroupSalePrice			(EnjoyUtils.nullToStr(row[2]));
				bean.setCusGroupStatus			(EnjoyUtils.nullToStr(row[3]));
				bean.setSeq						(EnjoyUtils.nullToStr(seq));
				
				relationGroupCustomerList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return relationGroupCustomerList;
		
	}

	
	public void updateRelationGroupCustomer(Session session, RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		logger.info("[updateRelationGroupCustomer][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Relationgroupcustomer set cusGroupName 	= :cusGroupName"
															+ ", groupSalePrice	= :groupSalePrice"
										+ " where cusGroupCode = :cusGroupCode";
			
			logger.info("[updateRelationGroupCustomer] cusGroupName :: " + relationGroupCustomerBean.getCusGroupName());
			logger.info("[updateRelationGroupCustomer] groupSalePrice :: " + EnjoyUtils.parseInt(relationGroupCustomerBean.getGroupSalePrice()));
			logger.info("[updateRelationGroupCustomer] cusGroupCode :: " + EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			
			query = session.createQuery(hql);
			query.setParameter("cusGroupName"			, relationGroupCustomerBean.getCusGroupName());
			query.setParameter("groupSalePrice"			, EnjoyUtils.parseInt(relationGroupCustomerBean.getGroupSalePrice()));
			query.setParameter("cusGroupCode"			, EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("Error updateRelationGroupCustomer");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateRelationGroupCustomer][End]");
		}
	}
	
	public void rejectRelationGroupCustomer(Session session, RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		logger.info("[rejectRelationGroupCustomer][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Relationgroupcustomer set cusGroupStatus 	= 'R'"
										+ " where cusGroupCode = :cusGroupCode";
			
			query = session.createQuery(hql);
			query.setParameter("cusGroupCode"			, EnjoyUtils.parseInt(relationGroupCustomerBean.getCusGroupCode()));
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("Error rejectRelationGroupCustomer");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[rejectRelationGroupCustomer][End]");
		}
	}
	
	public void insertRelationGroupCustomer(Session session, RelationGroupCustomerBean 	relationGroupCustomerBean) throws EnjoyException{
		logger.info("[insertRelationGroupCustomer][Begin]");
		
		Relationgroupcustomer		relationgroupcustomer		= null;
		
		try{
			
			relationgroupcustomer 	= new Relationgroupcustomer();
//			relationgroupcustomer.setCusGroupCode	(EnjoyUtils.parseInt(relationGroupCustomerBean.getSeq()));
			relationgroupcustomer.setCusGroupName	(relationGroupCustomerBean.getCusGroupName());
			relationgroupcustomer.setGroupSalePrice	(EnjoyUtils.parseInt(relationGroupCustomerBean.getGroupSalePrice()));
			relationgroupcustomer.setCusGroupStatus	("A");
			
			session.saveOrUpdate(relationgroupcustomer);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new EnjoyException("Error insertRelationGroupCustomer");
		}finally{
			relationgroupcustomer 	= null;
			logger.info("[insertRelationGroupCustomer][End]");
		}
	}
	public int getlastId(Session session) throws EnjoyException{
		logger.info("[getlastId][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			
			hql				= "select max(cusGroupCode) lastId from relationgroupcustomer";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0)==null?0:list.get(0);
			}
			
			result++;
			
			logger.info("[getlastId] result 			:: " + result);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			hql									= null;
			list								= null;
			query 								= null;
			logger.info("[genId][End]");
		}
		
		return result;
	}	
}
