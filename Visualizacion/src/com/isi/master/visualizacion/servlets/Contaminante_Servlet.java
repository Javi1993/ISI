package com.isi.master.visualizacion.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.isi.master.visualizacion.clases.Contaminante;

/**
 * Servlet implementation class Contaminante_Servlet
 */
@WebServlet("/contaminante")
public class Contaminante_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Contaminante_Servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Contaminante cont = new Contaminante();
		cont.infoContaminantes(request);
		request.getRequestDispatcher("/contaminante.jsp").forward(request, response);
	}
}
