package net.kalman98.freecoins.client.renderer.entity;

import javax.annotation.Nonnull;

import net.kalman98.freecoins.entity.EntityCoin;
import net.kalman98.freecoins.entity.model.ModelCoin;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCoin extends Render<EntityCoin>
{
	protected static ModelBase modelCoin = new ModelCoin();
	
	public RenderCoin(RenderManager rm)
	{
		super(rm);
	}

	public void doRender(@Nonnull EntityCoin entitycoin, double x, double y, double z, float rotationYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		
		GlStateManager.translate((float) x, (float) y, (float) z);
		
		if (!bindEntityTexture(entitycoin)) {
			return;
		}
        GlStateManager.scale(0.0625F, 0.0625F, 0.0625F);
        
        if (!entitycoin.onGround)
        {
        	GlStateManager.rotate(entitycoin.getAge() * entitycoin.rotSpeed, entitycoin.rotX, entitycoin.rotY, entitycoin.rotZ);
        }
        else
        {
        	GlStateManager.rotate(entitycoin.groundRot, 0.0F, entitycoin.rotY, 0.0F);
        }
        
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
        
        modelCoin.render(entitycoin, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
		
		GlStateManager.popMatrix();
		super.doRender(entitycoin, x, y, z, rotationYaw, partialTicks);
	}
	
	private static final ResourceLocation COIN_TEXTURE = new ResourceLocation("freecoins:textures/entity/coin.png");

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless
	 * you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityCoin entity)
	{
		return COIN_TEXTURE;
	}
}