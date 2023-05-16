package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.net.SocketAddress;
import java.nio.file.Path;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import org.slf4j.Logger;

public interface JvmProfiler {
   JvmProfiler e = (JvmProfiler)(Runtime.class.getModule().getLayer().findModule("jdk.jfr").isPresent() ? JfrProfiler.a() : new JvmProfiler.a());

   boolean a(Environment var1);

   Path b();

   boolean c();

   boolean d();

   void a(float var1);

   void a(int var1, int var2, SocketAddress var3, int var4);

   void b(int var1, int var2, SocketAddress var3, int var4);

   @Nullable
   ProfiledDuration e();

   @Nullable
   ProfiledDuration a(ChunkCoordIntPair var1, ResourceKey<World> var2, String var3);

   public static class a implements JvmProfiler {
      private static final Logger b = LogUtils.getLogger();
      static final ProfiledDuration a = () -> {
      };

      @Override
      public boolean a(Environment var0) {
         b.warn("Attempted to start Flight Recorder, but it's not supported on this JVM");
         return false;
      }

      @Override
      public Path b() {
         throw new IllegalStateException("Attempted to stop Flight Recorder, but it's not supported on this JVM");
      }

      @Override
      public boolean c() {
         return false;
      }

      @Override
      public boolean d() {
         return false;
      }

      @Override
      public void a(int var0, int var1, SocketAddress var2, int var3) {
      }

      @Override
      public void b(int var0, int var1, SocketAddress var2, int var3) {
      }

      @Override
      public void a(float var0) {
      }

      @Override
      public ProfiledDuration e() {
         return a;
      }

      @Nullable
      @Override
      public ProfiledDuration a(ChunkCoordIntPair var0, ResourceKey<World> var1, String var2) {
         return null;
      }
   }
}
