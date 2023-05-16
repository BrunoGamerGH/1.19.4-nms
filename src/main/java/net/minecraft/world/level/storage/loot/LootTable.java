package net.minecraft.world.level.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.SystemUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionUser;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.world.LootGenerateEvent;
import org.slf4j.Logger;

public class LootTable {
   static final Logger c = LogUtils.getLogger();
   public static final LootTable a = new LootTable(LootContextParameterSets.a, new LootSelector[0], new LootItemFunction[0]);
   public static final LootContextParameterSet b = LootContextParameterSets.l;
   final LootContextParameterSet d;
   final LootSelector[] e;
   final LootItemFunction[] f;
   private final BiFunction<ItemStack, LootTableInfo, ItemStack> g;

   LootTable(LootContextParameterSet lootcontextparameterset, LootSelector[] alootselector, LootItemFunction[] alootitemfunction) {
      this.d = lootcontextparameterset;
      this.e = alootselector;
      this.f = alootitemfunction;
      this.g = LootItemFunctions.a(alootitemfunction);
   }

   public static Consumer<ItemStack> a(LootTableInfo loottableinfo, Consumer<ItemStack> consumer) {
      return itemstack -> {
         if (itemstack.a(loottableinfo.c().G())) {
            if (itemstack.K() < itemstack.f()) {
               consumer.accept(itemstack);
            } else {
               int i = itemstack.K();

               while(i > 0) {
                  ItemStack itemstack1 = itemstack.o();
                  itemstack1.f(Math.min(itemstack.f(), i));
                  i -= itemstack1.K();
                  consumer.accept(itemstack1);
               }
            }
         }
      };
   }

   public void b(LootTableInfo loottableinfo, Consumer<ItemStack> consumer) {
      if (loottableinfo.a(this)) {
         Consumer<ItemStack> consumer1 = LootItemFunction.a(this.g, consumer, loottableinfo);

         for(LootSelector lootselector : this.e) {
            lootselector.a(consumer1, loottableinfo);
         }

         loottableinfo.b(this);
      } else {
         c.warn("Detected infinite loop in loot tables");
      }
   }

   public void c(LootTableInfo loottableinfo, Consumer<ItemStack> consumer) {
      this.b(loottableinfo, a(loottableinfo, consumer));
   }

   public ObjectArrayList<ItemStack> a(LootTableInfo loottableinfo) {
      ObjectArrayList<ItemStack> objectarraylist = new ObjectArrayList();
      this.c(loottableinfo, objectarraylist::add);
      return objectarraylist;
   }

   public LootContextParameterSet a() {
      return this.d;
   }

   public void a(LootCollector lootcollector) {
      for(int i = 0; i < this.e.length; ++i) {
         this.e[i].a(lootcollector.b(".pools[" + i + "]"));
      }

      for(int var3 = 0; var3 < this.f.length; ++var3) {
         this.f[var3].a(lootcollector.b(".functions[" + var3 + "]"));
      }
   }

   public void a(IInventory iinventory, LootTableInfo loottableinfo) {
      this.fillInventory(iinventory, loottableinfo, false);
   }

   public void fillInventory(IInventory iinventory, LootTableInfo loottableinfo, boolean plugin) {
      ObjectArrayList<ItemStack> objectarraylist = this.a(loottableinfo);
      RandomSource randomsource = loottableinfo.a();
      LootGenerateEvent event = CraftEventFactory.callLootGenerateEvent(iinventory, this, loottableinfo, objectarraylist, plugin);
      if (!event.isCancelled()) {
         objectarraylist = event.getLoot().stream().map(CraftItemStack::asNMSCopy).collect(ObjectArrayList.toList());
         List<Integer> list = this.a(iinventory, randomsource);
         this.a(objectarraylist, list.size(), randomsource);
         ObjectListIterator objectlistiterator = objectarraylist.iterator();

         while(objectlistiterator.hasNext()) {
            ItemStack itemstack = (ItemStack)objectlistiterator.next();
            if (list.isEmpty()) {
               c.warn("Tried to over-fill a container");
               return;
            }

            if (itemstack.b()) {
               iinventory.a(list.remove(list.size() - 1), ItemStack.b);
            } else {
               iinventory.a(list.remove(list.size() - 1), itemstack);
            }
         }
      }
   }

   private void a(ObjectArrayList<ItemStack> objectarraylist, int i, RandomSource randomsource) {
      List<ItemStack> list = Lists.newArrayList();
      ObjectListIterator objectlistiterator = objectarraylist.iterator();

      while(objectlistiterator.hasNext()) {
         ItemStack itemstack = (ItemStack)objectlistiterator.next();
         if (itemstack.b()) {
            objectlistiterator.remove();
         } else if (itemstack.K() > 1) {
            list.add(itemstack);
            objectlistiterator.remove();
         }
      }

      while(i - objectarraylist.size() - list.size() > 0 && !list.isEmpty()) {
         ItemStack itemstack1 = list.remove(MathHelper.a(randomsource, 0, list.size() - 1));
         int j = MathHelper.a(randomsource, 1, itemstack1.K() / 2);
         ItemStack itemstack2 = itemstack1.a(j);
         if (itemstack1.K() > 1 && randomsource.h()) {
            list.add(itemstack1);
         } else {
            objectarraylist.add(itemstack1);
         }

         if (itemstack2.K() > 1 && randomsource.h()) {
            list.add(itemstack2);
         } else {
            objectarraylist.add(itemstack2);
         }
      }

      objectarraylist.addAll(list);
      SystemUtils.b(objectarraylist, randomsource);
   }

   private List<Integer> a(IInventory iinventory, RandomSource randomsource) {
      ObjectArrayList<Integer> objectarraylist = new ObjectArrayList();

      for(int i = 0; i < iinventory.b(); ++i) {
         if (iinventory.a(i).b()) {
            objectarraylist.add(i);
         }
      }

      SystemUtils.b(objectarraylist, randomsource);
      return objectarraylist;
   }

   public static LootTable.a b() {
      return new LootTable.a();
   }

   public static class a implements LootItemFunctionUser<LootTable.a> {
      private final List<LootSelector> a = Lists.newArrayList();
      private final List<LootItemFunction> b = Lists.newArrayList();
      private LootContextParameterSet c = LootTable.b;

      public LootTable.a a(LootSelector.a lootselector_a) {
         this.a.add(lootselector_a.b());
         return this;
      }

      public LootTable.a a(LootContextParameterSet lootcontextparameterset) {
         this.c = lootcontextparameterset;
         return this;
      }

      public LootTable.a a(LootItemFunction.a lootitemfunction_a) {
         this.b.add(lootitemfunction_a.b());
         return this;
      }

      public LootTable.a a() {
         return this;
      }

      public LootTable b() {
         return new LootTable(this.c, this.a.toArray(new LootSelector[0]), this.b.toArray(new LootItemFunction[0]));
      }
   }

   public static class b implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
      public LootTable a(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
         JsonObject jsonobject = ChatDeserializer.m(jsonelement, "loot table");
         LootSelector[] alootselector = (LootSelector[])ChatDeserializer.a(
            jsonobject, "pools", new LootSelector[0], jsondeserializationcontext, LootSelector[].class
         );
         LootContextParameterSet lootcontextparameterset = null;
         if (jsonobject.has("type")) {
            String s = ChatDeserializer.h(jsonobject, "type");
            lootcontextparameterset = LootContextParameterSets.a(new MinecraftKey(s));
         }

         LootItemFunction[] alootitemfunction = (LootItemFunction[])ChatDeserializer.a(
            jsonobject, "functions", new LootItemFunction[0], jsondeserializationcontext, LootItemFunction[].class
         );
         return new LootTable(lootcontextparameterset != null ? lootcontextparameterset : LootContextParameterSets.l, alootselector, alootitemfunction);
      }

      public JsonElement a(LootTable loottable, Type type, JsonSerializationContext jsonserializationcontext) {
         JsonObject jsonobject = new JsonObject();
         if (loottable.d != LootTable.b) {
            MinecraftKey minecraftkey = LootContextParameterSets.a(loottable.d);
            if (minecraftkey != null) {
               jsonobject.addProperty("type", minecraftkey.toString());
            } else {
               LootTable.c.warn("Failed to find id for param set {}", loottable.d);
            }
         }

         if (loottable.e.length > 0) {
            jsonobject.add("pools", jsonserializationcontext.serialize(loottable.e));
         }

         if (!ArrayUtils.isEmpty(loottable.f)) {
            jsonobject.add("functions", jsonserializationcontext.serialize(loottable.f));
         }

         return jsonobject;
      }
   }
}
