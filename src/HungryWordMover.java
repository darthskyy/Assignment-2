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

	/**
	 * Creates a Hungry Word Mover thread with the Hungry Word
	 * @param word The Hungry Word to be moved
	 * @param falling an array of the current Falling Words on screen
	 * @param dict the word dictionary
	 * @param score the score object for update scores
	 * @param startLatch the countdown latch for synchronicity
	 * @param d the boolean to check if the game is done
	 * @param p the boolean to check if the game is paused
	 */
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
			// added a new condition to make sure that the Hungry Word is not waiting to be put on screen
			while (!myWord.slid() && !done.get() && !myWord.waiting()) {
				    myWord.slide(10);
					int noWords = fallingWords.length;
					
					// checks every word to see if they collide with  the Hungry Word
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

			// in the case that the Hungry Word slides off screen but the game isn't done
			if (!done.get() && myWord.slid()) {
				score.missedWord();
				myWord.resetWord();
				myWord.startWaiting();
			}
			// this if statement is for timing out the hungry word so that it appears at random intervals of no greater than 10 seconds
			if (myWord.waiting()) {
				long x = (long)(Math.random()*10) + 1;
				System.out.println("waiting for: " + x + " seconds.");
				try {
					sleep(x * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myWord.stopWaiting();
			}

			// resets the word at the ends
			myWord.resetWord();
		}
	}
}
