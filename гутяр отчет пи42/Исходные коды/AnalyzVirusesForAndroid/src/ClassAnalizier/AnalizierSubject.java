package ClassAnalizier;

public class AnalizierSubject {
	protected AnalizierObserver observer;
	protected void sendCompleted()
	{
		observer.completed();
	}
}
