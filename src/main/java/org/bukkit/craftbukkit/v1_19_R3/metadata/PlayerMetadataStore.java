package org.bukkit.craftbukkit.v1_19_R3.metadata;

import org.bukkit.OfflinePlayer;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataStoreBase;

public class PlayerMetadataStore extends MetadataStoreBase<OfflinePlayer> implements MetadataStore<OfflinePlayer> {
   protected String disambiguate(OfflinePlayer player, String metadataKey) {
      return player.getUniqueId() + ":" + metadataKey;
   }
}
