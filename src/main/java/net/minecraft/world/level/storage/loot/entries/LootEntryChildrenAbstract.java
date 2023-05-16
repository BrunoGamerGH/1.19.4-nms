package net.minecraft.world.level.storage.loot.entries;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.level.storage.loot.LootCollector;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public abstract class LootEntryChildrenAbstract extends LootEntryAbstract {
   protected final LootEntryAbstract[] c;
   private final LootEntryChildren e;

   protected LootEntryChildrenAbstract(LootEntryAbstract[] var0, LootItemCondition[] var1) {
      super(var1);
      this.c = var0;
      this.e = this.a(var0);
   }

   @Override
   public void a(LootCollector var0) {
      super.a(var0);
      if (this.c.length == 0) {
         var0.a("Empty children list");
      }

      for(int var1 = 0; var1 < this.c.length; ++var1) {
         this.c[var1].a(var0.b(".entry[" + var1 + "]"));
      }
   }

   protected abstract LootEntryChildren a(LootEntryChildren[] var1);

   @Override
   public final boolean expand(LootTableInfo var0, Consumer<LootEntry> var1) {
      return !this.a(var0) ? false : this.e.expand(var0, var1);
   }

   public static <T extends LootEntryChildrenAbstract> LootEntryAbstract.Serializer<T> a(final LootEntryChildrenAbstract.a<T> var0) {
      return new LootEntryAbstract.Serializer<T>() {
         public void a(JsonObject var0x, T var1, JsonSerializationContext var2) {
            var0.add("children", var2.serialize(var1.c));
         }

         public final T a(JsonObject var0x, JsonDeserializationContext var1, LootItemCondition[] var2) {
            LootEntryAbstract[] var3 = (LootEntryAbstract[])ChatDeserializer.a(var0, "children", var1, LootEntryAbstract[].class);
            return var0.create(var3, var2);
         }
      };
   }

   @FunctionalInterface
   public interface a<T extends LootEntryChildrenAbstract> {
      T create(LootEntryAbstract[] var1, LootItemCondition[] var2);
   }
}
