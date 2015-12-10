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
		<jsp:include page="./header.html" flush="true" />
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
											<li><a class="selector" name="<%=prov.getString("_id")%>" id='a<%=prov.getString("_id").replaceAll("\\s+","")%>' style="cursor:pointer;"><%=prov.getString("_id")%></a></li>
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
													<%	List<Document> geoList = (List<Document>)request.getAttribute("geo");
														for(Document geo:geoList){ %>
													<span class="map" id="map<%=geo.getString("_id").replaceAll("\\s+","")%>"><%if(geo.get("Geo")==null){%><p style="color:red;">No hay mapa de geolocalización de estaciones para <%=geo.getString("_id")%></p><%}else{%><%=geo.get("Geo")%><%}%></span>
													<%} %>
													<div class="column">
													<%for (Document prov:listProv) {%>
													<ul class="provincias" id='<%=prov.getString("_id").replaceAll("\\s+","")%>'>
														<%for(String estaciones:(List<String>)prov.get("estaciones")){ %>
													  <li><a href="/Visualizacion/datasheet?csv=<%=estaciones%>&op=2"><%=estaciones+".csv" %></a></li>
													  <%} %>
													</ul>
													<%} %>
													</div>
												</article>

										</div>
									</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="./footer.html" flush="true" />
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
	$( ".map" ).css("display","none");
	$('#'+$(this).attr('id').substring(1)).css("display","inline");
	$('#map'+$(this).attr('id').substring(1)).css("display","inline");
	$('#prov').html($(this).attr('name'));
	});

$( ".map" ).css("display","none");
$( ".provincias" ).css("display","none");
$( '#<%=provincia%>' ).css("display","inline");
$( '#map<%=provincia%>' ).css("display","inline");
$( "li#d" ).addClass( "current_page_item" );
</script>
<style>
#menu ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	text-align: center;
	width: 200px;
	font-size: 9pt;
	font-weight: bold;
		border: 2px solid #3b464c;
    border-radius: 25px;
    background-color: #2b2e35;
}

#menu ul li {
	
	inset: 0px 0px 0px 1px rgba(0,0,0,0.45), inset 0px 2px 1px 0px rgba(255,255,255,0.15);
	border-bottom: 1px solid;
	text-shadow: -1px -1px 0px rgba(0,0,0,0.5);
}

#menu ul li a {
	color: white;
	text-decoration: none;
	text-transform: uppercase;
	display: block;
}

#menu ul li a:hover {
	background: #28292e;
	color: #fff;
}
.column {
    -webkit-column-count: 3; /* Chrome, Safari, Opera */
    -moz-column-count: 3; /* Firefox */
    column-count: 3;
}
</style>
</html>