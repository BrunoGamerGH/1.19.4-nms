package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class ContainerOpenersCounter {
   private static final int a = 5;
   private int b;
   public boolean opened;

   protected abstract void a(World var1, BlockPosition var2, IBlockData var3);

   protected abstract void b(World var1, BlockPosition var2, IBlockData var3);

   protected abstract void a(World var1, BlockPosition var2, IBlockData var3, int var4, int var5);

   public void onAPIOpen(World world, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(world, blockposition, iblockdata);
   }

   public void onAPIClose(World world, BlockPosition blockposition, IBlockData iblockdata) {
      this.b(world, blockposition, iblockdata);
   }

   public void openerAPICountChanged(World world, BlockPosition blockposition, IBlockData iblockdata, int i, int j) {
      this.a(world, blockposition, iblockdata, i, j);
   }

   protected abstract boolean a(EntityHuman var1);

   public void a(EntityHuman entityhuman, World world, BlockPosition blockposition, IBlockData iblockdata) {
      int oldPower = Math.max(0, Math.min(15, this.b));
      int i = this.b++;
      if (world.a_(blockposition).a(Blocks.gU)) {
         int newPower = Math.max(0, Math.min(15, this.b));
         if (oldPower != newPower) {
            CraftEventFactory.callRedstoneChange(world, blockposition, oldPower, newPower);
         }
      }

      if (i == 0) {
         this.a(world, blockposition, iblockdata);
         world.a(entityhuman, GameEvent.k, blockposition);
         d(world, blockposition, iblockdata);
      }

      this.a(world, blockposition, iblockdata, i, this.b);
   }

   public void b(EntityHuman entityhuman, World world, BlockPosition blockposition, IBlockData iblockdata) {
      int oldPower = Math.max(0, Math.min(15, this.b));
      int i = this.b--;
      if (world.a_(blockposition).a(Blocks.gU)) {
         int newPower = Math.max(0, Math.min(15, this.b));
         if (oldPower != newPower) {
            CraftEventFactory.callRedstoneChange(world, blockposition, oldPower, newPower);
         }
      }

      if (this.b == 0) {
         this.b(world, blockposition, iblockdata);
         world.a(entityhuman, GameEvent.j, blockposition);
      }

      this.a(world, blockposition, iblockdata, i, this.b);
   }

   private int a(World world, BlockPosition blockposition) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();
      float f = 5.0F;
      AxisAlignedBB axisalignedbb = new AxisAlignedBB(
         (double)((float)i - 5.0F),
         (double)((float)j - 5.0F),
         (double)((float)k - 5.0F),
         (double)((float)(i + 1) + 5.0F),
         (double)((float)(j + 1) + 5.0F),
         (double)((float)(k + 1) + 5.0F)
      );
      return world.a(EntityTypeTest.a(EntityHuman.class), axisalignedbb, this::a).size();
   }

   public void c(World world, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.a(world, blockposition);
      if (this.opened) {
         ++i;
      }

      int j = this.b;
      if (j != i) {
         boolean flag = i != 0;
         boolean flag1 = j != 0;
         if (flag && !flag1) {
            this.a(world, blockposition, iblockdata);
            world.a(null, GameEvent.k, blockposition);
         } else if (!flag) {
            this.b(world, blockposition, iblockdata);
            world.a(null, GameEvent.j, blockposition);
         }

         this.b = i;
      }

      this.a(world, blockposition, iblockdata, j, i);
      if (i > 0) {
         d(world, blockposition, iblockdata);
      }
   }

   public int a() {
      return this.b;
   }

   private static void d(World world, BlockPosition blockposition, IBlockData iblockdata) {
      world.a(blockposition, iblockdata.b(), 5);
   }
}
