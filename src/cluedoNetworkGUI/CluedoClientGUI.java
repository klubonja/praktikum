package cluedoNetworkGUI;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import staticClasses.Config;


 
public class CluedoClientGUI extends CluedoNetworkGUI{
	
	
	final public Button submitMessageButton;
	final public Button createGame;
	final public Button connectToTestServer;
	final public Button refreshGamesList;
	final public Button createServer;
	final public ObservableList<String> clientNicks;
	final public ListView<String> clientNicksView;
	
	private VBox chatArea;
	private VBox servers;
	private VBox clients;
	private VBox games;
	private Label chatTitle;
	private Label serversListTitle;
	private Label clientsListTitle;
	private Label gamesListTitle;
	
	private Text title;
	
	private Background background;
	private BackgroundSize backgroundSize;
	private BackgroundImage backgroundImage;
	private Image image;
	
	private Slider volume;
	private Label volumeLabel;
	private HBox volumeBox;
	
	private Pane promptArea;
	
	private Media clip;
	private MediaPlayer audio;

	
	
	
	 public CluedoClientGUI(Stage primaryStage){
		 
		 super(primaryStage);
		 submitMessageButton = new Button("Send");
		 createGame = new Button("Create Game");
		 createServer = new Button("Create Server");
		 connectToTestServer = new Button("TestServerConnection");
		 refreshGamesList = new Button("refreshGamesList");
		 clientNicks =  FXCollections.observableArrayList();
		 clientNicksView = new ListView<String>(clientNicks);
		 width = Config.CLIENT_WINDOW_WIDTH;
		 height = Config.CLIENT_WINDOW_HEIGHT;
		 
		 setStageWidth(width);
		 setStageHeight(height);
		 
		 setStartServiceButtonLabel("senddhandshake");
		 setStylesheet("cluedoNetworkGUI/networkStyle.css");
		 startUp();	 		 
	}
	    
	@Override
	public void startUp() {    
		
		this.backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
				false, false, false, true);
		this.image = new Image("http://img2.wikia.nocookie.net/__cb20120601223256/lotr/images/7/77/Isengard_army.jpg");
	    this.backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.CENTER, backgroundSize);
	    this.background = new Background(backgroundImage);
	    
	        
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setGridLinesVisible(false);
        grid.setBackground(background);
        
        
        //Creates the rows and columns of the grid
        ColumnConstraints col0 = new ColumnConstraints();
        //col0.setHgrow(Priority.ALWAYS);
        col0.setPercentWidth(30);
        grid.getColumnConstraints().add(col0);
        
        ColumnConstraints col1 = new ColumnConstraints();
        //col1.setHgrow(Priority.ALWAYS);
        col1.setPercentWidth(40);
        grid.getColumnConstraints().add(col1);
        
        ColumnConstraints col2 = new ColumnConstraints();
        //col2.setHgrow(Priority.ALWAYS);
        col2.setPercentWidth(30);
        grid.getColumnConstraints().add(col2);
        
        RowConstraints row0 = new RowConstraints(); //menue
	    row0.setPercentHeight(10); 
	    grid.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(40);        
	    grid.getRowConstraints().add(row1);
	    
	    RowConstraints row2 = new RowConstraints();
	    row1.setPercentHeight(40);        
	    grid.getRowConstraints().add(row2);
	    
	    RowConstraints row3 = new RowConstraints();
	    row3.setPercentHeight(10);        
	    grid.getRowConstraints().add(row3);
	    //End
        
        messagesIn.setWrapText(true);
        messagesOut.setWrapText(true);
     
//        Tab tab0 = new Tab();
//        tab0.setText("Spiele");           
//        tab0.setContent(gameListView);
//              
//        Tab tab1 = new Tab();
//        tab1.setText("Server");           
//        tab1.setContent(networkActorsListView);
//        
//        tabPane.getTabs().add(tab1);
//        tabPane.getTabs().add(tab0);  
        
        menue.getChildren().addAll(createGame, connectToTestServer, refreshGamesList, createServer);
        
        title = new Text(desc);
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.BOLD, 12));
        
        chatTitle = new Label();
        serversListTitle = new Label();
        clientsListTitle = new Label();
        gamesListTitle = new Label();
        
        chatTitle.setText("Chat");
        serversListTitle.setText("Server List");
        clientsListTitle.setText("Connected Clients");
        gamesListTitle.setText("Game List");
        
        volume = new Slider(0, 1, 0.5);
        volumeLabel = new Label("Volume");
        volumeBox = new HBox(4);
        volumeBox.setPrefWidth(50);
        volumeBox.getChildren().addAll(volumeLabel, volume);
        
        
        chatArea = new VBox(2);
        chatArea.getChildren().addAll(chatTitle ,messagesIn, inputField);
        
        servers = new VBox(2);
        servers.getChildren().addAll(serversListTitle, networkActorsListView);
        
        clients = new VBox(2);
        clients.getChildren().addAll(clientsListTitle, clientNicksView);
        
        games = new VBox(2);
        games.getChildren().addAll(gamesListTitle, gameListView);
        
        promptArea = new Pane();
        
        File file = new File("C:/Users/Kristi/Music/media.mp3");
        String MEDIA_URL = file.toURI().toString();
        clip = new Media(MEDIA_URL);
        audio = new MediaPlayer(clip);
        audio.play();
        
        GridPane.setConstraints(statusContainer, 1, 0);
         
        GridPane.setHalignment(menue, HPos.CENTER);
        GridPane.setValignment(menue, VPos.CENTER);
        GridPane.setConstraints(menue, 1, 3);
        
        GridPane.setHalignment(chatArea, HPos.CENTER);
        GridPane.setHalignment(chatArea, HPos.CENTER);
        GridPane.setConstraints(chatArea, 0, 2);
        
        GridPane.setHalignment(servers, HPos.CENTER);
        GridPane.setValignment(servers, VPos.CENTER);
        GridPane.setConstraints(servers, 0, 1);
        
        GridPane.setHalignment(clients, HPos.CENTER);
        GridPane.setHalignment(clients, HPos.CENTER);
        GridPane.setConstraints(clients, 2, 1);
        
        GridPane.setHalignment(games, HPos.CENTER);
        GridPane.setHalignment(games, HPos.CENTER);
        GridPane.setConstraints(games, 2, 2);
        
        GridPane.setConstraints(volumeBox, 2, 0);
        GridPane.setHalignment(volumeBox, HPos.RIGHT);
        GridPane.setValignment(volumeBox, VPos.CENTER);
        
        GridPane.setConstraints(promptArea, 1, 1);
        GridPane.setRowSpan(promptArea, 2);
        GridPane.setHalignment(promptArea, HPos.CENTER);
        GridPane.setValignment(promptArea, VPos.CENTER);
        
        grid.getChildren().addAll(statusContainer, menue, chatArea, servers, clients, games, volumeBox,
        		promptArea);
        
       
//      grid.add(node,				  col,row,colspan,rowspan)
//      grid.add(menue, 				1, 3, 	1, 		1);
//      grid.add(statusContainer,   	1, 0, 	1, 		1);
//	    grid.add(networkActorsListView, 0, 1, 	1, 		1);
//	    grid.add(clientNicksView,       2, 1, 	1, 		1);
//	    grid.add(gameListView, 			2, 2,	1, 		1);
//	    grid.add(chatArea, 			    0, 2,	1, 		1);
//	    grid.add(inLabel, 				1, 2, 	1, 		1);
//	    grid.add(messagesIn, 			1, 3, 	1, 		1);
//	    grid.add(outLabel, 				1, 4,   1,      1);
//	    grid.add(messagesOut, 			1, 5,   1,      1);	    
//	    grid.add(inputField, 			0, 3, 	2, 		1);
//	    grid.add(messagesIn,			1, 3, 	2, 		1);
       

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public String askForIp() {
    	Stage ipPrompt = new Stage();
    	IpPromptGrid ipr = new IpPromptGrid(ipPrompt);
		Scene secondary = new Scene(ipr,Config.IP_PROMPT_WINDOW_WIDTH,Config.IP_PROMPT_WINDOW_HEIGHT);		
		ipPrompt.setScene(secondary);
		ipPrompt.showAndWait();	
		
		return ipr.returnIp();
	}
    
    public String[] loginPrompt(String stageTitle){
    	Stage loginStage = new Stage();
    	LoginPrompt loginPrompt = new LoginPrompt(loginStage);
    	Scene secondary = new Scene(loginPrompt,Config.LOGIN_PROMPT_WINDOW_WIDTH,Config.LOGIN_PROMPT_WINDOW_HEIGHT);		
		loginStage.setScene(secondary);
		loginStage.setTitle(stageTitle);
		loginStage.showAndWait();	
		
		return loginPrompt.returnLoginData();
    }
     
    
    public String getUserMessage(){
    	String m = inputField.getText();
    	inputField.setText("");
    	return m;
    }  
   
	  
	public void clearMessages(){
		  messagesIn.setText("");
	}
	  
	public String selectColor(ArrayList<String> colors) {
		Stage selectNewColor = new Stage();
		IntroColorPrompt select = new IntroColorPrompt(selectNewColor, colors);
		//promptArea.getChildren().add(select);
	
	    Scene secondary = new Scene(select, Config.COLOR_SELECT_WINDOW_WIDTH, Config.COLOR_SELECT_WINDOW_HEIGHT);
	    selectNewColor.initStyle(StageStyle.UNDECORATED);
	    selectNewColor.setScene(secondary);
	    selectNewColor.showAndWait();
	    return select.returnColor();
	    
	    
	    
	}
	
	public void addClient(String nick){
		Platform.runLater(() -> {
			clientNicks.add(nick);
			System.out.println(nick+" single added");
		});
		
	}
	
	public void removeClient(String nick){
		Platform.runLater(() -> {
			clientNicks.remove(nick);
		});
		
	}
	
	public void emptyClientNicks(){
		Platform.runLater(() -> {
			clientNicks.clear();
		});	
	}
	
	public void setClientNicks(ArrayList<String> nicks){
		Platform.runLater(() -> {
			clientNicks.clear();
			for (String nick : nicks){
				addClient(nick);
			}				
		});	
	}

	public Background getBackground() {
		return background;
	}

	public void setBackground(Background background) {
		this.background = background;
	}

	public BackgroundSize getBackgroundSize() {
		return backgroundSize;
	}

	public void setBackgroundSize(BackgroundSize backgroundSize) {
		this.backgroundSize = backgroundSize;
	}

	public BackgroundImage getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(BackgroundImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Button getSubmitMessageButton() {
		return submitMessageButton;
	}

	public Button getCreateGame() {
		return createGame;
	}

	public Button getConnectToTestServer() {
		return connectToTestServer;
	}

	public Button getRefreshGamesList() {
		return refreshGamesList;
	}

	public Button getCreateServer() {
		return createServer;
	}

	public ObservableList<String> getClientNicks() {
		return clientNicks;
	}

	public ListView<String> getClientNicksView() {
		return clientNicksView;
	}

	public VBox getChatArea() {
		return chatArea;
	}

	public void setChatArea(VBox chatArea) {
		this.chatArea = chatArea;
	}

	public VBox getServers() {
		return servers;
	}

	public void setServers(VBox servers) {
		this.servers = servers;
	}

	public VBox getClients() {
		return clients;
	}

	public void setClients(VBox clients) {
		this.clients = clients;
	}

	public VBox getGames() {
		return games;
	}

	public void setGames(VBox games) {
		this.games = games;
	}

	public Label getChatTitle() {
		return chatTitle;
	}

	public void setChatTitle(Label chatTitle) {
		this.chatTitle = chatTitle;
	}

	public Label getServersListTitle() {
		return serversListTitle;
	}

	public void setServersListTitle(Label serversListTitle) {
		this.serversListTitle = serversListTitle;
	}

	public Label getClientsListTitle() {
		return clientsListTitle;
	}

	public void setClientsListTitle(Label clientsListTitle) {
		this.clientsListTitle = clientsListTitle;
	}

	public Label getGamesListTitle() {
		return gamesListTitle;
	}

	public void setGamesListTitle(Label gamesListTitle) {
		this.gamesListTitle = gamesListTitle;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public Slider getVolume() {
		return volume;
	}

	public void setVolume(Slider volume) {
		this.volume = volume;
	}

	public Label getVolumeLabel() {
		return volumeLabel;
	}

	public void setVolumeLabel(Label volumeLabel) {
		this.volumeLabel = volumeLabel;
	}

	public HBox getVolumeBox() {
		return volumeBox;
	}

	public void setVolumeBox(HBox volumeBox) {
		this.volumeBox = volumeBox;
	}

	public Pane getPromptArea() {
		return promptArea;
	}

	public void setPromptArea(Pane promptArea) {
		this.promptArea = promptArea;
	}

	public Media getClip() {
		return clip;
	}

	public void setClip(Media clip) {
		this.clip = clip;
	}

	public MediaPlayer getAudio() {
		return audio;
	}

	public void setAudio(MediaPlayer audio) {
		this.audio = audio;
	}
}