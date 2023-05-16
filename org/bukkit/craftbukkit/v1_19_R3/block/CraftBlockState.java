package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.lang.ref.WeakReference;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class CraftBlockState implements BlockState {
   protected final CraftWorld world;
   private final BlockPosition position;
   protected IBlockData data;
   protected int flag;
   private WeakReference<GeneratorAccess> weakWorld;

   protected CraftBlockState(Block block) {
      this(block.getWorld(), ((CraftBlock)block).getPosition(), ((CraftBlock)block).getNMS());
      this.flag = 3;
      this.setWorldHandle(((CraftBlock)block).getHandle());
   }

   protected CraftBlockState(Block block, int flag) {
      this(block);
      this.flag = flag;
   }

   protected CraftBlockState(@Nullable World world, BlockPosition blockPosition, IBlockData blockData) {
      this.world = (CraftWorld)world;
      this.position = blockPosition;
      this.data = blockData;
   }

   public void setWorldHandle(GeneratorAccess generatorAccess) {
      if (generatorAccess instanceof net.minecraft.world.level.World) {
         this.weakWorld = null;
      } else {
         this.weakWorld = new WeakReference<>(generatorAccess);
      }
   }

   public GeneratorAccess getWorldHandle() {
      if (this.weakWorld == null) {
         return this.isPlaced() ? this.world.getHandle() : null;
      } else {
         GeneratorAccess access = this.weakWorld.get();
         if (access == null) {
            this.weakWorld = null;
            return this.isPlaced() ? this.world.getHandle() : null;
         } else {
            return access;
         }
      }
   }

   protected final boolean isWorldGeneration() {
      GeneratorAccess generatorAccess = this.getWorldHandle();
      return generatorAccess != null && !(generatorAccess instanceof net.minecraft.world.level.World);
   }

   protected final void ensureNoWorldGeneration() {
      if (this.isWorldGeneration()) {
         throw new IllegalStateException("This operation is not supported during world generation!");
      }
   }

   public World getWorld() {
      this.requirePlaced();
      return this.world;
   }

   public int getX() {
      return this.position.u();
   }

   public int getY() {
      return this.position.v();
   }

   public int getZ() {
      return this.position.w();
   }

   public Chunk getChunk() {
      this.requirePlaced();
      return this.world.getChunkAt(this.getX() >> 4, this.getZ() >> 4);
   }

   public void setData(IBlockData data) {
      this.data = data;
   }

   public BlockPosition getPosition() {
      return this.position;
   }

   public IBlockData getHandle() {
      return this.data;
   }

   public BlockData getBlockData() {
      return CraftBlockData.fromData(this.data);
   }

   public void setBlockData(BlockData data) {
      Preconditions.checkArgument(data != null, "BlockData cannot be null");
      this.data = ((CraftBlockData)data).getState();
   }

   public void setData(MaterialData data) {
      Material mat = CraftMagicNumbers.getMaterial(this.data).getItemType();
      if (mat != null && mat.getData() != null) {
         if (data.getClass() != mat.getData() && data.getClass() != MaterialData.class) {
            throw new IllegalArgumentException("Provided data is not of type " + mat.getData().getName() + ", found " + data.getClass().getName());
         }

         this.data = CraftMagicNumbers.getBlock(data);
      } else {
         this.data = CraftMagicNumbers.getBlock(data);
      }
   }

   public MaterialData getData() {
      return CraftMagicNumbers.getMaterial(this.data);
   }

   public void setType(Material type) {
      Preconditions.checkArgument(type != null, "Material cannot be null");
      Preconditions.checkArgument(type.isBlock(), "Material must be a block!");
      if (this.getType() != type) {
         this.data = CraftMagicNumbers.getBlock(type).o();
      }
   }

   public Material getType() {
      return CraftMagicNumbers.getMaterial(this.data.b());
   }

   public void setFlag(int flag) {
      this.flag = flag;
   }

   public int getFlag() {
      return this.flag;
   }

   public byte getLightLevel() {
      return this.getBlock().getLightLevel();
   }

   public CraftBlock getBlock() {
      this.requirePlaced();
      return CraftBlock.at(this.getWorldHandle(), this.position);
   }

   public boolean update() {
      return this.update(false);
   }

   public boolean update(boolean force) {
      return this.update(force, true);
   }

   public boolean update(boolean force, boolean applyPhysics) {
      if (!this.isPlaced()) {
         return true;
      } else {
         GeneratorAccess access = this.getWorldHandle();
         CraftBlock block = this.getBlock();
         if (block.getType() != this.getType() && !force) {
            return false;
         } else {
            IBlockData newBlock = this.data;
            block.setTypeAndData(newBlock, applyPhysics);
            if (access instanceof net.minecraft.world.level.World) {
               this.world.getHandle().a(this.position, block.getNMS(), newBlock, 3);
            }

            return true;
         }
      }
   }

   public byte getRawData() {
      return CraftMagicNumbers.toLegacyData(this.data);
   }

   public Location getLocation() {
      return new Location(this.world, (double)this.getX(), (double)this.getY(), (double)this.getZ());
   }

   public Location getLocation(Location loc) {
      if (loc != null) {
         loc.setWorld(this.world);
         loc.setX((double)this.getX());
         loc.setY((double)this.getY());
         loc.setZ((double)this.getZ());
         loc.setYaw(0.0F);
         loc.setPitch(0.0F);
      }

      return loc;
   }

   public void setRawData(byte data) {
      this.data = CraftMagicNumbers.getBlock(this.getType(), data);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CraftBlockState other = (CraftBlockState)obj;
         if (this.world == other.world || this.world != null && this.world.equals(other.world)) {
            if (this.position == other.position || this.position != null && this.position.equals(other.position)) {
               return this.data == other.data || this.data != null && this.data.equals(other.data);
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);
      hash = 73 * hash + (this.position != null ? this.position.hashCode() : 0);
      return 73 * hash + (this.data != null ? this.data.hashCode() : 0);
   }

   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
      this.requirePlaced();
      this.world.getBlockMetadata().setMetadata((Block)this.getBlock(), metadataKey, newMetadataValue);
   }

   public List<MetadataValue> getMetadata(String metadataKey) {
      this.requirePlaced();
      return this.world.getBlockMetadata().getMetadata((Block)this.getBlock(), metadataKey);
   }

   public boolean hasMetadata(String metadataKey) {
      this.requirePlaced();
      return this.world.getBlockMetadata().hasMetadata((Block)this.getBlock(), metadataKey);
   }

   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
      this.requirePlaced();
      this.world.getBlockMetadata().removeMetadata((Block)this.getBlock(), metadataKey, owningPlugin);
   }

   public boolean isPlaced() {
      return this.world != null;
   }

   protected void requirePlaced() {
      if (!this.isPlaced()) {
         throw new IllegalStateException("The blockState must be placed to call this method");
      }
   }
}
