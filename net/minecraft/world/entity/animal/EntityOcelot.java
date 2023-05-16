package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
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
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.GroupDataEntity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLeapAtTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalOcelotAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntityOcelot extends EntityAnimal {
   public static final double bS = 0.6;
   public static final double bT = 0.8;
   public static final double bV = 1.33;
   private static final RecipeItemStack bW = RecipeItemStack.a(Items.qh, Items.qi);
   private static final DataWatcherObject<Boolean> bX = DataWatcher.a(EntityOcelot.class, DataWatcherRegistry.k);
   @Nullable
   private EntityOcelot.a<EntityHuman> bY;
   @Nullable
   private EntityOcelot.b bZ;

   public EntityOcelot(EntityTypes<? extends EntityOcelot> entitytypes, World world) {
      super(entitytypes, world);
      this.r();
   }

   public boolean w() {
      return this.am.a(bX);
   }

   public void w(boolean flag) {
      this.am.b(bX, flag);
      this.r();
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("Trusting", this.w());
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.w(nbttagcompound.q("Trusting"));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bX, false);
   }

   @Override
   protected void x() {
      this.bZ = new EntityOcelot.b(this, 0.6, bW, true);
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(3, this.bZ);
      this.bN.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
      this.bN.a(8, new PathfinderGoalOcelotAttack(this));
      this.bN.a(9, new PathfinderGoalBreed(this, 0.8));
      this.bN.a(10, new PathfinderGoalRandomStrollLand(this, 0.8, 1.0000001E-5F));
      this.bN.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
      this.bO.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityChicken.class, false));
      this.bO.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityTurtle.class, 10, false, false, EntityTurtle.bT));
   }

   @Override
   public void U() {
      if (this.D().b()) {
         double d0 = this.D().c();
         if (d0 == 0.6) {
            this.b(EntityPose.f);
            this.g(false);
         } else if (d0 == 1.33) {
            this.b(EntityPose.a);
            this.g(true);
         } else {
            this.b(EntityPose.a);
            this.g(false);
         }
      } else {
         this.b(EntityPose.a);
         this.g(false);
      }
   }

   @Override
   public boolean h(double d0) {
      return !this.w() && this.ag > 2400;
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 0.3F).a(GenericAttributes.f, 3.0);
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return SoundEffects.qq;
   }

   @Override
   public int K() {
      return 900;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.qp;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.qr;
   }

   private float fS() {
      return (float)this.b(GenericAttributes.f);
   }

   @Override
   public boolean z(Entity entity) {
      return entity.a(this.dG().b((EntityLiving)this), this.fS());
   }

   @Override
   public EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if ((this.bZ == null || this.bZ.i()) && !this.w() && this.m(itemstack) && entityhuman.f(this) < 9.0) {
         this.a(entityhuman, enumhand, itemstack);
         if (!this.H.B) {
            if (this.af.a(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
               this.w(true);
               this.x(true);
               this.H.a(this, (byte)41);
            } else {
               this.x(false);
               this.H.a(this, (byte)40);
            }
         }

         return EnumInteractionResult.a(this.H.B);
      } else {
         return super.b(entityhuman, enumhand);
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 41) {
         this.x(true);
      } else if (b0 == 40) {
         this.x(false);
      } else {
         super.b(b0);
      }
   }

   private void x(boolean flag) {
      ParticleType particletype = Particles.O;
      if (!flag) {
         particletype = Particles.ab;
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.af.k() * 0.02;
         double d1 = this.af.k() * 0.02;
         double d2 = this.af.k() * 0.02;
         this.H.a(particletype, this.d(1.0), this.do() + 0.5, this.g(1.0), d0, d1, d2);
      }
   }

   protected void r() {
      if (this.bY == null) {
         this.bY = new EntityOcelot.a<>(this, EntityHuman.class, 16.0F, 0.8, 1.33);
      }

      this.bN.a(this.bY);
      if (!this.w()) {
         this.bN.a(4, this.bY);
      }
   }

   @Nullable
   public EntityOcelot b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.aq.a((World)worldserver);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return bW.a(itemstack);
   }

   public static boolean c(
      EntityTypes<EntityOcelot> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      return randomsource.a(3) != 0;
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      if (iworldreader.f(this) && !iworldreader.d(this.cD())) {
         BlockPosition blockposition = this.dg();
         if (blockposition.v() < iworldreader.m_()) {
            return false;
         }

         IBlockData iblockdata = iworldreader.a_(blockposition.d());
         if (iblockdata.a(Blocks.i) || iblockdata.a(TagsBlock.N)) {
            return true;
         }
      }

      return false;
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
         groupdataentity = new EntityAgeable.a(1.0F);
      }

      return super.a(worldaccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.5F * this.cE()), (double)(this.dc() * 0.4F));
   }

   @Override
   public boolean bP() {
      return this.bT() || super.bP();
   }

   private static class a<T extends EntityLiving> extends PathfinderGoalAvoidTarget<T> {
      private final EntityOcelot i;

      public a(EntityOcelot entityocelot, Class<T> oclass, float f, double d0, double d1) {
         super(entityocelot, oclass, f, d0, d1, IEntitySelector.e::test);
         this.i = entityocelot;
      }

      @Override
      public boolean a() {
         return !this.i.w() && super.a();
      }

      @Override
      public boolean b() {
         return !this.i.w() && super.b();
      }
   }

   private static class b extends PathfinderGoalTempt {
      private final EntityOcelot c;

      public b(EntityOcelot entityocelot, double d0, RecipeItemStack recipeitemstack, boolean flag) {
         super(entityocelot, d0, recipeitemstack, flag);
         this.c = entityocelot;
      }

      @Override
      protected boolean h() {
         return super.h() && !this.c.w();
      }
   }
}
