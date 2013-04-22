EASIT4ALL
=========

Description
-----------

The EASIT4ALL project aims to provide to any person independently of its disability an accessible way  
to use main functionalities of social networks. Thus, Easit4all solution consits of a web interface that merges in a
simple and intuitive way different social networks. Additionally the tool provides a set of plugins 
to make more usable and accessible the tool to the final users (e.g. such as most common operations or self-configuration of visual features).
The ultimate objective of the tool is the auto-configuration of the application accessible features to the user needs. 
This functionality will be provided thanks to the GPII/Cloud4all framework.

The official website is available at http://www.easit4all.com 

Technicalities
--------------
The Easit4All solution is simple Java based framework composed of 
three main modules: core, dao and web. The first one contains core components such as configuration, 
controllers, handlers or plugins. The second module contains the data managers and data access objects of the application domain 
and the last module is the web graphical interface and the files to set up the application.

Easit4all uses Java Spring Framework (MVC, Security, JPA and Social), Maven and MySQL database

License
-------

This project has been developed by Barcelona Digital Health department. Easit4all is shared under New BSD license. 
This project folder includes a license file. You can share and distribute this project and built software using New 
BSD license. Please, send any feedback to http://www.easit4all.com

Installation
------------

A MySQL schema database should be created.
- Use mysql dump file which is found at easit4all/dao/src/db
- Create a mysql user and add privileges to this schema

Modify properties file accordingly.
- Change database properties
- Change mailing properties
- Change Social providers key words


Quick Start & Examples
----------------------

Basically we use maven to compile and launch the website. 
At least Maven 2.0 must be installed and configured in the machine. 
Also a jave IDE (e.g. eclipse) should be preferred to have it installed.

To clean and compile the sources
- mvn clean install

To run the website under a tomcat server plugin
- mvn tomcat:run


Running Tests
-------------

The maven installation by default executes all tests created for the project
- mvn clean install


Acknowledgements
----------------

The idea of easit4all project was originally created for the European Project cloud4all (FP7-289016) by the Active Independent Living Group 
within the eHealth Research Department of Barcelona Digital.

Authors
-------

Xavier Rafael-Palou (main contact) and Cristina Palmero
