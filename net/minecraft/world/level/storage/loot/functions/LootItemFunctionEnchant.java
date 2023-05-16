package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.WeightedRandomEnchant;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.slf4j.Logger;

public class LootItemFunctionEnchant extends LootItemFunctionConditional {
   private static final Logger a = LogUtils.getLogger();
   final List<Enchantment> b;

   LootItemFunctionEnchant(LootItemCondition[] var0, Collection<Enchantment> var1) {
      super(var0);
      this.b = ImmutableList.copyOf(var1);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.d;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      RandomSource var3 = var1.a();
      Enchantment var2;
      if (this.b.isEmpty()) {
         boolean var4 = var0.a(Items.pX);
         List<Enchantment> var5 = BuiltInRegistries.g.s().filter(Enchantment::i).filter(var2x -> var4 || var2x.a(var0)).collect(Collectors.toList());
         if (var5.isEmpty()) {
            a.warn("Couldn't find a compatible enchantment for {}", var0);
            return var0;
         }

         var2 = var5.get(var3.a(var5.size()));
      } else {
         var2 = this.b.get(var3.a(this.b.size()));
      }

      return a(var0, var2, var3);
   }

   private static ItemStack a(ItemStack var0, Enchantment var1, RandomSource var2) {
      int var3 = MathHelper.a(var2, var1.e(), var1.a());
      if (var0.a(Items.pX)) {
         var0 = new ItemStack(Items.ty);
         ItemEnchantedBook.a(var0, new WeightedRandomEnchant(var1, var3));
      } else {
         var0.a(var1, var3);
      }

      return var0;
   }

   public static LootItemFunctionEnchant.a c() {
      return new LootItemFunctionEnchant.a();
   }

   public static LootItemFunctionConditional.a<?> d() {
      return a(var0 -> new LootItemFunctionEnchant(var0, ImmutableList.of()));
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionEnchant.a> {
      private final Set<Enchantment> a = Sets.newHashSet();

      protected LootItemFunctionEnchant.a a() {
         return this;
      }

      public LootItemFunctionEnchant.a a(Enchantment var0) {
         this.a.add(var0);
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionEnchant(this.g(), this.a);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionEnchant> {
      public void a(JsonObject var0, LootItemFunctionEnchant var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         if (!var1.b.isEmpty()) {
            JsonArray var3 = new JsonArray();

            for(Enchantment var5 : var1.b) {
               MinecraftKey var6 = BuiltInRegistries.g.b(var5);
               if (var6 == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + var5);
               }

               var3.add(new JsonPrimitive(var6.toString()));
            }

            var0.add("enchantments", var3);
         }
      }

      public LootItemFunctionEnchant a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         List<Enchantment> var3 = Lists.newArrayList();
         if (var0.has("enchantments")) {
            for(JsonElement var6 : ChatDeserializer.u(var0, "enchantments")) {
               String var7 = ChatDeserializer.a(var6, "enchantment");
               Enchantment var8 = BuiltInRegistries.g
                  .b(new MinecraftKey(var7))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + var7 + "'"));
               var3.add(var8);
            }
         }

         return new LootItemFunctionEnchant(var2, var3);
      }
   }
}
