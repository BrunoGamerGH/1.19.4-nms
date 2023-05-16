package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;

public class RandomSpreadStructurePlacement extends StructurePlacement {
   public static final Codec<RandomSpreadStructurePlacement> a = RecordCodecBuilder.mapCodec(
         var0 -> a(var0)
               .and(
                  var0.group(
                     Codec.intRange(0, 4096).fieldOf("spacing").forGetter(RandomSpreadStructurePlacement::a),
                     Codec.intRange(0, 4096).fieldOf("separation").forGetter(RandomSpreadStructurePlacement::b),
                     RandomSpreadType.c.optionalFieldOf("spread_type", RandomSpreadType.a).forGetter(RandomSpreadStructurePlacement::c)
                  )
               )
               .apply(var0, RandomSpreadStructurePlacement::new)
      )
      .flatXmap(var0 -> var0.c <= var0.d ? DataResult.error(() -> "Spacing has to be larger than separation") : DataResult.success(var0), DataResult::success)
      .codec();
   private final int c;
   private final int d;
   private final RandomSpreadType e;

   public RandomSpreadStructurePlacement(
      BaseBlockPosition var0, StructurePlacement.c var1, float var2, int var3, Optional<StructurePlacement.a> var4, int var5, int var6, RandomSpreadType var7
   ) {
      super(var0, var1, var2, var3, var4);
      this.c = var5;
      this.d = var6;
      this.e = var7;
   }

   public RandomSpreadStructurePlacement(int var0, int var1, RandomSpreadType var2, int var3) {
      this(BaseBlockPosition.g, StructurePlacement.c.a, 1.0F, var3, Optional.empty(), var0, var1, var2);
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   public RandomSpreadType c() {
      return this.e;
   }

   public ChunkCoordIntPair a(long var0, int var2, int var3) {
      int var4 = Math.floorDiv(var2, this.c);
      int var5 = Math.floorDiv(var3, this.c);
      SeededRandom var6 = new SeededRandom(new LegacyRandomSource(0L));
      var6.a(var0, var4, var5, this.i());
      int var7 = this.c - this.d;
      int var8 = this.e.a(var6, var7);
      int var9 = this.e.a(var6, var7);
      return new ChunkCoordIntPair(var4 * this.c + var8, var5 * this.c + var9);
   }

   @Override
   protected boolean a(ChunkGeneratorStructureState var0, int var1, int var2) {
      ChunkCoordIntPair var3 = this.a(var0.d(), var1, var2);
      return var3.e == var1 && var3.f == var2;
   }

   @Override
   public StructurePlacementType<?> e() {
      return StructurePlacementType.a;
   }
}
