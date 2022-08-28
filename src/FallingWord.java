

public class FallingWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxY, boundX; //maximum height
	private boolean dropped; //flag for if user does not manage to catch word in time
	
	private int fallingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	FallingWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	FallingWord(String text) { 
		this();
		this.word=text;
	}
	
	FallingWord(String text,int x, int maxY, int boundX) { //most commonly used constructor - sets it all.
		this(text);
		this.maxY=maxY;
		this.boundX=boundX-wordLength();
		this.x = x>boundX ? boundX:x; //only need to set x, word is at top of screen at start
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true; //user did not manage to catch this word
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	
	public synchronized int wordLength() {
		return getWord().length()*16;
	}
	
	/**
	 * resets the falling word's position to a random x value and moves it back to the top
	 */
	public synchronized void resetPos() {
		setPos((int)(Math.random()*(boundX)), 0);
	}

	public synchronized int wordPixels() {

		return 1;
	}
	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		// System.out.println(word + (double)26/word.length());
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	/**
	 * Checks if the Falling Word matches a string
	 * @param typedText the text being compare to see if it matches the Falling Word
	 * @return true if they match and false if they do not
	 */
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			// resetWord();
			return true;
		}
		else
			return false;
	}

	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	public synchronized void slide(int inc) {
		setX(x+inc);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}


	// the box methods are for getting the bounds of the word
	/**
	 * Gets the coordinates of the corner points of a word (16 was just used as the arbitrary length of a character)
	 * @return an integer arry with the coordinates
	 */
	public synchronized int[] getBox() {
		int x1,x2,y1,y2;
		x1 = getX();
		x2 = x1 + getWord().length()*16;
		y2 = getY();
		y1 = y2 - 16;

		int[] array = {x1,x2,y1,y2};
		return array;
	}
}
