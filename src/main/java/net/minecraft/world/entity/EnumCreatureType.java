package net.minecraft.world.entity;

import com.mojang.serialization.Codec;
import net.minecraft.util.INamable;

public enum EnumCreatureType implements INamable {
   a("monster", 70, false, false, 128),
   b("creature", 10, true, true, 128),
   c("ambient", 15, true, false, 128),
   d("axolotls", 5, true, false, 128),
   e("underground_water_creature", 5, true, false, 128),
   f("water_creature", 5, true, false, 128),
   g("water_ambient", 20, true, false, 64),
   h("misc", -1, true, true, 128);

   public static final Codec<EnumCreatureType> i = INamable.a(EnumCreatureType::values);
   private final int j;
   private final boolean k;
   private final boolean l;
   private final String m;
   private final int n = 32;
   private final int o;

   private EnumCreatureType(String var2, int var3, boolean var4, boolean var5, int var6) {
      this.m = var2;
      this.j = var3;
      this.k = var4;
      this.l = var5;
      this.o = var6;
   }

   public String a() {
      return this.m;
   }

   @Override
   public String c() {
      return this.m;
   }

   public int b() {
      return this.j;
   }

   public boolean d() {
      return this.k;
   }

   public boolean e() {
      return this.l;
   }

   public int f() {
      return this.o;
   }

   public int g() {
      return 32;
   }
}
