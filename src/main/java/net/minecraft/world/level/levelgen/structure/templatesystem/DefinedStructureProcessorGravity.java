package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.levelgen.HeightMap;

public class DefinedStructureProcessorGravity extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorGravity> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               HeightMap.Type.g.fieldOf("heightmap").orElse(HeightMap.Type.a).forGetter(var0x -> var0x.b),
               Codec.INT.fieldOf("offset").orElse(0).forGetter(var0x -> var0x.c)
            )
            .apply(var0, DefinedStructureProcessorGravity::new)
   );
   private final HeightMap.Type b;
   private final int c;

   public DefinedStructureProcessorGravity(HeightMap.Type var0, int var1) {
      this.b = var0;
      this.c = var1;
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      HeightMap.Type var6;
      if (var0 instanceof WorldServer) {
         if (this.b == HeightMap.Type.a) {
            var6 = HeightMap.Type.b;
         } else if (this.b == HeightMap.Type.c) {
            var6 = HeightMap.Type.d;
         } else {
            var6 = this.b;
         }
      } else {
         var6 = this.b;
      }

      int var7 = var0.a(var6, var4.a.u(), var4.a.w()) + this.c;
      int var8 = var3.a.v();
      return new DefinedStructure.BlockInfo(new BlockPosition(var4.a.u(), var7 + var8, var4.a.w()), var4.b, var4.c);
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.c;
   }
}
