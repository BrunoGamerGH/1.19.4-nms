package net.minecraft.world.level.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class BulkSectionAccess implements AutoCloseable {
   private final GeneratorAccess a;
   private final Long2ObjectMap<ChunkSection> b = new Long2ObjectOpenHashMap();
   @Nullable
   private ChunkSection c;
   private long d;

   public BulkSectionAccess(GeneratorAccess var0) {
      this.a = var0;
   }

   @Nullable
   public ChunkSection a(BlockPosition var0) {
      int var1 = this.a.e(var0.v());
      if (var1 >= 0 && var1 < this.a.aj()) {
         long var2 = SectionPosition.c(var0);
         if (this.c == null || this.d != var2) {
            this.c = (ChunkSection)this.b.computeIfAbsent(var2, var2x -> {
               IChunkAccess var4 = this.a.a(SectionPosition.a(var0.u()), SectionPosition.a(var0.w()));
               ChunkSection var5 = var4.b(var1);
               var5.a();
               return var5;
            });
            this.d = var2;
         }

         return this.c;
      } else {
         return null;
      }
   }

   public IBlockData b(BlockPosition var0) {
      ChunkSection var1 = this.a(var0);
      if (var1 == null) {
         return Blocks.a.o();
      } else {
         int var2 = SectionPosition.b(var0.u());
         int var3 = SectionPosition.b(var0.v());
         int var4 = SectionPosition.b(var0.w());
         return var1.a(var2, var3, var4);
      }
   }

   @Override
   public void close() {
      ObjectIterator var1 = this.b.values().iterator();

      while(var1.hasNext()) {
         ChunkSection var1x = (ChunkSection)var1.next();
         var1x.b();
      }
   }
}
