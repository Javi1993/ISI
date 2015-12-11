<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List, org.bson.Document, java.util.Set, java.util.HashSet, java.util.Arrays"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>ISI | Grupo 2</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1" />
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
	for(int i = 0;i<conta.length;i++)
	{//colocamos el NO2 el primero de la lista para la visualizacion inicial
		if(conta[i].equals("NO2"))
		{
			Object aux = conta[0];
			conta[0] = conta[i];
			conta[i] = aux;
		}
	}
	
	List<Document> medias = (List<Document>) request.getAttribute("medias");//prov1

	if (medias != null && medias.size() > 0 && medias.get(0).size()>1) {
		%>
    <script type="text/javascript">
    google.load("visualization", "1", {packages:["corechart"]});
    <%for(int i = 0; i<conta.length; i++){%>
    google.setOnLoadCallback(drawChart<%="Colum"+conta[i]%>);
    function drawChart<%="Colum"+conta[i]%>() {
      var data = google.visualization.arrayToDataTable([
		['Fecha', '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>'],
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
      var chart = new google.visualization.ColumnChart(document.getElementById('colum<%=conta[i]%>'));
      chart.draw(view, options);
  }
    
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
	      
	      google.setOnLoadCallback(drawChart<%="Combo"+conta[i]%>);
	      function drawChart<%="Combo"+conta[i]%>() {
	        // Some raw data (not necessarily accurate)
	       var data = google.visualization.arrayToDataTable([
         ["Fecha", '<%=request.getParameter("provincia").charAt(0)+request.getParameter("provincia").substring(1).toLowerCase()%>_<%=conta[i]%>', '<%=request.getParameter("provincia2").charAt(0)+request.getParameter("provincia2").substring(1).toLowerCase()%>_<%=conta[i]%>', 'Media'],
 		<%for (Document med : medias) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+conta[i])%>,<%=med.get("average2_"+conta[i])%>,<%if(med.get("average_"+conta[i])!=null&&med.get("average2_"+conta[i])!=null){%><%=(med.getDouble("average_"+conta[i])+med.getDouble("average2_"+conta[i]))/2%><%}else{%>0<%}%>],
		<%}
		%>
      ]);

	    var options = {
			width: 1200,
			height: 500,
	      vAxis: {title: '<%if(conta[i].equals("CO")||conta[i].equals("TOL")||conta[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>', titleTextStyle: {color: '#333'}, minValue: 0},
	      hAxis: {title: 'Fecha'},
	      seriesType: 'bars',
	      series: {2: {type: 'line'}}
	    };

	    var chart = new google.visualization.ComboChart(document.getElementById('combo<%=conta[i]%>'));
	    chart.draw(data, options);
	  }
      <%}%>
    </script>
    <%}%>
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
					<jsp:include page="./header.html" flush="true" />
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
								<span class="carto" id="emap<%=conta[0]%>b"><%if(mapas[1].get(conta[0])!=null){%><%=mapas[1].get(conta[0])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[0]%> para <%=request.getAttribute("provincia2")%></p><%}%></span>
								<%for(int i = 1; i<conta.length; i++){ %>
								<span class="carto" id="emap<%=conta[i]%>"><%if(mapas[0].get(conta[i])!=null){%><%=mapas[0].get(conta[i])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[i]%> para <%=request.getAttribute("provincia")%></p><%}%></span>
								<span class="carto" id="emap<%=conta[i]%>b"><%if(mapas[1].get(conta[i])!=null){%><%=mapas[1].get(conta[i])%><%}else{%><p style="color:red;">No hay mapa de <%=conta[i]%> para <%=request.getAttribute("provincia2")%></p><%}%></span>
								<%}
								%>
								
                               <h3>Gráfico de comparativa de contaminante</h3>
									<div class="overflow-element">
									 <img class="graph" id="img-colum" title="Gráfico de barras vertical" src="images/column.jpg" style="cursor: pointer;"> 
									 <img class="graph" src="images/bar.jpg" id="img-bar" title="Gráfico de barras horizontal" style="cursor: pointer;">									
									 <img class="graph" src="images/line.jpg" id="img-lineal" title="Gráfico lineal" style="cursor: pointer;">
									 <img class="graph" src="images/combo.jpg" id="img-combo" title="Gráfico combo" style="cursor: pointer;">
									 <img class="graph" id="img-area" title="Gráfico de área" src="images/area.jpg" style="cursor: pointer;">
									 <img class="graph" id="img-escalon" src="images/stepped.jpg" title="Gráfico escalonado" style="cursor: pointer;">
								</div>
										<%for(int i=0; i<conta.length; i++){ %>
										<div class="igraph" id="colum<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="area<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="escalon<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
											<div class="igraph" id="combo<%=conta[i]%>" style="width: 100%; height: 520px;"></div>
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
$( ".map" ).click(function() {
	$( ".carto" ).css("display","none");
	$('#e'+$(this).attr('id')).css("display","inline");
	$('#e'+$(this).attr('id')+'b').css("display","inline");
	$('.map').css("color","#7b818c");
	$('#'+$(this).attr('id')).css("color","white");
	$('#elemento').html($(this).attr('id').substring(3));
	$( ".igraph" ).css("display","none");
	$( ".graph" ).css("border","none");
	$('#img-colum').css("border","2px solid yellow");
	$( '#colum'+$(this).attr('id').substring(3)).css("display","inline");
	
	});
$( ".graph" ).click(function() {
	$( ".igraph" ).css("display","none");
	$( ".graph" ).css("border","none");
	$('#'+$(this).attr('id')).css("border","2px solid yellow");
	$( '#'+$(this).attr('id').substring(4)+$('#elemento').text()).css("display","inline");
	});
	
$( ".carto" ).css("display","none");
$( '#emap<%=elem%>' ).css('display','inline');
$( '#emap<%=elem%>'+'b' ).css('display','inline');
$( ".igraph" ).css("display","none");
$('#img-colum').css("border","2px solid yellow");
$( '#colum<%=elem%>' ).css("display","inline");
$( "li#v" ).addClass( "current_page_item" );
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