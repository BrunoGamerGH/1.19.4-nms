package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileBanEntry;
import net.minecraft.server.players.GameProfileBanList;
import net.minecraft.server.players.JsonListEntry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;

public class CraftProfileBanList implements BanList {
   private final GameProfileBanList list;

   public CraftProfileBanList(GameProfileBanList list) {
      this.list = list;
   }

   public BanEntry getBanEntry(String target) {
      Validate.notNull(target, "Target cannot be null");
      GameProfile profile = this.getProfile(target);
      if (profile == null) {
         return null;
      } else {
         GameProfileBanEntry entry = this.list.b(profile);
         return entry == null ? null : new CraftProfileBanEntry(profile, entry, this.list);
      }
   }

   public BanEntry addBan(String target, String reason, Date expires, String source) {
      Validate.notNull(target, "Ban target cannot be null");
      GameProfile profile = this.getProfile(target);
      if (profile == null) {
         return null;
      } else {
         GameProfileBanEntry entry = new GameProfileBanEntry(
            profile, new Date(), StringUtils.isBlank(source) ? null : source, expires, StringUtils.isBlank(reason) ? null : reason
         );
         this.list.a(entry);

         try {
            this.list.e();
         } catch (IOException var8) {
            Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-players.json, {0}", var8.getMessage());
         }

         return new CraftProfileBanEntry(profile, entry, this.list);
      }
   }

   public Set<BanEntry> getBanEntries() {
      Builder<BanEntry> builder = ImmutableSet.builder();

      for(JsonListEntry entry : this.list.getValues()) {
         GameProfile profile = (GameProfile)entry.g();
         builder.add(new CraftProfileBanEntry(profile, (GameProfileBanEntry)entry, this.list));
      }

      return builder.build();
   }

   public boolean isBanned(String target) {
      Validate.notNull(target, "Target cannot be null");
      GameProfile profile = this.getProfile(target);
      return profile == null ? false : this.list.a(profile);
   }

   public void pardon(String target) {
      Validate.notNull(target, "Target cannot be null");
      GameProfile profile = this.getProfile(target);
      this.list.c(profile);
   }

   private GameProfile getProfile(String target) {
      UUID uuid = null;

      try {
         uuid = UUID.fromString(target);
      } catch (IllegalArgumentException var4) {
      }

      return (GameProfile)(uuid != null ? MinecraftServer.getServer().ap().a(uuid) : MinecraftServer.getServer().ap().a(target)).orElse(null);
   }
}
