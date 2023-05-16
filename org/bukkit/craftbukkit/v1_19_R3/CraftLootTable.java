package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameter;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.RandomSourceWrapper;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootContext.Builder;

public class CraftLootTable implements LootTable {
   private final net.minecraft.world.level.storage.loot.LootTable handle;
   private final NamespacedKey key;

   public CraftLootTable(NamespacedKey key, net.minecraft.world.level.storage.loot.LootTable handle) {
      this.handle = handle;
      this.key = key;
   }

   public net.minecraft.world.level.storage.loot.LootTable getHandle() {
      return this.handle;
   }

   public Collection<org.bukkit.inventory.ItemStack> populateLoot(Random random, LootContext context) {
      Preconditions.checkArgument(context != null, "LootContext cannot be null");
      LootTableInfo nmsContext = this.convertContext(context, random);
      List<ItemStack> nmsItems = this.handle.a(nmsContext);
      Collection<org.bukkit.inventory.ItemStack> bukkit = new ArrayList(nmsItems.size());

      for(ItemStack item : nmsItems) {
         if (!item.b()) {
            bukkit.add(CraftItemStack.asBukkitCopy(item));
         }
      }

      return bukkit;
   }

   public void fillInventory(Inventory inventory, Random random, LootContext context) {
      Preconditions.checkArgument(inventory != null, "Inventory cannot be null");
      Preconditions.checkArgument(context != null, "LootContext cannot be null");
      LootTableInfo nmsContext = this.convertContext(context, random);
      CraftInventory craftInventory = (CraftInventory)inventory;
      IInventory handle = craftInventory.getInventory();
      this.getHandle().fillInventory(handle, nmsContext, true);
   }

   public NamespacedKey getKey() {
      return this.key;
   }

   private LootTableInfo convertContext(LootContext context, Random random) {
      Preconditions.checkArgument(context != null, "LootContext cannot be null");
      Location loc = context.getLocation();
      Preconditions.checkArgument(loc.getWorld() != null, "LootContext.getLocation#getWorld cannot be null");
      WorldServer handle = ((CraftWorld)loc.getWorld()).getHandle();
      LootTableInfo.Builder builder = new LootTableInfo.Builder(handle);
      if (random != null) {
         builder = builder.a(new RandomSourceWrapper(random));
      }

      this.setMaybe(builder, LootContextParameters.f, new Vec3D(loc.getX(), loc.getY(), loc.getZ()));
      if (this.getHandle() != net.minecraft.world.level.storage.loot.LootTable.a) {
         if (context.getLootedEntity() != null) {
            Entity nmsLootedEntity = ((CraftEntity)context.getLootedEntity()).getHandle();
            this.setMaybe(builder, LootContextParameters.a, nmsLootedEntity);
            this.setMaybe(builder, LootContextParameters.c, handle.af().n());
            this.setMaybe(builder, LootContextParameters.f, nmsLootedEntity.de());
         }

         if (context.getKiller() != null) {
            EntityHuman nmsKiller = ((CraftHumanEntity)context.getKiller()).getHandle();
            this.setMaybe(builder, LootContextParameters.d, nmsKiller);
            this.setMaybe(builder, LootContextParameters.c, handle.af().a(nmsKiller));
            this.setMaybe(builder, LootContextParameters.b, nmsKiller);
            this.setMaybe(builder, LootContextParameters.i, nmsKiller.fg());
         }

         if (context.getLootingModifier() != -1) {
            this.setMaybe(builder, LootContextParameters.LOOTING_MOD, context.getLootingModifier());
         }
      }

      LootContextParameterSet.Builder nmsBuilder = new LootContextParameterSet.Builder();

      for(LootContextParameter<?> param : this.getHandle().a().a()) {
         nmsBuilder.a(param);
      }

      for(LootContextParameter<?> param : this.getHandle().a().b()) {
         if (!this.getHandle().a().a().contains(param)) {
            nmsBuilder.b(param);
         }
      }

      nmsBuilder.b(LootContextParameters.LOOTING_MOD);
      return builder.a(nmsBuilder.a());
   }

   private <T> void setMaybe(LootTableInfo.Builder builder, LootContextParameter<T> param, T value) {
      if (this.getHandle().a().a().contains(param) || this.getHandle().a().b().contains(param)) {
         builder.a(param, value);
      }
   }

   public static LootContext convertContext(LootTableInfo info) {
      Vec3D position = info.c(LootContextParameters.f);
      if (position == null) {
         position = info.c(LootContextParameters.a).de();
      }

      Location location = new Location(info.c().getWorld(), position.a(), position.b(), position.c());
      Builder contextBuilder = new Builder(location);
      if (info.a(LootContextParameters.d)) {
         CraftEntity killer = info.c(LootContextParameters.d).getBukkitEntity();
         if (killer instanceof CraftHumanEntity) {
            contextBuilder.killer((CraftHumanEntity)killer);
         }
      }

      if (info.a(LootContextParameters.a)) {
         contextBuilder.lootedEntity(info.c(LootContextParameters.a).getBukkitEntity());
      }

      if (info.a(LootContextParameters.LOOTING_MOD)) {
         contextBuilder.lootingModifier(info.c(LootContextParameters.LOOTING_MOD));
      }

      contextBuilder.luck(info.b());
      return contextBuilder.build();
   }

   @Override
   public String toString() {
      return this.getKey().toString();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof LootTable)) {
         return false;
      } else {
         LootTable table = (LootTable)obj;
         return table.getKey().equals(this.getKey());
      }
   }
}
