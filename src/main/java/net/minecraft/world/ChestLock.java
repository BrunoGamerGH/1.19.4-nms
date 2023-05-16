package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

@Immutable
public class ChestLock {
   public static final ChestLock a = new ChestLock("");
   public static final String b = "Lock";
   public final String c;

   public ChestLock(String s) {
      this.c = s;
   }

   public boolean a(ItemStack itemstack) {
      if (this.c.isEmpty()) {
         return true;
      } else if (!itemstack.b() && itemstack.z()) {
         return this.c.indexOf(167) == -1 ? this.c.equals(itemstack.x().getString()) : this.c.equals(CraftChatMessage.fromComponent(itemstack.x()));
      } else {
         return false;
      }
   }

   public void a(NBTTagCompound nbttagcompound) {
      if (!this.c.isEmpty()) {
         nbttagcompound.a("Lock", this.c);
      }
   }

   public static ChestLock b(NBTTagCompound nbttagcompound) {
      return nbttagcompound.b("Lock", 8) ? new ChestLock(nbttagcompound.l("Lock")) : a;
   }
}
