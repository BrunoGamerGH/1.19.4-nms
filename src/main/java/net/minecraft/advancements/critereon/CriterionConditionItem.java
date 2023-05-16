package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.IMaterial;

public class CriterionConditionItem {
   public static final CriterionConditionItem a = new CriterionConditionItem();
   @Nullable
   private final TagKey<Item> b;
   @Nullable
   private final Set<Item> c;
   private final CriterionConditionValue.IntegerRange d;
   private final CriterionConditionValue.IntegerRange e;
   private final CriterionConditionEnchantments[] f;
   private final CriterionConditionEnchantments[] g;
   @Nullable
   private final PotionRegistry h;
   private final CriterionConditionNBT i;

   public CriterionConditionItem() {
      this.b = null;
      this.c = null;
      this.h = null;
      this.d = CriterionConditionValue.IntegerRange.e;
      this.e = CriterionConditionValue.IntegerRange.e;
      this.f = CriterionConditionEnchantments.b;
      this.g = CriterionConditionEnchantments.b;
      this.i = CriterionConditionNBT.a;
   }

   public CriterionConditionItem(
      @Nullable TagKey<Item> var0,
      @Nullable Set<Item> var1,
      CriterionConditionValue.IntegerRange var2,
      CriterionConditionValue.IntegerRange var3,
      CriterionConditionEnchantments[] var4,
      CriterionConditionEnchantments[] var5,
      @Nullable PotionRegistry var6,
      CriterionConditionNBT var7
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
   }

   public boolean a(ItemStack var0) {
      if (this == a) {
         return true;
      } else if (this.b != null && !var0.a(this.b)) {
         return false;
      } else if (this.c != null && !this.c.contains(var0.c())) {
         return false;
      } else if (!this.d.d(var0.K())) {
         return false;
      } else if (!this.e.c() && !var0.h()) {
         return false;
      } else if (!this.e.d(var0.k() - var0.j())) {
         return false;
      } else if (!this.i.a(var0)) {
         return false;
      } else {
         if (this.f.length > 0) {
            Map<Enchantment, Integer> var1 = EnchantmentManager.a(var0.w());

            for(CriterionConditionEnchantments var5 : this.f) {
               if (!var5.a(var1)) {
                  return false;
               }
            }
         }

         if (this.g.length > 0) {
            Map<Enchantment, Integer> var1 = EnchantmentManager.a(ItemEnchantedBook.d(var0));

            for(CriterionConditionEnchantments var5 : this.g) {
               if (!var5.a(var1)) {
                  return false;
               }
            }
         }

         PotionRegistry var1 = PotionUtil.d(var0);
         return this.h == null || this.h == var1;
      }
   }

   public static CriterionConditionItem a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "item");
         CriterionConditionValue.IntegerRange var2 = CriterionConditionValue.IntegerRange.a(var1.get("count"));
         CriterionConditionValue.IntegerRange var3 = CriterionConditionValue.IntegerRange.a(var1.get("durability"));
         if (var1.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            CriterionConditionNBT var4 = CriterionConditionNBT.a(var1.get("nbt"));
            Set<Item> var5 = null;
            JsonArray var6 = ChatDeserializer.a(var1, "items", null);
            if (var6 != null) {
               Builder<Item> var7 = ImmutableSet.builder();

               for(JsonElement var9 : var6) {
                  MinecraftKey var10 = new MinecraftKey(ChatDeserializer.a(var9, "item"));
                  var7.add(BuiltInRegistries.i.b(var10).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + var10 + "'")));
               }

               var5 = var7.build();
            }

            TagKey<Item> var7 = null;
            if (var1.has("tag")) {
               MinecraftKey var8 = new MinecraftKey(ChatDeserializer.h(var1, "tag"));
               var7 = TagKey.a(Registries.C, var8);
            }

            PotionRegistry var8 = null;
            if (var1.has("potion")) {
               MinecraftKey var9 = new MinecraftKey(ChatDeserializer.h(var1, "potion"));
               var8 = BuiltInRegistries.j.b(var9).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + var9 + "'"));
            }

            CriterionConditionEnchantments[] var9 = CriterionConditionEnchantments.b(var1.get("enchantments"));
            CriterionConditionEnchantments[] var10 = CriterionConditionEnchantments.b(var1.get("stored_enchantments"));
            return new CriterionConditionItem(var7, var5, var2, var3, var9, var10, var8, var4);
         }
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (this.c != null) {
            JsonArray var1 = new JsonArray();

            for(Item var3 : this.c) {
               var1.add(BuiltInRegistries.i.b(var3).toString());
            }

            var0.add("items", var1);
         }

         if (this.b != null) {
            var0.addProperty("tag", this.b.b().toString());
         }

         var0.add("count", this.d.d());
         var0.add("durability", this.e.d());
         var0.add("nbt", this.i.a());
         if (this.f.length > 0) {
            JsonArray var1 = new JsonArray();

            for(CriterionConditionEnchantments var5 : this.f) {
               var1.add(var5.a());
            }

            var0.add("enchantments", var1);
         }

         if (this.g.length > 0) {
            JsonArray var1 = new JsonArray();

            for(CriterionConditionEnchantments var5 : this.g) {
               var1.add(var5.a());
            }

            var0.add("stored_enchantments", var1);
         }

         if (this.h != null) {
            var0.addProperty("potion", BuiltInRegistries.j.b(this.h).toString());
         }

         return var0;
      }
   }

   public static CriterionConditionItem[] b(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonArray var1 = ChatDeserializer.n(var0, "items");
         CriterionConditionItem[] var2 = new CriterionConditionItem[var1.size()];

         for(int var3 = 0; var3 < var2.length; ++var3) {
            var2[var3] = a(var1.get(var3));
         }

         return var2;
      } else {
         return new CriterionConditionItem[0];
      }
   }

   public static class a {
      private final List<CriterionConditionEnchantments> a = Lists.newArrayList();
      private final List<CriterionConditionEnchantments> b = Lists.newArrayList();
      @Nullable
      private Set<Item> c;
      @Nullable
      private TagKey<Item> d;
      private CriterionConditionValue.IntegerRange e;
      private CriterionConditionValue.IntegerRange f;
      @Nullable
      private PotionRegistry g;
      private CriterionConditionNBT h;

      private a() {
         this.e = CriterionConditionValue.IntegerRange.e;
         this.f = CriterionConditionValue.IntegerRange.e;
         this.h = CriterionConditionNBT.a;
      }

      public static CriterionConditionItem.a a() {
         return new CriterionConditionItem.a();
      }

      public CriterionConditionItem.a a(IMaterial... var0) {
         this.c = Stream.of(var0).map(IMaterial::k).collect(ImmutableSet.toImmutableSet());
         return this;
      }

      public CriterionConditionItem.a a(TagKey<Item> var0) {
         this.d = var0;
         return this;
      }

      public CriterionConditionItem.a a(CriterionConditionValue.IntegerRange var0) {
         this.e = var0;
         return this;
      }

      public CriterionConditionItem.a b(CriterionConditionValue.IntegerRange var0) {
         this.f = var0;
         return this;
      }

      public CriterionConditionItem.a a(PotionRegistry var0) {
         this.g = var0;
         return this;
      }

      public CriterionConditionItem.a a(NBTTagCompound var0) {
         this.h = new CriterionConditionNBT(var0);
         return this;
      }

      public CriterionConditionItem.a a(CriterionConditionEnchantments var0) {
         this.a.add(var0);
         return this;
      }

      public CriterionConditionItem.a b(CriterionConditionEnchantments var0) {
         this.b.add(var0);
         return this;
      }

      public CriterionConditionItem b() {
         return new CriterionConditionItem(
            this.d, this.c, this.e, this.f, this.a.toArray(CriterionConditionEnchantments.b), this.b.toArray(CriterionConditionEnchantments.b), this.g, this.h
         );
      }
   }
}
