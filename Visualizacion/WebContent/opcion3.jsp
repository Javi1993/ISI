<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.List, org.bson.Document"%>
<!DOCTYPE HTML>
<html>
<head>
<title>ISI | Grupo 2</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%List<Document> medias = (List<Document>) request.getAttribute("medias");
	if (medias != null && medias.size() > 0 && medias.get(0).size()>1) {
		String contaminantes[] = new String[medias.get(0).keySet().size()];
		contaminantes = medias.get(0).keySet().toArray(contaminantes);%>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
		['Fecha',<%for (int i = 0; i < contaminantes.length - 1; i++) {%>'<%=contaminantes[i + 1].replace("average_", "")%>',<%}%>],
		<%for (Document med : medias) {%>
          ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes.length - 1; j++) {%>,<%=med.get(contaminantes[j + 1])%><%}%>],
		<%}
		%>
        ]);

        var options = {
        		width: 1200,
        		  height: 520,
          hAxis: {title: 'Fecha',  titleTextStyle: {color: '#333'}},
          vAxis: {title: 'ug/m3', titleTextStyle: {color: '#333'}, minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('area_1'));
        chart.draw(data, options);
      }

      google.load("visualization", "1.1", {packages:["bar"]});
      google.setOnLoadCallback(drawChart1);
      function drawChart1() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha',<%for (int i = 0; i < contaminantes.length - 1; i++) {%>'<%=contaminantes[i + 1].replace("average_", "")%>',<%}%>],
  		<%for (Document med : medias) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes.length - 1; j++) {%>,<%=med.get(contaminantes[j + 1])%><%}%>],
		<%}
		%>
        ]);

        var options = {
      		  chart: {
  	            subtitle: '(ug/m3)',
  	          },
        	vAxis: {title: 'ug/m3'}
        };

        var chart = new google.charts.Bar(document.getElementById('column_1'));
        chart.draw(data, options);
      }
      
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart2);
      function drawChart2() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha',<%for (int i = 0; i < contaminantes.length - 1; i++) {%>'<%=contaminantes[i + 1].replace("average_", "")%>',<%}%>],
  		<%for (Document med : medias) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes.length - 1; j++) {%>,<%=med.get(contaminantes[j + 1])%><%}%>],
		<%}
		%>
        ]);

        var options = {
        		width: 1200,
        		height: 520,
          hAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
          vAxis: {title: 'ug/m3', titleTextStyle: {color: '#333'}, minValue: 0},
          isStacked: true
        };

        var chart = new google.visualization.SteppedAreaChart(document.getElementById('escalon_1'));
        chart.draw(data, options);
      }
      
      google.load('visualization', '1.1', {packages: ['line']});
      google.setOnLoadCallback(drawChart3);
      function drawChart3() {
    	  
    	var data = new google.visualization.DataTable();
    	data.addColumn('string', 'Fecha');
    	<%for (int i = 0; i < contaminantes.length - 1; i++) {%>
    	data.addColumn('number', '<%=contaminantes[i + 1].replace("average_", "")%>');
    	<%}%>
    	
    	data.addRows([
    		<%for (Document med : medias) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes.length - 1; j++) {%>,<%=med.get(contaminantes[j + 1])%><%}%>],
    		<%}
    		%>
        ]);

	var options = {
			chart: {
		          subtitle: 'ug/m3'
		        },
			width: 1200,
			height: 520,
        };

	var chart = new google.charts.Line(document.getElementById('lineal_1'));

    chart.draw(data, options);

      }
      
	    google.load("visualization", "1", {packages:["corechart"]});
	    google.setOnLoadCallback(drawChart4);
	    function drawChart4() {
	      var data = google.visualization.arrayToDataTable([
	        ['Fecha',<%for (int i = 0; i < contaminantes.length - 1; i++) {%>'<%=contaminantes[i + 1].replace("average_", "")%>',<%}%>],
    		<%for (Document med : medias) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes.length - 1; j++) {%>,<%=med.get(contaminantes[j + 1])%><%}%>],
    		<%}
    		%>
	      ]);

	      var view = new google.visualization.DataView(data);


	      var options = {
			width: 1200,
			height: 750,
	        bar: {groupWidth: "75%"},
			vAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
			hAxis: {title: 'ug/m3', titleTextStyle: {color: '#333'}, minValue: 0},		
	        legend: { position: "right" }
	      };
	      var chart = new google.visualization.BarChart(document.getElementById("bar_1"));
	      chart.draw(view, options);
	    }
    </script>
<%} 
	List<Document> medias2 = (List<Document>) request.getAttribute("medias2");
		if (medias2 != null && medias2.size() > 0 && medias2.get(0).size()>1) {
			String contaminantes2[] = new String[medias2.get(0).keySet().size()];
			contaminantes2 = medias2.get(0).keySet().toArray(contaminantes2);%>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
		['Fecha',<%for (int i = 0; i < contaminantes2.length - 1; i++) {%>'<%=contaminantes2[i + 1].replace("average_", "")%>',<%}%>],
		<%for (Document med : medias2) {%>
          ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes2.length - 1; j++) {%>,<%=med.get(contaminantes2[j + 1])%><%}%>],
		<%}%>
        ]);

        var options = {
        		width: 1200,
        		height: 520,
          hAxis: {title: 'Fecha',  titleTextStyle: {color: '#333'}},
          vAxis: {title: 'mg/m3', titleTextStyle: {color: '#333'}, minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('area_2'));
        chart.draw(data, options);
      }

    google.load("visualization", "1.1", {packages:["bar"]});
    google.setOnLoadCallback(drawChart1);
    function drawChart1() {
      var data = google.visualization.arrayToDataTable([
        ['Fecha',<%for (int i = 0; i < contaminantes2.length - 1; i++) {%>'<%=contaminantes2[i + 1].replace("average_", "")%>',<%}%>],
		<%for (Document med : medias2) {%>
      ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes2.length - 1; j++) {%>,<%=med.get(contaminantes2[j + 1])%><%}%>],
		<%}
		%>
      ]);

      var options = {
    		  chart: {
    	            subtitle: '(mg/m3)',
    	          },
    	title: " (mg/m3)",
      	vAxis: {title: 'mg/m3'}
      };

      var chart = new google.charts.Bar(document.getElementById('column_2'));
      chart.draw(data, options);
    }
    
    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart2);
    function drawChart2() {
      var data = google.visualization.arrayToDataTable([
        ['Fecha',<%for (int i = 0; i < contaminantes2.length - 1; i++) {%>'<%=contaminantes2[i + 1].replace("average_", "")%>',<%}%>],
		<%for (Document med : medias2) {%>
      ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes2.length - 1; j++) {%>,<%=med.get(contaminantes2[j + 1])%><%}%>],
		<%}
		%>
      ]);

      var options = {
    		  width: 1200,
    		  height: 520,
        hAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
        vAxis: {title: 'mg/m3', titleTextStyle: {color: '#333'}, minValue: 0},
        isStacked: true
      };

      var chart = new google.visualization.SteppedAreaChart(document.getElementById('escalon_2'));
      chart.draw(data, options);
    }
    
    google.load('visualization', '1.1', {packages: ['line']});
    google.setOnLoadCallback(drawChart3);
    function drawChart3() {
    	
    	var data = new google.visualization.DataTable();
    	data.addColumn('string', 'Fecha');
    	<%for (int i = 0; i < contaminantes2.length - 1; i++) {%>
    	data.addColumn('number', '<%=contaminantes2[i + 1].replace("average_", "")%>');
    	<%}%>
    	
    	data.addRows([
    		<%for (Document med : medias2) {%>
            ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes2.length - 1; j++) {%>,<%=med.get(contaminantes2[j + 1])%><%}%>],
    		<%}
    		%>
        ]);

    	var options = {
    			chart: {
    		          subtitle: 'mg/m3'
    		        },
    			width: 1200,
    			height: 520,
            };

    	var chart = new google.charts.Line(document.getElementById('lineal_2'));

        chart.draw(data, options);

    }

    google.load("visualization", "1", {packages:["corechart"]});
    google.setOnLoadCallback(drawChart4);
    function drawChart4() {
      var data = google.visualization.arrayToDataTable([
        ['Fecha',<%for (int i = 0; i < contaminantes2.length - 1; i++) {%>'<%=contaminantes2[i + 1].replace("average_", "")%>',<%}%>],
		<%for (Document med : medias2) {%>
        ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>'<%for (int j = 0; j < contaminantes2.length - 1; j++) {%>,<%=med.get(contaminantes2[j + 1])%><%}%>],
		<%}
		%>
      ]);

      var view = new google.visualization.DataView(data);

      var options = {
		width: 1200,
		height: 750,
        bar: {groupWidth: "75%"},
		vAxis: {title: 'Fecha', titleTextStyle: {color: '#333'}},
		hAxis: {title: 'mg/m3', titleTextStyle: {color: '#333'}, minValue: 0},		
        legend: { position: "right" }
      };
      var chart = new google.visualization.BarChart(document.getElementById("bar_2"));
      chart.draw(view, options);
    }
    </script>
    <%} %>
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
							<%Document mapas = (Document)request.getAttribute("maps");
							String elem="NO2";//default
							if(mapas!=null && medias!=null && medias.size()>0){
							String contaminantes3[] = new String[mapas.keySet().size()];
							contaminantes3 = mapas.keySet().toArray(contaminantes3);
							elem=contaminantes3[0];
							%><header class="major">
								<h2><%=request.getAttribute("provincia")%></h2>	
									<p>
										evolución del <span id="elemento"><%=contaminantes3[0]%></span> en la
										provincia de
										<%=request.getAttribute("provincia")%> desde el <%=((Document) medias.get(0).get("_id")).get("month")+"/"+((Document) medias.get(0).get("_id")).get("year")%> hasta <%=((Document) medias.get(medias.size()-1).get("_id")).get("month")+"/"+((Document) medias.get(medias.size()-1).get("_id")).get("year")%></p>
								<p><span class="overflow-element">
									<span title="<%=contaminantes3[0]%>" class="map" id="map<%=contaminantes3[0]%>" style="color:white;cursor:pointer;"><%=contaminantes3[0]%></span><%for(int i = 1; i<contaminantes3.length; i++){%>&nbsp;&nbsp;<span title="<%=contaminantes3[i]%>" class="map" id="map<%=contaminantes3[i]%>" style="cursor:pointer;"><%=contaminantes3[i]%></span><%}%>
								</span></p>
								</header>
								<span class="carto" id="emap<%=contaminantes3[0]%>"><%=mapas.get(contaminantes3[0]) %></span>
								<%for(int i = 1; i<contaminantes3.length; i++){ %>
								<span class="carto" id="emap<%=contaminantes3[i]%>"><%=mapas.get(contaminantes3[i]) %></span>
								<%} }%>
								<h3>Gráficos de medias mensuales</h3>
								<div class="overflow-element">
									 <img class="graph" id="img-column" title="GrÃ¡fico de barras vertical" src="images/column.jpg" style="cursor: pointer;"> 
									 <img class="graph" src="images/bar.jpg" id="img-bar" title="GrÃ¡fico de barras horizontal" style="cursor: pointer;">									
									 <img class="graph" src="images/line.jpg" id="img-lineal" title="GrÃ¡fico lineal" style="cursor: pointer;">
									 <img class="graph" id="img-area" title="GrÃ¡fico de Ã¡rea" src="images/area.jpg" style="cursor: pointer;">
									 <img class="graph" id="img-escalon" src="images/stepped.jpg" title="GrÃ¡fico escalonado" style="cursor: pointer;">
									 
								</div>
								<%if (medias != null && medias.size() > 0 && medias.get(0).size()>1) { %>
								<div class="igraph" id="area_1" style="width: 100%; height: 520px;"></div>	
								<div class="igraph" id="column_1" style="width: 100%; height: 520px;"></div>
								<div class="igraph"id="lineal_1" style="width: 100%; height: 520px;"></div>
								<div class="igraph" id="escalon_1" style="width: 100%; height: 520px;"></div>
								<div class="igraph"id="bar_1" style="width: 100%; height: 750px;"></div>
								<%}if (medias2 != null && medias2.size() > 0 && medias2.get(0).size()>1) { %>
								<div class="igraph" id="area_2" style="width: 100%; height: 520px;"></div>
								<div class="igraph" id="column_2" style="width: 100%; height: 520px;"></div>
								<div class="igraph" id="lineal_2" style="width: 100%; height: 520px;"></div>
								<div class="igraph" id="escalon_2" style="width: 100%; height: 520px;"></div>
								<div class="igraph" id="bar_2" style="width: 100%; height: 750px;"></div>
								<%} %>
							</article>
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
$( ".map" ).click(function() {
	$( ".carto" ).css("display","none");
	$('#e'+$(this).attr('id')).css("display","inline");
	$('.map').css("color","#7b818c");
	$('#'+$(this).attr('id')).css("color","white");
	$('#elemento').html($(this).attr('id').substring(3));
	
	});
$( ".graph" ).click(function() {
	if($('#'+$(this).attr('id').substring(4)+"_1").css("display")=="block"){
		$('#'+$(this).attr('id')).css("border","none");
		$( '#'+$(this).attr('id').substring(4)+"_1" ).css("display","none");
		$( '#'+$(this).attr('id').substring(4)+"_2" ).css("display","none");
	}else{
		$('#'+$(this).attr('id')).css("border","2px solid yellow");
		$( '#'+$(this).attr('id').substring(4)+"_1" ).css("display","block");
		$( '#'+$(this).attr('id').substring(4)+"_2" ).css("display","block");
		}
	});
	
$( ".carto" ).css("display","none");
$( '#emap<%=elem%>' ).css('display','inline');
$('.graph').css("border","2px solid yellow");
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
}
</style>
</html>