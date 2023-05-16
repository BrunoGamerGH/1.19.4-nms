package net.minecraft.world.entity.animal.frog;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationGuardian;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.EntityFish;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTransformEvent.TransformReason;

public class Tadpole extends EntityFish {
   @VisibleForTesting
   public static int b = Math.abs(-24000);
   public static float c = 0.4F;
   public static float d = 0.3F;
   public int bT;
   protected static final ImmutableList<SensorType<? extends Sensor<? super Tadpole>>> e = ImmutableList.of(
      SensorType.c, SensorType.d, SensorType.f, SensorType.r
   );
   protected static final ImmutableList<MemoryModuleType<?>> bS = ImmutableList.of(
      MemoryModuleType.n,
      MemoryModuleType.h,
      MemoryModuleType.m,
      MemoryModuleType.E,
      MemoryModuleType.t,
      MemoryModuleType.J,
      MemoryModuleType.O,
      MemoryModuleType.Q,
      MemoryModuleType.N,
      MemoryModuleType.r,
      MemoryModuleType.Y
   );

   public Tadpole(EntityTypes<? extends EntityFish> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.bJ = new SmoothSwimmingLookControl(this, 10);
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new NavigationGuardian(this, world);
   }

   @Override
   protected BehaviorController.b<Tadpole> dI() {
      return BehaviorController.a(bS, e);
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> dynamic) {
      return TadpoleAi.a(this.dI().a(dynamic));
   }

   @Override
   public BehaviorController<Tadpole> dH() {
      return super.dH();
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.xi;
   }

   @Override
   protected void U() {
      this.H.ac().a("tadpoleBrain");
      this.dH().a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("tadpoleActivityUpdate");
      TadpoleAi.a(this);
      this.H.ac().c();
      super.U();
   }

   public static AttributeProvider.Builder fU() {
      return EntityInsentient.y().a(GenericAttributes.d, 1.0).a(GenericAttributes.a, 6.0);
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B) {
         this.r(this.bT + 1);
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Age", this.bT);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.r(nbttagcompound.h("Age"));
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.xk;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.xh;
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (this.m(itemstack)) {
         this.a(entityhuman, itemstack);
         return EnumInteractionResult.a(this.H.B);
      } else {
         return Bucketable.a(entityhuman, enumhand, this).orElse(super.b(entityhuman, enumhand));
      }
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   @Override
   public boolean r() {
      return true;
   }

   @Override
   public void w(boolean flag) {
   }

   @Override
   public void l(ItemStack itemstack) {
      Bucketable.a(this, itemstack);
      NBTTagCompound nbttagcompound = itemstack.v();
      nbttagcompound.a("Age", this.fV());
   }

   @Override
   public void c(NBTTagCompound nbttagcompound) {
      Bucketable.a(this, nbttagcompound);
      if (nbttagcompound.e("Age")) {
         this.r(nbttagcompound.h("Age"));
      }
   }

   @Override
   public ItemStack b() {
      return new ItemStack(Items.pS);
   }

   @Override
   public SoundEffect w() {
      return SoundEffects.cC;
   }

   private boolean m(ItemStack itemstack) {
      return Frog.bS.a(itemstack);
   }

   private void a(EntityHuman entityhuman, ItemStack itemstack) {
      this.b(entityhuman, itemstack);
      this.c(EntityAgeable.d_(this.fX()));
      this.H.a(Particles.M, this.d(1.0), this.do() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
   }

   private void b(EntityHuman entityhuman, ItemStack itemstack) {
      if (!entityhuman.fK().d) {
         itemstack.h(1);
      }
   }

   private int fV() {
      return this.bT;
   }

   private void c(int i) {
      this.r(this.bT + i * 20);
   }

   private void r(int i) {
      this.bT = i;
      if (this.bT >= b) {
         this.fW();
      }
   }

   private void fW() {
      World world = this.H;
      if (world instanceof WorldServer worldserver) {
         Frog frog = EntityTypes.O.a(this.H);
         if (frog != null) {
            frog.b(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
            frog.a(worldserver, this.H.d_(frog.dg()), EnumMobSpawn.i, null, null);
            frog.t(this.fK());
            if (this.aa()) {
               frog.b(this.ab());
               frog.n(this.cx());
            }

            frog.fz();
            if (CraftEventFactory.callEntityTransformEvent(this, frog, TransformReason.METAMORPHOSIS).isCancelled()) {
               this.r(0);
               return;
            }

            this.a(SoundEffects.xj, 0.15F, 1.0F);
            worldserver.addFreshEntityWithPassengers(frog, SpawnReason.METAMORPHOSIS);
            this.ai();
         }
      }
   }

   private int fX() {
      return Math.max(0, b - this.bT);
   }

   @Override
   public boolean dV() {
      return false;
   }
}
