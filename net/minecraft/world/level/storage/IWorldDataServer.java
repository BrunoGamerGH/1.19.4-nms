package net.minecraft.world.level.storage;

import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.timers.CustomFunctionCallbackTimerQueue;

public interface IWorldDataServer extends WorldDataMutable {
   @Override
   String g();

   void a(boolean var1);

   int l();

   void f(int var1);

   void e(int var1);

   int j();

   @Override
   default void a(CrashReportSystemDetails var0, LevelHeightAccessor var1) {
      WorldDataMutable.super.a(var0, var1);
      var0.a("Level name", this::g);
      var0.a(
         "Level game mode",
         () -> String.format(Locale.ROOT, "Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", this.m().b(), this.m().a(), this.n(), this.o())
      );
      var0.a("Level weather", () -> String.format(Locale.ROOT, "Rain time: %d (now: %b), thunder time: %d (now: %b)", this.l(), this.k(), this.j(), this.i()));
   }

   int h();

   void a(int var1);

   int v();

   void g(int var1);

   int w();

   void h(int var1);

   @Nullable
   UUID x();

   void a(UUID var1);

   EnumGamemode m();

   void a(WorldBorder.c var1);

   WorldBorder.c r();

   boolean p();

   void c(boolean var1);

   boolean o();

   void a(EnumGamemode var1);

   CustomFunctionCallbackTimerQueue<MinecraftServer> u();

   void a(long var1);

   void b(long var1);
}
