package net.minecraft.world.entity.animal.horse;

import com.mojang.serialization.Codec;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.IInventory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLlamaFollow;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTame;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCarpet;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class EntityLlama extends EntityHorseChestedAbstract implements VariantHolder<EntityLlama.Variant>, IRangedEntity {
   private static final int bT = 5;
   private static final RecipeItemStack bV = RecipeItemStack.a(Items.oE, Blocks.ii.k());
   private static final DataWatcherObject<Integer> bW = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bX = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bY = DataWatcher.a(EntityLlama.class, DataWatcherRegistry.b);
   boolean bZ;
   @Nullable
   private EntityLlama ca;
   @Nullable
   private EntityLlama cb;

   public EntityLlama(EntityTypes<? extends EntityLlama> entitytypes, World world) {
      super(entitytypes, world);
   }

   public boolean gb() {
      return false;
   }

   public void setStrengthPublic(int i) {
      this.v(i);
   }

   private void v(int i) {
      this.am.b(bW, Math.max(1, Math.min(5, i)));
   }

   private void b(RandomSource randomsource) {
      int i = randomsource.i() < 0.04F ? 5 : 3;
      this.v(1 + randomsource.a(i));
   }

   public int gc() {
      return this.am.a(bW);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Variant", this.ge().g);
      nbttagcompound.a("Strength", this.gc());
      if (!this.cn.a(1).b()) {
         nbttagcompound.a("DecorItem", this.cn.a(1).b(new NBTTagCompound()));
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.v(nbttagcompound.h("Strength"));
      super.a(nbttagcompound);
      this.a(EntityLlama.Variant.a(nbttagcompound.h("Variant")));
      if (nbttagcompound.b("DecorItem", 10)) {
         this.cn.a(1, ItemStack.a(nbttagcompound.p("DecorItem")));
      }

      this.gp();
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalTame(this, 1.2));
      this.bN.a(2, new PathfinderGoalLlamaFollow(this, 2.1F));
      this.bN.a(3, new PathfinderGoalArrowAttack(this, 1.25, 40, 20.0F));
      this.bN.a(3, new PathfinderGoalPanic(this, 1.2));
      this.bN.a(4, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(5, new PathfinderGoalTempt(this, 1.25, RecipeItemStack.a(Items.hf), false));
      this.bN.a(6, new PathfinderGoalFollowParent(this, 1.0));
      this.bN.a(7, new PathfinderGoalRandomStrollLand(this, 0.7));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(9, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new EntityLlama.c(this));
      this.bO.a(2, new EntityLlama.a(this));
   }

   public static AttributeProvider.Builder gd() {
      return q().a(GenericAttributes.b, 40.0);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bW, 0);
      this.am.a(bX, -1);
      this.am.a(bY, 0);
   }

   public EntityLlama.Variant ge() {
      return EntityLlama.Variant.a(this.am.a(bY));
   }

   public void a(EntityLlama.Variant entityllama_variant) {
      this.am.b(bY, entityllama_variant.g);
   }

   @Override
   protected int U_() {
      return this.r() ? 2 + 3 * this.ga() : super.U_();
   }

   @Override
   public void i(Entity entity) {
      if (this.u(entity)) {
         float f = MathHelper.b(this.aT * (float) (Math.PI / 180.0));
         float f1 = MathHelper.a(this.aT * (float) (Math.PI / 180.0));
         float f2 = 0.3F;
         entity.e(this.dl() + (double)(0.3F * f1), this.dn() + this.bv() + entity.bu(), this.dr() - (double)(0.3F * f));
      }
   }

   @Override
   public double bv() {
      return (double)this.dd() * 0.6;
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      return null;
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return bV.a(itemstack);
   }

   @Override
   protected boolean a(EntityHuman entityhuman, ItemStack itemstack) {
      byte b0 = 0;
      byte b1 = 0;
      float f = 0.0F;
      boolean flag = false;
      if (itemstack.a(Items.oE)) {
         b0 = 10;
         b1 = 3;
         f = 2.0F;
      } else if (itemstack.a(Blocks.ii.k())) {
         b0 = 90;
         b1 = 6;
         f = 10.0F;
         if (this.gh() && this.h() == 0 && this.fT()) {
            flag = true;
            this.f(entityhuman);
         }
      }

      if (this.eo() < this.eE() && f > 0.0F) {
         this.b(f);
         flag = true;
      }

      if (this.y_() && b0 > 0) {
         this.H.a(Particles.M, this.d(1.0), this.do() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
         if (!this.H.B) {
            this.b_(b0);
         }

         flag = true;
      }

      if (b1 > 0 && (flag || !this.gh()) && this.gn() < this.gt()) {
         flag = true;
         if (!this.H.B) {
            this.u(b1);
         }
      }

      if (flag && !this.aO()) {
         SoundEffect soundeffect = this.fZ();
         if (soundeffect != null) {
            this.H.a(null, this.dl(), this.dn(), this.dr(), this.fZ(), this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
         }
      }

      return flag;
   }

   @Override
   public boolean eP() {
      return this.ep() || this.gk();
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
      RandomSource randomsource = worldaccess.r_();
      this.b(randomsource);
      EntityLlama.Variant entityllama_variant;
      if (groupdataentity instanceof EntityLlama.b) {
         entityllama_variant = ((EntityLlama.b)groupdataentity).a;
      } else {
         entityllama_variant = SystemUtils.a(EntityLlama.Variant.values(), randomsource);
         groupdataentity = new EntityLlama.b(entityllama_variant);
      }

      this.a(entityllama_variant);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   protected boolean fY() {
      return false;
   }

   @Override
   protected SoundEffect gr() {
      return SoundEffects.mo;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.mn;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.ms;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.mq;
   }

   @Nullable
   @Override
   protected SoundEffect fZ() {
      return SoundEffects.mr;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.mu, 0.15F, 1.0F);
   }

   @Override
   protected void fS() {
      this.a(SoundEffects.mp, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
   }

   @Override
   public int ga() {
      return this.gc();
   }

   @Override
   public boolean gB() {
      return true;
   }

   @Override
   public boolean gC() {
      return !this.cn.a(1).b();
   }

   @Override
   public boolean l(ItemStack itemstack) {
      return itemstack.a(TagsItem.f);
   }

   @Override
   public boolean g() {
      return false;
   }

   @Override
   public void a(IInventory iinventory) {
      EnumColor enumcolor = this.gf();
      super.a(iinventory);
      EnumColor enumcolor1 = this.gf();
      if (this.ag > 20 && enumcolor1 != null && enumcolor1 != enumcolor) {
         this.a(SoundEffects.mv, 0.5F, 1.0F);
      }
   }

   @Override
   protected void gp() {
      if (!this.H.B) {
         super.gp();
         this.a(n(this.cn.a(1)));
      }
   }

   private void a(@Nullable EnumColor enumcolor) {
      this.am.b(bX, enumcolor == null ? -1 : enumcolor.a());
   }

   @Nullable
   private static EnumColor n(ItemStack itemstack) {
      Block block = Block.a(itemstack.c());
      return block instanceof BlockCarpet ? ((BlockCarpet)block).b() : null;
   }

   @Nullable
   public EnumColor gf() {
      int i = this.am.a(bX);
      return i == -1 ? null : EnumColor.a(i);
   }

   @Override
   public int gt() {
      return 30;
   }

   @Override
   public boolean a(EntityAnimal entityanimal) {
      return entityanimal != this && entityanimal instanceof EntityLlama && this.gA() && ((EntityLlama)entityanimal).gA();
   }

   @Nullable
   public EntityLlama b(WorldServer worldserver, EntityAgeable entityageable) {
      EntityLlama entityllama = this.gg();
      if (entityllama != null) {
         this.a(entityageable, entityllama);
         EntityLlama entityllama1 = (EntityLlama)entityageable;
         int i = this.af.a(Math.max(this.gc(), entityllama1.gc())) + 1;
         if (this.af.i() < 0.03F) {
            ++i;
         }

         entityllama.v(i);
         entityllama.a(this.af.h() ? this.ge() : entityllama1.ge());
      }

      return entityllama;
   }

   @Nullable
   protected EntityLlama gg() {
      return EntityTypes.aj.a(this.H);
   }

   private void n(EntityLiving entityliving) {
      EntityLlamaSpit entityllamaspit = new EntityLlamaSpit(this.H, this);
      double d0 = entityliving.dl() - this.dl();
      double d1 = entityliving.e(0.3333333333333333) - entityllamaspit.dn();
      double d2 = entityliving.dr() - this.dr();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2) * 0.2F;
      entityllamaspit.c(d0, d1 + d3, d2, 1.5F, 10.0F);
      if (!this.aO()) {
         this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.mt, this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
      }

      this.H.b(entityllamaspit);
      this.bZ = true;
   }

   void D(boolean flag) {
      this.bZ = flag;
   }

   @Override
   public boolean a(float f, float f1, DamageSource damagesource) {
      int i = this.d(f, f1);
      if (i <= 0) {
         return false;
      } else {
         if (f >= 6.0F) {
            this.a(damagesource, (float)i);
            if (this.bM()) {
               for(Entity entity : this.cQ()) {
                  entity.a(damagesource, (float)i);
               }
            }
         }

         this.eA();
         return true;
      }
   }

   public void gE() {
      if (this.ca != null) {
         this.ca.cb = null;
      }

      this.ca = null;
   }

   public void a(EntityLlama entityllama) {
      this.ca = entityllama;
      this.ca.cb = this;
   }

   public boolean gF() {
      return this.cb != null;
   }

   public boolean gG() {
      return this.ca != null;
   }

   @Nullable
   public EntityLlama gH() {
      return this.ca;
   }

   @Override
   protected double fR() {
      return 2.0;
   }

   @Override
   protected void gu() {
      if (!this.gG() && this.y_()) {
         super.gu();
      }
   }

   @Override
   public boolean gv() {
      return false;
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      this.n(entityliving);
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, 0.75 * (double)this.cE(), (double)this.dc() * 0.5);
   }

   public static enum Variant implements INamable {
      a(0, "creamy"),
      b(1, "white"),
      c(2, "brown"),
      d(3, "gray");

      public static final Codec<EntityLlama.Variant> e = INamable.a(EntityLlama.Variant::values);
      private static final IntFunction<EntityLlama.Variant> f = ByIdMap.a(EntityLlama.Variant::a, values(), ByIdMap.a.c);
      final int g;
      private final String h;

      private Variant(int i, String s) {
         this.g = i;
         this.h = s;
      }

      public int a() {
         return this.g;
      }

      public static EntityLlama.Variant a(int i) {
         return f.apply(i);
      }

      @Override
      public String c() {
         return this.h;
      }
   }

   private static class a extends PathfinderGoalNearestAttackableTarget<EntityWolf> {
      public a(EntityLlama entityllama) {
         super(entityllama, EntityWolf.class, 16, false, true, entityliving -> !((EntityWolf)entityliving).q());
      }

      @Override
      protected double l() {
         return super.l() * 0.25;
      }
   }

   private static class b extends EntityAgeable.a {
      public final EntityLlama.Variant a;

      b(EntityLlama.Variant entityllama_variant) {
         super(true);
         this.a = entityllama_variant;
      }
   }

   private static class c extends PathfinderGoalHurtByTarget {
      public c(EntityLlama entityllama) {
         super(entityllama);
      }

      @Override
      public boolean b() {
         if (this.e instanceof EntityLlama entityllama && entityllama.bZ) {
            entityllama.D(false);
            return false;
         }

         return super.b();
      }
   }
}
