<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, org.bson.Document"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Left Sidebar - ZeroFour by HTML5 UP</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
</head>
<body class="left-sidebar">
	<div id="page-wrapper">

		<!-- Header Wrapper -->
		<div id="header-wrapper">
			<div class="container">

				<!-- Header -->
				<header id="header">
				<div class="inner">

					<!-- Logo -->
					<h1>
						<a href="index.html" id="logo">GRUPO 2 | ISI</a>
					</h1>

					<!-- Nav -->
					<nav id="nav">
					<ul>
						<li><a href="index.html">Home</a></li>
						<li><a href="#">Dropdown</a>
							<ul>
								<li><a href="#">Lorem ipsum dolor</a></li>
								<li><a href="#">Magna phasellus</a></li>
								<li><a href="#">Phasellus consequat</a>
									<ul>
										<li><a href="#">Lorem ipsum dolor</a></li>
										<li><a href="#">Phasellus consequat</a></li>
										<li><a href="#">Magna phasellus</a></li>
										<li><a href="#">Etiam dolore nisl</a></li>
									</ul></li>
								<li><a href="#">Veroeros feugiat</a></li>
							</ul></li>
						<li class="current_page_item"><a href="left-sidebar.html">Left
								Sidebar</a></li>
						<li><a href="right-sidebar.html">Right Sidebar</a></li>
						<li><a href="no-sidebar.html">No Sidebar</a></li>
					</ul>
					</nav>

				</div>
				</header>

			</div>
		</div>

		<!-- Main Wrapper -->
		<div id="main-wrapper">
			<div class="wrapper style2">
				<div class="inner">
					<div class="container">
						<div class="row">
							<div class="4u 12u(mobile)">
								<div id="sidebar">
									<!-- Sidebar -->
									<section>
									<div id="menu">
										<ul>
											<%	List<Document> listProv = (List<Document>)request.getAttribute("list");
												String provincia = listProv.get(0).getString("_id");
												for (Document prov:listProv) {%>
											<li><a class="selector" id='a<%=prov.getString("_id").replaceAll("\\s+","")%>' style="cursor:pointer;"><%=prov.getString("_id")%></a></li>
											<%}%>
										</ul>
									</div>
									</section>
								</div>
							</div>
																<div class="8u 12u(mobile) important(mobile)">
										<div id="content">

											<!-- Content -->

												<article>
													<header class="major">
														<h2 id="prov"><%=provincia %></h2>
														<p>Descargue las medidas realizadas por las siguientes estaciones de calidad del aire</p>
													</header>
													<%for (Document prov:listProv) {%>
													<ul class="provincias" id='<%=prov.getString("_id").replaceAll("\\s+","")%>'>
														<%for(String estaciones:(List<String>)prov.get("estaciones")){ %>
													  <li><a href="#"><%=estaciones+".csv" %></a></li>
													  <%} %>
													</ul>
													<%} %>
												</article>

										</div>
									</div>
						</div>
					</div>
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
							<li>&copy; Untitled. All rights reserved</li>
							<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
						</ul>
					</div>
				</div>
			</div>
			</footer>
		</div>

	</div>

	<!-- Scripts -->

	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.dropotron.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="assets/js/main.js"></script>
</body>
	<script>
$( ".selector" ).click(function() {
	$( ".provincias" ).css("display","none");
	$('#'+$(this).attr('id').substring(1)).css("display","inline");
	$('#prov').html($(this).attr('id').substring(1));
	});
	
//$( ".provincias" ).css("display","none");
$( '#<%=provincia%>' ).css("display","inline");
</script>
<style>
#menu ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	width: 200px;
	font-family: Arial, sans-serif;
	font-size: 11pt;
}

#menu ul li {
	background-color: #2b2e35;
	inset: 0px 0px 0px 1px rgba(0,0,0,0.45), inset 0px 2px 1px 0px rgba(255,255,255,0.15);
	border-bottom: 1px solid;
	text-shadow: -1px -1px 0px rgba(0,0,0,0.5);
	border: 2px solid #3b464c;
    border-radius: 25px;
}

#menu ul li a {
	color: white;
	text-decoration: none;
	text-transform: uppercase;
	display: block;
	padding: 10px 10px 10px 20px;
}

#menu ul li a:hover {
	background: #28292e;
	border-left: 10px solid #333;
	color: #fff;
	border: 2px solid #3b464c;
    border-radius: 25px;
}
</style>
</html>