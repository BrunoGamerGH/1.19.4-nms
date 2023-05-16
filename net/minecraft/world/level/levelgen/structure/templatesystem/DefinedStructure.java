package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistryBlockID;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.BlockAccessAir;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.IFluidContainer;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShapeBitSet;
import net.minecraft.world.phys.shapes.VoxelShapeDiscrete;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;

public class DefinedStructure {
   public static final String a = "palette";
   public static final String b = "palettes";
   public static final String c = "entities";
   public static final String d = "blocks";
   public static final String e = "pos";
   public static final String f = "state";
   public static final String g = "nbt";
   public static final String h = "pos";
   public static final String i = "blockPos";
   public static final String j = "nbt";
   public static final String k = "size";
   public final List<DefinedStructure.a> l = Lists.newArrayList();
   public final List<DefinedStructure.EntityInfo> m = Lists.newArrayList();
   private BaseBlockPosition n;
   private String o;
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   public CraftPersistentDataContainer persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);

   public DefinedStructure() {
      this.n = BaseBlockPosition.g;
      this.o = "?";
   }

   public BaseBlockPosition a() {
      return this.n;
   }

   public void a(String s) {
      this.o = s;
   }

   public String b() {
      return this.o;
   }

   public void a(World world, BlockPosition blockposition, BaseBlockPosition baseblockposition, boolean flag, @Nullable Block block) {
      if (baseblockposition.u() >= 1 && baseblockposition.v() >= 1 && baseblockposition.w() >= 1) {
         BlockPosition blockposition1 = blockposition.a(baseblockposition).b(-1, -1, -1);
         List<DefinedStructure.BlockInfo> list = Lists.newArrayList();
         List<DefinedStructure.BlockInfo> list1 = Lists.newArrayList();
         List<DefinedStructure.BlockInfo> list2 = Lists.newArrayList();
         BlockPosition blockposition2 = new BlockPosition(
            Math.min(blockposition.u(), blockposition1.u()), Math.min(blockposition.v(), blockposition1.v()), Math.min(blockposition.w(), blockposition1.w())
         );
         BlockPosition blockposition3 = new BlockPosition(
            Math.max(blockposition.u(), blockposition1.u()), Math.max(blockposition.v(), blockposition1.v()), Math.max(blockposition.w(), blockposition1.w())
         );
         this.n = baseblockposition;

         for(BlockPosition blockposition4 : BlockPosition.a(blockposition2, blockposition3)) {
            BlockPosition blockposition5 = blockposition4.b(blockposition2);
            IBlockData iblockdata = world.a_(blockposition4);
            if (block == null || !iblockdata.a(block)) {
               TileEntity tileentity = world.c_(blockposition4);
               DefinedStructure.BlockInfo definedstructure_blockinfo;
               if (tileentity != null) {
                  definedstructure_blockinfo = new DefinedStructure.BlockInfo(blockposition5, iblockdata, tileentity.n());
               } else {
                  definedstructure_blockinfo = new DefinedStructure.BlockInfo(blockposition5, iblockdata, null);
               }

               a(definedstructure_blockinfo, list, list1, list2);
            }
         }

         List<DefinedStructure.BlockInfo> list3 = a(list, list1, list2);
         this.l.clear();
         this.l.add(new DefinedStructure.a(list3));
         if (flag) {
            this.a(world, blockposition2, blockposition3.b(1, 1, 1));
         } else {
            this.m.clear();
         }
      }
   }

   private static void a(
      DefinedStructure.BlockInfo definedstructure_blockinfo,
      List<DefinedStructure.BlockInfo> list,
      List<DefinedStructure.BlockInfo> list1,
      List<DefinedStructure.BlockInfo> list2
   ) {
      if (definedstructure_blockinfo.c != null) {
         list1.add(definedstructure_blockinfo);
      } else if (!definedstructure_blockinfo.b.b().p() && definedstructure_blockinfo.b.r(BlockAccessAir.a, BlockPosition.b)) {
         list.add(definedstructure_blockinfo);
      } else {
         list2.add(definedstructure_blockinfo);
      }
   }

   private static List<DefinedStructure.BlockInfo> a(
      List<DefinedStructure.BlockInfo> list, List<DefinedStructure.BlockInfo> list1, List<DefinedStructure.BlockInfo> list2
   ) {
      Comparator<DefinedStructure.BlockInfo> comparator = Comparator.<DefinedStructure.BlockInfo>comparingInt(
            definedstructure_blockinfo -> definedstructure_blockinfo.a.v()
         )
         .thenComparingInt(definedstructure_blockinfo -> definedstructure_blockinfo.a.u())
         .thenComparingInt(definedstructure_blockinfo -> definedstructure_blockinfo.a.w());
      list.sort(comparator);
      list2.sort(comparator);
      list1.sort(comparator);
      List<DefinedStructure.BlockInfo> list3 = Lists.newArrayList();
      list3.addAll(list);
      list3.addAll(list2);
      list3.addAll(list1);
      return list3;
   }

   private void a(World world, BlockPosition blockposition, BlockPosition blockposition1) {
      List<Entity> list = world.a(Entity.class, new AxisAlignedBB(blockposition, blockposition1), entityx -> !(entityx instanceof EntityHuman));
      this.m.clear();

      for(Entity entity : list) {
         Vec3D vec3d = new Vec3D(entity.dl() - (double)blockposition.u(), entity.dn() - (double)blockposition.v(), entity.dr() - (double)blockposition.w());
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         entity.e(nbttagcompound);
         BlockPosition blockposition2;
         if (entity instanceof EntityPainting) {
            blockposition2 = ((EntityPainting)entity).x().b(blockposition);
         } else {
            blockposition2 = BlockPosition.a(vec3d);
         }

         this.m.add(new DefinedStructure.EntityInfo(vec3d, blockposition2, nbttagcompound.h()));
      }
   }

   public List<DefinedStructure.BlockInfo> a(BlockPosition blockposition, DefinedStructureInfo definedstructureinfo, Block block) {
      return this.a(blockposition, definedstructureinfo, block, true);
   }

   public ObjectArrayList<DefinedStructure.BlockInfo> a(BlockPosition blockposition, DefinedStructureInfo definedstructureinfo, Block block, boolean flag) {
      ObjectArrayList<DefinedStructure.BlockInfo> objectarraylist = new ObjectArrayList();
      StructureBoundingBox structureboundingbox = definedstructureinfo.g();
      if (this.l.isEmpty()) {
         return objectarraylist;
      } else {
         for(DefinedStructure.BlockInfo definedstructure_blockinfo : definedstructureinfo.a(this.l, blockposition).a(block)) {
            BlockPosition blockposition1 = flag ? a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition) : definedstructure_blockinfo.a;
            if (structureboundingbox == null || structureboundingbox.b(blockposition1)) {
               objectarraylist.add(
                  new DefinedStructure.BlockInfo(blockposition1, definedstructure_blockinfo.b.a(definedstructureinfo.d()), definedstructure_blockinfo.c)
               );
            }
         }

         return objectarraylist;
      }
   }

   public BlockPosition a(
      DefinedStructureInfo definedstructureinfo, BlockPosition blockposition, DefinedStructureInfo definedstructureinfo1, BlockPosition blockposition1
   ) {
      BlockPosition blockposition2 = a(definedstructureinfo, blockposition);
      BlockPosition blockposition3 = a(definedstructureinfo1, blockposition1);
      return blockposition2.b(blockposition3);
   }

   public static BlockPosition a(DefinedStructureInfo definedstructureinfo, BlockPosition blockposition) {
      return a(blockposition, definedstructureinfo.c(), definedstructureinfo.d(), definedstructureinfo.e());
   }

   public boolean a(
      WorldAccess worldaccess,
      BlockPosition blockposition,
      BlockPosition blockposition1,
      DefinedStructureInfo definedstructureinfo,
      RandomSource randomsource,
      int i
   ) {
      if (this.l.isEmpty()) {
         return false;
      } else {
         List<DefinedStructure.BlockInfo> list = definedstructureinfo.a(this.l, blockposition).a();
         if ((!list.isEmpty() || !definedstructureinfo.f() && !this.m.isEmpty()) && this.n.u() >= 1 && this.n.v() >= 1 && this.n.w() >= 1) {
            StructureBoundingBox structureboundingbox = definedstructureinfo.g();
            List<BlockPosition> list1 = Lists.newArrayListWithCapacity(definedstructureinfo.j() ? list.size() : 0);
            List<BlockPosition> list2 = Lists.newArrayListWithCapacity(definedstructureinfo.j() ? list.size() : 0);
            List<Pair<BlockPosition, NBTTagCompound>> list3 = Lists.newArrayListWithCapacity(list.size());
            int j = Integer.MAX_VALUE;
            int k = Integer.MAX_VALUE;
            int l = Integer.MAX_VALUE;
            int i1 = Integer.MIN_VALUE;
            int j1 = Integer.MIN_VALUE;
            int k1 = Integer.MIN_VALUE;

            for(DefinedStructure.BlockInfo definedstructure_blockinfo : a(worldaccess, blockposition, blockposition1, definedstructureinfo, list)) {
               BlockPosition blockposition2 = definedstructure_blockinfo.a;
               if (structureboundingbox == null || structureboundingbox.b(blockposition2)) {
                  Fluid fluid = definedstructureinfo.j() ? worldaccess.b_(blockposition2) : null;
                  IBlockData iblockdata = definedstructure_blockinfo.b.a(definedstructureinfo.c()).a(definedstructureinfo.d());
                  if (definedstructure_blockinfo.c != null) {
                     TileEntity tileentity = worldaccess.c_(blockposition2);
                     Clearable.a_(tileentity);
                     worldaccess.a(blockposition2, Blocks.hV.o(), 20);
                  }

                  if (worldaccess.a(blockposition2, iblockdata, i)) {
                     j = Math.min(j, blockposition2.u());
                     k = Math.min(k, blockposition2.v());
                     l = Math.min(l, blockposition2.w());
                     i1 = Math.max(i1, blockposition2.u());
                     j1 = Math.max(j1, blockposition2.v());
                     k1 = Math.max(k1, blockposition2.w());
                     list3.add(Pair.of(blockposition2, definedstructure_blockinfo.c));
                     if (definedstructure_blockinfo.c != null) {
                        TileEntity tileentity = worldaccess.c_(blockposition2);
                        if (tileentity != null) {
                           if (tileentity instanceof TileEntityLootable) {
                              definedstructure_blockinfo.c.a("LootTableSeed", randomsource.g());
                           }

                           tileentity.a(definedstructure_blockinfo.c);
                        }
                     }

                     if (fluid != null) {
                        if (iblockdata.r().b()) {
                           list2.add(blockposition2);
                        } else if (iblockdata.b() instanceof IFluidContainer) {
                           ((IFluidContainer)iblockdata.b()).a(worldaccess, blockposition2, iblockdata, fluid);
                           if (!fluid.b()) {
                              list1.add(blockposition2);
                           }
                        }
                     }
                  }
               }
            }

            boolean flag = true;
            EnumDirection[] aenumdirection = new EnumDirection[]{EnumDirection.b, EnumDirection.c, EnumDirection.f, EnumDirection.d, EnumDirection.e};

            while(flag && !list1.isEmpty()) {
               flag = false;
               Iterator iterator1 = list1.iterator();

               while(iterator1.hasNext()) {
                  BlockPosition blockposition3 = (BlockPosition)iterator1.next();
                  Fluid fluid1 = worldaccess.b_(blockposition3);

                  for(int l1 = 0; l1 < aenumdirection.length && !fluid1.b(); ++l1) {
                     BlockPosition blockposition4 = blockposition3.a(aenumdirection[l1]);
                     Fluid fluid2 = worldaccess.b_(blockposition4);
                     if (fluid2.b() && !list2.contains(blockposition4)) {
                        fluid1 = fluid2;
                     }
                  }

                  if (fluid1.b()) {
                     IBlockData iblockdata1 = worldaccess.a_(blockposition3);
                     Block block = iblockdata1.b();
                     if (block instanceof IFluidContainer) {
                        ((IFluidContainer)block).a(worldaccess, blockposition3, iblockdata1, fluid1);
                        flag = true;
                        iterator1.remove();
                     }
                  }
               }
            }

            if (j <= i1) {
               if (!definedstructureinfo.h()) {
                  VoxelShapeBitSet voxelshapebitset = new VoxelShapeBitSet(i1 - j + 1, j1 - k + 1, k1 - l + 1);
                  int i2 = j;
                  int j2 = k;
                  int l1 = l;

                  for(Pair<BlockPosition, NBTTagCompound> pair : list3) {
                     BlockPosition blockposition5 = (BlockPosition)pair.getFirst();
                     voxelshapebitset.c(blockposition5.u() - i2, blockposition5.v() - j2, blockposition5.w() - l1);
                  }

                  a(worldaccess, i, voxelshapebitset, i2, j2, l1);
               }

               for(Pair<BlockPosition, NBTTagCompound> pair1 : list3) {
                  BlockPosition blockposition6 = (BlockPosition)pair1.getFirst();
                  if (!definedstructureinfo.h()) {
                     IBlockData iblockdata1 = worldaccess.a_(blockposition6);
                     IBlockData iblockdata2 = Block.b(iblockdata1, worldaccess, blockposition6);
                     if (iblockdata1 != iblockdata2) {
                        worldaccess.a(blockposition6, iblockdata2, i & -2 | 16);
                     }

                     worldaccess.b(blockposition6, iblockdata2.b());
                  }

                  if (pair1.getSecond() != null) {
                     TileEntity tileentity = worldaccess.c_(blockposition6);
                     if (tileentity != null) {
                        tileentity.e();
                     }
                  }
               }
            }

            if (!definedstructureinfo.f()) {
               this.a(
                  worldaccess,
                  blockposition,
                  definedstructureinfo.c(),
                  definedstructureinfo.d(),
                  definedstructureinfo.e(),
                  structureboundingbox,
                  definedstructureinfo.k()
               );
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public static void a(GeneratorAccess generatoraccess, int i, VoxelShapeDiscrete voxelshapediscrete, int j, int k, int l) {
      voxelshapediscrete.a((enumdirection, i1, j1, k1) -> {
         BlockPosition blockposition = new BlockPosition(j + i1, k + j1, l + k1);
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         IBlockData iblockdata = generatoraccess.a_(blockposition);
         IBlockData iblockdata1 = generatoraccess.a_(blockposition1);
         IBlockData iblockdata2 = iblockdata.a(enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
         if (iblockdata != iblockdata2) {
            generatoraccess.a(blockposition, iblockdata2, i & -2);
         }

         IBlockData iblockdata3 = iblockdata1.a(enumdirection.g(), iblockdata2, generatoraccess, blockposition1, blockposition);
         if (iblockdata1 != iblockdata3) {
            generatoraccess.a(blockposition1, iblockdata3, i & -2);
         }
      });
   }

   public static List<DefinedStructure.BlockInfo> a(
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1,
      DefinedStructureInfo definedstructureinfo,
      List<DefinedStructure.BlockInfo> list
   ) {
      List<DefinedStructure.BlockInfo> list1 = Lists.newArrayList();

      for(DefinedStructure.BlockInfo definedstructure_blockinfo : list) {
         BlockPosition blockposition2 = a(definedstructureinfo, definedstructure_blockinfo.a).a(blockposition);
         DefinedStructure.BlockInfo definedstructure_blockinfo1 = new DefinedStructure.BlockInfo(
            blockposition2, definedstructure_blockinfo.b, definedstructure_blockinfo.c != null ? definedstructure_blockinfo.c.h() : null
         );
         Iterator iterator1 = definedstructureinfo.i().iterator();

         while(definedstructure_blockinfo1 != null && iterator1.hasNext()) {
            definedstructure_blockinfo1 = ((DefinedStructureProcessor)iterator1.next())
               .a(generatoraccess, blockposition, blockposition1, definedstructure_blockinfo, definedstructure_blockinfo1, definedstructureinfo);
         }

         if (definedstructure_blockinfo1 != null) {
            list1.add(definedstructure_blockinfo1);
         }
      }

      for(DefinedStructureProcessor definedstructureprocessor : definedstructureinfo.i()) {
         definedstructureprocessor.a(generatoraccess, blockposition, blockposition1, definedstructureinfo, list1);
      }

      return list1;
   }

   private void a(
      WorldAccess worldaccess,
      BlockPosition blockposition,
      EnumBlockMirror enumblockmirror,
      EnumBlockRotation enumblockrotation,
      BlockPosition blockposition1,
      @Nullable StructureBoundingBox structureboundingbox,
      boolean flag
   ) {
      for(DefinedStructure.EntityInfo definedstructure_entityinfo : this.m) {
         BlockPosition blockposition2 = a(definedstructure_entityinfo.b, enumblockmirror, enumblockrotation, blockposition1).a(blockposition);
         if (structureboundingbox == null || structureboundingbox.b(blockposition2)) {
            NBTTagCompound nbttagcompound = definedstructure_entityinfo.c.h();
            Vec3D vec3d = a(definedstructure_entityinfo.a, enumblockmirror, enumblockrotation, blockposition1);
            Vec3D vec3d1 = vec3d.b((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
            NBTTagList nbttaglist = new NBTTagList();
            nbttaglist.add(NBTTagDouble.a(vec3d1.c));
            nbttaglist.add(NBTTagDouble.a(vec3d1.d));
            nbttaglist.add(NBTTagDouble.a(vec3d1.e));
            nbttagcompound.a("Pos", nbttaglist);
            nbttagcompound.r("UUID");
            a(worldaccess, nbttagcompound).ifPresent(entity -> {
               float f = entity.a(enumblockrotation);
               f += entity.a(enumblockmirror) - entity.dw();
               entity.b(vec3d1.c, vec3d1.d, vec3d1.e, f, entity.dy());
               if (flag && entity instanceof EntityInsentient) {
                  ((EntityInsentient)entity).a(worldaccess, worldaccess.d_(BlockPosition.a(vec3d1)), EnumMobSpawn.d, null, nbttagcompound);
               }

               worldaccess.a_(entity);
            });
         }
      }
   }

   private static Optional<Entity> a(WorldAccess worldaccess, NBTTagCompound nbttagcompound) {
      return EntityTypes.a(nbttagcompound, worldaccess.C());
   }

   public BaseBlockPosition a(EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case b:
         case d:
            return new BaseBlockPosition(this.n.w(), this.n.v(), this.n.u());
         case c:
         default:
            return this.n;
      }
   }

   public static BlockPosition a(
      BlockPosition blockposition, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation, BlockPosition blockposition1
   ) {
      int i = blockposition.u();
      int j = blockposition.v();
      int k = blockposition.w();
      boolean flag = true;
      switch(enumblockmirror) {
         case b:
            k = -k;
            break;
         case c:
            i = -i;
            break;
         default:
            flag = false;
      }

      int l = blockposition1.u();
      int i1 = blockposition1.w();
      switch(enumblockrotation) {
         case b:
            return new BlockPosition(l + i1 - k, j, i1 - l + i);
         case c:
            return new BlockPosition(l + l - i, j, i1 + i1 - k);
         case d:
            return new BlockPosition(l - i1 + k, j, l + i1 - i);
         default:
            return flag ? new BlockPosition(i, j, k) : blockposition;
      }
   }

   public static Vec3D a(Vec3D vec3d, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation, BlockPosition blockposition) {
      double d0 = vec3d.c;
      double d1 = vec3d.d;
      double d2 = vec3d.e;
      boolean flag = true;
      switch(enumblockmirror) {
         case b:
            d2 = 1.0 - d2;
            break;
         case c:
            d0 = 1.0 - d0;
            break;
         default:
            flag = false;
      }

      int i = blockposition.u();
      int j = blockposition.w();
      switch(enumblockrotation) {
         case b:
            return new Vec3D((double)(i + j + 1) - d2, d1, (double)(j - i) + d0);
         case c:
            return new Vec3D((double)(i + i + 1) - d0, d1, (double)(j + j + 1) - d2);
         case d:
            return new Vec3D((double)(i - j) + d2, d1, (double)(i + j + 1) - d0);
         default:
            return flag ? new Vec3D(d0, d1, d2) : vec3d;
      }
   }

   public BlockPosition a(BlockPosition blockposition, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation) {
      return a(blockposition, enumblockmirror, enumblockrotation, this.a().u(), this.a().w());
   }

   public static BlockPosition a(BlockPosition blockposition, EnumBlockMirror enumblockmirror, EnumBlockRotation enumblockrotation, int i, int j) {
      --i;
      --j;
      int k = enumblockmirror == EnumBlockMirror.c ? i : 0;
      int l = enumblockmirror == EnumBlockMirror.b ? j : 0;
      BlockPosition blockposition1 = blockposition;
      switch(enumblockrotation) {
         case a:
            blockposition1 = blockposition.b(k, 0, l);
            break;
         case b:
            blockposition1 = blockposition.b(j - l, 0, k);
            break;
         case c:
            blockposition1 = blockposition.b(i - k, 0, j - l);
            break;
         case d:
            blockposition1 = blockposition.b(l, 0, i - k);
      }

      return blockposition1;
   }

   public StructureBoundingBox b(DefinedStructureInfo definedstructureinfo, BlockPosition blockposition) {
      return this.a(blockposition, definedstructureinfo.d(), definedstructureinfo.e(), definedstructureinfo.c());
   }

   public StructureBoundingBox a(
      BlockPosition blockposition, EnumBlockRotation enumblockrotation, BlockPosition blockposition1, EnumBlockMirror enumblockmirror
   ) {
      return a(blockposition, enumblockrotation, blockposition1, enumblockmirror, this.n);
   }

   @VisibleForTesting
   protected static StructureBoundingBox a(
      BlockPosition blockposition,
      EnumBlockRotation enumblockrotation,
      BlockPosition blockposition1,
      EnumBlockMirror enumblockmirror,
      BaseBlockPosition baseblockposition
   ) {
      BaseBlockPosition baseblockposition1 = baseblockposition.c(-1, -1, -1);
      BlockPosition blockposition2 = a(BlockPosition.b, enumblockmirror, enumblockrotation, blockposition1);
      BlockPosition blockposition3 = a(BlockPosition.b.a(baseblockposition1), enumblockmirror, enumblockrotation, blockposition1);
      return StructureBoundingBox.a(blockposition2, blockposition3).a((BaseBlockPosition)blockposition);
   }

   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      if (this.l.isEmpty()) {
         nbttagcompound.a("blocks", new NBTTagList());
         nbttagcompound.a("palette", new NBTTagList());
      } else {
         List<DefinedStructure.b> list = Lists.newArrayList();
         DefinedStructure.b definedstructure_b = new DefinedStructure.b();
         list.add(definedstructure_b);

         for(int i = 1; i < this.l.size(); ++i) {
            list.add(new DefinedStructure.b());
         }

         NBTTagList nbttaglist = new NBTTagList();
         List<DefinedStructure.BlockInfo> list1 = this.l.get(0).a();

         for(int j = 0; j < list1.size(); ++j) {
            DefinedStructure.BlockInfo definedstructure_blockinfo = list1.get(j);
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.a("pos", this.a(definedstructure_blockinfo.a.u(), definedstructure_blockinfo.a.v(), definedstructure_blockinfo.a.w()));
            int k = definedstructure_b.a(definedstructure_blockinfo.b);
            nbttagcompound1.a("state", k);
            if (definedstructure_blockinfo.c != null) {
               nbttagcompound1.a("nbt", definedstructure_blockinfo.c);
            }

            nbttaglist.add(nbttagcompound1);

            for(int l = 1; l < this.l.size(); ++l) {
               DefinedStructure.b definedstructure_b1 = list.get(l);
               definedstructure_b1.a(this.l.get(l).a().get(j).b, k);
            }
         }

         nbttagcompound.a("blocks", nbttaglist);
         if (list.size() == 1) {
            NBTTagList nbttaglist1 = new NBTTagList();

            for(IBlockData iblockdata : definedstructure_b) {
               nbttaglist1.add(GameProfileSerializer.a(iblockdata));
            }

            nbttagcompound.a("palette", nbttaglist1);
         } else {
            NBTTagList nbttaglist1 = new NBTTagList();

            for(DefinedStructure.b definedstructure_b2 : list) {
               NBTTagList nbttaglist2 = new NBTTagList();

               for(IBlockData iblockdata1 : definedstructure_b2) {
                  nbttaglist2.add(GameProfileSerializer.a(iblockdata1));
               }

               nbttaglist1.add(nbttaglist2);
            }

            nbttagcompound.a("palettes", nbttaglist1);
         }
      }

      NBTTagList nbttaglist3 = new NBTTagList();

      for(DefinedStructure.EntityInfo definedstructure_entityinfo : this.m) {
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
         nbttagcompound2.a("pos", this.a(definedstructure_entityinfo.a.c, definedstructure_entityinfo.a.d, definedstructure_entityinfo.a.e));
         nbttagcompound2.a("blockPos", this.a(definedstructure_entityinfo.b.u(), definedstructure_entityinfo.b.v(), definedstructure_entityinfo.b.w()));
         if (definedstructure_entityinfo.c != null) {
            nbttagcompound2.a("nbt", definedstructure_entityinfo.c);
         }

         nbttaglist3.add(nbttagcompound2);
      }

      nbttagcompound.a("entities", nbttaglist3);
      nbttagcompound.a("size", this.a(this.n.u(), this.n.v(), this.n.w()));
      if (!this.persistentDataContainer.isEmpty()) {
         nbttagcompound.a("BukkitValues", this.persistentDataContainer.toTagCompound());
      }

      return GameProfileSerializer.g(nbttagcompound);
   }

   public void a(HolderGetter<Block> holdergetter, NBTTagCompound nbttagcompound) {
      this.l.clear();
      this.m.clear();
      NBTTagList nbttaglist = nbttagcompound.c("size", 3);
      this.n = new BaseBlockPosition(nbttaglist.e(0), nbttaglist.e(1), nbttaglist.e(2));
      NBTTagList nbttaglist1 = nbttagcompound.c("blocks", 10);
      if (nbttagcompound.b("palettes", 9)) {
         NBTTagList nbttaglist2 = nbttagcompound.c("palettes", 9);

         for(int i = 0; i < nbttaglist2.size(); ++i) {
            this.a(holdergetter, nbttaglist2.b(i), nbttaglist1);
         }
      } else {
         this.a(holdergetter, nbttagcompound.c("palette", 10), nbttaglist1);
      }

      NBTTagList nbttaglist2 = nbttagcompound.c("entities", 10);

      for(int i = 0; i < nbttaglist2.size(); ++i) {
         NBTTagCompound nbttagcompound1 = nbttaglist2.a(i);
         NBTTagList nbttaglist3 = nbttagcompound1.c("pos", 6);
         Vec3D vec3d = new Vec3D(nbttaglist3.h(0), nbttaglist3.h(1), nbttaglist3.h(2));
         NBTTagList nbttaglist4 = nbttagcompound1.c("blockPos", 3);
         BlockPosition blockposition = new BlockPosition(nbttaglist4.e(0), nbttaglist4.e(1), nbttaglist4.e(2));
         if (nbttagcompound1.e("nbt")) {
            NBTTagCompound nbttagcompound2 = nbttagcompound1.p("nbt");
            this.m.add(new DefinedStructure.EntityInfo(vec3d, blockposition, nbttagcompound2));
         }
      }

      NBTBase base = nbttagcompound.c("BukkitValues");
      if (base instanceof NBTTagCompound) {
         this.persistentDataContainer.putAll((NBTTagCompound)base);
      }
   }

   private void a(HolderGetter<Block> holdergetter, NBTTagList nbttaglist, NBTTagList nbttaglist1) {
      DefinedStructure.b definedstructure_b = new DefinedStructure.b();

      for(int i = 0; i < nbttaglist.size(); ++i) {
         definedstructure_b.a(GameProfileSerializer.a(holdergetter, nbttaglist.a(i)), i);
      }

      List<DefinedStructure.BlockInfo> list = Lists.newArrayList();
      List<DefinedStructure.BlockInfo> list1 = Lists.newArrayList();
      List<DefinedStructure.BlockInfo> list2 = Lists.newArrayList();

      for(int j = 0; j < nbttaglist1.size(); ++j) {
         NBTTagCompound nbttagcompound = nbttaglist1.a(j);
         NBTTagList nbttaglist2 = nbttagcompound.c("pos", 3);
         BlockPosition blockposition = new BlockPosition(nbttaglist2.e(0), nbttaglist2.e(1), nbttaglist2.e(2));
         IBlockData iblockdata = definedstructure_b.a(nbttagcompound.h("state"));
         NBTTagCompound nbttagcompound1;
         if (nbttagcompound.e("nbt")) {
            nbttagcompound1 = nbttagcompound.p("nbt");
         } else {
            nbttagcompound1 = null;
         }

         DefinedStructure.BlockInfo definedstructure_blockinfo = new DefinedStructure.BlockInfo(blockposition, iblockdata, nbttagcompound1);
         a(definedstructure_blockinfo, list, list1, list2);
      }

      List<DefinedStructure.BlockInfo> list3 = a(list, list1, list2);
      this.l.add(new DefinedStructure.a(list3));
   }

   private NBTTagList a(int... aint) {
      NBTTagList nbttaglist = new NBTTagList();

      for(int k : aint) {
         nbttaglist.add(NBTTagInt.a(k));
      }

      return nbttaglist;
   }

   private NBTTagList a(double... adouble) {
      NBTTagList nbttaglist = new NBTTagList();

      for(double d0 : adouble) {
         nbttaglist.add(NBTTagDouble.a(d0));
      }

      return nbttaglist;
   }

   public static class BlockInfo {
      public final BlockPosition a;
      public final IBlockData b;
      public final NBTTagCompound c;

      public BlockInfo(BlockPosition blockposition, IBlockData iblockdata, @Nullable NBTTagCompound nbttagcompound) {
         this.a = blockposition;
         this.b = iblockdata;
         this.c = nbttagcompound;
      }

      @Override
      public String toString() {
         return String.format(Locale.ROOT, "<StructureBlockInfo | %s | %s | %s>", this.a, this.b, this.c);
      }
   }

   public static class EntityInfo {
      public final Vec3D a;
      public final BlockPosition b;
      public final NBTTagCompound c;

      public EntityInfo(Vec3D vec3d, BlockPosition blockposition, NBTTagCompound nbttagcompound) {
         this.a = vec3d;
         this.b = blockposition;
         this.c = nbttagcompound;
      }
   }

   public static final class a {
      private final List<DefinedStructure.BlockInfo> a;
      private final Map<Block, List<DefinedStructure.BlockInfo>> b = Maps.newHashMap();

      a(List<DefinedStructure.BlockInfo> list) {
         this.a = list;
      }

      public List<DefinedStructure.BlockInfo> a() {
         return this.a;
      }

      public List<DefinedStructure.BlockInfo> a(Block block) {
         return this.b
            .computeIfAbsent(
               block, block1 -> this.a.stream().filter(definedstructure_blockinfo -> definedstructure_blockinfo.b.a(block1)).collect(Collectors.toList())
            );
      }
   }

   private static class b implements Iterable<IBlockData> {
      public static final IBlockData a = Blocks.a.o();
      private final RegistryBlockID<IBlockData> b = new RegistryBlockID<>(16);
      private int c;

      b() {
      }

      public int a(IBlockData iblockdata) {
         int i = this.b.a(iblockdata);
         if (i == -1) {
            i = this.c++;
            this.b.a(iblockdata, i);
         }

         return i;
      }

      @Nullable
      public IBlockData a(int i) {
         IBlockData iblockdata = this.b.a(i);
         return iblockdata == null ? a : iblockdata;
      }

      @Override
      public Iterator<IBlockData> iterator() {
         return this.b.iterator();
      }

      public void a(IBlockData iblockdata, int i) {
         this.b.a(iblockdata, i);
      }
   }
}
