package net.kalman98.freecoins.entity;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCoin extends Entity
{
	private int age;
	public int lifespan = 20;
	public int rotSpeed = 0;
	public int groundRot = 0;
	public float rotX, rotY, rotZ;
	
	public EntityCoin(World worldIn, double x, double y, double z, float velX, float velY, float velZ)
	{
		super(worldIn);
		this.setPosition(x, y, z);
		this.setVelocity(velX, velY, velZ);
		Random rand = new Random();
		this.rotSpeed = 50 + rand.nextInt(20);
		this.rotX = rand.nextFloat();
		this.rotY = rand.nextFloat();
		this.rotZ = rand.nextFloat();
		this.setSize(2.0F / 16.0F, 1.0F / 16.0F);
	}
	
	public EntityCoin(World worldIn, double x, double y, double z)
	{
		super(worldIn);
		this.setPosition(x, y, z);
		Random rand = new Random();
		this.rotSpeed = 50 + rand.nextInt(20);
		this.rotX = rand.nextFloat();
		this.rotY = rand.nextFloat();
		this.rotZ = rand.nextFloat();
		this.setSize(2.0F / 16.0F, 1.0F / 16.0F);
	}
	
	public EntityCoin(World worldIn)
	{
		super(worldIn);
		Random rand = new Random();
		this.rotSpeed = 50 + rand.nextInt(20);
		this.rotX = rand.nextFloat();
		this.rotY = rand.nextFloat();
		this.rotZ = rand.nextFloat();
		this.setSize(2.0F / 16.0F, 1.0F / 16.0F);
	}
	
    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public void onUpdate()
    {

    	super.onUpdate();
    	
    	this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03999999910593033D;
        this.noClip = this.pushOutOfBlocks(this.posX, (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0D, this.posZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        
        float f = 0.98F;

        if (!this.onGround)
        {
        	this.groundRot = 0;
        }
        else if (this.onGround && this.groundRot == 0)
        {
        	this.groundRot = this.getAge() * this.rotSpeed;
        }
        
        if (this.onGround)
        {
            f = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.98F;
        }

        this.motionX *= (double)f;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= (double)f;

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }
        
        this.handleWaterMovement();
        
        ++this.age;
		if (this.age >= lifespan)
		{
			this.setDead();
		}
    }
    
    /**
     * Returns if this entity is in water and will end up adding the waters velocity to the entity
     */
    public boolean handleWaterMovement()
    {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this))
        {
            if (!this.inWater && !this.firstUpdate)
            {
                this.resetHeight();
            }

            this.inWater = true;
        }
        else
        {
            this.inWater = false;
        }

        return this.inWater;
    }

	@Override
	protected void entityInit()
	{

	}
	
	 @SideOnly(Side.CLIENT)
	    public int getAge()
	    {
	        return this.age;
	    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    public boolean canAttackWithItem()
    {
        return false;
    }
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound)
	{
        this.age = tagCompound.getShort("Age");
        if (tagCompound.hasKey("Lifespan")) lifespan = tagCompound.getInteger("Lifespan");	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound)
	{
        tagCompound.setShort("Age", (short)this.age);
        tagCompound.setInteger("Lifespan", lifespan);
	}
	
	@Override
	public String getName()
	{
		return "Coin";
	}
	@Override
	public void setEntityBoundingBox(AxisAlignedBB bb)
	{
		// TODO Auto-generated method stub
		super.setEntityBoundingBox(bb);
	}
}
