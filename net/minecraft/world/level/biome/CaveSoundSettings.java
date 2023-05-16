package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;

public class CaveSoundSettings {
   public static final Codec<CaveSoundSettings> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               SoundEffect.b.fieldOf("sound").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("tick_delay").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("block_search_extent").forGetter(var0x -> var0x.e),
               Codec.DOUBLE.fieldOf("offset").forGetter(var0x -> var0x.f)
            )
            .apply(var0, CaveSoundSettings::new)
   );
   public static final CaveSoundSettings b = new CaveSoundSettings(SoundEffects.h, 6000, 8, 2.0);
   private final Holder<SoundEffect> c;
   private final int d;
   private final int e;
   private final double f;

   public CaveSoundSettings(Holder<SoundEffect> var0, int var1, int var2, double var3) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
   }

   public Holder<SoundEffect> a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }

   public double d() {
      return this.f;
   }
}
