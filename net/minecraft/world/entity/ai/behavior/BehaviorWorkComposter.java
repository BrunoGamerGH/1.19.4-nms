package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.InventorySubcontainer;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BlockComposter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class BehaviorWorkComposter extends BehaviorWork {
   private static final List<Item> c = ImmutableList.of(Items.oD, Items.um);

   @Override
   protected void a(WorldServer var0, EntityVillager var1) {
      Optional<GlobalPos> var2 = var1.dH().c(MemoryModuleType.c);
      if (var2.isPresent()) {
         GlobalPos var3 = var2.get();
         IBlockData var4 = var0.a_(var3.b());
         if (var4.a(Blocks.oY)) {
            this.a(var1);
            this.a(var0, var1, var3, var4);
         }
      }
   }

   private void a(WorldServer var0, EntityVillager var1, GlobalPos var2, IBlockData var3) {
      BlockPosition var4 = var2.b();
      if (var3.c(BlockComposter.d) == 8) {
         var3 = BlockComposter.a(var1, var3, var0, var4);
      }

      int var5 = 20;
      int var6 = 10;
      int[] var7 = new int[c.size()];
      InventorySubcontainer var8 = var1.w();
      int var9 = var8.b();
      IBlockData var10 = var3;

      for(int var11 = var9 - 1; var11 >= 0 && var5 > 0; --var11) {
         ItemStack var12 = var8.a(var11);
         int var13 = c.indexOf(var12.c());
         if (var13 != -1) {
            int var14 = var12.K();
            int var15 = var7[var13] + var14;
            var7[var13] = var15;
            int var16 = Math.min(Math.min(var15 - 10, var5), var14);
            if (var16 > 0) {
               var5 -= var16;

               for(int var17 = 0; var17 < var16; ++var17) {
                  var10 = BlockComposter.a(var1, var10, var0, var12, var4);
                  if (var10.c(BlockComposter.d) == 7) {
                     this.a(var0, var3, var4, var10);
                     return;
                  }
               }
            }
         }
      }

      this.a(var0, var3, var4, var10);
   }

   private void a(WorldServer var0, IBlockData var1, BlockPosition var2, IBlockData var3) {
      var0.c(1500, var2, var3 != var1 ? 1 : 0);
   }

   private void a(EntityVillager var0) {
      InventorySubcontainer var1 = var0.w();
      if (var1.a_(Items.oF) <= 36) {
         int var2 = var1.a_(Items.oE);
         int var3 = 3;
         int var4 = 3;
         int var5 = Math.min(3, var2 / 3);
         if (var5 != 0) {
            int var6 = var5 * 3;
            var1.a(Items.oE, var6);
            ItemStack var7 = var1.a(new ItemStack(Items.oF, var5));
            if (!var7.b()) {
               var0.a(var7, 0.5F);
            }
         }
      }
   }
}
