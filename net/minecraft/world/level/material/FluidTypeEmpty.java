package net.minecraft.world.level.material;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class FluidTypeEmpty extends FluidType {
   @Override
   public Item a() {
      return Items.a;
   }

   @Override
   public boolean a(Fluid var0, IBlockAccess var1, BlockPosition var2, FluidType var3, EnumDirection var4) {
      return true;
   }

   @Override
   public Vec3D a(IBlockAccess var0, BlockPosition var1, Fluid var2) {
      return Vec3D.b;
   }

   @Override
   public int a(IWorldReader var0) {
      return 0;
   }

   @Override
   protected boolean b() {
      return true;
   }

   @Override
   protected float c() {
      return 0.0F;
   }

   @Override
   public float a(Fluid var0, IBlockAccess var1, BlockPosition var2) {
      return 0.0F;
   }

   @Override
   public float a(Fluid var0) {
      return 0.0F;
   }

   @Override
   protected IBlockData b(Fluid var0) {
      return Blocks.a.o();
   }

   @Override
   public boolean c(Fluid var0) {
      return false;
   }

   @Override
   public int d(Fluid var0) {
      return 0;
   }

   @Override
   public VoxelShape b(Fluid var0, IBlockAccess var1, BlockPosition var2) {
      return VoxelShapes.a();
   }
}
