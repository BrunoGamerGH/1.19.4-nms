package net.minecraft.world.inventory;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventorySmithing;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

@Deprecated(
   forRemoval = true
)
public class LegacySmithingMenu extends ContainerAnvilAbstract {
   private final World n;
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 2;
   private static final int s = 27;
   private static final int t = 76;
   private static final int u = 134;
   private static final int v = 47;
   @Nullable
   private LegacyUpgradeRecipe w;
   private final List<LegacyUpgradeRecipe> x;
   private CraftInventoryView bukkitEntity;

   public LegacySmithingMenu(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public LegacySmithingMenu(int i, PlayerInventory playerinventory, ContainerAccess containeraccess) {
      super(Containers.u, i, playerinventory, containeraccess);
      this.n = playerinventory.m.H;
      this.x = this.n
         .q()
         .<IInventory, SmithingRecipe>a(Recipes.g)
         .stream()
         .filter(smithingrecipe -> smithingrecipe instanceof LegacyUpgradeRecipe)
         .map(smithingrecipe -> (LegacyUpgradeRecipe)smithingrecipe)
         .toList();
   }

   @Override
   protected ItemCombinerMenuSlotDefinition l() {
      return ItemCombinerMenuSlotDefinition.a().a(0, 27, 47, itemstack -> true).a(1, 76, 47, itemstack -> true).a(2, 134, 47).a();
   }

   @Override
   protected boolean a(IBlockData iblockdata) {
      return iblockdata.a(Blocks.nX);
   }

   @Override
   protected boolean a(EntityHuman entityhuman, boolean flag) {
      return this.w != null && this.w.a(this.q, this.n);
   }

   @Override
   protected void a(EntityHuman entityhuman, ItemStack itemstack) {
      itemstack.a(entityhuman.H, entityhuman, itemstack.K());
      this.r.b(entityhuman);
      this.e(0);
      this.e(1);
      this.o.a((world, blockposition) -> world.c(1044, blockposition, 0));
   }

   private void e(int i) {
      ItemStack itemstack = this.q.a(i);
      itemstack.h(1);
      this.q.a(i, itemstack);
   }

   @Override
   public void m() {
      List<LegacyUpgradeRecipe> list = this.n
         .q()
         .b(Recipes.g, this.q, this.n)
         .stream()
         .filter(smithingrecipe -> smithingrecipe instanceof LegacyUpgradeRecipe)
         .map(smithingrecipe -> (LegacyUpgradeRecipe)smithingrecipe)
         .toList();
      if (list.isEmpty()) {
         CraftEventFactory.callPrepareSmithingEvent(this.getBukkitView(), ItemStack.b);
      } else {
         LegacyUpgradeRecipe legacyupgraderecipe = list.get(0);
         ItemStack itemstack = legacyupgraderecipe.a(this.q, this.n.u_());
         if (itemstack.a(this.n.G())) {
            this.w = legacyupgraderecipe;
            this.r.a(legacyupgraderecipe);
            CraftEventFactory.callPrepareSmithingEvent(this.getBukkitView(), itemstack);
         }
      }
   }

   @Override
   public int d(ItemStack itemstack) {
      return this.e(itemstack) ? 1 : 0;
   }

   protected boolean e(ItemStack itemstack) {
      return this.x.stream().anyMatch(legacyupgraderecipe -> legacyupgraderecipe.c(itemstack));
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.r && super.a(itemstack, slot);
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventorySmithing(this.o.getLocation(), this.q, this.r);
         this.bukkitEntity = new CraftInventoryView(this.p.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
