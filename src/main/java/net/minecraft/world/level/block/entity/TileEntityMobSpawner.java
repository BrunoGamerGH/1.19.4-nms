package net.minecraft.world.level.block.entity;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.MobSpawnerAbstract;
import net.minecraft.world.level.MobSpawnerData;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityMobSpawner extends TileEntity {
   private final MobSpawnerAbstract a = new MobSpawnerAbstract() {
      @Override
      public void a(World var0, BlockPosition var1, int var2) {
         var0.a(var1, Blocks.cs, var2, 0);
      }

      @Override
      public void a(@Nullable World var0, BlockPosition var1, MobSpawnerData var2) {
         super.a(var0, var1, var2);
         if (var0 != null) {
            IBlockData var3 = var0.a_(var1);
            var0.a(var1, var3, var3, 4);
         }
      }
   };

   public TileEntityMobSpawner(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.j, var0, var1);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.a.a(this.o, this.p, var0);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      this.a.a(var0);
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityMobSpawner var3) {
      var3.a.a(var0, var1);
   }

   public static void b(World var0, BlockPosition var1, IBlockData var2, TileEntityMobSpawner var3) {
      var3.a.a((WorldServer)var0, var1);
   }

   public PacketPlayOutTileEntityData c() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      NBTTagCompound var0 = this.o();
      var0.r("SpawnPotentials");
      return var0;
   }

   @Override
   public boolean a_(int var0, int var1) {
      return this.a.a(this.o, var0) ? true : super.a_(var0, var1);
   }

   @Override
   public boolean t() {
      return true;
   }

   public void a(EntityTypes<?> var0, RandomSource var1) {
      this.a.a(var0, this.o, var1, this.p);
   }

   public MobSpawnerAbstract d() {
      return this.a;
   }
}
