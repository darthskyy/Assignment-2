/**
 * 
 * Changed the dictionaries to be non-static in order to have separate accesses from the hungry words and falling words
 */
public class WordDictionary {
	int size;
	String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado", "kayuri", "sang"
	}; //default dictionary
	
	// dictionary if its for a hungry word
	String[] theHungryDict = {
		"preposterous", "josephine", "ribonucleic", "pterodactyl", "ibuprofen", "incomprehensible", "unbelievable",
		"phenomenal", "supercalifragilisticexpialidocious", "tubercolosis", "paediatrician",
		"osteoporosis", "encyclopaedia", "bibliography"
	};

	String[] dict;
	
	/**
	 * Default constructor
	 */
	WordDictionary() {
		size=theDict.length;
		dict=theDict;

	}

	/**
	 * If the user wants to create a new dictionary for the word
	 * @param tmp
	 */
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		dict=theDict;
		
	}
	
	/**
	 * Assigns the default dictionary for the appropriate type of word.
	 * The Dictionaries are mutually exclusive hence there is no chance of a Falling Word
	 * and a Hungry Word being the same.
	 * @param type
	 */
	WordDictionary(String type) {
		if (type.equals("normal")) {
			size=theDict.length;
			dict=theDict;
		}
		else if (type.equals("hungry")) {
			size=theHungryDict.length;
			dict=theHungryDict;
		}

	}
	
	/**
	 * Gets a random word from the dictionary.
	 * @return a random word from the dictionary
	 */
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return dict[wdPos];
	}
	
}
