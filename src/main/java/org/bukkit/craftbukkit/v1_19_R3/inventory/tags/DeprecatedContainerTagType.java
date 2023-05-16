package org.bukkit.craftbukkit.v1_19_R3.inventory.tags;

import org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class DeprecatedContainerTagType<Z> implements PersistentDataType<PersistentDataContainer, Z> {
   private final ItemTagType<CustomItemTagContainer, Z> deprecated;

   DeprecatedContainerTagType(ItemTagType<CustomItemTagContainer, Z> deprecated) {
      this.deprecated = deprecated;
   }

   public Class<PersistentDataContainer> getPrimitiveType() {
      return PersistentDataContainer.class;
   }

   public Class<Z> getComplexType() {
      return this.deprecated.getComplexType();
   }

   public PersistentDataContainer toPrimitive(Z complex, PersistentDataAdapterContext context) {
      CustomItemTagContainer deprecated = (CustomItemTagContainer)this.deprecated.toPrimitive(complex, new DeprecatedItemAdapterContext(context));
      Validate.isInstanceOf(
         DeprecatedCustomTagContainer.class,
         deprecated,
         "Could not wrap deprecated API due to foreign CustomItemTagContainer implementation %s",
         new Object[]{deprecated.getClass().getSimpleName()}
      );
      DeprecatedCustomTagContainer tagContainer = (DeprecatedCustomTagContainer)deprecated;
      PersistentDataContainer wrapped = tagContainer.getWrapped();
      Validate.isInstanceOf(
         CraftPersistentDataContainer.class,
         wrapped,
         "Could not wrap deprecated API due to wrong deprecation wrapper %s",
         new Object[]{deprecated.getClass().getSimpleName()}
      );
      CraftPersistentDataContainer craftTagContainer = (CraftPersistentDataContainer)wrapped;
      return new CraftPersistentDataContainer(craftTagContainer.getRaw(), craftTagContainer.getDataTagTypeRegistry());
   }

   public Z fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
      Validate.isInstanceOf(
         CraftPersistentDataContainer.class,
         primitive,
         "Could not wrap deprecated API due to foreign PersistentMetadataContainer implementation %s",
         new Object[]{primitive.getClass().getSimpleName()}
      );
      return (Z)this.deprecated.fromPrimitive(new DeprecatedCustomTagContainer(primitive), new DeprecatedItemAdapterContext(context));
   }
}
