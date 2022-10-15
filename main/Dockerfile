FROM amazoncorretto:11-alpine-jdk
COPY target/main-service*.jar main_service.jar
ENTRYPOINT ["java","-jar","/main_service.jar"] 
