EASIT4ALL
=========

Description
-----------

The EASIT4ALL project aims to provide to any person independently of its disability an accessible way  
to use main functionalities of social networks. Thus, Easit4all solution consits of a web interface that merges in a
simple and intuitive way different social networks. Additionally, the tool provides a set of plugins 
that makes it more usable and accessible for the final users (e.g. most common operations or self-configuration of visual features).
The ultimate objective of the tool is the auto-configuration of the application accessible features to the user needs. 
This functionality will be provided thanks to the GPII/Cloud4all framework.

The official website is available at http://www.easit4all.com 

Technicalities
--------------
The Easit4All software solution is a simple java based framework composed of 
three main modules: core, dao and web. The first one contains core components such as configuration, 
controllers, handlers or plugins. The second module contains the data managers and data access objects of the application domain 
and the last module is the web graphical interface and the files to set up the application.

Easit4all uses Java Spring Framework (MVC, Security, JPA and Social), HTML5, JQuery, CSS3 and MySQL database.

License
-------

This project has been developed by Barcelona Digital eHealth department. Easit4all is shared under New BSD license. 
This project folder includes a license file. You can share and distribute this project and built software using New 
BSD license. Please, send any feedback to easit4all@gmail.com

Installation
------------

Basically, we use maven to compile and launch the website. 
(Maven 2.0 must be installed and configured in the local machine)

1.- Execute install file located in lib folder. This script will install local jar files into your local maven repository

2.- Create the MySQL schema database
- Use mysql dump file which is found at easit4all/dao/src/db
- Create a mysql user and add privileges to this schema

3.- Modify properties file accordingly.
- Change database properties
- Change mailing properties
- Change Social providers key words

4.- Clean and compile the sources
- mvn clean install

Quick Start & Examples
----------------------

To run the website under a tomcat server plugin
- mvn tomcat:run


Running Tests
-------------

The maven installation by default executes all tests created for the project
- mvn clean install


Acknowledgements
----------------

The idea of easit4all project was originally created for the European Project Cloud4All (FP7-289016) by the Active Independent Living Group 
within the eHealth Research Department of Barcelona Digital.

Authors
-------

Xavier Rafael-Palou (main contact) and Cristina Palmero

