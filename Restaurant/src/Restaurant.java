import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

//Robert Walsh 17333396

public class Restaurant {
	private static final ReentrantLock chefLock = new ReentrantLock();
	private static final ReentrantLock serverLock = new ReentrantLock();
	
	private static Queue<String> toCook = new LinkedList<String>(); 
	private static Queue<String> toServe = new LinkedList<String>();
	
	private static Chef chef1 = new Chef("John");
	private static Chef chef2 = new Chef("Mark");
	private static Server server1 = new Server("Katie");
	private static Server server2 = new Server("Andrew");
	private static Server server3 = new Server("Emily");
	
	public static void main(String[] args) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader("orderList.txt");
			br = new BufferedReader(fr);
			String txt;
			while ((txt = br.readLine()) != null) {
				toCook.add(txt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chef1.start();
		chef2.start();
		server1.start();
		server2.start();
		server3.start();
		
		try {
			chef1.join();
			chef2.join();
			server1.join();
			server2.join();
			server3.join();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		// Stats of workers
		System.out.println(chef1);
		System.out.println(chef2);
		System.out.println(server1);
		System.out.println(server2);
		System.out.println(server3);
	}
	
	public static String getOrderToCook() {
		chefLock.lock();
		try {
			return toCook.remove();
		}
		finally {
			chefLock.unlock();
		}
	}
	
	public static String getOrderToServe() {
		serverLock.lock();
		try {
			return toServe.remove();
		}
		finally {
			serverLock.unlock();
		}
	}
	
	public static void readyToServe(String order) {
		toServe.add(order);
	}
	
	// Stop threads when no more orders
	public static boolean stopThreads() {
		if(toCook.size() == 0 && toCook.size() == 0)
			return false;
		return true;
	}
}
