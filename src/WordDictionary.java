

public class WordDictionary {
	int size;
	static String [] theDict= {"litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi","litchi"}; //default dictionary
	
	WordDictionary(String [] tmp) {
		size = tmp.length;
		theDict = new String[size];
		for (int i=0;i<size;i++) {
			theDict[i] = tmp[i];
		}
		
	}
	
	WordDictionary() {
		size=theDict.length;
	}
	
	public synchronized String getNewWord() {
		int wdPos= (int)(Math.random() * size);
		return theDict[wdPos];
	}
	
}
