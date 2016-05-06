package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.AddressBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyDetailsBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AddressDao;
import th.go.stock.app.enjoy.dao.CompanyDetailsDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CompanyDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyDetailsMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "companyDetailsMaintananceForm";
    private static final String 	PROVINCE 				= "p";
    private static final String 	DISTRICT 				= "d";
    private static final String 	SUBDISTRICT 			= "s";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private CompanyDetailsDao				dao							= null;
    private CompanyDetailsMaintananceForm	form						= null;
    private AddressDao						addressDao					= null;
    
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
             this.form               	= (CompanyDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao					= new CompanyDetailsDao();
             this.addressDao			= new AddressDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new CompanyDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/CompanyDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/CompanyDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("checkDupTin")){
 				this.checkDupTin();
 			}else if(pageAction.equals("validate")){
				this.lp_validate();
			}else if(pageAction.equals("save")){
				this.onSave();
			}else if(pageAction.equals(PROVINCE)){
				this.lp_province();
			}else if(pageAction.equals(DISTRICT)){
				this.lp_district();
			}else if(pageAction.equals(SUBDISTRICT)){
				this.lp_subdistrict();
			}
 			
 			session.setAttribute(FORM_NAME, this.form);
 			
 		}catch(EnjoyException e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}catch(Exception e){
 			e.printStackTrace();
 			logger.info(e.getMessage());
 		}finally{
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		CompanyDetailsBean 		companyDetailsBean = null;
		
		try{
			
			this.setRefference();
			this.form.setTitlePage("เพิ่มรายละเอียดบริษัท");
			
			companyDetailsBean = this.form.getCompanyDetailsBean();
			
			companyDetailsBean.setCompanyStatus("A");
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{
			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void setRefference() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		try{
			setStatusCombo();
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			
			logger.info("[setRefference][End]");
		}
	}
	
	private void setStatusCombo() throws EnjoyException{
		
		logger.info("[setRefference][Begin]");
		
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		List<ComboBean> 			comboListDb				= null;
		
		try{
			comboListDb = this.dao.getCompanystatusCombo();
			
//			comboList.add(new ComboBean("", "กรุณาระบุ"));
			
			for(ComboBean bean:comboListDb){
				comboList.add(new ComboBean(bean.getCode(), bean.getDesc()));
			}
			
			this.form.setStatusCombo(comboList);
			
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
			throw new EnjoyException("setRefference is error");
		}finally{
			logger.info("[setRefference][End]");
		}
	}
	
	private void getDetail(String tin) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		CompanyDetailsBean 	companyDetailsBean		= null;
		CompanyDetailsBean 	companyDetailsBeanDb	= null;
		
		try{
			tin							= tin.equals("0")?EnjoyUtil.nullToStr(request.getParameter("tin")):tin;
			
			logger.info("[getDetail] tin :: " + tin);
			
			companyDetailsBean = new CompanyDetailsBean();
			companyDetailsBean.setTin(tin);
			
			companyDetailsBeanDb				= this.dao.getCompanyDetail(companyDetailsBean);
			
			this.form.setTitlePage("แก้ไขรายละเอียดบริษัท");
			this.form.setPageMode(CompanyDetailsMaintananceForm.EDIT);
			
			logger.info("[getDetail] companyDetailsBeanDb :: " + companyDetailsBeanDb);
			
			if(companyDetailsBeanDb!=null){
				
				this.form.setCompanyDetailsBean(companyDetailsBeanDb);
				
				
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดบริษัท");
			}
			
		}catch(EnjoyException e){
			throw new EnjoyException(e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("getDetail is error");
		}finally{
			this.setRefference();
			logger.info("[getDetail][End]");
		}
		
	}
	
	private void checkDupTin() throws EnjoyException{
		logger.info("[checkDupTin][Begin]");
		
		int 				cou				= 0;
		SessionFactory 		sessionFactory	= null;
		Session 			session			= null;
		JSONObject 			obj 			= null;
		String				tin				= null;
		
		try{
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin")).replaceAll("-", "");
			
			logger.info("[checkDupTin] tin 		:: " + tin);
			
			cou							= this.dao.checkDupTin(session, tin);
			obj 						= new JSONObject();
			
			obj.put(STATUS, 		SUCCESS);
			obj.put("COU", 			cou);
			
			
		}catch(EnjoyException e){
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			logger.info(e.getMessage());
			
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"checkDupTin is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[checkDupTin][End]");
		}
		
	}
	

	   
   private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   String				provinceCode				= null;
	   String				districtCode				= null;
	   String				subdistrictCode				= null;
	   String				provinceName				= null;
	   String				districtName				= null;
	   String				subdistrictName				= null;
	   JSONObject 			obj 						= new JSONObject();
	   AddressBean			addressBean					= null;
	   
	   try{
		   provinceName		= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName		= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName	= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   
		   logger.info("[lp_validate] provinceName 			:: " + provinceName);
		   logger.info("[lp_validate] districtName 			:: " + districtName);
		   logger.info("[lp_validate] subdistrictName 		:: " + subdistrictName);
		   
		   addressBean 		= this.addressDao.validateAddress(provinceName, districtName, subdistrictName);
		   
		   if(addressBean.getErrMsg().equals("")){
			   
			   provinceCode 	= addressBean.getProvinceId();
			   districtCode 	= addressBean.getDistrictId();
			   subdistrictCode 	= addressBean.getSubdistrictId();
			   
			   logger.info("[lp_validate] provinceCode 			:: " + provinceCode);
			   logger.info("[lp_validate] districtCode 			:: " + districtCode);
			   logger.info("[lp_validate] subdistrictCode 		:: " + subdistrictCode);
			   
			   obj.put(STATUS, 				SUCCESS);
			   obj.put("provinceCode", 		provinceCode);
			   obj.put("districtCode", 		districtCode);
			   obj.put("subdistrictCode", 	subdistrictCode);
			   
			   
		   }else{
			   obj.put(ERR_TYPE, 			addressBean.getErrType());
			   throw new EnjoyException(addressBean.getErrMsg());
		   }
		   
	   }catch(EnjoyException e){
		   obj.put(STATUS, 				ERROR);
		   obj.put(ERR_MSG, 			e.getMessage());
	   }catch(Exception e){
			obj.put(STATUS, 			ERROR);
			obj.put(ERR_TYPE, 			ERR_ERROR);
			obj.put(ERR_MSG, 			"เกิดข้อผิดพลาดในการตรวจสอบข้อมูล");
			logger.info(e.getMessage());
			e.printStackTrace();
	   }finally{
		   this.enjoyUtil.writeMSG(obj.toString());
		   logger.info("[lp_validate][End]");
	   }
   }
	 
	
	private void onSave() throws EnjoyException{
		logger.info("[onSave][Begin]");
		
		String				pageMode				= null;
		String 				tin 					= null;
		String 				companyName 			= null;
		String 				branchName 				= null;
		String 				buildingName 			= null;
		String 				houseNumber 			= null;
		String 				mooNumber 				= null;
		String 				soiName 				= null;
		String 				streetName 				= null;
		String 				provinceCode 			= null;
		String 				districtCode 			= null;
		String 				subdistrictCode 		= null;
		String 				postCode 				= null;
		String 				tel 					= null;
		String 				fax 					= null;
		String 				email 					= null;
		String 				remark 					= null;
		String 				companyStatus 			= null;
		SessionFactory 		sessionFactory			= null;
		Session 			session					= null;
		JSONObject 			obj 					= null;
		CompanyDetailsBean 	companyDetailsBean		= null;
		String				flagChkCompany			= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin")).replaceAll("-", "");
			companyName 				= EnjoyUtil.nullToStr(request.getParameter("companyName"));
			branchName 					= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			buildingName 				= EnjoyUtil.nullToStr(request.getParameter("buildingName"));
			houseNumber 				= EnjoyUtil.nullToStr(request.getParameter("houseNumber"));
			mooNumber 					= EnjoyUtil.nullToStr(request.getParameter("mooNumber"));
			soiName 					= EnjoyUtil.nullToStr(request.getParameter("soiName"));
			streetName 					= EnjoyUtil.nullToStr(request.getParameter("streetName"));
			provinceCode 				= EnjoyUtil.nullToStr(request.getParameter("provinceCode"));
			districtCode 				= EnjoyUtil.nullToStr(request.getParameter("districtCode"));
			subdistrictCode 			= EnjoyUtil.nullToStr(request.getParameter("subdistrictCode"));
			postCode 					= EnjoyUtil.nullToStr(request.getParameter("postCode"));
			tel 						= EnjoyUtil.nullToStr(request.getParameter("tel"));
			fax 						= EnjoyUtil.nullToStr(request.getParameter("fax"));
			email 						= EnjoyUtil.nullToStr(request.getParameter("email"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			companyStatus 				= EnjoyUtil.nullToStr(request.getParameter("companyStatus"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			companyDetailsBean			= new CompanyDetailsBean();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] tin 					:: " + tin);
			logger.info("[onSave] companyName 			:: " + companyName);
			logger.info("[onSave] branchName 			:: " + branchName);
			logger.info("[onSave] buildingName 			:: " + buildingName);
			logger.info("[onSave] houseNumber 			:: " + houseNumber);
			logger.info("[onSave] mooNumber 			:: " + mooNumber);
			logger.info("[onSave] soiName 				:: " + soiName);
			logger.info("[onSave] streetName 			:: " + streetName);
			logger.info("[onSave] provinceCode 			:: " + provinceCode);
			logger.info("[onSave] districtCode 			:: " + districtCode);
			logger.info("[onSave] subdistrictCode 		:: " + subdistrictCode);
			logger.info("[onSave] postCode 				:: " + postCode);
			logger.info("[onSave] tel 					:: " + tel);
			logger.info("[onSave] fax 					:: " + fax);
			logger.info("[onSave] email 				:: " + email);
			logger.info("[onSave] remark 				:: " + remark);
			logger.info("[onSave] companyStatus 		:: " + companyStatus);
			
			
			companyDetailsBean.setTin(tin);
			companyDetailsBean.setCompanyName(companyName);
			companyDetailsBean.setBranchName(branchName);
			companyDetailsBean.setBuildingName(buildingName);
			companyDetailsBean.setHouseNumber(houseNumber);
			companyDetailsBean.setMooNumber(mooNumber);
			companyDetailsBean.setSoiName(soiName);
			companyDetailsBean.setStreetName(streetName);
			companyDetailsBean.setProvinceCode(provinceCode);
			companyDetailsBean.setDistrictCode(districtCode);
			companyDetailsBean.setSubdistrictCode(subdistrictCode);
			companyDetailsBean.setPostCode(postCode);
			companyDetailsBean.setTel(tel);
			companyDetailsBean.setFax(fax);
			companyDetailsBean.setEmail(email);
			companyDetailsBean.setRemark(remark);
			companyDetailsBean.setCompanyStatus(companyStatus);
			
			session.beginTransaction();
			
			if(pageMode.equals(CompanyDetailsMaintananceForm.NEW)){
				this.dao.insertCompanyDetail(session, companyDetailsBean);
			}else{
				this.dao.updateCompanyDetail(session, companyDetailsBean);
			}
			
			flagChkCompany = userBean.getFlagChkCompany();
			if(flagChkCompany.equals("Y") && this.userBean.getFlagChangePassword().equals("N")){
				userBean.setFlagChkCompany("N");
			}
			
			obj.put(STATUS				, SUCCESS);
			obj.put("flagChkCompany"	, flagChkCompany);
			obj.put("FlagChange"		, this.userBean.getFlagChangePassword());
			
			session.getTransaction().commit();
			
		}catch(EnjoyException e){
			session.getTransaction().rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			session.getTransaction().rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			
			session.flush();
			session.clear();
			session.close();
			
			this.getDetail(tin);
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onSave][End]");
		}
	}	
	
	private void lp_province(){
	   logger.info("[lp_search][Begin]");
	   
	   String							provinceName			= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       CompanyDetailsBean 				companyDetailsBean		= null;
       
	   try{
		   provinceName				= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   companyDetailsBean		= this.form.getCompanyDetailsBean();
		   
		   logger.info("[lp_province] provinceName 			:: " + provinceName);
		   
		   companyDetailsBean.setProvinceName(provinceName);
		   
		   list 		= this.addressDao.provinceList(provinceName);
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_province] " + e.getMessage());
	   }finally{
		   logger.info("[lp_province][End]");
	   }
   }
	   
   private void lp_district(){
	   logger.info("[lp_district][Begin]");
	   
	   String							provinceName			= null;
	   String							districtName			= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       CompanyDetailsBean 				companyDetailsBean		= null;
       
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   companyDetailsBean			= this.form.getCompanyDetailsBean();
		   
		   companyDetailsBean.setProvinceName(provinceName);
		   companyDetailsBean.setDistrictName(districtName);
		   
		   logger.info("[lp_district] provinceName 			:: " + provinceName);
		   logger.info("[lp_district] districtName 			:: " + districtName);
		   
		   list 		= this.addressDao.districtList(provinceName, districtName);
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_district] " + e.getMessage());
	   }finally{
		   logger.info("[lp_district][End]");
	   }
   }
   
   private void lp_subdistrict(){
	   logger.info("[subdistrict][Begin]");
	   
	   String							provinceName			= null;
	   String							districtName			= null;
	   String							subdistrictName			= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       CompanyDetailsBean 				companyDetailsBean		= null;
       
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName				= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   companyDetailsBean			= this.form.getCompanyDetailsBean();
		   
		   companyDetailsBean.setProvinceName(provinceName);
		   companyDetailsBean.setDistrictName(districtName);
		   companyDetailsBean.setSubdistrictName(subdistrictName);
		   
		   logger.info("[lp_district] provinceName 			:: " + provinceName);
		   logger.info("[lp_district] districtName 			:: " + districtName);
		   logger.info("[lp_subdistrict] subdistrict 		:: " + subdistrictName);
		   
		   list 		= this.addressDao.subdistrictList(provinceName, districtName, subdistrictName);
		   strArray 	= new String[list.size()];
		   strArray 	= list.toArray(strArray); 
		   
		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_subdistrict] " + e.getMessage());
	   }finally{
		   logger.info("[lp_subdistrict][End]");
	   }
   }
	 	
	
}
