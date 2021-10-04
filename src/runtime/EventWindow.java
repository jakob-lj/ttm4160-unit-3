package runtime;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The event window is used to generate events by pressing a button. 
 */
public class EventWindow {
	
	private JFrame frame;
	private String[] events;
	private Scheduler scheduler;
	private String name;
	
	/**
	 * 
	 * @param events - the list of the events that the state machine may accept
	 * @param scheduler - the scheduler to dispatch the events
	 */
	public EventWindow(String[] events, Scheduler scheduler) {
		this.events = events;
		this.scheduler = scheduler;
		this.name = "";
	}

	public EventWindow(String[] events, String name, Scheduler scheduler) {
		this.events = events;
		this.scheduler = scheduler;
		this.name = name;
	}

	public void show() {
		if (name.equals("")) {
			frame = new JFrame("Events");
		}
		else {
			frame = new JFrame("Evs@" + name);
		}
		frame.setLayout(new GridLayout(events.length, 1));

		for(String buttonText: events) {
			final JButton button = new JButton();
			button.setText(buttonText);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					scheduler.addToQueueLast(button.getText());
				}
			});
			frame.getContentPane().add(button);
		}

		frame.setVisible(true);
		frame.pack();
	}

}
