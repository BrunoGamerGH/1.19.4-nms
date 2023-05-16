package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityPiglinBrute extends EntityPiglinAbstract {
   private static final int bU = 50;
   private static final float bV = 0.35F;
   private static final int bW = 7;
   protected static final ImmutableList<SensorType<? extends Sensor<? super EntityPiglinBrute>>> bS = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.b, SensorType.f, SensorType.l
   );
   protected static final ImmutableList<MemoryModuleType<?>> bT = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.v,
      MemoryModuleType.g,
      MemoryModuleType.h,
      MemoryModuleType.k,
      MemoryModuleType.l,
      MemoryModuleType.an,
      MemoryModuleType.am,
      MemoryModuleType.x,
      MemoryModuleType.y,
      MemoryModuleType.m,
      MemoryModuleType.E,
      new MemoryModuleType[]{
         MemoryModuleType.o, MemoryModuleType.p, MemoryModuleType.q, MemoryModuleType.t, MemoryModuleType.aa, MemoryModuleType.L, MemoryModuleType.b
      }
   );

   public EntityPiglinBrute(EntityTypes<? extends EntityPiglinBrute> var0, World var1) {
      super(var0, var1);
      this.bI = 20;
   }

   public static AttributeProvider.Builder w() {
      return EntityMonster.fY().a(GenericAttributes.a, 50.0).a(GenericAttributes.d, 0.35F).a(GenericAttributes.f, 7.0);
   }

   @Nullable
   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      PiglinBruteAI.a(this);
      this.a(var0.r_(), var1);
      return super.a(var0, var1, var2, var3, var4);
   }

   @Override
   protected void a(RandomSource var0, DifficultyDamageScaler var1) {
      this.a(EnumItemSlot.a, new ItemStack(Items.og));
   }

   @Override
   protected BehaviorController.b<EntityPiglinBrute> dI() {
      return BehaviorController.a(bT, bS);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> var0) {
      return PiglinBruteAI.a(this, this.dI().a(var0));
   }

   @Override
   public BehaviorController<EntityPiglinBrute> dH() {
      return super.dH();
   }

   @Override
   public boolean q() {
      return false;
   }

   @Override
   public boolean k(ItemStack var0) {
      return var0.a(Items.og) ? super.k(var0) : false;
   }

   @Override
   protected void U() {
      this.H.ac().a("piglinBruteBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      PiglinBruteAI.b(this);
      PiglinBruteAI.c(this);
      super.U();
   }

   @Override
   public EntityPiglinArmPose fU() {
      return this.fM() && this.fV() ? EntityPiglinArmPose.a : EntityPiglinArmPose.f;
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      boolean var2 = super.a(var0, var1);
      if (this.H.B) {
         return false;
      } else {
         if (var2 && var0.d() instanceof EntityLiving) {
            PiglinBruteAI.a(this, (EntityLiving)var0.d());
         }

         return var2;
      }
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.rN;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.rQ;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.rP;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.rR, 0.15F, 1.0F);
   }

   protected void fZ() {
      this.a(SoundEffects.rO, 1.0F, this.eO());
   }

   @Override
   protected void fW() {
      this.a(SoundEffects.rS, 1.0F, this.eO());
   }
}
