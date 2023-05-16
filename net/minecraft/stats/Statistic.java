package net.minecraft.stats;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.scores.criteria.IScoreboardCriteria;

public class Statistic<T> extends IScoreboardCriteria {
   private final Counter n;
   private final T o;
   private final StatisticWrapper<T> p;

   protected Statistic(StatisticWrapper<T> var0, T var1, Counter var2) {
      super(a(var0, var1));
      this.p = var0;
      this.n = var2;
      this.o = var1;
   }

   public static <T> String a(StatisticWrapper<T> var0, T var1) {
      return a(BuiltInRegistries.x.b(var0)) + ":" + a(var0.a().b(var1));
   }

   private static <T> String a(@Nullable MinecraftKey var0) {
      return var0.toString().replace(':', '.');
   }

   public StatisticWrapper<T> a() {
      return this.p;
   }

   public T b() {
      return this.o;
   }

   public String a(int var0) {
      return this.n.format(var0);
   }

   @Override
   public boolean equals(Object var0) {
      return this == var0 || var0 instanceof Statistic && Objects.equals(this.d(), ((Statistic)var0).d());
   }

   @Override
   public int hashCode() {
      return this.d().hashCode();
   }

   @Override
   public String toString() {
      return "Stat{name=" + this.d() + ", formatter=" + this.n + "}";
   }
}
