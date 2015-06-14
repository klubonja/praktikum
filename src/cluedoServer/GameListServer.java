package cluedoServer;

import java.util.ArrayList;

import cluedoNetworkLayer.CluedoGameServer;

public class GameListServer extends ArrayList<CluedoGameServer> {
	
	public GameListServer() {
		super();
	}
	
	public boolean joinGame(int gameID, String color, String nick){
		for (CluedoGameServer cg : this)
			if (gameID == cg.getGameId()){
				cg.joinGame(color, nick);
				return true;
			}
		return false;
	}
	
	public boolean leaveGame(int gameID, String nick){
		for (CluedoGameServer cg : this)
			if (gameID == cg.getGameId()){
				
				return true;
			}
		return false;
	}
	
	public CluedoGameServer getGameByGameID(int gameID){
		for (CluedoGameServer cg : this)
			if (gameID == cg.getGameId()){
				
				return cg;
			}
		return null;
	}
}
