package net.kalman98.freecoins.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.kalman98.freecoins.FreeCoins;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Holds the configuration information and synchronises the various copies of it
 * The configuration information is stored in three places: 1) in the
 * configuration file on disk, as text 2) in the Configuration object config
 * (accessed by the mod GUI), as text 3) in the MBEConfiguration variables
 * (fields), as native values (integer, double, etc) Usage: Setup: (1) During
 * your mod preInit(), call MBEConfiguration.preInit() to a) set up the format
 * of the configuration file b) load the settings from the existing file, or if
 * it doesn't exist yet - create it with default values (2) On the client proxy
 * (not dedicated server), call clientPreInit() to register an
 * OnConfigChangedEvent handler- your GUI will modify the config object, and
 * when it is closed it will trigger a OnConfigChangedEvent, which should call
 * syncFromGUI().
 *
 * Usage: (3) You can read the fields such as uiX directly (4) If you
 * modify the configuration fields, you can save them to disk using
 * syncFromFields() (5) To reload the values from disk, call syncFromFile() (6)
 * If you have used a GUI to alter the config values, call syncFromGUI(). (If
 * you called clientPreInit(), this will happen automatically)
 *
 * See ForgeModContainer for more examples
 */
public class FreeCoinsConfiguration
{

	// Declare all configuration fields used by the mod here
	public static boolean minimalCoins;
	public static int uiX;
	public static int uiY;
	public static boolean showUI;
	public static boolean simpleUI;
	public static boolean showUiText;
	public static boolean disableMod;
	
	public static final String CATEGORY_NAME_GENERAL = "category_general";
	public static final String CATEGORY_NAME_UI = "category_ui";

	public static void preInit()
	{
		/*
		 * Here is where you specify the location from where your config file will be
		 * read, or created if it is not present. Loader.instance().getConfigDir()
		 * returns the default config directory and you specify the name of the config
		 * file, together this works similar to the old getSuggestedConfigurationFile()
		 * function
		 */
		File configFile = new File(Loader.instance().getConfigDir(), "freecoins.cfg");
		// initialize your configuration object with your configuration file values
		config = new Configuration(configFile);

		// load config from file (see mbe70 package for more info)
		syncFromFile();
	}

	@SuppressWarnings("deprecation")
	public static void clientPreInit()
	{
		// register the save config handler to the forge mod loader event bus
		// creates an instance of the static class ConfigEventHandler and has it listen
		// on the FML bus (see Notes and ConfigEventHandler for more information)
		FMLCommonHandler.instance().bus().register(new ConfigEventHandler());
	}

	public static Configuration getConfig()
	{
		return config;
	}

	/**
	 * load the configuration values from the configuration file
	 */
	public static void syncFromFile()
	{
		syncConfig(true, true);
	}

	/**
	 * save the GUI-altered values to disk
	 */
	public static void syncFromGUI()
	{
		syncConfig(false, true);
	}

	/**
	 * save the MBEConfiguration variables (fields) to disk
	 */
	public static void syncFromFields()
	{
		syncConfig(false, false);
	}

	/**
	 * Synchronise the three copies of the data 1) loadConfigFromFile &&
	 * readFieldsFromConfig -> initialise everything from the disk file 2)
	 * !loadConfigFromFile && readFieldsFromConfig --> copy everything from the
	 * config file (altered by GUI) 3) !loadConfigFromFile && !readFieldsFromConfig
	 * --> copy everything from the native fields
	 *
	 * @param loadConfigFromFile
	 *            if true, load the config field from the configuration file on disk
	 * @param readFieldsFromConfig
	 *            if true, reload the member variables from the config field
	 */

	private static void syncConfig(boolean loadConfigFromFile, boolean readFieldsFromConfig)
	{
		// ---- step 1 - load raw values from config file (if loadFromFile true)
		// -------------------

		/*
		 * Check if this configuration object is the main config file or a child
		 * configuration For simple configuration setups, this only matters if you
		 * enable global configuration for your configuration object by using
		 * config.enableGlobalConfiguration(), this will cause your config file to be
		 * 'global.cfg' in the default configuration directory and use it to read/write
		 * your configuration options
		 */
		if (loadConfigFromFile)
		{
			config.load();
		}

		/*
		 * Using language keys are a good idea if you are using a config GUI This allows
		 * you to provide "pretty" names for the config properties in a .lang file as
		 * well as allow others to provide other localizations for your mod The language
		 * key is also used to get the tooltip for your property, the language key for
		 * each properties tooltip is langKey + ".tooltip" If no tooltip lang key isSHOW
		 * specified in a .lang file, it will default to the property's comment field
		 * prop.setRequiresWorldRestart(true); and prop.setRequiresMcRestart(true); can
		 * be used to tell Forge if that specific property requires a world or complete
		 * Minecraft restart, respectively Note: if a property requires a world restart
		 * it cannot be edited in the in-world mod settings (which hasn't been
		 * implemented yet by Forge), only when a world isn't loaded -See the function
		 * definitions for more info
		 */

		// ---- step 2 - define the properties in the configuration file
		// -------------------

		// The following code is used to define the properties in the configuration
		// file-
		// their name, type, default / min / max values, a comment. These affect what is
		// displayed on the GUI.
		// If the file already exists, the property values will already have been read
		// from the file, otherwise they
		// will be assigned the default value.

		// ---- GENERAL CONFIGURATION ----
		final boolean DISABLE_MOD_DEFAULT = false;
	    Property propDisableMod = config.get(CATEGORY_NAME_GENERAL, "disableMod", DISABLE_MOD_DEFAULT);
	    propDisableMod.comment = "Disable the mod, which turns off both coin spawning and the UI.";
	    propDisableMod.setLanguageKey("gui.freecoins.disableMod");
		
		final boolean MINIMAL_COINS_DEFAULT = false;
	    Property propMinimalCoins = config.get(CATEGORY_NAME_GENERAL, "minimalCoins", MINIMAL_COINS_DEFAULT);
	    propMinimalCoins.comment = "Only render 1 coin per hit, regardless of multiplayer. Can help with lag.";
	    propMinimalCoins.setLanguageKey("gui.freecoins.minimalCoins");
	    
		List<String> propOrderUI = new ArrayList<String>();
		propOrderUI.add(propDisableMod.getName());
		propOrderUI.add(propMinimalCoins.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_GENERAL, propOrderUI);
	    
		
		// ---- UI CONFIGURATION ----
		final int UI_X_MIN = 0;
		final int UI_X_MAX = 9999999;
		final int UI_X_DEFAULT = 12;
		Property propUiX = config.get(CATEGORY_NAME_UI, "uiX", UI_X_DEFAULT,
				"The X coordinate of the coin HUD.", UI_X_MIN, UI_X_MAX);
		propUiX.setRequiresWorldRestart(false);
		propUiX.setLanguageKey("gui.freecoins.uiX");

		final int UI_Y_MIN = 0;
		final int UI_Y_MAX = 9999999;
		final int UI_Y_DEFAULT = 30;
		Property propUiY = config.get(CATEGORY_NAME_UI, "uiY", UI_Y_DEFAULT,
				"The Y coordinate of the coin hud.", UI_Y_MIN, UI_Y_MAX);
		propUiY.setLanguageKey("gui.freecoins.uiY");
		
		final boolean SHOW_UI_DEFAULT = true;
	    Property propShowUi = config.get(CATEGORY_NAME_UI, "showUi", SHOW_UI_DEFAULT);
	    propShowUi.comment = "Whether or not to show the coin HUD.";
	    propShowUi.setLanguageKey("gui.freecoins.showUi");

		final boolean SIMPLE_UI_DEFAULT = false;
	    Property propSimpleUi = config.get(CATEGORY_NAME_UI, "simpleUi", SIMPLE_UI_DEFAULT);
	    propSimpleUi.comment = "Show only the player's current score on the HUD.";
	    propSimpleUi.setLanguageKey("gui.freecoins.simpleUi");

		final boolean SHOW_UI_TEXT_DEFAULT = true;
	    Property propShowUiText = config.get(CATEGORY_NAME_UI, "showUiText", SHOW_UI_TEXT_DEFAULT);
	    propShowUiText.comment = "Show descriptive text next to coin icons on HUD.";
	    propShowUiText.setLanguageKey("gui.freecoins.showUiText");
	    
		// By defining a property order we can control the order of the properties in
		// the config file and GUI
		// This is defined on a per config-category basis
		propOrderUI = new ArrayList<String>();
		propOrderUI.add(propShowUi.getName());
		propOrderUI.add(propSimpleUi.getName());
		propOrderUI.add(propShowUiText.getName());
		propOrderUI.add(propUiX.getName());
		propOrderUI.add(propUiY.getName());
		config.setCategoryPropertyOrder(CATEGORY_NAME_UI, propOrderUI);

		// ---- step 3 - read the configuration property values into the class's
		// variables (if readFieldsFromConfig) -------------------

		// As each value is read from the property, it should be checked to make sure it
		// is valid, in case someone
		// has manually edited or corrupted the value. The get() methods don't check
		// that the value is in range even
		// if you have specified a MIN and MAX value of the property

		if (readFieldsFromConfig)
		{
			disableMod = propDisableMod.getBoolean(DISABLE_MOD_DEFAULT);
			minimalCoins = propMinimalCoins.getBoolean(MINIMAL_COINS_DEFAULT);
			showUI = propShowUi.getBoolean(SHOW_UI_DEFAULT);
			simpleUI = propSimpleUi.getBoolean(SIMPLE_UI_DEFAULT);
			showUiText = propShowUiText.getBoolean(SHOW_UI_TEXT_DEFAULT);
			// If getInt cannot get an integer value from the config file value of uiX
			// (e.g. corrupted file)
			// it will set it to the default value passed to the function
			uiX = propUiX.getInt(UI_X_DEFAULT);
			if (uiX > UI_X_MAX || uiX < UI_X_MIN)
			{
				uiX = UI_X_DEFAULT;
			}
			
			uiY = propUiY.getInt(UI_Y_DEFAULT);
			if (uiY > UI_Y_MAX || uiY < UI_Y_MIN)
			{
				uiY = UI_Y_DEFAULT;
			}
		}

		// ---- step 4 - write the class's variables back into the config properties and
		// save to disk -------------------

		// This is done even for a loadFromFile==true, because some of the properties
		// may have been assigned default
		// values if the file was empty or corrupt.
		propDisableMod.set(disableMod);
		propMinimalCoins.set(minimalCoins);
		propShowUi.set(showUI);
		propSimpleUi.set(simpleUI);
		propShowUiText.set(showUiText);
		propUiX.set(uiX);
		propUiY.set(uiY);

		if (config.hasChanged())
		{
			config.save();
		}
	}

	// Define your configuration object
	private static Configuration config = null;

	public static class ConfigEventHandler
	{
		/*
		 * This class, when instantiated as an object, will listen on the FML event bus
		 * for an OnConfigChangedEvent
		 */
		@SubscribeEvent(priority = EventPriority.NORMAL)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent event)
		{
			if (FreeCoins.MODID.equals(event.modID))
			{
				if (event.configID.equals(CATEGORY_NAME_GENERAL) || event.configID.equals(CATEGORY_NAME_UI))
				{
					syncFromGUI();
				}
			}
		}
	}
}
