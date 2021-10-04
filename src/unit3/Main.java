package unit3;

import org.eclipse.paho.client.mqttv3.MqttCallback;

import mqtt.MQTTclient;
import runtime.IStateMachine;
import runtime.Scheduler;

public class Main {
	public static void main(String[] args) {
		
		IStateMachine mainMachine = new SensehatController();

		Scheduler s = new Scheduler(mainMachine);

		s.start();

		MqttCallback cb = new MQTTclient("kk", "kk", false, s);
		System.out.println("Hey");
		
	}
}
