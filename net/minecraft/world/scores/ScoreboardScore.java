package net.minecraft.world.scores;

import java.util.Comparator;
import javax.annotation.Nullable;

public class ScoreboardScore {
   public static final Comparator<ScoreboardScore> a = (var0, var1) -> {
      if (var0.b() > var1.b()) {
         return 1;
      } else {
         return var0.b() < var1.b() ? -1 : var1.e().compareToIgnoreCase(var0.e());
      }
   };
   private final Scoreboard b;
   @Nullable
   private final ScoreboardObjective c;
   private final String d;
   private int e;
   private boolean f;
   private boolean g;

   public ScoreboardScore(Scoreboard var0, ScoreboardObjective var1, String var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.f = true;
      this.g = true;
   }

   public void a(int var0) {
      if (this.c.c().e()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.b(this.b() + var0);
      }
   }

   public void a() {
      this.a(1);
   }

   public int b() {
      return this.e;
   }

   public void c() {
      this.b(0);
   }

   public void b(int var0) {
      int var1 = this.e;
      this.e = var0;
      if (var1 != var0 || this.g) {
         this.g = false;
         this.f().a(this);
      }
   }

   @Nullable
   public ScoreboardObjective d() {
      return this.c;
   }

   public String e() {
      return this.d;
   }

   public Scoreboard f() {
      return this.b;
   }

   public boolean g() {
      return this.f;
   }

   public void a(boolean var0) {
      this.f = var0;
   }
}
