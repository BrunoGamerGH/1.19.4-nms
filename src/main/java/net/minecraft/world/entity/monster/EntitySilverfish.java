package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockMonsterEggs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class EntitySilverfish extends EntityMonster {
   @Nullable
   private EntitySilverfish.PathfinderGoalSilverfishWakeOthers b;

   public EntitySilverfish(EntityTypes<? extends EntitySilverfish> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void x() {
      this.b = new EntitySilverfish.PathfinderGoalSilverfishWakeOthers(this);
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(1, new ClimbOnTopOfPowderSnowGoal(this, this.H));
      this.bN.a(3, this.b);
      this.bN.a(4, new PathfinderGoalMeleeAttack(this, 1.0, false));
      this.bN.a(5, new EntitySilverfish.PathfinderGoalSilverfishHideInBlock(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
   }

   @Override
   public double bu() {
      return 0.1;
   }

   @Override
   protected float b(EntityPose entitypose, EntitySize entitysize) {
      return 0.13F;
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.a, 8.0).a(GenericAttributes.d, 0.25).a(GenericAttributes.f, 1.0);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.uU;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.uW;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.uV;
   }

   @Override
   protected void b(BlockPosition blockposition, IBlockData iblockdata) {
      this.a(SoundEffects.uX, 0.15F, 1.0F);
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else {
         if ((damagesource.d() != null || damagesource.a(DamageTypeTags.x)) && this.b != null) {
            this.b.h();
         }

         return super.a(damagesource, f);
      }
   }

   @Override
   public void l() {
      this.aT = this.dw();
      super.l();
   }

   @Override
   public void s(float f) {
      this.f(f);
      super.s(f);
   }

   @Override
   public float a(BlockPosition blockposition, IWorldReader iworldreader) {
      return BlockMonsterEggs.h(iworldreader.a_(blockposition.d())) ? 10.0F : super.a(blockposition, iworldreader);
   }

   public static boolean b(
      EntityTypes<EntitySilverfish> entitytypes,
      GeneratorAccess generatoraccess,
      EnumMobSpawn enummobspawn,
      BlockPosition blockposition,
      RandomSource randomsource
   ) {
      if (c(entitytypes, generatoraccess, enummobspawn, blockposition, randomsource)) {
         EntityHuman entityhuman = generatoraccess.a(
            (double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5, 5.0, true
         );
         return entityhuman == null;
      } else {
         return false;
      }
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.c;
   }

   private static class PathfinderGoalSilverfishHideInBlock extends PathfinderGoalRandomStroll {
      @Nullable
      private EnumDirection i;
      private boolean j;

      public PathfinderGoalSilverfishHideInBlock(EntitySilverfish entitysilverfish) {
         super(entitysilverfish, 1.0, 10);
         this.a(EnumSet.of(PathfinderGoal.Type.a));
      }

      @Override
      public boolean a() {
         if (this.b.P_() != null) {
            return false;
         } else if (!this.b.G().l()) {
            return false;
         } else {
            RandomSource randomsource = this.b.dZ();
            if (this.b.H.W().b(GameRules.c) && randomsource.a(b(10)) == 0) {
               this.i = EnumDirection.b(randomsource);
               BlockPosition blockposition = BlockPosition.a(this.b.dl(), this.b.dn() + 0.5, this.b.dr()).a(this.i);
               IBlockData iblockdata = this.b.H.a_(blockposition);
               if (BlockMonsterEggs.h(iblockdata)) {
                  this.j = true;
                  return true;
               }
            }

            this.j = false;
            return super.a();
         }
      }

      @Override
      public boolean b() {
         return this.j ? false : super.b();
      }

      @Override
      public void c() {
         if (!this.j) {
            super.c();
         } else {
            World world = this.b.H;
            BlockPosition blockposition = BlockPosition.a(this.b.dl(), this.b.dn() + 0.5, this.b.dr()).a(this.i);
            IBlockData iblockdata = world.a_(blockposition);
            if (BlockMonsterEggs.h(iblockdata)) {
               if (CraftEventFactory.callEntityChangeBlockEvent(this.b, blockposition, BlockMonsterEggs.n(iblockdata)).isCancelled()) {
                  return;
               }

               world.a(blockposition, BlockMonsterEggs.n(iblockdata), 3);
               this.b.M();
               this.b.ai();
            }
         }
      }
   }

   private static class PathfinderGoalSilverfishWakeOthers extends PathfinderGoal {
      private final EntitySilverfish a;
      private int b;

      public PathfinderGoalSilverfishWakeOthers(EntitySilverfish entitysilverfish) {
         this.a = entitysilverfish;
      }

      public void h() {
         if (this.b == 0) {
            this.b = this.a(20);
         }
      }

      @Override
      public boolean a() {
         return this.b > 0;
      }

      @Override
      public void e() {
         --this.b;
         if (this.b <= 0) {
            World world = this.a.H;
            RandomSource randomsource = this.a.dZ();
            BlockPosition blockposition = this.a.dg();

            for(int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
               for(int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                  for(int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                     BlockPosition blockposition1 = blockposition.b(j, i, k);
                     IBlockData iblockdata = world.a_(blockposition1);
                     Block block = iblockdata.b();
                     if (block instanceof BlockMonsterEggs
                        && !CraftEventFactory.callEntityChangeBlockEvent(this.a, blockposition1, Blocks.a.o()).isCancelled()) {
                        if (world.W().b(GameRules.c)) {
                           world.a(blockposition1, true, this.a);
                        } else {
                           world.a(blockposition1, ((BlockMonsterEggs)block).o(world.a_(blockposition1)), 3);
                        }

                        if (randomsource.h()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
