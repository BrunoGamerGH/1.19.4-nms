package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.BlockAccessAir;
import net.minecraft.world.level.BlockColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class NetherFossilStructure extends Structure {
   public static final Codec<NetherFossilStructure> d = RecordCodecBuilder.create(
      var0 -> var0.group(a(var0), HeightProvider.c.fieldOf("height").forGetter(var0x -> var0x.e)).apply(var0, NetherFossilStructure::new)
   );
   public final HeightProvider e;

   public NetherFossilStructure(Structure.c var0, HeightProvider var1) {
      super(var0);
      this.e = var1;
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      SeededRandom var1 = var0.f();
      int var2 = var0.h().d() + var1.a(16);
      int var3 = var0.h().e() + var1.a(16);
      int var4 = var0.b().e();
      WorldGenerationContext var5 = new WorldGenerationContext(var0.b(), var0.i());
      int var6 = this.e.a(var1, var5);
      BlockColumn var7 = var0.b().a(var2, var3, var0.i(), var0.d());
      BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition(var2, var6, var3);

      while(var6 > var4) {
         IBlockData var9 = var7.a(var6);
         IBlockData var10 = var7.a(--var6);
         if (var9.h() && (var10.a(Blocks.dW) || var10.d(BlockAccessAir.a, var8.q(var6), EnumDirection.b))) {
            break;
         }
      }

      if (var6 <= var4) {
         return Optional.empty();
      } else {
         BlockPosition var9 = new BlockPosition(var2, var6, var3);
         return Optional.of(new Structure.b(var9, (Consumer<StructurePiecesBuilder>)(var3x -> NetherFossilPieces.a(var0.e(), var3x, var1, var9))));
      }
   }

   @Override
   public StructureType<?> e() {
      return StructureType.i;
   }
}
