package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.chunk.ILightAccess;
import net.minecraft.world.level.chunk.NibbleArray;

public class LightEngineStorageBlock extends LightEngineStorage<LightEngineStorageBlock.a> {
   protected LightEngineStorageBlock(ILightAccess var0) {
      super(EnumSkyBlock.b, var0, new LightEngineStorageBlock.a(new Long2ObjectOpenHashMap()));
   }

   @Override
   protected int d(long var0) {
      long var2 = SectionPosition.e(var0);
      NibbleArray var4 = this.a(var2, false);
      return var4 == null
         ? 0
         : var4.a(SectionPosition.b(BlockPosition.a(var0)), SectionPosition.b(BlockPosition.b(var0)), SectionPosition.b(BlockPosition.c(var0)));
   }

   protected static final class a extends LightEngineStorageArray<LightEngineStorageBlock.a> {
      public a(Long2ObjectOpenHashMap<NibbleArray> var0) {
         super(var0);
      }

      public LightEngineStorageBlock.a a() {
         return new LightEngineStorageBlock.a(this.a.clone());
      }
   }
}
