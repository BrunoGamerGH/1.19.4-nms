package net.minecraft.world.effect;

import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.AttributeMapBase;

public class MobEffectAbsorption extends MobEffectList {
   protected MobEffectAbsorption(MobEffectInfo var0, int var1) {
      super(var0, var1);
   }

   @Override
   public void a(EntityLiving var0, AttributeMapBase var1, int var2) {
      var0.x(var0.fb() - (float)(4 * (var2 + 1)));
      super.a(var0, var1, var2);
   }

   @Override
   public void b(EntityLiving var0, AttributeMapBase var1, int var2) {
      var0.x(var0.fb() + (float)(4 * (var2 + 1)));
      super.b(var0, var1, var2);
   }
}
