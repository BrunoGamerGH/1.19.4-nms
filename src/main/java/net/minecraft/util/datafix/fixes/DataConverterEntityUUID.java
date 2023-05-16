package net.minecraft.util.datafix.fixes;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;

public class DataConverterEntityUUID extends DataConverterUUIDBase {
   private static final Logger b = LogUtils.getLogger();
   private static final Set<String> c = Sets.newHashSet();
   private static final Set<String> d = Sets.newHashSet();
   private static final Set<String> e = Sets.newHashSet();
   private static final Set<String> f = Sets.newHashSet();
   private static final Set<String> g = Sets.newHashSet();
   private static final Set<String> h = Sets.newHashSet();

   public DataConverterEntityUUID(Schema var0) {
      super(var0, DataConverterTypes.q);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.a), var0 -> {
         var0 = var0.update(DSL.remainderFinder(), DataConverterEntityUUID::c);

         for(String var2 : c) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::l);
         }

         for(String var2 : d) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::l);
         }

         for(String var2 : e) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::m);
         }

         for(String var2 : f) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::n);
         }

         for(String var2 : g) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::b);
         }

         for(String var2 : h) {
            var0 = this.a(var0, var2, DataConverterEntityUUID::o);
         }

         var0 = this.a(var0, "minecraft:bee", DataConverterEntityUUID::k);
         var0 = this.a(var0, "minecraft:zombified_piglin", DataConverterEntityUUID::k);
         var0 = this.a(var0, "minecraft:fox", DataConverterEntityUUID::j);
         var0 = this.a(var0, "minecraft:item", DataConverterEntityUUID::i);
         var0 = this.a(var0, "minecraft:shulker_bullet", DataConverterEntityUUID::h);
         var0 = this.a(var0, "minecraft:area_effect_cloud", DataConverterEntityUUID::g);
         var0 = this.a(var0, "minecraft:zombie_villager", DataConverterEntityUUID::f);
         var0 = this.a(var0, "minecraft:evoker_fangs", DataConverterEntityUUID::e);
         return this.a(var0, "minecraft:piglin", DataConverterEntityUUID::d);
      });
   }

   private static Dynamic<?> d(Dynamic<?> var0) {
      return var0.update(
         "Brain",
         var0x -> var0x.update("memories", var0xx -> var0xx.update("minecraft:angry_at", var0xxx -> (Dynamic)a(var0xxx, "value", "value").orElseGet(() -> {
                     b.warn("angry_at has no value.");
                     return var0xxx;
                  })))
      );
   }

   private static Dynamic<?> e(Dynamic<?> var0) {
      return (Dynamic<?>)c(var0, "OwnerUUID", "Owner").orElse(var0);
   }

   private static Dynamic<?> f(Dynamic<?> var0) {
      return (Dynamic<?>)c(var0, "ConversionPlayer", "ConversionPlayer").orElse(var0);
   }

   private static Dynamic<?> g(Dynamic<?> var0) {
      return (Dynamic<?>)c(var0, "OwnerUUID", "Owner").orElse(var0);
   }

   private static Dynamic<?> h(Dynamic<?> var0) {
      var0 = (Dynamic)b(var0, "Owner", "Owner").orElse(var0);
      return (Dynamic<?>)b(var0, "Target", "Target").orElse(var0);
   }

   private static Dynamic<?> i(Dynamic<?> var0) {
      var0 = (Dynamic)b(var0, "Owner", "Owner").orElse(var0);
      return (Dynamic<?>)b(var0, "Thrower", "Thrower").orElse(var0);
   }

   private static Dynamic<?> j(Dynamic<?> var0) {
      Optional<Dynamic<?>> var1 = var0.get("TrustedUUIDs")
         .result()
         .map(var1x -> var0.createList(var1x.asStream().map(var0xx -> (Dynamic)a(var0xx).orElseGet(() -> {
                  b.warn("Trusted contained invalid data.");
                  return var0xx;
               }))));
      return (Dynamic<?>)DataFixUtils.orElse(var1.map(var1x -> var0.remove("TrustedUUIDs").set("Trusted", var1x)), var0);
   }

   private static Dynamic<?> k(Dynamic<?> var0) {
      return (Dynamic<?>)a(var0, "HurtBy", "HurtBy").orElse(var0);
   }

   private static Dynamic<?> l(Dynamic<?> var0) {
      Dynamic<?> var1 = m(var0);
      return (Dynamic<?>)a(var1, "OwnerUUID", "Owner").orElse(var1);
   }

   private static Dynamic<?> m(Dynamic<?> var0) {
      Dynamic<?> var1 = n(var0);
      return (Dynamic<?>)c(var1, "LoveCause", "LoveCause").orElse(var1);
   }

   private static Dynamic<?> n(Dynamic<?> var0) {
      return b(var0).update("Leash", var0x -> (Dynamic)c(var0x, "UUID", "UUID").orElse(var0x));
   }

   public static Dynamic<?> b(Dynamic<?> var0) {
      return var0.update(
         "Attributes",
         var1 -> var0.createList(
               var1.asStream()
                  .map(
                     var0xx -> var0xx.update(
                           "Modifiers", var1x -> var0xx.createList(var1x.asStream().map(var0xxxx -> (Dynamic)c(var0xxxx, "UUID", "UUID").orElse(var0xxxx)))
                        )
                  )
            )
      );
   }

   private static Dynamic<?> o(Dynamic<?> var0) {
      return (Dynamic<?>)DataFixUtils.orElse(var0.get("OwnerUUID").result().map(var1 -> var0.remove("OwnerUUID").set("Owner", var1)), var0);
   }

   public static Dynamic<?> c(Dynamic<?> var0) {
      return (Dynamic<?>)c(var0, "UUID", "UUID").orElse(var0);
   }

   static {
      c.add("minecraft:donkey");
      c.add("minecraft:horse");
      c.add("minecraft:llama");
      c.add("minecraft:mule");
      c.add("minecraft:skeleton_horse");
      c.add("minecraft:trader_llama");
      c.add("minecraft:zombie_horse");
      d.add("minecraft:cat");
      d.add("minecraft:parrot");
      d.add("minecraft:wolf");
      e.add("minecraft:bee");
      e.add("minecraft:chicken");
      e.add("minecraft:cow");
      e.add("minecraft:fox");
      e.add("minecraft:mooshroom");
      e.add("minecraft:ocelot");
      e.add("minecraft:panda");
      e.add("minecraft:pig");
      e.add("minecraft:polar_bear");
      e.add("minecraft:rabbit");
      e.add("minecraft:sheep");
      e.add("minecraft:turtle");
      e.add("minecraft:hoglin");
      f.add("minecraft:bat");
      f.add("minecraft:blaze");
      f.add("minecraft:cave_spider");
      f.add("minecraft:cod");
      f.add("minecraft:creeper");
      f.add("minecraft:dolphin");
      f.add("minecraft:drowned");
      f.add("minecraft:elder_guardian");
      f.add("minecraft:ender_dragon");
      f.add("minecraft:enderman");
      f.add("minecraft:endermite");
      f.add("minecraft:evoker");
      f.add("minecraft:ghast");
      f.add("minecraft:giant");
      f.add("minecraft:guardian");
      f.add("minecraft:husk");
      f.add("minecraft:illusioner");
      f.add("minecraft:magma_cube");
      f.add("minecraft:pufferfish");
      f.add("minecraft:zombified_piglin");
      f.add("minecraft:salmon");
      f.add("minecraft:shulker");
      f.add("minecraft:silverfish");
      f.add("minecraft:skeleton");
      f.add("minecraft:slime");
      f.add("minecraft:snow_golem");
      f.add("minecraft:spider");
      f.add("minecraft:squid");
      f.add("minecraft:stray");
      f.add("minecraft:tropical_fish");
      f.add("minecraft:vex");
      f.add("minecraft:villager");
      f.add("minecraft:iron_golem");
      f.add("minecraft:vindicator");
      f.add("minecraft:pillager");
      f.add("minecraft:wandering_trader");
      f.add("minecraft:witch");
      f.add("minecraft:wither");
      f.add("minecraft:wither_skeleton");
      f.add("minecraft:zombie");
      f.add("minecraft:zombie_villager");
      f.add("minecraft:phantom");
      f.add("minecraft:ravager");
      f.add("minecraft:piglin");
      g.add("minecraft:armor_stand");
      h.add("minecraft:arrow");
      h.add("minecraft:dragon_fireball");
      h.add("minecraft:firework_rocket");
      h.add("minecraft:fireball");
      h.add("minecraft:llama_spit");
      h.add("minecraft:small_fireball");
      h.add("minecraft:snowball");
      h.add("minecraft:spectral_arrow");
      h.add("minecraft:egg");
      h.add("minecraft:ender_pearl");
      h.add("minecraft:experience_bottle");
      h.add("minecraft:potion");
      h.add("minecraft:trident");
      h.add("minecraft:wither_skull");
   }
}
