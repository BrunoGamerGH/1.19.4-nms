package org.bukkit.craftbukkit.v1_19_R3.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockEntityState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;

public class BlockStateListPopulator extends DummyGeneratorAccess {
   private final GeneratorAccess world;
   private final Map<BlockPosition, IBlockData> dataMap = new HashMap<>();
   private final Map<BlockPosition, TileEntity> entityMap = new HashMap<>();
   private final LinkedHashMap<BlockPosition, CraftBlockState> list;

   public BlockStateListPopulator(GeneratorAccess world) {
      this(world, new LinkedHashMap<>());
   }

   private BlockStateListPopulator(GeneratorAccess world, LinkedHashMap<BlockPosition, CraftBlockState> list) {
      this.world = world;
      this.list = list;
   }

   @Override
   public IBlockData a_(BlockPosition bp) {
      IBlockData blockData = this.dataMap.get(bp);
      return blockData != null ? blockData : this.world.a_(bp);
   }

   @Override
   public Fluid b_(BlockPosition bp) {
      IBlockData blockData = this.dataMap.get(bp);
      return blockData != null ? blockData.r() : this.world.b_(bp);
   }

   @Override
   public TileEntity c_(BlockPosition blockposition) {
      return this.entityMap.containsKey(blockposition) ? this.entityMap.get(blockposition) : this.world.c_(blockposition);
   }

   @Override
   public boolean a(BlockPosition position, IBlockData data, int flag) {
      position = position.i();
      this.list.remove(position);
      this.dataMap.put(position, data);
      if (data.q()) {
         this.entityMap.put(position, ((ITileEntity)data.b()).a(position, data));
      } else {
         this.entityMap.put(position, null);
      }

      CraftBlockState state = (CraftBlockState)CraftBlock.at(this, position).getState();
      state.setFlag(flag);
      state.setWorldHandle(this.world);
      this.list.put(position, state);
      return true;
   }

   @Override
   public WorldServer getMinecraftWorld() {
      return this.world.getMinecraftWorld();
   }

   public void refreshTiles() {
      for(CraftBlockState state : this.list.values()) {
         if (state instanceof CraftBlockEntityState) {
            ((CraftBlockEntityState)state).refreshSnapshot();
         }
      }
   }

   public void updateList() {
      for(BlockState state : this.list.values()) {
         state.update(true);
      }
   }

   public Set<BlockPosition> getBlocks() {
      return this.list.keySet();
   }

   public List<CraftBlockState> getList() {
      return new ArrayList<>(this.list.values());
   }

   public GeneratorAccess getWorld() {
      return this.world;
   }

   @Override
   public int v_() {
      return this.getWorld().v_();
   }

   @Override
   public int w_() {
      return this.getWorld().w_();
   }

   @Override
   public boolean a(BlockPosition blockposition, Predicate<IBlockData> predicate) {
      return predicate.test(this.a_(blockposition));
   }

   @Override
   public boolean b(BlockPosition bp, Predicate<Fluid> prdct) {
      return this.world.b(bp, prdct);
   }

   @Override
   public DimensionManager q_() {
      return this.world.q_();
   }

   @Override
   public IRegistryCustom u_() {
      return this.world.u_();
   }
}
