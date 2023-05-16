package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.inventory.meta.BookMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaBookSigned extends CraftMetaBook implements BookMeta {
   CraftMetaBookSigned(CraftMetaItem meta) {
      super(meta);
   }

   CraftMetaBookSigned(NBTTagCompound tag) {
      super(tag);
   }

   CraftMetaBookSigned(Map<String, Object> map) {
      super(map);
   }

   @Override
   protected String deserializePage(String pageData) {
      return CraftChatMessage.fromJSONOrStringToJSON(pageData, false, true, 320, false);
   }

   @Override
   protected String convertPlainPageToData(String page) {
      return CraftChatMessage.fromStringToJSON(page, true);
   }

   @Override
   protected String convertDataToPlainPage(String pageData) {
      return CraftChatMessage.fromJSONComponent(pageData);
   }

   @Override
   void applyToItem(NBTTagCompound itemData) {
      super.applyToItem(itemData);
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty();
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.WRITTEN_BOOK || type == Material.WRITABLE_BOOK;
   }

   public CraftMetaBookSigned clone() {
      return (CraftMetaBookSigned)super.clone();
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      return original != hash ? CraftMetaBookSigned.class.hashCode() ^ hash : hash;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      return super.equalsCommon(meta);
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaBookSigned || this.isBookEmpty());
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      return builder;
   }
}
