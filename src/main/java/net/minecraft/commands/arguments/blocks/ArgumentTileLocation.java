package net.minecraft.commands.arguments.blocks;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class ArgumentTileLocation implements Predicate<ShapeDetectorBlock> {
   private final IBlockData a;
   private final Set<IBlockState<?>> b;
   @Nullable
   private final NBTTagCompound c;

   public ArgumentTileLocation(IBlockData var0, Set<IBlockState<?>> var1, @Nullable NBTTagCompound var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public IBlockData a() {
      return this.a;
   }

   public Set<IBlockState<?>> b() {
      return this.b;
   }

   public boolean a(ShapeDetectorBlock var0) {
      IBlockData var1 = var0.a();
      if (!var1.a(this.a.b())) {
         return false;
      } else {
         for(IBlockState<?> var3 : this.b) {
            if (var1.c(var3) != this.a.c(var3)) {
               return false;
            }
         }

         if (this.c == null) {
            return true;
         } else {
            TileEntity var2 = var0.b();
            return var2 != null && GameProfileSerializer.a(this.c, var2.m(), true);
         }
      }
   }

   public boolean a(WorldServer var0, BlockPosition var1) {
      return this.a(new ShapeDetectorBlock(var0, var1, false));
   }

   public boolean a(WorldServer var0, BlockPosition var1, int var2) {
      IBlockData var3 = Block.b(this.a, var0, var1);
      if (var3.h()) {
         var3 = this.a;
      }

      if (!var0.a(var1, var3, var2)) {
         return false;
      } else {
         if (this.c != null) {
            TileEntity var4 = var0.c_(var1);
            if (var4 != null) {
               var4.a(this.c);
            }
         }

         return true;
      }
   }
}
