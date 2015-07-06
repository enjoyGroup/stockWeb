package th.go.stock.web.enjoy.servlet;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import th.go.stock.app.enjoy.main.ConfigFile;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.main.MailConFigFile;
import th.go.stock.app.enjoy.utils.EnjoyLogger;

/**
 * Servlet implementation class InitServlet
 */
@WebServlet(urlPatterns="/InitServlet",loadOnStartup=1)
public class InitServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		System.out.println("[InitServlet][init][Begin]");
		String realPath  = null;  
		try{
//			String log4jFile = realPath + getServletContext().getInitParameter("Log4JFile");
//			initLog4J(log4jFile); 
			System.out.println("[InitServlet][init] :: 1");
			realPath  = getServletContext().getRealPath("");  
			ConfigFile.init(realPath + getServletContext().getInitParameter(Constants.CONFIG_FILE));
			MailConFigFile.init(realPath + getServletContext().getInitParameter(Constants.MAIL_CONFIG_FILE));
			System.out.println("[InitServlet][init] :: 2");
			EnjoyLogger.initial(Boolean.valueOf(ConfigFile.getOpenLog()));
			System.out.println("[InitServlet][init] :: 3");
			
		} catch(Exception e){
			e.printStackTrace();
			throw new ServletException("Can not Initial Servlet [" + e.toString() + "]");
		}finally{
			System.out.println("[InitServlet][init][End]");
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
