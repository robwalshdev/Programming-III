// Robert Walsh 17333396

public class Chef extends Thread{
	private String name;
	
	private boolean completedOrders = true;
	private int numOfOrders = 0;
	private int numOfBurgers = 0;
	private int numOfPizza = 0;
	private int numOfFishNChips = 0;
	
	public Chef(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		while(completedOrders) { 
			completedOrders = Restaurant.stopThreads();
			try {
				String order = Restaurant.getOrderToCook(); 
				System.out.println("Chef " + name + " is preparing " + order);
				calculateStats(order);
				try {
					Thread.sleep((long) (1000*Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Restaurant.readyToServe(order);
			}
			catch(java.util.NoSuchElementException e) {}
		}
	}
	
	private void calculateStats(String order) {
		this.numOfOrders++;
		if(order.toLowerCase().contains("burger"))
			this.numOfBurgers++;
		else if(order.toLowerCase().contains("pizza"))
			this.numOfPizza++;
		else if(order.toLowerCase().contains("fish"))
			this.numOfFishNChips++;
	}
	
	@Override
	public String toString() {
		return "Chef " + this.name + " finished preparing " + this.numOfOrders
						+ " inlcuding " + this.numOfBurgers + " burgers, "
						+ this.numOfPizza + " pizzas and " + this.numOfFishNChips 
						+ " fish n chips";
	}
}
