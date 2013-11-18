package com.vipshop.microscope.mysql.report;

public class MsgReport extends AbstraceReport{
	
	private long msgNum;
	private long msgSize;
	
	public long getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(long msgNum) {
		this.msgNum = msgNum;
	}


	public long getMsgSize() {
		return msgSize;
	}


	public void setMsgSize(long msgSize) {
		this.msgSize = msgSize;
	}

	@Override
	public String toString() {
		return "MsgReport [year=" + year + ", month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", msgNum=" + msgNum + ", msgSize=" + msgSize + "]";
	}

}
