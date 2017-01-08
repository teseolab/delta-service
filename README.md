# Delta Service

Server-side support infrastructure for Delta Android app.

Master Thesis project developed by Mikael Reiersølmoen.


## Deploy the server

The service was deployed on a virtual machine running Linux Ubuntu.

### Install Java

I installed java like described here: https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get

### Install PostgreSQL

https://www.postgresql.org/download/linux/ubuntu/

How the application connects to the database is defined in src/main/resources/hibernate.cfg.xml. Here you can specify database IP-address and database name, database user and password.

The following line needs to be added to etc/postgresql/9.3/main/pg_hba.conf:
```
host    delta-db        postgres        129.241.113.73/24       trust
```
“delta-db” is the name of the database.
“postgres” is the database user that should be allowed to connect.
“129.241.113.73” is the IP-address that should be allowed to connect.

If you want to connect to the database from a remote machine another line for this have to be added. “delta-db” and “postgres” can be replaced with “all” to be less restrictive.

To make sure the database listens to TCP/IP connections the “listen_addresses” property in etc/postgresql/9.3/main/postgresql.conf must be like this:
```
listen_addresses = '*'
```
A specific IP-address could also be specified instead of *.

The database needs to be restarted for the changes to take effect.
Change IP-address in src/main/java/no/ntnu/mikaelr/util/Constants.java

### Run the application

To make sure that the application always runs on the server it is started in a script that runs when the server boots. A file called delta.conf is placed in etc/init:
```
description "delta"
author "Mikael"

start on runlevel [1234]
stop on shutdown

expect fork

script
    cd /home/mikaelr/
    sudo -u mikaelr java -jar /home/mikaelr/delta-backend-1.0.jar >/home/mikaelr/delta.log 2>&1
    emit delta_running
end script
```
When the application runs like this, these commands can be used to run/restart/stop:
```
sudo service delta start
sudo service delta restart
sudo service delta stop
```
### Run the server locally

Install PostgreSQL in your machine.
Uncomment line 9 and comment line 10 in src/main/resources/hibernate.cfg.xml.
The property in line 12 can be changed from "update" to "create-drop" to recreate the database tables.

I used IntelliJ IDEA to run the application. It is run with a Maven run configuration, with command line ```spring-boot:run```.

[![run-configuration.png](https://s28.postimg.org/cscfrqpa5/run_configuration.png)](https://postimg.org/image/dhv843ptl/)

#### Values in the app that need to be changed

SERVER_URL in app/src/main/java/no/ntnu/mikaelr/delta/util/Constants.java

