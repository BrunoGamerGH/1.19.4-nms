package net.minecraft.world.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;

public class GlowSquid extends EntitySquid {
   private static final DataWatcherObject<Integer> bW = DataWatcher.a(GlowSquid.class, DataWatcherRegistry.b);

   public GlowSquid(EntityTypes<? extends GlowSquid> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected ParticleParam q() {
      return Particles.aL;
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(bW, 0);
   }

   @Override
   protected SoundEffect r() {
      return SoundEffects.jt;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.jq;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.js;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.jr;
   }

   @Override
   public void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("DarkTicksRemaining", this.w());
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.c(var0.h("DarkTicksRemaining"));
   }

   @Override
   public void b_() {
      super.b_();
      int var0 = this.w();
      if (var0 > 0) {
         this.c(var0 - 1);
      }

      this.H.a(Particles.aM, this.d(0.6), this.do(), this.g(0.6), 0.0, 0.0, 0.0);
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      boolean var2 = super.a(var0, var1);
      if (var2) {
         this.c(100);
      }

      return var2;
   }

   public void c(int var0) {
      this.am.b(bW, var0);
   }

   public int w() {
      return this.am.a(bW);
   }

   public static boolean a(EntityTypes<? extends EntityLiving> var0, WorldAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      return var3.v() <= var1.m_() - 33 && var1.b(var3, 0) == 0 && var1.a_(var3).a(Blocks.G);
   }
}
