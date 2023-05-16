package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.LootDeserializationContext;
import net.minecraft.advancements.critereon.LootSerializationContext;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.ChatDeserializer;

public class Criterion {
   @Nullable
   private final CriterionInstance a;

   public Criterion(CriterionInstance var0) {
      this.a = var0;
   }

   public Criterion() {
      this.a = null;
   }

   public void a(PacketDataSerializer var0) {
   }

   public static Criterion a(JsonObject var0, LootDeserializationContext var1) {
      MinecraftKey var2 = new MinecraftKey(ChatDeserializer.h(var0, "trigger"));
      CriterionTrigger<?> var3 = CriterionTriggers.a(var2);
      if (var3 == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + var2);
      } else {
         CriterionInstance var4 = var3.a(ChatDeserializer.a(var0, "conditions", new JsonObject()), var1);
         return new Criterion(var4);
      }
   }

   public static Criterion b(PacketDataSerializer var0) {
      return new Criterion();
   }

   public static Map<String, Criterion> b(JsonObject var0, LootDeserializationContext var1) {
      Map<String, Criterion> var2 = Maps.newHashMap();

      for(Entry<String, JsonElement> var4 : var0.entrySet()) {
         var2.put(var4.getKey(), a(ChatDeserializer.m((JsonElement)var4.getValue(), "criterion"), var1));
      }

      return var2;
   }

   public static Map<String, Criterion> c(PacketDataSerializer var0) {
      return var0.a(PacketDataSerializer::s, Criterion::b);
   }

   public static void a(Map<String, Criterion> var0, PacketDataSerializer var1) {
      var1.a(var0, PacketDataSerializer::a, (var0x, var1x) -> var1x.a(var0x));
   }

   @Nullable
   public CriterionInstance a() {
      return this.a;
   }

   public JsonElement b() {
      if (this.a == null) {
         throw new JsonSyntaxException("Missing trigger");
      } else {
         JsonObject var0 = new JsonObject();
         var0.addProperty("trigger", this.a.a().toString());
         JsonObject var1 = this.a.a(LootSerializationContext.a);
         if (var1.size() != 0) {
            var0.add("conditions", var1);
         }

         return var0;
      }
   }
}
