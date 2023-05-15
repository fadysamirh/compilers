package csen1002.main.task1;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/**
 * Write your info here
 * 
 * @name Fady Samir
 * @id 43-1366
 * @labNumber 18
 */
public class DFA {
	/**
	 * DFA constructor
	 * 
	 * @param description is the string describing a DFA
	 */
	Set<Integer> acceptStates;
	Hashtable<Integer, State> transitionTable;

	public DFA(String description) {
		// TODO Write Your Code Here
		acceptStates = new HashSet<Integer>();
		transitionTable = new Hashtable<Integer, State>();

		String[] splitedStringByHash = description.split("#");
		String[] acceptStatesArray = splitedStringByHash[1].split(",");

		for (int i = 0; i < acceptStatesArray.length; i++) {
			acceptStates.add(Integer.parseInt(acceptStatesArray[i]));
		}

		String[] transistionStringSplitedSemi = splitedStringByHash[0].split(";");

		for (int i = 0; i < transistionStringSplitedSemi.length; i++) {
			String[] transitionSplitedComma = transistionStringSplitedSemi[i].split(",");
			transitionTable.put(Integer.parseInt(transitionSplitedComma[0]),
					new State(Integer.parseInt(transitionSplitedComma[0]), Integer.parseInt(transitionSplitedComma[1]),
							Integer.parseInt(transitionSplitedComma[2])));
		}

	}

	/**
	 * Returns true if the string is accepted by the DFA and false otherwise.
	 * 
	 * @param input is the string to check by the DFA.
	 * @return if the string is accepted or not.
	 */
	public boolean run(String input) {
		// TODO Write Your Code Here
		int currentState = 0;
		for (int i = 0; i < input.length(); i++) {
			int currentCharacter = Integer.parseInt("" + input.charAt(i));
			currentState = currentCharacter == 0 ? transitionTable.get(currentState).zeroTransition
					: transitionTable.get(currentState).oneTransition;
		}
		return acceptStates.contains(currentState)?true:false;
	}

	public class State {
		int state;
		int zeroTransition;
		int oneTransition;

		public State(int state, int zeroTransition, int oneTransition) {
			this.state = state;
			this.zeroTransition = zeroTransition;
			this.oneTransition = oneTransition;
		}
	}
}
