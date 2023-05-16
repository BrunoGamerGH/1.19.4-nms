package org.bukkit.craftbukkit.v1_19_R3;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import net.minecraft.server.players.GameProfileBanEntry;
import net.minecraft.server.players.GameProfileBanList;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;

public final class CraftProfileBanEntry implements BanEntry {
   private final GameProfileBanList list;
   private final GameProfile profile;
   private Date created;
   private String source;
   private Date expiration;
   private String reason;

   public CraftProfileBanEntry(GameProfile profile, GameProfileBanEntry entry, GameProfileBanList list) {
      this.list = list;
      this.profile = profile;
      this.created = entry.a() != null ? new Date(entry.a().getTime()) : null;
      this.source = entry.b();
      this.expiration = entry.c() != null ? new Date(entry.c().getTime()) : null;
      this.reason = entry.d();
   }

   public String getTarget() {
      return this.profile.getName();
   }

   public Date getCreated() {
      return this.created == null ? null : (Date)this.created.clone();
   }

   public void setCreated(Date created) {
      this.created = created;
   }

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public Date getExpiration() {
      return this.expiration == null ? null : (Date)this.expiration.clone();
   }

   public void setExpiration(Date expiration) {
      if (expiration != null && expiration.getTime() == new Date(0, 0, 0, 0, 0, 0).getTime()) {
         expiration = null;
      }

      this.expiration = expiration;
   }

   public String getReason() {
      return this.reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }

   public void save() {
      GameProfileBanEntry entry = new GameProfileBanEntry(this.profile, this.created, this.source, this.expiration, this.reason);
      this.list.a(entry);

      try {
         this.list.e();
      } catch (IOException var3) {
         Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-players.json, {0}", var3.getMessage());
      }
   }
}
