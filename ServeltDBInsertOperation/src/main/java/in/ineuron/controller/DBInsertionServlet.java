package in.ineuron.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DBInsertionServlet extends HttpServlet {
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String name=request.getParameter("name");
		int age=Integer.parseInt(request.getParameter("age"));
		String address=request.getParameter("address");
		String sqlQuery="INSERT INTO students(name, age, address) VALUES (?,?,?);";
		try {
			ps=connection.prepareStatement(sqlQuery);
			if(ps!=null) {
				ps.setString(1,name);
				ps.setInt(2, age);
				ps.setString(3, address);
				int rowsEffected=ps.executeUpdate();
				out.println("Rows Effected;"+rowsEffected);
				if(rowsEffected==1)
					out.println("success");
				else
					out.println("failure");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

	}
}
