package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.entity.Axolotl.Variant;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaAxolotlBucket extends CraftMetaItem implements AxolotlBucketMeta {
   static final CraftMetaItem.ItemMetaKey VARIANT = new CraftMetaItem.ItemMetaKey("Variant", "axolotl-variant");
   static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
   private Integer variant;
   private NBTTagCompound entityTag;

   CraftMetaAxolotlBucket(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaAxolotlBucket) {
         CraftMetaAxolotlBucket bucket = (CraftMetaAxolotlBucket)meta;
         this.variant = bucket.variant;
         this.entityTag = bucket.entityTag;
      }
   }

   CraftMetaAxolotlBucket(NBTTagCompound tag) {
      super(tag);
      if (tag.b(VARIANT.NBT, 3)) {
         this.variant = tag.h(VARIANT.NBT);
      }

      if (tag.e(ENTITY_TAG.NBT)) {
         this.entityTag = tag.p(ENTITY_TAG.NBT).h();
      }
   }

   CraftMetaAxolotlBucket(Map<String, Object> map) {
      super(map);
      Integer variant = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, VARIANT.BUKKIT, true);
      if (variant != null) {
         this.variant = variant;
      }
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
      if (this.hasVariant()) {
         tag.a(VARIANT.NBT, this.variant);
      }

      if (this.entityTag != null) {
         tag.a(ENTITY_TAG.NBT, this.entityTag);
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.AXOLOTL_BUCKET;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isBucketEmpty();
   }

   boolean isBucketEmpty() {
      return !this.hasVariant() && this.entityTag == null;
   }

   public Variant getVariant() {
      return Variant.values()[this.variant];
   }

   public void setVariant(Variant variant) {
      if (variant == null) {
         variant = Variant.LUCY;
      }

      this.variant = variant.ordinal();
   }

   public boolean hasVariant() {
      return this.variant != null;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaAxolotlBucket)) {
         return true;
      } else {
         CraftMetaAxolotlBucket that = (CraftMetaAxolotlBucket)meta;
         return (this.hasVariant() ? that.hasVariant() && this.variant.equals(that.variant) : !that.hasVariant())
            && (this.entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : that.entityTag == null);
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaAxolotlBucket || this.isBucketEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasVariant()) {
         hash = 61 * hash + this.variant;
      }

      if (this.entityTag != null) {
         hash = 61 * hash + this.entityTag.hashCode();
      }

      return original != hash ? CraftMetaAxolotlBucket.class.hashCode() ^ hash : hash;
   }

   public CraftMetaAxolotlBucket clone() {
      CraftMetaAxolotlBucket clone = (CraftMetaAxolotlBucket)super.clone();
      if (this.entityTag != null) {
         clone.entityTag = this.entityTag.h();
      }

      return clone;
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasVariant()) {
         builder.put(VARIANT.BUKKIT, this.variant);
      }

      return builder;
   }
}
