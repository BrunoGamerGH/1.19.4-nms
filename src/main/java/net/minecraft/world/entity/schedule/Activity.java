package net.minecraft.world.entity.schedule;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class Activity {
   public static final Activity a = a("core");
   public static final Activity b = a("idle");
   public static final Activity c = a("work");
   public static final Activity d = a("play");
   public static final Activity e = a("rest");
   public static final Activity f = a("meet");
   public static final Activity g = a("panic");
   public static final Activity h = a("raid");
   public static final Activity i = a("pre_raid");
   public static final Activity j = a("hide");
   public static final Activity k = a("fight");
   public static final Activity l = a("celebrate");
   public static final Activity m = a("admire_item");
   public static final Activity n = a("avoid");
   public static final Activity o = a("ride");
   public static final Activity p = a("play_dead");
   public static final Activity q = a("long_jump");
   public static final Activity r = a("ram");
   public static final Activity s = a("tongue");
   public static final Activity t = a("swim");
   public static final Activity u = a("lay_spawn");
   public static final Activity v = a("sniff");
   public static final Activity w = a("investigate");
   public static final Activity x = a("roar");
   public static final Activity y = a("emerge");
   public static final Activity z = a("dig");
   private final String A;
   private final int B;

   private Activity(String var0) {
      this.A = var0;
      this.B = var0.hashCode();
   }

   public String a() {
      return this.A;
   }

   private static Activity a(String var0) {
      return IRegistry.a(BuiltInRegistries.E, var0, new Activity(var0));
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         Activity var1 = (Activity)var0;
         return this.A.equals(var1.A);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.B;
   }

   @Override
   public String toString() {
      return this.a();
   }
}
