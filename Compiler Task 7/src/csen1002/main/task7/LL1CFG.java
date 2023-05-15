package csen1002.main.task7;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Write your info here
 * 
 * @name John Smith
 * @id 43-0234
 * @labNumber 07
 */
public class LL1CFG {
	String[][] table;
	ArrayList<String> terminals;
	ArrayList<String> variables;

	/**
	 * LL1 CFG constructor
	 * 
	 * @param description is the string describing an LL(1) CFG, first, and follow
	 *                    as represented in the task description.
	 */
	public LL1CFG(String description) {
		String[] splitByHash = description.split("#");
		String[] rules = splitByHash[0].split(";");
		String[] firsts = splitByHash[1].split(";");
		String[] follows = splitByHash[2].split(";");
		terminals = new ArrayList<String>();
		variables = new ArrayList<String>();
		// getting variables and terminals
		for (int i = 0; i < rules.length; i++) {
			String[] rulesSplitByComma = rules[i].split(",");
			variables.add(rulesSplitByComma[0]);
			for (int j = 0; j < rulesSplitByComma.length; j++) {
				for (int k = 0; k < rulesSplitByComma[j].length(); k++) {
					if (Character.isLowerCase(rulesSplitByComma[j].charAt(k))
							&& !terminals.contains(rulesSplitByComma[j].charAt(k) + "")
							&& rulesSplitByComma[j].charAt(k) != 'e') {
						terminals.add(rulesSplitByComma[j].charAt(k) + "");
					}
				}
			}
		}
		terminals.add("$");

		table = new String[variables.size()][terminals.size()];

		for (int i = 0; i < variables.size(); i++) {
			String[] rulesSplitByComma = rules[i].split(",");
			String[] firstsSplitByComma = firsts[i].split(",");
			for (int j = 0; j < terminals.size(); j++) {
				for (int k = 1; k < rulesSplitByComma.length; k++) {
					if (firstsSplitByComma[k].contains(terminals.get(j))
							|| (firstsSplitByComma[k].contains("e") && follows[i].contains(terminals.get(j)))) {
						table[i][j] = rulesSplitByComma[k];
						break;

					}

				}
			}
		}
//		printArrayList(variables);
//		printArrayList(terminals);
//		printTable(table);

	}

	/**
	 * Returns A string encoding a derivation is a comma-separated sequence of
	 * sentential forms each representing a step in the derivation..
	 * 
	 * @param input is the string to be parsed by the LL(1) CFG.
	 * @return returns a string encoding a left-most derivation.
	 */


	public String parse(String input) {
		Stack<String> pda = new Stack<String>();
		String derivation="S";
		String lastVariable="S";
		input+="$";
		pda.push("$");
		pda.push("S");
		int i=0;
		while(!pda.peek().equals("$")) {
			String topOfStack=pda.pop();
			if(Character.isLowerCase(topOfStack.charAt(0))&&topOfStack.length()==1) {
				if(topOfStack.equals(input.charAt(i)+"")) {
					i++;
				}else {
					lastVariable="ERROR";
					derivation+=","+"ERROR";
					break;
				}
			}else {
				//check parsing table
				int variableIndex=variables.indexOf(topOfStack);
				int terminalIndex=terminals.indexOf(input.charAt(i)+"");
				String ruleFromParsingTable=table[variableIndex][terminalIndex];
				if(ruleFromParsingTable==(null)) {
					lastVariable="ERROR";
					derivation+=","+"ERROR";
					break;
				}
				else if(ruleFromParsingTable.equals("e")) {
					lastVariable=lastVariable.replaceFirst(topOfStack,"");
					derivation+=","+lastVariable;

				}
				 else {
					lastVariable=lastVariable.replaceFirst(topOfStack,ruleFromParsingTable);
					System.out.println(lastVariable+i);
					derivation+=","+lastVariable;
					for(int j=0;j<ruleFromParsingTable.length();j++) {
						pda.push(ruleFromParsingTable.charAt(ruleFromParsingTable.length()-j-1)+"");
					}
				}
				
				

			}
//			   printList((String[])pda.toArray());

		}
		
//		System.out.println(i);

		return derivation;
	}
	public ArrayList<String> listToArrayList(String[] list) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for (int i = 0; i < list.length; i++)
			toReturn.add(list[i]);
		return toReturn;
	}

	public void printArrayList(ArrayList<String> array) {
		System.out.print("[" + array.get(0));
		for (int i = 1; i < array.size(); i++) {
			System.out.print(",");
			System.out.print(array.get(i));
		}
		System.out.println("]");
	}

	public void printList(String[] list) {
		System.out.print("[" + list[0]);
		for (int i = 1; i < list.length; i++) {
			System.out.print(",");
			System.out.print(list[i]);
		}
		System.out.println("]");
	}

	public void printTable(String[][] table) {
		for (String[] row : table) {
			printList(row);
		}
	}
	public static void main(String[] args) {
//		LL1CFG ll1cfg1 = new LL1CFG("S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o");
//		LL1CFG ll1cfg1 = new LL1CFG("S, iST, e; T, cS, a#S, i, e; T, c, a#S, ca$; T, ca$");
//		LL1CFG ll1cfg2 = new LL1CFG(
//				"S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x");
		

	}

}
