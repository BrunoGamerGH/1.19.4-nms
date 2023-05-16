package net.minecraft.world.entity.animal;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalGotoTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockTurtleEgg;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityTurtle extends EntityAnimal {
   private static final DataWatcherObject<BlockPosition> bV = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.n);
   private static final DataWatcherObject<Boolean> bW = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> bX = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<BlockPosition> bY = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.n);
   private static final DataWatcherObject<Boolean> bZ = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> ca = DataWatcher.a(EntityTurtle.class, DataWatcherRegistry.k);
   public static final RecipeItemStack bS = RecipeItemStack.a(Blocks.bv.k());
   int cb;
   public static final Predicate<EntityLiving> bT = entityliving -> entityliving.y_() && !entityliving.aT();

   public EntityTurtle(EntityTypes<? extends EntityTurtle> entitytypes, World world) {
      super(entitytypes, world);
      this.a(PathType.j, 0.0F);
      this.a(PathType.t, -1.0F);
      this.a(PathType.s, -1.0F);
      this.a(PathType.r, -1.0F);
      this.bK = new EntityTurtle.e(this);
      this.v(1.0F);
   }

   public void g(BlockPosition blockposition) {
      this.am.b(bV, blockposition);
   }

   BlockPosition fS() {
      return this.am.a(bV);
   }

   void h(BlockPosition blockposition) {
      this.am.b(bY, blockposition);
   }

   BlockPosition fY() {
      return this.am.a(bY);
   }

   public boolean q() {
      return this.am.a(bW);
   }

   void w(boolean flag) {
      this.am.b(bW, flag);
   }

   public boolean r() {
      return this.am.a(bX);
   }

   void x(boolean flag) {
      this.cb = flag ? 1 : 0;
      this.am.b(bX, flag);
   }

   boolean fZ() {
      return this.am.a(bZ);
   }

   void y(boolean flag) {
      this.am.b(bZ, flag);
   }

   boolean ga() {
      return this.am.a(ca);
   }

   void z(boolean flag) {
      this.am.b(ca, flag);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bV, BlockPosition.b);
      this.am.a(bW, false);
      this.am.a(bY, BlockPosition.b);
      this.am.a(bZ, false);
      this.am.a(ca, false);
      this.am.a(bX, false);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("HomePosX", this.fS().u());
      nbttagcompound.a("HomePosY", this.fS().v());
      nbttagcompound.a("HomePosZ", this.fS().w());
      nbttagcompound.a("HasEgg", this.q());
      nbttagcompound.a("TravelPosX", this.fY().u());
      nbttagcompound.a("TravelPosY", this.fY().v());
      nbttagcompound.a("TravelPosZ", this.fY().w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      int i = nbttagcompound.h("HomePosX");
      int j = nbttagcompound.h("HomePosY");
      int k = nbttagcompound.h("HomePosZ");
      this.g(new BlockPosition(i, j, k));
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("HasEgg"));
      int l = nbttagcompound.h("TravelPosX");
      int i1 = nbttagcompound.h("TravelPosY");
      int j1 = nbttagcompound.h("TravelPosZ");
      this.h(new BlockPosition(l, i1, j1));
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
      this.g(this.dg());
      this.h(BlockPosition.b);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   public static boolean c(
      EntityTypes<EntityTurtle> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return blockposition.v() < generatoraccess.m_() + 4 && BlockTurtleEgg.a(generatoraccess, blockposition) && a(generatoraccess, blockposition);
   }

   @Override
   protected void x() {
      this.bN.a(0, new EntityTurtle.f(this, 1.2));
      this.bN.a(1, new EntityTurtle.a(this, 1.0));
      this.bN.a(1, new EntityTurtle.d(this, 1.0));
      this.bN.a(2, new PathfinderGoalTempt(this, 1.1, bS, false));
      this.bN.a(3, new EntityTurtle.c(this, 1.0));
      this.bN.a(4, new EntityTurtle.b(this, 1.0));
      this.bN.a(7, new EntityTurtle.i(this, 1.0));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(9, new EntityTurtle.h(this, 1.0, 100));
   }

   public static AttributeProvider.Builder w() {
      return EntityInsentient.y().a(GenericAttributes.a, 30.0).a(GenericAttributes.d, 0.25);
   }

   @Override
   public boolean cv() {
      return false;
   }

   @Override
   public boolean dK() {
      return true;
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.e;
   }

   @Override
   public int K() {
      return 200;
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return !this.aT() && this.N && !this.y_() ? SoundEffects.xJ : super.s();
   }

   @Override
   protected void i(float f) {
      super.i(f * 1.5F);
   }

   @Override
   protected SoundEffect aI() {
      return SoundEffects.xU;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return this.y_() ? SoundEffects.xQ : SoundEffects.xP;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return this.y_() ? SoundEffects.xL : SoundEffects.xK;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      SoundEffect soundeffect = this.y_() ? SoundEffects.xT : SoundEffects.xS;
      this.a(soundeffect, 0.15F, 1.0F);
   }

   @Override
   public boolean fT() {
      return super.fT() && !this.q();
   }

   @Override
   protected float aH() {
      return this.Y + 0.15F;
   }

   @Override
   public float dS() {
      return this.y_() ? 0.3F : 1.0F;
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new EntityTurtle.g(this, world);
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.bd.a((World)worldserver);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return itemstack.a(Blocks.bv.k());
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return !this.fZ() && iworldreader.b_(blockposition).a(TagsFluid.a)
         ? 10.0F
         : (BlockTurtleEgg.a(iworldreader, blockposition) ? 10.0F : iworldreader.y(blockposition));
   }

   @Override
   public void b_() {
      super.b_();
      if (this.bq() && this.r() && this.cb >= 1 && this.cb % 5 == 0) {
         BlockPosition blockposition = this.dg();
         if (BlockTurtleEgg.a(this.H, blockposition)) {
            this.H.c(2001, blockposition, Block.i(this.H.a_(blockposition.d())));
         }
      }
   }

   @Override
   protected void m() {
      super.m();
      if (!this.y_() && this.H.W().b(GameRules.f)) {
         this.forceDrops = true;
         this.a(Items.nz, 1);
         this.forceDrops = false;
      }
   }

   @Override
   public void h(Vec3D vec3d) {
      if (this.cT() && this.aT()) {
         this.a(0.1F, vec3d);
         this.a(EnumMoveType.a, this.dj());
         this.f(this.dj().a(0.9));
         if (this.P_() == null && (!this.fZ() || !this.fS().a(this.de(), 20.0))) {
            this.f(this.dj().b(0.0, -0.005, 0.0));
         }
      } else {
         super.h(vec3d);
      }
   }

   @Override
   public boolean a(EntityHuman entityhuman) {
      return false;
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      CraftEventFactory.entityDamage = entitylightning;
      this.a(this.dG().b(), Float.MAX_VALUE);
      CraftEventFactory.entityDamage = null;
   }

   private static class a extends PathfinderGoalBreed {
      private final EntityTurtle d;

      a(EntityTurtle entityturtle, double d0) {
         super(entityturtle, d0);
         this.d = entityturtle;
      }

      @Override
      public boolean a() {
         return super.a() && !this.d.q();
      }

      @Override
      protected void g() {
         EntityPlayer entityplayer = this.a.fV();
         if (entityplayer == null && this.c.fV() != null) {
            entityplayer = this.c.fV();
         }

         if (entityplayer != null) {
            entityplayer.a(StatisticList.P);
            CriterionTriggers.o.a(entityplayer, this.a, this.c, null);
         }

         this.d.w(true);
         this.a.c_(6000);
         this.c.c_(6000);
         this.a.fX();
         this.c.fX();
         RandomSource randomsource = this.a.dZ();
         if (this.b.W().b(GameRules.f)) {
            this.b.b(new EntityExperienceOrb(this.b, this.a.dl(), this.a.dn(), this.a.dr(), randomsource.a(7) + 1));
         }
      }
   }

   private static class b extends PathfinderGoal {
      private final EntityTurtle a;
      private final double b;
      private boolean c;
      private int d;
      private static final int e = 600;

      b(EntityTurtle entityturtle, double d0) {
         this.a = entityturtle;
         this.b = d0;
      }

      @Override
      public boolean a() {
         return this.a.y_() ? false : (this.a.q() ? true : (this.a.dZ().a(b(700)) != 0 ? false : !this.a.fS().a(this.a.de(), 64.0)));
      }

      @Override
      public void c() {
         this.a.y(true);
         this.c = false;
         this.d = 0;
      }

      @Override
      public void d() {
         this.a.y(false);
      }

      @Override
      public boolean b() {
         return !this.a.fS().a(this.a.de(), 7.0) && !this.c && this.d <= this.a(600);
      }

      @Override
      public void e() {
         BlockPosition blockposition = this.a.fS();
         boolean flag = blockposition.a(this.a.de(), 16.0);
         if (flag) {
            ++this.d;
         }

         if (this.a.G().l()) {
            Vec3D vec3d = Vec3D.c(blockposition);
            Vec3D vec3d1 = DefaultRandomPos.a(this.a, 16, 3, vec3d, (float) (Math.PI / 10));
            if (vec3d1 == null) {
               vec3d1 = DefaultRandomPos.a(this.a, 8, 7, vec3d, (float) (Math.PI / 2));
            }

            if (vec3d1 != null && !flag && !this.a.H.a_(BlockPosition.a(vec3d1)).a(Blocks.G)) {
               vec3d1 = DefaultRandomPos.a(this.a, 16, 5, vec3d, (float) (Math.PI / 2));
            }

            if (vec3d1 == null) {
               this.c = true;
               return;
            }

            this.a.G().a(vec3d1.c, vec3d1.d, vec3d1.e, this.b);
         }
      }
   }

   private static class c extends PathfinderGoalGotoTarget {
      private static final int g = 1200;
      private final EntityTurtle h;

      c(EntityTurtle entityturtle, double d0) {
         super(entityturtle, entityturtle.y_() ? 2.0 : d0, 24);
         this.h = entityturtle;
         this.f = -1;
      }

      @Override
      public boolean b() {
         return !this.h.aT() && this.d <= 1200 && this.a(this.h.H, this.e);
      }

      @Override
      public boolean a() {
         return this.h.y_() && !this.h.aT() ? super.a() : (!this.h.fZ() && !this.h.aT() && !this.h.q() ? super.a() : false);
      }

      @Override
      public boolean l() {
         return this.d % 160 == 0;
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         return iworldreader.a_(blockposition).a(Blocks.G);
      }
   }

   private static class d extends PathfinderGoalGotoTarget {
      private final EntityTurtle g;

      d(EntityTurtle entityturtle, double d0) {
         super(entityturtle, d0, 16);
         this.g = entityturtle;
      }

      @Override
      public boolean a() {
         return this.g.q() && this.g.fS().a(this.g.de(), 9.0) ? super.a() : false;
      }

      @Override
      public boolean b() {
         return super.b() && this.g.q() && this.g.fS().a(this.g.de(), 9.0);
      }

      @Override
      public void e() {
         super.e();
         BlockPosition blockposition = this.g.dg();
         if (!this.g.aT() && this.m()) {
            if (this.g.cb < 1) {
               this.g.x(true);
            } else if (this.g.cb > this.a(200)) {
               World world = this.g.H;
               if (!CraftEventFactory.callEntityChangeBlockEvent(this.g, this.e.c(), Blocks.mc.o().a(BlockTurtleEgg.e, Integer.valueOf(this.g.af.a(4) + 1)))
                  .isCancelled()) {
                  world.a(null, blockposition, SoundEffects.xR, SoundCategory.e, 0.3F, 0.9F + world.z.i() * 0.2F);
                  BlockPosition blockposition1 = this.e.c();
                  IBlockData iblockdata = Blocks.mc.o().a(BlockTurtleEgg.e, Integer.valueOf(this.g.af.a(4) + 1));
                  world.a(blockposition1, iblockdata, 3);
                  world.a(GameEvent.i, blockposition1, GameEvent.a.a(this.g, iblockdata));
               }

               this.g.w(false);
               this.g.x(false);
               this.g.r(600);
            }

            if (this.g.r()) {
               ++this.g.cb;
            }
         }
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         return !iworldreader.w(blockposition.c()) ? false : BlockTurtleEgg.b(iworldreader, blockposition);
      }
   }

   private static class e extends ControllerMove {
      private final EntityTurtle l;

      e(EntityTurtle entityturtle) {
         super(entityturtle);
         this.l = entityturtle;
      }

      private void g() {
         if (this.l.aT()) {
            this.l.f(this.l.dj().b(0.0, 0.005, 0.0));
            if (!this.l.fS().a(this.l.de(), 16.0)) {
               this.l.h(Math.max(this.l.eW() / 2.0F, 0.08F));
            }

            if (this.l.y_()) {
               this.l.h(Math.max(this.l.eW() / 3.0F, 0.06F));
            }
         } else if (this.l.N) {
            this.l.h(Math.max(this.l.eW() / 2.0F, 0.06F));
         }
      }

      @Override
      public void a() {
         this.g();
         if (this.k == ControllerMove.Operation.b && !this.l.G().l()) {
            double d0 = this.e - this.l.dl();
            double d1 = this.f - this.l.dn();
            double d2 = this.g - this.l.dr();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            if (d3 < 1.0E-5F) {
               this.d.h(0.0F);
            } else {
               d1 /= d3;
               float f = (float)(MathHelper.d(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
               this.l.f(this.a(this.l.dw(), f, 90.0F));
               this.l.aT = this.l.dw();
               float f1 = (float)(this.h * this.l.b(GenericAttributes.d));
               this.l.h(MathHelper.i(0.125F, this.l.eW(), f1));
               this.l.f(this.l.dj().b(0.0, (double)this.l.eW() * d1 * 0.1, 0.0));
            }
         } else {
            this.l.h(0.0F);
         }
      }
   }

   private static class f extends PathfinderGoalPanic {
      f(EntityTurtle entityturtle, double d0) {
         super(entityturtle, d0);
      }

      @Override
      public boolean a() {
         if (!this.h()) {
            return false;
         } else {
            BlockPosition blockposition = this.a(this.b.H, this.b, 7);
            if (blockposition != null) {
               this.d = (double)blockposition.u();
               this.e = (double)blockposition.v();
               this.f = (double)blockposition.w();
               return true;
            } else {
               return this.i();
            }
         }
      }
   }

   private static class g extends AmphibiousPathNavigation {
      g(EntityTurtle entityturtle, World world) {
         super(entityturtle, world);
      }

      @Override
      public boolean a(BlockPosition blockposition) {
         EntityInsentient entityinsentient = this.a;
         if (entityinsentient instanceof EntityTurtle entityturtle && entityturtle.ga()) {
            return this.b.a_(blockposition).a(Blocks.G);
         }

         return !this.b.a_(blockposition.d()).h();
      }
   }

   private static class h extends PathfinderGoalRandomStroll {
      private final EntityTurtle i;

      h(EntityTurtle entityturtle, double d0, int i) {
         super(entityturtle, d0, i);
         this.i = entityturtle;
      }

      @Override
      public boolean a() {
         return !this.b.aT() && !this.i.fZ() && !this.i.q() ? super.a() : false;
      }
   }

   private static class i extends PathfinderGoal {
      private final EntityTurtle a;
      private final double b;
      private boolean c;

      i(EntityTurtle entityturtle, double d0) {
         this.a = entityturtle;
         this.b = d0;
      }

      @Override
      public boolean a() {
         return !this.a.fZ() && !this.a.q() && this.a.aT();
      }

      @Override
      public void c() {
         boolean flag = true;
         boolean flag1 = true;
         RandomSource randomsource = this.a.af;
         int i = randomsource.a(1025) - 512;
         int j = randomsource.a(9) - 4;
         int k = randomsource.a(1025) - 512;
         if ((double)j + this.a.dn() > (double)(this.a.H.m_() - 1)) {
            j = 0;
         }

         BlockPosition blockposition = BlockPosition.a((double)i + this.a.dl(), (double)j + this.a.dn(), (double)k + this.a.dr());
         this.a.h(blockposition);
         this.a.z(true);
         this.c = false;
      }

      @Override
      public void e() {
         if (this.a.G().l()) {
            Vec3D vec3d = Vec3D.c(this.a.fY());
            Vec3D vec3d1 = DefaultRandomPos.a(this.a, 16, 3, vec3d, (float) (Math.PI / 10));
            if (vec3d1 == null) {
               vec3d1 = DefaultRandomPos.a(this.a, 8, 7, vec3d, (float) (Math.PI / 2));
            }

            if (vec3d1 != null) {
               int i = MathHelper.a(vec3d1.c);
               int j = MathHelper.a(vec3d1.e);
               boolean flag = true;
               if (!this.a.H.b(i - 34, j - 34, i + 34, j + 34)) {
                  vec3d1 = null;
               }
            }

            if (vec3d1 == null) {
               this.c = true;
               return;
            }

            this.a.G().a(vec3d1.c, vec3d1.d, vec3d1.e, this.b);
         }
      }

      @Override
      public boolean b() {
         return !this.a.G().l() && !this.c && !this.a.fZ() && !this.a.fW() && !this.a.q();
      }

      @Override
      public void d() {
         this.a.z(false);
         super.d();
      }
   }
}
