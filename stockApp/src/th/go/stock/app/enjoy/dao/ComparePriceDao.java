
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;

import th.go.stock.app.enjoy.bean.ComparePriceBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.DaoControl;
import th.go.stock.app.enjoy.model.Compareprice;
import th.go.stock.app.enjoy.model.ComparepricePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class ComparePriceDao extends DaoControl{
	
	public ComparePriceDao(){
		setLogger(EnjoyLogger.getLogger(ComparePriceDao.class));
		super.init();
	}
	
	public List<ComparePriceBean> searchByCriteria(ComparePriceBean comparePriceBean) throws EnjoyException{
		getLogger().info("[searchByCriteria][Begin]");
		
		String								hql					= null;
		ComparePriceBean					bean				= null;
		List<ComparePriceBean> 				comparePriceList 	= new ArrayList<ComparePriceBean>();
		int									seq					= 0;
		HashMap<String, Object>				param				= new HashMap<String, Object>();
		List<String>						columnList			= new ArrayList<String>();
		List<HashMap<String, Object>>		resultList			= null;
		
		try{	
			hql					= "select a.productCode, b.productName, a.seq, a.vendorCode, c.vendorName, c.branchName, a.quantity, a.price"
									+ "	from compareprice a, productmaster b, companyvendor c"
									+ "	where b.productCode 	= a.productCode"
									+ "		and b.tin			= a.tin"
									+ " 	AND c.vendorCode 	= a.vendorCode"
									+ "		and c.tinCompany	= a.tin"
									+ " 	AND a.productCode 	= :productCode"
									+ "		and a.tin			= :tin"
									+ " order by a.seq asc";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(comparePriceBean.getProductCode()));
			param.put("tin"			, comparePriceBean.getTin());
			
			//Column select
			columnList.add("productCode");
			columnList.add("productName");
			columnList.add("vendorCode");
			columnList.add("vendorName");
			columnList.add("branchName");
			columnList.add("seq");
			columnList.add("quantity");
			columnList.add("price");

			resultList = getResult(hql, param, columnList);
			
			for(HashMap<String, Object> row:resultList){
				bean 	= new ComparePriceBean();
				
				bean.setProductCode	(EnjoyUtils.nullToStr(row.get("productCode")));
				bean.setProductName	(EnjoyUtils.nullToStr(row.get("productName")));
				bean.setVendorCode	(EnjoyUtils.nullToStr(row.get("vendorCode")));
				bean.setVendorName	(EnjoyUtils.nullToStr(row.get("vendorName")));
				bean.setBranchName	(EnjoyUtils.nullToStr(row.get("branchName")));
				bean.setSeqDb		(EnjoyUtils.nullToStr(row.get("seq")));
				bean.setQuantity	(EnjoyUtils.convertFloatToDisplay(row.get("quantity"), 2));
				bean.setPrice		(EnjoyUtils.convertFloatToDisplay(row.get("price"), 2));
				bean.setSeq			(EnjoyUtils.nullToStr(seq));
				
				comparePriceList.add(bean);
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
		
		return comparePriceList;
		
	}
	
	public void insertCompareprice(ComparePriceBean comparePriceBean) throws EnjoyException{
		getLogger().info("[insertCompareprice][Begin]");
		
		Compareprice	compareprice	= null;
		ComparepricePK	pk				= null;
		
		try{
			
			compareprice 	= new Compareprice();
			pk				= new ComparepricePK();
			
			pk.setProductCode(EnjoyUtils.parseInt(comparePriceBean.getProductCode()));
			pk.setTin(comparePriceBean.getTin());
			pk.setSeq(EnjoyUtils.parseInt(comparePriceBean.getSeq()));
			
			compareprice.setId(pk);
			compareprice.setVendorCode(EnjoyUtils.parseInt(comparePriceBean.getVendorCode()));
			compareprice.setQuantity(EnjoyUtils.parseBigDecimal(comparePriceBean.getQuantity()));
			compareprice.setPrice(EnjoyUtils.parseBigDecimal(comparePriceBean.getPrice()));
			
			insertData(compareprice);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error insertCompareprice");
		}finally{
			compareprice = null;
			pk			 = null;
			getLogger().info("[insertCompareprice][End]");
		}
	}
	
	public int couVenderInThisProduct(String productCode, String vendorCode, String tin) throws EnjoyException{
		getLogger().info("[couVenderInThisProduct][Begin]");
		
		String							hql					= null;
		int								cou					= 0;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		try{
			hql		= "select count(*) cou from compareprice"
						+ " where productCode	= :productCode"
						+ "		and tin			= :tin"
						+ "		and vendorCode	= :vendorCode";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(productCode));
			param.put("tin"			, tin);
			param.put("vendorCode"	, vendorCode);
			
			resultList = getResult(hql, param, "cou", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				cou = EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[couVenderInThisProduct] cou 			:: " + cou);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error couVenderInThisProduct");
		}finally{
			hql				= null;
			getLogger().info("[couVenderInThisProduct][End]");
		}
		
		return cou;
	}
	
	public int getNewSeqInThisProduct(String productCode, String tin) throws EnjoyException{
		getLogger().info("[getNewSeqInThisProduct][Begin]");
		
		String							hql					= null;
		int								newSeq				= 1;
		HashMap<String, Object>			param				= new HashMap<String, Object>();
		List<Object>					resultList			= null;
		
		
		try{
			hql		= "select (max(seq) + 1) newSeq from compareprice"
					+ " where productCode	= :productCode"
					+ "		and tin			= :tin";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(productCode));
			param.put("tin"			, tin);
			
			resultList = getResult(hql, param, "newSeq", Constants.INT_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				newSeq = EnjoyUtils.parseInt(resultList.get(0))==0?1:EnjoyUtils.parseInt(resultList.get(0));
			}
			
			getLogger().info("[getNewSeqInThisProduct] newSeq :: " + newSeq);
			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("Error getNewSeqInThisProduct");
		}finally{
			hql				= null;
			getLogger().info("[getNewSeqInThisProduct][End]");
		}
		
		return newSeq;
	}
	
	public void deleteCompareprice(String productCode, String tin) throws EnjoyException{
		getLogger().info("[deleteCompareprice][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Compareprice t where t.id.productCode	 = :productCode and tin = :tin";
			
			query = createQuery(hql);
			
			query.setParameter("productCode", EnjoyUtils.parseInt(productCode));
			query.setParameter("tin"		, tin);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			getLogger().error(e);
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			getLogger().info("[deleteCompareprice][End]");
		}
	}
	
	public String getPrice(ComparePriceBean comparePriceBean) throws EnjoyException{
		getLogger().info("[getPrice][Begin]");
		
		String						hql					= null;
		String 						price				= "0.00";
		String						productCode			= "";
		String						vendorCode			= "";
		double						quantity			= 0;
		HashMap<String, Object>		param				= new HashMap<String, Object>();
		List<Object>				resultList			= null;
		
		try{
			productCode			= EnjoyUtils.nullToStr(comparePriceBean.getProductCode());
			vendorCode			= EnjoyUtils.nullToStr(comparePriceBean.getVendorCode());
			quantity			= EnjoyUtils.parseDouble(comparePriceBean.getQuantity());
			
			hql		= "select price from compareprice"
					+ "		where productCode 	= :productCode"
					+ "			and tin			= :tin"
					+ "			and vendorCode 	= :vendorCode"
					+ "			and quantity 	<= :quantity"
					+ "		order by quantity ASC"
					+ "		LIMIT 1";
			
			//Criteria
			param.put("productCode"	, EnjoyUtils.parseInt(productCode));
			param.put("tin"			, comparePriceBean.getTin());
			param.put("vendorCode"	, vendorCode);
			param.put("quantity"	, quantity);
			
			resultList = getResult(hql, param, "price", Constants.STRING_TYPE);
			
			if(resultList!=null && resultList.size() > 0){
				price = EnjoyUtils.convertFloatToDisplay(resultList.get(0), 2);
			}
			
			getLogger().info("[getPrice] price 			:: " + price);
			
		}catch(Exception e){
			getLogger().info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			hql				= null;
			getLogger().info("[getPrice][End]");
		}
		
		return price;
	}
	
}
