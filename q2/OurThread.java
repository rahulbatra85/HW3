
public class OurThread extends Thread {

	MyLock lock;
	int counter; //shared amount threads
	int id; //not used for this assignment
	int cnt; //num of counter increments alloted for this thread

	OurThread(MyLock lock, int counter, int id, int cnt) {
		this.lock = lock;
		this.counter = counter;
		this.id = id; //not used for this assignment
		this.cnt = cnt;
	}

	public void run(){
		for(int i=0; i<cnt; i++){
			lock.lock(id);
			counter++;
			lock.unlock(id);
		}
	}
	
}
