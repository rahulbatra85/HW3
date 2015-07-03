// TODO 
// Implement Anderson’s array-based lock
import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements MyLock {
	AtomicInteger tailSlot;
	volatile boolean[] available;
	ThreadLocal<Integer> mySlot;
	int numThread;

    public ALock(int numThread) {
      // TODO: initialize your algorithm
		available = new boolean[numThread];
		available[0] = true;
		mySlot = new ThreadLocal<Integer>();
		tailSlot = new AtomicInteger(0);
		this.numThread = numThread;
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
		mySlot.set(tailSlot.getAndIncrement() % numThread);
		while(!available[mySlot.get()]);
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
		available[mySlot.get()] = false;
		available[(mySlot.get()+1) % numThread] = true;
    }

	public static void main(String[] args){
		int numThread=0;

		if(args.length != 1){
			System.out.println("Invalid args. ./ALock [numThreads]");
		System.exit(-1);
		} else{
			numThread = Integer.parseInt(args[0]);
		}

		ALock alock = new ALock(numThread);
		int[] counter = new int[1];
		counter[0] = 0;

		OurThread[] t = new OurThread[numThread];
		for(int n=0; n<numThread; n++){
			t[n] = new OurThread(alock,counter,n);
		}
		
		for(int n=0; n<numThread; n++){
			t[n].start();
		}

		try{
			for(int n=0; n<numThread; n++){
				t[n].join();
			}
		} catch (InterruptedException e) { }
				
		if(counter[0] != numThread){
			System.out.println("ERROR: Expected:" + numThread + " Observed:" + counter[0]);
		} else{
			System.out.println("PASS: Expected:" + numThread + " Observed:" + counter[0]);
		}
	}
}
