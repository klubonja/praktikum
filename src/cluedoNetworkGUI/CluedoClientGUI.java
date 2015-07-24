package cluedoNetworkGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import staticClasses.Config;
import staticClasses.HowToPlay;


 
public class CluedoClientGUI extends CluedoNetworkGUI{
	
	
	final public Button submitMessageButton;
	final public Button createGame;
	final public Button connectToTestServer;
	final public Button refreshGamesList;
	final public Button createServer;
	private Button exitButton;
	final public ObservableList<String> clientNicks;
	final public ListView<String> clientNicksView;
	
	private HBox buttons;
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
	
	final private Image backIsengard = new Image("http://img2.wikia.nocookie.net/__cb20120601223256/lotr/images/7/77/Isengard_army.jpg");
	final private Image backMinas = new Image("http://img4.wikia.nocookie.net/__cb20141228214636/lotr/images/e/e4/Minas_Tirith.jpg");
	final private Image backRivendell = new Image("http://hdwallpapersfit.com/wp-content/uploads/2015/03/rivendell-wallpaper-new.jpg");
	final private Image backMordor = new Image("http://img4.wikia.nocookie.net/__cb20140520211519/middleearthshadowofmordor7723/images/5/50/Yre1o.jpg");
	final private Image backArgonath  = new Image("http://img4.wikia.nocookie.net/__cb20140520211519/middleearthshadowofmordor7723/images/5/50/Yre1o.jpg");
	
	private Background buttonBackground;
	
	private Slider volume;
	private Label volumeLabel;
	private HBox volumeBox;
	private Button mute;
	private final Image muteGraphic = new Image("http://png-3.findicons.com/files/icons/2711/free_icons_for_windows8_metro/512/mute.png"); 
	private final Image unmuteGraphic = new Image("http://www.veryicon.com/icon/png/System/Icons8%20Metro%20Style/Volume%20Controls%20Volume%20up.png"); 
	
	private final File audioFile = new File("media/clientAudio1.mp3");
	
	private GridPane promptArea;
	private GridPane infoArea;
	private Button closePromptArea;
	
	private Media clip;
	private MediaPlayer audio;
	
	private MenuBar menuBar;
	private Menu file;
	private Menu help;
	private Menu window;
	private MenuItem options;
	private MenuItem createGameMenu;
	private MenuItem createServerMenu;
	private MenuItem testServer;
	private MenuItem exit;
	private MenuItem fullScreen;
	private MenuItem gameRules;
	

	 public CluedoClientGUI(Stage primaryStage){
		 
		 super(primaryStage);
		 submitMessageButton = new Button("Send");
		 createGame = new Button("Create Game");
		 createServer = new Button("Create Server");
		 connectToTestServer = new Button("Test LMU Server");
		 refreshGamesList = new Button("Refresh Games List");
		 clientNicks =  FXCollections.observableArrayList();
		 clientNicksView = new ListView<String>(clientNicks);
		 width = Config.CLIENT_WINDOW_WIDTH;
		 height = Config.CLIENT_WINDOW_HEIGHT;
		 
		 setStageWidth(width);
		 setStageHeight(height);
		 
		 setStartServiceButtonLabel("senddhandshake");
		 setStylesheet("cluedoNetworkGUI/networkStyle.css");
		 
		 setMenuBar();
		 setPromptArea();
		 setOptionsArea();
		 
		 startUp();	 		 
	}
	    
	@Override
	public void startUp() {    
		
		//Formats the background of the Client Screen
		backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
				false, false, false, true);
		backgroundImage = new BackgroundImage(backIsengard, BackgroundRepeat.NO_REPEAT , 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
	    background = new Background(backgroundImage);

		//assignBackground();
	       
	    //Formats the Grid
        grid.setPadding(new Insets(0, 0, 20, 0));
        grid.setHgap(20);
        grid.setVgap(10);
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
        
        RowConstraints menuRow = new RowConstraints();
	    menuRow.setPercentHeight(4); 
	    grid.getRowConstraints().add(menuRow);
	    
        RowConstraints row0 = new RowConstraints();
	    row0.setPercentHeight(8); 
	    grid.getRowConstraints().add(row0);
	    
	    RowConstraints row1 = new RowConstraints();
	    row1.setPercentHeight(38);        
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
        
        
        //Formats the background of the buttons
        Stop white = new Stop(0,Color.SNOW);
//		Stop transparent = new Stop(1, Color.TRANSPARENT);
		Stop[] stops = new Stop[] { white};
		LinearGradient lg1 = new LinearGradient(0, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops);
		
		BackgroundFill buttonBackgroundFill = new BackgroundFill(lg1 , new CornerRadii(2) , new Insets(1));
		buttonBackground = new Background(buttonBackgroundFill);
        
		
		//Creates the buttons
        buttons = new HBox(0.5);
        buttons.setMaxWidth(800);
        
        
        createGame.setPrefSize(200, 50);
        createGame.setMaxSize(200, 50);
        createGame.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 13));
		createGame.setTextFill(Color.DARKSLATEGREY);
        createGame.setBackground(buttonBackground);
        
        connectToTestServer.setPrefSize(200, 50);
        connectToTestServer.setMaxSize(200, 50);
        connectToTestServer.setBackground(buttonBackground);
        connectToTestServer.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 13));
        connectToTestServer.setTextFill(Color.DARKSLATEGREY);
        
        refreshGamesList.setPrefSize(200, 50);
        refreshGamesList.setMaxSize(200, 50);
        refreshGamesList.setBackground(buttonBackground);
        refreshGamesList.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 13));
        refreshGamesList.setTextFill(Color.DARKSLATEGREY);
        
        createServer.setPrefSize(200, 50);
        createServer.setMaxSize(200, 50);
        createServer.setBackground(buttonBackground);
        createServer.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 13));
        createServer.setTextFill(Color.DARKSLATEGREY);
        
        exitButton = new Button("Exit");
        exitButton.setPrefSize(200, 50);
        exitButton.setMaxSize(200, 50);
        exitButton.setBackground(buttonBackground);
        exitButton.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 13));
        exitButton.setTextFill(Color.DARKSLATEGREY);
        
        buttons.getChildren().addAll(createGame, createServer, refreshGamesList, exitButton);
        //buttons.getChildren().add(connectToTestServer);
        buttons.setPadding(new Insets(0, 20, 0, 20));
        
        title = new Text(desc);
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        status.setFont(Font.font("Tahoma", FontWeight.BOLD, 13));
        
        //Creates labels for each TextArea and formats them
        chatTitle = new Label();
        serversListTitle = new Label();
        clientsListTitle = new Label();
        gamesListTitle = new Label();
        
        chatTitle.setText("Chat");
        chatTitle.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
        chatTitle.setTextFill(Color.DARKSLATEGREY);
        serversListTitle.setText("Server List");
        serversListTitle.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
        serversListTitle.setTextFill(Color.DARKSLATEGREY);
        clientsListTitle.setText("Connected Clients");
        clientsListTitle.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
        clientsListTitle.setTextFill(Color.DARKSLATEGREY);
        gamesListTitle.setText("Game List");
        gamesListTitle.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
        gamesListTitle.setTextFill(Color.DARKSLATEGREY);
        
        //Creates the Volume Slider
        volume = new Slider(0, 1, 0.5);
        volume.setOpacity(0.9);
        volumeLabel = new Label("Volume");
        volumeLabel.setFont(Font.font("Tahoma", FontWeight.SEMI_BOLD, 12));
        volumeLabel.setTextFill(Color.WHITESMOKE);
        volumeLabel.setAlignment(Pos.CENTER);
        mute = new Button();
        mute.setBackground(buttonBackground);
        ImageView muteImage = new ImageView(unmuteGraphic);
        muteImage.setFitHeight(20);
        muteImage.setFitWidth(20);
        mute.setGraphic(muteImage);
        mute.setMaxSize(20, 20);
        mute.setAlignment(Pos.CENTER);
        volumeBox = new HBox(5);
        volumeBox.setPrefWidth(50);
        volumeBox.setMaxWidth(200);
        volumeBox.getChildren().addAll(volumeLabel, volume , mute);
        volumeBox.setPadding(new Insets(0, 20 , 0 , 0));
        
        //Different shadow for the different TextAreas in the grid
        InnerShadow innerShadow1 = new InnerShadow();
		innerShadow1.setOffsetX(1.5);
		innerShadow1.setOffsetY(1.5);
		innerShadow1.setWidth(15);
		innerShadow1.setHeight(15);
		innerShadow1.setColor(Color.DARKSLATEGREY);
		
		InnerShadow innerShadow2 = new InnerShadow();
		innerShadow2.setOffsetX(1.5);
		innerShadow2.setOffsetY(0);
		innerShadow2.setWidth(15);
		innerShadow2.setHeight(15);
		innerShadow2.setColor(Color.DARKSLATEGREY);
		
		InnerShadow innerShadow3 = new InnerShadow();
		innerShadow3.setOffsetX(1.5);
		innerShadow3.setOffsetY(-1);
		innerShadow3.setWidth(15);
		innerShadow3.setHeight(15);
		innerShadow3.setColor(Color.DARKSLATEGREY);
		
		InnerShadow innerShadow4 = new InnerShadow();
		innerShadow4.setOffsetX(-1.5);
		innerShadow4.setOffsetY(1.5);
		innerShadow4.setWidth(15);
		innerShadow4.setHeight(15);
		innerShadow4.setColor(Color.DARKSLATEGREY);
		
		InnerShadow innerShadow5 = new InnerShadow();
		innerShadow5.setOffsetX(-1.5);
		innerShadow5.setOffsetY(-1.5);
		innerShadow5.setWidth(15);
		innerShadow5.setHeight(15);
		innerShadow5.setColor(Color.DARKSLATEGREY);
		
		InnerShadow innerShadow6 = new InnerShadow();
		innerShadow6.setOffsetX(0);
		innerShadow6.setOffsetY(0);
		innerShadow6.setWidth(15);
		innerShadow6.setHeight(15);
		innerShadow6.setColor(Color.DARKSLATEGREY);
		 
		
		//Packing the elements in different VBoxes with the corresponding labels
        chatArea = new VBox(2);
        chatArea.getChildren().addAll(chatTitle ,messagesIn, inputField);
        chatArea.setPadding(new Insets(0, 0 , 0 , 20));
        messagesIn.setEffect(innerShadow2);
        inputField.setEffect(innerShadow3);
        messagesIn.setOpacity(0.9);
        inputField.setOpacity(0.9);
        
        servers = new VBox(2);
        servers.getChildren().addAll(serversListTitle, networkActorsListView);
        servers.setPadding(new Insets(0, 0 , 0 , 20));
        networkActorsListView.setEffect(innerShadow1);
        networkActorsListView.setOpacity(0.9);
        
        clients = new VBox(2);
        clients.getChildren().addAll(clientsListTitle, clientNicksView);
        clients.setPadding(new Insets(0, 20 , 0 , 0));
        clientNicksView.setEffect(innerShadow4);
        clientNicksView.setOpacity(0.9);
        
        games = new VBox(2);
        games.getChildren().addAll(gamesListTitle, gameListView);
        games.setPadding(new Insets(0, 20 , 0 , 0));
        gameListView.setEffect(innerShadow5);
        gameListView.setOpacity(0.9);
        
        
//        //Setup the area in the middle of the screen
//        promptArea = new GridPane();
//        promptArea.setPadding(new Insets(20));
// 
//        ColumnConstraints prompt0 = new ColumnConstraints();
//        prompt0.setPercentWidth(100);
//        promptArea.getColumnConstraints().add(prompt0);
//        
//        RowConstraints prompt1 = new RowConstraints();
//	    prompt1.setPercentHeight(50); 
//	    promptArea.getRowConstraints().add(prompt1);
//	    
//	    RowConstraints prompt2 = new RowConstraints();
//	    prompt2.setPercentHeight(50);        
//	    promptArea.getRowConstraints().add(prompt2);
//        
//        infoArea = new GridPane();
//        infoArea.setPadding(new Insets(2.5));
//        infoArea.setVgap(5);
//        
//        ColumnConstraints info0 = new ColumnConstraints();
//        info0.setPercentWidth(100);
//        promptArea.getColumnConstraints().add(info0);
//        
//        RowConstraints info1 = new RowConstraints();
//	    info1.setPercentHeight(90); 
//	    promptArea.getRowConstraints().add(info1);
//	    
//	    RowConstraints info2 = new RowConstraints();
//	    info2.setPercentHeight(10);        
//	    promptArea.getRowConstraints().add(info2);
//        
//        
//        TextArea rules = new TextArea();
//        rules.setPrefHeight(350);
//        rules.setMaxHeight(350);
//        rules.setWrapText(true);
//        rules.setEditable(false);
//        rules.setOpacity(0.9);
//        rules.setEffect(innerShadow6);
//        rules.setText(HowToPlay.GOAL + HowToPlay.PLAY);
//        
//        GridPane.setConstraints(rules, 0, 0);
//        GridPane.setValignment(rules, VPos.CENTER);
//        GridPane.setHalignment(rules, HPos.CENTER);
//        
//        infoArea.getChildren().add(rules);
//        
//        GridPane.setConstraints(infoArea, 0, 0);
//        GridPane.setRowSpan(infoArea, 2);
//        GridPane.setValignment(infoArea, VPos.CENTER);
//        GridPane.setHalignment(infoArea, HPos.CENTER);
//        //promptArea.getChildren().add(infoArea);
//        promptArea.setGridLinesVisible(false);
        
        
//        String url = audioFile.toURI().toString();
//        clip = new //Media(url);
//        Media("file:////home/g/guldener/git/yinyanyolos/src/media/clientAudio1.mp3");
//        audio = new MediaPlayer(clip);
//        audio.play();
//        audio.setCycleCount(MediaPlayer.INDEFINITE);
        
        
        GridPane.setConstraints(statusContainer, 1, 1);
        GridPane.setHalignment(statusContainer, HPos.CENTER);
        GridPane.setValignment(statusContainer, VPos.BOTTOM);
         
        GridPane.setHalignment(buttons, HPos.CENTER);
        GridPane.setColumnSpan(buttons, 3);
        GridPane.setValignment(buttons, VPos.BOTTOM);
        GridPane.setConstraints(buttons, 0, 4);
        
        GridPane.setHalignment(chatArea, HPos.CENTER);
        GridPane.setHalignment(chatArea, HPos.CENTER);
        GridPane.setConstraints(chatArea, 0, 3);
        
        GridPane.setHalignment(servers, HPos.CENTER);
        GridPane.setValignment(servers, VPos.CENTER);
        GridPane.setConstraints(servers, 0, 2);
        
        GridPane.setHalignment(clients, HPos.CENTER);
        GridPane.setHalignment(clients, HPos.CENTER);
        GridPane.setConstraints(clients, 2, 2);
        
        GridPane.setHalignment(games, HPos.CENTER);
        GridPane.setHalignment(games, HPos.CENTER);
        GridPane.setConstraints(games, 2, 3);
        
        GridPane.setConstraints(volumeBox, 2, 1);
        GridPane.setHalignment(volumeBox, HPos.RIGHT);
        GridPane.setValignment(volumeBox, VPos.CENTER);
        
        GridPane.setConstraints(promptArea, 1, 2);
        GridPane.setRowSpan(promptArea, 2);
        GridPane.setHalignment(promptArea, HPos.CENTER);
        GridPane.setValignment(promptArea, VPos.CENTER);
        
        grid.getChildren().addAll(statusContainer, buttons, chatArea, servers, clients, games, volumeBox,
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
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
        
    }
	
	public void setMenuBar(){
	
		menuBar = new MenuBar();
		
		file = new Menu("File");
		help = new Menu("Help");
		window = new Menu("Window");
		
		options = new MenuItem("Options");
		createGameMenu = new MenuItem("Create Game");
		createServerMenu = new MenuItem("Create Server");
		testServer = new MenuItem("Test LMU Server");
		exit = new MenuItem("Quit");
		file.getItems().addAll(createGameMenu, createServerMenu, testServer, options, exit);
		
		fullScreen = new MenuItem("Full Screen");
		window.getItems().addAll(fullScreen);
		
		
		gameRules = new MenuItem("Game Rules");
		help.getItems().addAll(gameRules);
		
		menuBar.getMenus().addAll(file, window, help);
		menuBar.setOpacity(0.87);
//		menuBar.setMaxWidth(1400);
//		menuBar.setPrefWidth(1400);
		
		GridPane.setConstraints(menuBar, 0, 0);
		GridPane.setColumnSpan(menuBar, 3);
		GridPane.setValignment(menuBar, VPos.CENTER);
		GridPane.setHalignment(menuBar, HPos.CENTER);
		
		grid.getChildren().add(menuBar);
		
	}
	
	public void setPromptArea(){
		
        promptArea = new GridPane();
        promptArea.setPadding(new Insets(20));
 
        //Constraints for the prompt grid
        ColumnConstraints prompt0 = new ColumnConstraints();
        prompt0.setPercentWidth(100);
        promptArea.getColumnConstraints().add(prompt0);
        
        RowConstraints prompt1 = new RowConstraints();
	    prompt1.setPercentHeight(50); 
	    promptArea.getRowConstraints().add(prompt1);
	    
	    RowConstraints prompt2 = new RowConstraints();
	    prompt2.setPercentHeight(50);        
	    promptArea.getRowConstraints().add(prompt2);
        
        infoArea = new GridPane();
//      infoArea.setPadding(new Insets(2.5));
        infoArea.setVgap(8);
        
        //Constraints for the info grid
        ColumnConstraints info0 = new ColumnConstraints();
        info0.setPercentWidth(100);
        infoArea.getColumnConstraints().add(info0);
        
        RowConstraints info1 = new RowConstraints();
	    info1.setPercentHeight(85); 
	    infoArea.getRowConstraints().add(info1);
	    
	    RowConstraints info2 = new RowConstraints();
	    info2.setPercentHeight(15);        
	    infoArea.getRowConstraints().add(info2);
        
	    InnerShadow innerShadow6 = new InnerShadow();
		innerShadow6.setOffsetX(0);
		innerShadow6.setOffsetY(0);
		innerShadow6.setWidth(15);
		innerShadow6.setHeight(15);
		innerShadow6.setColor(Color.DARKSLATEGREY);
        
        TextArea rules = new TextArea();
        rules.setPrefHeight(350);
        rules.setMaxHeight(350);
        rules.setWrapText(true);
        rules.setEditable(false);
        rules.setOpacity(0.9);
        rules.setEffect(innerShadow6);
        rules.setText(HowToPlay.GOAL + HowToPlay.PLAY + HowToPlay.SUGGESTION 
        		+ HowToPlay.ACCUSING);
        
        GridPane.setConstraints(rules, 0, 0);
        GridPane.setValignment(rules, VPos.BOTTOM);
        GridPane.setHalignment(rules, HPos.CENTER);
        
        closePromptArea = new Button("Close");
        closePromptArea.setOpacity(0.9);
         
        GridPane.setConstraints(closePromptArea, 0, 1);
        GridPane.setValignment(closePromptArea, VPos.TOP);
        GridPane.setHalignment(closePromptArea, HPos.RIGHT);
        
        infoArea.getChildren().addAll(rules, closePromptArea);
        infoArea.setGridLinesVisible(false);
        
        GridPane.setConstraints(infoArea, 0, 0);
        GridPane.setRowSpan(infoArea, 2);
        GridPane.setValignment(infoArea, VPos.CENTER);
        GridPane.setHalignment(infoArea, HPos.CENTER);
        //promptArea.getChildren().add(infoArea);
        promptArea.setGridLinesVisible(false);
		
	}
	
	public void setOptionsArea(){
		
		GridPane optionsArea = new GridPane();
		
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
    	
    	promptArea.getChildren().clear();
    	
    	Stage loginStage = new Stage(StageStyle.TRANSPARENT);
    	LoginPrompt loginPrompt = new LoginPrompt(loginStage);
    	
    	Scene secondary = new Scene(loginPrompt,Config.LOGIN_PROMPT_WINDOW_WIDTH,Config.LOGIN_PROMPT_WINDOW_HEIGHT);
    	secondary.setFill(Color.TRANSPARENT);
		loginStage.setScene(secondary);
		loginStage.setTitle(stageTitle);
		loginStage.setOpacity(0.9);
		loginStage.setAlwaysOnTop(true);
		
		
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
		
		promptArea.getChildren().clear();
		
		Stage selectNewColor = new Stage(StageStyle.TRANSPARENT);
		IntroColorPrompt select = new IntroColorPrompt(selectNewColor, colors);
	
	    Scene secondary = new Scene(select, 415, 400);
	    secondary.setFill(Color.TRANSPARENT);
	    selectNewColor.setScene(secondary);
	    selectNewColor.setOpacity(0.95);
	    selectNewColor.setAlwaysOnTop(true);
	    selectNewColor.showAndWait();
	    return select.returnColor();
	    
	    
	    
	}
	
	public void addClient(String nick){
		Platform.runLater(() -> {
			clientNicks.add(nick);
			System.out.println(nick+" single added");
		});
		
	}
	
	public void assignBackground(){
		
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, 
				false, false, false, true);
		
		 Random rand = new Random();
		 int number = rand.nextInt(7);
		
		switch(number){
		case 1 :{
			setBackgroundImage(new BackgroundImage(getBackIsengard(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		case 2 :{
			setBackgroundImage(new BackgroundImage(getBackMinas(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		case 3 :{
			setBackgroundImage(new BackgroundImage(getBackMordor(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		case 4 :{
			setBackgroundImage(new BackgroundImage(getBackRivendell(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		case 5 :{
			setBackgroundImage(new BackgroundImage(getBackArgonath(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		case 6 :{
			setBackgroundImage(new BackgroundImage(getBackMinas(), BackgroundRepeat.NO_REPEAT , BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER, backgroundSize));
			setBackground(new Background(getBackgroundImage()));} break;
		}
	}
	
	public Button getExit() {
		return exitButton;
	}

	public void setExit(Button exit) {
		this.exitButton = exit;
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
		return backIsengard;
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

	public Button getMute() {
		return mute;
	}

	public void setMute(Button mute) {
		this.mute = mute;
	}

	public Image getMuteGraphic() {
		return muteGraphic;
	}

	public Image getUnmuteGraphic() {
		return unmuteGraphic;
	}

	public File getFile() {
		return audioFile;
	}

	public GridPane getPromptArea() {
		return promptArea;
	}

	public void setPromptArea(GridPane promptArea) {
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

	public Button getExitButton() {
		return exitButton;
	}

	public void setExitButton(Button exitButton) {
		this.exitButton = exitButton;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public Menu getHelp() {
		return help;
	}

	public void setHelp(Menu help) {
		this.help = help;
	}

	public Menu getWindow() {
		return window;
	}

	public void setWindow(Menu window) {
		this.window = window;
	}

	public MenuItem getOptions() {
		return options;
	}

	public void setOptions(MenuItem options) {
		this.options = options;
	}

	public MenuItem getCreateGameMenu() {
		return createGameMenu;
	}

	public void setCreateGameMenu(MenuItem createGameMenu) {
		this.createGameMenu = createGameMenu;
	}

	public MenuItem getCreateServerMenu() {
		return createServerMenu;
	}

	public void setCreateServerMenu(MenuItem createServerMenu) {
		this.createServerMenu = createServerMenu;
	}

	public MenuItem getTestServer() {
		return testServer;
	}

	public void setTestServer(MenuItem testServer) {
		this.testServer = testServer;
	}

	public MenuItem getFullScreen() {
		return fullScreen;
	}

	public void setFullScreen(MenuItem fullScreen) {
		this.fullScreen = fullScreen;
	}

	public MenuItem getGameRules() {
		return gameRules;
	}

	public void setGameRules(MenuItem gameRules) {
		this.gameRules = gameRules;
	}

	public void setFile(Menu file) {
		this.file = file;
	}

	public void setExit(MenuItem exit) {
		this.exit = exit;
	}

	public Background getButtonBackground() {
		return buttonBackground;
	}

	public void setButtonBackground(Background buttonBackground) {
		this.buttonBackground = buttonBackground;
	}

	public HBox getButtons() {
		return buttons;
	}

	public void setButtons(HBox buttons) {
		this.buttons = buttons;
	}

	public Image getBackIsengard() {
		return backIsengard;
	}

	public Image getBackMinas() {
		return backMinas;
	}

	public Image getBackRivendell() {
		return backRivendell;
	}

	public Image getBackMordor() {
		return backMordor;
	}

	public Image getBackArgonath() {
		return backArgonath;
	}

	public GridPane getInfoArea() {
		return infoArea;
	}

	public void setInfoArea(GridPane infoArea) {
		this.infoArea = infoArea;
	}

	public Button getClosePromptArea() {
		return closePromptArea;
	}

	public void setClosePromptArea(Button closePromptArea) {
		this.closePromptArea = closePromptArea;
	}

}