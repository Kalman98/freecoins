package net.kalman98.freecoins;

import java.util.Arrays;
import java.util.Random;

import net.kalman98.freecoins.entity.EntityCoin;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CoreEventHandler
{
	@SubscribeEvent
	public void onUpdateEvent(AttackEntityEvent event)
	{
		World world = event.entityPlayer.getEntityWorld();

		Random rand = new Random();
		if (!world.isRemote)
			return;
		Item[] stabbyWeapons = { Items.wooden_sword, Items.stone_sword, Items.golden_sword, Items.iron_sword,
				Items.diamond_sword };
		try
		{
			if (Arrays.asList(stabbyWeapons).contains(event.entityPlayer.getHeldItem().getItem()))
			{
				EntityCoin entity = new EntityCoin(world, event.target.posX, event.target.posY, event.target.posZ);
				entity.setPosition(event.target.posX, event.target.posY + 1.5, event.target.posZ);
				entity.setVelocity((rand.nextFloat() / 2.0F) - 0.25F, (rand.nextFloat() / 2.0F) - 0.25F,
						(rand.nextFloat() / 2.0F) - 0.25F);
	
				world.spawnEntityInWorld(entity);
			}
		}
		catch (NullPointerException e) {/*the player's hand was empty (holding null)*/}
	}
}