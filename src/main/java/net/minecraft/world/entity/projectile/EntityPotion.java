package net.minecraft.world.entity.projectile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.LingeringPotionSplashEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityPotion extends EntityProjectileThrowable implements ItemSupplier {
   public static final double b = 4.0;
   private static final double d = 16.0;
   public static final Predicate<EntityLiving> c = entityliving -> entityliving.eX() || entityliving.bK();

   public EntityPotion(EntityTypes<? extends EntityPotion> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityPotion(World world, EntityLiving entityliving) {
      super(EntityTypes.aA, entityliving, world);
   }

   public EntityPotion(World world, double d0, double d1, double d2) {
      super(EntityTypes.aA, d0, d1, d2, world);
   }

   @Override
   protected Item j() {
      return Items.up;
   }

   @Override
   protected float o() {
      return 0.05F;
   }

   @Override
   protected void a(MovingObjectPositionBlock movingobjectpositionblock) {
      super.a(movingobjectpositionblock);
      if (!this.H.B) {
         ItemStack itemstack = this.i();
         PotionRegistry potionregistry = PotionUtil.d(itemstack);
         List<MobEffect> list = PotionUtil.a(itemstack);
         boolean flag = potionregistry == Potions.c && list.isEmpty();
         EnumDirection enumdirection = movingobjectpositionblock.b();
         BlockPosition blockposition = movingobjectpositionblock.a();
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         if (flag) {
            this.a(blockposition1);
            this.a(blockposition1.a(enumdirection.g()));

            for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.a) {
               this.a(blockposition1.a(enumdirection1));
            }
         }
      }
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (!this.H.B) {
         ItemStack itemstack = this.i();
         PotionRegistry potionregistry = PotionUtil.d(itemstack);
         List<MobEffect> list = PotionUtil.a(itemstack);
         boolean flag = potionregistry == Potions.c && list.isEmpty();
         if (flag) {
            this.p();
         } else if (this.q()) {
            this.a(itemstack, potionregistry);
         } else {
            this.a(
               list, movingobjectposition.c() == MovingObjectPosition.EnumMovingObjectType.c ? ((MovingObjectPositionEntity)movingobjectposition).a() : null
            );
         }

         int i = potionregistry.b() ? 2007 : 2002;
         this.H.c(i, this.dg(), PotionUtil.c(itemstack));
         this.ai();
      }
   }

   private void p() {
      AxisAlignedBB axisalignedbb = this.cD().c(4.0, 2.0, 4.0);

      for(EntityLiving entityliving : this.H.a(EntityLiving.class, axisalignedbb, c)) {
         double d0 = this.f(entityliving);
         if (d0 < 16.0) {
            if (entityliving.eX()) {
               entityliving.a(this.dG().c(this, this.v()), 1.0F);
            }

            if (entityliving.bK() && entityliving.bq()) {
               entityliving.aA();
            }
         }
      }

      for(Axolotl axolotl : this.H.a(Axolotl.class, axisalignedbb)) {
         axolotl.q();
      }
   }

   private void a(List<MobEffect> list, @Nullable Entity entity) {
      AxisAlignedBB axisalignedbb = this.cD().c(4.0, 2.0, 4.0);
      List<EntityLiving> list1 = this.H.a(EntityLiving.class, axisalignedbb);
      Map<LivingEntity, Double> affected = new HashMap<>();
      if (!list1.isEmpty()) {
         Entity entity1 = this.z();

         for(EntityLiving entityliving : list1) {
            if (entityliving.fp()) {
               double d0 = this.f(entityliving);
               if (d0 < 16.0) {
                  double d1;
                  if (entityliving == entity) {
                     d1 = 1.0;
                  } else {
                     d1 = 1.0 - Math.sqrt(d0) / 4.0;
                  }

                  affected.put((LivingEntity)entityliving.getBukkitEntity(), d1);
               }
            }
         }
      }

      PotionSplashEvent event = CraftEventFactory.callPotionSplashEvent(this, affected);
      if (!event.isCancelled() && list != null && !list.isEmpty()) {
         Entity entity1 = this.z();

         for(LivingEntity victim : event.getAffectedEntities()) {
            if (victim instanceof CraftLivingEntity) {
               EntityLiving entityliving = ((CraftLivingEntity)victim).getHandle();
               double d1 = event.getIntensity(victim);

               for(MobEffect mobeffect : list) {
                  MobEffectList mobeffectlist = mobeffect.c();
                  if (!this.H.pvpMode && this.v() instanceof EntityPlayer && entityliving instanceof EntityPlayer && entityliving != this.v()) {
                     int i = MobEffectList.a(mobeffectlist);
                     if (i == 2 || i == 4 || i == 7 || i == 15 || i == 17 || i == 18 || i == 19) {
                        continue;
                     }
                  }

                  if (mobeffectlist.a()) {
                     mobeffectlist.a(this, this.v(), entityliving, mobeffect.e(), d1);
                  } else {
                     int i = mobeffect.a(j -> (int)(d1 * (double)j + 0.5));
                     MobEffect mobeffect1 = new MobEffect(mobeffectlist, i, mobeffect.e(), mobeffect.f(), mobeffect.g());
                     if (!mobeffect1.a(20)) {
                        entityliving.addEffect(mobeffect1, entity1, Cause.POTION_SPLASH);
                     }
                  }
               }
            }
         }
      }
   }

   private void a(ItemStack itemstack, PotionRegistry potionregistry) {
      EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.H, this.dl(), this.dn(), this.dr());
      Entity entity = this.v();
      if (entity instanceof EntityLiving) {
         entityareaeffectcloud.a((EntityLiving)entity);
      }

      entityareaeffectcloud.a(3.0F);
      entityareaeffectcloud.b(-0.5F);
      entityareaeffectcloud.d(10);
      entityareaeffectcloud.c(-entityareaeffectcloud.h() / (float)entityareaeffectcloud.m());
      entityareaeffectcloud.a(potionregistry);

      for(MobEffect mobeffect : PotionUtil.b(itemstack)) {
         entityareaeffectcloud.a(new MobEffect(mobeffect));
      }

      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null && nbttagcompound.b("CustomPotionColor", 99)) {
         entityareaeffectcloud.a(nbttagcompound.h("CustomPotionColor"));
      }

      LingeringPotionSplashEvent event = CraftEventFactory.callLingeringPotionSplashEvent(this, entityareaeffectcloud);
      if (!event.isCancelled() && !entityareaeffectcloud.dB()) {
         this.H.b(entityareaeffectcloud);
      } else {
         entityareaeffectcloud.ai();
      }
   }

   public boolean q() {
      return this.i().a(Items.us);
   }

   private void a(BlockPosition blockposition) {
      IBlockData iblockdata = this.H.a_(blockposition);
      if (iblockdata.a(TagsBlock.aH)) {
         if (!CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, Blocks.a.o()).isCancelled()) {
            this.H.a(blockposition, false);
         }
      } else if (AbstractCandleBlock.b(iblockdata)) {
         if (!CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, iblockdata.a(AbstractCandleBlock.b, Boolean.valueOf(false))).isCancelled()) {
            AbstractCandleBlock.a(null, iblockdata, this.H, blockposition);
         }
      } else if (BlockCampfire.g(iblockdata)
         && !CraftEventFactory.callEntityChangeBlockEvent(this, blockposition, iblockdata.a(BlockCampfire.b, Boolean.valueOf(false))).isCancelled()) {
         this.H.a(null, 1009, blockposition, 0);
         BlockCampfire.a(this.v(), this.H, blockposition, iblockdata);
         this.H.b(blockposition, iblockdata.a(BlockCampfire.b, Boolean.valueOf(false)));
      }
   }
}
