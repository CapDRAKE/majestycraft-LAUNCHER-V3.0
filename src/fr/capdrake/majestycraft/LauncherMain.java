package fr.capdrake.majestycraft;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import animatefx.animation.JackInTheBox;
import animatefx.animation.Tada;
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
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.EnumConfig;
import fr.trxyy.alternative.alternative_api.utils.config.LauncherConfig;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {

	private static LauncherMain instance;
	public static LauncherPane contentPane;
	private Scene scene;
	private static GameFolder gameFolder = new GameFolder("majestycraft");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("MajestyLauncher Optifine + Forge", 1050,
			750, Mover.MOVE);
	public static GameLinks gameLinks = new GameLinks("https://majestycraft.com/minecraft/", "1.18.json");
	private GameEngine gameEngine = new GameEngine(LauncherMain.gameFolder, LauncherMain.gameLinks,
			this.launcherPreferences, GameStyle.VANILLA);
	public static GameForge gameForge;
	private GameMaintenance gameMaintenance = new GameMaintenance(Maintenance.USE, gameEngine);
	private static GameConnect gameConnect = new GameConnect("play.majestycraft.com", "25565");
	public static Media media;
	private static MediaPlayer mediaPlayer;
	public LauncherConfig config;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		playMusic(media, "Minecraft.mp3");
		createContent();
		// Affiche ou non le statut discord
		this.gameEngine.reg(primaryStage);
		if(LauncherMain.netIsAvailable()) {
			this.gameEngine.reg(this.gameMaintenance);
		}
		LauncherBase launcherBase = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, this.gameEngine);
		launcherBase.setIconImage(primaryStage, "launchergifpng.png");
	}

	private Parent createContent() throws IOException {
		LauncherMain.contentPane = new LauncherPane(this.gameEngine);
		scene = new Scene(LauncherMain.getContentPane());
		Rectangle rectangle = new Rectangle(this.gameEngine.getLauncherPreferences().getWidth(),
				this.gameEngine.getLauncherPreferences().getHeight());
		this.gameEngine.reg(LauncherMain.gameLinks);
		rectangle.setArcWidth(15.0);
		rectangle.setArcWidth(15.0);
		LauncherMain.getContentPane().setClip(rectangle);
		LauncherMain.getContentPane().setStyle("-fx-background-color: transparent;");
		LauncherPanel panel = new LauncherPanel(LauncherMain.getContentPane(), this.gameEngine, this);
		readVersion(panel);
		final JackInTheBox animationOUVERTURE = new JackInTheBox(LauncherMain.getContentPane());
		animationOUVERTURE.setSpeed(0.5);
		animationOUVERTURE.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent actionEvent) {
				new Tada(LauncherPanel.getTiktokButton()).play();
				new Tada(LauncherPanel.getMinestratorButton()).play();
				new Tada(LauncherPanel.getTwitterButton()).play();
				new Tada(LauncherPanel.getYoutubeButton()).play();
			}
		});
		animationOUVERTURE.play();
		return LauncherMain.getContentPane();
	}

	private void playMusic(Media media, String path) {
		media = getResourceLocation().getMedia(this.gameEngine, path);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
		mediaPlayer.setVolume(0.05);
	}

	private void readVersion(LauncherPanel panel) {
		switch ((String) panel.config.getValue(EnumConfig.VERSION)) {
		case "1.8":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.8.json";
			break;
		case "1.9":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.9.json";
			break;
		case "1.10.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.10.2.json";
			break;
		case "1.11.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.11.2.json";
			break;
		case "1.12.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.12.2.json";
			break;
		case "1.16.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.2.json";
			break;
		case "1.15.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.15.2.json";
			break;
		case "1.16.4":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.4.json";
			break;
		case "1.14.4":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.14.4.json";
			break;
		case "1.13.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.13.2.json";
			break;
		case "1.16.3":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.3.json";
			break;
		case "1.16.5":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.16.5.json";
			break;
		case "1.17":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.17.json";
			break;
		case "1.17.1":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.17.1.json";
			break;
		case "1.18":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.18.json";
			break;
		case "1.18.1":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.18.1.json";
			break;
		case "1.18.2":
			this.gameEngine.setGameStyle(GameStyle.VANILLA);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.18.2.json";
			break;
		case "1.19":
			//this.gameEngine.setGameStyle(GameStyle.VANILLA_1_19_HIGHER);
			gameLinks.JSON_URL = gameLinks.BASE_URL + "1.19.json";
			break;
		default:
			panel.config.updateValue("version", gameLinks.getJsonName().replace(".json", ""));
			break;
		}
	}

	public static void muteMusic() {
		LauncherMain.getMediaPlayer().setMute(true);
	}

	public static void resumeMusic() {
		LauncherMain.getMediaPlayer().setMute(false);
	}

	public static Media getMedia() {
		return media;
	}

	public static MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	// Permet l'affichage sur discord
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
		presence.state = "Version : 1.8 => 1.19";

		discord.Discord_UpdatePresence(presence);
	}

	public static GameLinks getGameLinks() {
		return gameLinks;
	}

	public static LauncherPane getContentPane() {
		return LauncherMain.contentPane;
	}
	
	public static void setContentPane(LauncherPane contentPane) {
		LauncherMain.contentPane = contentPane;
	}

	public static LauncherMain getInstance() {
		return instance;
	}

	public static GameFolder getGameFolder() {
		return gameFolder;
	}

	public static GameConnect getGameConnect() {
		return gameConnect;
	}

	public static void setGameConnect(GameConnect gameConnect) {
		LauncherMain.gameConnect = gameConnect;
	}
	
	public static boolean netIsAvailable() {
	    try {
	        final URL url = new URL("http://www.google.com");
	        final URLConnection conn = url.openConnection();
	        conn.connect();
	        conn.getInputStream().close();
	        return true;
	    } catch (MalformedURLException e) {
	        throw new RuntimeException(e);
	    } catch (IOException e) {
	        return false;
	    }
	}

}
