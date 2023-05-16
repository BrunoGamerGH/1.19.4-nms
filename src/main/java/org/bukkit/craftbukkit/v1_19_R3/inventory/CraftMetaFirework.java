package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.FireworkMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaFirework extends CraftMetaItem implements FireworkMeta {
   static final CraftMetaItem.ItemMetaKey FIREWORKS = new CraftMetaItem.ItemMetaKey("Fireworks");
   static final CraftMetaItem.ItemMetaKey FLIGHT = new CraftMetaItem.ItemMetaKey("Flight", "power");
   static final CraftMetaItem.ItemMetaKey EXPLOSIONS = new CraftMetaItem.ItemMetaKey("Explosions", "firework-effects");
   static final CraftMetaItem.ItemMetaKey EXPLOSION_COLORS = new CraftMetaItem.ItemMetaKey("Colors");
   static final CraftMetaItem.ItemMetaKey EXPLOSION_TYPE = new CraftMetaItem.ItemMetaKey("Type");
   static final CraftMetaItem.ItemMetaKey EXPLOSION_TRAIL = new CraftMetaItem.ItemMetaKey("Trail");
   static final CraftMetaItem.ItemMetaKey EXPLOSION_FLICKER = new CraftMetaItem.ItemMetaKey("Flicker");
   static final CraftMetaItem.ItemMetaKey EXPLOSION_FADE = new CraftMetaItem.ItemMetaKey("FadeColors");
   private List<FireworkEffect> effects;
   private Integer power;

   CraftMetaFirework(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaFirework) {
         CraftMetaFirework that = (CraftMetaFirework)meta;
         this.power = that.power;
         if (that.hasEffects()) {
            this.effects = new ArrayList(that.effects);
         }
      }
   }

   CraftMetaFirework(NBTTagCompound tag) {
      super(tag);
      if (tag.e(FIREWORKS.NBT)) {
         NBTTagCompound fireworks = tag.p(FIREWORKS.NBT);
         this.power = Integer.valueOf(fireworks.f(FLIGHT.NBT));
         if (fireworks.e(EXPLOSIONS.NBT)) {
            NBTTagList fireworkEffects = fireworks.c(EXPLOSIONS.NBT, 10);
            List<FireworkEffect> effects = this.effects = new ArrayList(fireworkEffects.size());

            for(int i = 0; i < fireworkEffects.size(); ++i) {
               try {
                  effects.add(getEffect((NBTTagCompound)fireworkEffects.k(i)));
               } catch (IllegalArgumentException var7) {
               }
            }
         }
      }
   }

   static FireworkEffect getEffect(NBTTagCompound explosion) {
      org.bukkit.FireworkEffect.Builder effect = FireworkEffect.builder()
         .flicker(explosion.q(EXPLOSION_FLICKER.NBT))
         .trail(explosion.q(EXPLOSION_TRAIL.NBT))
         .with(getEffectType(255 & explosion.f(EXPLOSION_TYPE.NBT)));
      int[] colors = explosion.n(EXPLOSION_COLORS.NBT);
      if (colors.length == 0) {
         effect.withColor(Color.WHITE);
      }

      for(int color : colors) {
         effect.withColor(Color.fromRGB(color));
      }

      int[] var10;
      for(int color : var10 = explosion.n(EXPLOSION_FADE.NBT)) {
         effect.withFade(Color.fromRGB(color));
      }

      return effect.build();
   }

   static NBTTagCompound getExplosion(FireworkEffect effect) {
      NBTTagCompound explosion = new NBTTagCompound();
      if (effect.hasFlicker()) {
         explosion.a(EXPLOSION_FLICKER.NBT, true);
      }

      if (effect.hasTrail()) {
         explosion.a(EXPLOSION_TRAIL.NBT, true);
      }

      addColors(explosion, EXPLOSION_COLORS, effect.getColors());
      addColors(explosion, EXPLOSION_FADE, effect.getFadeColors());
      explosion.a(EXPLOSION_TYPE.NBT, (byte)getNBT(effect.getType()));
      return explosion;
   }

   static int getNBT(Type type) {
      switch(type) {
         case BALL:
            return 0;
         case BALL_LARGE:
            return 1;
         case STAR:
            return 2;
         case BURST:
            return 4;
         case CREEPER:
            return 3;
         default:
            throw new IllegalArgumentException("Unknown effect type " + type);
      }
   }

   static Type getEffectType(int nbt) {
      switch(nbt) {
         case 0:
            return Type.BALL;
         case 1:
            return Type.BALL_LARGE;
         case 2:
            return Type.STAR;
         case 3:
            return Type.CREEPER;
         case 4:
            return Type.BURST;
         default:
            throw new IllegalArgumentException("Unknown effect type " + nbt);
      }
   }

   CraftMetaFirework(Map<String, Object> map) {
      super(map);
      Integer power = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, FLIGHT.BUKKIT, true);
      if (power != null) {
         this.power = power;
      }

      Iterable<?> effects = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, EXPLOSIONS.BUKKIT, true);
      this.safelyAddEffects(effects);
   }

   public boolean hasEffects() {
      return this.effects != null && !this.effects.isEmpty();
   }

   void safelyAddEffects(Iterable<?> collection) {
      if (collection != null && (!(collection instanceof Collection) || !((Collection)collection).isEmpty())) {
         List<FireworkEffect> effects = this.effects;
         if (effects == null) {
            effects = this.effects = new ArrayList();
         }

         for(Object obj : collection) {
            if (!(obj instanceof FireworkEffect)) {
               throw new IllegalArgumentException(obj + " in " + collection + " is not a FireworkEffect");
            }

            effects.add((FireworkEffect)obj);
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound itemTag) {
      super.applyToItem(itemTag);
      if (!this.isFireworkEmpty()) {
         NBTTagCompound fireworks = itemTag.p(FIREWORKS.NBT);
         itemTag.a(FIREWORKS.NBT, fireworks);
         if (this.hasEffects()) {
            NBTTagList effects = new NBTTagList();

            for(FireworkEffect effect : this.effects) {
               effects.add(getExplosion(effect));
            }

            if (effects.size() > 0) {
               fireworks.a(EXPLOSIONS.NBT, effects);
            }
         }

         if (this.hasPower()) {
            fireworks.a(FLIGHT.NBT, this.power.byteValue());
         }
      }
   }

   static void addColors(NBTTagCompound compound, CraftMetaItem.ItemMetaKey key, List<Color> colors) {
      if (!colors.isEmpty()) {
         int[] colorArray = new int[colors.size()];
         int i = 0;

         for(Color color : colors) {
            colorArray[i++] = color.asRGB();
         }

         compound.a(key.NBT, colorArray);
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.FIREWORK_ROCKET;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isFireworkEmpty();
   }

   boolean isFireworkEmpty() {
      return !this.hasEffects() && !this.hasPower();
   }

   boolean hasPower() {
      return this.power != null && this.power != 0;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaFirework)) {
         return true;
      } else {
         CraftMetaFirework that = (CraftMetaFirework)meta;
         return (this.hasPower() ? that.hasPower() && this.power == that.power : !that.hasPower())
            && (this.hasEffects() ? that.hasEffects() && this.effects.equals(that.effects) : !that.hasEffects());
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaFirework || this.isFireworkEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasPower()) {
         hash = 61 * hash + this.power;
      }

      if (this.hasEffects()) {
         hash = 61 * hash + 13 * this.effects.hashCode();
      }

      return hash != original ? CraftMetaFirework.class.hashCode() ^ hash : hash;
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasEffects()) {
         builder.put(EXPLOSIONS.BUKKIT, ImmutableList.copyOf(this.effects));
      }

      if (this.hasPower()) {
         builder.put(FLIGHT.BUKKIT, this.power);
      }

      return builder;
   }

   public CraftMetaFirework clone() {
      CraftMetaFirework meta = (CraftMetaFirework)super.clone();
      if (this.effects != null) {
         meta.effects = new ArrayList(this.effects);
      }

      return meta;
   }

   public void addEffect(FireworkEffect effect) {
      Validate.notNull(effect, "Effect cannot be null");
      if (this.effects == null) {
         this.effects = new ArrayList();
      }

      this.effects.add(effect);
   }

   public void addEffects(FireworkEffect... effects) {
      Validate.notNull(effects, "Effects cannot be null");
      if (effects.length != 0) {
         List<FireworkEffect> list = this.effects;
         if (list == null) {
            list = this.effects = new ArrayList();
         }

         for(FireworkEffect effect : effects) {
            Validate.notNull(effect, "Effect cannot be null");
            list.add(effect);
         }
      }
   }

   public void addEffects(Iterable<FireworkEffect> effects) {
      Validate.notNull(effects, "Effects cannot be null");
      this.safelyAddEffects(effects);
   }

   public List<FireworkEffect> getEffects() {
      return this.effects == null ? ImmutableList.of() : ImmutableList.copyOf(this.effects);
   }

   public int getEffectsSize() {
      return this.effects == null ? 0 : this.effects.size();
   }

   public void removeEffect(int index) {
      if (this.effects == null) {
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
      } else {
         this.effects.remove(index);
      }
   }

   public void clearEffects() {
      this.effects = null;
   }

   public int getPower() {
      return this.hasPower() ? this.power : 0;
   }

   public void setPower(int power) {
      Validate.isTrue(power >= 0, "Power cannot be less than zero: ", (long)power);
      Validate.isTrue(power < 128, "Power cannot be more than 127: ", (long)power);
      this.power = power;
   }
}
