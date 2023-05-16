package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class PosRuleTestLinear extends PosRuleTest {
   public static final Codec<PosRuleTestLinear> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.b),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.e),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.f)
            )
            .apply(var0, PosRuleTestLinear::new)
   );
   private final float b;
   private final float d;
   private final int e;
   private final int f;

   public PosRuleTestLinear(float var0, float var1, int var2, int var3) {
      if (var2 >= var3) {
         throw new IllegalArgumentException("Invalid range: [" + var2 + "," + var3 + "]");
      } else {
         this.b = var0;
         this.d = var1;
         this.e = var2;
         this.f = var3;
      }
   }

   @Override
   public boolean a(BlockPosition var0, BlockPosition var1, BlockPosition var2, RandomSource var3) {
      int var4 = var1.k(var2);
      float var5 = var3.i();
      return var5 <= MathHelper.b(this.b, this.d, MathHelper.g((float)var4, (float)this.e, (float)this.f));
   }

   @Override
   protected PosRuleTestType<?> a() {
      return PosRuleTestType.b;
   }
}
