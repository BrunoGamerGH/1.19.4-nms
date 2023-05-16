package net.minecraft.world.entity;

import java.util.function.Consumer;
import net.minecraft.util.MathHelper;

public class AnimationState {
   private static final long a = Long.MAX_VALUE;
   private long b = Long.MAX_VALUE;
   private long c;

   public void a(int var0) {
      this.b = (long)var0 * 1000L / 20L;
      this.c = 0L;
   }

   public void b(int var0) {
      if (!this.c()) {
         this.a(var0);
      }
   }

   public void a(boolean var0, int var1) {
      if (var0) {
         this.b(var1);
      } else {
         this.a();
      }
   }

   public void a() {
      this.b = Long.MAX_VALUE;
   }

   public void a(Consumer<AnimationState> var0) {
      if (this.c()) {
         var0.accept(this);
      }
   }

   public void a(float var0, float var1) {
      if (this.c()) {
         long var2 = MathHelper.b((double)(var0 * 1000.0F / 20.0F));
         this.c += (long)((float)(var2 - this.b) * var1);
         this.b = var2;
      }
   }

   public long b() {
      return this.c;
   }

   public boolean c() {
      return this.b != Long.MAX_VALUE;
   }
}
