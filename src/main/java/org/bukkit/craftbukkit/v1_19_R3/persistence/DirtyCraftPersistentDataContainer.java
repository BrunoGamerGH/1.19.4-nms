package org.bukkit.craftbukkit.v1_19_R3.persistence;

import java.util.Map;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public final class DirtyCraftPersistentDataContainer extends CraftPersistentDataContainer {
   private boolean dirty;

   public DirtyCraftPersistentDataContainer(Map<String, NBTBase> customTags, CraftPersistentDataTypeRegistry registry) {
      super(customTags, registry);
   }

   public DirtyCraftPersistentDataContainer(CraftPersistentDataTypeRegistry registry) {
      super(registry);
   }

   public boolean dirty() {
      return this.dirty;
   }

   public void dirty(boolean dirty) {
      this.dirty = dirty;
   }

   @Override
   public <T, Z> void set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
      super.set(key, type, value);
      this.dirty(true);
   }

   @Override
   public void remove(NamespacedKey key) {
      super.remove(key);
      this.dirty(true);
   }

   @Override
   public void put(String key, NBTBase base) {
      super.put(key, base);
      this.dirty(true);
   }

   @Override
   public void putAll(NBTTagCompound compound) {
      super.putAll(compound);
      this.dirty(true);
   }

   @Override
   public void putAll(Map<String, NBTBase> map) {
      super.putAll(map);
      this.dirty(true);
   }
}
