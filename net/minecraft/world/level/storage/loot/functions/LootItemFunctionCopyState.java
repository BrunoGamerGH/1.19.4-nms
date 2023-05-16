package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionCopyState extends LootItemFunctionConditional {
   final Block a;
   final Set<IBlockState<?>> b;

   LootItemFunctionCopyState(LootItemCondition[] var0, Block var1, Set<IBlockState<?>> var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.w;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return ImmutableSet.of(LootContextParameters.g);
   }

   @Override
   protected ItemStack a(ItemStack var0, LootTableInfo var1) {
      IBlockData var2 = var1.c(LootContextParameters.g);
      if (var2 != null) {
         NBTTagCompound var3 = var0.v();
         NBTTagCompound var4;
         if (var3.b("BlockStateTag", 10)) {
            var4 = var3.p("BlockStateTag");
         } else {
            var4 = new NBTTagCompound();
            var3.a("BlockStateTag", var4);
         }

         this.b.stream().filter(var2::b).forEach(var2x -> var4.a(var2x.f(), a(var2, var2x)));
      }

      return var0;
   }

   public static LootItemFunctionCopyState.a a(Block var0) {
      return new LootItemFunctionCopyState.a(var0);
   }

   private static <T extends Comparable<T>> String a(IBlockData var0, IBlockState<T> var1) {
      T var2 = var0.c(var1);
      return var1.a(var2);
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionCopyState.a> {
      private final Block a;
      private final Set<IBlockState<?>> b = Sets.newHashSet();

      a(Block var0) {
         this.a = var0;
      }

      public LootItemFunctionCopyState.a a(IBlockState<?> var0) {
         if (!this.a.n().d().contains(var0)) {
            throw new IllegalStateException("Property " + var0 + " is not present on block " + this.a);
         } else {
            this.b.add(var0);
            return this;
         }
      }

      protected LootItemFunctionCopyState.a a() {
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionCopyState(this.g(), this.a, this.b);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionCopyState> {
      public void a(JsonObject var0, LootItemFunctionCopyState var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("block", BuiltInRegistries.f.b(var1.a).toString());
         JsonArray var3 = new JsonArray();
         var1.b.forEach(var1x -> var3.add(var1x.f()));
         var0.add("properties", var3);
      }

      public LootItemFunctionCopyState a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         MinecraftKey var3 = new MinecraftKey(ChatDeserializer.h(var0, "block"));
         Block var4 = BuiltInRegistries.f.b(var3).orElseThrow(() -> new IllegalArgumentException("Can't find block " + var3));
         BlockStateList<Block, IBlockData> var5 = var4.n();
         Set<IBlockState<?>> var6 = Sets.newHashSet();
         JsonArray var7 = ChatDeserializer.a(var0, "properties", null);
         if (var7 != null) {
            var7.forEach(var2x -> var6.add(var5.a(ChatDeserializer.a(var2x, "property"))));
         }

         return new LootItemFunctionCopyState(var2, var4, var6);
      }
   }
}
