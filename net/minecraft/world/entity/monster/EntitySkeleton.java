package net.minecraft.world.entity.monster;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class EntitySkeleton extends EntitySkeletonAbstract {
   private static final int c = 300;
   public static final DataWatcherObject<Boolean> d = DataWatcher.a(EntitySkeleton.class, DataWatcherRegistry.k);
   public static final String b = "StrayConversionTime";
   private int e;
   public int bS;

   public EntitySkeleton(EntityTypes<? extends EntitySkeleton> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void a_() {
      super.a_();
      this.aj().a(d, false);
   }

   public boolean fT() {
      return this.aj().a(d);
   }

   public void w(boolean flag) {
      this.am.b(d, flag);
   }

   @Override
   public boolean fS() {
      return this.fT();
   }

   @Override
   public void l() {
      if (!this.H.B && this.bq() && !this.fK()) {
         if (this.az) {
            if (this.fT()) {
               --this.bS;
               if (this.bS < 0) {
                  this.fU();
               }
            } else {
               ++this.e;
               if (this.e >= 140) {
                  this.b(300);
               }
            }
         } else {
            this.e = -1;
            this.w(false);
         }
      }

      super.l();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("StrayConversionTime", this.fT() ? this.bS : -1);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("StrayConversionTime", 99) && nbttagcompound.h("StrayConversionTime") > -1) {
         this.b(nbttagcompound.h("StrayConversionTime"));
      }
   }

   public void b(int i) {
      this.bS = i;
      this.w(true);
   }

   protected void fU() {
      this.convertTo(EntityTypes.aU, true, TransformReason.FROZEN, SpawnReason.FROZEN);
      if (!this.aO()) {
         this.H.a(null, 1048, this.dg(), 0);
      }
   }

   @Override
   public boolean du() {
      return false;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.uY;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.vj;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.va;
   }

   @Override
   SoundEffect r() {
      return SoundEffects.vl;
   }

   @Override
   protected void a(DamageSource damagesource, int i, boolean flag) {
      super.a(damagesource, i, flag);
      Entity entity = damagesource.d();
      if (entity instanceof EntityCreeper entitycreeper && entitycreeper.fT()) {
         entitycreeper.fU();
         this.a(Items.tn);
      }
   }
}
