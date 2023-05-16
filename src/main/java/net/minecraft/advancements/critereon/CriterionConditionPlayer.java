package net.minecraft.advancements.critereon;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.AdvancementDataPlayer;
import net.minecraft.server.AdvancementDataWorld;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticManager;
import net.minecraft.stats.StatisticWrapper;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionEntity;
import net.minecraft.world.phys.Vec3D;

public class CriterionConditionPlayer implements EntitySubPredicate {
   public static final int b = 100;
   private final CriterionConditionValue.IntegerRange c;
   @Nullable
   private final EnumGamemode d;
   private final Map<Statistic<?>, CriterionConditionValue.IntegerRange> e;
   private final Object2BooleanMap<MinecraftKey> f;
   private final Map<MinecraftKey, CriterionConditionPlayer.c> g;
   private final CriterionConditionEntity h;

   private static CriterionConditionPlayer.c b(JsonElement var0) {
      if (var0.isJsonPrimitive()) {
         boolean var1 = var0.getAsBoolean();
         return new CriterionConditionPlayer.b(var1);
      } else {
         Object2BooleanMap<String> var1 = new Object2BooleanOpenHashMap();
         JsonObject var2 = ChatDeserializer.m(var0, "criterion data");
         var2.entrySet().forEach(var1x -> {
            boolean var2x = ChatDeserializer.c((JsonElement)var1x.getValue(), "criterion test");
            var1.put((String)var1x.getKey(), var2x);
         });
         return new CriterionConditionPlayer.a(var1);
      }
   }

   CriterionConditionPlayer(
      CriterionConditionValue.IntegerRange var0,
      @Nullable EnumGamemode var1,
      Map<Statistic<?>, CriterionConditionValue.IntegerRange> var2,
      Object2BooleanMap<MinecraftKey> var3,
      Map<MinecraftKey, CriterionConditionPlayer.c> var4,
      CriterionConditionEntity var5
   ) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = var5;
   }

   @Override
   public boolean a(Entity var0, WorldServer var1, @Nullable Vec3D var2) {
      if (!(var0 instanceof EntityPlayer)) {
         return false;
      } else {
         EntityPlayer var3 = (EntityPlayer)var0;
         if (!this.c.d(var3.cc)) {
            return false;
         } else if (this.d != null && this.d != var3.d.b()) {
            return false;
         } else {
            StatisticManager var4 = var3.D();

            for(Entry<Statistic<?>, CriterionConditionValue.IntegerRange> var6 : this.e.entrySet()) {
               int var7 = var4.a(var6.getKey());
               if (!var6.getValue().d(var7)) {
                  return false;
               }
            }

            RecipeBook var5 = var3.E();
            ObjectIterator var13 = this.f.object2BooleanEntrySet().iterator();

            while(var13.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<MinecraftKey> var7 = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry)var13.next();
               if (var5.b((MinecraftKey)var7.getKey()) != var7.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.g.isEmpty()) {
               AdvancementDataPlayer var6 = var3.M();
               AdvancementDataWorld var7 = var3.cH().az();

               for(Entry<MinecraftKey, CriterionConditionPlayer.c> var9 : this.g.entrySet()) {
                  Advancement var10 = var7.a(var9.getKey());
                  if (var10 == null || !var9.getValue().test(var6.b(var10))) {
                     return false;
                  }
               }
            }

            if (this.h != CriterionConditionEntity.a) {
               Vec3D var6 = var3.bk();
               Vec3D var7 = var3.j(1.0F);
               Vec3D var8 = var6.b(var7.c * 100.0, var7.d * 100.0, var7.e * 100.0);
               MovingObjectPositionEntity var9 = ProjectileHelper.a(var3.H, var3, var6, var8, new AxisAlignedBB(var6, var8).g(1.0), var0x -> !var0x.F_(), 0.0F);
               if (var9 == null || var9.c() != MovingObjectPosition.EnumMovingObjectType.c) {
                  return false;
               }

               Entity var10 = var9.a();
               if (!this.h.a(var3, var10) || !var3.B(var10)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static CriterionConditionPlayer a(JsonObject var0) {
      CriterionConditionValue.IntegerRange var1 = CriterionConditionValue.IntegerRange.a(var0.get("level"));
      String var2 = ChatDeserializer.a(var0, "gamemode", "");
      EnumGamemode var3 = EnumGamemode.a(var2, null);
      Map<Statistic<?>, CriterionConditionValue.IntegerRange> var4 = Maps.newHashMap();
      JsonArray var5 = ChatDeserializer.a(var0, "stats", null);
      if (var5 != null) {
         for(JsonElement var7 : var5) {
            JsonObject var8 = ChatDeserializer.m(var7, "stats entry");
            MinecraftKey var9 = new MinecraftKey(ChatDeserializer.h(var8, "type"));
            StatisticWrapper<?> var10 = BuiltInRegistries.x.a(var9);
            if (var10 == null) {
               throw new JsonParseException("Invalid stat type: " + var9);
            }

            MinecraftKey var11 = new MinecraftKey(ChatDeserializer.h(var8, "stat"));
            Statistic<?> var12 = a(var10, var11);
            CriterionConditionValue.IntegerRange var13 = CriterionConditionValue.IntegerRange.a(var8.get("value"));
            var4.put(var12, var13);
         }
      }

      Object2BooleanMap<MinecraftKey> var6 = new Object2BooleanOpenHashMap();
      JsonObject var7 = ChatDeserializer.a(var0, "recipes", new JsonObject());

      for(Entry<String, JsonElement> var9 : var7.entrySet()) {
         MinecraftKey var10 = new MinecraftKey(var9.getKey());
         boolean var11 = ChatDeserializer.c((JsonElement)var9.getValue(), "recipe present");
         var6.put(var10, var11);
      }

      Map<MinecraftKey, CriterionConditionPlayer.c> var8 = Maps.newHashMap();
      JsonObject var9 = ChatDeserializer.a(var0, "advancements", new JsonObject());

      for(Entry<String, JsonElement> var11 : var9.entrySet()) {
         MinecraftKey var12 = new MinecraftKey(var11.getKey());
         CriterionConditionPlayer.c var13 = b((JsonElement)var11.getValue());
         var8.put(var12, var13);
      }

      CriterionConditionEntity var10 = CriterionConditionEntity.a(var0.get("looking_at"));
      return new CriterionConditionPlayer(var1, var3, var4, var6, var8, var10);
   }

   private static <T> Statistic<T> a(StatisticWrapper<T> var0, MinecraftKey var1) {
      IRegistry<T> var2 = var0.a();
      T var3 = var2.a(var1);
      if (var3 == null) {
         throw new JsonParseException("Unknown object " + var1 + " for stat type " + BuiltInRegistries.x.b(var0));
      } else {
         return var0.b(var3);
      }
   }

   private static <T> MinecraftKey a(Statistic<T> var0) {
      return var0.a().a().b(var0.b());
   }

   @Override
   public JsonObject a() {
      JsonObject var0 = new JsonObject();
      var0.add("level", this.c.d());
      if (this.d != null) {
         var0.addProperty("gamemode", this.d.b());
      }

      if (!this.e.isEmpty()) {
         JsonArray var1 = new JsonArray();
         this.e.forEach((var1x, var2x) -> {
            JsonObject var3x = new JsonObject();
            var3x.addProperty("type", BuiltInRegistries.x.b(var1x.a()).toString());
            var3x.addProperty("stat", a(var1x).toString());
            var3x.add("value", var2x.d());
            var1.add(var3x);
         });
         var0.add("stats", var1);
      }

      if (!this.f.isEmpty()) {
         JsonObject var1 = new JsonObject();
         this.f.forEach((var1x, var2x) -> var1.addProperty(var1x.toString(), var2x));
         var0.add("recipes", var1);
      }

      if (!this.g.isEmpty()) {
         JsonObject var1 = new JsonObject();
         this.g.forEach((var1x, var2x) -> var1.add(var1x.toString(), var2x.a()));
         var0.add("advancements", var1);
      }

      var0.add("looking_at", this.h.a());
      return var0;
   }

   @Override
   public EntitySubPredicate.a c() {
      return EntitySubPredicate.b.d;
   }

   static class a implements CriterionConditionPlayer.c {
      private final Object2BooleanMap<String> a;

      public a(Object2BooleanMap<String> var0) {
         this.a = var0;
      }

      @Override
      public JsonElement a() {
         JsonObject var0 = new JsonObject();
         this.a.forEach(var0::addProperty);
         return var0;
      }

      public boolean a(AdvancementProgress var0) {
         ObjectIterator var2 = this.a.object2BooleanEntrySet().iterator();

         while(var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> var2x = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry)var2.next();
            CriterionProgress var3 = var0.c((String)var2x.getKey());
            if (var3 == null || var3.a() != var2x.getBooleanValue()) {
               return false;
            }
         }

         return true;
      }
   }

   static class b implements CriterionConditionPlayer.c {
      private final boolean a;

      public b(boolean var0) {
         this.a = var0;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(this.a);
      }

      public boolean a(AdvancementProgress var0) {
         return var0.a() == this.a;
      }
   }

   interface c extends Predicate<AdvancementProgress> {
      JsonElement a();
   }

   public static class d {
      private CriterionConditionValue.IntegerRange a = CriterionConditionValue.IntegerRange.e;
      @Nullable
      private EnumGamemode b;
      private final Map<Statistic<?>, CriterionConditionValue.IntegerRange> c = Maps.newHashMap();
      private final Object2BooleanMap<MinecraftKey> d = new Object2BooleanOpenHashMap();
      private final Map<MinecraftKey, CriterionConditionPlayer.c> e = Maps.newHashMap();
      private CriterionConditionEntity f = CriterionConditionEntity.a;

      public static CriterionConditionPlayer.d a() {
         return new CriterionConditionPlayer.d();
      }

      public CriterionConditionPlayer.d a(CriterionConditionValue.IntegerRange var0) {
         this.a = var0;
         return this;
      }

      public CriterionConditionPlayer.d a(Statistic<?> var0, CriterionConditionValue.IntegerRange var1) {
         this.c.put(var0, var1);
         return this;
      }

      public CriterionConditionPlayer.d a(MinecraftKey var0, boolean var1) {
         this.d.put(var0, var1);
         return this;
      }

      public CriterionConditionPlayer.d a(EnumGamemode var0) {
         this.b = var0;
         return this;
      }

      public CriterionConditionPlayer.d a(CriterionConditionEntity var0) {
         this.f = var0;
         return this;
      }

      public CriterionConditionPlayer.d b(MinecraftKey var0, boolean var1) {
         this.e.put(var0, new CriterionConditionPlayer.b(var1));
         return this;
      }

      public CriterionConditionPlayer.d a(MinecraftKey var0, Map<String, Boolean> var1) {
         this.e.put(var0, new CriterionConditionPlayer.a(new Object2BooleanOpenHashMap(var1)));
         return this;
      }

      public CriterionConditionPlayer b() {
         return new CriterionConditionPlayer(this.a, this.b, this.c, this.d, this.e, this.f);
      }
   }
}
