EASIT4ALL
=========

Description
-----------

The EASIT4ALL project is a web application that provides an easy and accessible way to use social networks for a broad
audience. For this purpose the application enables self-configuration of visual features and a simple and intuitive graphical
interface. The official website is available at http://www.easit4all.com 

This web application is developed using Java Spring Framework (MVC, Security and Social)
 

License
-------

This project has been developed by Barcelona Digital Health department. Easit4all is shared under New BSD license. This project folder includes a license file. You can share and distribute this project and built software using New BSD license. Please, send any feedback to http://www.cloud4all.info

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

  
