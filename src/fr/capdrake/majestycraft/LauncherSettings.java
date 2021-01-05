package fr.capdrake.majestycraft;


import java.util.HashMap;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LauncherSettings extends IScreen {
	
	private LauncherLabel titleLabel;
	private LauncherRectangle topRectangle;
	private LauncherButton saveButton;
	private LauncherLabel memorySliderLabel;
	private LauncherLabel sliderLabel;
	private Slider memorySlider;
	private LauncherLabel windowsSizeLabel;
	private ComboBox<String> windowsSizeList;
	private ComboBox<String> versionList;
	private LauncherLabel versionListLabel;
	private CheckBox autoLogin;
	private CheckBox useForge;
	private CheckBox useVMArguments;
	private LauncherTextField vmArguments;
	
	
	public LauncherSettings(final Pane root, final GameEngine engine, final LauncherPanel pane) {
		this.drawBackgroundImage(engine, root, "background.png");
		pane.config.loadConfiguration();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 500, 15);
		this.topRectangle.setOpacity(0.7);
		/** ===================== LABEL TITRE ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("PARAMETRES");
		this.titleLabel.setStyle("-fx-text-fill: white;");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 28F));
		this.titleLabel.setPosition(150, 20);
		this.titleLabel.setSize(230, 35);
		/** ===================== MC SIZE LABEL ===================== */
		this.windowsSizeLabel = new LauncherLabel(root);
		this.windowsSizeLabel.setText("Taille de la fenetre:");
		this.windowsSizeLabel.setOpacity(1.0);
		this.windowsSizeLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.windowsSizeLabel.setStyle("-fx-text-fill: white;");
		this.windowsSizeLabel.setSize(370, 30);
		this.windowsSizeLabel.setPosition(50, 60);
		/** ===================== MC SIZE LIST ===================== */
		this.windowsSizeList = new ComboBox<String>();
		this.populateSizeList();
		if (pane.config.getValue("gamesize") != null) {
			this.windowsSizeList.setValue(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))).getDesc());
		}
		this.windowsSizeList.setPrefSize(100, 20);
		this.windowsSizeList.setLayoutX(340);
		this.windowsSizeList.setLayoutY(65);
		this.windowsSizeList.setVisibleRowCount(5);
		root.getChildren().add(this.windowsSizeList);
		/** ===================== SLIDER RAM LABEL ===================== */
		this.sliderLabel = new LauncherLabel(root);
		this.sliderLabel.setText("RAM Allouee:");
		this.sliderLabel.setOpacity(1.0);
		this.sliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.sliderLabel.setStyle("-fx-text-fill: white;");
		this.sliderLabel.setSize(370, 30);
		this.sliderLabel.setPosition(50, 170);
		/** ===================== SLIDER RAM LABEL SELECTIONNED ===================== */
		this.memorySliderLabel = new LauncherLabel(root);
		this.memorySliderLabel.setOpacity(1.0);
		this.memorySliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.memorySliderLabel.setStyle("-fx-text-fill: white;");
		this.memorySliderLabel.setSize(370, 30);
		this.memorySliderLabel.setPosition(380, 170);
		/** ===================== SLIDER RAM ===================== */
		this.memorySlider = new Slider();
		this.memorySlider.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 0.5);");
		this.memorySlider.setMin(1);
		this.memorySlider.setMax(10);
		if (pane.config.getValue("allocatedram") != null) {
			double d = Double.parseDouble((String) pane.config.getValue("allocatedram"));
			this.memorySlider.setValue(d);
		}
		this.memorySlider.setLayoutX(50);
		this.memorySlider.setLayoutY(210);
		this.memorySlider.setPrefWidth(395);
		this.memorySlider.setBlockIncrement(1);
		memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                    memorySlider.setValue(Math.round(new_val.doubleValue()));
            }
        });
		this.memorySlider.valueProperty().addListener(new ChangeListener<Number>() {
	         @Override
	         public void changed(ObservableValue<? extends Number> observable,
	               Number oldValue, Number newValue) {
	        	 memorySliderLabel.setText(newValue + "Gb");
	         }
	      });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	 root.getChildren().add(memorySlider);
            }
        });
		
		this.memorySliderLabel.setText(this.memorySlider.getValue() + "Gb");
		
		/** ===================== MC VERSION LABEL ===================== */
		this.versionListLabel = new LauncherLabel(root);
		this.versionListLabel.setText("Choix de la version:");
		this.versionListLabel.setOpacity(1.0);
		this.versionListLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.versionListLabel.setStyle("-fx-text-fill: white;");
		this.versionListLabel.setSize(370, 30);
		this.versionListLabel.setPosition(50, 110);
		/** ===================== MC VERSION LIST ===================== */
		this.versionList = new ComboBox<String>();
		this.populateVersionList();
		if (pane.config.getValue("version") != null) {
			this.versionList.setValue((String) pane.config.getValue("version"));
		}
		this.versionList.setPrefSize(100, 20);
		this.versionList.setLayoutX(340);
		this.versionList.setLayoutY(115);
		this.versionList.setVisibleRowCount(10);
		this.versionList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pane.config.updateValue("version", versionList.getValue());
			}
		});
		root.getChildren().add(this.versionList);
		
		/** ===================== VM ARGUMENTS TEXTFIELD ===================== */
		this.vmArguments = new LauncherTextField(root);
		this.vmArguments.setText((String) pane.config.getValue("vmarguments"));
		this.vmArguments.setSize(390, 20);
		this.vmArguments.setPosition(50, 285);
		/** ===================== CHECKBOX USE VM ARGUMENTS ===================== */
		this.useVMArguments = new CheckBox();
		this.useVMArguments.setText("Utiliser les Arguments JVM");
		this.useVMArguments.setSelected((Boolean)pane.config.getValue("usevmarguments"));
		this.useVMArguments.setOpacity(1.0);
		this.useVMArguments.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useVMArguments.setStyle("-fx-text-fill: white;");
		this.useVMArguments.setLayoutX(50);
		this.useVMArguments.setLayoutY(255);
		this.useVMArguments.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (useVMArguments.isSelected()) {
					vmArguments.setDisable(false);
				}
				else {
					vmArguments.setDisable(true);
				}
			}
		});
		root.getChildren().add(useVMArguments);
		this.vmArguments.setDisable(!this.useVMArguments.isSelected());
		
		/** ===================== CHECKBOX USE Forge ===================== */
		this.useForge = new CheckBox();
		this.useForge.setText("Optifine ( 1.16.2 ONLY )");
		this.useForge.setSelected((boolean) pane.config.getValue("useforge"));
		this.useForge.setOpacity(1.0);
		this.useForge.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useForge.setStyle("-fx-text-fill: white;");
		this.useForge.setLayoutX(300);
		this.useForge.setLayoutY(255);
		root.getChildren().add(useForge);
		
		/** ===================== AUTO LOGIN CHECK BOX ===================== */
		this.autoLogin = new CheckBox();
		this.autoLogin.setText("Connexion auto (crack)");
		this.autoLogin.setSelected((Boolean)pane.config.getValue("autologin"));
		this.autoLogin.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLogin.setStyle("-fx-text-fill: white;");
		this.autoLogin.setLayoutX(50);
		this.autoLogin.setLayoutY(330);
		root.getChildren().add(autoLogin);
		
		/** ===================== BOUTON DE VALIDATION ===================== */
		this.saveButton = new LauncherButton(root);
		this.saveButton.setText("Valider");
		this.saveButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.saveButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.saveButton.setPosition(310, 350);
		this.saveButton.setSize(130, 35);
		this.saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("allocatedram", String.valueOf(memorySlider.getValue()));
				configMap.put("gamesize", "" + GameSize.getWindowSize(windowsSizeList.getValue()));
				configMap.put("autologin", "" + autoLogin.isSelected());
				configMap.put("usevmarguments", "" + useVMArguments.isSelected());
				configMap.put("vmarguments", "" + vmArguments.getText());
				configMap.put("version", "" + versionList.getValue());
				configMap.put("useforge", "" + useForge.isSelected());
				pane.config.updateValues(configMap);
				engine.reg(GameMemory.getMemory(Double.parseDouble((String) pane.config.getValue("allocatedram"))));
				engine.reg(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))));
				engine.getGameLinks().JSON_NAME = (String) pane.config.getValue("version") + ".json";
				engine.getGameLinks().JSON_URL = engine.getGameLinks().BASE_URL + engine.getGameLinks().JSON_NAME;	
				if(useForge.isSelected())
				{
					switch(engine.getGameLinks().JSON_NAME)
					{
						case "1.16.2.json":
							engine.setGameStyle(GameStyle.OPTIFINE);
							LauncherMain.gameForge = new GameForge("fmlclient", "33.0.61", "1.16.2", "net.minecraft.launchwrapper.Launch", "20200812.004259");
							break;
					}
				} else {
					engine.setGameStyle(GameStyle.VANILLA);
				}
				engine.reg(engine.getGameLinks());
				engine.reg(engine.getGameStyle());
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
			}
		});
	}

	private void populateSizeList() {
		for (GameSize size : GameSize.values()) {
			this.windowsSizeList.getItems().add(size.getDesc());
		}
	}
	
	//private String isAutoLogin() {
		 //if (autoLogin.isSelected()) {
		 //return "true";
		 //} else {
		 //return "false";
		 //}
		 //}
	private void populateVersionList() 
	{
		for(int i = 1; i < 12; i++) 
		{
			if(i == 1) 
			{
				this.versionList.getItems().add("1.8");
			}
			else if(i == 2) 
			{
				this.versionList.getItems().add("1.9");
			}
			else if(i == 3) 
			{
				this.versionList.getItems().add("1.10.2");
			}
			else if(i == 4) 
			{
				this.versionList.getItems().add("1.11.2");
			}
			else if(i == 5) 
			{
				this.versionList.getItems().add("1.12.2");
			}
			else if(i == 6) 
			{
				this.versionList.getItems().add("1.13.2");
			}
			else if(i == 7) 
			{
				this.versionList.getItems().add("1.14.2");
			}
			else if(i == 8) 
			{
				this.versionList.getItems().add("1.15.2");
			}
			else if(i == 9) 
			{
				this.versionList.getItems().add("1.16.2");
			}
			else if(i == 10) 
			{
				this.versionList.getItems().add("1.16.3");
			}
			else if(i == 11) 
			{
				this.versionList.getItems().add("1.16.4");
			}
		}
	}
}
