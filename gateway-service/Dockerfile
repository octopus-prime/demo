FROM openjdk:10
#MAINTAINER Mike Gresens <mike.gresens@gmail.com>
#
#EXPOSE 8080

ADD build/libs/*.jar app.jar

# Regarding settings of java.security.egd, see http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
