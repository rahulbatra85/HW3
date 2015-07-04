
public class OurThread extends Thread {
	MyLock lock;
	int[] counter;	
	int id;
	int cnt;

	OurThread(MyLock lock, int[] counter, int id, int cnt){
		this.lock = lock;
		this.counter = counter;
		this.id = id;
		this.cnt = cnt;
	}

	public void run(){
		for(int i=0; i<cnt; i++){
			lock.lock(id);
			counter[0]++;
			lock.unlock(id);
		}
	}
	
}
