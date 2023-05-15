package csen1002.main.task5;

import java.util.ArrayList;

/**
 * Write your info here
 * 
 * @name Fady Samir
 * @id 43-1366
 * @labNumber 18
 */
public class CFG {
	/**
	 * CFG constructor
	 * 
	 * @param description is the string describing a CFG
	 */
	String [] rules;

	public CFG(String description) {
		// TODO Write Your Code Here
		rules=description.split(";");
	}

	/**
	 * Returns a string of elimnated left recursion.
	 * 
	 * @param input is the string to simulate by the CFG.
	 * @return string of elimnated left recursion.
	 */
	public String lre() {
		// TODO Write Your Code Here
		String sol="";
		for(int i=0;i<rules.length;i++) {
			for(int j=0;j<i;j++) {
				String [] previousRule=rules[j].split(",");
				String [] currentRule=rules[i].split(",");
				ArrayList<String> withAddedRules=new ArrayList<String>();
				withAddedRules.add(currentRule[0]);
				for(int k=1;k<currentRule.length;k++) {
					if(currentRule[k].indexOf(previousRule[0])==0) {
						for(int l=1;l<previousRule.length;l++) {
							withAddedRules.add(previousRule[l]+currentRule[k].substring(1));
						}
					}else {
						withAddedRules.add(currentRule[k]);
					}
				}
				rules[i]=String.join(",", withAddedRules);
			}
			
			
			char currentRuleSymbol=rules[i].charAt(0);
			String alphaRule=currentRuleSymbol+"'";
			String betaRule=currentRuleSymbol+"";
			String [] currentRuleSplittedByComma=rules[i].split(",");
			boolean hasAlpha=false;
			for(int j=1;j<currentRuleSplittedByComma.length;j++) {
				if(currentRuleSplittedByComma[j].indexOf(currentRuleSymbol)==0) {
					hasAlpha=true;
					alphaRule+=","+currentRuleSplittedByComma[j].substring(1)+currentRuleSymbol+"'";
					}else {
					betaRule+=","+currentRuleSplittedByComma[j]+currentRuleSymbol+"'";
				}
			}
			if(hasAlpha) {
				sol+=betaRule+";"+alphaRule+",e;";
				rules[i]=betaRule;
			}else {
				sol+=rules[i]+";";
			}

			
		}
		if(sol.charAt(sol.length()-1)==';') sol=sol.substring(0, sol.length()-1);
		return sol;
	}

}
