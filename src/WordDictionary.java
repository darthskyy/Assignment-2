public class WordDictionary {
	int size;
	String [] theDict= {"litchi","banana","apple","mango","pear","orange","strawberry",
		"cherry","lemon","apricot","peach","guava","grape","kiwi","quince","plum","prune",
		"cranberry","blueberry","rhubarb","fruit","grapefruit","kumquat","tomato","berry",
		"boysenberry","loquat","avocado"}; //default dictionary
	
	String[] theHungryDict = {"supercalifragilisticexpialidocious"
	};

	String[] dict;
	
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}
	
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
	
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return dict[wdPos];
	}
	
}
