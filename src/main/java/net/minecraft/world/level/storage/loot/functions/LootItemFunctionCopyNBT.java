package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;

public class LootItemFunctionCopyNBT extends LootItemFunctionConditional {
   final NbtProvider a;
   final List<LootItemFunctionCopyNBT.b> b;

   LootItemFunctionCopyNBT(LootItemCondition[] var0, NbtProvider var1, List<LootItemFunctionCopyNBT.b> var2) {
      super(var0);
      this.a = var1;
      this.b = ImmutableList.copyOf(var2);
   }

   @Override
   public LootItemFunctionType a() {
      return LootItemFunctions.v;
   }

   static ArgumentNBTKey.g a(String var0) {
      try {
         return new ArgumentNBTKey().a(new StringReader(var0));
      } catch (CommandSyntaxException var2) {
         throw new IllegalArgumentException("Failed to parse path " + var0, var2);
      }
   }

   @Override
   public Set<LootContextParameter<?>> b() {
      return this.a.b();
   }

   @Override
   public ItemStack a(ItemStack var0, LootTableInfo var1) {
      NBTBase var2 = this.a.a(var1);
      if (var2 != null) {
         this.b.forEach(var2x -> var2x.a(var0::v, var2));
      }

      return var0;
   }

   public static LootItemFunctionCopyNBT.a a(NbtProvider var0) {
      return new LootItemFunctionCopyNBT.a(var0);
   }

   public static LootItemFunctionCopyNBT.a a(LootTableInfo.EntityTarget var0) {
      return new LootItemFunctionCopyNBT.a(ContextNbtProvider.a(var0));
   }

   public static enum Action {
      a("replace") {
         @Override
         public void a(NBTBase var0, ArgumentNBTKey.g var1, List<NBTBase> var2) throws CommandSyntaxException {
            var1.a(var0, (NBTBase)Iterables.getLast(var2));
         }
      },
      b("append") {
         @Override
         public void a(NBTBase var0, ArgumentNBTKey.g var1, List<NBTBase> var2) throws CommandSyntaxException {
            List<NBTBase> var3 = var1.a(var0, NBTTagList::new);
            var3.forEach(var1x -> {
               if (var1x instanceof NBTTagList) {
                  var2.forEach(var1xx -> ((NBTTagList)var1x).add(var1xx.d()));
               }
            });
         }
      },
      c("merge") {
         @Override
         public void a(NBTBase var0, ArgumentNBTKey.g var1, List<NBTBase> var2) throws CommandSyntaxException {
            List<NBTBase> var3 = var1.a(var0, NBTTagCompound::new);
            var3.forEach(var1x -> {
               if (var1x instanceof NBTTagCompound) {
                  var2.forEach(var1xx -> {
                     if (var1xx instanceof NBTTagCompound) {
                        ((NBTTagCompound)var1x).a((NBTTagCompound)var1xx);
                     }
                  });
               }
            });
         }
      };

      final String d;

      public abstract void a(NBTBase var1, ArgumentNBTKey.g var2, List<NBTBase> var3) throws CommandSyntaxException;

      Action(String var2) {
         this.d = var2;
      }

      public static LootItemFunctionCopyNBT.Action a(String var0) {
         for(LootItemFunctionCopyNBT.Action var4 : values()) {
            if (var4.d.equals(var0)) {
               return var4;
            }
         }

         throw new IllegalArgumentException("Invalid merge strategy" + var0);
      }
   }

   public static class a extends LootItemFunctionConditional.a<LootItemFunctionCopyNBT.a> {
      private final NbtProvider a;
      private final List<LootItemFunctionCopyNBT.b> b = Lists.newArrayList();

      a(NbtProvider var0) {
         this.a = var0;
      }

      public LootItemFunctionCopyNBT.a a(String var0, String var1, LootItemFunctionCopyNBT.Action var2) {
         this.b.add(new LootItemFunctionCopyNBT.b(var0, var1, var2));
         return this;
      }

      public LootItemFunctionCopyNBT.a a(String var0, String var1) {
         return this.a(var0, var1, LootItemFunctionCopyNBT.Action.a);
      }

      protected LootItemFunctionCopyNBT.a a() {
         return this;
      }

      @Override
      public LootItemFunction b() {
         return new LootItemFunctionCopyNBT(this.g(), this.a, this.b);
      }
   }

   static class b {
      private final String a;
      private final ArgumentNBTKey.g b;
      private final String c;
      private final ArgumentNBTKey.g d;
      private final LootItemFunctionCopyNBT.Action e;

      b(String var0, String var1, LootItemFunctionCopyNBT.Action var2) {
         this.a = var0;
         this.b = LootItemFunctionCopyNBT.a(var0);
         this.c = var1;
         this.d = LootItemFunctionCopyNBT.a(var1);
         this.e = var2;
      }

      public void a(Supplier<NBTBase> var0, NBTBase var1) {
         try {
            List<NBTBase> var2 = this.b.a(var1);
            if (!var2.isEmpty()) {
               this.e.a(var0.get(), this.d, var2);
            }
         } catch (CommandSyntaxException var4) {
         }
      }

      public JsonObject a() {
         JsonObject var0 = new JsonObject();
         var0.addProperty("source", this.a);
         var0.addProperty("target", this.c);
         var0.addProperty("op", this.e.d);
         return var0;
      }

      public static LootItemFunctionCopyNBT.b a(JsonObject var0) {
         String var1 = ChatDeserializer.h(var0, "source");
         String var2 = ChatDeserializer.h(var0, "target");
         LootItemFunctionCopyNBT.Action var3 = LootItemFunctionCopyNBT.Action.a(ChatDeserializer.h(var0, "op"));
         return new LootItemFunctionCopyNBT.b(var1, var2, var3);
      }
   }

   public static class d extends LootItemFunctionConditional.c<LootItemFunctionCopyNBT> {
      public void a(JsonObject var0, LootItemFunctionCopyNBT var1, JsonSerializationContext var2) {
         super.a(var0, var1, var2);
         var0.add("source", var2.serialize(var1.a));
         JsonArray var3 = new JsonArray();
         var1.b.stream().map(LootItemFunctionCopyNBT.b::a).forEach(var3::add);
         var0.add("ops", var3);
      }

      public LootItemFunctionCopyNBT a(JsonObject var0, JsonDeserializationContext var1, LootItemCondition[] var2) {
         NbtProvider var3 = ChatDeserializer.a(var0, "source", var1, NbtProvider.class);
         List<LootItemFunctionCopyNBT.b> var4 = Lists.newArrayList();

         for(JsonElement var7 : ChatDeserializer.u(var0, "ops")) {
            JsonObject var8 = ChatDeserializer.m(var7, "op");
            var4.add(LootItemFunctionCopyNBT.b.a(var8));
         }

         return new LootItemFunctionCopyNBT(var2, var3, var4);
      }
   }
}
