# 工具说明
用于将消息发送到kafka指定source队列，并从kafka的target队列监听消息用log输出到控制台

## 打包编译方法
```shell script
# mvn clean package -DskipTests
# docker build -t kafka_demo:1.0 .
```

## 启动
```shell script
# docker run -p 50001:50001 -d --restart always --name kafka_demo kafka_demo:1.0
```

## 发送
sendTimes表示一次请求发送多少条数据（连续发送中间不停顿）

直接浏览器输入这个地址就开始发送了（GET请求）：http://localhost:50001/kafka/test


## 关于配置

    bootstrap-servers： kafka集群地址
    source-topic：发送到哪个队列
    target-topic：监听哪个队列
    send-times：发送次数
