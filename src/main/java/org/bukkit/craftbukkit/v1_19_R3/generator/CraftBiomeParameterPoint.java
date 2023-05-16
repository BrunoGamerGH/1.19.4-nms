package org.bukkit.craftbukkit.v1_19_R3.generator;

import net.minecraft.world.level.biome.Climate;
import org.bukkit.generator.BiomeParameterPoint;

public class CraftBiomeParameterPoint implements BiomeParameterPoint {
   private final double temperature;
   private final double humidity;
   private final double continentalness;
   private final double erosion;
   private final double depth;
   private final double weirdness;
   private final Climate.Sampler sampler;

   public static BiomeParameterPoint createBiomeParameterPoint(Climate.Sampler sampler, Climate.h targetPoint) {
      return new CraftBiomeParameterPoint(
         sampler,
         (double)Climate.a(targetPoint.b()),
         (double)Climate.a(targetPoint.c()),
         (double)Climate.a(targetPoint.d()),
         (double)Climate.a(targetPoint.e()),
         (double)Climate.a(targetPoint.f()),
         (double)Climate.a(targetPoint.g())
      );
   }

   private CraftBiomeParameterPoint(
      Climate.Sampler sampler, double temperature, double humidity, double continentalness, double erosion, double depth, double weirdness
   ) {
      this.sampler = sampler;
      this.temperature = temperature;
      this.humidity = humidity;
      this.continentalness = continentalness;
      this.erosion = erosion;
      this.depth = depth;
      this.weirdness = weirdness;
   }

   public double getTemperature() {
      return this.temperature;
   }

   public double getMaxTemperature() {
      return this.sampler.b().b();
   }

   public double getMinTemperature() {
      return this.sampler.b().a();
   }

   public double getHumidity() {
      return this.humidity;
   }

   public double getMaxHumidity() {
      return this.sampler.c().b();
   }

   public double getMinHumidity() {
      return this.sampler.c().a();
   }

   public double getContinentalness() {
      return this.continentalness;
   }

   public double getMaxContinentalness() {
      return this.sampler.d().b();
   }

   public double getMinContinentalness() {
      return this.sampler.d().a();
   }

   public double getErosion() {
      return this.erosion;
   }

   public double getMaxErosion() {
      return this.sampler.e().b();
   }

   public double getMinErosion() {
      return this.sampler.e().a();
   }

   public double getDepth() {
      return this.depth;
   }

   public double getMaxDepth() {
      return this.sampler.f().b();
   }

   public double getMinDepth() {
      return this.sampler.f().a();
   }

   public double getWeirdness() {
      return this.weirdness;
   }

   public double getMaxWeirdness() {
      return this.sampler.g().b();
   }

   public double getMinWeirdness() {
      return this.sampler.g().a();
   }
}
