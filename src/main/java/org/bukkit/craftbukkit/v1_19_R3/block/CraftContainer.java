package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.ChestLock;
import net.minecraft.world.level.block.entity.TileEntityContainer;
import org.bukkit.World;
import org.bukkit.block.Container;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public abstract class CraftContainer<T extends TileEntityContainer> extends CraftBlockEntityState<T> implements Container {
   public CraftContainer(World world, T tileEntity) {
      super(world, tileEntity);
   }

   public boolean isLocked() {
      return !this.getSnapshot().c.c.isEmpty();
   }

   public String getLock() {
      return this.getSnapshot().c.c;
   }

   public void setLock(String key) {
      this.getSnapshot().c = key == null ? ChestLock.a : new ChestLock(key);
   }

   public String getCustomName() {
      T container = this.getSnapshot();
      return container.d != null ? CraftChatMessage.fromComponent(container.ab()) : null;
   }

   public void setCustomName(String name) {
      this.getSnapshot().a(CraftChatMessage.fromStringOrNull(name));
   }

   public void applyTo(T container) {
      super.applyTo(container);
      if (this.getSnapshot().d == null) {
         container.a(null);
      }
   }
}
