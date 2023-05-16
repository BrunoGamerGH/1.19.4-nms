package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.entity.vehicle.EntityMinecartContainer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

public abstract class CraftMinecartContainer extends CraftMinecart implements Lootable {
   public CraftMinecartContainer(CraftServer server, EntityMinecartAbstract entity) {
      super(server, entity);
   }

   public EntityMinecartContainer getHandle() {
      return (EntityMinecartContainer)this.entity;
   }

   public void setLootTable(LootTable table) {
      this.setLootTable(table, this.getSeed());
   }

   public LootTable getLootTable() {
      MinecraftKey nmsTable = this.getHandle().d;
      if (nmsTable == null) {
         return null;
      } else {
         NamespacedKey key = CraftNamespacedKey.fromMinecraft(nmsTable);
         return Bukkit.getLootTable(key);
      }
   }

   public void setSeed(long seed) {
      this.setLootTable(this.getLootTable(), seed);
   }

   public long getSeed() {
      return this.getHandle().e;
   }

   private void setLootTable(LootTable table, long seed) {
      MinecraftKey newKey = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
      this.getHandle().a(newKey, seed);
   }
}
