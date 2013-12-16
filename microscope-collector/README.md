microscope-collector

1 receive span from client by thrift
2 put span to disruptor (RingBuffer)
3 storage span to hbase
4 analyze span to mysql


microscope-collector structure

+--------+     +---------------+    +------------+          +----------------+     +---------------+     +-------+
| client | --->| Thrift Server | -->| RingBuffer | --+----->| Storage Thread | --->|Storage Service| --->| Hbase |
+--------+     +-----+---------+    +------------+   |      +----------------+     +---------------+     +-------+
                     |                               |      +----------------+     +---------------+     +-------+
			         |								 +----->| Analyze Thread | --->|Report Service | --->| MySQL | 		 
+--------+           |                                      +----------------+     +---------------+     +-------+
| client | ----------+
+--------+