package net.minecraft.world;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public interface IInventory extends Clearable {
   int k_ = 64;
   int l_ = 8;
   int MAX_STACK = 64;

   int b();

   boolean aa_();

   ItemStack a(int var1);

   ItemStack a(int var1, int var2);

   ItemStack b(int var1);

   void a(int var1, ItemStack var2);

   int ab_();

   void e();

   boolean a(EntityHuman var1);

   default void d_(EntityHuman entityhuman) {
   }

   default void c(EntityHuman entityhuman) {
   }

   default boolean b(int i, ItemStack itemstack) {
      return true;
   }

   default boolean a(IInventory iinventory, int i, ItemStack itemstack) {
      return true;
   }

   default int a_(Item item) {
      int i = 0;

      for(int j = 0; j < this.b(); ++j) {
         ItemStack itemstack = this.a(j);
         if (itemstack.c().equals(item)) {
            i += itemstack.K();
         }
      }

      return i;
   }

   default boolean a(Set<Item> set) {
      return this.a_(itemstack -> !itemstack.b() && set.contains(itemstack.c()));
   }

   default boolean a_(Predicate<ItemStack> predicate) {
      for(int i = 0; i < this.b(); ++i) {
         ItemStack itemstack = this.a(i);
         if (predicate.test(itemstack)) {
            return true;
         }
      }

      return false;
   }

   static boolean a(TileEntity tileentity, EntityHuman entityhuman) {
      return a(tileentity, entityhuman, 8);
   }

   static boolean a(TileEntity tileentity, EntityHuman entityhuman, int i) {
      World world = tileentity.k();
      BlockPosition blockposition = tileentity.p();
      return world == null
         ? false
         : (
            world.c_(blockposition) != tileentity
               ? false
               : entityhuman.i((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.5, (double)blockposition.w() + 0.5) <= (double)(i * i)
         );
   }

   List<ItemStack> getContents();

   void onOpen(CraftHumanEntity var1);

   void onClose(CraftHumanEntity var1);

   List<HumanEntity> getViewers();

   InventoryHolder getOwner();

   void setMaxStackSize(int var1);

   Location getLocation();

   default IRecipe getCurrentRecipe() {
      return null;
   }

   default void setCurrentRecipe(IRecipe recipe) {
   }
}
