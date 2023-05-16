package net.minecraft.world.entity.ai.memory;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.util.VisibleForDebug;

public class ExpirableMemory<T> {
   private final T a;
   private long b;

   public ExpirableMemory(T var0, long var1) {
      this.a = var0;
      this.b = var1;
   }

   public void a() {
      if (this.e()) {
         --this.b;
      }
   }

   public static <T> ExpirableMemory<T> a(T var0) {
      return new ExpirableMemory<>(var0, Long.MAX_VALUE);
   }

   public static <T> ExpirableMemory<T> a(T var0, long var1) {
      return new ExpirableMemory<>(var0, var1);
   }

   public long b() {
      return this.b;
   }

   public T c() {
      return this.a;
   }

   public boolean d() {
      return this.b <= 0L;
   }

   @Override
   public String toString() {
      return this.a + (this.e() ? " (ttl: " + this.b + ")" : "");
   }

   @VisibleForDebug
   public boolean e() {
      return this.b != Long.MAX_VALUE;
   }

   public static <T> Codec<ExpirableMemory<T>> a(Codec<T> var0) {
      return RecordCodecBuilder.create(
         var1 -> var1.group(
                  var0.fieldOf("value").forGetter(var0xx -> var0xx.a),
                  Codec.LONG.optionalFieldOf("ttl").forGetter(var0xx -> var0xx.e() ? Optional.of(var0xx.b) : Optional.empty())
               )
               .apply(var1, (var0xx, var1x) -> new ExpirableMemory<>(var0xx, var1x.orElse(Long.MAX_VALUE)))
      );
   }
}
