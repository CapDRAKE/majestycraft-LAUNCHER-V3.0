package fr.capdrake.majestycraft;



import fr.trxyy.alternative.alternative_api.GameConnect;
import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api.maintenance.GameMaintenance;
import fr.trxyy.alternative.alternative_api.maintenance.Maintenance;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase{
		
	private GameFolder gameFolder = new GameFolder("majestycraft");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher MajestyCraft 1.16.2", 950, 600, true);
	private GameLinks gameLinks = new GameLinks("https://majestycraftmc.alwaysdata.net/", "optifine.json");
	private GameEngine gameEngine = new GameEngine(this.gameFolder, this.gameLinks, this.launcherPreferences, GameStyle.OPTIFINE);
	private GameMaintenance gameMaintenance = new GameMaintenance(Maintenance.USE, gameEngine);
	private GameConnect gameConnect = new GameConnect("51.38.13.50", "25764");
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Scene scene = new Scene(createContent());
		this.gameEngine.reg(primaryStage);
		this.gameEngine.reg(this.gameLinks);
		this.gameEngine.reg(this.gameMaintenance);
		this.gameEngine.reg(this.gameConnect);
		LauncherBase launcherBase = new LauncherBase(primaryStage, scene, StageStyle.UNDECORATED, gameEngine);
		launcherBase.setIconImage(primaryStage,  getResourceLocation().loadImage(gameEngine, "favicon.png"));
	}
	
	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(gameEngine);
		Rectangle rectangle = new Rectangle(gameEngine.getLauncherPreferences().getWidth(), gameEngine.getLauncherPreferences().getHeight());
		rectangle.setArcWidth(15.0);
		rectangle.setArcWidth(15.0);
		contentPane.setClip(rectangle);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherBackground(this.gameEngine, getResourceLocation().getMedia(gameEngine, "background.mp4"), contentPane);
		new LauncherPanel(contentPane, this.gameEngine);
		return contentPane;
	}

}
