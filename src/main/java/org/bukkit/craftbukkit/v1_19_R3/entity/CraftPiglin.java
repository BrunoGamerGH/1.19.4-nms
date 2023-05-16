package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Piglin;
import org.bukkit.inventory.Inventory;

public class CraftPiglin extends CraftPiglinAbstract implements Piglin {
   public CraftPiglin(CraftServer server, EntityPiglin entity) {
      super(server, entity);
   }

   public boolean isAbleToHunt() {
      return this.getHandle().cj;
   }

   public void setIsAbleToHunt(boolean flag) {
      this.getHandle().cj = flag;
   }

   public boolean addBarterMaterial(Material material) {
      Preconditions.checkArgument(material != null, "material cannot be null");
      Item item = CraftMagicNumbers.getItem(material);
      return this.getHandle().allowedBarterItems.add(item);
   }

   public boolean removeBarterMaterial(Material material) {
      Preconditions.checkArgument(material != null, "material cannot be null");
      Item item = CraftMagicNumbers.getItem(material);
      return this.getHandle().allowedBarterItems.remove(item);
   }

   public boolean addMaterialOfInterest(Material material) {
      Preconditions.checkArgument(material != null, "material cannot be null");
      Item item = CraftMagicNumbers.getItem(material);
      return this.getHandle().interestItems.add(item);
   }

   public boolean removeMaterialOfInterest(Material material) {
      Preconditions.checkArgument(material != null, "material cannot be null");
      Item item = CraftMagicNumbers.getItem(material);
      return this.getHandle().interestItems.remove(item);
   }

   public Set<Material> getInterestList() {
      return Collections.unmodifiableSet(this.getHandle().interestItems.stream().map(CraftMagicNumbers::getMaterial).collect(Collectors.toSet()));
   }

   public Set<Material> getBarterList() {
      return Collections.unmodifiableSet(this.getHandle().allowedBarterItems.stream().map(CraftMagicNumbers::getMaterial).collect(Collectors.toSet()));
   }

   public Inventory getInventory() {
      return new CraftInventory(this.getHandle().ci);
   }

   public EntityPiglin getHandle() {
      return (EntityPiglin)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.PIGLIN;
   }

   @Override
   public String toString() {
      return "CraftPiglin";
   }
}
