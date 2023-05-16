package net.minecraft.world.entity.animal.horse;

import javax.annotation.Nullable;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.level.World;

public class EntityHorseDonkey extends EntityHorseChestedAbstract {
   public EntityHorseDonkey(EntityTypes<? extends EntityHorseDonkey> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.fV;
   }

   @Override
   protected SoundEffect gr() {
      return SoundEffects.fW;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.fY;
   }

   @Nullable
   @Override
   protected SoundEffect fZ() {
      return SoundEffects.fZ;
   }

   @Override
   protected SoundEffect d(DamageSource var0) {
      return SoundEffects.ga;
   }

   @Override
   public boolean a(EntityAnimal var0) {
      if (var0 == this) {
         return false;
      } else if (!(var0 instanceof EntityHorseDonkey) && !(var0 instanceof EntityHorse)) {
         return false;
      } else {
         return this.gA() && ((EntityHorseAbstract)var0).gA();
      }
   }

   @Nullable
   @Override
   public EntityAgeable a(WorldServer var0, EntityAgeable var1) {
      EntityTypes<? extends EntityHorseAbstract> var2 = var1 instanceof EntityHorse ? EntityTypes.ap : EntityTypes.w;
      EntityHorseAbstract var3 = var2.a((World)var0);
      if (var3 != null) {
         this.a(var1, var3);
      }

      return var3;
   }
}
