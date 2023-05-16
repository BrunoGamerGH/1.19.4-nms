package org.bukkit.craftbukkit.v1_19_R3.boss;

import net.minecraft.server.bossevents.BossBattleCustom;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public class CraftKeyedBossbar extends CraftBossBar implements KeyedBossBar {
   public CraftKeyedBossbar(BossBattleCustom bossBattleCustom) {
      super(bossBattleCustom);
   }

   public NamespacedKey getKey() {
      return CraftNamespacedKey.fromMinecraft(this.getHandle().a());
   }

   public BossBattleCustom getHandle() {
      return (BossBattleCustom)super.getHandle();
   }
}
