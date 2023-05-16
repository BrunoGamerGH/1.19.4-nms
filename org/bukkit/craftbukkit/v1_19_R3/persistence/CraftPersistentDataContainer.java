package org.bukkit.craftbukkit.v1_19_R3.persistence;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNBTTagConfigSerializer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CraftPersistentDataContainer implements PersistentDataContainer {
   private final Map<String, NBTBase> customDataTags = new HashMap<>();
   private final CraftPersistentDataTypeRegistry registry;
   private final CraftPersistentDataAdapterContext adapterContext;

   public CraftPersistentDataContainer(Map<String, NBTBase> customTags, CraftPersistentDataTypeRegistry registry) {
      this(registry);
      this.customDataTags.putAll(customTags);
   }

   public CraftPersistentDataContainer(CraftPersistentDataTypeRegistry registry) {
      this.registry = registry;
      this.adapterContext = new CraftPersistentDataAdapterContext(this.registry);
   }

   public <T, Z> void set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
      Validate.notNull(key, "The provided key for the custom value was null");
      Validate.notNull(type, "The provided type for the custom value was null");
      Validate.notNull(value, "The provided value for the custom value was null");
      this.customDataTags.put(key.toString(), this.registry.wrap(type.getPrimitiveType(), type.toPrimitive(value, this.adapterContext)));
   }

   public <T, Z> boolean has(NamespacedKey key, PersistentDataType<T, Z> type) {
      Validate.notNull(key, "The provided key for the custom value was null");
      Validate.notNull(type, "The provided type for the custom value was null");
      NBTBase value = this.customDataTags.get(key.toString());
      return value == null ? false : this.registry.isInstanceOf(type.getPrimitiveType(), value);
   }

   public <T, Z> Z get(NamespacedKey key, PersistentDataType<T, Z> type) {
      Validate.notNull(key, "The provided key for the custom value was null");
      Validate.notNull(type, "The provided type for the custom value was null");
      NBTBase value = this.customDataTags.get(key.toString());
      return (Z)(value == null ? null : type.fromPrimitive(this.registry.extract(type.getPrimitiveType(), value), this.adapterContext));
   }

   public <T, Z> Z getOrDefault(NamespacedKey key, PersistentDataType<T, Z> type, Z defaultValue) {
      Z z = this.get(key, type);
      return (Z)(z != null ? z : defaultValue);
   }

   public Set<NamespacedKey> getKeys() {
      Set<NamespacedKey> keys = new HashSet();
      this.customDataTags.keySet().forEach(key -> {
         String[] keyData = key.split(":", 2);
         if (keyData.length == 2) {
            keys.add(new NamespacedKey(keyData[0], keyData[1]));
         }
      });
      return keys;
   }

   public void remove(NamespacedKey key) {
      Validate.notNull(key, "The provided key for the custom value was null");
      this.customDataTags.remove(key.toString());
   }

   public boolean isEmpty() {
      return this.customDataTags.isEmpty();
   }

   public PersistentDataAdapterContext getAdapterContext() {
      return this.adapterContext;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof CraftPersistentDataContainer)) {
         return false;
      } else {
         Map<String, NBTBase> myRawMap = this.getRaw();
         Map<String, NBTBase> theirRawMap = ((CraftPersistentDataContainer)obj).getRaw();
         return Objects.equals(myRawMap, theirRawMap);
      }
   }

   public NBTTagCompound toTagCompound() {
      NBTTagCompound tag = new NBTTagCompound();

      for(Entry<String, NBTBase> entry : this.customDataTags.entrySet()) {
         tag.a(entry.getKey(), entry.getValue());
      }

      return tag;
   }

   public void put(String key, NBTBase base) {
      this.customDataTags.put(key, base);
   }

   public void putAll(Map<String, NBTBase> map) {
      this.customDataTags.putAll(map);
   }

   public void putAll(NBTTagCompound compound) {
      for(String key : compound.e()) {
         this.customDataTags.put(key, compound.c(key));
      }
   }

   public Map<String, NBTBase> getRaw() {
      return this.customDataTags;
   }

   public CraftPersistentDataTypeRegistry getDataTagTypeRegistry() {
      return this.registry;
   }

   @Override
   public int hashCode() {
      int hashCode = 3;
      return hashCode + this.customDataTags.hashCode();
   }

   public Map<String, Object> serialize() {
      return (Map<String, Object>)CraftNBTTagConfigSerializer.serialize(this.toTagCompound());
   }
}
