package net.minecraft.world.inventory;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipes;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventorySmithingNew;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryView;

public class ContainerSmithing extends ContainerAnvilAbstract {
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 2;
   public static final int n = 3;
   public static final int s = 8;
   public static final int t = 26;
   public static final int u = 44;
   private static final int w = 98;
   public static final int v = 48;
   private final World x;
   @Nullable
   private SmithingRecipe y;
   private final List<SmithingRecipe> z;
   private CraftInventoryView bukkitEntity;

   public ContainerSmithing(int i, PlayerInventory playerinventory) {
      this(i, playerinventory, ContainerAccess.a);
   }

   public ContainerSmithing(int i, PlayerInventory playerinventory, ContainerAccess containeraccess) {
      super(Containers.v, i, playerinventory, containeraccess);
      this.x = playerinventory.m.H;
      this.z = this.x.q().a(Recipes.g);
   }

   @Override
   protected ItemCombinerMenuSlotDefinition l() {
      return ItemCombinerMenuSlotDefinition.a()
         .a(0, 8, 48, itemstack -> this.z.stream().anyMatch(smithingrecipe -> smithingrecipe.a(itemstack)))
         .a(1, 26, 48, itemstack -> this.z.stream().anyMatch(smithingrecipe -> smithingrecipe.b(itemstack) && smithingrecipe.a(this.i.get(0).e())))
         .a(2, 44, 48, itemstack -> this.z.stream().anyMatch(smithingrecipe -> smithingrecipe.c(itemstack) && smithingrecipe.a(this.i.get(0).e())))
         .a(3, 98, 48)
         .a();
   }

   @Override
   protected boolean a(IBlockData iblockdata) {
      return iblockdata.a(Blocks.nX);
   }

   @Override
   protected boolean a(EntityHuman entityhuman, boolean flag) {
      return this.y != null && this.y.a(this.q, this.x);
   }

   @Override
   protected void a(EntityHuman entityhuman, ItemStack itemstack) {
      itemstack.a(entityhuman.H, entityhuman, itemstack.K());
      this.r.b(entityhuman);
      this.e(0);
      this.e(1);
      this.e(2);
      this.o.a((world, blockposition) -> world.c(1044, blockposition, 0));
   }

   private void e(int i) {
      ItemStack itemstack = this.q.a(i);
      itemstack.h(1);
      this.q.a(i, itemstack);
   }

   @Override
   public void m() {
      List<SmithingRecipe> list = this.x.q().b(Recipes.g, this.q, this.x);
      if (list.isEmpty()) {
         CraftEventFactory.callPrepareSmithingEvent(this.getBukkitView(), ItemStack.b);
      } else {
         SmithingRecipe smithingrecipe = list.get(0);
         ItemStack itemstack = smithingrecipe.a(this.q, this.x.u_());
         if (itemstack.a(this.x.G())) {
            this.y = smithingrecipe;
            this.r.a(smithingrecipe);
            CraftEventFactory.callPrepareSmithingEvent(this.getBukkitView(), itemstack);
         }
      }
   }

   @Override
   public int d(ItemStack itemstack) {
      return this.z.stream().map(smithingrecipe -> a(smithingrecipe, itemstack)).filter(Optional::isPresent).findFirst().orElse(Optional.of(0)).get();
   }

   private static Optional<Integer> a(SmithingRecipe smithingrecipe, ItemStack itemstack) {
      return smithingrecipe.a(itemstack)
         ? Optional.of(0)
         : (smithingrecipe.b(itemstack) ? Optional.of(1) : (smithingrecipe.c(itemstack) ? Optional.of(2) : Optional.empty()));
   }

   @Override
   public boolean a(ItemStack itemstack, Slot slot) {
      return slot.d != this.r && super.a(itemstack, slot);
   }

   @Override
   public boolean c(ItemStack itemstack) {
      return this.z.stream().map(smithingrecipe -> a(smithingrecipe, itemstack)).anyMatch(Optional::isPresent);
   }

   public CraftInventoryView getBukkitView() {
      if (this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory inventory = new CraftInventorySmithingNew(this.o.getLocation(), this.q, this.r);
         this.bukkitEntity = new CraftInventoryView(this.p.getBukkitEntity(), inventory, this);
         return this.bukkitEntity;
      }
   }
}
