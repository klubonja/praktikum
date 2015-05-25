package testGui;

import java.lang.reflect.Array;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TestGUI extends GridPane {
	/*private Label 
	label0_0, label0_1, label0_2, label0_3, label0_4, label0_5, label0_6, label0_7, label0_8, label0_9, label0_10, label0_11, label0_12, label0_13, label0_14, label0_15, label0_16, label0_17, label0_18, label0_19, label0_20, label0_21, label0_22, label0_23, 
	label1_0, label1_1, label1_2, label1_3, label1_4, label1_5, label1_6, label1_7, label1_8, label1_9, label1_10, label1_11, label1_12, label1_13, label1_14, label1_15, label1_16, label1_17, label1_18, label1_19, label1_20, label1_21, label1_22, label1_23,
	label2_0, label2_1, label2_2, label2_3, label2_4, label2_5, label2_6, label2_7, label2_8, label2_9, label2_10, label2_11, label2_12, label2_13, label2_14, label2_15, label2_16, label2_17, label2_18, label2_19, label2_20, label2_21, label2_22, label2_23,
	label3_0, label3_1, label3_2, label3_3, label3_4, label3_5, label3_6, label3_7, label3_8, label3_9, label3_10, label3_11, label3_12, label3_13, label3_14, label3_15, label3_16, label3_17, label3_18, label3_19, label3_20, label3_21, label3_22, label3_23,
	label4_0, label4_1, label4_2, label4_3, label4_4, label4_5, label4_6, label4_7, label4_8, label4_9, label4_10, label4_11, label4_12, label4_13, label4_14, label4_15, label4_16, label4_17, label4_18, label4_19, label4_20, label4_21, label4_22, label4_23,
	label5_0, label5_1, label5_2, label5_3, label5_4, label5_5, label5_6, label5_7, label5_8, label5_9, label5_10, label5_11, label5_12, label5_13, label5_14, label5_15, label5_16, label5_17, label5_18, label5_19, label5_20, label5_21, label5_22, label5_23,
	label6_0, label6_1, label6_2, label6_3, label6_4, label6_5, label6_6, label6_7, label6_8, label6_9, label6_10, label6_11, label6_12, label6_13, label6_14, label6_15, label6_16, label6_17, label6_18, label6_19, label6_20, label6_21, label6_22, label6_23,
	label7_0, label7_1, label7_2, label7_3, label7_4, label7_5, label7_6, label7_7, label7_8, label7_9, label7_10, label7_11, label7_12, label7_13, label7_14, label7_15, label7_16, label7_17, label7_18, label7_19, label7_20, label7_21, label7_22, label7_23,
	label8_0, label8_1, label8_2, label8_3, label8_4, label8_5, label8_6, label8_7, label8_8, label8_9, label8_10, label8_11, label8_12, label8_13, label8_14, label8_15, label8_16, label8_17, label8_18, label8_19, label8_20, label8_21, label8_22, label8_23,
	label9_0, label9_1, label9_2, label9_3, label9_4, label9_5, label9_6, label9_7, label9_8, label9_9, label9_10, label9_11, label9_12, label9_13, label9_14, label9_15, label9_16, label9_17, label9_18, label9_19, label9_20, label9_21, label9_22, label9_23,
	label10_0, label10_1, label10_2, label10_3, label10_4, label10_5, label10_6, label10_7, label10_8, label10_9, label10_10, label10_11, label10_12, label10_13, label10_14, label10_15, label10_16, label10_17, label10_18, label10_19, label10_20, label10_21, label10_22, label10_23,
	label11_0, label11_1, label11_2, label11_3, label11_4, label11_5, label11_6, label11_7, label11_8, label11_9, label11_10, label11_11, label11_12, label11_13, label11_14, label11_15, label11_16, label11_17, label11_18, label11_19, label11_20, label11_21, label11_22, label11_23,
	label12_0, label12_1, label12_2, label12_3, label12_4, label12_5, label12_6, label12_7, label12_8, label12_9, label12_10, label12_11, label12_12, label12_13, label12_14, label12_15, label12_16, label12_17, label12_18, label12_19, label12_20, label12_21, label12_22, label12_23,
	label13_0, label13_1, label13_2, label13_3, label13_4, label13_5, label13_6, label13_7, label13_8, label13_9, label13_10, label13_11, label13_12, label13_13, label13_14, label13_15, label13_16, label13_17, label13_18, label13_19, label13_20, label13_21, label13_22, label13_23,
	label14_0, label14_1, label14_2, label14_3, label14_4, label14_5, label14_6, label14_7, label14_8, label14_9, label14_10, label14_11, label14_12, label14_13, label14_14, label14_15, label14_16, label14_17, label14_18, label14_19, label14_20, label14_21, label14_22, label14_23,
	label15_0, label15_1, label15_2, label15_3, label15_4, label15_5, label15_6, label15_7, label15_8, label15_9, label15_10, label15_11, label15_12, label15_13, label15_14, label15_15, label15_16, label15_17, label15_18, label15_19, label15_20, label15_21, label15_22, label15_23,
	label16_0, label16_1, label16_2, label16_3, label16_4, label16_5, label16_6, label16_7, label16_8, label16_9, label16_10, label16_11, label16_12, label16_13, label16_14, label16_15, label16_16, label16_17, label16_18, label16_19, label16_20, label16_21, label16_22, label16_23,
	label17_0, label17_1, label17_2, label17_3, label17_4, label17_5, label17_6, label17_7, label17_8, label17_9, label17_10, label17_11, label17_12, label17_13, label17_14, label17_15, label17_16, label17_17, label17_18, label17_19, label17_20, label17_21, label17_22, label17_23,
	label18_0, label18_1, label18_2, label18_3, label18_4, label18_5, label18_6, label18_7, label18_8, label18_9, label18_10, label18_11, label18_12, label18_13, label18_14, label18_15, label18_16, label18_17, label18_18, label18_19, label18_20, label18_21, label18_22, label18_23,
	label19_0, label19_1, label19_2, label19_3, label19_4, label19_5, label19_6, label19_7, label19_8, label19_9, label19_10, label19_11, label19_12, label19_13, label19_14, label19_15, label19_16, label19_17, label19_18, label19_19, label19_20, label19_21, label19_22, label19_23,
	label20_0, label20_1, label20_2, label20_3, label20_4, label20_5, label20_6, label20_7, label20_8, label20_9, label20_10, label20_11, label20_12, label20_13, label20_14, label20_15, label20_16, label20_17, label20_18, label20_19, label20_20, label20_21, label20_22, label20_23,
	label21_0, label21_1, label21_2, label21_3, label21_4, label21_5, label21_6, label21_7, label21_8, label21_9, label21_10, label21_11, label21_12, label21_13, label21_14, label21_15, label21_16, label21_17, label21_18, label21_19, label21_20, label21_21, label21_22, label21_23,
	label22_0, label22_1, label22_2, label22_3, label22_4, label22_5, label22_6, label22_7, label22_8, label22_9, label22_10, label22_11, label22_12, label22_13, label22_14, label22_15, label22_16, label22_17, label22_18, label22_19, label22_20, label22_21, label22_22, label22_23,
	label23_0, label23_1, label23_2, label23_3, label23_4, label23_5, label23_6, label23_7, label23_8, label23_9, label23_10, label23_11, label23_12, label23_13, label23_14, label23_15, label23_16, label23_17, label23_18, label23_19, label23_20, label23_21, label23_22, label23_23,
	label24_0, label24_1, label24_2, label24_3, label24_4, label24_5, label24_6, label24_7, label24_8, label24_9, label24_10, label24_11, label24_12, label24_13, label24_14, label24_15, label24_16, label24_17, label24_18, label24_19, label24_20, label24_21, label24_22, label24_23
	;
	*/
	/*
	Label [][] labelArray = {{label0_0, label0_1, label0_2, label0_3, label0_4, label0_5, label0_6, label0_7, label0_8, label0_9, label0_10, label0_11, label0_12, label0_13, label0_14, label0_15, label0_16, label0_17, label0_18, label0_19, label0_20, label0_21, label0_22, label0_23}, 
        	{label1_0, label1_1, label1_2, label1_3, label1_4, label1_5, label1_6, label1_7, label1_8, label1_9, label1_10, label1_11, label1_12, label1_13, label1_14, label1_15, label1_16, label1_17, label1_18, label1_19, label1_20, label1_21, label1_22, label1_23},
        	{label2_0, label2_1, label2_2, label2_3, label2_4, label2_5, label2_6, label2_7, label2_8, label2_9, label2_10, label2_11, label2_12, label2_13, label2_14, label2_15, label2_16, label2_17, label2_18, label2_19, label2_20, label2_21, label2_22, label2_23},
        	{label3_0, label3_1, label3_2, label3_3, label3_4, label3_5, label3_6, label3_7, label3_8, label3_9, label3_10, label3_11, label3_12, label3_13, label3_14, label3_15, label3_16, label3_17, label3_18, label3_19, label3_20, label3_21, label3_22, label3_23},
        	{label4_0, label4_1, label4_2, label4_3, label4_4, label4_5, label4_6, label4_7, label4_8, label4_9, label4_10, label4_11, label4_12, label4_13, label4_14, label4_15, label4_16, label4_17, label4_18, label4_19, label4_20, label4_21, label4_22, label4_23},
        	{label5_0, label5_1, label5_2, label5_3, label5_4, label5_5, label5_6, label5_7, label5_8, label5_9, label5_10, label5_11, label5_12, label5_13, label5_14, label5_15, label5_16, label5_17, label5_18, label5_19, label5_20, label5_21, label5_22, label5_23},
        	{label6_0, label6_1, label6_2, label6_3, label6_4, label6_5, label6_6, label6_7, label6_8, label6_9, label6_10, label6_11, label6_12, label6_13, label6_14, label6_15, label6_16, label6_17, label6_18, label6_19, label6_20, label6_21, label6_22, label6_23},
        	{label7_0, label7_1, label7_2, label7_3, label7_4, label7_5, label7_6, label7_7, label7_8, label7_9, label7_10, label7_11, label7_12, label7_13, label7_14, label7_15, label7_16, label7_17, label7_18, label7_19, label7_20, label7_21, label7_22, label7_23},
        	{label8_0, label8_1, label8_2, label8_3, label8_4, label8_5, label8_6, label8_7, label8_8, label8_9, label8_10, label8_11, label8_12, label8_13, label8_14, label8_15, label8_16, label8_17, label8_18, label8_19, label8_20, label8_21, label8_22, label8_23},
        	{label9_0, label9_1, label9_2, label9_3, label9_4, label9_5, label9_6, label9_7, label9_8, label9_9, label9_10, label9_11, label9_12, label9_13, label9_14, label9_15, label9_16, label9_17, label9_18, label9_19, label9_20, label9_21, label9_22, label9_23},
        	{label10_0, label10_1, label10_2, label10_3, label10_4, label10_5, label10_6, label10_7, label10_8, label10_9, label10_10, label10_11, label10_12, label10_13, label10_14, label10_15, label10_16, label10_17, label10_18, label10_19, label10_20, label10_21, label10_22, label10_23},
        	{label11_0, label11_1, label11_2, label11_3, label11_4, label11_5, label11_6, label11_7, label11_8, label11_9, label11_10, label11_11, label11_12, label11_13, label11_14, label11_15, label11_16, label11_17, label11_18, label11_19, label11_20, label11_21, label11_22, label11_23},
        	{label12_0, label12_1, label12_2, label12_3, label12_4, label12_5, label12_6, label12_7, label12_8, label12_9, label12_10, label12_11, label12_12, label12_13, label12_14, label12_15, label12_16, label12_17, label12_18, label12_19, label12_20, label12_21, label12_22, label12_23},
        	{label13_0, label13_1, label13_2, label13_3, label13_4, label13_5, label13_6, label13_7, label13_8, label13_9, label13_10, label13_11, label13_12, label13_13, label13_14, label13_15, label13_16, label13_17, label13_18, label13_19, label13_20, label13_21, label13_22, label13_23},
        	{label14_0, label14_1, label14_2, label14_3, label14_4, label14_5, label14_6, label14_7, label14_8, label14_9, label14_10, label14_11, label14_12, label14_13, label14_14, label14_15, label14_16, label14_17, label14_18, label14_19, label14_20, label14_21, label14_22, label14_23},
        	{label15_0, label15_1, label15_2, label15_3, label15_4, label15_5, label15_6, label15_7, label15_8, label15_9, label15_10, label15_11, label15_12, label15_13, label15_14, label15_15, label15_16, label15_17, label15_18, label15_19, label15_20, label15_21, label15_22, label15_23},
        	{label16_0, label16_1, label16_2, label16_3, label16_4, label16_5, label16_6, label16_7, label16_8, label16_9, label16_10, label16_11, label16_12, label16_13, label16_14, label16_15, label16_16, label16_17, label16_18, label16_19, label16_20, label16_21, label16_22, label16_23},
        	{label17_0, label17_1, label17_2, label17_3, label17_4, label17_5, label17_6, label17_7, label17_8, label17_9, label17_10, label17_11, label17_12, label17_13, label17_14, label17_15, label17_16, label17_17, label17_18, label17_19, label17_20, label17_21, label17_22, label17_23},
        	{label18_0, label18_1, label18_2, label18_3, label18_4, label18_5, label18_6, label18_7, label18_8, label18_9, label18_10, label18_11, label18_12, label18_13, label18_14, label18_15, label18_16, label18_17, label18_18, label18_19, label18_20, label18_21, label18_22, label18_23},
        	{label19_0, label19_1, label19_2, label19_3, label19_4, label19_5, label19_6, label19_7, label19_8, label19_9, label19_10, label19_11, label19_12, label19_13, label19_14, label19_15, label19_16, label19_17, label19_18, label19_19, label19_20, label19_21, label19_22, label19_23},
        	{label20_0, label20_1, label20_2, label20_3, label20_4, label20_5, label20_6, label20_7, label20_8, label20_9, label20_10, label20_11, label20_12, label20_13, label20_14, label20_15, label20_16, label20_17, label20_18, label20_19, label20_20, label20_21, label20_22, label20_23},
        	{label21_0, label21_1, label21_2, label21_3, label21_4, label21_5, label21_6, label21_7, label21_8, label21_9, label21_10, label21_11, label21_12, label21_13, label21_14, label21_15, label21_16, label21_17, label21_18, label21_19, label21_20, label21_21, label21_22, label21_23},
        	{label22_0, label22_1, label22_2, label22_3, label22_4, label22_5, label22_6, label22_7, label22_8, label22_9, label22_10, label22_11, label22_12, label22_13, label22_14, label22_15, label22_16, label22_17, label22_18, label22_19, label22_20, label22_21, label22_22, label22_23},
        	{label23_0, label23_1, label23_2, label23_3, label23_4, label23_5, label23_6, label23_7, label23_8, label23_9, label23_10, label23_11, label23_12, label23_13, label23_14, label23_15, label23_16, label23_17, label23_18, label23_19, label23_20, label23_21, label23_22, label23_23},
        	{label24_0, label24_1, label24_2, label24_3, label24_4, label24_5, label24_6, label24_7, label24_8, label24_9, label24_10, label24_11, label24_12, label24_13, label24_14, label24_15, label24_16, label24_17, label24_18, label24_19, label24_20, label24_21, label24_22, label24_23}
}; 
	*/
	
	
	
	
	Label [][] labelArray;
	private Stage stage;
	private Scene sceneYo;
	private Circle testSpieler;
	private BackgroundFill fill;
	private Label labelAnfang;
	
	
	public TestGUI(int size){
		size = size +2;
		labelArray = new Label[size][size];
	}	
	
	public void start(){
	this.stage = new Stage();
	this.sceneYo = new Scene(this,600,600);
	this.stage.setScene(sceneYo);
	setLayout();
	this.stage.show();
	}
	
	public void setLayout(){
		this.setGridLinesVisible(true);

		for (int i = 0; i< labelArray.length-1;i++){
			for (int j = 0; j<labelArray[j].length-1;j++){
				String iString = +i +" - " +j;
				labelArray[i][j] = new Label(iString);
				this.add(labelArray[i][j], i, j);
				System.out.println(i +" - "+j);
				// labelArray[i][j].setPrefHeight(20);
				// labelArray[i][j].setPrefWidth(20);
				// this.setConstraints(labelArray[i][j], i, j);
				
			}
		}
		labelAnfang = labelArray[0][0];
		testSpieler = new Circle(20);
		testSpieler.setFill(Color.ROYALBLUE);
		this.add(testSpieler, 0, 0);
		//this.setRotate(20);
		
	}

	public Label[][] getLabelArray() {
		return labelArray;
	}

	public void setLabelArray(Label[][] labelArray) {
		this.labelArray = labelArray;
	}

	public void setBackground(Label label){
		fill = new BackgroundFill(Color.ROYALBLUE, null, null);
		Background hintergrund = new Background(fill);
		label.setBackground(hintergrund);
	}
	
	public void revertBackground(Label label){
		label.setBackground(null);
	}
	
		public Circle getTestSpieler() {
		return testSpieler;
	}

	public void setTestSpieler(Circle testSpieler) {
		this.testSpieler = testSpieler;
	}
	
	public Label getLabelAnfang() {
		return labelAnfang;
	}

	public void setLabelAnfang(Label labelAnfang) {
		this.labelAnfang = labelAnfang;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Scene getSceneYo() {
		return sceneYo;
	}

	public void setSceneYo(Scene sceneYo) {
		this.sceneYo = sceneYo;
	}
	
}
