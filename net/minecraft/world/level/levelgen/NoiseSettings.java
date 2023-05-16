package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.dimension.DimensionManager;

public record NoiseSettings(int minY, int height, int noiseSizeHorizontal, int noiseSizeVertical) {
   private final int g;
   private final int h;
   private final int i;
   private final int j;
   public static final Codec<NoiseSettings> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.intRange(DimensionManager.e, DimensionManager.d).fieldOf("min_y").forGetter(NoiseSettings::c),
                  Codec.intRange(0, DimensionManager.c).fieldOf("height").forGetter(NoiseSettings::d),
                  Codec.intRange(1, 4).fieldOf("size_horizontal").forGetter(NoiseSettings::e),
                  Codec.intRange(1, 4).fieldOf("size_vertical").forGetter(NoiseSettings::f)
               )
               .apply(var0, NoiseSettings::new)
      )
      .comapFlatMap(NoiseSettings::a, Function.identity());
   protected static final NoiseSettings b = a(-64, 384, 1, 2);
   protected static final NoiseSettings c = a(0, 128, 1, 2);
   protected static final NoiseSettings d = a(0, 128, 2, 1);
   protected static final NoiseSettings e = a(-64, 192, 1, 2);
   protected static final NoiseSettings f = a(0, 256, 2, 1);

   public NoiseSettings(int var0, int var1, int var2, int var3) {
      this.g = var0;
      this.h = var1;
      this.i = var2;
      this.j = var3;
   }

   private static DataResult<NoiseSettings> a(NoiseSettings var0) {
      if (var0.c() + var0.d() > DimensionManager.d + 1) {
         return DataResult.error(() -> "min_y + height cannot be higher than: " + (DimensionManager.d + 1));
      } else if (var0.d() % 16 != 0) {
         return DataResult.error(() -> "height has to be a multiple of 16");
      } else {
         return var0.c() % 16 != 0 ? DataResult.error(() -> "min_y has to be a multiple of 16") : DataResult.success(var0);
      }
   }

   public static NoiseSettings a(int var0, int var1, int var2, int var3) {
      NoiseSettings var4 = new NoiseSettings(var0, var1, var2, var3);
      a(var4).error().ifPresent(var0x -> {
         throw new IllegalStateException(var0x.message());
      });
      return var4;
   }

   public int a() {
      return QuartPos.c(this.f());
   }

   public int b() {
      return QuartPos.c(this.e());
   }

   public NoiseSettings a(LevelHeightAccessor var0) {
      int var1 = Math.max(this.g, var0.v_());
      int var2 = Math.min(this.g + this.h, var0.ai()) - var1;
      return new NoiseSettings(var1, var2, this.i, this.j);
   }

   public int c() {
      return this.g;
   }

   public int d() {
      return this.h;
   }

   public int e() {
      return this.i;
   }

   public int f() {
      return this.j;
   }
}
