package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javafx.scene.paint.Color;
import kommunikation.ServerGameModel;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
import cluedoClient.Client;
import cluedoServer.ClientItem;
import enums.GameStates;
import enums.JoinGameStatus;
import enums.PlayerStates;

public class CluedoGameServer extends CluedoGame {
	private ArrayList<ClientItem> participants;
	private ArrayList<ClientItem> watchers;
	private ServerGameModel gameLogic;
	
//	WinningStatement winningStatement;
//	int currentPlayerIndex = 0;

	public CluedoGameServer(int gameId) {
		super(gameId);
		setParticipants(new ArrayList<ClientItem>());
		watchers = new ArrayList<ClientItem>();
	}

	public WinningStatement getWinningStatement() {
		return gameLogic.getWinningStatement();
	}

	@Override
	public boolean start() {
		filterPlayers();
		
		//updatePlayerStates();		
		gameLogic = new ServerGameModel(this);
		gameLogic.start();
		notifyInit();
		notifyNextRound();
		
		setGameState(GameStates.started);
		return true;
	}
	
	public void filterPlayers(){
		players = getPlayersConnected();
	}
	
	public ArrayList<String> getWatchersNicks() {
		ArrayList<String> nicks = new ArrayList<String>();
		for (ClientItem c : watchers)
			nicks.add(c.getNick());

		return nicks;
	}
	
	public ArrayList<ClientItem> getWatchers() {
		return watchers;
	}
	
	public String getWatchersConnected(){
		StringBuffer nb = new StringBuffer();
		for (ClientItem p : watchers){
			if (!p.getNick().equals("")){
				nb.append(p.getNick()+", ");
			}
		}
			
		if (nb.length() > 2) nb.delete(nb.length()-2, nb.length()-1);
		return nb.toString();
	}

	public void notifyAll(String msg) {
		for (ClientItem c : getParticipants()) {
			auxx.sendTCPMsg(c.getSocket(), msg);
		}
	}

	public boolean hasPlayerNick(ClientItem client) {
		for (ClientItem c : getParticipants())
			if (c == client)
				return true;

		return false;
	}

	public boolean findAndRemovePlayer(ClientItem client) {
		for (ClientItem c : participants)
			if (c == client) {
				if (removePlayer(client.getNick())) {
					return participants.remove(client);
				}
			}
		return false;
	}
	
	public boolean findAndRemoveWatcher(ClientItem client) {
		for (ClientItem c : watchers)
			if (c == client) {
				return watchers.remove(client);
			}
		return false;
	}

	public JoinGameStatus joinGameServer(String color, ClientItem client) {
		if (getParticipants().contains(client))
			return JoinGameStatus.already_joined;
		if (getGameState() != GameStates.not_started)
			return JoinGameStatus.not_joinable;

		for (CluedoPlayer p : players) {
			if (p.getCluedoPerson().getColor().equals(color)) {
				if (p.getNick().equals("")) {
					if (getParticipants().add(client)) {
						p.setNick(client.getNick());
						client.setPlayer(p);
						return JoinGameStatus.added;
					}
					return JoinGameStatus.error;
				}
				return JoinGameStatus.nick_already_taken;
			}
		}

		return JoinGameStatus.game_not_found;
	}

	public boolean leaveGameServer(ClientItem client) {
		for (CluedoPlayer p : players) {
			if (p.getNick().equals(client.getNick())) {
				if (getParticipants().remove(client)) {
					p.setNick("");
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public CluedoPlayer getPlayerByClient(ClientItem client) {
		for (CluedoPlayer p : players) {
			if (client.getNick().equals(p.getNick()))
				return p;
		}

		return null;
	}

	public boolean addWatcher(ClientItem c) {
		if (!watchers.contains(c)){
			return watchers.add(c);
		}
		
		return false;	
	}

	public void notifyInit() {
		for (ClientItem client: getParticipants()){
			//client.sendMsg(NetworkMessages.game_startedMsg(getGameId(), getConnectedPlayersString()));
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(
					NetworkMessages.player_cardsMsg(
							getGameId(),
							p.getCards()
						)
				);
		}
	}
	
	//NICHT MEHR BENUTZEN
//	public void updatePlayerStates(){		
//		for (int i = 0;i < players.size(); i++){
//			if (i == currentPlayerIndex)	{
//				players.get(i).setCurrentState(PlayerStates.do_nothing); // hier werden possible moves von do nothing aus gesetzt
//			}
//			else{
//				players.get(i).setDoNothing(); // hier werden possible moves geleert und do nothing hinzugefügt
//			}			
//		}
//			
//	}

	public void notifyNextRound() {
		for (CluedoPlayer player : players){
			notifyAll(NetworkMessages.stateupdateMsg(getGameId(), 
					NetworkMessages.player_info(
							player.getNick(),
							player.getCluedoPerson().getColor(), 
							player.getStatesStringList()
					)
			));
		}
	}

//	public void setCurrentPlayerNext() {
//		currentPlayerIndex = (currentPlayerIndex + 1) % getParticipants().size();
//	}
	
//	public int getCurrentPlayerIndex() {
//		return currentPlayerIndex;
//	}

	public ArrayList<ClientItem> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<ClientItem> participants) {
		this.participants = participants;
	}
	
	public boolean hasClient(ClientItem client){
		return participants.contains(client);
	}	
	
	public boolean rollDice(ClientItem client){
		if (checkandHandleStateTrans(PlayerStates.roll_dice, client)){
			int[] diceres = gameLogic.rollDice();
			if (diceres != null){
				sendMsgToParticipants(NetworkMessages.dice_resultMsg(getGameId(),diceres));
				return true;
			};
		}
		return false;
	}
	
	public boolean endTurnRequest(ClientItem client){
		if (checkandHandleStateTrans(PlayerStates.end_turn, client)){
			gameLogic.endTurn();
			client.sendMsg(NetworkMessages.okMsg());
			return true;
		}
		return false;
	}
	
	public boolean movePlayer(ClientItem client, CluedoPosition newpos){
		if(checkandHandleStateTrans(PlayerStates.move, client)){
			if (gameLogic.movePlayer(client.getNick(), newpos)) {
				client.sendMsg(NetworkMessages.okMsg());
				 sendMsgToParticipants(NetworkMessages.movedMsg(getGameId(),getPlayerByClient(client).getCluedoPerson().getPersonName(), newpos));
				return true	;
			}
				
			client.sendMsg(NetworkMessages.error_Msg("move not legit"));
		}
		return false;
	}
	
	public void sendMsgToParticipants(String msg){
		ArrayList<ClientItem> clients = getParticipants();
		for (ClientItem client : clients)
			client.sendMsg(msg);
	}
	
	public boolean checkandHandleStateTrans(PlayerStates state, ClientItem client){
		CluedoPlayer player = getPlayerByClient(client);
		if (player.getPossibleStates().contains(state)) return true;
		client.sendMsg(NetworkMessages.error_Msg("you cant do \""+state.getName()+"\" at this point, idiot, read the manual :  "+player.getStatesAsString()));

		return false;
	}
}


