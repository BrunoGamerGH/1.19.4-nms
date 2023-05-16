package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaLeatherArmor extends CraftMetaItem implements LeatherArmorMeta {
   private static final Set<Material> LEATHER_ARMOR_MATERIALS = Sets.newHashSet(
      new Material[]{Material.LEATHER_HELMET, Material.LEATHER_HORSE_ARMOR, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS}
   );
   static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("color");
   private Color color = CraftItemFactory.DEFAULT_LEATHER_COLOR;

   CraftMetaLeatherArmor(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaLeatherArmor) {
         CraftMetaLeatherArmor armorMeta = (CraftMetaLeatherArmor)meta;
         this.color = armorMeta.color;
      }
   }

   CraftMetaLeatherArmor(NBTTagCompound tag) {
      super(tag);
      if (tag.e(DISPLAY.NBT)) {
         NBTTagCompound display = tag.p(DISPLAY.NBT);
         if (display.e(COLOR.NBT)) {
            try {
               this.color = Color.fromRGB(display.h(COLOR.NBT));
            } catch (IllegalArgumentException var4) {
            }
         }
      }
   }

   CraftMetaLeatherArmor(Map<String, Object> map) {
      super(map);
      this.setColor(CraftMetaItem.SerializableMeta.getObject(Color.class, map, COLOR.BUKKIT, true));
   }

   @Override
   void applyToItem(NBTTagCompound itemTag) {
      super.applyToItem(itemTag);
      if (this.hasColor()) {
         this.setDisplayTag(itemTag, COLOR.NBT, NBTTagInt.a(this.color.asRGB()));
      }
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isLeatherArmorEmpty();
   }

   boolean isLeatherArmorEmpty() {
      return !this.hasColor();
   }

   @Override
   boolean applicableTo(Material type) {
      return LEATHER_ARMOR_MATERIALS.contains(type);
   }

   public CraftMetaLeatherArmor clone() {
      return (CraftMetaLeatherArmor)super.clone();
   }

   public Color getColor() {
      return this.color;
   }

   public void setColor(Color color) {
      this.color = color == null ? CraftItemFactory.DEFAULT_LEATHER_COLOR : color;
   }

   boolean hasColor() {
      return !CraftItemFactory.DEFAULT_LEATHER_COLOR.equals(this.color);
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasColor()) {
         builder.put(COLOR.BUKKIT, this.color);
      }

      return builder;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else {
         return meta instanceof CraftMetaLeatherArmor that ? this.color.equals(that.color) : true;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaLeatherArmor || this.isLeatherArmorEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasColor()) {
         hash ^= this.color.hashCode();
      }

      return original != hash ? CraftMetaSkull.class.hashCode() ^ hash : hash;
   }
}
