<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, org.bson.Document, java.util.HashMap, java.util.Iterator, java.util.Map"%>
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
<%int[] feeling = (int[])request.getAttribute("feeling"); 
if(feeling!=null){%>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Sentimiento', 'Num. Tweets'],
          ['Positivo',  <%=feeling[0]%>],
          ['Negativo',  <%=feeling[1]%>],
          ['Neutro', <%=feeling[2]%>]
        ]);

      var options = {
    	title: 'Sentimiento de los tweets',
        legend: 'none',
        pieSliceText: 'label',
        pieStartAngle: 100,
 	   slices: {
 			0: { color: 'green' },
 			1: { color: 'red' },
 			2: { color: 'grey' }
 		  }
      };

        var chart = new google.visualization.PieChart(document.getElementById('piechart'));
        chart.draw(data, options);
      }
    </script>
    <%} %>
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
									<h2><%=request.getAttribute("provincia") %></h2>
									<p>Opinión social</p>
									</header>
									<span id="hashtag">
									<%HashMap<String,Integer> hashTag = (HashMap<String,Integer>)request.getAttribute("hashTag");
									if(hashTag!=null){ 
										Iterator it = hashTag.entrySet().iterator();%>
									<script>
									var fill = d3.scale.category20();
									
									    var frequency_list = [
									        <%
											 while (it.hasNext()) {
												 Map.Entry<String,Integer> e = (Map.Entry<String,Integer>)it.next();
									        %>
									        {"text":"<%=e.getKey()%>","size":<%=e.getValue()%>},
									        <%}%>
									        ];
										
									    var layout = d3.layout.cloud()
									    .size([800, 500])
										.words(frequency_list)
									    /*.words([
									      "Hello", "world", "normally", "you", "want", "more", "words",
									      "than", "this"].map(function(d) {
									      return {text: d, size: 10 + Math.random() * 90, test: "haha"};
									    }))*/
									    .padding(5)
									    .rotate(function() { return ~~(Math.random() * 2) * 90; })
									    .font("Impact")
									    .fontSize(function(d) { return d.size; })
									    .on("end", draw);

									layout.start();

									function draw(words) {
									  d3.select("#hashtag").append("svg")
									      .attr("width", layout.size()[0])
									      .attr("height", layout.size()[1])
									    .append("g")
									      .attr("transform", "translate(" + layout.size()[0] / 2 + "," + layout.size()[1] / 2 + ")")
									    .selectAll("text")
									      .data(words)
									    .enter().append("text")
									      .style("font-size", function(d) {
									    	  if(frequency_list.length>50){
										    	  if(d.size<10){
										    		  return d.size + 5 + "px"; 
										    	  }else if(d.size>150){
										    		  return 125 + "px"; 
										    	  }else{
										    		  return d.size + 2 + "px"; 
										    	  }
								    		  }else{
										    	  if(d.size<10){
										    		  return d.size + 15 + "px"; 
										    	  }else if(d.size>150){
										    		  return 125 + "px"; 
										    	  }else{
										    		  return d.size + 25 + "px"; 
										    	  }								    			  
								    		  }
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
									</span>
										<div id="piechart" style="width: 800px; height: 400px;"></div>
									<p>(PONER GRAFICO) El top-5 de hashtags es.</p>
									<h3>More intriguing information</h3>
									<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
										Maecenas ac quam risus, at tempus justo. Sed dictum rutrum
										massa eu volutpat. Quisque vitae hendrerit sem. Pellentesque
										lorem felis, ultricies a bibendum id, bibendum sit amet nisl.
										Mauris et lorem quam. Maecenas rutrum imperdiet vulputate.
										Nulla quis nibh ipsum, sed egestas justo. Morbi ut ante mattis
										orci convallis tempor. Etiam a lacus a lacus pharetra
										porttitor quis accumsan odio. Sed vel euismod nisi. Etiam
										convallis rhoncus dui quis euismod. Maecenas lorem tellus,
										congue et condimentum ac, ullamcorper non sapien. Donec
										sagittis massa et leo semper a scelerisque metus faucibus.
										Morbi congue mattis mi. Phasellus sed nisl vitae risus
										tristique volutpat. Cras rutrum commodo luctus.</p>
									<p>Phasellus odio risus, faucibus et viverra vitae,
										eleifend ac purus. Praesent mattis, enim quis hendrerit
										porttitor, sapien tortor viverra magna, sit amet rhoncus nisl
										lacus nec arcu. Suspendisse laoreet metus ut metus imperdiet
										interdum aliquam justo tincidunt. Mauris dolor urna, fringilla
										vel malesuada ac, dignissim eu mi. Praesent mollis massa ac
										nulla pretium pretium. Maecenas tortor mauris, consectetur
										pellentesque dapibus eget, tincidunt vitae arcu. Vestibulum
										purus augue, tincidunt sit amet iaculis id, porta eu purus.</p>
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