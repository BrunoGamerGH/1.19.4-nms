package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class NetherFortressStructure extends Structure {
   public static final WeightedRandomList<BiomeSettingsMobs.c> d = WeightedRandomList.a(
      new BiomeSettingsMobs.c(EntityTypes.i, 10, 2, 3),
      new BiomeSettingsMobs.c(EntityTypes.bs, 5, 4, 4),
      new BiomeSettingsMobs.c(EntityTypes.bl, 8, 5, 5),
      new BiomeSettingsMobs.c(EntityTypes.aJ, 2, 5, 5),
      new BiomeSettingsMobs.c(EntityTypes.al, 3, 4, 4)
   );
   public static final Codec<NetherFortressStructure> e = a(NetherFortressStructure::new);

   public NetherFortressStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      ChunkCoordIntPair var1 = var0.h();
      BlockPosition var2 = new BlockPosition(var1.d(), 64, var1.e());
      return Optional.of(new Structure.b(var2, (Consumer<StructurePiecesBuilder>)(var1x -> a(var1x, var0))));
   }

   private static void a(StructurePiecesBuilder var0, Structure.a var1) {
      NetherFortressPieces.q var2 = new NetherFortressPieces.q(var1.f(), var1.h().a(2), var1.h().b(2));
      var0.a(var2);
      var2.a(var2, var0, var1.f());
      List<StructurePiece> var3 = var2.d;

      while(!var3.isEmpty()) {
         int var4 = var1.f().a(var3.size());
         StructurePiece var5 = var3.remove(var4);
         var5.a(var2, var0, var1.f());
      }

      var0.a(var1.f(), 48, 70);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.d;
   }
}
