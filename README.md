## Resumen

El trabajo que recoge este repositorio es una aplicación web que muestra la contaminación atmosférica a nivel provincial en toda España durante los años 2014-2015. Mediante el uso de dashboards los usuarios podrán ver los datos recogidos por las diferentes estaciones de calidad del aire de una manera clara y precisa. Por otra parte, se aporta la opinión social de los habitantes de las provincias mediante la recopilación y estudio de los tweets publicados durante esas fechas relacionados con la contaminación.

Este trabajo se encuentra en su versión inicial, por lo existen provincias que no cuentan con información para ciertos periodos de tiempo entre 2014 y 2015. Destacar que todas las medidas usadas de las estaciones de calidad del aire y opiniones de los usuarios son reales. Pueden verificarse las fuentes en el **ANEXO III** y **ANEXO IV** de la [memoria](https://github.com/Javi1993/ISI/blob/master/100290698_100290892.pdf).

## Objetivos

El objetivo es abordar el tema de la contaminación atmosférica en España desde diferentes puntos de vista para su posterior análisis. Para esta meta se han usado datos validados de las estaciones de calidad del aire de las diferentes provincias del país y opiniones sobre la contaminación realizadas por sus habitantes en Twitter.

Se quiere lograr hacer una comparación de los niveles de contaminación reales extraídos de las estaciones con lo que realmente percibe y expresa la gente mediante sus mensajes en Internet. Se mostrarán niveles de todas las provincias de España según los datos oficiales de las estaciones de aire y de la misma manera, para estos lugares, comprobaremos si las personas también perciben y expresan este hecho mediante su opinión online. *Ej: En Madrid el nivel de NO2 medido por las estaciones es muy alto y se observan quejas en twitter de cómo el gran volumen de tráfico afecta al aire de la ciudad*.
Toda esta información se mostrará en unos dashboards intuitivos y completos, que permitirán analizar una provincia de forma individual o comparándola con otra.

Se partirá de los datos recogidos por las estaciones de calidad del aire de España durante los años 2014-2015. A la hora de mostrar la información de estos datos se usarán medias diarias.
Se cuenta con datos de un total de 331 estaciones de calidad del aire. Estas estaciones abarcan todo el territorio español salvo las provincias de las CCAA de Andalucía y Extremadura, y las ciudades autónomas de Ceuta y Melilla, debido a la falta de documentación compatible con la que se pudiera trabajar y extraer datos. 

Se pueden consultar el número de estaciones de aire recopiladas por provincia en la tabla del **ANEXO I** de la [memoria](https://github.com/Javi1993/ISI/blob/master/100290698_100290892.pdf). Los contaminantes medidos por las estaciones y que se analizarán en la solución final se pueden ver en el **ANEXO II**. Matizar que cada estación de calidad de aire no mide todos los contaminantes listados en ese apartado, esto dependerá de su ubicación y función.
En cuanto la opinión social, se recopilaron tweets entre 2014-2015 en base a mensajes publicados con los temas, hashtags y cuentas listados en el **ANEXO III**. En esta versión inicial únicamente se usarán tweets entre el 1 de noviembre y 31 de diciembre de 2015 debido a limitaciones con la API de Twitter.

## Tecnologías

Este trabajo utiliza diferente aplicaciones y herramientas externas para la correcta recopilación y visualización de los datos sobre contaminación. A continuación se hace un listado de las principales que se han usado junto con su funcionalidad:

* **MongoDB:** Como base de datos para el almacenamiento de las medidas tomadas por las estaciones de calidad del aire y para los mensajes sobre la contaminación en Twitter de los usuarios.
* **CartoDB:** Usado para la representación, utilizando mapas, de la evolución de la contaminación a lo largo del tiempo en las provincias.
* **Meaningcloud:** Analizador de tweets para detectar si hablan de contaminación, lo positivo o negativo del mensaje y para localizar la provincia a la que hacen referencia.
* **Twitter API:** Herramienta usada para la recopilación de tweets en base a hastags, palabras y cuentas.
* **Google geocoding:** Herramienta usada para obtener la provincia asociado a cada estación de calidad del aire en base a sus coordenadas.

## Instalación

Tener MongoDB y un servidor de aplicaciones J2EE (Tomcat, Wildfly, ...) en la máquina donde vaya a ejecutarse la aplicación. Hay que realizar una primera recopilación de datos debido a que en un principio la base de datos estará vacía, para ello:
* **Calidad del aire:** Se proporcionan los CSV con los datos entre 2014-2015. Para introducirlos en la base de datos basta con ejecutar una vez la clase [Aire.java](https://github.com/Javi1993/ISI/blob/master/DATOS/src/main/java/com/isi/master/DATOS/Aire.java).
* **Tweets:** Por motivos de privacidad no se proporcionan los tweets usados. Para recopilar una muestra basta con ejecutar las veces que se quiera, hasta tener una cantidad considerable, la clase [Personas.java](https://github.com/Javi1993/ISI/blob/master/DATOS/src/main/java/com/isi/master/DATOS/Personas.java).

*Comprobar que el puerto de la BBDD es el correcto y que la KEY de meaningcloud como de Twitter API tiene validez. Si tiene problemas con esto último crear nueva cuenta para usar estas API*

Una vez se tienen los datos basta con montar en el servidor de aplicaciones el EAR [Visualizacion](https://github.com/Javi1993/ISI/tree/master/Visualizacion) y con cualquier navegador ya podrá acceder a la aplicación web.

## Test

A continuación se adjuntan una serie de capturas sobre las principales funcionalidades que proporciona esta versión inicial de la aplicación:

* **Opinión solcial:** Análisis de una provincia en base a las opiniones de los ciudadanos en Twitter.
![A Coruña](http://oi67.tinypic.com/2i8yckg.jpg)

* **Provincia individual:** Análisis de una provincia en base a las medidas recogidas por las estaciones de calidad del aire.
![Barcelona](http://i64.tinypic.com/29ase50.png)

* **Comparador de provincias:** Comparación provincia vs procincia en base a un contaminante elegido.
![Álava VS Barcelona](http://i65.tinypic.com/2qkhspd.png)
