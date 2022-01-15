package fr.capdrake.majestycraft.forge;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import fr.capdrake.majestycraft.LauncherMain;
import fr.capdrake.majestycraft.LauncherPanel;
import fr.flowarg.flowio.FileUtils;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.json.ExternalFile;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.AbstractForgeVersion;
import fr.flowarg.flowupdater.versions.ForgeVersionBuilder;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.flowarg.flowupdater.versions.VersionType;
import fr.trxyy.alternative.alternative_api.GameFolder;

public class CustomForgeUpdater 
{
	
   VanillaVersion version;
   UpdaterOptions options;
   AbstractForgeVersion forgeVersion;
   
  private static GameFolder dossier = LauncherMain.getGameFolder();
  private static String gp = dossier.getBinDir().getAbsolutePath();
  private static String sub = gp.substring(0, gp.length() - 3);
  public String versionConfig, forgeVersionConfig, mcpVersion;
  private static final Path UPDATE_DIR = Paths.get(sub);


	
	public CustomForgeUpdater(String versionConfig, String forgeVersionConfig, String mcpVersion)
	{		    			
				this.versionConfig = versionConfig;
				this.forgeVersionConfig = forgeVersionConfig;
				this.mcpVersion = mcpVersion;
				final String vanillaForge = versionConfig + "-" + forgeVersionConfig;
				System.out.println(vanillaForge);
		        version = new VanillaVersion.VanillaVersionBuilder().withName(versionConfig).withSnapshot(false).withVersionType(VersionType.FORGE).build();
		        options = new UpdaterOptions.UpdaterOptionsBuilder().build();				        
		        
		        switch(versionConfig) 
		        {
	        		case "1.9":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD).withForgeVersion(forgeVersionConfig).build();
		        		  break;
	        		case "1.10.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD).withForgeVersion(forgeVersionConfig).build();
		        		  break;
		        	case "1.11.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD).withForgeVersion(forgeVersionConfig).build();
		        		  break;
		        	case "1.12.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
    				case "1.13.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
        			case "1.14.4":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
        			case "1.15.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
	        		case "1.16.2":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
	        		case "1.16.3":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
	        		case "1.16.4":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
		        	case "1.16.5":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(forgeVersionConfig).build();
		        		  break;
		        	case "1.17.1":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(vanillaForge).build();
		        		  break;
		        	case "1.18.1":
		        		  forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW).withForgeVersion(vanillaForge).build();
		        		  break;
		        }

	}
	
	public String getVersionConfig() {
		return versionConfig;
	}
	
	public String getForgeVersionConfig() {
		return forgeVersionConfig;
	}
	
	public String getMcpVersion() {
		return mcpVersion;
	}
	
	public void update() throws Exception 
	{
		deleteExistingFiles();
		System.out.println(UPDATE_DIR);
        final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
        		.withVanillaVersion(version)
                .withProgressCallback(LauncherPanel.getInstance().getCallback())
                .withForgeVersion(forgeVersion)
                .withUpdaterOptions(options)   
                .withExternalFiles(ExternalFile.getExternalFilesFromJson("https://majestycraft.com/minecraft/1.16.5/forge/files"))
                .build();
        updater.update(UPDATE_DIR);
	}
	
	public enum StepInfo {
        READ("Lecture du fichier json..."),
        DL_LIBS("Téléchargement des libraries..."),
        DL_ASSETS("Téléchargement des ressources..."),
        EXTRACT_NATIVES("Extraction des natives..."),
        FORGE("Installation de forge..."),
        FABRIC("Installation de fabric..."),
        MODS("Téléchargement des mods..."),
        EXTERNAL_FILES("Téléchargement des fichier externes..."),
        POST_EXECUTIONS("Exécution post-installation..."),
        END("Finit !");
        String details;

        StepInfo(String details) {
            this.details = details;
        }

        public String getDetails() {
            return details;
        }
    }
	
	private void deleteExistingFiles() 
	{
		
		GameFolder folder = LauncherMain.getGameFolder();
		String gp = folder.getBinDir().getAbsolutePath();
		String sub = gp.substring(0, gp.length() - 3);
		System.out.println(sub);
		if(new File(sub).exists()) 
		{
				System.out.println("---------------------------------------------");
				System.out.println("DELETAGE DES FICHIERS EXISTANTS");
				System.out.println("---------------------------------------------");
				Path assetsFile = Paths.get(folder.getAssetsDir().getAbsolutePath());
				//Path modsFile = Paths.get(sub + "mods");
				Path libsFile = Paths.get(sub + "libraries");
				Path nativesFile = Paths.get(sub + "natives");
				Path clientJar = Paths.get(sub + "client.jar");
				Path cacheFile = Paths.get(sub + "cache");
				
				try {
					FileUtils.deleteDirectory(assetsFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//FileUtils.deleteDirectory(modsFile);
				try {
					FileUtils.deleteDirectory(libsFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					FileUtils.deleteDirectory(nativesFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					FileUtils.deleteDirectory(clientJar);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					FileUtils.deleteDirectory(cacheFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}