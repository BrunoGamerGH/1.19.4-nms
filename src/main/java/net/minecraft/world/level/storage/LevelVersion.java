package net.minecraft.world.level.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.SharedConstants;

public class LevelVersion {
   private final int a;
   private final long b;
   private final String c;
   private final DataVersion d;
   private final boolean e;

   private LevelVersion(int var0, long var1, String var3, int var4, String var5, boolean var6) {
      this.a = var0;
      this.b = var1;
      this.c = var3;
      this.d = new DataVersion(var4, var5);
      this.e = var6;
   }

   public static LevelVersion a(Dynamic<?> var0) {
      int var1 = var0.get("version").asInt(0);
      long var2 = var0.get("LastPlayed").asLong(0L);
      OptionalDynamic<?> var4 = var0.get("Version");
      return var4.result().isPresent()
         ? new LevelVersion(
            var1,
            var2,
            var4.get("Name").asString(SharedConstants.b().c()),
            var4.get("Id").asInt(SharedConstants.b().d().c()),
            var4.get("Series").asString(DataVersion.a),
            var4.get("Snapshot").asBoolean(!SharedConstants.b().g())
         )
         : new LevelVersion(var1, var2, "", 0, DataVersion.a, false);
   }

   public int a() {
      return this.a;
   }

   public long b() {
      return this.b;
   }

   public String c() {
      return this.c;
   }

   public DataVersion d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }
}
