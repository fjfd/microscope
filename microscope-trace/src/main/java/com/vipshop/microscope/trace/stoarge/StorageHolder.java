package com.vipshop.microscope.trace.stoarge;

/**
 * Get singleton message storage.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class StorageHolder {
	
	private static final int key = 1;
	
	public static Storage getStorage() {
		return getStorage(key);
	}
	
	public static Storage getStorage(int key) {
		switch (key) {
		case 1:
			return getArrayBlockingQueueStorage();
		case 2:
			return getDisruptorQueueStorage();
		case 3:
			return getNonBlockingQueueStorage();
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
	
	private static class DisruptorQueueStorageHolder {
		private static final Storage storage = new DisruptorQueueStorage();
	}
	
	private static Storage getDisruptorQueueStorage() {
		return DisruptorQueueStorageHolder.storage;
	}
	
	private static class NonBlockingQueueStorageHolder {
		private static final Storage storage = new NonBlockingQueueStorage();
	}
	
	private static Storage getNonBlockingQueueStorage() {
		return NonBlockingQueueStorageHolder.storage;
	}

}
