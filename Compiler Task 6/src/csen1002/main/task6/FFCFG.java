package csen1002.main.task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.condition.DisabledIfSystemProperties;

/**
 * Write your info here
 * 
 * @name Fady Samir
 * @id 43-1366
 * @labNumber 18
 */

public class FFCFG {

	/**
	 * Constructs a CFG for which the First and Follow are to be computed
	 * 
	 * @param description A string representation of a CFG as specified in the task
	 *                    description     "S,aBDh;B,cA;A,bA,e;D,EF;E,g,e;F,f,e"
	 */
	
    Hashtable <String, ArrayList<String>> rules;
    Hashtable <String, ArrayList<String>> firsts;
    Hashtable <String, ArrayList<String>> follows;
    String[] prod;
    
	@SuppressWarnings("rawtypes")
	public FFCFG(String description) {
		String[] r = description.split(";");
		rules = new Hashtable<String,ArrayList<String>>();
		firsts = new Hashtable<String,ArrayList<String>>();
		follows = new Hashtable<String,ArrayList<String>>();
		prod= new String[r.length];
		
		for (int i = 0; i < r.length; i++) {
			
			String[] ar = r[i].split(",");
			String[] currentrules = Arrays.copyOfRange(ar, 1, ar.length);
			prod[i]=ar[0];
			@SuppressWarnings("unchecked")
			ArrayList<String> al = new ArrayList(Arrays.asList(currentrules));
			

			rules.put(ar[0],  al);
			firsts.put(ar[0], new ArrayList<String>());
			if(ar[0].equals("S")) {
				ArrayList<String> dollar = new ArrayList<String>();
				dollar.add("$");
				follows.put(ar[0], dollar);
			}else {
				follows.put(ar[0], new ArrayList<String>());
			}
		}
		
		
//		System.out.println(rules);
//		System.out.println(firsts);
//		System.out.println(follows);

	}

	/**
	 * Calculates the First of each variable in the CFG.
	 * 
	 * @return A string representation of the First of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	
	public void printArray( String[] array) {
		System.out.print("["+array[0]);
		for (int i = 1; i < array.length; i++) {
			System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("]");
	}
	
	
	public String first() {
		int counter = 0;
		boolean change = true;
		while(change) {
			counter++;
			change = false;
//			System.out.println(firsts);

	        Set<String> setOfKeys = rules.keySet();
			 for (String key : setOfKeys) {
				ArrayList<String> current = rules.get(key);
				for (int i = 0; i < current.size(); i++) {
					String[] bk = current.get(i).split("");
//					printArray(bk);
					
					boolean allEpsilon = true;
					if(bk[0].equals("e")) {
						ArrayList<String> theFirst = firsts.get(key);
						if(!theFirst.contains("e")) {
							change=true;
//							System.out.println("added epsilon to " + key );

							theFirst.add("e");
							firsts.put(key,theFirst);
						}							
					}else {
						for (int j = 0; j < bk.length; j++) {
							ArrayList<String> theFirst = firsts.get(bk[j]);
//							System.out.println("the rule:" + theFirst);
							if(theFirst != null) {
								if(!theFirst.contains("e")) {
									allEpsilon = false;
								}		
							}else {
								allEpsilon=false;
							}
						
					}
					if(allEpsilon) {
							ArrayList<String> theFirst = firsts.get(key);
							if(!theFirst.contains("e")) {
								change=true;
//								System.out.println("added epsilon to " + key );
								theFirst.add("e");
								firsts.put(key,theFirst);
							}	
					}
					//---------------------------------------------------------------
		
					for (int j = 0; j < bk.length; j++) {
					
						allEpsilon = true;
						for (int k = 0; k < j; k++) {
							ArrayList<String> theFirst = firsts.get(bk[k]);
							if(theFirst != null) {
								if(!theFirst.contains("e")) {
									allEpsilon = false;
								}		
							}else {
								allEpsilon=false;
							}
						}
						
						if(allEpsilon) {
							
							ArrayList<String> theNewFirst = firsts.get(bk[j]);
//							System.out.println("theNewFirst of " + bk[j]);
							
							if(theNewFirst==null) {//terminal
								ArrayList<String> theFirst = firsts.get(key);
								if(!theFirst.contains(bk[j])) {
									change=true;
//									System.out.println("added terminal " + bk[j] + " to " + key );

									theFirst.add(bk[j]);
									firsts.put(key,theFirst);
								}	
							}else {
								for (int k = 0; k < theNewFirst.size(); k++) {
									ArrayList<String> theFirst = firsts.get(key);
									if(!theFirst.contains(theNewFirst.get(k)) && !theNewFirst.get(k).equals("e")) {
										change=true;
//										System.out.println("added firsts of " + bk[j] + " that is: " + theNewFirst.get(k)+  " to " + key +" due to epsilons");
										theFirst.add(theNewFirst.get(k));
										firsts.put(key,theFirst);
									}	
								}

							}
							
						}
						
						
						
					}
					}
					
					
				}
		     }
		}
		
		// TODO Auto-generated method stub
		return firstToString();
	}
	
	String firstToString() {
		String s ="";
		for (int i = 0; i < prod.length; i++) {
			s+= prod[i]+"," ;
			ArrayList<String> theFirst = firsts.get(prod[i]);
			Collections.sort(theFirst);
			for (int j = 0; j < theFirst.size(); j++) {
				s+= theFirst.get(j);
			}
			if(i!=prod.length-1) {
							s+= ";";

			}
			
		}
		
		
		return s;
		
	}
	
	String followToString() {
		String s ="";
		for (int i = 0; i < prod.length; i++) {
			s+= prod[i]+"," ;
			ArrayList<String> theFollow = follows.get(prod[i]);
			Collections.sort(theFollow);
			boolean containsDollar=false;

			for (int j = 0; j < theFollow.size(); j++) {
				if(!theFollow.get(j).equals("$")) {
						s+= theFollow.get(j);
				}else {
					containsDollar=true;
				}
			}
			if(containsDollar) {
				s+= "$";
			}
			if(i!=prod.length-1) {
							s+= ";";
			}
		}
		return s;
		
	}

	/**
	 * Calculates the Follow of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String follow() {
		first();
		System.out.println("First is:" + firsts);
		System.out.println();
		System.out.println();


		boolean change = true;
		while(change) {
			System.out.println("--------------------------");

			System.out.println(follows);
			change=false;
			Set<String> setOfKeys = rules.keySet();
			for (String key : setOfKeys) {
				ArrayList<String> current = rules.get(key);
				for (int i = 0; i < current.size(); i++) {
					String[] bk = current.get(i).split("");
					for (int j = 1; j < bk.length; j++) {
						ArrayList<String> B = rules.get(bk[j-1]);
						if(B!=null) {//not a terminal
							ArrayList<String> Beta = rules.get(bk[j]);
							if(Beta==null) {// beta is terminal so just add it
								ArrayList<String> theFollows = follows.get(bk[j-1]);
								if(!theFollows.contains(bk[j])) {
									change=true;
									System.out.println("added terminal " + bk[j] + " to " + bk[j-1] );

									theFollows.add(bk[j]);
									follows.put(bk[j-1],theFollows);
								}	
								
								
							}else {//beta is not a terminal add firsts of Beta
								ArrayList<String> theNewFirst = firsts.get(bk[j]);
								for (int k = 0; k < theNewFirst.size(); k++) {
									ArrayList<String> theFollows = follows.get(bk[j-1]);
									if(!theFollows.contains(theNewFirst.get(k)) && !theNewFirst.get(k).equals("e")) {
										change=true;
										System.out.println("added firsts of " + bk[j] + " that is: " + theNewFirst.get(k)+  " to " + bk[j-1]);
										theFollows.add(theNewFirst.get(k));
										follows.put(bk[j-1],theFollows);
									}	
								}
								
								//part2 
								
								if(theNewFirst.contains("e")) {
									
									if(j==bk.length-1) { // handle end add follow to follow
										ArrayList<String> theNewFollows = follows.get(key);
										for (int k = 0; k < theNewFollows.size(); k++) {
											ArrayList<String> theFollows = follows.get(bk[j-1]);
											if(!theFollows.contains(theNewFollows.get(k)) && !theNewFollows.get(k).equals("e")) {
												change=true;
												System.out.println("added follows of " + key + " that is: " + theNewFollows.get(k)+  " to " + bk[j-1]);
												theFollows.add(theNewFollows.get(k));
												follows.put(bk[j-1],theFollows);
											}	
										}
										
									}else {
										ArrayList<String> theVeryNewFirst = firsts.get(bk[j+1]);
										if(theVeryNewFirst==null) {
											ArrayList<String> theFollows = follows.get(bk[j-1]);
											if(!theFollows.contains(bk[j+1]) && !bk[j+1].equals("e")) {
												change=true;
												System.out.println("added terminal of " + bk[j+1] + " that is: " + bk[j+1] +  " to " + bk[j-1]);
												theFollows.add(bk[j+1]);
												follows.put(bk[j-1],theFollows);
											}	
											
										}else {
												for (int k = 0; k < theVeryNewFirst.size(); k++) {
											ArrayList<String> theFollows = follows.get(bk[j-1]);
											if(!theFollows.contains(theVeryNewFirst.get(k)) && !theVeryNewFirst.get(k).equals("e")) {
												change=true;
												System.out.println("added firsts of " + bk[j+1] + " that is: " + theVeryNewFirst.get(k)+  " to " + bk[j-1]);
												theFollows.add(theVeryNewFirst.get(k));
												follows.put(bk[j-1],theFollows);
											}	
										}
										}
				
									}
								}
							}
						}
					}
					//--------------------------------
					String finalS = bk[bk.length-1];
					
					ArrayList<String> Beta = rules.get(finalS);
					if(Beta!=null) {//beta is not a terminal add firsts of Beta
						ArrayList<String> theNewFollows = follows.get(key);
						for (int k = 0; k < theNewFollows.size(); k++) {
							ArrayList<String> theFollows = follows.get(finalS);
							if(!theFollows.contains(theNewFollows.get(k)) && !theNewFollows.get(k).equals("e")) {
								change=true;
//								System.out.println("added follows of " + key + " that is: " + theNewFollows.get(k)+  " to " + finalS);
								theFollows.add(theNewFollows.get(k));
								follows.put(finalS,theFollows);
							}	
						}
					}
				}
			}
		}
		
		
		return followToString();
	}
	
	public static void main(String[] args) {
//		FFCFG ffgfg = new FFCFG("S,yS,van;K,dSg,rw;H,uk,fHK,HwK,e;P,KPa,f");
//		System.out.println(ffgfg.follow());
//		System.out.println("S,g$;K,dfrw;H,drw;P,a");
	}

}
