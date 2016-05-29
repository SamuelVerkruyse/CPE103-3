import java.util.*; // import data structure setups
public class HashTableP3<T> {
	LinkedList<T>[] container; //Stores the chaining method
	ArrayList<T> storage = new ArrayList<T>();//Contains the T data
	ArrayList<Integer> store = new ArrayList<Integer>();//Stores the 0-size location
	ArrayList<Integer> bucketstore = new ArrayList<Integer>();//Stores the bucket location
	int bucketsize;
	boolean hashchoice = false;//Contains the trigger for switching hashes
	public HashTableP3(int size){//Constructor for HashTable
		LinkedList[] array = new LinkedList[size];//Array for chain
		container = (LinkedList<T>[])array;//Creates the chain linkedlist
		for(int i = 0; i < size; i++)//Initializes each LinkedList
			container[i] = new LinkedList<T>();
	}
	public void hashchoice(int choice){//Controls the hash method
		if(choice == 1)//If the user chooses hash 1
			hashchoice = false;//Make the hashchoice false
		if(choice == 2)//If the user chooses hash 2
			hashchoice = true;//Make the hashchoice true
	}
	private int hash_func1(String receiver){//First hash method
		int passed;//Creates an integer variable
		if(receiver.matches(".*[0-9].*")){//If the string contains an integer
			passed = Integer.parseInt(receiver);//Make an integer from the string
			return (int)passed%container.length;//Return the integer's remained divided by the container size
		}
		Character one = receiver.charAt(0);//Character one is the first character
		Character two = 0;
		if(receiver.length() > 1)//If the string is longer than 1 
			two = receiver.charAt(1);//Make two the second character
		Character three = 0;
		if(receiver.length() > 2)//If the string is longer than 2
			three = receiver.charAt(2);//Make three the third character
		double returner = (Math.pow(27,0) * one) + (Math.pow(27, 1) * two) + (Math.pow(27,2) * three);
		return (int)returner%container.length;
	}
	private int hash_func2(String receiver){
		int passed;//Creates an integer variable
		if(receiver.matches(".*[0-9].*")){//If the string contains an integer
			passed = Integer.parseInt(receiver);//Make an integer from the string
			return (int)passed%container.length;//Return the integer's remained divided by the container size
		}
		Character one = receiver.charAt(0);//Character one is the first character
		Character two = 0;
		if(receiver.length() > 1)//If the string is longer than 1 
			two = receiver.charAt(1);//Make two the second character
		Character three = 0;
		if(receiver.length() > 2)//If the string is longer than 2
			three = receiver.charAt(2);//Make three the third character
		double returner = (Math.pow(37,0) * one) + (Math.pow(37, 1) * two) + (Math.pow(37,2) * three);
		return (int)returner%container.length;
	}

	public void insert(T item){
		int spot = 0;//Initialize the spot
		if(!hashchoice)//If the user chose hashmethod one
			spot = hash_func1(item.toString());//Calculate the spot using hash one
		if(hashchoice)//If the user chose hashmethod two
			spot = hash_func2(item.toString());//Calculate the spot using hash two
		LinkedList<T> og = container[spot];//Make the linked list equal to the array in container at the given spot
		og.addFirst(item);//Add the item to the beginning of the linked list
		container[spot] = og;//Make the array at the spot equal to the new linked list
		storage.add(item);//Add the item to the storage array list
		store.add(spot);//Add the int hash to the store array list
		bucketstore.add(spot%bucketsize);//Add the int hash mod bucketsize to the bucket store array list
	}
	public void delete(T item){
		int spot = 0;//Initialize the spot
		if(!hashchoice)//If the user chose hashmethod one
			spot = hash_func1(item.toString());//Calculate the spot using hash one
		if(hashchoice)//If the user chose hashmethod two
			spot = hash_func2(item.toString());//Calculate the spot using hash two
		LinkedList<T> og = container[spot];//Make the linked list equal to the array in container at the given spot		
		og.remove(item);//remove the given item
		int remover = storage.indexOf(item);//Find where the item is stored
		storage.remove(remover);//Remove that item
		store.remove(remover);//Remove the hash for that item
		bucketstore.remove(remover);//Remove the bucket hash for that item
	}
	public boolean find(T item){
		int spot = 0;//Initialize the spot
		if(!hashchoice)//If the user chose hashmethod one
			spot = hash_func1(item.toString());//Calculate the spot using hash one
		if(hashchoice)//If the user chose hashmethod two
			spot = hash_func2(item.toString());//Calculate the spot using hash two
		LinkedList<T> og = container[spot];//Make the linked list equal to the array in container at the given spot		
		int returnee = og.indexOf(item);//Find where the item is stored
		return returnee != -1;//If the item is stored, return true, else, return false

	}
	public boolean isEmpty(){//Checks if the container is empty
		for(int i = 0; i < container.length; i++){//Go through the container
			if(container[i].size() != 0)//If anything is present
				return false;//Return false
		}
		return true;
	}
	public void print(){//Prints all 4 methods
		for(int i = 0; i < container.length; i++){//Go through the container's length size of int
			System.out.print(i + ": ");//Print the current level
			for(T item: container[i]){//Go through the container
				System.out.print(item + " ");//Print every item
			}
			System.out.println();//Put in a line
		}
		linearProbing();//Use the linear probe method
		quadraticProbing();//Use the quadratic probe method
		bucket();//Use buckets
	}
	public void linearProbing(){//Linear probe method
		int collision = 0;//At first, there are no collisions
		ArrayList<String> whatever = new ArrayList<String>();//Make an arraylist for the linear probe
		for(int i = 0; i < container.length; i++)//Go through the container length
			whatever.add("");//Add an empty string to each spot
		int incrementer;//Create an incrementer variable
		for(int i = 0; i < store.size(); i++){//Go through the hash storage
			incrementer = store.get(i);//Get the hash at the given spot
			if(whatever.get(incrementer).equals("")){//If the hash is empty
				whatever.set(incrementer, storage.get(i).toString());//Add the item to the given hash spot
			}
			else{//Otherwise
				while(!whatever.get(incrementer).equals("")){//If the space is not empty
					collision++;//Add a collision
					incrementer++;//Make incrementer go up
					incrementer = incrementer%container.length;//Mod the new incrementer by the container length
				}
				whatever.set(incrementer, storage.get(i).toString());//Make the empty space the new item
			}
		}
		System.out.println("Chaining");
		for(int i = 0; i < whatever.size(); i++){//Go through the arraylist
			System.out.println(i + ": " + whatever.get(i));//Print the contents at the given spot in the array
		}
		System.out.println(collision + " collisions");//Print how many collisions happened

	}
	public void quadraticProbing(){
		int collision = 0;//At first, there are no collisions
		ArrayList<String> whatever = new ArrayList<String>();//Make an arraylist for the quadratic probe
		for(int i = 0; i < container.length; i++)//Go through the container length
			whatever.add("");//Add an empty string to each spot
		int incrementer;//Create an incrementer variable
		int quad = 1; //Start quadratic incrementer as 1
		for(int i = 0; i < store.size(); i++){//Go through the hash storage
			incrementer = store.get(i);//Get the hash at the given spot
			if(whatever.get(incrementer).equals("")){//If the hash is empty
				whatever.set(incrementer, storage.get(i).toString());//Add the item to the given hash spot
			}
			else{//Otherwise
				while(!whatever.get(incrementer).equals("")){//If the space is not empty
					collision++;//Increase the collision counter
					incrementer = incrementer + (quad * quad);//incrementer by adding the square of the quad variable
					incrementer = incrementer%container.length;//Mod the incrementer by the container length
					quad++;//increment quad
				}
				whatever.set(incrementer, storage.get(i).toString());//Make the empty space the new item
			}
		}
		System.out.println("Quadratic Probing");
		for(int i = 0; i < whatever.size(); i++){//Go through the arraylist
			System.out.println(i + ": " + whatever.get(i));//Print the contents at the given spot in the array
		}
		System.out.println(collision + " collisions");//Print how many collisions happened
	}
	public void bucket(){
		int collision = 0;//At first, there are no collisions
		ArrayList<String> whatever = new ArrayList<String>();//Make an arraylist for bucket
		int limit = container.length/bucketsize;//Make the first limit the container length divided by 3
		int limitwo = 2*limit;//Make the second limit twice the side of the first limit
		for(int i = 0; i < container.length; i++)//Go through the container's length
			whatever.add("");//Add empty strings to each spot of the array
		int incrementer;//Create an empty incrementer
		int mover1;//Create an empty mover
		int mover2;//Create another empty mover
		int mover3;//Create one more empty mover
		for(int i = 0; i < bucketstore.size(); i++){//Go through the bucketstore arraylist
			incrementer = bucketstore.get(i);//Make the incrementer equal to the contents of bucketstore
			if(incrementer == 0){//if the incrementer is 0
				mover1 = limit;//Make mover1 equal to the limit
				for(int j = 0; j < limit; j++){//Go from 0 to the limit
					if(whatever.get(j).equals("")){//If it is empty
						whatever.set(j, storage.get(i).toString());//Make the spot contain the item
						j = limit;//End the loop
					}
					else{//Otherwise
						mover1--;//Deincrement the mover
						collision++;//Count a collision
					}
				}
				if(mover1 == 0)//If the mover is equal to 0
					incrementer++;//Pass to the next bucket
			}
			if(incrementer == 1){//If the incrementer is 1
				mover2 = limitwo - limit;//Make mover equal to the difference between the two limits
				for(int k = limit; k < limitwo; k++){//Go from limit to the second limt
					if(whatever.get(k).equals("")){//If it is empty
						whatever.set(k, storage.get(i).toString());//Make the spot contain the item
						k = limitwo;//End the loop
					}
					else{//Otherwise
						mover2--;//Deincrement the mover
						collision++;//Count a collision
					}
				}
				if(mover2 == 0)//If the mover is equal to 0
					incrementer++;//Pass to the next bucket
			}
			if(incrementer == 2){//If the incrementer is 2
				mover3 = container.length - limitwo;//Make mover equal to the difference between the container length and limitwo
				for(int l = limitwo; l < container.length; l++){//Go from limitwo to the end of the container
					if(whatever.get(l).equals("")){//If it is empty
						whatever.set(l, storage.get(i).toString());//Make the spot contain the item
						l = container.length;//End the loop
					}
					else{//Otherwise
						mover3--;//Deincrement the mover
						collision++;//Count a collision
					}
				}
				if(mover3 == 0){//If the mover is equal to 0
					incrementer = 0;//Pass to the first bucket
					i--;//Restart the loop
				}
			}
		}
		System.out.println("Bucket Hashing");
		for(int i = 0; i < whatever.size(); i++){//Go through the arraylist
			System.out.println(i + ": " + whatever.get(i));//Print the contents of the arraylist
		}
		System.out.println(collision + " collisions");//Print the number of collisions
	}


}
class HashTest{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);//Creates a scanner for user input
		System.out.println("Type your table size");//Prompt for the table size
		int size = in.nextInt();
		in.nextLine();
		System.out.println("Type your bucket size");//Prompt for the bucket size
		int bucketsize = in.nextInt();
		in.nextLine();
		System.out.println("Type 1 for Hash 1, Type 2 for Hash 2");//Prompt for the hash type
		int hasher = in.nextInt();
		HashTableP3<Integer> test = new HashTableP3<Integer>(size);//Creates an empty hashtable for user operations
		test.hashchoice(hasher);//Set the hash type to the user choice
		test.bucketsize = bucketsize;
		in.nextLine();
		System.out.println("Choose one of the following operations:");//Prompts user to choose an option
		System.out.println("-add/insert (enter the letter a)");
		System.out.println("-find (enter the letter f)");
		System.out.println("-delete (enter the letter d)");
		System.out.println("-is empty (enter the letter e)");
		System.out.println("-print (enter the letter p)");
		System.out.println("-Quit (enter the letter q)");
		char userInput = in.nextLine().charAt(0);//Notes the user's choice
		do{
			switch (userInput) {//Switch statement utilizing user input
				case 'a': System.out.println("Enter the integer you want to add:");//Prompts user to push an int
						int pushee = in.nextInt();
						in.nextLine();
						test.insert(pushee);//Pushes whatever they chose to push
						System.out.println(pushee + " inserted");//Lets the user know that their data was pushed
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'd': System.out.println("Enter the integer you want to delete:");//Prompts user to push an int
						int delete = in.nextInt();
						test.delete(delete);
						in.nextLine();
						System.out.println("If " + delete + " was in the hashtable, it was removed");
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'f':System.out.println("Enter the integer you want to find:");//Prompts user to push a string
						int find = in.nextInt();
						boolean found = test.find(find);
						in.nextLine();
						if(found)
							System.out.println("Your integer was found");
						else
							System.out.println("Your integer was not found");
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'e': if(test.isEmpty()){//If the table is empty
							System.out.println("Empty");//Let them know the table is empty
						}
						else{//Otherwise
							System.out.println("Not Empty");//Let them know the table is not empty
						}
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'p': test.print();
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'q': System.out.println("quitting"); //Quit if the user hits the quit key
						break;
				default: System.out.println("Invalid choice"); //If they choose a different character, let them know that's not allowed, strictly speaking
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
			}
		}while(userInput != 'q');
	}
}
