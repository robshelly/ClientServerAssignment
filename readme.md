# Client Server System

This simple Java App designed for Distributed Systems assignment. Consists of a Client-Server systems. Clients (students) can connect to the server and request and grades for their modules. The server connects to a MySQL database to retrieve the students records, calculates the overall grade and sends it to the client.


#### Dependencies

* [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/3.1.html)
* App also depends on the an existing MySQL database (.sql file for the DB can be found in project root directory) with the following tables created:
  * students
  * modulegrades