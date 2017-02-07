
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.ReciveOrdeDetailBean;
import th.go.stock.app.enjoy.bean.ReciveOrderMasterBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Reciveordedetail;
import th.go.stock.app.enjoy.model.ReciveordedetailPK;
import th.go.stock.app.enjoy.model.Reciveordermaster;
import th.go.stock.app.enjoy.model.ReciveordermasterPK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ReciveStockDao extends DaoControl{
	
	public ReciveStockDao(){
		setLogger(EnjoyLogger.getLogger(ReciveStockDao.class));
		super.init();
	}
	
	public List<ReciveOrderMasterBean> searchByCriteria(ReciveOrderMasterBean 	reciveOrderMasterBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String							hql						= null;
		ReciveOrderMasterBean			bean					= null;
		List<ReciveOrderMasterBean> 	reciveOrderMasterList 	= new ArrayList<ReciveOrderMasterBean>();
		String 							reciveDateFrom			= null;
		String 							reciveDateTo			= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		int								seqDis					= 1;
		
		try{	
			
			reciveDateFrom 	= EnjoyUtils.dateThaiToDb(reciveOrderMasterBean.getReciveDateFrom());
			reciveDateTo	= EnjoyUtils.dateThaiToDb(reciveOrderMasterBean.getReciveDateTo());
			
			hql					= "select a.*, CONCAT(b.username, ' ', b.userSurname) as usrName, c.reciveStatusName"
								+ "	from reciveordermaster a, userdetails b, refreciveorderstatus c"
								+ "	where a.userUniqueId 	= b.userUniqueId"
								+ " 	and a.reciveStatus 	= c.reciveStatusCode"
								+ "		and a.tin			= :tin";
			
			//Criteria
			param.put("tin"	, reciveOrderMasterBean.getTin());
			
			if(!reciveOrderMasterBean.getReciveNo().equals("")){
				hql += " and a.reciveNo LIKE CONCAT(:reciveNo, '%')";
				param.put("reciveNo"	, reciveOrderMasterBean.getReciveNo());
			}
			
			if(!reciveDateFrom.equals("")){
				hql += " and a.reciveDate >= STR_TO_DATE(:reciveDateFrom, '%Y%m%d')";
				param.put("reciveDateFrom"	, reciveDateFrom);
			}
			
			if(!reciveDateTo.equals("")){
				hql += " and a.reciveDate <= STR_TO_DATE(:reciveDateTo, '%Y%m%d')";
				param.put("reciveDateTo"	, reciveDateTo);
			}
			
			if(!reciveOrderMasterBean.getReciveStatus().equals("")){
				hql += " and a.reciveStatus = :reciveStatus";
				param.put("reciveStatus"	, reciveOrderMasterBean.getReciveStatus());
			}
			
			//Column select
			columnList.add("reciveNo");
			columnList.add("reciveDate");
			columnList.add("usrName");
			columnList.add("reciveStatus");
			columnList.add("reciveStatusName");
			columnList.add("tin");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ReciveOrderMasterBean();
				
				bean.setReciveNo			(EnjoyUtils.nullToStr(row.get("reciveNo")));
				bean.setReciveDate			(EnjoyUtils.dateToThaiDisplay(row.get("reciveDate")));
				bean.setUsrName				(EnjoyUtils.nullToStr(row.get("usrName")));
				bean.setReciveStatus		(EnjoyUtils.nullToStr(row.get("reciveStatus")));
				bean.setReciveStatusDesc	(EnjoyUtils.nullToStr(row.get("reciveStatusName")));
				bean.setTin					(EnjoyUtils.nullToStr(row.get("tin")));
				bean.setSeqDis				(String.valueOf(seqDis++));
				
				reciveOrderMasterList.add(bean);
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			getLogger().info("[searchByCriteria][End]");
		}
		
		return reciveOrderMasterList;
		
	}
	
	public ReciveOrderMasterBean getReciveOrderMaster(ReciveOrderMasterBean reciveOrderMasterBean) throws EnjoyException{
		getLogger().info("[getReciveOrderMaster][Begin]");
		
		String							hql						= null;
		ReciveOrderMasterBean			bean					= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{		
			hql		= "select *"
					+ "	from reciveordermaster"
					+ "	where reciveNo 	= :reciveNo"
					+ "		and tin 	= :tin";
			
			//Criteria
			param.put("reciveNo", reciveOrderMasterBean.getReciveNo());
			param.put("tin"		, reciveOrderMasterBean.getTin());
			
			//Column select
			columnList.add("reciveNo");
			columnList.add("reciveDate");
			columnList.add("reciveType");
			columnList.add("creditDay");
			columnList.add("creditExpire");
			columnList.add("vendorCode");
			columnList.add("branchName");
			columnList.add("billNo");
			columnList.add("priceType");
			columnList.add("reciveStatus");
			columnList.add("userUniqueId");
			columnList.add("reciveAmount");
			columnList.add("reciveDiscount");
			columnList.add("reciveVat");
			columnList.add("reciveTotal");
			columnList.add("tin");
			
			resultList = getResult(hql, param, columnList);
			
			if(resultList.size()==1){
				for(HashMap<String, Object> row:resultList){
					bean 	= new ReciveOrderMasterBean();
					
					bean.setReciveNo				(EnjoyUtils.nullToStr(row.get("reciveNo")));
					bean.setReciveDate				(EnjoyUtils.dateToThaiDisplay(row.get("reciveDate")));
					bean.setReciveType				(EnjoyUtils.nullToStr(row.get("reciveType")));
					bean.setCreditDay				(EnjoyUtils.nullToStr(row.get("creditDay")));
					bean.setCreditExpire			(EnjoyUtils.dateToThaiDisplay(row.get("creditExpire")));
					bean.setVendorCode				(EnjoyUtils.nullToStr(row.get("vendorCode")));
					bean.setBranchName				(EnjoyUtils.nullToStr(row.get("branchName")));
					bean.setBillNo					(EnjoyUtils.nullToStr(row.get("billNo")));
					bean.setPriceType				(EnjoyUtils.nullToStr(row.get("priceType")));
					bean.setReciveStatus			(EnjoyUtils.nullToStr(row.get("reciveStatus")));
					bean.setUserUniqueId			(EnjoyUtils.nullToStr(row.get("userUniqueId")));
					bean.setReciveAmount			(EnjoyUtils.convertFloatToDisplay(row.get("reciveAmount"), 2));
					bean.setReciveDiscount			(EnjoyUtils.convertFloatToDisplay(row.get("reciveDiscount"), 2));
					bean.setReciveVat				(EnjoyUtils.convertFloatToDisplay(row.get("reciveVat"), 2));
					bean.setReciveTotal				(EnjoyUtils.convertFloatToDisplay(row.get("reciveTotal"), 2));
					bean.setTin						(EnjoyUtils.nullToStr(row.get("tin")));
					
				}	
			}
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrderMaster");
		}finally{
			hql						= null;
			getLogger().info("[getReciveOrderMaster][End]");
		}
		
		return bean;
		
	}
	
	public void insertReciveordermaster(ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		getLogger().info("[insertReciveordermaster][Begin]");
		
		Reciveordermaster	reciveordermaster	= null;//genReciveNo
		ReciveordermasterPK	id					= null;
		
		try{
			
			reciveordermaster 	= new Reciveordermaster();
			id					= new ReciveordermasterPK();
			
			id.setReciveNo				(reciveOrderMasterBean.getReciveNo());
			id.setTin					(reciveOrderMasterBean.getTin());
			
			reciveordermaster.setId						(id);
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
			
			insertData(reciveordermaster);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertReciveordermaster");
		}finally{
			
			reciveordermaster = null;
			getLogger().info("[insertReciveordermaster][End]");
		}
	}
	
	public void updateReciveOrderMaster(ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		getLogger().info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Reciveordermaster t set t.reciveDate 	= :reciveDate"
													+ ", t.reciveType			= :reciveType"
													+ ", t.creditDay			= :creditDay"
													+ ", t.creditExpire			= :creditExpire"
													+ ", t.vendorCode			= :vendorCode"
													+ ", t.branchName 			= :branchName"
													+ ", t.billNo				= :billNo"
													+ ", t.priceType			= :priceType"
													+ ", t.reciveStatus 		= :reciveStatus"
													+ ", t.userUniqueId 		= :userUniqueId"
													+ ", t.reciveAmount 		= :reciveAmount"
													+ ", t.reciveDiscount 		= :reciveDiscount"
													+ ", t.reciveVat 			= :reciveVat"
													+ ", t.reciveTotal 			= :reciveTotal"
										+ " where t.id.reciveNo = :reciveNo"
										+ "		and t.id.tin 	= :tin";
			
			query = createQuery(hql);
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
			query.setParameter("reciveNo"			, reciveOrderMasterBean.getReciveNo());
			query.setParameter("tin"				, reciveOrderMasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateReciveOrderMaster][End]");
		}
	}
	
	public void updateReciveOrderMasterSpecial(ReciveOrderMasterBean 		reciveOrderMasterBean) throws EnjoyException{
		getLogger().info("[updateReciveOrderMaster][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "update  Reciveordermaster t set t.reciveDate 	= :reciveDate"
													+ ", t.reciveStatus 		= :reciveStatus"
													+ ", t.userUniqueId 		= :userUniqueId"
										+ " where t.id.reciveNo = :reciveNo"
										+ "		and t.id.tin 	= :tin";
			
			query = createQuery(hql);
			query.setParameter("reciveDate"			, reciveOrderMasterBean.getReciveDate());
			query.setParameter("reciveStatus"		, reciveOrderMasterBean.getReciveStatus());
			query.setParameter("userUniqueId"		, EnjoyUtils.parseInt(reciveOrderMasterBean.getUserUniqueId()));
			query.setParameter("reciveNo"			, reciveOrderMasterBean.getReciveNo());
			query.setParameter("tin"				, reciveOrderMasterBean.getTin());
			
			query.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error updateReciveOrderMaster");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[updateReciveOrderMaster][End]");
		}
	}

	public List<ReciveOrdeDetailBean> getReciveOrdeDetailList(ReciveOrdeDetailBean 	reciveOrdeDetailBean) throws EnjoyException{
		getLogger().info("[getReciveOrdeDetailList][Begin]");
		
		String							hql						= null;
		ReciveOrdeDetailBean			bean					= null;
		List<ReciveOrdeDetailBean> 		reciveOrdeDetailList 	= new ArrayList<ReciveOrdeDetailBean>();
		int								seq						= 0;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
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
									+ "	inner JOIN productmaster b on b.productCode = a.productCode and a.tin = b.tin"
									+ "	inner JOIN unittype c on  b.unitCode = c.unitCode and a.tin = c.tin"
									+ "	LEFT JOIN reciveordermaster d on a.reciveNo = d.reciveNo and a.tin = d.tin"
									+ "	LEFT JOIN productquantity e on a.productCode = e.productCode AND d.tin = e.tin"
								+ "	where a.reciveNo 	= :reciveNo"
								+ "		and a.tin		= :tin"
								+ "	order by a.seq asc";
			
			//Criteria
			param.put("reciveNo"	, reciveOrdeDetailBean.getReciveNo());
			param.put("tin"			, reciveOrdeDetailBean.getTin());
			
			//Column select
			columnList.add("reciveNo");
			columnList.add("seq");
			columnList.add("productCode");
			columnList.add("productName");
			columnList.add("inventory");
			columnList.add("quantity");
			columnList.add("unitCode");
			columnList.add("unitName");
			columnList.add("price");
			columnList.add("discountRate");
			columnList.add("costPrice");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ReciveOrdeDetailBean();
				
				bean.setReciveNo			(EnjoyUtils.nullToStr(row.get("reciveNo")));
				bean.setSeqDb				(EnjoyUtils.nullToStr(row.get("seq")));
				bean.setProductCode			(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setProductName			(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setInventory			(EnjoyUtils.convertFloatToDisplay(row.get("inventory"), 2));
				bean.setQuantity			(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				bean.setUnitCode			(EnjoyUtils.nullToStr(row.get("unitCode")));
				bean.setUnitName			(EnjoyUtils.nullToStr(row.get("unitName")));
				bean.setPrice				(EnjoyUtils.convertFloatToDisplay(row.get("price"), 2));
				bean.setDiscountRate		(EnjoyUtils.convertFloatToDisplay(row.get("discountRate"), 2));
				bean.setCostPrice			(EnjoyUtils.convertFloatToDisplay(row.get("costPrice"), 2));
				bean.setSeq					(EnjoyUtils.nullToStr(seq));
				
				reciveOrdeDetailList.add(bean);
				seq++;
				
			}	
			
		}catch(Exception e){
			getLogger().error(e);
			e.printStackTrace();
			throw new EnjoyException("error getReciveOrdeDetailList");
		}finally{
			hql						= null;
			getLogger().info("[getReciveOrdeDetailList][End]");
		}
		
		return reciveOrdeDetailList;
		
	}
	
	public void insertReciveOrdeDetail(ReciveOrdeDetailBean reciveOrdeDetailBean) throws EnjoyException{
		getLogger().info("[insertReciveOrdeDetail][Begin]");
		
		Reciveordedetail		reciveordedetail		= null;
		ReciveordedetailPK 		id 					= null;
		
		try{
			
			reciveordedetail 	= new Reciveordedetail();
			id 					= new ReciveordedetailPK();
			
			id.setReciveNo	(reciveOrdeDetailBean.getReciveNo());
			id.setTin		(reciveOrdeDetailBean.getTin());
			id.setSeq		(EnjoyUtils.parseInt(reciveOrdeDetailBean.getSeqDb()));
			
			reciveordedetail.setId					(id);
			reciveordedetail.setProductCode			(reciveOrdeDetailBean.getProductCode());
			reciveordedetail.setQuantity			(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getQuantity()));
			reciveordedetail.setPrice				(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getPrice()));
			reciveordedetail.setDiscountRate		(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getDiscountRate()));
			reciveordedetail.setCostPrice			(EnjoyUtils.parseBigDecimal(reciveOrdeDetailBean.getCostPrice()));
			
			insertData(reciveordedetail);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().info(e.getMessage());
			throw new EnjoyException("Error insertReciveOrdeDetail");
		}finally{
			id 					= null;
			reciveordedetail 	= null;
			getLogger().info("[insertReciveOrdeDetail][End]");
		}
	}
	
	public void deleteReciveordedetail(String reciveNo, String tin) throws EnjoyException{
		getLogger().info("[deleteReciveordedetail][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Reciveordedetail t"
							+ " where t.id.reciveNo	 = :reciveNo"
							+ "		and t.id.tin	 = :tin";
			
			query = createQuery(hql);
			query.setParameter("reciveNo"	, reciveNo);
			query.setParameter("tin"		, tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteReciveordedetail][End]");
		}
	}
	
	/*ดึงสถานะมาอยู่ใน Combo*/
	public List<ComboBean> getRefReciveOrderStatusCombo() throws EnjoyException{
		getLogger().info("[getRefReciveOrderStatusCombo][Begin]");
		
		String							hql						= null;
		ComboBean						comboBean				= null;
		List<ComboBean> 				comboList				= new ArrayList<ComboBean>();
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<String>					columnList				= new ArrayList<String>();
		List<HashMap<String, Object>>	resultList				= null;
		
		try{
			hql				= "select * from refreciveorderstatus order by reciveStatusCode asc";
			
			//Column select
			columnList.add("reciveStatusCode");
			columnList.add("reciveStatusName");
			
			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				comboBean = new ComboBean();
				
				comboBean.setCode(EnjoyUtils.nullToStr(row.get("reciveStatusCode")));
				comboBean.setDesc(EnjoyUtils.nullToStr(row.get("reciveStatusName")));
				
				comboList.add(comboBean);
			}
			
		}catch(Exception e){
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึงสถานะมาอยู่ใน Combo");
		}finally{
			hql				= null;
			getLogger().info("[getRefReciveOrderStatusCombo][End]");
		}
		
		return comboList;
		
	}
	
	public String genReciveNo(String tin) throws EnjoyException{
		getLogger().info("[genReciveNo][Begin]");
		
		String							hql						= null;
		String							newId					= "";
		String							codeDisplay				= null;
		RefconstantcodeDao				refconstantcodeDao		= null;
		HashMap<String, Object>			param					= new HashMap<String, Object>();
		List<Object>					resultList				= null;
		
		try{
			refconstantcodeDao 	= new RefconstantcodeDao();
			codeDisplay			= refconstantcodeDao.getCodeDisplay("2", tin);
			
			hql				= "SELECT (MAX(SUBSTRING_INDEX(reciveNo, '-', -1)) + 1) AS newId"
							+ "	FROM reciveordermaster"
							+ "	WHERE"
							+ "		SUBSTRING_INDEX(reciveNo, '-', 1) = :codeDisplay"
							+ "		and tin = :tin";
			
			//Criteria
			param.put("codeDisplay"	, codeDisplay);
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "newId", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0 && resultList.get(0)!=null){
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingReciveNo(), EnjoyUtils.parseInt(resultList.get(0)));
			}else{
				newId = codeDisplay + "-" + String.format(ConfigFile.getPadingReciveNo(), 1);
			}
			
			getLogger().info("[genReciveNo] newId 			:: " + newId);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("genReciveNo error");
		}finally{
			hql				= null;
			refconstantcodeDao.destroySession();
			getLogger().info("[genReciveNo][End]");
		}
		
		return newId;
	}

	
}
