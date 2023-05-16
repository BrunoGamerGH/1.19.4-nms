package net.minecraft.advancements.critereon;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.ChatDeserializer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityMushroomCow;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.animal.horse.HorseColor;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.phys.Vec3D;

public interface EntitySubPredicate {
   EntitySubPredicate a = new EntitySubPredicate() {
      @Override
      public boolean a(Entity var0, WorldServer var1, @Nullable Vec3D var2) {
         return true;
      }

      @Override
      public JsonObject a() {
         return new JsonObject();
      }

      @Override
      public EntitySubPredicate.a c() {
         return EntitySubPredicate.b.a;
      }
   };

   static EntitySubPredicate a(@Nullable JsonElement var0) {
      if (var0 != null && !var0.isJsonNull()) {
         JsonObject var1 = ChatDeserializer.m(var0, "type_specific");
         String var2 = ChatDeserializer.a(var1, "type", null);
         if (var2 == null) {
            return a;
         } else {
            EntitySubPredicate.a var3 = (EntitySubPredicate.a)EntitySubPredicate.b.s.get(var2);
            if (var3 == null) {
               throw new JsonSyntaxException("Unknown sub-predicate type: " + var2);
            } else {
               return var3.deserialize(var1);
            }
         }
      } else {
         return a;
      }
   }

   boolean a(Entity var1, WorldServer var2, @Nullable Vec3D var3);

   JsonObject a();

   default JsonElement b() {
      if (this.c() == EntitySubPredicate.b.a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var0 = this.a();
         String var1 = (String)EntitySubPredicate.b.s.inverse().get(this.c());
         var0.addProperty("type", var1);
         return var0;
      }
   }

   EntitySubPredicate.a c();

   static EntitySubPredicate a(CatVariant var0) {
      return EntitySubPredicate.b.f.a(var0);
   }

   static EntitySubPredicate a(FrogVariant var0) {
      return EntitySubPredicate.b.g.a(var0);
   }

   public interface a {
      EntitySubPredicate deserialize(JsonObject var1);
   }

   public static final class b {
      public static final EntitySubPredicate.a a = var0 -> EntitySubPredicate.a;
      public static final EntitySubPredicate.a b = LighthingBoltPredicate::a;
      public static final EntitySubPredicate.a c = CriterionConditionInOpenWater::a;
      public static final EntitySubPredicate.a d = CriterionConditionPlayer::a;
      public static final EntitySubPredicate.a e = SlimePredicate::a;
      public static final EntityVariantPredicate<CatVariant> f = EntityVariantPredicate.a(
         BuiltInRegistries.ai, var0 -> var0 instanceof EntityCat var1 ? Optional.of(var1.fZ()) : Optional.empty()
      );
      public static final EntityVariantPredicate<FrogVariant> g = EntityVariantPredicate.a(
         BuiltInRegistries.aj, var0 -> var0 instanceof Frog var1 ? Optional.of(var1.w()) : Optional.empty()
      );
      public static final EntityVariantPredicate<Axolotl.Variant> h = EntityVariantPredicate.a(
         Axolotl.Variant.f, var0 -> var0 instanceof Axolotl var1 ? Optional.of(var1.fS()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityBoat.EnumBoatType> i = EntityVariantPredicate.a(
         EntityBoat.EnumBoatType.j, var0 -> var0 instanceof EntityBoat var1 ? Optional.of(var1.t()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityFox.Type> j = EntityVariantPredicate.a(
         EntityFox.Type.c, var0 -> var0 instanceof EntityFox var1 ? Optional.of(var1.r()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityMushroomCow.Type> k = EntityVariantPredicate.a(
         EntityMushroomCow.Type.c, var0 -> var0 instanceof EntityMushroomCow var1 ? Optional.of(var1.r()) : Optional.empty()
      );
      public static final EntityVariantPredicate<Holder<PaintingVariant>> l = EntityVariantPredicate.a(
         BuiltInRegistries.m.r(), var0 -> var0 instanceof EntityPainting var1 ? Optional.of(var1.i()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityRabbit.Variant> m = EntityVariantPredicate.a(
         EntityRabbit.Variant.h, var0 -> var0 instanceof EntityRabbit var1 ? Optional.of(var1.fS()) : Optional.empty()
      );
      public static final EntityVariantPredicate<HorseColor> n = EntityVariantPredicate.a(
         HorseColor.h, var0 -> var0 instanceof EntityHorse var1 ? Optional.of(var1.r()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityLlama.Variant> o = EntityVariantPredicate.a(
         EntityLlama.Variant.e, var0 -> var0 instanceof EntityLlama var1 ? Optional.of(var1.ge()) : Optional.empty()
      );
      public static final EntityVariantPredicate<VillagerType> p = EntityVariantPredicate.a(
         BuiltInRegistries.y.q(), var0 -> var0 instanceof VillagerDataHolder var1 ? Optional.of(var1.a()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityParrot.Variant> q = EntityVariantPredicate.a(
         EntityParrot.Variant.f, var0 -> var0 instanceof EntityParrot var1 ? Optional.of(var1.ga()) : Optional.empty()
      );
      public static final EntityVariantPredicate<EntityTropicalFish.Variant> r = EntityVariantPredicate.a(
         EntityTropicalFish.Variant.m, var0 -> var0 instanceof EntityTropicalFish var1 ? Optional.of(var1.gd()) : Optional.empty()
      );
      public static final BiMap<String, EntitySubPredicate.a> s = ImmutableBiMap.builder()
         .put("any", a)
         .put("lightning", b)
         .put("fishing_hook", c)
         .put("player", d)
         .put("slime", e)
         .put("cat", f.a())
         .put("frog", g.a())
         .put("axolotl", h.a())
         .put("boat", i.a())
         .put("fox", j.a())
         .put("mooshroom", k.a())
         .put("painting", l.a())
         .put("rabbit", m.a())
         .put("horse", n.a())
         .put("llama", o.a())
         .put("villager", p.a())
         .put("parrot", q.a())
         .put("tropical_fish", r.a())
         .buildOrThrow();
   }
}
