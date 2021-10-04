package unit3;

import runtime.IStateMachine;
import runtime.Scheduler;

public class SensehatController implements IStateMachine{

	@Override
	public int fire(String event, Scheduler scheduler) {
		// TODO Auto-generated method stub
		return DISCARD_EVENT;
	}

}
