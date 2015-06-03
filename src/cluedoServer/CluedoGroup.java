package cluedoServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CluedoGroup {
	
	ExecutorService pool;	
	int size;
	String groupName;
	
	public CluedoGroup(int poolSize,String groupName){
		size = poolSize;
		this.groupName = groupName;
		pool = Executors.newFixedThreadPool(poolSize);
	}
	
	public String getName(){
		return groupName;
	}
}
