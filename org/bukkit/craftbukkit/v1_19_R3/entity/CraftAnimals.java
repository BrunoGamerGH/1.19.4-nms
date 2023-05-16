package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import java.util.UUID;
import net.minecraft.world.entity.animal.EntityAnimal;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Animals;
import org.bukkit.inventory.ItemStack;

public class CraftAnimals extends CraftAgeable implements Animals {
   public CraftAnimals(CraftServer server, EntityAnimal entity) {
      super(server, entity);
   }

   public EntityAnimal getHandle() {
      return (EntityAnimal)this.entity;
   }

   @Override
   public String toString() {
      return "CraftAnimals";
   }

   public UUID getBreedCause() {
      return this.getHandle().bT;
   }

   public void setBreedCause(UUID uuid) {
      this.getHandle().bT = uuid;
   }

   public boolean isLoveMode() {
      return this.getHandle().fW();
   }

   public void setLoveModeTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "Love mode ticks must be positive or 0");
      this.getHandle().r(ticks);
   }

   public int getLoveModeTicks() {
      return this.getHandle().bS;
   }

   public boolean isBreedItem(ItemStack itemStack) {
      return this.getHandle().m(CraftItemStack.asNMSCopy(itemStack));
   }

   public boolean isBreedItem(Material material) {
      return this.isBreedItem(new ItemStack(material));
   }
}
