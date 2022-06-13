package fr.capdrake.majestycraft.forge;

import java.io.File;

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

public class CustomForgeUpdater {

	VanillaVersion version;
	UpdaterOptions options;
	AbstractForgeVersion forgeVersion;

	public String versionConfig, forgeVersionConfig, mcpVersion;

	public CustomForgeUpdater(String versionConfig, String forgeVersionConfig, String mcpVersion) throws Exception {
		this.versionConfig = versionConfig;
		this.forgeVersionConfig = forgeVersionConfig;
		this.mcpVersion = mcpVersion;
		version = new VanillaVersion.VanillaVersionBuilder().withName(versionConfig).withSnapshot(false)
				.withVersionType(VersionType.FORGE).build();
		options = new UpdaterOptions.UpdaterOptionsBuilder().withReExtractNatives(true).withEnableCurseForgePlugin(true)
				.build();

		switch (versionConfig) {
		case "1.9":
			case "1.10.2":
			case "1.11.2":
				forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.OLD)
					.withForgeVersion(forgeVersionConfig).withVanillaVersion(version).build();
			break;
			default:
			forgeVersion = new ForgeVersionBuilder(ForgeVersionBuilder.ForgeVersionType.NEW)
					.withForgeVersion(forgeVersionConfig).withVanillaVersion(version).build();
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

	public void update() throws Exception {
		deleteExistingFiles();
		final FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder().withVersion(version)
				.withProgressCallback(LauncherPanel.getInstance().getCallback()).withForgeVersion(forgeVersion)
				.withUpdaterOptions(options)
				.withExternalFiles(
						ExternalFile.getExternalFilesFromJson("https://majestycraft.com/minecraft/1.16.5/forge/files"))
				.build();
		updater.update(LauncherMain.getGameFolder().gameDir);
	}

	private void deleteExistingFiles() {

		GameFolder folder = LauncherMain.getGameFolder();
		String gp = folder.getBinDir().getAbsolutePath();
		String sub = gp.substring(0, gp.length() - 3);
		System.out.println(sub);
		if (new File(sub).exists()) {
			System.out.println("---------------------------------------------");
			System.out.println("DELETAGE DES FICHIERS EXISTANTS");
			System.out.println("---------------------------------------------");
			File assetsFile = new File(folder.getAssetsDir().getAbsolutePath());
			//File modsFile = new File(sub + "mods");
			File libsFile = new File(sub + "libraries");
			File nativesFile = new File(sub + "natives");
			File clientJar = new File(sub + "client.jar");
			File cacheFile = new File(sub + "cache");

			FileUtils.deleteDirectory(assetsFile);
			// FileUtils.deleteDirectory(modsFile);
			FileUtils.deleteDirectory(libsFile);
			FileUtils.deleteDirectory(nativesFile);
			FileUtils.deleteDirectory(clientJar);
			FileUtils.deleteDirectory(cacheFile);
		}
	}
}