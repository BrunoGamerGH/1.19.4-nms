package net.minecraft.world.item;

import javax.annotation.Nullable;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;

public class ItemWorldMapBase extends Item {
   public ItemWorldMapBase(Item.Info var0) {
      super(var0);
   }

   @Override
   public boolean ac_() {
      return true;
   }

   @Nullable
   public Packet<?> a(ItemStack var0, World var1, EntityHuman var2) {
      return null;
   }
}
