package net.minecraft.world.level.levelgen.flat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.item.Item;

public record FlatLevelGeneratorPreset(Holder<Item> displayItem, GeneratorSettingsFlat settings) {
   private final Holder<Item> c;
   private final GeneratorSettingsFlat d;
   public static final Codec<FlatLevelGeneratorPreset> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               RegistryFixedCodec.a(Registries.C).fieldOf("display").forGetter(var0x -> var0x.c),
               GeneratorSettingsFlat.a.fieldOf("settings").forGetter(var0x -> var0x.d)
            )
            .apply(var0, FlatLevelGeneratorPreset::new)
   );
   public static final Codec<Holder<FlatLevelGeneratorPreset>> b = RegistryFileCodec.a(Registries.at, a);

   public FlatLevelGeneratorPreset(Holder<Item> var0, GeneratorSettingsFlat var1) {
      this.c = var0;
      this.d = var1;
   }

   public Holder<Item> a() {
      return this.c;
   }

   public GeneratorSettingsFlat b() {
      return this.d;
   }
}
