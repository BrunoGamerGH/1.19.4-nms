package net.minecraft.world.entity.monster;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.behavior.BehaviorAttack;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetForget;
import net.minecraft.world.entity.ai.behavior.BehaviorAttackTargetSet;
import net.minecraft.world.entity.ai.behavior.BehaviorGateSingle;
import net.minecraft.world.entity.ai.behavior.BehaviorLook;
import net.minecraft.world.entity.ai.behavior.BehaviorLookWalk;
import net.minecraft.world.entity.ai.behavior.BehaviorNop;
import net.minecraft.world.entity.ai.behavior.BehaviorStrollRandomUnconstrained;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.behavior.BehaviorWalkAwayOutOfRange;
import net.minecraft.world.entity.ai.behavior.BehavorMove;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.hoglin.IOglin;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityZoglin extends EntityMonster implements IMonster, IOglin {
   private static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityZoglin.class, DataWatcherRegistry.k);
   private static final int e = 40;
   private static final int bS = 1;
   private static final float bT = 0.6F;
   private static final int bU = 6;
   private static final float bV = 0.5F;
   private static final int bW = 40;
   private static final int bX = 15;
   private static final int bY = 200;
   private static final float bZ = 0.3F;
   private static final float ca = 0.4F;
   private int cb;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super EntityZoglin>>> b = ImmutableList.of(SensorType.c, SensorType.d);
   protected static final ImmutableList<? extends MemoryModuleType<?>> c = ImmutableList.of(
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.n,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.o,
      MemoryModuleType.p
   );

   public EntityZoglin(EntityTypes<? extends EntityZoglin> var0, World var1) {
      super(var0, var1);
      this.bI = 5;
   }

   @Override
   protected BehaviorController.b<EntityZoglin> dI() {
      return BehaviorController.a(c, b);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> var0) {
      BehaviorController<EntityZoglin> var1 = this.dI().a(var0);
      a(var1);
      b(var1);
      c(var1);
      var1.a(ImmutableSet.of(Activity.a));
      var1.b(Activity.b);
      var1.f();
      return var1;
   }

   private static void a(BehaviorController<EntityZoglin> var0) {
      var0.a(Activity.a, 0, ImmutableList.of(new BehaviorLook(45, 90), new BehavorMove()));
   }

   private static void b(BehaviorController<EntityZoglin> var0) {
      var0.a(
         Activity.b,
         10,
         ImmutableList.of(
            BehaviorAttackTargetSet.a(EntityZoglin::fU),
            SetEntityLookTargetSometimes.a(8.0F, UniformInt.a(30, 60)),
            new BehaviorGateSingle(
               ImmutableList.of(
                  Pair.of(BehaviorStrollRandomUnconstrained.a(0.4F), 2), Pair.of(BehaviorLookWalk.a(0.4F, 3), 2), Pair.of(new BehaviorNop(30, 60), 1)
               )
            )
         )
      );
   }

   private static void c(BehaviorController<EntityZoglin> var0) {
      var0.a(
         Activity.k,
         10,
         ImmutableList.of(
            BehaviorWalkAwayOutOfRange.a(1.0F),
            BehaviorBuilder.a(EntityZoglin::r, BehaviorAttack.a(40)),
            BehaviorBuilder.a(EntityZoglin::y_, BehaviorAttack.a(15)),
            BehaviorAttackTargetForget.a()
         ),
         MemoryModuleType.o
      );
   }

   private Optional<? extends EntityLiving> fU() {
      return this.dH().c(MemoryModuleType.h).orElse(NearestVisibleLivingEntities.a()).a(this::m);
   }

   private boolean m(EntityLiving var0) {
      EntityTypes<?> var1 = var0.ae();
      return var1 != EntityTypes.bo && var1 != EntityTypes.u && Sensor.c(this, var0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(d, false);
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      super.a(var0);
      if (d.equals(var0)) {
         this.c_();
      }
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY()
         .a(GenericAttributes.a, 40.0)
         .a(GenericAttributes.d, 0.3F)
         .a(GenericAttributes.c, 0.6F)
         .a(GenericAttributes.g, 1.0)
         .a(GenericAttributes.f, 6.0);
   }

   public boolean r() {
      return !this.y_();
   }

   @Override
   public boolean z(Entity var0) {
      if (!(var0 instanceof EntityLiving)) {
         return false;
      } else {
         this.cb = 10;
         this.H.a(this, (byte)4);
         this.a(SoundEffects.Ar, 1.0F, this.eO());
         return IOglin.a(this, (EntityLiving)var0);
      }
   }

   @Override
   public boolean a(EntityHuman var0) {
      return !this.fI();
   }

   @Override
   protected void e(EntityLiving var0) {
      if (!this.y_()) {
         IOglin.b(this, var0);
      }
   }

   @Override
   public double bv() {
      return (double)this.dd() - (this.y_() ? 0.2 : 0.15);
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      boolean var2 = super.a(var0, var1);
      if (this.H.B) {
         return false;
      } else if (var2 && var0.d() instanceof EntityLiving var3) {
         if (this.c(var3) && !BehaviorUtil.a(this, var3, 4.0)) {
            this.n(var3);
         }

         return var2;
      } else {
         return var2;
      }
   }

   private void n(EntityLiving var0) {
      this.by.b(MemoryModuleType.E);
      this.by.a(MemoryModuleType.o, var0, 200L);
   }

   @Override
   public BehaviorController<EntityZoglin> dH() {
      return super.dH();
   }

   protected void w() {
      Activity var0 = this.by.g().orElse(null);
      this.by.a(ImmutableList.of(Activity.k, Activity.b));
      Activity var1 = this.by.g().orElse(null);
      if (var1 == Activity.k && var0 != Activity.k) {
         this.fT();
      }

      this.v(this.by.a(MemoryModuleType.o));
   }

   @Override
   protected void U() {
      this.H.ac().a("zoglinBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.w();
   }

   @Override
   public void a(boolean var0) {
      this.aj().b(d, var0);
      if (!this.H.B && var0) {
         this.a(GenericAttributes.f).a(0.5);
      }
   }

   @Override
   public boolean y_() {
      return this.aj().a(d);
   }

   @Override
   public void b_() {
      if (this.cb > 0) {
         --this.cb;
      }

      super.b_();
   }

   @Override
   public void b(byte var0) {
      if (var0 == 4) {
         this.cb = 10;
         this.a(SoundEffects.Ar, 1.0F, this.eO());
      } else {
         super.b(var0);
      }
   }

   @Override
   public int fS() {
      return this.cb;
   }

   @Override
   protected SoundEffect s() {
      if (this.H.B) {
         return null;
      } else {
         return this.by.a(MemoryModuleType.o) ? SoundEffects.Aq : SoundEffects.Ap;
      }
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.At;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.As;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.Au, 0.15F, 1.0F);
   }

   protected void fT() {
      this.a(SoundEffects.Aq, 1.0F, this.eO());
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.y_()) {
         var0.a("IsBaby", true);
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      if (var0.q("IsBaby")) {
         this.a(true);
      }
   }
}
