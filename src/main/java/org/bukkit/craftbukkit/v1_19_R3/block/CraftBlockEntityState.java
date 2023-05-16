package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.World;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;

public class CraftBlockEntityState<T extends TileEntity> extends CraftBlockState implements TileState {
   private final T tileEntity;
   private final T snapshot;

   public CraftBlockEntityState(World world, T tileEntity) {
      super(world, tileEntity.p(), tileEntity.q());
      this.tileEntity = tileEntity;
      this.snapshot = this.createSnapshot(tileEntity);
      this.load(this.snapshot);
   }

   public void refreshSnapshot() {
      this.load(this.tileEntity);
   }

   private T createSnapshot(T tileEntity) {
      if (tileEntity == null) {
         return null;
      } else {
         NBTTagCompound nbtTagCompound = tileEntity.m();
         return (T)TileEntity.a(this.getPosition(), this.getHandle(), nbtTagCompound);
      }
   }

   private void copyData(T from, T to) {
      NBTTagCompound nbtTagCompound = from.m();
      to.a(nbtTagCompound);
   }

   protected T getTileEntity() {
      return this.tileEntity;
   }

   protected T getSnapshot() {
      return this.snapshot;
   }

   protected TileEntity getTileEntityFromWorld() {
      this.requirePlaced();
      return this.getWorldHandle().c_(this.getPosition());
   }

   public NBTTagCompound getSnapshotNBT() {
      this.applyTo(this.snapshot);
      return this.snapshot.m();
   }

   protected void load(T tileEntity) {
      if (tileEntity != null && tileEntity != this.snapshot) {
         this.copyData(tileEntity, this.snapshot);
      }
   }

   protected void applyTo(T tileEntity) {
      if (tileEntity != null && tileEntity != this.snapshot) {
         this.copyData(this.snapshot, tileEntity);
      }
   }

   protected boolean isApplicable(TileEntity tileEntity) {
      return tileEntity != null && this.tileEntity.getClass() == tileEntity.getClass();
   }

   @Override
   public boolean update(boolean force, boolean applyPhysics) {
      boolean result = super.update(force, applyPhysics);
      if (result && this.isPlaced()) {
         TileEntity tile = this.getTileEntityFromWorld();
         if (this.isApplicable(tile)) {
            this.applyTo((T)tile);
            tile.e();
         }
      }

      return result;
   }

   public PersistentDataContainer getPersistentDataContainer() {
      return this.getSnapshot().persistentDataContainer;
   }
}
