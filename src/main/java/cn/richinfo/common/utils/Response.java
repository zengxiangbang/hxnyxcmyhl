package cn.richinfo.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Response {

	public static void Write(HttpServletResponse response, String info) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(info);
		out.flush();
		out.close();
	}

	public static void Status404(HttpServletResponse response) {
		response.setContentType("text/html;charset=UTF-8");
		response.setStatus(404);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
