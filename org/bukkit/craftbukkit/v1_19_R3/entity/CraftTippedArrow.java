package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectList;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CraftTippedArrow extends CraftArrow implements Arrow {
   public CraftTippedArrow(CraftServer server, EntityTippedArrow entity) {
      super(server, entity);
   }

   public EntityTippedArrow getHandle() {
      return (EntityTippedArrow)this.entity;
   }

   @Override
   public String toString() {
      return "CraftTippedArrow";
   }

   @Override
   public EntityType getType() {
      return EntityType.ARROW;
   }

   public boolean addCustomEffect(PotionEffect effect, boolean override) {
      int effectId = effect.getType().getId();
      MobEffect existing = null;

      for(MobEffect mobEffect : this.getHandle().k) {
         if (MobEffectList.a(mobEffect.c()) == effectId) {
            existing = mobEffect;
         }
      }

      if (existing != null) {
         if (!override) {
            return false;
         }

         this.getHandle().k.remove(existing);
      }

      this.getHandle().a(CraftPotionUtil.fromBukkit(effect));
      this.getHandle().refreshEffects();
      return true;
   }

   public void clearCustomEffects() {
      this.getHandle().k.clear();
      this.getHandle().refreshEffects();
   }

   public List<PotionEffect> getCustomEffects() {
      Builder<PotionEffect> builder = ImmutableList.builder();

      for(MobEffect effect : this.getHandle().k) {
         builder.add(CraftPotionUtil.toBukkit(effect));
      }

      return builder.build();
   }

   public boolean hasCustomEffect(PotionEffectType type) {
      for(MobEffect effect : this.getHandle().k) {
         if (CraftPotionUtil.equals(effect.c(), type)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasCustomEffects() {
      return !this.getHandle().k.isEmpty();
   }

   public boolean removeCustomEffect(PotionEffectType effect) {
      int effectId = effect.getId();
      MobEffect existing = null;

      for(MobEffect mobEffect : this.getHandle().k) {
         if (MobEffectList.a(mobEffect.c()) == effectId) {
            existing = mobEffect;
         }
      }

      if (existing == null) {
         return false;
      } else {
         this.getHandle().k.remove(existing);
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

   public void setColor(Color color) {
      int colorRGB = color == null ? -1 : color.asRGB();
      this.getHandle().d(colorRGB);
   }

   public Color getColor() {
      return this.getHandle().y() <= -1 ? null : Color.fromRGB(this.getHandle().y());
   }
}
