package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.MobSpawnerData;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class CraftCreatureSpawner extends CraftBlockEntityState<TileEntityMobSpawner> implements CreatureSpawner {
   public CraftCreatureSpawner(World world, TileEntityMobSpawner tileEntity) {
      super(world, tileEntity);
   }

   public EntityType getSpawnedType() {
      MobSpawnerData spawnData = this.getSnapshot().d().f;
      if (spawnData == null) {
         return EntityType.PIG;
      } else {
         Optional<EntityTypes<?>> type = EntityTypes.a(spawnData.a());
         return type.isEmpty() ? EntityType.PIG : EntityType.fromName(EntityTypes.a(type.get()).a());
      }
   }

   public void setSpawnedType(EntityType entityType) {
      if (entityType != null && entityType.getName() != null) {
         RandomSource rand = this.isPlaced() ? this.getWorldHandle().r_() : RandomSource.a();
         this.getSnapshot().a(EntityTypes.a(entityType.getName()).get(), rand);
      } else {
         throw new IllegalArgumentException("Can't spawn EntityType " + entityType + " from mobspawners!");
      }
   }

   public String getCreatureTypeName() {
      MobSpawnerData spawnData = this.getSnapshot().d().f;
      if (spawnData == null) {
         return "";
      } else {
         Optional<EntityTypes<?>> type = EntityTypes.a(spawnData.a());
         return type.isEmpty() ? "" : EntityTypes.a(type.get()).a();
      }
   }

   public void setCreatureTypeByName(String creatureType) {
      EntityType type = EntityType.fromName(creatureType);
      if (type != null) {
         this.setSpawnedType(type);
      }
   }

   public int getDelay() {
      return this.getSnapshot().d().d;
   }

   public void setDelay(int delay) {
      this.getSnapshot().d().d = delay;
   }

   public int getMinSpawnDelay() {
      return this.getSnapshot().d().i;
   }

   public void setMinSpawnDelay(int spawnDelay) {
      Preconditions.checkArgument(spawnDelay <= this.getMaxSpawnDelay(), "Minimum Spawn Delay must be less than or equal to Maximum Spawn Delay");
      this.getSnapshot().d().i = spawnDelay;
   }

   public int getMaxSpawnDelay() {
      return this.getSnapshot().d().j;
   }

   public void setMaxSpawnDelay(int spawnDelay) {
      Preconditions.checkArgument(spawnDelay > 0, "Maximum Spawn Delay must be greater than 0.");
      Preconditions.checkArgument(spawnDelay >= this.getMinSpawnDelay(), "Maximum Spawn Delay must be greater than or equal to Minimum Spawn Delay");
      this.getSnapshot().d().j = spawnDelay;
   }

   public int getMaxNearbyEntities() {
      return this.getSnapshot().d().m;
   }

   public void setMaxNearbyEntities(int maxNearbyEntities) {
      this.getSnapshot().d().m = maxNearbyEntities;
   }

   public int getSpawnCount() {
      return this.getSnapshot().d().k;
   }

   public void setSpawnCount(int count) {
      this.getSnapshot().d().k = count;
   }

   public int getRequiredPlayerRange() {
      return this.getSnapshot().d().n;
   }

   public void setRequiredPlayerRange(int requiredPlayerRange) {
      this.getSnapshot().d().n = requiredPlayerRange;
   }

   public int getSpawnRange() {
      return this.getSnapshot().d().o;
   }

   public void setSpawnRange(int spawnRange) {
      this.getSnapshot().d().o = spawnRange;
   }
}
