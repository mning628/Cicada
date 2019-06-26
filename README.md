# Cicada

email:975670918@qq.com

maning all demo

ZK-Lock模块为zk分布式锁的实现，以临时顺序节点实现~

实现思路：就是某个节点尝试创建临时顺序znode，此时创建成功了就获取了这个锁；这个时候别的客户端来创建锁会失败，只能注册个监听器监听这个锁。释放锁就是删除这个znode，一旦释放掉就会通知客户端，然后有一个等待着的客户端就可以再次重新加锁。



Distributed-Transaction-A
Distributed-Transaction-B
以上两个模块通过消息队列实现了分布式事务，算是初体验~~~

思路参考：http://www.17coding.info/article/20?from=timeline&isappinstalled=0#10006-weixin-1-52626-6b3bffd01fdde4900130bc5a2751b6d1

//Todo   定时器扫描A系统流水表，补偿到消息队列

//Todo   代码整理，抽取配置项~~~~

//Todo   估计还有很多没想到的点，有问题请告知

