package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.blocks.ArgumentTileLocation;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.structures.DebugReportNBT;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntityCommand;
import net.minecraft.world.level.block.entity.TileEntityStructure;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import net.minecraft.world.level.levelgen.flat.GeneratorSettingsFlat;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class GameTestHarnessStructures {
   private static final Logger c = LogUtils.getLogger();
   public static final String a = "gameteststructures";
   public static String b = "gameteststructures";
   private static final int d = 4;

   public static EnumBlockRotation a(int var0) {
      switch(var0) {
         case 0:
            return EnumBlockRotation.a;
         case 1:
            return EnumBlockRotation.b;
         case 2:
            return EnumBlockRotation.c;
         case 3:
            return EnumBlockRotation.d;
         default:
            throw new IllegalArgumentException("rotationSteps must be a value from 0-3. Got value " + var0);
      }
   }

   public static int a(EnumBlockRotation var0) {
      switch(var0) {
         case a:
            return 0;
         case b:
            return 1;
         case c:
            return 2;
         case d:
            return 3;
         default:
            throw new IllegalArgumentException("Unknown rotation value, don't know how many steps it represents: " + var0);
      }
   }

   public static void a(String[] var0) throws IOException {
      DispenserRegistry.a();
      Files.walk(Paths.get(b)).filter(var0x -> var0x.toString().endsWith(".snbt")).forEach(var0x -> {
         try {
            String var1 = Files.readString(var0x);
            NBTTagCompound var2 = GameProfileSerializer.a(var1);
            NBTTagCompound var3 = StructureUpdater.a(var0x.toString(), var2);
            DebugReportNBT.a(CachedOutput.a, var0x, GameProfileSerializer.c(var3));
         } catch (IOException | CommandSyntaxException var4) {
            c.error("Something went wrong upgrading: {}", var0x, var4);
         }
      });
   }

   public static AxisAlignedBB a(TileEntityStructure var0) {
      BlockPosition var1 = var0.p();
      BlockPosition var2 = var1.a(var0.j().c(-1, -1, -1));
      BlockPosition var3 = DefinedStructure.a(var2, EnumBlockMirror.a, var0.w(), var1);
      return new AxisAlignedBB(var1, var3);
   }

   public static StructureBoundingBox b(TileEntityStructure var0) {
      BlockPosition var1 = var0.p();
      BlockPosition var2 = var1.a(var0.j().c(-1, -1, -1));
      BlockPosition var3 = DefinedStructure.a(var2, EnumBlockMirror.a, var0.w(), var1);
      return StructureBoundingBox.a(var1, var3);
   }

   public static void a(BlockPosition var0, BlockPosition var1, EnumBlockRotation var2, WorldServer var3) {
      BlockPosition var4 = DefinedStructure.a(var0.a(var1), EnumBlockMirror.a, var2, var0);
      var3.b(var4, Blocks.fM.o());
      TileEntityCommand var5 = (TileEntityCommand)var3.c_(var4);
      var5.c().a("test runthis");
      BlockPosition var6 = DefinedStructure.a(var4.b(0, 0, -1), EnumBlockMirror.a, var2, var4);
      var3.b(var6, Blocks.dL.o().a(var2));
   }

   public static void a(String var0, BlockPosition var1, BaseBlockPosition var2, EnumBlockRotation var3, WorldServer var4) {
      StructureBoundingBox var5 = a(var1, var2, var3);
      a(var5, var1.v(), var4);
      var4.b(var1, Blocks.oW.o());
      TileEntityStructure var6 = (TileEntityStructure)var4.c_(var1);
      var6.a(false);
      var6.a(new MinecraftKey(var0));
      var6.a(var2);
      var6.a(BlockPropertyStructureMode.a);
      var6.e(true);
   }

   public static TileEntityStructure a(String var0, BlockPosition var1, EnumBlockRotation var2, int var3, WorldServer var4, boolean var5) {
      BaseBlockPosition var6 = a(var0, var4).a();
      StructureBoundingBox var7 = a(var1, var6, var2);
      BlockPosition var8;
      if (var2 == EnumBlockRotation.a) {
         var8 = var1;
      } else if (var2 == EnumBlockRotation.b) {
         var8 = var1.b(var6.w() - 1, 0, 0);
      } else if (var2 == EnumBlockRotation.c) {
         var8 = var1.b(var6.u() - 1, 0, var6.w() - 1);
      } else {
         if (var2 != EnumBlockRotation.d) {
            throw new IllegalArgumentException("Invalid rotation: " + var2);
         }

         var8 = var1.b(0, 0, var6.u() - 1);
      }

      a(var1, var4);
      a(var7, var1.v(), var4);
      TileEntityStructure var9 = a(var0, var8, var2, var4, var5);
      var4.l().a(var7);
      var4.a(var7);
      return var9;
   }

   private static void a(BlockPosition var0, WorldServer var1) {
      ChunkCoordIntPair var2 = new ChunkCoordIntPair(var0);

      for(int var3 = -1; var3 < 4; ++var3) {
         for(int var4 = -1; var4 < 4; ++var4) {
            int var5 = var2.e + var3;
            int var6 = var2.f + var4;
            var1.a(var5, var6, true);
         }
      }
   }

   public static void a(StructureBoundingBox var0, int var1, WorldServer var2) {
      StructureBoundingBox var3 = new StructureBoundingBox(var0.g() - 2, var0.h() - 3, var0.i() - 3, var0.j() + 3, var0.k() + 20, var0.l() + 3);
      BlockPosition.a(var3).forEach(var2x -> a(var1, var2x, var2));
      var2.l().a(var3);
      var2.a(var3);
      AxisAlignedBB var4 = new AxisAlignedBB((double)var3.g(), (double)var3.h(), (double)var3.i(), (double)var3.j(), (double)var3.k(), (double)var3.l());
      List<Entity> var5 = var2.a(Entity.class, var4, var0x -> !(var0x instanceof EntityHuman));
      var5.forEach(Entity::ai);
   }

   public static StructureBoundingBox a(BlockPosition var0, BaseBlockPosition var1, EnumBlockRotation var2) {
      BlockPosition var3 = var0.a(var1).b(-1, -1, -1);
      BlockPosition var4 = DefinedStructure.a(var3, EnumBlockMirror.a, var2, var0);
      StructureBoundingBox var5 = StructureBoundingBox.a(var0, var4);
      int var6 = Math.min(var5.g(), var5.j());
      int var7 = Math.min(var5.i(), var5.l());
      return var5.a(var0.u() - var6, 0, var0.w() - var7);
   }

   public static Optional<BlockPosition> a(BlockPosition var0, int var1, WorldServer var2) {
      return c(var0, var1, var2).stream().filter(var2x -> a(var2x, var0, var2)).findFirst();
   }

   @Nullable
   public static BlockPosition b(BlockPosition var0, int var1, WorldServer var2) {
      Comparator<BlockPosition> var3 = Comparator.comparingInt(var1x -> var1x.k(var0));
      Collection<BlockPosition> var4 = c(var0, var1, var2);
      Optional<BlockPosition> var5 = var4.stream().min(var3);
      return var5.orElse(null);
   }

   public static Collection<BlockPosition> c(BlockPosition var0, int var1, WorldServer var2) {
      Collection<BlockPosition> var3 = Lists.newArrayList();
      AxisAlignedBB var4 = new AxisAlignedBB(var0);
      var4 = var4.g((double)var1);

      for(int var5 = (int)var4.a; var5 <= (int)var4.d; ++var5) {
         for(int var6 = (int)var4.b; var6 <= (int)var4.e; ++var6) {
            for(int var7 = (int)var4.c; var7 <= (int)var4.f; ++var7) {
               BlockPosition var8 = new BlockPosition(var5, var6, var7);
               IBlockData var9 = var2.a_(var8);
               if (var9.a(Blocks.oW)) {
                  var3.add(var8);
               }
            }
         }
      }

      return var3;
   }

   private static DefinedStructure a(String var0, WorldServer var1) {
      StructureTemplateManager var2 = var1.p();
      Optional<DefinedStructure> var3 = var2.b(new MinecraftKey(var0));
      if (var3.isPresent()) {
         return var3.get();
      } else {
         String var4 = var0 + ".snbt";
         Path var5 = Paths.get(b, var4);
         NBTTagCompound var6 = a(var5);
         if (var6 == null) {
            throw new RuntimeException("Could not find structure file " + var5 + ", and the structure is not available in the world structures either.");
         } else {
            return var2.a(var6);
         }
      }
   }

   private static TileEntityStructure a(String var0, BlockPosition var1, EnumBlockRotation var2, WorldServer var3, boolean var4) {
      var3.b(var1, Blocks.oW.o());
      TileEntityStructure var5 = (TileEntityStructure)var3.c_(var1);
      var5.a(BlockPropertyStructureMode.b);
      var5.a(var2);
      var5.a(false);
      var5.a(new MinecraftKey(var0));
      var5.a(var3, var4);
      if (var5.j() != BaseBlockPosition.g) {
         return var5;
      } else {
         DefinedStructure var6 = a(var0, var3);
         var5.a(var3, var4, var6);
         if (var5.j() == BaseBlockPosition.g) {
            throw new RuntimeException("Failed to load structure " + var0);
         } else {
            return var5;
         }
      }
   }

   @Nullable
   private static NBTTagCompound a(Path var0) {
      try {
         BufferedReader var1 = Files.newBufferedReader(var0);
         String var2 = IOUtils.toString(var1);
         return GameProfileSerializer.a(var2);
      } catch (IOException var3) {
         return null;
      } catch (CommandSyntaxException var4) {
         throw new RuntimeException("Error while trying to load structure " + var0, var4);
      }
   }

   private static void a(int var0, BlockPosition var1, WorldServer var2) {
      IBlockData var3 = null;
      IRegistryCustom var4 = var2.u_();
      GeneratorSettingsFlat var5 = GeneratorSettingsFlat.a(var4.b(Registries.an), var4.b(Registries.az), var4.b(Registries.aw));
      List<IBlockData> var6 = var5.f();
      int var7 = var1.v() - var2.v_();
      if (var1.v() < var0 && var7 > 0 && var7 <= var6.size()) {
         var3 = var6.get(var7 - 1);
      }

      if (var3 == null) {
         var3 = Blocks.a.o();
      }

      ArgumentTileLocation var8 = new ArgumentTileLocation(var3, Collections.emptySet(), null);
      var8.a(var2, var1, 2);
      var2.b(var1, var3.b());
   }

   private static boolean a(BlockPosition var0, BlockPosition var1, WorldServer var2) {
      TileEntityStructure var3 = (TileEntityStructure)var2.c_(var0);
      AxisAlignedBB var4 = a(var3).g(1.0);
      return var4.d(Vec3D.b(var1));
   }
}
