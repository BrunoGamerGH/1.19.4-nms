package net.minecraft.server;

import java.util.List;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;

public enum RegistryLayer {
   a,
   b,
   c,
   d;

   private static final List<RegistryLayer> e = List.of(values());
   private static final IRegistryCustom.Dimension f = IRegistryCustom.a(BuiltInRegistries.an);

   public static LayeredRegistryAccess<RegistryLayer> a() {
      return new LayeredRegistryAccess<>(e).a(a, f);
   }
}
