package net.minecraft.world.entity.animal;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutGameStateChange;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityPufferFish extends EntityFish {
   private static final DataWatcherObject<Integer> e = DataWatcher.a(EntityPufferFish.class, DataWatcherRegistry.b);
   int bS;
   int bT;
   private static final Predicate<EntityLiving> bU = entityliving -> entityliving instanceof EntityHuman && ((EntityHuman)entityliving).f()
         ? false
         : entityliving.ae() == EntityTypes.f || entityliving.eJ() != EnumMonsterType.e;
   static final PathfinderTargetCondition bV = PathfinderTargetCondition.b().e().d().a(bU);
   public static final int b = 0;
   public static final int c = 1;
   public static final int d = 2;

   public EntityPufferFish(EntityTypes<? extends EntityPufferFish> entitytypes, World world) {
      super(entitytypes, world);
      this.c_();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(e, 0);
   }

   public int fU() {
      return this.am.a(e);
   }

   public void c(int i) {
      this.am.b(e, i);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (e.equals(datawatcherobject)) {
         this.c_();
      }

      super.a(datawatcherobject);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("PuffState", this.fU());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.c(Math.min(nbttagcompound.h("PuffState"), 2));
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pN);
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(1, new EntityPufferFish.a(this));
   }

   @Override
   public void l() {
      if (!this.H.B && this.bq() && this.cU()) {
         if (this.bS > 0) {
            if (this.fU() == 0) {
               this.a(SoundEffects.sO, this.eN(), this.eO());
               this.c(1);
            } else if (this.bS > 40 && this.fU() == 1) {
               this.a(SoundEffects.sO, this.eN(), this.eO());
               this.c(2);
            }

            ++this.bS;
         } else if (this.fU() != 0) {
            if (this.bT > 60 && this.fU() == 2) {
               this.a(SoundEffects.sN, this.eN(), this.eO());
               this.c(1);
            } else if (this.bT > 100 && this.fU() == 1) {
               this.a(SoundEffects.sN, this.eN(), this.eO());
               this.c(0);
            }

            ++this.bT;
         }
      }

      super.l();
   }

   @Override
   public void b_() {
      super.b_();
      if (this.bq() && this.fU() > 0) {
         for(EntityInsentient entityinsentient : this.H.a(EntityInsentient.class, this.cD().g(0.3), entityinsentientx -> bV.a(this, entityinsentientx))) {
            if (entityinsentient.bq()) {
               this.a(entityinsentient);
            }
         }
      }
   }

   private void a(EntityInsentient entityinsentient) {
      int i = this.fU();
      if (entityinsentient.a(this.dG().b((EntityLiving)this), (float)(1 + i))) {
         entityinsentient.addEffect(new MobEffect(MobEffects.s, 60 * i, 0), this, Cause.ATTACK);
         this.a(SoundEffects.sS, 1.0F, 1.0F);
      }
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      int i = this.fU();
      if (entityhuman instanceof EntityPlayer && i > 0 && entityhuman.a(this.dG().b((EntityLiving)this), (float)(1 + i))) {
         if (!this.aO()) {
            ((EntityPlayer)entityhuman).b.a(new PacketPlayOutGameStateChange(PacketPlayOutGameStateChange.j, 0.0F));
         }

         entityhuman.addEffect(new MobEffect(MobEffects.s, 60 * i, 0), this, Cause.ATTACK);
      }
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.sM;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.sP;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.sR;
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.sQ;
   }

   @Override
   public EntitySize a(EntityPose entitypose) {
      return super.a(entitypose).a(r(this.fU()));
   }

   private static float r(int i) {
      switch(i) {
         case 0:
            return 0.5F;
         case 1:
            return 0.7F;
         default:
            return 1.0F;
      }
   }

   private static class a extends PathfinderGoal {
      private final EntityPufferFish a;

      public a(EntityPufferFish entitypufferfish) {
         this.a = entitypufferfish;
      }

      @Override
      public boolean a() {
         List<EntityLiving> list = this.a.H.a(EntityLiving.class, this.a.cD().g(2.0), entityliving -> EntityPufferFish.bV.a(this.a, entityliving));
         return !list.isEmpty();
      }

      @Override
      public void c() {
         this.a.bS = 1;
         this.a.bT = 0;
      }

      @Override
      public void d() {
         this.a.bS = 0;
      }
   }
}
