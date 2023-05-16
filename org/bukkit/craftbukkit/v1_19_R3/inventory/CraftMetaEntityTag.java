package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaEntityTag extends CraftMetaItem {
   private static final Set<Material> ENTITY_TAGGABLE_MATERIALS = Sets.newHashSet(
      new Material[]{
         Material.COD_BUCKET, Material.PUFFERFISH_BUCKET, Material.SALMON_BUCKET, Material.ITEM_FRAME, Material.GLOW_ITEM_FRAME, Material.PAINTING
      }
   );
   static final CraftMetaItem.ItemMetaKey ENTITY_TAG = new CraftMetaItem.ItemMetaKey("EntityTag", "entity-tag");
   NBTTagCompound entityTag;

   CraftMetaEntityTag(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaEntityTag) {
         CraftMetaEntityTag entity = (CraftMetaEntityTag)meta;
         this.entityTag = entity.entityTag;
      }
   }

   CraftMetaEntityTag(NBTTagCompound tag) {
      super(tag);
      if (tag.e(ENTITY_TAG.NBT)) {
         this.entityTag = tag.p(ENTITY_TAG.NBT).h();
      }
   }

   CraftMetaEntityTag(Map<String, Object> map) {
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
      return ENTITY_TAGGABLE_MATERIALS.contains(type);
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isEntityTagEmpty();
   }

   boolean isEntityTagEmpty() {
      return this.entityTag == null;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaEntityTag)) {
         return true;
      } else {
         CraftMetaEntityTag that = (CraftMetaEntityTag)meta;
         return this.entityTag != null ? that.entityTag != null && this.entityTag.equals(that.entityTag) : this.entityTag == null;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaEntityTag || this.isEntityTagEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.entityTag != null) {
         hash = 73 * hash + this.entityTag.hashCode();
      }

      return original != hash ? CraftMetaEntityTag.class.hashCode() ^ hash : hash;
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      return builder;
   }

   public CraftMetaEntityTag clone() {
      CraftMetaEntityTag clone = (CraftMetaEntityTag)super.clone();
      if (this.entityTag != null) {
         clone.entityTag = this.entityTag.h();
      }

      return clone;
   }
}
