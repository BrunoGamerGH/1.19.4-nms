package net.minecraft.world.entity.monster.piglin;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.item.ItemToolMaterial;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public abstract class EntityPiglinAbstract extends EntityMonster {
   protected static final DataWatcherObject<Boolean> b = DataWatcher.a(EntityPiglinAbstract.class, DataWatcherRegistry.k);
   protected static final int c = 300;
   protected static final float d = 1.79F;
   public int e;

   public EntityPiglinAbstract(EntityTypes<? extends EntityPiglinAbstract> entitytypes, World world) {
      super(entitytypes, world);
      this.s(true);
      this.w();
      this.a(PathType.n, 16.0F);
      this.a(PathType.o, -1.0F);
   }

   private void w() {
      if (PathfinderGoalUtil.a(this)) {
         ((Navigation)this.G()).b(true);
      }
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 1.79F;
   }

   protected abstract boolean q();

   public void w(boolean flag) {
      this.aj().b(b, flag);
   }

   public boolean r() {
      return this.aj().a(b);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(b, false);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.r()) {
         nbttagcompound.a("IsImmuneToZombification", true);
      }

      nbttagcompound.a("TimeInOverworld", this.e);
   }

   @Override
   public double bu() {
      return this.y_() ? -0.05 : -0.45;
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("IsImmuneToZombification"));
      this.e = nbttagcompound.h("TimeInOverworld");
   }

   @Override
   protected void U() {
      super.U();
      if (this.fS()) {
         ++this.e;
      } else {
         this.e = 0;
      }

      if (this.e > 300) {
         this.fW();
         this.c((WorldServer)this.H);
      }
   }

   public boolean fS() {
      return !this.H.q_().b() && !this.r() && !this.fK();
   }

   protected void c(WorldServer worldserver) {
      EntityPigZombie entitypigzombie = this.convertTo(EntityTypes.bs, true, TransformReason.PIGLIN_ZOMBIFIED, SpawnReason.PIGLIN_ZOMBIFIED);
      if (entitypigzombie != null) {
         entitypigzombie.b(new MobEffect(MobEffects.i, 200, 0));
      }
   }

   public boolean fT() {
      return !this.y_();
   }

   public abstract EntityPiglinArmPose fU();

   @Nullable
   @Override
   public EntityLiving P_() {
      return this.by.c(MemoryModuleType.o).orElse(null);
   }

   protected boolean fV() {
      return this.eK().c() instanceof ItemToolMaterial;
   }

   @Override
   public void L() {
      if (PiglinAI.d(this)) {
         super.L();
      }
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   protected abstract void fW();
}
