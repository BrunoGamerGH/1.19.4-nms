package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.attributes.AttributeBase;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class LootItemFunctionSetAttribute extends LootItemFunctionConditional {
   final List<LootItemFunctionSetAttribute.b> a;

   LootItemFunctionSetAttribute(LootItemCondition[] var0, List<LootItemFunctionSetAttribute.b> var1) {
      super(var0);
      this.a = ImmutableList.copyOf(var1);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.j;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.stream().flatMap(var0 -> var0.d.b().stream()).collect(ImmutableSet.toImmutableSet());
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      RandomSource var2 = var1.a();

      for(LootItemFunctionSetAttribute.b var4 : this.a) {
         UUID var5 = var4.e;
         if (var5 == null) {
            var5 = UUID.randomUUID();
         }

         EnumItemSlot var6 = SystemUtils.a(var4.f, var2);
         var0.a(var4.b, new AttributeModifier(var5, var4.a, (double)var4.d.b(var1), var4.c), var6);
      }

      return var0;
   }

   public static LootItemFunctionSetAttribute.c a(String var0, AttributeBase var1, AttributeModifier.Operation var2, NumberProvider var3) {
      return new LootItemFunctionSetAttribute.c(var0, var1, var2, var3);
   }

   public static LootItemFunctionSetAttribute.a c() {
      return new LootItemFunctionSetAttribute.a();
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionSetAttribute.a> {
      private final List<LootItemFunctionSetAttribute.b> a = Lists.newArrayList();

      protected LootItemFunctionSetAttribute.a a() {
         return this;
      }

      public LootItemFunctionSetAttribute.a a(LootItemFunctionSetAttribute.c var0) {
         this.a.add(var0.a());
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionSetAttribute(this.g(), this.a);
      }
   }

   static class b {
      final String a;
      final AttributeBase b;
      final AttributeModifier.Operation c;
      final NumberProvider d;
      @Nullable
      final UUID e;
      final EnumItemSlot[] f;

      b(String var0, AttributeBase var1, AttributeModifier.Operation var2, NumberProvider var3, EnumItemSlot[] var4, @Nullable UUID var5) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var5;
         this.f = var4;
      }

      public JsonObject a(JsonSerializationContext var0) {
         JsonObject var1 = new JsonObject();
         var1.addProperty("name", this.a);
         var1.addProperty("attribute", BuiltInRegistries.u.b(this.b).toString());
         var1.addProperty("operation", a(this.c));
         var1.add("amount", var0.serialize(this.d));
         if (this.e != null) {
            var1.addProperty("id", this.e.toString());
         }

         if (this.f.length == 1) {
            var1.addProperty("slot", this.f[0].d());
         } else {
            JsonArray var2 = new JsonArray();

            for(EnumItemSlot var6 : this.f) {
               var2.add(new JsonPrimitive(var6.d()));
            }

            var1.add("slot", var2);
         }

         return var1;
      }

      public static LootItemFunctionSetAttribute.b a(JsonObject var0, JsonDeserializationContext var1) {
         String var2 = ChatDeserializer.h(var0, "name");
         MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "attribute"));
         AttributeBase var4 = BuiltInRegistries.u.a(var3);
         if (var4 == null) {
            throw new JsonSyntaxException("Unknown attribute: " + var3);
         } else {
            AttributeModifier.Operation var5 = a(ChatDeserializer.h(var0, "operation"));
            NumberProvider var6 = ChatDeserializer.a(var0, "amount", var1, NumberProvider.class);
            UUID var8 = null;
            EnumItemSlot[] var7;
            if (ChatDeserializer.a(var0, "slot")) {
               var7 = new EnumItemSlot[]{EnumItemSlot.a(ChatDeserializer.h(var0, "slot"))};
            } else {
               if (!ChatDeserializer.d(var0, "slot")) {
                  throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
               }

               JsonArray var9 = ChatDeserializer.u(var0, "slot");
               var7 = new EnumItemSlot[var9.size()];
               int var10 = 0;

               for(JsonElement var12 : var9) {
                  var7[var10++] = EnumItemSlot.a(ChatDeserializer.a(var12, "slot"));
               }

               if (var7.length == 0) {
                  throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
               }
            }

            if (var0.has("id")) {
               String var9 = ChatDeserializer.h(var0, "id");

               try {
                  var8 = UUID.fromString(var9);
               } catch (IllegalArgumentException var13) {
                  throw new JsonSyntaxException("Invalid attribute modifier id '" + var9 + "' (must be UUID format, with dashes)");
               }
            }

            return new LootItemFunctionSetAttribute.b(var2, var4, var5, var6, var7, var8);
         }
      }

      private static String a(AttributeModifier.Operation var0) {
         switch(var0) {
            case a:
               return "addition";
            case b:
               return "multiply_base";
            case c:
               return "multiply_total";
            default:
               throw new IllegalArgumentException("Unknown operation " + var0);
         }
      }

      private static AttributeModifier.Operation a(String var0) {
         switch(var0) {
            case "addition":
               return AttributeModifier.Operation.a;
            case "multiply_base":
               return AttributeModifier.Operation.b;
            case "multiply_total":
               return AttributeModifier.Operation.c;
            default:
               throw new JsonSyntaxException("Unknown attribute modifier operation " + var0);
         }
      }
   }

   public static class c {
      private final String a;
      private final AttributeBase b;
      private final AttributeModifier.Operation c;
      private final NumberProvider d;
      @Nullable
      private UUID e;
      private final Set<EnumItemSlot> f = EnumSet.noneOf(EnumItemSlot.class);

      public c(String var0, AttributeBase var1, AttributeModifier.Operation var2, NumberProvider var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public LootItemFunctionSetAttribute.c a(EnumItemSlot var0) {
         this.f.add(var0);
         return this;
      }

      public LootItemFunctionSetAttribute.c a(UUID var0) {
         this.e = var0;
         return this;
      }

      public LootItemFunctionSetAttribute.b a() {
         return new LootItemFunctionSetAttribute.b(this.a, this.b, this.c, this.d, this.f.toArray(new EnumItemSlot[0]), this.e);
      }
   }

   public static class d extends LootItemFunctionConditional.c<LootItemFunctionSetAttribute> {
      public void a(JsonObject var0, LootItemFunctionSetAttribute var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         JsonArray var3 = new JsonArray();

         for(LootItemFunctionSetAttribute.b var5 : var1.a) {
            var3.add(var5.a(var2));
         }

         var0.add("modifiers", var3);
      }

      public LootItemFunctionSetAttribute a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         JsonArray var3 = ChatDeserializer.u(var0, "modifiers");
         List<LootItemFunctionSetAttribute.b> var4 = Lists.newArrayListWithExpectedSize(var3.size());

         for(JsonElement var6 : var3) {
            var4.add(LootItemFunctionSetAttribute.b.a(ChatDeserializer.m(var6, "modifier"), var1));
         }

         if (var4.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new LootItemFunctionSetAttribute(var2, var4);
         }
      }
   }
}
