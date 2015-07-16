package cluedoNetworkGUI;


import java.util.ArrayList;

import staticClasses.Config;
import staticClasses.NetworkMessages;
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
	
	public void setServer(){
		try {
			refreshGamesListServer(serverPool.get(0));
			
		} catch (Exception e) {
			auxx.loginfo("no server to refresh gamelist from");
		}
	}
	
	public void addChatMsgIn(String chatmsg, String ts,ServerItem server){
		if (server == getSelectedServer()) addMsgIn(ts+" : "+chatmsg);
		server.addChatMsg(chatmsg, ts);
	}
	
	public void addGameToServer(ServerItem server, int gameID, String nick,String color){
		CluedoGameClient newgame = 
				new CluedoGameClient(gameID,server);
		newgame.joinGame(color, nick);
		addGameToGui(gameID, nick,"",newgame.getGameState(),server.getGroupName(),server.getIpString());
		
		server.addGame(newgame);
	}
	
	public void addClient(ServerItem server , String nick){
		//System.out.println("added : "+server.getMyNick()+" - "+nick+" equals : "+ nick.equals(server.getMyNick()));
		if (!nick.equals(server.getMyNick()) && server.addClient(nick) && server == selectedServer){
			getGui().addClient(nick);
			System.out.println("added : "+server.getMyNick()+" - "+nick+" equals : "+ nick.equals(server.getMyNick()));	
		}
				
	}
	
	
	public void setClients(ServerItem server ,ArrayList<String> nicks){
		server.setClientNicks(nicks);
//		if (server == getSelectedServer())
//			//setNicksGui(server);
		
	}
	
	public void setSelectedServer(ServerItem selectedServer) {
		this.selectedServer = selectedServer;
		setStatus("selected server : "+selectedServer.getGroupName());
		setWindowName("logged in to server "+selectedServer.getGroupName()+" as "+selectedServer.getMyNick());
		cleanInput();
		setNicksGui(selectedServer);
		addMsgIn(selectedServer.getChat());
		auxx.logfine("logged in to server "+selectedServer.getGroupName()+" as "+selectedServer.getMyNick());
	}
	
	public ServerItem getSelectedServer() {
		return selectedServer;
	}
	
	public void removePlayerFromGameOnServer(int gameID,String nick,ServerItem server){
		CluedoGameClient game = server.getGameByGameID(gameID);
		game.removePlayer(nick);	
		if (server == selectedServer) refreshGamesListServer(server);
	}
	
	public boolean joinGameOnServer(ServerItem server,int gameID,String color,String nick){
		CluedoGameClient game = server.getGameByGameID(gameID);
		if (game.joinGame(color, nick)){
			if (game.getNumberConnected() >= Config.MIN_CLIENTS_FOR_GAMESTART) {
				setReadyGame(gameID);				
			}				
			updateGame(gameID, game.getNicksConnected(),game.getWatchersConnected());
			auxx.loginfo("connected to game "+gameID+" : number Nicks connected : "+game.getNumberConnected());

			return true;
		}
		return false;
	}
	
	public void startGameOnServer(ServerItem server,int gameID,String gameState, ArrayList <String> order){
		CluedoGameClient game = server.getGameByGameID(gameID);
		//game.setOrder(order);
		if (game.hasPlayerConnectedByNick(server.getMyNick()) || game.hasWatcherConnectedByNick(server.getMyNick()))
			game.start(order);
		game.setGameState(GameStates.getState(gameState));
		setRunningGame(gameID);
	}
	
	public boolean deleteGameOnServer(ServerItem server,int gameID){
		if (server.removeGameByID(gameID)){
			removeGameGui(gameID);
			return true;
		}
		
		return false;
	}
	
	public boolean removeClientFromSystemServer(ServerItem server,String nickID){
		if (server.removeClient(nickID) && server == selectedServer){
			getGui().removeClient(nickID);
		}		
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
		setSelectedServer(server);
	}
	
	public void setGameEndedOnServer(ServerItem server,int gameID){
		CluedoGameClient game = server.getGameByGameID(gameID);
		game.killCommunicator();
		game.setGameState(GameStates.ended);
		setGameEndedGui(gameID);
	}
	
	public void setGamesOnServer(ServerItem server, ArrayList<CluedoGameClient> glist){
		server.addGames(glist);
		addGamesToGui(glist);
	}
	
	public boolean addServer(ServerItem server,String status){
		if (serverPool.add(server)){
			addNetworkActorToGui(server.getGroupName(),server.getIpString(),status);
			//setSelectedServer(server);
			return true;
		}
		
		return false;
	}
	
	public boolean removeServer(ServerItem server){
		auxx.loginfo("trying to remove server "+server.getGroupName());
		killAllGamesOnServer(server);
		if (serverPool.remove(server)){
			removeNetworkActorFromGui(server.getGroupName(), server.getIpString());
			emptyGamesList();
			return true;
		}
		
		return false;
			
	}
	
	public void killAllGamesOnServer(ServerItem server){
		server.killAllGames();
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
	
	public void sayGoodbye(){
		leaveAllGames();
		serverPool.sendToAll(NetworkMessages.disconnectMsg());
	}
	
	public void addGamesToGui(ArrayList<CluedoGameClient> glist ){
		for (CluedoGameClient cg: glist){
			addGameToGui(cg.getGameId(), cg.getNicksConnected(),cg.getWatchersConnected(),cg.getGameState(),cg.getServer().getGroupName(),cg.getServer().getIpString());
		}
	}
	
	public void leaveAllGames(){
		ArrayList<CluedoGameClient> glist = serverPool.getGamesConnected();
		for (CluedoGameClient g: glist)
			auxx.sendTCPMsg(g.getServer().getSocket(), NetworkMessages.leave_gameMsg(g.getGameId()));
	}
	
	public void leaveGame(int gameID){
		selectedServer.getGameByGameID(gameID).kill();
	}
	
	public void setNicksGui(ServerItem server){
		getGui().setClientNicks(server.getClientNicks());
	}
	
	public void addWatcherToGame(ServerItem server,int gameID,String nick){
		CluedoGameClient game = server.getGameByGameID(gameID);
		game.addWatcher(nick);
		if (server == getSelectedServer()){
			updateGame(gameID, game.getNicksConnected(),game.getWatchersConnected());
		}
			refreshGamesListServer(server);
	};	
	
	public void joinGameAsWatcher(ServerItem server,int gameID){
		server.sendMsg(NetworkMessages.watch_gameMsg(gameID));
	}
	
	
	
	
}
