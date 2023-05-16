package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang.Validate;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaPotion extends CraftMetaItem implements PotionMeta {
   private static final Set<Material> POTION_MATERIALS = Sets.newHashSet(
      new Material[]{Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW}
   );
   static final CraftMetaItem.ItemMetaKey AMPLIFIER = new CraftMetaItem.ItemMetaKey("Amplifier", "amplifier");
   static final CraftMetaItem.ItemMetaKey AMBIENT = new CraftMetaItem.ItemMetaKey("Ambient", "ambient");
   static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("Duration", "duration");
   static final CraftMetaItem.ItemMetaKey SHOW_PARTICLES = new CraftMetaItem.ItemMetaKey("ShowParticles", "has-particles");
   static final CraftMetaItem.ItemMetaKey SHOW_ICON = new CraftMetaItem.ItemMetaKey("ShowIcon", "has-icon");
   static final CraftMetaItem.ItemMetaKey POTION_EFFECTS = new CraftMetaItem.ItemMetaKey("CustomPotionEffects", "custom-effects");
   static final CraftMetaItem.ItemMetaKey POTION_COLOR = new CraftMetaItem.ItemMetaKey("CustomPotionColor", "custom-color");
   static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("Id", "potion-id");
   static final CraftMetaItem.ItemMetaKey DEFAULT_POTION = new CraftMetaItem.ItemMetaKey("Potion", "potion-type");
   private PotionData type = new PotionData(PotionType.UNCRAFTABLE, false, false);
   private List<PotionEffect> customEffects;
   private Color color;

   CraftMetaPotion(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaPotion) {
         CraftMetaPotion potionMeta = (CraftMetaPotion)meta;
         this.type = potionMeta.type;
         this.color = potionMeta.color;
         if (potionMeta.hasCustomEffects()) {
            this.customEffects = new ArrayList(potionMeta.customEffects);
         }
      }
   }

   CraftMetaPotion(NBTTagCompound tag) {
      super(tag);
      if (tag.e(DEFAULT_POTION.NBT)) {
         this.type = CraftPotionUtil.toBukkit(tag.l(DEFAULT_POTION.NBT));
      }

      if (tag.e(POTION_COLOR.NBT)) {
         try {
            this.color = Color.fromRGB(tag.h(POTION_COLOR.NBT));
         } catch (IllegalArgumentException var12) {
         }
      }

      if (tag.e(POTION_EFFECTS.NBT)) {
         NBTTagList list = tag.c(POTION_EFFECTS.NBT, 10);
         int length = list.size();
         this.customEffects = new ArrayList(length);

         for(int i = 0; i < length; ++i) {
            NBTTagCompound effect = list.a(i);
            PotionEffectType type = PotionEffectType.getById(effect.f(ID.NBT));
            if (type != null) {
               int amp = effect.f(AMPLIFIER.NBT);
               int duration = effect.h(DURATION.NBT);
               boolean ambient = effect.q(AMBIENT.NBT);
               boolean particles = effect.b(SHOW_PARTICLES.NBT, 1) ? effect.q(SHOW_PARTICLES.NBT) : true;
               boolean icon = effect.b(SHOW_ICON.NBT, 1) ? effect.q(SHOW_ICON.NBT) : particles;
               this.customEffects.add(new PotionEffect(type, duration, amp, ambient, particles, icon));
            }
         }
      }
   }

   CraftMetaPotion(Map<String, Object> map) {
      super(map);
      this.type = CraftPotionUtil.toBukkit(CraftMetaItem.SerializableMeta.getString(map, DEFAULT_POTION.BUKKIT, true));
      Color color = CraftMetaItem.SerializableMeta.getObject(Color.class, map, POTION_COLOR.BUKKIT, true);
      if (color != null) {
         this.setColor(color);
      }

      Iterable<?> rawEffectList = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, POTION_EFFECTS.BUKKIT, true);
      if (rawEffectList != null) {
         for(Object obj : rawEffectList) {
            if (!(obj instanceof PotionEffect)) {
               throw new IllegalArgumentException("Object in effect list is not valid. " + obj.getClass());
            }

            this.addCustomEffect((PotionEffect)obj, true);
         }
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      tag.a(DEFAULT_POTION.NBT, CraftPotionUtil.fromBukkit(this.type));
      if (this.hasColor()) {
         tag.a(POTION_COLOR.NBT, this.color.asRGB());
      }

      if (this.customEffects != null) {
         NBTTagList effectList = new NBTTagList();
         tag.a(POTION_EFFECTS.NBT, effectList);

         for(PotionEffect effect : this.customEffects) {
            NBTTagCompound effectData = new NBTTagCompound();
            effectData.a(ID.NBT, (byte)effect.getType().getId());
            effectData.a(AMPLIFIER.NBT, (byte)effect.getAmplifier());
            effectData.a(DURATION.NBT, effect.getDuration());
            effectData.a(AMBIENT.NBT, effect.isAmbient());
            effectData.a(SHOW_PARTICLES.NBT, effect.hasParticles());
            effectData.a(SHOW_ICON.NBT, effect.hasIcon());
            effectList.add(effectData);
         }
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isPotionEmpty();
   }

   boolean isPotionEmpty() {
      return this.type.getType() == PotionType.UNCRAFTABLE && !this.hasCustomEffects() && !this.hasColor();
   }

   @Override
   boolean applicableTo(Material type) {
      return POTION_MATERIALS.contains(type);
   }

   public CraftMetaPotion clone() {
      CraftMetaPotion clone = (CraftMetaPotion)super.clone();
      clone.type = this.type;
      if (this.customEffects != null) {
         clone.customEffects = new ArrayList(this.customEffects);
      }

      return clone;
   }

   public void setBasePotionData(PotionData data) {
      Validate.notNull(data, "PotionData cannot be null");
      this.type = data;
   }

   public PotionData getBasePotionData() {
      return this.type;
   }

   public boolean hasCustomEffects() {
      return this.customEffects != null;
   }

   public List<PotionEffect> getCustomEffects() {
      return this.hasCustomEffects() ? ImmutableList.copyOf(this.customEffects) : ImmutableList.of();
   }

   public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
      Validate.notNull(effect, "Potion effect must not be null");
      int index = this.indexOfEffect(effect.getType());
      if (index != -1) {
         if (overwrite) {
            PotionEffect old = (PotionEffect)this.customEffects.get(index);
            if (old.getAmplifier() == effect.getAmplifier() && old.getDuration() == effect.getDuration() && old.isAmbient() == effect.isAmbient()) {
               return false;
            } else {
               this.customEffects.set(index, effect);
               return true;
            }
         } else {
            return false;
         }
      } else {
         if (this.customEffects == null) {
            this.customEffects = new ArrayList();
         }

         this.customEffects.add(effect);
         return true;
      }
   }

   public boolean removeCustomEffect(PotionEffectType type) {
      Validate.notNull(type, "Potion effect type must not be null");
      if (!this.hasCustomEffects()) {
         return false;
      } else {
         boolean changed = false;
         Iterator<PotionEffect> iterator = this.customEffects.iterator();

         while(iterator.hasNext()) {
            PotionEffect effect = (PotionEffect)iterator.next();
            if (type.equals(effect.getType())) {
               iterator.remove();
               changed = true;
            }
         }

         if (this.customEffects.isEmpty()) {
            this.customEffects = null;
         }

         return changed;
      }
   }

   public boolean hasCustomEffect(PotionEffectType type) {
      Validate.notNull(type, "Potion effect type must not be null");
      return this.indexOfEffect(type) != -1;
   }

   public boolean setMainEffect(PotionEffectType type) {
      Validate.notNull(type, "Potion effect type must not be null");
      int index = this.indexOfEffect(type);
      if (index != -1 && index != 0) {
         PotionEffect old = (PotionEffect)this.customEffects.get(0);
         this.customEffects.set(0, (PotionEffect)this.customEffects.get(index));
         this.customEffects.set(index, old);
         return true;
      } else {
         return false;
      }
   }

   private int indexOfEffect(PotionEffectType type) {
      if (!this.hasCustomEffects()) {
         return -1;
      } else {
         for(int i = 0; i < this.customEffects.size(); ++i) {
            if (((PotionEffect)this.customEffects.get(i)).getType().equals(type)) {
               return i;
            }
         }

         return -1;
      }
   }

   public boolean clearCustomEffects() {
      boolean changed = this.hasCustomEffects();
      this.customEffects = null;
      return changed;
   }

   public boolean hasColor() {
      return this.color != null;
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color;
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.type.getType() != PotionType.UNCRAFTABLE) {
         hash = 73 * hash + this.type.hashCode();
      }

      if (this.hasColor()) {
         hash = 73 * hash + this.color.hashCode();
      }

      if (this.hasCustomEffects()) {
         hash = 73 * hash + this.customEffects.hashCode();
      }

      return original != hash ? CraftMetaPotion.class.hashCode() ^ hash : hash;
   }

   @Override
   public boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaPotion)) {
         return true;
      } else {
         CraftMetaPotion that = (CraftMetaPotion)meta;
         return this.type.equals(that.type)
            && (this.hasCustomEffects() ? that.hasCustomEffects() && this.customEffects.equals(that.customEffects) : !that.hasCustomEffects())
            && (this.hasColor() ? that.hasColor() && this.color.equals(that.color) : !that.hasColor());
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaPotion || this.isPotionEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.type.getType() != PotionType.UNCRAFTABLE) {
         builder.put(DEFAULT_POTION.BUKKIT, CraftPotionUtil.fromBukkit(this.type));
      }

      if (this.hasColor()) {
         builder.put(POTION_COLOR.BUKKIT, this.getColor());
      }

      if (this.hasCustomEffects()) {
         builder.put(POTION_EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
      }

      return builder;
   }
}
