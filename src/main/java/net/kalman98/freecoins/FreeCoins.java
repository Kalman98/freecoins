package net.kalman98.freecoins;

import org.apache.logging.log4j.Logger;

import net.kalman98.freecoins.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FreeCoins.MODID,
	 version = FreeCoins.VERSION)
public class FreeCoins
{
    public static final String MODID = "freecoins";
    public static final String VERSION = "1.0";

	@SidedProxy(clientSide="net.kalman98.freecoins.proxy.ClientProxy", serverSide="net.kalman98.freecoins.proxy.ServerProxy")
	public static CommonProxy proxy;
    
	@Mod.Instance
	public static FreeCoins instance;

	public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	logger = e.getModLog();
		MinecraftForge.EVENT_BUS.register(new CoreEventHandler());
		proxy.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	proxy.init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit();
    }
}
