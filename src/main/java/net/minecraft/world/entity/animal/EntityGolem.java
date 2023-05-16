package net.minecraft.world.entity.animal;

import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;

public abstract class EntityGolem extends EntityCreature {
   protected EntityGolem(EntityTypes<? extends EntityGolem> var0, World var1) {
      super(var0, var1);
   }

   @Nullable
   @Override
   protected SoundEffect s() {
      return null;
   }

   @Nullable
   @Override
   protected SoundEffect d(DamageSource var0) {
      return null;
   }

   @Nullable
   @Override
   protected SoundEffect x_() {
      return null;
   }

   @Override
   public int K() {
      return 120;
   }

   @Override
   public boolean h(double var0) {
      return false;
   }
}
