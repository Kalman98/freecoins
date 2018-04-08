package net.kalman98.freecoins.proxy;

import net.kalman98.freecoins.client.renderer.entity.RenderCoin;
import net.kalman98.freecoins.entity.EntityCoin;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityCoin.class, RenderCoin::new);
	}
}
