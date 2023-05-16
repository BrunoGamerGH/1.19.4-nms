package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaArmorStand extends CraftMetaItem {
   static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
   NBTTagCompound entityTag;

   CraftMetaArmorStand(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaArmorStand) {
         CraftMetaArmorStand armorStand = (CraftMetaArmorStand)meta;
         this.entityTag = armorStand.entityTag;
      }
   }

   CraftMetaArmorStand(NBTTagCompound tag) {
      super(tag);
      if (tag.e(ENTITY_TAG.NBT)) {
         this.entityTag = tag.p(ENTITY_TAG.NBT).h();
      }
   }

   CraftMetaArmorStand(Map<String, Object> map) {
      super(map);
   }

   @Override
   void deserializeInternal(NBTTagCompound tag, Object context) {
      super.deserializeInternal(tag, context);
      if (tag.e(ENTITY_TAG.NBT)) {
         this.entityTag = tag.p(ENTITY_TAG.NBT);
      }
   }

   @Override
   void serializeInternal(Map<String, NBTBase> internalTags) {
      if (this.entityTag != null && !this.entityTag.g()) {
         internalTags.put(ENTITY_TAG.NBT, this.entityTag);
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.entityTag != null) {
         tag.a(ENTITY_TAG.NBT, this.entityTag);
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.ARMOR_STAND;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isArmorStandEmpty();
   }

   boolean isArmorStandEmpty() {
      return this.entityTag == null;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaArmorStand)) {
         return true;
      } else {
         CraftMetaArmorStand that = (CraftMetaArmorStand)meta;
         return this.entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : this.entityTag == null;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaArmorStand || this.isArmorStandEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.entityTag != null) {
         hash = 73 * hash + this.entityTag.hashCode();
      }

      return original != hash ? CraftMetaArmorStand.class.hashCode() ^ hash : hash;
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      return builder;
   }

   public CraftMetaArmorStand clone() {
      CraftMetaArmorStand clone = (CraftMetaArmorStand)super.clone();
      if (this.entityTag != null) {
         clone.entityTag = this.entityTag.h();
      }

      return clone;
   }
}
