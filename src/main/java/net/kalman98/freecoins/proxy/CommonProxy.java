
package net.kalman98.freecoins.proxy;

import net.kalman98.freecoins.CoreEventHandler;
import net.kalman98.freecoins.config.FreeCoinsConfiguration;
import net.kalman98.freecoins.entity.FreeCoinsEntities;
import net.kalman98.freecoins.gui.FreeCoinsHud;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		FreeCoinsConfiguration.preInit();
		MinecraftForge.EVENT_BUS.register(new CoreEventHandler());
		FreeCoinsEntities.setup();
	}

	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(FreeCoinsHud.instance);
	}

	public void postInit(FMLPostInitializationEvent e)
	{

	}
}