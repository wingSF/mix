@Deprecated
#spring-boot集成rabbitmq，演示Queue与Topic的配置用法
前置条件：需要正常运行的rabbitmq

注意：建议采用Spring Cloud Stream方式，可以实现一套代码，只切换依赖就能实现消息队列实现方式切换

# [Rabbitmq](http://www.rabbitmq.com/getstarted.html)
## AMQP协议

## 涉及名词
* producer
    * 生产者，产生消息的一方
* consumer
    * 消费者，消费消息的一方
* queue
    * 消息保存的队列，与consumer一一对应
* exchange
    * 交换机，消息总是通过交换机投递到queue
* routing key
    * 生产者将消息投递给消费者的时候，会指定一个routing key，便于交换机将消息转发给后面的特定的queue
* binding key
    * 绑定规则，queue创建声明的时候，需要指定binding规则，绑定到某个exchange上，exchange在分发消息的是否，根据消息的routing key和多个queue的binding key，判断将消息投递到哪个或者哪几个queue上。

## Exchange
* direct exchange
    * 直连交换机
    * producer发送消息时，指定exchange，指定routing key，exchange通过routing key唯一匹配与某个queue的binding key，投递消息到相应的queue
* topoc exchange
    * 主题交换机
    * queue与该exchange绑定的时候，可以使用通配符。#代表一个单词，$代表一个或多个单词，可转发消息到多个queue
* fanout exchange    
    * 广播交换机
    * procuder不需要指定routing key，queue不需要指定binding key
    
* dead letter exchange
    * 死信交换机
    * 处理那些未被投递成功的消息，前提producer配置x-dead-letter-exchange
        * 投递消息在指定时间内未被消费的消息(消息本身自带超时时间/queue声明的超时时间)
        * 生产比消费快的情况下，超过了queue所能存储的最大范围
        * 未启用autoack的情况下，接收方reject/nack
    