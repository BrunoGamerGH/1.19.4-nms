package net.minecraft.world.entity.animal;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public record CatVariant(MinecraftKey texture) {
   private final MinecraftKey l;
   public static final ResourceKey<CatVariant> a = a("tabby");
   public static final ResourceKey<CatVariant> b = a("black");
   public static final ResourceKey<CatVariant> c = a("red");
   public static final ResourceKey<CatVariant> d = a("siamese");
   public static final ResourceKey<CatVariant> e = a("british_shorthair");
   public static final ResourceKey<CatVariant> f = a("calico");
   public static final ResourceKey<CatVariant> g = a("persian");
   public static final ResourceKey<CatVariant> h = a("ragdoll");
   public static final ResourceKey<CatVariant> i = a("white");
   public static final ResourceKey<CatVariant> j = a("jellie");
   public static final ResourceKey<CatVariant> k = a("all_black");

   public CatVariant(MinecraftKey var0) {
      this.l = var0;
   }

   private static ResourceKey<CatVariant> a(String var0) {
      return ResourceKey.a(Registries.j, new MinecraftKey(var0));
   }

   public static CatVariant a(IRegistry<CatVariant> var0) {
      a(var0, a, "textures/entity/cat/tabby.png");
      a(var0, b, "textures/entity/cat/black.png");
      a(var0, c, "textures/entity/cat/red.png");
      a(var0, d, "textures/entity/cat/siamese.png");
      a(var0, e, "textures/entity/cat/british_shorthair.png");
      a(var0, f, "textures/entity/cat/calico.png");
      a(var0, g, "textures/entity/cat/persian.png");
      a(var0, h, "textures/entity/cat/ragdoll.png");
      a(var0, i, "textures/entity/cat/white.png");
      a(var0, j, "textures/entity/cat/jellie.png");
      return a(var0, k, "textures/entity/cat/all_black.png");
   }

   private static CatVariant a(IRegistry<CatVariant> var0, ResourceKey<CatVariant> var1, String var2) {
      return IRegistry.a(var0, var1, new CatVariant(new MinecraftKey(var2)));
   }

   public MinecraftKey a() {
      return this.l;
   }
}
