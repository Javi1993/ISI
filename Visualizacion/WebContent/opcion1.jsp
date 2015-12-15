<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List, org.bson.Document"%>
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
									<h2>Right Sidebar</h2>
									<p>Which means the sidebar is on the right</p>
									</header> <span class="image featured"><img
										src="images/pic08.jpg" alt="" /></span>
									<p>Phasellus quam turpis, feugiat sit amet ornare in,
										hendrerit in lectus. Praesent semper mod quis eget mi. Etiam
										eu ante risus. Aliquam erat volutpat. Aliquam luctus et mattis
										lectus sit amet pulvinar. Nam turpis nisi consequat etiam
										lorem ipsum dolor sit amet nullam.</p>
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
									<h2>Subheading</h2>
									</header>
									<%
									if(tweets!=null){
									for(Document tweet:tweets){ %>
										<blockquote class="twitter-tweet" data-cards="hidden" lang="es">
											<p lang="es" dir="ltr"></p>
											<a
												href='https://twitter.com/<%=tweet.getString("user")%>/status/<%=tweet.getString("id_tweet")%>'></a>
										</blockquote>
										<script async src="./assets/js/widgets.js" charset="utf-8"></script>
									<%} 
									}%>
									</section>
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