package net.minecraft.world.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityPanda extends EntityAnimal {
   private static final DataWatcherObject<Integer> bV = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bW = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Integer> bX = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Byte> bY = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Byte> bZ = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Byte> ca = DataWatcher.a(EntityPanda.class, DataWatcherRegistry.a);
   static final PathfinderTargetCondition cb = PathfinderTargetCondition.b().a(8.0);
   private static final int cc = 2;
   private static final int cd = 4;
   private static final int ce = 8;
   private static final int cf = 16;
   private static final int cg = 5;
   public static final int bS = 32;
   private static final int ch = 32;
   boolean ci;
   boolean cj;
   public int bT;
   private Vec3D ck;
   private float cl;
   private float cm;
   private float cn;
   private float co;
   private float cp;
   private float cq;
   EntityPanda.g cr;
   static final Predicate<EntityItem> cs = entityitem -> {
      ItemStack itemstack = entityitem.i();
      return (itemstack.a(Blocks.mV.k()) || itemstack.a(Blocks.eg.k())) && entityitem.bq() && !entityitem.q();
   };

   public EntityPanda(EntityTypes<? extends EntityPanda> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new EntityPanda.h(this);
      if (!this.y_()) {
         this.s(true);
      }
   }

   @Override
   public boolean f(ItemStack itemstack) {
      EnumItemSlot enumitemslot = EntityInsentient.h(itemstack);
      return !this.c(enumitemslot).b() ? false : enumitemslot == EnumItemSlot.a && super.f(itemstack);
   }

   public int q() {
      return this.am.a(bV);
   }

   public void s(int i) {
      this.am.b(bV, i);
   }

   public boolean r() {
      return this.v(2);
   }

   public boolean w() {
      return this.v(8);
   }

   public void w(boolean flag) {
      this.d(8, flag);
   }

   public boolean fS() {
      return this.v(16);
   }

   public void x(boolean flag) {
      this.d(16, flag);
   }

   public boolean fY() {
      return this.am.a(bX) > 0;
   }

   public void y(boolean flag) {
      this.am.b(bX, flag ? 1 : 0);
   }

   private int gn() {
      return this.am.a(bX);
   }

   private void u(int i) {
      this.am.b(bX, i);
   }

   public void z(boolean flag) {
      this.d(2, flag);
      if (!flag) {
         this.t(0);
      }
   }

   public int fZ() {
      return this.am.a(bW);
   }

   public void t(int i) {
      this.am.b(bW, i);
   }

   public EntityPanda.Gene ga() {
      return EntityPanda.Gene.a(this.am.a(bY));
   }

   public void a(EntityPanda.Gene entitypanda_gene) {
      if (entitypanda_gene.a() > 6) {
         entitypanda_gene = EntityPanda.Gene.a(this.af);
      }

      this.am.b(bY, (byte)entitypanda_gene.a());
   }

   public EntityPanda.Gene gb() {
      return EntityPanda.Gene.a(this.am.a(bZ));
   }

   public void b(EntityPanda.Gene entitypanda_gene) {
      if (entitypanda_gene.a() > 6) {
         entitypanda_gene = EntityPanda.Gene.a(this.af);
      }

      this.am.b(bZ, (byte)entitypanda_gene.a());
   }

   public boolean gc() {
      return this.v(4);
   }

   public void A(boolean flag) {
      this.d(4, flag);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bV, 0);
      this.am.a(bW, 0);
      this.am.a(bY, (byte)0);
      this.am.a(bZ, (byte)0);
      this.am.a(ca, (byte)0);
      this.am.a(bX, 0);
   }

   private boolean v(int i) {
      return (this.am.a(ca) & i) != 0;
   }

   private void d(int i, boolean flag) {
      byte b0 = this.am.a(ca);
      if (flag) {
         this.am.b(ca, (byte)(b0 | i));
      } else {
         this.am.b(ca, (byte)(b0 & ~i));
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("MainGene", this.ga().c());
      nbttagcompound.a("HiddenGene", this.gb().c());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(EntityPanda.Gene.a(nbttagcompound.l("MainGene")));
      this.b(EntityPanda.Gene.a(nbttagcompound.l("HiddenGene")));
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      EntityPanda entitypanda = EntityTypes.as.a((World)worldserver);
      if (entitypanda != null) {
         if (entityageable instanceof EntityPanda entitypanda1) {
            entitypanda.a(this, entitypanda1);
         }

         entitypanda.gl();
      }

      return entitypanda;
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(2, new EntityPanda.i(this, 2.0));
      this.bN.a(2, new EntityPanda.d(this, 1.0));
      this.bN.a(3, new EntityPanda.b(this, 1.2F, true));
      this.bN.a(4, new PathfinderGoalTempt(this, 1.0, RecipeItemStack.a(Blocks.mV.k()), false));
      this.bN.a(6, new EntityPanda.c<>(this, EntityHuman.class, 8.0F, 2.0, 2.0));
      this.bN.a(6, new EntityPanda.c<>(this, EntityMonster.class, 4.0F, 2.0, 2.0));
      this.bN.a(7, new EntityPanda.k());
      this.bN.a(8, new EntityPanda.f(this));
      this.bN.a(8, new EntityPanda.l(this));
      this.cr = new EntityPanda.g(this, EntityHuman.class, 6.0F);
      this.bN.a(9, this.cr);
      this.bN.a(10, new PathfinderGoalRandomLookaround(this));
      this.bN.a(12, new EntityPanda.j(this));
      this.bN.a(13, new PathfinderGoalFollowParent(this, 1.25));
      this.bN.a(14, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bO.a(1, new EntityPanda.e(this).a(new Class[0]));
   }

   public static AttributeProvider.Builder gd() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.15F).a(GenericAttributes.f, 6.0);
   }

   public EntityPanda.Gene ge() {
      return EntityPanda.Gene.a(this.ga(), this.gb());
   }

   public boolean gf() {
      return this.ge() == EntityPanda.Gene.b;
   }

   public boolean gg() {
      return this.ge() == EntityPanda.Gene.c;
   }

   public boolean gh() {
      return this.ge() == EntityPanda.Gene.d;
   }

   public boolean gi() {
      return this.ge() == EntityPanda.Gene.e;
   }

   public boolean gj() {
      return this.ge() == EntityPanda.Gene.f;
   }

   @Override
   public boolean fM() {
      return this.ge() == EntityPanda.Gene.g;
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return false;
   }

   @Override
   public boolean z(Entity entity) {
      this.a(SoundEffects.qE, 1.0F, 1.0F);
      if (!this.fM()) {
         this.cj = true;
      }

      return super.z(entity);
   }

   @Override
   public void l() {
      super.l();
      if (this.gg()) {
         if (this.H.X() && !this.aT()) {
            this.w(true);
            this.y(false);
         } else if (!this.fY()) {
            this.w(false);
         }
      }

      EntityLiving entityliving = this.P_();
      if (entityliving == null) {
         this.ci = false;
         this.cj = false;
      }

      if (this.q() > 0) {
         if (entityliving != null) {
            this.a(entityliving, 90.0F, 90.0F);
         }

         if (this.q() == 29 || this.q() == 14) {
            this.a(SoundEffects.qA, 1.0F, 1.0F);
         }

         this.s(this.q() - 1);
      }

      if (this.r()) {
         this.t(this.fZ() + 1);
         if (this.fZ() > 20) {
            this.z(false);
            this.gu();
         } else if (this.fZ() == 1) {
            this.a(SoundEffects.qu, 1.0F, 1.0F);
         }
      }

      if (this.gc()) {
         this.gt();
      } else {
         this.bT = 0;
      }

      if (this.w()) {
         this.e(0.0F);
      }

      this.gq();
      this.go();
      this.gr();
      this.gs();
   }

   public boolean gk() {
      return this.gg() && this.H.X();
   }

   private void go() {
      if (!this.fY() && this.w() && !this.gk() && !this.c(EnumItemSlot.a).b() && this.af.a(80) == 1) {
         this.y(true);
      } else if (this.c(EnumItemSlot.a).b() || !this.w()) {
         this.y(false);
      }

      if (this.fY()) {
         this.gp();
         if (!this.H.B && this.gn() > 80 && this.af.a(20) == 1) {
            if (this.gn() > 100 && this.l(this.c(EnumItemSlot.a))) {
               if (!this.H.B) {
                  this.a(EnumItemSlot.a, ItemStack.b);
                  this.a(GameEvent.n);
               }

               this.w(false);
            }

            this.y(false);
            return;
         }

         this.u(this.gn() + 1);
      }
   }

   private void gp() {
      if (this.gn() % 5 == 0) {
         this.a(SoundEffects.qy, 0.5F + 0.5F * (float)this.af.a(2), (this.af.i() - this.af.i()) * 0.2F + 1.0F);

         for(int i = 0; i < 6; ++i) {
            Vec3D vec3d = new Vec3D(((double)this.af.i() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, ((double)this.af.i() - 0.5) * 0.1);
            vec3d = vec3d.a(-this.dy() * (float) (Math.PI / 180.0));
            vec3d = vec3d.b(-this.dw() * (float) (Math.PI / 180.0));
            double d0 = (double)(-this.af.i()) * 0.6 - 0.3;
            Vec3D vec3d1 = new Vec3D(((double)this.af.i() - 0.5) * 0.8, d0, 1.0 + ((double)this.af.i() - 0.5) * 0.4);
            vec3d1 = vec3d1.b(-this.aT * (float) (Math.PI / 180.0));
            vec3d1 = vec3d1.b(this.dl(), this.dp() + 1.0, this.dr());
            this.H.a(new ParticleParamItem(Particles.Q, this.c(EnumItemSlot.a)), vec3d1.c, vec3d1.d, vec3d1.e, vec3d.c, vec3d.d + 0.05, vec3d.e);
         }
      }
   }

   private void gq() {
      this.cm = this.cl;
      if (this.w()) {
         this.cl = Math.min(1.0F, this.cl + 0.15F);
      } else {
         this.cl = Math.max(0.0F, this.cl - 0.19F);
      }
   }

   private void gr() {
      this.co = this.cn;
      if (this.fS()) {
         this.cn = Math.min(1.0F, this.cn + 0.15F);
      } else {
         this.cn = Math.max(0.0F, this.cn - 0.19F);
      }
   }

   private void gs() {
      this.cq = this.cp;
      if (this.gc()) {
         this.cp = Math.min(1.0F, this.cp + 0.15F);
      } else {
         this.cp = Math.max(0.0F, this.cp - 0.19F);
      }
   }

   public float C(float f) {
      return MathHelper.i(f, this.cm, this.cl);
   }

   public float D(float f) {
      return MathHelper.i(f, this.co, this.cn);
   }

   public float E(float f) {
      return MathHelper.i(f, this.cq, this.cp);
   }

   private void gt() {
      ++this.bT;
      if (this.bT > 32) {
         this.A(false);
      } else if (!this.H.B) {
         Vec3D vec3d = this.dj();
         if (this.bT == 1) {
            float f = this.dw() * (float) (Math.PI / 180.0);
            float f1 = this.y_() ? 0.1F : 0.2F;
            this.ck = new Vec3D(vec3d.c + (double)(-MathHelper.a(f) * f1), 0.0, vec3d.e + (double)(MathHelper.b(f) * f1));
            this.f(this.ck.b(0.0, 0.27, 0.0));
         } else if ((float)this.bT != 7.0F && (float)this.bT != 15.0F && (float)this.bT != 23.0F) {
            this.o(this.ck.c, vec3d.d, this.ck.e);
         } else {
            this.o(0.0, this.N ? 0.27 : vec3d.d, 0.0);
         }
      }
   }

   private void gu() {
      Vec3D vec3d = this.dj();
      this.H
         .a(
            Particles.ac,
            this.dl() - (double)(this.dc() + 1.0F) * 0.5 * (double)MathHelper.a(this.aT * (float) (Math.PI / 180.0)),
            this.dp() - 0.1F,
            this.dr() + (double)(this.dc() + 1.0F) * 0.5 * (double)MathHelper.b(this.aT * (float) (Math.PI / 180.0)),
            vec3d.c,
            0.0,
            vec3d.e
         );
      this.a(SoundEffects.qv, 1.0F, 1.0F);

      for(EntityPanda entitypanda : this.H.a(EntityPanda.class, this.cD().g(10.0))) {
         if (!entitypanda.y_() && entitypanda.N && !entitypanda.aT() && entitypanda.gm()) {
            entitypanda.eS();
         }
      }

      if (!this.H.k_() && this.af.a(700) == 0 && this.H.W().b(GameRules.f)) {
         this.a(Items.pY);
      }
   }

   @Override
   protected void b(EntityItem entityitem) {
      if (!CraftEventFactory.callEntityPickupItemEvent(this, entityitem, 0, !this.c(EnumItemSlot.a).b() || !cs.test(entityitem)).isCancelled()) {
         this.a(entityitem);
         ItemStack itemstack = entityitem.i();
         this.a(EnumItemSlot.a, itemstack);
         this.e(EnumItemSlot.a);
         this.a(entityitem, itemstack.K());
         entityitem.ai();
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (!this.H.B) {
         this.w(false);
      }

      return super.a(damagesource, f);
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
      this.a(EntityPanda.Gene.a(randomsource));
      this.b(EntityPanda.Gene.a(randomsource));
      this.gl();
      if (groupdataentity == null) {
         groupdataentity = new EntityAgeable.a(0.2F);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   public void a(EntityPanda entitypanda, @Nullable EntityPanda entitypanda1) {
      if (entitypanda1 == null) {
         if (this.af.h()) {
            this.a(entitypanda.gv());
            this.b(EntityPanda.Gene.a(this.af));
         } else {
            this.a(EntityPanda.Gene.a(this.af));
            this.b(entitypanda.gv());
         }
      } else if (this.af.h()) {
         this.a(entitypanda.gv());
         this.b(entitypanda1.gv());
      } else {
         this.a(entitypanda1.gv());
         this.b(entitypanda.gv());
      }

      if (this.af.a(32) == 0) {
         this.a(EntityPanda.Gene.a(this.af));
      }

      if (this.af.a(32) == 0) {
         this.b(EntityPanda.Gene.a(this.af));
      }
   }

   private EntityPanda.Gene gv() {
      return this.af.h() ? this.ga() : this.gb();
   }

   public void gl() {
      if (this.gj()) {
         this.a(GenericAttributes.a).a(10.0);
      }

      if (this.gf()) {
         this.a(GenericAttributes.d).a(0.07F);
      }
   }

   void gw() {
      if (!this.aT()) {
         this.y(0.0F);
         this.G().n();
         this.w(true);
      }
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (this.gk()) {
         return EnumInteractionResult.d;
      } else if (this.fS()) {
         this.x(false);
         return EnumInteractionResult.a(this.H.B);
      } else if (this.m(itemstack)) {
         if (this.P_() != null) {
            this.ci = true;
         }

         if (this.y_()) {
            this.a(entityhuman, enumhand, itemstack);
            this.a((int)((float)(-this.h() / 20) * 0.1F), true);
         } else if (!this.H.B && this.h() == 0 && this.fT()) {
            this.a(entityhuman, enumhand, itemstack);
            this.f(entityhuman);
         } else {
            if (this.H.B || this.w() || this.aT()) {
               return EnumInteractionResult.d;
            }

            this.gw();
            this.y(true);
            ItemStack itemstack1 = this.c(EnumItemSlot.a);
            if (!itemstack1.b() && !entityhuman.fK().d) {
               this.b(itemstack1);
            }

            this.a(EnumItemSlot.a, new ItemStack(itemstack.c(), 1));
            this.a(entityhuman, enumhand, itemstack);
         }

         return EnumInteractionResult.a;
      } else {
         return EnumInteractionResult.d;
      }
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return this.fM() ? SoundEffects.qB : (this.gg() ? SoundEffects.qC : SoundEffects.qw);
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.qz, 0.15F, 1.0F);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(Blocks.mV.k());
   }

   private boolean l(ItemStack itemstack) {
      return this.m(itemstack) || itemstack.a(Blocks.eg.k());
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return SoundEffects.qx;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.qD;
   }

   public boolean gm() {
      return !this.fS() && !this.gk() && !this.fY() && !this.gc() && !this.w();
   }

   public static enum Gene implements INamable {
      a(0, "normal", false),
      b(1, "lazy", false),
      c(2, "worried", false),
      d(3, "playful", false),
      e(4, "brown", true),
      f(5, "weak", true),
      g(6, "aggressive", false);

      public static final INamable.a<EntityPanda.Gene> h = INamable.a(EntityPanda.Gene::values);
      private static final IntFunction<EntityPanda.Gene> i = ByIdMap.a(EntityPanda.Gene::a, values(), ByIdMap.a.a);
      private static final int j = 6;
      private final int k;
      private final String l;
      private final boolean m;

      private Gene(int i, String s, boolean flag) {
         this.k = i;
         this.l = s;
         this.m = flag;
      }

      public int a() {
         return this.k;
      }

      @Override
      public String c() {
         return this.l;
      }

      public boolean b() {
         return this.m;
      }

      static EntityPanda.Gene a(EntityPanda.Gene entitypanda_gene, EntityPanda.Gene entitypanda_gene1) {
         return entitypanda_gene.b() ? (entitypanda_gene == entitypanda_gene1 ? entitypanda_gene : a) : entitypanda_gene;
      }

      public static EntityPanda.Gene a(int i) {
         return EntityPanda.Gene.i.apply(i);
      }

      public static EntityPanda.Gene a(String s) {
         return h.a(s, a);
      }

      public static EntityPanda.Gene a(RandomSource randomsource) {
         int i = randomsource.a(16);
         return i == 0 ? b : (i == 1 ? c : (i == 2 ? d : (i == 4 ? g : (i < 9 ? f : (i < 11 ? e : a)))));
      }
   }

   private static class b extends PathfinderGoalMeleeAttack {
      private final EntityPanda b;

      public b(EntityPanda entitypanda, double d0, boolean flag) {
         super(entitypanda, d0, flag);
         this.b = entitypanda;
      }

      @Override
      public boolean a() {
         return this.b.gm() && super.a();
      }
   }

   private static class c<T extends EntityLiving> extends PathfinderGoalAvoidTarget<T> {
      private final EntityPanda i;

      public c(EntityPanda entitypanda, Class<T> oclass, float f, double d0, double d1) {
         super(entitypanda, oclass, f, d0, d1, IEntitySelector.f::test);
         this.i = entitypanda;
      }

      @Override
      public boolean a() {
         return this.i.gg() && this.i.gm() && super.a();
      }
   }

   private static class d extends PathfinderGoalBreed {
      private final EntityPanda d;
      private int e;

      public d(EntityPanda entitypanda, double d0) {
         super(entitypanda, d0);
         this.d = entitypanda;
      }

      @Override
      public boolean a() {
         if (!super.a() || this.d.q() != 0) {
            return false;
         } else if (!this.h()) {
            if (this.e <= this.d.ag) {
               this.d.s(32);
               this.e = this.d.ag + 600;
               if (this.d.cU()) {
                  EntityHuman entityhuman = this.b.a(EntityPanda.cb, this.d);
                  this.d.cr.a(entityhuman);
               }
            }

            return false;
         } else {
            return true;
         }
      }

      private boolean h() {
         BlockPosition blockposition = this.d.dg();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 8; ++j) {
               for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                  for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                     blockposition_mutableblockposition.a(blockposition, k, i, l);
                     if (this.b.a_(blockposition_mutableblockposition).a(Blocks.mV)) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   private static class e extends PathfinderGoalHurtByTarget {
      private final EntityPanda a;

      public e(EntityPanda entitypanda, Class<?>... aclass) {
         super(entitypanda, aclass);
         this.a = entitypanda;
      }

      @Override
      public boolean b() {
         if (!this.a.ci && !this.a.cj) {
            return super.b();
         } else {
            this.a.i(null);
            return false;
         }
      }

      @Override
      protected void a(EntityInsentient entityinsentient, EntityLiving entityliving) {
         if (entityinsentient instanceof EntityPanda && ((EntityPanda)entityinsentient).fM()) {
            entityinsentient.setTarget(entityliving, TargetReason.TARGET_ATTACKED_ENTITY, true);
         }
      }
   }

   private static class f extends PathfinderGoal {
      private final EntityPanda a;
      private int b;

      public f(EntityPanda entitypanda) {
         this.a = entitypanda;
      }

      @Override
      public boolean a() {
         return this.b < this.a.ag && this.a.gf() && this.a.gm() && this.a.af.a(b(400)) == 1;
      }

      @Override
      public boolean b() {
         return !this.a.aT() && (this.a.gf() || this.a.af.a(b(600)) != 1) ? this.a.af.a(b(2000)) != 1 : false;
      }

      @Override
      public void c() {
         this.a.x(true);
         this.b = 0;
      }

      @Override
      public void d() {
         this.a.x(false);
         this.b = this.a.ag + 200;
      }
   }

   private static class g extends PathfinderGoalLookAtPlayer {
      private final EntityPanda h;

      public g(EntityPanda entitypanda, Class<? extends EntityLiving> oclass, float f) {
         super(entitypanda, oclass, f);
         this.h = entitypanda;
      }

      public void a(EntityLiving entityliving) {
         this.c = entityliving;
      }

      @Override
      public boolean b() {
         return this.c != null && super.b();
      }

      @Override
      public boolean a() {
         if (this.b.dZ().i() >= this.e) {
            return false;
         } else {
            if (this.c == null) {
               if (this.f == EntityHuman.class) {
                  this.c = this.b.H.a(this.g, this.b, this.b.dl(), this.b.dp(), this.b.dr());
               } else {
                  this.c = this.b
                     .H
                     .a(
                        this.b.H.a(this.f, this.b.cD().c((double)this.d, 3.0, (double)this.d), entityliving -> true),
                        this.g,
                        this.b,
                        this.b.dl(),
                        this.b.dp(),
                        this.b.dr()
                     );
               }
            }

            return this.h.gm() && this.c != null;
         }
      }

      @Override
      public void e() {
         if (this.c != null) {
            super.e();
         }
      }
   }

   private static class h extends ControllerMove {
      private final EntityPanda l;

      public h(EntityPanda entitypanda) {
         super(entitypanda);
         this.l = entitypanda;
      }

      @Override
      public void a() {
         if (this.l.gm()) {
            super.a();
         }
      }
   }

   private static class i extends PathfinderGoalPanic {
      private final EntityPanda h;

      public i(EntityPanda entitypanda, double d0) {
         super(entitypanda, d0);
         this.h = entitypanda;
      }

      @Override
      protected boolean h() {
         return this.b.dv() || this.b.bK();
      }

      @Override
      public boolean b() {
         if (this.h.w()) {
            this.h.G().n();
            return false;
         } else {
            return super.b();
         }
      }
   }

   private static class j extends PathfinderGoal {
      private final EntityPanda a;

      public j(EntityPanda entitypanda) {
         this.a = entitypanda;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b, PathfinderGoal.Type.c));
      }

      @Override
      public boolean a() {
         if ((this.a.y_() || this.a.gh()) && this.a.N) {
            if (!this.a.gm()) {
               return false;
            } else {
               float f = this.a.dw() * (float) (Math.PI / 180.0);
               float f1 = -MathHelper.a(f);
               float f2 = MathHelper.b(f);
               int i = (double)Math.abs(f1) > 0.5 ? MathHelper.j((double)f1) : 0;
               int j = (double)Math.abs(f2) > 0.5 ? MathHelper.j((double)f2) : 0;
               return this.a.H.a_(this.a.dg().b(i, -1, j)).h() ? true : (this.a.gh() && this.a.af.a(b(60)) == 1 ? true : this.a.af.a(b(500)) == 1);
            }
         } else {
            return false;
         }
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         this.a.A(true);
      }

      @Override
      public boolean I_() {
         return false;
      }
   }

   private class k extends PathfinderGoal {
      private int b;

      public k() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         if (this.b <= EntityPanda.this.ag && !EntityPanda.this.y_() && !EntityPanda.this.aT() && EntityPanda.this.gm() && EntityPanda.this.q() <= 0) {
            List<EntityItem> list = EntityPanda.this.H.a(EntityItem.class, EntityPanda.this.cD().c(6.0, 6.0, 6.0), EntityPanda.cs);
            return !list.isEmpty() || !EntityPanda.this.c(EnumItemSlot.a).b();
         } else {
            return false;
         }
      }

      @Override
      public boolean b() {
         return !EntityPanda.this.aT() && (EntityPanda.this.gf() || EntityPanda.this.af.a(b(600)) != 1) ? EntityPanda.this.af.a(b(2000)) != 1 : false;
      }

      @Override
      public void e() {
         if (!EntityPanda.this.w() && !EntityPanda.this.c(EnumItemSlot.a).b()) {
            EntityPanda.this.gw();
         }
      }

      @Override
      public void c() {
         List<EntityItem> list = EntityPanda.this.H.a(EntityItem.class, EntityPanda.this.cD().c(8.0, 8.0, 8.0), EntityPanda.cs);
         if (!list.isEmpty() && EntityPanda.this.c(EnumItemSlot.a).b()) {
            EntityPanda.this.G().a(list.get(0), 1.2F);
         } else if (!EntityPanda.this.c(EnumItemSlot.a).b()) {
            EntityPanda.this.gw();
         }

         this.b = 0;
      }

      @Override
      public void d() {
         ItemStack itemstack = EntityPanda.this.c(EnumItemSlot.a);
         if (!itemstack.b()) {
            EntityPanda.this.b(itemstack);
            EntityPanda.this.a(EnumItemSlot.a, ItemStack.b);
            int i = EntityPanda.this.gf() ? EntityPanda.this.af.a(50) + 10 : EntityPanda.this.af.a(150) + 10;
            this.b = EntityPanda.this.ag + i * 20;
         }

         EntityPanda.this.w(false);
      }
   }

   private static class l extends PathfinderGoal {
      private final EntityPanda a;

      public l(EntityPanda entitypanda) {
         this.a = entitypanda;
      }

      @Override
      public boolean a() {
         return this.a.y_() && this.a.gm() ? (this.a.gj() && this.a.af.a(b(500)) == 1 ? true : this.a.af.a(b(6000)) == 1) : false;
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         this.a.z(true);
      }
   }
}
