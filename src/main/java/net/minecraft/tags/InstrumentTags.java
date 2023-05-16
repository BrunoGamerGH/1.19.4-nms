package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.Instrument;

public interface InstrumentTags {
   TagKey<Instrument> a = a("regular_goat_horns");
   TagKey<Instrument> b = a("screaming_goat_horns");
   TagKey<Instrument> c = a("goat_horns");

   private static TagKey<Instrument> a(String var0) {
      return TagKey.a(Registries.A, new MinecraftKey(var0));
   }
}
