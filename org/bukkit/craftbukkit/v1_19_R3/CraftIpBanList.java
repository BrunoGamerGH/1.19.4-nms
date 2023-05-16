package org.bukkit.craftbukkit.v1_19_R3;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.server.players.IpBanEntry;
import net.minecraft.server.players.IpBanList;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;

public class CraftIpBanList implements BanList {
   private final IpBanList list;

   public CraftIpBanList(IpBanList list) {
      this.list = list;
   }

   public BanEntry getBanEntry(String target) {
      Validate.notNull(target, "Target cannot be null");
      IpBanEntry entry = this.list.b(target);
      return entry == null ? null : new CraftIpBanEntry(target, entry, this.list);
   }

   public BanEntry addBan(String target, String reason, Date expires, String source) {
      Validate.notNull(target, "Ban target cannot be null");
      IpBanEntry entry = new IpBanEntry(target, new Date(), StringUtils.isBlank(source) ? null : source, expires, StringUtils.isBlank(reason) ? null : reason);
      this.list.a(entry);

      try {
         this.list.e();
      } catch (IOException var7) {
         Bukkit.getLogger().log(Level.SEVERE, "Failed to save banned-ips.json, {0}", var7.getMessage());
      }

      return new CraftIpBanEntry(target, entry, this.list);
   }

   public Set<BanEntry> getBanEntries() {
      Builder<BanEntry> builder = ImmutableSet.builder();

      String[] var5;
      for(String target : var5 = this.list.a()) {
         builder.add(new CraftIpBanEntry(target, this.list.b(target), this.list));
      }

      return builder.build();
   }

   public boolean isBanned(String target) {
      Validate.notNull(target, "Target cannot be null");
      return this.list.a(InetSocketAddress.createUnresolved(target, 0));
   }

   public void pardon(String target) {
      Validate.notNull(target, "Target cannot be null");
      this.list.c(target);
   }
}
