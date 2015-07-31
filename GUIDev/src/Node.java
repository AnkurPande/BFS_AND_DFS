

/**
 * @author Ankur Pandey
 *
 */
public class Node {
	
	
	private int nodeNumber ;
	private String cityName =null;
	private int population ;
	private float elevation ;
	private boolean visited =false;
		
	public Node(int n, String city, int population, float elevation) {
		this.nodeNumber = n;
		this.cityName = city;
		this.population = population;
		this.elevation = elevation;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public int getNodeNumber() {
		return nodeNumber;
	}
	
	public String getCityName() {
		return cityName;
		
	}
	public int getPopulation() {
		return population;
	}
	
	public float getElevation() {
		return elevation;
	}
	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public void setElevation(float elevation) {
		this.elevation = elevation;
	}
	
	
	
}
