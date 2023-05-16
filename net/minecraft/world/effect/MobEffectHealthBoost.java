package net.minecraft.world.effect;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;

public class MobEffectHealthBoost extends MobEffectList {
   public MobEffectHealthBoost(MobEffectInfo var0, int var1) {
      super(var0, var1);
   }

   @Override
   public void a(EntityLiving var0, AttributeMapBase var1, int var2) {
      super.a(var0, var1, var2);
      if (var0.eo() > var0.eE()) {
         var0.c(var0.eE());
      }
   }
}
