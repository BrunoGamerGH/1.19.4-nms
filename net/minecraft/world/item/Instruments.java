package net.minecraft.world.item;

import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;

public interface Instruments {
   int a = 256;
   int b = 140;
   ResourceKey<Instrument> c = a("ponder_goat_horn");
   ResourceKey<Instrument> d = a("sing_goat_horn");
   ResourceKey<Instrument> e = a("seek_goat_horn");
   ResourceKey<Instrument> f = a("feel_goat_horn");
   ResourceKey<Instrument> g = a("admire_goat_horn");
   ResourceKey<Instrument> h = a("call_goat_horn");
   ResourceKey<Instrument> i = a("yearn_goat_horn");
   ResourceKey<Instrument> j = a("dream_goat_horn");

   private static ResourceKey<Instrument> a(String var0) {
      return ResourceKey.a(Registries.A, new MinecraftKey(var0));
   }

   static Instrument a(IRegistry<Instrument> var0) {
      IRegistry.a(var0, c, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(0), 140, 256.0F));
      IRegistry.a(var0, d, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(1), 140, 256.0F));
      IRegistry.a(var0, e, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(2), 140, 256.0F));
      IRegistry.a(var0, f, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(3), 140, 256.0F));
      IRegistry.a(var0, g, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(4), 140, 256.0F));
      IRegistry.a(var0, h, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(5), 140, 256.0F));
      IRegistry.a(var0, i, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(6), 140, 256.0F));
      return IRegistry.a(var0, j, new Instrument((Holder<SoundEffect>)SoundEffects.kU.get(7), 140, 256.0F));
   }
}
