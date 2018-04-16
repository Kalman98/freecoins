package net.kalman98.freecoins.proxy;

import net.kalman98.freecoins.client.renderer.entity.RenderCoin;
import net.kalman98.freecoins.config.FreeCoinsConfiguration;
import net.kalman98.freecoins.entity.EntityCoin;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		FreeCoinsConfiguration.clientPreInit();
		RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, RenderCoin::new);
	}
}
