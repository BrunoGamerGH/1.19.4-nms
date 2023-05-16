package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityEnchantTable;
import org.bukkit.World;
import org.bukkit.block.EnchantingTable;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public class CraftEnchantingTable extends CraftBlockEntityState<TileEntityEnchantTable> implements EnchantingTable {
   public CraftEnchantingTable(World world, TileEntityEnchantTable tileEntity) {
      super(world, tileEntity);
   }

   public String getCustomName() {
      TileEntityEnchantTable enchant = this.getSnapshot();
      return enchant.aa() ? CraftChatMessage.fromComponent(enchant.ab()) : null;
   }

   public void setCustomName(String name) {
      this.getSnapshot().a(CraftChatMessage.fromStringOrNull(name));
   }

   public void applyTo(TileEntityEnchantTable enchantingTable) {
      super.applyTo(enchantingTable);
      if (!this.getSnapshot().aa()) {
         enchantingTable.a(null);
      }
   }
}
