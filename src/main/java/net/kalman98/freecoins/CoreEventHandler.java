package net.kalman98.freecoins;

import java.util.Arrays;
import java.util.Random;

import net.kalman98.freecoins.config.FreeCoinsConfiguration;
import net.kalman98.freecoins.entity.EntityCoin;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CoreEventHandler
{

	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent e)
	{
		if (e.entity.getClass().equals(EntityPlayerSP.class))
		{
			CoinboHandler.getInstance().worldChange();
		}
	}

	@SubscribeEvent
	public void onAttackEntityEvent(AttackEntityEvent e)
	{
		World world = e.entityPlayer.getEntityWorld();
		Random rand = new Random();
		if (!world.isRemote)
			return;
		Item[] stabbyWeapons = { Items.wooden_sword, Items.stone_sword, Items.golden_sword, Items.iron_sword,
				Items.diamond_sword };
		try
		{
			// the last condition in this if statement should, more plainly put, check if
			// the player's hurtResistantTime is less than or equal to 13. 10 is the time
			// when the mob can take damage again, but for some reason when we get it here
			// it's delayed by several ticks, so we have to add three to account for that.
			if (e.target.getClass().equals(EntityOtherPlayerMP.class)
					&& Arrays.asList(stabbyWeapons).contains(e.entityPlayer.getHeldItem().getItem())
					&& e.target.isEntityAlive() && ((float) ((EntityLivingBase) e.target).getHealth()) > 0.0F
					&& ((EntityLivingBase) e.target).hurtResistantTime <= (((EntityLivingBase) e.target).maxHurtResistantTime
							/ 2.0F) + 3)
			{
				// ----- get the amount of coins to render -----
				int coinCount = 1;
				if (CoinboHandler.getInstance().isComboValid(world.getTotalWorldTime()))
				{
					CoinboHandler.getInstance().incrementMultiplier();
					for (int i = 0; i < CoinboHandler.getInstance().getMultiplier() - 1 && coinCount < 8; i++)
						coinCount = coinCount * 2;
				} else
				{
					CoinboHandler.getInstance().resetMultiplier();
				}
				// ----- create coin entities and render -----
				for (int i = 0; i < coinCount; i++)
				{
					EntityCoin entity = new EntityCoin(world, e.target.posX, e.target.posY, e.target.posZ);
					entity.setPosition(e.target.posX, e.target.posY + 1.5, e.target.posZ);
					entity.setVelocity((rand.nextFloat() / 2.0F) - 0.25F, (rand.nextFloat() / 2.0F) - 0.25F,
							(rand.nextFloat() / 2.0F) - 0.25F);

					world.spawnEntityInWorld(entity);
					if (FreeCoinsConfiguration.minimalCoins == true)
						break;
				}
				CoinboHandler.getInstance().addScore(coinCount);
				CoinboHandler.getInstance().addOverallScore(coinCount);
				if (CoinboHandler.getInstance().getScore() > CoinboHandler.getInstance().getHighScore())
					CoinboHandler.getInstance().setHighScore(CoinboHandler.getInstance().getScore());
				CoinboHandler.getInstance().setMostRecentHitTime(world.getTotalWorldTime());
			}
		} catch (NullPointerException ex){/* the player's hand was empty (holding null) */}
	}
}