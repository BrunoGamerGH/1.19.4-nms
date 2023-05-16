package net.minecraft.world.level;

import com.mojang.serialization.Dynamic;
import net.minecraft.world.EnumDifficulty;

public final class WorldSettings {
   public String a;
   private final EnumGamemode b;
   public boolean c;
   private final EnumDifficulty d;
   private final boolean e;
   private final GameRules f;
   private final WorldDataConfiguration g;

   public WorldSettings(String var0, EnumGamemode var1, boolean var2, EnumDifficulty var3, boolean var4, GameRules var5, WorldDataConfiguration var6) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
   }

   public static WorldSettings a(Dynamic<?> var0, WorldDataConfiguration var1) {
      EnumGamemode var2 = EnumGamemode.a(var0.get("GameType").asInt(0));
      return new WorldSettings(
         var0.get("LevelName").asString(""),
         var2,
         var0.get("hardcore").asBoolean(false),
         var0.get("Difficulty").asNumber().map(var0x -> EnumDifficulty.a(var0x.byteValue())).result().orElse(EnumDifficulty.c),
         var0.get("allowCommands").asBoolean(var2 == EnumGamemode.b),
         new GameRules(var0.get("GameRules")),
         var1
      );
   }

   public String a() {
      return this.a;
   }

   public EnumGamemode b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }

   public EnumDifficulty d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }

   public GameRules f() {
      return this.f;
   }

   public WorldDataConfiguration g() {
      return this.g;
   }

   public WorldSettings a(EnumGamemode var0) {
      return new WorldSettings(this.a, var0, this.c, this.d, this.e, this.f, this.g);
   }

   public WorldSettings a(EnumDifficulty var0) {
      return new WorldSettings(this.a, this.b, this.c, var0, this.e, this.f, this.g);
   }

   public WorldSettings a(WorldDataConfiguration var0) {
      return new WorldSettings(this.a, this.b, this.c, this.d, this.e, this.f, var0);
   }

   public WorldSettings h() {
      return new WorldSettings(this.a, this.b, this.c, this.d, this.e, this.f.b(), this.g);
   }
}
