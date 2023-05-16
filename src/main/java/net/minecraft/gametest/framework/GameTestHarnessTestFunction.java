package net.minecraft.gametest.framework;

import java.util.function.Consumer;
import net.minecraft.world.level.block.EnumBlockRotation;

public class GameTestHarnessTestFunction {
   private final String a;
   private final String b;
   private final String c;
   private final boolean d;
   private final int e;
   private final int f;
   private final Consumer<GameTestHarnessHelper> g;
   private final int h;
   private final long i;
   private final EnumBlockRotation j;

   public GameTestHarnessTestFunction(String var0, String var1, String var2, int var3, long var4, boolean var6, Consumer<GameTestHarnessHelper> var7) {
      this(var0, var1, var2, EnumBlockRotation.a, var3, var4, var6, 1, 1, var7);
   }

   public GameTestHarnessTestFunction(
      String var0, String var1, String var2, EnumBlockRotation var3, int var4, long var5, boolean var7, Consumer<GameTestHarnessHelper> var8
   ) {
      this(var0, var1, var2, var3, var4, var5, var7, 1, 1, var8);
   }

   public GameTestHarnessTestFunction(
      String var0,
      String var1,
      String var2,
      EnumBlockRotation var3,
      int var4,
      long var5,
      boolean var7,
      int var8,
      int var9,
      Consumer<GameTestHarnessHelper> var10
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.j = var3;
      this.h = var4;
      this.d = var7;
      this.f = var8;
      this.e = var9;
      this.g = var10;
      this.i = var5;
   }

   public void a(GameTestHarnessHelper var0) {
      this.g.accept(var0);
   }

   public String a() {
      return this.b;
   }

   public String b() {
      return this.c;
   }

   @Override
   public String toString() {
      return this.b;
   }

   public int c() {
      return this.h;
   }

   public boolean d() {
      return this.d;
   }

   public String e() {
      return this.a;
   }

   public long f() {
      return this.i;
   }

   public EnumBlockRotation g() {
      return this.j;
   }

   public boolean h() {
      return this.e > 1;
   }

   public int i() {
      return this.e;
   }

   public int j() {
      return this.f;
   }
}
