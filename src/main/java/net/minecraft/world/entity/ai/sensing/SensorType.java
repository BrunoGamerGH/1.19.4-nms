package net.minecraft.world.entity.ai.sensing;

import java.util.function.Supplier;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.animal.camel.CamelAi;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.animal.goat.GoatAi;

public class SensorType<U extends Sensor<?>> {
   public static final SensorType<SensorDummy> a = a("dummy", SensorDummy::new);
   public static final SensorType<SensorNearestItems> b = a("nearest_items", SensorNearestItems::new);
   public static final SensorType<SensorNearestLivingEntities<EntityLiving>> c = a("nearest_living_entities", SensorNearestLivingEntities::new);
   public static final SensorType<SensorNearestPlayers> d = a("nearest_players", SensorNearestPlayers::new);
   public static final SensorType<SensorNearestBed> e = a("nearest_bed", SensorNearestBed::new);
   public static final SensorType<SensorHurtBy> f = a("hurt_by", SensorHurtBy::new);
   public static final SensorType<SensorVillagerHostiles> g = a("villager_hostiles", SensorVillagerHostiles::new);
   public static final SensorType<SensorVillagerBabies> h = a("villager_babies", SensorVillagerBabies::new);
   public static final SensorType<SensorSecondaryPlaces> i = a("secondary_pois", SensorSecondaryPlaces::new);
   public static final SensorType<SensorGolemLastSeen> j = a("golem_detected", SensorGolemLastSeen::new);
   public static final SensorType<SensorPiglinSpecific> k = a("piglin_specific_sensor", SensorPiglinSpecific::new);
   public static final SensorType<SensorPiglinBruteSpecific> l = a("piglin_brute_specific_sensor", SensorPiglinBruteSpecific::new);
   public static final SensorType<SensorHoglinSpecific> m = a("hoglin_specific_sensor", SensorHoglinSpecific::new);
   public static final SensorType<SensorAdult> n = a("nearest_adult", SensorAdult::new);
   public static final SensorType<AxolotlAttackablesSensor> o = a("axolotl_attackables", AxolotlAttackablesSensor::new);
   public static final SensorType<TemptingSensor> p = a("axolotl_temptations", () -> new TemptingSensor(AxolotlAi.a()));
   public static final SensorType<TemptingSensor> q = a("goat_temptations", () -> new TemptingSensor(GoatAi.a()));
   public static final SensorType<TemptingSensor> r = a("frog_temptations", () -> new TemptingSensor(FrogAi.a()));
   public static final SensorType<TemptingSensor> s = a("camel_temptations", () -> new TemptingSensor(CamelAi.b()));
   public static final SensorType<FrogAttackablesSensor> t = a("frog_attackables", FrogAttackablesSensor::new);
   public static final SensorType<IsInWaterSensor> u = a("is_in_water", IsInWaterSensor::new);
   public static final SensorType<WardenEntitySensor> v = a("warden_entity_sensor", WardenEntitySensor::new);
   private final Supplier<U> w;

   private SensorType(Supplier<U> var0) {
      this.w = var0;
   }

   public U a() {
      return this.w.get();
   }

   private static <U extends Sensor<?>> SensorType<U> a(String var0, Supplier<U> var1) {
      return IRegistry.a(BuiltInRegistries.C, new MinecraftKey(var0), new SensorType<>(var1));
   }
}
