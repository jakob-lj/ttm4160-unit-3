package runtime;

public interface IStateMachine {
	
	public static final int 
		EXECUTE_TRANSITION = 0, 
		DISCARD_EVENT = 1,
		TERMINATE_SYSTEM = 2;
	
	public int fire(String event, Scheduler scheduler);
	
}
