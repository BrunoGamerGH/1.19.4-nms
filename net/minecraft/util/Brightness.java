package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Brightness(int block, int sky) {
   private final int d;
   private final int e;
   public static final Codec<Integer> a = ExtraCodecs.a(0, 15);
   public static final Codec<Brightness> b = RecordCodecBuilder.create(
      var0 -> var0.group(a.fieldOf("block").forGetter(Brightness::b), a.fieldOf("sky").forGetter(Brightness::c)).apply(var0, Brightness::new)
   );
   public static Brightness c = new Brightness(15, 15);

   public Brightness(int var0, int var1) {
      this.d = var0;
      this.e = var1;
   }

   public int a() {
      return this.d << 4 | this.e << 20;
   }

   public static Brightness a(int var0) {
      int var1 = var0 >> 4 & 65535;
      int var2 = var0 >> 20 & 65535;
      return new Brightness(var1, var2);
   }

   public int b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }
}
