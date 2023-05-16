package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class ConstantHeight extends HeightProvider {
   public static final ConstantHeight a = new ConstantHeight(VerticalAnchor.a(0));
   public static final Codec<ConstantHeight> b = Codec.either(
         VerticalAnchor.a,
         RecordCodecBuilder.create(var0 -> var0.group(VerticalAnchor.a.fieldOf("value").forGetter(var0x -> var0x.d)).apply(var0, ConstantHeight::new))
      )
      .xmap(var0 -> (ConstantHeight)var0.map(ConstantHeight::a, var0x -> var0x), var0 -> Either.left(var0.d));
   private final VerticalAnchor d;

   public static ConstantHeight a(VerticalAnchor var0) {
      return new ConstantHeight(var0);
   }

   private ConstantHeight(VerticalAnchor var0) {
      this.d = var0;
   }

   public VerticalAnchor b() {
      return this.d;
   }

   @Override
   public int a(RandomSource var0, WorldGenerationContext var1) {
      return this.d.a(var1);
   }

   @Override
   public HeightProviderType<?> a() {
      return HeightProviderType.a;
   }

   @Override
   public String toString() {
      return this.d.toString();
   }
}
