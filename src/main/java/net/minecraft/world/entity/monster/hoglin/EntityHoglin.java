package net.minecraft.world.entity.monster.hoglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityZoglin;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityHoglin extends EntityAnimal implements IMonster, IOglin {
   private static final DataWatcherObject<Boolean> bV = DataWatcher.a(EntityHoglin.class, DataWatcherRegistry.k);
   private static final float bW = 0.2F;
   private static final int bX = 40;
   private static final float bY = 0.3F;
   private static final int bZ = 1;
   private static final float ca = 0.6F;
   private static final int cb = 6;
   private static final float cc = 0.5F;
   private static final int cd = 300;
   private int ce;
   public int cf;
   public boolean cg;
   protected static final ImmutableList<? extends SensorType<? extends Sensor<? super EntityHoglin>>> bS = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.n, SensorType.m
   );
   protected static final ImmutableList<? extends MemoryModuleType<?>> bT = ImmutableList.of(
      MemoryModuleType.r,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.n,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.o,
      MemoryModuleType.p,
      MemoryModuleType.ap,
      new MemoryModuleType[]{
         MemoryModuleType.z, MemoryModuleType.ar, MemoryModuleType.as, MemoryModuleType.ao, MemoryModuleType.J, MemoryModuleType.av, MemoryModuleType.aw
      }
   );

   public EntityHoglin(EntityTypes<? extends EntityHoglin> var0, World var1) {
      super(var0, var1);
      this.bI = 5;
   }

   @Override
   public boolean a(EntityHuman var0) {
      return !this.fI();
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY()
         .a(GenericAttributes.a, 40.0)
         .a(GenericAttributes.d, 0.3F)
         .a(GenericAttributes.c, 0.6F)
         .a(GenericAttributes.g, 1.0)
         .a(GenericAttributes.f, 6.0);
   }

   @Override
   public boolean z(Entity var0) {
      if (!(var0 instanceof EntityLiving)) {
         return false;
      } else {
         this.ce = 10;
         this.H.a(this, (byte)4);
         this.a(SoundEffects.kF, 1.0F, this.eO());
         HoglinAI.a(this, (EntityLiving)var0);
         return IOglin.a(this, (EntityLiving)var0);
      }
   }

   @Override
   protected void e(EntityLiving var0) {
      if (this.r()) {
         IOglin.b(this, var0);
      }
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      boolean var2 = super.a(var0, var1);
      if (this.H.B) {
         return false;
      } else {
         if (var2 && var0.d() instanceof EntityLiving) {
            HoglinAI.b(this, (EntityLiving)var0.d());
         }

         return var2;
      }
   }

   @Override
   protected BehaviorController.b<EntityHoglin> dI() {
      return BehaviorController.a(bT, bS);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> var0) {
      return HoglinAI.a(this.dI().a(var0));
   }

   @Override
   public BehaviorController<EntityHoglin> dH() {
      return super.dH();
   }

   @Override
   protected void U() {
      this.H.ac().a("hoglinBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      HoglinAI.a(this);
      if (this.w()) {
         ++this.cf;
         if (this.cf > 300) {
            this.b(SoundEffects.kG);
            this.c((WorldServer)this.H);
         }
      } else {
         this.cf = 0;
      }
   }

   @Override
   public void b_() {
      if (this.ce > 0) {
         --this.ce;
      }

      super.b_();
   }

   @Override
   protected void m() {
      if (this.y_()) {
         this.bI = 3;
         this.a(GenericAttributes.f).a(0.5);
      } else {
         this.bI = 5;
         this.a(GenericAttributes.f).a(6.0);
      }
   }

   public static boolean c(EntityTypes<EntityHoglin> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return !var1.a_(var3.d()).a(Blocks.kH);
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      if (var0.r_().i() < 0.2F) {
         this.a(true);
      }

      return super.a(var0, var1, var2, var3, var4);
   }

   @Override
   public boolean h(double var0) {
      return !this.fB();
   }

   @Override
   public float a(BlockPosition var0, IWorldReader var1) {
      if (HoglinAI.a(this, var0)) {
         return -1.0F;
      } else {
         return var1.a_(var0.d()).a(Blocks.os) ? 10.0F : 0.0F;
      }
   }

   @Override
   public double bv() {
      return (double)this.dd() - (this.y_() ? 0.2 : 0.15);
   }

   @Override
   public EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      EnumInteractionResult var2 = super.b(var0, var1);
      if (var2.a()) {
         this.fz();
      }

      return var2;
   }

   @Override
   public void b(byte var0) {
      if (var0 == 4) {
         this.ce = 10;
         this.a(SoundEffects.kF, 1.0F, this.eO());
      } else {
         super.b(var0);
      }
   }

   @Override
   public int fS() {
      return this.ce;
   }

   @Override
   public boolean dV() {
      return true;
   }

   @Override
   public int dX() {
      return this.bI;
   }

   private void c(WorldServer var0) {
      EntityZoglin var1 = this.a(EntityTypes.bo, true);
      if (var1 != null) {
         var1.b(new MobEffect(MobEffects.i, 200, 0));
      }
   }

   @Override
   public boolean m(ItemStack var0) {
      return var0.a(Items.de);
   }

   public boolean r() {
      return !this.y_();
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bV, false);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      if (this.fZ()) {
         var0.a("IsImmuneToZombification", true);
      }

      var0.a("TimeInOverworld", this.cf);
      if (this.cg) {
         var0.a("CannotBeHunted", true);
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.w(var0.q("IsImmuneToZombification"));
      this.cf = var0.h("TimeInOverworld");
      this.x(var0.q("CannotBeHunted"));
   }

   public void w(boolean var0) {
      this.aj().b(bV, var0);
   }

   public boolean fZ() {
      return this.aj().a(bV);
   }

   public boolean w() {
      return !this.H.q_().b() && !this.fZ() && !this.fK();
   }

   private void x(boolean var0) {
      this.cg = var0;
   }

   public boolean fY() {
      return this.r() && !this.cg;
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      EntityHoglin var2 = EntityTypes.W.a((World)var0);
      if (var2 != null) {
         var2.fz();
      }

      return var2;
   }

   @Override
   public boolean fT() {
      return !HoglinAI.c(this) && super.fT();
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected SoundEffect s() {
      return this.H.B ? null : HoglinAI.b(this).orElse(null);
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.kI;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.kH;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.ln;
   }

   @Override
   protected SoundEffect aJ() {
      return SoundEffects.lm;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.kK, 0.15F, 1.0F);
   }

   protected void b(SoundEffect var0) {
      this.a(var0, this.eN(), this.eO());
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }
}
