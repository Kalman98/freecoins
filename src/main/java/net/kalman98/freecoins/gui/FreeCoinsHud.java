package net.kalman98.freecoins.gui;

import org.lwjgl.opengl.GL11;

import net.kalman98.freecoins.CoinboHandler;
import net.kalman98.freecoins.config.FreeCoinsConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FreeCoinsHud
{
	Minecraft mc = Minecraft.getMinecraft();
	public static FreeCoinsHud instance = new FreeCoinsHud();
	private static ResourceLocation coinHudTextures = new ResourceLocation("freecoins:textures/gui/coin_hud.png");
	
    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent event)
    {
    	if (!FreeCoinsConfiguration.showUI)
    		return;
    	// we *MUST* save OpenGL's current blend state here and
    	// remember to set it back to this later. Thanks
    	// StackOverflow :) https://stackoverflow.com/a/18022495
    	boolean blendEnabled = GL11.glGetBoolean(GL11.GL_BLEND);
    	int blendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
    	int blendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);
    	
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glScalef(0.1875F, 0.1875F, 1.0F);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        int coinhudX = FreeCoinsConfiguration.uiX, coinhudY = FreeCoinsConfiguration.uiY;

        int comX = coinhudX, highX = coinhudX + 2, currX = coinhudX + 2;
        int comY = coinhudY, highY = coinhudY + 13, currY = coinhudY + 22;
        // 5.33333 is the number to multiply by the scale factor for ~1
        mc.getTextureManager().bindTexture(coinHudTextures);
      
        if (!FreeCoinsConfiguration.simpleUI) {
        	mc.ingameGUI.drawTexturedModalRect(comX * 5.33333F, comY * 5.33333F, 38, 0, 60, 60); // 60
        	mc.ingameGUI.drawTexturedModalRect(highX * 5.33333F, highY * 5.33333F, 99, 0, 39, 37); // 60
        	mc.ingameGUI.drawTexturedModalRect(currX * 5.33333F, currY * 5.33333F, 0, 0, 37, 37); // 37
        }
        else
        {
        	mc.ingameGUI.drawTexturedModalRect(comX * 5.33333F, comY * 5.33333F, 0, 0, 37, 37); // 37
        }
        GL11.glPopMatrix();
        // old coordinates of UI elements, in order, for future reference
        // 60, 70
        // 70, 140
        // 70, 190
        
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(0.7F, 0.7F, 0.7F);
        // 1.428571429 is the number to multiply by the scale factor for 1
        if (FreeCoinsConfiguration.showUiText)
        {
	        if (!FreeCoinsConfiguration.simpleUI)
	        {
	        	mc.fontRendererObj.drawStringWithShadow("Combined:", (comX + 13) * 1.42857F, (comY + 3) * 1.42857F, 0xE8D43F);
	        	mc.fontRendererObj.drawStringWithShadow("Highest:", (highX + 11) * 1.42857F, (highY + 1) * 1.42857F, 0xE8D43F);
	        	mc.fontRendererObj.drawStringWithShadow("Current:", (currX + 11) * 1.42857F, (currY + 1) * 1.42857F, 0xE8D43F);
	        }
	        else
	        {
	        	mc.fontRendererObj.drawStringWithShadow("Current:", (comX + 11) * 1.42857F, (comY + 1) * 1.42857F, 0xE8D43F);	
	        }
	        
	        // 120, 78
	        // 120, 136
	        // 120, 184
	        if (!FreeCoinsConfiguration.simpleUI)
	        {
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getOverallScore()), (comX + 48) * 1.42857F, (comY + 3.5F) * 1.42857F, 0xFFFFFF);
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getHighScore()), (highX + 46) * 1.42857F, (highY + 1.5F) * 1.42857F, 0xFFFFFF);
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getScore()), (currX + 46) * 1.42857F, (currY + 1.5F) * 1.42857F, 0xFFFFFF);
	        }
	        else
	        {
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getScore()), (comX + 46) * 1.42857F, (comY + 1.5F) * 1.42857F, 0xFFFFFF);        	
	        }
        }
        else
        {
	        if (!FreeCoinsConfiguration.simpleUI)
	        {
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getOverallScore()), (comX + 13) * 1.42857F, (comY + 3) * 1.42857F, 0xFFFFFF);
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getHighScore()), (highX + 11) * 1.42857F, (highY + 1) * 1.42857F, 0xFFFFFF);
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getScore()), (currX + 11) * 1.42857F, (currY + 1) * 1.42857F, 0xFFFFFF);
	        }
	        else
	        {
	        	mc.fontRendererObj.drawStringWithShadow(String.valueOf(CoinboHandler.getInstance().getScore()), (comX + 11) * 1.42857F, (comY + 1) * 1.42857F, 0xFFFFFF);        	
	        }
        }
        // 300, 186
        // 300, 136
        // 300, 78
        
        GL11.glPopMatrix();
        mc.getTextureManager().bindTexture(Gui.icons);
        
        // set the blend state back to how it was before - fixes
        // the scoreboard transparency issue
        if (blendEnabled) {
            GL11.glEnable(GL11.GL_BLEND);
        }
        else {
            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glBlendFunc(blendSrc, blendDst);
    }
}
