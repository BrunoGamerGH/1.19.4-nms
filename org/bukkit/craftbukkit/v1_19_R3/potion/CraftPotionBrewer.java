package org.bukkit.craftbukkit.v1_19_R3.potion;

import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.PotionRegistry;
import org.bukkit.potion.PotionBrewer;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class CraftPotionBrewer implements PotionBrewer {
   private static final Map<PotionType, Collection<PotionEffect>> cache = Maps.newHashMap();

   public Collection<PotionEffect> getEffects(PotionType damage, boolean upgraded, boolean extended) {
      if (cache.containsKey(damage)) {
         return cache.get(damage);
      } else {
         List<MobEffect> mcEffects = PotionRegistry.a(CraftPotionUtil.fromBukkit(new PotionData(damage, extended, upgraded))).a();
         Builder<PotionEffect> builder = new Builder();

         for(MobEffect effect : mcEffects) {
            builder.add(CraftPotionUtil.toBukkit(effect));
         }

         cache.put(damage, builder.build());
         return cache.get(damage);
      }
   }

   public Collection<PotionEffect> getEffectsFromDamage(int damage) {
      return new ArrayList();
   }

   public PotionEffect createEffect(PotionEffectType potion, int duration, int amplifier) {
      return new PotionEffect(potion, potion.isInstant() ? 1 : (int)((double)duration * potion.getDurationModifier()), amplifier);
   }
}
