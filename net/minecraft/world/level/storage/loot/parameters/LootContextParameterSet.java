package net.minecraft.world.level.storage.loot.parameters;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootItemUser;

public class LootContextParameterSet {
   private final Set<LootContextParameter<?>> a;
   private final Set<LootContextParameter<?>> b;

   LootContextParameterSet(Set<LootContextParameter<?>> var0, Set<LootContextParameter<?>> var1) {
      this.a = ImmutableSet.copyOf(var0);
      this.b = ImmutableSet.copyOf(Sets.union(var0, var1));
   }

   public boolean a(LootContextParameter<?> var0) {
      return this.b.contains(var0);
   }

   public Set<LootContextParameter<?>> a() {
      return this.a;
   }

   public Set<LootContextParameter<?>> b() {
      return this.b;
   }

   @Override
   public String toString() {
      return "[" + Joiner.on(", ").join(this.b.stream().map(var0 -> (this.a.contains(var0) ? "!" : "") + var0.a()).iterator()) + "]";
   }

   public void a(LootCollector var0, LootItemUser var1) {
      Set<LootContextParameter<?>> var2 = var1.b();
      Set<LootContextParameter<?>> var3 = Sets.difference(var2, this.b);
      if (!var3.isEmpty()) {
         var0.a("Parameters " + var3 + " are not provided in this context");
      }
   }

   public static LootContextParameterSet.Builder c() {
      return new LootContextParameterSet.Builder();
   }

   public static class Builder {
      private final Set<LootContextParameter<?>> a = Sets.newIdentityHashSet();
      private final Set<LootContextParameter<?>> b = Sets.newIdentityHashSet();

      public LootContextParameterSet.Builder a(LootContextParameter<?> var0) {
         if (this.b.contains(var0)) {
            throw new IllegalArgumentException("Parameter " + var0.a() + " is already optional");
         } else {
            this.a.add(var0);
            return this;
         }
      }

      public LootContextParameterSet.Builder b(LootContextParameter<?> var0) {
         if (this.a.contains(var0)) {
            throw new IllegalArgumentException("Parameter " + var0.a() + " is already required");
         } else {
            this.b.add(var0);
            return this;
         }
      }

      public LootContextParameterSet a() {
         return new LootContextParameterSet(this.a, this.b);
      }
   }
}
