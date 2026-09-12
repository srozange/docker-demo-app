FROM tomcat:10-jdk11-openjdk

USER root

COPY target/docker-demo-app.war /usr/local/tomcat/webapps

COPY config-container.properties /usr/local/tomcat/conf/config.properties

ENV CONFIG_PATH /usr/local/tomcat/conf
