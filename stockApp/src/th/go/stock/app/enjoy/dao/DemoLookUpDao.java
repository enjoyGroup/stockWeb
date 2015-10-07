
package th.go.stock.app.enjoy.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;

import th.go.stock.app.enjoy.bean.DemoLookUpBean;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.DemoLookUpForm;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;

public class DemoLookUpDao {
	
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(DemoLookUpDao.class);
	
	public List<DemoLookUpBean> getLookUpList(	Session 					session, 
												DemoLookUpForm 				form) throws EnjoyException{
		logger.info("[getLookUpList][Begin]");
		
		String					hql						= null;
		SQLQuery 				query 					= null;
		List<Object[]>			list					= null;
		DemoLookUpBean			demoZoomBean			= null;
		List<DemoLookUpBean> 	listData 				= new ArrayList<DemoLookUpBean>();
		String					find					= null;
		
		try{
			find				= form.getFind();
			hql					= "select pageCodes"
										+ ", pageNames"
										+ ", pathPages"
								+ "	from refpagedetails "
								+ "	where 1=1 ";
			
			if(find!=null && !find.equals("")){
				hql += " and " + form.getColumn();
				
				if(form.getLikeFlag().equals("Y")){
					hql += " like ('" + find + "%')";
				}else{
					hql += " = '" + find + "'";
				}
				
			}
			
			hql += " order by " + form.getOrderBy() + " " + form.getSortBy();
			
			logger.info("[getLookUpList] hql :: " + hql);

			query			= session.createSQLQuery(hql);			
			query.addScalar("pageCodes"				, new StringType());
			query.addScalar("pageNames"				, new StringType());
			query.addScalar("pathPages"				, new StringType());
			
			list		 	= query.list();
			
			logger.info("[getLookUpList] list :: " + list);
			
			if(list!=null){
				logger.info("[getLookUpList] list.size() :: " + list.size());
				
				for(Object[] row:list){
					demoZoomBean 		= new DemoLookUpBean();
					
					logger.info("[getLookUpList] pageCodes 		:: " + row[0]);
					logger.info("[getLookUpList] pageNames 		:: " + row[1]);
					logger.info("[getLookUpList] pathPages 		:: " + row[2]);
					
					
					demoZoomBean.setPageCodes				(EnjoyUtils.nullToStr(row[0]));
					demoZoomBean.setPageNames				(EnjoyUtils.nullToStr(row[1]));
					demoZoomBean.setPathPages				(EnjoyUtils.nullToStr(row[2]));
					
					listData.add(demoZoomBean);
				}	
			}
			
		}catch(Exception e){
			logger.info("[getLookUpList] " + e.getMessage());
			e.printStackTrace();
			throw new EnjoyException("เกิดข้อผิดพลาดในการดึง LookUp");
		}finally{
			hql						= null;
			logger.info("[getLookUpList][End]");
		}
		
		return listData;
		
	}
	
	
}
