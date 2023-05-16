package org.bukkit.craftbukkit.v1_19_R3.advancement;

import java.util.Collection;
import java.util.Collections;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;

public class CraftAdvancement implements Advancement {
   private final net.minecraft.advancements.Advancement handle;

   public CraftAdvancement(net.minecraft.advancements.Advancement handle) {
      this.handle = handle;
   }

   public net.minecraft.advancements.Advancement getHandle() {
      return this.handle;
   }

   public NamespacedKey getKey() {
      return CraftNamespacedKey.fromMinecraft(this.handle.i());
   }

   public Collection<String> getCriteria() {
      return Collections.unmodifiableCollection(this.handle.g().keySet());
   }

   public AdvancementDisplay getDisplay() {
      return this.handle.d() == null ? null : new CraftAdvancementDisplay(this.handle.d());
   }
}
