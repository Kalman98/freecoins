
package net.kalman98.freecoins.proxy;

import net.kalman98.freecoins.CoreEventHandler;
import net.kalman98.freecoins.entity.FreeCoinsEntities;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy
{
	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(new CoreEventHandler());
		FreeCoinsEntities.setup();
	}

	public void init()
	{
		
	}

	public void postInit()
	{
		
	}
}