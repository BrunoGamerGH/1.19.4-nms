package net.minecraft.world.entity.animal;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;

public record FrogVariant(MinecraftKey texture) {
   private final MinecraftKey d;
   public static final FrogVariant a = a("temperate", "textures/entity/frog/temperate_frog.png");
   public static final FrogVariant b = a("warm", "textures/entity/frog/warm_frog.png");
   public static final FrogVariant c = a("cold", "textures/entity/frog/cold_frog.png");

   public FrogVariant(MinecraftKey var0) {
      this.d = var0;
   }

   private static FrogVariant a(String var0, String var1) {
      return IRegistry.a(BuiltInRegistries.aj, var0, new FrogVariant(new MinecraftKey(var1)));
   }

   public MinecraftKey a() {
      return this.d;
   }
}
