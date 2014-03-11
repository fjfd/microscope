package com.vipshop.microscope.trace.stoarge;

/**
 * Get singleton message storage
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class StorageHolder {
	
	private static final int key = 1;
	
	public static Storage getStorage() {
		switch (key) {
		case 1:
			return getArrayBlockingQueueStorage();
		case 2:
			return getDisruptorStorage();
		case 3:
			return getLog4jStorageHolder();
		default:
			return getArrayBlockingQueueStorage();
		}
	}
	
	private static class ArrayBlockingQueueStorageHolder {
		private static final Storage storage = new ArrayBlockingQueueStorage();
	}
	
	private static Storage getArrayBlockingQueueStorage() {
		return ArrayBlockingQueueStorageHolder.storage;
	}
	
	private static class DisruptorStorageHolder {
		private static final Storage storage = new DisruptorStorage();
	}
	
	private static Storage getDisruptorStorage() {
		return DisruptorStorageHolder.storage;
	}
	
	private static class Log4jStorageHolder {
		private static final Storage storage = new Log4jStorage();
	}
	
	private static Storage getLog4jStorageHolder() {
		return Log4jStorageHolder.storage;
	}

}
