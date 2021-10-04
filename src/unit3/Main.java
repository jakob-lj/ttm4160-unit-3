package unit3;

import mqtt.MQTTclient;
import runtime.IStateMachine;
import runtime.Scheduler;

public class Main {
	public static void main(String[] args) {
		
		IStateMachine mainMachine = new SensehatController();

		Scheduler s = new Scheduler(mainMachine);

		s.start();

		MQTTclient client = new MQTTclient("kk", "mqtt.stud.ntnu.no", false, s);
		System.out.println("Initialized");
	}
}
