package net.minecraft.server.packs.repository;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.packs.IResourcePack;
import net.minecraft.world.flag.FeatureFlagSet;

public class ResourcePackRepository {
   private final Set<ResourcePackSource> a;
   private Map<String, ResourcePackLoader> b = ImmutableMap.of();
   private List<ResourcePackLoader> c = ImmutableList.of();

   public ResourcePackRepository(ResourcePackSource... var0) {
      this.a = ImmutableSet.copyOf(var0);
   }

   public void a() {
      List<String> var0 = this.c.stream().map(ResourcePackLoader::f).collect(ImmutableList.toImmutableList());
      this.b = this.h();
      this.c = this.b(var0);
   }

   private Map<String, ResourcePackLoader> h() {
      Map<String, ResourcePackLoader> var0 = Maps.newTreeMap();

      for(ResourcePackSource var2 : this.a) {
         var2.a(var1x -> var0.put(var1x.f(), var1x));
      }

      return ImmutableMap.copyOf(var0);
   }

   public void a(Collection<String> var0) {
      this.c = this.b(var0);
   }

   public boolean a(String var0) {
      ResourcePackLoader var1 = this.b.get(var0);
      if (var1 != null && !this.c.contains(var1)) {
         List<ResourcePackLoader> var2 = Lists.newArrayList(this.c);
         var2.add(var1);
         this.c = var2;
         return true;
      } else {
         return false;
      }
   }

   public boolean b(String var0) {
      ResourcePackLoader var1 = this.b.get(var0);
      if (var1 != null && this.c.contains(var1)) {
         List<ResourcePackLoader> var2 = Lists.newArrayList(this.c);
         var2.remove(var1);
         this.c = var2;
         return true;
      } else {
         return false;
      }
   }

   private List<ResourcePackLoader> b(Collection<String> var0) {
      List<ResourcePackLoader> var1 = this.c(var0).collect(Collectors.toList());

      for(ResourcePackLoader var3 : this.b.values()) {
         if (var3.g() && !var1.contains(var3)) {
            var3.i().a(var1, var3, Functions.identity(), false);
         }
      }

      return ImmutableList.copyOf(var1);
   }

   private Stream<ResourcePackLoader> c(Collection<String> var0) {
      return var0.stream().map(this.b::get).filter(Objects::nonNull);
   }

   public Collection<String> b() {
      return this.b.keySet();
   }

   public Collection<ResourcePackLoader> c() {
      return this.b.values();
   }

   public Collection<String> d() {
      return this.c.stream().map(ResourcePackLoader::f).collect(ImmutableSet.toImmutableSet());
   }

   public FeatureFlagSet e() {
      return this.f().stream().map(ResourcePackLoader::d).reduce(FeatureFlagSet::b).orElse(FeatureFlagSet.a());
   }

   public Collection<ResourcePackLoader> f() {
      return this.c;
   }

   @Nullable
   public ResourcePackLoader c(String var0) {
      return this.b.get(var0);
   }

   public boolean d(String var0) {
      return this.b.containsKey(var0);
   }

   public List<IResourcePack> g() {
      return this.c.stream().map(ResourcePackLoader::e).collect(ImmutableList.toImmutableList());
   }
}
