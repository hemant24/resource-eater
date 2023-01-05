FROM openjdk:11-jre-slim
ARG JAR_NAME=app.jar
COPY ./target/${JAR_NAME} app.jar
EXPOSE 8080
EXPOSE 8090
CMD ["java", \
"-Dcom.sun.management.jmxremote", \
"-Dcom.sun.management.jmxremote.port=8090", \
"-Dcom.sun.management.jmxremote.local.only=false", \
"-Dcom.sun.management.jmxremote.authenticate=false", \
"-Dcom.sun.management.jmxremote.ssl=false", "-jar", "app.jar"]