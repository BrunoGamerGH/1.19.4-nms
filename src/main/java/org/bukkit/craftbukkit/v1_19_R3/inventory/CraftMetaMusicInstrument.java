package org.bukkit.craftbukkit.v1_19_R3.inventory;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.DelegateDeserialization;
import org.bukkit.inventory.meta.MusicInstrumentMeta;

@DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
public class CraftMetaMusicInstrument extends CraftMetaItem implements MusicInstrumentMeta {
   static final CraftMetaItem.ItemMetaKey GOAT_HORN_INSTRUMENT = new CraftMetaItem.ItemMetaKey("instrument");
   private MusicInstrument instrument;

   CraftMetaMusicInstrument(CraftMetaItem meta) {
      super(meta);
      if (meta instanceof CraftMetaMusicInstrument craftMetaMusicInstrument) {
         this.instrument = craftMetaMusicInstrument.instrument;
      }
   }

   CraftMetaMusicInstrument(NBTTagCompound tag) {
      super(tag);
      if (tag.e(GOAT_HORN_INSTRUMENT.NBT)) {
         String string = tag.l(GOAT_HORN_INSTRUMENT.NBT);
         this.instrument = MusicInstrument.getByKey(NamespacedKey.fromString(string));
      }
   }

   CraftMetaMusicInstrument(Map<String, Object> map) {
      super(map);
      String instrumentString = CraftMetaItem.SerializableMeta.getString(map, GOAT_HORN_INSTRUMENT.BUKKIT, true);
      if (instrumentString != null) {
         this.instrument = MusicInstrument.getByKey(NamespacedKey.fromString(instrumentString));
      }
   }

   @Override
   void applyToItem(NBTTagCompound tag) {
      super.applyToItem(tag);
      if (this.instrument != null) {
         tag.a(GOAT_HORN_INSTRUMENT.NBT, this.instrument.getKey().toString());
      }
   }

   @Override
   boolean applicableTo(Material type) {
      return type == Material.GOAT_HORN;
   }

   @Override
   boolean equalsCommon(CraftMetaItem meta) {
      if (!super.equalsCommon(meta)) {
         return false;
      } else if (meta instanceof CraftMetaMusicInstrument that) {
         return this.instrument == that.instrument;
      } else {
         return true;
      }
   }

   @Override
   boolean notUncommon(CraftMetaItem meta) {
      return super.notUncommon(meta) && (meta instanceof CraftMetaMusicInstrument || this.isInstrumentEmpty());
   }

   @Override
   boolean isEmpty() {
      return super.isEmpty() && this.isInstrumentEmpty();
   }

   boolean isInstrumentEmpty() {
      return this.instrument == null;
   }

   @Override
   int applyHash() {
      int orginal;
      int hash = orginal = super.applyHash();
      if (this.hasInstrument()) {
         hash = 61 * hash + this.instrument.hashCode();
      }

      return orginal != hash ? CraftMetaMusicInstrument.class.hashCode() ^ hash : hash;
   }

   public CraftMetaMusicInstrument clone() {
      CraftMetaMusicInstrument meta = (CraftMetaMusicInstrument)super.clone();
      meta.instrument = this.instrument;
      return meta;
   }

   @Override
   Builder<String, Object> serialize(Builder<String, Object> builder) {
      super.serialize(builder);
      if (this.hasInstrument()) {
         builder.put(GOAT_HORN_INSTRUMENT.BUKKIT, this.instrument.getKey().toString());
      }

      return builder;
   }

   public MusicInstrument getInstrument() {
      return this.instrument;
   }

   public boolean hasInstrument() {
      return this.instrument != null;
   }

   public void setInstrument(MusicInstrument instrument) {
      this.instrument = instrument;
   }
}
