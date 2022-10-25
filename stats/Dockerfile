FROM amazoncorretto:11-alpine-jdk
COPY target/ewm-stats-server*.jar ewm_stats_server.jar
ENTRYPOINT ["java","-jar","/ewm_stats_server.jar"] 
