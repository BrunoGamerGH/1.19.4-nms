package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalArrowAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalGotoTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.PathfinderGoalZombieAttack;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationGuardian;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;

public class EntityDrowned extends EntityZombie implements IRangedEntity {
   public static final float b = 0.03F;
   boolean bW;
   public final NavigationGuardian c;
   public final Navigation d;

   public EntityDrowned(EntityTypes<? extends EntityDrowned> entitytypes, World world) {
      super(entitytypes, world);
      this.v(1.0F);
      this.bK = new EntityDrowned.d(this);
      this.a(PathType.j, 0.0F);
      this.c = new NavigationGuardian(this, world);
      this.d = new Navigation(this, world);
   }

   @Override
   protected void q() {
      this.bN.a(1, new EntityDrowned.c(this, 1.0));
      this.bN.a(2, new EntityDrowned.f(this, 1.0, 40, 10.0F));
      this.bN.a(2, new EntityDrowned.a(this, 1.0, false));
      this.bN.a(5, new EntityDrowned.b(this, 1.0));
      this.bN.a(6, new EntityDrowned.e(this, 1.0, this.H.m_()));
      this.bN.a(7, new PathfinderGoalRandomStroll(this, 1.0));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityDrowned.class).a(EntityPigZombie.class));
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, 10, true, false, this::m));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, false));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, true));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, Axolotl.class, true, false));
      this.bO.a(5, new PathfinderGoalNearestAttackableTarget<>(this, EntityTurtle.class, 10, true, false, EntityTurtle.bT));
   }

   @Override
   public GroupDataEntity a(
      WorldAccess worldaccess,
      DifficultyDamageScaler difficultydamagescaler,
      EnumMobSpawn enummobspawn,
      @Nullable GroupDataEntity groupdataentity,
      @Nullable NBTTagCompound nbttagcompound
   ) {
      groupdataentity = super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      if (this.c(EnumItemSlot.b).b() && worldaccess.r_().i() < 0.03F) {
         this.a(EnumItemSlot.b, new ItemStack(Items.uR));
         this.e(EnumItemSlot.b);
      }

      return groupdataentity;
   }

   public static boolean a(
      EntityTypes<EntityDrowned> entitytypes, WorldAccess worldaccess, EnumMobSpawn enummobspawn, BlockPosition blockposition, RandomSource randomsource
   ) {
      if (!worldaccess.b_(blockposition.d()).a(TagsFluid.a)) {
         return false;
      } else {
         Holder<BiomeBase> holder = worldaccess.v(blockposition);
         boolean flag = worldaccess.ah() != EnumDifficulty.a
            && a(worldaccess, blockposition, randomsource)
            && (enummobspawn == EnumMobSpawn.c || worldaccess.b_(blockposition).a(TagsFluid.a));
         return holder.a(BiomeTags.an) ? randomsource.a(15) == 0 && flag : randomsource.a(40) == 0 && a(worldaccess, blockposition) && flag;
      }
   }

   private static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      return blockposition.v() < generatoraccess.m_() - 5;
   }

   @Override
   protected boolean r() {
      return false;
   }

   @Override
   protected SoundEffect s() {
      return this.aT() ? SoundEffects.gt : SoundEffects.gs;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.aT() ? SoundEffects.gx : SoundEffects.gw;
   }

   @Override
   protected SoundEffect x_() {
      return this.aT() ? SoundEffects.gv : SoundEffects.gu;
   }

   @Override
   protected SoundEffect w() {
      return SoundEffects.gz;
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.gA;
   }

   @Override
   protected ItemStack fS() {
      return ItemStack.b;
   }

   @Override
   protected void a(RandomSource randomsource, DifficultyDamageScaler difficultydamagescaler) {
      if ((double)randomsource.i() > 0.9) {
         int i = randomsource.a(16);
         if (i < 10) {
            this.a(EnumItemSlot.a, new ItemStack(Items.uP));
         } else {
            this.a(EnumItemSlot.a, new ItemStack(Items.qd));
         }
      }
   }

   @Override
   protected boolean b(ItemStack itemstack, ItemStack itemstack1) {
      return itemstack1.a(Items.uR)
         ? false
         : (
            itemstack1.a(Items.uP)
               ? (itemstack.a(Items.uP) ? itemstack.j() < itemstack1.j() : false)
               : (itemstack.a(Items.uP) ? true : super.b(itemstack, itemstack1))
         );
   }

   @Override
   protected boolean fT() {
      return false;
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return iworldreader.f(this);
   }

   public boolean m(@Nullable EntityLiving entityliving) {
      return entityliving != null ? !this.H.M() || entityliving.aT() : false;
   }

   @Override
   public boolean cv() {
      return !this.bV();
   }

   boolean gc() {
      if (this.bW) {
         return true;
      } else {
         EntityLiving entityliving = this.P_();
         return entityliving != null && entityliving.aT();
      }
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.cT() && this.aT() && this.gc()) {
         this.a(0.01F, vec3d);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
      } else {
         super.h(vec3d);
      }
   }

   @Override
   public void aY() {
      if (!this.H.B) {
         if (this.cU() && this.aT() && this.gc()) {
            this.bM = this.c;
            this.h(true);
         } else {
            this.bM = this.d;
            this.h(false);
         }
      }
   }

   @Override
   public boolean bW() {
      return this.bV();
   }

   protected boolean fU() {
      PathEntity pathentity = this.G().j();
      if (pathentity != null) {
         BlockPosition blockposition = pathentity.m();
         if (blockposition != null) {
            double d0 = this.i((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
            if (d0 < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void a(EntityLiving entityliving, float f) {
      EntityThrownTrident entitythrowntrident = new EntityThrownTrident(this.H, this, this.b(ProjectileHelper.a(this, Items.uP)));
      double d0 = entityliving.dl() - this.dl();
      double d1 = entityliving.e(0.3333333333333333) - entitythrowntrident.dn();
      double d2 = entityliving.dr() - this.dr();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      entitythrowntrident.c(d0, d1 + d3 * 0.2F, d2, 1.6F, (float)(14 - this.H.ah().a() * 4));
      this.a(SoundEffects.gy, 1.0F, 1.0F / (this.dZ().i() * 0.4F + 0.8F));
      this.H.b(entitythrowntrident);
   }

   public void w(boolean flag) {
      this.bW = flag;
   }

   private static class a extends PathfinderGoalZombieAttack {
      private final EntityDrowned b;

      public a(EntityDrowned entitydrowned, double d0, boolean flag) {
         super(entitydrowned, d0, flag);
         this.b = entitydrowned;
      }

      @Override
      public boolean a() {
         return super.a() && this.b.m(this.b.P_());
      }

      @Override
      public boolean b() {
         return super.b() && this.b.m(this.b.P_());
      }
   }

   private static class b extends PathfinderGoalGotoTarget {
      private final EntityDrowned g;

      public b(EntityDrowned entitydrowned, double d0) {
         super(entitydrowned, d0, 8, 2);
         this.g = entitydrowned;
      }

      @Override
      public boolean a() {
         return super.a() && !this.g.H.M() && this.g.aT() && this.g.dn() >= (double)(this.g.H.m_() - 3);
      }

      @Override
      public boolean b() {
         return super.b();
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         BlockPosition blockposition1 = blockposition.c();
         return iworldreader.w(blockposition1) && iworldreader.w(blockposition1.c())
            ? iworldreader.a_(blockposition).a(iworldreader, blockposition, this.g)
            : false;
      }

      @Override
      public void c() {
         this.g.w(false);
         this.g.bM = this.g.d;
         super.c();
      }

      @Override
      public void d() {
         super.d();
      }
   }

   private static class c extends PathfinderGoal {
      private final EntityCreature a;
      private double b;
      private double c;
      private double d;
      private final double e;
      private final World f;

      public c(EntityCreature entitycreature, double d0) {
         this.a = entitycreature;
         this.e = d0;
         this.f = entitycreature.H;
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         if (!this.f.M()) {
            return false;
         } else if (this.a.aT()) {
            return false;
         } else {
            Vec3D vec3d = this.h();
            if (vec3d == null) {
               return false;
            } else {
               this.b = vec3d.c;
               this.c = vec3d.d;
               this.d = vec3d.e;
               return true;
            }
         }
      }

      @Override
      public boolean b() {
         return !this.a.G().l();
      }

      @Override
      public void c() {
         this.a.G().a(this.b, this.c, this.d, this.e);
      }

      @Nullable
      private Vec3D h() {
         RandomSource randomsource = this.a.dZ();
         BlockPosition blockposition = this.a.dg();

         for(int i = 0; i < 10; ++i) {
            BlockPosition blockposition1 = blockposition.b(randomsource.a(20) - 10, 2 - randomsource.a(8), randomsource.a(20) - 10);
            if (this.f.a_(blockposition1).a(Blocks.G)) {
               return Vec3D.c(blockposition1);
            }
         }

         return null;
      }
   }

   private static class d extends ControllerMove {
      private final EntityDrowned l;

      public d(EntityDrowned entitydrowned) {
         super(entitydrowned);
         this.l = entitydrowned;
      }

      @Override
      public void a() {
         EntityLiving entityliving = this.l.P_();
         if (this.l.gc() && this.l.aT()) {
            if (entityliving != null && entityliving.dn() > this.l.dn() || this.l.bW) {
               this.l.f(this.l.dj().b(0.0, 0.002, 0.0));
            }

            if (this.k != ControllerMove.Operation.b || this.l.G().l()) {
               this.l.h(0.0F);
               return;
            }

            double d0 = this.e - this.l.dl();
            double d1 = this.f - this.l.dn();
            double d2 = this.g - this.l.dr();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(MathHelper.d(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
            this.l.f(this.a(this.l.dw(), f, 90.0F));
            this.l.aT = this.l.dw();
            float f1 = (float)(this.h * this.l.b(GenericAttributes.d));
            float f2 = MathHelper.i(0.125F, this.l.eW(), f1);
            this.l.h(f2);
            this.l.f(this.l.dj().b((double)f2 * d0 * 0.005, (double)f2 * d1 * 0.1, (double)f2 * d2 * 0.005));
         } else {
            if (!this.l.N) {
               this.l.f(this.l.dj().b(0.0, -0.008, 0.0));
            }

            super.a();
         }
      }
   }

   private static class e extends PathfinderGoal {
      private final EntityDrowned a;
      private final double b;
      private final int c;
      private boolean d;

      public e(EntityDrowned entitydrowned, double d0, int i) {
         this.a = entitydrowned;
         this.b = d0;
         this.c = i;
      }

      @Override
      public boolean a() {
         return !this.a.H.M() && this.a.aT() && this.a.dn() < (double)(this.c - 2);
      }

      @Override
      public boolean b() {
         return this.a() && !this.d;
      }

      @Override
      public void e() {
         if (this.a.dn() < (double)(this.c - 1) && (this.a.G().l() || this.a.fU())) {
            Vec3D vec3d = DefaultRandomPos.a(this.a, 4, 8, new Vec3D(this.a.dl(), (double)(this.c - 1), this.a.dr()), (float) (Math.PI / 2));
            if (vec3d == null) {
               this.d = true;
               return;
            }

            this.a.G().a(vec3d.c, vec3d.d, vec3d.e, this.b);
         }
      }

      @Override
      public void c() {
         this.a.w(true);
         this.d = false;
      }

      @Override
      public void d() {
         this.a.w(false);
      }
   }

   private static class f extends PathfinderGoalArrowAttack {
      private final EntityDrowned a;

      public f(IRangedEntity irangedentity, double d0, int i, float f) {
         super(irangedentity, d0, i, f);
         this.a = (EntityDrowned)irangedentity;
      }

      @Override
      public boolean a() {
         return super.a() && this.a.eK().a(Items.uP);
      }

      @Override
      public void c() {
         super.c();
         this.a.v(true);
         this.a.c(EnumHand.a);
      }

      @Override
      public void d() {
         super.d();
         this.a.fk();
         this.a.v(false);
      }
   }
}
