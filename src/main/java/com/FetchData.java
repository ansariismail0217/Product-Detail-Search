package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.xdevapi.Result;

@WebServlet("/fetch")
public class FetchData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		
		Properties props = new Properties();
		
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		props.load(in);
		
		Connection con = DatabaseConfig.getConnection(props);
		
		if(con!=null) {
			try {
				PreparedStatement stmt=con.prepareStatement("select * from product where id=?");
				stmt.setString(1, id);
				ResultSet rs= stmt.executeQuery();
				if(rs.isBeforeFirst()) {
				while(rs.next()) {
					out.print("<h3>Product Details</h3>");
					out.print("<br>"+"ID:  "+rs.getInt(1)+
							"<br>"+"Name:  "+rs.getString(2)+
							"<br>"+"Type:  "+rs.getString(3)+
							"<br>"+"Price: "+"Rs. "+rs.getDouble(4));
				}
				}
				else 
					out.print("Product with this ID does not exist.");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			out.print("Error while connecting");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
