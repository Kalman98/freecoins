package net.kalman98.freecoins.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCoin extends ModelBase
{
	ModelRenderer renderer;
	public ModelCoin() {
		renderer = new ModelRenderer(this, 0, 0);
		renderer.setTextureSize(4, 4);
		int width = 2;
		int height = 1;
		int depth = 2;
		renderer.addBox(-1.0F, -0.5F, -1.0F, width, height, depth);
	}
	
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale)
	{
		renderer.render(scale);
	}
}
