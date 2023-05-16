package net.minecraft.world.entity.animal.horse;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.npc.EntityVillagerTrader;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityLlamaTrader extends EntityLlama {
   private int bT = 47999;

   public EntityLlamaTrader(EntityTypes<? extends EntityLlamaTrader> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   public boolean gb() {
      return true;
   }

   @Nullable
   @Override
   protected EntityLlama gg() {
      return EntityTypes.ba.a(this.H);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("DespawnDelay", this.bT);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("DespawnDelay", 99)) {
         this.bT = nbttagcompound.h("DespawnDelay");
      }
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(1, new PathfinderGoalPanic(this, 2.0));
      this.bO.a(1, new EntityLlamaTrader.a(this));
   }

   @Override
   public void v(int i) {
      this.bT = i;
   }

   @Override
   protected void e(EntityHuman entityhuman) {
      Entity entity = this.fJ();
      if (!(entity instanceof EntityVillagerTrader)) {
         super.e(entityhuman);
      }
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B) {
         this.gI();
      }
   }

   private void gI() {
      if (this.gJ()) {
         this.bT = this.gK() ? ((EntityVillagerTrader)this.fJ()).gb() - 1 : this.bT - 1;
         if (this.bT <= 0) {
            this.a(true, false);
            this.ai();
         }
      }
   }

   private boolean gJ() {
      return !this.gh() && !this.gL() && !this.cR();
   }

   private boolean gK() {
      return this.fJ() instanceof EntityVillagerTrader;
   }

   private boolean gL() {
      return this.fI() && !this.gK();
   }

   @Nullable
   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      if (enummobspawn == EnumMobSpawn.h) {
         this.c_(0);
      }

      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(false);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   protected static class a extends PathfinderGoalTarget {
      private final EntityLlama a;
      private EntityLiving b;
      private int c;

      public a(EntityLlama entityllama) {
         super(entityllama, false);
         this.a = entityllama;
         this.a(EnumSet.of(PathfinderGoal.Type.d));
      }

      @Override
      public boolean a() {
         if (!this.a.fI()) {
            return false;
         } else {
            Entity entity = this.a.fJ();
            if (!(entity instanceof EntityVillagerTrader)) {
               return false;
            } else {
               EntityVillagerTrader entityvillagertrader = (EntityVillagerTrader)entity;
               this.b = entityvillagertrader.ea();
               int i = entityvillagertrader.eb();
               return i != this.c && this.a(this.b, PathfinderTargetCondition.a);
            }
         }
      }

      @Override
      public void c() {
         this.e.setTarget(this.b, TargetReason.TARGET_ATTACKED_OWNER, true);
         Entity entity = this.a.fJ();
         if (entity instanceof EntityVillagerTrader) {
            this.c = ((EntityVillagerTrader)entity).eb();
         }

         super.c();
      }
   }
}
