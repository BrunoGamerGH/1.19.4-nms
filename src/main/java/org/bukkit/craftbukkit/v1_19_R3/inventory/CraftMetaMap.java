package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
class CraftMetaMap extends CraftMetaItem implements MapMeta {
   static final CraftMetaItem.ItemMetaKey MAP_SCALING = new CraftMetaItem.ItemMetaKey("map_is_scaling", "scaling");
   static final CraftMetaItem.ItemMetaKey MAP_LOC_NAME = new CraftMetaItem.ItemMetaKey("LocName", "display-loc-name");
   static final CraftMetaItem.ItemMetaKey MAP_COLOR = new CraftMetaItem.ItemMetaKey("MapColor", "display-map-color");
   static final CraftMetaItem.ItemMetaKey MAP_ID = new CraftMetaItem.ItemMetaKey("map", "map-id");
   static final byte SCALING_EMPTY = 0;
   static final byte SCALING_TRUE = 1;
   static final byte SCALING_FALSE = 2;
   private Integer mapId;
   private byte scaling = 0;
   private String locName;
   private Color color;

   CraftMetaMap(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaMap) {
         CraftMetaMap map = (CraftMetaMap)meta;
         this.mapId = map.mapId;
         this.scaling = map.scaling;
         this.locName = map.locName;
         this.color = map.color;
      }
   }

   CraftMetaMap(NBTTagCompound tag) {
      super(tag);
      if (tag.b(MAP_ID.NBT, 99)) {
         this.mapId = tag.h(MAP_ID.NBT);
      }

      if (tag.e(MAP_SCALING.NBT)) {
         this.scaling = (byte)(tag.q(MAP_SCALING.NBT) ? 1 : 2);
      }

      if (tag.e(DISPLAY.NBT)) {
         NBTTagCompound display = tag.p(DISPLAY.NBT);
         if (display.e(MAP_LOC_NAME.NBT)) {
            this.locName = display.l(MAP_LOC_NAME.NBT);
         }

         if (display.e(MAP_COLOR.NBT)) {
            try {
               this.color = Color.fromRGB(display.h(MAP_COLOR.NBT));
            } catch (IllegalArgumentException var4) {
            }
         }
      }
   }

   CraftMetaMap(Map<String, Object> map) {
      super(map);
      Integer id = CraftMetaItem.SerializableMeta.getObject(Integer.class, map, MAP_ID.BUKKIT, true);
      if (id != null) {
         this.setMapId(id);
      }

      Boolean scaling = CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, MAP_SCALING.BUKKIT, true);
      if (scaling != null) {
         this.setScaling(scaling);
      }

      String locName = CraftMetaItem.SerializableMeta.getString(map, MAP_LOC_NAME.BUKKIT, true);
      if (locName != null) {
         this.setLocationName(locName);
      }

      Color color = CraftMetaItem.SerializableMeta.getObject(Color.class, map, MAP_COLOR.BUKKIT, true);
      if (color != null) {
         this.setColor(color);
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.hasMapId()) {
         tag.a(MAP_ID.NBT, this.getMapId());
      }

      if (this.hasScaling()) {
         tag.a(MAP_SCALING.NBT, this.isScaling());
      }

      if (this.hasLocationName()) {
         this.setDisplayTag(tag, MAP_LOC_NAME.NBT, NBTTagString.a(this.getLocationName()));
      }

      if (this.hasColor()) {
         this.setDisplayTag(tag, MAP_COLOR.NBT, NBTTagInt.a(this.color.asRGB()));
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.FILLED_MAP;
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isMapEmpty();
   }

   boolean isMapEmpty() {
      return !this.hasMapId() && !(this.hasScaling() | this.hasLocationName()) && !this.hasColor();
   }

   public boolean hasMapId() {
      return this.mapId != null;
   }

   public int getMapId() {
      return this.mapId;
   }

   public void setMapId(int id) {
      this.mapId = id;
   }

   public boolean hasMapView() {
      return this.mapId != null;
   }

   public MapView getMapView() {
      Preconditions.checkState(this.hasMapView(), "Item does not have map associated - check hasMapView() first!");
      return Bukkit.getMap(this.mapId);
   }

   public void setMapView(MapView map) {
      this.mapId = map != null ? map.getId() : null;
   }

   boolean hasScaling() {
      return this.scaling != 0;
   }

   public boolean isScaling() {
      return this.scaling == 1;
   }

   public void setScaling(boolean scaling) {
      this.scaling = (byte)(scaling ? 1 : 2);
   }

   public boolean hasLocationName() {
      return this.locName != null;
   }

   public String getLocationName() {
      return this.locName;
   }

   public void setLocationName(String name) {
      this.locName = name;
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
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (!(meta instanceof CraftMetaMap)) {
         return true;
      } else {
         CraftMetaMap that = (CraftMetaMap)meta;
         return this.scaling == that.scaling
            && (this.hasMapId() ? that.hasMapId() && this.mapId.equals(that.mapId) : !that.hasMapId())
            && (this.hasLocationName() ? that.hasLocationName() && this.locName.equals(that.locName) : !that.hasLocationName())
            && (this.hasColor() ? that.hasColor() && this.color.equals(that.color) : !that.hasColor());
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaMap || this.isMapEmpty());
   }

   @Override
   int applyHash() {
      int original;
      int hash = original = super.applyHash();
      if (this.hasMapId()) {
         hash = 61 * hash + this.mapId.hashCode();
      }

      if (this.hasScaling()) {
         hash ^= 572662306 << (this.isScaling() ? 1 : -1);
      }

      if (this.hasLocationName()) {
         hash = 61 * hash + this.locName.hashCode();
      }

      if (this.hasColor()) {
         hash = 61 * hash + this.color.hashCode();
      }

      return original != hash ? CraftMetaMap.class.hashCode() ^ hash : hash;
   }

   public CraftMetaMap clone() {
      return (CraftMetaMap)super.clone();
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasMapId()) {
         builder.put(MAP_ID.BUKKIT, this.getMapId());
      }

      if (this.hasScaling()) {
         builder.put(MAP_SCALING.BUKKIT, this.isScaling());
      }

      if (this.hasLocationName()) {
         builder.put(MAP_LOC_NAME.BUKKIT, this.getLocationName());
      }

      if (this.hasColor()) {
         builder.put(MAP_COLOR.BUKKIT, this.getColor());
      }

      return builder;
   }
}
