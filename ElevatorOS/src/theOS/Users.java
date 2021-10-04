package theOS;

public class Users extends Thread {

	protected final static int maxUsers = 5;
	protected static boolean usersLimitAlert = false;
	protected static int currUsers = 0;

	public static void enterElevator() {
		currUsers++;
	}

	public static void leaveElevator() {
		currUsers--;
	}

	public static void limitAlert() {

		if (currUsers > maxUsers) {
			usersLimitAlert = true;
		} else {
			usersLimitAlert = false;
		}
	}
	
	public static void callElevator() {
		
	}

}
