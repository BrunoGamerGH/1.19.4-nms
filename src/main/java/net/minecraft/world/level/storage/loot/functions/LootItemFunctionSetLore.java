package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public class LootItemFunctionSetLore extends LootItemFunctionConditional {
   final boolean a;
   final List<IChatBaseComponent> b;
   @Nullable
   final LootTableInfo.EntityTarget c;

   public LootItemFunctionSetLore(LootItemCondition[] var0, boolean var1, List<IChatBaseComponent> var2, @Nullable LootTableInfo.EntityTarget var3) {
      super(var0);
      this.a = var1;
      this.b = ImmutableList.copyOf(var2);
      this.c = var3;
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.t;
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.c != null ? ImmutableSet.of(this.c.a()) : ImmutableSet.of();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      NBTTagList var2 = this.a(var0, !this.b.isEmpty());
      if (var2 != null) {
         if (this.a) {
            var2.clear();
         }

         UnaryOperator<IChatBaseComponent> var3 = LootItemFunctionSetName.a(var1, this.c);
         this.b.stream().map(var3).map(IChatBaseComponent.ChatSerializer::a).map(NBTTagString::a).forEach(var2::add);
      }

      return var0;
   }

   @Nullable
   private NBTTagList a(ItemStack var0, boolean var1) {
      NBTTagCompound var2;
      if (var0.t()) {
         var2 = var0.u();
      } else {
         if (!var1) {
            return null;
         }

         var2 = new NBTTagCompound();
         var0.c(var2);
      }

      NBTTagCompound var3;
      if (var2.b("display", 10)) {
         var3 = var2.p("display");
      } else {
         if (!var1) {
            return null;
         }

         var3 = new NBTTagCompound();
         var2.a("display", var3);
      }

      if (var3.b("Lore", 9)) {
         return var3.c("Lore", 8);
      } else if (var1) {
         NBTTagList var4 = new NBTTagList();
         var3.a("Lore", var4);
         return var4;
      } else {
         return null;
      }
   }

   public static LootItemFunctionSetLore.a c() {
      return new LootItemFunctionSetLore.a();
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionSetLore.a> {
      private boolean a;
      private LootTableInfo.EntityTarget b;
      private final List<IChatBaseComponent> c = Lists.newArrayList();

      public LootItemFunctionSetLore.a a(boolean var0) {
         this.a = var0;
         return this;
      }

      public LootItemFunctionSetLore.a a(LootTableInfo.EntityTarget var0) {
         this.b = var0;
         return this;
      }

      public LootItemFunctionSetLore.a a(IChatBaseComponent var0) {
         this.c.add(var0);
         return this;
      }

      protected LootItemFunctionSetLore.a a() {
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionSetLore(this.g(), this.a, this.c, this.b);
      }
   }

   public static class b extends LootItemFunctionConditional.c<LootItemFunctionSetLore> {
      public void a(JsonObject var0, LootItemFunctionSetLore var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.addProperty("replace", var1.a);
         JsonArray var3 = new JsonArray();

         for(IChatBaseComponent var5 : var1.b) {
            var3.add(IChatBaseComponent.ChatSerializer.c(var5));
         }

         var0.add("lore", var3);
         if (var1.c != null) {
            var0.add("entity", var2.serialize(var1.c));
         }
      }

      public LootItemFunctionSetLore a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         boolean var3 = ChatDeserializer.a(var0, "replace", false);
         List<IChatBaseComponent> var4 = Streams.stream(ChatDeserializer.u(var0, "lore"))
            .map(IChatBaseComponent.ChatSerializer::a)
            .collect(ImmutableList.toImmutableList());
         LootTableInfo.EntityTarget var5 = ChatDeserializer.a(var0, "entity", null, var1, LootTableInfo.EntityTarget.class);
         return new LootItemFunctionSetLore(var2, var3, var4, var5);
      }
   }
}
