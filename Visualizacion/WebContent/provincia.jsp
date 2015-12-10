<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.List, org.bson.Document"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ISI | Grupo 2</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
</head>
<%
	List<Document> provincias = (List<Document>) request.getAttribute("provincias");
%>
<body class="homepage">
	<div id="page-wrapper">
		<div id="header-wrapper">
			<div class="container">
				<div id="banner">
					<form method="post" action="/Visualizacion/opcion?num=<%=request.getAttribute("op")%>" id="provincia">
						<%if(request.getAttribute("op").equals("2")){ %>
						<h2>Elige dos provincias</h2>
						<select name="provincia" form="provincia">
							<% for(Document prov:provincias){%>
							<option value="<%=prov.getString("_id")%>"><%=prov.getString("_id").toUpperCase()%></option>
							<%} %>
						</select><br>
												<select name="provincia2" form="provincia">
							<% for(Document prov:provincias){%>
							<option value="<%=prov.getString("_id")%>"><%=prov.getString("_id").toUpperCase()%></option>
							<%} %>
						</select>
						<%}else{ %>
												<h2>Elige una provincia</h2>
						<select name="provincia" form="provincia">
							<% for(Document prov:provincias){%>
							<option value="<%=prov.getString("_id")%>"><%=prov.getString("_id").toUpperCase()%></option>
							<%} %>
						</select>
						<%} %>
						 <br>
						<br> <input type="submit"><br>
											</form>
						<a href="javascript:history.back()">Volver</a>
					<br>

				</div>
			</div>
		</div>
		<!-- Footer Wrapper -->
		<jsp:include page="./footer.html" flush="true" />
	</div>
</body>
</html>