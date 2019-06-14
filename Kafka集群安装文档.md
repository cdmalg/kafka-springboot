## Kafka集群安装文档

1. **下载**

   官网地址：<http://kafka.apache.org/downloads>

   版本：2.2.1

2. **安装**

   - 首先确保服务器已安装jdk（版本建议1.8)

   - 该版本自带zookeeper，无需安装

   - 将官网下载到的包解压即可

3. **集群安装**

   - 搭建zookeeper集群**（zookeeper 集群节点数量要是奇数）**

   - 修改kafka配置文件 

     ```
     broker.id=0     				   #当前机器在集群中的唯一标识，和zookeeper的myid性质一样
     listeners=PLAINTEXT://:9092        #当前kafka对外提供服务的端口默认是9092
     num.network.threads=3              #这个是borker进行网络处理的线程数
     num.io.threads=8                   #这个是borker进行I/O处理的线程数
     socket.send.buffer.bytes=102400    #发送缓冲区buffer大小，数据不是一下子就发送的，先回存储到缓冲区了到达一定的大小后在发送，能提高性能
     socket.receive.buffer.bytes=102400 #kafka接收缓冲区大小，当数据到达一定大小后在序列化到磁盘
     socket.request.max.bytes=104857600 #这个参数是向kafka请求消息或者向kafka发送消息的请请求的最大数，这个值不能超过java的堆栈大小
     log.dirs=/opt/kafka/kafka-logs     #消息存放的目录，这个目录可以配置为“，”逗号分割的表达式，上面的num.io.threads要大于这个目录的个数这个目录，如果配置多个目录，新创建的topic他把消息持久化的地方是，当前以逗号分割的目录中，那个分区数最少就放那一个
     num.partitions=3                   #默认的分区数，一个topic默认1个分区数  
     offsets.topic.replication.factor=2 #kafka保存消息的副本数，如果一个副本失效了，另一个还可以继续提供服务
     log.retention.hours=168            #默认消息的最大持久化时间，168小时，7天
     log.segment.bytes=1073741824       #这个参数是：因为kafka的消息是以追加的形式落地到文件，当超过这个值的时候，kafka会新起一个文件
     log.retention.check.interval.ms=30 #每隔30毫秒去检查上面配置的log失效时间，到目录查看是否有过期的消息如果有，删除
     zookeeper.connect=192.168.118.14:2181,192.168.118.15:2181  #设置zookeeper的连接端口
     zookeeper.connection.timeout.ms	  #zookeeper leader切换连接时间，默认6秒
     ```

     [^必需修改参数：]: broker.id，zookeeper.connect，其他参数看情况修改

4. **服务启动**

   - 在集群服务器的相应kafka所在目录执行命令进行后台启动

     ```
     ./kafka-server-start.sh -daemon ../config/server.properties
     ```

   - 默认端口为9092

5. **测试集群启动状态**

   - 创建topic

     ```
     ./kafka-topics.sh --create --zookeeper 192.168.102.144:2181 --replication-factor 3 --partitions 3 --topic testKafka
     
     --replication-factor 3   	#复制3份
     --partitions 3 				#创建3个分区
     --topic 					#主题名称
     ```

   - 在一台服务器上创建一个发布者

     ```
     ./kafka-console-producer.sh --broker-list 192.168.102.144:9092 --topic testKafka
     ```

   - 在一台服务器上创建一个消费者

     ```
     ./kafka-console-consumer.sh --bootstrap-server 192.168.102.144:9092 --topic testKafka --from-beginning
     ```

   - 查看 topic

     ```
     ./kafka-topics.sh --list --zookeeper 192.168.102.144:2181
     ```

   - 查看 topic 状态

     ```
     ./kafka-topics.sh --describe --zookeeper 192.168.102.144:2181 --topic testKafka
     ```

     

6. **kafka-manager**

   - kafka-manager 主要用来管理 kafka 集群

   - 安装kafka-manager

     - 安装环境必须存在jdk

     - 源码地址：<https://github.com/yahoo/kafka-manager>

     - kafka-manager需要sbt编译，如果没有需要install一下。另：sbt默认下载库文件很慢，可以将库地址改为阿里云的镜像（修改sbt库地址请自行百度）

     - 解压后在对应目录执行命令进行编译

       ```
       ./sbt clean dist
       ```

     - 编译好后会提示编译好的包路径，找到位置解压编译好的包即完成安装

   - 修改配置文件

     ```
     kafka-manager.zkhosts="192.168.102.144:2181,192.168.102.145:2181" #zk集群
     #kafka-manager.zkhosts=${?ZK_HOSTS}   							  #注释该字段
     ```

   - 后台启动kafka-manager

     ```
     nohup bin/kafka-manager -Dconfig.file=conf/application.conf -Dhttp.port=8090 1>/dev/null 2>&1 &
     ```

   - 完成后访问配置后即可使用

     ```
     http://192.168.102.144:8090/
     ```

7. **目前测试kafka部署情况**

   - kafka集群

     | 192.168.102.144：9092 | server1 |
     | --------------------- | ------- |
     | 192.168.102.145：9092 | server2 |
     | 192.168.102.146：9092 | server3 |

   - zk集群

     | 192.168.102.144：2181 | zk1  |
     | --------------------- | ---- |
     | 192.168.102.145：2181 | zk2  |
     | 192.168.102.146：2181 | zk3  |

   - kafka-manager服务
   
     ```
     192.168.102.144：8090
     ```
   
     

