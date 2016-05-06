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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import th.go.stock.app.enjoy.bean.AddressBean;
import th.go.stock.app.enjoy.bean.ComboBean;
import th.go.stock.app.enjoy.bean.CustomerDetailsBean;
import th.go.stock.app.enjoy.bean.RelationGroupCustomerBean;
import th.go.stock.app.enjoy.bean.UserDetailsBean;
import th.go.stock.app.enjoy.dao.AddressDao;
import th.go.stock.app.enjoy.dao.CustomerDetailsDao;
import th.go.stock.app.enjoy.dao.RelationGroupCustomerDao;
import th.go.stock.app.enjoy.exception.EnjoyException;
import th.go.stock.app.enjoy.form.CompanyDetailsMaintananceForm;
import th.go.stock.app.enjoy.form.CustomerDetailsMaintananceForm;
import th.go.stock.app.enjoy.main.Constants;
import th.go.stock.app.enjoy.utils.EnjoyLogger;
import th.go.stock.app.enjoy.utils.EnjoyUtils;
import th.go.stock.app.enjoy.utils.HibernateUtil;
import th.go.stock.web.enjoy.common.EnjoyStandardSvc;
import th.go.stock.web.enjoy.utils.EnjoyUtil;

public class CustomerDetailsMaintananceServlet extends EnjoyStandardSvc {
	 
	static final long serialVersionUID = 1L;
	private static final EnjoyLogger logger = EnjoyLogger.getLogger(CustomerDetailsMaintananceServlet.class);
	
    private static final String 	FORM_NAME 				= "customerDetailsMaintananceForm";
    private static final String 	PROVINCE 				= "p";
    private static final String 	DISTRICT 				= "d";
    private static final String 	SUBDISTRICT 			= "s";
    
    private EnjoyUtil               		enjoyUtil                   = null;
    private HttpServletRequest          	request                     = null;
    private HttpServletResponse         	response                    = null;
    private HttpSession                 	session                     = null;
    private UserDetailsBean             	userBean                    = null;
    private CustomerDetailsDao				dao							= null;
    private CustomerDetailsMaintananceForm	form						= null;
    private AddressDao						addressDao					= null;
    private RelationGroupCustomerDao		relationGroupCustomerDao	= null;
    
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
             this.form               		= (CustomerDetailsMaintananceForm)session.getAttribute(FORM_NAME);
             this.dao						= new CustomerDetailsDao();
             this.addressDao				= new AddressDao();
             this.relationGroupCustomerDao 	= new RelationGroupCustomerDao();
 			
             logger.info("[execute] pageAction : " + pageAction );
             
 			if(this.form == null || pageAction.equals("new") || pageAction.equals("getDetail")) this.form = new CustomerDetailsMaintananceForm();
 			
 			if( pageAction.equals("") || pageAction.equals("new") ){
 				this.onLoad();
 				request.setAttribute("target", Constants.PAGE_URL +"/CustomerDetailsMaintananceScn.jsp");
 			}else if(pageAction.equals("getDetail")){
 				this.getDetail("0");
 				request.setAttribute("target", Constants.PAGE_URL +"/CustomerDetailsMaintananceScn.jsp");
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
		
		CustomerDetailsBean 	customerDetailsBean = null;
		
		try{
			customerDetailsBean = this.form.getCustomerDetailsBean();
			
			this.setRefference();
			this.form.setTitlePage("เพิ่มรายละเอียดลูกค้า");
			
			customerDetailsBean.setCusStatus("A");
			customerDetailsBean.setPoint("0");
			
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
			
			this.setCusStatus();
			this.setSexCombo();
			this.setIdTypeCombo();
			this.setGroupSalePriceCombo();
			
			
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
	
	private void setCusStatus() throws EnjoyException{
		
		logger.info("[setCusStatus][Begin]");
		
		List<ComboBean> 			comboList				= new ArrayList<ComboBean>();
		List<ComboBean> 			comboListDb				= null;
		
		try{
			
			comboListDb = this.dao.getStatusCombo();
			
			for(ComboBean bean:comboListDb){
				comboList.add(new ComboBean(bean.getCode(), bean.getDesc()));
			}
			
			this.form.setStatusCombo(comboList);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setCusStatus is error");
		}finally{
			logger.info("[setCusStatus][End]");
		}
	}	
	private void setSexCombo() throws EnjoyException{
		
		logger.info("[setCusStatus][Begin]");
		
		List<ComboBean>			sexCombo = null;
		
		try{
			
			sexCombo = new ArrayList<ComboBean>();
			
			sexCombo.add(new ComboBean(""	, "กรุณาระบุ"));
			sexCombo.add(new ComboBean("M"	, "ชาย"));
			sexCombo.add(new ComboBean("F"	, "หญิง"));
			
			this.form.setSexCombo(sexCombo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setSexCombo is error");
		}finally{
			logger.info("[setSexCombo][End]");
		}
	}
	
	private void setIdTypeCombo() throws EnjoyException{
		
		logger.info("[setIdTypeCombo][Begin]");
		
		List<ComboBean>			idTypeCombo = null;
		
		try{
			
			idTypeCombo = new ArrayList<ComboBean>();
			
			idTypeCombo.add(new ComboBean("0"	, "ไม่ระบุ"));
			idTypeCombo.add(new ComboBean("1"	, "บุคคลธรรมดา"));
			idTypeCombo.add(new ComboBean("2"	, "นิติบุคคล"));
			
			this.form.setIdTypeCombo(idTypeCombo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setIdTypeCombo is error");
		}finally{
			logger.info("[setIdTypeCombo][End]");
		}
	}
	
	private void setGroupSalePriceCombo() throws EnjoyException{
		
		logger.info("[setGroupSalePriceCombo][Begin]");
		
		List<ComboBean>					combo 						= null;
		SessionFactory 					sessionFactory				= null;
		Session 						session						= null;
		List<RelationGroupCustomerBean> list 						= null;
		RelationGroupCustomerBean		relationGroupCustomerBean 	= null;
		
		try{
			sessionFactory 					= HibernateUtil.getSessionFactory();
			session 						= sessionFactory.openSession();
			combo 					= new ArrayList<ComboBean>();
			relationGroupCustomerBean 		= new RelationGroupCustomerBean();
			list							= this.relationGroupCustomerDao.searchByCriteria(session, relationGroupCustomerBean);
			
			combo.add(new ComboBean(""	, "กรุณาเลือก"));
			for(RelationGroupCustomerBean bean:list){
				combo.add(new ComboBean(bean.getCusGroupCode()	, bean.getCusGroupName()));
			}
			
			this.form.setGroupSalePriceCombo(combo);
		}
		catch(Exception e){
			logger.info(e.getMessage());
			throw new EnjoyException("setGroupSalePriceCombo is error");
		}finally{
			session.close();
			sessionFactory	= null;
			session			= null;
			logger.info("[setGroupSalePriceCombo][End]");
		}
	}
	
	private void getDetail(String cusCode) throws EnjoyException{
		logger.info("[getDetail][Begin]");
		
		CustomerDetailsBean 	customerDetailsBean		= null;
		CustomerDetailsBean 	customerDetailsBeanDb	= null;
		
		try{
			cusCode						= cusCode.equals("0")?EnjoyUtil.nullToStr(request.getParameter("cusCode")):cusCode;
			
			logger.info("[getDetail] cusCode :: " + cusCode);
			
			customerDetailsBean = new CustomerDetailsBean();
			customerDetailsBean.setCusCode(cusCode);
			
			customerDetailsBeanDb				= this.dao.getCustomerDetail(customerDetailsBean);
			
			this.form.setTitlePage("แก้ไขรายละเอียดลูกค้า");
			this.form.setPageMode(CustomerDetailsMaintananceForm.EDIT);
			
			logger.info("[getDetail] customerDetailsBeanDb :: " + customerDetailsBeanDb);
			
			if(customerDetailsBeanDb!=null){
				this.form.setCustomerDetailsBean(customerDetailsBeanDb);
			}else{
				throw new EnjoyException("เกิดข้อผิดพลาดในการดึงรายละเอียดลูกค้า");
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
	   
   private void lp_validate(){
	   logger.info("[lp_validate][Begin]");
	   
	   String				provinceCode				= null;
	   String				districtCode				= null;
	   String				subdistrictCode				= null;
	   String				provinceName				= null;
	   String				districtName				= null;
	   String				subdistrictName				= null;
	   String				idType 						= null;
	   String				idNumber 					= null;
	   String				cusCode 					= null;
	   String				cusName 					= null;
	   String				cusSurname 					= null;
	   String				branchName					= null;
	   String				branchNameTmp				= "";
	   JSONObject 			obj 						= new JSONObject();
	   AddressBean			addressBean					= null;
	   
	   try{
		   provinceName		= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName		= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName	= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   idType 			= EnjoyUtil.nullToStr(request.getParameter("idType"));
		   idNumber 		= EnjoyUtil.nullToStr(request.getParameter("idNumber"));
		   cusCode 			= EnjoyUtil.nullToStr(request.getParameter("cusCode"));
		   cusName 			= EnjoyUtil.nullToStr(request.getParameter("cusName"));
		   cusSurname 		= EnjoyUtil.nullToStr(request.getParameter("cusSurname"));
		   branchName 		= EnjoyUtil.nullToStr(request.getParameter("branchName"));
		   
		   logger.info("[lp_validate] provinceName 			:: " + provinceName);
		   logger.info("[lp_validate] districtName 			:: " + districtName);
		   logger.info("[lp_validate] subdistrictName 		:: " + subdistrictName);
		   logger.info("[lp_validate] idType 				:: " + idType);
		   logger.info("[lp_validate] idNumber 				:: " + idNumber);
		   logger.info("[lp_validate] cusCode 				:: " + cusCode);
		   logger.info("[lp_validate] cusName 				:: " + cusName);
		   logger.info("[lp_validate] cusSurname 			:: " + cusSurname);
		   logger.info("[lp_validate] branchName 			:: " + branchName);
		   
		   if(!idType.equals("0")){
			   if(this.dao.checkDupIdNumber(idNumber, cusCode) > 0){
				   obj.put(ERR_TYPE, 			"E");
				   throw new EnjoyException("เลขที่บัตร " + idNumber + " มีอยู่ในระบบแล้ว");
			   }
		   }
		   
		   if(this.dao.checkDupCusName(cusName, cusSurname, branchName, cusCode) > 0){
			   if(!branchName.equals("")){
				   branchNameTmp = " สาขา " + branchName;
			   }
			   
			   
			   obj.put(ERR_TYPE, 			"E");
			   throw new EnjoyException("ชื่อ"+cusName + " " + cusSurname + branchNameTmp + " มีอยู่แล้วในระบบ");
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
		
		String				pageMode				= null;
		String				cusCode 				= null;
		String				cusName 				= null;
		String				cusSurname 				= null;
		String				branchName				= null;
		String				cusGroupCode			= null;
		String				sex 					= null;
		String				idType 					= null;
		String				idNumber 				= null;
		String				birthDate 				= null;
		String				religion 				= null;
		String				job 					= null;
		String				buildingName 			= null;
		String				houseNumber 			= null;
		String				mooNumber 				= null;
		String				soiName 				= null;
		String				streetName 				= null;
		String				subdistrictCode 		= null;
		String				districtCode 			= null;
		String				provinceCode 			= null;
		String				postCode 				= null;
		String				tel 					= null;
		String				fax 					= null;
		String				email 					= null;
		String				cusStatus 				= null;
		String				startDate 				= null;
		String				expDate 				= null;
		String				point 					= null;
		String				remark 					= null;
		SessionFactory 		sessionFactory			= null;
		Session 			session					= null;
		JSONObject 			obj 					= null;
		CustomerDetailsBean customerDetailsBean		= null;
		
		try{
			pageMode 					= EnjoyUtil.nullToStr(request.getParameter("pageMode"));
			cusCode 					= EnjoyUtil.nullToStr(request.getParameter("cusCode"));
			cusName 					= EnjoyUtil.nullToStr(request.getParameter("cusName"));
			cusSurname 					= EnjoyUtil.nullToStr(request.getParameter("cusSurname"));
			branchName 					= EnjoyUtil.nullToStr(request.getParameter("branchName"));
			cusGroupCode 				= EnjoyUtil.nullToStr(request.getParameter("cusGroupCode"));
			sex 						= EnjoyUtil.nullToStr(request.getParameter("sex"));
			idType 						= EnjoyUtil.nullToStr(request.getParameter("idType"));
			idNumber 					= EnjoyUtil.nullToStr(request.getParameter("idNumber")).replaceAll("-", "");
			birthDate 					= EnjoyUtil.nullToStr(request.getParameter("birthDate"));
			religion 					= EnjoyUtil.nullToStr(request.getParameter("religion"));
			job 						= EnjoyUtil.nullToStr(request.getParameter("job"));
			buildingName 				= EnjoyUtil.nullToStr(request.getParameter("buildingName"));
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
			cusStatus 					= EnjoyUtil.nullToStr(request.getParameter("cusStatus"));
			startDate 					= EnjoyUtil.nullToStr(request.getParameter("startDate"));
			expDate 					= EnjoyUtil.nullToStr(request.getParameter("expDate"));
			point 						= EnjoyUtil.nullToStr(request.getParameter("point"));
			remark 						= EnjoyUtil.nullToStr(request.getParameter("remark"));
			sessionFactory 				= HibernateUtil.getSessionFactory();
			session 					= sessionFactory.openSession();
			obj 						= new JSONObject();
			customerDetailsBean			= new CustomerDetailsBean();
			
			logger.info("[onSave] pageMode 				:: " + pageMode);
			logger.info("[onSave] cusCode 				:: " + cusCode);
			logger.info("[onSave] cusName 				:: " + cusName);
			logger.info("[onSave] cusSurname 			:: " + cusSurname);
			logger.info("[onSave] branchName 			:: " + branchName);
			logger.info("[onSave] cusGroupCode 			:: " + cusGroupCode);
			logger.info("[onSave] sex 					:: " + sex);
			logger.info("[onSave] idType 				:: " + idType);
			logger.info("[onSave] idNumber 				:: " + idNumber);
			logger.info("[onSave] birthDate 			:: " + birthDate);
			logger.info("[onSave] religion 				:: " + religion);
			logger.info("[onSave] job 					:: " + job);
			logger.info("[onSave] buildingName 			:: " + buildingName);
			logger.info("[onSave] houseNumber 			:: " + houseNumber);
			logger.info("[onSave] mooNumber 			:: " + mooNumber);
			logger.info("[onSave] soiName 				:: " + soiName);
			logger.info("[onSave] streetName 			:: " + streetName);
			logger.info("[onSave] subdistrictCode 		:: " + subdistrictCode);
			logger.info("[onSave] districtCode 			:: " + districtCode);
			logger.info("[onSave] provinceCode 			:: " + provinceCode);
			logger.info("[onSave] postCode 				:: " + postCode);
			logger.info("[onSave] tel 					:: " + tel);
			logger.info("[onSave] fax 					:: " + fax);
			logger.info("[onSave] email 				:: " + email);
			logger.info("[onSave] cusStatus 			:: " + cusStatus);
			logger.info("[onSave] startDate 			:: " + startDate);
			logger.info("[onSave] expDate 				:: " + expDate);
			logger.info("[onSave] point 				:: " + point);
			logger.info("[onSave] remark 				:: " + remark);
			
			customerDetailsBean.setCusCode				(cusCode);
			customerDetailsBean.setCusName				(cusName);
			customerDetailsBean.setCusSurname			(cusSurname);
			customerDetailsBean.setBranchName			(branchName);
			customerDetailsBean.setCusGroupCode			(cusGroupCode);
			customerDetailsBean.setSex					(sex);
			customerDetailsBean.setIdType				(idType);
			customerDetailsBean.setIdNumber				(idNumber);
			customerDetailsBean.setBirthDate			(EnjoyUtils.dateThaiToDb(birthDate));
			customerDetailsBean.setReligion				(religion);
			customerDetailsBean.setJob					(job);
			customerDetailsBean.setBuildingName			(buildingName);
			customerDetailsBean.setHouseNumber			(houseNumber);
			customerDetailsBean.setMooNumber			(mooNumber);
			customerDetailsBean.setSoiName				(soiName);
			customerDetailsBean.setStreetName			(streetName);
			customerDetailsBean.setSubdistrictCode		(subdistrictCode);
			customerDetailsBean.setDistrictCode			(districtCode);
			customerDetailsBean.setProvinceCode			(provinceCode);
			customerDetailsBean.setPostCode				(postCode);
			customerDetailsBean.setTel					(tel);
			customerDetailsBean.setFax					(fax);
			customerDetailsBean.setEmail				(email);
			customerDetailsBean.setCusStatus			(cusStatus);
			customerDetailsBean.setStartDate			(EnjoyUtils.dateThaiToDb(startDate));
			customerDetailsBean.setExpDate				(EnjoyUtils.dateThaiToDb(expDate));
			customerDetailsBean.setPoint				(point);
			customerDetailsBean.setRemark				(remark);
			
			session.beginTransaction();
			
			if(pageMode.equals(CompanyDetailsMaintananceForm.NEW)){
				this.dao.insertCustomerDetails(session, customerDetailsBean);
			}else{
				this.dao.updateCustomerDetail(session, customerDetailsBean);
			}
			
			session.getTransaction().commit();
			
			obj.put(STATUS				, SUCCESS);
			
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
			
//			this.getDetail(cusCode);
			this.enjoyUtil.writeMSG(obj.toString());
			
			sessionFactory	= null;
			session			= null;
			
			logger.info("[onSave][End]");
		}
	}	
//	
//	private void lp_province(){
//	   logger.info("[lp_province][Begin]");
//	   
//	   String							provinceName			= null;
//       List<String> 					list 					= new ArrayList<String>();
//       String[]							strArray				= null;
//       CustomerDetailsBean 				customerDetailsBean		= null;
//       
//	   try{
//		   provinceName				= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
//		   customerDetailsBean		= this.form.getCustomerDetailsBean();
//		   
//		   logger.info("[lp_province] provinceName 			:: " + provinceName);
//		   
//		   customerDetailsBean.setProvinceName(provinceName);
//		   
//		   list 		= this.addressDao.provinceList(provinceName);
//		   strArray 	= new String[list.size()];
//		   strArray 	= list.toArray(strArray); 
//		   
//		   this.enjoyUtil.writeJsonMSG((String[]) strArray);
//		   
//	   }catch(Exception e){
//		   e.printStackTrace();
//		   logger.info("[lp_province] " + e.getMessage());
//	   }finally{
//		   logger.info("[lp_province][End]");
//	   }
//   }
	
	private void lp_province(){
	   logger.info("[lp_province][Begin]");
	   
	   String							provinceName			= null;
       List<String> 					list 					= new ArrayList<String>();
       CustomerDetailsBean 				customerDetailsBean		= null;
       JSONArray 						jSONArray 				= null;
       JSONObject 						objDetail 				= null;
       int								id						= 0;
       
	   try{
		   provinceName				= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   customerDetailsBean		= this.form.getCustomerDetailsBean();
		   jSONArray 			= new JSONArray();
		   
		   logger.info("[lp_province] provinceName 			:: " + provinceName);
		   
		   customerDetailsBean.setProvinceName(provinceName);
		   
		   list 		= this.addressDao.provinceList(provinceName);
		   
		   for(String province:list){
			   objDetail 		= new JSONObject();
			   
			   objDetail.put("id"			,id);
			   objDetail.put("value"		,province);
			   
			   jSONArray.add(objDetail);
			   id++;
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
	   
	   String							provinceName			= null;
	   String							districtName			= null;
       List<String> 					list 					= new ArrayList<String>();
       String[]							strArray				= null;
       CustomerDetailsBean 				customerDetailsBean		= null;
       
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   customerDetailsBean			= this.form.getCustomerDetailsBean();
		   
		   customerDetailsBean.setProvinceName(provinceName);
		   customerDetailsBean.setDistrictName(districtName);
		   
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
       CustomerDetailsBean 				customerDetailsBean		= null;
       
	   try{
		   provinceName					= EnjoyUtils.nullToStr(this.request.getParameter("provinceName"));
		   districtName					= EnjoyUtils.nullToStr(this.request.getParameter("districtName"));
		   subdistrictName				= EnjoyUtils.nullToStr(this.request.getParameter("subdistrictName"));
		   customerDetailsBean			= this.form.getCustomerDetailsBean();
		   
		   customerDetailsBean.setProvinceName(provinceName);
		   customerDetailsBean.setDistrictName(districtName);
		   customerDetailsBean.setSubdistrictName(subdistrictName);
		   
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
