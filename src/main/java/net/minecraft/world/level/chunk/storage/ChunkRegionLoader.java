package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.LightEngineThreaded;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkConverter;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.DataPaletteBlock;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.chunk.NibbleArray;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.ProtoChunkExtension;
import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.ticks.LevelChunkTicks;
import net.minecraft.world.ticks.ProtoChunkTickList;
import org.slf4j.Logger;

public class ChunkRegionLoader {
   public static final Codec<DataPaletteBlock<IBlockData>> h = DataPaletteBlock.a(Block.o, IBlockData.b, DataPaletteBlock.d.d, Blocks.a.o());
   private static final Logger i = LogUtils.getLogger();
   private static final String j = "UpgradeData";
   private static final String k = "block_ticks";
   private static final String l = "fluid_ticks";
   public static final String a = "xPos";
   public static final String b = "zPos";
   public static final String c = "Heightmaps";
   public static final String d = "isLightOn";
   public static final String e = "sections";
   public static final String f = "BlockLight";
   public static final String g = "SkyLight";

   public static ProtoChunk a(WorldServer worldserver, VillagePlace villageplace, ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
      ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(nbttagcompound.h("xPos"), nbttagcompound.h("zPos"));
      if (!Objects.equals(chunkcoordintpair, chunkcoordintpair1)) {
         ChunkRegionLoader.i
            .error(
               "Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})",
               new Object[]{chunkcoordintpair, chunkcoordintpair, chunkcoordintpair1}
            );
      }

      ChunkConverter chunkconverter = nbttagcompound.b("UpgradeData", 10)
         ? new ChunkConverter(nbttagcompound.p("UpgradeData"), worldserver)
         : ChunkConverter.a;
      boolean flag = nbttagcompound.q("isLightOn");
      NBTTagList nbttaglist = nbttagcompound.c("sections", 10);
      int i = worldserver.aj();
      ChunkSection[] achunksection = new ChunkSection[i];
      boolean flag1 = worldserver.q_().g();
      ChunkProviderServer chunkproviderserver = worldserver.k();
      LightEngine lightengine = chunkproviderserver.a();
      IRegistry<BiomeBase> iregistry = worldserver.u_().d(Registries.an);
      Codec<DataPaletteBlock<Holder<BiomeBase>>> codec = makeBiomeCodecRW(iregistry);
      boolean flag2 = false;

      for(int j = 0; j < nbttaglist.size(); ++j) {
         NBTTagCompound nbttagcompound1 = nbttaglist.a(j);
         byte b0 = nbttagcompound1.f("Y");
         int k = worldserver.f(b0);
         if (k >= 0 && k < achunksection.length) {
            DataPaletteBlock datapaletteblock;
            if (nbttagcompound1.b("block_states", 10)) {
               DataResult dataresult = h.parse(DynamicOpsNBT.a, nbttagcompound1.p("block_states")).promotePartial(s -> a(chunkcoordintpair, b0, s));
               Logger logger = ChunkRegionLoader.i;
               datapaletteblock = (DataPaletteBlock)dataresult.getOrThrow(false, logger::error);
            } else {
               datapaletteblock = new DataPaletteBlock<>(Block.o, Blocks.a.o(), DataPaletteBlock.d.d);
            }

            DataPaletteBlock object;
            if (nbttagcompound1.b("biomes", 10)) {
               DataResult dataresult = codec.parse(DynamicOpsNBT.a, nbttagcompound1.p("biomes")).promotePartial(s -> a(chunkcoordintpair, b0, s));
               Logger logger = ChunkRegionLoader.i;
               object = (DataPaletteBlock)dataresult.getOrThrow(false, logger::error);
            } else {
               object = new DataPaletteBlock<>(iregistry.t(), iregistry.f(Biomes.b), DataPaletteBlock.d.e);
            }

            ChunkSection chunksection = new ChunkSection(b0, datapaletteblock, object);
            achunksection[k] = chunksection;
            villageplace.a(chunkcoordintpair, chunksection);
         }

         boolean flag3 = nbttagcompound1.b("BlockLight", 7);
         boolean flag4 = flag1 && nbttagcompound1.b("SkyLight", 7);
         if (flag3 || flag4) {
            if (!flag2) {
               lightengine.b(chunkcoordintpair, true);
               flag2 = true;
            }

            if (flag3) {
               lightengine.a(EnumSkyBlock.b, SectionPosition.a(chunkcoordintpair, b0), new NibbleArray(nbttagcompound1.m("BlockLight")), true);
            }

            if (flag4) {
               lightengine.a(EnumSkyBlock.a, SectionPosition.a(chunkcoordintpair, b0), new NibbleArray(nbttagcompound1.m("SkyLight")), true);
            }
         }
      }

      long l = nbttagcompound.i("InhabitedTime");
      ChunkStatus.Type chunkstatus_type = a(nbttagcompound);
      BlendingData blendingdata;
      if (nbttagcompound.b("blending_data", 10)) {
         DataResult dataresult = BlendingData.e.parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.p("blending_data")));
         Logger logger1 = ChunkRegionLoader.i;
         blendingdata = (BlendingData)dataresult.resultOrPartial(logger1::error).orElse(null);
      } else {
         blendingdata = null;
      }

      Object object1;
      if (chunkstatus_type == ChunkStatus.Type.b) {
         LevelChunkTicks<Block> levelchunkticks = LevelChunkTicks.a(
            nbttagcompound.c("block_ticks", 10), s -> BuiltInRegistries.f.b(MinecraftKey.a(s)), chunkcoordintpair
         );
         LevelChunkTicks<FluidType> levelchunkticks1 = LevelChunkTicks.a(
            nbttagcompound.c("fluid_ticks", 10), s -> BuiltInRegistries.d.b(MinecraftKey.a(s)), chunkcoordintpair
         );
         object1 = new Chunk(
            worldserver.C(),
            chunkcoordintpair,
            chunkconverter,
            levelchunkticks,
            levelchunkticks1,
            l,
            achunksection,
            a(worldserver, nbttagcompound),
            blendingdata
         );
      } else {
         ProtoChunkTickList<Block> protochunkticklist = ProtoChunkTickList.a(
            nbttagcompound.c("block_ticks", 10), s -> BuiltInRegistries.f.b(MinecraftKey.a(s)), chunkcoordintpair
         );
         ProtoChunkTickList<FluidType> protochunkticklist1 = ProtoChunkTickList.a(
            nbttagcompound.c("fluid_ticks", 10), s -> BuiltInRegistries.d.b(MinecraftKey.a(s)), chunkcoordintpair
         );
         ProtoChunk protochunk = new ProtoChunk(
            chunkcoordintpair, chunkconverter, achunksection, protochunkticklist, protochunkticklist1, worldserver, iregistry, blendingdata
         );
         object1 = protochunk;
         protochunk.b(l);
         if (nbttagcompound.b("below_zero_retrogen", 10)) {
            DataResult dataresult = BelowZeroRetrogen.a.parse(new Dynamic(DynamicOpsNBT.a, nbttagcompound.p("below_zero_retrogen")));
            Logger logger1 = ChunkRegionLoader.i;
            Optional<BelowZeroRetrogen> optional = dataresult.resultOrPartial(logger1::error);
            optional.ifPresent(protochunk::a);
         }

         ChunkStatus chunkstatus = ChunkStatus.a(nbttagcompound.l("Status"));
         protochunk.a(chunkstatus);
         if (chunkstatus.b(ChunkStatus.k)) {
            protochunk.a(lightengine);
         }

         BelowZeroRetrogen belowzeroretrogen = protochunk.x();
         boolean flag5 = chunkstatus.b(ChunkStatus.l) || belowzeroretrogen != null && belowzeroretrogen.a().b(ChunkStatus.l);
         if (!flag && flag5) {
            for(BlockPosition blockposition : BlockPosition.b(
               chunkcoordintpair.d(), worldserver.v_(), chunkcoordintpair.e(), chunkcoordintpair.f(), worldserver.ai() - 1, chunkcoordintpair.g()
            )) {
               if (((IChunkAccess)object1).a_(blockposition).g() != 0) {
                  protochunk.j(blockposition);
               }
            }
         }
      }

      NBTBase persistentBase = nbttagcompound.c("ChunkBukkitValues");
      if (persistentBase instanceof NBTTagCompound) {
         ((IChunkAccess)object1).persistentDataContainer.putAll((NBTTagCompound)persistentBase);
      }

      ((IChunkAccess)object1).b(flag);
      NBTTagCompound nbttagcompound2 = nbttagcompound.p("Heightmaps");
      EnumSet<HeightMap.Type> enumset = EnumSet.noneOf(HeightMap.Type.class);

      for(HeightMap.Type heightmap_type : ((IChunkAccess)object1).j().h()) {
         String s = heightmap_type.a();
         if (nbttagcompound2.b(s, 12)) {
            ((IChunkAccess)object1).a(heightmap_type, nbttagcompound2.o(s));
         } else {
            enumset.add(heightmap_type);
         }
      }

      HeightMap.a((IChunkAccess)object1, enumset);
      NBTTagCompound nbttagcompound3 = nbttagcompound.p("structures");
      ((IChunkAccess)object1).a(a(StructurePieceSerializationContext.a(worldserver), nbttagcompound3, worldserver.A()));
      ((IChunkAccess)object1).b(a(worldserver.u_(), chunkcoordintpair, nbttagcompound3));
      if (nbttagcompound.q("shouldSave")) {
         ((IChunkAccess)object1).a(true);
      }

      NBTTagList nbttaglist1 = nbttagcompound.c("PostProcessing", 9);

      for(int j1 = 0; j1 < nbttaglist1.size(); ++j1) {
         NBTTagList nbttaglist2 = nbttaglist1.b(j1);

         for(int i1 = 0; i1 < nbttaglist2.size(); ++i1) {
            ((IChunkAccess)object1).a(nbttaglist2.d(i1), j1);
         }
      }

      if (chunkstatus_type == ChunkStatus.Type.b) {
         return new ProtoChunkExtension((Chunk)object1, false);
      } else {
         ProtoChunk protochunk1 = (ProtoChunk)object1;
         NBTTagList nbttaglist2 = nbttagcompound.c("entities", 10);

         for(int i1 = 0; i1 < nbttaglist2.size(); ++i1) {
            protochunk1.b(nbttaglist2.a(i1));
         }

         NBTTagList nbttaglist3 = nbttagcompound.c("block_entities", 10);

         for(int k1 = 0; k1 < nbttaglist3.size(); ++k1) {
            NBTTagCompound nbttagcompound4 = nbttaglist3.a(k1);
            ((IChunkAccess)object1).a(nbttagcompound4);
         }

         NBTTagList nbttaglist4 = nbttagcompound.c("Lights", 9);

         for(int l1 = 0; l1 < nbttaglist4.size(); ++l1) {
            ChunkSection chunksection1 = achunksection[l1];
            if (chunksection1 != null && !chunksection1.c()) {
               NBTTagList nbttaglist5 = nbttaglist4.b(l1);

               for(int i2 = 0; i2 < nbttaglist5.size(); ++i2) {
                  protochunk1.b(nbttaglist5.d(i2), l1);
               }
            }
         }

         NBTTagCompound nbttagcompound4 = nbttagcompound.p("CarvingMasks");

         for(String s1 : nbttagcompound4.e()) {
            WorldGenStage.Features worldgenstage_features = WorldGenStage.Features.valueOf(s1);
            protochunk1.a(worldgenstage_features, new CarvingMask(nbttagcompound4.o(s1), ((IChunkAccess)object1).v_()));
         }

         return protochunk1;
      }
   }

   private static void a(ChunkCoordIntPair chunkcoordintpair, int i, String s) {
      ChunkRegionLoader.i.error("Recoverable errors when loading section [" + chunkcoordintpair.e + ", " + i + ", " + chunkcoordintpair.f + "]: " + s);
   }

   private static Codec<PalettedContainerRO<Holder<BiomeBase>>> a(IRegistry<BiomeBase> iregistry) {
      return DataPaletteBlock.b(iregistry.t(), iregistry.r(), DataPaletteBlock.d.e, iregistry.f(Biomes.b));
   }

   private static Codec<DataPaletteBlock<Holder<BiomeBase>>> makeBiomeCodecRW(IRegistry<BiomeBase> iregistry) {
      return DataPaletteBlock.a(iregistry.t(), iregistry.r(), DataPaletteBlock.d.e, iregistry.f(Biomes.b));
   }

   public static NBTTagCompound a(WorldServer worldserver, IChunkAccess ichunkaccess) {
      ChunkCoordIntPair chunkcoordintpair = ichunkaccess.f();
      NBTTagCompound nbttagcompound = GameProfileSerializer.g(new NBTTagCompound());
      nbttagcompound.a("xPos", chunkcoordintpair.e);
      nbttagcompound.a("yPos", ichunkaccess.ak());
      nbttagcompound.a("zPos", chunkcoordintpair.f);
      nbttagcompound.a("LastUpdate", worldserver.U());
      nbttagcompound.a("InhabitedTime", ichunkaccess.u());
      nbttagcompound.a("Status", ichunkaccess.j().d());
      BlendingData blendingdata = ichunkaccess.t();
      if (blendingdata != null) {
         DataResult<NBTBase> dataresult = BlendingData.e.encodeStart(DynamicOpsNBT.a, blendingdata);
         Logger logger = ChunkRegionLoader.i;
         dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("blending_data", nbtbase));
      }

      BelowZeroRetrogen belowzeroretrogen = ichunkaccess.x();
      if (belowzeroretrogen != null) {
         DataResult<NBTBase> dataresult = BelowZeroRetrogen.a.encodeStart(DynamicOpsNBT.a, belowzeroretrogen);
         Logger logger = ChunkRegionLoader.i;
         dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("below_zero_retrogen", nbtbase));
      }

      ChunkConverter chunkconverter = ichunkaccess.r();
      if (!chunkconverter.a()) {
         nbttagcompound.a("UpgradeData", chunkconverter.b());
      }

      ChunkSection[] achunksection = ichunkaccess.d();
      NBTTagList nbttaglist = new NBTTagList();
      LightEngineThreaded lightenginethreaded = worldserver.k().a();
      IRegistry<BiomeBase> iregistry = worldserver.u_().d(Registries.an);
      Codec<PalettedContainerRO<Holder<BiomeBase>>> codec = a(iregistry);
      boolean flag = ichunkaccess.v();

      for(int i = lightenginethreaded.c(); i < lightenginethreaded.d(); ++i) {
         int j = ichunkaccess.f(i);
         boolean flag1 = j >= 0 && j < achunksection.length;
         NibbleArray nibblearray = lightenginethreaded.a(EnumSkyBlock.b).a(SectionPosition.a(chunkcoordintpair, i));
         NibbleArray nibblearray1 = lightenginethreaded.a(EnumSkyBlock.a).a(SectionPosition.a(chunkcoordintpair, i));
         if (flag1 || nibblearray != null || nibblearray1 != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            if (flag1) {
               ChunkSection chunksection = achunksection[j];
               DataResult<NBTBase> dataresult1 = h.encodeStart(DynamicOpsNBT.a, chunksection.i());
               Logger logger1 = ChunkRegionLoader.i;
               nbttagcompound1.a("block_states", (NBTBase)dataresult1.getOrThrow(false, logger1::error));
               dataresult1 = codec.encodeStart(DynamicOpsNBT.a, chunksection.j());
               logger1 = ChunkRegionLoader.i;
               nbttagcompound1.a("biomes", (NBTBase)dataresult1.getOrThrow(false, logger1::error));
            }

            if (nibblearray != null && !nibblearray.c()) {
               nbttagcompound1.a("BlockLight", nibblearray.a());
            }

            if (nibblearray1 != null && !nibblearray1.c()) {
               nbttagcompound1.a("SkyLight", nibblearray1.a());
            }

            if (!nbttagcompound1.g()) {
               nbttagcompound1.a("Y", (byte)i);
               nbttaglist.add(nbttagcompound1);
            }
         }
      }

      nbttagcompound.a("sections", nbttaglist);
      if (flag) {
         nbttagcompound.a("isLightOn", true);
      }

      NBTTagList nbttaglist1 = new NBTTagList();

      for(BlockPosition blockposition : ichunkaccess.c()) {
         NBTTagCompound nbttagcompound2 = ichunkaccess.g(blockposition);
         if (nbttagcompound2 != null) {
            nbttaglist1.add(nbttagcompound2);
         }
      }

      nbttagcompound.a("block_entities", nbttaglist1);
      if (ichunkaccess.j().g() == ChunkStatus.Type.a) {
         ProtoChunk protochunk = (ProtoChunk)ichunkaccess;
         NBTTagList nbttaglist2 = new NBTTagList();
         nbttaglist2.addAll(protochunk.D());
         nbttagcompound.a("entities", nbttaglist2);
         nbttagcompound.a("Lights", a(protochunk.B()));
         NBTTagCompound nbttagcompound2 = new NBTTagCompound();

         for(WorldGenStage.Features worldgenstage_features : WorldGenStage.Features.values()) {
            CarvingMask carvingmask = protochunk.a(worldgenstage_features);
            if (carvingmask != null) {
               nbttagcompound2.a(worldgenstage_features.toString(), carvingmask.a());
            }
         }

         nbttagcompound.a("CarvingMasks", nbttagcompound2);
      }

      a(worldserver, nbttagcompound, ichunkaccess.q());
      nbttagcompound.a("PostProcessing", a(ichunkaccess.k()));
      NBTTagCompound nbttagcompound3 = new NBTTagCompound();

      for(Entry<HeightMap.Type, HeightMap> entry : ichunkaccess.e()) {
         if (ichunkaccess.j().h().contains(entry.getKey())) {
            nbttagcompound3.a(entry.getKey().a(), new NBTTagLongArray(entry.getValue().a()));
         }
      }

      nbttagcompound.a("Heightmaps", nbttagcompound3);
      nbttagcompound.a("structures", a(StructurePieceSerializationContext.a(worldserver), chunkcoordintpair, ichunkaccess.g(), ichunkaccess.h()));
      if (!ichunkaccess.persistentDataContainer.isEmpty()) {
         nbttagcompound.a("ChunkBukkitValues", ichunkaccess.persistentDataContainer.toTagCompound());
      }

      return nbttagcompound;
   }

   private static void a(WorldServer worldserver, NBTTagCompound nbttagcompound, IChunkAccess.a ichunkaccess_a) {
      long i = worldserver.n_().e();
      nbttagcompound.a("block_ticks", ichunkaccess_a.a().b(i, block -> BuiltInRegistries.f.b(block).toString()));
      nbttagcompound.a("fluid_ticks", ichunkaccess_a.b().b(i, fluidtype -> BuiltInRegistries.d.b(fluidtype).toString()));
   }

   public static ChunkStatus.Type a(@Nullable NBTTagCompound nbttagcompound) {
      return nbttagcompound != null ? ChunkStatus.a(nbttagcompound.l("Status")).g() : ChunkStatus.Type.a;
   }

   @Nullable
   private static Chunk.c a(WorldServer worldserver, NBTTagCompound nbttagcompound) {
      NBTTagList nbttaglist = a(nbttagcompound, "entities");
      NBTTagList nbttaglist1 = a(nbttagcompound, "block_entities");
      return nbttaglist == null && nbttaglist1 == null ? null : chunk -> {
         worldserver.timings.syncChunkLoadEntitiesTimer.startTiming();
         if (nbttaglist != null) {
            worldserver.a(EntityTypes.a(nbttaglist, worldserver));
         }

         worldserver.timings.syncChunkLoadEntitiesTimer.stopTiming();
         worldserver.timings.syncChunkLoadTileEntitiesTimer.startTiming();
         if (nbttaglist1 != null) {
            for(int i = 0; i < nbttaglist1.size(); ++i) {
               NBTTagCompound nbttagcompound1 = nbttaglist1.a(i);
               boolean flag = nbttagcompound1.q("keepPacked");
               if (flag) {
                  chunk.a(nbttagcompound1);
               } else {
                  BlockPosition blockposition = TileEntity.c(nbttagcompound1);
                  TileEntity tileentity = TileEntity.a(blockposition, chunk.a_(blockposition), nbttagcompound1);
                  if (tileentity != null) {
                     chunk.a(tileentity);
                  }
               }
            }
         }

         worldserver.timings.syncChunkLoadTileEntitiesTimer.stopTiming();
      };
   }

   @Nullable
   private static NBTTagList a(NBTTagCompound nbttagcompound, String s) {
      NBTTagList nbttaglist = nbttagcompound.c(s, 10);
      return nbttaglist.isEmpty() ? null : nbttaglist;
   }

   private static NBTTagCompound a(
      StructurePieceSerializationContext structurepieceserializationcontext,
      ChunkCoordIntPair chunkcoordintpair,
      Map<Structure, StructureStart> map,
      Map<Structure, LongSet> map1
   ) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      NBTTagCompound nbttagcompound1 = new NBTTagCompound();
      IRegistry<Structure> iregistry = structurepieceserializationcontext.b().d(Registries.ax);

      for(Entry<Structure, StructureStart> entry : map.entrySet()) {
         MinecraftKey minecraftkey = iregistry.b(entry.getKey());
         nbttagcompound1.a(minecraftkey.toString(), entry.getValue().a(structurepieceserializationcontext, chunkcoordintpair));
      }

      nbttagcompound.a("starts", nbttagcompound1);
      NBTTagCompound nbttagcompound2 = new NBTTagCompound();

      for(Entry<Structure, LongSet> entry1 : map1.entrySet()) {
         if (!((LongSet)entry1.getValue()).isEmpty()) {
            MinecraftKey minecraftkey1 = iregistry.b(entry1.getKey());
            nbttagcompound2.a(minecraftkey1.toString(), new NBTTagLongArray((LongSet)entry1.getValue()));
         }
      }

      nbttagcompound.a("References", nbttagcompound2);
      return nbttagcompound;
   }

   private static Map<Structure, StructureStart> a(
      StructurePieceSerializationContext structurepieceserializationcontext, NBTTagCompound nbttagcompound, long i
   ) {
      Map<Structure, StructureStart> map = Maps.newHashMap();
      IRegistry<Structure> iregistry = structurepieceserializationcontext.b().d(Registries.ax);
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("starts");

      for(String s : nbttagcompound1.e()) {
         MinecraftKey minecraftkey = MinecraftKey.a(s);
         Structure structure = iregistry.a(minecraftkey);
         if (structure == null) {
            ChunkRegionLoader.i.error("Unknown structure start: {}", minecraftkey);
         } else {
            StructureStart structurestart = StructureStart.a(structurepieceserializationcontext, nbttagcompound1.p(s), i);
            if (structurestart != null) {
               map.put(structure, structurestart);
            }
         }
      }

      return map;
   }

   private static Map<Structure, LongSet> a(IRegistryCustom iregistrycustom, ChunkCoordIntPair chunkcoordintpair, NBTTagCompound nbttagcompound) {
      Map<Structure, LongSet> map = Maps.newHashMap();
      IRegistry<Structure> iregistry = iregistrycustom.d(Registries.ax);
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("References");

      for(String s : nbttagcompound1.e()) {
         MinecraftKey minecraftkey = MinecraftKey.a(s);
         Structure structure = iregistry.a(minecraftkey);
         if (structure == null) {
            i.warn("Found reference to unknown structure '{}' in chunk {}, discarding", minecraftkey, chunkcoordintpair);
         } else {
            long[] along = nbttagcompound1.o(s);
            if (along.length != 0) {
               map.put(
                  structure,
                  new LongOpenHashSet(
                     Arrays.stream(along)
                        .filter(
                           i -> {
                              ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(i);
                              if (chunkcoordintpair1.a(chunkcoordintpair) > 8) {
                                 ChunkRegionLoader.i
                                    .warn(
                                       "Found invalid structure reference [ {} @ {} ] for chunk {}.",
                                       new Object[]{minecraftkey, chunkcoordintpair1, chunkcoordintpair}
                                    );
                                 return false;
                              } else {
                                 return true;
                              }
                           }
                        )
                        .toArray()
                  )
               );
            }
         }
      }

      return map;
   }

   public static NBTTagList a(ShortList[] ashortlist) {
      NBTTagList nbttaglist = new NBTTagList();

      for(ShortList shortlist : ashortlist) {
         NBTTagList nbttaglist1 = new NBTTagList();
         if (shortlist != null) {
            ShortListIterator shortlistiterator = shortlist.iterator();

            while(shortlistiterator.hasNext()) {
               Short oshort = shortlistiterator.next();
               nbttaglist1.add(NBTTagShort.a(oshort));
            }
         }

         nbttaglist.add(nbttaglist1);
      }

      return nbttaglist;
   }
}
