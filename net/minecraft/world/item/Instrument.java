package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.ExtraCodecs;

public record Instrument(Holder<SoundEffect> soundEvent, int useDuration, float range) {
   private final Holder<SoundEffect> b;
   private final int c;
   private final float d;
   public static final Codec<Instrument> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               SoundEffect.b.fieldOf("sound_event").forGetter(Instrument::a),
               ExtraCodecs.i.fieldOf("use_duration").forGetter(Instrument::b),
               ExtraCodecs.j.fieldOf("range").forGetter(Instrument::c)
            )
            .apply(var0, Instrument::new)
   );

   public Instrument(Holder<SoundEffect> var0, int var1, float var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }

   public Holder<SoundEffect> a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public float c() {
      return this.d;
   }
}
