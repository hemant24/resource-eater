FROM openjdk:11-jre-slim
ARG JAR_NAME=app.jar
COPY ./target/${JAR_NAME} app.jar
EXPOSE 8080
EXPOSE 8090
CMD ["java", \
"-jar", "app.jar"]