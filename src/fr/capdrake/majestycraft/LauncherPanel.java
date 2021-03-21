package fr.capdrake.majestycraft;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import re.alwyn974.minecraftserverping.MinecraftServerPing;
import re.alwyn974.minecraftserverping.MinecraftServerPingInfos;
import re.alwyn974.minecraftserverping.MinecraftServerPingOptions;

public class LauncherPanel extends IScreen{

	private LauncherImage titleImage;

	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	
	private LauncherTextField usernameField2;
	
	//Les bouttons 
	private LauncherButton siteButton;
	private LauncherButton forumButton;
	private LauncherButton voteButton;
	private LauncherButton boutiqueButton;
	private LauncherButton loginButton;
	private LauncherButton loginButton2;
	private LauncherButton settingsButton;
	private LauncherButton settingsButton2;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	private LauncherButton facebookButton;
	private LauncherButton twitterButton;
	private LauncherButton instagramButton;
	private LauncherButton youtubeButton;
	private LauncherButton infoButton;
	private LauncherButton muteButton;
	private LauncherButton unmuteButton;
	
	//Les labels
	private LauncherLabel autoLoginLabel;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	private LauncherLabel titleMajestycraft;
	private LauncherLabel titleMajestycraft2;
	private LauncherLabel titleTropicolands;
	private LauncherLabel titleTropicolands2;
	private LauncherLabel infoServeur;
	private LauncherLabel infoJoueur;
	private LauncherLabel titleLabel;
	private LauncherLabel titlePremium;
	private LauncherLabel titleCrack;
	
	// Les rectangles 
	private LauncherRectangle autoLoginRectangle;
	private LauncherRectangle topRectangle;
	private LauncherRectangle updateRectangle;
	private LauncherRectangle serveurRectangle;
	private LauncherRectangle joueursRectangle;
	private LauncherRectangle connexionRectangle;
	
	/** GAMEENGINE REQUIRED */
	private static GameEngine theGameEngine;	
	
	// Se souvenir de moi
	private CheckBox rememberMe;

	private Timer autoLoginTimer;

	private LauncherButton autoLoginButton;
	
	private Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater gameUpdater = new GameUpdater();

	public LauncherProgressBar bar;
	
	// Image avatar
		public static LauncherImage avatar;
		public static LauncherImage avatar2;
		public static LauncherImage avatar3;
	
	private ImageView imageanniv;

	
	private String FACEBOOK_URL = "http://facebook.com/";
	private String INSTAGRAM_URL = "http://instagram.com/";
	private String TWITTER_URL = "http://twitter.com/craftsurvie";
	private String YOUTUBE_URL = "http://youtube.com/";
	private String VOTE_URL = "https://majestycraft.com/index.php?&page=voter";
	private String BOUTIQUE_URL = "https://majestycraft.com/index.php?&page=boutique";
	private String SITE_URL = "https://majestycraft.com/index.php";
	private String DISCORD_URL = "https://discord.gg/bj7mUb9";
	//private String TROPICOLANDS_URL = "https://tropicolands.ezcraft.fr/";
	public LauncherConfig config;
	
	
	public LauncherPanel(Pane root, GameEngine engine){
		
		// Déselectionne la textfield par défaut
	    Platform.runLater( () -> root.requestFocus());
	    
	    
		
		/** ===================== RECTANGLE INFO SERVEUR ===================== */
		this.serveurRectangle = new LauncherRectangle(root, engine.getWidth() / 2 - 460, engine.getHeight() / 2 - 255, 250, 90);
		this.serveurRectangle.setArcWidth(10.0);
		this.serveurRectangle.setArcHeight(10.0);
		this.serveurRectangle.setFill(Color.rgb(0, 0, 0, 0.40));
		this.serveurRectangle.setVisible(false);
		
		
		/** ===================== INFO SERVEUR ===================== */
		this.infoServeur = new LauncherLabel(root);
		try {
			MinecraftServerPingInfos data = new MinecraftServerPing().getPing(new MinecraftServerPingOptions().setHostname("51.38.13.50").setPort(25764));
			this.infoServeur.setText("MajestyCraft - Information\n" + data.getVersion().getName() + "\n" + data.getLatency() + " ms\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.infoServeur.setPosition(engine.getWidth() / 2 - 450, engine.getHeight() / 2 - 250);
		this.infoServeur.setFont(Font.font("FontName", FontWeight.BOLD, 18d));
		this.infoServeur.setStyle("-fx-background-color: transparent; -fx-text-fill: red");
		this.infoServeur.setOpacity(0.7);
		this.infoServeur.setVisible(false);
		
		/** ===================== RECTANGLE INFO NB JOUEURS ===================== */
		this.joueursRectangle = new LauncherRectangle(root, engine.getWidth() / 2 + 205, engine.getHeight() / 2 - 255, 250, 90);
		this.joueursRectangle.setArcWidth(10.0);
		this.joueursRectangle.setArcHeight(10.0);
		this.joueursRectangle.setFill(Color.rgb(0, 0, 0, 0.40));
		this.joueursRectangle.setVisible(false);
		
		/** ===================== INFO NB JOUEURS ===================== */
		this.infoJoueur = new LauncherLabel(root);
		try {
			MinecraftServerPingInfos data = new MinecraftServerPing().getPing(new MinecraftServerPingOptions().setHostname("51.38.13.50").setPort(25764));
			this.infoJoueur.setText("MajestyCraft - Joueurs\n" + data.getPlayers().getOnline() + " / " + data.getPlayers().getMax() + " joueurs");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.infoJoueur.setPosition(engine.getWidth() / 2 + 235, engine.getHeight() / 2 - 250);
		this.infoJoueur.setFont(Font.font("FontName", FontWeight.BOLD, 18d));
		this.infoJoueur.setStyle("-fx-background-color: transparent; -fx-text-fill: red");
		this.infoJoueur.setOpacity(0.7);
		this.infoJoueur.setVisible(false);
		
		
		theGameEngine = engine;
		
		this.config = new LauncherConfig(engine);
		this.config.loadConfiguration();
		
		this.topRectangle = new LauncherRectangle(root, 0, 0, engine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.50));
		
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "logo.png"), engine.getWidth() / 2 - 95, 40, 200, 200, root, Mover.DONT_MOVE);
		
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "anniv.gif"), engine.getWidth() / 2 - 95, 250, 200, 100, root, Mover.DONT_MOVE);
		//this.drawLogo(engine, getResourceLocation().loadImage(engine, "NEWlogo.jpg"), engine.getWidth() / 2 - 386, 260, 130, 130, root, Mover.DONT_MOVE);
		
		//this.drawLogo(engine, getResourceLocation().loadImage(engine, "tropicolands.png"), engine.getWidth() / 2 + 266, 260, 130, 130, root, Mover.DONT_MOVE);
		
		//Partie texte
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Launcher MajestyCraft 1.16.2 Optifine + Forge");
		this.titleLabel.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleLabel.setPosition(engine.getWidth() / 2 - 170, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500,  40);
		
		
		/** ===================== TITRE 1 MAJESTYCRAFT ===================== */
		this.titleMajestycraft = new LauncherLabel(root);
		this.titleMajestycraft.setText("Bienvenue sur");
		this.titleMajestycraft.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleMajestycraft.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleMajestycraft.setPosition(engine.getWidth() / 2 - 395, engine.getHeight() / 2- 130);
		this.titleMajestycraft.setOpacity(0.7);
		this.titleMajestycraft.setSize(500,  40);
		this.titleMajestycraft.setVisible(false);
		
		/** ===================== TITRE 2 MAJESTYCRAFT ===================== */
		this.titleMajestycraft2 = new LauncherLabel(root);
		this.titleMajestycraft2.setText("MajestyCraft");
		this.titleMajestycraft2.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleMajestycraft2.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleMajestycraft2.setPosition(engine.getWidth() / 2 - 389, engine.getHeight() / 2- 90);
		this.titleMajestycraft2.setOpacity(0.7);
		this.titleMajestycraft2.setSize(500,  40);
		this.titleMajestycraft2.setVisible(false);
		
		/** ===================== TITRE 1 TROPICOLANDS ===================== */
		this.titleTropicolands = new LauncherLabel(root);
		this.titleTropicolands.setText("Partenaire : ");
		this.titleTropicolands.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleTropicolands.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleTropicolands.setPosition(engine.getWidth() / 2 + 268, engine.getHeight() / 2- 130);
		this.titleTropicolands.setOpacity(0.7);
		this.titleTropicolands.setSize(500,  40);
		this.titleTropicolands.setVisible(false);
		
		
		/** ===================== TITRE 2 TROPICOLANDS ===================== */
		this.titleTropicolands2 = new LauncherLabel(root);
		this.titleTropicolands2.setText("TropicoLands");
		this.titleTropicolands2.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleTropicolands2.setStyle("-fx-background-color: transparent; -fx-text-fill: green");
		this.titleTropicolands2.setPosition(engine.getWidth() / 2 + 257, engine.getHeight() / 2- 90);
		this.titleTropicolands2.setOpacity(0.7);
		this.titleTropicolands2.setSize(500,  40);
		this.titleTropicolands2.setVisible(false);


		
		/** ===================== IMAGE DU LOGO EN HAUT ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(engine, "favicon.png"));
		this.titleImage.setSize(25, 25);
		this.titleImage.setPosition(engine.getWidth() / 3 - 50, 3);
		
		
		//root.getChildren().addAll(imageanniv);
		
		/** ===================== BOUTON info ===================== */
		this.infoButton = new LauncherButton(root);
		this.infoButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		LauncherImage settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "info.png"));
		settingsImg.setSize(27, 27);
		this.infoButton.setGraphic(settingsImg);
		this.infoButton.setPosition(engine.getWidth() / 2 - 525, engine.getHeight() / 2 - 320);
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
		
		/** ===================== BOUTON MUSIQUE OFF ===================== */
		this.muteButton = new LauncherButton(root);
		this.muteButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "mute.png"));
		settingsImg.setSize(27, 27);
		this.muteButton.setGraphic(settingsImg);
		this.muteButton.setPosition(engine.getWidth() / 2 - 525, engine.getHeight() / 2 - 270);
		this.muteButton.setSize(60, 46);
		this.muteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LauncherMain.muteMusic();
			}
		});
		
		/** ===================== BOUTON MUSIQUE ON ===================== */
		this.unmuteButton = new LauncherButton(root);
		this.unmuteButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "unmute.png"));
		settingsImg.setSize(27, 27);
		this.unmuteButton.setGraphic(settingsImg);
		this.unmuteButton.setPosition(engine.getWidth() / 2 - 525, engine.getHeight() / 2 - 220);
		this.unmuteButton.setSize(60, 46);
		this.unmuteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LauncherMain.resumeMusic();
			}
		});
		
		/** ===================== RECTANGLE DES CONNEXIONS ===================== */
		this.connexionRectangle = new LauncherRectangle(root, -10 , engine.getHeight() / 2 - 150 , 1350, 320);
		this.connexionRectangle.setArcWidth(10.0);
		this.connexionRectangle.setArcHeight(10.0);
		this.connexionRectangle.setFill(Color.rgb(255, 255, 255, 0.10));
		this.connexionRectangle.setVisible(true);
		
		/** ===================== AVATAR CONSTANT CRACK ===================== */
		avatar2 = new LauncherImage(root, new Image("https://minotar.net/avatar/MHF_Steve.png"));
		avatar2.setSize(50, 50);
		avatar2.setPosition(theGameEngine.getWidth() / 2 + 121, theGameEngine.getHeight() / 2-12);
		
		/** ================================ PARTIE CRACK ================================== */
		
		
		/** ===================== TITRE 1 CRACK ===================== */
		this.titleCrack = new LauncherLabel(root);
		this.titleCrack.setText("Connexion crack");
		this.titleCrack.setFont(Font.font("FontName", FontWeight.BOLD, 25d));
		this.titleCrack.setStyle("-fx-background-color: transparent; -fx-text-fill: red");
		this.titleCrack.setPosition(engine.getWidth() / 2 + 222, engine.getHeight() / 2- 130);
		this.titleCrack.setOpacity(0.7);
		this.titleCrack.setSize(500,  40);
		this.titleCrack.setVisible(true);
		
		/** ===================== NOM D'UTILISATEUR ===================== */
		this.usernameField2 = new LauncherTextField(root);
		this.usernameField2.setText((String)this.config.getValue("username"));
		this.usernameField2.setPosition(engine.getWidth() / 2 + 176, engine.getHeight() / 2- 12);
		this.usernameField2.setSize(270, 50);
		this.usernameField2.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.usernameField2.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.2); -fx-text-fill: red");
		this.usernameField2.setVoidText("Pseudo ");
		
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton2 = new LauncherButton(root);
		this.loginButton2.setText("Connexion");
		this.loginButton2.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 22F));
		this.loginButton2.setPosition(engine.getWidth() / 2 + 244,  engine.getHeight() / 2 + 100);
		this.loginButton2.setSize(200,  45);
		this.loginButton2.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: red");
		this.loginButton2.setAction(event -> {
			/**
			 * ===================== VERIFICATION USEFORGE =====================
			 */

			if((boolean) config.getValue("useforge"))
			{
				LauncherMain.getGameLinks().JSON_NAME = config.getValue("version") + ".json";
				switch(engine.getGameLinks().JSON_NAME)
				{
					case "1.9.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#1938", "1.9", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.10.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2511", "1.10.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.11.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2588", "1.11.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.12.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2847", "1.12.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.13.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.13.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.14.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.14.4", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.15.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "31.2.45", "1.15.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.16.2.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "33.0.61", "1.16.2", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
					case "1.16.3.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "34.1.42", "1.16.3", "net.minecraft.launchwrapper.Launch", "20201025.185957");
						break;
					case "1.16.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "35.0.1", "1.16.4", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
					case "1.16.5.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "36.0.42", "1.16.5", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
				}
			} else {
				engine.setGameStyle(GameStyle.VANILLA);
			}
			/**
			 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
			 */

			config.updateValue("username", usernameField2.getText());
			config.updateValue("password", "");
			if (usernameField2.getText().length() < 3) {
				new LauncherAlert("Authentification echouee",
						"Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			} else if (usernameField2.getText().length() > 3) {
				GameAuth auth = new GameAuth(usernameField2.getText(), passwordField.getText(),
						AccountType.OFFLINE);
				if (auth.isLogged()) {
					update(engine, auth);
					connectAccountCrackCO(root);

				}
			}
		});
		
		/** ===================== BOUTON PARAMETRE ===================== */
		this.settingsButton2 = new LauncherButton(root);
		this.settingsButton2.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "settings.png"));
		settingsImg.setSize(27, 27);
		this.settingsButton2.setGraphic(settingsImg);
		this.settingsButton2.setPosition(engine.getWidth() / 2 +176, engine.getHeight() / 2 + 100);
		this.settingsButton2.setSize(60, 46);
		this.settingsButton2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Scene scene = new Scene(createSettingsPanel());
				Stage stage = new Stage();
				scene.setFill(Color.TRANSPARENT);
				stage.setResizable(false);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.setTitle("Parametres Launcher");
				stage.setWidth(500);
				stage.setHeight(400);
				stage.setScene(scene);
				stage.show();
			}
		});
		
		/** ================================ PARTIE PREMIUM ================================== */
		
		

		
		/** ===================== TITRE 1 PREMIUM ===================== */
		this.titlePremium = new LauncherLabel(root);
		this.titlePremium.setText("Connexion premium");
		this.titlePremium.setFont(Font.font("FontName", FontWeight.BOLD, 25d));
		this.titlePremium.setStyle("-fx-background-color: transparent; -fx-text-fill: red");
		this.titlePremium.setPosition(engine.getWidth() / 2 - 422, engine.getHeight() / 2- 130);
		this.titlePremium.setOpacity(0.7);
		this.titlePremium.setSize(500,  40);
		this.titlePremium.setVisible(true);
		
		/** ===================== NOM D'UTILISATEUR ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setPosition(engine.getWidth() / 2 - 435, engine.getHeight() / 2- 42);
		this.usernameField.setText((String)this.config.getValue("username"));
		this.usernameField.setSize(270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.2); -fx-text-fill: red");
		this.usernameField.setVoidText("Votre email");
		
		/** ===================== MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setPosition(engine.getWidth() / 2 - 435, engine.getHeight() / 2 + 15);
		this.passwordField.setSize(270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: red");
		this.passwordField.setVoidText("Mot de passe (vide = compte crack)");
		
		//Verif si la case "se souvenir de moi" est coché
		if((boolean) config.getValue("rememberme") == true) 
		{
			passwordField.setText((String) config.getValue("password"));
		} 
		else 
		{
			passwordField.setText("");
		} 
		
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Connexion");
		this.loginButton.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 22F));
		this.loginButton.setPosition(engine.getWidth() / 2 - 367,  engine.getHeight() / 2 + 100);
		this.loginButton.setSize(200,  45);
		this.loginButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: red");
		this.loginButton.setAction(event -> {
			/**
			 * ===================== VERIFICATION USEFORGE =====================
			 */
			
			if((boolean) config.getValue("useforge"))
			{
				LauncherMain.getGameLinks().JSON_NAME = config.getValue("version") + ".json";
				switch(engine.getGameLinks().JSON_NAME)
				{
					case "1.9.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#1938", "1.9", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.10.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2511", "1.10.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.11.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2588", "1.11.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.12.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "#2847", "1.12.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.13.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.13.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.14.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.14.4", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.15.2.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "31.2.45", "1.15.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
						break;
					case "1.16.2.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "33.0.61", "1.16.2", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
					case "1.16.3.json":
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "34.1.42", "1.16.3", "net.minecraft.launchwrapper.Launch", "20201025.185957");
						break;
					case "1.16.4.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "35.0.1", "1.16.4", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
					case "1.16.5.json":
						LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
						engine.setGameStyle(GameStyle.OPTIFINE);
						LauncherMain.gameForge = new GameForge("fmlclient", "36.0.42", "1.16.5", "net.minecraft.launchwrapper.Launch", "20200812.004259");
						break;
				
				}
			} else {
				engine.setGameStyle(GameStyle.VANILLA);
			}
			config.updateValue("username", usernameField.getText());
			if((boolean) config.getValue("rememberme") == true) 
			{
				config.updateValue("password", passwordField.getText());
			} 
			else 
			{
				config.updateValue("password", "");
			} 
			/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
			if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
						AccountType.MOJANG);
				if (auth.isLogged()) {
					update(engine, auth);
					connectAccountPremiumCO(auth.getSession().getUsername(), root);
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
		String userName = (String)this.config.getValue("username");
		
		if (userName.length() > 2 && (boolean)this.config.getValue("autologin").equals(true)) {
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
							if((boolean) config.getValue("useforge"))
							{
								LauncherMain.getGameLinks().JSON_NAME = config.getValue("version") + ".json";
								switch(engine.getGameLinks().JSON_NAME)
								{
									case "1.9.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.9/", "1.9.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "#1938", "1.9", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.10.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.10.2/", "1.10.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "#2511", "1.10.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.11.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.11.2/", "1.11.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "#2588", "1.11.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.12.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.12.2/", "1.12.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "#2847", "1.12.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.13.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.13.2/", "1.13.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.13.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.14.4.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.14.4/", "1.14.4.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "28.2.23", "1.14.4", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.15.2.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.15.2/", "1.15.2.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "31.2.45", "1.15.2", "net.minecraft.launchwrapper.Launch", "20200515.085601");
										break;
									case "1.16.2.json":
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "33.0.61", "1.16.2", "net.minecraft.launchwrapper.Launch", "20200812.004259");
										break;
									case "1.16.3.json":
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "34.1.42", "1.16.3", "net.minecraft.launchwrapper.Launch", "20201025.185957");
										break;
									case "1.16.4.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.4/", "1.16.4.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "35.0.1", "1.16.4", "net.minecraft.launchwrapper.Launch", "20200812.004259");
										break;
									case "1.16.5.json":
										LauncherMain.gameLinks = new GameLinks("https://majestycraft.com/minecraft/1.16.5/", "1.16.5.json");
										engine.setGameStyle(GameStyle.OPTIFINE);
										LauncherMain.gameForge = new GameForge("fmlclient", "36.0.42", "1.16.5", "net.minecraft.launchwrapper.Launch", "20200812.004259");
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
										loginButton.fire();
										autoLoginTimer.cancel();
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);
										GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
												AccountType.OFFLINE);
										update(engine, auth);

										
									}
									
									/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
									else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
										autoLoginTimer.cancel();
										loginButton.fire();
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);

										GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
												AccountType.MOJANG);
										if (auth.isLogged()) {
											update(engine, auth);
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
		
		/** ===================== CHECKBOX SE SOUVENIR ===================== */
		this.rememberMe = new CheckBox();
		this.rememberMe.setText("Se souvenir de moi");
		this.rememberMe.setSelected((boolean) config.getValue("rememberme"));
		this.rememberMe.setOpacity(1.0);
		this.rememberMe.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.rememberMe.setStyle("-fx-text-fill: red;");
		this.rememberMe.setLayoutX(91);
		this.rememberMe.setLayoutY(447);
		this.rememberMe.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				HashMap<String, String> configMap = new HashMap<String, String>();
				configMap.put("rememberme", "" + rememberMe.isSelected());
				config.updateValues(configMap);
			}
		});
		
		root.getChildren().add(rememberMe);
		
		/** ===================== BOUTON PARAMETRE ===================== */
		this.settingsButton = new LauncherButton(root);
		this.settingsButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "settings.png"));
		settingsImg.setSize(27, 27);
		this.settingsButton.setGraphic(settingsImg);
		this.settingsButton.setPosition(engine.getWidth() / 2 - 435, engine.getHeight() / 2 + 100);
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
				stage.setWidth(500);
				stage.setHeight(400);
				stage.setScene(scene);
				stage.show();
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
			System.exit(0);
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
			Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
			stage.setIconified(true);
		});
		
		/** ===================== BOUTON URL VOTE ===================== */
		this.voteButton = new LauncherButton(root);
		this.voteButton.setText("Voter");
		this.voteButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.voteButton.setPosition(engine.getWidth() / 2 - 260,  engine.getHeight() / 2 + 190);
		this.voteButton.setSize(250,  45);
		this.voteButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.voteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(VOTE_URL);
			}
		});
		
		
		/** ===================== BOUTON URL BOUTIQUE ===================== */
		this.boutiqueButton = new LauncherButton(root);
		this.boutiqueButton.setText("Boutique");
		this.boutiqueButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.boutiqueButton.setPosition(engine.getWidth() / 2 - 125 + 130,  engine.getHeight() / 2 + 190);
		this.boutiqueButton.setSize(250,  45);
		this.boutiqueButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.boutiqueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(BOUTIQUE_URL);
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
		
		/** ===================== BOUTON URL FACEBOOK ===================== */
		this.facebookButton = new LauncherButton(root);
		this.facebookButton.setInvisible();
		this.facebookButton.setPosition(engine.getWidth() / 2 - 125, engine.getHeight() - 130);
		LauncherImage facebookImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "fb_icon.png"));
		facebookImg.setSize(80, 80);
		this.facebookButton.setGraphic(facebookImg);
		this.facebookButton.setSize((int) facebookImg.getFitWidth(), (int) facebookImg.getFitHeight());
		this.facebookButton.setBackground(null);
		this.facebookButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(FACEBOOK_URL);
			}
		});
		/** ===================== BOUTON URL TWITTER ===================== */
		this.twitterButton = new LauncherButton(root);
		this.twitterButton.setInvisible();
		this.twitterButton.setPosition(engine.getWidth() / 2 + 25, engine.getHeight() - 130);
		LauncherImage twitterImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "twitter_icon.png"));
		twitterImg.setSize(80, 80);
		this.twitterButton.setGraphic(twitterImg);
		this.twitterButton.setSize((int) twitterImg.getFitWidth(), (int) twitterImg.getFitHeight());
		this.twitterButton.setBackground(null);
		this.twitterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(TWITTER_URL);
			}
		});
		/** ===================== BOUTON URL INSTAGRAM ===================== */
		this.instagramButton = new LauncherButton(root);
		this.instagramButton.setInvisible();
		this.instagramButton.setPosition(engine.getWidth() / 2 - 125 - 150, engine.getHeight() - 130);
		LauncherImage instagramImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "insta_icon.png"));
		instagramImg.setSize(80, 80);
		this.instagramButton.setGraphic(instagramImg);
		this.instagramButton.setSize((int) instagramImg.getFitWidth(), (int) instagramImg.getFitHeight());
		this.instagramButton.setBackground(null);
		this.instagramButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(INSTAGRAM_URL);
			}
		});
		/** ===================== BOUTON URL YOUTUBE ===================== */
		this.youtubeButton = new LauncherButton(root);
		this.youtubeButton.setInvisible();
		this.youtubeButton.setPosition(engine.getWidth() / 2 - 125 + 300, engine.getHeight() - 130);
		LauncherImage youtubeImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "yt_icon.png"));
		youtubeImg.setSize(80, 80);
		this.youtubeButton.setGraphic(youtubeImg);
		this.youtubeButton.setSize((int) youtubeImg.getFitWidth(), (int) youtubeImg.getFitHeight());
		this.youtubeButton.setBackground(null);
		this.youtubeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(YOUTUBE_URL);
			}
		});
		
		/** ===================== RECTANGLE DE MISE A JOURS ===================== */
		this.updateRectangle = new LauncherRectangle(root, engine.getWidth() / 2 - 175, engine.getHeight() / 2 - 80, 350, 180);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
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
		this.bar = new LauncherProgressBar(root);
		this.bar.setPosition(engine.getWidth() / 2 - 125, engine.getHeight() / 2 + 40);
		this.bar.setSize(250, 20);
		this.bar.setVisible(false);
		
		/** =============== AFFICHAGE DE LA TETE DU JOUEUR =============== **/		
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
					connectAccountCrack(root);
					connectAccountCrackCO(root);
				}
			}
			else {
				this.usernameField2.setText("");
				connectAccountCrack(root);
				connectAccountCrackCO(root);
			}
		}
		else {
			this.usernameField.setText("");
			this.rememberMe.setSelected(false);
			connectAccountCrack(root);
			connectAccountCrackCO(root);
		}
		
		//Sert à éviter que la tête de connexion s'affichent lors de la connexion
		avatar3.setVisible(false);
		
		

	}

		
	public void update(GameEngine engine, GameAuth auth) {
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
			this.settingsButton2.setDisable(true);
			this.usernameField.setVisible(false);
			this.usernameField2.setVisible(false);
			this.connexionRectangle.setVisible(false);
			this.titlePremium.setVisible(false);
			this.titleCrack.setVisible(false);
			this.rememberMe.setVisible(false);
			this.passwordField.setVisible(false);
			this.loginButton.setVisible(false);
			this.loginButton2.setVisible(false);
			this.settingsButton.setVisible(false);
			this.settingsButton2.setVisible(false);
			this.updateRectangle.setVisible(true);
			this.updateLabel.setVisible(true);
			this.currentStep.setVisible(true);
			this.currentFileLabel.setVisible(true);
			this.percentageLabel.setVisible(true);
			this.bar.setVisible(true);
			avatar.setVisible(false);
			avatar2.setVisible(false);
			avatar3.setVisible(true);
			theGameEngine.reg(LauncherMain.gameLinks);
			theGameEngine.getGameLinks().JSON_URL = theGameEngine.getGameLinks().BASE_URL + this.config.getValue("version") + ".json";
			gameUpdater.reg(engine);
			gameUpdater.reg(auth.getSession());
			
			
			
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
			LauncherPane contentPane = new LauncherPane(theGameEngine);
			Rectangle rect = new Rectangle(800, 500);
			rect.setArcHeight(15.0);
			rect.setArcWidth(15.0);
			contentPane.setClip(rect);
			contentPane.setStyle("-fx-background-color: transparent;");
			new LauncherSettings(contentPane, theGameEngine, this);
			return contentPane;
		}
		
		private Parent createInfoPanel() {
			LauncherPane contentPane2 = new LauncherPane(theGameEngine);
			Rectangle rect = new Rectangle(1500, 900);
			rect.setArcHeight(15.0);
			rect.setArcWidth(15.0);
			contentPane2.setClip(rect);
			contentPane2.setStyle("-fx-background-color: transparent;");
			new LauncherInfo(contentPane2, theGameEngine, this);
			return contentPane2;
		}
		
		public void update2(GameAuth auth) {
			gameUpdater.reg(auth.getSession());
			theGameEngine.reg(this.gameUpdater);

		}
		
		public static void connectAccountCrack(Pane root)
		{
			avatar = new LauncherImage(root, new Image("https://minotar.net/avatar/MHF_Steve.png"));
			avatar.setSize(50, 50);
			avatar.setPosition(theGameEngine.getWidth() / 2 - 490, theGameEngine.getHeight() / 2- 42);
		}
		
		public static void connectAccountPremium(String username, Pane root) 
		{
			avatar = new LauncherImage(root, new Image("https://minotar.net/avatar/" + username + ".png"));
			avatar.setSize(50, 50);
			avatar.setPosition(theGameEngine.getWidth() / 2 - 490, theGameEngine.getHeight() / 2- 42);
		}
		
		public static void connectAccountCrackCO(Pane root)
		{
			avatar3 = new LauncherImage(root, new Image("https://minotar.net/avatar/MHF_Steve.png"));
			avatar3.setSize(50, 50);
			avatar3.setPosition(theGameEngine.getWidth() / 2 - 230, theGameEngine.getHeight() / 2 - 80);
		}
		
		public static void connectAccountPremiumCO(String username, Pane root) 
		{
			avatar3 = new LauncherImage(root, new Image("https://minotar.net/avatar/" + username + ".png"));
			avatar3.setSize(50, 50);
			avatar3.setPosition(theGameEngine.getWidth() / 2 - 230, theGameEngine.getHeight() / 2 - 80);
		}
		
		public LauncherTextField getUsernameField() {
			return usernameField;
		}

		public LauncherPasswordField getPasswordField() {
			return passwordField;
		}
		

	
}
