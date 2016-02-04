
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.ComparePriceBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.model.Compareprice;
import th.go.stock.app.enjoy.model.ComparepricePK;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;

public class ComparePriceDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(ComparePriceDao.class);
	
	public List<ComparePriceBean> searchByCriteria(	Session 			session, 
													ComparePriceBean 	comparePriceBean) throws EnjoyException{
		logger.info("[searchByCriteria][Begin]");
		
		String								hql							= null;
		SQLQuery 							query 						= null;
		List<Object[]>						list						= null;
		ComparePriceBean					bean						= null;
		List<ComparePriceBean> 				comparePriceList 			= new ArrayList<ComparePriceBean>();
		int									seq							= 0;
		
		try{	
			hql					= "select a.productCode, b.productName, a.seq, a.vendorCode, c.vendorName, c.branchName, a.quantity, a.price"
									+ "	from compareprice a, productmaster b, companyvendor c"
									+ "	where b.productCode = a.productCode"
										+ " AND c.vendorCode = a.vendorCode"
										+ " AND a.productCode = '" + comparePriceBean.getProductCode() + "'"
									+ " order by a.seq asc";
			
			logger.info("[searchByCriteria] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("productCode"		, new StringType());
			query.addScalar("productName"		, new StringType());
			query.addScalar("vendorCode"		, new StringType());
			query.addScalar("vendorName"		, new StringType());
			query.addScalar("branchName"		, new StringType());
			query.addScalar("seq"				, new StringType());
			query.addScalar("quantity"			, new StringType());
			query.addScalar("price"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[searchByCriteria] list.size() :: " + list.size());
			
			for(Object[] row:list){
				bean 	= new ComparePriceBean();
				
				bean.setProductCode	(EnjoyUtils.nullToStr(row[0]));
				bean.setProductName	(EnjoyUtils.nullToStr(row[1]));
				bean.setVendorCode	(EnjoyUtils.nullToStr(row[2]));
				bean.setVendorName	(EnjoyUtils.nullToStr(row[3]));
				bean.setBranchName	(EnjoyUtils.nullToStr(row[4]));
				bean.setSeqDb		(EnjoyUtils.nullToStr(row[5]));
				bean.setQuantity	(EnjoyUtils.convertFloatToDisplay(row[6], 2));
				bean.setPrice		(EnjoyUtils.convertFloatToDisplay(row[7], 2));
				bean.setSeq			(EnjoyUtils.nullToStr(seq));
				
				comparePriceList.add(bean);
				seq++;
			}	
			
		}catch(Exception e){
			logger.info("[searchByCriteria] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("error searchByCriteria");
		}finally{
			hql						= null;
			logger.info("[searchByCriteria][End]");
		}
		
		return comparePriceList;
		
	}
	
	public void insertCompareprice(Session session, ComparePriceBean comparePriceBean) throws EnjoyException{
		logger.info("[insertCompareprice][Begin]");
		
		Compareprice	compareprice	= null;
		ComparepricePK	pk				= null;
		
		try{
			
			compareprice 	= new Compareprice();
			pk				= new ComparepricePK();
			
			pk.setProductCode(comparePriceBean.getProductCode());
			pk.setSeq(EnjoyUtils.parseInt(comparePriceBean.getSeq()));
			
			compareprice.setId(pk);
			compareprice.setVendorCode(EnjoyUtils.parseInt(comparePriceBean.getVendorCode()));
			compareprice.setQuantity(EnjoyUtils.parseBigDecimal(comparePriceBean.getQuantity()));
			compareprice.setPrice(EnjoyUtils.parseBigDecimal(comparePriceBean.getPrice()));
			
			session.saveOrUpdate(compareprice);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error insertCompareprice");
		}finally{
			compareprice = null;
			pk			 = null;
			logger.info("[insertCompareprice][End]");
		}
	}
	
	public int couVenderInThisProduct(String productCode, String vendorCode) throws EnjoyException{
		logger.info("[couVenderInThisProduct][Begin]");
		
		String			hql					= null;
		SQLQuery 		query 				= null;
		SessionFactory 	sessionFactory		= null;
		Session 		session				= null;
		List<Integer>	list				= null;
		int				cou					= 0;
		
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql		= "select count(*) cou from compareprice"
						+ " where productCode	= '" + productCode + "'"
						+ "		and vendorCode	= '" + vendorCode + "'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("cou"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				cou = EnjoyUtils.parseInt(list.get(0));
			}
			
			logger.info("[couVenderInThisProduct] cou 			:: " + cou);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error couVenderInThisProduct");
		}finally{
			session.flush();
			session.clear();
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			query 			= null;
			logger.info("[couVenderInThisProduct][End]");
		}
		
		return cou;
	}
	
	public int getNewSeqInThisProduct(String productCode) throws EnjoyException{
		logger.info("[getNewSeqInThisProduct][Begin]");
		
		String			hql					= null;
		SQLQuery 		query 				= null;
		SessionFactory 	sessionFactory		= null;
		Session 		session				= null;
		List<Integer>	list				= null;
		int				newSeq				= 0;
		
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			
			hql		= "select (max(seq) + 1) newSeq from compareprice"
					+ " where productCode	= '" + productCode + "'";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("newSeq"			, new IntegerType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				newSeq = EnjoyUtils.parseInt(list.get(0));
			}
			
			logger.info("[getNewSeqInThisProduct] newSeq :: " + newSeq);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("Error getNewSeqInThisProduct");
		}finally{
			session.flush();
			session.clear();
			session.close();
			
			sessionFactory	= null;
			session			= null;
			hql				= null;
			query 			= null;
			logger.info("[getNewSeqInThisProduct][End]");
		}
		
		return newSeq;
	}
	
	public void deleteCompareprice(Session session, String productCode) throws EnjoyException{
		logger.info("[deleteCompareprice][Begin]");
		
		String							hql									= null;
		Query 							query 								= null;
		
		try{
			hql				= "delete Compareprice t"
							+ " where t.id.productCode	 = '" + productCode + "'";
			
			query = session.createQuery(hql);
			
			query.executeUpdate();			
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("เกิดข้อผิดพลาดในการลบข้อมูล");
		}finally{
			
			hql									= null;
			query 								= null;
			logger.info("[deleteCompareprice][End]");
		}
	}
	
	public String getPrice(ComparePriceBean comparePriceBean) throws EnjoyException{
		logger.info("[getPrice][Begin]");
		
		String			hql					= null;
		List<String>	list				= null;
		SQLQuery 		query 				= null;
		String 			price				= "0.00";
		SessionFactory 	sessionFactory		= null;
		Session 		session				= null;
		String			productCode			= "";
		String			vendorCode			= "";
		double			quantity			= 0;
		
		
		try{
			sessionFactory 		= HibernateUtil.getSessionFactory();
			session 			= sessionFactory.openSession();
			productCode			= EnjoyUtils.nullToStr(comparePriceBean.getProductCode());
			vendorCode			= EnjoyUtils.nullToStr(comparePriceBean.getVendorCode());
			quantity			= EnjoyUtils.parseDouble(comparePriceBean.getQuantity());
			
			hql		= "select price from compareprice"
					+ "		where productCode = '" + productCode + "'"
					+ "			and vendorCode = '" + vendorCode + "'"
					+ "			and quantity <= " + quantity
					+ "		order by quantity ASC"
					+ "		LIMIT 1";
			
			query			= session.createSQLQuery(hql);
			
			query.addScalar("price"			, new StringType());
			
			list		 	= query.list();
			
			if(list!=null && list.size() > 0){
				price = EnjoyUtils.convertFloatToDisplay(list.get(0), 2);
			}
			
			logger.info("[getPrice] price 			:: " + price);
			
			
			
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException(e.getMessage());
		}finally{
			session.flush();
			session.clear();
			session.close();
			
			hql				= null;
			list			= null;
			query 			= null;
			sessionFactory	= null;
			session			= null;
			logger.info("[getPrice][End]");
		}
		
		return price;
	}
	
}
