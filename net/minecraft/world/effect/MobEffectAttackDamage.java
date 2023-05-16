package net.minecraft.world.effect;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class MobEffectAttackDamage extends MobEffectList {
   protected final double a;

   protected MobEffectAttackDamage(MobEffectInfo var0, int var1, double var2) {
      super(var0, var1);
      this.a = var2;
   }

   @Override
   public double a(int var0, AttributeModifier var1) {
      return this.a * (double)(var0 + 1);
   }
}
