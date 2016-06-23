import java.io.*;

class Assn4Main {
	public static void main(String[] args) throws IOException{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		SPLT tree = null;
		
		if (args.length==0) {
			boolean looping = true;
			String input;
			System.out.println("mode 1");
		    System.out.println("we will do the interactive test driver");
		    
		    // loop until the q command
		    while(looping){
		    	System.out.println("input command character");
		    	input = in.readLine();
		    	
		    	if(input.equals("new")){
		    		tree = new SPLT();
		    	}
		    	else if(input.equals("i")){
		    		System.out.println("input string to insert");
		    		input = in.readLine();
		    		tree.insert(input);
		    	}
		    	else if(input.equals("r")){
		    		System.out.println("input string to remove");
		    		input = in.readLine();
		    		tree.remove(input);
		    	}
		    	else if(input.equals("c")){
		    		System.out.println("input string to search for");
		    		input = in.readLine();
		    		System.out.println(tree.contains(input));
		    	}
		    	else if(input.equals("g")){
		    		System.out.println("input string to get");
		    		input = in.readLine();
		    		System.out.println(tree.get(input));
		    	}
		    	else if(input.equals("x")){
		    		System.out.println(tree.findMax());
		    	}
		    	else if(input.equals("n")){
		    		System.out.println(tree.findMin());
		    	}
		    	else if(input.equals("v")){
		    		System.out.println(tree.val());
		    	}
		    	else if(input.equals("e")){
		    		System.out.println(tree.empty());
		    	}
		    	else if(input.equals("s")){
		    		System.out.println(tree.size());
		    	}
		    	else if(input.equals("h")){
		    		System.out.println(tree.height());
		    	}
		    	else if(input.equals("q")){
		    		System.out.println("quitting the program");
		    		break;
		    	}
		    	else if(input.equals("p")){
		    		tree.printTree();
		    	}	
		    }
		}
		else {  
			String cmd;
		    String assocData;
		    System.out.println("mode 2");
		    System.out.println("here are the args: \n");
		    int na = args.length; 
		    for (int i=0; i < na; i++) {
		    	cmd = args[i];
		        System.out.println("command: "+cmd);
		        switch (cmd) {
		        	case "new":
		        		tree = new SPLT();
		        		break;
		        	case "i":
		        		assocData = args[++i];
			            System.out.println("assoc: "+assocData);
		        		tree.insert(assocData);
		        		break;
		        	case "r":
		        		assocData = args[++i];
			            System.out.println("assoc: "+assocData);
			            tree.remove(assocData);
			            break;
		        	case "c":
		        		assocData = args[++i];
			            System.out.println("assoc: "+assocData);
			            System.out.println(tree.contains(assocData));
			            break;
		        	case "g":
		        		assocData = args[++i];
			            System.out.println("assoc: "+assocData);
			            System.out.println(tree.get(assocData));
			            break;
		        	case "x":
		        		System.out.println(tree.findMax());
		        		break;
		        	case "n":
		        		System.out.println(tree.findMin());
		        		break;
		        	case "v":
		        		System.out.println(tree.findMin());
		        		break;
		        	case "e":
		        		System.out.println(tree.empty());
		        		break;
		        	case "s":
		        		System.out.println(tree.size());
		        		break;
		        	case "h":
		        		System.out.println(tree.height());
		        		break;
		        	case "q":
		        		System.out.println("quitting the program");
		        		System.exit(0);
		        		break;
		        	case "p":
		        		tree.printTree();
		        		
		        }
		        //System.out.println();
		    }
		    //System.out.println();     
		}
	}
}

class SPLT {
	TreeNode root;
	int size;
	
	SPLT(){
		root = null;
		size = 0;
	}
	
	public void insert(String string){
		if(root == null){
			root = new TreeNode(string);
			size++;
		}
		else{
			if(this.containsNoSplay(string)){
				this.contains(string);
			}
			else{
				this.insert(root, string);
			}
		}
	}
	
	public void insert(TreeNode current, String string){
		if(string.compareTo(current.getVal()) < 0){
			if(current.getLeft() == null){
				current.setLeft(string);
				current.getLeft().setParent(current);
				size++;
				this.splay(current.getLeft());
			}
			else{
				this.insert(current.getLeft(), string);
			}
		}
		else{
			if(current.getRight() == null){
				current.setRight(string);
				current.getRight().setParent(current);
				size++;
				this.splay(current.getRight());
			}
			else{
				this.insert(current.getRight(), string);
			}
		}
	}
	
	public boolean containsNoSplay(String string){
		return containsNoSplay(root, string);
	}
	
	public boolean containsNoSplay(TreeNode current, String string){
		if(current == null){
			return false;
		}
		
		if(string.compareTo(current.getVal()) < 0){
			return contains(current.getLeft(), string, current);
		}
		else if(string.compareTo(current.getVal()) > 0){
			return contains(current.getRight(), string, current);
		}
		else{
			return true;
		}
	}
	
	public void remove(String string){
		if(root == null){
		}
		else{
			boolean check = this.contains(string);
			if(check){
				this.removeRoot();
			}
		}
	}
	
	public void removeRoot(){
		TreeNode leftC = root.getLeft();
		TreeNode rightC = root.getRight();
		
		if(leftC == null && rightC == null){
			root = null;
		}
		else if(leftC == null){
			root = rightC;
			rightC.setParent(null);
		}
		else if(rightC == null){
			root = leftC;
			leftC.setParent(null);
		}
		else{
			leftC.setParent(null);
			rightC.setParent(null);
			
			root = leftC;
			
			this.findMax();
			
			root.setRight(rightC);
			rightC.setParent(root);
		}
		size--;
	}
	
	public TreeNode remove(TreeNode current, String string){
		if(string.compareTo(current.getVal()) < 0){
			if(current.getLeft() != null){
				current.setLeft(this.remove(current.getLeft(), string));
			}
		}
		else if(string.compareTo(current.getVal()) > 0){
			if(current.getRight() != null){
				current.setRight(this.remove(current.getRight(), string));
			}
		}
		else if(current.getLeft() != null && current.getRight() != null){
			current.setVal(findMin(current.getRight()));
			current.setRight(remove(current.getRight(), current.getVal()));
		}
		else{
			if(current.getLeft() != null){
				size--;
				return current.getLeft();
				
			}
			else{
				size--;
				return current.getRight();
			}
		}
		return current;
	}
	
	public String findMin(){
		return this.findMin(root);
	}
	
	public String findMin(TreeNode current){
		if(current == null){
			return null;
		}
		else if(current.getLeft() == null){
			this.splay(current);
			return current.getVal();
		}
		else{
			return this.findMin(current.getLeft());
		}
	}
	
	public String findMax(){
		return this.findMax(root);
	}
	
	public String findMax(TreeNode current){
		if(current == null){
			return null;
		}
		else if(current.getRight() == null){
			this.splay(current);
			return current.getVal();
		}
		else{
			return this.findMax(current.getRight());
		}
	}
	
	public boolean contains(String string){
		return contains(root, string, root.getParent());
	}
	
	public boolean contains(TreeNode current, String string, TreeNode parent){
		if(current == null){
			this.splay(parent);
			return false;
		}
		
		if(string.compareTo(current.getVal()) < 0){
			return contains(current.getLeft(), string, current);
		}
		else if(string.compareTo(current.getVal()) > 0){
			return contains(current.getRight(), string, current);
		}
		else{
			this.splay(current);
			return true;
		}
	}
	
	public TreeNode get(String string){
		return get(root, string);
	}
	
	public TreeNode get(TreeNode current, String string){
		if(current == null){
			return null;
		}
		
		if(string.compareTo(current.getVal()) < 0){
			return get(current.getLeft(), string);
		}
		else if(string.compareTo(current.getVal()) > 0){
			return get(current.getRight(), string);
		}
		else{
			return current;
		}
	}
	
	public String val(){
		return root.getVal();
	}
	
	public boolean empty(){
		return root == null;
	}
	
	public int size(){
		return size; //doesnt use remove
	}
	
	public int height(){
		if(this.empty()){
			return 0;
		}
		else{
			return height(root);
		}
	}
	
	public int height(TreeNode current){
		
		if(current.getRight() == null && current.getLeft() == null){
			return 0;
		}
		else if(current.getLeft() == null){
			return 1 + height(current.getRight());
		}
		else if(current.getRight() == null){
			return 1 + height(current.getLeft());
		}
		else{
			return 1 + greater(height(current.getLeft()), height(current.getRight()));
		}
	}
	
	public int greater(int int1, int int2){
		if(int1 > int2){
			return int1;
		}
		else{
			return int2;
		}
	}
	
	public void printTree(){
		int depth = 0;
		printTree(root, depth);
	}
	
	public void printTree(TreeNode current, int depth){
		if(root == null){
			System.out.println("The tree is empty!");
		}
		else{
			String output = "";
			for(int i = 0; i < (3*depth); i++){
				output += " ";
			}
			System.out.println(output + current.getVal());
		
			if(current.getLeft() != null){
				printTree(current.getLeft(), depth+1);
			}
			if(current.getRight() != null){
				printTree(current.getRight(), depth+1);
			}
		}
	}
	
	public void splay(TreeNode x){
		TreeNode b, c;
		TreeNode p = x.getParent();
		TreeNode g = null;
		TreeNode gg = null;
		if(p != null){
			g = p.getParent();
		}
		if(g != null){
			gg = g.getParent();
		}
		
		
		
		if(p == null){
		}
		
		else if(g == null){
			if(p.getLeft() == x){     //zig L
				b = x.getRight();
				x.setRight(p);
				p.setParent(x);
				p.setLeft(b);
				if(b != null){
					b.setParent(p);
				}
				x.setParent(null);
			}
			else{					  //zig R
				b = x.getLeft();
				x.setLeft(p);
				p.setParent(x);
				p.setRight(b);
				if(b != null){
					b.setParent(p);
				}
				x.setParent(null);
			}
			this.root = x;
		}
		
		else if(x == p.getRight() && p == g.getRight()){     //zig-zig R
			c = p.getLeft();
			b = x.getLeft();
			x.setParent(gg);
			if(gg != null){
				if(gg.getLeft() == g){
					gg.setLeft(x);
				}
				else{
					gg.setRight(x);
				}
			}
			p.setParent(x);
			x.setLeft(p);
			g.setParent(p);
			p.setLeft(g);
			p.setRight(b);
			if(b != null){
				b.setParent(p);
			}
			g.setRight(c);
			if(c != null){
				c.setParent(g);
			}
		}
		else if(x == p.getLeft() && p == g.getLeft()){		//zig-zig L
			c = p.getRight();
			b = x.getRight();
			x.setParent(gg);
			if(gg != null){
				if(gg.getLeft() == g){
					gg.setLeft(x);
				}
				else{
					gg.setRight(x);
				}
			}
			x.setRight(p);
			p.setParent(x);
			p.setRight(g);
			g.setParent(p);
			g.setLeft(c);
			if(c != null){
				c.setParent(g);
			}
			p.setLeft(b);
			if(b != null){
				b.setParent(p);
			}
		}
		else if(x == p.getRight() && p == g.getLeft()){		//zig-zag L
			c = x.getRight();
			b = x.getLeft();
			x.setParent(gg);
			if(gg != null){
				if(gg.getLeft() == g){
					gg.setLeft(x);
				}
				else{
					gg.setRight(x);
				}
			}
			x.setLeft(p);
			p.setParent(x);
			x.setRight(g);
			g.setParent(x);
			g.setLeft(c);
			if(c != null){
				c.setParent(g);
			}
			p.setRight(b);
			if(b != null){
				b.setParent(p);
			}
		}
		else if(x == p.getLeft() && p == g.getRight()){		//zig-zag R
			c = x.getRight();
			b = x.getLeft();
			x.setParent(gg);
			if(gg != null){
				if(gg.getLeft() == g){
					gg.setLeft(x);
				}
				else{
					gg.setRight(x);
				}
			}
			x.setLeft(g);
			g.setParent(x);
			x.setRight(p);
			p.setParent(x);
			g.setRight(b);
			if(b != null){
				b.setParent(g);
			}
			p.setLeft(c);
			if(c != null){
				c.setParent(p);
			}
		}
		
		if(x.getParent() == null){
			this.root = x;
		}
		
		if(x.getParent() != null){
			this.splay(x);
		}
	}
}

class TreeNode {
	String val;
	TreeNode left, right, parent;
	
	
	TreeNode(String string){
		this(string, null, null, null);
	}

	TreeNode(String string, TreeNode lc, TreeNode rc, TreeNode p){
		val = string;
		left = lc;
		right = rc;
		parent = p;
	}
	
	public void setVal(String string){
		val = string;
	}
	
	public String getVal(){
		return val;
	}
	
	public void setLeft(String string){
		left = new TreeNode(string);
	}
	
	public void setLeft(TreeNode node){
		left = node;
	}
	
	public void setRight(String string){
		right = new TreeNode(string);
	}
	
	public void setRight(TreeNode node){
		right = node;
	}
	
	public TreeNode getLeft(){
		return left;
	}
	
	public TreeNode getRight(){
		return right;
	}
	
	public void setParent(TreeNode node){
		parent = node;
	}
	
	public TreeNode getParent(){
		return parent;
	}
	
}




