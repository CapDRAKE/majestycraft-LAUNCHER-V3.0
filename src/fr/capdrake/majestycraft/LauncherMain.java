package fr.capdrake.majestycraft;

import java.io.IOException;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import fr.trxyy.alternative.alternative_api.GameConnect;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameForge;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.maintenance.GameMaintenance;
import fr.trxyy.alternative.alternative_api.maintenance.Maintenance;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LauncherMain extends AlternativeBase{
		
	private GameFolder gameFolder = new GameFolder("majestycraft");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher MajestyCraft Optifine + Forge", 1050, 750, true);
	public static GameLinks gameLinks = new GameLinks("https://majestycraft.com/minecraft/", "1.16.2.json");
	private GameEngine gameEngine = new GameEngine(this.gameFolder, this.gameLinks, this.launcherPreferences, GameStyle.VANILLA);
	public static GameForge gameForge;
	private GameMaintenance gameMaintenance = new GameMaintenance(Maintenance.USE, gameEngine);
	//private GameConnect gameConnect = new GameConnect("46.105.32.110", "25662");
	public static Media media;
	private static MediaPlayer mediaPlayer;
	public LauncherConfig config;

	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		playMusic(media, "Minecraft.mp3");
		Scene scene = new Scene(createContent());
		this.gameEngine.reg(primaryStage);  
		this.gameEngine.reg(this.gameMaintenance);
		//this.gameEngine.reg(this.gameLinks);
		//this.gameEngine.reg(this.gameConnect);
		//this.gameEngine.reg(this.newForge);
		LauncherBase launcherBase = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, gameEngine);
		launcherBase.setIconImage(primaryStage,  getResourceLocation().loadImage(gameEngine, "server-icon.png"));
	}
	
	private Parent createContent() throws IOException {
		LauncherPane contentPane = new LauncherPane(gameEngine);
		//new LauncherBackground(this.gameEngine, getResourceLocation().getMedia(gameEngine, "background.png"), contentPane);
		Rectangle rectangle = new Rectangle(gameEngine.getLauncherPreferences().getWidth(), gameEngine.getLauncherPreferences().getHeight());
		LauncherPanel panel = new LauncherPanel(contentPane, gameEngine, this);
		readVersion(panel);
		this.gameEngine.reg(this.gameLinks);
		rectangle.setArcWidth(15.0);
		rectangle.setArcWidth(15.0);
		contentPane.setClip(rectangle);
		contentPane.setStyle("-fx-background-color: transparent;");
		
		return contentPane;
	}
	
	private void playMusic(Media media, String path) 
	{
		media = getResourceLocation().getMedia(this.gameEngine, path);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	    mediaPlayer.setVolume(0.05);
	}
	
	private void readVersion(LauncherPanel panel)
	{
		switch((String) panel.config.getValue("version")) 
		{	
			case "1.8":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.8.json";
				break;	
			case "1.9":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.9.json";
				break;	
			case "1.10.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.10.2.json";
				break;	
			case "1.11.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.11.2.json";
				break;	
			case "1.12.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.12.2.json";
				break;		    
			case "1.16.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.2.json";
				break;
			case "1.15.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.15.2.json";
				break;
			case "1.16.4":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.4.json";
				break;
			case "1.14.4":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.14.4.json";
				break;
			case "1.13.2":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.13.2.json";
				break;
			case "1.16.3":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.3.json";
				break;
			case "1.16.5":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.5.json";
				break;
			case "21w14a":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "21w14a.json";
				break;
			case "21w15a":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "21w15a.json";
				break;
			case "1.17":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.17.json";
				break;
			case "1.17.1":
				gameLinks.JSON_URL = gameLinks.BASE_URL + "1.17.1.json";
				break;
			default :
                panel.config.updateValue("version", gameLinks.getJsonName().replace(".json",""));
                break;
		}
	}
	
	public static void muteMusic() {LauncherMain.getMediaPlayer().setMute(true);}
	public static void resumeMusic() {LauncherMain.getMediaPlayer().setMute(false);}
	public static Media getMedia() {return media;}
	public static MediaPlayer getMediaPlayer() {return mediaPlayer;}
	
	
	//Permet l'affichage sur discord
	static void discordRPC() {
		DiscordRPC discord = DiscordRPC.INSTANCE;
		String applicationId = "805862518567469077";
		String steamId = "";
		
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		discord.Discord_Initialize(applicationId, handlers, true, steamId);
		
		DiscordRichPresence presence = new DiscordRichPresence();
		presence.startTimestamp = System.currentTimeMillis() / 1000;
		presence.largeImageKey = "image";
		presence.largeImageText = "MajestyCraft, Launcher Gratuit Crack/Premium";
		presence.details = "Launcher MajestyCraft";
		presence.state = "Version : 1.7 => 1.17";
		
		discord.Discord_UpdatePresence(presence);
	}
	
	public static GameLinks getGameLinks() {
		return gameLinks;
	}
	
}
