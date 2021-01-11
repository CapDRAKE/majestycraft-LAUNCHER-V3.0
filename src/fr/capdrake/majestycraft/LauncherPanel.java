package fr.capdrake.majestycraft;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
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
import re.alwyn974.minecraftserverping.MinecraftServerPing;
import re.alwyn974.minecraftserverping.MinecraftServerPingInfos;
import re.alwyn974.minecraftserverping.MinecraftServerPingOptions;

public class LauncherPanel extends IScreen{

	private LauncherImage titleImage;

	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	
	//Les bouttons 
	private LauncherButton siteButton;
	private LauncherButton forumButton;
	private LauncherButton voteButton;
	private LauncherButton boutiqueButton;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	private LauncherButton facebookButton;
	private LauncherButton twitterButton;
	private LauncherButton instagramButton;
	private LauncherButton youtubeButton;
	
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
	
	// Les rectangles 
	private LauncherRectangle autoLoginRectangle;
	private LauncherRectangle topRectangle;
	private LauncherRectangle updateRectangle;
	private LauncherRectangle serveurRectangle;
	private LauncherRectangle joueursRectangle;
	
	private GameEngine theGameEngine;
	// Se souvenir de moi
	private CheckBox rememberMe;

	private Timer autoLoginTimer;

	private LauncherButton autoLoginButton;
	
	private Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater gameUpdater = new GameUpdater();

	public LauncherProgressBar bar;
	

	
	private String FACEBOOK_URL = "http://facebook.com/";
	private String INSTAGRAM_URL = "http://instagram.com/";
	private String TWITTER_URL = "http://twitter.com/craftsurvie";
	private String YOUTUBE_URL = "http://youtube.com/";
	private String VOTE_URL = "https://majestycraft.w2.websr.fr/index.php?&page=voter";
	private String BOUTIQUE_URL = "https://majestycraft.w2.websr.fr/index.php?&page=boutique";
	private String SITE_URL = "https://majestycraft.w2.websr.fr/index.php";
	private String FORUM_URL = "https://majestycraft.w2.websr.fr/index.php?page=forum";
	//private String TROPICOLANDS_URL = "https://tropicolands.ezcraft.fr/";
	public LauncherConfig config;
	
	
	public LauncherPanel(Pane root, GameEngine engine){
		
		
		/** ===================== RECTANGLE INFO SERVEUR ===================== */
		this.serveurRectangle = new LauncherRectangle(root, engine.getWidth() / 2 - 460, engine.getHeight() / 2 - 255, 250, 90);
		this.serveurRectangle.setArcWidth(10.0);
		this.serveurRectangle.setArcHeight(10.0);
		this.serveurRectangle.setFill(Color.rgb(0, 0, 0, 0.40));
		
		
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
		
		/** ===================== RECTANGLE INFO NB JOUEURS ===================== */
		this.joueursRectangle = new LauncherRectangle(root, engine.getWidth() / 2 + 205, engine.getHeight() / 2 - 255, 250, 90);
		this.joueursRectangle.setArcWidth(10.0);
		this.joueursRectangle.setArcHeight(10.0);
		this.joueursRectangle.setFill(Color.rgb(0, 0, 0, 0.40));
		
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
		
		
		
		this.theGameEngine = engine;
		
		this.config = new LauncherConfig(engine);
		this.config.loadConfiguration();
		
		this.topRectangle = new LauncherRectangle(root, 0, 0, engine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.50));
		
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "logo.png"), engine.getWidth() / 2 - 165, 50, 330, 160, root, Mover.DONT_MOVE);
		
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "NEWlogo.jpg"), engine.getWidth() / 2 - 386, 260, 130, 130, root, Mover.DONT_MOVE);
		
		this.drawLogo(engine, getResourceLocation().loadImage(engine, "tropicolands.png"), engine.getWidth() / 2 + 266, 260, 130, 130, root, Mover.DONT_MOVE);
		
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
		
		/** ===================== TITRE 2 MAJESTYCRAFT ===================== */
		this.titleMajestycraft2 = new LauncherLabel(root);
		this.titleMajestycraft2.setText("MajestyCraft");
		this.titleMajestycraft2.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleMajestycraft2.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleMajestycraft2.setPosition(engine.getWidth() / 2 - 389, engine.getHeight() / 2- 90);
		this.titleMajestycraft2.setOpacity(0.7);
		this.titleMajestycraft2.setSize(500,  40);
		
		/** ===================== TITRE 1 TROPICOLANDS ===================== */
		this.titleTropicolands = new LauncherLabel(root);
		this.titleTropicolands.setText("Partenaire : ");
		this.titleTropicolands.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleTropicolands.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.titleTropicolands.setPosition(engine.getWidth() / 2 + 268, engine.getHeight() / 2- 130);
		this.titleTropicolands.setOpacity(0.7);
		this.titleTropicolands.setSize(500,  40);
		
		
		/** ===================== TITRE 2 TROPICOLANDS ===================== */
		this.titleTropicolands2 = new LauncherLabel(root);
		this.titleTropicolands2.setText("TropicoLands");
		this.titleTropicolands2.setFont(Font.font("FontName", FontWeight.BOLD, 22d));
		this.titleTropicolands2.setStyle("-fx-background-color: transparent; -fx-text-fill: green");
		this.titleTropicolands2.setPosition(engine.getWidth() / 2 + 257, engine.getHeight() / 2- 90);
		this.titleTropicolands2.setOpacity(0.7);
		this.titleTropicolands2.setSize(500,  40);


		
		/** ===================== IMAGE DU LOGO EN HAUT ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(engine, "favicon.png"));
		this.titleImage.setSize(25, 25);
		this.titleImage.setPosition(engine.getWidth() / 3 - 50, 3);
		
		/** ===================== NOM D'UTILISATEUR ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText((String)this.config.getValue("username"));
		this.usernameField.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2- 82);
		this.usernameField.setSize(270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.usernameField.setVoidText("Votre pseudo (ou email si premium)");
		
		/** ===================== MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2 - 25);
		this.passwordField.setSize(270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Roboto-Light.ttf", "Roboto Light", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
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
		this.loginButton.setPosition(engine.getWidth() / 2 - 67,  engine.getHeight() / 2 + 60);
		this.loginButton.setSize(200,  45);
		this.loginButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.loginButton.setAction(event -> {
			/**
			 * ===================== VERIFICATION USEFORGE =====================
			 */
			if((boolean) config.getValue("useforge"))
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
			/**
			 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
			 */
			config.updateValue("username", usernameField.getText());
			if((boolean) config.getValue("rememberme") == true) 
			{
				config.updateValue("password", passwordField.getText());
			} 
			else 
			{
				config.updateValue("password", "");
			} 
			if (usernameField.getText().length() < 3) {
				new LauncherAlert("Authentification echouee",
						"Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
			} else if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
						AccountType.OFFLINE);
				if (auth.isLogged()) {
					update(engine, auth);
				}
			}
			/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
			else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
				GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
						AccountType.MOJANG);
				if (auth.isLogged()) {
					update(engine, auth);
				} else {
					new LauncherAlert("Authentification echouee!",
							"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
									+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
									+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang. \nAssurez-vous de bien utiliser votre email dans le  cas d'une connexion avec un compte Mojang !");
				}
			} else {
				new LauncherAlert("Authentification echouee!",
						"Impossible de se connecter, l'authentification semble etre une authentification 'hors-ligne'"
								+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres.");
			}
		});
		
		/** =============== LOGIN AUTOMATIQUE (CRACK ET PREMIUM =============== **/
		this.autoLoginRectangle = new LauncherRectangle(root, 0, theGameEngine.getHeight() - 32, 1000, theGameEngine.getHeight());
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
							
							elapsed++;
							//S'execute à la fin du compte à rebours
							if (elapsed % waitTime == 0) { 
								if (!theGameEngine.getGameMaintenance().isAccessBlocked()) {
									/**
									 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
									 */
									if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
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
									else{
										loginButton.fire();
										autoLoginTimer.cancel();
										autoLoginLabel.setVisible(false);
										autoLoginButton.setVisible(false);
										autoLoginRectangle.setVisible(false);
										GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
												AccountType.MOJANG);
										update(engine, auth);
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
		this.rememberMe.setStyle("-fx-text-fill: orange;");
		this.rememberMe.setLayoutX(340);
		this.rememberMe.setLayoutY(332);
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
		LauncherImage settingsImg = new LauncherImage(root, getResourceLocation().loadImage(engine, "settings.png"));
		settingsImg.setSize(27, 27);
		this.settingsButton.setGraphic(settingsImg);
		this.settingsButton.setPosition(engine.getWidth() / 2 - 135, engine.getHeight() / 2 + 60);
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
		this.voteButton.setPosition(engine.getWidth() / 2 - 260,  engine.getHeight() / 2 + 120);
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
		this.boutiqueButton.setPosition(engine.getWidth() / 2 - 125 + 130,  engine.getHeight() / 2 + 120);
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
		this.forumButton.setText("Forum");
		this.forumButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.forumButton.setPosition(engine.getWidth() /2 + 300,  engine.getHeight() - 107);
		this.forumButton.setSize(200,  45);
		this.forumButton.setStyle("-fx-background-color: rgba(0 ,0 ,0 , 0.4); -fx-text-fill: orange");
		this.forumButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(FORUM_URL);
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
		

	}

		
	public void update(GameEngine engine, GameAuth auth) {
			this.usernameField.setDisable(true);
			this.rememberMe.setDisable(true);
			this.passwordField.setDisable(true);
			this.loginButton.setDisable(true);
			this.settingsButton.setDisable(true);
			this.usernameField.setVisible(false);
			this.rememberMe.setVisible(false);
			this.passwordField.setVisible(false);
			this.loginButton.setVisible(false);
			this.settingsButton.setVisible(false);
			this.updateRectangle.setVisible(true);
			this.updateLabel.setVisible(true);
			this.currentStep.setVisible(true);
			this.currentFileLabel.setVisible(true);
			this.percentageLabel.setVisible(true);
			this.bar.setVisible(true);
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
		
		public LauncherTextField getUsernameField() {
			return usernameField;
		}

		public LauncherPasswordField getPasswordField() {
			return passwordField;
		}
	
}
