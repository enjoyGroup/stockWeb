
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Invoicecashdetail;
import th.go.stock.app.enjoy.model.InvoicecashdetailPK;
import th.go.stock.app.enjoy.model.Invoicecashmaster;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class InvoiceCashDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(InvoiceCashDao.class);
	
	
	public List<InvoiceCashMasterBean> searchByCriteria(Session 				session, 
														InvoiceCashMasterBean 	invoiceCashMasterBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		InvoiceCashMasterBean		bean					= null;
		List<InvoiceCashMasterBean> invoiceCashMasterList 	= new ArrayList<InvoiceCashMasterBean>();
		String 						invoiceDateForm			= null;
		String 						invoiceDateTo			= null;
		String						invoiceTypeDesc			= "";
		String						invoiceStatus			= "";
		String						invoiceStatusDesc		= "";
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCashMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCashMasterBean.getInvoiceDateTo());
			
			hql					= "select a.*, CONCAT(b.cusName, ' ', b.cusSurname) as cusFullName"
									+ " from invoicecashmaster a LEFT JOIN customer b"
									+ " 	ON a.cusCode = b.cusCode"
									+ "	where 1=1";
			
			if(!invoiceCashMasterBean.getInvoiceCode().equals("***")){
				if(invoiceCashMasterBean.getInvoiceCode().equals("")){
					hql += " and (a.invoiceCode is null or a.invoiceCode = '')";
				}else{
					hql += " and a.invoiceCode like ('" + invoiceCashMasterBean.getInvoiceCode() + "%')";
				}
			}
			
			if(!invoiceDateForm.equals("")){
				hql += " and a.invoiceDate >= STR_TO_DATE('" + invoiceDateForm + "', '%Y%m%d')";
			}
			
			if(!invoiceDateTo.equals("")){
				hql += " and a.invoiceDate <= STR_TO_DATE('" + invoiceDateTo + "', '%Y%m%d')";
			}
			
			if(!invoiceCashMasterBean.getCusFullName().equals("***")){
				if(invoiceCashMasterBean.getCusFullName().equals("")){
					hql += " and CONCAT(b.cusName, ' ', b.cusSurname) = ''";
				}else{
					hql += " and CONCAT(b.cusName, ' ', b.cusSurname) like ('" + invoiceCashMasterBean.getCusFullName() + "%')";
				}
			}
			
			if(!invoiceCashMasterBean.getInvoiceStatus().equals("")){
				hql += " and a.invoiceStatus = '" + invoiceCashMasterBean.getInvoiceStatus() + "'";
			}
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("invoiceCode"		, new StringType());
			query.addScalar("invoiceType"		, new StringType());
			query.addScalar("cusFullName"		, new StringType());
			query.addScalar("invoiceDate"		, new StringType());
			query.addScalar("invoiceTotal"		, new StringType());
			query.addScalar("invoiceStatus"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new InvoiceCashMasterBean();
				
				logger.info("invoiceCode 			:: " + row[0]);
				logger.info("invoiceType 			:: " + row[1]);
				logger.info("cusFullName 			:: " + row[2]);
				logger.info("invoiceDate 			:: " + row[3]);
				logger.info("invoiceTotal 			:: " + row[4]);
				logger.info("invoiceStatus 			:: " + row[5]);
				
				invoiceTypeDesc = EnjoyUtils.nullToStr(row[1]).equals("V")?"มี VAT":"ไม่มี VAT";
				invoiceStatus	= EnjoyUtils.nullToStr(row[5]);
				
				if(invoiceStatus.equals("A")){
					invoiceStatusDesc = "ใช้งานอยู่";
				}else if(invoiceStatus.equals("C")){
					invoiceStatusDesc = "ยกเลิกการใช้งาน";
				}else if(invoiceStatus.equals("W")){
					invoiceStatusDesc = "รอสร้างใบ Invoice";
				}
				
				
				bean.setInvoiceCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setInvoiceType			(EnjoyUtils.nullToStr(row[1]));
				bean.setInvoiceTypeDesc		(invoiceTypeDesc);
				bean.setCusFullName			(EnjoyUtils.nullToStr(row[2]));
				bean.setInvoiceDate			(EnjoyUtils.dateToThaiDisplay(row[3]));
				bean.setInvoiceTotal		(EnjoyUtils.convertFloatToDisplay(row[4], 2));
				bean.setInvoiceStatus		(invoiceStatus);
				bean.setInvoiceStatusDesc	(invoiceStatusDesc);
				
				invoiceCashMasterList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return invoiceCashMasterList;
		
	}
	
	public InvoiceCashMasterBean getInvoiceCashMaster(	Session 				session, 
														InvoiceCashMasterBean 	invoiceCashMasterBean) throws EnjoyException{
		logger.info("[getInvoiceCashMaster][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		InvoiceCashMasterBean		bean					= null;
		
		try{		
			hql		= "select *"
					+ "	from invoicecashmaster"
					+ "	where invoiceCode 	= '" + invoiceCashMasterBean.getInvoiceCode() + "'";
			
			logger.info("[getReciveOrderMaster] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("invoiceCode"		, new StringType());
			query.addScalar("invoiceDate"		, new StringType());
			query.addScalar("invoiceType"		, new StringType());
			query.addScalar("cusCode"			, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("saleUniqueId"		, new StringType());
			query.addScalar("saleCommission"	, new StringType());
			query.addScalar("invoicePrice"		, new StringType());
			query.addScalar("invoicediscount"	, new StringType());
			query.addScalar("invoiceDeposit"	, new StringType());
			query.addScalar("invoiceVat"		, new StringType());
			query.addScalar("invoiceTotal"		, new StringType());
			query.addScalar("userUniqueId"		, new StringType());
			query.addScalar("invoiceCredit"		, new StringType());
			query.addScalar("invoiceStatus"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getInvoiceCashMaster] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new InvoiceCashMasterBean();
					
					logger.info("invoiceCode 				:: " + row[0]);
					logger.info("invoiceDate 				:: " + row[1]);
					logger.info("invoiceType 				:: " + row[2]);
					logger.info("cusCode					:: " + row[3]);
					logger.info("branchName 				:: " + row[4]);
					logger.info("saleUniqueId 				:: " + row[5]);
					logger.info("saleCommission 			:: " + row[6]);
					logger.info("invoicePrice 				:: " + row[7]);
					logger.info("invoicediscount 			:: " + row[8]);
					logger.info("invoiceDeposit 			:: " + row[9]);
					logger.info("invoiceVat 				:: " + row[10]);
					logger.info("invoiceTotal 				:: " + row[11]);
					logger.info("userUniqueId 				:: " + row[12]);
					logger.info("invoiceCredit 				:: " + row[13]);
					logger.info("invoiceStatus 				:: " + row[14]);
					
					bean.setInvoiceCode				(EnjoyUtils.nullToStr(row[0]));
					bean.setInvoiceDate				(EnjoyUtils.dateToThaiDisplay(row[1]));
					bean.setInvoiceType				(EnjoyUtils.nullToStr(row[2]));
					bean.setCusCode					(EnjoyUtils.nullToStr(row[3]));
					bean.setBranchName				(EnjoyUtils.nullToStr(row[4]));
					bean.setSaleUniqueId			(EnjoyUtils.nullToStr(row[5]));
					bean.setSaleCommission			(EnjoyUtils.convertFloatToDisplay(row[6], 2));
					bean.setInvoicePrice			(EnjoyUtils.convertFloatToDisplay(row[7], 2));
					bean.setInvoicediscount			(EnjoyUtils.convertFloatToDisplay(row[8], 2));
					bean.setInvoiceDeposit			(EnjoyUtils.convertFloatToDisplay(row[9], 2));
					bean.setInvoiceVat				(EnjoyUtils.convertFloatToDisplay(row[10], 2));
					bean.setInvoiceTotal			(EnjoyUtils.convertFloatToDisplay(row[11], 2));
					bean.setUserUniqueId			(EnjoyUtils.nullToStr(row[12]));
					bean.setInvoiceCredit			(EnjoyUtils.nullToStr(row[13]));
					bean.setInvoiceStatus			(EnjoyUtils.nullToStr(row[14]));
					
				}	
			}
			
			
			
		}catch(Exception e){
			logger.info("[getInvoiceCashMaster] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCashMaster");
		}finally{
			hql						= null;
			logger.info("[getInvoiceCashMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertInvoiceCashMaster(Session session, InvoiceCashMasterBean 		invoiceCashMasterBean) throws EnjoyException{
		logger.info("[insertInvoiceCashMaster][Begin]");
		
		Invoicecashmaster	invoiceCashMaster						= null;
		
		try{
			
			invoiceCashMaster = new Invoicecashmaster();
			
			invoiceCashMaster.setInvoiceCode			(invoiceCashMasterBean.getInvoiceCode());
			invoiceCashMaster.setInvoiceDate			(invoiceCashMasterBean.getInvoiceDate());
			invoiceCashMaster.setInvoiceType			(invoiceCashMasterBean.getInvoiceType());
			invoiceCashMaster.setCusCode				(invoiceCashMasterBean.getCusCode());
			invoiceCashMaster.setBranchName				(invoiceCashMasterBean.getBranchName());
			invoiceCashMaster.setSaleUniqueId			(EnjoyUtils.parseInt(invoiceCashMasterBean.getSaleUniqueId()));
			invoiceCashMaster.setSaleCommission			(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getSaleCommission()));
			invoiceCashMaster.setInvoicePrice			(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoicePrice()));
			invoiceCashMaster.setInvoicediscount		(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoicediscount()));
			invoiceCashMaster.setInvoiceDeposit			(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceDeposit()));
			invoiceCashMaster.setInvoiceVat				(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceVat()));
			invoiceCashMaster.setInvoiceTotal			(EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceTotal()));
			invoiceCashMaster.setUserUniqueId			(EnjoyUtils.parseInt(invoiceCashMasterBean.getUserUniqueId()));
			invoiceCashMaster.setInvoiceCredit			(invoiceCashMasterBean.getInvoiceCredit());
			invoiceCashMaster.setInvoiceStatus			(invoiceCashMasterBean.getInvoiceStatus());
			
			session.saveOrUpdate(invoiceCashMaster);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertInvoiceCashMaster");
		}finally{
			
			invoiceCashMaster = null;
			logger.info("[insertInvoiceCashMaster][End]");
		}
	}
	
	public void updateInvoiceCashMaster(Session session, InvoiceCashMasterBean 		invoiceCashMasterBean) throws EnjoyException{
		logger.info("[updateInvoiceCashMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecashmaster set invoiceDate 	= :invoiceDate"
												+ ", invoiceType			= :invoiceType"
												+ ", cusCode				= :cusCode"
												+ ", branchName				= :branchName"
												+ ", saleUniqueId			= :saleUniqueId"
												+ ", saleCommission 		= :saleCommission"
												+ ", invoicePrice			= :invoicePrice"
												+ ", invoicediscount		= :invoicediscount"
												+ ", invoiceDeposit 		= :invoiceDeposit"
												+ ", invoiceVat 			= :invoiceVat"
												+ ", invoiceTotal 			= :invoiceTotal"
												+ ", userUniqueId 			= :userUniqueId"
												+ ", invoiceCredit 			= :invoiceCredit"
												+ ", invoiceStatus 			= :invoiceStatus"
										+ " where invoiceCode = :invoiceCode";
			
			query = session.createQuery(hql);
			query.setParameter("invoiceDate"		, invoiceCashMasterBean.getInvoiceDate());
			query.setParameter("invoiceType"		, invoiceCashMasterBean.getInvoiceType());
			query.setParameter("cusCode"			, invoiceCashMasterBean.getCusCode());
			query.setParameter("branchName"			, invoiceCashMasterBean.getBranchName());
			query.setParameter("saleUniqueId"		, EnjoyUtils.parseInt(invoiceCashMasterBean.getSaleUniqueId()));
			query.setParameter("saleCommission"		, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getSaleCommission()));
			query.setParameter("invoicePrice"		, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoicePrice()));
			query.setParameter("invoicediscount"	, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoicediscount()));
			query.setParameter("invoiceDeposit"		, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceDeposit()));
			query.setParameter("invoiceVat"			, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceVat()));
			query.setParameter("invoiceTotal"		, EnjoyUtils.parseBigDecimal(invoiceCashMasterBean.getInvoiceTotal()));
			query.setParameter("userUniqueId"		, EnjoyUtils.parseInt(invoiceCashMasterBean.getUserUniqueId()));
			query.setParameter("invoiceCredit"		, invoiceCashMasterBean.getInvoiceCredit());
			query.setParameter("invoiceStatus"		, invoiceCashMasterBean.getInvoiceStatus());
			query.setParameter("invoiceCode"		, invoiceCashMasterBean.getInvoiceCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateInvoiceCashMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateInvoiceCashMaster][End]");
		}
	}
	
	public void updateInvoiceCashMasterStatus(Session session, InvoiceCashMasterBean 		invoiceCashMasterBean) throws EnjoyException{
		logger.info("[updateInvoiceCashMasterStatus][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecashmaster set invoiceStatus 	= :invoiceStatus"
										+ " where invoiceCode = :invoiceCode";
			
			query = session.createQuery(hql);
			query.setParameter("invoiceStatus"		, invoiceCashMasterBean.getInvoiceStatus());
			query.setParameter("invoiceCode"		, invoiceCashMasterBean.getInvoiceCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateInvoiceCashMasterStatus");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateInvoiceCashMasterStatus][End]");
		}
	}
	
	public List<InvoiceCashDetailBean> getInvoiceCashDetailList( Session 				session
															  	,InvoiceCashDetailBean 	invoiceCashDetailBean) throws EnjoyException{
		logger.info("[getInvoiceCashDetailList][Begin]");
		
		String						hql							= null;
		SQLQuery 					query 						= null;
		List<Object[]>				list						= null;
		InvoiceCashDetailBean		bean						= null;
		List<InvoiceCashDetailBean> invoiceCashDetailList 		= new ArrayList<InvoiceCashDetailBean>();
		int							seq							= 0;
		
		try{	
			hql					= "select a.*"
									+ "	, b.productName"
									+ "	, b.quantity as inventory"
									+ "	, b.unitCode"
									+ "	, c.unitName"
									+ "	from invoicecashdetail a, productmaster b, unittype c"
									+ "	where b.productCode 	= a.productCode"
									+ "		and c.unitCode 		= b.unitCode"
									+ "		and a.invoiceCode 	= '" + invoiceCashDetailBean.getInvoiceCode() + "'"
									+ "	order by a.seq asc";
			
			logger.info("[getInvoiceCashDetailList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("invoiceCode"	, new StringType());
			query.addScalar("seq"			, new StringType());
			query.addScalar("productCode"	, new StringType());
			query.addScalar("productName"	, new StringType());
			query.addScalar("inventory"		, new StringType());
			query.addScalar("quantity"		, new StringType());
			query.addScalar("pricePerUnit"	, new StringType());
			query.addScalar("discount"		, new StringType());
			query.addScalar("price"			, new StringType());
			query.addScalar("unitCode"		, new StringType());
			query.addScalar("unitName"		, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getInvoiceCashDetailList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new InvoiceCashDetailBean();
				
				logger.info("invoiceCode 	:: " + row[0]);
				logger.info("seqDb 			:: " + row[1]);
				logger.info("productCode 	:: " + row[2]);
				logger.info("productName 	:: " + row[3]);
				logger.info("inventory 		:: " + row[4]);
				logger.info("quantity 		:: " + row[5]);
				logger.info("pricePerUnit 	:: " + row[6]);
				logger.info("discount 		:: " + row[7]);
				logger.info("price 			:: " + row[8]);
				logger.info("unitCode 		:: " + row[9]);
				logger.info("unitName 		:: " + row[10]);
				logger.info("seq 			:: " + seq);
				
				bean.setInvoiceCode			(EnjoyUtils.nullToStr(row[0]));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row[1]));
				bean.setProductCode			(EnjoyUtils.nullToStr(row[2]));
				bean.setProductName			(EnjoyUtils.nullToStr(row[3]));
				bean.setInventory			(EnjoyUtils.convertFloatToDisplay(row[4], 2));
				bean.setQuantity			(EnjoyUtils.convertFloatToDisplay(row[5], 2));
				bean.setPricePerUnit		(EnjoyUtils.convertFloatToDisplay(row[6], 2));
				bean.setDiscount			(EnjoyUtils.convertFloatToDisplay(row[7], 2));
				bean.setPrice				(EnjoyUtils.convertFloatToDisplay(row[8], 2));
				bean.setUnitCode			(EnjoyUtils.nullToStr(row[9]));
				bean.setUnitName			(EnjoyUtils.nullToStr(row[10]));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				invoiceCashDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getInvoiceCashDetailList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCashDetailList");
		}finally{
			hql						= null;
			logger.info("[getInvoiceCashDetailList][End]");
		}
		
		return invoiceCashDetailList;
		
	}
	
	public void insertInvoiceCashDetail(Session session, InvoiceCashDetailBean 	invoiceCashDetailBean) throws EnjoyException{
		logger.info("[insertInvoiceCashDetail][Begin]");
		
		Invoicecashdetail		invoicecashdetail		= null;
		InvoicecashdetailPK 	id 						= null;
		
		try{
			
			invoicecashdetail 	= new Invoicecashdetail();
			id 					= new InvoicecashdetailPK();
			
			id.setInvoiceCode	(invoiceCashDetailBean.getInvoiceCode());
			id.setSeq			(EnjoyUtils.parseInt(invoiceCashDetailBean.getSeqDb()));
			
			invoicecashdetail.setId					(id);
			invoicecashdetail.setProductCode		(invoiceCashDetailBean.getProductCode());
			invoicecashdetail.setQuantity			(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getQuantity()));
			invoicecashdetail.setPricePerUnit		(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getPricePerUnit()));
			invoicecashdetail.setDiscount			(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getDiscount()));
			invoicecashdetail.setPrice				(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getPrice()));
			
			session.saveOrUpdate(invoicecashdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertInvoiceCashDetail");
		}finally{
			id 					= null;
			invoicecashdetail 	= null;
			logger.info("[insertInvoiceCashDetail][End]");
		}
	}
	
	public void deleteInvoiceCashDetail(Session session, String invoiceCode) throws EnjoyException{
		logger.info("[deleteInvoiceCashDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Invoicecashdetail t"
							+ " where t.id.invoiceCode	 = '" + invoiceCode + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteInvoiceCashDetail][End]");
		}
	}
	
	public int genId(Session session) throws EnjoyException{
		logger.info("[genId][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			
			hql				= "select max(invoiceCode) lastId from invoicecashmaster";
			query			= session.createSQLQuery(hql);
			
			query.addScalar("lastId"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				result = list.get(0)==null?0:list.get(0);
			}
			
			logger.info("[genId] result 			:: " + result);
			
			result++;
			
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