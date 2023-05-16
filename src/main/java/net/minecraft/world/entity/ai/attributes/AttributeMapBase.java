package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import org.slf4j.Logger;

public class AttributeMapBase {
   private static final Logger a = LogUtils.getLogger();
   private final Map<AttributeBase, AttributeModifiable> b = Maps.newHashMap();
   private final Set<AttributeModifiable> c = Sets.newHashSet();
   private final AttributeProvider d;

   public AttributeMapBase(AttributeProvider var0) {
      this.d = var0;
   }

   private void a(AttributeModifiable var0) {
      if (var0.a().b()) {
         this.c.add(var0);
      }
   }

   public Set<AttributeModifiable> a() {
      return this.c;
   }

   public Collection<AttributeModifiable> b() {
      return this.b.values().stream().filter(var0 -> var0.a().b()).collect(Collectors.toList());
   }

   @Nullable
   public AttributeModifiable a(AttributeBase var0) {
      return this.b.computeIfAbsent(var0, var0x -> this.d.a(this::a, var0x));
   }

   @Nullable
   public AttributeModifiable a(Holder<AttributeBase> var0) {
      return this.a(var0.a());
   }

   public boolean b(AttributeBase var0) {
      return this.b.get(var0) != null || this.d.c(var0);
   }

   public boolean b(Holder<AttributeBase> var0) {
      return this.b(var0.a());
   }

   public boolean a(AttributeBase var0, UUID var1) {
      AttributeModifiable var2 = this.b.get(var0);
      return var2 != null ? var2.a(var1) != null : this.d.b(var0, var1);
   }

   public boolean a(Holder<AttributeBase> var0, UUID var1) {
      return this.a(var0.a(), var1);
   }

   public double c(AttributeBase var0) {
      AttributeModifiable var1 = this.b.get(var0);
      return var1 != null ? var1.f() : this.d.a(var0);
   }

   public double d(AttributeBase var0) {
      AttributeModifiable var1 = this.b.get(var0);
      return var1 != null ? var1.b() : this.d.b(var0);
   }

   public double b(AttributeBase var0, UUID var1) {
      AttributeModifiable var2 = this.b.get(var0);
      return var2 != null ? var2.a(var1).d() : this.d.a(var0, var1);
   }

   public double b(Holder<AttributeBase> var0, UUID var1) {
      return this.b(var0.a(), var1);
   }

   public void a(Multimap<AttributeBase, AttributeModifier> var0) {
      var0.asMap().forEach((var0x, var1x) -> {
         AttributeModifiable var2 = this.b.get(var0x);
         if (var2 != null) {
            var1x.forEach(var2::d);
         }
      });
   }

   public void b(Multimap<AttributeBase, AttributeModifier> var0) {
      var0.forEach((var0x, var1x) -> {
         AttributeModifiable var2 = this.a(var0x);
         if (var2 != null) {
            var2.d(var1x);
            var2.b(var1x);
         }
      });
   }

   public void a(AttributeMapBase var0) {
      var0.b.values().forEach(var0x -> {
         AttributeModifiable var1x = this.a(var0x.a());
         if (var1x != null) {
            var1x.a(var0x);
         }
      });
   }

   public NBTTagList c() {
      NBTTagList var0 = new NBTTagList();

      for(AttributeModifiable var2 : this.b.values()) {
         var0.add(var2.g());
      }

      return var0;
   }

   public void a(NBTTagList var0) {
      for(int var1 = 0; var1 < var0.size(); ++var1) {
         NBTTagCompound var2 = var0.a(var1);
         String var3 = var2.l("Name");
         SystemUtils.a(BuiltInRegistries.u.b(MinecraftKey.a(var3)), var1x -> {
            AttributeModifiable var2x = this.a(var1x);
            if (var2x != null) {
               var2x.a(var2);
            }
         }, () -> a.warn("Ignoring unknown attribute '{}'", var3));
      }
   }
}
