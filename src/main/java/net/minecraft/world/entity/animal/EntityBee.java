package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.MathHelper;
import net.minecraft.util.TimeRange;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerLook;
import net.minecraft.world.entity.ai.control.ControllerMoveFlying;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalSelector;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.navigation.NavigationFlying;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceRecord;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCrops;
import net.minecraft.world.level.block.BlockStem;
import net.minecraft.world.level.block.BlockSweetBerryBush;
import net.minecraft.world.level.block.BlockTallPlant;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IBlockFragilePlantElement;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityBee extends EntityAnimal implements IEntityAngerable, EntityBird {
   public static final float bS = 120.32113F;
   public static final int bT = MathHelper.f(1.4959966F);
   private static final DataWatcherObject<Byte> cc = DataWatcher.a(EntityBee.class, DataWatcherRegistry.a);
   private static final DataWatcherObject<Integer> cd = DataWatcher.a(EntityBee.class, DataWatcherRegistry.b);
   private static final int ce = 2;
   private static final int cf = 4;
   private static final int cg = 8;
   private static final int ch = 1200;
   private static final int ci = 2400;
   private static final int cj = 3600;
   private static final int ck = 4;
   private static final int cl = 10;
   private static final int cm = 10;
   private static final int cn = 18;
   private static final int co = 32;
   private static final int cp = 2;
   private static final int cq = 16;
   private static final int cr = 20;
   public static final String bV = "CropsGrownSincePollination";
   public static final String bW = "CannotEnterHiveTicks";
   public static final String bX = "TicksSincePollination";
   public static final String bY = "HasStung";
   public static final String bZ = "HasNectar";
   public static final String ca = "FlowerPos";
   public static final String cb = "HivePos";
   private static final UniformInt cs = TimeRange.a(20, 39);
   @Nullable
   private UUID ct;
   private float cu;
   private float cv;
   private int cw;
   int cx;
   public int cy;
   private int cz;
   private static final int cA = 200;
   int cB;
   private static final int cC = 200;
   int cD = MathHelper.a(this.af, 20, 60);
   @Nullable
   BlockPosition cE;
   @Nullable
   public BlockPosition cF;
   EntityBee.k cG;
   EntityBee.e cH;
   private EntityBee.f cI;
   private int cJ;

   public EntityBee(EntityTypes<? extends EntityBee> entitytypes, World world) {
      super(entitytypes, world);
      this.bK = new ControllerMoveFlying(this, 20, true);
      this.bJ = new EntityBee.j(this);
      this.a(PathType.n, -1.0F);
      this.a(PathType.j, -1.0F);
      this.a(PathType.k, 16.0F);
      this.a(PathType.x, -1.0F);
      this.a(PathType.h, -1.0F);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(cc, (byte)0);
      this.am.a(cd, 0);
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return iworldreader.a_(blockposition).h() ? 10.0F : 0.0F;
   }

   @Override
   protected void x() {
      this.bN.a(0, new EntityBee.b(this, 1.4F, true));
      this.bN.a(1, new EntityBee.d());
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.25, RecipeItemStack.a(TagsItem.R), false));
      this.cG = new EntityBee.k();
      this.bN.a(4, this.cG);
      this.bN.a(5, new PathfinderGoalFollowParent(this, 1.25));
      this.bN.a(5, new EntityBee.i());
      this.cH = new EntityBee.e();
      this.bN.a(5, this.cH);
      this.cI = new EntityBee.f();
      this.bN.a(6, this.cI);
      this.bN.a(7, new EntityBee.g());
      this.bN.a(8, new EntityBee.l());
      this.bN.a(9, new PathfinderGoalFloat(this));
      this.bO.a(1, new EntityBee.h(this).a(new Class[0]));
      this.bO.a(2, new EntityBee.c(this));
      this.bO.a(3, new PathfinderGoalUniversalAngerReset<>(this, true));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.fZ()) {
         nbttagcompound.a("HivePos", GameProfileSerializer.a(this.ga()));
      }

      if (this.r()) {
         nbttagcompound.a("FlowerPos", GameProfileSerializer.a(this.q()));
      }

      nbttagcompound.a("HasNectar", this.gc());
      nbttagcompound.a("HasStung", this.gd());
      nbttagcompound.a("TicksSincePollination", this.cx);
      nbttagcompound.a("CannotEnterHiveTicks", this.cy);
      nbttagcompound.a("CropsGrownSincePollination", this.cz);
      this.c(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.cF = null;
      if (nbttagcompound.e("HivePos")) {
         this.cF = GameProfileSerializer.b(nbttagcompound.p("HivePos"));
      }

      this.cE = null;
      if (nbttagcompound.e("FlowerPos")) {
         this.cE = GameProfileSerializer.b(nbttagcompound.p("FlowerPos"));
      }

      super.a(nbttagcompound);
      this.w(nbttagcompound.q("HasNectar"));
      this.x(nbttagcompound.q("HasStung"));
      this.cx = nbttagcompound.h("TicksSincePollination");
      this.cy = nbttagcompound.h("CannotEnterHiveTicks");
      this.cz = nbttagcompound.h("CropsGrownSincePollination");
      this.a(this.H, nbttagcompound);
   }

   @Override
   public boolean z(Entity entity) {
      boolean flag = entity.a(this.dG().a((EntityLiving)this), (float)((int)this.b(GenericAttributes.f)));
      if (flag) {
         this.a(this, entity);
         if (entity instanceof EntityLiving) {
            ((EntityLiving)entity).p(((EntityLiving)entity).eG() + 1);
            byte b0 = 0;
            if (this.H.ah() == EnumDifficulty.c) {
               b0 = 10;
            } else if (this.H.ah() == EnumDifficulty.d) {
               b0 = 18;
            }

            if (b0 > 0) {
               ((EntityLiving)entity).addEffect(new MobEffect(MobEffects.s, b0 * 20, 0), this, Cause.ATTACK);
            }
         }

         this.x(true);
         this.N_();
         this.a(SoundEffects.bC, 1.0F, 1.0F);
      }

      return flag;
   }

   @Override
   public void l() {
      super.l();
      if (this.gc() && this.gl() < 10 && this.af.i() < 0.05F) {
         for(int i = 0; i < this.af.a(2) + 1; ++i) {
            this.a(this.H, this.dl() - 0.3F, this.dl() + 0.3F, this.dr() - 0.3F, this.dr() + 0.3F, this.e(0.5), Particles.au);
         }
      }

      this.gj();
   }

   private void a(World world, double d0, double d1, double d2, double d3, double d4, ParticleParam particleparam) {
      world.a(particleparam, MathHelper.d(world.z.j(), d0, d1), d4, MathHelper.d(world.z.j(), d2, d3), 0.0, 0.0, 0.0);
   }

   void h(BlockPosition blockposition) {
      Vec3D vec3d = Vec3D.c(blockposition);
      byte b0 = 0;
      BlockPosition blockposition1 = this.dg();
      int i = (int)vec3d.d - blockposition1.v();
      if (i > 2) {
         b0 = 4;
      } else if (i < -2) {
         b0 = -4;
      }

      int j = 6;
      int k = 8;
      int l = blockposition1.k(blockposition);
      if (l < 15) {
         j = l / 2;
         k = l / 2;
      }

      Vec3D vec3d1 = AirRandomPos.a(this, j, k, b0, vec3d, (float) (Math.PI / 10));
      if (vec3d1 != null) {
         this.bM.a(0.5F);
         this.bM.a(vec3d1.c, vec3d1.d, vec3d1.e, 1.0);
      }
   }

   @Nullable
   public BlockPosition q() {
      return this.cE;
   }

   public boolean r() {
      return this.cE != null;
   }

   public void g(BlockPosition blockposition) {
      this.cE = blockposition;
   }

   @VisibleForDebug
   public int w() {
      return Math.max(this.cH.d, this.cI.d);
   }

   @VisibleForDebug
   public List<BlockPosition> fS() {
      return this.cH.f;
   }

   private boolean gh() {
      return this.cx > 3600;
   }

   boolean gi() {
      if (this.cy <= 0 && !this.cG.l() && !this.gd() && this.P_() == null) {
         boolean flag = this.gh() || this.H.Y() || this.H.N() || this.gc();
         return flag && !this.gk();
      } else {
         return false;
      }
   }

   public void s(int i) {
      this.cy = i;
   }

   public float C(float f) {
      return MathHelper.i(f, this.cv, this.cu);
   }

   private void gj() {
      this.cv = this.cu;
      if (this.gp()) {
         this.cu = Math.min(1.0F, this.cu + 0.2F);
      } else {
         this.cu = Math.max(0.0F, this.cu - 0.24F);
      }
   }

   @Override
   protected void U() {
      boolean flag = this.gd();
      if (this.aW()) {
         ++this.cJ;
      } else {
         this.cJ = 0;
      }

      if (this.cJ > 20) {
         this.a(this.dG().h(), 1.0F);
      }

      if (flag) {
         ++this.cw;
         if (this.cw % 5 == 0 && this.af.a(MathHelper.a(1200 - this.cw, 1, 1200)) == 0) {
            this.a(this.dG().n(), this.eo());
         }
      }

      if (!this.gc()) {
         ++this.cx;
      }

      if (!this.H.B) {
         this.a((WorldServer)this.H, false);
      }
   }

   public void fY() {
      this.cx = 0;
   }

   private boolean gk() {
      if (this.cF == null) {
         return false;
      } else {
         TileEntity tileentity = this.H.c_(this.cF);
         return tileentity instanceof TileEntityBeehive && ((TileEntityBeehive)tileentity).c();
      }
   }

   @Override
   public int a() {
      return this.am.a(cd);
   }

   @Override
   public void a(int i) {
      this.am.b(cd, i);
   }

   @Nullable
   @Override
   public UUID b() {
      return this.ct;
   }

   @Override
   public void a(@Nullable UUID uuid) {
      this.ct = uuid;
   }

   @Override
   public void c() {
      this.a(cs.a(this.af));
   }

   private boolean i(BlockPosition blockposition) {
      TileEntity tileentity = this.H.c_(blockposition);
      return tileentity instanceof TileEntityBeehive ? !((TileEntityBeehive)tileentity).f() : false;
   }

   @VisibleForDebug
   public boolean fZ() {
      return this.cF != null;
   }

   @Nullable
   @VisibleForDebug
   public BlockPosition ga() {
      return this.cF;
   }

   @VisibleForDebug
   public PathfinderGoalSelector gb() {
      return this.bN;
   }

   @Override
   protected void T() {
      super.T();
      PacketDebug.a(this);
   }

   int gl() {
      return this.cz;
   }

   private void gm() {
      this.cz = 0;
   }

   void gn() {
      ++this.cz;
   }

   @Override
   public void b_() {
      super.b_();
      if (!this.H.B) {
         if (this.cy > 0) {
            --this.cy;
         }

         if (this.cB > 0) {
            --this.cB;
         }

         if (this.cD > 0) {
            --this.cD;
         }

         boolean flag = this.R_() && !this.gd() && this.P_() != null && this.P_().f(this) < 4.0;
         this.y(flag);
         if (this.ag % 20 == 0 && !this.go()) {
            this.cF = null;
         }
      }
   }

   boolean go() {
      if (!this.fZ()) {
         return false;
      } else if (this.j(this.cF)) {
         return false;
      } else {
         TileEntity tileentity = this.H.c_(this.cF);
         return tileentity != null && tileentity.u() == TileEntityTypes.H;
      }
   }

   public boolean gc() {
      return this.t(8);
   }

   public void w(boolean flag) {
      if (flag) {
         this.fY();
      }

      this.d(8, flag);
   }

   public boolean gd() {
      return this.t(4);
   }

   public void x(boolean flag) {
      this.d(4, flag);
   }

   private boolean gp() {
      return this.t(2);
   }

   private void y(boolean flag) {
      this.d(2, flag);
   }

   boolean j(BlockPosition blockposition) {
      return !this.b(blockposition, 32);
   }

   private void d(int i, boolean flag) {
      if (flag) {
         this.am.b(cc, (byte)(this.am.a(cc) | i));
      } else {
         this.am.b(cc, (byte)(this.am.a(cc) & ~i));
      }
   }

   private boolean t(int i) {
      return (this.am.a(cc) & i) != 0;
   }

   public static AttributeProvider.Builder ge() {
      return EntityInsentient.y()
         .a(GenericAttributes.a, 10.0)
         .a(GenericAttributes.e, 0.6F)
         .a(GenericAttributes.d, 0.3F)
         .a(GenericAttributes.f, 2.0)
         .a(GenericAttributes.b, 48.0);
   }

   @Override
   protected NavigationAbstract a(World world) {
      NavigationFlying navigationflying = new NavigationFlying(this, world) {
         @Override
         public boolean a(BlockPosition blockposition) {
            return !this.b.a_(blockposition.d()).h();
         }

         @Override
         public void c() {
            if (!EntityBee.this.cG.l()) {
               super.c();
            }
         }
      };
      navigationflying.b(false);
      navigationflying.a(false);
      navigationflying.c(true);
      return navigationflying;
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(TagsItem.R);
   }

   boolean k(BlockPosition blockposition) {
      return this.H.o(blockposition) && this.H.a_(blockposition).a(TagsBlock.T);
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
   }

   @Override
   protected SoundEffect s() {
      return null;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.bz;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.by;
   }

   @Override
   protected float eN() {
      return 0.4F;
   }

   @Nullable
   public EntityBee b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.h.a((World)worldserver);
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return this.y_() ? entitysize.b * 0.5F : entitysize.b * 0.5F;
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
   }

   @Override
   public boolean aN() {
      return this.gf() && this.ag % bT == 0;
   }

   @Override
   public boolean gf() {
      return !this.N;
   }

   public void gg() {
      this.w(false);
      this.gm();
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         boolean result = super.a(damagesource, f);
         if (result && !this.H.B) {
            this.cG.m();
         }

         return result;
      }
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.c;
   }

   @Override
   protected void c(TagKey<FluidType> tagkey) {
      this.f(this.dj().b(0.0, 0.01, 0.0));
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.5F * this.cE()), (double)(this.dc() * 0.2F));
   }

   boolean b(BlockPosition blockposition, int i) {
      return blockposition.a(this.dg(), (double)i);
   }

   private abstract class a extends PathfinderGoal {
      a() {
      }

      public abstract boolean h();

      public abstract boolean i();

      @Override
      public boolean a() {
         return this.h() && !EntityBee.this.R_();
      }

      @Override
      public boolean b() {
         return this.i() && !EntityBee.this.R_();
      }
   }

   private class b extends PathfinderGoalMeleeAttack {
      b(EntityCreature entitycreature, double d0, boolean flag) {
         super(entitycreature, d0, flag);
      }

      @Override
      public boolean a() {
         return super.a() && EntityBee.this.R_() && !EntityBee.this.gd();
      }

      @Override
      public boolean b() {
         return super.b() && EntityBee.this.R_() && !EntityBee.this.gd();
      }
   }

   private static class c extends PathfinderGoalNearestAttackableTarget<EntityHuman> {
      c(EntityBee entitybee) {
         super(entitybee, EntityHuman.class, 10, true, false, entitybee::a_);
      }

      @Override
      public boolean a() {
         return this.i() && super.a();
      }

      @Override
      public boolean b() {
         boolean flag = this.i();
         if (flag && this.e.P_() != null) {
            return super.b();
         } else {
            this.g = null;
            return false;
         }
      }

      private boolean i() {
         EntityBee entitybee = (EntityBee)this.e;
         return entitybee.R_() && !entitybee.gd();
      }
   }

   private class d extends EntityBee.a {
      d() {
      }

      @Override
      public boolean h() {
         if (EntityBee.this.fZ() && EntityBee.this.gi() && EntityBee.this.cF.a(EntityBee.this.de(), 2.0)) {
            TileEntity tileentity = EntityBee.this.H.c_(EntityBee.this.cF);
            if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
               if (!tileentitybeehive.f()) {
                  return true;
               }

               EntityBee.this.cF = null;
            }
         }

         return false;
      }

      @Override
      public boolean i() {
         return false;
      }

      @Override
      public void c() {
         TileEntity tileentity = EntityBee.this.H.c_(EntityBee.this.cF);
         if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
            tileentitybeehive.a(EntityBee.this, EntityBee.this.gc());
         }
      }
   }

   @VisibleForDebug
   public class e extends EntityBee.a {
      public static final int b = 600;
      int d = EntityBee.this.H.z.a(10);
      private static final int e = 3;
      final List<BlockPosition> f = Lists.newArrayList();
      @Nullable
      private PathEntity g;
      private static final int h = 60;
      private int i;

      e() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean h() {
         return EntityBee.this.cF != null
            && !EntityBee.this.fG()
            && EntityBee.this.gi()
            && !this.d(EntityBee.this.cF)
            && EntityBee.this.H.a_(EntityBee.this.cF).a(TagsBlock.aD);
      }

      @Override
      public boolean i() {
         return this.h();
      }

      @Override
      public void c() {
         this.d = 0;
         this.i = 0;
         super.c();
      }

      @Override
      public void d() {
         this.d = 0;
         this.i = 0;
         EntityBee.this.bM.n();
         EntityBee.this.bM.g();
      }

      @Override
      public void e() {
         if (EntityBee.this.cF != null) {
            ++this.d;
            if (this.d > this.a(600)) {
               this.l();
            } else if (!EntityBee.this.bM.m()) {
               if (!EntityBee.this.b(EntityBee.this.cF, 16)) {
                  if (EntityBee.this.j(EntityBee.this.cF)) {
                     this.m();
                  } else {
                     EntityBee.this.h(EntityBee.this.cF);
                  }
               } else {
                  boolean flag = this.a(EntityBee.this.cF);
                  if (!flag) {
                     this.l();
                  } else if (this.g != null && EntityBee.this.bM.j().a(this.g)) {
                     ++this.i;
                     if (this.i > 60) {
                        this.m();
                        this.i = 0;
                     }
                  } else {
                     this.g = EntityBee.this.bM.j();
                  }
               }
            }
         }
      }

      private boolean a(BlockPosition blockposition) {
         EntityBee.this.bM.a(10.0F);
         EntityBee.this.bM.a((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), 1.0);
         return EntityBee.this.bM.j() != null && EntityBee.this.bM.j().j();
      }

      boolean b(BlockPosition blockposition) {
         return this.f.contains(blockposition);
      }

      private void c(BlockPosition blockposition) {
         this.f.add(blockposition);

         while(this.f.size() > 3) {
            this.f.remove(0);
         }
      }

      void k() {
         this.f.clear();
      }

      private void l() {
         if (EntityBee.this.cF != null) {
            this.c(EntityBee.this.cF);
         }

         this.m();
      }

      private void m() {
         EntityBee.this.cF = null;
         EntityBee.this.cB = 200;
      }

      private boolean d(BlockPosition blockposition) {
         if (EntityBee.this.b(blockposition, 2)) {
            return true;
         } else {
            PathEntity pathentity = EntityBee.this.bM.j();
            return pathentity != null && pathentity.m().equals(blockposition) && pathentity.j() && pathentity.c();
         }
      }
   }

   public class f extends EntityBee.a {
      private static final int c = 600;
      int d = EntityBee.this.H.z.a(10);

      f() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean h() {
         return EntityBee.this.cE != null
            && !EntityBee.this.fG()
            && this.k()
            && EntityBee.this.k(EntityBee.this.cE)
            && !EntityBee.this.b(EntityBee.this.cE, 2);
      }

      @Override
      public boolean i() {
         return this.h();
      }

      @Override
      public void c() {
         this.d = 0;
         super.c();
      }

      @Override
      public void d() {
         this.d = 0;
         EntityBee.this.bM.n();
         EntityBee.this.bM.g();
      }

      @Override
      public void e() {
         if (EntityBee.this.cE != null) {
            ++this.d;
            if (this.d > this.a(600)) {
               EntityBee.this.cE = null;
            } else if (!EntityBee.this.bM.m()) {
               if (EntityBee.this.j(EntityBee.this.cE)) {
                  EntityBee.this.cE = null;
               } else {
                  EntityBee.this.h(EntityBee.this.cE);
               }
            }
         }
      }

      private boolean k() {
         return EntityBee.this.cx > 2400;
      }
   }

   private class g extends EntityBee.a {
      static final int b = 30;

      g() {
      }

      @Override
      public boolean h() {
         return EntityBee.this.gl() >= 10 ? false : (EntityBee.this.af.i() < 0.3F ? false : EntityBee.this.gc() && EntityBee.this.go());
      }

      @Override
      public boolean i() {
         return this.h();
      }

      @Override
      public void e() {
         if (EntityBee.this.af.a(this.a(30)) == 0) {
            for(int i = 1; i <= 2; ++i) {
               BlockPosition blockposition = EntityBee.this.dg().c(i);
               IBlockData iblockdata = EntityBee.this.H.a_(blockposition);
               Block block = iblockdata.b();
               boolean flag = false;
               BlockStateInteger blockstateinteger = null;
               if (iblockdata.a(TagsBlock.aF)) {
                  if (block instanceof BlockCrops blockcrops) {
                     if (!blockcrops.h(iblockdata)) {
                        flag = true;
                        blockstateinteger = blockcrops.b();
                     }
                  } else if (block instanceof BlockStem) {
                     int j = iblockdata.c(BlockStem.b);
                     if (j < 7) {
                        flag = true;
                        blockstateinteger = BlockStem.b;
                     }
                  } else if (iblockdata.a(Blocks.oe)) {
                     int j = iblockdata.c(BlockSweetBerryBush.b);
                     if (j < 3) {
                        flag = true;
                        blockstateinteger = BlockSweetBerryBush.b;
                     }
                  } else if (iblockdata.a(Blocks.rp) || iblockdata.a(Blocks.rq)) {
                     ((IBlockFragilePlantElement)iblockdata.b()).a((WorldServer)EntityBee.this.H, EntityBee.this.af, blockposition, iblockdata);
                  }

                  if (flag
                     && !CraftEventFactory.callEntityChangeBlockEvent(
                           EntityBee.this, blockposition, iblockdata.a(blockstateinteger, Integer.valueOf(iblockdata.c(blockstateinteger) + 1))
                        )
                        .isCancelled()) {
                     EntityBee.this.H.c(2005, blockposition, 0);
                     EntityBee.this.H.b(blockposition, iblockdata.a(blockstateinteger, Integer.valueOf(iblockdata.c(blockstateinteger) + 1)));
                     EntityBee.this.gn();
                  }
               }
            }
         }
      }
   }

   private class h extends PathfinderGoalHurtByTarget {
      h(EntityBee entitybee) {
         super(entitybee);
      }

      @Override
      public boolean b() {
         return EntityBee.this.R_() && super.b();
      }

      @Override
      protected void a(EntityInsentient entityinsentient, EntityLiving entityliving) {
         if (entityinsentient instanceof EntityBee && this.e.B(entityliving)) {
            entityinsentient.setTarget(entityliving, TargetReason.TARGET_ATTACKED_ENTITY, true);
         }
      }
   }

   private class i extends EntityBee.a {
      i() {
      }

      @Override
      public boolean h() {
         return EntityBee.this.cB == 0 && !EntityBee.this.fZ() && EntityBee.this.gi();
      }

      @Override
      public boolean i() {
         return false;
      }

      @Override
      public void c() {
         EntityBee.this.cB = 200;
         List<BlockPosition> list = this.k();
         if (!list.isEmpty()) {
            Iterator iterator = list.iterator();

            BlockPosition blockposition;
            do {
               if (!iterator.hasNext()) {
                  EntityBee.this.cH.k();
                  EntityBee.this.cF = list.get(0);
                  return;
               }

               blockposition = (BlockPosition)iterator.next();
            } while(EntityBee.this.cH.b(blockposition));

            EntityBee.this.cF = blockposition;
         }
      }

      private List<BlockPosition> k() {
         BlockPosition blockposition = EntityBee.this.dg();
         VillagePlace villageplace = ((WorldServer)EntityBee.this.H).w();
         Stream<VillagePlaceRecord> stream = villageplace.c(holder -> holder.a(PoiTypeTags.c), blockposition, 20, VillagePlace.Occupancy.c);
         return stream.map(VillagePlaceRecord::f)
            .filter(EntityBee::access$3)
            .sorted(Comparator.comparingDouble(blockposition1 -> blockposition1.j(blockposition)))
            .collect(Collectors.toList());
      }
   }

   private class j extends ControllerLook {
      j(EntityInsentient entityinsentient) {
         super(entityinsentient);
      }

      @Override
      public void a() {
         if (!EntityBee.this.R_()) {
            super.a();
         }
      }

      @Override
      protected boolean c() {
         return !EntityBee.this.cG.l();
      }
   }

   private class k extends EntityBee.a {
      private static final int c = 400;
      private static final int d = 20;
      private static final int e = 60;
      private final Predicate<IBlockData> f = iblockdata -> iblockdata.b(BlockProperties.C) && iblockdata.c(BlockProperties.C)
            ? false
            : (iblockdata.a(TagsBlock.T) ? (iblockdata.a(Blocks.iC) ? iblockdata.c(BlockTallPlant.a) == BlockPropertyDoubleBlockHalf.a : true) : false);
      private static final double g = 0.1;
      private static final int h = 25;
      private static final float i = 0.35F;
      private static final float j = 0.6F;
      private static final float k = 0.33333334F;
      private int l;
      private int m;
      private boolean n;
      @Nullable
      private Vec3D o;
      private int p;
      private static final int q = 600;

      k() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean h() {
         if (EntityBee.this.cD > 0) {
            return false;
         } else if (EntityBee.this.gc()) {
            return false;
         } else if (EntityBee.this.H.Y()) {
            return false;
         } else {
            Optional<BlockPosition> optional = this.p();
            if (optional.isPresent()) {
               EntityBee.this.cE = optional.get();
               EntityBee.this.bM.a((double)EntityBee.this.cE.u() + 0.5, (double)EntityBee.this.cE.v() + 0.5, (double)EntityBee.this.cE.w() + 0.5, 1.2F);
               return true;
            } else {
               EntityBee.this.cD = MathHelper.a(EntityBee.this.af, 20, 60);
               return false;
            }
         }
      }

      @Override
      public boolean i() {
         if (!this.n) {
            return false;
         } else if (!EntityBee.this.r()) {
            return false;
         } else if (EntityBee.this.H.Y()) {
            return false;
         } else if (this.k()) {
            return EntityBee.this.af.i() < 0.2F;
         } else if (EntityBee.this.ag % 20 == 0 && !EntityBee.this.k(EntityBee.this.cE)) {
            EntityBee.this.cE = null;
            return false;
         } else {
            return true;
         }
      }

      private boolean k() {
         return this.l > 400;
      }

      boolean l() {
         return this.n;
      }

      void m() {
         this.n = false;
      }

      @Override
      public void c() {
         this.l = 0;
         this.p = 0;
         this.m = 0;
         this.n = true;
         EntityBee.this.fY();
      }

      @Override
      public void d() {
         if (this.k()) {
            EntityBee.this.w(true);
         }

         this.n = false;
         EntityBee.this.bM.n();
         EntityBee.this.cD = 200;
      }

      @Override
      public boolean J_() {
         return true;
      }

      @Override
      public void e() {
         ++this.p;
         if (this.p > 600) {
            EntityBee.this.cE = null;
         } else {
            Vec3D vec3d = Vec3D.c(EntityBee.this.cE).b(0.0, 0.6F, 0.0);
            if (vec3d.f(EntityBee.this.de()) > 1.0) {
               this.o = vec3d;
               this.n();
            } else {
               if (this.o == null) {
                  this.o = vec3d;
               }

               boolean flag = EntityBee.this.de().f(this.o) <= 0.1;
               boolean flag1 = true;
               if (!flag && this.p > 600) {
                  EntityBee.this.cE = null;
               } else {
                  if (flag) {
                     boolean flag2 = EntityBee.this.af.a(25) == 0;
                     if (flag2) {
                        this.o = new Vec3D(vec3d.a() + (double)this.o(), vec3d.b(), vec3d.c() + (double)this.o());
                        EntityBee.this.bM.n();
                     } else {
                        flag1 = false;
                     }

                     EntityBee.this.C().a(vec3d.a(), vec3d.b(), vec3d.c());
                  }

                  if (flag1) {
                     this.n();
                  }

                  ++this.l;
                  if (EntityBee.this.af.i() < 0.05F && this.l > this.m + 60) {
                     this.m = this.l;
                     EntityBee.this.a(SoundEffects.bD, 1.0F, 1.0F);
                  }
               }
            }
         }
      }

      private void n() {
         EntityBee.this.D().a(this.o.a(), this.o.b(), this.o.c(), 0.35F);
      }

      private float o() {
         return (EntityBee.this.af.i() * 2.0F - 1.0F) * 0.33333334F;
      }

      private Optional<BlockPosition> p() {
         return this.a(this.f, 5.0);
      }

      private Optional<BlockPosition> a(Predicate<IBlockData> predicate, double d0) {
         BlockPosition blockposition = EntityBee.this.dg();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int i = 0; (double)i <= d0; i = i > 0 ? -i : 1 - i) {
            for(int j = 0; (double)j < d0; ++j) {
               for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                  for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                     blockposition_mutableblockposition.a(blockposition, k, i - 1, l);
                     if (blockposition.a(blockposition_mutableblockposition, d0) && predicate.test(EntityBee.this.H.a_(blockposition_mutableblockposition))) {
                        return Optional.of(blockposition_mutableblockposition);
                     }
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   private class l extends PathfinderGoal {
      private static final int b = 22;

      l() {
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         return EntityBee.this.bM.l() && EntityBee.this.af.a(10) == 0;
      }

      @Override
      public boolean b() {
         return EntityBee.this.bM.m();
      }

      @Override
      public void c() {
         Vec3D vec3d = this.h();
         if (vec3d != null) {
            EntityBee.this.bM.a(EntityBee.this.bM.a(BlockPosition.a(vec3d), 1), 1.0);
         }
      }

      @Nullable
      private Vec3D h() {
         Vec3D vec3d;
         if (EntityBee.this.go() && !EntityBee.this.b(EntityBee.this.cF, 22)) {
            Vec3D vec3d1 = Vec3D.b(EntityBee.this.cF);
            vec3d = vec3d1.d(EntityBee.this.de()).d();
         } else {
            vec3d = EntityBee.this.j(0.0F);
         }

         boolean flag = true;
         Vec3D vec3d2 = HoverRandomPos.a(EntityBee.this, 8, 7, vec3d.c, vec3d.e, (float) (Math.PI / 2), 3, 1);
         return vec3d2 != null ? vec3d2 : AirAndWaterRandomPos.a(EntityBee.this, 8, 4, -2, vec3d.c, vec3d.e, (float) (Math.PI / 2));
      }
   }
}
