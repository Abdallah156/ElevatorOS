package processManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import memoryManager.Memory;
import theOS.TheElevator;

public class Processes extends Thread {
	
	
	//For the log file
	protected static File File = new File("elevatorLogData.csv");
	public static StringBuilder sb = new StringBuilder();


	// Process states
	private static ArrayList<Processes> New = new ArrayList<>();
	private static ArrayList<Processes> Ready = new ArrayList<>();
	private static ArrayList<Processes> Blocked = new ArrayList<>();
	private static ArrayList<Processes> Running = new ArrayList<>();
	private static ArrayList<Processes> Finished = new ArrayList<>();

	private int process_id;
	private int process_priority;
	private int process_pid;

	// The interrupt process
	private int interrupt_id;
	private int interrput_pid;
	private int interrupt_priority;
	
	
	
	// ------------------------------------------------------------------------------------
	// Getter and Setter methods

	public int getProcess_id() {
		return process_id;
	}

	public int getProcess_priority() {
		return process_priority;
	}

	public int getProcess_pid() {
		return process_pid;
	}

	public void setProcess_id(int process_id) {
		this.process_id = process_id;
	}

	public void setProcess_priority(int process_priority) {
		this.process_priority = process_priority;
	}

	public void setProcess_pid(int process_pid) {
		this.process_pid = process_pid;
	}

	public void setInterrupt_id(int interrupt_id) {
		this.interrupt_id = interrupt_id;
	}

	public void setInterrupt_priority(int interrupt_priority) {
		this.interrupt_priority = interrupt_priority;
	}

	public int getInterrput_pid() {
		return interrput_pid;
	}

	public void setInterrput_pid(int interrput_pid) {
		this.interrput_pid = interrput_pid;
	}

	public int getInterrupt_id() {
		return interrupt_id;
	}

	public int getInterrupt_priority() {
		return interrupt_priority;
	}

	public ArrayList<Processes> getNew() {
		return New;
	}

	public ArrayList<Processes> getReady() {
		return Ready;
	}

	public ArrayList<Processes> getBlocked() {
		return Blocked;
	}

	public ArrayList<Processes> getRunning() {
		return Running;
	}

	public ArrayList<Processes> getFinished() {
		return Finished;
	}

	public void setNew(ArrayList<Processes> new1) {
		New = new1;
	}

	public void setReady(ArrayList<Processes> ready) {
		Ready = ready;
	}

	public void setBlocked(ArrayList<Processes> blocked) {
		Blocked = blocked;
	}

	public void setRunning(ArrayList<Processes> running) {
		Running = running;
	}

	public void setFinished(ArrayList<Processes> finished) {
		Finished = finished;
	}
	// ---------------------------------------------------------------------------------------------------

	// Constructor
	public Processes(int id, int pid, int priority) {
		this.process_id = id;
		this.process_priority = priority;
		this.process_pid = pid;
	}

	// Generate a random ID every time per java run
	private static int idCounter = 1;

	public static int createID() {
		return idCounter++;
	}
	

	// When a new process is called , it gets added to the new Array
	public static void createNewProcess(SystemCalls s)  {

		switch (s) {
		case callElevator:
			int x1 = createID();
			int y1 = createID();
			int z1 = 5;
			Processes p1 = new Processes(x1, y1, z1);
			p1.setName("callElevator");
			New.add(p1);
			break;
		case openDoor:
			int x2 = createID();
			int y2 = createID();
			int z2 = 5;
			Processes p2 = new Processes(x2, y2, z2);
			p2.setName("openDoor");
			New.add(p2);
			break;
		case closeDoor:
			int x3 = createID();
			int y3 = createID();
			int z3 = 5;
			Processes p3 = new Processes(x3, y3, z3);
			p3.setName("closeDoor");
			New.add(p3);
			break;
		case announceFloor:
			int x4 = createID();
			int y4 = createID();
			int z4 = 5;
			Processes p4 = new Processes(x4, y4, z4);
			p4.setName("announceFloor");
			New.add(p4);
			break;
		case breakInterrupt:
			int x5 = createID();
			int y5 = createID();
			int z5 = 5;
			Processes p5 = new Processes(x5, y5, z5);
			p5.setName("breakInterrupt");
			New.add(p5);
			break;
	
		}

	}
	
	

	// Takes all processes in the New state and add them to the Ready state
	public static void createReadyProcess() {
		
		
		
		if(New.get(0).getName().equals("breakInterrupt")) {
			Blocked.add(New.get(0));
			try {
				New.get(0);
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			New.remove(0);
			return;
		}else {
			for (int i = 0; i < New.size(); i++) {
				Ready.add(New.get(i));

			}

			while (!(New.isEmpty())) {
				
				New.remove(0);
			}
		}
		
	

	}

	// Running process will be on a FCFS basis and no timeout will be set since a
	// process must be completed before the next process is running
	// Interrupts will be handled later..
	public static void createRunningProcess() {
		
		Running.add(Ready.get(0));
		Ready.remove(0);
	}
	
	// Finished state
	public static void createFinishedProcess() {
		Finished.add(Running.get(0));
		Running.remove(0);
	}

	public static void systemLogger() {

		// A new memory with size 64 (Assuming each location is 1 Byte = 1 word)
		Processes[] locations = new Processes[64];
		Memory m = new Memory(64, false, locations);

		// New state test
		try (PrintWriter writer = new PrintWriter(File)){
			
			System.out.println("\n" + "System Event Log: ");
			sb.append("System Event Log: ");
			sb.append('\n');
			sb.append('\n');

			
			
			for (int i = 0; i < New.size(); i++) {
				if (m.isFull()) {
					System.out.println("ERROR MEMORY IS FULL");
					break;
				} else {
					locations[i] = New.get(i);
				}
			}

			System.out.println("\n" + "Memory:");
			sb.append("Memory:");
			sb.append(',');
			for (int i = 0; i < locations.length - 1; i++) {
				System.out.print(locations[i] + " ");
				sb.append(locations[i]);
				sb.append(',');
			}
			
			sb.append('\n');
			sb.append('\n');
			
			
			
			if(New.get(0).getName().equals("breakInterrupt")) {
				System.out.println("The Blocked State array:");
				sb.append("The Blocked State array:");
				sb.append('\n');
				long tn = System.nanoTime();
				for (Processes p1 : New) {
					long t2 = System.nanoTime();
					long t = t2 - tn;
					if (t < 0) {
						t = t* -1;
					}else {
						t = t*1 ;
					}
					System.out.println(p1.getName() + " --> " + " -id: " + p1.getProcess_id() + " -pid: " + p1.getProcess_pid()
							+ " -priority: " + p1.getProcess_priority());
					sb.append(p1.getName());
					sb.append(',');
					sb.append(" -->  id: " + p1.getProcess_id());
					sb.append(',');
					sb.append(" pid: " +p1.getProcess_pid() );
					sb.append(',');
					sb.append(" priority: " + p1.getProcess_priority());
					sb.append(',');
					sb.append("Execution time : " + t);
					sb.append('\n');
				}
			
				sb.append('\n');

						
			}else {
				System.out.println("The New State array:");
				sb.append("The New State array:");
				sb.append('\n');
				long tn = System.nanoTime();
				for (Processes p1 : New) {
					long t2 = System.nanoTime();
					long t = t2 - tn;
					if (t < 0) {
						t = t* -1;
					}else {
						t = t*1 ;
					}
					System.out.println(p1.getName() + " --> " + " -id: " + p1.getProcess_id() + " -pid: " + p1.getProcess_pid()
							+ " -priority: " + p1.getProcess_priority());
					sb.append(p1.getName());
					sb.append(',');
					sb.append(" -->  id: " + p1.getProcess_id());
					sb.append(',');
					sb.append(" pid: " +p1.getProcess_pid() );
					sb.append(',');
					sb.append(" priority: " + p1.getProcess_priority());
					sb.append(',');
					sb.append("Execution time : " + t);
					sb.append('\n');
				}

				sb.append('\n');
			}
			

			

			

			System.out.println();
			System.out.println();

			// Ready state test
			createReadyProcess();
			
			while (!(Ready.isEmpty())) {
				System.out.println("\n" + "The Ready State array:");
				sb.append("The Ready State array:");
				sb.append('\n');
				long tr = System.nanoTime();
				for (Processes p1 : Ready) {
					long t2 = System.nanoTime();
					long t = t2 - tr;
					if (t < 0) {
						t = t* -1;
					}else {
						t = t*1 ;
					}
					System.out.println(p1.getName() + " --> " + " -id: " + p1.getProcess_id() + " -pid: "
							+ p1.getProcess_pid() + " -priority: " + p1.getProcess_priority());
					sb.append(p1.getName());
					sb.append(',');
					sb.append(" -->  id: " + p1.getProcess_id());
					sb.append(',');
					sb.append(" pid: " +p1.getProcess_pid() );
					sb.append(',');
					sb.append(" priority: " + p1.getProcess_priority());
					sb.append(',');
					sb.append(" Execution time : " + t);
					sb.append('\n');
				}
			
				sb.append('\n');

				// Running state test
				System.out.println("\n" + "The Running State array:");
				sb.append("The Running State array:");
				sb.append('\n');
				
				createRunningProcess();
				long trn= System.nanoTime();
				for (Processes p1 : Running) {
					long trn2 = System.nanoTime();
					long tt = trn - trn2;
					if (tt < 0) {
						tt = tt* -1;
					}else {
						tt = tt*1 ;
					}
					System.out.println(p1.getName() + " --> " + " -id: " + p1.getProcess_id() + " -pid: "
							+ p1.getProcess_pid() + " -priority: " + p1.getProcess_priority());
					sb.append(p1.getName());
					sb.append(',');
					sb.append(" -->  id: " + p1.getProcess_id());
					sb.append(',');
					sb.append(" pid: " +p1.getProcess_pid() );
					sb.append(',');
					sb.append(" priority: " + p1.getProcess_priority());
					sb.append(',');
					sb.append("Execution time : " + tt);
					sb.append('\n');
				}
				
				sb.append('\n');
				
				//Finished state array
				System.out.println("\n" + "Finished State array:");
				sb.append("The Finished State array:");
				sb.append('\n');
				createFinishedProcess();
				long tf = System.nanoTime();
				for (Processes p1 : Finished) {
					long tf2 = System.nanoTime();
					long ttf = tf - tf2;
					if (ttf < 0) {
						ttf = ttf* -1;
					}else {
						ttf = ttf*1 ;
					}
					System.out.println(p1.getName() + " --> " + " -id: " + p1.getProcess_id() + " -pid: "
							+ p1.getProcess_pid() + " -priority: " + p1.getProcess_priority());
					sb.append(p1.getName());
					sb.append(',');
					sb.append(" -->  id: " + p1.getProcess_id());
					sb.append(',');
					sb.append(" pid: " +p1.getProcess_pid() );
					sb.append(',');
					sb.append(" priority: " + p1.getProcess_priority());
					sb.append(',');
					sb.append("Execution time : " + ttf);
					sb.append('\n');
				}
			
				sb.append('\n');
			}
			
			writer.write(sb.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	

	}


}
