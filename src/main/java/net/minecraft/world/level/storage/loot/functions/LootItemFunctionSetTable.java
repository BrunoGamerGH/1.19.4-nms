package net.minecraft.world.level.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionSetTable extends LootItemFunctionConditional {
   final MinecraftKey a;
   final long b;
   final TileEntityTypes<?> c;

   LootItemFunctionSetTable(LootItemCondition[] var0, MinecraftKey var1, long var2, TileEntityTypes<?> var4) {
      super(var0);
      this.a = var1;
      this.b = var2;
      this.c = var4;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.r;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.b()) {
         return var0;
      } else {
         NBTTagCompound var2 = ItemBlock.a(var0);
         if (var2 == null) {
            var2 = new NBTTagCompound();
         }

         var2.a("LootTable", this.a.toString());
         if (this.b != 0L) {
            var2.a("LootTableSeed", this.b);
         }

         ItemBlock.a(var0, this.c, var2);
         return var0;
      }
   }

   @Override
   public void a(LootCollector var0) {
      if (var0.a(this.a)) {
         var0.a("Table " + this.a + " is recursively called");
      } else {
         super.a(var0);
         LootTable var1 = var0.c(this.a);
         if (var1 == null) {
            var0.a("Unknown loot table called " + this.a);
         } else {
            var1.a(var0.a("->{" + this.a + "}", this.a));
         }
      }
   }

   public static LootItemFunctionConditional.a<?> a(TileEntityTypes<?> var0, MinecraftKey var1) {
      return a(var2 -> new LootItemFunctionSetTable(var2, var1, 0L, var0));
   }

   public static LootItemFunctionConditional.a<?> a(TileEntityTypes<?> var0, MinecraftKey var1, long var2) {
      return a(var4 -> new LootItemFunctionSetTable(var4, var1, var2, var0));
   }

   public static class a extends LootItemFunctionConditional.c<LootItemFunctionSetTable> {
      public void a(JsonObject var0, LootItemFunctionSetTable var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("name", var1.a.toString());
         var0.addProperty("type", BuiltInRegistries.l.b(var1.c).toString());
         if (var1.b != 0L) {
            var0.addProperty("seed", var1.b);
         }
      }

      public LootItemFunctionSetTable a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "name"));
         long var4 = ChatDeserializer.a(var0, "seed", 0L);
         MinecraftKey var6 = new MinecraftKey(ChatDeserializer.h(var0, "type"));
         TileEntityTypes<?> var7 = BuiltInRegistries.l.b(var6).orElseThrow(() -> new JsonSyntaxException("Unknown block entity type id '" + var6 + "'"));
         return new LootItemFunctionSetTable(var2, var3, var4, var7);
      }
   }
}
