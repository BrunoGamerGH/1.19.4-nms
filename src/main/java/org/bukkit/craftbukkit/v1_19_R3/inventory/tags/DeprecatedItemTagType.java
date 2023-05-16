package org.bukkit.craftbukkit.v1_19_R3.inventory.tags;

import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public final class DeprecatedItemTagType<T, Z> implements PersistentDataType<T, Z> {
   private final ItemTagType<T, Z> deprecated;

   public DeprecatedItemTagType(ItemTagType<T, Z> deprecated) {
      this.deprecated = deprecated;
   }

   public Class<T> getPrimitiveType() {
      return this.deprecated.getPrimitiveType();
   }

   public Class<Z> getComplexType() {
      return this.deprecated.getComplexType();
   }

   public T toPrimitive(Z complex, PersistentDataAdapterContext context) {
      return (T)this.deprecated.toPrimitive(complex, new DeprecatedItemAdapterContext(context));
   }

   public Z fromPrimitive(T primitive, PersistentDataAdapterContext context) {
      return (Z)this.deprecated.fromPrimitive(primitive, new DeprecatedItemAdapterContext(context));
   }
}
