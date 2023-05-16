package net.minecraft.server.level;

import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.levelgen.HeightMap;

public class WorldProviderNormal {
   @Nullable
   protected static BlockPosition a(WorldServer var0, int var1, int var2) {
      boolean var3 = var0.q_().h();
      Chunk var4 = var0.d(SectionPosition.a(var1), SectionPosition.a(var2));
      int var5 = var3 ? var0.k().g().a(var0) : var4.a(HeightMap.Type.e, var1 & 15, var2 & 15);
      if (var5 < var0.v_()) {
         return null;
      } else {
         int var6 = var4.a(HeightMap.Type.b, var1 & 15, var2 & 15);
         if (var6 <= var5 && var6 > var4.a(HeightMap.Type.d, var1 & 15, var2 & 15)) {
            return null;
         } else {
            BlockPosition.MutableBlockPosition var7 = new BlockPosition.MutableBlockPosition();

            for(int var8 = var5 + 1; var8 >= var0.v_(); --var8) {
               var7.d(var1, var8, var2);
               IBlockData var9 = var0.a_(var7);
               if (!var9.r().c()) {
                  break;
               }

               if (Block.a(var9.k(var0, var7), EnumDirection.b)) {
                  return var7.c().i();
               }
            }

            return null;
         }
      }
   }

   @Nullable
   public static BlockPosition a(WorldServer var0, ChunkCoordIntPair var1) {
      if (SharedConstants.a(var1)) {
         return null;
      } else {
         for(int var2 = var1.d(); var2 <= var1.f(); ++var2) {
            for(int var3 = var1.e(); var3 <= var1.g(); ++var3) {
               BlockPosition var4 = a(var0, var2, var3);
               if (var4 != null) {
                  return var4;
               }
            }
         }

         return null;
      }
   }
}
