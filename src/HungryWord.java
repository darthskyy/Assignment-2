public class HungryWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxX, boundY; //maximum height
	private boolean slid; //flag for if user does not manage to catch word in time
	
	private boolean waiting = true;
	private int slidingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	HungryWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=500;	
		maxX=1000;
		slid=false;
		slidingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	HungryWord(String text) { 
		this();
		this.word=text;
	}
	
	HungryWord(String text,int y, int maxX, int boundY) { //most commonly used constructor - sets it all.
		this(text);
		this.y=y; //only need to set y, word is at top of screen at start
		this.maxX=maxX;
		this.boundY=boundY;
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
	public synchronized  void setX(int x) {
		if (x>maxX) {
			x=maxX;
			slid=true; //user did not manage to catch this word
		}
		this.x=x;
	}
	
	public synchronized  void setY(int y) {
		this.y=y;
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
		return slidingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		int y = (int)(Math.random()*boundY);
		setPos(0, y);
	}

	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		slid=false;
		slidingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			// resetWord();
			return true;
		}
		else
			return false;
	}

	public synchronized void slide(int inc) {
		setX(x+inc);
	}
	
	public synchronized  boolean slid() {
		return slid;
	}

	// the waiting methods are to pace the hungry word into
	// appearing at random moments in a ten second span
	public synchronized void startWaiting() {
		waiting = true;
	}

	public synchronized void stopWaiting() {
		waiting = false;
	}

	public synchronized boolean waiting() {
		return waiting;
	}


	// the box methods are for getting the bounds of the word
	public synchronized int[] getBox() {
		int x1,x2,y1,y2;
		x1 = getX() - getWord().length()*15;
		x2 = x1 + getWord().length()*15;
		y2 = getY()+16;
		y1 = y2 - 16;

		int[] array = {x1,x2,y1,y2};
		return array;
	}

	public synchronized boolean collides(FallingWord fallingWord) {
		int[] coordinates = getBox();
		int[] coordinatesFalling = fallingWord.getBox();
		
		if ((coordinates[0]<=coordinatesFalling[1] && coordinates[0]>=coordinatesFalling[0]) || (coordinatesFalling[0]<=coordinates[1] && coordinatesFalling[0]>=coordinates[0])) {
			if ((coordinates[2]<=coordinatesFalling[3] && coordinates[2]>=coordinatesFalling[2]) || (coordinatesFalling[2]<=coordinates[3] && coordinatesFalling[2]>=coordinates[2])) {
				System.out.print("-------------- hungry"+getWord() + " ");
				for (int item: coordinates) System.out.print(item+" ");
				
				System.out.println();
				System.out.print("--------------"+fallingWord.getWord() + " ");
				for (int item: coordinatesFalling) System.out.print(item+" ");
				System.out.println();
				return true;
			}
		}

		return false;
	}
}
