package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BlockJukeBox;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityJukeBox;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Jukebox;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryJukebox;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.JukeboxInventory;

public class CraftJukebox extends CraftBlockEntityState<TileEntityJukeBox> implements Jukebox {
   public CraftJukebox(World world, TileEntityJukeBox tileEntity) {
      super(world, tileEntity);
   }

   public JukeboxInventory getSnapshotInventory() {
      return new CraftInventoryJukebox(this.getSnapshot());
   }

   public JukeboxInventory getInventory() {
      return (JukeboxInventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryJukebox(this.getTileEntity()));
   }

   @Override
   public boolean update(boolean force, boolean applyPhysics) {
      boolean result = super.update(force, applyPhysics);
      if (result && this.isPlaced() && this.getType() == Material.JUKEBOX) {
         Material record = this.getPlaying();
         this.getWorldHandle().a(this.getPosition(), this.data, 3);
         TileEntity tileEntity = this.getTileEntityFromWorld();
         TileEntityJukeBox jukebox;
         if (tileEntity instanceof TileEntityJukeBox && (jukebox = (TileEntityJukeBox)tileEntity) == (TileEntityJukeBox)tileEntity) {
            CraftWorld world = (CraftWorld)this.getWorld();
            if (record.isAir()) {
               jukebox.a(ItemStack.b);
               world.playEffect(this.getLocation(), Effect.IRON_DOOR_CLOSE, 0);
            } else {
               world.playEffect(this.getLocation(), Effect.RECORD_PLAY, record);
            }
         }
      }

      return result;
   }

   public Material getPlaying() {
      return this.getRecord().getType();
   }

   public void setPlaying(Material record) {
      if (record == null || CraftMagicNumbers.getItem(record) == null) {
         record = Material.AIR;
      }

      this.setRecord(new org.bukkit.inventory.ItemStack(record));
   }

   public boolean hasRecord() {
      return this.getHandle().c(BlockJukeBox.a) && !this.getPlaying().isAir();
   }

   public org.bukkit.inventory.ItemStack getRecord() {
      ItemStack record = this.getSnapshot().at_();
      return CraftItemStack.asBukkitCopy(record);
   }

   public void setRecord(org.bukkit.inventory.ItemStack record) {
      ItemStack nms = CraftItemStack.asNMSCopy(record);
      TileEntityJukeBox snapshot = this.getSnapshot();
      snapshot.a(nms);
      snapshot.g = snapshot.f;
      snapshot.h = !nms.b();
      this.data = this.data.a(BlockJukeBox.a, Boolean.valueOf(!nms.b()));
   }

   public boolean isPlaying() {
      this.requirePlaced();
      TileEntity tileEntity = this.getTileEntityFromWorld();
      TileEntityJukeBox jukebox;
      return tileEntity instanceof TileEntityJukeBox && (jukebox = (TileEntityJukeBox)tileEntity) == (TileEntityJukeBox)tileEntity && jukebox.f();
   }

   public boolean startPlaying() {
      this.requirePlaced();
      TileEntity tileEntity = this.getTileEntityFromWorld();
      TileEntityJukeBox jukebox;
      if (tileEntity instanceof TileEntityJukeBox && (jukebox = (TileEntityJukeBox)tileEntity) == (TileEntityJukeBox)tileEntity) {
         ItemStack record = jukebox.at_();
         if (!record.b() && !this.isPlaying()) {
            jukebox.h = true;
            jukebox.g = jukebox.f;
            this.getWorld().playEffect(this.getLocation(), Effect.RECORD_PLAY, CraftMagicNumbers.getMaterial(record.c()));
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void stopPlaying() {
      this.requirePlaced();
      TileEntity tileEntity = this.getTileEntityFromWorld();
      TileEntityJukeBox jukebox;
      if (tileEntity instanceof TileEntityJukeBox && (jukebox = (TileEntityJukeBox)tileEntity) == (TileEntityJukeBox)tileEntity) {
         jukebox.h = false;
         this.getWorld().playEffect(this.getLocation(), Effect.IRON_DOOR_CLOSE, 0);
      }
   }

   public boolean eject() {
      this.ensureNoWorldGeneration();
      TileEntity tileEntity = this.getTileEntityFromWorld();
      if (!(tileEntity instanceof TileEntityJukeBox)) {
         return false;
      } else {
         TileEntityJukeBox jukebox = (TileEntityJukeBox)tileEntity;
         boolean result = !jukebox.at_().b();
         jukebox.i();
         return result;
      }
   }
}
