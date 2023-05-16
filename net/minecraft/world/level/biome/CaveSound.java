package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEffect;

public class CaveSound {
   public static final Codec<CaveSound> a = RecordCodecBuilder.create(
      var0 -> var0.group(SoundEffect.b.fieldOf("sound").forGetter(var0x -> var0x.b), Codec.DOUBLE.fieldOf("tick_chance").forGetter(var0x -> var0x.c))
            .apply(var0, CaveSound::new)
   );
   private final Holder<SoundEffect> b;
   private final double c;

   public CaveSound(Holder<SoundEffect> var0, double var1) {
      this.b = var0;
      this.c = var1;
   }

   public Holder<SoundEffect> a() {
      return this.b;
   }

   public double b() {
      return this.c;
   }
}
