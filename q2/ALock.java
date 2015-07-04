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
		mySlot = new ThreadLocal<Integer>(){	
			@Override protected Integer initialValue(){
				return 0;
			}
		};
		tailSlot = new AtomicInteger(0);
		this.numThread = numThread;
    }

    @Override
    public void lock(int myId) {
      // TODO: the locking algorithm
		int slot = tailSlot.getAndIncrement() % numThread;
		mySlot.set(slot);
		while(!available[slot]);
    }

    @Override
    public void unlock(int myId) {
      // TODO: the unlocking algorithm
		int slot = mySlot.get();
		available[slot] = false;
		available[(slot+1) % numThread] = true;
    }

	public static void main(String[] args){
		int numThread = 0;
		int count = 1200000;

		if(args.length == 1){
			numThread = Integer.parseInt(args[0]);
		} else if(args.length == 2){
			numThread = Integer.parseInt(args[0]);
			count = Integer.parseInt(args[1]);
		} else{
			System.out.println("Invalid args. ./ALock [numThreads] [count] ");
			System.exit(-1);
		}
 
		ALock alock = new ALock(numThread);
		int[] counter = new int[1];
		counter[0] = 0;

		OurThread[] t = new OurThread[numThread];
		for(int n=0; n<numThread; n++){
			t[n] = new OurThread(alock,counter,n,count/numThread);
		}
		
		for(int n=0; n<numThread; n++){
			t[n].start();
		}

		try{
			for(int n=0; n<numThread; n++){
				t[n].join();
			}
		} catch (InterruptedException e) { }
				
		if(counter[0] != count){
			System.out.println("ERROR: Expected:" + count + " Observed:" + counter[0]);
		} else{
			System.out.println("PASS: Expected:" + count + " Observed:" + counter[0]);
		}
	}
}
