package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
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
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalMeleeAttack;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomLookaround;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStrollLand;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityEndermite extends EntityMonster {
   private static final int b = 2400;
   private int c;

   public EntityEndermite(EntityTypes<? extends EntityEndermite> var0, World var1) {
      super(var0, var1);
      this.bI = 3;
   }

   @Override
   protected void x() {
      this.bN.a(1, new PathfinderGoalFloat(this));
      this.bN.a(1, new ClimbOnTopOfPowderSnowGoal(this, this.H));
      this.bN.a(2, new PathfinderGoalMeleeAttack(this, 1.0, false));
      this.bN.a(3, new PathfinderGoalRandomStrollLand(this, 1.0));
      this.bN.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.bN.a(8, new PathfinderGoalRandomLookaround(this));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));
   }

   @Override
   protected float b(EntityPose var0, EntitySize var1) {
      return 0.13F;
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.a, 8.0).a(GenericAttributes.d, 0.25).a(GenericAttributes.f, 2.0);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.c;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.he;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.hg;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.hf;
   }

   @Override
   protected void b(BlockPosition var0, IBlockData var1) {
      this.a(SoundEffects.hh, 0.15F, 1.0F);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.c = var0.h("Lifetime");
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("Lifetime", this.c);
   }

   @Override
   public void l() {
      this.aT = this.dw();
      super.l();
   }

   @Override
   public void s(float var0) {
      this.f(var0);
      super.s(var0);
   }

   @Override
   public double bu() {
      return 0.1;
   }

   @Override
   public void b_() {
      super.b_();
      if (this.H.B) {
         for(int var0 = 0; var0 < 2; ++var0) {
            this.H.a(Particles.Z, this.d(0.5), this.do(), this.g(0.5), (this.af.j() - 0.5) * 2.0, -this.af.j(), (this.af.j() - 0.5) * 2.0);
         }
      } else {
         if (!this.fB()) {
            ++this.c;
         }

         if (this.c >= 2400) {
            this.ai();
         }
      }
   }

   public static boolean b(EntityTypes<EntityEndermite> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      if (c(var0, var1, var2, var3, var4)) {
         EntityHuman var5 = var1.a((double)var3.u() + 0.5, (double)var3.v() + 0.5, (double)var3.w() + 0.5, 5.0, true);
         return var5 == null;
      } else {
         return false;
      }
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.c;
   }
}
