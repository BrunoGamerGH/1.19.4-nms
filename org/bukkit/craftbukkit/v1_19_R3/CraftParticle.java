package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.ParticleParamBlock;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SculkChargeParticleOptions;
import net.minecraft.core.particles.ShriekParticleOption;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Vibration;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Particle.DustTransition;
import org.bukkit.Vibration.Destination.BlockDestination;
import org.bukkit.Vibration.Destination.EntityDestination;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.joml.Vector3f;

public enum CraftParticle {
   EXPLOSION_NORMAL("poof"),
   EXPLOSION_LARGE("explosion"),
   EXPLOSION_HUGE("explosion_emitter"),
   FIREWORKS_SPARK("firework"),
   WATER_BUBBLE("bubble"),
   WATER_SPLASH("splash"),
   WATER_WAKE("fishing"),
   SUSPENDED("underwater"),
   SUSPENDED_DEPTH("underwater"),
   CRIT("crit"),
   CRIT_MAGIC("enchanted_hit"),
   SMOKE_NORMAL("smoke"),
   SMOKE_LARGE("large_smoke"),
   SPELL("effect"),
   SPELL_INSTANT("instant_effect"),
   SPELL_MOB("entity_effect"),
   SPELL_MOB_AMBIENT("ambient_entity_effect"),
   SPELL_WITCH("witch"),
   DRIP_WATER("dripping_water"),
   DRIP_LAVA("dripping_lava"),
   VILLAGER_ANGRY("angry_villager"),
   VILLAGER_HAPPY("happy_villager"),
   TOWN_AURA("mycelium"),
   NOTE("note"),
   PORTAL("portal"),
   ENCHANTMENT_TABLE("enchant"),
   FLAME("flame"),
   LAVA("lava"),
   CLOUD("cloud"),
   REDSTONE("dust"),
   SNOWBALL("item_snowball"),
   SNOW_SHOVEL("item_snowball"),
   SLIME("item_slime"),
   HEART("heart"),
   ITEM_CRACK("item"),
   BLOCK_CRACK("block"),
   BLOCK_DUST("block"),
   WATER_DROP("rain"),
   MOB_APPEARANCE("elder_guardian"),
   DRAGON_BREATH("dragon_breath"),
   END_ROD("end_rod"),
   DAMAGE_INDICATOR("damage_indicator"),
   SWEEP_ATTACK("sweep_attack"),
   FALLING_DUST("falling_dust"),
   TOTEM("totem_of_undying"),
   SPIT("spit"),
   SQUID_INK("squid_ink"),
   BUBBLE_POP("bubble_pop"),
   CURRENT_DOWN("current_down"),
   BUBBLE_COLUMN_UP("bubble_column_up"),
   NAUTILUS("nautilus"),
   DOLPHIN("dolphin"),
   SNEEZE("sneeze"),
   CAMPFIRE_COSY_SMOKE("campfire_cosy_smoke"),
   CAMPFIRE_SIGNAL_SMOKE("campfire_signal_smoke"),
   COMPOSTER("composter"),
   FLASH("flash"),
   FALLING_LAVA("falling_lava"),
   LANDING_LAVA("landing_lava"),
   FALLING_WATER("falling_water"),
   DRIPPING_HONEY("dripping_honey"),
   FALLING_HONEY("falling_honey"),
   LANDING_HONEY("landing_honey"),
   FALLING_NECTAR("falling_nectar"),
   SOUL_FIRE_FLAME("soul_fire_flame"),
   ASH("ash"),
   CRIMSON_SPORE("crimson_spore"),
   WARPED_SPORE("warped_spore"),
   SOUL("soul"),
   DRIPPING_OBSIDIAN_TEAR("dripping_obsidian_tear"),
   FALLING_OBSIDIAN_TEAR("falling_obsidian_tear"),
   LANDING_OBSIDIAN_TEAR("landing_obsidian_tear"),
   REVERSE_PORTAL("reverse_portal"),
   WHITE_ASH("white_ash"),
   DUST_COLOR_TRANSITION("dust_color_transition"),
   VIBRATION("vibration"),
   FALLING_SPORE_BLOSSOM("falling_spore_blossom"),
   SPORE_BLOSSOM_AIR("spore_blossom_air"),
   SMALL_FLAME("small_flame"),
   SNOWFLAKE("snowflake"),
   DRIPPING_DRIPSTONE_LAVA("dripping_dripstone_lava"),
   FALLING_DRIPSTONE_LAVA("falling_dripstone_lava"),
   DRIPPING_DRIPSTONE_WATER("dripping_dripstone_water"),
   FALLING_DRIPSTONE_WATER("falling_dripstone_water"),
   GLOW_SQUID_INK("glow_squid_ink"),
   GLOW("glow"),
   WAX_ON("wax_on"),
   WAX_OFF("wax_off"),
   ELECTRIC_SPARK("electric_spark"),
   SCRAPE("scrape"),
   BLOCK_MARKER("block_marker"),
   SONIC_BOOM("sonic_boom"),
   SCULK_SOUL("sculk_soul"),
   SCULK_CHARGE("sculk_charge"),
   SCULK_CHARGE_POP("sculk_charge_pop"),
   SHRIEK("shriek"),
   DRIPPING_CHERRY_LEAVES("dripping_cherry_leaves"),
   FALLING_CHERRY_LEAVES("falling_cherry_leaves"),
   LANDING_CHERRY_LEAVES("landing_cherry_leaves"),
   LEGACY_BLOCK_CRACK("block"),
   LEGACY_BLOCK_DUST("block"),
   LEGACY_FALLING_DUST("falling_dust");

   private final MinecraftKey minecraftKey;
   private final Particle bukkit;
   private static final BiMap<Particle, MinecraftKey> particles = HashBiMap.create();
   private static final Map<Particle, Particle> aliases = new HashMap();

   static {
      CraftParticle[] var3;
      for(CraftParticle particle : var3 = values()) {
         if (particles.containsValue(particle.minecraftKey)) {
            aliases.put(particle.bukkit, (Particle)particles.inverse().get(particle.minecraftKey));
         } else {
            particles.put(particle.bukkit, particle.minecraftKey);
         }
      }
   }

   private CraftParticle(String minecraftKey) {
      this.minecraftKey = new MinecraftKey(minecraftKey);
      this.bukkit = Particle.valueOf(this.name());
      Preconditions.checkState(this.bukkit != null, "Bukkit particle %s does not exist", this.name());
   }

   public static ParticleParam toNMS(Particle bukkit) {
      return toNMS(bukkit, null);
   }

   public static <T> ParticleParam toNMS(Particle particle, T obj) {
      Particle canonical = particle;
      if (aliases.containsKey(particle)) {
         canonical = (Particle)aliases.get(particle);
      }

      net.minecraft.core.particles.Particle nms = BuiltInRegistries.k.a((MinecraftKey)particles.get(canonical));
      Preconditions.checkArgument(nms != null, "No NMS particle %s", particle);
      if (particle.getDataType().equals(Void.class)) {
         return (ParticleType)nms;
      } else {
         Preconditions.checkArgument(obj != null, "Particle %s requires data, null provided", particle);
         if (particle.getDataType().equals(ItemStack.class)) {
            ItemStack itemStack = (ItemStack)obj;
            return new ParticleParamItem(nms, CraftItemStack.asNMSCopy(itemStack));
         } else if (particle.getDataType() == MaterialData.class) {
            MaterialData data = (MaterialData)obj;
            return new ParticleParamBlock(nms, CraftMagicNumbers.getBlock(data));
         } else if (particle.getDataType() == BlockData.class) {
            BlockData data = (BlockData)obj;
            return new ParticleParamBlock(nms, ((CraftBlockData)data).getState());
         } else if (particle.getDataType() == DustOptions.class) {
            DustOptions data = (DustOptions)obj;
            Color color = data.getColor();
            return new ParticleParamRedstone(
               new Vector3f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F), data.getSize()
            );
         } else if (particle.getDataType() == DustTransition.class) {
            DustTransition data = (DustTransition)obj;
            Color from = data.getColor();
            Color to = data.getToColor();
            return new DustColorTransitionOptions(
               new Vector3f((float)from.getRed() / 255.0F, (float)from.getGreen() / 255.0F, (float)from.getBlue() / 255.0F),
               new Vector3f((float)to.getRed() / 255.0F, (float)to.getGreen() / 255.0F, (float)to.getBlue() / 255.0F),
               data.getSize()
            );
         } else if (particle.getDataType() == Vibration.class) {
            Vibration vibration = (Vibration)obj;
            Location origin = vibration.getOrigin();
            PositionSource source;
            if (vibration.getDestination() instanceof BlockDestination) {
               Location destination = ((BlockDestination)vibration.getDestination()).getLocation();
               source = new BlockPositionSource(new BlockPosition(destination.getBlockX(), destination.getBlockY(), destination.getBlockZ()));
            } else {
               if (!(vibration.getDestination() instanceof EntityDestination)) {
                  throw new IllegalArgumentException("Unknown vibration destination " + vibration.getDestination());
               }

               Entity destination = ((CraftEntity)((EntityDestination)vibration.getDestination()).getEntity()).getHandle();
               source = new EntityPositionSource(destination, destination.cE());
            }

            return new VibrationParticleOption(source, vibration.getArrivalTime());
         } else if (particle.getDataType() == Float.class) {
            return new SculkChargeParticleOptions((Float)obj);
         } else if (particle.getDataType() == Integer.class) {
            return new ShriekParticleOption((Integer)obj);
         } else {
            throw new IllegalArgumentException(particle.getDataType().toString());
         }
      }
   }

   public static Particle toBukkit(ParticleParam nms) {
      return toBukkit(nms.b());
   }

   public static Particle toBukkit(net.minecraft.core.particles.Particle nms) {
      return (Particle)particles.inverse().get(BuiltInRegistries.k.b(nms));
   }
}
