package th.go.stock.app.enjoy.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class EnjoyUtils {
	
	public static void main(String[] args) {
        
//		currDateThai();
//		System.out.println(dateToStringThai(date));
//		genPassword();

	}
	
	public static String nullToStr(String str){
        return (str==null?"":str.trim());
    }

	public static String nullToStr(String str, String strRep){
        return (str==null?strRep:str.trim());
    }
	
	public static String nullToStrUpperCase(String str){
        return (str==null?"":str.trim().toUpperCase());
    }
	
    public static String dateToString(Date dDate, String stFormat){
        String stDate = "";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(stFormat,Locale.US);
            stDate = sdf.format(dDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return stDate;
    }
    
    public static String dateToStringThai(Date dDate){
        String 	stDate 	= "";
        int 	year 	= 0;
        int 	month 	= 0;
        int 	day 	= 0;
        try{
//            SimpleDateFormat 	sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            Calendar 			c 	= Calendar.getInstance(Locale.US);
            c.setTime(dDate);
            
            
            year 	= c.get(Calendar.YEAR);
			month 	= c.get(Calendar.MONTH) + 1;
			day 	= c.get(Calendar.DATE);
            
//            stDate = sdf.format(dDate);
            stDate = String.format("%02d/%02d/%04d", day, month, year+543);
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return stDate;
    }
    
    public static String currDateThai(){
        String 	stDate 	= "";
        Date 	date	= new Date();
        
        try{
            java.text.SimpleDateFormat df= new java.text.SimpleDateFormat("yyyyMMdd", new Locale("th", "TH"));
            
//            df.applyPattern("yyyyMMdd");
            stDate = df.format(date);
            System.out.println(stDate);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return stDate;
    }
    
    public static String dateFormat (String av_date, String av_currFormat, String av_toFormat){
        System.out.println("[FormatUtil][dateFormat][Begin]");
        
        SimpleDateFormat    dt              = null;
        Date                date            = null;
        SimpleDateFormat    dt1             = null;
        String              dateFormat      = null;
        
        try{
            if(av_date==null || av_date.equals("")){
                dateFormat = "";
            }else{
                dt      = new SimpleDateFormat(av_currFormat); 
                date    = dt.parse(av_date); 
//                dt1     = new SimpleDateFormat(av_toFormat,Locale.US);// เน€เธ�๏ฟฝ.เน€เธ�เธ�.
                dt1     = new SimpleDateFormat(av_toFormat, new Locale("th", "TH"));//เน€เธ�๏ฟฝ.เน€เธ�เธ�.
                
                dateFormat = dt1.format(date);
            }
        }catch(Exception e){
                e.printStackTrace();
        }finally{
            System.out.println("[FormatUtil][dateFormat][End]");
        }
        
        return dateFormat;
    }
    
    public static String  sanitizeURLString(String av_val){
        
        System.out.println("[FormatUtil][sanitizeURLString][Begin]");
        
        String[]    la_symbol   = {"%" , "<" , ">" , "#" , "{" , "}" , "|" , "\\" , "^" , "~" , "[" , "]" , "`" , ";" , "/" , "?" , ":" , "@" , "=" , "&" , "$"};
        String[]    la_replace  = {"%25", "%3C", "%3E", "%23", "%7B", "%7D", "%7C", "%5C", "%5E", "%7E", "%5B", "%5D", "%60", "%3B", "%2F", "%3F", "%3A", "%40", "%3D", "%26", "%24"};
        String      lv_return   = "";
        String      lv_char     = "";
        
        try{
            loop1:for(int i=0;i<av_val.length();i++){
                lv_char = av_val.substring(i, (i+1));
                loop2:for(int j=0;j<la_symbol.length;j++){
                    if(lv_char.indexOf(la_symbol[j]) > -1){
                        lv_char = lv_char.replaceAll(la_symbol[j], la_replace[j]);
                        break loop2;
                    }
                }
                lv_return = lv_return + lv_char;
            }
            
            System.out.println("[FormatUtil][sanitizeURLString] lv_return :: " + lv_return);
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            System.out.println("[FormatUtil][sanitizeURLString][End]");
        }
        
        return lv_return;
    }
    
	public static String convertFloatToDisplay(String stFloat,int point){

		if (stFloat!=null&&!stFloat.equals("")){
			String strFormat = "##,##0";
			if (point > 0) { strFormat = strFormat + "."; }
			for(int i=0;i<point;i++){
				strFormat = strFormat + "0";	
			}		
			DecimalFormat df	= new DecimalFormat(strFormat);			
			stFloat 			= df.format(Double.parseDouble(stFloat));	
		}else{
			stFloat = "0.00";
		}

		return stFloat;
	}

	/**
	 * @param stData
	 * @return String
	 * convert Thai format
	 */
	public static String convertDataThai(String stData){
		String lv_strData 	 = nullToStr(stData);
		StringBuffer strTemp = new StringBuffer(lv_strData);		
		int maxLength 		 = lv_strData.length();
		int code;

		for( int i = 0; i < maxLength; i++){ 
			code = (int) strTemp.charAt(i); 
			if ( ( 0xA1 <= code ) && ( code <= 0xFB ) ){ 
				strTemp.setCharAt( i, (char) ( code + 0xD60 ) ); 
			} 
		} 
		return strTemp.toString(); 
	}
	/*
	public static String getCustNext(String code) {
    	StringBuffer codeReturn = new StringBuffer("CUS000");
    	String lv_strData 	 = nullToStr(code); 	
		int maxLength 		 = lv_strData.length();
		System.out.println("maxLength:"+maxLength);
		if(maxLength>=7){
			String lastRef       = lv_strData.substring(3);
	    	int codeInt  = Integer.parseInt(lastRef);
	    	codeInt++;
	    	System.out.println("codeInt:"+codeInt);
	    	codeReturn = codeReturn.append(String.valueOf(codeInt)); 
	    	System.out.println("codeReturn:"+codeReturn);
		}
        return codeReturn.toString();
    }*/
	
    public static String getCustNext(String code) {
    	String returnValue      = "";
    	String lv_strData 	    = nullToStr(code); 
    	int codeInt  = Integer.parseInt(lv_strData);
    	codeInt++;
    	System.out.println("codeInt:"+codeInt);
    	returnValue             = String.valueOf(codeInt); 
        return returnValue;
    }
    
    public static String displayAmountThai(String amt)
    {
        String CurText,Thai_CurText = "";
        char ch;
        String st_ch;
        int idx,Digit;

        String[] GetNum   = { "เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ–เน€เธ�๏ฟฝเน€เธ�๏ฟฝ","เน€เธ�เธ�เน€เธ�เธ�เน€เธ�๏ฟฝ","เน€เธ�เธ�เน€เธ�เธ’เน€เธ�เธ�","เน€เธ�เธ�เน€เธ�เธ•เน€เธ�๏ฟฝ","เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ’","เน€เธ�เธ�เน€เธ�๏ฟฝ","เน€เธ�โ�ฌเน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�โ€�","เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�โ€�","เน€เธ�โ�ฌเน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�เธ’","เน€เธ�เธ�เน€เธ�เธ•เน€เธ�๏ฟฝ","เน€เธ�โ�ฌเน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�โ€�" };
        String[] GetDigit = { "","เน€เธ�เธ�เน€เธ�เธ”เน€เธ�๏ฟฝ","เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ�","เน€เธ�๏ฟฝเน€เธ�เธ‘เน€เธ�๏ฟฝ","เน€เธ�เธ�เน€เธ�เธ�เน€เธ�เธ—เน€เธ�๏ฟฝเน€เธ�๏ฟฝ","เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝ","เน€เธ�เธ…เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�๏ฟฝ"};

        CurText = amt;
        Digit = 0;
        for ( idx = CurText.length() ; idx >= 1 ;idx-- )
        {
           if ( Digit == 6 ){
              Digit = 0;
              Thai_CurText = "เน€เธ�เธ…เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�๏ฟฝ" + Thai_CurText;
           }

           ch      = CurText.substring(idx-1,idx).toCharArray()[0];
           st_ch   = CurText.substring(idx-1,idx);
           switch (ch){
              case '.' :  Digit = 0;
                          Thai_CurText = "เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�โ€”" + Thai_CurText;
                          break;
              case ',' :  break;

              case '0' :  Digit = Digit + 1;
                          break;
              case '1' :  Digit = Digit + 1;
                      switch (Digit){
                          case 1 : if ( st_ch.equals(CurText.substring(0,idx))) {
                                       Thai_CurText = GetNum[0] + Thai_CurText;
                                   }else if (! CurText.substring(idx-1,idx).equals("0") ){
                                	   if(CurText.substring(idx-2,idx-1).equals("0")){
                                		   Thai_CurText = GetNum[0] + Thai_CurText;
                                	   }else{
                                		   Thai_CurText = GetNum[10] + Thai_CurText;
                                	   }
                                   }else {
                                       Thai_CurText = GetNum[0] + Thai_CurText;
                                   }
                                   break;
                          case 2 : Thai_CurText = GetDigit[Digit-1] + Thai_CurText;
                                   break;
                          case 3 : Thai_CurText = GetNum[0] + GetDigit[Digit-1] + Thai_CurText;
                                   break;
                          case 4 : Thai_CurText = GetNum[0] + GetDigit[Digit-1] + Thai_CurText;
                                   break;
                          case 5 : Thai_CurText = GetNum[0] + GetDigit[Digit-1] + Thai_CurText;
                                   break;
                          case 6 : Thai_CurText = GetNum[0] + GetDigit[Digit-1] + Thai_CurText;
                                   break;
                          }
                          break;
              case '2' :  Digit = Digit + 1;
                          if ( Digit == 2 ) {
                              Thai_CurText = GetNum[9] + GetDigit[Digit-1] + Thai_CurText;
                          }else{
                              Thai_CurText = GetNum[1] + GetDigit[Digit-1] + Thai_CurText;
                          }
                          break;
              default  :  Digit = Digit + 1;
                          Thai_CurText = GetNum[Integer.parseInt(st_ch)-1] + GetDigit[Digit-1] + Thai_CurText;
                          break;

           }
        }

        int pos_dot = CurText.indexOf(".");
        if (Integer.parseInt(CurText.substring(pos_dot+1,CurText.length())) == 0 ){
        	if(amt.equals("0.00")){
        		Thai_CurText = "เน€เธ�เธ�เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝ" + Thai_CurText + "เน€เธ�โ€“เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝ";
        	}else{
        		Thai_CurText = Thai_CurText + "เน€เธ�โ€“เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝ";
        	}
        }else{
        	Thai_CurText = Thai_CurText + "เน€เธ�เธ�เน€เธ�โ€ขเน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�๏ฟฝ";
        }
        return Thai_CurText;
    }
    
	public static String displayDateThai(String dateThai) {
        Hashtable tblMonth = new Hashtable();
        String    day      = "";
        int       month    = 0;
        int       year     = 0;
        String	  dateDisplay = "";
        try {
        	if (!dateThai.equals(""))
			{	
	            tblMonth.put("1", "เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("2", "เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ�เน€เธ�ย เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ‘เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�๏ฟฝ");
	            tblMonth.put("3", "เน€เธ�เธ�เน€เธ�เธ•เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("4", "เน€เธ�โ�ฌเน€เธ�เธ�เน€เธ�เธ�เน€เธ�เธ’เน€เธ�เธ�เน€เธ�๏ฟฝ");
	            tblMonth.put("5", "เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ�เน€เธ�ย เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("6", "เน€เธ�เธ�เน€เธ�เธ”เน€เธ�โ€“เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�เธ�เน€เธ�๏ฟฝ");
	            tblMonth.put("7", "เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("8", "เน€เธ�เธ�เน€เธ�เธ”เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("9", "เน€เธ�๏ฟฝเน€เธ�เธ‘เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ’เน€เธ�เธ�เน€เธ�๏ฟฝ");
	            tblMonth.put("10","เน€เธ�โ€ขเน€เธ�เธ�เน€เธ�เธ…เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	            tblMonth.put("11","เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�เธ”เน€เธ�๏ฟฝเน€เธ�เธ’เน€เธ�เธ�เน€เธ�๏ฟฝ");
	            tblMonth.put("12","เน€เธ�๏ฟฝเน€เธ�เธ‘เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ’เน€เธ�๏ฟฝเน€เธ�เธ�");
	
	            day    = dateThai.substring(6, 8);
				month  = Integer.parseInt(dateThai.substring(4, 6));
		        year   = Integer.parseInt(dateThai.substring(0, 4));
		        dateDisplay = day + " " + tblMonth.get(String.valueOf(month)).toString() +" เน€เธ�๏ฟฝ.เน€เธ�เธ�. " + year;
			} else {
				dateDisplay = "";			
			}
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            tblMonth = null;
        }
        return dateDisplay;
	}
	
	public static String replaceComma(String av_val) {
		
		String lv_ret = null;
		
        try {
        	if (av_val!=null&&!av_val.equals("")){	
        		lv_ret = av_val.replaceAll(",", "");
			} else {
				lv_ret = "";			
			}
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return lv_ret;
	}
	
	public static String chkBoxtoDb(String av_val) {
		
		String lv_ret = null;
		
        try {
        	if (av_val!=null&&!av_val.equals("")){	
        		lv_ret = av_val;
			} else {
				lv_ret = "N";			
			}
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return lv_ret;
	}
	
	public static int parseInt(String av_val) {
		
		int lv_ret = 0;
		
        try {
        	if (av_val!=null&&!av_val.equals("")) lv_ret = Integer.parseInt(av_val);
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return lv_ret;
	}
	
	public static Double parseDouble(String av_val) {
		
		Double lv_ret = 0.00;
		
        try {
        	if (av_val!=null&&!av_val.equals("")){	
        		lv_ret = Double.parseDouble(av_val);
			} 
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return lv_ret;
	}
	
	public static String genPassword(int charLength){
		Random r 			= new Random();
		String newPass		= "";
		String alphabet 	= "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Prints 50 random characters from alphabet
		for (int i = 0; i < charLength; i++){
			newPass = newPass.concat(String.valueOf(alphabet.charAt(r.nextInt(alphabet.length()))));
			
		}
		
		return newPass;
		
	}
	
	
	public static List<String> getListFromArr(String dataDelete){
		 List<String>  returnList = null;
		 String        lv_value   = null;
		   try {
	        	if(dataDelete != null && dataDelete != ""){	
	        		returnList = new ArrayList();
	        		lv_value = dataDelete.replaceAll("\\s", ""); 
	        		String[] parts = lv_value.split(",");
	        		for(String s : parts){
	        		    returnList.add(s);
	        		}
				} 
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 
	        return returnList;
		
	}
	
	public static boolean checkRowName(String rowName,int rows){
		System.out.println("rowName:"+rowName);	
		System.out.println("rows:"+rows);	
		
		boolean flag = false;
		 String        lv_value   = null;
		   try {
	        	if(rowName != null && rowName != ""){	 
	        		lv_value = rowName.replaceAll("\\s", ""); 
	        		String[] parts = lv_value.split(",");
	        		System.out.println("parts:"+parts.length);	
    				
	        		if(parts.length != rows){
	        		   flag = true;
	        		}
				}else{
					 flag = true;
				}
	        } catch (Exception e) {
	        	e.printStackTrace();
	        } 
		   
	        return flag;
		
	}
	
	//เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�เธ…เน€เธ�๏ฟฝเน€เธ�เธ�เน€เธ�เธ�เน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�๏ฟฝเน€เธ�โ�ฌเน€เธ�เธ�เน€เธ�เธ…เน€เธ�เธ’ Ex av_time = "1725"
	public static String formatTime(String av_time) throws Exception{
		
		String t_formay		= "00:00";
		
		try{
			
			if(av_time!=null && !av_time.equals("") && av_time.length()==4){
				t_formay = av_time.substring(0, 2) + ":" + av_time.substring(2, 4);
			}
			
		}catch(Exception e){
			throw e;
		}
		
		return t_formay;
		
	}

	public static String dateToThaiDB(String dDate){
        String 	stDate 	= "";
        String  day     = "";
        String  month   = "";
        String  year    = "";
        String  dateDB  = "";
        try{  
        	dDate  = dDate.replaceAll("\\s", ""); 
        	stDate = dDate.replaceAll("/", "");  
        	day    = stDate.substring(0, 2);
			month  = stDate.substring(2, 4);
		    year   = stDate.substring(4, 8);
		    dateDB = year+month+day;
		    System.out.println("EnjoyUtils dateToThaiDB : "+ dateDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return dateDB;
    }
    
    public static String dateToThaiDisplay(String dDate){
        String 	stDate 	= "";
        String  day     = "";
        String  month   = "";
        String  year    = "";
        String  display  = "";
        try{     
            day    = dDate.substring(6, 8);
       		month  = dDate.substring(4, 6);
       		year   = dDate.substring(0, 4);
       		display = day+"/"+month+"/"+year;
		    System.out.println("EnjoyUtils dateToThaiDisplay : "+ display);
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return display;
    }
    
    public static String timeToDB(String dTime){
        String 	stDate 	= ""; 
        try{ 
        	dTime  = dTime.replaceAll("\\s", ""); 
        	stDate = dTime.replaceAll(":", "");  
        } catch (Exception e) {
            e.printStackTrace();
        }
           
        return stDate;
    }
    
    public static String timeToDisplay(String dTime){
        String 	display         = "";   
        String lv_time			= "";
        String lv_timeTmp		= "";
        int    lv_txtLength	    = 0;

    	try{ 
    		lv_time		=  dTime ;

    		if(lv_time != "") {
    			lv_txtLength = lv_time.length();
    			if(lv_txtLength==3 && lv_time.indexOf(":") < 0){
    				lv_timeTmp		= lv_time.substring(0, 1) + ":" + lv_time.substring(1, 3);
    				display	        = lv_timeTmp;
    			}else if(lv_txtLength==4 && lv_time.indexOf(":") < 0){
    				lv_timeTmp		= lv_time.substring(0, 2) + ":" + lv_time.substring(2, 4);
    				display	        = lv_timeTmp;
    			}else{
    				display         = lv_time;
    			}
 
    		} 

    	 } catch (Exception e) {
             e.printStackTrace();
         }
    	
        return display;
    }

     
	
}
