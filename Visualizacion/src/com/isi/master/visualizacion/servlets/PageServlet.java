package com.isi.master.visualizacion.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/page")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("n").equals("0"))
		{//pagina anterior
			request.setAttribute("cnt", Integer.parseInt(request.getParameter("p"))-5);
		}else if(request.getParameter("n").equals("1"))
		{//pagina siguiente
			request.setAttribute("cnt", Integer.parseInt(request.getParameter("p"))+5);
		}
		request.setAttribute("tweets", request.getSession().getAttribute("tweets"));
		request.setAttribute("hashTagDate", request.getSession().getAttribute("hashTagDate"));
		request.setAttribute("hashTag", request.getSession().getAttribute("hashTag"));
		request.setAttribute("feeling", request.getSession().getAttribute("feeling"));
		request.setAttribute("provincia",request.getSession().getAttribute("provincia"));//guardamos la provincia
		request.getRequestDispatcher("/opcion1.jsp").forward(request, response);
	}

}
