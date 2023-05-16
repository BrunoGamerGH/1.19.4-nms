package net.minecraft.world.level.saveddata.maps;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.saveddata.PersistentBase;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.map.CraftMapView;
import org.bukkit.craftbukkit.v1_19_R3.map.RenderData;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.map.MapCursor;
import org.slf4j.Logger;

public class WorldMap extends PersistentBase {
   private static final Logger i = LogUtils.getLogger();
   private static final int j = 128;
   private static final int k = 64;
   public static final int a = 4;
   public static final int b = 256;
   public int c;
   public int d;
   public ResourceKey<World> e;
   public boolean l;
   public boolean m;
   public byte f;
   public byte[] g = new byte[16384];
   public boolean h;
   public final List<WorldMap.WorldMapHumanTracker> n = Lists.newArrayList();
   public final Map<EntityHuman, WorldMap.WorldMapHumanTracker> o = Maps.newHashMap();
   private final Map<String, MapIconBanner> p = Maps.newHashMap();
   public final Map<String, MapIcon> q = Maps.newLinkedHashMap();
   private final Map<String, WorldMapFrame> r = Maps.newHashMap();
   private int s;
   public final CraftMapView mapView;
   private CraftServer server;
   private UUID uniqueId = null;
   public String id;

   private WorldMap(int i, int j, byte b0, boolean flag, boolean flag1, boolean flag2, ResourceKey<World> resourcekey) {
      this.f = b0;
      this.c = i;
      this.d = j;
      this.e = resourcekey;
      this.l = flag;
      this.m = flag1;
      this.h = flag2;
      this.b();
      this.mapView = new CraftMapView(this);
      this.server = (CraftServer)Bukkit.getServer();
   }

   public static WorldMap a(double d0, double d1, byte b0, boolean flag, boolean flag1, ResourceKey<World> resourcekey) {
      int i = 128 * (1 << b0);
      int j = MathHelper.a((d0 + 64.0) / (double)i);
      int k = MathHelper.a((d1 + 64.0) / (double)i);
      int l = j * i + i / 2 - 64;
      int i1 = k * i + i / 2 - 64;
      return new WorldMap(l, i1, b0, flag, flag1, false, resourcekey);
   }

   public static WorldMap a(byte b0, boolean flag, ResourceKey<World> resourcekey) {
      return new WorldMap(0, 0, b0, false, false, flag, resourcekey);
   }

   public static WorldMap b(NBTTagCompound nbttagcompound) {
      DataResult<ResourceKey<World>> dataresult = DimensionManager.a(new Dynamic(DynamicOpsNBT.a, nbttagcompound.c("dimension")));
      Logger logger = WorldMap.i;
      ResourceKey<World> resourcekey = dataresult.resultOrPartial(logger::error).orElseGet(() -> {
         long least = nbttagcompound.i("UUIDLeast");
         long most = nbttagcompound.i("UUIDMost");
         if (least != 0L && most != 0L) {
            UUID uniqueId = new UUID(most, least);
            CraftWorld world = (CraftWorld)Bukkit.getWorld(uniqueId);
            if (world != null) {
               return world.getHandle().ab();
            }
         }

         throw new IllegalArgumentException("Invalid map dimension: " + nbttagcompound.c("dimension"));
      });
      int i = nbttagcompound.h("xCenter");
      int j = nbttagcompound.h("zCenter");
      byte b0 = (byte)MathHelper.a(nbttagcompound.f("scale"), 0, 4);
      boolean flag = !nbttagcompound.b("trackingPosition", 1) || nbttagcompound.q("trackingPosition");
      boolean flag1 = nbttagcompound.q("unlimitedTracking");
      boolean flag2 = nbttagcompound.q("locked");
      WorldMap worldmap = new WorldMap(i, j, b0, flag, flag1, flag2, resourcekey);
      byte[] abyte = nbttagcompound.m("colors");
      if (abyte.length == 16384) {
         worldmap.g = abyte;
      }

      NBTTagList nbttaglist = nbttagcompound.c("banners", 10);

      for(int k = 0; k < nbttaglist.size(); ++k) {
         MapIconBanner mapiconbanner = MapIconBanner.a(nbttaglist.a(k));
         worldmap.p.put(mapiconbanner.f(), mapiconbanner);
         worldmap.a(mapiconbanner.c(), null, mapiconbanner.f(), (double)mapiconbanner.a().u(), (double)mapiconbanner.a().w(), 180.0, mapiconbanner.d());
      }

      NBTTagList nbttaglist1 = nbttagcompound.c("frames", 10);

      for(int l = 0; l < nbttaglist1.size(); ++l) {
         WorldMapFrame worldmapframe = WorldMapFrame.a(nbttaglist1.a(l));
         worldmap.r.put(worldmapframe.e(), worldmapframe);
         worldmap.a(
            MapIcon.Type.b, null, "frame-" + worldmapframe.d(), (double)worldmapframe.b().u(), (double)worldmapframe.b().w(), (double)worldmapframe.c(), null
         );
      }

      return worldmap;
   }

   @Override
   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      DataResult<NBTBase> dataresult = MinecraftKey.a.encodeStart(DynamicOpsNBT.a, this.e.a());
      Logger logger = i;
      dataresult.resultOrPartial(logger::error).ifPresent(nbtbase -> nbttagcompound.a("dimension", nbtbase));
      if (this.uniqueId == null) {
         for(org.bukkit.World world : this.server.getWorlds()) {
            CraftWorld cWorld = (CraftWorld)world;
            if (cWorld.getHandle().ab() == this.e) {
               this.uniqueId = cWorld.getUID();
               break;
            }
         }
      }

      if (this.uniqueId != null) {
         nbttagcompound.a("UUIDLeast", this.uniqueId.getLeastSignificantBits());
         nbttagcompound.a("UUIDMost", this.uniqueId.getMostSignificantBits());
      }

      nbttagcompound.a("xCenter", this.c);
      nbttagcompound.a("zCenter", this.d);
      nbttagcompound.a("scale", this.f);
      nbttagcompound.a("colors", this.g);
      nbttagcompound.a("trackingPosition", this.l);
      nbttagcompound.a("unlimitedTracking", this.m);
      nbttagcompound.a("locked", this.h);
      NBTTagList nbttaglist = new NBTTagList();

      for(MapIconBanner mapiconbanner : this.p.values()) {
         nbttaglist.add(mapiconbanner.e());
      }

      nbttagcompound.a("banners", nbttaglist);
      NBTTagList nbttaglist1 = new NBTTagList();

      for(WorldMapFrame worldmapframe : this.r.values()) {
         nbttaglist1.add(worldmapframe.a());
      }

      nbttagcompound.a("frames", nbttaglist1);
      return nbttagcompound;
   }

   public WorldMap a() {
      WorldMap worldmap = new WorldMap(this.c, this.d, this.f, this.l, this.m, true, this.e);
      worldmap.p.putAll(this.p);
      worldmap.q.putAll(this.q);
      worldmap.s = this.s;
      System.arraycopy(this.g, 0, worldmap.g, 0, this.g.length);
      worldmap.b();
      return worldmap;
   }

   public WorldMap a(int i) {
      return a((double)this.c, (double)this.d, (byte)MathHelper.a(this.f + i, 0, 4), this.l, this.m, this.e);
   }

   public void a(EntityHuman entityhuman, ItemStack itemstack) {
      if (!this.o.containsKey(entityhuman)) {
         WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker = new WorldMap.WorldMapHumanTracker(entityhuman);
         this.o.put(entityhuman, worldmap_worldmaphumantracker);
         this.n.add(worldmap_worldmaphumantracker);
      }

      if (!entityhuman.fJ().h(itemstack)) {
         this.a(entityhuman.Z().getString());
      }

      for(int i = 0; i < this.n.size(); ++i) {
         WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker1 = this.n.get(i);
         String s = worldmap_worldmaphumantracker1.a.Z().getString();
         if (!worldmap_worldmaphumantracker1.a.dB() && (worldmap_worldmaphumantracker1.a.fJ().h(itemstack) || itemstack.E())) {
            if (!itemstack.E() && worldmap_worldmaphumantracker1.a.H.ab() == this.e && this.l) {
               this.a(
                  MapIcon.Type.a,
                  worldmap_worldmaphumantracker1.a.H,
                  s,
                  worldmap_worldmaphumantracker1.a.dl(),
                  worldmap_worldmaphumantracker1.a.dr(),
                  (double)worldmap_worldmaphumantracker1.a.dw(),
                  null
               );
            }
         } else {
            this.o.remove(worldmap_worldmaphumantracker1.a);
            this.n.remove(worldmap_worldmaphumantracker1);
            this.a(s);
         }
      }

      if (itemstack.E() && this.l) {
         EntityItemFrame entityitemframe = itemstack.F();
         BlockPosition blockposition = entityitemframe.x();
         WorldMapFrame worldmapframe = this.r.get(WorldMapFrame.a(blockposition));
         if (worldmapframe != null && entityitemframe.af() != worldmapframe.d() && this.r.containsKey(worldmapframe.e())) {
            this.a("frame-" + worldmapframe.d());
         }

         WorldMapFrame worldmapframe1 = new WorldMapFrame(blockposition, entityitemframe.cA().e() * 90, entityitemframe.af());
         this.a(
            MapIcon.Type.b,
            entityhuman.H,
            "frame-" + entityitemframe.af(),
            (double)blockposition.u(),
            (double)blockposition.w(),
            (double)(entityitemframe.cA().e() * 90),
            null
         );
         this.r.put(worldmapframe1.e(), worldmapframe1);
      }

      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null && nbttagcompound.b("Decorations", 9)) {
         NBTTagList nbttaglist = nbttagcompound.c("Decorations", 10);

         for(int j = 0; j < nbttaglist.size(); ++j) {
            NBTTagCompound nbttagcompound1 = nbttaglist.a(j);
            if (!this.q.containsKey(nbttagcompound1.l("id"))) {
               this.a(
                  MapIcon.Type.a(nbttagcompound1.f("type")),
                  entityhuman.H,
                  nbttagcompound1.l("id"),
                  nbttagcompound1.k("x"),
                  nbttagcompound1.k("z"),
                  nbttagcompound1.k("rot"),
                  null
               );
            }
         }
      }
   }

   private void a(String s) {
      MapIcon mapicon = this.q.remove(s);
      if (mapicon != null && mapicon.b().e()) {
         --this.s;
      }

      this.g();
   }

   public static void a(ItemStack itemstack, BlockPosition blockposition, String s, MapIcon.Type mapicon_type) {
      NBTTagList nbttaglist;
      if (itemstack.t() && itemstack.u().b("Decorations", 9)) {
         nbttaglist = itemstack.u().c("Decorations", 10);
      } else {
         nbttaglist = new NBTTagList();
         itemstack.a("Decorations", nbttaglist);
      }

      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.a("type", mapicon_type.a());
      nbttagcompound.a("id", s);
      nbttagcompound.a("x", (double)blockposition.u());
      nbttagcompound.a("z", (double)blockposition.w());
      nbttagcompound.a("rot", 180.0);
      nbttaglist.add(nbttagcompound);
      if (mapicon_type.c()) {
         NBTTagCompound nbttagcompound1 = itemstack.a("display");
         nbttagcompound1.a("MapColor", mapicon_type.d());
      }
   }

   private void a(
      MapIcon.Type mapicon_type,
      @Nullable GeneratorAccess generatoraccess,
      String s,
      double d0,
      double d1,
      double d2,
      @Nullable IChatBaseComponent ichatbasecomponent
   ) {
      int i = 1 << this.f;
      float f = (float)(d0 - (double)this.c) / (float)i;
      float f1 = (float)(d1 - (double)this.d) / (float)i;
      byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5));
      byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5));
      boolean flag = true;
      byte b2;
      if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
         d2 += d2 < 0.0 ? -8.0 : 8.0;
         b2 = (byte)((int)(d2 * 16.0 / 360.0));
         if (this.e == World.i && generatoraccess != null) {
            int j = (int)(generatoraccess.n_().f() / 10L);
            b2 = (byte)(j * j * 34187121 + j * 121 >> 15 & 15);
         }
      } else {
         if (mapicon_type != MapIcon.Type.a) {
            this.a(s);
            return;
         }

         boolean flag1 = true;
         if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
            mapicon_type = MapIcon.Type.g;
         } else {
            if (!this.m) {
               this.a(s);
               return;
            }

            mapicon_type = MapIcon.Type.h;
         }

         b2 = 0;
         if (f <= -63.0F) {
            b0 = -128;
         }

         if (f1 <= -63.0F) {
            b1 = -128;
         }

         if (f >= 63.0F) {
            b0 = 127;
         }

         if (f1 >= 63.0F) {
            b1 = 127;
         }
      }

      MapIcon mapicon = new MapIcon(mapicon_type, b0, b1, b2, ichatbasecomponent);
      MapIcon mapicon1 = this.q.put(s, mapicon);
      if (!mapicon.equals(mapicon1)) {
         if (mapicon1 != null && mapicon1.b().e()) {
            --this.s;
         }

         if (mapicon_type.e()) {
            ++this.s;
         }

         this.g();
      }
   }

   @Nullable
   public Packet<?> a(int i, EntityHuman entityhuman) {
      WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker = this.o.get(entityhuman);
      return worldmap_worldmaphumantracker == null ? null : worldmap_worldmaphumantracker.a(i);
   }

   public void a(int i, int j) {
      this.b();

      for(WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker : this.n) {
         worldmap_worldmaphumantracker.a(i, j);
      }
   }

   public void g() {
      this.b();
      this.n.forEach(WorldMap.WorldMapHumanTracker::access$0);
   }

   public WorldMap.WorldMapHumanTracker a(EntityHuman entityhuman) {
      WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker = this.o.get(entityhuman);
      if (worldmap_worldmaphumantracker == null) {
         worldmap_worldmaphumantracker = new WorldMap.WorldMapHumanTracker(entityhuman);
         this.o.put(entityhuman, worldmap_worldmaphumantracker);
         this.n.add(worldmap_worldmaphumantracker);
      }

      return worldmap_worldmaphumantracker;
   }

   public boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      double d0 = (double)blockposition.u() + 0.5;
      double d1 = (double)blockposition.w() + 0.5;
      int i = 1 << this.f;
      double d2 = (d0 - (double)this.c) / (double)i;
      double d3 = (d1 - (double)this.d) / (double)i;
      boolean flag = true;
      if (d2 >= -63.0 && d3 >= -63.0 && d2 <= 63.0 && d3 <= 63.0) {
         MapIconBanner mapiconbanner = MapIconBanner.a(generatoraccess, blockposition);
         if (mapiconbanner == null) {
            return false;
         }

         if (this.p.remove(mapiconbanner.f(), mapiconbanner)) {
            this.a(mapiconbanner.f());
            return true;
         }

         if (!this.b(256)) {
            this.p.put(mapiconbanner.f(), mapiconbanner);
            this.a(mapiconbanner.c(), generatoraccess, mapiconbanner.f(), d0, d1, 180.0, mapiconbanner.d());
            return true;
         }
      }

      return false;
   }

   public void a(IBlockAccess iblockaccess, int i, int j) {
      Iterator iterator = this.p.values().iterator();

      while(iterator.hasNext()) {
         MapIconBanner mapiconbanner = (MapIconBanner)iterator.next();
         if (mapiconbanner.a().u() == i && mapiconbanner.a().w() == j) {
            MapIconBanner mapiconbanner1 = MapIconBanner.a(iblockaccess, mapiconbanner.a());
            if (!mapiconbanner.equals(mapiconbanner1)) {
               iterator.remove();
               this.a(mapiconbanner.f());
            }
         }
      }
   }

   public Collection<MapIconBanner> d() {
      return this.p.values();
   }

   public void a(BlockPosition blockposition, int i) {
      this.a("frame-" + i);
      this.r.remove(WorldMapFrame.a(blockposition));
   }

   public boolean a(int i, int j, byte b0) {
      byte b1 = this.g[i + j * 128];
      if (b1 != b0) {
         this.b(i, j, b0);
         return true;
      } else {
         return false;
      }
   }

   public void b(int i, int j, byte b0) {
      this.g[i + j * 128] = b0;
      this.a(i, j);
   }

   public boolean e() {
      for(MapIcon mapicon : this.q.values()) {
         if (mapicon.b() == MapIcon.Type.i || mapicon.b() == MapIcon.Type.j) {
            return true;
         }
      }

      return false;
   }

   public void a(List<MapIcon> list) {
      this.q.clear();
      this.s = 0;

      for(int i = 0; i < list.size(); ++i) {
         MapIcon mapicon = list.get(i);
         this.q.put("icon-" + i, mapicon);
         if (mapicon.b().e()) {
            ++this.s;
         }
      }
   }

   public Iterable<MapIcon> f() {
      return this.q.values();
   }

   public boolean b(int i) {
      return this.s >= i;
   }

   public class WorldMapHumanTracker {
      public final EntityHuman a;
      private boolean d = true;
      private int e;
      private int f;
      private int g = 127;
      private int h = 127;
      private boolean i = true;
      private int j;
      public int b;

      WorldMapHumanTracker(EntityHuman entityhuman) {
         this.a = entityhuman;
      }

      private WorldMap.b createPatch(byte[] buffer) {
         int i = this.e;
         int j = this.f;
         int k = this.g + 1 - this.e;
         int l = this.h + 1 - this.f;
         byte[] abyte = new byte[k * l];

         for(int i1 = 0; i1 < k; ++i1) {
            for(int j1 = 0; j1 < l; ++j1) {
               abyte[i1 + j1 * k] = buffer[i + i1 + (j + j1) * 128];
            }
         }

         return new WorldMap.b(i, j, k, l, abyte);
      }

      @Nullable
      Packet<?> a(int i) {
         RenderData render = WorldMap.this.mapView.render((CraftPlayer)this.a.getBukkitEntity());
         WorldMap.b worldmap_b;
         if (this.d) {
            this.d = false;
            worldmap_b = this.createPatch(render.buffer);
         } else {
            worldmap_b = null;
         }

         Collection collection;
         if (this.j++ % 5 == 0) {
            this.i = false;
            Collection<MapIcon> icons = new ArrayList<>();

            for(MapCursor cursor : render.cursors) {
               if (cursor.isVisible()) {
                  icons.add(
                     new MapIcon(
                        MapIcon.Type.a(cursor.getRawType()),
                        cursor.getX(),
                        cursor.getY(),
                        cursor.getDirection(),
                        CraftChatMessage.fromStringOrNull(cursor.getCaption())
                     )
                  );
               }
            }

            collection = icons;
         } else {
            collection = null;
         }

         return collection == null && worldmap_b == null ? null : new PacketPlayOutMap(i, WorldMap.this.f, WorldMap.this.h, collection, worldmap_b);
      }

      void a(int i, int j) {
         if (this.d) {
            this.e = Math.min(this.e, i);
            this.f = Math.min(this.f, j);
            this.g = Math.max(this.g, i);
            this.h = Math.max(this.h, j);
         } else {
            this.d = true;
            this.e = i;
            this.f = j;
            this.g = i;
            this.h = j;
         }
      }

      private void b() {
         this.i = true;
      }
   }

   public static class b {
      public final int a;
      public final int b;
      public final int c;
      public final int d;
      public final byte[] e;

      public b(int i, int j, int k, int l, byte[] abyte) {
         this.a = i;
         this.b = j;
         this.c = k;
         this.d = l;
         this.e = abyte;
      }

      public void a(WorldMap worldmap) {
         for(int i = 0; i < this.c; ++i) {
            for(int j = 0; j < this.d; ++j) {
               worldmap.b(this.a + i, this.b + j, this.e[i + j * this.c]);
            }
         }
      }
   }
}
