package net.minecraft.world.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.world.item.ItemStack;

public class ItemCombinerMenuSlotDefinition {
   private final List<ItemCombinerMenuSlotDefinition.b> a;
   private final ItemCombinerMenuSlotDefinition.b b;

   ItemCombinerMenuSlotDefinition(List<ItemCombinerMenuSlotDefinition.b> var0, ItemCombinerMenuSlotDefinition.b var1) {
      if (!var0.isEmpty() && !var1.equals(ItemCombinerMenuSlotDefinition.b.e)) {
         this.a = var0;
         this.b = var1;
      } else {
         throw new IllegalArgumentException("Need to define both inputSlots and resultSlot");
      }
   }

   public static ItemCombinerMenuSlotDefinition.a a() {
      return new ItemCombinerMenuSlotDefinition.a();
   }

   public boolean a(int var0) {
      return this.a.size() >= var0;
   }

   public ItemCombinerMenuSlotDefinition.b b(int var0) {
      return this.a.get(var0);
   }

   public ItemCombinerMenuSlotDefinition.b b() {
      return this.b;
   }

   public List<ItemCombinerMenuSlotDefinition.b> c() {
      return this.a;
   }

   public int d() {
      return this.a.size();
   }

   public int e() {
      return this.d();
   }

   public List<Integer> f() {
      return this.a.stream().map(ItemCombinerMenuSlotDefinition.b::a).collect(Collectors.toList());
   }

   public static class a {
      private final List<ItemCombinerMenuSlotDefinition.b> a = new ArrayList<>();
      private ItemCombinerMenuSlotDefinition.b b = ItemCombinerMenuSlotDefinition.b.e;

      public ItemCombinerMenuSlotDefinition.a a(int var0, int var1, int var2, Predicate<ItemStack> var3) {
         this.a.add(new ItemCombinerMenuSlotDefinition.b(var0, var1, var2, var3));
         return this;
      }

      public ItemCombinerMenuSlotDefinition.a a(int var0, int var1, int var2) {
         this.b = new ItemCombinerMenuSlotDefinition.b(var0, var1, var2, var0x -> false);
         return this;
      }

      public ItemCombinerMenuSlotDefinition a() {
         return new ItemCombinerMenuSlotDefinition(this.a, this.b);
      }
   }

   public static record b(int slotIndex, int x, int y, Predicate<ItemStack> mayPlace) {
      private final int a;
      private final int b;
      private final int c;
      private final Predicate<ItemStack> d;
      static final ItemCombinerMenuSlotDefinition.b e = new ItemCombinerMenuSlotDefinition.b(0, 0, 0, var0 -> true);

      public b(int var0, int var1, int var2, Predicate<ItemStack> var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }
   }
}
