<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, org.bson.Document, java.util.Map.Entry, java.util.HashMap, java.util.Iterator, java.util.Map, java.util.Date, com.isi.master.visualizacion.clases.HashtagFreq, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GRUPO 2 | ISI</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/jqcloud.min.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
<script src="assets/js/jquery.min.js"></script>
<script type="text/javascript" src="./assets/js/jqcloud.min.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%int[] feeling = (int[])request.getAttribute("feeling"); 
Map<Date, List<HashtagFreq>> hashTagDate = (Map <Date, List<HashtagFreq>>)request.getAttribute("hashTagDate");
HashMap<String,Integer> hashTag = (HashMap<String,Integer>)request.getAttribute("hashTag");
%>
<script type="text/javascript">
<%if(feeling!=null){%>
	  google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Sentimiento', 'Num. Tweets'],
          ['Tweets positivos',  <%=feeling[0]%>],
          ['Tweets negativos',  <%=feeling[1]%>],
          ['Tweets neutros', <%=feeling[2]%>]
        ]);

      var options = {
    	is3D: true,
 	   slices: {
 			0: { color: '#00cc00' },
 			1: { color: '#ff0000' },
 			2: { color: '#adad85' }
 		  }
      };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
      }
     <%}if(hashTagDate!=null&&hashTag!=null&&hashTag.size()>0){
     	int i = 0;
     	SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yy");
		Iterator<Entry<String, Integer>> it = hashTag.entrySet().iterator();
		List<String> auxHashtags = new ArrayList<String>();
		%>
	  google.load('visualization', '1.1', {packages: ['line']});
      google.setOnLoadCallback(drawChart2);
      function drawChart2() {
    	  
      	var data = new google.visualization.DataTable();
    	data.addColumn('string', 'Fecha (MM/dd/yy)');
    	<%while (it.hasNext()) {
			if(i>=5){break;}else{
				 Map.Entry<String,Integer> e = (Map.Entry<String,Integer>)it.next();%>
    	data.addColumn('number', '#<%=e.getKey()%>');
    	<%auxHashtags.add("#"+e.getKey());}i++;
    	}%>
    	
    	data.addRows([
    	 <%
    	 int j = 0;
 		Iterator<Entry<Date, List<HashtagFreq>>> ite = hashTagDate.entrySet().iterator();
 		while (ite.hasNext()) {   
 			Map.Entry<Date,List<HashtagFreq>> e = (Map.Entry<Date,List<HashtagFreq>>)ite.next();%> 
 			['<%=dt1.format(e.getKey())%>',
 			 <%for(int k = 0; k<auxHashtags.size(); k++){
 				boolean esta = false;
 			 	for(HashtagFreq hashtagFreq : e.getValue()){
 			 	if(auxHashtags.get(k).equals(hashtagFreq.getHashtag())){%>
 			 		<%=hashtagFreq.getSize()%>,
 			 	<%esta=true;break;}}%>
 		<%if(!esta){%>0,<%}}%>],<%}%>]);

  	var options = {	
  			legend: { position: 'bottom'},
          };

	var chart = new google.charts.Line(document.getElementById('lineal'));

    chart.draw(data, options);

        }
      <%} %>
    </script>
<%Document mapas = (Document)request.getAttribute("maps");
List<Document> medias = (List<Document>) request.getAttribute("medias");
String contaminantes3[] = null;
String elem = "NO2";
if(mapas!=null && medias!=null && medias.size()>0){
	contaminantes3 = new String[mapas.keySet().size()];
	contaminantes3 = mapas.keySet().toArray(contaminantes3);
	elem = contaminantes3[0];
%>
<script type="text/javascript">
google.load('visualization', '1.1', {packages: ['line']});
   <%for(int i = 0; i<contaminantes3.length; i++){%>    
     google.setOnLoadCallback(drawChart<%=contaminantes3[i]%>);
     function drawChart<%=contaminantes3[i]%>() {
   	var data = new google.visualization.DataTable();
   		data.addColumn('string', 'Fecha (MM/yyyy)');
   		data.addColumn('number', '<%=contaminantes3[i]%>');
   	
	   	data.addRows([
	   		<%for (Document med : medias) {%>
	           ['<%=((Document) med.get("_id")).get("month")%>/<%=((Document) med.get("_id")).get("year")%>',<%=med.get("average_"+contaminantes3[i])%>],
	   		<%}
	   		%>
	       ]);

	var options = {
			chart: {
		          subtitle: '<%if(contaminantes3[i].equals("CO")||contaminantes3[i].equals("TOL")||contaminantes3[i].equals("SH2")){%>mg/m3<%}else{%>ug/m3<%}%>',
		        },
			width: 783,
			height: 400,
	       };

	var chart = new google.charts.Line(document.getElementById('plus<%=contaminantes3[i]%>'));

   chart.draw(data, options);

     }
     <%}%>
</script>
<%}%>
</head>
<body class="right-sidebar">
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
						<div class="row">
							<div class="8u 12u(mobile)">
								<div id="content">
									<!-- Content -->
									<article> <header class="major">
									<h2><%=request.getAttribute("provincia")%></h2>
									<p>Opinión social</p>
									</header>
									<%
									if(hashTag!=null&&hashTag.size()>0){ 
										%><h3>Frecuencia de hashtags</h3><%
										int i = 0;
										Iterator<Entry<String, Integer>> it = hashTag.entrySet().iterator();%>
									<div id="hashtag"></div>
									<script>					    
									    var words = [
								        <%
										while (it.hasNext()) {
											if(i>25){break;}else{
												 Map.Entry<String,Integer> e = (Map.Entry<String,Integer>)it.next();
									        %>
									        		{text:"#<%=e.getKey()%>",weight:<%=e.getValue()%>},
									        <%}i++;
							        			}%>
									               ];
						               $('#hashtag').jQCloud(words,{
						            	  autoResize: true,
						               	  width: 800,
						               	  height: 300
						               });
									</script>
									<%} %>
									<h3>Sentimiento de tweets</h3>
										<div id="piechart" style="width: 100%; height: 400px;"></div>
									<%if(hashTag!=null&&hashTag.size()>0){ %>
									<h3>Evolcuión diaria de Hashtags principales</h3>
										<div class="igraph" id="lineal" style="width: 100%; height: 400px;"></div>
									<%}%>

									<a id="plus" style="cursor:pointer;">Ver <%=request.getAttribute("provincia")%> en detalle &raquo;</a>
									<div id="plusG">
									<a id="less" style="cursor:pointer;">&laquo; Ocultar</a>
									<br><br>
									<p><span class="overflow-element">
									<span title="<%=contaminantes3[0]%>" class="map" id="map<%=contaminantes3[0]%>" style="color:white;cursor:pointer;"><%=contaminantes3[0]%></span><%for(int i = 1; i<contaminantes3.length; i++){%>&nbsp;&nbsp;<span title="<%=contaminantes3[i]%>" class="map" id="map<%=contaminantes3[i]%>" style="cursor:pointer;"><%=contaminantes3[i]%></span><%}%>
								</span></p>
									</div>
																	<%if(contaminantes3!=null&&contaminantes3.length>0){
									for(int i=0; i<contaminantes3.length; i++){ %>
										<div class="iplus" id="plus<%=contaminantes3[i]%>"></div>
											<%} }%>
									</article>
								</div>
							</div>
							<%
								List<Document> tweets = (List<Document> )request.getAttribute("tweets");
							%>
							<div class="4u 12u(mobile)">
								<div id="sidebar">
									<!-- Sidebar -->
									<section> <header class="major">
									<h2>Tweets</h2>
									</header> <%
									if(tweets!=null){
										int cnt = 0;
										if(request.getAttribute("cnt")!=null){cnt=(int)request.getAttribute("cnt");}
										int limite = cnt+5;
										if(limite>tweets.size()){limite=tweets.size();}
										for(int k = cnt; k<limite; k++){%>
									<blockquote class="twitter-tweet" data-cards="hidden" lang="es">
										<p lang="es" dir="ltr"></p>
										<a href='https://twitter.com/<%=tweets.get(k).getString("user")%>/status/<%=tweets.get(k).getString("id_tweet")%>'></a>
									</blockquote>
									<%String sentimiento = tweets.get(k).getString("feeling");
										switch(sentimiento){
											case "P":
												sentimiento = "positivo";
												break;
											case "N":
												sentimiento = "negativo";
												break;
											default:
												sentimiento = "neutro";
										}
									%>
									<div title="<%=sentimiento%>" style="background-color: <%if(sentimiento.equals("negativo")){%>#ff0000<%}else if(sentimiento.equals("neutro")){%>#adad85<%}else{%>#00cc00<%}%>; height: 5px; width: 100%; margin-top: -10px"></div>
									<%}%>
									<script async src="./assets/js/widgets.js" charset="utf-8"></script>
									<p>Página <%=cnt/5+1%> de <%if(tweets.size()/5==0){%>1<%}else{%><%=tweets.size()/5%><%}if(tweets.size()>5){%>
									<br><%if(cnt>=5){%><a href="/Visualizacion/page?n=0&p=<%=cnt%>">Anterior</a> | <%}if(limite<tweets.size()){%><a href="/Visualizacion/page?n=1&p=<%=cnt%>">Siguiente</a><%}}%></p>
									<p>Mostrando <%if(tweets.size()<=5){%><%=tweets.size()%><%}else if(tweets.size()-cnt<5){%><%=tweets.size()-cnt%><%}else{%>5<%}%> de un total de <%=tweets.size()%> tweets</p>
									<%
										request.getSession().setAttribute("hashTagDate", hashTagDate);								
										request.getSession().setAttribute("hashTag", hashTag);
										request.getSession().setAttribute("tweets", tweets);
										request.getSession().setAttribute("feeling", feeling);	
										request.getSession().setAttribute("provincia", request.getAttribute("provincia"));	
									}%> 
									<p></section>
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
	<script src="assets/js/jquery.dropotron.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="assets/js/main.js"></script>
</body>
<script>
$( "li#v" ).addClass( "current_page_item" );
$("#plusG").css("display","none");
$(".iplus").css("display","none");

$( "#plus" ).click(function() {
	$(this).css("display","none");
	$('#plusG').css("display","inline");
	$("#plus<%=elem%>").css("display","inline");
});
$( "#less" ).click(function() {
	$("#plusG").css("display","none");
	$('#plus').css("display","inline");
	$(".iplus").css("display","none");
});
$( ".map" ).click(function() {
	$(".iplus").css("display","none");
	$('#plus'+$(this).attr('id').substring(3)).css("display","inline");
	$('.map').css("color","#7b818c");
	$('#'+$(this).attr('id')).css("color","white");
});
</script>
<style>
.overflow-element {
	border: 5px solid #404248;
	font-size: 11px;
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