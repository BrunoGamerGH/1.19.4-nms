package org.bukkit.craftbukkit.v1_19_R3.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRotation;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.craftbukkit.v1_19_R3.CraftRegionAccessor;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.util.RandomSourceWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.structure.Palette;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;

public class CraftStructure implements Structure {
   private final DefinedStructure structure;

   public CraftStructure(DefinedStructure structure) {
      this.structure = structure;
   }

   public void place(
      Location location, boolean includeEntities, StructureRotation structureRotation, Mirror mirror, int palette, float integrity, Random random
   ) {
      location.checkFinite();
      World world = location.getWorld();
      Validate.notNull(world, "location#getWorld() cannot be null", new Object[0]);
      BlockVector blockVector = new BlockVector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
      this.place(world, blockVector, includeEntities, structureRotation, mirror, palette, integrity, random);
   }

   public void place(
      RegionAccessor regionAccessor,
      BlockVector location,
      boolean includeEntities,
      StructureRotation structureRotation,
      Mirror mirror,
      int palette,
      float integrity,
      Random random
   ) {
      Validate.notNull(regionAccessor, "regionAccessor can not be null", new Object[0]);
      location.checkFinite();
      if (!(integrity < 0.0F) && !(integrity > 1.0F)) {
         RandomSource randomSource = new RandomSourceWrapper(random);
         DefinedStructureInfo definedstructureinfo = new DefinedStructureInfo()
            .a(EnumBlockMirror.valueOf(mirror.name()))
            .a(EnumBlockRotation.valueOf(structureRotation.name()))
            .a(!includeEntities)
            .a(new DefinedStructureProcessorRotation(integrity))
            .a(randomSource);
         definedstructureinfo.h = palette;
         BlockPosition blockPosition = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
         this.structure.a(((CraftRegionAccessor)regionAccessor).getHandle(), blockPosition, blockPosition, definedstructureinfo, randomSource, 2);
      } else {
         throw new IllegalArgumentException("Integrity must be between 0 and 1 inclusive. Was \"" + integrity + "\"");
      }
   }

   public void fill(Location corner1, Location corner2, boolean includeEntities) {
      Validate.notNull(corner1, "corner1 cannot be null", new Object[0]);
      Validate.notNull(corner2, "corner2 cannot be null", new Object[0]);
      World world = corner1.getWorld();
      Validate.notNull(world, "corner1#getWorld() cannot be null", new Object[0]);
      Location origin = new Location(
         world,
         (double)Math.min(corner1.getBlockX(), corner2.getBlockX()),
         (double)Math.min(corner1.getBlockY(), corner2.getBlockY()),
         (double)Math.min(corner1.getBlockZ(), corner2.getBlockZ())
      );
      BlockVector size = new BlockVector(
         Math.abs(corner1.getBlockX() - corner2.getBlockX()),
         Math.abs(corner1.getBlockY() - corner2.getBlockY()),
         Math.abs(corner1.getBlockZ() - corner2.getBlockZ())
      );
      this.fill(origin, size, includeEntities);
   }

   public void fill(Location origin, BlockVector size, boolean includeEntities) {
      Validate.notNull(origin, "origin cannot be null", new Object[0]);
      World world = origin.getWorld();
      Validate.notNull(world, "origin#getWorld() cannot be null", new Object[0]);
      Validate.notNull(size, "size cannot be null", new Object[0]);
      if (size.getBlockX() >= 1 && size.getBlockY() >= 1 && size.getBlockZ() >= 1) {
         this.structure
            .a(
               ((CraftWorld)world).getHandle(),
               new BlockPosition(origin.getBlockX(), origin.getBlockY(), origin.getBlockZ()),
               new BlockPosition(size.getBlockX(), size.getBlockY(), size.getBlockZ()),
               includeEntities,
               Blocks.kK
            );
      } else {
         throw new IllegalArgumentException("Size must be at least 1x1x1 but was " + size.getBlockX() + "x" + size.getBlockY() + "x" + size.getBlockZ());
      }
   }

   public BlockVector getSize() {
      return new BlockVector(this.structure.a().u(), this.structure.a().v(), this.structure.a().w());
   }

   public List<Entity> getEntities() {
      List<Entity> entities = new ArrayList();

      for(DefinedStructure.EntityInfo entity : this.structure.m) {
         EntityTypes.a(entity.c, ((CraftWorld)Bukkit.getServer().getWorlds().get(0)).getHandle()).ifPresent(dummyEntity -> {
            dummyEntity.e(entity.a.c, entity.a.d, entity.a.e);
            entities.add(dummyEntity.getBukkitEntity());
         });
      }

      return Collections.unmodifiableList(entities);
   }

   public int getEntityCount() {
      return this.structure.m.size();
   }

   public List<Palette> getPalettes() {
      return this.structure.l.stream().map(CraftPalette::new).collect(Collectors.toList());
   }

   public int getPaletteCount() {
      return this.structure.l.size();
   }

   public PersistentDataContainer getPersistentDataContainer() {
      return this.getHandle().persistentDataContainer;
   }

   public DefinedStructure getHandle() {
      return this.structure;
   }
}
