package fr.capdrake.majestycraft;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import animatefx.animation.BounceInLeft;
import animatefx.animation.BounceInUp;
import animatefx.animation.BounceOutDown;
import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutDown;
import animatefx.animation.Hinge;
import animatefx.animation.RotateIn;
import animatefx.animation.RotateOut;
import animatefx.animation.ZoomInDown;
import animatefx.animation.ZoomInLeft;
import animatefx.animation.ZoomOutDown;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameMemory;
import fr.trxyy.alternative.alternative_api.GameSize;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.JVMArguments;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Forge;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_auth.account.AccountType;
import fr.trxyy.alternative.alternative_auth.base.GameAuth;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherPanel extends IScreen{
	
	//Auth de Microsoft
	private GameAuth gameAuthentication;
	
	private LauncherImage titleImage;

	private JFXTextField usernameField;
	private JFXPasswordField passwordField;
	
	private JFXTextField usernameField2;
	
	//Les bouttons 
	private LauncherButton siteButton;
	private LauncherButton forumButton;
	private LauncherButton voteButton;
	private LauncherButton boutiqueButton;
	
	//Nouveaux buttons
	private JFXButton loginButton;
	private JFXButton loginButton2;
	
	
	private LauncherButton settingsButton;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	private static LauncherButton minestratorButton;
	private static LauncherButton twitterButton;
	private static LauncherButton tiktokButton;
	private static LauncherButton youtubeButton;
	private LauncherButton infoButton;
	private LauncherButton lolButton;
	private LauncherButton lolButton2;
	private LauncherButton deadButton;
	private LauncherButton microsoftButton;
	
	//ToggleButton du premium/crack
	private JFXToggleButton toggleButton;
	//ToggleButton du remmeberme
	private JFXToggleButton rememberMe;
	
	//Les labels
	private LauncherLabel autoLoginLabel;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	private LauncherLabel titleLabel;
	private LauncherLabel titlePremium;
	private LauncherLabel titleCrack;
	
	// Les rectangles 
	private LauncherRectangle autoLoginRectangle;
	private LauncherRectangle topRectangle;
	private LauncherRectangle updateRectangle;
	private LauncherRectangle connexionRectangle;
	
	/** GAMEENGINE REQUIRED */
	private static GameEngine theGameEngine;	
	
	// Se souvenir de moi

	private Timer autoLoginTimer;

	private LauncherButton autoLoginButton;
	
	private Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater gameUpdater = new GameUpdater();

	public JFXProgressBar bar;
	
	// Image avatar
	public static LauncherImage avatar;
	public static LauncherImage avatar2;
	public static LauncherImage avatar3;

	
	private String MINESTRATOR_URL = "https://minestrator.com/?partner=eus561rkso";
	private String INSTAGRAM_URL = "https://www.tiktok.com/@majestycraft?lang=fr";
	private String TWITTER_URL = "http://twitter.com/craftsurvie";
	private String YOUTUBE_URL = "https://www.youtube.com/channel/UCWtD5WQZKiHO7NLSSs-WOQg";
	private String SITE_URL = "https://majestycraft.com/index.php";
	private String DISCORD_URL = "https://discord.gg/qyuuHk4udD";
	public LauncherConfig config;
	public static Media media;
	
	public LauncherPanel(Pane root, GameEngine engine, final LauncherMain launcherMain){

		this.drawBackgroundImage(engine, root, "heading.jpg");
		// Déselectionne la textfield par défaut
	    Platform.runLater( () -> root.requestFocus());	
		
		theGameEngine = engine;
		
		this.config = new LauncherConfig(engine);
		this.config.loadConfiguration();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 70, engine.getHeight());
		this.topRectangle.setFill(Color.rgb(255,255,255, 0.10));
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawImage(engine, getResourceLocation().loadImage(engine, "logo.png"), engine.getWidth() / 2 - 70, 40, 150, 150, root, Mover.DONT_MOVE);
		
		//Partie texte
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Launcher MajestyCraft Optifine + Forge");
		this.titleLabel.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleLabel.setPosition(engine.getWidth() / 2 - 150, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500,  40);
		this.titleLabel.setVisible(false);
		
		// Affiche ou non le statut discord
		if((boolean) config.getValue(EnumConfig.USE_DISCORD)) {
			LauncherMain.discordRPC();
		}
		
		//Music on/off
		if((boolean) config.getValue(EnumConfig.USE_MUSIC)) {
			LauncherMain.muteMusic();
		}
		else {
			LauncherMain.resumeMusic();
		}
		
		//Est censé me donner le CSS mais bon c'est experimental hein 
		LauncherMain.getContentPane().getScene().getStylesheets().add("css/design.css");
		//System.out.println(LauncherMain.contentPane.getScene());

		
		/** ===================== IMAGE DU LOGO EN HAUT ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(engine, "server-icon.png"));
		this.titleImage.setSize(50, 50);
		this.titleImage.setBounds(12, 5, 50, 50);
		
		
		//root.getChildren().addAll(imageanniv);
		
		/** ===================== BOUTON info ===================== */
		this.infoButton = new LauncherButton(root);
		this.infoButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		LauncherImage settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "info.png"));
		settingsImg.setSize(27, 27);
		this.infoButton.setGraphic(settingsImg);
		this.infoButton.setPosition(engine.getWidth() / 2 - 522, engine.getHeight() / 2 - 50);
		this.infoButton.setSize(60, 46);
		this.infoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = new Scene(createInfoPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(900);
				stage.setHeight(600);
				stage.setScene(scene);
				stage.show();
			}
		});
		
		JFXRippler rippler3 = new JFXRippler(this.infoButton);
		rippler3.setLayoutX(engine.getWidth() / 2 - 515);
		rippler3.setLayoutY(engine.getHeight() / 2 - 50);
		rippler3.getStyleClass().add("rippler2");
		root.getChildren().add(rippler3);
		
		/** ===================== BOUTON microsoft ===================== */
		this.microsoftButton = new LauncherButton(root);
		this.microsoftButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		LauncherImage microsoftImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "microsoft.png"));
		microsoftImg.setSize(27, 27);
		this.microsoftButton.setGraphic(microsoftImg);
		this.microsoftButton.setPosition(engine.getWidth() / 2 - 522, engine.getHeight() / 2 - 100);
		this.microsoftButton.setSize(60, 46);
		this.microsoftButton.setOnAction(new EventHandler<ActionEvent>() {
			
			 public void handle(ActionEvent event) {
				 /**
					 * ===================== VERIFICATION USEFORGE =====================
					 */

				 	if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == true) {
						new LauncherAlert("Echec du démarrage d'optifine/forge. ",
								"Impossible de démarrer le jeu avec optifine et forge d'activé."
										+ " \nMerci de ne choisir que l'une des deux options ");
					}
					else if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == false)
					{
						LauncherMain.getGameLinks().JSON_NAME = config.getValue(EnumConfig.VERSION) + ".json";
						switch(engine.getGameLinks().JSON_NAME)
						{
							case "1.9.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.9", "#1938", "20200515.085601");
								break;
							case "1.10.2.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.10.2", "#2511", "20200515.085601");
								break;
							case "1.11.2.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.11.2", "#2588", "20200515.085601");
								break;
							case "1.12.2.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.12.2", "#2847", "20200515.085601");
								break;
							case "1.13.2.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.13.2", "28.2.23", "20200515.085601");
								break;
							case "1.14.4.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.14.4", "28.2.23", "20200515.085601");
								break;
							case "1.15.2.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.15.2", "31.2.45", "20200515.085601");
								break;
							case "1.16.2.json":
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.2", "33.0.61", "20200812.004259");
								break;
							case "1.16.3.json":
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.3", "34.1.42", "20201025.185957");
								break;
							case "1.16.4.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.4", "35.0.1", "20200812.004259");
								break;
							case "1.16.5.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.5", "36.0.42", "20200812.004259");
								break;
							case "1.17.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17/", "1.17.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17", "36.0.42", "20200812.004259");
								break;
							case "1.17.1.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17.1/", "1.17.1.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17.1", "36.0.42", "20200812.004259");
								break;
							case "1.18.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18/", "1.18.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18", "38.0.14", "20200812.004259");
								break;
							case "1.18.1.json":
								LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18.1/", "1.18.1.json");
								engine.setGameStyle(GameStyle.OPTIFINE);
								LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18.1", "39.0.0", "20200812.004259");
								break;
						}
					} else {
						engine.setGameStyle(GameStyle.VANILLA);
					}
				 gameAuthentication = new GameAuth(AccountType.MICROSOFT);
				 showMicrosoftAuth(gameAuthentication);
				 if (gameAuthentication.isLogged()) {
					 update(gameAuthentication);
				 }
			}
		});
		
		/** ===================== RECTANGLE DES CONNEXIONS ===================== */
		this.connexionRectangle = new LauncherRectangle(root,engine.getWidth() / 2 - 188 , engine.getHeight() / 2 - 150 , 380, 320);
		this.connexionRectangle.setArcWidth(50.0);
		this.connexionRectangle.setArcHeight(50.0);
		this.connexionRectangle.setFill(Color.rgb(0, 0, 0, 0.30));
		this.connexionRectangle.setVisible(true);
		
		/** ================================ PARTIE CRACK ================================== */
		
		
		/** ===================== TITRE 1 CRACK ===================== */
		this.titleCrack = new LauncherLabel(root);
		this.titleCrack.setText("Connexion crack");
		this.titleCrack.setFont(Font.font("leadcoat.ttf", FontWeight.BOLD, 25d));
		this.titleCrack.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleCrack.setPosition(engine.getWidth() / 2 - 116, engine.getHeight() / 2- 130);
		this.titleCrack.setSize(500,  40);
		
		JFXRippler rippler2 = new JFXRippler(this.titleCrack);
		rippler2.setLayoutX(engine.getWidth() / 2 - 116);
		rippler2.setLayoutY(engine.getHeight() / 2- 130);
		rippler2.getStyleClass().add("rippler");
		root.getChildren().add(rippler2);
		
		
		/** ===================== NOM D'UTILISATEUR ===================== */
		this.usernameField2 = new JFXTextField();
		this.usernameField2.setText((String)this.config.getValue(EnumConfig.USERNAME));
		this.usernameField2.getStyleClass().add("input");
		this.usernameField2.setLayoutX(engine.getWidth() / 2 - 126);
		this.usernameField2.setLayoutY(engine.getHeight() / 2- 12);
		this.usernameField2.setFont(FontLoader.loadFont("leadcoat.ttf", "Lead Coat", 14F));
		this.usernameField2.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.2); -fx-text-fill: orange; -fx-font-family: leadcoat");
		this.usernameField2.setPromptText("Pseudo ");
		root.getChildren().add(this.usernameField2);
		
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton2 = new JFXButton("Se connecter");
		this.loginButton2.getStyleClass().add("button-raised");
		this.loginButton2.setLayoutX(400);
		this.loginButton2.setLayoutY(480);
		this.loginButton2.setFont(FontLoader.loadFont("../resources/leadcoat.ttf", "leadcoat", 22F));
		this.loginButton2.setOnAction(event -> {
			/**
			 * ===================== VERIFICATION USEFORGE =====================
			 */

			if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == true) {
				new LauncherAlert("Echec du démarrage d'optifine/forge. ",
						"Impossible de démarrer le jeu avec optifine et forge d'activé."
								+ " \nMerci de ne choisir que l'une des deux options ");
			}
			else if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == false)
			{
				LauncherMain.getGameLinks().JSON_NAME = config.getValue(EnumConfig.VERSION) + ".json";
				switch(engine.getGameLinks().JSON_NAME)
				{
					case "1.9.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.9", "#1938", "20200515.085601");
						break;
					case "1.10.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.10.2", "#2511", "20200515.085601");
						break;
					case "1.11.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.11.2", "#2588", "20200515.085601");
						break;
					case "1.12.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.12.2", "#2847", "20200515.085601");
						break;
					case "1.13.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.13.2", "28.2.23", "20200515.085601");
						break;
					case "1.14.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.14.4", "28.2.23", "20200515.085601");
						break;
					case "1.15.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.15.2", "31.2.45", "20200515.085601");
						break;
					case "1.16.2.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.2", "33.0.61", "20200812.004259");
						break;
					case "1.16.3.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.3", "34.1.42", "20201025.185957");
						break;
					case "1.16.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.4", "35.0.1", "20200812.004259");
						break;
					case "1.16.5.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.5", "36.0.42", "20200812.004259");
						break;
					case "1.17.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17/", "1.17.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17", "36.0.42", "20200812.004259");
						break;
					case "1.17.1.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17.1/", "1.17.1.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17.1", "36.0.42", "20200812.004259");
						break;
					case "1.18.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18/", "1.18.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18", "38.0.14", "20200812.004259");
						break;
					case "1.18.1.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18.1/", "1.18.1.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18.1", "39.0.0", "20200812.004259");
						break;
				}
			} 
			else if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == false && (boolean) config.getValue(EnumConfig.USE_FORGE) == true) {
				LauncherMain.getGameLinks().JSON_NAME = config.getValue(EnumConfig.VERSION) + ".json";
				switch(engine.getGameLinks().JSON_NAME)
				{
					case "1.9.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/forge/", "1.9.json");
						engine.setGameStyle(GameStyle.FORGE_1_8_TO_1_12_2);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.9", "#1938", "20200515.085601");
						break;
					case "1.10.2.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/forge/", "1.10.2.json");
						engine.setGameStyle(GameStyle.FORGE_1_8_TO_1_12_2);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.10.2", "#2511", "20200515.085601");
						break;
					case "1.11.2.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/forge/", "1.11.2.json");
						engine.setGameStyle(GameStyle.FORGE_1_8_TO_1_12_2);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.11.2", "#2588", "20200515.085601");
						break;
					case "1.12.2.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/forge/", "1.12.2.json");
						engine.setGameStyle(GameStyle.FORGE_1_8_TO_1_12_2);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.12.2", "#2847", "20200515.085601");
						break;
					case "1.13.2.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/forge/", "1.13.2.json");
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.13.2", "28.2.23", "20200515.085601");
						break;
					case "1.14.4.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/forge/", "1.14.4.json");
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.14.4", "28.2.23", "20200515.085601");
						break;
					case "1.15.2.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/forge/", "1.15.2.json");
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.15.2", "31.2.57", "20200515.085601");
						break;
					case "1.16.2.json": //NOT GOOD
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.2", "33.0.61", "20200812.004259");
						break;
					case "1.16.3.json": //NOT GOOD
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.3", "34.1.42", "20201025.185957");
						break;
					case "1.16.4.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/forge/", "1.16.4.json");
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.4", "35.0.1", "20200812.004259");
						break;
					case "1.16.5.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/forge/", "1.16.5.json");
						engine.setGameStyle(GameStyle.FORGE_1_13_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.5", "36.0.42", "20200812.004259");
						break;
					case "1.17.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17/forge/", "1.17.json");
						engine.setGameStyle(GameStyle.FORGE_1_17_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17", "36.0.42", "20200812.004259");
						break;
					case "1.17.1.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17.1/forge/", "1.17.1.json");
						engine.setGameStyle(GameStyle.FORGE_1_17_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17.1", "36.0.42", "20200812.004259");
						break;
					case "1.18.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18/forge/", "1.18.json");
						engine.setGameStyle(GameStyle.FORGE_1_17_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18", "38.0.14", "20200812.004259");
						break;
					case "1.18.1.json": //NOT GOOD
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18.1/forge/", "1.18.1.json");
						engine.setGameStyle(GameStyle.FORGE_1_17_HIGHER);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18.1", "39.0.0", "20200812.004259");
						break;
				}
			}
			else {
				engine.setGameStyle(GameStyle.VANILLA);
			}
			this.loginButton.setVisible(false);
			/**
			 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
			 */

			config.updateValue("username", usernameField2.getText());
			config.updateValue("password", "");
			if (usernameField2.getText().length() < 3) {
				new LauncherAlert("Authentification echouee",
						"Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			} else if (usernameField2.getText().length() > 3) {
				gameAuthentication = new GameAuth(usernameField2.getText(), passwordField.getText(), AccountType.OFFLINE);
				 if (gameAuthentication.isLogged()) {
					 update(gameAuthentication);
				}
				connectAccountCrackCO(root);
			}
		});
		root.getChildren().add(this.loginButton2);

		
		/** ================================ PARTIE PREMIUM ================================== */
		
		/** ===================== CHECKBOX SE SOUVENIR ===================== */

		
		this.rememberMe = new JFXToggleButton();;
		this.rememberMe.setText("Se souvenir de moi");
		this.rememberMe.setSelected((boolean) config.getValue(EnumConfig.REMEMBER_ME));
		this.rememberMe.getStyleClass().add("jfx-toggle-button");
		this.rememberMe.setLayoutX(385);
		this.rememberMe.setLayoutY(427);
		this.rememberMe.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("rememberme", "" + rememberMe.isSelected());
				config.updateValues(configMap);
			}
		});
		
		root.getChildren().add(this.rememberMe);

		
		/** ===================== TITRE 1 PREMIUM ===================== */
		this.titlePremium = new LauncherLabel(root);
		this.titlePremium.setText("Connexion premium");
		this.titlePremium.setFont(Font.font("FontName", FontWeight.BOLD, 25d));
		this.titlePremium.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titlePremium.setPosition(engine.getWidth() / 2 - 116, engine.getHeight() / 2- 130);
		//this.titlePremium.setOpacity(0.7);
		this.titlePremium.setSize(500,  40);
		this.titlePremium.setVisible(true);
		
		JFXRippler rippler = new JFXRippler(this.titlePremium);
		rippler.setLayoutX(engine.getWidth() / 2 - 116);
		rippler.setLayoutY(engine.getHeight() / 2- 130);
		rippler.getStyleClass().add("rippler");
		root.getChildren().add(rippler);
		
		/** ===================== NOM D'UTILISATEUR ===================== */
		this.usernameField = new JFXTextField("Votre email");
		this.usernameField.setPromptText("Votre email");
		this.usernameField.getStyleClass().add("input");
		this.usernameField.setLayoutX(engine.getWidth() / 2 - 126);
		this.usernameField.setLayoutY(engine.getHeight() / 2- 42);
		this.usernameField.setText((String)this.config.getValue(EnumConfig.USERNAME));
		this.usernameField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.2); -fx-text-fill: orange");
		root.getChildren().add(this.usernameField);
		
		/** ===================== MOT DE PASSE ===================== */
		this.passwordField = new JFXPasswordField();
		this.passwordField.setLayoutX(engine.getWidth() / 2 - 126);
		this.passwordField.setLayoutY(engine.getHeight() / 2 + 15);
		this.passwordField.getStyleClass().add("input");
		this.passwordField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.passwordField.setPromptText("Mot de passe");
		
		//Verif si la case "se souvenir de moi" est coché
		if((boolean) config.getValue(EnumConfig.REMEMBER_ME) == true) 
		{
			passwordField.setText((String) config.getValue(EnumConfig.PASSWORD));
		} 
		else 
		{
			passwordField.setText("");
		} 
		
		root.getChildren().add(this.passwordField);
		
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new JFXButton("Se connecter");
		this.loginButton.getStyleClass().add("button-raised");
		this.loginButton.setLayoutX(400);
		this.loginButton.setLayoutY(480);
		this.loginButton.setFont(FontLoader.loadFont("../resources/leadcoat.ttf", "leadcoat", 22F));
		//this.loginButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.loginButton.setOnAction(event -> {
			/**
			 * ===================== VERIFICATION USEFORGE =====================
			 */
			
			if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == true) {
				new LauncherAlert("Echec du démarrage d'optifine/forge. ",
						"Impossible de démarrer le jeu avec optifine et forge d'activé."
								+ " \nMerci de ne choisir que l'une des deux options ");
			}
			else if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == false)
			{
				LauncherMain.getGameLinks().JSON_NAME = config.getValue(EnumConfig.VERSION) + ".json";
				switch(engine.getGameLinks().JSON_NAME)
				{
					case "1.9.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.9", "#1938", "20200515.085601");
						break;
					case "1.10.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.10.2", "#2511", "20200515.085601");
						break;
					case "1.11.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.11.2", "#2588", "20200515.085601");
						break;
					case "1.12.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.12.2", "#2847", "20200515.085601");
						break;
					case "1.13.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.13.2", "28.2.23", "20200515.085601");
						break;
					case "1.14.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.14.4", "28.2.23", "20200515.085601");
						break;
					case "1.15.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.15.2", "31.2.45", "20200515.085601");
						break;
					case "1.16.2.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.2", "33.0.61", "20200812.004259");
						break;
					case "1.16.3.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.3", "34.1.42", "20201025.185957");
						break;
					case "1.16.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.4", "35.0.1", "20200812.004259");
						break;
					case "1.16.5.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.5", "36.0.42", "20200812.004259");
						break;
					case "1.17.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17/", "1.17.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17", "36.0.42", "20200812.004259");
						break;
					case "1.17.1.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17.1/", "1.17.1.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17.1", "36.0.42", "20200812.004259");
						break;
					case "1.18.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18/", "1.18.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18", "38.0.14", "20200812.004259");
						break;
					case "1.18.1.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18.1/", "1.18.1.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18.1", "39.0.0", "20200812.004259");
						break;
				}
			}else {
				engine.setGameStyle(GameStyle.VANILLA);
			}
			config.updateValue("username", usernameField.getText());
			if((boolean) config.getValue(EnumConfig.REMEMBER_ME) == true) 
			{
				config.updateValue("password", passwordField.getText());
			} 
			else 
			{
				config.updateValue("password", "");
			} 
			/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
			if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
				gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.MOJANG);
				if (gameAuthentication.isLogged()) {
					update(gameAuthentication);
					connectAccountPremiumCO(usernameField.getText(), root);
				} else {
					new LauncherAlert("Authentification echouée!",
							"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
									+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
									+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang. \nAssurez-vous de bien utiliser votre email dans le  cas d'une connexion avec un compte Mojang !");
				}
			} else {
				new LauncherAlert("Authentification echouée!",
						"Impossible de se connecter, l'authentification semble etre une authentification 'hors-ligne'"
								+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres.");
			}
		});
		
		root.getChildren().add(this.loginButton);
		
		if((boolean) config.getValue(EnumConfig.REMEMBER_ME) == true) 
		{
			config.updateValue("password", passwordField.getText());
		} 
		else 
		{
			config.updateValue("password", "");
		} 
		/** ===================== CHECKBOX SE SOUVENIR ===================== */

        toggleButton = new JFXToggleButton();
        toggleButton.setText("Connexion premium");
        toggleButton.getStyleClass().add("jfx-toggle-button");
        toggleButton.setLayoutX(385);
        toggleButton.setLayoutY(277);
        toggleButton.setSelected((boolean) config.getValue(EnumConfig.USE_PREMIUM));
        toggleButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("usePremium", "" + toggleButton.isSelected());
				config.updateValues(configMap);
				if(toggleButton.isSelected()) {
					usernameField.setDisable(false);
					passwordField.setDisable(false);
					rememberMe.setDisable(false);
					new BounceInUp(titlePremium).play();
					loginButton2.setVisible(false);
					new FadeOut(usernameField2).setResetOnFinished(false).play();
					new FadeOutDown(titleCrack).setResetOnFinished(false).play();
					new RotateOut(avatar).setResetOnFinished(false).play();
					usernameField2.setDisable(true);
					
					loginButton.setVisible(true);
					usernameField.setVisible(true);
					titlePremium.setVisible(true);
					passwordField.setVisible(true);
					new BounceInLeft(usernameField).play();
					new BounceInLeft(passwordField).play();
					
					GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
							AccountType.MOJANG);
					if (auth.isLogged()) {
						connectAccountPremium(auth.getSession().getUsername(), root);
					}
					else {
						connectAccountPremiumOFF(root);
					}
					new RotateIn(avatar).play();
					rememberMe.setVisible(true);
					new ZoomInLeft(rememberMe).play();
				}
				else {
					new BounceInUp(titleCrack).play();
					loginButton.setVisible(false);
					new FadeOut(usernameField).setResetOnFinished(false).play();
					new FadeOutDown(titlePremium).setResetOnFinished(false).play();
					new FadeOut(passwordField).setResetOnFinished(false).play();
					new RotateOut(avatar).play();
					new FadeOut(rememberMe).setResetOnFinished(false).play();
					usernameField.setDisable(true);
					passwordField.setDisable(true);
					rememberMe.setDisable(true);
					
					connectAccountCrack(root);
					
					new RotateIn(avatar).play();					
					loginButton2.setVisible(true);
					usernameField2.setVisible(true);
					new BounceInLeft(usernameField2).play();
					titleCrack.setVisible(true);
					usernameField2.setDisable(false);
				}
			}
		});
		root.getChildren().add(toggleButton);
		
		
		if((boolean) config.getValue(EnumConfig.USE_PREMIUM) == true) 
		{
			new BounceInUp(this.titlePremium).play();
			this.loginButton2.setVisible(false);
			this.usernameField2.setVisible(false);
			this.titleCrack.setVisible(false);
		} 
		else 
		{
			new BounceInUp(this.titleCrack).play();
			this.loginButton.setVisible(false);
			this.usernameField.setVisible(false);
			this.titlePremium.setVisible(false);
			this.passwordField.setVisible(false);
			this.rememberMe.setVisible(false);
		} 
		
		/** =============== LOGIN AUTOMATIQUE (CRACK ET PREMIUM) =============== **/
		this.autoLoginRectangle = new LauncherRectangle(root, 0, theGameEngine.getHeight() - 32, 2000, theGameEngine.getHeight());
		this.autoLoginRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.autoLoginRectangle.setOpacity(1.0);
		this.autoLoginRectangle.setVisible(false);
		
		/** ===================== MESSAGE AUTOLOGIN ===================== */
		this.autoLoginLabel = new LauncherLabel(root);
		this.autoLoginLabel.setText("Connexion auto dans 3 secondes. Appuyez sur ECHAP pour annuler.");
		this.autoLoginLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.autoLoginLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
		this.autoLoginLabel.setPosition(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() - 34);
		this.autoLoginLabel.setOpacity(0.7);
		this.autoLoginLabel.setSize(700, 40);
		this.autoLoginLabel.setVisible(false);
		
		/** ===================== ANNULER AUTOLOGIN ===================== */
		this.autoLoginButton = new LauncherButton(root);
		this.autoLoginButton.setText("Annuler");
		this.autoLoginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLoginButton.setPosition(theGameEngine.getWidth() / 2 + 60, theGameEngine.getHeight() - 30);
		this.autoLoginButton.setSize(200, 20);
		this.autoLoginButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-text-fill: black;");
		this.autoLoginButton.setVisible(false);
		this.autoLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				autoLoginTimer.cancel();
				autoLoginLabel.setVisible(false);
				autoLoginButton.setVisible(false);
				autoLoginRectangle.setVisible(false);
			}
		});
		String userName = (String)this.config.getValue(EnumConfig.USERNAME);
		
		if (userName.length() > 2 && (boolean)this.config.getValue(EnumConfig.AUTOLOGIN).equals(true)) {
			Platform.runLater(new Runnable() {
				public void run() {
					autoLoginTimer = new Timer();
					TimerTask timerTask = new TimerTask() {
						int waitTime = 5;
						int elapsed = 0;
						@Override
						
						public void run() {
							/**
							 * ===================== VERIFICATION USEFORGE =====================
							 */
							if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == true) {
								new LauncherAlert("Echec du démarrage d'optifine/forge. ",
										"Impossible de démarrer le jeu avec optifine et forge d'activé."
												+ " \nMerci de ne choisir que l'une des deux options ");
							}
							else if((boolean) config.getValue(EnumConfig.USE_OPTIFINE) == true && (boolean) config.getValue(EnumConfig.USE_FORGE) == false)
							{
								LauncherMain.getGameLinks().JSON_NAME = config.getValue(EnumConfig.VERSION) + ".json";
								switch(engine.getGameLinks().JSON_NAME)
								{
									case "1.9.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.9", "#1938", "20200515.085601");
										break;
									case "1.10.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.10.2", "#2511", "20200515.085601");
										break;
									case "1.11.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.11.2", "#2588", "20200515.085601");
										break;
									case "1.12.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.12.2", "#2847", "20200515.085601");
										break;
									case "1.13.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.13.2", "28.2.23", "20200515.085601");
										break;
									case "1.14.4.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.14.4", "28.2.23", "20200515.085601");
										break;
									case "1.15.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.15.2", "31.2.45", "20200515.085601");
										break;
									case "1.16.2.json":
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.2", "33.0.61", "20200812.004259");
										break;
									case "1.16.3.json":
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.3", "34.1.42", "20201025.185957");
										break;
									case "1.16.4.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.4", "35.0.1", "20200812.004259");
										break;
									case "1.16.5.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.16.5", "36.0.42", "20200812.004259");
										break;
									case "1.17.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17/", "1.17.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17", "36.0.42", "20200812.004259");
										break;
									case "1.17.1.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.17.1/", "1.17.1.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.17.1", "36.0.42", "20200812.004259");
										break;
									case "1.18.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18/", "1.18.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18", "38.0.14", "20200812.004259");
										break;
									case "1.18.1.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.18.1/", "1.18.1.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge(Forge.FML_CLIENT, "1.18.1", "39.0.0", "20200812.004259");
										break;
								}
							} else {
								engine.setGameStyle(GameStyle.VANILLA);
							}
							
							
							
							elapsed++;
							//S'execute à la fin du compte à rebours
							if (elapsed % waitTime == 0) { 
								if (!theGameEngine.getGameMaintenance().isAccessBlocked()) {
									/**
									 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
									 */
									if (usernameField2.getText().length() > 3 && passwordField.getText().isEmpty()) {
										autoLoginTimer.cancel();
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);
										gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(),
												AccountType.OFFLINE);
										update(gameAuthentication);

										
									}
									
									/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
									else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
										autoLoginTimer.cancel();
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);

										gameAuthentication = new GameAuth(usernameField.getText(), passwordField.getText(),
												AccountType.MOJANG);
										if (gameAuthentication.isLogged()) {
											update(gameAuthentication);
										} 
										else {
											autoLoginLabel.setVisible(false);
											autoLoginButton.setVisible(false);
											autoLoginRectangle.setVisible(false);
											autoLoginTimer.cancel();
											//Ceci est nécessaire pour éviter de faire planter. Le LauncherAlert ne peut s'afficher lors de l'utilisation d'un "time"
											Platform.runLater(() -> {
											new LauncherAlert("Authentification echouée!",
													"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
															+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
															+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang. \nAssurez-vous de bien utiliser votre email dans le  cas d'une connexion avec un compte Mojang !");
											});
										}
									}
									else {
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);
										autoLoginTimer.cancel();
										Platform.runLater(() -> {
											new LauncherAlert("Authentification echouée!",
													"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
															+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le mdp soit bien saisi."
															+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang. \nAssurez-vous de bien utiliser votre email dans le  cas d'une connexion avec un compte Mojang !");
											});
									}
								}
							} 
							/** ===================== MESSAGE DE CONNEXION AUTO ===================== */
							else {
								final int time = (waitTime - (elapsed % waitTime));
								Platform.runLater(new Runnable() {
									public void run() {
										autoLoginLabel.setText("Connexion auto dans " + time + " secondes.");

									}
								});
							}
						}
					};
					autoLoginTimer.schedule(timerTask, 0, 1000);
					autoLoginLabel.setVisible(true);
					autoLoginRectangle.setVisible(true);
					autoLoginButton.setVisible(true);
				}
			});
		 

		}
		
		/** ===================== BOUTON PARAMETRE ===================== */
		this.settingsButton = new LauncherButton(root);
		this.settingsButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "settings.png"));
		settingsImg.setSize(27, 27);
		this.settingsButton.setGraphic(settingsImg);
		this.settingsButton.setPosition(engine.getWidth() / 2 - 522, engine.getHeight() / 2 );
		this.settingsButton.setSize(60, 46);
		this.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = new Scene(createSettingsPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(900);
				stage.setHeight(600);
				stage.setScene(scene);
				stage.show();
			}
		});
		
		JFXRippler rippler4 = new JFXRippler(this.settingsButton);
		rippler4.setLayoutX(engine.getWidth() / 2 - 515);
		rippler4.setLayoutY( engine.getHeight() / 2 );
		rippler4.getStyleClass().add("rippler2");
		root.getChildren().add(rippler4);
		
		/** ===================== BOUTON easter egg 1 ===================== */
		this.lolButton = new LauncherButton(root);
		this.lolButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "LOL.png"));
		settingsImg.setSize(27, 27);
		this.lolButton.setGraphic(settingsImg);
		this.lolButton.setPosition(engine.getWidth() / 2 - 522, engine.getHeight() / 2 +50);
		this.lolButton.setSize(60, 46);
		this.lolButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
			}
		});
		
		/** ===================== BOUTON easter egg 2 ===================== */
		this.deadButton = new LauncherButton(root);
		this.deadButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "dead.png"));
		settingsImg.setSize(22, 27);
		this.deadButton.setGraphic(settingsImg);
		this.deadButton.setPosition(engine.getWidth() / 2 + 467, engine.getHeight() / 2 + 330);
		this.deadButton.setSize(60, 46);
		this.deadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink("https://youtu.be/koQN49gW5fE?t=31");
			}
		});
		
		/** ===================== BOUTON easter egg 1 ===================== */
		this.lolButton2 = new LauncherButton(root);
		this.lolButton2.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "lol2.PNG"));
		settingsImg.setSize(27, 27);
		this.lolButton2.setGraphic(settingsImg);
		this.lolButton2.setPosition(engine.getWidth() / 2 - 522, engine.getHeight() / 2 +300);
		this.lolButton2.setSize(60, 60);
		this.lolButton2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Les animations de l'easter egg
				new Hinge(microsoftButton).setResetOnFinished(true).play();
				new Hinge(infoButton).setResetOnFinished(true).play();
				new Hinge(settingsButton).setResetOnFinished(true).play();
				new Hinge(lolButton).setResetOnFinished(true).play();
				new Hinge(boutiqueButton).setResetOnFinished(true).play();
				new Hinge(avatar).setResetOnFinished(true).play();
				new Hinge(getMinestratorButton()).setResetOnFinished(true).play();
				new Hinge(getTwitterButton()).setResetOnFinished(true).play();
				new Hinge(getTiktokButton()).setResetOnFinished(true).play();
				new Hinge(getYoutubeButton()).setResetOnFinished(true).play();
				new Hinge(deadButton).setResetOnFinished(true).play();
				new Hinge(titleImage).setResetOnFinished(true).play();
				new Hinge(toggleButton).setResetOnFinished(true).play();
				new Hinge(siteButton).setResetOnFinished(true).play();
				new Hinge(voteButton).setResetOnFinished(true).play();
				new Hinge(connexionRectangle).setResetOnFinished(true).play();
				
				if(toggleButton.isSelected()) {
					new Hinge(rememberMe).setResetOnFinished(true).play();
					new Hinge(titlePremium).setResetOnFinished(true).play();
					new Hinge(usernameField).setResetOnFinished(true).play();
					new Hinge(passwordField).setResetOnFinished(true).play();
					new Hinge(loginButton).setResetOnFinished(true).play();
				}else {
					new Hinge(usernameField2).setResetOnFinished(true).play();
					new Hinge(loginButton2).setResetOnFinished(true).play();
					new Hinge(titleCrack).setResetOnFinished(true).play();
				}
			}
		});
		
		/** ===================== BOUTON FERMETURE ===================== */
		this.closeButton = new LauncherButton(root);
		//this.closeButton.setInvisible();
		LauncherImage closeImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "close.png"));
		closeImg.setSize(15, 15);
		this.closeButton.setGraphic(closeImg);
		this.closeButton.setBackground(null);
		this.closeButton.setPosition(engine.getWidth() - 35, 2);
		this.closeButton.setSize(15, 15);
		this.closeButton.setOnAction(event -> {
			final BounceOutDown animation = new BounceOutDown(LauncherMain.getContentPane());
			animation.setOnFinished(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(final ActionEvent actionEvent) {
	    			System.exit(0);
	            }
	        });
			animation.play();
		});
		
		/** ===================== BOUTON REDUIRE ===================== */
		this.reduceButton = new LauncherButton(root);
		//this.reduceButton.setInvisible();
		LauncherImage reduceImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "reduce.png"));
		reduceImg.setSize(15, 15);
		this.reduceButton.setGraphic(reduceImg);
		this.reduceButton.setBackground(null);
		this.reduceButton.setPosition(engine.getWidth() - 65, 2);
		this.reduceButton.setSize(15, 15);
		this.reduceButton.setOnAction(event -> {
			final ZoomOutDown animation2 = new ZoomOutDown(LauncherMain.getContentPane());
			animation2.setOnFinished(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(final ActionEvent actionEvent) {
	    			Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
	    			stage.setIconified(true);
	            }
	        });
			animation2.setResetOnFinished(true);
			animation2.play();
		});
		
		/** ===================== BOUTON URL VOTE ===================== */
		this.voteButton = new LauncherButton(root);
		this.voteButton.setText("Site");
		this.voteButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.voteButton.setPosition(engine.getWidth() / 2 - 260,  engine.getHeight() / 2 + 190);
		this.voteButton.setSize(250,  45);
		this.voteButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.voteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(SITE_URL);
			}
		});
		
		
		/** ===================== BOUTON URL BOUTIQUE ===================== */
		this.boutiqueButton = new LauncherButton(root);
		this.boutiqueButton.setText("Discord");
		this.boutiqueButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.boutiqueButton.setPosition(engine.getWidth() / 2 - 125 + 130,  engine.getHeight() / 2 + 190);
		this.boutiqueButton.setSize(250,  45);
		this.boutiqueButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.boutiqueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(DISCORD_URL);
			}
		});
		
		/** ===================== BOUTON URL SITE ===================== */
		this.siteButton = new LauncherButton(root);
		this.siteButton.setText("Site");
		this.siteButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.siteButton.setPosition(engine.getWidth() /2 - 500,  engine.getHeight() - 107);
		this.siteButton.setSize(200,  45);
		this.siteButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.siteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(SITE_URL);
			}
		});
		this.siteButton.setVisible(false);
		
		/** ===================== BOUTON URL FORUM ===================== */
		this.forumButton = new LauncherButton(root);
		this.forumButton.setText("Discord");
		this.forumButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.forumButton.setPosition(engine.getWidth() /2 + 300,  engine.getHeight() - 107);
		this.forumButton.setSize(200,  45);
		this.forumButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.forumButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(DISCORD_URL);
			}
		});
		this.forumButton.setVisible(false);
		
		/** ===================== BOUTON URL MINESTRATOR ===================== */
		setMinestratorButton(new LauncherButton(root));
		getMinestratorButton().setInvisible();
		getMinestratorButton().setPosition(engine.getWidth() / 2 - 125, engine.getHeight() - 130);
		LauncherImage facebookImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "minestrator.png"));
		facebookImg.setSize(80, 80);
		getMinestratorButton().setGraphic(facebookImg);
		getMinestratorButton().setSize((int) facebookImg.getFitWidth(), (int) facebookImg.getFitHeight());
		getMinestratorButton().setBackground(null);
		getMinestratorButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(MINESTRATOR_URL);
			}
		});
		/** ===================== BOUTON URL TWITTER ===================== */
		setTwitterButton(new LauncherButton(root));
		getTwitterButton().setInvisible();
		getTwitterButton().setPosition(engine.getWidth() / 2 + 25, engine.getHeight() - 130);
		LauncherImage twitterImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "twitter_icon.png"));
		twitterImg.setSize(80, 80);
		getTwitterButton().setGraphic(twitterImg);
		getTwitterButton().setSize((int) twitterImg.getFitWidth(), (int) twitterImg.getFitHeight());
		getTwitterButton().setBackground(null);
		getTwitterButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(TWITTER_URL);
			}
		});
		/** ===================== BOUTON URL TIKTOK ===================== */
		setTiktokButton(new LauncherButton(root));
		getTiktokButton().setInvisible();
		getTiktokButton().setPosition(engine.getWidth() / 2 - 125 - 150, engine.getHeight() - 130);
		LauncherImage tiktokImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "tiktok.png"));
		tiktokImg.setSize(80, 80);
		getTiktokButton().setGraphic(tiktokImg);
		getTiktokButton().setSize((int) tiktokImg.getFitWidth(), (int) tiktokImg.getFitHeight());
		getTiktokButton().setBackground(null);
		getTiktokButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(INSTAGRAM_URL);
			}
		});
		/** ===================== BOUTON URL YOUTUBE ===================== */
		setYoutubeButton(new LauncherButton(root));
		getYoutubeButton().setInvisible();
		getYoutubeButton().setPosition(engine.getWidth() / 2 - 125 + 300, engine.getHeight() - 130);
		LauncherImage youtubeImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "yt_icon.png"));
		youtubeImg.setSize(80, 80);
		getYoutubeButton().setGraphic(youtubeImg);
		getYoutubeButton().setSize((int) youtubeImg.getFitWidth(), (int) youtubeImg.getFitHeight());
		getYoutubeButton().setBackground(null);
		getYoutubeButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(YOUTUBE_URL);
			}
		});
		
		/** ===================== RECTANGLE DE MISE A JOURS ===================== */
		this.updateRectangle = new LauncherRectangle(root, engine.getWidth() / 2 - 175, engine.getHeight() / 2 - 80, 350, 180);
		this.updateRectangle.setArcWidth(50.0);
		this.updateRectangle.setArcHeight(50.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.60));
		this.updateRectangle.setVisible(false);
		
		/** =============== LABEL TITRE MISE A JOUR =============== **/
		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("- MISE A JOUR -");
		this.updateLabel.setAlignment(Pos.CENTER);
		this.updateLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.updateLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: orange;");
		this.updateLabel.setPosition(engine.getWidth() / 2 - 95, engine.getHeight() / 2 - 75);
		this.updateLabel.setOpacity(1);
		this.updateLabel.setSize(190, 40);
		this.updateLabel.setVisible(false);
		/** =============== ETAPE DE MISE A JOUR =============== **/
		this.currentStep = new LauncherLabel(root);
		this.currentStep.setText("Preparation de la mise a jour.");
		this.currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 18F)); // FontPosture.ITALIC
		this.currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: orange;");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setPosition(engine.getWidth() / 2 - 160, engine.getHeight() / 2 + 63);
		this.currentStep.setOpacity(0.4);
		this.currentStep.setSize(320, 40);
		this.currentStep.setVisible(false);
		/** =============== FICHIER ACTUEL EN TELECHARGEMENT =============== **/
		this.currentFileLabel = new LauncherLabel(root);
		this.currentFileLabel.setText("launchwrapper-12.0.jar");
		this.currentFileLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.currentFileLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: orange;");
		this.currentFileLabel.setAlignment(Pos.CENTER);
		this.currentFileLabel.setPosition(engine.getWidth() / 2 - 160, engine.getHeight() / 2 + 5);
		this.currentFileLabel.setOpacity(0.8);
		this.currentFileLabel.setSize(320, 40);
		this.currentFileLabel.setVisible(false);
		/** =============== POURCENTAGE =============== **/
		this.percentageLabel = new LauncherLabel(root);
		this.percentageLabel.setText("0%");
		this.percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 30F));
		this.percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: orange;");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setPosition(engine.getWidth() / 2 - 50, engine.getHeight() / 2 - 25);
		this.percentageLabel.setOpacity(0.8);
		this.percentageLabel.setSize(100, 40);
		this.percentageLabel.setVisible(false);
		
		/** ===================== BARRE DE CHARGEMENT ===================== */
		this.bar = new JFXProgressBar();
		this.bar.setLayoutX(engine.getWidth() / 2 - 125);
		this.bar.setLayoutY(engine.getHeight() / 2 + 40);
		this.bar.getStyleClass().add("jfx-progress-bar");
		//this.bar.setSize(250, 20);
		this.bar.setVisible(false);
		root.getChildren().add(this.bar);
		/** =============== AFFICHAGE DE LA TETE DU JOUEUR =============== **/
			if((boolean) config.getValue(EnumConfig.USE_PREMIUM) == false) {
				if(usernameField.getText().contains("@")) {
					this.usernameField2.setText("");
				}
				this.usernameField.setText("");
			}
			if (usernameField.getText().length() > 3 && usernameField.getText().contains("@")) {
				if (!passwordField.getText().isEmpty()) {
					GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
							AccountType.MOJANG);
					if (auth.isLogged()) {
						this.usernameField2.setText("");
						connectAccountPremium(auth.getSession().getUsername(), root);
						connectAccountPremiumCO(auth.getSession().getUsername(), root);
					}
					else {
						this.usernameField2.setText("");
						connectAccountPremiumOFF(root);
						connectAccountCrackCO(root);
					}
				}
				else {
					this.usernameField2.setText("");
					connectAccountPremiumOFF(root);
					connectAccountCrackCO(root);
				}
			}
			else if((boolean) config.getValue(EnumConfig.USE_PREMIUM) == true){
				connectAccountPremiumOFF(root);
				connectAccountCrackCO(root);
			}
			else {
				this.usernameField.setText("");
				this.rememberMe.setSelected(false);
				connectAccountCrack(root);
				connectAccountCrackCO(root);
			}
		}

		
	public void update(GameAuth auth) {
			new ZoomOutDown(this.microsoftButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.infoButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.settingsButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.lolButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.usernameField2).setResetOnFinished(false).play();
			new ZoomOutDown(this.usernameField).setResetOnFinished(false).play();
			new ZoomOutDown(this.passwordField).setResetOnFinished(false).play();
			new ZoomOutDown(this.boutiqueButton).setResetOnFinished(false).play();
			new ZoomOutDown(avatar).setResetOnFinished(false).play();
			new ZoomOutDown(this.titlePremium).setResetOnFinished(false).play();
			new ZoomOutDown(this.titleCrack).setResetOnFinished(false).play();
			new ZoomOutDown(getMinestratorButton()).setResetOnFinished(false).play();
			new ZoomOutDown(getTwitterButton()).setResetOnFinished(false).play();
			new ZoomOutDown(getTiktokButton()).setResetOnFinished(false).play();
			new ZoomOutDown(getYoutubeButton()).setResetOnFinished(false).play();
			new ZoomOutDown(this.deadButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.rememberMe).setResetOnFinished(false).play();
			new ZoomOutDown(this.loginButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.loginButton2).setResetOnFinished(false).play();
			new ZoomOutDown(this.toggleButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.siteButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.voteButton).setResetOnFinished(false).play();
			new ZoomOutDown(this.connexionRectangle).setResetOnFinished(false).play();
			new ZoomOutDown(this.lolButton2).setResetOnFinished(false).play();
		 
			this.usernameField.setDisable(true);
			this.connexionRectangle.setDisable(true);
			this.usernameField2.setDisable(true);
			this.titlePremium.setDisable(true);
			this.titleCrack.setDisable(true);
			this.rememberMe.setDisable(true);
			this.passwordField.setDisable(true);
			this.loginButton.setDisable(true);
			this.loginButton2.setDisable(true);
			this.settingsButton.setDisable(true);
			
			this.updateRectangle.setVisible(true);
			this.updateLabel.setVisible(true);
			this.currentStep.setVisible(true);
			this.currentFileLabel.setVisible(true);
			this.percentageLabel.setVisible(true);
			this.bar.setVisible(true);
			avatar.setVisible(false);
			new ZoomInDown(this.updateRectangle).play();
			new ZoomInDown(this.updateLabel).play();
			new ZoomInDown(this.currentStep).play();
			new ZoomInDown(this.currentFileLabel).play();
			new ZoomInDown(this.percentageLabel).play();
			new ZoomInDown(this.bar).play();
			new ZoomInDown(avatar3).play();
			avatar3.setVisible(true);
			theGameEngine.reg(LauncherMain.gameLinks);
			theGameEngine.getGameLinks().JSON_URL = theGameEngine.getGameLinks().BASE_URL + this.config.getValue(EnumConfig.VERSION) + ".json";
			this.gameUpdater.reg(theGameEngine);
			this.gameUpdater.reg(auth.getSession());
			
			/**
			 * Change settings in GameEngine from launcher_config.json
			 */
			theGameEngine.reg(GameMemory.getMemory(Double.parseDouble((String) this.config.getValue(EnumConfig.RAM))));
			theGameEngine.reg(GameSize.getWindowSize(Integer.parseInt((String) this.config.getValue(EnumConfig.GAME_SIZE))));
			boolean useVmArgs = (Boolean)config.getValue(EnumConfig.USE_VM_ARGUMENTS);
			String vmArgs = (String) config.getValue(EnumConfig.VM_ARGUMENTS);
			String[] s = null;
			if (useVmArgs) {
				if (vmArgs.length() > 3) {
					s = vmArgs.split(" ");
				}
				JVMArguments arguments = new JVMArguments(s);
				theGameEngine.reg(arguments);
			}
			/** END */
			
			theGameEngine.reg(this.gameUpdater);

			this.updateThread = new Thread() {
				public void run() {
					theGameEngine.getGameUpdater().run();
				}
			};
			this.updateThread.start();
			/**
			 * ===================== REFAICHIR LE NOM DU FICHIER, PROGRESSBAR, POURCENTAGE
			 * =====================
			 **/
			this.timeline = new Timeline(
					new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							timelineUpdate(theGameEngine);
						}
					}, new javafx.animation.KeyValue[0]),
							new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
			this.timeline.setCycleCount(Animation.INDEFINITE);
			this.timeline.play();

		}

	public void timelineUpdate(GameEngine engine) {
		if (engine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(
					engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentFileLabel.setText(engine.getGameUpdater().getCurrentFile());
		this.currentStep.setText(engine.getGameUpdater().getCurrentInfo());
		double percent = (engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload
				/ 100.0D);
		this.bar.setProgress(percent);
	}
		
		private Parent createSettingsPanel() {
			LauncherMain.contentPane = new LauncherPane(theGameEngine);
			Rectangle rect = new Rectangle(1000, 750);
			rect.setArcHeight(15.0);
			rect.setArcWidth(15.0);
			LauncherMain.getContentPane().setClip(rect);
			LauncherMain.getContentPane().setStyle("-fx-background-color: transparent;");
			new LauncherSettings(LauncherMain.getContentPane(), theGameEngine, this);
			new ZoomInLeft(rect).play();
			return LauncherMain.getContentPane();
		}
		
		private Parent createInfoPanel() {
			LauncherMain.contentPane = new LauncherPane(theGameEngine);
			Rectangle rect = new Rectangle(1500, 900);
			rect.setArcHeight(15.0);
			rect.setArcWidth(15.0);
			LauncherMain.getContentPane().setClip(rect);
			LauncherMain.getContentPane().setStyle("-fx-background-color: transparent;");
			new LauncherInfo(LauncherMain.getContentPane(), theGameEngine, this);
			new ZoomInLeft(rect).setResetOnFinished(true).play();
			return LauncherMain.getContentPane();
		}
		
		
		
		public void update2(GameAuth auth) {
			gameUpdater.reg(auth.getSession());
			theGameEngine.reg(this.gameUpdater);

		}
		
		public static void connectAccountCrack(Pane root)
		{
			avatar = new LauncherImage(root, new Image("https://minotar.net/cube/MHF_Steve.png"));
			avatar.setBounds(theGameEngine.getWidth() / 2 - 182, theGameEngine.getHeight() / 2- 12, 50, 60);
		}
		
		public static void connectAccountPremium(String username, Pane root) 
		{
			avatar = new LauncherImage(root, new Image("https://minotar.net/cube/" + username + ".png"));
			avatar.setBounds(theGameEngine.getWidth() / 2 - 182, theGameEngine.getHeight() / 2- 42, 50, 60);
		}
		
		public static void connectAccountPremiumOFF(Pane root) 
		{
			avatar = new LauncherImage(root, new Image("https://minotar.net/cube/MHF_Steve.png"));
			avatar.setBounds(theGameEngine.getWidth() / 2 - 182, theGameEngine.getHeight() / 2- 42, 50, 60);
		}
		
		public static void connectAccountCrackCO(Pane root)
		{
			avatar3 = new LauncherImage(root, new Image("https://minotar.net/body/MHF_Steve.png"));
			avatar3.setSize(100, 200);
			avatar3.setBounds(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() / 2 - 90, 100, 200);
			avatar3.setVisible(false);
		}
		
		public static void connectAccountPremiumCO(String username, Pane root) 
		{
			avatar3 = new LauncherImage(root, new Image("https://minotar.net/body/" + username + ".png"));
			avatar3.setBounds(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() / 2 - 90, 100, 200);
			avatar3.setVisible(false);
		}
		
		public JFXTextField getUsernameField() {
			return usernameField;
		}

		public JFXPasswordField getPasswordField() {
			return passwordField;
		}


		public static LauncherButton getTiktokButton() {
			return tiktokButton;
		}


		public static void setTiktokButton(LauncherButton tiktokButton) {
			LauncherPanel.tiktokButton = tiktokButton;
		}


		public static LauncherButton getMinestratorButton() {
			return minestratorButton;
		}


		public static void setMinestratorButton(LauncherButton minestratorButton) {
			LauncherPanel.minestratorButton = minestratorButton;
		}


		public static LauncherButton getTwitterButton() {
			return twitterButton;
		}


		public static void setTwitterButton(LauncherButton twitterButton) {
			LauncherPanel.twitterButton = twitterButton;
		}


		public static LauncherButton getYoutubeButton() {
			return youtubeButton;
		}


		public static void setYoutubeButton(LauncherButton youtubeButton) {
			LauncherPanel.youtubeButton = youtubeButton;
		}	
		
		private void showMicrosoftAuth(GameAuth auth) {
			 Scene scene = new Scene(createMicrosoftPanel(auth));
			 Stage stage = new Stage();
			 scene.setFill(Color.TRANSPARENT);
			 stage.setResizable(false);
			 stage.setTitle("Microsoft Authentication");
			 stage.setWidth(500);
			 stage.setHeight(600);
			 stage.setScene(scene);
			 stage.initModality(Modality.APPLICATION_MODAL);
			 stage.showAndWait();
		}
			 
		private Parent createMicrosoftPanel(GameAuth auth) {
			 LauncherPane contentPane = new LauncherPane(theGameEngine);
			 auth.connectMicrosoft(contentPane);
			 return contentPane;
		}

}