package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.entity.SuspiciousSandBlockEntity;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.SuspiciousSand;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootTable;

public class CraftSuspiciousSand extends CraftBlockEntityState<SuspiciousSandBlockEntity> implements SuspiciousSand {
   public CraftSuspiciousSand(World world, SuspiciousSandBlockEntity tileEntity) {
      super(world, tileEntity);
   }

   public ItemStack getItem() {
      return CraftItemStack.asBukkitCopy(this.getSnapshot().g());
   }

   public void setItem(ItemStack item) {
      this.getSnapshot().l = CraftItemStack.asNMSCopy(item);
   }

   public void applyTo(SuspiciousSandBlockEntity lootable) {
      super.applyTo(lootable);
      if (this.getSnapshot().n == null) {
         lootable.a(null, 0L);
      }
   }

   public LootTable getLootTable() {
      if (this.getSnapshot().n == null) {
         return null;
      } else {
         MinecraftKey key = this.getSnapshot().n;
         return Bukkit.getLootTable(CraftNamespacedKey.fromMinecraft(key));
      }
   }

   public void setLootTable(LootTable table) {
      this.setLootTable(table, this.getSeed());
   }

   public long getSeed() {
      return this.getSnapshot().r;
   }

   public void setSeed(long seed) {
      this.setLootTable(this.getLootTable(), seed);
   }

   private void setLootTable(LootTable table, long seed) {
      MinecraftKey key = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
      this.getSnapshot().a(key, seed);
   }
}
