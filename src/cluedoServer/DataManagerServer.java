package cluedoServer;

import java.util.ArrayList;

public class DataManagerServer {
	
	ClientPool clientPool;
	ArrayList<ClientItem> blackList;
	GameListServer gameList;
	public DataManagerServer() {
		clientPool = new ClientPool();
		blackList = new ArrayList<ClientItem>();
		gameList = new GameListServer();
	}
	
	public ArrayList<ClientItem> getBlackList() {
		return blackList;
	}
	public ClientPool getClientPool() {
		return clientPool;
	}
	public GameListServer getGameList() {
		return gameList;
	}
}
