# adobe-air-db-connector
Java based database connector bridge for adobe air application (From Attic)
**This project is migrated from google code archive**

AIR DB Connector for Adobe AIR application that requires to connect local database without any web application server.

## Introduction

Adobe AIR applications are not able to connect to local database. This library provides API to connect to local database.

Read More on **[this blog](https://myjavaacademy.com/connecting-to-local-database-in-adobe-air/)**

## Details

This library has three parts:

* Java application that sits between your AIR application and local database.
* AirConnector.swc library for air applications providing API to communicate through java application.
* Sql Config generator - Utility to generate SQL configuration file having pre-defined sql queries.

This helps AIR application to connect any local database such as mysql, oracle, ms-sql.

*As of now, first release only supports database connection through ODBC DSN.*
