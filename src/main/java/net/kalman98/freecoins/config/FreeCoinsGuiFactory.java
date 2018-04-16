package net.kalman98.freecoins.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.kalman98.freecoins.FreeCoins;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.GuiConfigEntries.CategoryEntry;
import net.minecraftforge.fml.client.config.IConfigElement;

public class FreeCoinsGuiFactory implements IModGuiFactory
{
	// this class is accessed when Forge needs a GUI made relating to your mod (e.g.
	// config GUI)

	@Override
	public void initialize(Minecraft minecraftInstance)
	{
		// needed to implement IModGuiFactory but doesn't need to do anything
		// for the configuration gui
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return FreeCoinsConfigGui.class; // tells Forge which class represents our main GUI screen
	}

	// The following two functions are needed for implementation only, the config
	// gui does not require them
	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		return null;
	}

	// This class inherits from GuiConfig, a specialized GuiScreen designed to
	// display your
	// configuration categories
	public static class FreeCoinsConfigGui extends GuiConfig
	{
		public FreeCoinsConfigGui(GuiScreen parentScreen)
		{
			// I18n function basically "translates" or localizes the given key using the
			// appropriate .lang file
			super(parentScreen, getConfigElements(), FreeCoins.MODID, false, false,
					I18n.format("gui.freecoins.config.mainTitle"));
			titleLine2 = FreeCoinsConfiguration.getConfig().getConfigFile().toString();
		}

		private static List<IConfigElement> getConfigElements()
		{
			List<IConfigElement> list = new ArrayList<IConfigElement>();
			// Add the two buttons that will go to each config category edit screen
			list.add(new DummyCategoryElement("generalCfg", "gui.freecoins.config.ctgy.general",
					CategoryEntryGeneral.class));
			list.add(new DummyCategoryElement("uiCfg", "gui.freecoins.config.ctgy.ui", CategoryEntryUI.class));
			return list;
		}

		// the next two classes are the edit screens for each configuration category
		public static class CategoryEntryGeneral extends CategoryEntry
		{
			public CategoryEntryGeneral(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
			{
				super(owningScreen, owningEntryList, prop);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				// The following GuiConfig object specifies the configID of the object and thus
				// will force-save
				// when closed.
				// Parent GuiConfig object's entryList will also be refreshed to reflect the
				// changes.
				// --see GuiFactory of Forge for more info
				// Additionally, Forge best practices say to put the path to the config file for
				// the category as
				// the title for the category config screen

				Configuration configuration = FreeCoinsConfiguration.getConfig();
				System.out.print(configuration);
				ConfigElement cat_general = new ConfigElement(
						configuration.getCategory(FreeCoinsConfiguration.CATEGORY_NAME_GENERAL));
				List<IConfigElement> propertiesOnThisScreen = cat_general.getChildElements();
				String windowTitle = I18n.format("gui.freecoins.config.ctgy.general");

				return new GuiConfig(this.owningScreen, propertiesOnThisScreen, this.owningScreen.modID,
						FreeCoinsConfiguration.CATEGORY_NAME_GENERAL,
						this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
						this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
				// this is a complicated object that specifies the category's gui screen, to
				// better understand
				// how it works, look into the definitions of the called functions and objects
			}
		}

		public static class CategoryEntryUI extends CategoryEntry
		{
			public CategoryEntryUI(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
			{
				super(owningScreen, owningEntryList, prop);
			}

			@Override
			protected GuiScreen buildChildScreen()
			{
				Configuration configuration = FreeCoinsConfiguration.getConfig();
				ConfigElement cat_ui = new ConfigElement(
						configuration.getCategory(FreeCoinsConfiguration.CATEGORY_NAME_UI));
				List<IConfigElement> propertiesOnThisScreen = cat_ui.getChildElements();
				String windowTitle = I18n.format("gui.freecoins.config.ctgy.ui");

				return new GuiConfig(this.owningScreen, propertiesOnThisScreen, this.owningScreen.modID,
						FreeCoinsConfiguration.CATEGORY_NAME_UI,
						this.configElement.requiresWorldRestart() || this.owningScreen.allRequireWorldRestart,
						this.configElement.requiresMcRestart() || this.owningScreen.allRequireMcRestart, windowTitle);
				// this is a complicated object that specifies the category's gui screen, to
				// better understand
				// how it works, look into the definitions of the called functions and objects
			}
		}
	}
}