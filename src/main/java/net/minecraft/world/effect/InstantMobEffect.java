package net.minecraft.world.effect;

public class InstantMobEffect extends MobEffectList {
   public InstantMobEffect(MobEffectInfo var0, int var1) {
      super(var0, var1);
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 >= 1;
   }
}
