package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.ChatDeserializer;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   final Map<String, CriterionProgress> a;
   private String[][] b = new String[0][];

   private AdvancementProgress(Map<String, CriterionProgress> var0) {
      this.a = var0;
   }

   public AdvancementProgress() {
      this.a = Maps.newHashMap();
   }

   public void a(Map<String, Criterion> var0, String[][] var1) {
      Set<String> var2 = var0.keySet();
      this.a.entrySet().removeIf(var1x -> !var2.contains(var1x.getKey()));

      for(String var4 : var2) {
         if (!this.a.containsKey(var4)) {
            this.a.put(var4, new CriterionProgress());
         }
      }

      this.b = var1;
   }

   public boolean a() {
      if (this.b.length == 0) {
         return false;
      } else {
         for(String[] var3 : this.b) {
            boolean var4 = false;

            for(String var8 : var3) {
               CriterionProgress var9 = this.c(var8);
               if (var9 != null && var9.a()) {
                  var4 = true;
                  break;
               }
            }

            if (!var4) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean b() {
      for(CriterionProgress var1 : this.a.values()) {
         if (var1.a()) {
            return true;
         }
      }

      return false;
   }

   public boolean a(String var0) {
      CriterionProgress var1 = this.a.get(var0);
      if (var1 != null && !var1.a()) {
         var1.b();
         return true;
      } else {
         return false;
      }
   }

   public boolean b(String var0) {
      CriterionProgress var1 = this.a.get(var0);
      if (var1 != null && var1.a()) {
         var1.c();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "AdvancementProgress{criteria=" + this.a + ", requirements=" + Arrays.deepToString(this.b) + "}";
   }

   public void a(PacketDataSerializer var0) {
      var0.a(this.a, PacketDataSerializer::a, (var0x, var1x) -> var1x.a(var0x));
   }

   public static AdvancementProgress b(PacketDataSerializer var0) {
      Map<String, CriterionProgress> var1 = var0.a(PacketDataSerializer::s, CriterionProgress::b);
      return new AdvancementProgress(var1);
   }

   @Nullable
   public CriterionProgress c(String var0) {
      return this.a.get(var0);
   }

   public float c() {
      if (this.a.isEmpty()) {
         return 0.0F;
      } else {
         float var0 = (float)this.b.length;
         float var1 = (float)this.h();
         return var1 / var0;
      }
   }

   @Nullable
   public String d() {
      if (this.a.isEmpty()) {
         return null;
      } else {
         int var0 = this.b.length;
         if (var0 <= 1) {
            return null;
         } else {
            int var1 = this.h();
            return var1 + "/" + var0;
         }
      }
   }

   private int h() {
      int var0 = 0;

      for(String[] var4 : this.b) {
         boolean var5 = false;

         for(String var9 : var4) {
            CriterionProgress var10 = this.c(var9);
            if (var10 != null && var10.a()) {
               var5 = true;
               break;
            }
         }

         if (var5) {
            ++var0;
         }
      }

      return var0;
   }

   public Iterable<String> e() {
      List<String> var0 = Lists.newArrayList();

      for(Entry<String, CriterionProgress> var2 : this.a.entrySet()) {
         if (!var2.getValue().a()) {
            var0.add(var2.getKey());
         }
      }

      return var0;
   }

   public Iterable<String> f() {
      List<String> var0 = Lists.newArrayList();

      for(Entry<String, CriterionProgress> var2 : this.a.entrySet()) {
         if (var2.getValue().a()) {
            var0.add(var2.getKey());
         }
      }

      return var0;
   }

   @Nullable
   public Date g() {
      Date var0 = null;

      for(CriterionProgress var2 : this.a.values()) {
         if (var2.a() && (var0 == null || var2.d().before(var0))) {
            var0 = var2.d();
         }
      }

      return var0;
   }

   public int a(AdvancementProgress var0) {
      Date var1 = this.g();
      Date var2 = var0.g();
      if (var1 == null && var2 != null) {
         return 1;
      } else if (var1 != null && var2 == null) {
         return -1;
      } else {
         return var1 == null && var2 == null ? 0 : var1.compareTo(var2);
      }
   }

   public static class a implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public JsonElement a(AdvancementProgress var0, Type var1, JsonSerializationContext var2) {
         JsonObject var3 = new JsonObject();
         JsonObject var4 = new JsonObject();

         for(Entry<String, CriterionProgress> var6 : var0.a.entrySet()) {
            CriterionProgress var7 = var6.getValue();
            if (var7.a()) {
               var4.add(var6.getKey(), var7.e());
            }
         }

         if (!var4.entrySet().isEmpty()) {
            var3.add("criteria", var4);
         }

         var3.addProperty("done", var0.a());
         return var3;
      }

      public AdvancementProgress a(JsonElement var0, Type var1, JsonDeserializationContext var2) throws JsonParseException {
         JsonObject var3 = ChatDeserializer.m(var0, "advancement");
         JsonObject var4 = ChatDeserializer.a(var3, "criteria", new JsonObject());
         AdvancementProgress var5 = new AdvancementProgress();

         for(Entry<String, JsonElement> var7 : var4.entrySet()) {
            String var8 = var7.getKey();
            var5.a.put(var8, CriterionProgress.a(ChatDeserializer.a((JsonElement)var7.getValue(), var8)));
         }

         return var5;
      }
   }
}
