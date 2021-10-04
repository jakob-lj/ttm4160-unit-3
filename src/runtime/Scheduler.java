package runtime;

import java.lang.Thread;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Scheduler extends Thread {

	/* This simplified scheduler only has one single state machine */
	private IStateMachine stm;
	private BlockingDeque<String> inputQueue = new LinkedBlockingDeque<String>();
	private String name;

	public Scheduler(IStateMachine stm) {
		this.stm = stm;
		this.name = "Scheduler";
	}

	public Scheduler(IStateMachine stm, String name) {
		this.stm = stm;
		this.name = name;
	}

	public void run() {
		boolean running = true;
		while(running) {
			try {
				// wait for a new event arriving in the queue
				String event = inputQueue.take();

				// execute a transition
				log(name + ": firing state machine with event: " + event);
				int result = stm.fire(event, this);
				if(result==IStateMachine.DISCARD_EVENT) {
					log(name + ": Discarded Event: " + event);
				} else if(result==IStateMachine.TERMINATE_SYSTEM) {
					log(name + ": Terminating System... Good bye!");
					running = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Normal events are enqueued at the end of the queue.
	 * @param event - the name of the event
	 */
	public synchronized void addToQueueLast(String eventId) {
		inputQueue.addLast(eventId);
	}

	/**
	 * Timeouts are added at the first place of the queue.
	 * @param event - the name of the timer
	 */
	public synchronized void addToQueueFirst(String timerId) {
		inputQueue.addFirst(timerId);
	}

	private void log(String message) {
		System.out.println(message);
	}
}
