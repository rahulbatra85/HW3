
public class OurThread extends Thread {
	MyLock lock;
	int[] counter;	
	int id;

	OurThread(MyLock lock, int[] counter, int id){
		this.lock = lock;
		this.counter = counter;
		this.id = id;
	}

	public void run(){
		lock.lock(id);
		counter[0]++;
		lock.unlock(id);
	}
	
}
