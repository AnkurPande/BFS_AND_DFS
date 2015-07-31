import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;


/**
 * @author Ankur Pandey
 *
 */
public class SimplGUI extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private static class CUSTOMIZE{
		//JLabel Caption String 
		private static String  caption_StartNode = "Source Node :";
		private static String  caption_LastNode = "Last Node :";
		private static String  caption_start = "Reset";
		private static String  caption_next = "Next";
		private static String  caption_end = "Fast";
		private static String  caption_pause = "Stop";
		private static String  caption_nodeNo= "Node No :";
		private static String  caption_city= "City :";
		private static String  caption_elevation= "Elevation :";
		private static String  caption_population= "Population :";
						
		// JLabel color
		private static Color colorInfoPanel = Color.GREEN; 
		private static Color colorOutputPanel = Color.BLUE; 
		private static Color colorControlPanel = Color.RED; 
		
		//Input FileNames
		private static String NODE_DETAILS_FILENAME = "test_nodecols-3000.txt";
		private static String ADJACENCY_MATRIX_FILENAME = "test_matrix-3000.txt";
		private static String TARGET_FILENAME = "test_target-3000.txt";
	}
	
	//Components for info Panel
	JLabel source,last,startNodeVal,endNodeVal;
	
	//Components for output Panel
	JLabel  nodeNo,city,elevation,population;
	JLabel  nodeNoVal,cityVal,elevVal,popuVal;
	
	//Components for control panel
	JLabel start,end,nxt,pause;
	JButton reset,nextButton,stop,endButton;
	
	//Components operation panel
	JButton bfs,dfs;
		
	Boolean isBfs= false, isDfs =false;
	static Graph g;
	static Node[] nodes;
	static int[][] adjMatrix;
	
	
	/**
	 * @return
	 */
	public JPanel createGUI(){
		JPanel fullGUI = new JPanel();
		fullGUI.setLayout(null);
			
		/*=====Setting up Operation panel===========================================================================*/
		JPanel operationPanel = new JPanel();
		operationPanel.setLayout(null);
		operationPanel.setLocation(10, 0);
		operationPanel.setSize(330, 50);
		fullGUI.add(operationPanel);
		
		bfs  = new JButton("BFS");
		bfs.setLocation(10,10);
		bfs.setSize(145, 30);
		bfs.setHorizontalTextPosition(SwingConstants.CENTER);
		bfs.setHorizontalAlignment(0);
		bfs.addActionListener(this);
		operationPanel.add(bfs);
		
		dfs = new JButton("DFS");
		dfs.setLocation(170,10);
		dfs.setSize(145,30);
		dfs.setHorizontalTextPosition(SwingConstants.CENTER);
		dfs.setHorizontalAlignment(0);
		dfs.addActionListener(this);
		operationPanel.add(dfs);
		fullGUI.add(operationPanel);
		
		/*=====Setting up info panel===============================================================================*/
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setLocation(10, 70);
		infoPanel.setSize(330, 50);
		fullGUI.add(infoPanel);
		
		source = new JLabel(CUSTOMIZE.caption_StartNode);
		source.setLocation(10,10);
		source.setSize(100, 30);
		source.setHorizontalTextPosition(SwingConstants.CENTER);
		source.setHorizontalAlignment(0);
		infoPanel.add(source);
		
		startNodeVal = new JLabel();
		startNodeVal.setLocation(90,10);
		startNodeVal.setSize(40, 30);
		startNodeVal.setHorizontalTextPosition(SwingConstants.CENTER);
		startNodeVal.setHorizontalAlignment(0);
		startNodeVal.setForeground(CUSTOMIZE.colorInfoPanel);
		infoPanel.add(startNodeVal);
		
		last = new JLabel(CUSTOMIZE.caption_LastNode);
		last.setLocation(170,10);
		last.setSize(100, 30);
		last.setHorizontalTextPosition(SwingConstants.CENTER);
		last.setHorizontalAlignment(0);
		infoPanel.add(last);
		
		endNodeVal = new JLabel();
		endNodeVal.setLocation(250,10);
		endNodeVal.setSize(40, 30);
		endNodeVal.setHorizontalTextPosition(SwingConstants.CENTER);
		endNodeVal.setHorizontalAlignment(0);
		endNodeVal.setForeground(CUSTOMIZE.colorInfoPanel);
		infoPanel.add(endNodeVal);
		
		/*=====Setting up Control panel===================================================================*/
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);
		controlPanel.setLocation(10, 140);
		controlPanel.setSize(330, 50);
		fullGUI.add(controlPanel);
		
		reset = new JButton(CUSTOMIZE.caption_start);
		reset.setLocation(10,10);
		reset.setSize(70, 30);
		reset.setHorizontalTextPosition(SwingConstants.CENTER);
		reset.setHorizontalAlignment(0);
		reset.setForeground(CUSTOMIZE.colorControlPanel);
		reset.addActionListener(this);
		controlPanel.add(reset);
		
		nextButton = new JButton(CUSTOMIZE.caption_next);
		nextButton.setLocation(90,10);
		nextButton.setSize(70, 30);
		nextButton.setHorizontalTextPosition(SwingConstants.CENTER);
		nextButton.setHorizontalAlignment(0);
		nextButton.setForeground(CUSTOMIZE.colorControlPanel);
		nextButton.addActionListener(this);
		controlPanel.add(nextButton);
		
		stop =new JButton(CUSTOMIZE.caption_pause);
		stop.setLocation(170,10);
		stop.setSize(70, 30);
		stop.setHorizontalTextPosition(SwingConstants.CENTER);
		stop.setHorizontalAlignment(0);
		stop.setForeground(CUSTOMIZE.colorControlPanel);
		stop.addActionListener(this);
		controlPanel.add(stop);
		
		endButton = new JButton(CUSTOMIZE.caption_end);
		endButton.setLocation(250,10);
		endButton.setSize(70, 30);
		endButton.setHorizontalTextPosition(SwingConstants.CENTER);
		endButton.setHorizontalAlignment(0);
		endButton.setForeground(CUSTOMIZE.colorControlPanel);
		endButton.addActionListener(this);
		controlPanel.add(endButton);
		
		/*=============Setting up Output panel========================================================================*/
		JPanel outputPanel = new JPanel();
		outputPanel.setLayout(null);
		outputPanel.setLocation(10, 230);
		outputPanel.setSize(330, 80);
		fullGUI.add(outputPanel);
		
		nodeNo = new JLabel(CUSTOMIZE.caption_nodeNo);
		nodeNo.setLocation(10,10);
		nodeNo.setSize(70, 30);
		nodeNo.setHorizontalTextPosition(SwingConstants.LEFT);
		nodeNo.setHorizontalAlignment(0);
		outputPanel.add(nodeNo);
		
		nodeNoVal = new JLabel();
		nodeNoVal.setLocation(80,10);
		nodeNoVal.setSize(40, 30);
		nodeNoVal.setHorizontalTextPosition(SwingConstants.CENTER);
		nodeNoVal.setHorizontalAlignment(0);
		nodeNoVal.setForeground(CUSTOMIZE.colorOutputPanel);
		outputPanel.add(nodeNoVal);
		
		city = new JLabel(CUSTOMIZE.caption_city);
		city.setLocation(130,10);
		city.setSize(60, 30);
		city.setHorizontalTextPosition(SwingConstants.LEFT);
		city.setHorizontalAlignment(0);
		outputPanel.add(city);
		
		cityVal = new JLabel();
		cityVal.setLocation(190,10);
		cityVal.setSize(140, 30);
		cityVal.setHorizontalTextPosition(SwingConstants.CENTER);
		cityVal.setHorizontalAlignment(0);
		cityVal.setForeground(CUSTOMIZE.colorOutputPanel);
		outputPanel.add(cityVal);
		
		elevation = new JLabel(CUSTOMIZE.caption_elevation);
		elevation.setLocation(10,50);
		elevation.setSize(60, 30);
		elevation.setHorizontalTextPosition(SwingConstants.LEFT);
		elevation.setHorizontalAlignment(0);
		outputPanel.add(elevation);
		
		elevVal = new JLabel();
		elevVal.setLocation(80,50);
		elevVal.setSize(40, 30);
		elevVal.setHorizontalTextPosition(SwingConstants.CENTER);
		elevVal.setHorizontalAlignment(0);
		elevVal.setForeground(CUSTOMIZE.colorOutputPanel);
		outputPanel.add(elevVal);
		
		population = new JLabel(CUSTOMIZE.caption_population);
		population.setLocation(130,50);
		population.setSize(70, 30);
		population.setHorizontalTextPosition(SwingConstants.LEFT);
		population.setHorizontalAlignment(0);
		outputPanel.add(population);
		
		popuVal = new JLabel();
		popuVal.setLocation(190,50);
		popuVal.setSize(140, 30);
		popuVal.setHorizontalTextPosition(SwingConstants.CENTER);
		popuVal.setHorizontalAlignment(0);
		popuVal.setForeground(CUSTOMIZE.colorOutputPanel);
		outputPanel.add(popuVal);
						
		fullGUI.setOpaque(true);
		return fullGUI;
	}
	
	public SimplGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("BFS & DFS GUI APPLICATION");
		frame.setContentPane(this.createGUI());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(350,310);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		
		BufferedReader br = null;
		String line = null; String[] parts;  
		int count =0, row =0, col =0,source =0;
			
		// Scanner
		Scanner sc = new Scanner(System.in);
		
		//Number of nodes in the graph.
		System.out.println("Enter No of nodes in graph");
		int size = sc.nextInt();sc.close();
				
		//Create the graph, add nodes, create edges between nodes
		g=new Graph();
		nodes = new Node[size];
		adjMatrix= new int[size][size];
		g.setSize(size);
		
		//Read Node Detail
		
		br = new BufferedReader(new FileReader(CUSTOMIZE.NODE_DETAILS_FILENAME));
		while((line=br.readLine())!=null){
			line = line.trim();
			parts = line.split(" ");
			nodes[count] = new Node(Integer.parseInt(parts[0]),parts[1], Integer.parseInt(parts[2]),Float.parseFloat(parts[3]));
			g.addNode(nodes[count]);
			count++;
		}
		
		br.close();
		//set root node
		br = new BufferedReader(new FileReader(CUSTOMIZE.TARGET_FILENAME));
		while((line=br.readLine())!=null){
			line= line.trim();
			parts = line.split(" ");
			source = Integer.parseInt(parts[0]);
		}
		g.setRootNode(nodes[source-1]);
		
		br.close();
		//Get adjacent matrix details
		br = new BufferedReader(new FileReader(CUSTOMIZE.ADJACENCY_MATRIX_FILENAME));
		while((line=br.readLine())!=null){
			line = line.trim();
			parts = line.split(" ");
			for(col=0;col<=parts.length-1;col++){
				adjMatrix[row][col] = Integer.parseInt(parts[col]);
			}
			row++;
		}
		
		br.close();
		
		g.setAdjMatrix(adjMatrix);
				
		SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	new SimplGUI();
	            }
	    });
	}

	public void reset(){
		//Reset the display.
		nodeNoVal.setText("");	
		cityVal.setText("");
		elevVal.setText("");
		popuVal.setText("");
			
	}
	
	public void display(Node n){
		//Display next node.
		nodeNoVal.setText(n.getNodeNumber()+"");	
		cityVal.setText(n.getCityName()+"");
		elevVal.setText(n.getElevation()+"");
		popuVal.setText(n.getPopulation()+"");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Get the source of action.
		Object eventSource = e.getSource();
				
		if(eventSource == reset){
			this.reset();
		}
		
		if(eventSource == stop){
			this.reset();
			isDfs=false;
			isBfs = false;
			startNodeVal.setText("");
			endNodeVal.setText("");
		}
		
		if(isBfs==true){
			//Traverse the graph step by step.
			if(eventSource==nextButton){
				Node n;
				try {
					if((n = g.bfs())!=null){
						this.display(n);
					}
					//Traversal completed. Reset counters. 
					else {
						//Clear Nodes for next operation.
						g.clearNodes();
						//Reset counter
						isBfs =false;
						//Display the last node of traversal.
						endNodeVal.setText(g.getLast().getNodeNumber()+"");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			//Fast forward to reach the last.
			}else if(eventSource==endButton){
				
				Node n;
				while(true){
					try {
						if((n = g.bfs())!=null){
							this.display(n);
						}
						//Traversal completed. Reset counters. 
						else {
							//Clear Nodes for next operation.
							g.clearNodes();
							//Reset counter
							isBfs =false;
							//Display the last node of traversal.
							endNodeVal.setText(g.getLast().getNodeNumber()+"");
							break;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		else if(isDfs ==true){
			//Traverse the graph step by step.
			if(eventSource==nextButton){
				Node n;
				try {
					if((n = g.dfs())!=null){
						this.display(n);
					}
					//Traversal completed. Reset counters. 
					else {
						//Clear Nodes for next operation.
						g.clearNodes();
						//Reset counter
						isDfs =false;
						//Display the last node of traversal.
						endNodeVal.setText(g.getLast().getNodeNumber()+"");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			//Fast forward to reach the last.
			}else if(eventSource==endButton){
				
				Node n;
				while(true){
					try {
						if((n = g.dfs())!=null){
							this.display(n);
						}
						
						//Traversal completed. Reset counters. 
						else {
							//Clear Nodes for next operation.
							g.clearNodes();
							//Reset counter
							isDfs =false;
							//Display the last node of traversal.
							endNodeVal.setText(g.getLast().getNodeNumber()+"");
							break;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		else {
			
			if(eventSource==bfs){
				isBfs = true;
				try {
					g.initBfs();
					this.reset();
					startNodeVal.setText("");
					endNodeVal.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startNodeVal.setText(""+g.getRootNode().getNodeNumber());
			}
			else if(eventSource==dfs){
				isDfs = true;
				try {
					g.initDfs();
					this.reset();
					startNodeVal.setText("");
					endNodeVal.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				startNodeVal.setText(""+g.getRootNode().getNodeNumber());
			}
		}
	}
}

