//************** metric related structs **************//

namespace java com.vipshop.microscope.thrift

struct SysStatus {
  1: i32 cpu,
  2: i32 memory,
  3: string thread,
  4: i32 disk                      
}