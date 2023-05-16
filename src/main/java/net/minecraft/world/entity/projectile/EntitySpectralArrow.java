package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntitySpectralArrow extends EntityArrow {
   public int f = 200;

   public EntitySpectralArrow(EntityTypes<? extends EntitySpectralArrow> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntitySpectralArrow(World world, EntityLiving entityliving) {
      super(EntityTypes.aR, entityliving, world);
   }

   public EntitySpectralArrow(World world, double d0, double d1, double d2) {
      super(EntityTypes.aR, d0, d1, d2, world);
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B && !this.b) {
         this.H.a(Particles.P, this.dl(), this.dn(), this.dr(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected ItemStack o() {
      return new ItemStack(Items.uq);
   }

   @Override
   protected void a(EntityLiving entityliving) {
      super.a(entityliving);
      MobEffect mobeffect = new MobEffect(MobEffects.x, this.f, 0);
      entityliving.addEffect(mobeffect, this.z(), Cause.ARROW);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("Duration")) {
         this.f = nbttagcompound.h("Duration");
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Duration", this.f);
   }
}
