package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityHorseZombie;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.entity.Horse.Variant;

public class CraftZombieHorse extends CraftAbstractHorse implements ZombieHorse {
   public CraftZombieHorse(CraftServer server, EntityHorseZombie entity) {
      super(server, entity);
   }

   @Override
   public String toString() {
      return "CraftZombieHorse";
   }

   @Override
   public EntityType getType() {
      return EntityType.ZOMBIE_HORSE;
   }

   public Variant getVariant() {
      return Variant.UNDEAD_HORSE;
   }
}
