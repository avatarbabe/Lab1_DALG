package labbett;


public class SymbolTable {
	private static final int INIT_CAPACITY = 7;

	/* Number of key-value pairs in the symbol table */
	private int N;
	/* Size of linear probing table */
	private int M;
	/* The keys */
	private String[] keys;
	/* The values */
	private Character[] vals;

	/**
	 * Create an empty hash table - use 7 as default size
	 */
	public SymbolTable() {
		this(INIT_CAPACITY);
	}

	/**
	 * Create linear probing hash table of given capacity
	 */
	public SymbolTable(int capacity) {
		N = 0;
		M = capacity;
		keys = new String[M];
		vals = new Character[M];
	}

	/**
	 * Return the number of key-value pairs in the symbol table
	 */
	public int size() {
		return N;
	}

	/**
	 * Is the symbol table empty?
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Does a key-value pair with the given key exist in the symbol table?
	 */
	public boolean contains(String key) {
		return get(key) != null;
	}

	/**
	 * Hash function for keys - returns value between 0 and M-1
	 */
	public int hash(String key) {
		int i;
		int v = 0;

		for (i = 0; i < key.length(); i++) {
			v += key.charAt(i);
		}
		return v % M;
	}

	/**
	 * Insert the key-value pair into the symbol table
	 */
	public void put(String key, Character val) {
		
		N += 1;	
		
		//Om ett anrop till put där val är null ska ge samma resultat som anropet delete(key).
		if(val == '\n') {	
			N -= 1; //Behövs eftersom vi lägger till en på 'N' direkt.
			delete(key);
			return;
		}
		//////////////////////////////////////////////////////////////////////////////////////
		
		//Resultatet är odefinierat då hashtabellen är/blir full eller då key är null
		if (key == null || N >= M) {
			
			return;
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//Om identifieraren redan finns i tabellen, ändra till det nya val-värdet
		int hashKey = hash(key);
		try {
			if(keys[hashKey].equals(key)){
				vals[hashKey] = val;
				return;
			}else {
				//Det här borde ändras till något effektivare
				for(int i = 0; i < keys.length; i++){
					if(keys[i].equals(key)){
						vals[i] = val;
						return;
					}
				}
			}
		}catch(NullPointerException e){
			
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		//Om nyckelplatsen är upptagen och nyckelordet skiljer sig, vill vi lägga till 
		//nyckeln och värdet på nästa lediga plats uppåt i listan.
		
		if(keys[hashKey] != null && !keys[hashKey].equals(key)){
			for(int i = hashKey; i < M; i++){
				if(keys[i] == null){
					keys[i] = key;
					vals[i] = val;
					return;
				}
			}
			for(int i = 0; i < hashKey; i++){
				if(keys[i] == null){
					keys[i] = key;
					vals[i] = val;
					return;
				}
			}
		}
		
		keys[hashKey] = key;
		vals[hashKey] = val;
				
		return;
	} // dummy code

	/**
	 * Return the value associated with the given key, null if no such value
	 */
	public Character get(String key) {
		
		//Ta hashvärde, kolla upp nyckeln i listan, om nyckel = nyckel i listan returnera val på den platsen. Stämmer de inte, gå vidare upp i listan
		
		int hashKey = hash(key);
		//Om värdet finns där det bör finnas, returneras det
		try {
			if(keys[hashKey].equals(key)){
				return vals[hashKey];
			}
		}catch(NullPointerException e){
			return null;
		}
		
		//Värdet finns inte där det bör finnas, kollar platserna uppåt om de stämmer
		
		for(int i = hashKey + 1; i < M; i++){
			if(keys[i].equals(key)){
				return vals[i];
			}
		}
		
		for(int i = 0; i < hashKey; i++){
			if(keys[i].equals(key)){
				return vals[i];
			}
		}
		
		return null;

	} // dummy code

	/**
	 * Delete the key (and associated value) from the symbol table
	 */
	public void delete(String key) {
		
		//Börjar med att minska listans storlek med 1
		N -= 1;
		int hashKey = hash(key);
				
		//Om key är detsamma som keys[hashKey] så tas nyckeln bort
		if(key.equals(keys[hashKey])){
			keys[hashKey] = null;
			//Här ska rearrange ske
			
			
			
			return;
		}
		
		//Om key inte överrensstämmer med nyckeln på designerad plats, gå vidare uppåt för att se om
		//den finns längre upp.
		int tmpInt = hashKey + 1;
		
		while(tmpInt != hashKey) {
			tmpInt = (tmpInt < M) ? tmpInt: 0;
			if(key.equals(keys[tmpInt])){
				vals[tmpInt] = null;
				keys[tmpInt] = null;
				//Här ska rearrange ske
				return;
			}	
			tmpInt++;
		}		
		return;
	} // dummy code

	/**
	 * Print the contents of the symbol table
	 */
	public void dump() {
		String str = "";

		for (int i = 0; i < M; i++) {
			str = str + i + ". " + vals[i];
			if (keys[i] != null) {
				str = str + " " + keys[i] + " (";
				str = str + hash(keys[i]) + ")";
			} else {
				str = str + " -";
			}
			System.out.println(str);
			str = "";
		}
	}
}