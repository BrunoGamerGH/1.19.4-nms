package net.minecraft.world.entity.animal.camel;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Dynamic;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IJumpable;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.RiderShieldingMount;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.control.EntityAIBodyControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;

public class Camel extends EntityHorseAbstract implements IJumpable, RiderShieldingMount, ISaddleable {
   public static final RecipeItemStack bS = RecipeItemStack.a(Items.ey);
   public static final int bT = 55;
   public static final int bV = 30;
   private static final float ct = 0.1F;
   private static final float cu = 1.4285F;
   private static final float cv = 22.2222F;
   private static final int cw = 40;
   private static final int cx = 52;
   private static final int cy = 80;
   private static final float cz = 1.43F;
   public static final DataWatcherObject<Boolean> bW = DataWatcher.a(Camel.class, DataWatcherRegistry.k);
   public static final DataWatcherObject<Long> bX = DataWatcher.a(Camel.class, DataWatcherRegistry.c);
   public final AnimationState bY = new AnimationState();
   public final AnimationState bZ = new AnimationState();
   public final AnimationState ca = new AnimationState();
   public final AnimationState cb = new AnimationState();
   public final AnimationState cc = new AnimationState();
   private static final EntitySize cA = EntitySize.b(EntityTypes.l.k(), EntityTypes.l.l() - 1.43F);
   private int cB = 0;
   private int cC = 0;

   public Camel(EntityTypes<? extends Camel> var0, World var1) {
      super(var0, var1);
      this.v(1.5F);
      this.bK = new Camel.b();
      Navigation var2 = (Navigation)this.G();
      var2.a(true);
      var2.e(true);
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("LastPoseTick", this.am.a(bX));
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      long var1 = var0.i("LastPoseTick");
      if (var1 < 0L) {
         this.b(EntityPose.k);
      }

      this.a(var1);
   }

   public static AttributeProvider.Builder q() {
      return gs().a(GenericAttributes.a, 32.0).a(GenericAttributes.d, 0.09F).a(GenericAttributes.m, 0.42F);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bW, false);
      this.am.a(bX, 0L);
   }

   @Override
   public GroupDataEntity a(WorldAccess var0, DifficultyDamageScaler var1, EnumMobSpawn var2, @Nullable GroupDataEntity var3, @Nullable NBTTagCompound var4) {
      CamelAi.a(this, var0.r_());
      this.b(var0.C().U());
      return super.a(var0, var1, var2, var3, var4);
   }

   @Override
   protected BehaviorController.b<Camel> dI() {
      return CamelAi.a();
   }

   @Override
   protected void x() {
   }

   @Override
   protected BehaviorController<?> a(Dynamic<?> var0) {
      return CamelAi.a(this.dI().a(var0));
   }

   @Override
   public EntitySize a(EntityPose var0) {
      return var0 == EntityPose.k ? cA.a(this.dS()) : super.a(var0);
   }

   @Override
   protected float b(EntityPose var0, EntitySize var1) {
      return var1.b - 0.1F;
   }

   @Override
   public double d() {
      return 0.5;
   }

   @Override
   protected void U() {
      this.H.ac().a("camelBrain");
      BehaviorController<?> var0 = this.dH();
      var0.a((WorldServer)this.H, this);
      this.H.ac().c();
      this.H.ac().a("camelActivityUpdate");
      CamelAi.a(this);
      this.H.ac().c();
      super.U();
   }

   @Override
   public void l() {
      super.l();
      if (this.w() && this.cB < 55 && (this.N || this.aT())) {
         this.w(false);
      }

      if (this.cB > 0) {
         --this.cB;
         if (this.cB == 0) {
            this.H.a(null, this.dg(), SoundEffects.cO, SoundCategory.g, 1.0F, 1.0F);
         }
      }

      if (this.H.k_()) {
         this.gE();
      }

      if (this.r()) {
         this.a(this, 30.0F);
      }
   }

   private void gE() {
      if (this.cC <= 0) {
         this.cC = this.af.a(40) + 80;
         this.cb.a(this.ag);
      } else {
         --this.cC;
      }

      if (this.gb()) {
         this.ca.a();
         this.cc.a();
         if (this.gF()) {
            this.bY.b(this.ag);
            this.bZ.a();
         } else {
            this.bY.a();
            this.bZ.b(this.ag);
         }
      } else {
         this.bY.a();
         this.bZ.a();
         this.cc.a(this.w(), this.ag);
         this.ca.a(this.gc() && this.gg() >= 0L, this.ag);
      }
   }

   @Override
   protected void g(float var0) {
      float var1;
      if (this.al() == EntityPose.a && !this.cc.c()) {
         var1 = Math.min(var0 * 6.0F, 1.0F);
      } else {
         var1 = 0.0F;
      }

      this.aP.a(var1, 0.2F);
   }

   @Override
   public void h(Vec3D var0) {
      if (this.r() && this.ax()) {
         this.f(this.dj().d(0.0, 1.0, 0.0));
         var0 = var0.d(0.0, 1.0, 0.0);
      }

      super.h(var0);
   }

   @Override
   protected void a(EntityLiving var0, Vec3D var1) {
      super.a(var0, var1);
      if (var0.bl > 0.0F && this.ga() && !this.gc()) {
         this.ge();
      }
   }

   public boolean r() {
      return this.ga() || this.gc();
   }

   @Override
   protected float g(EntityLiving var0) {
      float var1 = var0.bU() && this.V_() == 0 ? 0.1F : 0.0F;
      return (float)this.b(GenericAttributes.d) + var1;
   }

   @Override
   protected Vec2F m(EntityLiving var0) {
      return this.r() ? new Vec2F(this.dy(), this.dw()) : super.m(var0);
   }

   @Override
   protected Vec3D b(EntityLiving var0, Vec3D var1) {
      return this.r() ? Vec3D.b : super.b(var0, var1);
   }

   @Override
   public boolean a() {
      return !this.r() && super.a();
   }

   @Override
   public void b(int var0) {
      if (this.i() && this.cB <= 0 && this.ax()) {
         super.b(var0);
      }
   }

   @Override
   public boolean dz() {
      return true;
   }

   @Override
   protected void b(float var0, Vec3D var1) {
      double var2 = this.b(GenericAttributes.m) * (double)this.aE() + this.eR();
      this.g(
         this.bC()
            .d(1.0, 0.0, 1.0)
            .d()
            .a((double)(22.2222F * var0) * this.b(GenericAttributes.d) * (double)this.aF())
            .b(0.0, (double)(1.4285F * var0) * var2, 0.0)
      );
      this.cB = 55;
      this.w(true);
      this.at = true;
   }

   public boolean w() {
      return this.am.a(bW);
   }

   public void w(boolean var0) {
      this.am.b(bW, var0);
   }

   public boolean fS() {
      return this.dH().a(MemoryModuleType.Y, MemoryStatus.a);
   }

   @Override
   public void c(int var0) {
      this.a(SoundEffects.cN, 1.0F, 1.0F);
      this.w(true);
   }

   @Override
   public void b() {
   }

   @Override
   public int V_() {
      return this.cB;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.cM;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.cP;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.cR;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      if (var1.t() == SoundEffectType.i) {
         this.a(SoundEffects.cW, 1.0F, 1.0F);
      } else {
         this.a(SoundEffects.cV, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean m(ItemStack var0) {
      return bS.a(var0);
   }

   @Override
   public EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      ItemStack var2 = var0.b(var1);
      if (var0.fz()) {
         this.b(var0);
         return EnumInteractionResult.a(this.H.B);
      } else {
         EnumInteractionResult var3 = var2.a(var0, this, var1);
         if (var3.a()) {
            return var3;
         } else if (this.m(var2)) {
            return this.c(var0, var2);
         } else {
            if (this.cM().size() < 2 && !this.y_()) {
               this.e(var0);
            }

            return EnumInteractionResult.a(this.H.B);
         }
      }
   }

   @Override
   protected void B(float var0) {
      if (var0 > 6.0F && this.ga() && !this.gc()) {
         this.ge();
      }
   }

   @Override
   protected boolean a(EntityHuman var0, ItemStack var1) {
      if (!this.m(var1)) {
         return false;
      } else {
         boolean var2 = this.eo() < this.eE();
         if (var2) {
            this.b(2.0F);
         }

         boolean var3 = this.gh() && this.h() == 0 && this.fT();
         if (var3) {
            this.f(var0);
         }

         boolean var4 = this.y_();
         if (var4) {
            this.H.a(Particles.M, this.d(1.0), this.do() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
            if (!this.H.B) {
               this.b_(10);
            }
         }

         if (!var2 && !var3 && !var4) {
            return false;
         } else {
            if (!this.aO()) {
               SoundEffect var5 = this.fZ();
               if (var5 != null) {
                  this.H.a(null, this.dl(), this.dn(), this.dr(), var5, this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
               }
            }

            return true;
         }
      }
   }

   @Override
   protected boolean fY() {
      return false;
   }

   @Override
   public boolean a(EntityAnimal var0) {
      if (var0 != this && var0 instanceof Camel var1 && this.gA() && var1.gA()) {
         return true;
      }

      return false;
   }

   @Nullable
   public Camel b(WorldServer var0, EntityAgeable var1) {
      return EntityTypes.l.a((World)var0);
   }

   @Nullable
   @Override
   protected SoundEffect fZ() {
      return SoundEffects.cQ;
   }

   protected void f(DamageSource var0, float var1) {
      this.gf();
      super.f(var0, var1);
   }

   @Override
   public void i(Entity var0) {
      int var1 = this.cM().indexOf(var0);
      if (var1 >= 0) {
         boolean var2 = var1 == 0;
         float var3 = 0.5F;
         float var4 = (float)(this.dB() ? 0.01F : this.a(var2, 0.0F) + var0.bu());
         if (this.cM().size() > 1) {
            if (!var2) {
               var3 = -0.7F;
            }

            if (var0 instanceof EntityAnimal) {
               var3 += 0.2F;
            }
         }

         Vec3D var5 = new Vec3D(0.0, 0.0, (double)var3).b(-this.aT * (float) (Math.PI / 180.0));
         var0.e(this.dl() + var5.c, this.dn() + (double)var4, this.dr() + var5.e);
         this.a(var0);
      }
   }

   private double a(boolean var0, float var1) {
      double var2 = this.bv();
      float var4 = this.dS() * 1.43F;
      float var5 = var4 - this.dS() * 0.2F;
      float var6 = var4 - var5;
      boolean var7 = this.gc();
      boolean var8 = this.ga();
      if (var7) {
         int var9 = var8 ? 40 : 52;
         int var10;
         float var11;
         if (var8) {
            var10 = 28;
            var11 = var0 ? 0.5F : 0.1F;
         } else {
            var10 = var0 ? 24 : 32;
            var11 = var0 ? 0.6F : 0.35F;
         }

         float var12 = MathHelper.a((float)this.gg() + var1, 0.0F, (float)var9);
         boolean var13 = var12 < (float)var10;
         float var14 = var13 ? var12 / (float)var10 : (var12 - (float)var10) / (float)(var9 - var10);
         float var15 = var4 - var11 * var5;
         var2 += var8
            ? (double)MathHelper.i(var14, var13 ? var4 : var15, var13 ? var15 : var6)
            : (double)MathHelper.i(var14, var13 ? var6 - var4 : var6 - var15, var13 ? var6 - var15 : 0.0F);
      }

      if (var8 && !var7) {
         var2 += (double)var6;
      }

      return var2;
   }

   @Override
   public Vec3D t(float var0) {
      return new Vec3D(0.0, this.a(true, var0) - (double)(0.2F * this.dS()), (double)(this.dc() * 0.56F));
   }

   @Override
   public double bv() {
      return (double)(this.a(this.ga() ? EntityPose.k : EntityPose.a).b - (this.y_() ? 0.35F : 0.6F));
   }

   @Override
   public void j(Entity var0) {
      if (this.cK() != var0) {
         this.a(var0);
      }
   }

   private void a(Entity var0) {
      var0.s(this.dw());
      float var1 = var0.dw();
      float var2 = MathHelper.g(var1 - this.dw());
      float var3 = MathHelper.a(var2, -160.0F, 160.0F);
      var0.L += var3 - var2;
      float var4 = var1 + var3 - var2;
      var0.f(var4);
      var0.r(var4);
   }

   private void a(Entity var0, float var1) {
      float var2 = var0.ck();
      float var3 = MathHelper.g(this.aT - var2);
      float var4 = MathHelper.a(MathHelper.g(this.aT - var2), -var1, var1);
      float var5 = var2 + var3 - var4;
      var0.r(var5);
   }

   @Override
   public int W() {
      return 30;
   }

   @Override
   protected boolean o(Entity var0) {
      return this.cM().size() <= 2;
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      if (!this.cM().isEmpty() && this.i()) {
         Entity var0 = this.cM().get(0);
         if (var0 instanceof EntityLiving) {
            return (EntityLiving)var0;
         }
      }

      return null;
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   public boolean ga() {
      return this.am.a(bX) < 0L;
   }

   public boolean gb() {
      return this.gg() < 0L != this.ga();
   }

   public boolean gc() {
      long var0 = this.gg();
      return var0 < (long)(this.ga() ? 40 : 52);
   }

   private boolean gF() {
      return this.ga() && this.gg() < 40L && this.gg() >= 0L;
   }

   public void gd() {
      if (!this.ga()) {
         this.a(SoundEffects.cT, 1.0F, 1.0F);
         this.b(EntityPose.k);
         this.a(-this.H.U());
      }
   }

   public void ge() {
      if (this.ga()) {
         this.a(SoundEffects.cU, 1.0F, 1.0F);
         this.b(EntityPose.a);
         this.a(this.H.U());
      }
   }

   public void gf() {
      this.b(EntityPose.a);
      this.b(this.H.U());
   }

   @VisibleForTesting
   public void a(long var0) {
      this.am.b(bX, var0);
   }

   private void b(long var0) {
      this.a(Math.max(0L, var0 - 52L - 1L));
   }

   public long gg() {
      return this.H.U() - Math.abs(this.am.a(bX));
   }

   @Override
   public SoundEffect Q_() {
      return SoundEffects.cS;
   }

   @Override
   public void a(DataWatcherObject<?> var0) {
      if (!this.al && bW.equals(var0)) {
         this.cB = this.cB == 0 ? 55 : this.cB;
      }

      super.a(var0);
   }

   @Override
   protected EntityAIBodyControl A() {
      return new Camel.a(this);
   }

   @Override
   public boolean gh() {
      return true;
   }

   @Override
   public void b(EntityHuman var0) {
      if (!this.H.B) {
         var0.a(this, this.cn);
      }
   }

   class a extends EntityAIBodyControl {
      public a(Camel var1) {
         super(var1);
      }

      @Override
      public void a() {
         if (!Camel.this.r()) {
            super.a();
         }
      }
   }

   class b extends ControllerMove {
      public b() {
         super(Camel.this);
      }

      @Override
      public void a() {
         if (this.k == ControllerMove.Operation.b && !Camel.this.fI() && Camel.this.ga() && !Camel.this.gc()) {
            Camel.this.ge();
         }

         super.a();
      }
   }
}
