package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.datafixers.Products.P4;
import com.mojang.datafixers.Products.P5;
import com.mojang.datafixers.Products.P9;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;

public class ConcentricRingsStructurePlacement extends StructurePlacement {
   public static final Codec<ConcentricRingsStructurePlacement> a = RecordCodecBuilder.create(
      var0 -> b(var0).apply(var0, ConcentricRingsStructurePlacement::new)
   );
   private final int c;
   private final int d;
   private final int e;
   private final HolderSet<BiomeBase> f;

   private static P9<Mu<ConcentricRingsStructurePlacement>, BaseBlockPosition, StructurePlacement.c, Float, Integer, Optional<StructurePlacement.a>, Integer, Integer, Integer, HolderSet<BiomeBase>> b(
      Instance<ConcentricRingsStructurePlacement> var0
   ) {
      P5<Mu<ConcentricRingsStructurePlacement>, BaseBlockPosition, StructurePlacement.c, Float, Integer, Optional<StructurePlacement.a>> var1 = a(var0);
      P4<Mu<ConcentricRingsStructurePlacement>, Integer, Integer, Integer, HolderSet<BiomeBase>> var2 = var0.group(
         Codec.intRange(0, 1023).fieldOf("distance").forGetter(ConcentricRingsStructurePlacement::a),
         Codec.intRange(0, 1023).fieldOf("spread").forGetter(ConcentricRingsStructurePlacement::b),
         Codec.intRange(1, 4095).fieldOf("count").forGetter(ConcentricRingsStructurePlacement::c),
         RegistryCodecs.a(Registries.an).fieldOf("preferred_biomes").forGetter(ConcentricRingsStructurePlacement::d)
      );
      return new P9(var1.t1(), var1.t2(), var1.t3(), var1.t4(), var1.t5(), var2.t1(), var2.t2(), var2.t3(), var2.t4());
   }

   public ConcentricRingsStructurePlacement(
      BaseBlockPosition var0,
      StructurePlacement.c var1,
      float var2,
      int var3,
      Optional<StructurePlacement.a> var4,
      int var5,
      int var6,
      int var7,
      HolderSet<BiomeBase> var8
   ) {
      super(var0, var1, var2, var3, var4);
      this.c = var5;
      this.d = var6;
      this.e = var7;
      this.f = var8;
   }

   public ConcentricRingsStructurePlacement(int var0, int var1, int var2, HolderSet<BiomeBase> var3) {
      this(BaseBlockPosition.g, StructurePlacement.c.a, 1.0F, 0, Optional.empty(), var0, var1, var2, var3);
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }

   public HolderSet<BiomeBase> d() {
      return this.f;
   }

   @Override
   protected boolean a(ChunkGeneratorStructureState var0, int var1, int var2) {
      List<ChunkCoordIntPair> var3 = var0.a(this);
      return var3 == null ? false : var3.contains(new ChunkCoordIntPair(var1, var2));
   }

   @Override
   public StructurePlacementType<?> e() {
      return StructurePlacementType.b;
   }
}
