package deviceManager;

public class Door {

	boolean isOpen;
	
	
	public boolean isOpen() {
		return isOpen;
	}


	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}


	public Door(boolean b) {
		this.isOpen = b;
	}
	
}
