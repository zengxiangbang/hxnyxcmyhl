package cn.richinfo.common.utils;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONSerializer;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author other
 */
public class ResponseJson {
	
	public static void WriteShar(HttpServletResponse response, Object info) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(info));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static void write(HttpServletResponse response, Object info) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(JSONSerializer.toJSON(info));
        out.flush();
        out.close();
    }





    public class ExportRetInfo {
        private String ret;
        private String msg;
        private Object data;

        public String getRet() {
            return ret;
        }

        public void setRet(String ret) {
            this.ret = ret;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
//            System.out.println("data instanceof Object:"+data instanceof Object);
//            if(data instanceof Object){
//                this.data = JSONObject.fromObject(data).toString();
//            }else{
//                this.data = data;
//            }
        }

    }


    public static void main(String[] args) {
        ResponseJson.ExportRetInfo aExportRetInfo = (new ResponseJson()).new ExportRetInfo();
        aExportRetInfo.setData("ssss");

    }
}
