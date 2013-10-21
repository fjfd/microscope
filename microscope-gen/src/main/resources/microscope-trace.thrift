//************** trace related structs **************//

namespace java com.vipshop.microscope.thrift

// system status type
enum EndPointType {
	CPU, MEMORY, IPV4, JVM
}

// record system status
struct EndPoint {
   1: list<map<EndPointType, string>> values
   2: string text
}

// annotation type
enum AnnotationType { CS, CR, SS, SR, MSG, KV }

// annotation means some kind event
struct Annotation {
  1: i64 timestamp                 // microseconds from epoch
  2: EndPoint endPoint,                     
  3: AnnotationType type           // event type?
}

// span means some kind method
struct Span {
  1: i64 trace_id                               // unique trace id, use for all spans in trace
  3: string name,                               // span name, rpc method for example
  4: i64 id,                                    // unique span id, only used for this span
  5: optional i64 parent_id,                    // parent span id
  6: list<Annotation> annotations,              // list of all annotations/events that occured
  7: optional bool debug = 0,                   // if true, we DEMAND that this span passes all samplers
  8: string app_name,                           // message head
  9: i32 order                                  // span order
  10: i32 duration                              // how long did it take?
  11: i64 startstamp                            // start timestamp
}

