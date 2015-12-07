<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.List, org.bson.Document"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ISI | Grupo 2</title>
<meta charset="utf-8" />
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
						<select name="provincia1" form="provincia">
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
		<div id="footer-wrapper">
			<footer id="footer" class="container">
			<div class="row">
				<div class="3u 12u(mobile)">

					<!-- Links -->
					<section>
					<h2>Filler Links</h2>
					<ul class="divided">
						<li><a href="#">Quam turpis feugiat dolor</a></li>
						<li><a href="#">Amet ornare in hendrerit </a></li>
						<li><a href="#">Semper mod quisturpis nisi</a></li>
						<li><a href="#">Consequat etiam phasellus</a></li>
						<li><a href="#">Amet turpis, feugiat et</a></li>
						<li><a href="#">Ornare hendrerit lectus</a></li>
						<li><a href="#">Semper mod quis et dolore</a></li>
						<li><a href="#">Amet ornare in hendrerit</a></li>
						<li><a href="#">Consequat lorem phasellus</a></li>
						<li><a href="#">Amet turpis, feugiat amet</a></li>
						<li><a href="#">Semper mod quisturpis</a></li>
					</ul>
					</section>

				</div>
				<div class="3u 12u(mobile)">

					<!-- Links -->
					<section>
					<h2>More Filler</h2>
					<ul class="divided">
						<li><a href="#">Quam turpis feugiat dolor</a></li>
						<li><a href="#">Amet ornare in in lectus</a></li>
						<li><a href="#">Semper mod sed tempus nisi</a></li>
						<li><a href="#">Consequat etiam phasellus</a></li>
					</ul>
					</section>

					<!-- Links -->
					<section>
					<h2>Even More Filler</h2>
					<ul class="divided">
						<li><a href="#">Quam turpis feugiat dolor</a></li>
						<li><a href="#">Amet ornare hendrerit lectus</a></li>
						<li><a href="#">Semper quisturpis nisi</a></li>
						<li><a href="#">Consequat lorem phasellus</a></li>
					</ul>
					</section>

				</div>
				<div class="6u 12u(mobile)">

					<!-- About -->
					<section>
					<h2>
						<strong>ZeroFour</strong> by HTML5 UP
					</h2>
					<p>
						Hi! This is <strong>ZeroFour</strong>, a free, fully responsive
						HTML5 site template by <a href="http://n33.co/">AJ</a> for <a
							href="http://html5up.net/">HTML5 UP</a>. It's <a
							href="http://html5up.net/license/">Creative Commons
							Attribution</a> licensed so use it for any personal or commercial
						project (just credit us for the design!).
					</p>
					<a href="#" class="button alt icon fa-arrow-circle-right">Learn
						More</a> </section>

					<!-- Contact -->
					<section>
					<h2>Get in touch</h2>
					<div>
						<div class="row">
							<div class="6u 12u(mobile)">
								<dl class="contact">
									<dt>Twitter</dt>
									<dd>
										<a href="#">@untitled-corp</a>
									</dd>
									<dt>Facebook</dt>
									<dd>
										<a href="#">facebook.com/untitled</a>
									</dd>
									<dt>WWW</dt>
									<dd>
										<a href="#">untitled.tld</a>
									</dd>
									<dt>Email</dt>
									<dd>
										<a href="#">user@untitled.tld</a>
									</dd>
								</dl>
							</div>
							<div class="6u 12u(mobile)">
								<dl class="contact">
									<dt>Address</dt>
									<dd>
										1234 Fictional Rd<br /> Nashville, TN 00000-0000<br /> USA
									</dd>
									<dt>Phone</dt>
									<dd>(000) 000-0000</dd>
								</dl>
							</div>
						</div>
					</div>
					</section>

				</div>
			</div>
			<div class="row">
				<div class="12u">
					<div id="copyright">
						<ul class="menu">
							<li>&copy; ATMOSPHSPAIN. All rights reserved</li>
							<li>ISI | GRUPO 2</li>
						</ul>
					</div>
				</div>
			</div>
			</footer>
		</div>
	</div>
</body>
</html>