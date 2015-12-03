<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>No Sidebar - ZeroFour by HTML5 UP</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
            <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Month', 'NO2', 'NO', 'SO2'],
          ['ENE',  100,      40,	20],
          ['FEB',  117,      46,	25],
          ['MAR',  66,       112,	10],
		  ['ABR',  150,      80,	20],
          ['MAY',  180,      60,	25],
          ['JUN',  200,       52,	16],
		  ['JUL',  220,      40,	20],
          ['AGO',  180,      46,	28],
          ['SEP',  170,       72,	30],
		  ['OCT',  140,      80,	25],
          ['NOV',  158,      96,	20],
          ['DIC',  150,       112,	16]
        ]);

        var options = {
          title: 'Evolucion de contaminantes',
          hAxis: {title: 'A\xf1o',  titleTextStyle: {color: '#333'}},
          vAxis: {title: 'ug/m3', titleTextStyle: {color: '#333'}, minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
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
											<header class="major">
											  <h2>Barcelona</h2>
											  <p>evolución del dióxido de nitrógeno (NO2) en la provincia de barcelona</p>
											</header>

											<span><%= request.getAttribute("NO2")%></span>
											<p>Texto sobre el mapa o poner con JS la opción de cmabiar de NO2 a otro tipo de contaminante.</p>
											<h3>Gráfico de evolución mensual</h3>
											<div id="chart_div" style="width: 100%; height: 520px;"></div>
										</article>

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
</html>