package org.bukkit.craftbukkit.v1_19_R3;

import com.mojang.authlib.GameProfile;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.players.WhiteListEntry;
import net.minecraft.stats.ServerStatisticManager;
import net.minecraft.world.level.storage.WorldNBTStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.BanList.Type;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.craftbukkit.v1_19_R3.entity.memory.CraftMemoryMapper;
import org.bukkit.craftbukkit.v1_19_R3.profile.CraftPlayerProfile;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;

@SerializableAs("Player")
public class CraftOfflinePlayer implements OfflinePlayer, ConfigurationSerializable {
   private final GameProfile profile;
   private final CraftServer server;
   private final WorldNBTStorage storage;

   protected CraftOfflinePlayer(CraftServer server, GameProfile profile) {
      this.server = server;
      this.profile = profile;
      this.storage = server.console.i;
   }

   public boolean isOnline() {
      return this.getPlayer() != null;
   }

   public String getName() {
      Player player = this.getPlayer();
      if (player != null) {
         return player.getName();
      } else if (this.profile.getName() != null) {
         return this.profile.getName();
      } else {
         NBTTagCompound data = this.getBukkitData();
         return data != null && data.e("lastKnownName") ? data.l("lastKnownName") : null;
      }
   }

   public UUID getUniqueId() {
      return this.profile.getId();
   }

   public PlayerProfile getPlayerProfile() {
      return new CraftPlayerProfile(this.profile);
   }

   public Server getServer() {
      return this.server;
   }

   public boolean isOp() {
      return this.server.getHandle().f(this.profile);
   }

   public void setOp(boolean value) {
      if (value != this.isOp()) {
         if (value) {
            this.server.getHandle().a(this.profile);
         } else {
            this.server.getHandle().b(this.profile);
         }
      }
   }

   public boolean isBanned() {
      return this.getName() == null ? false : this.server.getBanList(Type.NAME).isBanned(this.getName());
   }

   public void setBanned(boolean value) {
      if (this.getName() != null) {
         if (value) {
            this.server.getBanList(Type.NAME).addBan(this.getName(), null, null, null);
         } else {
            this.server.getBanList(Type.NAME).pardon(this.getName());
         }
      }
   }

   public boolean isWhitelisted() {
      return this.server.getHandle().i().a(this.profile);
   }

   public void setWhitelisted(boolean value) {
      if (value) {
         this.server.getHandle().i().a(new WhiteListEntry(this.profile));
      } else {
         this.server.getHandle().i().c(this.profile);
      }
   }

   public Map<String, Object> serialize() {
      Map<String, Object> result = new LinkedHashMap<>();
      result.put("UUID", this.profile.getId().toString());
      return result;
   }

   public static OfflinePlayer deserialize(Map<String, Object> args) {
      return args.get("name") != null
         ? Bukkit.getServer().getOfflinePlayer((String)args.get("name"))
         : Bukkit.getServer().getOfflinePlayer(UUID.fromString((String)args.get("UUID")));
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + "[UUID=" + this.profile.getId() + "]";
   }

   public Player getPlayer() {
      return this.server.getPlayer(this.getUniqueId());
   }

   @Override
   public boolean equals(Object obj) {
      if (obj != null && obj instanceof OfflinePlayer other) {
         return this.getUniqueId() != null && other.getUniqueId() != null ? this.getUniqueId().equals(other.getUniqueId()) : false;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int hash = 5;
      return 97 * hash + (this.getUniqueId() != null ? this.getUniqueId().hashCode() : 0);
   }

   private NBTTagCompound getData() {
      return this.storage.getPlayerData(this.getUniqueId().toString());
   }

   private NBTTagCompound getBukkitData() {
      NBTTagCompound result = this.getData();
      if (result != null) {
         if (!result.e("bukkit")) {
            result.a("bukkit", new NBTTagCompound());
         }

         result = result.p("bukkit");
      }

      return result;
   }

   private File getDataFile() {
      return new File(this.storage.getPlayerDir(), this.getUniqueId() + ".dat");
   }

   public long getFirstPlayed() {
      Player player = this.getPlayer();
      if (player != null) {
         return player.getFirstPlayed();
      } else {
         NBTTagCompound data = this.getBukkitData();
         if (data != null) {
            if (data.e("firstPlayed")) {
               return data.i("firstPlayed");
            } else {
               File file = this.getDataFile();
               return file.lastModified();
            }
         } else {
            return 0L;
         }
      }
   }

   public long getLastPlayed() {
      Player player = this.getPlayer();
      if (player != null) {
         return player.getLastPlayed();
      } else {
         NBTTagCompound data = this.getBukkitData();
         if (data != null) {
            if (data.e("lastPlayed")) {
               return data.i("lastPlayed");
            } else {
               File file = this.getDataFile();
               return file.lastModified();
            }
         } else {
            return 0L;
         }
      }
   }

   public boolean hasPlayedBefore() {
      return this.getData() != null;
   }

   public Location getLastDeathLocation() {
      return this.getData().b("LastDeathLocation", 10)
         ? (Location)GlobalPos.a.parse(DynamicOpsNBT.a, this.getData().c("LastDeathLocation")).result().map(CraftMemoryMapper::fromNms).orElse(null)
         : null;
   }

   public Location getBedSpawnLocation() {
      NBTTagCompound data = this.getData();
      if (data == null) {
         return null;
      } else if (data.e("SpawnX") && data.e("SpawnY") && data.e("SpawnZ")) {
         String spawnWorld = data.l("SpawnWorld");
         if (spawnWorld.equals("")) {
            spawnWorld = ((World)this.server.getWorlds().get(0)).getName();
         }

         return new Location(this.server.getWorld(spawnWorld), (double)data.h("SpawnX"), (double)data.h("SpawnY"), (double)data.h("SpawnZ"));
      } else {
         return null;
      }
   }

   public void setMetadata(String metadataKey, MetadataValue metadataValue) {
      this.server.getPlayerMetadata().setMetadata(this, metadataKey, metadataValue);
   }

   public List<MetadataValue> getMetadata(String metadataKey) {
      return this.server.getPlayerMetadata().getMetadata(this, metadataKey);
   }

   public boolean hasMetadata(String metadataKey) {
      return this.server.getPlayerMetadata().hasMetadata(this, metadataKey);
   }

   public void removeMetadata(String metadataKey, Plugin plugin) {
      this.server.getPlayerMetadata().removeMetadata(this, metadataKey, plugin);
   }

   private ServerStatisticManager getStatisticManager() {
      return this.server.getHandle().getPlayerStats(this.getUniqueId(), this.getName());
   }

   public void incrementStatistic(Statistic statistic) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic);
         manager.a();
      }
   }

   public int getStatistic(Statistic statistic) {
      return this.isOnline() ? this.getPlayer().getStatistic(statistic) : CraftStatistic.getStatistic(this.getStatisticManager(), statistic);
   }

   public void incrementStatistic(Statistic statistic, int amount) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic, amount);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic, int amount) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic, amount);
         manager.a();
      }
   }

   public void setStatistic(Statistic statistic, int newValue) {
      if (this.isOnline()) {
         this.getPlayer().setStatistic(statistic, newValue);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.setStatistic(manager, statistic, newValue);
         manager.a();
      }
   }

   public void incrementStatistic(Statistic statistic, Material material) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic, material);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic, material);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic, Material material) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic, material);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic, material);
         manager.a();
      }
   }

   public int getStatistic(Statistic statistic, Material material) {
      return this.isOnline()
         ? this.getPlayer().getStatistic(statistic, material)
         : CraftStatistic.getStatistic(this.getStatisticManager(), statistic, material);
   }

   public void incrementStatistic(Statistic statistic, Material material, int amount) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic, material, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic, material, amount);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic, Material material, int amount) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic, material, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic, material, amount);
         manager.a();
      }
   }

   public void setStatistic(Statistic statistic, Material material, int newValue) {
      if (this.isOnline()) {
         this.getPlayer().setStatistic(statistic, material, newValue);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.setStatistic(manager, statistic, material, newValue);
         manager.a();
      }
   }

   public void incrementStatistic(Statistic statistic, EntityType entityType) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic, entityType);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic, entityType);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic, EntityType entityType) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic, entityType);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic, entityType);
         manager.a();
      }
   }

   public int getStatistic(Statistic statistic, EntityType entityType) {
      return this.isOnline()
         ? this.getPlayer().getStatistic(statistic, entityType)
         : CraftStatistic.getStatistic(this.getStatisticManager(), statistic, entityType);
   }

   public void incrementStatistic(Statistic statistic, EntityType entityType, int amount) {
      if (this.isOnline()) {
         this.getPlayer().incrementStatistic(statistic, entityType, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.incrementStatistic(manager, statistic, entityType, amount);
         manager.a();
      }
   }

   public void decrementStatistic(Statistic statistic, EntityType entityType, int amount) {
      if (this.isOnline()) {
         this.getPlayer().decrementStatistic(statistic, entityType, amount);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.decrementStatistic(manager, statistic, entityType, amount);
         manager.a();
      }
   }

   public void setStatistic(Statistic statistic, EntityType entityType, int newValue) {
      if (this.isOnline()) {
         this.getPlayer().setStatistic(statistic, entityType, newValue);
      } else {
         ServerStatisticManager manager = this.getStatisticManager();
         CraftStatistic.setStatistic(manager, statistic, entityType, newValue);
         manager.a();
      }
   }
}
