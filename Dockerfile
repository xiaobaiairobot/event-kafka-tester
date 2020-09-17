FROM openjdk:8-alpine
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY ./target/event-kafka-tester-1.0-SNAPSHOT.jar /home
ENTRYPOINT java $JAVA_OPTS -jar /home/event-kafka-tester-1.0-SNAPSHOT.jar