package at.ms;

public interface AsyncTaskCallbacks{
	void onTaskCompleted(String response, String switchingLamp);
	void onProgressUpdate(int status);
}
