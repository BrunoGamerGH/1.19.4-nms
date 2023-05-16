package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.resources.MinecraftKey;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;

public class CraftChestBoat extends CraftBoat implements ChestBoat {
   private final Inventory inventory;

   public CraftChestBoat(CraftServer server, net.minecraft.world.entity.vehicle.ChestBoat entity) {
      super(server, entity);
      this.inventory = new CraftInventory(entity);
   }

   public net.minecraft.world.entity.vehicle.ChestBoat getHandle() {
      return (net.minecraft.world.entity.vehicle.ChestBoat)this.entity;
   }

   @Override
   public String toString() {
      return "CraftChestBoat";
   }

   @Override
   public EntityType getType() {
      return EntityType.CHEST_BOAT;
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   public void setLootTable(LootTable table) {
      this.setLootTable(table, this.getSeed());
   }

   public LootTable getLootTable() {
      MinecraftKey nmsTable = this.getHandle().z();
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
      return this.getHandle().A();
   }

   private void setLootTable(LootTable table, long seed) {
      MinecraftKey newKey = table == null ? null : CraftNamespacedKey.toMinecraft(table.getKey());
      this.getHandle().a(newKey);
      this.getHandle().a(seed);
   }
}
