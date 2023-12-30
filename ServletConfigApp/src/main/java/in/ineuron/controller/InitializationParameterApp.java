package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InitializationParameterApp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<center><h1>Initialization Parameters</h1>");
		out.println("<table border='1' align='center'>");
		out.println("<tr><th>Parameter Name</th><th>Parameter Value</th></tr>");
		Enumeration<String> parameterNames=getInitParameterNames();
		while(parameterNames.hasMoreElements()) {
			String parameterName=(String)parameterNames.nextElement();
			String value=getInitParameter(parameterName);
			out.println("<tr><td>"+parameterName+"</th><th>"+value+"</td></tr>");
		}
		out.println("</table>");
		out.println("</center>");
	}

}
