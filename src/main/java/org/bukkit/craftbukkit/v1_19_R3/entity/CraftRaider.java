package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.raid.EntityRaider;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.entity.Raider;

public abstract class CraftRaider extends CraftMonster implements Raider {
   public CraftRaider(CraftServer server, EntityRaider entity) {
      super(server, entity);
   }

   public EntityRaider getHandle() {
      return (EntityRaider)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftRaider";
   }

   public Block getPatrolTarget() {
      return this.getHandle().fU() == null ? null : CraftBlock.at(this.getHandle().H, this.getHandle().fU());
   }

   public void setPatrolTarget(Block block) {
      if (block == null) {
         this.getHandle().g(null);
      } else {
         Preconditions.checkArgument(block.getWorld().equals(this.getWorld()), "Block must be in same world");
         this.getHandle().g(new BlockPosition(block.getX(), block.getY(), block.getZ()));
      }
   }

   public boolean isPatrolLeader() {
      return this.getHandle().fW();
   }

   public void setPatrolLeader(boolean leader) {
      this.getHandle().w(leader);
   }

   public boolean isCanJoinRaid() {
      return this.getHandle().gf();
   }

   public void setCanJoinRaid(boolean join) {
      this.getHandle().z(join);
   }

   public Sound getCelebrationSound() {
      return CraftSound.getBukkit(this.getHandle().X_());
   }
}
