package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CustomFunction;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;

public class AdvancementRewards {
   public static final AdvancementRewards a = new AdvancementRewards(0, new MinecraftKey[0], new MinecraftKey[0], CustomFunction.a.a);
   private final int b;
   private final MinecraftKey[] c;
   private final MinecraftKey[] d;
   private final CustomFunction.a e;

   public AdvancementRewards(int var0, MinecraftKey[] var1, MinecraftKey[] var2, CustomFunction.a var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public MinecraftKey[] a() {
      return this.d;
   }

   public void a(EntityPlayer var0) {
      var0.d(this.b);
      LootTableInfo var1 = new LootTableInfo.Builder(var0.x())
         .a(LootContextParameters.a, var0)
         .a(LootContextParameters.f, var0.de())
         .a(var0.dZ())
         .a(LootContextParameterSets.j);
      boolean var2 = false;

      for(MinecraftKey var6 : this.c) {
         ObjectListIterator var8 = var0.c.aH().a(var6).a(var1).iterator();

         while(var8.hasNext()) {
            ItemStack var8x = (ItemStack)var8.next();
            if (var0.i(var8x)) {
               var0.H.a(null, var0.dl(), var0.dn(), var0.dr(), SoundEffects.lR, SoundCategory.h, 0.2F, ((var0.dZ().i() - var0.dZ().i()) * 0.7F + 1.0F) * 2.0F);
               var2 = true;
            } else {
               EntityItem var9 = var0.a(var8x, false);
               if (var9 != null) {
                  var9.o();
                  var9.b(var0.cs());
               }
            }
         }
      }

      if (var2) {
         var0.bP.d();
      }

      if (this.d.length > 0) {
         var0.a(this.d);
      }

      MinecraftServer var3 = var0.c;
      this.e.a(var3.aA()).ifPresent(var2x -> var3.aA().a(var2x, var0.cZ().a().a(2)));
   }

   @Override
   public String toString() {
      return "AdvancementRewards{experience="
         + this.b
         + ", loot="
         + Arrays.toString((Object[])this.c)
         + ", recipes="
         + Arrays.toString((Object[])this.d)
         + ", function="
         + this.e
         + "}";
   }

   public JsonElement b() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = new JsonObject();
         if (this.b != 0) {
            var0.addProperty("experience", this.b);
         }

         if (this.c.length > 0) {
            JsonArray var1 = new JsonArray();

            for(MinecraftKey var5 : this.c) {
               var1.add(var5.toString());
            }

            var0.add("loot", var1);
         }

         if (this.d.length > 0) {
            JsonArray var1 = new JsonArray();

            for(MinecraftKey var5 : this.d) {
               var1.add(var5.toString());
            }

            var0.add("recipes", var1);
         }

         if (this.e.a() != null) {
            var0.addProperty("function", this.e.a().toString());
         }

         return var0;
      }
   }

   public static AdvancementRewards a(JsonObject var0) throws JsonParseException {
      int var1 = ChatDeserializer.a(var0, "experience", 0);
      JsonArray var2 = ChatDeserializer.a(var0, "loot", new JsonArray());
      MinecraftKey[] var3 = new MinecraftKey[var2.size()];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         var3[var4] = new MinecraftKey(ChatDeserializer.a(var2.get(var4), "loot[" + var4 + "]"));
      }

      JsonArray var4 = ChatDeserializer.a(var0, "recipes", new JsonArray());
      MinecraftKey[] var5 = new MinecraftKey[var4.size()];

      for(int var6 = 0; var6 < var5.length; ++var6) {
         var5[var6] = new MinecraftKey(ChatDeserializer.a(var4.get(var6), "recipes[" + var6 + "]"));
      }

      CustomFunction.a var6;
      if (var0.has("function")) {
         var6 = new CustomFunction.a(new MinecraftKey(ChatDeserializer.h(var0, "function")));
      } else {
         var6 = CustomFunction.a.a;
      }

      return new AdvancementRewards(var1, var3, var5, var6);
   }

   public static class a {
      private int a;
      private final List<MinecraftKey> b = Lists.newArrayList();
      private final List<MinecraftKey> c = Lists.newArrayList();
      @Nullable
      private MinecraftKey d;

      public static AdvancementRewards.a a(int var0) {
         return new AdvancementRewards.a().b(var0);
      }

      public AdvancementRewards.a b(int var0) {
         this.a += var0;
         return this;
      }

      public static AdvancementRewards.a a(MinecraftKey var0) {
         return new AdvancementRewards.a().b(var0);
      }

      public AdvancementRewards.a b(MinecraftKey var0) {
         this.b.add(var0);
         return this;
      }

      public static AdvancementRewards.a c(MinecraftKey var0) {
         return new AdvancementRewards.a().d(var0);
      }

      public AdvancementRewards.a d(MinecraftKey var0) {
         this.c.add(var0);
         return this;
      }

      public static AdvancementRewards.a e(MinecraftKey var0) {
         return new AdvancementRewards.a().f(var0);
      }

      public AdvancementRewards.a f(MinecraftKey var0) {
         this.d = var0;
         return this;
      }

      public AdvancementRewards a() {
         return new AdvancementRewards(
            this.a,
            this.b.toArray(new MinecraftKey[0]),
            this.c.toArray(new MinecraftKey[0]),
            this.d == null ? CustomFunction.a.a : new CustomFunction.a(this.d)
         );
      }
   }
}
