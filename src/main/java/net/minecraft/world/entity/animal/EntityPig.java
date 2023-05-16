package net.minecraft.world.entity.animal;

import com.google.common.collect.UnmodifiableIterator;
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
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.ISteerable;
import net.minecraft.world.entity.SaddleStorage;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalBreed;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFollowParent;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalPanic;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.PathfinderGoalTempt;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.DismountUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityPig extends EntityAnimal implements ISteerable, ISaddleable {
   private static final DataWatcherObject<Boolean> bS = DataWatcher.a(EntityPig.class, DataWatcherRegistry.k);
   private static final DataWatcherObject<Integer> bT = DataWatcher.a(EntityPig.class, DataWatcherRegistry.b);
   private static final RecipeItemStack bV = RecipeItemStack.a(Items.th, Items.ti, Items.ul);
   public final SaddleStorage bW = new SaddleStorage(this.am, bT, bS);

   public EntityPig(EntityTypes<? extends EntityPig> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new PathfinderGoalPanic(this, 1.25));
      this.bN.a(3, new PathfinderGoalBreed(this, 1.0));
      this.bN.a(4, new PathfinderGoalTempt(this, 1.2, RecipeItemStack.a(Items.nb), false));
      this.bN.a(4, new PathfinderGoalTempt(this, 1.2, bV, false));
      this.bN.a(5, new PathfinderGoalFollowParent(this, 1.1));
      this.bN.a(6, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 10.0).a(GenericAttributes.d, 0.25);
   }

   @Nullable
   @Override
   public EntityLiving cK() {
      if (this.i()) {
         Entity entity = this.cN();
         if (entity instanceof EntityHuman entityhuman && (entityhuman.eK().a(Items.nb) || entityhuman.eL().a(Items.nb))) {
            return entityhuman;
         }
      }

      return null;
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (bT.equals(datawatcherobject) && this.H.B) {
         this.bW.a();
      }

      super.a(datawatcherobject);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bS, false);
      this.am.a(bT, 0);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      this.bW.a(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.bW.b(nbttagcompound);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.ry;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.rA;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.rz;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.rC, 0.15F, 1.0F);
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
            return enuminteractionresult;
         }
      }
   }

   @Override
   public boolean g() {
      return this.bq() && !this.y_();
   }

   @Override
   protected void er() {
      super.er();
      if (this.i()) {
         this.a(Items.mV);
      }
   }

   @Override
   public boolean i() {
      return this.bW.d();
   }

   @Override
   public void a(@Nullable SoundCategory soundcategory) {
      this.bW.a(true);
      if (soundcategory != null) {
         this.H.a(null, this, SoundEffects.rB, soundcategory, 0.5F, 1.0F);
      }
   }

   @Override
   public Vec3D b(EntityLiving entityliving) {
      EnumDirection enumdirection = this.cB();
      if (enumdirection.o() == EnumDirection.EnumAxis.b) {
         return super.b(entityliving);
      } else {
         int[][] aint = DismountUtil.a(enumdirection);
         BlockPosition blockposition = this.dg();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
         UnmodifiableIterator unmodifiableiterator = entityliving.fr().iterator();

         while(unmodifiableiterator.hasNext()) {
            EntityPose entitypose = (EntityPose)unmodifiableiterator.next();
            AxisAlignedBB axisalignedbb = entityliving.g(entitypose);

            for(int[] aint2 : aint) {
               blockposition_mutableblockposition.d(blockposition.u() + aint2[0], blockposition.v(), blockposition.w() + aint2[1]);
               double d0 = this.H.i(blockposition_mutableblockposition);
               if (DismountUtil.a(d0)) {
                  Vec3D vec3d = Vec3D.a(blockposition_mutableblockposition, d0);
                  if (DismountUtil.a(this.H, entityliving, axisalignedbb.c(vec3d))) {
                     entityliving.b(entitypose);
                     return vec3d;
                  }
               }
            }
         }

         return super.b(entityliving);
      }
   }

   @Override
   public void a(WorldServer worldserver, EntityLightning entitylightning) {
      if (worldserver.ah() != EnumDifficulty.a) {
         EntityPigZombie entitypigzombie = EntityTypes.bs.a((World)worldserver);
         if (entitypigzombie != null) {
            entitypigzombie.a(EnumItemSlot.a, new ItemStack(Items.od));
            entitypigzombie.b(this.dl(), this.dn(), this.dr(), this.dw(), this.dy());
            entitypigzombie.t(this.fK());
            entitypigzombie.a(this.y_());
            if (this.aa()) {
               entitypigzombie.b(this.ab());
               entitypigzombie.n(this.cx());
            }

            entitypigzombie.fz();
            if (CraftEventFactory.callPigZapEvent(this, entitylightning, entitypigzombie).isCancelled()) {
               return;
            }

            worldserver.addFreshEntity(entitypigzombie, SpawnReason.LIGHTNING);
            this.ai();
         } else {
            super.a(worldserver, entitylightning);
         }
      } else {
         super.a(worldserver, entitylightning);
      }
   }

   @Override
   protected void a(EntityLiving entityliving, Vec3D vec3d) {
      super.a(entityliving, vec3d);
      this.a(entityliving.dw(), entityliving.dy() * 0.5F);
      this.L = this.aT = this.aV = this.dw();
      this.bW.b();
   }

   @Override
   protected Vec3D b(EntityLiving entityliving, Vec3D vec3d) {
      return new Vec3D(0.0, 0.0, 1.0);
   }

   @Override
   protected float g(EntityLiving entityliving) {
      return (float)(this.b(GenericAttributes.d) * 0.225 * (double)this.bW.c());
   }

   @Override
   public boolean a() {
      return this.bW.a(this.dZ());
   }

   @Nullable
   public EntityPig b(WorldServer worldserver, EntityAgeable entityageable) {
      return EntityTypes.av.a((World)worldserver);
   }

   @Override
   public boolean m(ItemStack itemstack) {
      return bV.a(itemstack);
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.6F * this.cE()), (double)(this.dc() * 0.4F));
   }
}
