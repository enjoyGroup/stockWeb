
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ReciveOrdeDetailBean;
import th.go.stock.app.enjoy.bean.ReciveOrderMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.model.Reciveordedetail;
import th.go.stock.app.enjoy.model.ReciveordedetailPK;
import th.go.stock.app.enjoy.model.Reciveordermaster;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ReciveStockDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ReciveStockDao.class);
	
	
	public List<ReciveOrderMasterBean> searchByCriteria(	Session 				session, 
															ReciveOrderMasterBean 	reciveOrderMasterBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ReciveOrderMasterBean		bean					= null;
		List<ReciveOrderMasterBean> reciveOrderMasterList 	= new ArrayList<ReciveOrderMasterBean>();
		String 						reciveDateFrom			= null;
		String 						reciveDateTo			= null;
		
		try{	
			
			reciveDateFrom 	= EnjoyUtils.dateThaiToDb(reciveOrderMasterBean.getReciveDateFrom());
			reciveDateTo	= EnjoyUtils.dateThaiToDb(reciveOrderMasterBean.getReciveDateTo());
			
			hql					= "select a.*, CONCAT(b.username, ' ', b.userSurname) as usrName, c.reciveStatusName"
								+ "	from reciveordermaster a, userdetails b, refreciveorderstatus c"
								+ "	where a.userUniqueId = b.userUniqueId"
								+ " 	and a.reciveStatus = c.reciveStatusCode";
			
			if(!reciveOrderMasterBean.getReciveNo().equals("***")){
				if(reciveOrderMasterBean.getReciveNo().equals("")){
					hql += " and (a.reciveNo is null or a.reciveNo = '')";
				}else{
					hql += " and a.reciveNo like ('" + reciveOrderMasterBean.getReciveNo() + "%')";
				}
			}
			
			if(!reciveDateFrom.equals("")){
				hql += " and a.reciveDate >= STR_TO_DATE('" + reciveDateFrom + "', '%Y%m%d')";
			}
			
			if(!reciveDateTo.equals("")){
				hql += " and a.reciveDate <= STR_TO_DATE('" + reciveDateTo + "', '%Y%m%d')";
			}
			
			if(!reciveOrderMasterBean.getReciveStatus().equals("")){
				hql += " and a.reciveStatus = '" + reciveOrderMasterBean.getReciveStatus() + "'";
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("reciveNo"			, new StringType());
			query.addScalar("reciveDate"		, new StringType());
			query.addScalar("usrName"			, new StringType());
			query.addScalar("reciveStatus"		, new StringType());
			query.addScalar("reciveStatusName"	, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ReciveOrderMasterBean();
				
				logger.info("reciveNo 			:: " + row[0]);
				logger.info("reciveDate 		:: " + row[1]);
				logger.info("usrName 			:: " + row[2]);
				logger.info("reciveStatus 		:: " + row[3]);
				logger.info("reciveStatusName 	:: " + row[4]);
				
				bean.setReciveNo			(EnjoyUtils.nullToStr(row[0]));
				bean.setReciveDate			(EnjoyUtils.dateToThaiDisplay(row[1]));
				bean.setUsrName				(EnjoyUtils.nullToStr(row[2]));
				bean.setReciveStatus		(EnjoyUtils.nullToStr(row[3]));
				bean.setReciveStatusDesc	(EnjoyUtils.nullToStr(row[4]));
				
				reciveOrderMasterList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return reciveOrderMasterList;
		
	}

	
	public ReciveOrderMasterBean getReciveOrderMaster(	Session 				session, 
														ReciveOrderMasterBean 	reciveOrderMasterBean) throws EnjoyException{
		logger.info("[getReciveOrderMaster][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ReciveOrderMasterBean		bean					= null;
		
		try{		
			hql		= "select *"
					+ "	from reciveordermaster"
					+ "	where reciveNo 	= '" + reciveOrderMasterBean.getReciveNo() + "'";
			
			logger.info("[getReciveOrderMaster] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("reciveNo"			, new StringType());
			query.addScalar("reciveDate"		, new StringType());
			query.addScalar("reciveType"		, new StringType());
			query.addScalar("creditDay"			, new StringType());
			query.addScalar("creditExpire"		, new StringType());
			query.addScalar("vendorCode"		, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("billNo"			, new StringType());
			query.addScalar("priceType"			, new StringType());
			query.addScalar("reciveStatus"		, new StringType());
			query.addScalar("userUniqueId"		, new StringType());
			query.addScalar("reciveAmount"		, new StringType());
			query.addScalar("reciveDiscount"	, new StringType());
			query.addScalar("reciveVat"			, new StringType());
			query.addScalar("reciveTotal"		, new StringType());
			query.addScalar("tin"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getReciveOrderMaster] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new ReciveOrderMasterBean();
					
					logger.info("reciveNo 				:: " + row[0]);
					logger.info("reciveDate 			:: " + row[1]);
					logger.info("reciveType 			:: " + row[2]);
					logger.info("creditDay 				:: " + row[3]);
					logger.info("creditExpire 			:: " + row[4]);
					logger.info("vendorCode 			:: " + row[5]);
					logger.info("branchName 			:: " + row[6]);
					logger.info("billNo 				:: " + row[7]);
					logger.info("priceType 				:: " + row[8]);
					logger.info("reciveStatus 			:: " + row[9]);
					logger.info("userUniqueId 			:: " + row[10]);
					logger.info("reciveAmount 			:: " + row[11]);
					logger.info("reciveDiscount 		:: " + row[12]);
					logger.info("reciveVat 				:: " + row[13]);
					logger.info("reciveTotal 			:: " + row[14]);
					logger.info("tin 					:: " + row[15]);
					
					bean.setReciveNo				(EnjoyUtils.nullToStr(row[0]));
					bean.setReciveDate				(EnjoyUtils.dateToThaiDisplay(row[1]));
					bean.setReciveType				(EnjoyUtils.nullToStr(row[2]));
					bean.setCreditDay				(EnjoyUtils.nullToStr(row[3]));
					bean.setCreditExpire			(EnjoyUtils.dateToThaiDisplay(row[4]));
					bean.setVendorCode				(EnjoyUtils.nullToStr(row[5]));
					bean.setBranchName				(EnjoyUtils.nullToStr(row[6]));
					bean.setBillNo					(EnjoyUtils.nullToStr(row[7]));
					bean.setPriceType				(EnjoyUtils.nullToStr(row[8]));
					bean.setReciveStatus			(EnjoyUtils.nullToStr(row[9]));
					bean.setUserUniqueId			(EnjoyUtils.nullToStr(row[10]));
					bean.setReciveAmount			(EnjoyUtils.convertFloatToDisplay(row[11], 2));
					bean.setReciveDiscount			(EnjoyUtils.convertFloatToDisplay(row[12], 2));
					bean.setReciveVat				(EnjoyUtils.convertFloatToDisplay(row[13], 2));
					bean.setReciveTotal				(EnjoyUtils.convertFloatToDisplay(row[14], 2));
					bean.setTin						(EnjoyUtils.nullToStr(row[15]));
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getReciveOrderMaster] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrderMaster");
		}finally{
			hql						= null;
			logger.info("[getReciveOrderMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertReciveordermaster(Session session, ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		logger.info("[insertReciveordermaster][Begin]");
		
		Reciveordermaster	reciveordermaster						= null;//genReciveNo
		
		try{
			
			reciveordermaster = new Reciveordermaster();
			
			reciveordermaster.setReciveNo				(reciveOrderMasterBean.getReciveNo());
			reciveordermaster.setReciveDate				(reciveOrderMasterBean.getReciveDate());
			reciveordermaster.setReciveType				(reciveOrderMasterBean.getReciveType());
			reciveordermaster.setCreditDay				(EnjoyUtils.parseInt(reciveOrderMasterBean.getCreditDay()));
			reciveordermaster.setCreditExpire			(reciveOrderMasterBean.getCreditExpire());
			reciveordermaster.setVendorCode				(EnjoyUtils.parseInt(reciveOrderMasterBean.getVendorCode()));
			reciveordermaster.setBranchName				(reciveOrderMasterBean.getBranchName());
			reciveordermaster.setBillNo					(reciveOrderMasterBean.getBillNo());
			reciveordermaster.setPriceType				(reciveOrderMasterBean.getPriceType());
			reciveordermaster.setReciveStatus			(reciveOrderMasterBean.getReciveStatus());
			reciveordermaster.setUserUniqueId			(EnjoyUtils.parseInt(reciveOrderMasterBean.getUserUniqueId()));
			reciveordermaster.setReciveAmount			(EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveAmount()));
			reciveordermaster.setReciveDiscount			(EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveDiscount()));
			reciveordermaster.setReciveVat				(EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveVat()));
			reciveordermaster.setReciveTotal			(EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveTotal()));
			reciveordermaster.setTin					(reciveOrderMasterBean.getTin());
			
			session.saveOrUpdate(reciveordermaster);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertReciveordermaster");
		}finally{
			
			reciveordermaster = null;
			logger.info("[insertReciveordermaster][End]");
		}
	}
	
	public void updateReciveOrderMaster(Session session, ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		logger.info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Reciveordermaster set reciveDate 	= :reciveDate"
												+ ", reciveType				= :reciveType"
												+ ", creditDay				= :creditDay"
												+ ", creditExpire			= :creditExpire"
												+ ", vendorCode				= :vendorCode"
												+ ", branchName 			= :branchName"
												+ ", billNo					= :billNo"
												+ ", priceType				= :priceType"
												+ ", reciveStatus 			= :reciveStatus"
												+ ", userUniqueId 			= :userUniqueId"
												+ ", reciveAmount 			= :reciveAmount"
												+ ", reciveDiscount 		= :reciveDiscount"
												+ ", reciveVat 				= :reciveVat"
												+ ", reciveTotal 			= :reciveTotal"
												+ ", tin 					= :tin"
										+ " where reciveNo = :reciveNo";
			
			query = session.createQuery(hql);
			query.setParameter("reciveDate"			, reciveOrderMasterBean.getReciveDate());
			query.setParameter("reciveType"			, reciveOrderMasterBean.getReciveType());
			query.setParameter("creditDay"			, EnjoyUtils.parseInt(reciveOrderMasterBean.getCreditDay()));
			query.setParameter("creditExpire"		, reciveOrderMasterBean.getCreditExpire());
			query.setParameter("vendorCode"			, EnjoyUtils.parseInt(reciveOrderMasterBean.getVendorCode()));
			query.setParameter("branchName"			, reciveOrderMasterBean.getBranchName());
			query.setParameter("billNo"				, reciveOrderMasterBean.getBillNo());
			query.setParameter("priceType"			, reciveOrderMasterBean.getPriceType());
			query.setParameter("reciveStatus"		, reciveOrderMasterBean.getReciveStatus());
			query.setParameter("userUniqueId"		, EnjoyUtils.parseInt(reciveOrderMasterBean.getUserUniqueId()));
			query.setParameter("reciveAmount"		, EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveAmount()));
			query.setParameter("reciveDiscount"		, EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveDiscount()));
			query.setParameter("reciveVat"			, EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveVat()));
			query.setParameter("reciveTotal"		, EnjoyUtils.parseBigDecimal(reciveOrderMasterBean.getReciveTotal()));
			query.setParameter("tin"				, reciveOrderMasterBean.getTin());
			query.setParameter("reciveNo"			, reciveOrderMasterBean.getReciveNo());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateReciveOrderMaster][End]");
		}
	}
	
	public void updateReciveOrderMasterSpecial(Session session, ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		logger.info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Reciveordermaster set reciveDate 	= :reciveDate"
												+ ", reciveStatus 			= :reciveStatus"
												+ ", userUniqueId 			= :userUniqueId"
										+ " where reciveNo = :reciveNo";
			
			query = session.createQuery(hql);
			query.setParameter("reciveDate"			, reciveOrderMasterBean.getReciveDate());
			query.setParameter("reciveStatus"		, reciveOrderMasterBean.getReciveStatus());
			query.setParameter("userUniqueId"		, EnjoyUtils.parseInt(reciveOrderMasterBean.getUserUniqueId()));
			query.setParameter("reciveNo"			, reciveOrderMasterBean.getReciveNo());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateReciveOrderMaster][End]");
		}
	}

	public List<ReciveOrdeDetailBean> getReciveOrdeDetailList( Session 					session
															  ,ReciveOrdeDetailBean 	reciveOrdeDetailBean) throws EnjoyException{
		logger.info("[getReciveOrdeDetailList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		ReciveOrdeDetailBean		bean						= null;
		List<ReciveOrdeDetailBean> 	reciveOrdeDetailList 		= new ArrayList<ReciveOrdeDetailBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select a.reciveNo"
									+ "	, a.seq"
									+ "	, a.productCode"
									+ "	, b.productName"
									+ "	, e.quantity as inventory"
									+ "	, a.quantity"
									+ "	, b.unitCode"
									+ "	, c.unitName"
									+ "	, a.price"
									+ "	, a.discountRate"
									+ "	, a.costPrice"
								+ "	from reciveordedetail a"
									+ "	inner JOIN productmaster b on b.productCode 	= a.productCode"
									+ "	inner JOIN unittype c on  b.unitCode 	= c.unitCode"
									+ "	LEFT JOIN reciveordermaster d on a.reciveNo = d.reciveNo"
									+ "	LEFT JOIN productquantity e on a.productCode = e.productCode AND d.tin = e.tin"
								+ "	where"
									+ " a.reciveNo 	= '" + reciveOrdeDetailBean.getReciveNo() + "'"
								+ "	order by a.seq asc";
			
			logger.info("[getReciveOrdeDetailList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("reciveNo"		, new StringType());
			query.addScalar("seq"			, new StringType());
			query.addScalar("productCode"	, new StringType());
			query.addScalar("productName"	, new StringType());
			query.addScalar("inventory"		, new StringType());
			query.addScalar("quantity"		, new StringType());
			query.addScalar("unitCode"		, new StringType());
			query.addScalar("unitName"		, new StringType());
			query.addScalar("price"			, new StringType());
			query.addScalar("discountRate"	, new StringType());
			query.addScalar("costPrice"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getReciveOrdeDetailList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ReciveOrdeDetailBean();
				
				logger.info("reciveNo 		:: " + row[0]);
				logger.info("seqDb 			:: " + row[1]);
				logger.info("productCode 	:: " + row[2]);
				logger.info("productName 	:: " + row[3]);
				logger.info("inventory 		:: " + row[4]);
				logger.info("quantity 		:: " + row[5]);
				logger.info("unitCode 		:: " + row[6]);
				logger.info("unitName 		:: " + row[7]);
				logger.info("price 			:: " + row[8]);
				logger.info("discountRate 	:: " + row[9]);
				logger.info("costPrice 		:: " + row[10]);
				logger.info("seq 			:: " + seq);
				
				bean.setReciveNo			(EnjoyUtils.nullToStr(row[0]));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row[1]));
				bean.setProductCode			(EnjoyUtils.nullToStr(row[2]));
				bean.setProductName			(EnjoyUtils.nullToStr(row[3]));
				bean.setInventory			(EnjoyUtils.convertFloatToDisplay(row[4], 2));
				bean.setQuantity			(EnjoyUtils.convertFloatToDisplay(row[5], 2));
				bean.setUnitCode			(EnjoyUtils.nullToStr(row[6]));
				bean.setUnitName			(EnjoyUtils.nullToStr(row[7]));
				bean.setPrice				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
				bean.setDiscountRate		(EnjoyUtils.convertFloatToDisplay(row[9], 2));
				bean.setCostPrice			(EnjoyUtils.convertFloatToDisplay(row[10], 2));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				reciveOrdeDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getReciveOrdeDetailList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrdeDetailList");
		}finally{
			hql						= null;
			logger.info("[getReciveOrdeDetailList][End]");
		}
		
		return reciveOrdeDetailList;
		
	}
	
	public void insertReciveOrdeDetail(Session session, ReciveOrdeDetailBean 		reciveOrdeDetailBean) throws EnjoyException{
		logger.info("[insertReciveOrdeDetail][Begin]");
		
		Reciveordedetail		reciveordedetail		= null;
		ReciveordedetailPK 		id 					= null;
		
		try{
			
			reciveordedetail 	= new Reciveordedetail();
			id 					= new ReciveordedetailPK();
			
			id.setReciveNo	(reciveOrdeDetailBean.getReciveNo());
			id.setSeq		(EnjoyUtils.parseInt(reciveOrdeDetailBean.getSeqDb()));
			
			reciveordedetail.setId					(id);
			reciveordedetail.setProductCode			(reciveOrdeDetailBean.getProductCode());
			reciveordedetail.setQuantity			(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getQuantity()));
			reciveordedetail.setPrice				(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getPrice()));
			reciveordedetail.setDiscountRate		(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getDiscountRate()));
			reciveordedetail.setCostPrice			(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getCostPrice()));
			
			session.saveOrUpdate(reciveordedetail);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertReciveOrdeDetail");
		}finally{
			id 					= null;
			reciveordedetail 	= null;
			logger.info("[insertReciveOrdeDetail][End]");
		}
	}
	
	public void deleteReciveordedetail(Session session, String reciveNo) throws EnjoyException{
		logger.info("[deleteReciveordedetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Reciveordedetail t"
							+ " where t.id.reciveNo	 = '" + reciveNo + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteReciveordedetail][End]");
		}
	}
	
//	public int genId(Session session) throws EnjoyException{
//		logger.info("[genId][Begin]");
//		
//		String							hql									= null;
//		List<Integer>			 		list								= null;
//		SQLQuery 						query 								= null;
//		int 							result								= 0;
//		
//		
//		try{
//			
//			hql				= "select max(reciveNo) lastId from reciveordermaster";
//			query			= session.createSQLQuery(hql);
//			
//			query.addScalar("lastId"			, new IntegerType());
//			
//			list		 	= query.list();
//			
//			if(list!=null && list.size() > 0){
//				result = list.get(0)==null?0:list.get(0);
//			}
//			
//			logger.info("[genId] result 			:: " + result);
//			
//			result++;
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.info(e.getMessage());
//			throw new EnjoyException(e.getMessage());
//		}finally{
//			
//			hql									= null;
//			list								= null;
//			query 								= null;
//			logger.info("[genId][End]");
//		}
//		
//		return result;
//	}
	
	/*ดึงสถานะมาอยู่ใน Combo*/
	public List<ComboBean> getRefReciveOrderStatusCombo() throws EnjoyException{
		logger.info("[getRefReciveOrderStatusCombo][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		ComboBean					comboBean				= null;
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{
			sessionFactory 	= HibernateUtil.getSessionFactory();
			session 		= sessionFactory.openSession();
			hql				= "select * from refreciveorderstatus order by reciveStatusCode asc";
			query			= session.createSQLQuery(hql);
			query.addScalar("reciveStatusCode"		, new StringType());
			query.addScalar("reciveStatusName"		, new StringType());
			
			list		 	= query.list();
			
//			comboList.add(new ComboBean("", "กรุณาระบุ"));
			for(Object[] row:list){
				comboBean = new ComboBean();
				
				logger.info("[getRefReciveOrderStatusCombo] reciveStatusCode :: " + row[0]);
				logger.info("[getRefReciveOrderStatusCombo] reciveStatusName :: " + row[1]);
				
				comboBean.setCode(EnjoyUtils.nullToStr(row[0]));
				comboBean.setDesc(EnjoyUtils.nullToStr(row[1]));
				
				comboList.add(comboBean);
			}
			
			
		}catch(Exception e){
			logger.info("[getRefReciveOrderStatusCombo] " + e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			session.close();
			
			hql				= null;
			sessionFactory	= null;
			session			= null;
			logger.info("[getRefReciveOrderStatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	public String genReciveNo() throws EnjoyException{
		logger.info("[genReciveNo][Begin]");
		
		String				hql						= null;
		List<Integer>		list					= null;
		SQLQuery 			query 					= null;
		SessionFactory 		sessionFactory			= null;
		Session 			session					= null;
		String				newId					= "";
		String				codeDisplay				= null;
		RefconstantcodeDao	refconstantcodeDao		= null;
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			refconstantcodeDao 	= new RefconstantcodeDao();
			codeDisplay			= refconstantcodeDao.getCodeDisplay("2");
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(reciveNo, '-', -1)) + 1) AS newId"
							+ "	FROM reciveordermaster"
							+ "	WHERE"
							+ "		SUBSTRING_INDEX(reciveNo, '-', 1) = '" + codeDisplay + "'";
			query			= session.createSQLQuery(hql);
			
			
			query.addScalar("newId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingReciveNo(), list.get(0));
			}else{
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingReciveNo(), 1);
			}
			
			logger.info("[genReciveNo] newId 			:: " + newId);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			
			session.close();
			sessionFactory	= null;
			session			= null;
			hql				= null;
			list			= null;
			query 			= null;
			logger.info("[genReciveNo][End]");
		}
		
		return newId;
	}

	
}
