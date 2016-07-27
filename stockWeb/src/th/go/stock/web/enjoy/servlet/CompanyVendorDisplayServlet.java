package th.go.stock.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AddressDao;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyVendorDisplayForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CompanyVendorDisplayServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyVendorDisplayServlet.class);
	
    private static final String 	FORM_NAME 				= "companyVendorDisplayForm";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private CompanyVendorDisplayForm		form						= null;
    private CompanyVendorDao				dao							= null;
    
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("[execute][Begin]");
		
         String pageAction = null; 
 		
 		try{
 			 pageAction 				= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 			= new EnjoyUtil(request, response);
 			 this.request            	= request;
             this.response           	= response;
             this.session            	= request.getSession(false);
             this.userBean           	= (UserDetailsBean)session.getAttribute("userBean");
             this.form               	= (CompanyVendorDisplayForm)session.getAttribute(FORM_NAME);
             this.dao					= new CompanyVendorDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("getDetail")) this.form = new CompanyVendorDisplayForm();
 			
 			if(pageAction.equals("getDetail")){
 				this.getDetail();
 				request.setAttribute("target", Constants.PAGE_URL +"/CompanyVendorDisplayScn.jsp");
 			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void getDetail() throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		CompanyVendorBean 	companyVendorBean			= null;
		CompanyVendorBean 	companyVendorBeanDb			= null;
		String				vendorCode					= null;
		String				tinCompany 					= null;
		
		try{
			vendorCode	= EnjoyUtil.nullToStr(request.getParameter("vendorCode"));
			tinCompany	= this.userBean.getTin();;
			
			logger.info("[getDetail] vendorCode :: " + vendorCode);
			logger.info("[getDetail] tinCompany :: " + tinCompany);
			
			this.form.setTitlePage("รายละเอียดผู้จำหน่าย");
			
			companyVendorBean = new CompanyVendorBean();
			companyVendorBean.setVendorCode(vendorCode);
			companyVendorBean.setTinCompany(tinCompany);
			companyVendorBeanDb					= this.dao.getCompanyVendor(companyVendorBean);
			
			this.form.setCompanyVendorBean		(companyVendorBeanDb);
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			logger.info("[getDetail][End]");
		}
		
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
	}

	@Override
	public void commit() {
		this.dao.commit();
	}

	@Override
	public void rollback() {
		this.dao.commit();
	}
	   	
}

