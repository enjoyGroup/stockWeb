package th.go.stock.web.enjoy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.AddressBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CompanyVendorBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AddressDao;
import th.go.stock.app.enjoy.dao.CompanyVendorDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyVendorMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CompanyVendorMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CompanyVendorMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "companyVendorMaintananceForm";
    private static final String 	PROVINCE 				= "p";
    private static final String 	DISTRICT 				= "d";
    private static final String 	SUBDISTRICT 			= "s";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private CompanyVendorMaintananceForm	form						= null;
    private CompanyVendorDao				dao							= null;
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
 			 pageAction 					= EnjoyUtil.nullToStr(request.getParameter("pageAction"));
 			 this.enjoyUtil 				= new EnjoyUtil(request, response);
 			 this.request            		= request;
             this.response           		= response;
             this.session            		= request.getSession(false);
             this.userBean           		= (UserDetailsBean)session.getAttribute("userBean");
             this.form               		= (CompanyVendorMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao						= new CompanyVendorDao();
             this.addressDao				= new AddressDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new CompanyVendorMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/CompanyVendorMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/CompanyVendorMaintananceScn.jsp");
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
 			destroySession();
 			logger.info("[execute][End]");
 		}
	}
	
	private void onLoad() throws EnjoyException{
		logger.info("[onLoad][Begin]");
		
		try{
			this.form.setTitlePage("บันทึกบริษัทสั่งซื้อ");
		}catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("onLoad is error");
		}finally{			
			logger.info("[onLoad][End]");
		}
		
	}
	
	private void getDetail(String vendorCode) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		CompanyVendorBean 	companyVendorBean			= null;
		CompanyVendorBean 	companyVendorBeanDb			= null;
		String				tinCompany					= null;
		
		try{
			vendorCode	= vendorCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("vendorCode")):vendorCode;
			tinCompany	= this.userBean.getTin();
			
			
			logger.info("[getDetail] vendorCode :: " + vendorCode);
			logger.info("[getDetail] tinCompany :: " + tinCompany);
			
			this.form.setPageMode(CompanyVendorMaintananceForm.EDIT);
			this.form.setTitlePage("แก้ไขบริษัทสั่งซื้อ");
			
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
	   
   private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   String				provinceCode				= null;
	   String				districtCode				= null;
	   String				subdistrictCode				= null;
	   String				provinceName				= null;
	   String				districtName				= null;
	   String				subdistrictName				= null;
	   String				tin							= null;
	   String				vendorCode					= null;
	   String				vendorName					= null;
	   String				branchName					= null;
	   String				tinCompany					= null;
	   JSONObject 			obj 						= new JSONObject();
	   AddressBean			addressBean					= null;
	   
	   try{
		   provinceName		= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName		= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName	= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   tin				= EnjoyUtils.nullToStr(this.request.getParameter("tin"));
		   vendorCode		= EnjoyUtils.nullToStr(this.request.getParameter("vendorCode"));
		   vendorName		= EnjoyUtils.nullToStr(this.request.getParameter("vendorName"));
		   branchName		= EnjoyUtils.nullToStr(this.request.getParameter("branchName"));
		   tinCompany		= this.userBean.getTin();
		   
		   logger.info("[lp_validate] provinceName 			:: " + provinceName);
		   logger.info("[lp_validate] districtName 			:: " + districtName);
		   logger.info("[lp_validate] subdistrictName 		:: " + subdistrictName);
		   logger.info("[lp_validate] tin 					:: " + tin);
		   logger.info("[lp_validate] vendorCode 			:: " + vendorCode);
		   logger.info("[lp_validate] vendorName 			:: " + vendorName);
		   logger.info("[lp_validate] branchName 			:: " + branchName);
		   logger.info("[lp_validate] tinCompany 			:: " + tinCompany);
		   
		   if(!tin.equals("")){
			   if(this.dao.checkDupTin(tin, vendorCode, tinCompany) > 0){
				   obj.put(ERR_TYPE, 			"E");
				   throw new EnjoyException("เลขประจำตัวผู้เสียภาษี "+ tin + " มีอยู่ในระบบแล้ว");
			   }
		   }
		   
		   if(this.dao.checkDupVendorName(vendorName, branchName, vendorCode, tinCompany) > 0){
			   obj.put(ERR_TYPE, 			"E");
			   throw new EnjoyException("บริษัทกับสาขามีอยู่แล้วในระบบ");
		   }
		   
		   if(!provinceName.equals("") && !districtName.equals("") && !subdistrictName.equals("")){
			   addressBean 		= this.addressDao.validateAddress(provinceName, districtName, subdistrictName);
			   
			   if(addressBean.getErrMsg().equals("")){
				   
				   provinceCode 	= addressBean.getProvinceId();
				   districtCode 	= addressBean.getDistrictId();
				   subdistrictCode 	= addressBean.getSubdistrictId();
				   
				   logger.info("[lp_validate] provinceCode 			:: " + provinceCode);
				   logger.info("[lp_validate] districtCode 			:: " + districtCode);
				   logger.info("[lp_validate] subdistrictCode 		:: " + subdistrictCode);
				   
				   //obj.put(STATUS, 				SUCCESS);
				   obj.put("provinceCode", 		provinceCode);
				   obj.put("districtCode", 		districtCode);
				   obj.put("subdistrictCode", 	subdistrictCode);
				   
				   
			   }else{
				   obj.put(ERR_TYPE, 			addressBean.getErrType());
				   throw new EnjoyException(addressBean.getErrMsg());
			   }
		   }
		   
		   obj.put(STATUS, 				SUCCESS);
		   
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
		
		String						pageMode					= null;
		String				 		vendorCode					= null;
		String						tin							= null;
		String				 		branchName					= null;
		String				 		vendorName					= null;
		String				 		buildingName				= null;
		String				 		houseNumber					= null;
		String				 		mooNumber					= null;
		String				 		soiName						= null;
		String				 		streetName					= null;
		String				 		subdistrictCode				= null;
		String				 		districtCode				= null;
		String				 		provinceCode				= null;
		String				 		postCode					= null;
		String				 		tel							= null;
		String				 		fax							= null;
		String				 		email						= null;
		String				 		remark						= null;
		String						tinCompany					= null;
		JSONObject 					obj 						= null;
		CompanyVendorBean 			companyVendorBean			= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			vendorCode 					= EnjoyUtil.nullToStr(request.getParameter("vendorCode"));
			tin 						= EnjoyUtil.nullToStr(request.getParameter("tin")).replaceAll("-", "");
			branchName 					= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			vendorName 					= EnjoyUtil.nullToStr(request.getParameter("vendorName"));
			buildingName				= EnjoyUtil.nullToStr(request.getParameter("buildingName"));
			houseNumber 				= EnjoyUtil.nullToStr(request.getParameter("houseNumber"));
			mooNumber 					= EnjoyUtil.nullToStr(request.getParameter("mooNumber"));
			soiName 					= EnjoyUtil.nullToStr(request.getParameter("soiName"));
			streetName 					= EnjoyUtil.nullToStr(request.getParameter("streetName"));
			subdistrictCode 			= EnjoyUtil.nullToStr(request.getParameter("subdistrictCode"));
			districtCode 				= EnjoyUtil.nullToStr(request.getParameter("districtCode"));
			provinceCode 				= EnjoyUtil.nullToStr(request.getParameter("provinceCode"));
			postCode 					= EnjoyUtil.nullToStr(request.getParameter("postCode"));
			tel 						= EnjoyUtil.nullToStr(request.getParameter("tel"));
			fax 						= EnjoyUtil.nullToStr(request.getParameter("fax"));
			email 						= EnjoyUtil.nullToStr(request.getParameter("email"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			tinCompany 					= this.userBean.getTin();
			obj 						= new JSONObject();
			companyVendorBean			= new CompanyVendorBean();
			
			if(vendorCode.equals("")){
//				vendorCode = String.valueOf(this.companyVendorDao.genId(session));
				
//				companyVendorBean.setVendorCode		(vendorCode);
				companyVendorBean.setTin			(tin);
				companyVendorBean.setVendorName		(vendorName);
				companyVendorBean.setBranchName		(branchName);
				companyVendorBean.setBuildingName	(buildingName);
				companyVendorBean.setHouseNumber	(houseNumber);
				companyVendorBean.setMooNumber		(mooNumber);
				companyVendorBean.setSoiName		(soiName);
				companyVendorBean.setStreetName		(streetName);
				companyVendorBean.setProvinceCode	(provinceCode);
				companyVendorBean.setDistrictCode	(districtCode);
				companyVendorBean.setSubdistrictCode(subdistrictCode);
				companyVendorBean.setPostCode		(postCode);
				companyVendorBean.setTel			(tel);
				companyVendorBean.setFax			(fax);
				companyVendorBean.setEmail			(email);
				companyVendorBean.setRemark			(remark);
				companyVendorBean.setTinCompany		(tinCompany);
				
				this.dao.insertCompanyVendor(companyVendorBean);
			}else{
				companyVendorBean.setVendorCode		(vendorCode);
				companyVendorBean.setTin			(tin);
				companyVendorBean.setVendorName		(vendorName);
				companyVendorBean.setBranchName		(branchName);
				companyVendorBean.setBuildingName	(buildingName);
				companyVendorBean.setHouseNumber	(houseNumber);
				companyVendorBean.setMooNumber		(mooNumber);
				companyVendorBean.setSoiName		(soiName);
				companyVendorBean.setStreetName		(streetName);
				companyVendorBean.setProvinceCode	(provinceCode);
				companyVendorBean.setDistrictCode	(districtCode);
				companyVendorBean.setSubdistrictCode(subdistrictCode);
				companyVendorBean.setPostCode		(postCode);
				companyVendorBean.setTel			(tel);
				companyVendorBean.setFax			(fax);
				companyVendorBean.setEmail			(email);
				companyVendorBean.setRemark			(remark);
				companyVendorBean.setTinCompany		(tinCompany);
				
				this.dao.updateCompanyvendor(companyVendorBean);
			}
			
			commit();
			
			obj.put(STATUS, 			SUCCESS);
			
		}catch(EnjoyException e){
			rollback();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		e.getMessage());
		}catch(Exception e){
			rollback();
			logger.info(e.getMessage());
			e.printStackTrace();
			obj.put(STATUS, 		ERROR);
			obj.put(ERR_MSG, 		"onSave is error");
		}finally{
			this.enjoyUtil.writeMSG(obj.toString());
			
			logger.info("[onSave][End]");
		}
	}
	
	private void lp_province(){
	   logger.info("[lp_search][Begin]");
	   
	   String				provinceName		= null;
       List<ComboBean> 		list 				= null;
       JSONArray 			jSONArray 			= new JSONArray();
       JSONObject 			objDetail 			= null;
       
	   try{
		   provinceName				= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   
		   logger.info("[lp_province] provinceName 			:: " + provinceName);
		   
		   list 		= this.addressDao.provinceList(provinceName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_province] " + e.getMessage());
	   }finally{
		   logger.info("[lp_province][End]");
	   }
   }
	   
	private void lp_district(){
	   logger.info("[lp_district][Begin]");
	   
	   String				provinceName		= null;
	   String				districtName		= null;
	   List<ComboBean> 		list 				= null;
	   JSONArray 			jSONArray 			= new JSONArray();
	   JSONObject 			objDetail 			= null;
    
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   
		   logger.info("[lp_district] provinceName 			:: " + provinceName);
		   logger.info("[lp_district] districtName 			:: " + districtName);
		   
		   list 		= this.addressDao.districtList(provinceName, districtName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_district] " + e.getMessage());
	   }finally{
		   logger.info("[lp_district][End]");
	   }
	}

	private void lp_subdistrict(){
	   logger.info("[subdistrict][Begin]");
	   
	   String				provinceName		= null;
	   String				districtName		= null;
	   String				subdistrictName		= null;
	   List<ComboBean> 		list 				= null;
	   JSONArray 			jSONArray 			= new JSONArray();
	   JSONObject 			objDetail 			= null;
    
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName				= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   
		   logger.info("[lp_district] provinceName 			:: " + provinceName);
		   logger.info("[lp_district] districtName 			:: " + districtName);
		   logger.info("[lp_subdistrict] subdistrict 		:: " + subdistrictName);
		   
		   list 		= this.addressDao.subdistrictList(provinceName, districtName, subdistrictName);
		   
		   for(ComboBean bean:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,bean.getCode());
			   objDetail.put("value"		,bean.getDesc());
			   
			   jSONArray.add(objDetail);
		   }
		   
		   this.enjoyUtil.writeMSG(jSONArray.toString());
		   
	   }catch(Exception e){
		   e.printStackTrace();
		   logger.info("[lp_subdistrict] " + e.getMessage());
	   }finally{
		   logger.info("[lp_subdistrict][End]");
	   }
	}

	@Override
	public void destroySession() {
		this.dao.destroySession();
        this.addressDao.destroySession();
	}
	
	@Override
	public void commit() {
		this.dao.commit();
        this.addressDao.commit();
	}
	
	@Override
	public void rollback() {
		this.dao.rollback();
        this.addressDao.rollback();
	}	
	
}

