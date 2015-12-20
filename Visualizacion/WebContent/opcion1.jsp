<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, org.bson.Document, java.util.Map.Entry, java.util.HashMap, java.util.Iterator, java.util.Map, java.util.Date, com.isi.master.visualizacion.HashtagFreq, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GRUPO 2 | ISI</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
<script type="text/javascript" src="./assets/js/d3.v3.min.js"></script>
<script type="text/javascript" src="./assets/js/d3.layout.cloud.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<%
int[] feeling = (int[])request.getAttribute("feeling"); 
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
									var fill = d3.scale.category20();
									
									    var frequency_list = [
									        <%
										while (it.hasNext()) {
											if(i>25){break;}else{
												 Map.Entry<String,Integer> e = (Map.Entry<String,Integer>)it.next();
									        %>
									        {"text":"<%=e.getKey()%>","size":<%=e.getValue()%>},
									        <%}i++;
									        	}%>
									        ];
										
									    var layout = d3.layout.cloud()
									    <%if(hashTag.size()>=25){%>
									    .size([800, 500])
									    <%}else{%>
									    .size([800, 300])
									    <%}%>
										.words(frequency_list)
									    /*.words([
									      "Hello", "world", "normally", "you", "want", "more", "words",
									      "than", "this"].map(function(d) {
									      return {text: d, size: 10 + Math.random() * 90, test: "haha"};
									    }))*/
									    .padding(5)
									    .rotate(function() { return ~~((Math.random() * 6) - 3) * 30; })
									    .font("Impact")
									    .fontSize(function(d) { return d.size; })
									    .on("end", draw);

									layout.start();

									function draw(words) {
									  d3.select("div#hashtag").append("svg")
									      .attr("width", layout.size()[0])
									      .attr("height", layout.size()[1])
									    .append("g")
									      .attr("transform", "translate(" + layout.size()[0] / 2 + "," + layout.size()[1] / 2 + ")")
									    .selectAll("text")
									      .data(words)
									    .enter().append("text")
									      .style("font-size", function(d) {
									    	  <%if(hashTag.size()>=25){%>
										    	  if(d.size<5){
										    		  return (d.size+15)+"px"; 
										    	  }else if(d.size>=100){
										    		  return (80+d.size*0.1)+"px"; 
										    	  }else{
										    		  return (d.size+10) + "px"; 
										    	  }
										    	  <%}else{%>
										    	  if(d.size<=1){
										    		  return "9px"; 
										    	  }else if(d.size>=100){
										    		  return (80+d.size*0.1)+"px"; 
										    	  }else{
										    		  return (d.size+20) + "px"; 
										    	  }
												    <%}%>
									    	   }
									      )
									      .style("font-family", "Impact")
									      .style("fill", function(d, i) { return fill(i); })
									      .attr("text-anchor", "middle")
									      .attr("transform", function(d) {
									        return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
									      })
									      .text(function(d) { return d.text; });
									}
									</script>
									<%} %>
									<h3>Sentimiento de tweets</h3>
										<div id="piechart" style="width: 100%; height: 400px;"></div>
									<h3>Evolcuión diaria de Hashtags principales</h3>
										<div class="igraph" id="lineal" style="width: 100%; height: 400px;"></div>
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
									int cnt=0;
									for(Document tweet:tweets){ 
										if(cnt>=5){break;}else{%>
									<blockquote class="twitter-tweet" data-cards="hidden" lang="es">
										<p lang="es" dir="ltr"></p>
										<a
											href='https://twitter.com/<%=tweet.getString("user")%>/status/<%=tweet.getString("id_tweet")%>'></a>
									</blockquote>
									<script async src="./assets/js/widgets.js" charset="utf-8"></script>
									
									<%cnt++;}
									}%>
									<p>Mostrando <%=cnt%> de un total de <%=tweets.size()%> tweets</p>
								<%	}%> 
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
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.dropotron.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="assets/js/main.js"></script>
</body>
</html>