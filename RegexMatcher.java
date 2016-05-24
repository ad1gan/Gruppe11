public class RegexMatcher{

	public static void main(String[] args) {
		Tree oak = new Tree(args[0]);
		Automaton entron = new Automaton(oak);
		RegexMatchResult douche = simulateBFS(args[1], entron);
		douche.print();
	}

	public static RegexMatchResult matchSetBased(String regex, String testText){
		Tree tree = new Tree(regex);
		Automaton auto = new Automaton(tree);
		RegexMatchResult res = simulateBFS(testText, auto);
		return res;
	}

	/** Tests if a given string testText matches on an Automaton or actually
	 *   if it matches a regular expression returns true if it's a yes
	 *   @param testText the String which is tested
	 *   @param entron the Automaton that represents the given regular expression
	 *   @return true if testText matches with entron otherwise false
	 */
	private static RegexMatchResult simulateBFS(String testText, Automaton entron){
		Set<Integer> actives   = new Set<Integer>();
		Set<Integer> actives2  = new Set<Integer>();
		RegexMatchResult curr  = new RegexMatchResult(0, "");
		RegexMatchResult valid = new RegexMatchResult(-1,"");
		entron.resetcounter();
		actives.addElement(entron.start());
		
		for(int i=0; i<testText.length(); i++){
			entron.freeSteps(actives);
			
			if(actives.contains(entron.end())){ //We already found a valid string, just need to see if it's of maximum length
				valid.setStartingPosition(curr.getStartingPosition());
				valid.setMatchedString(curr.getMatchedString());
			}

			for(int j=0; j<actives.size(); j++) //"expensive" connect
				for(int k=0; k<entron.getSize(); k++)
					if( entron.getEdge(actives.getElement(j),k)==testText.charAt(i) ) 
						actives2.addElement(k);

			if(actives2.size()==0){ //Cannot go further with the current string. Return or scrap?
				if(valid.getStartingPosition()!=-1 && valid.getMatchedString()!="") //We already found something valid, so it's of maximum length. Return it.
					return valid;
				else{ //We haven't found anything valid. Scrap.
					curr.setStartingPosition(i+1);
					curr.setMatchedString("");
				}
			} else{
				curr.setMatchedString(curr.getMatchedString() + testText.charAt(i));
				actives.copy(actives2);
			}
			actives2.clear();
		}
		entron.freeSteps(actives);

		if(!actives.contains(entron.end())) //The last string was not valid, so we haven't found anything
			return valid;
		return curr;
	}

}