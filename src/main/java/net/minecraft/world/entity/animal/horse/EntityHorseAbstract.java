package net.minecraft.world.entity.animal.horse;

import com.google.common.collect.UnmodifiableIterator;
import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.players.NameReferencingFileConverter;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.IInventoryListener;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMainHand;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.IJumpable;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTame;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.RandomStandGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.DismountUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public abstract class EntityHorseAbstract extends EntityAnimal implements IInventoryListener, HasCustomInventoryScreen, OwnableEntity, IJumpable, ISaddleable {
   public static final int cd = 400;
   public static final int ce = 499;
   public static final int cf = 500;
   public static final double cg = 0.15;
   private static final float bS = (float)b(() -> 0.0);
   private static final float bT = (float)b(() -> 1.0);
   private static final float bV = (float)a(() -> 0.0);
   private static final float bW = (float)a(() -> 1.0);
   private static final float bX = a(i -> 0);
   private static final float bY = a(i -> i - 1);
   private static final float bZ = 0.25F;
   private static final float ca = 0.5F;
   private static final Predicate<EntityLiving> cb = entityliving -> entityliving instanceof EntityHorseAbstract && ((EntityHorseAbstract)entityliving).gm();
   private static final PathfinderTargetCondition cc = PathfinderTargetCondition.b().a(16.0).d().a(cb);
   private static final RecipeItemStack ct = RecipeItemStack.a(Items.oE, Items.qI, Blocks.ii.k(), Items.nB, Items.tm, Items.pi, Items.pj);
   private static final DataWatcherObject<Byte> cu = DataWatcher.a(EntityHorseAbstract.class, DataWatcherRegistry.a);
   private static final int cv = 2;
   private static final int cw = 4;
   private static final int cx = 8;
   private static final int cy = 16;
   private static final int cz = 32;
   private static final int cA = 64;
   public static final int ch = 0;
   public static final int ci = 1;
   public static final int cj = 2;
   private int cB;
   private int cC;
   private int cD;
   public int ck;
   public int cl;
   protected boolean cm;
   public InventorySubcontainer cn;
   protected int co;
   protected float cp;
   protected boolean cq;
   private float cE;
   private float cF;
   private float cG;
   private float cH;
   private float cI;
   private float cJ;
   protected boolean cr = true;
   protected int cs;
   @Nullable
   private UUID cK;
   public int maxDomestication = 100;

   protected EntityHorseAbstract(EntityTypes<? extends EntityHorseAbstract> entitytypes, World world) {
      super(entitytypes, world);
      this.v(1.0F);
      this.go();
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalPanic(this, 1.2));
      this.bN.a(1, new PathfinderGoalTame(this, 1.2));
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0, EntityHorseAbstract.class));
      this.bN.a(4, new PathfinderGoalFollowParent(this, 1.0));
      this.bN.a(6, new PathfinderGoalRandomStrollLand(this, 0.7));
      this.bN.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      if (this.fY()) {
         this.bN.a(9, new RandomStandGoal(this));
      }

      this.gi();
   }

   protected void gi() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.25, RecipeItemStack.a(Items.tm, Items.pi, Items.pj), false));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cu, (byte)0);
   }

   protected boolean s(int i) {
      return (this.am.a(cu) & i) != 0;
   }

   protected void d(int i, boolean flag) {
      byte b0 = this.am.a(cu);
      if (flag) {
         this.am.b(cu, (byte)(b0 | i));
      } else {
         this.am.b(cu, (byte)(b0 & ~i));
      }
   }

   public boolean gh() {
      return this.s(2);
   }

   @Nullable
   @Override
   public UUID T_() {
      return this.cK;
   }

   public void b(@Nullable UUID uuid) {
      this.cK = uuid;
   }

   public boolean gj() {
      return this.cm;
   }

   public void x(boolean flag) {
      this.d(2, flag);
   }

   public void y(boolean flag) {
      this.cm = flag;
   }

   @Override
   protected void B(float f) {
      if (f > 6.0F && this.gk()) {
         this.A(false);
      }
   }

   public boolean gk() {
      return this.s(16);
   }

   public boolean gl() {
      return this.s(32);
   }

   public boolean gm() {
      return this.s(8);
   }

   public void z(boolean flag) {
      this.d(8, flag);
   }

   @Override
   public boolean g() {
      return this.bq() && !this.y_() && this.gh();
   }

   @Override
   public void a(@Nullable SoundCategory soundcategory) {
      this.cn.a(0, new ItemStack(Items.mV));
      if (soundcategory != null) {
         this.H.a(null, this, this.Q_(), soundcategory, 0.5F, 1.0F);
      }
   }

   public void b(EntityHuman entityhuman, ItemStack itemstack) {
      if (this.l(itemstack)) {
         this.cn.a(1, new ItemStack(itemstack.c()));
         if (!entityhuman.fK().d) {
            itemstack.h(1);
         }
      }
   }

   @Override
   public boolean i() {
      return this.s(4);
   }

   public int gn() {
      return this.co;
   }

   public void t(int i) {
      this.co = i;
   }

   public int u(int i) {
      int j = MathHelper.a(this.gn() + i, 0, this.gt());
      this.t(j);
      return j;
   }

   @Override
   public boolean bn() {
      return !this.bM();
   }

   private void q() {
      this.fS();
      if (!this.aO()) {
         SoundEffect soundeffect = this.fZ();
         if (soundeffect != null) {
            this.H.a(null, this.dl(), this.dn(), this.dr(), soundeffect, this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
         }
      }
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      if (f > 1.0F) {
         this.a(SoundEffects.le, 0.4F, 1.0F);
      }

      int i = this.d(f, f1);
      if (i <= 0) {
         return false;
      } else {
         this.a(damagesource, (float)i);
         if (this.bM()) {
            for(Entity entity : this.cQ()) {
               entity.a(damagesource, (float)i);
            }
         }

         this.eA();
         return true;
      }
   }

   @Override
   protected int d(float f, float f1) {
      return MathHelper.f((f * 0.5F - 3.0F) * f1);
   }

   protected int U_() {
      return 2;
   }

   public void go() {
      InventorySubcontainer inventorysubcontainer = this.cn;
      this.cn = new InventorySubcontainer(this.U_(), (AbstractHorse)this.getBukkitEntity());
      if (inventorysubcontainer != null) {
         inventorysubcontainer.b(this);
         int i = Math.min(inventorysubcontainer.b(), this.cn.b());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = inventorysubcontainer.a(j);
            if (!itemstack.b()) {
               this.cn.a(j, itemstack.o());
            }
         }
      }

      this.cn.a(this);
      this.gp();
   }

   protected void gp() {
      if (!this.H.B) {
         this.d(4, !this.cn.a(0).b());
      }
   }

   @Override
   public void a(IInventory iinventory) {
      boolean flag = this.i();
      this.gp();
      if (this.ag > 20 && !flag && this.i()) {
         this.a(SoundEffects.lf, 0.5F, 1.0F);
      }
   }

   public double gq() {
      return this.b(GenericAttributes.m);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      boolean flag = super.a(damagesource, f);
      if (flag && this.af.a(3) == 0) {
         this.gx();
      }

      return flag;
   }

   protected boolean fY() {
      return true;
   }

   @Nullable
   protected SoundEffect fZ() {
      return null;
   }

   @Nullable
   protected SoundEffect gr() {
      return null;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      if (!iblockdata.d().a()) {
         IBlockData iblockdata1 = this.H.a_(blockposition.c());
         SoundEffectType soundeffecttype = iblockdata.t();
         if (iblockdata1.a(Blocks.dM)) {
            soundeffecttype = iblockdata1.t();
         }

         if (this.bM() && this.cr) {
            ++this.cs;
            if (this.cs > 5 && this.cs % 3 == 0) {
               this.a(soundeffecttype);
            } else if (this.cs <= 5) {
               this.a(SoundEffects.lh, soundeffecttype.a() * 0.15F, soundeffecttype.b());
            }
         } else if (soundeffecttype == SoundEffectType.a) {
            this.a(SoundEffects.lh, soundeffecttype.a() * 0.15F, soundeffecttype.b());
         } else {
            this.a(SoundEffects.lg, soundeffecttype.a() * 0.15F, soundeffecttype.b());
         }
      }
   }

   protected void a(SoundEffectType soundeffecttype) {
      this.a(SoundEffects.lb, soundeffecttype.a() * 0.15F, soundeffecttype.b());
   }

   public static AttributeProvider.Builder gs() {
      return EntityInsentient.y().a(GenericAttributes.m).a(GenericAttributes.a, 53.0).a(GenericAttributes.d, 0.225F);
   }

   @Override
   public int fy() {
      return 6;
   }

   public int gt() {
      return this.maxDomestication;
   }

   @Override
   protected float eN() {
      return 0.8F;
   }

   @Override
   public int K() {
      return 400;
   }

   @Override
   public void b(EntityHuman entityhuman) {
      if (!this.H.B && (!this.bM() || this.u(entityhuman)) && this.gh()) {
         entityhuman.a(this, this.cn);
      }
   }

   public EnumInteractionResult c(EntityHuman entityhuman, ItemStack itemstack) {
      boolean flag = this.a(entityhuman, itemstack);
      if (!entityhuman.fK().d) {
         itemstack.h(1);
      }

      return this.H.B ? EnumInteractionResult.b : (flag ? EnumInteractionResult.a : EnumInteractionResult.d);
   }

   protected boolean a(EntityHuman entityhuman, ItemStack itemstack) {
      boolean flag = false;
      float f = 0.0F;
      short short0 = 0;
      byte b0 = 0;
      if (itemstack.a(Items.oE)) {
         f = 2.0F;
         short0 = 20;
         b0 = 3;
      } else if (itemstack.a(Items.qI)) {
         f = 1.0F;
         short0 = 30;
         b0 = 3;
      } else if (itemstack.a(Blocks.ii.k())) {
         f = 20.0F;
         short0 = 180;
      } else if (itemstack.a(Items.nB)) {
         f = 3.0F;
         short0 = 60;
         b0 = 3;
      } else if (itemstack.a(Items.tm)) {
         f = 4.0F;
         short0 = 60;
         b0 = 5;
         if (!this.H.B && this.gh() && this.h() == 0 && !this.fW()) {
            flag = true;
            this.f(entityhuman);
         }
      } else if (itemstack.a(Items.pi) || itemstack.a(Items.pj)) {
         f = 10.0F;
         short0 = 240;
         b0 = 10;
         if (!this.H.B && this.gh() && this.h() == 0 && !this.fW()) {
            flag = true;
            this.f(entityhuman);
         }
      }

      if (this.eo() < this.eE() && f > 0.0F) {
         this.heal(f, RegainReason.EATING);
         flag = true;
      }

      if (this.y_() && short0 > 0) {
         this.H.a(Particles.M, this.d(1.0), this.do() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
         if (!this.H.B) {
            this.b_(short0);
         }

         flag = true;
      }

      if (b0 > 0 && (flag || !this.gh()) && this.gn() < this.gt()) {
         flag = true;
         if (!this.H.B) {
            this.u(b0);
         }
      }

      if (flag) {
         this.q();
         this.a(GameEvent.n);
      }

      return flag;
   }

   protected void e(EntityHuman entityhuman) {
      this.A(false);
      this.B(false);
      if (!this.H.B) {
         entityhuman.f(this.dw());
         entityhuman.e(this.dy());
         entityhuman.k(this);
      }
   }

   @Override
   public boolean eP() {
      return super.eP() && this.bM() && this.i() || this.gk() || this.gl();
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return ct.a(itemstack);
   }

   private void r() {
      this.ck = 1;
   }

   @Override
   protected void er() {
      super.er();
      if (this.cn != null) {
         for(int i = 0; i < this.cn.b(); ++i) {
            ItemStack itemstack = this.cn.a(i);
            if (!itemstack.b() && !EnchantmentManager.e(itemstack)) {
               this.b(itemstack);
            }
         }
      }
   }

   @Override
   public void b_() {
      if (this.af.a(200) == 0) {
         this.r();
      }

      super.b_();
      if (!this.H.B && this.bq()) {
         if (this.af.a(900) == 0 && this.aL == 0) {
            this.heal(1.0F, RegainReason.REGEN);
         }

         if (this.gv()) {
            if (!this.gk() && !this.bM() && this.af.a(300) == 0 && this.H.a_(this.dg().d()).a(Blocks.i)) {
               this.A(true);
            }

            if (this.gk() && ++this.cB > 50) {
               this.cB = 0;
               this.A(false);
            }
         }

         this.gu();
      }
   }

   protected void gu() {
      if (this.gm() && this.y_() && !this.gk()) {
         EntityLiving entityliving = this.H.a(EntityHorseAbstract.class, cc, this, this.dl(), this.dn(), this.dr(), this.cD().g(16.0));
         if (entityliving != null && this.f((Entity)entityliving) > 4.0) {
            this.bM.a(entityliving, 0);
         }
      }
   }

   public boolean gv() {
      return true;
   }

   @Override
   public void l() {
      super.l();
      if (this.cC > 0 && ++this.cC > 30) {
         this.cC = 0;
         this.d(64, false);
      }

      if (this.cU() && this.cD > 0 && ++this.cD > 20) {
         this.cD = 0;
         this.B(false);
      }

      if (this.ck > 0 && ++this.ck > 8) {
         this.ck = 0;
      }

      if (this.cl > 0) {
         ++this.cl;
         if (this.cl > 300) {
            this.cl = 0;
         }
      }

      this.cF = this.cE;
      if (this.gk()) {
         this.cE += (1.0F - this.cE) * 0.4F + 0.05F;
         if (this.cE > 1.0F) {
            this.cE = 1.0F;
         }
      } else {
         this.cE += (0.0F - this.cE) * 0.4F - 0.05F;
         if (this.cE < 0.0F) {
            this.cE = 0.0F;
         }
      }

      this.cH = this.cG;
      if (this.gl()) {
         this.cE = 0.0F;
         this.cF = this.cE;
         this.cG += (1.0F - this.cG) * 0.4F + 0.05F;
         if (this.cG > 1.0F) {
            this.cG = 1.0F;
         }
      } else {
         this.cq = false;
         this.cG += (0.8F * this.cG * this.cG * this.cG - this.cG) * 0.6F - 0.05F;
         if (this.cG < 0.0F) {
            this.cG = 0.0F;
         }
      }

      this.cJ = this.cI;
      if (this.s(64)) {
         this.cI += (1.0F - this.cI) * 0.7F + 0.05F;
         if (this.cI > 1.0F) {
            this.cI = 1.0F;
         }
      } else {
         this.cI += (0.0F - this.cI) * 0.7F - 0.05F;
         if (this.cI < 0.0F) {
            this.cI = 0.0F;
         }
      }
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      if (this.bM() || this.y_()) {
         return super.b(entityhuman, enumhand);
      } else if (this.gh() && entityhuman.fz()) {
         this.b(entityhuman);
         return EnumInteractionResult.a(this.H.B);
      } else {
         ItemStack itemstack = entityhuman.b(enumhand);
         if (!itemstack.b()) {
            EnumInteractionResult enuminteractionresult = itemstack.a(entityhuman, this, enumhand);
            if (enuminteractionresult.a()) {
               return enuminteractionresult;
            }

            if (this.gB() && this.l(itemstack) && !this.gC()) {
               this.b(entityhuman, itemstack);
               return EnumInteractionResult.a(this.H.B);
            }
         }

         this.e(entityhuman);
         return EnumInteractionResult.a(this.H.B);
      }
   }

   private void fS() {
      if (!this.H.B) {
         this.cC = 1;
         this.d(64, true);
      }
   }

   public void A(boolean flag) {
      this.d(16, flag);
   }

   public void B(boolean flag) {
      if (flag) {
         this.A(false);
      }

      this.d(32, flag);
   }

   @Nullable
   public SoundEffect gw() {
      return this.s();
   }

   public void gx() {
      if (this.fY() && this.cU()) {
         this.cD = 1;
         this.B(true);
      }
   }

   public void gy() {
      if (!this.gl()) {
         this.gx();
         SoundEffect soundeffect = this.gr();
         if (soundeffect != null) {
            this.a(soundeffect, this.eN(), this.eO());
         }
      }
   }

   public boolean g(EntityHuman entityhuman) {
      this.b(entityhuman.cs());
      this.x(true);
      if (entityhuman instanceof EntityPlayer) {
         CriterionTriggers.x.a((EntityPlayer)entityhuman, this);
      }

      this.H.a(this, (byte)7);
      return true;
   }

   @Override
   protected void a(EntityLiving entityliving, Vec3D vec3d) {
      super.a(entityliving, vec3d);
      Vec2F vec2f = this.m(entityliving);
      this.a(vec2f.j, vec2f.i);
      this.L = this.aT = this.aV = this.dw();
      if (this.cT()) {
         if (vec3d.e <= 0.0) {
            this.cs = 0;
         }

         if (this.N) {
            this.y(false);
            if (this.cp > 0.0F && !this.gj()) {
               this.b(this.cp, vec3d);
            }

            this.cp = 0.0F;
         }
      }
   }

   protected Vec2F m(EntityLiving entityliving) {
      return new Vec2F(entityliving.dy() * 0.5F, entityliving.dw());
   }

   @Override
   protected Vec3D b(EntityLiving entityliving, Vec3D vec3d) {
      if (this.N && this.cp == 0.0F && this.gl() && !this.cq) {
         return Vec3D.b;
      } else {
         float f = entityliving.bj * 0.5F;
         float f1 = entityliving.bl;
         if (f1 <= 0.0F) {
            f1 *= 0.25F;
         }

         return new Vec3D((double)f, 0.0, (double)f1);
      }
   }

   @Override
   protected float g(EntityLiving entityliving) {
      return (float)this.b(GenericAttributes.d);
   }

   protected void b(float f, Vec3D vec3d) {
      double d0 = this.gq() * (double)f * (double)this.aE();
      double d1 = d0 + this.eR();
      Vec3D vec3d1 = this.dj();
      this.o(vec3d1.c, d1, vec3d1.e);
      this.y(true);
      this.at = true;
      if (vec3d.e > 0.0) {
         float f1 = MathHelper.a(this.dw() * (float) (Math.PI / 180.0));
         float f2 = MathHelper.b(this.dw() * (float) (Math.PI / 180.0));
         this.f(this.dj().b((double)(-0.4F * f1 * f), 0.0, (double)(0.4F * f2 * f)));
      }
   }

   protected void gz() {
      this.a(SoundEffects.ld, 0.4F, 1.0F);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("EatingHaystack", this.gk());
      nbttagcompound.a("Bred", this.gm());
      nbttagcompound.a("Temper", this.gn());
      nbttagcompound.a("Tame", this.gh());
      if (this.T_() != null) {
         nbttagcompound.a("Owner", this.T_());
      }

      nbttagcompound.a("Bukkit.MaxDomestication", this.maxDomestication);
      if (!this.cn.a(0).b()) {
         nbttagcompound.a("SaddleItem", this.cn.a(0).b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.A(nbttagcompound.q("EatingHaystack"));
      this.z(nbttagcompound.q("Bred"));
      this.t(nbttagcompound.h("Temper"));
      this.x(nbttagcompound.q("Tame"));
      UUID uuid;
      if (nbttagcompound.b("Owner")) {
         uuid = nbttagcompound.a("Owner");
      } else {
         String s = nbttagcompound.l("Owner");
         uuid = NameReferencingFileConverter.a(this.cH(), s);
      }

      if (uuid != null) {
         this.b(uuid);
      }

      if (nbttagcompound.e("Bukkit.MaxDomestication")) {
         this.maxDomestication = nbttagcompound.h("Bukkit.MaxDomestication");
      }

      if (nbttagcompound.b("SaddleItem", 10)) {
         ItemStack itemstack = ItemStack.a(nbttagcompound.p("SaddleItem"));
         if (itemstack.a(Items.mV)) {
            this.cn.a(0, itemstack);
         }
      }

      this.gp();
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      return false;
   }

   protected boolean gA() {
      return !this.bM() && !this.bL() && this.gh() && !this.y_() && this.eo() >= this.eE() && this.fW();
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      return null;
   }

   protected void a(EntityAgeable entityageable, EntityHorseAbstract entityhorseabstract) {
      this.a(entityageable, entityhorseabstract, GenericAttributes.a, (double)bX, (double)bY);
      this.a(entityageable, entityhorseabstract, GenericAttributes.m, (double)bV, (double)bW);
      this.a(entityageable, entityhorseabstract, GenericAttributes.d, (double)bS, (double)bT);
   }

   private void a(EntityAgeable entityageable, EntityHorseAbstract entityhorseabstract, AttributeBase attributebase, double d0, double d1) {
      double d2 = a(this.c(attributebase), entityageable.c(attributebase), d0, d1, this.af);
      entityhorseabstract.a(attributebase).a(d2);
   }

   static double a(double d0, double d1, double d2, double d3, RandomSource randomsource) {
      if (d3 <= d2) {
         throw new IllegalArgumentException("Incorrect range for an attribute");
      } else {
         d0 = MathHelper.a(d0, d2, d3);
         d1 = MathHelper.a(d1, d2, d3);
         double d4 = 0.15 * (d3 - d2);
         double d5 = Math.abs(d0 - d1) + d4 * 2.0;
         double d6 = (d0 + d1) / 2.0;
         double d7 = (randomsource.j() + randomsource.j() + randomsource.j()) / 3.0 - 0.5;
         double d8 = d6 + d5 * d7;
         if (d8 > d3) {
            double d9 = d8 - d3;
            return d3 - d9;
         } else if (d8 < d2) {
            double d9 = d2 - d8;
            return d2 + d9;
         } else {
            return d8;
         }
      }
   }

   public float C(float f) {
      return MathHelper.i(f, this.cF, this.cE);
   }

   public float D(float f) {
      return MathHelper.i(f, this.cH, this.cG);
   }

   public float E(float f) {
      return MathHelper.i(f, this.cJ, this.cI);
   }

   @Override
   public void b(int i) {
      if (this.i()) {
         if (i < 0) {
            i = 0;
         } else {
            this.cq = true;
            this.gx();
         }

         if (i >= 90) {
            this.cp = 1.0F;
         } else {
            this.cp = 0.4F + 0.4F * (float)i / 90.0F;
         }
      }
   }

   @Override
   public boolean a() {
      return this.i();
   }

   @Override
   public void c(int i) {
      float power;
      if (i >= 90) {
         power = 1.0F;
      } else {
         power = 0.4F + 0.4F * (float)i / 90.0F;
      }

      HorseJumpEvent event = CraftEventFactory.callHorseJumpEvent(this, power);
      if (!event.isCancelled()) {
         this.cq = true;
         this.gx();
         this.gz();
      }
   }

   @Override
   public void b() {
   }

   protected void C(boolean flag) {
      ParticleType particletype = flag ? Particles.O : Particles.ab;

      for(int i = 0; i < 7; ++i) {
         double d0 = this.af.k() * 0.02;
         double d1 = this.af.k() * 0.02;
         double d2 = this.af.k() * 0.02;
         this.H.a(particletype, this.d(1.0), this.do() + 0.5, this.g(1.0), d0, d1, d2);
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 7) {
         this.C(true);
      } else if (b0 == 6) {
         this.C(false);
      } else {
         super.b(b0);
      }
   }

   @Override
   public void i(Entity entity) {
      super.i(entity);
      if (entity instanceof EntityInsentient entityinsentient) {
         this.aT = entityinsentient.aT;
      }

      if (this.cH > 0.0F) {
         float f = MathHelper.a(this.aT * (float) (Math.PI / 180.0));
         float f1 = MathHelper.b(this.aT * (float) (Math.PI / 180.0));
         float f2 = 0.7F * this.cH;
         float f3 = 0.15F * this.cH;
         entity.e(this.dl() + (double)(f2 * f), this.dn() + this.bv() + entity.bu() + (double)f3, this.dr() - (double)(f2 * f1));
         if (entity instanceof EntityLiving) {
            ((EntityLiving)entity).aT = this.aT;
         }
      }
   }

   protected static float a(IntUnaryOperator intunaryoperator) {
      return 15.0F + (float)intunaryoperator.applyAsInt(8) + (float)intunaryoperator.applyAsInt(9);
   }

   protected static double a(DoubleSupplier doublesupplier) {
      return 0.4F + doublesupplier.getAsDouble() * 0.2 + doublesupplier.getAsDouble() * 0.2 + doublesupplier.getAsDouble() * 0.2;
   }

   protected static double b(DoubleSupplier doublesupplier) {
      return (0.45F + doublesupplier.getAsDouble() * 0.3 + doublesupplier.getAsDouble() * 0.3 + doublesupplier.getAsDouble() * 0.3) * 0.25;
   }

   @Override
   public boolean z_() {
      return false;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return entitysize.b * 0.95F;
   }

   public boolean gB() {
      return false;
   }

   public boolean gC() {
      return !this.c(EnumItemSlot.e).b();
   }

   public boolean l(ItemStack itemstack) {
      return false;
   }

   private SlotAccess a(final int i, final Predicate<ItemStack> predicate) {
      return new SlotAccess() {
         @Override
         public ItemStack a() {
            return EntityHorseAbstract.this.cn.a(i);
         }

         @Override
         public boolean a(ItemStack itemstack) {
            if (!predicate.test(itemstack)) {
               return false;
            } else {
               EntityHorseAbstract.this.cn.a(i, itemstack);
               EntityHorseAbstract.this.gp();
               return true;
            }
         }
      };
   }

   @Override
   public SlotAccess a_(int i) {
      int j = i - 400;
      if (j >= 0 && j < 2 && j < this.cn.b()) {
         if (j == 0) {
            return this.a(j, itemstack -> itemstack.b() || itemstack.a(Items.mV));
         }

         if (j == 1) {
            if (!this.gB()) {
               return SlotAccess.b;
            }

            return this.a(j, itemstack -> itemstack.b() || this.l(itemstack));
         }
      }

      int k = i - 500 + 2;
      return k >= 2 && k < this.cn.b() ? SlotAccess.a(this.cn, k) : super.a_(i);
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      if (this.i()) {
         Entity entity = this.cN();
         if (entity instanceof EntityLiving) {
            return (EntityLiving)entity;
         }
      }

      return null;
   }

   @Nullable
   private Vec3D a(Vec3D vec3d, EntityLiving entityliving) {
      double d0 = this.dl() + vec3d.c;
      double d1 = this.cD().b;
      double d2 = this.dr() + vec3d.e;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
      UnmodifiableIterator unmodifiableiterator = entityliving.fr().iterator();

      while(unmodifiableiterator.hasNext()) {
         EntityPose entitypose = (EntityPose)unmodifiableiterator.next();
         blockposition_mutableblockposition.b(d0, d1, d2);
         double d3 = this.cD().e + 0.75;

         do {
            double d4 = this.H.i(blockposition_mutableblockposition);
            if ((double)blockposition_mutableblockposition.v() + d4 > d3) {
               break;
            }

            if (DismountUtil.a(d4)) {
               AxisAlignedBB axisalignedbb = entityliving.g(entitypose);
               Vec3D vec3d1 = new Vec3D(d0, (double)blockposition_mutableblockposition.v() + d4, d2);
               if (DismountUtil.a(this.H, entityliving, axisalignedbb.c(vec3d1))) {
                  entityliving.b(entitypose);
                  return vec3d1;
               }
            }

            blockposition_mutableblockposition.c(EnumDirection.b);
         } while((double)blockposition_mutableblockposition.v() >= d3);
      }

      return null;
   }

   @Override
   public Vec3D b(EntityLiving entityliving) {
      Vec3D vec3d = a((double)this.dc(), (double)entityliving.dc(), this.dw() + (entityliving.fd() == EnumMainHand.b ? 90.0F : -90.0F));
      Vec3D vec3d1 = this.a(vec3d, entityliving);
      if (vec3d1 != null) {
         return vec3d1;
      } else {
         Vec3D vec3d2 = a((double)this.dc(), (double)entityliving.dc(), this.dw() + (entityliving.fd() == EnumMainHand.a ? 90.0F : -90.0F));
         Vec3D vec3d3 = this.a(vec3d2, entityliving);
         return vec3d3 != null ? vec3d3 : this.de();
      }
   }

   protected void a(RandomSource randomsource) {
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
      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(0.2F);
      }

      this.a(worldaccess.r_());
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   public boolean b(IInventory iinventory) {
      return this.cn != iinventory;
   }

   public int gD() {
      return this.K();
   }
}
