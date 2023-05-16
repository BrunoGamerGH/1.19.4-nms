package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class PosRuleTestAxisAlignedLinear extends PosRuleTest {
   public static final Codec<PosRuleTestAxisAlignedLinear> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter(var0x -> var0x.b),
               Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("min_dist").orElse(0).forGetter(var0x -> var0x.e),
               Codec.INT.fieldOf("max_dist").orElse(0).forGetter(var0x -> var0x.f),
               EnumDirection.EnumAxis.e.fieldOf("axis").orElse(EnumDirection.EnumAxis.b).forGetter(var0x -> var0x.g)
            )
            .apply(var0, PosRuleTestAxisAlignedLinear::new)
   );
   private final float b;
   private final float d;
   private final int e;
   private final int f;
   private final EnumDirection.EnumAxis g;

   public PosRuleTestAxisAlignedLinear(float var0, float var1, int var2, int var3, EnumDirection.EnumAxis var4) {
      if (var2 >= var3) {
         throw new IllegalArgumentException("Invalid range: [" + var2 + "," + var3 + "]");
      } else {
         this.b = var0;
         this.d = var1;
         this.e = var2;
         this.f = var3;
         this.g = var4;
      }
   }

   @Override
   public boolean a(BlockPosition var0, BlockPosition var1, BlockPosition var2, RandomSource var3) {
      EnumDirection var4 = EnumDirection.a(EnumDirection.EnumAxisDirection.a, this.g);
      float var5 = (float)Math.abs((var1.u() - var2.u()) * var4.j());
      float var6 = (float)Math.abs((var1.v() - var2.v()) * var4.k());
      float var7 = (float)Math.abs((var1.w() - var2.w()) * var4.l());
      int var8 = (int)(var5 + var6 + var7);
      float var9 = var3.i();
      return var9 <= MathHelper.b(this.b, this.d, MathHelper.g((float)var8, (float)this.e, (float)this.f));
   }

   @Override
   protected PosRuleTestType<?> a() {
      return PosRuleTestType.c;
   }
}
