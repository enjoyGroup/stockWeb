
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.InvoiceCashDetailBean;
import th.go.stock.app.enjoy.bean.InvoiceCashMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Invoicecashdetail;
import th.go.stock.app.enjoy.model.InvoicecashdetailPK;
import th.go.stock.app.enjoy.model.Invoicecashmaster;
import th.go.stock.app.enjoy.model.InvoicecashmasterPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class InvoiceCashDao extends DaoControl{
	
	public InvoiceCashDao(){
		setLogger(EnjoyLogger.getLogger(InvoiceCashDao.class));
		super.init();
	}
	
	public List<InvoiceCashMasterBean> searchByCriteria(InvoiceCashMasterBean 	invoiceCashMasterBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		InvoiceCashMasterBean			bean					= null;
		List<InvoiceCashMasterBean> 	invoiceCashMasterList 	= new ArrayList<InvoiceCashMasterBean>();
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
			
			invoiceDateForm 	= EnjoyUtils.dateThaiToDb(invoiceCashMasterBean.getInvoiceDateForm());
			invoiceDateTo		= EnjoyUtils.dateThaiToDb(invoiceCashMasterBean.getInvoiceDateTo());
			
			hql					= "select a.*, CONCAT(b.cusName, ' ', b.cusSurname) as cusFullName"
									+ " from invoicecashmaster a LEFT JOIN customer b"
									+ " 	ON a.cusCode = b.cusCode and a.tin = b.tin"
									+ "	where a.tin = :tin";
			
			//Criteria
			param.put("tin"	, invoiceCashMasterBean.getTin());
			
			if(!invoiceCashMasterBean.getInvoiceCode().equals("")){
				hql += " and a.invoiceCode LIKE CONCAT(:invoiceCode, '%')";
				param.put("invoiceCode"	, invoiceCashMasterBean.getInvoiceCode());
			}
			
			if(!invoiceDateForm.equals("")){
				hql += " and a.invoiceDate >= STR_TO_DATE(:invoiceDateForm, '%Y%m%d')";
				param.put("invoiceDateForm"	, invoiceDateForm);
			}
			
			if(!invoiceDateTo.equals("")){
				hql += " and a.invoiceDate <= STR_TO_DATE(:invoiceDateTo, '%Y%m%d')";
				param.put("invoiceDateTo"	, invoiceDateTo);
			}
			
			if(!invoiceCashMasterBean.getCusFullName().equals("")){
				hql += " and CONCAT(b.cusName, ' ', b.cusSurname) LIKE CONCAT(:cusName, '%')";
				param.put("cusName"	, invoiceCashMasterBean.getCusFullName());
			}
			
			if(!invoiceCashMasterBean.getInvoiceStatus().equals("")){
				hql += " and a.invoiceStatus = :invoiceStatus";
				param.put("invoiceStatus"	, invoiceCashMasterBean.getInvoiceStatus());
			}else{
				hql += " and a.invoiceStatus not in ('W')";
			}
			
			//Column select
			columnList.add("invoiceCode");
			columnList.add("invoiceType");
			columnList.add("cusFullName");
			columnList.add("invoiceDate");
			columnList.add("invoiceTotal");
			columnList.add("invoiceStatus");
			columnList.add("tin");
			columnList.add("invoiceCredit");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new InvoiceCashMasterBean();
				
				invoiceTypeDesc = EnjoyUtils.nullToStr(row.get("invoiceType")).equals("V")?"มี VAT":"ไม่มี VAT";
				invoiceStatus	= EnjoyUtils.nullToStr(row.get("invoiceStatus"));
				
				if(invoiceStatus.equals("A")){
					invoiceStatusDesc = "ใช้งานอยู่";
				}else if(invoiceStatus.equals("C")){
					invoiceStatusDesc = "ยกเลิกการใช้งาน";
				}else if(invoiceStatus.equals("W")){
					invoiceStatusDesc = "รอสร้างใบ Invoice";
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
				bean.setInvoiceCredit		(EnjoyUtils.nullToStr(row.get("invoiceCredit")));
				bean.setSeqDis				(String.valueOf(seqDis++));
				
				invoiceCashMasterList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return invoiceCashMasterList;
		
	}
	
	public InvoiceCashMasterBean getInvoiceCashMaster(InvoiceCashMasterBean invoiceCashMasterBean) throws EnjoyException{
		getLogger().info("[getInvoiceCashMaster][Begin]");
		
		String							hql						= null;
		InvoiceCashMasterBean			bean					= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql		= "select t.*, CONCAT(a.userName,' ',a.userSurname) saleName"
					+ "	from invoicecashmaster t"
					+ "	left join userdetails a on t.saleUniqueId = a.userUniqueId"
					+ "	where t.invoiceCode = :invoiceCode"
					+ "		and t.tin		= :tin";
			
			//Criteria
			param.put("invoiceCode"	, invoiceCashMasterBean.getInvoiceCode());
			param.put("tin"			, invoiceCashMasterBean.getTin());
			
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
			columnList.add("invoiceCredit");
			columnList.add("invoiceStatus");
			columnList.add("tin");
			columnList.add("remark");
			columnList.add("saleName");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new InvoiceCashMasterBean();
					
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
					bean.setInvoiceCredit			(EnjoyUtils.nullToStr(row.get("invoiceCredit")));
					bean.setInvoiceStatus			(EnjoyUtils.nullToStr(row.get("invoiceStatus")));
					bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
					bean.setRemark					(EnjoyUtils.nullToStr(row.get("remark")));
					bean.setSaleName				(EnjoyUtils.nullToStr(row.get("saleName")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCashMaster");
		}finally{
			hql				= null;
			getLogger().info("[getInvoiceCashMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertInvoiceCashMaster(InvoiceCashMasterBean invoiceCashMasterBean) throws EnjoyException{
		getLogger().info("[insertInvoiceCashMaster][Begin]");
		
		Invoicecashmaster	invoiceCashMaster	= null;
		InvoicecashmasterPK	id					= null;
		
		try{
			
			invoiceCashMaster 	= new Invoicecashmaster();
			id					= new InvoicecashmasterPK();
			
			id.setInvoiceCode	(invoiceCashMasterBean.getInvoiceCode());
			id.setTin			(invoiceCashMasterBean.getTin());
			
			invoiceCashMaster.setId						(id);
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
			invoiceCashMaster.setRemark					(invoiceCashMasterBean.getRemark());
			invoiceCashMaster.setCreatedDt				(new Date());
			
			insertData(invoiceCashMaster);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertInvoiceCashMaster");
		}finally{
			
			invoiceCashMaster = null;
			getLogger().info("[insertInvoiceCashMaster][End]");
		}
	}
	
	public void updateInvoiceCashMaster(InvoiceCashMasterBean 		invoiceCashMasterBean) throws EnjoyException{
		getLogger().info("[updateInvoiceCashMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecashmaster t set t.invoiceDate 	= :invoiceDate"
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
													+ ", t.invoiceCredit 		= :invoiceCredit"
													+ ", t.invoiceStatus 		= :invoiceStatus"
													+ ", t.remark 				= :remark"
										+ " where t.id.invoiceCode 	= :invoiceCode"
										+ "		and t.id.tin		= :tin";
			
			query = createQuery(hql);
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
			query.setParameter("tin"				, invoiceCashMasterBean.getTin());
			query.setParameter("remark"				, invoiceCashMasterBean.getRemark());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateInvoiceCashMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateInvoiceCashMaster][End]");
		}
	}
	
	public void updateInvoiceCashMasterStatus(InvoiceCashMasterBean invoiceCashMasterBean) throws EnjoyException{
		getLogger().info("[updateInvoiceCashMasterStatus][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Invoicecashmaster t set t.invoiceStatus 	= :invoiceStatus"
										+ " where t.id.invoiceCode 	= :invoiceCode"
										+ "		and t.id.tin 		= :tin";
			
			query = createQuery(hql);
			query.setParameter("invoiceStatus"	, invoiceCashMasterBean.getInvoiceStatus());
			query.setParameter("invoiceCode"	, invoiceCashMasterBean.getInvoiceCode());
			query.setParameter("tin"			, invoiceCashMasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateInvoiceCashMasterStatus");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateInvoiceCashMasterStatus][End]");
		}
	}
	
	public List<InvoiceCashDetailBean> getInvoiceCashDetailList(InvoiceCashDetailBean invoiceCashDetailBean) throws EnjoyException{
		getLogger().info("[getInvoiceCashDetailList][Begin]");
		
		String							hql						= null;
		InvoiceCashDetailBean			bean					= null;
		List<InvoiceCashDetailBean> 	invoiceCashDetailList 	= new ArrayList<InvoiceCashDetailBean>();
		int								seq						= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{	
			hql					= "select a.*"
									+ "	, b.productName"
									+ "	, e.quantity as inventory"
									+ "	, b.unitCode"
									+ "	, c.unitName"
									+ "	from invoicecashdetail a"
									+ "		INNER JOIN productmaster b on b.productCode = a.productCode and a.tin = b.tin"
									+ "		INNER JOIN unittype c on c.unitCode = b.unitCode and a.tin = c.tin"
									+ "		LEFT  JOIN invoicecashmaster d ON a.invoiceCode = d.invoiceCode and a.tin = d.tin"
									+ "		LEFT JOIN	productquantity e ON d.tin = e.tin and e.productCode = a.productCode"
									+ "	where a.invoiceCode 	= :invoiceCode"
									+ "		and a.tin			= :tin"
									+ "	order by a.seq asc";
			
			//Criteria
			param.put("invoiceCode"	, invoiceCashDetailBean.getInvoiceCode());
			param.put("tin"			, invoiceCashDetailBean.getTin());
			
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
				bean 	= new InvoiceCashDetailBean();
				
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
				
				invoiceCashDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getInvoiceCashDetailList");
		}finally{
			hql				= null;
			getLogger().info("[getInvoiceCashDetailList][End]");
		}
		
		return invoiceCashDetailList;
		
	}
	
	public void insertInvoiceCashDetail(InvoiceCashDetailBean 	invoiceCashDetailBean) throws EnjoyException{
		getLogger().info("[insertInvoiceCashDetail][Begin]");
		
		Invoicecashdetail		invoicecashdetail		= null;
		InvoicecashdetailPK 	id 						= null;
		
		try{
			
			invoicecashdetail 	= new Invoicecashdetail();
			id 					= new InvoicecashdetailPK();
			
			id.setInvoiceCode	(invoiceCashDetailBean.getInvoiceCode());
			id.setTin			(invoiceCashDetailBean.getTin());
			id.setSeq			(EnjoyUtils.parseInt(invoiceCashDetailBean.getSeqDb()));
			
			invoicecashdetail.setId					(id);
			invoicecashdetail.setProductCode		(invoiceCashDetailBean.getProductCode());
			invoicecashdetail.setQuantity			(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getQuantity()));
			invoicecashdetail.setPricePerUnit		(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getPricePerUnit()));
			invoicecashdetail.setDiscount			(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getDiscount()));
			invoicecashdetail.setPrice				(EnjoyUtils.parseBigDecimal(invoiceCashDetailBean.getPrice()));
			
			insertData(invoicecashdetail);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertInvoiceCashDetail");
		}finally{
			id 					= null;
			invoicecashdetail 	= null;
			getLogger().info("[insertInvoiceCashDetail][End]");
		}
	}
	
	public void deleteInvoiceCashDetail(String invoiceCode, String tin) throws EnjoyException{
		getLogger().info("[deleteInvoiceCashDetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Invoicecashdetail t"
							+ " where t.id.invoiceCode	= :invoiceCode"
							+ "		and t.id.tin		= :tin";
			
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
			getLogger().info("[deleteInvoiceCashDetail][End]");
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
			id					= invoiceType.equals("V")?"3":"4";
			codeDisplay			= refconstantcodeDao.getCodeDisplay(id, tin);
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(invoiceCode, '-', -1)) + 1) AS newId"
							+ "	FROM invoicecashmaster"
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
			getLogger().error(e);
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			refconstantcodeDao.destroySession();
			getLogger().info("[genId][End]");
		}
		
		return newId;
	}

}
