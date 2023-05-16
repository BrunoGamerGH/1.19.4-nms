package net.minecraft.world.entity.monster;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;

public class EntityGiantZombie extends EntityMonster {
   public EntityGiantZombie(EntityTypes<? extends EntityGiantZombie> var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected float b(EntityPose var0, EntitySize var1) {
      return 10.440001F;
   }

   public static AttributeProvider.Builder q() {
      return EntityMonster.fY().a(GenericAttributes.a, 100.0).a(GenericAttributes.d, 0.5).a(GenericAttributes.f, 50.0);
   }

   @Override
   public float a(BlockPosition var0, IWorldReader var1) {
      return var1.y(var0);
   }
}
