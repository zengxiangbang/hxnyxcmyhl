package cn.richinfo.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class NetUtil {
	private static final String v_url = "";
	
	public static void main(String[] args) {
		for(int i=0;i<1000;i++) {
			String orderId = RandonNum.getrandom(6);
			System.out.println(orderId);
		}
	}
	public static String sendGet1(String v_methodname, String v_urlparam) throws IOException{
	      PrintWriter out = null;
	      String result = "";
	      URL url = new URL(v_url + v_methodname+"?"+v_urlparam);
	      URLConnection conn = url.openConnection();
	      conn.setRequestProperty("accept", "*/*");
	      conn.setRequestProperty("Accept-Charset", "UTF-8");
	      conn.setRequestProperty("contentType", "UTF-8");
	      conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
	      conn.setRequestProperty("Accept-Language", Locale.getDefault().toString());
	      conn.setRequestProperty(
	            "Accept", 
	            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
	      conn.setConnectTimeout(5000);
	      conn.setReadTimeout(5000);
	      conn.setDoOutput(true);
	      //String encoding = System.getProperty("file.encoding");
	      out = new PrintWriter(conn.getOutputStream());
	      //out.print(v_urlparam);
	      out.flush();
	      out.close();
	      result = readStrByCode(conn.getInputStream(), "UTF-8");
	      return result;
	  }

	  public static String readStrByCode(InputStream is, String code) {
          StringBuilder builder = new StringBuilder();
		  BufferedReader reader = null;
		  String line="";
		  try{
		        reader = new BufferedReader(new InputStreamReader(is, code));
		        while ((line = reader.readLine()) != null){
		              builder.append(line);
		        }
		  } catch (Exception e) {
			  e.printStackTrace();
			  try{
				  reader.close();
			  }catch (IOException e1) {
				  e1.printStackTrace();
			  }
		  }finally{
			  try{
				  reader.close();
			  }catch (IOException e) {
				  e.printStackTrace();
			  }
		  }
		  return builder.toString();
	  }
}
