package net.minecraft.util.worldupdate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.IRegistry;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.World;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.storage.IChunkLoader;
import net.minecraft.world.level.chunk.storage.RegionFile;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.storage.Convertable;
import net.minecraft.world.level.storage.WorldPersistentData;
import org.slf4j.Logger;

public class WorldUpgrader {
   private static final Logger a = LogUtils.getLogger();
   private static final ThreadFactory b = new ThreadFactoryBuilder().setDaemon(true).build();
   private final IRegistry<WorldDimension> c;
   private final Set<ResourceKey<WorldDimension>> d;
   private final boolean e;
   private final Convertable.ConversionSession f;
   private final Thread g;
   private final DataFixer h;
   private volatile boolean i = true;
   private volatile boolean j;
   private volatile float k;
   private volatile int l;
   private volatile int m;
   private volatile int n;
   private final Object2FloatMap<ResourceKey<WorldDimension>> o = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap(SystemUtils.k()));
   private volatile IChatBaseComponent p = IChatBaseComponent.c("optimizeWorld.stage.counting");
   private static final Pattern q = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final WorldPersistentData r;

   public WorldUpgrader(Convertable.ConversionSession convertable_conversionsession, DataFixer datafixer, IRegistry<WorldDimension> iregistry, boolean flag) {
      this.c = iregistry;
      this.d = iregistry.f().stream().collect(Collectors.toUnmodifiableSet());
      this.e = flag;
      this.h = datafixer;
      this.f = convertable_conversionsession;
      this.r = new WorldPersistentData(this.f.a(World.h).resolve("data").toFile(), datafixer);
      this.g = b.newThread(this::i);
      this.g.setUncaughtExceptionHandler((thread, throwable) -> {
         a.error("Error upgrading world", throwable);
         this.p = IChatBaseComponent.c("optimizeWorld.stage.failed");
         this.j = true;
      });
      this.g.start();
   }

   public void a() {
      this.i = false;

      try {
         this.g.join();
      } catch (InterruptedException var2) {
      }
   }

   private void i() {
      this.l = 0;
      Builder<ResourceKey<WorldDimension>, ListIterator<ChunkCoordIntPair>> builder = ImmutableMap.builder();

      for(ResourceKey<WorldDimension> resourcekey : this.d) {
         List list = this.b(resourcekey);
         builder.put(resourcekey, list.listIterator());
         this.l += list.size();
      }

      if (this.l == 0) {
         this.j = true;
      } else {
         float f = (float)this.l;
         ImmutableMap<ResourceKey<WorldDimension>, ListIterator<ChunkCoordIntPair>> immutablemap = builder.build();
         Builder<ResourceKey<WorldDimension>, IChunkLoader> builder1 = ImmutableMap.builder();

         for(ResourceKey<WorldDimension> resourcekey1 : this.d) {
            Path path = this.f.a(null);
            builder1.put(resourcekey1, new IChunkLoader(path.resolve("region"), this.h, true));
         }

         ImmutableMap<ResourceKey<WorldDimension>, IChunkLoader> immutablemap1 = builder1.build();
         long i = SystemUtils.b();
         this.p = IChatBaseComponent.c("optimizeWorld.stage.upgrading");

         while(this.i) {
            boolean flag = false;
            float f1 = 0.0F;

            for(ResourceKey<WorldDimension> resourcekey2 : this.d) {
               ListIterator<ChunkCoordIntPair> listiterator = (ListIterator)immutablemap.get(resourcekey2);
               IChunkLoader ichunkloader = (IChunkLoader)immutablemap1.get(resourcekey2);
               if (listiterator.hasNext()) {
                  ChunkCoordIntPair chunkcoordintpair = listiterator.next();
                  boolean flag1 = false;

                  try {
                     NBTTagCompound nbttagcompound = ichunkloader.f(chunkcoordintpair).join().orElse(null);
                     if (nbttagcompound != null) {
                        int j = IChunkLoader.a(nbttagcompound);
                        ChunkGenerator chunkgenerator = this.c.e(resourcekey2).b();
                        NBTTagCompound nbttagcompound1 = ichunkloader.upgradeChunkTag(
                           resourcekey2, () -> this.r, nbttagcompound, chunkgenerator.b(), chunkcoordintpair, null
                        );
                        ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(nbttagcompound1.h("xPos"), nbttagcompound1.h("zPos"));
                        if (!chunkcoordintpair1.equals(chunkcoordintpair)) {
                           a.warn("Chunk {} has invalid position {}", chunkcoordintpair, chunkcoordintpair1);
                        }

                        boolean flag2 = j < SharedConstants.b().d().c();
                        if (this.e) {
                           flag2 = flag2 || nbttagcompound1.e("Heightmaps");
                           nbttagcompound1.r("Heightmaps");
                           flag2 = flag2 || nbttagcompound1.e("isLightOn");
                           nbttagcompound1.r("isLightOn");
                           NBTTagList nbttaglist = nbttagcompound1.c("sections", 10);

                           for(int k = 0; k < nbttaglist.size(); ++k) {
                              NBTTagCompound nbttagcompound2 = nbttaglist.a(k);
                              flag2 = flag2 || nbttagcompound2.e("BlockLight");
                              nbttagcompound2.r("BlockLight");
                              flag2 = flag2 || nbttagcompound2.e("SkyLight");
                              nbttagcompound2.r("SkyLight");
                           }
                        }

                        if (flag2) {
                           ichunkloader.a(chunkcoordintpair, nbttagcompound1);
                           flag1 = true;
                        }
                     }
                  } catch (ReportedException | CompletionException var29) {
                     Throwable throwable = var29.getCause();
                     if (!(throwable instanceof IOException)) {
                        throw var29;
                     }

                     a.error("Error upgrading chunk {}", chunkcoordintpair, throwable);
                  }

                  if (flag1) {
                     ++this.m;
                  } else {
                     ++this.n;
                  }

                  flag = true;
               }

               float f2 = (float)listiterator.nextIndex() / f;
               this.o.put(resourcekey2, f2);
               f1 += f2;
            }

            this.k = f1;
            if (!flag) {
               this.i = false;
            }
         }

         this.p = IChatBaseComponent.c("optimizeWorld.stage.finished");
         UnmodifiableIterator unmodifiableiterator = immutablemap1.values().iterator();

         while(unmodifiableiterator.hasNext()) {
            IChunkLoader ichunkloader1 = (IChunkLoader)unmodifiableiterator.next();

            try {
               ichunkloader1.close();
            } catch (IOException var28) {
               a.error("Error upgrading chunk", var28);
            }
         }

         this.r.a();
         i = SystemUtils.b() - i;
         a.info("World optimizaton finished after {} ms", i);
         this.j = true;
      }
   }

   private List<ChunkCoordIntPair> b(ResourceKey<WorldDimension> resourcekey) {
      File file = this.f.a(null).toFile();
      File file1 = new File(file, "region");
      File[] afile = file1.listFiles((file2x, s) -> s.endsWith(".mca"));
      if (afile == null) {
         return ImmutableList.of();
      } else {
         List<ChunkCoordIntPair> list = Lists.newArrayList();

         for(File file2 : afile) {
            Matcher matcher = q.matcher(file2.getName());
            if (matcher.matches()) {
               int k = Integer.parseInt(matcher.group(1)) << 5;
               int l = Integer.parseInt(matcher.group(2)) << 5;

               try (RegionFile regionfile = new RegionFile(file2.toPath(), file1.toPath(), true)) {
                  for(int i1 = 0; i1 < 32; ++i1) {
                     for(int j1 = 0; j1 < 32; ++j1) {
                        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + k, j1 + l);
                        if (regionfile.b(chunkcoordintpair)) {
                           list.add(chunkcoordintpair);
                        }
                     }
                  }
               } catch (Throwable var19) {
               }
            }
         }

         return list;
      }
   }

   public boolean b() {
      return this.j;
   }

   public Set<ResourceKey<World>> c() {
      throw new AssertionError("Unsupported");
   }

   public float a(ResourceKey<World> resourcekey) {
      return this.o.getFloat(resourcekey);
   }

   public float d() {
      return this.k;
   }

   public int e() {
      return this.l;
   }

   public int f() {
      return this.m;
   }

   public int g() {
      return this.n;
   }

   public IChatBaseComponent h() {
      return this.p;
   }
}
