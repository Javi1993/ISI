<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ISI | Grupo 2</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="assets/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
</head>
<body class="homepage">
	<div id="page-wrapper">
		<!-- Header -->
		<div id="header-wrapper">
			<div class="container">
				<!-- Header -->
				<jsp:include page="./header.html" flush="true" />
				<!-- Banner -->
				<div id="banner">
					<h2>
						<strong>atmosphspain:</strong> consulta datos SOBRE LOS DIFERENTES
						CONTAMINANTES del aire español usando herramientas de
						visualización <br>
					</h2>
					<p>ver los niveles por provincias, mostar las opiniones de sus
						habitantes, comparar, ...</p>
					<a href="#level2" class="button big icon fa-check-circle">empezar</a>
				</div>
			</div>
		</div>
		<!-- Main Wrapper -->
		<div id="main-wrapper">
			<div class="wrapper style1">
				<div class="inner">
					<!-- Feature 1 -->
					<section id="level2" class="container box feature1">
					<div class="row">
						<div class="12u">
							<header class="first major">
							<h2>Elija un método de visualización</h2>
							<p>DISPONE DE DIVERSAS MANERAS DE VER LOS DATOS DE
								CONTAMINACIÓN EN SU PROVINCIA: MAPAS, GRÁFICOS, OPINIÓN
								SOCIAL,...</p>
							</header>
						</div>
					</div>
					<div class="row">
						<div class="4u 12u(mobile)">
							<section> <a href="#" class="image featured"><img
								src="images/pic01_b.jpg" alt="" /></a> <header
								class="second icon fa-user">
							<h3>OPINIÓN SOCIAL</h3>
							<p>TWEETS VS MAPAS DE CONTAMINACIÓN</p>
							</header> </section>
						</div>
						<div class="4u 12u(mobile)">
							<section> <a href="/Visualizacion/provincia?num=2"
								class="image featured"><img src="images/pic02_b.jpg" alt="" /></a>
							<header class="second icon fa-cog">
							<h3>COMPARAR PROVINCIAS</h3>
							<p>COMPARE PROVINCIAS CON MAPAS Y GRÁFICOS</p>
							</header> </section>
						</div>
						<div class="4u 12u(mobile)">
							<section> <a href="/Visualizacion/provincia?num=3"
								class="image featured"><img src="images/pic03_b.jpg" alt="" /></a>
							<header class="second icon fa-bar-chart-o">
							<h3>GRÁFICOS DE UNA PROVINCIA</h3>
							<p>GRÁFICOS Y MAPAS DE CONTAMINACIÓN</p>
							</header> </section>
						</div>
					</div>
					<div class="row">
						<div class="12u">
							<p>Estas formas de visualización proporcionan al usuario la
								opción de consultar los datos sobre la contaminación atmosférica
								en la provincia de España seleccionada. Esta información podrá
								ser visualizada desde dos puntos de vista diferentes, la opinión
								de los habitantes y los datos reales recopilados por las
								estaciones de calidad del aire de las provincias de estas
								personas.</p>
						</div>
					</div>
					</section>
				</div>
			</div>
			<div class="wrapper style2">
				<div class="inner">
					<!-- Feature 2 -->
					<section class="container box feature2">
					<div class="row">
						<div class="6u 12u(mobile)">
							<section> <header class="major">
							<h2>DESCARGAR LOS DATASHEET</h2>
							<p>LAS MEDICIONES EXACTAS DE LAS ESTACIONES DE CALIDAD DE
								AIRE UTILIZADAS</p>
							</header>
							<p>ATMOSPHSPAIN le da la oportunidad de descargar los
								datasheets con los datos reales de los contaminantes medidos por
								las estaciones de calidad del aire ubicadas en cada una de las
								provincias de España</p>
							<footer> <a href="/Visualizacion/datasheet?op=1"
								class="button medium icon fa-arrow-circle-right">ver
								datasheets</a></footer> </section>
						</div>
						<div class="6u 12u(mobile)">
							<section> <header class="major">
							<h2>numerosos contaminantes</h2>
							<p>informese sobre los contaminantes mostrados</p>
							</header>
							<p>ATMOSPHSPAIN le proporciona varias herramientas de
								visualización en la que podrá analizar la contaminación de una
								provincia en base a ciertos contaminantes. Infórmese sobre los
								riesgos para la salud de estos contaminantes atmosfericos.</p>
							<footer> <a href="#"
								class="button medium alt icon fa-info-circle">INFORMACIÓN DE
								CONTAMINANTES</a></footer> </section>
						</div>
					</div>
					</section>

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
$( "li#h" ).addClass( "current_page_item" );
</script>
</html>