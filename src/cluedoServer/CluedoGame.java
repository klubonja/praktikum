package cluedoServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CluedoGame {
	
	ExecutorService pool;	
	int size;
	String groupName;
	
	public CluedoGame(int poolSize,String groupName){
		size = poolSize;
		this.groupName = groupName;
		pool = Executors.newFixedThreadPool(poolSize);
	}
}
