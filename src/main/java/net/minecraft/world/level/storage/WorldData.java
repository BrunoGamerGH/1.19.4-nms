package net.minecraft.world.level.storage;

import java.util.Locale;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelHeightAccessor;

public interface WorldData {
   int a();

   int b();

   int c();

   float d();

   long e();

   long f();

   boolean i();

   boolean k();

   void b(boolean var1);

   boolean n();

   GameRules q();

   EnumDifficulty s();

   boolean t();

   default void a(CrashReportSystemDetails var0, LevelHeightAccessor var1) {
      var0.a("Level spawn location", () -> CrashReportSystemDetails.a(var1, this.a(), this.b(), this.c()));
      var0.a("Level time", () -> String.format(Locale.ROOT, "%d game time, %d day time", this.e(), this.f()));
   }
}
