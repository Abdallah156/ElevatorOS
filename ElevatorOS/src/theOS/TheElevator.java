package theOS;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import deviceManager.Buttons;
import deviceManager.Door;
import processManager.Processes;
import processManager.SystemCalls;

public class TheElevator {

	// floors
	protected static int currFloor = 0; // Initially starts at ground floor
	protected static int targetFloor;

	public static int getCurrFloor() {
		return currFloor;
	}

	public static int getTargetFloor() {
		return targetFloor;
	}

	public static void main(String[] args) throws InterruptedException {

		// testing

		while (true) {
			System.out.println("The Elevator is running...");
			long t1 = System.nanoTime();
			move();
			long t2 = System.nanoTime();
			long execTime = t2 - t1;
			Processes.sb.append(" Total Execution time: " + execTime + " nano seconds");
			Processes.sb.append('\n');
			Processes.sb.append('\n');
			Processes.systemLogger();
			System.out.println();

		}
	}

	public static void move() throws InterruptedException  {

		System.out.println("Enter Desired Floor");
		Scanner s = new Scanner(System.in);
		targetFloor = s.nextInt();

		// 100 = brakes
		if (targetFloor == 100) {
			Processes.createNewProcess(SystemCalls.breakInterrupt);

		} else {

			Buttons y = new Buttons(targetFloor);
			int x = targetFloor - currFloor;

	

			while (x != 0) {

				if (x > 0) {
					currFloor++;
					x--;
					TimeUnit.SECONDS.sleep(1);
				} else {
					currFloor--;
					x++;
					TimeUnit.SECONDS.sleep(1);
				}

				System.out.println("Current Floor Number : " + currFloor);

			}

			currFloor = targetFloor;

			Door d = new Door(true);
			Processes.createNewProcess(SystemCalls.closeDoor);
			d.setOpen(false);
			Processes.createNewProcess(SystemCalls.callElevator);
			Processes.createNewProcess(SystemCalls.announceFloor);
			Processes.createNewProcess(SystemCalls.openDoor);
			d.setOpen(true);

			System.out.println("\n" + "You have arrived at floor number :" + currFloor);

		}

	}

}
