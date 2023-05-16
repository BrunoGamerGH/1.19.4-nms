package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Spellcaster;
import org.bukkit.entity.Spellcaster.Spell;

public class CraftSpellcaster extends CraftIllager implements Spellcaster {
   public CraftSpellcaster(CraftServer server, EntityIllagerWizard entity) {
      super(server, entity);
   }

   public EntityIllagerWizard getHandle() {
      return (EntityIllagerWizard)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftSpellcaster";
   }

   public Spell getSpell() {
      return toBukkitSpell(this.getHandle().gd());
   }

   public void setSpell(Spell spell) {
      Preconditions.checkArgument(spell != null, "Use Spell.NONE");
      this.getHandle().a(toNMSSpell(spell));
   }

   public static Spell toBukkitSpell(EntityIllagerWizard.Spell spell) {
      return Spell.valueOf(spell.name());
   }

   public static EntityIllagerWizard.Spell toNMSSpell(Spell spell) {
      return EntityIllagerWizard.Spell.a(spell.ordinal());
   }
}
