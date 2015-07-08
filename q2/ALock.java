// Implement Anderson’s array-based lock
import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements MyLock {

	AtomicInteger tailSlot;
	volatile boolean[] available;
	ThreadLocal<Integer> mySlot;
	int numThread;

    public ALock(int numThread) {
      // initialize your algorithm
		available = new boolean[numThread];
		available[0] = true;
		
		mySlot = new ThreadLocal<Integer>() {
			@Override 
			protected Integer initialValue() {
                return 0;
			}
		};
		
		tailSlot = new AtomicInteger(0);
		this.numThread = numThread;
    }

    @Override
    public void lock(int myId) { //note: myID is not used
      //locking algorithm
		int slot = tailSlot.getAndIncrement() % numThread;
		mySlot.set(slot);
		while(!available[slot]);
    }

    @Override
    public void unlock(int myId) { //note: myID is not used
      //unlocking algorithm
		int slot = mySlot.get();
		available[slot] = false;
		available[(slot+1) % numThread] = true;
    }

	public static void main(String[] args){
		int numThread = 0;
		int count = 1200000;
        int[] shared_counter = {0}; //must use int[] to pass by ref
        //Assumption: count%numThread == 0

		if(args.length == 1) {
			numThread = Integer.parseInt(args[0]);
		} 
		else if(args.length == 2) {
			numThread = Integer.parseInt(args[0]);
			count = Integer.parseInt(args[1]);
		} 
		else {
            System.out.println("Invalid args. ./ALock [numThreads] [count]");
            System.exit(-1);
		}
 
		ALock alock = new ALock(numThread);
		OurThread[] t = new OurThread[numThread];

		for(int n=0; n<numThread; n++){
			t[n] = new OurThread(alock, shared_counter, n, count/numThread);
			//each thread will add to the shared_counter += count/numThread
		}
		
		long start = System.nanoTime();		
		for(int n=0; n<numThread; n++){
			t[n].start();
		}

		try{
			for(int n=0; n<numThread; n++){
				t[n].join();
			}
		} 
		catch (InterruptedException e) { }
		long end = System.nanoTime();	
		long executeTimeMS = (end - start)/1000000; //1ms = 1000000ns		
							
		if(shared_counter[0] != count){
			System.out.println("ERROR: Expected:" + count + " Observed:" + shared_counter[0]);
		} 
		else {
			System.out.println("PASS. Time: " + executeTimeMS + "ms");
		}
	}//END VOID MAIN
}
