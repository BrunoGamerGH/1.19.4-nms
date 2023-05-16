package net.minecraft.world.entity.ai.attributes;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;

public class AttributeProvider {
   private final Map<AttributeBase, AttributeModifiable> a;

   public AttributeProvider(Map<AttributeBase, AttributeModifiable> var0) {
      this.a = ImmutableMap.copyOf(var0);
   }

   private AttributeModifiable d(AttributeBase var0) {
      AttributeModifiable var1 = this.a.get(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("Can't find attribute " + BuiltInRegistries.u.b(var0));
      } else {
         return var1;
      }
   }

   public double a(AttributeBase var0) {
      return this.d(var0).f();
   }

   public double b(AttributeBase var0) {
      return this.d(var0).b();
   }

   public double a(AttributeBase var0, UUID var1) {
      AttributeModifier var2 = this.d(var0).a(var1);
      if (var2 == null) {
         throw new IllegalArgumentException("Can't find modifier " + var1 + " on attribute " + BuiltInRegistries.u.b(var0));
      } else {
         return var2.d();
      }
   }

   @Nullable
   public AttributeModifiable a(Consumer<AttributeModifiable> var0, AttributeBase var1) {
      AttributeModifiable var2 = this.a.get(var1);
      if (var2 == null) {
         return null;
      } else {
         AttributeModifiable var3 = new AttributeModifiable(var1, var0);
         var3.a(var2);
         return var3;
      }
   }

   public static AttributeProvider.Builder a() {
      return new AttributeProvider.Builder();
   }

   public boolean c(AttributeBase var0) {
      return this.a.containsKey(var0);
   }

   public boolean b(AttributeBase var0, UUID var1) {
      AttributeModifiable var2 = this.a.get(var0);
      return var2 != null && var2.a(var1) != null;
   }

   public static class Builder {
      private final Map<AttributeBase, AttributeModifiable> a = Maps.newHashMap();
      private boolean b;

      private AttributeModifiable b(AttributeBase var0) {
         AttributeModifiable var1 = new AttributeModifiable(var0, var1x -> {
            if (this.b) {
               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + BuiltInRegistries.u.b(var0));
            }
         });
         this.a.put(var0, var1);
         return var1;
      }

      public AttributeProvider.Builder a(AttributeBase var0) {
         this.b(var0);
         return this;
      }

      public AttributeProvider.Builder a(AttributeBase var0, double var1) {
         AttributeModifiable var3 = this.b(var0);
         var3.a(var1);
         return this;
      }

      public AttributeProvider a() {
         this.b = true;
         return new AttributeProvider(this.a);
      }
   }
}
