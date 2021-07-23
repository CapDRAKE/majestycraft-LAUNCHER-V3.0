package fr.capdrake.majestycraft;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class LauncherInfo extends IScreen {
	
	private LauncherLabel titleLabel;
	private LauncherLabel developpeur;
	private LauncherLabel DEV;
	private LauncherLabel HELPER;
	private LauncherLabel remarque;
	private LauncherLabel REM;
	private LauncherLabel REM2;
	private LauncherLabel version;
	private Slider volume;
	
	private LauncherRectangle topRectangle;
	
	private LauncherButton quitterButton;

	
	
	public LauncherInfo(final Pane root, final GameEngine engine, final LauncherPanel pane) {
		this.drawBackgroundImage(engine, root, "background.png");
		pane.config.loadConfiguration();
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, 1500, 15);
		this.topRectangle.setOpacity(0.7);
		/** ===================== LABEL TITRE ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("INFORMATIONS");
		this.titleLabel.setStyle("-fx-text-fill: white;");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 26F));
		this.titleLabel.setPosition(350, 20);
		this.titleLabel.setSize(230, 35);
		
		/** ===================== TITRE DEVELOPPEUR ===================== */
		this.developpeur = new LauncherLabel(root);
		this.developpeur.setText("Développeur");
		this.developpeur.setFont(Font.font("FontName", FontWeight.BOLD, 24d));
		this.developpeur.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.developpeur.setPosition(engine.getWidth() / 2 - 479, engine.getHeight() / 2- 300);
		this.developpeur.setOpacity(0.7);
		this.developpeur.setSize(500,  40);
		this.developpeur.setVisible(true);
		
		/** ===================== TITRE DEV ===================== */
		this.DEV = new LauncherLabel(root);
		this.DEV.setText("Développeur : Capdrake");
		this.DEV.setFont(Font.font("FontName", FontWeight.BOLD, 20d));
		this.DEV.setStyle("-fx-text-fill: white;");
		this.DEV.setPosition(engine.getWidth() / 2 - 449, engine.getHeight() / 2- 250);
		this.DEV.setOpacity(0.7);
		this.DEV.setSize(500,  40);
		this.DEV.setVisible(true);
		
		/** ===================== TITRE HELPER ===================== */
		this.HELPER = new LauncherLabel(root);
		this.HELPER.setText("Helper : aucun");
		this.HELPER.setFont(Font.font("FontName", FontWeight.BOLD, 20d));
		this.HELPER.setStyle("-fx-text-fill: white;");
		this.HELPER.setPosition(engine.getWidth() / 2 - 449, engine.getHeight() / 2- 220);
		this.HELPER.setOpacity(0.7);
		this.HELPER.setSize(500,  40);
		this.HELPER.setVisible(true);
		
		/** ===================== TITRE REMARQUE ===================== */
		this.remarque = new LauncherLabel(root);
		this.remarque.setText("Remarque");
		this.remarque.setFont(Font.font("FontName", FontWeight.BOLD, 24d));
		this.remarque.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.remarque.setPosition(engine.getWidth() / 2 - 479, engine.getHeight() / 2- 180);
		this.remarque.setOpacity(0.7);
		this.remarque.setSize(500,  40);
		this.remarque.setVisible(true);
		
		/** ===================== TITRE remarque 1 ===================== */
		this.REM = new LauncherLabel(root);
		this.REM.setText("1 => Si une version ne démarre pas, allez dans votre %appdata% et ");
		this.REM.setFont(Font.font("FontName", FontWeight.BOLD, 16d));
		this.REM.setStyle("-fx-text-fill: white;");
		this.REM.setPosition(engine.getWidth() / 2 - 449, engine.getHeight() / 2- 140);
		this.REM.setOpacity(0.7);
		this.REM.setSize(1000,  40);
		this.REM.setVisible(true);
		
		/** ===================== TITRE SUITE remarque 1 ===================== */
		this.REM2 = new LauncherLabel(root);
		this.REM2.setText("supprimez les fichiers 'Launcher_config.json' et '.majestycraft'");
		this.REM2.setFont(Font.font("FontName", FontWeight.BOLD, 16d));
		this.REM2.setStyle("-fx-text-fill: white;");
		this.REM2.setPosition(engine.getWidth() / 2 - 404, engine.getHeight() / 2- 120);
		this.REM2.setOpacity(0.7);
		this.REM2.setSize(1000,  40);
		this.REM2.setVisible(true);
		
		/** ===================== TITRE VERSION ===================== */
		this.remarque = new LauncherLabel(root);
		this.remarque.setText("Version");
		this.remarque.setFont(Font.font("FontName", FontWeight.BOLD, 24d));
		this.remarque.setStyle("-fx-background-color: transparent; -fx-text-fill: orange");
		this.remarque.setPosition(engine.getWidth() / 2 - 479, engine.getHeight() / 2- 60);
		this.remarque.setOpacity(0.7);
		this.remarque.setSize(500,  40);
		this.remarque.setVisible(true);
		
		/** ===================== TITRE Version actuelle ===================== */
		this.version = new LauncherLabel(root);
		this.version.setText("Version : 1.8");
		this.version.setFont(Font.font("FontName", FontWeight.BOLD, 20d));
		this.version.setStyle("-fx-text-fill: white;");
		this.version.setPosition(engine.getWidth() / 2 - 449, engine.getHeight() / 2 - 20);
		this.version.setOpacity(0.7);
		this.version.setSize(1000,  40);
		this.version.setVisible(true);
		
		/** ===================== BOUTON QUITTER ===================== */
		this.quitterButton = new LauncherButton(root);
		this.quitterButton.setText("Retour");
		this.quitterButton.setStyle("-fx-background-color: rgba(53, 89, 119, 0.4); -fx-text-fill: white;");
		this.quitterButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 16F));
		this.quitterButton.setPosition(700, 550);
		this.quitterButton.setSize(130, 35);
		this.quitterButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage)((LauncherButton)event.getSource()).getScene().getWindow();
				stage.close();
			}
		});		
		
		this.volume = new Slider();
		this.volume.setStyle("-fx-control-inner-background: rgba(46, 47, 48, 0.5);");
		this.volume.setMin(0.0);
		this.volume.setMax(10.0);
		this.volume.setValue(LauncherMain.getMediaPlayer().getVolume());
		this.volume.setLayoutX(50);
		this.volume.setLayoutY(210);
		this.volume.setPrefWidth(395);
		this.volume.setBlockIncrement(1);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	 root.getChildren().add(volume);
            }
        });
        this.volume.setVisible(false);
	}
}