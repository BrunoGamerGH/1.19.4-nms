package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.phys.Vec3D;

public class EntityMagmaCube extends EntitySlime {
   public EntityMagmaCube(EntityTypes<? extends EntityMagmaCube> var0, World var1) {
      super(var0, var1);
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.2F);
   }

   public static boolean b(EntityTypes<EntityMagmaCube> var0, GeneratorAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var1.ah() != EnumDifficulty.a;
   }

   @Override
   public boolean a(IWorldReader var0) {
      return var0.f(this) && !var0.d(this.cD());
   }

   @Override
   public void a(int var0, boolean var1) {
      super.a(var0, var1);
      this.a(GenericAttributes.i).a((double)(var0 * 3));
   }

   @Override
   public float bh() {
      return 1.0F;
   }

   @Override
   protected ParticleParam r() {
      return Particles.C;
   }

   @Override
   public boolean bK() {
      return false;
   }

   @Override
   protected int w() {
      return super.w() * 4;
   }

   @Override
   protected void fP() {
      this.d *= 0.9F;
   }

   @Override
   protected void eS() {
      Vec3D var0 = this.dj();
      this.o(var0.c, (double)(this.eQ() + (float)this.fU() * 0.1F), var0.e);
      this.at = true;
   }

   @Override
   protected void c(TagKey<FluidType> var0) {
      if (var0 == TagsFluid.b) {
         Vec3D var1 = this.dj();
         this.o(var1.c, (double)(0.22F + (float)this.fU() * 0.05F), var1.e);
         this.at = true;
      } else {
         super.c(var0);
      }
   }

   @Override
   protected boolean fQ() {
      return this.cU();
   }

   @Override
   protected float fR() {
      return super.fR() + 2.0F;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return this.fV() ? SoundEffects.mF : SoundEffects.mE;
   }

   @Override
   protected SoundEffect x_() {
      return this.fV() ? SoundEffects.mw : SoundEffects.mD;
   }

   @Override
   protected SoundEffect fS() {
      return this.fV() ? SoundEffects.mI : SoundEffects.mH;
   }

   @Override
   protected SoundEffect fT() {
      return SoundEffects.mG;
   }
}
