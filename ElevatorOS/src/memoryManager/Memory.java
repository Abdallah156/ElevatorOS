package memoryManager;

import processManager.Processes;

public class Memory {

	

	int size;
	boolean isFull;
	Object memoryLocation [] = new Object[size];
	
	public Memory(int s , boolean b , Processes[] m) {
		
		this.size = s;
		this.isFull = b;
		this.memoryLocation = m;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public Object[] getMemoryLocation() {
		return memoryLocation;
	}

	public void setMemoryLocation(Processes[] memoryLocation) {
		this.memoryLocation = memoryLocation;
	}
	
	
}
