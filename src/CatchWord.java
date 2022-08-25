

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static FallingWord[] words; //list of words
	private static HungryWord hWord;
	private static HungryWord[] hungryWords;
	private static int noWords, noHungryWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setHungryWord(HungryWord[] hungryWordsList) {
		hungryWords = hungryWordsList;
		noHungryWords = hungryWords.length;
	}
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}

	public void run() {
		int i=0;
		while (i<noWords) {		
			while(pause.get()) {};
			if (hungryWords[0].matchWord(target)) {
				System.out.println(" hungry! " + target);
				score.caughtWord(target.length());
				hungryWords[0].resetWord();
				hungryWords[0].startWaiting();
				break;
			}
			else if (words[i].matchWord(target)) {
				FallingWord max = words[i];
				for (int j=i+1; j<noWords; j++) {
					if (words[j].matchWord(target) && words[j].getY()>max.getY())
						max = words[j];
				}
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				//FallingWord.increaseSpeed();
				max.resetWord();
				break;
			}
		   i++;
		}
		
	}	
}
