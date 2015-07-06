package th.go.stock.web.enjoy.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;

/**
 * Servlet implementation class RrcGenericSrv
 */
@WebServlet(urlPatterns="/EnjoyGenericSrv")
public class EnjoyGenericSrv extends HttpServlet {
	private static final long 			serialVersionUID = 1L;
	private static final EnjoyLogger 	logger 			= EnjoyLogger.getLogger(EnjoyGenericSrv.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnjoyGenericSrv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		logger.info("[service][Begin]");
		
		String 				serviceName 			= null;
		Class 				cls 					= null;
		EnjoyStandardSvc 	enjoyStandardSvc 		= null;
		EnjoyStandardItf 	service 				= null;
		HttpSession 		session 				= null;
		String 				target 					= null;
		DiskFileItemFactory factory 				= null;
		ServletFileUpload 	upload 					= null;
		List 				listItem 				= null;
        
        try {
        	request.setCharacterEncoding("UTF-8");
        	response.setCharacterEncoding("UTF-8");
        	
            session				= request.getSession(true);
            serviceName 		= request.getParameter("service");
            cls 				= Class.forName(Constants.MAIN_PACKAGE + serviceName);
            enjoyStandardSvc 	= (EnjoyStandardSvc)cls.newInstance();
            service 			= enjoyStandardSvc;
            
            logger.info("[service]: service: " + serviceName);
            
            if (request != null && ServletFileUpload.isMultipartContent(request)) {
	            factory 	= new DiskFileItemFactory();
	            upload 		= new ServletFileUpload(factory);
	            listItem 	= upload.parseRequest(request);
	            
	            logger.info("[service] listItem :: " + listItem);
	            
	            request.setAttribute(Constants.LIST_FILE, listItem);
            }

            
            service.execute(request, response);
            target = (String)request.getAttribute("target");
            
            if(target != null){
                redirect(response, target);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            redirect(response, Constants.ERR_PAGE_URL);
        }finally{
        	logger.info("[service][End]");
        }
	}
	
	protected void redirect(HttpServletResponse response, String target) {
        try {
            System.out.println("[EnjoyGenericSrv][redirect]: target: "+target);
            response.sendRedirect(target);
        } catch (Exception ioe) {
            System.out.println("[EnjoyGenericSrv][redirect]: IO exception thrown");
            ioe.printStackTrace();
        }
    }
    

}
