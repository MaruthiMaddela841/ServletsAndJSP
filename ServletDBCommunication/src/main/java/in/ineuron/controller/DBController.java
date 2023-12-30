package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DBController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection connection=null;
	PreparedStatement ps=null;
	ResultSet resultSet=null;
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded Successfully....");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void init() throws ServletException{
		String url=getInitParameter("url");
		String username=getInitParameter("username");
		String password=getInitParameter("password");
		System.out.println("Config Object created and got the values from it......");
		try {
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null) {
				System.out.println("Connection Established Successfully......");
			}
		}
					
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		PrintWriter out=response.getWriter();
		String sqlSelectQuery="select id,name,age,address from students where id=?;";
		try {
			ps=connection.prepareStatement(sqlSelectQuery);
			if(ps!=null) {
				ps.setInt(1, id);
			}
			if(ps!=null) {
				resultSet=ps.executeQuery();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("<h1>Request Type is: "+request.getMethod()+"</h1>");
		if(resultSet!=null) {
			try {
				if(resultSet.next()) {
				out.println("<center><h1 style='color:red; text-align:center'>DB DATA</h1>");
				out.println("<table border='1' align='center'>");
				out.println("<tr><th>ID</th><th>NAME</th><th>AGE</th><th>ADDRESS</th></tr>");
				out.println("<tr><td>"+resultSet.getInt(1)+"</th><th>"+resultSet.getString(2)+"</td><td>"+resultSet.getInt(3)
				+"</th><th>"+resultSet.getString(4)+"</td></tr>");
				out.println("</table>");
				out.println("</center>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
