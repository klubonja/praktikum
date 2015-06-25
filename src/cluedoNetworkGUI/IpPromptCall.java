package cluedoNetworkGUI;

import java.util.concurrent.Callable;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class IpPromptCall implements Callable<String> {
	
@Override
	public String call() throws Exception {
    	Stage ipPrompt = new Stage();
    	IpPromptGrid ipr = new IpPromptGrid(ipPrompt);
		Scene secondary = new Scene(ipr,300,300);		
		ipPrompt.setScene(secondary);
		ipPrompt.showAndWait();	
		
		return ipr.returnIp();
    }
}

