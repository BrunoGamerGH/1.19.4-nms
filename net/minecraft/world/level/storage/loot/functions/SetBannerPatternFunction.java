package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnumBannerPatternType;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class SetBannerPatternFunction extends LootItemFunctionConditional {
   final List<Pair<Holder<EnumBannerPatternType>, EnumColor>> a;
   final boolean b;

   SetBannerPatternFunction(LootItemCondition[] var0, List<Pair<Holder<EnumBannerPatternType>, EnumColor>> var1, boolean var2) {
      super(var0);
      this.a = var1;
      this.b = var2;
   }

   @Override
   protected ItemStack a(ItemStack var0, LootTableInfo var1) {
      NBTTagCompound var2 = ItemBlock.a(var0);
      if (var2 == null) {
         var2 = new NBTTagCompound();
      }

      EnumBannerPatternType.a var3 = new EnumBannerPatternType.a();
      this.a.forEach(var3::a);
      NBTTagList var4 = var3.a();
      NBTTagList var5;
      if (this.b) {
         var5 = var2.c("Patterns", 10).e();
         var5.addAll(var4);
      } else {
         var5 = var4;
      }

      var2.a("Patterns", var5);
      ItemBlock.a(var0, TileEntityTypes.t, var2);
      return var0;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.x;
   }

   public static SetBannerPatternFunction.a a(boolean var0) {
      return new SetBannerPatternFunction.a(var0);
   }

   public static class a extends LootItemFunctionConditional.a<SetBannerPatternFunction.a> {
      private final Builder<Pair<Holder<EnumBannerPatternType>, EnumColor>> a = ImmutableList.builder();
      private final boolean b;

      a(boolean var0) {
         this.b = var0;
      }

      protected SetBannerPatternFunction.a a() {
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new SetBannerPatternFunction(this.g(), this.a.build(), this.b);
      }

      public SetBannerPatternFunction.a a(ResourceKey<EnumBannerPatternType> var0, EnumColor var1) {
         return this.a(BuiltInRegistries.ak.f(var0), var1);
      }

      public SetBannerPatternFunction.a a(Holder<EnumBannerPatternType> var0, EnumColor var1) {
         this.a.add(Pair.of(var0, var1));
         return this;
      }
   }

   public static class b extends LootItemFunctionConditional.c<SetBannerPatternFunction> {
      public void a(JsonObject var0, SetBannerPatternFunction var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         JsonArray var3 = new JsonArray();
         var1.a
            .forEach(
               var1x -> {
                  JsonObject var2x = new JsonObject();
                  var2x.addProperty(
                     "pattern",
                     ((ResourceKey)((Holder)var1x.getFirst()).e().orElseThrow(() -> new JsonSyntaxException("Unknown pattern: " + var1x.getFirst())))
                        .a()
                        .toString()
                  );
                  var2x.addProperty("color", ((EnumColor)var1x.getSecond()).b());
                  var3.add(var2x);
               }
            );
         var0.add("patterns", var3);
         var0.addProperty("append", var1.b);
      }

      public SetBannerPatternFunction a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         Builder<Pair<Holder<EnumBannerPatternType>, EnumColor>> var3 = ImmutableList.builder();
         JsonArray var4 = ChatDeserializer.u(var0, "patterns");

         for(int var5 = 0; var5 < var4.size(); ++var5) {
            JsonObject var6 = ChatDeserializer.m(var4.get(var5), "pattern[" + var5 + "]");
            String var7 = ChatDeserializer.h(var6, "pattern");
            Optional<? extends Holder<EnumBannerPatternType>> var8 = BuiltInRegistries.ak.b(ResourceKey.a(Registries.c, new MinecraftKey(var7)));
            if (var8.isEmpty()) {
               throw new JsonSyntaxException("Unknown pattern: " + var7);
            }

            String var9 = ChatDeserializer.h(var6, "color");
            EnumColor var10 = EnumColor.a(var9, null);
            if (var10 == null) {
               throw new JsonSyntaxException("Unknown color: " + var9);
            }

            var3.add(Pair.of(var8.get(), var10));
         }

         boolean var5 = ChatDeserializer.j(var0, "append");
         return new SetBannerPatternFunction(var2, var3.build(), var5);
      }
   }
}
