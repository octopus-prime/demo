FROM openjdk:10
#MAINTAINER Mike Gresens <mike.gresens@gmail.com>

#ENV DOCKERIZE_VERSION v0.6.1
#RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
#    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

ADD build/libs/*.jar app.jar

# Regarding settings of java.security.egd, see http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Entropy_Source
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "http://discovery-service:8761/actuator/health", "java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
