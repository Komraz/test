package ClassAnalizier;

public abstract class AnalizierObserver {
	private boolean isWait;
	protected void waitCompleted()
	{
		isWait=true;
	}
	protected void completed()
	{
		if(isWait)
		{
			completedAnalize();
			isWait=false;
		}
	}
	protected abstract void completedAnalize();
}
