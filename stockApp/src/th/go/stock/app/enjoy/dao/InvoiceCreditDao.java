
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Invoicecreditdetail;
import th.go.stock.app.enjoy.model.InvoicecreditdetailPK;
import th.go.stock.app.enjoy.model.Invoicecreditmaster;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class InvoiceCreditDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(InvoiceCreditDao.class);
	
	
	public List<InvoiceCreditMasterBean> searchByCriteria(Session 				session, 
														InvoiceCreditMasterBean 	invoiceCreditMasterBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		SQLQuery 						query 					= null;
		List<Object[]>					list					= null;
		InvoiceCreditMasterBean			bean					= null;
		List<InvoiceCreditMasterBean> 	invoicecreditmasterList = new ArrayList<InvoiceCreditMasterBean>();
		String 							invoiceDateForm			= null;
		String 							invoiceDateTo			= null;
		String							invoiceTypeDesc			= "";
		String							invoiceStatus			= "";
		String							invoiceStatusDesc		= "";
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateTo());
			
			hql					= "select a.*, CONCAT(b.cusName, ' ', b.cusSurname) as cusFullName"
									+ " from invoicecreditmaster a LEFT JOIN customer b"
									+ " 	ON a.cusCode = b.cusCode"
									+ "	where 1=1";
			
			if(!invoiceCreditMasterBean.getInvoiceCode().equals("***")){
				if(invoiceCreditMasterBean.getInvoiceCode().equals("")){
					hql += " and (a.invoiceCode is null or a.invoiceCode = '')";
				}else{
					hql += " and a.invoiceCode like ('" + invoiceCreditMasterBean.getInvoiceCode() + "%')";
				}
			}
			
			if(!invoiceDateForm.equals("")){
				hql += " and a.invoiceDate >= STR_TO_DATE('" + invoiceDateForm + "', '%Y%m%d')";
			}
			
			if(!invoiceDateTo.equals("")){
				hql += " and a.invoiceDate <= STR_TO_DATE('" + invoiceDateTo + "', '%Y%m%d')";
			}
			
			if(!invoiceCreditMasterBean.getCusFullName().equals("***")){
				if(invoiceCreditMasterBean.getCusFullName().equals("")){
					hql += " and CONCAT(b.cusName, ' ', b.cusSurname) = ''";
				}else{
					hql += " and CONCAT(b.cusName, ' ', b.cusSurname) like ('" + invoiceCreditMasterBean.getCusFullName() + "%')";
				}
			}
			
			if(!invoiceCreditMasterBean.getInvoiceStatus().equals("")){
				hql += " and a.invoiceStatus = '" + invoiceCreditMasterBean.getInvoiceStatus() + "'";
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
				bean 	= new InvoiceCreditMasterBean();
				
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
				
				invoicecreditmasterList.add(bean);
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return invoicecreditmasterList;
		
	}
	
	public InvoiceCreditMasterBean getInvoiceCreditMaster(InvoiceCreditMasterBean 	invoiceCreditMasterBean) throws EnjoyException{
		logger.info("[getInvoiceCreditMaster][Begin]");
		
		String						hql						= null;
		SQLQuery 					query 					= null;
		List<Object[]>				list					= null;
		InvoiceCreditMasterBean		bean					= null;
		SessionFactory 				sessionFactory			= null;
		Session 					session					= null;
		
		try{	
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql		= "select *"
					+ "	from invoicecreditmaster"
					+ "	where invoiceCode 	= '" + invoiceCreditMasterBean.getInvoiceCode() + "'";
			
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
			query.addScalar("invoiceCash"		, new StringType());
			query.addScalar("invoiceStatus"		, new StringType());
			query.addScalar("tin"				, new StringType());
			query.addScalar("remark"			, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getInvoiceCreditMaster] list.size() :: " + list.size());
			
			if(list.size()==1){
				for(Object[] row:list){
					bean 	= new InvoiceCreditMasterBean();
					
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
					logger.info("invoiceCash 				:: " + row[13]);
					logger.info("invoiceStatus 				:: " + row[14]);
					logger.info("tin 						:: " + row[15]);
					logger.info("remark 					:: " + row[16]);
					
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
					bean.setInvoiceCash				(EnjoyUtils.nullToStr(row[13]));
					bean.setInvoiceStatus			(EnjoyUtils.nullToStr(row[14]));
					bean.setTin						(EnjoyUtils.nullToStr(row[15]));
					bean.setRemark					(EnjoyUtils.nullToStr(row[16]));
					
				}	
			}
			
		}catch(Exception e){
			logger.info("[getInvoiceCreditMaster] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCreditMaster");
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getInvoiceCreditMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertInvoiceCreditMaster(Session session, InvoiceCreditMasterBean 		invoiceCreditMasterBean) throws EnjoyException{
		logger.info("[insertInvoiceCreditMaster][Begin]");
		
		Invoicecreditmaster	invoicecreditmaster						= null;
		
		try{
			
			invoicecreditmaster = new Invoicecreditmaster();
			
			invoicecreditmaster.setInvoiceCode			(invoiceCreditMasterBean.getInvoiceCode());
			invoicecreditmaster.setInvoiceDate			(invoiceCreditMasterBean.getInvoiceDate());
			invoicecreditmaster.setInvoiceType			(invoiceCreditMasterBean.getInvoiceType());
			invoicecreditmaster.setCusCode				(invoiceCreditMasterBean.getCusCode());
			invoicecreditmaster.setBranchName			(invoiceCreditMasterBean.getBranchName());
			invoicecreditmaster.setSaleUniqueId			(EnjoyUtils.parseInt(invoiceCreditMasterBean.getSaleUniqueId()));
			invoicecreditmaster.setSaleCommission		(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getSaleCommission()));
			invoicecreditmaster.setInvoicePrice			(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoicePrice()));
			invoicecreditmaster.setInvoicediscount		(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoicediscount()));
			invoicecreditmaster.setInvoiceDeposit		(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceDeposit()));
			invoicecreditmaster.setInvoiceVat			(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceVat()));
			invoicecreditmaster.setInvoiceTotal			(EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceTotal()));
			invoicecreditmaster.setUserUniqueId			(EnjoyUtils.parseInt(invoiceCreditMasterBean.getUserUniqueId()));
			invoicecreditmaster.setInvoiceCash			(invoiceCreditMasterBean.getInvoiceCash());
			invoicecreditmaster.setInvoiceStatus		(invoiceCreditMasterBean.getInvoiceStatus());
			invoicecreditmaster.setTin					(invoiceCreditMasterBean.getTin());
			invoicecreditmaster.setRemark				(invoiceCreditMasterBean.getRemark());
			
			session.saveOrUpdate(invoicecreditmaster);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertInvoiceCreditMaster");
		}finally{
			
			invoicecreditmaster = null;
			logger.info("[insertInvoiceCreditMaster][End]");
		}
	}
	
	public void updateInvoiceCreditMaster(Session session, InvoiceCreditMasterBean 		invoiceCreditMasterBean) throws EnjoyException{
		logger.info("[updateInvoiceCreditMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecreditmaster set invoiceDate 	= :invoiceDate"
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
												+ ", invoiceCash 			= :invoiceCash"
												+ ", invoiceStatus 			= :invoiceStatus"
												+ ", tin 					= :tin"
												+ ", remark 				= :remark"
										+ " where invoiceCode = :invoiceCode";
			
			query = session.createQuery(hql);
			query.setParameter("invoiceDate"		, invoiceCreditMasterBean.getInvoiceDate());
			query.setParameter("invoiceType"		, invoiceCreditMasterBean.getInvoiceType());
			query.setParameter("cusCode"			, invoiceCreditMasterBean.getCusCode());
			query.setParameter("branchName"			, invoiceCreditMasterBean.getBranchName());
			query.setParameter("saleUniqueId"		, EnjoyUtils.parseInt(invoiceCreditMasterBean.getSaleUniqueId()));
			query.setParameter("saleCommission"		, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getSaleCommission()));
			query.setParameter("invoicePrice"		, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoicePrice()));
			query.setParameter("invoicediscount"	, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoicediscount()));
			query.setParameter("invoiceDeposit"		, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceDeposit()));
			query.setParameter("invoiceVat"			, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceVat()));
			query.setParameter("invoiceTotal"		, EnjoyUtils.parseBigDecimal(invoiceCreditMasterBean.getInvoiceTotal()));
			query.setParameter("userUniqueId"		, EnjoyUtils.parseInt(invoiceCreditMasterBean.getUserUniqueId()));
			query.setParameter("invoiceCash"		, invoiceCreditMasterBean.getInvoiceCash());
			query.setParameter("invoiceStatus"		, invoiceCreditMasterBean.getInvoiceStatus());
			query.setParameter("tin"				, invoiceCreditMasterBean.getTin());
			query.setParameter("invoiceCode"		, invoiceCreditMasterBean.getInvoiceCode());
			query.setParameter("remark"				, invoiceCreditMasterBean.getRemark());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateInvoiceCreditMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateInvoiceCreditMaster][End]");
		}
	}
	
	public void updateInvoiceCreditMasterStatus(Session session, InvoiceCreditMasterBean 		invoiceCreditMasterBean) throws EnjoyException{
		logger.info("[updateInvoiceCreditMasterStatus][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecreditmaster set invoiceStatus 	= :invoiceStatus"
										+ " where invoiceCode = :invoiceCode";
			
			query = session.createQuery(hql);
			query.setParameter("invoiceStatus"		, invoiceCreditMasterBean.getInvoiceStatus());
			query.setParameter("invoiceCode"		, invoiceCreditMasterBean.getInvoiceCode());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error updateInvoiceCreditMasterStatus");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[updateInvoiceCreditMasterStatus][End]");
		}
	}
	
	public List<InvoiceCreditDetailBean> getInvoiceCreditDetailList(InvoiceCreditDetailBean 	invoiceCreditDetailBean) throws EnjoyException{
		logger.info("[getInvoiceCreditDetailList][Begin]");
		
		String							hql							= null;
		SQLQuery 						query 						= null;
		List<Object[]>					list						= null;
		InvoiceCreditDetailBean			bean						= null;
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList 	= new ArrayList<InvoiceCreditDetailBean>();
		int								seq							= 0;
		SessionFactory 					sessionFactory				= null;
		Session 						session						= null;
		
		try{	
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			hql					= "select a.*"
									+ "	, b.productName"
									+ "	, e.quantity as inventory"
									+ "	, b.unitCode"
									+ "	, c.unitName"
									+ "	from invoicecreditdetail a"
									+ "		INNER JOIN productmaster b on b.productCode 	= a.productCode"
									+ "		INNER JOIN unittype c on c.unitCode 		= b.unitCode"
									+ "		LEFT  JOIN invoicecreditmaster d ON a.invoiceCode = d.invoiceCode"
									+ "		LEFT JOIN	productquantity e ON d.tin = e.tin"
									+ "	where a.invoiceCode 	= '" + invoiceCreditDetailBean.getInvoiceCode() + "'"
									+ "	order by a.seq asc";
			
			logger.info("[getInvoiceCreditDetailList] hql :: " + hql);

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
			
			logger.info("[getInvoiceCreditDetailList] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new InvoiceCreditDetailBean();
				
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
				
				invoiceCreditDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			logger.info("[getInvoiceCreditDetailList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCreditDetailList");
		}finally{
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			logger.info("[getInvoiceCreditDetailList][End]");
		}
		
		return invoiceCreditDetailList;
		
	}
	
	public void insertInvoiceCreditDetail(Session session, InvoiceCreditDetailBean 	invoiceCreditDetailBean) throws EnjoyException{
		logger.info("[insertInvoiceCreditDetail][Begin]");
		
		Invoicecreditdetail		invoicecreditdetail		= null;
		InvoicecreditdetailPK 	id 						= null;
		
		try{
			
			invoicecreditdetail 	= new Invoicecreditdetail();
			id 						= new InvoicecreditdetailPK();
			
			id.setInvoiceCode	(invoiceCreditDetailBean.getInvoiceCode());
			id.setSeq			(EnjoyUtils.parseInt(invoiceCreditDetailBean.getSeqDb()));
			
			invoicecreditdetail.setId				(id);
			invoicecreditdetail.setProductCode		(invoiceCreditDetailBean.getProductCode());
			invoicecreditdetail.setQuantity			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getQuantity()));
			invoicecreditdetail.setPricePerUnit		(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getPricePerUnit()));
			invoicecreditdetail.setDiscount			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getDiscount()));
			invoicecreditdetail.setPrice			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getPrice()));
			
			session.saveOrUpdate(invoicecreditdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertInvoiceCreditDetail");
		}finally{
			id 					= null;
			invoicecreditdetail = null;
			logger.info("[insertInvoiceCreditDetail][End]");
		}
	}
	
	public void deleteInvoiceCreditDetail(Session session, String invoiceCode) throws EnjoyException{
		logger.info("[deleteInvoiceCreditDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Invoicecreditdetail t"
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
			logger.info("[deleteInvoiceCreditDetail][End]");
		}
	}
	
	public int genId(Session session) throws EnjoyException{
		logger.info("[genId][Begin]");
		
		String							hql									= null;
		List<Integer>			 		list								= null;
		SQLQuery 						query 								= null;
		int 							result								= 0;
		
		
		try{
			
			hql				= "select max(invoiceCode) lastId from invoicecreditmaster";
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
