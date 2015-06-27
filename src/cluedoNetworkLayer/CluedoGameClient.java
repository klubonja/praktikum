package cluedoNetworkLayer;

import cluedoClient.ServerItem;

public class CluedoGameClient extends CluedoGame {
	
	ServerItem server;
	
	public CluedoGameClient(int gameId,ServerItem server) {
		super(gameId);
		this.server = server;
	}
	
	public ServerItem getServer() {
		return server;
	}
}
