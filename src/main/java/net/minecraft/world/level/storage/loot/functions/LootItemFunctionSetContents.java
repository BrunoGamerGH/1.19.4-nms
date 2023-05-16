package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.entries.LootEntryAbstract;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionSetContents extends LootItemFunctionConditional {
   final List<LootEntryAbstract> a;
   final TileEntityTypes<?> b;

   LootItemFunctionSetContents(LootItemCondition[] var0, TileEntityTypes<?> var1, List<LootEntryAbstract> var2) {
      super(var0);
      this.b = var1;
      this.a = ImmutableList.copyOf(var2);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.o;
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      if (var0.b()) {
         return var0;
      } else {
         NonNullList<ItemStack> var2 = NonNullList.a();
         this.a.forEach(var2x -> var2x.expand(var1, var2xx -> var2xx.a(LootTable.a(var1, var2::add), var1)));
         NBTTagCompound var3 = new NBTTagCompound();
         ContainerUtil.a(var3, var2);
         NBTTagCompound var4 = ItemBlock.a(var0);
         if (var4 == null) {
            var4 = var3;
         } else {
            var4.a(var3);
         }

         ItemBlock.a(var0, this.b, var4);
         return var0;
      }
   }

   @Override
   public void a(LootCollector var0) {
      super.a(var0);

      for(int var1 = 0; var1 < this.a.size(); ++var1) {
         this.a.get(var1).a(var0.b(".entry[" + var1 + "]"));
      }
   }

   public static LootItemFunctionSetContents.a a(TileEntityTypes<?> var0) {
      return new LootItemFunctionSetContents.a(var0);
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionSetContents.a> {
      private final List<LootEntryAbstract> a = Lists.newArrayList();
      private final TileEntityTypes<?> b;

      public a(TileEntityTypes<?> var0) {
         this.b = var0;
      }

      protected LootItemFunctionSetContents.a a() {
         return this;
      }

      public LootItemFunctionSetContents.a a(LootEntryAbstract.a<?> var0) {
         this.a.add(var0.b());
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionSetContents(this.g(), this.b, this.a);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionSetContents> {
      public void a(JsonObject var0, LootItemFunctionSetContents var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("type", BuiltInRegistries.l.b(var1.b).toString());
         var0.add("entries", var2.serialize(var1.a));
      }

      public LootItemFunctionSetContents a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         LootEntryAbstract[] var3 = (LootEntryAbstract[])ChatDeserializer.a(var0, "entries", var1, LootEntryAbstract[].class);
         MinecraftKey var4 = new MinecraftKey(ChatDeserializer.h(var0, "type"));
         TileEntityTypes<?> var5 = BuiltInRegistries.l.b(var4).orElseThrow(() -> new JsonSyntaxException("Unknown block entity type id '" + var4 + "'"));
         return new LootItemFunctionSetContents(var2, var5, Arrays.asList(var3));
      }
   }
}
