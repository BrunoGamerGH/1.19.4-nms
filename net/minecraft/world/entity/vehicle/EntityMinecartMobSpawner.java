package net.minecraft.world.entity.vehicle;

import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.MobSpawnerAbstract;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public class EntityMinecartMobSpawner extends EntityMinecartAbstract {
   private final MobSpawnerAbstract c = new MobSpawnerAbstract() {
      @Override
      public void a(World var0, BlockPosition var1, int var2) {
         var0.a(EntityMinecartMobSpawner.this, (byte)var2);
      }
   };
   private final Runnable d;

   public EntityMinecartMobSpawner(EntityTypes<? extends EntityMinecartMobSpawner> var0, World var1) {
      super(var0, var1);
      this.d = this.a(var1);
   }

   public EntityMinecartMobSpawner(World var0, double var1, double var3, double var5) {
      super(EntityTypes.aQ, var0, var1, var3, var5);
      this.d = this.a(var0);
   }

   @Override
   protected Item i() {
      return Items.mW;
   }

   private Runnable a(World var0) {
      return var0 instanceof WorldServer ? () -> this.c.a((WorldServer)var0, this.dg()) : () -> this.c.a(var0, this.dg());
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.e;
   }

   @Override
   public IBlockData v() {
      return Blocks.cs.o();
   }

   @Override
   protected void a(NBTTagCompound var0) {
      super.a(var0);
      this.c.a(this.H, this.dg(), var0);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      this.c.a(var0);
   }

   @Override
   public void b(byte var0) {
      this.c.a(this.H, var0);
   }

   @Override
   public void l() {
      super.l();
      this.d.run();
   }

   public MobSpawnerAbstract z() {
      return this.c;
   }

   @Override
   public boolean cJ() {
      return true;
   }
}
