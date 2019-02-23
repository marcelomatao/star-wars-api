FROM openjdk:8-jdk

VOLUME /tmp

ADD star-wars-*.jar /app/app.jar

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]

EXPOSE 8080