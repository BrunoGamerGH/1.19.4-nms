package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.INamable;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.IBlockDataHolder;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.material.Fluid;

public class CriterionTriggerProperties {
   public static final CriterionTriggerProperties a = new CriterionTriggerProperties(ImmutableList.of());
   private final List<CriterionTriggerProperties.c> b;

   private static CriterionTriggerProperties.c a(String var0, JsonElement var1) {
      if (var1.isJsonPrimitive()) {
         String var2 = var1.getAsString();
         return new CriterionTriggerProperties.b(var0, var2);
      } else {
         JsonObject var2 = ChatDeserializer.m(var1, "value");
         String var3 = var2.has("min") ? b(var2.get("min")) : null;
         String var4 = var2.has("max") ? b(var2.get("max")) : null;
         return (CriterionTriggerProperties.c)(var3 != null && var3.equals(var4)
            ? new CriterionTriggerProperties.b(var0, var3)
            : new CriterionTriggerProperties.d(var0, var3, var4));
      }
   }

   @Nullable
   private static String b(JsonElement var0) {
      return var0.isJsonNull() ? null : var0.getAsString();
   }

   CriterionTriggerProperties(List<CriterionTriggerProperties.c> var0) {
      this.b = ImmutableList.copyOf(var0);
   }

   public <S extends IBlockDataHolder<?, S>> boolean a(BlockStateList<?, S> var0, S var1) {
      for(CriterionTriggerProperties.c var3 : this.b) {
         if (!var3.a(var0, var1)) {
            return false;
         }
      }

      return true;
   }

   public boolean a(IBlockData var0) {
      return this.a(var0.b().n(), var0);
   }

   public boolean a(Fluid var0) {
      return this.a(var0.a().f(), var0);
   }

   public void a(BlockStateList<?, ?> var0, Consumer<String> var1) {
      this.b.forEach(var2x -> var2x.a(var0, var1));
   }

   public static CriterionTriggerProperties a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "properties");
         List<CriterionTriggerProperties.c> var2 = Lists.newArrayList();

         for(Entry<String, JsonElement> var4 : var1.entrySet()) {
            var2.add(a(var4.getKey(), (JsonElement)var4.getValue()));
         }

         return new CriterionTriggerProperties(var2);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (!this.b.isEmpty()) {
            this.b.forEach(var1x -> var0.add(var1x.b(), var1x.a()));
         }

         return var0;
      }
   }

   public static class a {
      private final List<CriterionTriggerProperties.c> a = Lists.newArrayList();

      private a() {
      }

      public static CriterionTriggerProperties.a a() {
         return new CriterionTriggerProperties.a();
      }

      public CriterionTriggerProperties.a a(IBlockState<?> var0, String var1) {
         this.a.add(new CriterionTriggerProperties.b(var0.f(), var1));
         return this;
      }

      public CriterionTriggerProperties.a a(IBlockState<Integer> var0, int var1) {
         return this.a(var0, Integer.toString(var1));
      }

      public CriterionTriggerProperties.a a(IBlockState<Boolean> var0, boolean var1) {
         return this.a(var0, Boolean.toString(var1));
      }

      public <T extends Comparable<T> & INamable> CriterionTriggerProperties.a a(IBlockState<T> var0, T var1) {
         return this.a(var0, var1.c());
      }

      public CriterionTriggerProperties b() {
         return new CriterionTriggerProperties(this.a);
      }
   }

   static class b extends CriterionTriggerProperties.c {
      private final String a;

      public b(String var0, String var1) {
         super(var0);
         this.a = var1;
      }

      @Override
      protected <T extends Comparable<T>> boolean a(IBlockDataHolder<?, ?> var0, IBlockState<T> var1) {
         T var2 = var0.c(var1);
         Optional<T> var3 = var1.b(this.a);
         return var3.isPresent() && var2.compareTo(var3.get()) == 0;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(this.a);
      }
   }

   abstract static class c {
      private final String a;

      public c(String var0) {
         this.a = var0;
      }

      public <S extends IBlockDataHolder<?, S>> boolean a(BlockStateList<?, S> var0, S var1) {
         IBlockState<?> var2 = var0.a(this.a);
         return var2 == null ? false : this.a(var1, var2);
      }

      protected abstract <T extends Comparable<T>> boolean a(IBlockDataHolder<?, ?> var1, IBlockState<T> var2);

      public abstract JsonElement a();

      public String b() {
         return this.a;
      }

      public void a(BlockStateList<?, ?> var0, Consumer<String> var1) {
         IBlockState<?> var2 = var0.a(this.a);
         if (var2 == null) {
            var1.accept(this.a);
         }
      }
   }

   static class d extends CriterionTriggerProperties.c {
      @Nullable
      private final String a;
      @Nullable
      private final String b;

      public d(String var0, @Nullable String var1, @Nullable String var2) {
         super(var0);
         this.a = var1;
         this.b = var2;
      }

      @Override
      protected <T extends Comparable<T>> boolean a(IBlockDataHolder<?, ?> var0, IBlockState<T> var1) {
         T var2 = var0.c(var1);
         if (this.a != null) {
            Optional<T> var3 = var1.b(this.a);
            if (!var3.isPresent() || var2.compareTo(var3.get()) < 0) {
               return false;
            }
         }

         if (this.b != null) {
            Optional<T> var3 = var1.b(this.b);
            if (!var3.isPresent() || var2.compareTo(var3.get()) > 0) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonElement a() {
         JsonObject var0 = new JsonObject();
         if (this.a != null) {
            var0.addProperty("min", this.a);
         }

         if (this.b != null) {
            var0.addProperty("max", this.b);
         }

         return var0;
      }
   }
}
