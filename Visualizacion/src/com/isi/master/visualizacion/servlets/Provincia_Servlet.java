package com.isi.master.visualizacion.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.isi.master.visualizacion.clases.Provincia;

/**
 * Servlet implementation class Provincia_Servlet
 */
@WebServlet("/provincia")
public class Provincia_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Provincia_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Provincia prov = new Provincia();
		prov.selectProvincia(request);

		request.getRequestDispatcher("/provincia.jsp").forward(request, response);
	}

}
