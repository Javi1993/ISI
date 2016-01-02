package com.isi.master.visualizacion.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isi.master.visualizacion.clases.Opcion;

/**
 * Servlet implementation class Opcion3_Servlet
 */
@WebServlet("/opcion")
public class Opcion_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public Opcion_Servlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nextPage="";
		Opcion opc = new Opcion();
		switch (request.getParameter("num")) {
		case "1":
			opc.opcion1(request);
			nextPage="/opcion1.jsp";
			break;
		case "2":
			if(!request.getParameter("provincia").equals(request.getParameter("provincia2"))){
				opc.opcion2(request);
				nextPage="/opcion2.jsp";
				request.setAttribute("provincia2",request.getParameter("provincia2"));//guardamos la provincia
			}
			break;		
		case "3":
			opc.opcion3(request);
			nextPage="/opcion3.jsp";
			break;
		default:
			nextPage="/index.html";
			System.out.println("La opción elegida de visualización no es valida");
			break;
		}

		request.setAttribute("provincia",request.getParameter("provincia"));//guardamos la provincia
		request.getRequestDispatcher(nextPage).forward(request, response);
	}
}
