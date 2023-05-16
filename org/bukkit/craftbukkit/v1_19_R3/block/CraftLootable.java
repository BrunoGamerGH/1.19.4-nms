package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import org.bukkit.Bukkit;
import org.bukkit.Nameable;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public abstract class CraftLootable<T extends TileEntityLootable> extends CraftContainer<T> implements Nameable, Lootable {
   public CraftLootable(World world, T tileEntity) {
      super(world, tileEntity);
   }

   public void applyTo(T lootable) {
      super.applyTo(lootable);
      if (this.getSnapshot().h == null) {
         lootable.a(null, 0L);
      }
   }

   public LootTable getLootTable() {
      if (this.getSnapshot().h == null) {
         return null;
      } else {
         MinecraftKey key = this.getSnapshot().h;
         return Bukkit.getLootTable(CraftNamespacedKey.fromMinecraft(key));
      }
   }

   public void setLootTable(LootTable table) {
      this.setLootTable(table, this.getSeed());
   }

   public long getSeed() {
      return this.getSnapshot().i;
   }

   public void setSeed(long seed) {
      this.setLootTable(this.getLootTable(), seed);
   }

   private void setLootTable(LootTable table, long seed) {
      MinecraftKey key = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
      this.getSnapshot().a(key, seed);
   }
}
