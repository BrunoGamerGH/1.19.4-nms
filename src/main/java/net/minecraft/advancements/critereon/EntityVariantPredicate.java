package net.minecraft.advancements.critereon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3D;

public class EntityVariantPredicate<V> {
   private static final String a = "variant";
   final Codec<V> b;
   final Function<Entity, Optional<V>> c;
   final EntitySubPredicate.a d;

   public static <V> EntityVariantPredicate<V> a(IRegistry<V> var0, Function<Entity, Optional<V>> var1) {
      return new EntityVariantPredicate<>(var0.q(), var1);
   }

   public static <V> EntityVariantPredicate<V> a(Codec<V> var0, Function<Entity, Optional<V>> var1) {
      return new EntityVariantPredicate<>(var0, var1);
   }

   private EntityVariantPredicate(Codec<V> var0, Function<Entity, Optional<V>> var1) {
      this.b = var0;
      this.c = var1;
      this.d = var1x -> {
         JsonElement var2x = var1x.get("variant");
         if (var2x == null) {
            throw new JsonParseException("Missing variant field");
         } else {
            V var3 = (V)((Pair)SystemUtils.a(var0.decode(new Dynamic(JsonOps.INSTANCE, var2x)), JsonParseException::new)).getFirst();
            return this.a(var3);
         }
      };
   }

   public EntitySubPredicate.a a() {
      return this.d;
   }

   public EntitySubPredicate a(final V var0) {
      return new EntitySubPredicate() {
         @Override
         public boolean a(Entity var0x, WorldServer var1, @Nullable Vec3D var2) {
            return EntityVariantPredicate.this.c.apply(var0).filter(var1x -> var1x.equals(var0)).isPresent();
         }

         @Override
         public JsonObject a() {
            JsonObject var0 = new JsonObject();
            var0.add(
               "variant",
               SystemUtils.a(
                  EntityVariantPredicate.this.b.encodeStart(JsonOps.INSTANCE, var0),
                  var1x -> new JsonParseException("Can't serialize variant " + var0 + ", message " + var1x)
               )
            );
            return var0;
         }

         @Override
         public EntitySubPredicate.a c() {
            return EntityVariantPredicate.this.d;
         }
      };
   }
}
