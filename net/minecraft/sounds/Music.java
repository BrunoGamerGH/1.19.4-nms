package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

public class Music {
   public static final Codec<Music> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               SoundEffect.b.fieldOf("sound").forGetter(var0x -> var0x.b),
               Codec.INT.fieldOf("min_delay").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("max_delay").forGetter(var0x -> var0x.d),
               Codec.BOOL.fieldOf("replace_current_music").forGetter(var0x -> var0x.e)
            )
            .apply(var0, Music::new)
   );
   private final Holder<SoundEffect> b;
   private final int c;
   private final int d;
   private final boolean e;

   public Music(Holder<SoundEffect> var0, int var1, int var2, boolean var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public Holder<SoundEffect> a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }
}
