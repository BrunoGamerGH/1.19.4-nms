package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.item.ItemBoneMeal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockCrops;
import net.minecraft.world.level.block.state.IBlockData;

public class BehaviorBonemeal extends Behavior<EntityVillager> {
   private static final int c = 80;
   private long d;
   private long e;
   private int f;
   private Optional<BlockPosition> g = Optional.empty();

   public BehaviorBonemeal() {
      super(ImmutableMap.of(MemoryModuleType.n, MemoryStatus.b, MemoryModuleType.m, MemoryStatus.b));
   }

   protected boolean a(WorldServer var0, EntityVillager var1) {
      if (var1.ag % 10 == 0 && (this.e == 0L || this.e + 160L <= (long)var1.ag)) {
         if (var1.w().a_(Items.qG) <= 0) {
            return false;
         } else {
            this.g = this.b(var0, var1);
            return this.g.isPresent();
         }
      } else {
         return false;
      }
   }

   protected boolean a(WorldServer var0, EntityVillager var1, long var2) {
      return this.f < 80 && this.g.isPresent();
   }

   private Optional<BlockPosition> b(WorldServer var0, EntityVillager var1) {
      BlockPosition.MutableBlockPosition var2 = new BlockPosition.MutableBlockPosition();
      Optional<BlockPosition> var3 = Optional.empty();
      int var4 = 0;

      for(int var5 = -1; var5 <= 1; ++var5) {
         for(int var6 = -1; var6 <= 1; ++var6) {
            for(int var7 = -1; var7 <= 1; ++var7) {
               var2.a(var1.dg(), var5, var6, var7);
               if (this.a(var2, var0)) {
                  if (var0.z.a(++var4) == 0) {
                     var3 = Optional.of(var2.i());
                  }
               }
            }
         }
      }

      return var3;
   }

   private boolean a(BlockPosition var0, WorldServer var1) {
      IBlockData var2 = var1.a_(var0);
      Block var3 = var2.b();
      return var3 instanceof BlockCrops && !((BlockCrops)var3).h(var2);
   }

   protected void b(WorldServer var0, EntityVillager var1, long var2) {
      this.a(var1);
      var1.a(EnumItemSlot.a, new ItemStack(Items.qG));
      this.d = var2;
      this.f = 0;
   }

   private void a(EntityVillager var0) {
      this.g.ifPresent(var1x -> {
         BehaviorTarget var2 = new BehaviorTarget(var1x);
         var0.dH().a(MemoryModuleType.n, var2);
         var0.dH().a(MemoryModuleType.m, new MemoryTarget(var2, 0.5F, 1));
      });
   }

   protected void c(WorldServer var0, EntityVillager var1, long var2) {
      var1.a(EnumItemSlot.a, ItemStack.b);
      this.e = (long)var1.ag;
   }

   protected void d(WorldServer var0, EntityVillager var1, long var2) {
      BlockPosition var4 = this.g.get();
      if (var2 >= this.d && var4.a(var1.de(), 1.0)) {
         ItemStack var5 = ItemStack.b;
         InventorySubcontainer var6 = var1.w();
         int var7 = var6.b();

         for(int var8 = 0; var8 < var7; ++var8) {
            ItemStack var9 = var6.a(var8);
            if (var9.a(Items.qG)) {
               var5 = var9;
               break;
            }
         }

         if (!var5.b() && ItemBoneMeal.a(var5, var0, var4)) {
            var0.c(1505, var4, 0);
            this.g = this.b(var0, var1);
            this.a(var1);
            this.d = var2 + 40L;
         }

         ++this.f;
      }
   }
}
