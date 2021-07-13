package fr.capdrake.majestycraft;


import java.util.HashMap;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
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
	private CheckBox useDiscord;
	private CheckBox useMusic;
	//private CheckBox useDiscord;
	private CheckBox useVMArguments;
	private LauncherTextField vmArguments;
	private double xOffSet; // Position x à l'instant du clic
	private double yOffSet; // Position y à l'instant du clic
	Stage stage; // Le stage qu'on voudra faire bouger (ici notre menu des paramètres)
	
	
	public LauncherSettings(final Pane root, final GameEngine engine, final LauncherPanel pane) {
		
		/** ===================== BOUGER LE MENU PARAMETRE ===================== */
		//Cet évent nous permet de récupérer les valeurs en x et en y initiales.
		root.setOnMousePressed(event -> 
        {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
		//Cet évent s'occupe de faire bouger le menu
		root.setOnMouseDragged(event -> 
        {
            stage = (Stage) memorySlider.getScene().getWindow(); // On get le stage du menu des paramètres
            stage.setX(event.getScreenX() - xOffSet); // On donne la nouvelle position en x
            stage.setY(event.getScreenY() - yOffSet); // On donne la nouvelle postion en y      
        });
		
		
		
		this.drawBackgroundImage(engine, root, "background.png");
		pane.config.loadConfiguration();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 1500, 15);
		this.topRectangle.setOpacity(0.7);
		/** ===================== LABEL TITRE ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("PARAMETRES");
		this.titleLabel.setStyle("-fx-text-fill: white;");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 28F));
		this.titleLabel.setPosition(350, 20);
		this.titleLabel.setSize(230, 35);
		/** ===================== MC SIZE LABEL ===================== */
		this.windowsSizeLabel = new LauncherLabel(root);
		this.windowsSizeLabel.setText("Taille de la fenêtre:");
		this.windowsSizeLabel.setOpacity(1.0);
		this.windowsSizeLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.windowsSizeLabel.setStyle("-fx-text-fill: white;");
		this.windowsSizeLabel.setSize(370, 30);
		this.windowsSizeLabel.setPosition(250, 110);
		/** ===================== MC SIZE LIST ===================== */
		this.windowsSizeList = new ComboBox<String>();
		this.populateSizeList();
		if (pane.config.getValue("gamesize") != null) {
			this.windowsSizeList.setValue(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))).getDesc());
		}
		this.windowsSizeList.setPrefSize(150, 20);
		this.windowsSizeList.setLayoutX(490);
		this.windowsSizeList.setLayoutY(115);
		this.windowsSizeList.setVisibleRowCount(5);
		root.getChildren().add(this.windowsSizeList);
		/** ===================== SLIDER RAM LABEL ===================== */
		this.sliderLabel = new LauncherLabel(root);
		this.sliderLabel.setText("RAM Allouée:");
		this.sliderLabel.setOpacity(1.0);
		this.sliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.sliderLabel.setStyle("-fx-text-fill: white;");
		this.sliderLabel.setSize(370, 30);
		this.sliderLabel.setPosition(250, 220);
		/** ===================== SLIDER RAM LABEL SELECTIONNED ===================== */
		this.memorySliderLabel = new LauncherLabel(root);
		this.memorySliderLabel.setOpacity(1.0);
		this.memorySliderLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.memorySliderLabel.setStyle("-fx-text-fill: white;");
		this.memorySliderLabel.setSize(370, 30);
		this.memorySliderLabel.setPosition(540, 220);
		/** ===================== SLIDER RAM ===================== */
		this.memorySlider = new Slider();
		this.memorySlider.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 0.5);");
		this.memorySlider.setMin(1);
		this.memorySlider.setMax(10);
		if (pane.config.getValue("allocatedram") != null) {
			double d = Double.parseDouble((String) pane.config.getValue("allocatedram"));
			this.memorySlider.setValue(d);
		}
		this.memorySlider.setLayoutX(250);
		this.memorySlider.setLayoutY(260);
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
		this.versionListLabel.setPosition(250, 160);
		/** ===================== MC VERSION LIST ===================== */
		this.versionList = new ComboBox<String>();
		this.populateVersionList();
		if (pane.config.getValue("version") != null) {
			this.versionList.setValue((String) pane.config.getValue("version"));
		}
		this.versionList.setPrefSize(150, 20);
		this.versionList.setLayoutX(490);
		this.versionList.setLayoutY(165);
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
		this.vmArguments.setPosition(250, 335);
		/** ===================== CHECKBOX USE VM ARGUMENTS ===================== */
		this.useVMArguments = new CheckBox();
		this.useVMArguments.setText("Utiliser les Arguments JVM");
		this.useVMArguments.setSelected((Boolean)pane.config.getValue("usevmarguments"));
		this.useVMArguments.setOpacity(1.0);
		this.useVMArguments.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useVMArguments.setStyle("-fx-text-fill: white;");
		this.useVMArguments.setLayoutX(250);
		this.useVMArguments.setLayoutY(305);
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
		this.useForge.setText("Optifine + Forge");
		this.useForge.setSelected((boolean) pane.config.getValue("useforge"));
		this.useForge.setOpacity(1.0);
		this.useForge.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useForge.setStyle("-fx-text-fill: white;");
		this.useForge.setLayoutX(500);
		this.useForge.setLayoutY(305);		
		this.useForge.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pane.config.updateValue("useforge", useForge.isSelected());
			}
		});
		root.getChildren().add(this.useForge);
		
		
		/** ===================== CHECKBOX Discord statut ===================== */
		this.useDiscord = new CheckBox();
		this.useDiscord.setText("Statut discord");
		this.useDiscord.setSelected((Boolean)pane.config.getValue("usediscord"));
		this.useDiscord.setOpacity(1.0);
		this.useDiscord.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useDiscord.setStyle("-fx-text-fill: white;");
		this.useDiscord.setLayoutX(500);
		this.useDiscord.setLayoutY(380);
		this.useDiscord.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pane.config.updateValue("usediscord", useDiscord.isSelected());
			}
		});
		root.getChildren().add(this.useDiscord);
		
		
		/** ===================== AUTO LOGIN CHECK BOX ===================== */
		this.autoLogin = new CheckBox();
		this.autoLogin.setText("Connexion automatique");
		this.autoLogin.setSelected((Boolean)pane.config.getValue("autologin"));
		this.autoLogin.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLogin.setStyle("-fx-text-fill: white;");
		this.autoLogin.setLayoutX(250);
		this.autoLogin.setLayoutY(380);
		root.getChildren().add(autoLogin);
		
		/** ===================== AUTO LOGIN CHECK BOX ===================== */
		this.useMusic = new CheckBox();
		this.useMusic.setText("Couper la musique");
		this.useMusic.setSelected((Boolean)pane.config.getValue("usemusic"));
		this.useMusic.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.useMusic.setStyle("-fx-text-fill: white;");
		this.useMusic.setLayoutX(250);
		this.useMusic.setLayoutY(410);
		this.useMusic.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				pane.config.updateValue("usemusic", useMusic.isSelected());
				if (useMusic.isSelected()) {
					LauncherMain.muteMusic();
				}
				else {
					LauncherMain.resumeMusic();
				}
			}
		});
		root.getChildren().add(useMusic);
		
		/** ===================== BOUTON DE VALIDATION ===================== */
		this.saveButton = new LauncherButton(root);
		this.saveButton.setText("Valider");
		this.saveButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.saveButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.saveButton.setPosition(700, 550);
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
				configMap.put("usemusic", "" + useMusic.isSelected());
				configMap.put("usediscord", ""+ useDiscord.isSelected());
				pane.config.updateValues(configMap);
				engine.reg(GameMemory.getMemory(Double.parseDouble((String) pane.config.getValue("allocatedram"))));
				engine.reg(GameSize.getWindowSize(Integer.parseInt((String) pane.config.getValue("gamesize"))));
				engine.getGameLinks().JSON_NAME = (String) pane.config.getValue("version") + ".json";
				engine.getGameLinks().JSON_URL = engine.getGameLinks().BASE_URL + engine.getGameLinks().JSON_NAME;	
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
	
	
	private void populateVersionList() 
	{
		for(int i = 1; i < 17; i++) 
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
				this.versionList.getItems().add("1.14.4");
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
			else if(i == 12) 
			{
				this.versionList.getItems().add("1.16.5");
			}
			else if(i == 13) 
			{
				this.versionList.getItems().add("21w14a");
			}
			else if(i == 14)
			{
				this.versionList.getItems().add("21w15a");
			}
			else if(i == 15)
			{
				this.versionList.getItems().add("1.17");
			}
			else if(i == 16)
			{
				this.versionList.getItems().add("1.17.1");
			}
		}
	}
}
