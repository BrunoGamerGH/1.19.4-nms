package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityCommand;
import org.bukkit.World;
import org.bukkit.block.CommandBlock;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public class CraftCommandBlock extends CraftBlockEntityState<TileEntityCommand> implements CommandBlock {
   public CraftCommandBlock(World world, TileEntityCommand tileEntity) {
      super(world, tileEntity);
   }

   public String getCommand() {
      return this.getSnapshot().c().l();
   }

   public void setCommand(String command) {
      this.getSnapshot().c().a(command != null ? command : "");
   }

   public String getName() {
      return CraftChatMessage.fromComponent(this.getSnapshot().c().m());
   }

   public void setName(String name) {
      this.getSnapshot().c().b(CraftChatMessage.fromStringOrNull(name != null ? name : "@"));
   }
}
