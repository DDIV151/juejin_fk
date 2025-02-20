FROM openjdk:17-jdk-alpine
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY /juejin_fk-0.11.4.5.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]