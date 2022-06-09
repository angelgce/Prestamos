#/usr/bin/bash

CONF=/home/Prestamos
CP=/home/Prestamos/models
LIB=/home/Prestamos/libraries

clear
clear 

javac -cp $CP:$LIB/ini4j-0.5.4.jar:$LIB/commons-cli-1.4.jar $CP/*.java

java -cp $CP:$LIB/ini4j-0.5.4.jar:$LIB/commons-cli-1.4.jar Main -c $CONF/config.ini
