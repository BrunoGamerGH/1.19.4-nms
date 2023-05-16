package net.minecraft.world.item.enchantment;

import net.minecraft.util.random.WeightedEntry;

public class WeightedRandomEnchant extends WeightedEntry.a {
   public final Enchantment a;
   public final int b;

   public WeightedRandomEnchant(Enchantment var0, int var1) {
      super(var0.d().a());
      this.a = var0;
      this.b = var1;
   }
}
