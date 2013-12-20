//************** trace related structs **************//

namespace java com.vipshop.microscope.thrift.gen

// span means a method invoke
// span means a block of code
struct Span {
  1: string appName,                            // client name
  2: string appIp,                              // client ip adress
  3: i64 traceId                                // unique trace id, use for all spans in trace
  4: i64 spanId,                                // unique span id, only used for this span
  5: i64 parentId,                              // parent span id
  6: string spanName,                           // span name, rpc method for example
  7: string spanType,                           // span type, SQL/Cache/URL for example
  8: i64 startTime,                             // start timestamp
  9: i32 duration,                              // how long did it take
  10: string resultCode,                        // result OK/Exception
  11: i32 resultSize,                           // result size
  12: optional string serverName,			    // server name							
  13: optional string serverIp,	                // server ip adress
  14: map<string, string> debug,                // debug info
}

