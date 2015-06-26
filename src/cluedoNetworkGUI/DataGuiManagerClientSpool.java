package cluedoNetworkGUI;


import java.util.ArrayList;

import staticClasses.Config;
import staticClasses.auxx;
import cluedoClient.ServerItem;
import cluedoClient.ServerPool;
import cluedoNetworkLayer.CluedoGameClient;
import enums.GameStates;
import enums.ServerStatus;

public class DataGuiManagerClientSpool extends DataGuiManager{
	
	ServerPool serverPool;
	ServerItem selectedServer;
	
	public DataGuiManagerClientSpool(CluedoClientGUI gui,ServerPool spool) {
		super(gui);
		serverPool = spool;
	}
	
	public void addGameToServer(ServerItem server, int gameID, String nick,String color){
		CluedoGameClient newgame = 
				new CluedoGameClient(gameID,server);
		newgame.joinGame(color, nick);
		addGameToGui(gameID, "(created by "+nick+") Game "+gameID, nick,newgame.getGameState(),server.getGroupName(),server.getIpString());
		
		server.addGame(newgame);
	}
	
	public void setSelectedServer(ServerItem selectedServer) {
		this.selectedServer = selectedServer;
		setWindowName("selected : "+selectedServer.getGroupName());
	}
	
	public ServerItem getSelectedServer() {
		return selectedServer;
	}
	
	public boolean joinGameOnServer(ServerItem server,int gameID,String color,String nick){
		if (server.addPlayerByGameID(gameID, color, nick)){
			if (server.getGameByGameID(gameID).getNumberConnected() >= Config.MIN_CLIENTS_FOR_GAMESTART) {
				setReadyGame(gameID);
				
			}
			auxx.loginfo("connected to game "+gameID+" : "+server.getGameByGameID(gameID).getNumberConnected());
				
			updateGame(gameID, "(updated by "+nick+")",server.getGameByGameID(gameID).getNicksConnected());
			return true;
		}
		return false;
	}
	
	public void startGameOnServer(ServerItem server,int gameID,String gameState, ArrayList<String> order){
		CluedoGameClient game = server.getGameByGameID(gameID);
		if (game.start()){
			game.setGameState(GameStates.getState(gameState));
			game.setOrder(order);
			setRunningGame(gameID);
		}
	}
	
	public boolean deleteGameOnServer(ServerItem server,int gameID){
		if (server.removeGameByID(gameID)){
			removeGameGui(gameID);
			return true;
		}
		
		return false;
	}
	
	public boolean removeClientFromSystemServer(ServerItem server,String nickID){
	    if (server.removePlayerFromGames(nickID)){
			refreshGamesListServer(server);
			return true;
		}
		return false;
	}
	

	public void refreshGamesListServer(ServerItem server){
		emptyGamesList();
		addGamesToGui(server.getGameList());		
	}
	
	public void setServerLoggedIn(ServerItem server, ArrayList<CluedoGameClient> gameslist,String servername,String serverip,String status){
		setGamesOnServer(server,gameslist);		
		updateNetworkActorGui(servername,serverip,status);
		server.setStatus(ServerStatus.connected);
	}
	
	public void setGameEndedOnServer(ServerItem server,int gameID){
		server.getGameByGameID(gameID).setGameState(GameStates.ended);
		setGameEndedGui(gameID);
	}
	
	public void setGamesOnServer(ServerItem server, ArrayList<CluedoGameClient> glist){
		server.addGames(glist);
		addGamesToGui(glist);
	}
	
	public boolean addServer(ServerItem server,String status){
		if (serverPool.add(server)){
			addMsgIn("opened TCPSocket to "+server.getGroupName()+" on : "+ server.getIpString());
			addNetworkActorToGui(server.getGroupName(),server.getIpString(),status);
			setSelectedServer(server);
			return true;
		}
		
		return false;
	}
	
	public boolean removeServer(ServerItem server){
		auxx.loginfo("trying to remove server "+server.getGroupName());
		return serverPool.remove(server);
	}
	
	public ServerItem getServerByIndex(int index){
		return serverPool.get(index);
	}
	
	public ServerItem getServerByID(String name, String ip){
		for (ServerItem s: serverPool)
			if (s.getGroupName().equals(name) && s.getIpString().equals(ip))
				return s;
		return null;
	}
	
	@Override
	public CluedoClientGUI getGui() {
		return (CluedoClientGUI) super.getGui();
	}
	
	public void sayGoodbye(String msg){
		serverPool.sendToAll(msg);
	}
	
	public void addGamesToGui(ArrayList<CluedoGameClient> glist ){
		for (CluedoGameClient cg: glist){
			addGameToGui(cg.getGameId(), "Game "+cg.getGameId(), cg.getNicksConnected(),cg.getGameState(),cg.getServer().getGroupName(),cg.getServer().getIpString());
		}
	}
	
	
	
	
	
}
