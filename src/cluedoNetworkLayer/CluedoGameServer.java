package cluedoNetworkLayer;

import java.util.ArrayList;
import java.util.Iterator;

import kommunikation.ServerGameModel;
import staticClasses.NetworkMessages;
import staticClasses.auxx;
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

	public CluedoStatement getWinningStatement() {
		return gameLogic.getWinningStatement();
	}

	@Override
	public boolean start() {
		filterPlayers();
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
	
	//////////////////////////////////////////////SETTER///////////////////////////////////////////////////////
	
	public void setParticipants(ArrayList<ClientItem> participants) {
		this.participants = participants;
	}
	
//////////////////////////////////////////////GETTER///////////////////////////////////////////////////////
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
		return auxx.formatStringList(getWatchersNicks(), "and");
	}
	
	public CluedoPlayer getPlayerByClient(ClientItem client) {
		for (CluedoPlayer p : players) {
			if (client.getNick().equals(p.getNick()))
				return p;
		}

		return null;
	}
	
	public ArrayList<ClientItem> getParticipants() {
		return participants;
	}
//////////////////////////////////////////////HASER///////////////////////////////////////////////////////
	
	public boolean hasPlayerNick(ClientItem client) {
		for (ClientItem c : getParticipants())
			if (c == client)
				return true;

		return false;
	}
	
	public boolean hasClient(ClientItem client){
		return participants.contains(client);
	}	
	
	@Override
	public boolean hasPlayerConnectedByNick(String nick){
		for (CluedoPlayer p: players)
			if (p.getNick().equals(nick)) return true;
		
		return false;	
	}
	
	public boolean hasPlayerConnectedByClient(ClientItem client){
		return participants.contains(client);
	}
	
	public boolean hasWatcherConnectedByClient(ClientItem client){
		return watchers.contains(client);
	}
	
	@Override
	public boolean hasWatcherConnectedByNick(String nick) {
		for (ClientItem w: watchers)
			if (w.getNick().equals(nick)) return true;
		return false;
	}
	
//////////////////////////////////////////////SENDMSGS///////////////////////////////////////////////////////
	
	public void sendMsgsToAll(String msg){
		ArrayList<ClientItem> players = getParticipants();
		for (ClientItem player : players)
			player.sendMsg(msg);
		ArrayList<ClientItem> watchers = getWatchers();
		for (ClientItem watcher : watchers)
			watcher.sendMsg(msg);
	}
	
	public void notifyInit() {
		for (ClientItem client: getParticipants()){
			CluedoPlayer p = getPlayerByClient(client);
			client.sendMsg(
					NetworkMessages.player_cardsMsg(
							getGameId(),
							p.getCards()
						)
				);
		}
	}

	public void notifyNextRound() {
		for (CluedoPlayer player : players){
			sendMsgsToAll(NetworkMessages.stateupdateMsg(getGameId(), 
					NetworkMessages.player_info(
							player.getNick(),
							player.getCluedoPerson().getColor(), 
							player.getStatesStringList()
					)
			));
		}
	}
	
	public void sendStateUpdateMsg(CluedoPlayer player){
		sendMsgsToAll(
				NetworkMessages.stateupdateMsg(
						getGameId(),NetworkMessages.player_info(
								player.getNick(), player.getCluedoPerson().getColor(), player.getStatesStringList()
								)
						)
				);
	}
	
	public void sendMsgByPlayer(CluedoPlayer cp,String msg){
		for (ClientItem c: participants)
			if (cp.getNick().equals(c.getNick())){
				c.sendMsg(msg);
				return;
			}
	}
	
	public void sendPoolcardsByPlayer(CluedoPlayer cp,ArrayList<String> cards){
		for (ClientItem c: participants)
			if (cp.getNick().equals(c.getNick())){
				c.sendMsg(NetworkMessages.pool_cardsMsg(getGameId(), cards));
				return;
			}
	}

//////////////////////////////////////////////RUNNING GAME///////////////////////////////////////////////////////
	
	public boolean rollDice(ClientItem client){
		if (checkandHandleStateTrans(PlayerStates.roll_dice, client)){
			int[] diceres = gameLogic.rollDice();
			if (diceres != null){
				client.sendMsg(NetworkMessages.okMsg());
				sendMsgsToAll(NetworkMessages.dice_resultMsg(getGameId(),diceres));
				sendStateUpdateMsg(getPlayerByClient(client));
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
	
	public boolean movePlayerRequest(ClientItem client, CluedoPosition newpos){
		if(checkandHandleStateTrans(PlayerStates.move, client)){
			if (gameLogic.movePlayer(client.getNick(), newpos)) {
				 client.sendMsg(NetworkMessages.okMsg());
				 sendMsgsToAll(NetworkMessages.movedMsg(getGameId(),getPlayerByClient(client).getCluedoPerson().getColor(), newpos));
				 sendStateUpdateMsg(getPlayerByClient(client));

				return true	;
			}
			client.sendMsg(NetworkMessages.error_Msg("move not legit"));
		}
		return false;
	}
	
	public boolean useSecretPassageRequest(ClientItem client) {
		if(checkandHandleStateTrans(PlayerStates.use_secret_passage, client)){
			CluedoPosition position = gameLogic.useSecretPassage(client.getNick());
			if (position != null) {
				 client.sendMsg(NetworkMessages.okMsg());
				 
				 sendMsgsToAll(NetworkMessages.movedMsg(getGameId(),getPlayerByClient(client).getCluedoPerson().getColor(), position));
				 sendStateUpdateMsg(getPlayerByClient(client));

				return true	;
			}
			client.sendMsg(NetworkMessages.error_Msg("move not legit"));
		}
		return false;
	}
	
	
	public boolean accuseRequest(CluedoStatement accusation,ClientItem client) {
		if(checkandHandleStateTrans(PlayerStates.accuse, client)){
			gameLogic.accuse(accusation,client.getNick());
			return true;
		}
		
		return false;
	}
	
	public boolean suspect(CluedoStatement statement, ClientItem client) {
		if(checkandHandleStateTrans(PlayerStates.suspect, client)){
			sendMsgsToAll(
					NetworkMessages.suspicionMsg(
							getGameId(), 
							NetworkMessages.statement(
									statement.getPerson().getColor(), 
									statement.getRoom().getName(), 
									statement.getWeapon().getName()
							)
					)
			);
			gameLogic.suspect(statement);
			return true;			
		}
		return false;		
	}
	
	public void disproveRequest(String card, ClientItem client) {
		if(checkandHandleStateTrans(PlayerStates.disprove, client)){		
			if (!gameLogic.disprove(card,client.getNick())){
				client.sendMsg(NetworkMessages.error_Msg("yourea damn liar, thats a no disprove, and youre an idiot"));
			}				
		}		
	}
	
//////////////////////////////////////////////FIND///////////////////////////////////////////////////////
	
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
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public JoinGameStatus joinGameServer(String color, ClientItem client) {
		if (hasWatcherConnectedByClient(client))
			return JoinGameStatus.already_watching;
		if (hasPlayerConnectedByClient(client))
			return JoinGameStatus.already_joined;
		if (getGameState() != GameStates.not_started)
			return JoinGameStatus.not_joinable;

		for (CluedoPlayer p : players) {
			if (p.getCluedoPerson().getColor().equals(color)) {
				if (p.getNick().equals("")) {
					if (getParticipants().add(client)) {
						p.setNick(client.getNick());
						return JoinGameStatus.added;
					}
					return JoinGameStatus.error;
				}
				return JoinGameStatus.color_already_taken;
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

	

	public boolean addWatcher(ClientItem c) {
		if (!hasPlayerNick(c)){
			if (!hasWatcherConnectedByNick(c.getNick())){
				return watchers.add(c);	
			}
			c.sendMsg(NetworkMessages.error_Msg("you are already watching game "+getGameId()));
		}
		c.sendMsg(NetworkMessages.error_Msg("you damn cheater, you are already a player of game "+getGameId()));
		
		
		return false;	
	}

	public boolean checkandHandleStateTrans(PlayerStates state, ClientItem client){
		CluedoPlayer player = getPlayerByClient(client);
		if (player.getPossibleStates().contains(state)) return true;
		client.sendMsg(NetworkMessages.error_Msg("you cant do \""+state.getName()+"\" at this point, idiot, read the manual :  "+player.getStatesAsStringFormatted()));

		return false;
	}

	public boolean removeWatcher(ClientItem client){
		Iterator<ClientItem> iter = watchers.iterator();
		while (iter.hasNext()){
			ClientItem c = iter.next();
			if (c == client){
				iter.remove();
				return true;
			}
		}
		return false;
	}

	
}


