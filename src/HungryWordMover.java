import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;;

public class HungryWordMover extends Thread {
    private HungryWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	private FallingWord[] fallingWords;
	CountDownLatch startLatch; //so all can start at once
	
	HungryWordMover( HungryWord word) {
		myWord = word;
	}

	HungryWordMover( HungryWord word, FallingWord[] falling, WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.fallingWords = falling;
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}

	public void run() {

		//System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
		try {
			System.out.println("hungry " + myWord.getWord() + " waiting to start " );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start
		System.out.println("hungry " + myWord.getWord() + " started" );
		while (!done.get()) {				
			//animate the word
			while (!myWord.slid() && !done.get() && !myWord.waiting()) {
				    myWord.slide(10);
					int noWords = fallingWords.length;
					for (int i = 0; i<noWords; i++) {
						if (myWord.collides(fallingWords[i])) {
							fallingWords[i].resetWord();
							score.missedWord();
						}
					}
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
			}
			if (!done.get() && myWord.slid()) {
				score.missedWord();
				myWord.resetWord();
				myWord.startWaiting();
			}
			// this if statement is for timing out the hungry word so that it appears at random intervals
			if (myWord.waiting()) {
				long x = (long)(Math.random()*10) + 1;
				System.out.println("waiting for: " + x + " seconds.");
				try {
					sleep(x * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myWord.stopWaiting();
			}


			myWord.resetWord();
		}
	}
}
