package net.minecraft.world.entity.ambient;

import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public abstract class EntityAmbient extends EntityInsentient {
   protected EntityAmbient(EntityTypes<? extends EntityAmbient> var0, World var1) {
      super(var0, var1);
   }

   @Override
   public boolean a(EntityHuman var0) {
      return false;
   }
}
