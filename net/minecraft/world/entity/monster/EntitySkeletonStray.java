package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Blocks;

public class EntitySkeletonStray extends EntitySkeletonAbstract {
   public EntitySkeletonStray(EntityTypes<? extends EntitySkeletonStray> var0, World var1) {
      super(var0, var1);
   }

   public static boolean a(EntityTypes<EntitySkeletonStray> var0, WorldAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      BlockPosition var5 = var3;

      do {
         var5 = var5.c();
      } while(var1.a_(var5).a(Blocks.qy));

      return b(var0, var1, var2, var3, var4) && (var2 == EnumMobSpawn.c || var1.g(var5.d()));
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.xa;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.xc;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.xb;
   }

   @Override
   SoundEffect r() {
      return SoundEffects.xd;
   }

   @Override
   protected EntityArrow b(ItemStack var0, float var1) {
      EntityArrow var2 = super.b(var0, var1);
      if (var2 instanceof EntityTippedArrow) {
         ((EntityTippedArrow)var2).a(new MobEffect(MobEffects.b, 600));
      }

      return var2;
   }
}
