package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class EntityHorseSkeleton extends EntityHorseAbstract {
   private final PathfinderGoalHorseTrap bS = new PathfinderGoalHorseTrap(this);
   private static final int bT = 18000;
   private boolean bV;
   public int bW;

   public EntityHorseSkeleton(EntityTypes<? extends EntityHorseSkeleton> var0, World var1) {
      super(var0, var1);
   }

   public static AttributeProvider.Builder q() {
      return gs().a(GenericAttributes.a, 15.0).a(GenericAttributes.d, 0.2F);
   }

   @Override
   protected void a(RandomSource var0) {
      this.a(GenericAttributes.m).a(a(var0::j));
   }

   @Override
   protected void gi() {
   }

   @Override
   protected SoundEffect s() {
      return this.a(TagsFluid.a) ? SoundEffects.vf : SoundEffects.vb;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.vc;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.vd;
   }

   @Override
   protected SoundEffect aI() {
      if (this.N) {
         if (!this.bM()) {
            return SoundEffects.vi;
         }

         ++this.cs;
         if (this.cs > 5 && this.cs % 3 == 0) {
            return SoundEffects.vg;
         }

         if (this.cs <= 5) {
            return SoundEffects.vi;
         }
      }

      return SoundEffects.ve;
   }

   @Override
   protected void i(float var0) {
      if (this.N) {
         super.i(0.3F);
      } else {
         super.i(Math.min(0.1F, var0 * 25.0F));
      }
   }

   @Override
   protected void gz() {
      if (this.aT()) {
         this.a(SoundEffects.vh, 0.4F, 1.0F);
      } else {
         super.gz();
      }
   }

   @Override
   public EnumMonsterType eJ() {
      return EnumMonsterType.b;
   }

   @Override
   public double bv() {
      return super.bv() - 0.1875;
   }

   @Override
   public void b_() {
      super.b_();
      if (this.r() && this.bW++ >= 18000) {
         this.ai();
      }
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("SkeletonTrap", this.r());
      var0.a("SkeletonTrapTime", this.bW);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.w(var0.q("SkeletonTrap"));
      this.bW = var0.h("SkeletonTrapTime");
   }

   @Override
   protected float eU() {
      return 0.96F;
   }

   public boolean r() {
      return this.bV;
   }

   public void w(boolean var0) {
      if (var0 != this.bV) {
         this.bV = var0;
         if (var0) {
            this.bN.a(1, this.bS);
         } else {
            this.bN.a(this.bS);
         }
      }
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      return EntityTypes.aK.a((World)var0);
   }

   @Override
   public EnumInteractionResult b(EntityHuman var0, EnumHand var1) {
      return !this.gh() ? EnumInteractionResult.d : super.b(var0, var1);
   }
}
