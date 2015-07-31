

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * @author Ankur Pandey
 *
 */
public class Graph 
{
	public Node root;
	public ArrayList<Node> nodes=new ArrayList<Node>();
	public int[][] adjMatrix;//Edges will be represented as adjacency Matrix
	Queue q=new LinkedList();
	Stack s=new Stack();
	int size;
	private Node last =null;
	
	File file; 
	FileWriter wr;
	
	public Node getLast() {
		return last;
	}

	public void setLast(Node last) {
		this.last = last;
	}

	/**
	 * @return
	 */
	public int[][] getAdjMatrix() {
		return adjMatrix;
	}

	public int getSize() {
		return size;
	}

	public void setAdjMatrix(int[][] adjMatrix) {
		this.adjMatrix = adjMatrix;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public void setRootNode(Node n)
	{
		this.root=n;
	}
	
	public Node getRootNode()
	{
		return this.root;
	}
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	
	
	
	private Node getUnvisitedChildNode(Node n)
	{
		int index=nodes.indexOf(n);
		int j=0;
		while(j<size)
		{
			if(adjMatrix[index][j]==1 && ((Node)nodes.get(j)).isVisited()==false)
			{
				return (Node)nodes.get(j);
			}
			j++;
		}
		return null;
	}
	
	public void initBfs() throws IOException{
		
		file = new File("test_bfs_output-"+size+".txt");
		
		// creates the file
		file.createNewFile();
		wr = new FileWriter(file);
		
		//Add root to queue
		q.add(this.root);
	}
	
	public Node bfs() throws IOException
	{
		//BFS uses Queue data structure
	
		if(q.isEmpty()){
			return null;
		}
			
		root.setVisited(true);
		Node n=(Node)q.remove();
		Node child=null;
		while((child=getUnvisitedChildNode(n))!=null)
		{
			child.setVisited(true);
			q.add(child);
		}
		printNode(n,wr);
		last =n;
		return last;
	}
	
	public void initDfs() throws IOException{
		
		file = new File("test_dfs_output-"+size+".txt");
		// creates the file
		file.createNewFile();
		wr = new FileWriter(file);
		s.push(this.root);
		printNode(this.root,wr);
	}
	
	//DFS traversal of a tree is performed by the dfs() function
	public Node dfs() throws IOException
	{
		//DFS uses Stack data structure
	
		if(s.isEmpty()){
			return null;
		}
		
		Node child=null;
		root.setVisited(true);
				
		while(true){
			
			if(s.isEmpty()){
				return null;
			}
			
			Node n=(Node)s.peek();
			child=getUnvisitedChildNode(n);
			
			
			if(child!=null)
			{
				child.setVisited(true);
				printNode(child,wr);
				s.push(child);
				break;
			}
			else
			{
				s.pop();
			}
		}
		
		last=child;
		return last;
	}
	
	
	//Utility methods for clearing visited property of node
	public void clearNodes() throws IOException
	{
		int i=0;
		while(i<size)
		{
			Node n=(Node)nodes.get(i);
			n.setVisited(false);
			i++;
		}
			wr.flush();
			wr.close();
	}
	
	//Utility methods for printing the node's label
	public void printNode(Node n,FileWriter wr) throws IOException
	{
		
		wr.write(n.getNodeNumber()+".  "+n.getCityName()+" "+n.getPopulation()+" "+n.getElevation()+"\n");
		System.out.println(n.getNodeNumber()+".  "+n.getCityName()+" "+n.getPopulation()+" "+n.getElevation());
	}
}