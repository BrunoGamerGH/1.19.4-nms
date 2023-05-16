package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityLiving;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_19_R3.CraftParticle;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public class CraftAreaEffectCloud extends CraftEntity implements AreaEffectCloud {
   public CraftAreaEffectCloud(CraftServer server, EntityAreaEffectCloud entity) {
      super(server, entity);
   }

   public EntityAreaEffectCloud getHandle() {
      return (EntityAreaEffectCloud)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftAreaEffectCloud";
   }

   public EntityType getType() {
      return EntityType.AREA_EFFECT_CLOUD;
   }

   public int getDuration() {
      return this.getHandle().m();
   }

   public void setDuration(int duration) {
      this.getHandle().b(duration);
   }

   public int getWaitTime() {
      return this.getHandle().q;
   }

   public void setWaitTime(int waitTime) {
      this.getHandle().d(waitTime);
   }

   public int getReapplicationDelay() {
      return this.getHandle().r;
   }

   public void setReapplicationDelay(int delay) {
      this.getHandle().r = delay;
   }

   public int getDurationOnUse() {
      return this.getHandle().t;
   }

   public void setDurationOnUse(int duration) {
      this.getHandle().t = duration;
   }

   public float getRadius() {
      return this.getHandle().h();
   }

   public void setRadius(float radius) {
      this.getHandle().a(radius);
   }

   public float getRadiusOnUse() {
      return this.getHandle().u;
   }

   public void setRadiusOnUse(float radius) {
      this.getHandle().b(radius);
   }

   public float getRadiusPerTick() {
      return this.getHandle().aC;
   }

   public void setRadiusPerTick(float radius) {
      this.getHandle().c(radius);
   }

   public Particle getParticle() {
      return CraftParticle.toBukkit(this.getHandle().j());
   }

   public void setParticle(Particle particle) {
      this.setParticle(particle, null);
   }

   public <T> void setParticle(Particle particle, T data) {
      this.getHandle().a(CraftParticle.toNMS(particle, data));
   }

   public Color getColor() {
      return Color.fromRGB(this.getHandle().i());
   }

   public void setColor(Color color) {
      this.getHandle().a(color.asRGB());
   }

   public boolean addCustomEffect(PotionEffect effect, boolean override) {
      int effectId = effect.getType().getId();
      MobEffect existing = null;

      for(MobEffect mobEffect : this.getHandle().n) {
         if (MobEffectList.a(mobEffect.c()) == effectId) {
            existing = mobEffect;
         }
      }

      if (existing != null) {
         if (!override) {
            return false;
         }

         this.getHandle().n.remove(existing);
      }

      this.getHandle().a(CraftPotionUtil.fromBukkit(effect));
      this.getHandle().refreshEffects();
      return true;
   }

   public void clearCustomEffects() {
      this.getHandle().n.clear();
      this.getHandle().refreshEffects();
   }

   public List<PotionEffect> getCustomEffects() {
      Builder<PotionEffect> builder = ImmutableList.builder();

      for(MobEffect effect : this.getHandle().n) {
         builder.add(CraftPotionUtil.toBukkit(effect));
      }

      return builder.build();
   }

   public boolean hasCustomEffect(PotionEffectType type) {
      for(MobEffect effect : this.getHandle().n) {
         if (CraftPotionUtil.equals(effect.c(), type)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasCustomEffects() {
      return !this.getHandle().n.isEmpty();
   }

   public boolean removeCustomEffect(PotionEffectType effect) {
      int effectId = effect.getId();
      MobEffect existing = null;

      for(MobEffect mobEffect : this.getHandle().n) {
         if (MobEffectList.a(mobEffect.c()) == effectId) {
            existing = mobEffect;
         }
      }

      if (existing == null) {
         return false;
      } else {
         this.getHandle().n.remove(existing);
         this.getHandle().refreshEffects();
         return true;
      }
   }

   public void setBasePotionData(PotionData data) {
      Validate.notNull(data, "PotionData cannot be null");
      this.getHandle().setPotionType(CraftPotionUtil.fromBukkit(data));
   }

   public PotionData getBasePotionData() {
      return CraftPotionUtil.toBukkit(this.getHandle().getPotionType());
   }

   public ProjectileSource getSource() {
      EntityLiving source = this.getHandle().s();
      return source == null ? null : (LivingEntity)source.getBukkitEntity();
   }

   public void setSource(ProjectileSource shooter) {
      if (shooter instanceof CraftLivingEntity) {
         this.getHandle().a(((CraftLivingEntity)shooter).getHandle());
      } else {
         this.getHandle().a(null);
      }
   }
}
