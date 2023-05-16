package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaSuspiciousStew extends CraftMetaItem implements SuspiciousStewMeta {
   static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("EffectDuration", "duration");
   static final CraftMetaItem.ItemMetaKey EFFECTS = new CraftMetaItem.ItemMetaKey("Effects", "effects");
   static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("EffectId", "id");
   private List<PotionEffect> customEffects;

   CraftMetaSuspiciousStew(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaSuspiciousStew) {
         CraftMetaSuspiciousStew stewMeta = (CraftMetaSuspiciousStew)meta;
         if (stewMeta.hasCustomEffects()) {
            this.customEffects = new ArrayList(stewMeta.customEffects);
         }
      }
   }

   CraftMetaSuspiciousStew(NBTTagCompound tag) {
      super(tag);
      if (tag.e(EFFECTS.NBT)) {
         NBTTagList list = tag.c(EFFECTS.NBT, 10);
         int length = list.size();
         this.customEffects = new ArrayList(length);

         for(int i = 0; i < length; ++i) {
            NBTTagCompound effect = list.a(i);
            PotionEffectType type = PotionEffectType.getById(effect.f(ID.NBT));
            if (type != null) {
               int duration = effect.h(DURATION.NBT);
               this.customEffects.add(new PotionEffect(type, duration, 0));
            }
         }
      }
   }

   CraftMetaSuspiciousStew(Map<String, Object> map) {
      super(map);
      Iterable<?> rawEffectList = CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, EFFECTS.BUKKIT, true);
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
      if (this.customEffects != null) {
         NBTTagList effectList = new NBTTagList();
         tag.a(EFFECTS.NBT, effectList);

         for(PotionEffect effect : this.customEffects) {
            NBTTagCompound effectData = new NBTTagCompound();
            effectData.a(ID.NBT, (byte)effect.getType().getId());
            effectData.a(DURATION.NBT, effect.getDuration());
            effectList.add(effectData);
         }
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isStewEmpty();
   }

   boolean isStewEmpty() {
      return !this.hasCustomEffects();
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.SUSPICIOUS_STEW;
   }

   public CraftMetaSuspiciousStew clone() {
      CraftMetaSuspiciousStew clone = (CraftMetaSuspiciousStew)super.clone();
      if (this.customEffects != null) {
         clone.customEffects = new ArrayList(this.customEffects);
      }

      return clone;
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
            if (old.getDuration() == effect.getDuration()) {
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

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasCustomEffects()) {
         hash = 73 * hash + this.customEffects.hashCode();
      }

      return original != hash ? CraftMetaSuspiciousStew.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaSuspiciousStew)) {
         return true;
      } else {
         CraftMetaSuspiciousStew that = (CraftMetaSuspiciousStew)meta;
         return this.hasCustomEffects() ? that.hasCustomEffects() && this.customEffects.equals(that.customEffects) : !that.hasCustomEffects();
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaSuspiciousStew || this.isStewEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasCustomEffects()) {
         builder.put(EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
      }

      return builder;
   }
}
