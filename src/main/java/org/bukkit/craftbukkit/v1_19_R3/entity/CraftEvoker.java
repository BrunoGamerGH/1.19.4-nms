package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Evoker;
import org.bukkit.entity.Evoker.Spell;

public class CraftEvoker extends CraftSpellcaster implements Evoker {
   public CraftEvoker(CraftServer server, EntityEvoker entity) {
      super(server, entity);
   }

   public EntityEvoker getHandle() {
      return (EntityEvoker)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftEvoker";
   }

   @Override
   public EntityType getType() {
      return EntityType.EVOKER;
   }

   public Spell getCurrentSpell() {
      return Spell.values()[this.getHandle().gd().ordinal()];
   }

   public void setCurrentSpell(Spell spell) {
      this.getHandle().a(spell == null ? EntityIllagerWizard.Spell.a : EntityIllagerWizard.Spell.a(spell.ordinal()));
   }
}
