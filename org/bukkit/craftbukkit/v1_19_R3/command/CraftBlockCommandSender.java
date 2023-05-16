package org.bukkit.craftbukkit.v1_19_R3.command;

import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public class CraftBlockCommandSender extends ServerCommandSender implements BlockCommandSender {
   private final CommandListenerWrapper block;
   private final TileEntity tile;

   public CraftBlockCommandSender(CommandListenerWrapper commandBlockListenerAbstract, TileEntity tile) {
      this.block = commandBlockListenerAbstract;
      this.tile = tile;
   }

   public Block getBlock() {
      return CraftBlock.at(this.tile.k(), this.tile.p());
   }

   public void sendMessage(String message) {
      IChatBaseComponent[] var5;
      for(IChatBaseComponent component : var5 = CraftChatMessage.fromString(message)) {
         this.block.c.a(component);
      }
   }

   public void sendMessage(String... messages) {
      for(String message : messages) {
         this.sendMessage(message);
      }
   }

   public String getName() {
      return this.block.c();
   }

   public boolean isOp() {
      return true;
   }

   public void setOp(boolean value) {
      throw new UnsupportedOperationException("Cannot change operator status of a block");
   }

   public CommandListenerWrapper getWrapper() {
      return this.block;
   }
}
