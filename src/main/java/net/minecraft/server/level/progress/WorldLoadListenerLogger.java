package net.minecraft.server.level.progress;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.slf4j.Logger;

public class WorldLoadListenerLogger implements WorldLoadListener {
   private static final Logger a = LogUtils.getLogger();
   private final int b;
   private int c;
   private long d;
   private long e = Long.MAX_VALUE;

   public WorldLoadListenerLogger(int var0) {
      int var1 = var0 * 2 + 1;
      this.b = var1 * var1;
   }

   @Override
   public void a(ChunkCoordIntPair var0) {
      this.e = SystemUtils.b();
      this.d = this.e;
   }

   @Override
   public void a(ChunkCoordIntPair var0, @Nullable ChunkStatus var1) {
      if (var1 == ChunkStatus.o) {
         ++this.c;
      }

      int var2 = this.c();
      if (SystemUtils.b() > this.e) {
         this.e += 500L;
         a.info(IChatBaseComponent.a("menu.preparingSpawn", MathHelper.a(var2, 0, 100)).getString());
      }
   }

   @Override
   public void a() {
   }

   @Override
   public void b() {
      a.info("Time elapsed: {} ms", SystemUtils.b() - this.d);
      this.e = Long.MAX_VALUE;
   }

   public int c() {
      return MathHelper.d((float)this.c * 100.0F / (float)this.b);
   }
}
