package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AttributeModifiable {
   private final AttributeBase a;
   private final Map<AttributeModifier.Operation, Set<AttributeModifier>> b = Maps.newEnumMap(AttributeModifier.Operation.class);
   private final Map<UUID, AttributeModifier> c = new Object2ObjectArrayMap();
   private final Set<AttributeModifier> d = new ObjectArraySet();
   private double e;
   private boolean f = true;
   private double g;
   private final Consumer<AttributeModifiable> h;

   public AttributeModifiable(AttributeBase var0, Consumer<AttributeModifiable> var1) {
      this.a = var0;
      this.h = var1;
      this.e = var0.a();
   }

   public AttributeBase a() {
      return this.a;
   }

   public double b() {
      return this.e;
   }

   public void a(double var0) {
      if (var0 != this.e) {
         this.e = var0;
         this.d();
      }
   }

   public Set<AttributeModifier> a(AttributeModifier.Operation var0) {
      return this.b.computeIfAbsent(var0, var0x -> Sets.newHashSet());
   }

   public Set<AttributeModifier> c() {
      return ImmutableSet.copyOf(this.c.values());
   }

   @Nullable
   public AttributeModifier a(UUID var0) {
      return this.c.get(var0);
   }

   public boolean a(AttributeModifier var0) {
      return this.c.get(var0.a()) != null;
   }

   private void e(AttributeModifier var0) {
      AttributeModifier var1 = this.c.putIfAbsent(var0.a(), var0);
      if (var1 != null) {
         throw new IllegalArgumentException("Modifier is already applied on this attribute!");
      } else {
         this.a(var0.c()).add(var0);
         this.d();
      }
   }

   public void b(AttributeModifier var0) {
      this.e(var0);
   }

   public void c(AttributeModifier var0) {
      this.e(var0);
      this.d.add(var0);
   }

   protected void d() {
      this.f = true;
      this.h.accept(this);
   }

   public void d(AttributeModifier var0) {
      this.a(var0.c()).remove(var0);
      this.c.remove(var0.a());
      this.d.remove(var0);
      this.d();
   }

   public void b(UUID var0) {
      AttributeModifier var1 = this.a(var0);
      if (var1 != null) {
         this.d(var1);
      }
   }

   public boolean c(UUID var0) {
      AttributeModifier var1 = this.a(var0);
      if (var1 != null && this.d.contains(var1)) {
         this.d(var1);
         return true;
      } else {
         return false;
      }
   }

   public void e() {
      for(AttributeModifier var1 : this.c()) {
         this.d(var1);
      }
   }

   public double f() {
      if (this.f) {
         this.g = this.h();
         this.f = false;
      }

      return this.g;
   }

   private double h() {
      double var0 = this.b();

      for(AttributeModifier var3 : this.b(AttributeModifier.Operation.a)) {
         var0 += var3.d();
      }

      double var2 = var0;

      for(AttributeModifier var5 : this.b(AttributeModifier.Operation.b)) {
         var2 += var0 * var5.d();
      }

      for(AttributeModifier var5 : this.b(AttributeModifier.Operation.c)) {
         var2 *= 1.0 + var5.d();
      }

      return this.a.a(var2);
   }

   private Collection<AttributeModifier> b(AttributeModifier.Operation var0) {
      return this.b.getOrDefault(var0, Collections.emptySet());
   }

   public void a(AttributeModifiable var0) {
      this.e = var0.e;
      this.c.clear();
      this.c.putAll(var0.c);
      this.d.clear();
      this.d.addAll(var0.d);
      this.b.clear();
      var0.b.forEach((var0x, var1x) -> this.a(var0x).addAll(var1x));
      this.d();
   }

   public NBTTagCompound g() {
      NBTTagCompound var0 = new NBTTagCompound();
      var0.a("Name", BuiltInRegistries.u.b(this.a).toString());
      var0.a("Base", this.e);
      if (!this.d.isEmpty()) {
         NBTTagList var1 = new NBTTagList();

         for(AttributeModifier var3 : this.d) {
            var1.add(var3.e());
         }

         var0.a("Modifiers", var1);
      }

      return var0;
   }

   public void a(NBTTagCompound var0) {
      this.e = var0.k("Base");
      if (var0.b("Modifiers", 9)) {
         NBTTagList var1 = var0.c("Modifiers", 10);

         for(int var2 = 0; var2 < var1.size(); ++var2) {
            AttributeModifier var3 = AttributeModifier.a(var1.a(var2));
            if (var3 != null) {
               this.c.put(var3.a(), var3);
               this.a(var3.c()).add(var3);
               this.d.add(var3);
            }
         }
      }

      this.d();
   }
}
