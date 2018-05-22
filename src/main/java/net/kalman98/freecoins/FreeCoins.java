package net.kalman98.freecoins;

import org.apache.logging.log4j.Logger;

import net.kalman98.freecoins.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = FreeCoins.MODID,
	 version = FreeCoins.VERSION,
	 guiFactory= FreeCoins.GUIFACTORY)
public class FreeCoins
{
    public static final String MODID = "freecoins";
    public static final String VERSION = "1.2";

    public static final String GUIFACTORY = "net.kalman98.freecoins.config.FreeCoinsGuiFactory";
	
	@Mod.Instance(FreeCoins.MODID)
	public static FreeCoins instance;
	
    @SidedProxy(clientSide="net.kalman98.freecoins.proxy.ClientProxy", serverSide="net.kalman98.freecoins.proxy.ServerProxy")
	public static CommonProxy proxy;

	public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
    	logger = e.getModLog();
		proxy.preInit(e);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
    	proxy.init(e);
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }
}
