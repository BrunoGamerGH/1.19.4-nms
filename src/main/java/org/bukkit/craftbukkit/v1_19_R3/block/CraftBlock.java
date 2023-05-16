package org.bukkit.craftbukkit.v1_19_R3.block;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.item.ItemBoneMeal;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.BlockRedstoneWire;
import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_19_R3.CraftFluidCollisionMode;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftRayTraceResult;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftVoxelShape;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class CraftBlock implements Block {
   private final GeneratorAccess world;
   private final BlockPosition position;

   public CraftBlock(GeneratorAccess world, BlockPosition position) {
      this.world = world;
      this.position = position.i();
   }

   public static CraftBlock at(GeneratorAccess world, BlockPosition position) {
      return new CraftBlock(world, position);
   }

   public IBlockData getNMS() {
      return this.world.a_(this.position);
   }

   public BlockPosition getPosition() {
      return this.position;
   }

   public GeneratorAccess getHandle() {
      return this.world;
   }

   public World getWorld() {
      return this.world.getMinecraftWorld().getWorld();
   }

   public CraftWorld getCraftWorld() {
      return (CraftWorld)this.getWorld();
   }

   public Location getLocation() {
      return new Location(this.getWorld(), (double)this.position.u(), (double)this.position.v(), (double)this.position.w());
   }

   public Location getLocation(Location loc) {
      if (loc != null) {
         loc.setWorld(this.getWorld());
         loc.setX((double)this.position.u());
         loc.setY((double)this.position.v());
         loc.setZ((double)this.position.w());
         loc.setYaw(0.0F);
         loc.setPitch(0.0F);
      }

      return loc;
   }

   public BlockVector getVector() {
      return new BlockVector(this.getX(), this.getY(), this.getZ());
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
      return this.getWorld().getChunkAt(this);
   }

   public void setData(byte data) {
      this.setData(data, 3);
   }

   public void setData(byte data, boolean applyPhysics) {
      if (applyPhysics) {
         this.setData(data, 3);
      } else {
         this.setData(data, 2);
      }
   }

   private void setData(byte data, int flag) {
      this.world.a(this.position, CraftMagicNumbers.getBlock(this.getType(), data), flag);
   }

   public byte getData() {
      IBlockData blockData = this.world.a_(this.position);
      return CraftMagicNumbers.toLegacyData(blockData);
   }

   public BlockData getBlockData() {
      return CraftBlockData.fromData(this.getNMS());
   }

   public void setType(Material type) {
      this.setType(type, true);
   }

   public void setType(Material type, boolean applyPhysics) {
      Preconditions.checkArgument(type != null, "Material cannot be null");
      this.setBlockData(type.createBlockData(), applyPhysics);
   }

   public void setBlockData(BlockData data) {
      this.setBlockData(data, true);
   }

   public void setBlockData(BlockData data, boolean applyPhysics) {
      Preconditions.checkArgument(data != null, "BlockData cannot be null");
      this.setTypeAndData(((CraftBlockData)data).getState(), applyPhysics);
   }

   boolean setTypeAndData(IBlockData blockData, boolean applyPhysics) {
      return setTypeAndData(this.world, this.position, this.getNMS(), blockData, applyPhysics);
   }

   public static boolean setTypeAndData(GeneratorAccess world, BlockPosition position, IBlockData old, IBlockData blockData, boolean applyPhysics) {
      if (old.q() && blockData.b() != old.b()) {
         if (world instanceof net.minecraft.world.level.World) {
            ((net.minecraft.world.level.World)world).n(position);
         } else {
            world.a(position, Blocks.a.o(), 0);
         }
      }

      if (applyPhysics) {
         return world.a(position, blockData, 3);
      } else {
         boolean success = world.a(position, blockData, 1042);
         if (success && world instanceof net.minecraft.world.level.World) {
            world.getMinecraftWorld().a(position, old, blockData, 3);
         }

         return success;
      }
   }

   public Material getType() {
      return CraftMagicNumbers.getMaterial(this.world.a_(this.position).b());
   }

   public byte getLightLevel() {
      return (byte)this.world.getMinecraftWorld().C(this.position);
   }

   public byte getLightFromSky() {
      return (byte)this.world.a(EnumSkyBlock.a, this.position);
   }

   public byte getLightFromBlocks() {
      return (byte)this.world.a(EnumSkyBlock.b, this.position);
   }

   public Block getFace(BlockFace face) {
      return this.getRelative(face, 1);
   }

   public Block getFace(BlockFace face, int distance) {
      return this.getRelative(face, distance);
   }

   public Block getRelative(int modX, int modY, int modZ) {
      return this.getWorld().getBlockAt(this.getX() + modX, this.getY() + modY, this.getZ() + modZ);
   }

   public Block getRelative(BlockFace face) {
      return this.getRelative(face, 1);
   }

   public Block getRelative(BlockFace face, int distance) {
      return this.getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
   }

   public BlockFace getFace(Block block) {
      BlockFace[] values = BlockFace.values();

      for(BlockFace face : values) {
         if (this.getX() + face.getModX() == block.getX() && this.getY() + face.getModY() == block.getY() && this.getZ() + face.getModZ() == block.getZ()) {
            return face;
         }
      }

      return null;
   }

   @Override
   public String toString() {
      return "CraftBlock{pos=" + this.position + ",type=" + this.getType() + ",data=" + this.getNMS() + ",fluid=" + this.world.b_(this.position) + 125;
   }

   public static BlockFace notchToBlockFace(EnumDirection notch) {
      if (notch == null) {
         return BlockFace.SELF;
      } else {
         switch(notch) {
            case a:
               return BlockFace.DOWN;
            case b:
               return BlockFace.UP;
            case c:
               return BlockFace.NORTH;
            case d:
               return BlockFace.SOUTH;
            case e:
               return BlockFace.WEST;
            case f:
               return BlockFace.EAST;
            default:
               return BlockFace.SELF;
         }
      }
   }

   public static EnumDirection blockFaceToNotch(BlockFace face) {
      switch(face) {
         case NORTH:
            return EnumDirection.c;
         case EAST:
            return EnumDirection.f;
         case SOUTH:
            return EnumDirection.d;
         case WEST:
            return EnumDirection.e;
         case UP:
            return EnumDirection.b;
         case DOWN:
            return EnumDirection.a;
         default:
            return null;
      }
   }

   public BlockState getState() {
      return CraftBlockStates.getBlockState(this);
   }

   public Biome getBiome() {
      return this.getWorld().getBiome(this.getX(), this.getY(), this.getZ());
   }

   public void setBiome(Biome bio) {
      this.getWorld().setBiome(this.getX(), this.getY(), this.getZ(), bio);
   }

   public static Biome biomeBaseToBiome(IRegistry<BiomeBase> registry, Holder<BiomeBase> base) {
      return biomeBaseToBiome(registry, base.a());
   }

   public static Biome biomeBaseToBiome(IRegistry<BiomeBase> registry, BiomeBase base) {
      if (base == null) {
         return null;
      } else {
         Biome biome = (Biome)Registry.BIOME.get(CraftNamespacedKey.fromMinecraft(registry.b(base)));
         return biome == null ? Biome.CUSTOM : biome;
      }
   }

   public static Holder<BiomeBase> biomeToBiomeBase(IRegistry<BiomeBase> registry, Biome bio) {
      return bio != null && bio != Biome.CUSTOM ? registry.f(ResourceKey.a(Registries.an, CraftNamespacedKey.toMinecraft(bio.getKey()))) : null;
   }

   public double getTemperature() {
      return (double)this.world.v(this.position).a().f(this.position);
   }

   public double getHumidity() {
      return this.getWorld().getHumidity(this.getX(), this.getY(), this.getZ());
   }

   public boolean isBlockPowered() {
      return this.world.getMinecraftWorld().q(this.position) > 0;
   }

   public boolean isBlockIndirectlyPowered() {
      return this.world.getMinecraftWorld().r(this.position);
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof CraftBlock)) {
         return false;
      } else {
         CraftBlock other = (CraftBlock)o;
         return this.position.equals(other.position) && this.getWorld().equals(other.getWorld());
      }
   }

   @Override
   public int hashCode() {
      return this.position.hashCode() ^ this.getWorld().hashCode();
   }

   public boolean isBlockFacePowered(BlockFace face) {
      return this.world.getMinecraftWorld().a(this.position, blockFaceToNotch(face));
   }

   public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
      int power = this.world.getMinecraftWorld().b(this.position, blockFaceToNotch(face));
      Block relative = this.getRelative(face);
      if (relative.getType() == Material.REDSTONE_WIRE) {
         return Math.max(power, relative.getData()) > 0;
      } else {
         return power > 0;
      }
   }

   public int getBlockPower(BlockFace face) {
      int power = 0;
      net.minecraft.world.level.World world = this.world.getMinecraftWorld();
      int x = this.getX();
      int y = this.getY();
      int z = this.getZ();
      if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.a(new BlockPosition(x, y - 1, z), EnumDirection.a)) {
         power = getPower(power, world.a_(new BlockPosition(x, y - 1, z)));
      }

      if ((face == BlockFace.UP || face == BlockFace.SELF) && world.a(new BlockPosition(x, y + 1, z), EnumDirection.b)) {
         power = getPower(power, world.a_(new BlockPosition(x, y + 1, z)));
      }

      if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.a(new BlockPosition(x + 1, y, z), EnumDirection.f)) {
         power = getPower(power, world.a_(new BlockPosition(x + 1, y, z)));
      }

      if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.a(new BlockPosition(x - 1, y, z), EnumDirection.e)) {
         power = getPower(power, world.a_(new BlockPosition(x - 1, y, z)));
      }

      if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.a(new BlockPosition(x, y, z - 1), EnumDirection.c)) {
         power = getPower(power, world.a_(new BlockPosition(x, y, z - 1)));
      }

      if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.a(new BlockPosition(x, y, z + 1), EnumDirection.d)) {
         power = getPower(power, world.a_(new BlockPosition(x, y, z + 1)));
      }

      return power > 0 ? power : ((face == BlockFace.SELF ? !this.isBlockIndirectlyPowered() : !this.isBlockFaceIndirectlyPowered(face)) ? 0 : 15);
   }

   private static int getPower(int i, IBlockData iblockdata) {
      if (!iblockdata.a(Blocks.cv)) {
         return i;
      } else {
         int j = iblockdata.c(BlockRedstoneWire.e);
         return j > i ? j : i;
      }
   }

   public int getBlockPower() {
      return this.getBlockPower(BlockFace.SELF);
   }

   public boolean isEmpty() {
      return this.getNMS().h();
   }

   public boolean isLiquid() {
      return this.getNMS().d().a();
   }

   public PistonMoveReaction getPistonMoveReaction() {
      return PistonMoveReaction.getById(this.getNMS().l().ordinal());
   }

   public boolean breakNaturally() {
      return this.breakNaturally(null);
   }

   public boolean breakNaturally(ItemStack item) {
      IBlockData iblockdata = this.getNMS();
      net.minecraft.world.level.block.Block block = iblockdata.b();
      net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
      boolean result = false;
      if (block != Blocks.a && (item == null || !iblockdata.v() || nmsItem.b(iblockdata))) {
         net.minecraft.world.level.block.Block.a(iblockdata, this.world.getMinecraftWorld(), this.position, this.world.c_(this.position), null, nmsItem);
         result = true;
      }

      return this.world.a(this.position, Blocks.a.o(), 3) && result;
   }

   public boolean applyBoneMeal(BlockFace face) {
      EnumDirection direction = blockFaceToNotch(face);
      BlockFertilizeEvent event = null;
      WorldServer world = this.getCraftWorld().getHandle();
      ItemActionContext context = new ItemActionContext(
         world, null, EnumHand.a, Items.qG.ad_(), new MovingObjectPositionBlock(Vec3D.b, direction, this.getPosition(), false)
      );
      world.captureTreeGeneration = true;
      EnumInteractionResult result = ItemBoneMeal.applyBonemeal(context);
      world.captureTreeGeneration = false;
      if (world.capturedBlockStates.size() > 0) {
         TreeType treeType = BlockSapling.treeType;
         BlockSapling.treeType = null;
         List<BlockState> blocks = new ArrayList<>(world.capturedBlockStates.values());
         world.capturedBlockStates.clear();
         StructureGrowEvent structureEvent = null;
         if (treeType != null) {
            structureEvent = new StructureGrowEvent(this.getLocation(), treeType, true, null, blocks);
            Bukkit.getPluginManager().callEvent(structureEvent);
         }

         event = new BlockFertilizeEvent(at(world, this.getPosition()), null, blocks);
         event.setCancelled(structureEvent != null && structureEvent.isCancelled());
         Bukkit.getPluginManager().callEvent(event);
         if (!event.isCancelled()) {
            for(BlockState blockstate : blocks) {
               blockstate.update(true);
            }
         }
      }

      return result == EnumInteractionResult.a && (event == null || !event.isCancelled());
   }

   public Collection<ItemStack> getDrops() {
      return this.getDrops(null);
   }

   public Collection<ItemStack> getDrops(ItemStack item) {
      return this.getDrops(item, null);
   }

   public Collection<ItemStack> getDrops(ItemStack item, Entity entity) {
      IBlockData iblockdata = this.getNMS();
      net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(item);
      return (Collection<ItemStack>)(item != null && !CraftBlockData.isPreferredTool(iblockdata, nms)
         ? Collections.emptyList()
         : net.minecraft.world.level.block.Block.a(
               iblockdata,
               this.world.getMinecraftWorld(),
               this.position,
               this.world.c_(this.position),
               entity == null ? null : ((CraftEntity)entity).getHandle(),
               nms
            )
            .stream()
            .map(CraftItemStack::asBukkitCopy)
            .collect(Collectors.toList()));
   }

   public boolean isPreferredTool(ItemStack item) {
      IBlockData iblockdata = this.getNMS();
      net.minecraft.world.item.ItemStack nms = CraftItemStack.asNMSCopy(item);
      return CraftBlockData.isPreferredTool(iblockdata, nms);
   }

   public float getBreakSpeed(Player player) {
      Preconditions.checkArgument(player != null, "player cannot be null");
      return this.getNMS().a(((CraftPlayer)player).getHandle(), this.world, this.position);
   }

   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
      this.getCraftWorld().getBlockMetadata().setMetadata((Block)this, metadataKey, newMetadataValue);
   }

   public List<MetadataValue> getMetadata(String metadataKey) {
      return this.getCraftWorld().getBlockMetadata().getMetadata((Block)this, metadataKey);
   }

   public boolean hasMetadata(String metadataKey) {
      return this.getCraftWorld().getBlockMetadata().hasMetadata((Block)this, metadataKey);
   }

   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
      this.getCraftWorld().getBlockMetadata().removeMetadata((Block)this, metadataKey, owningPlugin);
   }

   public boolean isPassable() {
      return this.getNMS().k(this.world, this.position).b();
   }

   public RayTraceResult rayTrace(Location start, Vector direction, double maxDistance, FluidCollisionMode fluidCollisionMode) {
      Validate.notNull(start, "Start location is null!");
      Validate.isTrue(this.getWorld().equals(start.getWorld()), "Start location is from different world!");
      start.checkFinite();
      Validate.notNull(direction, "Direction is null!");
      direction.checkFinite();
      Validate.isTrue(direction.lengthSquared() > 0.0, "Direction's magnitude is 0!");
      Validate.notNull(fluidCollisionMode, "Fluid collision mode is null!");
      if (maxDistance < 0.0) {
         return null;
      } else {
         Vector dir = direction.clone().normalize().multiply(maxDistance);
         Vec3D startPos = new Vec3D(start.getX(), start.getY(), start.getZ());
         Vec3D endPos = new Vec3D(start.getX() + dir.getX(), start.getY() + dir.getY(), start.getZ() + dir.getZ());
         MovingObjectPosition nmsHitResult = this.world
            .clip(new RayTrace(startPos, endPos, RayTrace.BlockCollisionOption.b, CraftFluidCollisionMode.toNMS(fluidCollisionMode), null), this.position);
         return CraftRayTraceResult.fromNMS(this.getWorld(), nmsHitResult);
      }
   }

   public BoundingBox getBoundingBox() {
      VoxelShape shape = this.getNMS().j(this.world, this.position);
      if (shape.b()) {
         return new BoundingBox();
      } else {
         AxisAlignedBB aabb = shape.a();
         return new BoundingBox(
            (double)this.getX() + aabb.a,
            (double)this.getY() + aabb.b,
            (double)this.getZ() + aabb.c,
            (double)this.getX() + aabb.d,
            (double)this.getY() + aabb.e,
            (double)this.getZ() + aabb.f
         );
      }
   }

   public org.bukkit.util.VoxelShape getCollisionShape() {
      VoxelShape shape = this.getNMS().k(this.world, this.position);
      return new CraftVoxelShape(shape);
   }

   public boolean canPlace(BlockData data) {
      Preconditions.checkArgument(data != null, "Provided block data is null!");
      IBlockData iblockdata = ((CraftBlockData)data).getState();
      net.minecraft.world.level.World world = this.world.getMinecraftWorld();
      return iblockdata.a((IWorldReader)world, this.position);
   }

   public String getTranslationKey() {
      return this.getNMS().b().h();
   }
}
