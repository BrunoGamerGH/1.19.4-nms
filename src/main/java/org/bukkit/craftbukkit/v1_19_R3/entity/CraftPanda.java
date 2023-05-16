package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.animal.EntityPanda;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Panda.Gene;

public class CraftPanda extends CraftAnimals implements Panda {
   public CraftPanda(CraftServer server, EntityPanda entity) {
      super(server, entity);
   }

   public EntityPanda getHandle() {
      return (EntityPanda)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.PANDA;
   }

   @Override
   public String toString() {
      return "CraftPanda";
   }

   public Gene getMainGene() {
      return fromNms(this.getHandle().ga());
   }

   public void setMainGene(Gene gene) {
      this.getHandle().a(toNms(gene));
   }

   public Gene getHiddenGene() {
      return fromNms(this.getHandle().gb());
   }

   public void setHiddenGene(Gene gene) {
      this.getHandle().b(toNms(gene));
   }

   public boolean isRolling() {
      return this.getHandle().gc();
   }

   public void setRolling(boolean flag) {
      this.getHandle().A(flag);
   }

   public boolean isSneezing() {
      return this.getHandle().r();
   }

   public void setSneezing(boolean flag) {
      this.getHandle().z(flag);
   }

   public boolean isSitting() {
      return this.getHandle().w();
   }

   public void setSitting(boolean flag) {
      this.getHandle().w(flag);
   }

   public boolean isOnBack() {
      return this.getHandle().fS();
   }

   public void setOnBack(boolean flag) {
      this.getHandle().x(flag);
   }

   public boolean isEating() {
      return this.getHandle().fY();
   }

   public void setEating(boolean flag) {
      this.getHandle().y(flag);
   }

   public boolean isScared() {
      return this.getHandle().gk();
   }

   public int getUnhappyTicks() {
      return this.getHandle().q();
   }

   public static Gene fromNms(EntityPanda.Gene gene) {
      Preconditions.checkArgument(gene != null, "Gene may not be null");
      return Gene.values()[gene.ordinal()];
   }

   public static EntityPanda.Gene toNms(Gene gene) {
      Preconditions.checkArgument(gene != null, "Gene may not be null");
      return EntityPanda.Gene.values()[gene.ordinal()];
   }
}
