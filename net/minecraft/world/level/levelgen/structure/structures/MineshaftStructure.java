package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.IntFunction;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.INamable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class MineshaftStructure extends Structure {
   public static final Codec<MineshaftStructure> d = RecordCodecBuilder.create(
      var0 -> var0.group(a(var0), MineshaftStructure.a.c.fieldOf("mineshaft_type").forGetter(var0x -> var0x.e)).apply(var0, MineshaftStructure::new)
   );
   private final MineshaftStructure.a e;

   public MineshaftStructure(Structure.c var0, MineshaftStructure.a var1) {
      super(var0);
      this.e = var1;
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      var0.f().j();
      ChunkCoordIntPair var1 = var0.h();
      BlockPosition var2 = new BlockPosition(var1.b(), 50, var1.e());
      StructurePiecesBuilder var3 = new StructurePiecesBuilder();
      int var4 = this.a(var3, var0);
      return Optional.of(new Structure.b(var2.b(0, var4, 0), Either.right(var3)));
   }

   private int a(StructurePiecesBuilder var0, Structure.a var1) {
      ChunkCoordIntPair var2 = var1.h();
      SeededRandom var3 = var1.f();
      ChunkGenerator var4 = var1.b();
      MineshaftPieces.d var5 = new MineshaftPieces.d(0, var3, var2.a(2), var2.b(2), this.e);
      var0.a(var5);
      var5.a(var5, var0, var3);
      int var6 = var4.e();
      if (this.e == MineshaftStructure.a.b) {
         BlockPosition var7 = var0.d().f();
         int var8 = var4.a(var7.u(), var7.w(), HeightMap.Type.a, var1.i(), var1.d());
         int var9 = var8 <= var6 ? var6 : MathHelper.b(var3, var6, var8);
         int var10 = var9 - var7.v();
         var0.a(var10);
         return var10;
      } else {
         return var0.a(var6, var4.f(), var3, 10);
      }
   }

   @Override
   public StructureType<?> e() {
      return StructureType.h;
   }

   public static enum a implements INamable {
      a("normal", Blocks.T, Blocks.n, Blocks.dT),
      b("mesa", Blocks.Z, Blocks.t, Blocks.kh);

      public static final Codec<MineshaftStructure.a> c = INamable.a(MineshaftStructure.a::values);
      private static final IntFunction<MineshaftStructure.a> d = ByIdMap.a(Enum::ordinal, values(), ByIdMap.a.a);
      private final String e;
      private final IBlockData f;
      private final IBlockData g;
      private final IBlockData h;

      private a(String var2, Block var3, Block var4, Block var5) {
         this.e = var2;
         this.f = var3.o();
         this.g = var4.o();
         this.h = var5.o();
      }

      public String a() {
         return this.e;
      }

      public static MineshaftStructure.a a(int var0) {
         return d.apply(var0);
      }

      public IBlockData b() {
         return this.f;
      }

      public IBlockData d() {
         return this.g;
      }

      public IBlockData e() {
         return this.h;
      }

      @Override
      public String c() {
         return this.e;
      }
   }
}
