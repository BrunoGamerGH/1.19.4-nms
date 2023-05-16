package net.minecraft.world.level.storage.loot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootCollector {
   private final Multimap<String, String> a;
   private final Supplier<String> b;
   private final LootContextParameterSet c;
   private final Function<MinecraftKey, LootItemCondition> d;
   private final Set<MinecraftKey> e;
   private final Function<MinecraftKey, LootTable> f;
   private final Set<MinecraftKey> g;
   private String h;

   public LootCollector(LootContextParameterSet var0, Function<MinecraftKey, LootItemCondition> var1, Function<MinecraftKey, LootTable> var2) {
      this(HashMultimap.create(), () -> "", var0, var1, ImmutableSet.of(), var2, ImmutableSet.of());
   }

   public LootCollector(
      Multimap<String, String> var0,
      Supplier<String> var1,
      LootContextParameterSet var2,
      Function<MinecraftKey, LootItemCondition> var3,
      Set<MinecraftKey> var4,
      Function<MinecraftKey, LootTable> var5,
      Set<MinecraftKey> var6
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
   }

   private String b() {
      if (this.h == null) {
         this.h = this.b.get();
      }

      return this.h;
   }

   public void a(String var0) {
      this.a.put(this.b(), var0);
   }

   public LootCollector b(String var0) {
      return new LootCollector(this.a, () -> this.b() + var0, this.c, this.d, this.e, this.f, this.g);
   }

   public LootCollector a(String var0, MinecraftKey var1) {
      ImmutableSet<MinecraftKey> var2 = ImmutableSet.builder().addAll(this.g).add(var1).build();
      return new LootCollector(this.a, () -> this.b() + var0, this.c, this.d, this.e, this.f, var2);
   }

   public LootCollector b(String var0, MinecraftKey var1) {
      ImmutableSet<MinecraftKey> var2 = ImmutableSet.builder().addAll(this.e).add(var1).build();
      return new LootCollector(this.a, () -> this.b() + var0, this.c, this.d, var2, this.f, this.g);
   }

   public boolean a(MinecraftKey var0) {
      return this.g.contains(var0);
   }

   public boolean b(MinecraftKey var0) {
      return this.e.contains(var0);
   }

   public Multimap<String, String> a() {
      return ImmutableMultimap.copyOf(this.a);
   }

   public void a(LootItemUser var0) {
      this.c.a(this, var0);
   }

   @Nullable
   public LootTable c(MinecraftKey var0) {
      return this.f.apply(var0);
   }

   @Nullable
   public LootItemCondition d(MinecraftKey var0) {
      return this.d.apply(var0);
   }

   public LootCollector a(LootContextParameterSet var0) {
      return new LootCollector(this.a, this.b, var0, this.d, this.e, this.f, this.g);
   }
}
