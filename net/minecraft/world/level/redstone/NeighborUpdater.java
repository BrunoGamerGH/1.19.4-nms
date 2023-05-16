package net.minecraft.world.level.redstone;

import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.event.block.BlockPhysicsEvent;

public interface NeighborUpdater {
   EnumDirection[] a = new EnumDirection[]{EnumDirection.e, EnumDirection.f, EnumDirection.a, EnumDirection.b, EnumDirection.c, EnumDirection.d};

   void a(EnumDirection var1, IBlockData var2, BlockPosition var3, BlockPosition var4, int var5, int var6);

   void a(BlockPosition var1, Block var2, BlockPosition var3);

   void a(IBlockData var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5);

   default void a(BlockPosition blockposition, Block block, @Nullable EnumDirection enumdirection) {
      for(EnumDirection enumdirection1 : a) {
         if (enumdirection1 != enumdirection) {
            this.a(blockposition.a(enumdirection1), block, blockposition);
         }
      }
   }

   static void a(
      GeneratorAccess generatoraccess,
      EnumDirection enumdirection,
      IBlockData iblockdata,
      BlockPosition blockposition,
      BlockPosition blockposition1,
      int i,
      int j
   ) {
      IBlockData iblockdata1 = generatoraccess.a_(blockposition);
      IBlockData iblockdata2 = iblockdata1.a(enumdirection, iblockdata, generatoraccess, blockposition, blockposition1);
      Block.a(iblockdata1, iblockdata2, generatoraccess, blockposition, i, j);
   }

   static void a(World world, IBlockData iblockdata, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      try {
         CraftWorld cworld = ((WorldServer)world).getWorld();
         if (cworld != null) {
            BlockPhysicsEvent event = new BlockPhysicsEvent(
               CraftBlock.at(world, blockposition), CraftBlockData.fromData(iblockdata), CraftBlock.at(world, blockposition1)
            );
            ((WorldServer)world).getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }
         }

         iblockdata.a(world, blockposition, block, blockposition1, flag);
      } catch (StackOverflowError var9) {
         World.lastPhysicsProblem = new BlockPosition(blockposition);
      } catch (Throwable var10) {
         CrashReport crashreport = CrashReport.a(var10, "Exception while updating neighbours");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
         crashreportsystemdetails.a("Source block type", () -> {
            try {
               return String.format(Locale.ROOT, "ID #%s (%s // %s)", BuiltInRegistries.f.b(block), block.h(), block.getClass().getCanonicalName());
            } catch (Throwable var2x) {
               return "ID #" + BuiltInRegistries.f.b(block);
            }
         });
         CrashReportSystemDetails.a(crashreportsystemdetails, world, blockposition, iblockdata);
         throw new ReportedException(crashreport);
      }
   }
}
