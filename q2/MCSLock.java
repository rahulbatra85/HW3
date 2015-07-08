// Implement the MCS Lock 
import java.util.concurrent.atomic.*;

public class MCSLock implements MyLock {
	//Inner Class Queue Node
	class QNode{
		volatile QNode next = null;
		volatile boolean locked = false;

		QNode() {
			next = null;
			locked = false;
		}
	}

	volatile AtomicReference<QNode> tail; //Reference to tail node
	ThreadLocal<QNode> myNode;

    public MCSLock(int numThread) {
		tail = new AtomicReference<QNode>(null);
		myNode = new ThreadLocal<QNode>() {
			@Override 
			protected QNode initialValue(){
				return new QNode();
			}
		};
    }

    @Override
    public void lock(int myId) {
		QNode mnode = myNode.get();
		QNode pred = tail.getAndSet(mnode);
		if(pred != null) {
			mnode.locked = true;
			pred.next = mnode;
			while(mnode.locked);
		}
    }

    @Override
    public void unlock(int myId) {
		QNode mnode = myNode.get();
		if(mnode.next == null) {
			if(tail.compareAndSet(mnode,null)) {
				return;
			}
			while(mnode.next == null);
		}	
		mnode.next.locked = false;
		mnode.next = null;		
    }

	public static void main(String[] args){
		int numThread=0;
		int count = 1200000;
        int shared_counter = 0; 
        //Assumption: count%numThread == 0

		if(args.length == 1){
			numThread = Integer.parseInt(args[0]);
		} 
		else if(args.length == 2){
			numThread = Integer.parseInt(args[0]);
			count = Integer.parseInt(args[1]);
		} 
		else{
			System.out.println("Invalid args. ./MCSLock [numThreads] [count] ");
			System.exit(-1);
		}
 
		System.out.println("MCSLock numThreads="+numThread+" count="+ count);

		MCSLock mcsLock = new MCSLock(numThread);
		OurThread[] t = new OurThread[numThread];
		
		for(int n=0; n<numThread; n++){
			t[n] = new OurThread(mcsLock, counter, n, count/numThread);
		}
		
		for(int n=0; n<numThread; n++){
			t[n].start();
		}

		try{
			for(int n=0; n<numThread; n++){
				t[n].join();
			}
		} 
		catch (InterruptedException e) { }
				
		if(shared_counter != count){
			System.out.println("ERROR: Expected:" + count + " Observed:" + shared_counter);
		} 
		else{
			System.out.println("PASS: Expected:" + count + " Observed:" + shared_counter);
		}
	} //END VOID MAIN
}
