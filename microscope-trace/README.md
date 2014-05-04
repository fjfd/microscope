microscope-trace

                           1 trace client send
                           2 trace client receive
                           3 generate span
                           4 put span to queue
                           5 send span to server async

                           microscope-trace structure

                           +-------+
                           | send  |--------+
                           +-------+        |    +------+    +-------+    +--------+    +--------+
                                            |--->| span |--->| queue |--->| client |--->| server |
                           +-------+        |    +------+    +-------+    +--------+    +--------+
                           |receive|--------+
                           +-------+