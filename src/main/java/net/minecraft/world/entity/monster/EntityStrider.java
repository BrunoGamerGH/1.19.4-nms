package net.minecraft.world.entity.monster;

import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
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
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.ISteerable;
import net.minecraft.world.entity.SaddleStorage;
import net.minecraft.world.entity.ai.attributes.AttributeModifiable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalGotoTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.DismountUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityStrider extends EntityAnimal implements ISteerable, ISaddleable {
   private static final UUID bS = UUID.fromString("9e362924-01de-4ddd-a2b2-d0f7a405a174");
   private static final AttributeModifier bT = new AttributeModifier(bS, "Strider suffocating modifier", -0.34F, AttributeModifier.Operation.b);
   private static final float bV = 0.35F;
   private static final float bW = 0.55F;
   private static final RecipeItemStack bX = RecipeItemStack.a(Items.df);
   private static final RecipeItemStack bY = RecipeItemStack.a(Items.df, Items.nc);
   private static final DataWatcherObject<Integer> bZ = DataWatcher.a(EntityStrider.class, DataWatcherRegistry.b);
   private static final DataWatcherObject<Boolean> ca = DataWatcher.a(EntityStrider.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Boolean> cb = DataWatcher.a(EntityStrider.class, DataWatcherRegistry.k);
   public final SaddleStorage cc = new SaddleStorage(this.am, bZ, cb);
   @Nullable
   private PathfinderGoalTempt cd;
   @Nullable
   private PathfinderGoalPanic ce;

   public EntityStrider(EntityTypes<? extends EntityStrider> entitytypes, World world) {
      super(entitytypes, world);
      this.F = true;
      this.a(PathType.j, -1.0F);
      this.a(PathType.i, 0.0F);
      this.a(PathType.n, 0.0F);
      this.a(PathType.o, 0.0F);
   }

   public static boolean c(
      EntityTypes<EntityStrider> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      do {
         blockposition_mutableblockposition.c(EnumDirection.b);
      } while(generatoraccess.b_(blockposition_mutableblockposition).a(TagsFluid.b));

      return generatoraccess.a_(blockposition_mutableblockposition).h();
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bZ.equals(datawatcherobject) && this.H.B) {
         this.cc.a();
      }

      super.a(datawatcherobject);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bZ, 0);
      this.am.a(ca, false);
      this.am.a(cb, false);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.cc.a(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.cc.b(nbttagcompound);
   }

   @Override
   public boolean i() {
      return this.cc.d();
   }

   @Override
   public boolean g() {
      return this.bq() && !this.y_();
   }

   @Override
   public void a(@Nullable SoundCategory soundcategory) {
      this.cc.a(true);
      if (soundcategory != null) {
         this.H.a(null, this, SoundEffects.wb, soundcategory, 0.5F, 1.0F);
      }
   }

   @Override
   protected void x() {
      this.ce = new PathfinderGoalPanic(this, 1.65);
      this.bN.a(1, this.ce);
      this.bN.a(2, new PathfinderGoalBreed(this, 1.0));
      this.cd = new PathfinderGoalTempt(this, 1.4, bY, false);
      this.bN.a(3, this.cd);
      this.bN.a(4, new EntityStrider.a(this, 1.0));
      this.bN.a(5, new PathfinderGoalFollowParent(this, 1.0));
      this.bN.a(7, new PathfinderGoalRandomStroll(this, 1.0, 60));
      this.bN.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityStrider.class, 8.0F));
   }

   public void w(boolean flag) {
      this.am.b(ca, flag);
      AttributeModifiable attributemodifiable = this.a(GenericAttributes.d);
      if (attributemodifiable != null) {
         attributemodifiable.b(bS);
         if (flag) {
            attributemodifiable.b(bT);
         }
      }
   }

   public boolean q() {
      return this.am.a(ca);
   }

   @Override
   public boolean a(Fluid fluid) {
      return fluid.a(TagsFluid.b);
   }

   @Override
   public double bv() {
      float f = Math.min(0.25F, this.aP.a());
      float f1 = this.aP.b();
      return (double)this.dd() - 0.19 + (double)(0.12F * MathHelper.b(f1 * 1.5F) * 2.0F * f);
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      return iworldreader.f(this);
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      Entity entity = this.cN();
      if (entity instanceof EntityHuman entityhuman && (entityhuman.eK().a(Items.nc) || entityhuman.eL().a(Items.nc))) {
         return entityhuman;
      }

      return null;
   }

   @Override
   public Vec3D b(EntityLiving entityliving) {
      Vec3D[] avec3d = new Vec3D[]{
         a((double)this.dc(), (double)entityliving.dc(), entityliving.dw()),
         a((double)this.dc(), (double)entityliving.dc(), entityliving.dw() - 22.5F),
         a((double)this.dc(), (double)entityliving.dc(), entityliving.dw() + 22.5F),
         a((double)this.dc(), (double)entityliving.dc(), entityliving.dw() - 45.0F),
         a((double)this.dc(), (double)entityliving.dc(), entityliving.dw() + 45.0F)
      };
      Set<BlockPosition> set = Sets.newLinkedHashSet();
      double d0 = this.cD().e;
      double d1 = this.cD().b - 0.5;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(Vec3D vec3d : avec3d) {
         blockposition_mutableblockposition.b(this.dl() + vec3d.c, d0, this.dr() + vec3d.e);

         for(double d2 = d0; d2 > d1; --d2) {
            set.add(blockposition_mutableblockposition.i());
            blockposition_mutableblockposition.c(EnumDirection.a);
         }
      }

      for(BlockPosition blockposition : set) {
         if (!this.H.b_(blockposition).a(TagsFluid.b)) {
            double d3 = this.H.i(blockposition);
            if (DismountUtil.a(d3)) {
               Vec3D vec3d1 = Vec3D.a(blockposition, d3);
               UnmodifiableIterator unmodifiableiterator = entityliving.fr().iterator();

               while(unmodifiableiterator.hasNext()) {
                  EntityPose entitypose = (EntityPose)unmodifiableiterator.next();
                  AxisAlignedBB axisalignedbb = entityliving.g(entitypose);
                  if (DismountUtil.a(this.H, entityliving, axisalignedbb.c(vec3d1))) {
                     entityliving.b(entitypose);
                     return vec3d1;
                  }
               }
            }
         }
      }

      return new Vec3D(this.dl(), this.cD().e, this.dr());
   }

   @Override
   protected void a(EntityLiving entityliving, Vec3D vec3d) {
      this.a(entityliving.dw(), entityliving.dy() * 0.5F);
      this.L = this.aT = this.aV = this.dw();
      this.cc.b();
      super.a(entityliving, vec3d);
   }

   @Override
   protected Vec3D b(EntityLiving entityliving, Vec3D vec3d) {
      return new Vec3D(0.0, 0.0, 1.0);
   }

   @Override
   protected float g(EntityLiving entityliving) {
      return (float)(this.b(GenericAttributes.d) * (double)(this.q() ? 0.35F : 0.55F) * (double)this.cc.c());
   }

   @Override
   protected float aH() {
      return this.Y + 0.6F;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(this.bg() ? SoundEffects.vZ : SoundEffects.vY, 1.0F, 1.0F);
   }

   @Override
   public boolean a() {
      return this.cc.a(this.dZ());
   }

   @Override
   protected void a(double d0, boolean flag, IBlockData iblockdata, BlockPosition blockposition) {
      this.aL();
      if (this.bg()) {
         this.n();
      } else {
         super.a(d0, flag, iblockdata, blockposition);
      }
   }

   @Override
   public void l() {
      if (this.fS() && this.af.a(140) == 0) {
         this.a(SoundEffects.vU, 1.0F, this.eO());
      } else if (this.w() && this.af.a(60) == 0) {
         this.a(SoundEffects.vV, 1.0F, this.eO());
      }

      if (!this.fK()) {
         boolean flag;
         boolean flag1;
         label40: {
            IBlockData iblockdata = this.H.a_(this.dg());
            IBlockData iblockdata1 = this.bc();
            flag = iblockdata.a(TagsBlock.aR) || iblockdata1.a(TagsBlock.aR) || this.b(TagsFluid.b) > 0.0;
            Entity entity = this.cV();
            if (entity instanceof EntityStrider entitystrider && entitystrider.q()) {
               flag1 = true;
               break label40;
            }

            flag1 = false;
         }

         boolean suffocating = !flag || flag1;
         if (suffocating ^ this.q() && CraftEventFactory.callStriderTemperatureChangeEvent(this, suffocating)) {
            this.w(suffocating);
         }
      }

      super.l();
      this.fY();
      this.aL();
   }

   private boolean w() {
      return this.ce != null && this.ce.k();
   }

   private boolean fS() {
      return this.cd != null && this.cd.i();
   }

   @Override
   protected boolean z() {
      return true;
   }

   private void fY() {
      if (this.bg()) {
         VoxelShapeCollision voxelshapecollision = VoxelShapeCollision.a(this);
         if (voxelshapecollision.a(BlockFluids.c, this.dg(), true) && !this.H.b_(this.dg().c()).a(TagsFluid.b)) {
            this.N = true;
         } else {
            this.f(this.dj().a(0.5).b(0.0, 0.05, 0.0));
         }
      }
   }

   public static AttributeProvider.Builder r() {
      return EntityInsentient.y().a(GenericAttributes.d, 0.175F).a(GenericAttributes.b, 16.0);
   }

   @Override
   protected SoundEffect s() {
      return !this.w() && !this.fS() ? SoundEffects.vT : null;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.vX;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.vW;
   }

   @Override
   protected boolean o(Entity entity) {
      return !this.bM() && !this.a(TagsFluid.b);
   }

   @Override
   public boolean eX() {
      return true;
   }

   @Override
   public boolean bK() {
      return false;
   }

   @Override
   protected NavigationAbstract a(World world) {
      return new EntityStrider.b(this, world);
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return iworldreader.a_(blockposition).r().a(TagsFluid.b) ? 10.0F : (this.bg() ? Float.NEGATIVE_INFINITY : 0.0F);
   }

   @Nullable
   public EntityStrider b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.aV.a((World)worldserver);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return bX.a(itemstack);
   }

   @Override
   protected void er() {
      super.er();
      if (this.i()) {
         this.a(Items.mV);
      }
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      boolean flag = this.m(entityhuman.b(enumhand));
      if (!flag && this.i() && !this.bM() && !entityhuman.fz()) {
         if (!this.H.B) {
            entityhuman.k(this);
         }

         return EnumInteractionResult.a(this.H.B);
      } else {
         EnumInteractionResult enuminteractionresult = super.b(entityhuman, enumhand);
         if (!enuminteractionresult.a()) {
            ItemStack itemstack = entityhuman.b(enumhand);
            return itemstack.a(Items.mV) ? itemstack.a(entityhuman, this, enumhand) : EnumInteractionResult.d;
         } else {
            if (flag && !this.aO()) {
               this.H.a(null, this.dl(), this.dn(), this.dr(), SoundEffects.wa, this.cX(), 1.0F, 1.0F + (this.af.i() - this.af.i()) * 0.2F);
            }

            return enuminteractionresult;
         }
      }
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.6F * this.cE()), (double)(this.dc() * 0.4F));
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
      if (this.y_()) {
         return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      } else {
         RandomSource randomsource = worldaccess.r_();
         if (randomsource.a(30) == 0) {
            EntityInsentient entityinsentient = EntityTypes.bs.a((World)worldaccess.C());
            if (entityinsentient != null) {
               groupdataentity = this.a(
                  worldaccess, difficultydamagescaler, entityinsentient, new EntityZombie.GroupDataZombie(EntityZombie.a(randomsource), false)
               );
               entityinsentient.a(EnumItemSlot.a, new ItemStack(Items.nc));
               this.a(null);
            }
         } else if (randomsource.a(10) == 0) {
            EntityAgeable entityageable = EntityTypes.aV.a((World)worldaccess.C());
            if (entityageable != null) {
               entityageable.c_(-24000);
               groupdataentity = this.a(worldaccess, difficultydamagescaler, entityageable, null);
            }
         } else {
            groupdataentity = new EntityAgeable.a(0.5F);
         }

         return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
      }
   }

   private GroupDataEntity a(
      WorldAccess worldaccess, DifficultyDamageScaler difficultydamagescaler, EntityInsentient entityinsentient, @Nullable GroupDataEntity groupdataentity
   ) {
      entityinsentient.b(this.dl(), this.dn(), this.dr(), this.dw(), 0.0F);
      entityinsentient.a(worldaccess, difficultydamagescaler, EnumMobSpawn.g, groupdataentity, null);
      entityinsentient.a(this, true);
      return new EntityAgeable.a(0.0F);
   }

   private static class a extends PathfinderGoalGotoTarget {
      private final EntityStrider g;

      a(EntityStrider entitystrider, double d0) {
         super(entitystrider, d0, 8, 2);
         this.g = entitystrider;
      }

      @Override
      public BlockPosition k() {
         return this.e;
      }

      @Override
      public boolean b() {
         return !this.g.bg() && this.a(this.g.H, this.e);
      }

      @Override
      public boolean a() {
         return !this.g.bg() && super.a();
      }

      @Override
      public boolean l() {
         return this.d % 20 == 0;
      }

      @Override
      protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         return iworldreader.a_(blockposition).a(Blocks.H) && iworldreader.a_(blockposition.c()).a(iworldreader, blockposition, PathMode.a);
      }
   }

   private static class b extends Navigation {
      b(EntityStrider entitystrider, World world) {
         super(entitystrider, world);
      }

      @Override
      protected Pathfinder a(int i) {
         this.o = new PathfinderNormal();
         this.o.a(true);
         return new Pathfinder(this.o, i);
      }

      @Override
      protected boolean a(PathType pathtype) {
         return pathtype != PathType.i && pathtype != PathType.o && pathtype != PathType.n ? super.a(pathtype) : true;
      }

      @Override
      public boolean a(BlockPosition blockposition) {
         return this.b.a_(blockposition).a(Blocks.H) || super.a(blockposition);
      }
   }
}
