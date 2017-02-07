
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.InvoiceCreditDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCreditMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Invoicecreditdetail;
import th.go.stock.app.enjoy.model.InvoicecreditdetailPK;
import th.go.stock.app.enjoy.model.Invoicecreditmaster;
import th.go.stock.app.enjoy.model.InvoicecreditmasterPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class InvoiceCreditDao extends DaoControl{
	
	public InvoiceCreditDao(){
		setLogger(EnjoyLogger.getLogger(InvoiceCreditDao.class));
		super.init();
	}
		
	public List<InvoiceCreditMasterBean> searchByCriteria(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		InvoiceCreditMasterBean			bean					= null;
		List<InvoiceCreditMasterBean> 	invoicecreditmasterList = new ArrayList<InvoiceCreditMasterBean>();
		String 							invoiceDateForm			= null;
		String 							invoiceDateTo			= null;
		String							invoiceTypeDesc			= "";
		String							invoiceStatus			= "";
		String							invoiceStatusDesc		= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		int								seqDis					= 1;
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateTo());
			
			hql					= "select a.*, CONCAT(b.cusName, ' ', b.cusSurname) as cusFullName"
									+ " from invoicecreditmaster a LEFT JOIN customer b"
									+ " 	ON a.cusCode = b.cusCode and a.tin = b.tin"
									+ "	where a.tin = :tin";
			
			//Criteria
			param.put("tin"	, invoiceCreditMasterBean.getTin());
			
			if(!invoiceCreditMasterBean.getInvoiceCode().equals("")){
				hql += " and a.invoiceCode LIKE CONCAT(:invoiceCode, '%')";
				param.put("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			}
			
			if(!invoiceDateForm.equals("")){
				hql += " and a.invoiceDate >= STR_TO_DATE(:invoiceDateForm, '%Y%m%d')";
				param.put("invoiceDateForm"	, invoiceDateForm);
			}
			
			if(!invoiceDateTo.equals("")){
				hql += " and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo, '%Y%m%d')";
				param.put("invoiceDateTo"	, invoiceDateTo);
			}
			
			if(!invoiceCreditMasterBean.getCusFullName().equals("")){
				hql += " and CONCAT(b.cusName, ' ', b.cusSurname) LIKE CONCAT(:cusName, '%')";
				param.put("cusName"	, invoiceCreditMasterBean.getCusFullName());
			}
			
			if(!invoiceCreditMasterBean.getInvoiceStatus().equals("")){
				hql += " and a.invoiceStatus = :invoiceStatus";
				param.put("invoiceStatus"	, invoiceCreditMasterBean.getInvoiceStatus());
			}
			//else{
			//	hql += " and a.invoiceStatus not in ('S')";
			//}
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("invoiceType");
			columnList.add("cusFullName");
			columnList.add("invoiceDate");
			columnList.add("invoiceTotal");
			columnList.add("invoiceStatus");
			columnList.add("tin");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new InvoiceCreditMasterBean();
				
				invoiceTypeDesc = EnjoyUtils.nullToStr(row.get("invoiceType")).equals("V")?"มี VAT":"ไม่มี VAT";
				invoiceStatus	= EnjoyUtils.nullToStr(row.get("invoiceStatus"));
				
				if(invoiceStatus.equals("A")){
					invoiceStatusDesc = "ใช้งานอยู่";
				}else if(invoiceStatus.equals("C")){
					invoiceStatusDesc = "ยกเลิกการใช้งาน";
				}else if(invoiceStatus.equals("W")){
					invoiceStatusDesc = "รอสร้างใบ Invoice";
				}else if(invoiceStatus.equals("S")){
					invoiceStatusDesc = "รับเงินเรียบร้อยแล้ว";
				}
				
				
				bean.setInvoiceCode			(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setInvoiceType			(EnjoyUtils.nullToStr(row.get("invoiceType")));
				bean.setInvoiceTypeDesc		(invoiceTypeDesc);
				bean.setCusFullName			(EnjoyUtils.nullToStr(row.get("cusFullName")));
				bean.setInvoiceDate			(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setInvoiceTotal		(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
				bean.setInvoiceStatus		(invoiceStatus);
				bean.setInvoiceStatusDesc	(invoiceStatusDesc);
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setSeqDis				(String.valueOf(seqDis++));
				
				invoicecreditmasterList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return invoicecreditmasterList;
		
	}
	
	public InvoiceCreditMasterBean getInvoiceCreditMaster(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[getInvoiceCreditMaster][Begin]");
		
		String							hql						= null;
		InvoiceCreditMasterBean			bean					= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql		= "select t.*, CONCAT(a.userName,' ',a.userSurname) saleName"
					+ "	from invoicecreditmaster t"
					+ "		left join userdetails a on t.saleUniqueId = a.userUniqueId"
					+ "	where t.invoiceCode 	= :invoiceCode"
					+ "		and t.tin			= :tin";
			
			//Criteria
			param.put("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			param.put("tin"			, invoiceCreditMasterBean.getTin());
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("invoiceDate");
			columnList.add("invoiceType");
			columnList.add("cusCode");
			columnList.add("branchName");
			columnList.add("saleUniqueId");
			columnList.add("saleCommission");
			columnList.add("invoicePrice");
			columnList.add("invoicediscount");
			columnList.add("invoiceDeposit");
			columnList.add("invoiceVat");
			columnList.add("invoiceTotal");
			columnList.add("userUniqueId");
			columnList.add("invoiceCash");
			columnList.add("invoiceStatus");
			columnList.add("tin");
			columnList.add("remark");
			columnList.add("saleName");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new InvoiceCreditMasterBean();
					
					bean.setInvoiceCode				(EnjoyUtils.nullToStr(row.get("invoiceCode")));
					bean.setInvoiceDate				(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
					bean.setInvoiceType				(EnjoyUtils.nullToStr(row.get("invoiceType")));
					bean.setCusCode					(EnjoyUtils.nullToStr(row.get("cusCode")));
					bean.setBranchName				(EnjoyUtils.nullToStr(row.get("branchName")));
					bean.setSaleUniqueId			(EnjoyUtils.nullToStr(row.get("saleUniqueId")));
					bean.setSaleCommission			(EnjoyUtils.convertFloatToDisplay(row.get("saleCommission"), 2));
					bean.setInvoicePrice			(EnjoyUtils.convertFloatToDisplay(row.get("invoicePrice"), 2));
					bean.setInvoicediscount			(EnjoyUtils.convertFloatToDisplay(row.get("invoicediscount"), 2));
					bean.setInvoiceDeposit			(EnjoyUtils.convertFloatToDisplay(row.get("invoiceDeposit"), 2));
					bean.setInvoiceVat				(EnjoyUtils.convertFloatToDisplay(row.get("invoiceVat"), 2));
					bean.setInvoiceTotal			(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
					bean.setUserUniqueId			(EnjoyUtils.nullToStr(row.get("userUniqueId")));
					bean.setInvoiceCash				(EnjoyUtils.nullToStr(row.get("invoiceCash")));
					bean.setInvoiceStatus			(EnjoyUtils.nullToStr(row.get("invoiceStatus")));
					bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setRemark					(EnjoyUtils.nullToStr(row.get("remark")));
					bean.setSaleName				(EnjoyUtils.nullToStr(row.get("saleName")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCreditMaster");
		}finally{
			hql				= null;
			getLogger().info("[getInvoiceCreditMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertInvoiceCreditMaster(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[insertInvoiceCreditMaster][Begin]");
		
		Invoicecreditmaster		invoicecreditmaster		= null;
		InvoicecreditmasterPK 	id						= null;
		
		try{
			
			invoicecreditmaster = new Invoicecreditmaster();
			id					= new InvoicecreditmasterPK();
			
			id.setInvoiceCode			(invoiceCreditMasterBean.getInvoiceCode());
			id.setTin					(invoiceCreditMasterBean.getTin());
			
			invoicecreditmaster.setId					(id);
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
			invoicecreditmaster.setRemark				(invoiceCreditMasterBean.getRemark());
			invoicecreditmaster.setCreatedDt			(new Date());
			
			insertData(invoicecreditmaster);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertInvoiceCreditMaster");
		}finally{
			
			invoicecreditmaster = null;
			getLogger().info("[insertInvoiceCreditMaster][End]");
		}
	}
	
	public void updateInvoiceCreditMaster(InvoiceCreditMasterBean 		invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[updateInvoiceCreditMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecreditmaster t set t.invoiceDate 	= :invoiceDate"
													+ ", t.invoiceType			= :invoiceType"
													+ ", t.cusCode				= :cusCode"
													+ ", t.branchName			= :branchName"
													+ ", t.saleUniqueId			= :saleUniqueId"
													+ ", t.saleCommission 		= :saleCommission"
													+ ", t.invoicePrice			= :invoicePrice"
													+ ", t.invoicediscount		= :invoicediscount"
													+ ", t.invoiceDeposit 		= :invoiceDeposit"
													+ ", t.invoiceVat 			= :invoiceVat"
													+ ", t.invoiceTotal 		= :invoiceTotal"
													+ ", t.userUniqueId 		= :userUniqueId"
													+ ", t.invoiceCash 			= :invoiceCash"
													+ ", t.invoiceStatus 		= :invoiceStatus"
													+ ", t.remark 				= :remark"
										+ " where t.id.invoiceCode 	= :invoiceCode"
										+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
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
			query.setParameter("remark"				, invoiceCreditMasterBean.getRemark());
			query.setParameter("invoiceCode"		, invoiceCreditMasterBean.getInvoiceCode());
			query.setParameter("tin"				, invoiceCreditMasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateInvoiceCreditMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateInvoiceCreditMaster][End]");
		}
	}
	
	public void updateInvoiceCreditMasterStatus(InvoiceCreditMasterBean 		invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[updateInvoiceCreditMasterStatus][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecreditmaster t set t.invoiceStatus 	= :invoiceStatus"
										+ " where t.id.invoiceCode 	= :invoiceCode"
										+ "		and t.id.tin 		= :tin";
			
			query = createQuery(hql);
			query.setParameter("invoiceStatus"	, invoiceCreditMasterBean.getInvoiceStatus());
			query.setParameter("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			query.setParameter("tin"			, invoiceCreditMasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateInvoiceCreditMasterStatus");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateInvoiceCreditMasterStatus][End]");
		}
	}
	
	public List<InvoiceCreditDetailBean> getInvoiceCreditDetailList(InvoiceCreditDetailBean 	invoiceCreditDetailBean) throws EnjoyException{
		getLogger().info("[getInvoiceCreditDetailList][Begin]");
		
		String							hql							= null;
		InvoiceCreditDetailBean			bean						= null;
		List<InvoiceCreditDetailBean> 	invoiceCreditDetailList 	= new ArrayList<InvoiceCreditDetailBean>();
		int								seq							= 0;
		HashMap<String, Object>			param						= new HashMap<String, Object>();
		List<String>					columnList					= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList					= null;
		
		try{	
			hql					= "select a.*"
									+ "	, b.productName"
									+ "	, e.quantity as inventory"
									+ "	, b.unitCode"
									+ "	, c.unitName"
									+ "	from invoicecreditdetail a"
									+ "		INNER JOIN productmaster b on b.productCode = a.productCode and a.tin = b.tin"
									+ "		INNER JOIN unittype c on c.unitCode = b.unitCode and a.tin = c.tin"
									+ "		LEFT  JOIN invoicecreditmaster d ON a.invoiceCode = d.invoiceCode and a.tin = d.tin"
									+ "		LEFT JOIN	productquantity e ON d.tin = e.tin and e.productCode = a.productCode"
									+ "	where a.invoiceCode 	= :invoiceCode"
									+ "		and a.tin			= :tin"
									+ "	order by a.seq asc";
			
			//Criteria
			param.put("invoiceCode"	, invoiceCreditDetailBean.getInvoiceCode());
			param.put("tin"			, invoiceCreditDetailBean.getTin());
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("seq");
			columnList.add("productCode");
			columnList.add("productName");
			columnList.add("inventory");
			columnList.add("quantity");
			columnList.add("pricePerUnit");
			columnList.add("discount");
			columnList.add("price");
			columnList.add("unitCode");
			columnList.add("unitName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new InvoiceCreditDetailBean();
				
				bean.setInvoiceCode			(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row.get("seq")));
				bean.setProductCode			(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setProductName			(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setInventory			(EnjoyUtils.convertFloatToDisplay(row.get("inventory"), 2));
				bean.setQuantity			(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				bean.setPricePerUnit		(EnjoyUtils.convertFloatToDisplay(row.get("pricePerUnit"), 2));
				bean.setDiscount			(EnjoyUtils.convertFloatToDisplay(row.get("discount"), 2));
				bean.setPrice				(EnjoyUtils.convertFloatToDisplay(row.get("price"), 2));
				bean.setUnitCode			(EnjoyUtils.nullToStr(row.get("unitCode")));
				bean.setUnitName			(EnjoyUtils.nullToStr(row.get("unitName")));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				invoiceCreditDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCreditDetailList");
		}finally{
			hql				= null;
			getLogger().info("[getInvoiceCreditDetailList][End]");
		}
		
		return invoiceCreditDetailList;
		
	}
	
	public void insertInvoiceCreditDetail(InvoiceCreditDetailBean 	invoiceCreditDetailBean) throws EnjoyException{
		getLogger().info("[insertInvoiceCreditDetail][Begin]");
		
		Invoicecreditdetail		invoicecreditdetail		= null;
		InvoicecreditdetailPK 	id 						= null;
		
		try{
			
			invoicecreditdetail 	= new Invoicecreditdetail();
			id 						= new InvoicecreditdetailPK();
			
			id.setInvoiceCode	(invoiceCreditDetailBean.getInvoiceCode());
			id.setTin			(invoiceCreditDetailBean.getTin());
			id.setSeq			(EnjoyUtils.parseInt(invoiceCreditDetailBean.getSeqDb()));
			
			invoicecreditdetail.setId				(id);
			invoicecreditdetail.setProductCode		(invoiceCreditDetailBean.getProductCode());
			invoicecreditdetail.setQuantity			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getQuantity()));
			invoicecreditdetail.setPricePerUnit		(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getPricePerUnit()));
			invoicecreditdetail.setDiscount			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getDiscount()));
			invoicecreditdetail.setPrice			(EnjoyUtils.parseBigDecimal(invoiceCreditDetailBean.getPrice()));
			
			insertData(invoicecreditdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertInvoiceCreditDetail");
		}finally{
			id 					= null;
			invoicecreditdetail = null;
			getLogger().info("[insertInvoiceCreditDetail][End]");
		}
	}
	
	public void deleteInvoiceCreditDetail(String invoiceCode, String tin) throws EnjoyException{
		getLogger().info("[deleteInvoiceCreditDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Invoicecreditdetail t"
							+ " where t.id.invoiceCode	= :invoiceCode"
							+ "		and t.id.tin 		= :tin";
			
			query = createQuery(hql);
			query.setParameter("invoiceCode"	, invoiceCode);
			query.setParameter("tin"			, tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteInvoiceCreditDetail][End]");
		}
	}
	
	public String genId(String invoiceType, String tin) throws EnjoyException{
		getLogger().info("[genId][Begin]");
		
		String							hql						= null;
		String							newId					= "";
		String							codeDisplay				= null;
		RefconstantcodeDao				refconstantcodeDao		= null;
		String							id						= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			
			getLogger().info("[genId] invoiceType :: " + invoiceType);
			
			refconstantcodeDao 	= new RefconstantcodeDao();
			id					= invoiceType.equals("V")?"5":"6";
			codeDisplay			= refconstantcodeDao.getCodeDisplay(id, tin);
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(invoiceCode, '-', -1)) + 1) AS newId"
							+ "	FROM invoicecreditmaster"
							+ "	WHERE"
							+ "		SUBSTRING_INDEX(invoiceCode, '-', 1) = :codeDisplay"
							+ "		and tin = :tin";
			
			//Criteria
			param.put("codeDisplay"	, codeDisplay);
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "newId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0 && resultList.get(0)!=null){
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingInvoiceCode(), EnjoyUtils.parseInt(resultList.get(0)));
			}else{
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingInvoiceCode(), 1);
			}
			
			getLogger().info("[genId] newId 			:: " + newId);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			refconstantcodeDao.destroySession();
			getLogger().info("[genId][End]");
		}
		
		return newId;
	}
	
	public List<InvoiceCreditMasterBean> searchByCriteriaForCredit(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[searchByCriteriaForCredit][Begin]");
		
		String							hql						= null;
		InvoiceCreditMasterBean			bean					= null;
		List<InvoiceCreditMasterBean> 	invoiceCreditMasterList = new ArrayList<InvoiceCreditMasterBean>();
		String 							invoiceDateForm			= null;
		String 							invoiceDateTo			= null;
		String							invoiceTypeDesc			= "";
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		int								seqDis					= 1;
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateTo());
			
			hql					= "select a.invoiceCode"
										+ ", a.invoiceType"
										+ ", a.invoiceDate"
										+ ", a.invoiceTotal"
										+ ", a.remark"
										+ ", concat(b.cusName, ' ', b.cusSurname) cusFullName"
										+ ", a.invoiceCash"
										+ ", a.tin"
									+ " from invoicecreditmaster a  "
									+ " 	INNER JOIN customer b on a.cusCode = b.cusCode and a.tin = b.tin"
									+ "	where a.invoiceStatus 	= 'A'"
									+ "		and a.tin = :tin";
			
			param.put("tin"	, invoiceCreditMasterBean.getTin());
			
			if(!invoiceCreditMasterBean.getInvoiceCode().equals("***")){
				hql += " and a.invoiceCode LIKE CONCAT(:invoiceCode, '%')";
				param.put("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			}
			
			if(!invoiceDateForm.equals("")){
				hql += " and a.invoiceDate >= STR_TO_DATE(:invoiceDateForm, '%Y%m%d')";
				param.put("invoiceDateForm"	, invoiceDateForm);
			}
			
			if(!invoiceDateTo.equals("")){
				hql += " and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo, '%Y%m%d')";
				param.put("invoiceDateTo"	, invoiceDateTo);
			}
			
			if(!invoiceCreditMasterBean.getCusFullName().equals("***")){
				hql += " and CONCAT(b.cusName, ' ', b.cusSurname) LIKE CONCAT(:cusName, '%')";
				param.put("cusName"	, invoiceCreditMasterBean.getCusFullName());
			}
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("invoiceType");
			columnList.add("cusFullName");
			columnList.add("invoiceDate");
			columnList.add("invoiceTotal");
			columnList.add("remark");
			columnList.add("invoiceCash");
			columnList.add("tin");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new InvoiceCreditMasterBean();
				
				invoiceTypeDesc = EnjoyUtils.nullToStr(row.get("invoiceType")).equals("V")?"มี VAT":"ไม่มี VAT";
				
				bean.setInvoiceCode			(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setInvoiceType			(EnjoyUtils.nullToStr(row.get("invoiceType")));
				bean.setInvoiceTypeDesc		(invoiceTypeDesc);
				bean.setCusFullName			(EnjoyUtils.nullToStr(row.get("cusFullName")));
				bean.setInvoiceDate			(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setInvoiceTotal		(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
				bean.setRemark				(EnjoyUtils.nullToStr(row.get("remark")));
				bean.setInvoiceCash			(EnjoyUtils.nullToStr(row.get("invoiceCash")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setSeqDis				(String.valueOf(seqDis++));
				
				invoiceCreditMasterList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteriaForCredit");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteriaForCredit][End]");
		}
		
		return invoiceCreditMasterList;
		
	}
	
	public List<InvoiceCreditMasterBean> searchForBillingReport(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[searchForBillingReport][Begin]");
		
		String							hql						= null;
		InvoiceCreditMasterBean			bean					= null;
		List<InvoiceCreditMasterBean> 	invoicecreditmasterList = new ArrayList<InvoiceCreditMasterBean>();
		String 							invoiceDateForm			= null;
		String 							invoiceDateTo			= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateTo());
			
			hql					= "select * from invoicecreditmaster "
								+ "	where tin 				= :tin "
								+ "		and invoiceStatus 	= 'A'"
								+ "		and cusCode 		= :cusCode"
								+ "		and invoiceDate >= STR_TO_DATE(:invoiceDateForm, '%Y%m%d')"
								+ "		and invoiceDate <= STR_TO_DATE(:invoiceDateTo, '%Y%m%d')";
			
			//Criteria
			param.put("tin"				, invoiceCreditMasterBean.getTin());
			param.put("cusCode"			, invoiceCreditMasterBean.getCusCode());
			param.put("invoiceDateForm"	, invoiceDateForm);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			if(!invoiceCreditMasterBean.getInvoiceCode().equals("")){
				hql += " and invoiceCode LIKE CONCAT(:invoiceCode, '%')";
				param.put("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			}
			
			if(!invoiceCreditMasterBean.getInvoiceType().equals("")){
				hql += " and invoiceType = :invoiceType";
				param.put("invoiceType"	, invoiceCreditMasterBean.getInvoiceType());
			}
			
			hql += "	order by invoiceDate asc";
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("invoiceDate");
			columnList.add("invoiceType");
			columnList.add("cusCode");
			columnList.add("branchName");
			columnList.add("saleUniqueId");
			columnList.add("saleCommission");
			columnList.add("invoicePrice");
			columnList.add("invoicediscount");
			columnList.add("invoiceDeposit");
			columnList.add("invoiceVat");
			columnList.add("invoiceTotal");
			columnList.add("userUniqueId");
			columnList.add("invoiceCash");
			columnList.add("invoiceStatus");
			columnList.add("tin");
			columnList.add("remark");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new InvoiceCreditMasterBean();
				
				bean.setInvoiceCode				(EnjoyUtils.nullToStr(row.get("invoiceCode")));
				bean.setInvoiceDate				(EnjoyUtils.dateToThaiDisplay(row.get("invoiceDate")));
				bean.setInvoiceType				(EnjoyUtils.nullToStr(row.get("invoiceType")));
				bean.setCusCode					(EnjoyUtils.nullToStr(row.get("cusCode")));
				bean.setBranchName				(EnjoyUtils.nullToStr(row.get("branchName")));
				bean.setSaleUniqueId			(EnjoyUtils.nullToStr(row.get("saleUniqueId")));
				bean.setSaleCommission			(EnjoyUtils.convertFloatToDisplay(row.get("saleCommission"), 2));
				bean.setInvoicePrice			(EnjoyUtils.convertFloatToDisplay(row.get("invoicePrice"), 2));
				bean.setInvoicediscount			(EnjoyUtils.convertFloatToDisplay(row.get("invoicediscount"), 2));
				bean.setInvoiceDeposit			(EnjoyUtils.convertFloatToDisplay(row.get("invoiceDeposit"), 2));
				bean.setInvoiceVat				(EnjoyUtils.convertFloatToDisplay(row.get("invoiceVat"), 2));
				bean.setInvoiceTotal			(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
				bean.setUserUniqueId			(EnjoyUtils.nullToStr(row.get("userUniqueId")));
				bean.setInvoiceCash				(EnjoyUtils.nullToStr(row.get("invoiceCash")));
				bean.setInvoiceStatus			(EnjoyUtils.nullToStr(row.get("invoiceStatus")));
				bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setRemark					(EnjoyUtils.nullToStr(row.get("remark")));
				
				invoicecreditmasterList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchForBillingReport");
		}finally{
			hql						= null;
			getLogger().info("[searchForBillingReport][End]");
		}
		
		return invoicecreditmasterList;
		
	}	
	
	public InvoiceCreditMasterBean sumTotalForBillingReport(InvoiceCreditMasterBean invoiceCreditMasterBean) throws EnjoyException{
		getLogger().info("[sumTotalForBillingReport][Begin]");
		
		String							hql						= null;
		InvoiceCreditMasterBean			bean					= new InvoiceCreditMasterBean();
		String 							invoiceDateForm			= null;
		String 							invoiceDateTo			= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCreditMasterBean.getInvoiceDateTo());
			
			hql		= "select SUM(invoicePrice) as invoicePrice"
					+ "		, SUM(invoicediscount) as invoicediscount "
					+ "		, SUM(invoiceDeposit) as invoiceDeposit "
					+ "		, SUM(invoiceVat) as invoiceVat "
					+ "		, SUM(invoiceTotal) as invoiceTotal "
					+ "	from invoicecreditmaster"
					+ "	where tin 				= :tin "
					+ "		and invoiceStatus 	= 'A'"
					+ "		and cusCode 		= :cusCode"
					+ "		and invoiceDate >= STR_TO_DATE(:invoiceDateForm, '%Y%m%d')"
					+ "		and invoiceDate <= STR_TO_DATE(:invoiceDateTo, '%Y%m%d')";
			
			//Criteria
			param.put("tin"				, invoiceCreditMasterBean.getTin());
			param.put("cusCode"			, invoiceCreditMasterBean.getCusCode());
			param.put("invoiceDateForm"	, invoiceDateForm);
			param.put("invoiceDateTo"	, invoiceDateTo);
			
			if(!invoiceCreditMasterBean.getInvoiceCode().equals("")){
				hql += " and invoiceCode LIKE CONCAT(:invoiceCode, '%')";
				param.put("invoiceCode"	, invoiceCreditMasterBean.getInvoiceCode());
			}
			
			if(!invoiceCreditMasterBean.getInvoiceType().equals("")){
				hql += " and invoiceType = :invoiceType";
				param.put("invoiceType"	, invoiceCreditMasterBean.getInvoiceType());
			}
			
			//Column select
			columnList.add("invoicePrice");
			columnList.add("invoicediscount");
			columnList.add("invoiceDeposit");
			columnList.add("invoiceVat");
			columnList.add("invoiceTotal");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList!=null && !resultList.isEmpty()){
				HashMap<String, Object> row = resultList.get(0);
				
				bean.setInvoicePrice	(EnjoyUtils.convertFloatToDisplay(row.get("invoicePrice"), 2));
				bean.setInvoicediscount	(EnjoyUtils.convertFloatToDisplay(row.get("invoicediscount"), 2));
				bean.setInvoiceDeposit	(EnjoyUtils.convertFloatToDisplay(row.get("invoiceDeposit"), 2));
				bean.setInvoiceVat		(EnjoyUtils.convertFloatToDisplay(row.get("invoiceVat"), 2));
				bean.setInvoiceTotal	(EnjoyUtils.convertFloatToDisplay(row.get("invoiceTotal"), 2));
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchForBillingReport");
		}finally{
			hql						= null;
			getLogger().info("[searchForBillingReport][End]");
		}
		
		return bean;
		
	}	

}
