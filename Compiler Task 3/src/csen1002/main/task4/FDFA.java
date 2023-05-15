package csen1002.main.task4;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Write your info here
 * 
 * @name Fady Samir
 * @id 43-1366
 * @labNumber 18
 */
public class FDFA {
	/**
	 * FDFA constructor
	 * 
	 * @param description is the string describing a FDFA
	 */
	ArrayList<State> states;
	Set<Integer> acceptStates;
	Stack<State> stack;
	Queue<State> queue;
	Stack<Integer> inputString;

	public FDFA(String description) {
		// TODO Write Your Code Here
		states = new ArrayList<State>();
		stack = new Stack<State>();
		acceptStates = new HashSet<Integer>();
		inputString = new Stack<Integer>();
//		queue= new Queue<State>();
		// Split the string on '#'
		String[] splitedHashString = description.split("#");
		String[] splitSemiStatesString = splitedHashString[0].split(";");
		String[] splitedAcceptCommaString = splitedHashString[1].split(",");
		for (int i = 0; i < splitSemiStatesString.length; i++)
			states.add(new State(0, 0, 0, ""));

		for (int i = 0; i < splitSemiStatesString.length; i++) {
			String[] splitedCommaStatesString = splitSemiStatesString[i].split(",");
			states.set(Integer.parseInt(splitedCommaStatesString[0]),
					new State(Integer.parseInt(splitedCommaStatesString[0]),
							Integer.parseInt(splitedCommaStatesString[1]),
							Integer.parseInt(splitedCommaStatesString[2]), splitedCommaStatesString[3]));
		}

		for (int i = 0; i < splitedAcceptCommaString.length; i++) {
//			System.out.println(splitedAcceptCommaString[i]);
			acceptStates.add(Integer.parseInt(splitedAcceptCommaString[i]));
		}
	}

	/**
	 * Returns a string of actions.
	 * 
	 * @param input is the string to simulate by the FDFA.
	 * @return string of actions.
	 */
	public String run(String input) {
		// TODO Write Your Code Here

		String output = "";

		int left = 0;
		int right = 0;
		while (right < input.length()) {
			State currentState = states.get(0);
			stack.push(currentState);
			for (int i = left; i < input.length(); i++) {
				int transition = Integer.parseInt("" + input.charAt(i));

				if (transition == 0) {
					stack.push(states.get(stack.peek().zeroTransition));
				} else {
					stack.push(states.get(stack.peek().oneTransition));

				}
				left++;

			}

			Stack<State> temp = new Stack<State>();
			Stack<Integer> inputTemp = new Stack<Integer>();

//		while (!stack.isEmpty()) {
			State stateBeforePop = stack.peek();
			while (!stack.isEmpty() && !acceptStates.contains(stack.peek().state)) {
				temp.push(stack.pop());
				left--;
			}

			if (stack.isEmpty()) {
				// no accept state was found

//				if (left == -1)
//					left = 0;
//				if (left != 0)
//					left--;
				left++;
				output += input.substring(left) + "," + stateBeforePop.stateString+ ";";

				break;
			} else {
//				if(right!=0) {
//					right--;
//				}
//				right++;
				output += input.substring(right, left) + "," + stack.peek().stateString + ";";

				right = left;
				stack.clear();

			}
		}

//		}
//		System.out.println(output);

		return output;
	}

	public class State {
		int state;
		int zeroTransition;
		int oneTransition;
		String stateString;

		public State(int state, int zeroTransition, int oneTransition, String stateString) {
			this.state = state;
			this.zeroTransition = zeroTransition;
			this.oneTransition = oneTransition;
			this.stateString = stateString;
		}
	}

	public static void main(String[] args) {
		FDFA a = new FDFA("0,0,1,A;1,2,1,B;2,0,3,C;3,3,3,N#0,1,2");
		System.out.print(a.run("1011100"));
	}
}
