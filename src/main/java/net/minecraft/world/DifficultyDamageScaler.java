package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.MathHelper;

@Immutable
public class DifficultyDamageScaler {
   private static final float a = -72000.0F;
   private static final float b = 1440000.0F;
   private static final float c = 3600000.0F;
   private final EnumDifficulty d;
   private final float e;

   public DifficultyDamageScaler(EnumDifficulty var0, long var1, long var3, float var5) {
      this.d = var0;
      this.e = this.a(var0, var1, var3, var5);
   }

   public EnumDifficulty a() {
      return this.d;
   }

   public float b() {
      return this.e;
   }

   public boolean c() {
      return this.e >= (float)EnumDifficulty.d.ordinal();
   }

   public boolean a(float var0) {
      return this.e > var0;
   }

   public float d() {
      if (this.e < 2.0F) {
         return 0.0F;
      } else {
         return this.e > 4.0F ? 1.0F : (this.e - 2.0F) / 2.0F;
      }
   }

   private float a(EnumDifficulty var0, long var1, long var3, float var5) {
      if (var0 == EnumDifficulty.a) {
         return 0.0F;
      } else {
         boolean var6 = var0 == EnumDifficulty.d;
         float var7 = 0.75F;
         float var8 = MathHelper.a(((float)var1 + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         var7 += var8;
         float var9 = 0.0F;
         var9 += MathHelper.a((float)var3 / 3600000.0F, 0.0F, 1.0F) * (var6 ? 1.0F : 0.75F);
         var9 += MathHelper.a(var5 * 0.25F, 0.0F, var8);
         if (var0 == EnumDifficulty.b) {
            var9 *= 0.5F;
         }

         var7 += var9;
         return (float)var0.a() * var7;
      }
   }
}
