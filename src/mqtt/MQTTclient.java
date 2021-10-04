package mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import runtime.Scheduler;

public class MQTTclient implements MqttCallback {
	
	private Scheduler scheduler;
	private MqttClient client;
	
	public MQTTclient(String broker, String myAddress, boolean conf, Scheduler s) {
		scheduler = s;
		MemoryPersistence pers = new MemoryPersistence();
		try {
			client = new MqttClient(broker,myAddress,pers);
			MqttConnectOptions opts = new MqttConnectOptions();
			opts.setCleanSession(true);
			client.connect(opts);
			client.setCallback(this);
			scheduler.addToQueueLast("MQTTReady");
		}
		catch (MqttException e) {
			System.err.println("MQTT Exception: " + e);
			scheduler.addToQueueLast("MQTTError");
		}		
	}
	

	
	public void connectionLost(Throwable e) {

	}
	
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
	
	public void messageArrived(String topic, MqttMessage mess) {

	}

}
