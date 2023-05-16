package net.minecraft.world.level.storage.loot.parameters;

import net.minecraft.resources.MinecraftKey;

public class LootContextParameter<T> {
   private final MinecraftKey a;

   public LootContextParameter(MinecraftKey var0) {
      this.a = var0;
   }

   public MinecraftKey a() {
      return this.a;
   }

   @Override
   public String toString() {
      return "<parameter " + this.a + ">";
   }
}
