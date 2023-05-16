package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.storage.WorldPersistentData;

public class PersistentStructureLegacy {
   private static final Map<String, String> a = SystemUtils.a(Maps.newHashMap(), hashmap -> {
      hashmap.put("Village", "Village");
      hashmap.put("Mineshaft", "Mineshaft");
      hashmap.put("Mansion", "Mansion");
      hashmap.put("Igloo", "Temple");
      hashmap.put("Desert_Pyramid", "Temple");
      hashmap.put("Jungle_Pyramid", "Temple");
      hashmap.put("Swamp_Hut", "Temple");
      hashmap.put("Stronghold", "Stronghold");
      hashmap.put("Monument", "Monument");
      hashmap.put("Fortress", "Fortress");
      hashmap.put("EndCity", "EndCity");
   });
   private static final Map<String, String> b = SystemUtils.a(Maps.newHashMap(), hashmap -> {
      hashmap.put("Iglu", "Igloo");
      hashmap.put("TeDP", "Desert_Pyramid");
      hashmap.put("TeJP", "Jungle_Pyramid");
      hashmap.put("TeSH", "Swamp_Hut");
   });
   private static final Set<String> c = Set.of(
      "pillager_outpost",
      "mineshaft",
      "mansion",
      "jungle_pyramid",
      "desert_pyramid",
      "igloo",
      "ruined_portal",
      "shipwreck",
      "swamp_hut",
      "stronghold",
      "monument",
      "ocean_ruin",
      "fortress",
      "endcity",
      "buried_treasure",
      "village",
      "nether_fossil",
      "bastion_remnant"
   );
   private final boolean d;
   private final Map<String, Long2ObjectMap<NBTTagCompound>> e = Maps.newHashMap();
   private final Map<String, PersistentIndexed> f = Maps.newHashMap();
   private final List<String> g;
   private final List<String> h;

   public PersistentStructureLegacy(@Nullable WorldPersistentData worldpersistentdata, List<String> list, List<String> list1) {
      this.g = list;
      this.h = list1;
      this.a(worldpersistentdata);
      boolean flag = false;

      for(String s : this.h) {
         flag |= this.e.get(s) != null;
      }

      this.d = flag;
   }

   public void a(long i) {
      for(String s : this.g) {
         PersistentIndexed persistentindexed = this.f.get(s);
         if (persistentindexed != null && persistentindexed.c(i)) {
            persistentindexed.d(i);
            persistentindexed.b();
         }
      }
   }

   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("Level");
      ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(nbttagcompound1.h("xPos"), nbttagcompound1.h("zPos"));
      if (this.a(chunkcoordintpair.e, chunkcoordintpair.f)) {
         nbttagcompound = this.a(nbttagcompound, chunkcoordintpair);
      }

      NBTTagCompound nbttagcompound2 = nbttagcompound1.p("Structures");
      NBTTagCompound nbttagcompound3 = nbttagcompound2.p("References");

      for(String s : this.h) {
         boolean flag = c.contains(s.toLowerCase(Locale.ROOT));
         if (!nbttagcompound3.b(s, 12) && flag) {
            boolean flag1 = true;
            LongArrayList longarraylist = new LongArrayList();

            for(int i = chunkcoordintpair.e - 8; i <= chunkcoordintpair.e + 8; ++i) {
               for(int j = chunkcoordintpair.f - 8; j <= chunkcoordintpair.f + 8; ++j) {
                  if (this.a(i, j, s)) {
                     longarraylist.add(ChunkCoordIntPair.c(i, j));
                  }
               }
            }

            nbttagcompound3.c(s, longarraylist);
         }
      }

      nbttagcompound2.a("References", nbttagcompound3);
      nbttagcompound1.a("Structures", nbttagcompound2);
      nbttagcompound.a("Level", nbttagcompound1);
      return nbttagcompound;
   }

   private boolean a(int i, int j, String s) {
      return !this.d ? false : this.e.get(s) != null && this.f.get(a.get(s)).b(ChunkCoordIntPair.c(i, j));
   }

   private boolean a(int i, int j) {
      if (!this.d) {
         return false;
      } else {
         for(String s : this.h) {
            if (this.e.get(s) != null && this.f.get(a.get(s)).c(ChunkCoordIntPair.c(i, j))) {
               return true;
            }
         }

         return false;
      }
   }

   private NBTTagCompound a(NBTTagCompound nbttagcompound, ChunkCoordIntPair chunkcoordintpair) {
      NBTTagCompound nbttagcompound1 = nbttagcompound.p("Level");
      NBTTagCompound nbttagcompound2 = nbttagcompound1.p("Structures");
      NBTTagCompound nbttagcompound3 = nbttagcompound2.p("Starts");

      for(String s : this.h) {
         Long2ObjectMap<NBTTagCompound> long2objectmap = (Long2ObjectMap)this.e.get(s);
         if (long2objectmap != null) {
            long i = chunkcoordintpair.a();
            if (this.f.get(a.get(s)).c(i)) {
               NBTTagCompound nbttagcompound4 = (NBTTagCompound)long2objectmap.get(i);
               if (nbttagcompound4 != null) {
                  nbttagcompound3.a(s, nbttagcompound4);
               }
            }
         }
      }

      nbttagcompound2.a("Starts", nbttagcompound3);
      nbttagcompound1.a("Structures", nbttagcompound2);
      nbttagcompound.a("Level", nbttagcompound1);
      return nbttagcompound;
   }

   private void a(@Nullable WorldPersistentData worldpersistentdata) {
      if (worldpersistentdata != null) {
         Iterator iterator = this.g.iterator();

         while(true) {
            String s;
            NBTTagCompound nbttagcompound;
            while(true) {
               if (!iterator.hasNext()) {
                  return;
               }

               s = (String)iterator.next();
               nbttagcompound = new NBTTagCompound();

               try {
                  nbttagcompound = worldpersistentdata.a(s, 1493).p("data").p("Features");
                  if (nbttagcompound.g()) {
                     continue;
                  }
               } catch (IOException var13) {
               }
               break;
            }

            for(String s1 : nbttagcompound.e()) {
               NBTTagCompound nbttagcompound1 = nbttagcompound.p(s1);
               long i = ChunkCoordIntPair.c(nbttagcompound1.h("ChunkX"), nbttagcompound1.h("ChunkZ"));
               NBTTagList nbttaglist = nbttagcompound1.c("Children", 10);
               if (!nbttaglist.isEmpty()) {
                  String s2 = nbttaglist.a(0).l("id");
                  String s3 = b.get(s2);
                  if (s3 != null) {
                     nbttagcompound1.a("id", s3);
                  }
               }

               String s2 = nbttagcompound1.l("id");
               ((Long2ObjectMap)this.e.computeIfAbsent(s2, s4x -> new Long2ObjectOpenHashMap())).put(i, nbttagcompound1);
            }

            String s4 = s + "_index";
            PersistentIndexed persistentindexed = worldpersistentdata.a(PersistentIndexed::b, PersistentIndexed::new, s4);
            if (!persistentindexed.a().isEmpty()) {
               this.f.put(s, persistentindexed);
            } else {
               PersistentIndexed persistentindexed1 = new PersistentIndexed();
               this.f.put(s, persistentindexed1);

               for(String s5 : nbttagcompound.e()) {
                  NBTTagCompound nbttagcompound2 = nbttagcompound.p(s5);
                  persistentindexed1.a(ChunkCoordIntPair.c(nbttagcompound2.h("ChunkX"), nbttagcompound2.h("ChunkZ")));
               }

               persistentindexed1.b();
            }
         }
      }
   }

   public static PersistentStructureLegacy a(ResourceKey<WorldDimension> resourcekey, @Nullable WorldPersistentData worldpersistentdata) {
      if (resourcekey == WorldDimension.b) {
         return new PersistentStructureLegacy(
            worldpersistentdata,
            ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
            ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
         );
      } else if (resourcekey == WorldDimension.c) {
         ImmutableList immutablelist = ImmutableList.of("Fortress");
         return new PersistentStructureLegacy(worldpersistentdata, immutablelist, immutablelist);
      } else if (resourcekey == WorldDimension.d) {
         ImmutableList immutablelist = ImmutableList.of("EndCity");
         return new PersistentStructureLegacy(worldpersistentdata, immutablelist, immutablelist);
      } else {
         throw new RuntimeException(String.format(Locale.ROOT, "Unknown dimension type : %s", resourcekey));
      }
   }
}
