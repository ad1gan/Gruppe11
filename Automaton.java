public class Automaton{
	private Node[] nodes;
	private static int counter;
	private Pair start_end;

	private Pair parseTree(Tree tree){
		if(tree.getValue()=='.'){
			Pair l = parseTree(tree.getLeft());
			Pair r = parseTree(tree.getRight());
			//Nicht exakt gleiche Realisierung wie in Notes, aber nah genug
			nodes[counter] = new Node(nodes.length);
			if(l.first()!=l.second())
				nodes[l.second()].setEdge(counter,'3');
			else
				nodes[l.second()].setEdge(counter,tree.getLeft().getValue());
			if(r.first()!=r.second())
				nodes[counter].setEdge(r.first(),'3');
			else
				nodes[counter].setEdge(r.first(),tree.getRight().getValue());
			counter++;
			return new Pair(l.first(),r.second());
		} else if(tree.getValue()=='|'){
			//Oberer und unterer grauer Block; Pair jeweils erstes und letzes Element
			Pair l = parseTree(tree.getLeft());
			Pair r = parseTree(tree.getRight());
			//Legt Anfangs und Endnode an
			nodes[counter]   = new Node(nodes.length);
			nodes[counter+1] = new Node(nodes.length);
			//Legt Edges von erster Node aus an
			if(l.first()!=l.second())
				nodes[counter].setEdge(l.first(),'3');
			else
				nodes[counter].setEdge(l.first(),tree.getLeft().getValue());
			if(r.first()!=r.second())
				nodes[counter].setEdge(r.first(),'3');
			else
				nodes[counter].setEdge(r.first(),tree.getRight().getValue());
			//Legt Edges zu letzer Node an
			nodes[l.second()].setEdge(counter+1,'3');
			nodes[r.second()].setEdge(counter+1,'3');
			//Berichtigt Counter
			counter+=2;
			return new Pair(counter-2, counter-1);
		} else if(tree.getValue()=='*'){
			Pair l = parseTree(tree.getLeft());

			nodes[counter]   = new Node(nodes.length);
			nodes[counter+1] = new Node(nodes.length);

			nodes[counter].setEdge(l.first(),'3');
			nodes[counter].setEdge(counter+1,'3');
			nodes[l.second()].setEdge(counter+1,'3');

			if(l.first()!=l.second())
				nodes[l.second()].setEdge(l.first(),'3');
			else
				nodes[l.second()].setEdge(l.first(),tree.getLeft().getValue());

			counter+=2;
			return new Pair(counter-2, counter-1);
		} else{
			nodes[counter] = new Node(nodes.length);
			counter++;
			return new Pair(counter-1,counter-1);
		}
	}
	public Automaton(Tree tree){
		int size = tree.getVerts();
		nodes = new Node[size];
		for(int i=0;i<size;i++)
			nodes[i] = new Node(size);
		start_end = parseTree(tree);
	}
	public int start(){
		return start_end.first();
	}
	public int end(){
		return start_end.second();
	}
	public int getSize(){
		return nodes.length;
	}
	public char getEdge(int s, int e){
		return nodes[s].getEdge(e);
	}
	public boolean containsEdge(int s, int e){
		return (getEdge(s,e)!='0');
	}
	public void setEdge(int s, int e, char c){
		nodes[s].setEdge(e,c);
	}/*
	public Node[] getAutomaton(){
		return this.nodes;
	}*/
	public Node getNode(int i){
		return nodes[i];
	}
	public void printout(){
		for(int i=0;i<nodes.length;i++){
			System.out.print(i);
			for(int j=0;j<nodes.length;j++){
				System.out.print("\t");
				System.out.print(nodes[i].getEdge(j));
			}
			System.out.println("");
		}
	}
}