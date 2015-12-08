<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.List, org.bson.Document, java.util.Set, java.util.HashSet, java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>No Sidebar - ZeroFour by HTML5 UP</title>
		<meta charset="utf-8" />
           <script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%
Document[] mapas = (Document[])request.getAttribute("maps");
String elem="NO2";//default
if(mapas!=null){
	String contaminantes1[] = new String[mapas[0].keySet().size()];
	contaminantes1 = mapas[0].keySet().toArray(contaminantes1);
	
	String contaminantes2[] = new String[mapas[1].keySet().size()];
	contaminantes2 = mapas[1].keySet().toArray(contaminantes2);
	
	Set<String> cont = new HashSet<String>(Arrays.asList(contaminantes1));
	cont.addAll(Arrays.asList(contaminantes2));
	
	Object[] conta  = cont.toArray();
	List<Document> medias = (List<Document>) request.getAttribute("medias");//prov1

	if (medias != null && medias.size() > 0 && medias.get(0).size()>1) {
		%>
    <script type="text/javascript">
    <%for(int i = 0; i<conta.length; i++){%>
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart<%="Area"+conta[i]%>);
      function drawChart<%="Area"+conta[i]%>() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha', '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
  		<%for (Document med : medias) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>],
		<%}
		%>
        ]);

        var options = {
        		width: 1200,
      		  height: 520,
          hAxis: {title: 'A\xf1o',  titleTextStyle: {color: '#333'}},
          vAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>', titleTextStyle: {color: '#333'}, minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('area<%=conta[i]%>'));
        chart.draw(data, options);
      }
      
      google.load("visualization", "1.1", {packages:["bar"]});
      google.setOnLoadCallback(drawChart<%="Column"+conta[i]%>);
      function drawChart<%="Column"+conta[i]%>() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha', '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
    		<%for (Document med : medias) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>],
    		<%}
    		%>
        ]);

        var options = {
        		width: 1200,
      		  height: 520,
      		  chart: {
  	            subtitle: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>'
  	          },
        	vAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>'}
        };

        var chart = new google.charts.Bar(document.getElementById('column<%=conta[i]%>'));
        chart.draw(data, options);
      }
      
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart<%="Escalon"+conta[i]%>);
      function drawChart<%="Escalon"+conta[i]%>() {
        var data = google.visualization.arrayToDataTable([
        ['Fecha', '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
		<%for (Document med : medias) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>],
		<%}
		%>
        ]);

        var options = {
        		width: 1200,
        		height: 520,
          hAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
          vAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>', titleTextStyle: {color: '#333'}, minValue: 0},
          isStacked: true
        };

        var chart = new google.visualization.SteppedAreaChart(document.getElementById('escalon<%=conta[i]%>'));
        chart.draw(data, options);
      }
      
      google.load("visualization", "1", {packages:["corechart"]})
	    google.setOnLoadCallback(drawChart<%="Lineal"+conta[i]%>);
      function drawChart<%="Lineal"+conta[i]%>() {
          var data = google.visualization.arrayToDataTable([
            ['Fecha', '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
    		<%for (Document med : medias) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>],
    		<%}
    		%>
          ]);

  	var options = {
  			width: 1200,
  			height: 520,
  		hAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
  		vAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>', titleTextStyle: {color: '#333'}, minValue: 0},			
  		legend: { position: 'right' }
          };

     var chart = new google.visualization.LineChart(document.getElementById('lineal<%=conta[i]%>'));

          chart.draw(data, options);

        }
      
	    google.load("visualization", "1", {packages:["corechart"]});
	    google.setOnLoadCallback(drawChart<%="Bar"+conta[i]%>);
	      function drawChart<%="Bar"+conta[i]%>() {
	      var data = google.visualization.arrayToDataTable([
	        ["Fecha", '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
    		<%for (Document med : medias) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>],
    		<%}
    		%>
	      ]);

	      var view = new google.visualization.DataView(data);


	      var options = {
			width: 1200,
			height: 750,
	        bar: {groupWidth: "75%"},
			vAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
			hAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>', titleTextStyle: {color: '#333'}, minValue: 0},		
	        legend: { position: "right" }
	      };
	      var chart = new google.visualization.BarChart(document.getElementById('bar<%=conta[i]%>'));
	      chart.draw(view, options);
	    }
      <%}%>
    </script>
    <%} 	List<Document> medias2 = (List<Document>) request.getAttribute("medias2");//prov1

	if (medias2 != null && medias2.size() > 0 && medias2.get(0).size()>1) {
		%>
		
		<%} %>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
	</head>
	<body class="no-sidebar">
		<div id="page-wrapper">

			<!-- Header Wrapper -->
				<div id="header-wrapper">
					<div class="container">

						<!-- Header -->
							<header id="header">
								<div class="inner">

									<!-- Logo -->
									<h1><a href="index.html" id="logo">Grupo 2 | isi</a></h1>
									<!-- Nav -->
										<nav id="nav">
											<ul>
												<li><a href="index.html">Home</a></li>
												<li>
													<a href="#">Dropdown</a>
													<ul>
														<li><a href="#">Lorem ipsum dolor</a></li>
														<li><a href="#">Magna phasellus</a></li>
														<li>
															<a href="#">Phasellus consequat</a>
															<ul>
																<li><a href="#">Lorem ipsum dolor</a></li>
																<li><a href="#">Phasellus consequat</a></li>
																<li><a href="#">Magna phasellus</a></li>
																<li><a href="#">Etiam dolore nisl</a></li>
															</ul>
														</li>
														<li><a href="#">Veroeros feugiat</a></li>
													</ul>
												</li>
												<li><a href="left-sidebar.html">Left Sidebar</a></li>
												<li><a href="right-sidebar.html">Right Sidebar</a></li>
												<li class="current_page_item"><a href="no-sidebar.html">No Sidebar</a></li>
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
								<div id="content">
<!-- Content -->
							<article>
							<%
								elem=(String)conta[0];
							%><header class="major">
								<h2><%=request.getAttribute("provincia")%> vs <%=request.getAttribute("provincia2")%></h2>	
									<p>
										evolución del <span id="elemento"><%=conta[0]%></span> en las
										provincias de
										<%=request.getAttribute("provincia")%> y <%=request.getAttribute("provincia2")%></p>
								<p><span class="overflow-element">
									<span title="<%=conta[0]%>" class="map" id="map<%=conta[0]%>" style="color:white;cursor:pointer;"><%=conta[0]%></span><%for(int i = 1; i<conta.length; i++){%>&nbsp;&nbsp;<span title="<%=conta[i]%>" class="map" id="map<%=conta[i]%>" style="cursor:pointer;"><%=conta[i]%></span><%}%>
								</span></p>
								</header>
								<span class="carto" id="emap<%=conta[0]%>"><%if(mapas[0].get(conta[0])!=null){%><%=mapas[0].get(conta[0])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[0]%> para <%=request.getAttribute("provincia")%></p><%}%></span>
								<span class="carto" id="emap<%=conta[0]%>2"><%if(mapas[1].get(conta[0])!=null){%><%=mapas[1].get(conta[0])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[0]%> para <%=request.getAttribute("provincia")%></p><%}%></span>
								<%for(int i = 1; i<conta.length; i++){ %>
								<span class="carto" id="emap<%=conta[i]%>"><%if(mapas[0].get(conta[i])!=null){%><%=mapas[0].get(conta[i])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[i]%> para <%=request.getAttribute("provincia")%></p><%}%></span>
								<span class="carto" id="emap<%=conta[i]%>2"><%if(mapas[1].get(conta[i])!=null){%><%=mapas[1].get(conta[i])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[i]%> para <%=request.getAttribute("provincia2")%></p><%}%></span>
								<%}
								%>
								
                               <h3>Gráfico de comparativa de contaminante</h3>
									<div class="overflow-element">
									 <img class="graph" id="img-column" title="Gráfico de barras vertical" src="images/column.jpg" style="cursor: pointer;"> 
									 <img class="graph" src="images/bar.jpg" id="img-bar" title="Gráfico de barras horizontal" style="cursor: pointer;">									
									 <img class="graph" src="images/line.jpg" id="img-lineal" title="Gráfico lineal" style="cursor: pointer;">
									 <img class="graph" id="img-area" title="Gráfico de área" src="images/area.jpg" style="cursor: pointer;">
									 <img class="graph" id="img-escalon" src="images/stepped.jpg" title="Gráfico escalonado" style="cursor: pointer;">
								</div>
										<%for(int i=0; i<conta.length; i++){ %>
											<div class="igraph" id="area<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="column<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="escalon<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="lineal<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="bar<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<%} %>
										</article>
								</div>
								<%} %>
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
										<h2><strong>ZeroFour</strong> by HTML5 UP</h2>
										<p>Hi! This is <strong>ZeroFour</strong>, a free, fully responsive HTML5 site
										template by <a href="http://n33.co/">AJ</a> for <a href="http://html5up.net/">HTML5 UP</a>.
										It's <a href="http://html5up.net/license/">Creative Commons Attribution</a>
										licensed so use it for any personal or commercial project (just credit us
										for the design!).</p>
										<a href="#" class="button alt icon fa-arrow-circle-right">Learn More</a>
									</section>

								<!-- Contact -->
									<section>
										<h2>Get in touch</h2>
										<div>
											<div class="row">
												<div class="6u 12u(mobile)">
													<dl class="contact">
														<dt>Twitter</dt>
														<dd><a href="#">@untitled-corp</a></dd>
														<dt>Facebook</dt>
														<dd><a href="#">facebook.com/untitled</a></dd>
														<dt>WWW</dt>
														<dd><a href="#">untitled.tld</a></dd>
														<dt>Email</dt>
														<dd><a href="#">user@untitled.tld</a></dd>
													</dl>
												</div>
												<div class="6u 12u(mobile)">
													<dl class="contact">
														<dt>Address</dt>
														<dd>
															1234 Fictional Rd<br />
															Nashville, TN 00000-0000<br />
															USA
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
										<li>&copy; Untitled. All rights reserved</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
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
$( ".map" ).click(function() {
	$( ".carto" ).css("display","none");
	$('#e'+$(this).attr('id')).css("display","inline");
	$('#e'+$(this).attr('id')+'2').css("display","inline");
	$('.map').css("color","#7b818c");
	$('#'+$(this).attr('id')).css("color","white");
	$('#elemento').html($(this).attr('id').substring(3));
	$( ".igraph" ).css("display","none");
	$( ".graph" ).css("border","none");
	$('#img-column').css("border","2px solid yellow");
	$( '#column'+$(this).attr('id').substring(3)).css("display","inline");
	
	});
$( ".graph" ).click(function() {
	$( ".igraph" ).css("display","none");
	$( ".graph" ).css("border","none");
	$('#'+$(this).attr('id')).css("border","2px solid yellow");
	$( '#'+$(this).attr('id').substring(4)+$('#elemento').text()).css("display","inline");
	});
	
$( ".carto" ).css("display","none");
$( '#emap<%=elem%>' ).css('display','inline');
$( '#emap<%=elem%>'+'2' ).css('display','inline');
$( ".igraph" ).css("display","none");
$('#img-column').css("border","2px solid yellow");
$( '#column<%=elem%>' ).css("display","inline");
</script>
<style>
.overflow-element {
	border: 10px solid #404248;
	background-color: #404248;
	border-radius: 25px;
	overflow-x: auto;
	overflow-y: hidden;
	white-space: nowrap;
	box-sizing: border-box;
	margin: 20px;
	border-radius: 25px;
}
</style>
</html>