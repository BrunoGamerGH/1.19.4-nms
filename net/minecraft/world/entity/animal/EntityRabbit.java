package net.minecraft.world.entity.animal;

import com.mojang.serialization.Codec;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyDamageScaler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.control.ControllerJump;
import net.minecraft.world.entity.ai.control.ControllerMove;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalGotoTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockLightAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCarrots;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityRabbit extends EntityAnimal implements VariantHolder<EntityRabbit.Variant> {
   public static final double bS = 0.6;
   public static final double bT = 0.8;
   public static final double bV = 1.0;
   public static final double bW = 2.2;
   public static final double bX = 1.4;
   private static final DataWatcherObject<Integer> ca = DataWatcher.a(EntityRabbit.class, DataWatcherRegistry.b);
   private static final MinecraftKey cb = new MinecraftKey("killer_bunny");
   public static final int bY = 8;
   public static final int bZ = 8;
   private static final int cc = 40;
   private int cd;
   private int ce;
   private boolean cf;
   private int cg;
   int ch;

   public EntityRabbit(EntityTypes<? extends EntityRabbit> entitytypes, World world) {
      super(entitytypes, world);
      this.bL = new EntityRabbit.ControllerJumpRabbit(this);
      this.bK = new EntityRabbit.ControllerMoveRabbit(this);
      this.initializePathFinderGoals();
   }

   public void initializePathFinderGoals() {
      this.i(0.0);
   }

   @Override
   public void x() {
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(1, new ClimbOnTopOfPowderSnowGoal(this, this.H));
      this.bN.a(1, new EntityRabbit.PathfinderGoalRabbitPanic(this, 2.2));
      this.bN.a(2, new PathfinderGoalBreed(this, 0.8));
      this.bN.a(3, new PathfinderGoalTempt(this, 1.0, RecipeItemStack.a(Items.th, Items.tm, Blocks.bQ), false));
      this.bN.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget<>(this, EntityHuman.class, 8.0F, 2.2, 2.2));
      this.bN.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget<>(this, EntityWolf.class, 10.0F, 2.2, 2.2));
      this.bN.a(4, new EntityRabbit.PathfinderGoalRabbitAvoidTarget<>(this, EntityMonster.class, 4.0F, 2.2, 2.2));
      this.bN.a(5, new EntityRabbit.PathfinderGoalEatCarrots(this));
      this.bN.a(6, new PathfinderGoalRandomStrollLand(this, 0.6));
      this.bN.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
   }

   @Override
   protected float eQ() {
      if (!this.O && (!this.bK.b() || this.bK.e() <= this.dn() + 0.5)) {
         PathEntity pathentity = this.bM.j();
         if (pathentity != null && !pathentity.c()) {
            Vec3D vec3d = pathentity.a(this);
            if (vec3d.d > this.dn() + 0.5) {
               return 0.5F;
            }
         }

         return this.bK.c() <= 0.6 ? 0.2F : 0.3F;
      } else {
         return 0.5F;
      }
   }

   @Override
   protected void eS() {
      super.eS();
      double d0 = this.bK.c();
      if (d0 > 0.0) {
         double d1 = this.dj().i();
         if (d1 < 0.01) {
            this.a(0.1F, new Vec3D(0.0, 0.0, 1.0));
         }
      }

      if (!this.H.B) {
         this.H.a(this, (byte)1);
      }
   }

   public float C(float f) {
      return this.ce == 0 ? 0.0F : ((float)this.cd + f) / (float)this.ce;
   }

   public void i(double d0) {
      this.G().a(d0);
      this.bK.a(this.bK.d(), this.bK.e(), this.bK.f(), d0);
   }

   @Override
   public void r(boolean flag) {
      super.r(flag);
      if (flag) {
         this.a(this.w(), this.eN(), ((this.af.i() - this.af.i()) * 0.2F + 1.0F) * 0.8F);
      }
   }

   @Override
   public void q() {
      this.r(true);
      this.ce = 10;
      this.cd = 0;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(ca, EntityRabbit.Variant.a.j);
   }

   @Override
   public void U() {
      if (this.cg > 0) {
         --this.cg;
      }

      if (this.ch > 0) {
         this.ch -= this.af.a(3);
         if (this.ch < 0) {
            this.ch = 0;
         }
      }

      if (this.N) {
         if (!this.cf) {
            this.r(false);
            this.gb();
         }

         if (this.fS() == EntityRabbit.Variant.g && this.cg == 0) {
            EntityLiving entityliving = this.P_();
            if (entityliving != null && this.f((Entity)entityliving) < 16.0) {
               this.c(entityliving.dl(), entityliving.dr());
               this.bK.a(entityliving.dl(), entityliving.dn(), entityliving.dr(), this.bK.c());
               this.q();
               this.cf = true;
            }
         }

         EntityRabbit.ControllerJumpRabbit entityrabbit_controllerjumprabbit = (EntityRabbit.ControllerJumpRabbit)this.bL;
         if (!entityrabbit_controllerjumprabbit.c()) {
            if (this.bK.b() && this.cg == 0) {
               PathEntity pathentity = this.bM.j();
               Vec3D vec3d = new Vec3D(this.bK.d(), this.bK.e(), this.bK.f());
               if (pathentity != null && !pathentity.c()) {
                  vec3d = pathentity.a(this);
               }

               this.c(vec3d.c, vec3d.e);
               this.q();
            }
         } else if (!entityrabbit_controllerjumprabbit.d()) {
            this.fY();
         }
      }

      this.cf = this.N;
   }

   @Override
   public boolean be() {
      return false;
   }

   private void c(double d0, double d1) {
      this.f((float)(MathHelper.d(d1 - this.dr(), d0 - this.dl()) * 180.0F / (float)Math.PI) - 90.0F);
   }

   private void fY() {
      ((EntityRabbit.ControllerJumpRabbit)this.bL).a(true);
   }

   private void fZ() {
      ((EntityRabbit.ControllerJumpRabbit)this.bL).a(false);
   }

   private void ga() {
      if (this.bK.c() < 2.2) {
         this.cg = 10;
      } else {
         this.cg = 1;
      }
   }

   private void gb() {
      this.ga();
      this.fZ();
   }

   @Override
   public void b_() {
      super.b_();
      if (this.cd != this.ce) {
         ++this.cd;
      } else if (this.ce != 0) {
         this.cd = 0;
         this.ce = 0;
         this.r(false);
      }
   }

   public static AttributeProvider.Builder r() {
      return EntityInsentient.y().a(GenericAttributes.a, 3.0).a(GenericAttributes.d, 0.3F);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("RabbitType", this.fS().j);
      nbttagcompound.a("MoreCarrotTicks", this.ch);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.a(EntityRabbit.Variant.a(nbttagcompound.h("RabbitType")));
      this.ch = nbttagcompound.h("MoreCarrotTicks");
   }

   protected SoundEffect w() {
      return SoundEffects.sY;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.sU;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.sX;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.sW;
   }

   @Override
   public boolean z(Entity entity) {
      if (this.fS() == EntityRabbit.Variant.g) {
         this.a(SoundEffects.sV, 1.0F, (this.af.i() - this.af.i()) * 0.2F + 1.0F);
         return entity.a(this.dG().b((EntityLiving)this), 8.0F);
      } else {
         return entity.a(this.dG().b((EntityLiving)this), 3.0F);
      }
   }

   @Override
   public SoundCategory cX() {
      return this.fS() == EntityRabbit.Variant.g ? SoundCategory.f : SoundCategory.g;
   }

   private static boolean l(ItemStack itemstack) {
      return itemstack.a(Items.th) || itemstack.a(Items.tm) || itemstack.a(Blocks.bQ.k());
   }

   @Nullable
   public EntityRabbit b(WorldServer worldserver, EntityAgeable entityageable) {
      EntityRabbit entityrabbit = EntityTypes.aC.a((World)worldserver);
      if (entityrabbit != null) {
         EntityRabbit.Variant entityrabbit_variant;
         entityrabbit_variant = a(worldserver, this.dg());
         label16:
         if (this.af.a(20) != 0) {
            if (entityageable instanceof EntityRabbit entityrabbit1 && this.af.h()) {
               entityrabbit_variant = entityrabbit1.fS();
               break label16;
            }

            entityrabbit_variant = this.fS();
         }

         entityrabbit.a(entityrabbit_variant);
      }

      return entityrabbit;
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return l(itemstack);
   }

   public EntityRabbit.Variant fS() {
      return EntityRabbit.Variant.a(this.am.a(ca));
   }

   public void a(EntityRabbit.Variant entityrabbit_variant) {
      if (entityrabbit_variant == EntityRabbit.Variant.g) {
         this.a(GenericAttributes.i).a(8.0);
         this.bN.a(4, new EntityRabbit.PathfinderGoalKillerRabbitMeleeAttack(this));
         this.bO.a(1, new PathfinderGoalHurtByTarget(this).a());
         this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
         this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityWolf.class, true));
         if (!this.aa()) {
            this.b(IChatBaseComponent.c(SystemUtils.a("entity", cb)));
         }
      }

      this.am.b(ca, entityrabbit_variant.j);
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
      EntityRabbit.Variant entityrabbit_variant = a(worldaccess, this.dg());
      if (groupdataentity instanceof EntityRabbit.GroupDataRabbit) {
         entityrabbit_variant = ((EntityRabbit.GroupDataRabbit)groupdataentity).a;
      } else {
         groupdataentity = new EntityRabbit.GroupDataRabbit(entityrabbit_variant);
      }

      this.a(entityrabbit_variant);
      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   private static EntityRabbit.Variant a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      Holder<BiomeBase> holder = generatoraccess.v(blockposition);
      int i = generatoraccess.r_().a(100);
      return holder.a(BiomeTags.aj)
         ? (i < 80 ? EntityRabbit.Variant.b : EntityRabbit.Variant.d)
         : (holder.a(BiomeTags.ai) ? EntityRabbit.Variant.e : (i < 50 ? EntityRabbit.Variant.a : (i < 90 ? EntityRabbit.Variant.f : EntityRabbit.Variant.c)));
   }

   public static boolean c(
      EntityTypes<EntityRabbit> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return generatoraccess.a_(blockposition.d()).a(TagsBlock.bP) && a((IBlockLightAccess)generatoraccess, blockposition);
   }

   boolean gc() {
      return this.ch <= 0;
   }

   @Override
   public void b(byte b0) {
      if (b0 == 1) {
         this.bf();
         this.ce = 10;
         this.cd = 0;
      } else {
         super.b(b0);
      }
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.6F * this.cE()), (double)(this.dc() * 0.4F));
   }

   public static class ControllerJumpRabbit extends ControllerJump {
      private final EntityRabbit b;
      private boolean c;

      public ControllerJumpRabbit(EntityRabbit entityrabbit) {
         super(entityrabbit);
         this.b = entityrabbit;
      }

      public boolean c() {
         return this.a;
      }

      public boolean d() {
         return this.c;
      }

      public void a(boolean flag) {
         this.c = flag;
      }

      @Override
      public void b() {
         if (this.a) {
            this.b.q();
            this.a = false;
         }
      }
   }

   private static class ControllerMoveRabbit extends ControllerMove {
      private final EntityRabbit l;
      private double m;

      public ControllerMoveRabbit(EntityRabbit entityrabbit) {
         super(entityrabbit);
         this.l = entityrabbit;
      }

      @Override
      public void a() {
         if (this.l.N && !this.l.bi && !((EntityRabbit.ControllerJumpRabbit)this.l.bL).c()) {
            this.l.i(0.0);
         } else if (this.b()) {
            this.l.i(this.m);
         }

         super.a();
      }

      @Override
      public void a(double d0, double d1, double d2, double d3) {
         if (this.l.aT()) {
            d3 = 1.5;
         }

         super.a(d0, d1, d2, d3);
         if (d3 > 0.0) {
            this.m = d3;
         }
      }
   }

   public static class GroupDataRabbit extends EntityAgeable.a {
      public final EntityRabbit.Variant a;

      public GroupDataRabbit(EntityRabbit.Variant entityrabbit_variant) {
         super(1.0F);
         this.a = entityrabbit_variant;
      }
   }

   private static class PathfinderGoalEatCarrots extends PathfinderGoalGotoTarget {
      private final EntityRabbit g;
      private boolean h;
      private boolean i;

      public PathfinderGoalEatCarrots(EntityRabbit entityrabbit) {
         super(entityrabbit, 0.7F, 16);
         this.g = entityrabbit;
      }

      @Override
      public boolean a() {
         if (this.c <= 0) {
            if (!this.g.H.W().b(GameRules.c)) {
               return false;
            }

            this.i = false;
            this.h = this.g.gc();
         }

         return super.a();
      }

      @Override
      public boolean b() {
         return this.i && super.b();
      }

      @Override
      public void e() {
         super.e();
         this.g.C().a((double)this.e.u() + 0.5, (double)(this.e.v() + 1), (double)this.e.w() + 0.5, 10.0F, (float)this.g.V());
         if (this.m()) {
            World world = this.g.H;
            BlockPosition blockposition = this.e.c();
            IBlockData iblockdata = world.a_(blockposition);
            Block block = iblockdata.b();
            if (this.i && block instanceof BlockCarrots) {
               int i = iblockdata.c(BlockCarrots.d);
               if (i == 0) {
                  if (CraftEventFactory.callEntityChangeBlockEvent(this.g, blockposition, Blocks.a.o()).isCancelled()) {
                     return;
                  }

                  world.a(blockposition, Blocks.a.o(), 2);
                  world.a(blockposition, true, this.g);
               } else {
                  if (CraftEventFactory.callEntityChangeBlockEvent(this.g, blockposition, iblockdata.a(BlockCarrots.d, Integer.valueOf(i - 1))).isCancelled()) {
                     return;
                  }

                  world.a(blockposition, iblockdata.a(BlockCarrots.d, Integer.valueOf(i - 1)), 2);
                  world.c(2001, blockposition, Block.i(iblockdata));
               }

               this.g.ch = 40;
            }

            this.i = false;
            this.c = 10;
         }
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         IBlockData iblockdata = iworldreader.a_(blockposition);
         if (iblockdata.a(Blocks.cB) && this.h && !this.i) {
            iblockdata = iworldreader.a_(blockposition.c());
            if (iblockdata.b() instanceof BlockCarrots && ((BlockCarrots)iblockdata.b()).h(iblockdata)) {
               this.i = true;
               return true;
            }
         }

         return false;
      }
   }

   private static class PathfinderGoalKillerRabbitMeleeAttack extends PathfinderGoalMeleeAttack {
      public PathfinderGoalKillerRabbitMeleeAttack(EntityRabbit entityrabbit) {
         super(entityrabbit, 1.4, true);
      }

      @Override
      protected double a(EntityLiving entityliving) {
         return (double)(4.0F + entityliving.dc());
      }
   }

   private static class PathfinderGoalRabbitAvoidTarget<T extends EntityLiving> extends PathfinderGoalAvoidTarget<T> {
      private final EntityRabbit i;

      public PathfinderGoalRabbitAvoidTarget(EntityRabbit entityrabbit, Class<T> oclass, float f, double d0, double d1) {
         super(entityrabbit, oclass, f, d0, d1);
         this.i = entityrabbit;
      }

      @Override
      public boolean a() {
         return this.i.fS() != EntityRabbit.Variant.g && super.a();
      }
   }

   private static class PathfinderGoalRabbitPanic extends PathfinderGoalPanic {
      private final EntityRabbit h;

      public PathfinderGoalRabbitPanic(EntityRabbit entityrabbit, double d0) {
         super(entityrabbit, d0);
         this.h = entityrabbit;
      }

      @Override
      public void e() {
         super.e();
         this.h.i(this.c);
      }
   }

   public static enum Variant implements INamable {
      a(0, "brown"),
      b(1, "white"),
      c(2, "black"),
      d(3, "white_splotched"),
      e(4, "gold"),
      f(5, "salt"),
      g(99, "evil");

      private static final IntFunction<EntityRabbit.Variant> i = ByIdMap.a(EntityRabbit.Variant::a, values(), a);
      public static final Codec<EntityRabbit.Variant> h = INamable.a(EntityRabbit.Variant::values);
      final int j;
      private final String k;

      private Variant(int i, String s) {
         this.j = i;
         this.k = s;
      }

      @Override
      public String c() {
         return this.k;
      }

      public int a() {
         return this.j;
      }

      public static EntityRabbit.Variant a(int i) {
         return EntityRabbit.Variant.i.apply(i);
      }
   }
}
