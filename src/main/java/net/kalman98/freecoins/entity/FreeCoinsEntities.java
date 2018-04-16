package net.kalman98.freecoins.entity;

import net.kalman98.freecoins.FreeCoins;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class FreeCoinsEntities
{
	/**
	 * Initializes and registers all entities from the mod.
	 */
	public static void setup()
	{
		registerEntities();
	}

	public static void registerEntities()
	{
		registerEntity(EntityCoin.class, "coin", 80, 3, true);
	}

	// bits of this code were borrowed from Choonster because he wrote it very
	// neatly!
	// https://github.com/Choonster/TestMod3/blob/a6e5f7c223a18e4a53732af49aac6ac1bb52cc6f/src/main/java/choonster/testmod3/init/ModEntities.java
	private static int entityID = 0;
	
	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param entityClass
	 *            The entity's class
	 * @param entityName
	 *            The entity's unique name
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, FreeCoins.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
	/**
	 * Register an entity with the specified tracking values and spawn egg colours.
	 *
	 * @param entityClass
	 *            The entity's class
	 * @param entityName
	 *            The entity's unique name
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 * @param eggPrimary
	 *            The spawn egg's primary (background) colour
	 * @param eggSecondary
	 *            The spawn egg's secondary (foreground) colour
	 */
	@SuppressWarnings("unused")
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary)
	{
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, FreeCoins.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
	}
}
