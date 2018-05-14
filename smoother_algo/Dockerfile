FROM tomcat:9.0.7-jre9-slim

WORKDIR /usr/local/tomcat

ENV EXTRA_CATALINA_OPTS=" -server -Xms256m -Xmx2048m -Dfile.encoding=UTF-8"
ADD setenv.sh bin/
ADD target/smoother-algo.war webapps/ 