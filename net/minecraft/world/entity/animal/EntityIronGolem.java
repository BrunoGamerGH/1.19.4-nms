package net.minecraft.world.entity.animal;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntityAngerable;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMoveTowardsTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalOfferFlower;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalStrollVillage;
import net.minecraft.world.entity.ai.goal.PathfinderGoalStrollVillageGolem;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalDefendVillage;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalUniversalAngerReset;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityIronGolem extends EntityGolem implements IEntityAngerable {
   protected static final DataWatcherObject<Byte> c = DataWatcher.a(EntityIronGolem.class, DataWatcherRegistry.a);
   private static final int d = 25;
   private int e;
   private int bS;
   private static final UniformInt bT = TimeRange.a(20, 39);
   private int bU;
   @Nullable
   private UUID bV;

   public EntityIronGolem(EntityTypes<? extends EntityIronGolem> entitytypes, World world) {
      super(entitytypes, world);
      this.v(1.0F);
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalMeleeAttack(this, 1.0, true));
      this.bN.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9, 32.0F));
      this.bN.a(2, new PathfinderGoalStrollVillage(this, 0.6, false));
      this.bN.a(4, new PathfinderGoalStrollVillageGolem(this, 0.6));
      this.bN.a(5, new PathfinderGoalOfferFlower(this));
      this.bN.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalDefendVillage(this));
      this.bO.a(2, new PathfinderGoalHurtByTarget(this));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, 10, true, false, this::a_));
      this.bO
         .a(
            3,
            new PathfinderGoalNearestAttackableTarget<>(
               this, EntityInsentient.class, 5, false, false, entityliving -> entityliving instanceof IMonster && !(entityliving instanceof EntityCreeper)
            )
         );
      this.bO.a(4, new PathfinderGoalUniversalAngerReset<>(this, false));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(c, (byte)0);
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 100.0).a(GenericAttributes.d, 0.25).a(GenericAttributes.c, 1.0).a(GenericAttributes.f, 15.0);
   }

   @Override
   protected int l(int i) {
      return i;
   }

   @Override
   protected void A(Entity entity) {
      if (entity instanceof IMonster && !(entity instanceof EntityCreeper) && this.dZ().a(20) == 0) {
         this.setTarget((EntityLiving)entity, TargetReason.COLLISION, true);
      }

      super.A(entity);
   }

   @Override
   public void b_() {
      super.b_();
      if (this.e > 0) {
         --this.e;
      }

      if (this.bS > 0) {
         --this.bS;
      }

      if (this.dj().i() > 2.5000003E-7F && this.af.a(5) == 0) {
         int i = MathHelper.a(this.dl());
         int j = MathHelper.a(this.dn() - 0.2F);
         int k = MathHelper.a(this.dr());
         IBlockData iblockdata = this.H.a_(new BlockPosition(i, j, k));
         if (!iblockdata.h()) {
            this.H
               .a(
                  new ParticleParamBlock(Particles.c, iblockdata),
                  this.dl() + ((double)this.af.i() - 0.5) * (double)this.dc(),
                  this.dn() + 0.1,
                  this.dr() + ((double)this.af.i() - 0.5) * (double)this.dc(),
                  4.0 * ((double)this.af.i() - 0.5),
                  0.5,
                  ((double)this.af.i() - 0.5) * 4.0
               );
         }
      }

      if (!this.H.B) {
         this.a((WorldServer)this.H, true);
      }
   }

   @Override
   public boolean a(EntityTypes<?> entitytypes) {
      return this.fT() && entitytypes == EntityTypes.bt ? false : (entitytypes == EntityTypes.u ? false : super.a(entitytypes));
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("PlayerCreated", this.fT());
      this.c(nbttagcompound);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.x(nbttagcompound.q("PlayerCreated"));
      this.a(this.H, nbttagcompound);
   }

   @Override
   public void c() {
      this.a(bT.a(this.af));
   }

   @Override
   public void a(int i) {
      this.bU = i;
   }

   @Override
   public int a() {
      return this.bU;
   }

   @Override
   public void a(@Nullable UUID uuid) {
      this.bV = uuid;
   }

   @Nullable
   @Override
   public UUID b() {
      return this.bV;
   }

   private float fU() {
      return (float)this.b(GenericAttributes.f);
   }

   @Override
   public boolean z(Entity entity) {
      this.e = 10;
      this.H.a(this, (byte)4);
      float f = this.fU();
      float f1 = (int)f > 0 ? f / 2.0F + (float)this.af.a((int)f) : f;
      boolean flag = entity.a(this.dG().b((EntityLiving)this), f1);
      if (flag) {
         double d0;
         if (entity instanceof EntityLiving entityliving) {
            d0 = entityliving.b(GenericAttributes.c);
         } else {
            d0 = 0.0;
         }

         double d2 = Math.max(0.0, 1.0 - d0);
         entity.f(entity.dj().b(0.0, 0.4F * d2, 0.0));
         this.a(this, entity);
      }

      this.a(SoundEffects.lD, 1.0F, 1.0F);
      return flag;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      EntityIronGolem.CrackLevel entityirongolem_cracklevel = this.r();
      boolean flag = super.a(damagesource, f);
      if (flag && this.r() != entityirongolem_cracklevel) {
         this.a(SoundEffects.lE, 1.0F, 1.0F);
      }

      return flag;
   }

   public EntityIronGolem.CrackLevel r() {
      return EntityIronGolem.CrackLevel.a(this.eo() / this.eE());
   }

   @Override
   public void b(byte b0) {
      if (b0 == 4) {
         this.e = 10;
         this.a(SoundEffects.lD, 1.0F, 1.0F);
      } else if (b0 == 11) {
         this.bS = 400;
      } else if (b0 == 34) {
         this.bS = 0;
      } else {
         super.b(b0);
      }
   }

   public int w() {
      return this.e;
   }

   public void w(boolean flag) {
      if (flag) {
         this.bS = 400;
         this.H.a(this, (byte)11);
      } else {
         this.bS = 0;
         this.H.a(this, (byte)34);
      }
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.lG;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.lF;
   }

   @Override
   protected EnumInteractionResult b(EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      if (!itemstack.a(Items.nM)) {
         return EnumInteractionResult.d;
      } else {
         float f = this.eo();
         this.b(25.0F);
         if (this.eo() == f) {
            return EnumInteractionResult.d;
         } else {
            float f1 = 1.0F + (this.af.i() - this.af.i()) * 0.2F;
            this.a(SoundEffects.lH, 1.0F, f1);
            if (!entityhuman.fK().d) {
               itemstack.h(1);
            }

            return EnumInteractionResult.a(this.H.B);
         }
      }
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.lI, 1.0F, 1.0F);
   }

   public int fS() {
      return this.bS;
   }

   public boolean fT() {
      return (this.am.a(c) & 1) != 0;
   }

   public void x(boolean flag) {
      byte b0 = this.am.a(c);
      if (flag) {
         this.am.b(c, (byte)(b0 | 1));
      } else {
         this.am.b(c, (byte)(b0 & -2));
      }
   }

   @Override
   public void a(DamageSource damagesource) {
      super.a(damagesource);
   }

   @Override
   public boolean a(IWorldReader iworldreader) {
      BlockPosition blockposition = this.dg();
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata = iworldreader.a_(blockposition1);
      if (!iblockdata.a(iworldreader, blockposition1, this)) {
         return false;
      } else {
         for(int i = 1; i < 3; ++i) {
            BlockPosition blockposition2 = blockposition.b(i);
            IBlockData iblockdata1 = iworldreader.a_(blockposition2);
            if (!SpawnerCreature.a(iworldreader, blockposition2, iblockdata1, iblockdata1.r(), EntityTypes.ac)) {
               return false;
            }
         }

         return SpawnerCreature.a(iworldreader, blockposition, iworldreader.a_(blockposition), FluidTypes.a.g(), EntityTypes.ac) && iworldreader.f(this);
      }
   }

   @Override
   public Vec3D cF() {
      return new Vec3D(0.0, (double)(0.875F * this.cE()), (double)(this.dc() * 0.4F));
   }

   public static enum CrackLevel {
      a(1.0F),
      b(0.75F),
      c(0.5F),
      d(0.25F);

      private static final List<EntityIronGolem.CrackLevel> e = Stream.of(values())
         .sorted(Comparator.comparingDouble(entityirongolem_cracklevel -> (double)entityirongolem_cracklevel.f))
         .collect(ImmutableList.toImmutableList());
      private final float f;

      private CrackLevel(float f) {
         this.f = f;
      }

      public static EntityIronGolem.CrackLevel a(float f) {
         for(EntityIronGolem.CrackLevel entityirongolem_cracklevel : e) {
            if (!(f >= entityirongolem_cracklevel.f)) {
               return entityirongolem_cracklevel;
            }
         }

         return a;
      }
   }
}
