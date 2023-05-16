package org.bukkit.craftbukkit.v1_19_R3.advancement;

import org.bukkit.advancement.AdvancementDisplay;
import org.bukkit.advancement.AdvancementDisplayType;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.inventory.ItemStack;

public class CraftAdvancementDisplay implements AdvancementDisplay {
   private final net.minecraft.advancements.AdvancementDisplay handle;

   public CraftAdvancementDisplay(net.minecraft.advancements.AdvancementDisplay handle) {
      this.handle = handle;
   }

   public net.minecraft.advancements.AdvancementDisplay getHandle() {
      return this.handle;
   }

   public String getTitle() {
      return CraftChatMessage.fromComponent(this.handle.a());
   }

   public String getDescription() {
      return CraftChatMessage.fromComponent(this.handle.b());
   }

   public ItemStack getIcon() {
      return CraftItemStack.asBukkitCopy(this.handle.c());
   }

   public boolean shouldShowToast() {
      return this.handle.h();
   }

   public boolean shouldAnnounceChat() {
      return this.handle.i();
   }

   public boolean isHidden() {
      return this.handle.j();
   }

   public float getX() {
      return this.handle.f();
   }

   public float getY() {
      return this.handle.g();
   }

   public AdvancementDisplayType getType() {
      return AdvancementDisplayType.values()[this.handle.e().ordinal()];
   }
}
