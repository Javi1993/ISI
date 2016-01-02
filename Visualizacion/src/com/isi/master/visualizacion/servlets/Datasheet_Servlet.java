package com.isi.master.visualizacion.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.isi.master.visualizacion.clases.Datasheet;

/**
 * Servlet implementation class Datashet_Servlet
 */
@WebServlet("/datasheet")
public class Datasheet_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Datasheet_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		Datasheet dst = new Datasheet();
		if(request.getParameter("op").equals("1"))
		{
			dst.mostrarFiles(request);
			request.getRequestDispatcher("/datasheet.jsp").forward(request, response);
		}else if(request.getParameter("op").equals("2")){
			dst.descargarFiles(response, request.getParameter("csv"));
		}else{
			request.getRequestDispatcher("/index.html").forward(request, response);
		}
	}
}
