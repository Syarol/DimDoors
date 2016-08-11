package com.zixiken.dimdoors.client;

import com.zixiken.dimdoors.core.PocketManager;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClosingRiftFX extends EntityFX
{
	private int baseTextureIndex = 160;
	private boolean trail;
	private boolean twinkle;
	private EffectRenderer effectRenderer;
	private float fadeColourRed;
	private float fadeColourGreen;
	private float fadeColourBlue;
	private boolean hasFadeColour;

	public ClosingRiftFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ, EffectRenderer effectRenderer) {

		super(world, x, y, z);
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.effectRenderer = effectRenderer;
		this.particleScale *= .55F;
		this.particleMaxAge = 30 + this.rand.nextInt(16);
		this.noClip = true;
	}

	/**
	 * returns the bounding box for this entity
	 */
	 @Override
	public AxisAlignedBB getCollisionBoundingBox()
	{
		 return null;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	 @Override
	public boolean canBePushed() {
		 return false;
	 }

	 @Override
	public void renderParticle(WorldRenderer worldRenderer, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
		if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
			 this.doRenderParticle(worldRenderer, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
		}
	 }

	public void doRenderParticle(WorldRenderer worldRenderer, float par2, float par3, float par4, float par5, float par6, float par7) {
		float var8 = super.particleTextureIndexX % 16 / 16.0F;
		float var9 = var8 + 0.0624375F;
		float var10 = this.particleTextureIndexX / 16 / 16.0F;
		float var11 = var10 + 0.0624375F;
		float var12 = 0.1F * this.particleScale;
		float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
		float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
		float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
		float var16 = 0.8F;

		if (PocketManager.createDimensionData(worldObj).isPocketDimension()) {
		    var16 = 0.4F;
		}

		worldRenderer.pos(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12).tex(var9, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, (float) .7).endVertex();
        worldRenderer.pos(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12).tex(var9, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, (float) .7).endVertex();
        worldRenderer.pos(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12).tex(var8, var10).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, (float) .7).endVertex();
        worldRenderer.pos(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12).tex(var8, var11).color(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16, (float) .7).endVertex();
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
    	    this.setDead();
	    } if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0F - ((float)this.particleAge - (float)(this.particleMaxAge / 2)) / this.particleMaxAge);

	        if (this.hasFadeColour) {
                this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2F;
                this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2F;
				this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2F;
            }
        }

		this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
		// this.motionY -= 0.004D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9100000262260437D;
		this.motionY *= 0.9100000262260437D;
		this.motionZ *= 0.9100000262260437D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;}

		if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
			ClosingRiftFX var1 = new ClosingRiftFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.effectRenderer);
			var1.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
			var1.particleAge = var1.particleMaxAge / 2;

			if (this.hasFadeColour) {
				var1.hasFadeColour = true;
				var1.fadeColourRed = this.fadeColourRed;
				var1.fadeColourGreen = this.fadeColourGreen;
				var1.fadeColourBlue = this.fadeColourBlue;
            }

			var1.twinkle = this.twinkle;
			this.effectRenderer.addEffect(var1);
        }
    }

	@Override
	public int getBrightnessForRender(float par1) {
		  return 15728880;
    }

	/**
	 * Gets how bright this entity is.
	 */
	@Override
    public float getBrightness(float par1) {
        return 1.0F;
    }
}