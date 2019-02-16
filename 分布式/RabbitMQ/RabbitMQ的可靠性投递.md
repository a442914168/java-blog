###### 效率和可靠性是无法兼得的，如果要保证每一个环节都成功，势必会对消息的收发效率造成影响。  如果是一些业务实时一致性要求不是特别高的场合，可以牺牲一些可靠性来换取效率

##### RabbitMQ 消息传递的流程
![image](http://note.youdao.com/yws/res/1672/CA8B2AC043634243AD914D4FBF2243BE)
-   step1：生产者发送消息到Exchange(交换机);
-   step2：交换机经过路由找到对应的Queue;
-   step3：消息在Queue中储存;
-   step4：消费者订阅Queue并消费消息;


#### 1、确保消息发送到RabbitMQ服务器

##### 存在风险：

    由于网络或者整个Rabbit服务器的失效会导致step1（生产者发送消息到Exchange）的失败，
    而生产者是无法知道消息是否正确发送到Rabbit服务器上的。

##### 解决方案（事务模式、确认模式）

一、Transaction（事务）模式：  

    通过channel.txSelect开启事务之后，我们便可以发送消息给RabbitMQ，如果事务提交成功，则消息一定发送了RabbitMQ中，
    如果在事务提交执行之前由于RabbitMQ异常崩溃或者其它原因抛出异常，
    这个时候我们便可以将其捕获，进而通过执行channel.txRollback方法来实现事务回滚。   
    使用事务机制的话会使RabbitMQ的性能大幅度降低，一般不建议使用。

二、Confirm（确认）模式：  

```java
生产者通过调用channel.confirmSelect方法（即Confirm.Select命令）将通信设置为confirm模式。  
一旦消息被投递到所匹配的队列之后，RabbitMQ就会发送一个确认（Basic.Ack）给生产者（包含消息的唯一ID）
这就使得生产者知晓消息已正确到达了目的地了。
```


#### 2、确保消息路由到正确的队列
##### 存在风险：

    可能因为路由关键字错误，或者队列不存在，或者队列名称错误导致step2（交换机经过路由找到对应的Queue）的失败

##### 解决方案   

一、监听返回

    使用mandatory参数和ReturnListener，可以实现消息无法路由的时候返回给生产者。

二、备份交换机   

```java
使用备份交换机（alternate-exchange），无法路由的消息会发送到这个交换机上。
Map<String,Object> arguments = new HashMap<String,Object>();
arguments.put("alternate-exchange","ALTERNATE_EXCHANGE"); // 指定交换机的备份交换机
channel.exchangeDeclare("TEST_EXCHANGE","topic", false, false, false, arguments);
```



#### 3、确保消息在队列正确地储存
##### 存在风险：   

    可能因为系统宕机、重启、关闭等等情况导致储存在队列的消息丢失，会导致step3（消息在Queue中储存）的失败

##### 解决方案   

一、队列持久化

```java
// String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
channel.queueDeclare(QUEUE_NAME, true, false, false, null);
```

二、交换机持久化

```java
 // String exchange, boolean durable
channel.exchangeDeclare("MY_EXCHANGE","true");
```

三、消息持久化

```java
 AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder() 
        .deliveryMode(2) // 2代表持久化，其他代表瞬态
        .build();
        
channel.basicPublish("", QUEUE_NAME, properties, msg.getBytes());
```

四、集群，镜像队列


#### 4、确保消息从队列正确地投递到消费者
##### 存在风险：   

    如果消费者收到消息后未来得及处理即发生异常，或者处理过程中发生异常，会导致step4（消费者订阅Queue并消费消息）的失败

##### 解决方案

    为了保证消息从队列可靠地到达消费者，RabbitMQ提供了消息确认机制（message acknowledgement）。
    消费者在订阅队列时，可以指定autoAck参数，当autoAck等于false时，RabbitMQ会等待消费者显示地回复确认信号后才从队列中移除消息。
    
    如果消息消费失败，也可以调用Basic.Reject或者Basic.Nack来拒绝当前消息而不是确认。
    如果requeue参数设置为true，可以把这条消息重新存入队列，以便发送给下一个消费者（只有一个消费者的时候，
    这种方式可能会出现无限循环重复消费情况，可以投递到新的队列中，或者只打印异常日志）。


​    
#### 5、消费者回调
消费者处理消息后，可以再发送一条消息给生产者，或者调用生产者的API，告知消息处理完毕。

```java
例如调用支付的api，支付成功后，会收到支付状态的回调
```


​    
#### 6、补偿机制
对于一定时间没有得到响应的消息，可以设置一个定时重发的机制，但是要控制次数，比如最多重发3次，否则会造成消息堆积。


​    
​    
​    




​    