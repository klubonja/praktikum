package view;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import cluedoClient.Client;
import cluedoNetworkGUI.CluedoClientGUI;
import cluedoNetworkGUI.CluedoClientPresenter;
import cluedoNetworkGUI.CluedoServerGUI;
import cluedoServer.Server;


public class MainMenuPresenter {

	private MainMenuView view;
	
	private CluedoServerGUI ServerGUI;
	private CluedoClientGUI ClientGUI;
	
	private Server server;
	private Client client;
	
	public final String clickedButtonStyle = "-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; " 
			+ "-fx-background-radius: 8; -fx-background-color: "
			+ "linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),  #9d4024, #d86e3a,"
			+ "radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);"
			+ "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,1) , 10,0,1,1 );"
			+ "-fx-font-weight: bold; -fx-font-size: 1.1em;"
			+ "-fx-font-family: Arial; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 15px;";
	
	private final String clButtonStyle = "-fx-padding: 8 15 15 15; -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0; " 
			+ "-fx-background-radius: 8; -fx-background-color: "
			+ "linear-gradient(from 0% 93% to 0% 100%, #228b22 0%, #008000 100%),  #228b22, #008000,"
			+ "radial-gradient(center 50% 50%, radius 100%, #32cd32, #2e8b57);"
			+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
			+ "-fx-font-weight: bold; -fx-font-size: 1.1em;  font-stretch: ultra-expanded; "
			+ "-fx-font-family: Helvetica; -fx-text-fill: linear-gradient(white, #d0d0d0); -fx-font-size: 14px;";
	

	public MainMenuPresenter(MainMenuView view){
		
		this.view = view;
		
		activateEvents();
		bindComponents();
		
		}
	
	public void activateEvents(){
		
		
		view.getCreateClient().setOnMouseClicked(e -> createClient());
		view.getCreateServer().setOnMouseClicked(e -> createServer());
		view.getQuit().setOnMouseClicked(e -> quitGame());
		
		view.getCreateClient().setOnMouseEntered(e -> addGlow(view.getCreateClient()));
		view.getCreateClient().setOnMouseExited(e -> removeGlow(view.getCreateClient()));
		view.getCreateServer().setOnMouseEntered(e -> addGlow(view.getCreateServer()));
		view.getCreateServer().setOnMouseExited(e -> removeGlow(view.getCreateServer()));
		view.getQuit().setOnMouseEntered(e -> addGlow(view.getQuit()));
		view.getQuit().setOnMouseExited(e -> removeGlow(view.getQuit()));
	}
	
public void bindComponents(){
		
		view.getBackgroundVideo().volumeProperty().bind(view.getVolume().valueProperty());

	}
	
	@SuppressWarnings("unused")
	public void createClient(){
		
			Stage stage = new Stage();
			ClientGUI = new CluedoClientGUI(stage);
			client = new Client(ClientGUI);
			CluedoClientPresenter presenter = new CluedoClientPresenter(ClientGUI,client);
			stage.show();
			stage.setFullScreen(false);
			view.getBackgroundVideo().stop();
			view.close();
		}
	
	public void createServer(){
		
		Stage stage = new Stage();
		ServerGUI = new CluedoServerGUI(stage);
		server = new Server(ServerGUI);
		stage.show();
		stage.hide();
	}
	
	public void addGlow(Button b){
		
		b.setStyle(clButtonStyle);
	}
	
	public void removeGlow(Button b){
		
		b.setStyle(view.getButtonStyle());
	}
	
	public void quitGame(){
		
		Platform.exit();
		System.exit(0);
	}
		

	//Getters and Setter
	public MainMenuView getView() {
		return view;
	}

	public void setView(MainMenuView view) {
		this.view = view;
	}


	public CluedoServerGUI getServerGUI() {
		return ServerGUI;
	}

	public void setServerGUI(CluedoServerGUI serverGUI) {
		ServerGUI = serverGUI;
	}

	public CluedoClientGUI getClientGUI() {
		return ClientGUI;
	}

	public void setClientGUI(CluedoClientGUI clientGUI) {
		ClientGUI = clientGUI;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


}
