package net.minecraft.world.entity.raid;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.network.protocol.game.PacketPlayOutEntityStatus;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceRecord;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.saveddata.PersistentBase;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PersistentRaid extends PersistentBase {
   private static final String a = "raids";
   public final Map<Integer, Raid> b = Maps.newHashMap();
   private final WorldServer c;
   private int d;
   private int e;

   public PersistentRaid(WorldServer worldserver) {
      this.c = worldserver;
      this.d = 1;
      this.b();
   }

   public Raid a(int i) {
      return this.b.get(i);
   }

   public void a() {
      ++this.e;
      Iterator iterator = this.b.values().iterator();

      while(iterator.hasNext()) {
         Raid raid = (Raid)iterator.next();
         if (this.c.W().b(GameRules.z)) {
            raid.n();
         }

         if (raid.d()) {
            iterator.remove();
            this.b();
         } else {
            raid.o();
         }
      }

      if (this.e % 200 == 0) {
         this.b();
      }

      PacketDebug.a(this.c, this.b.values());
   }

   public static boolean a(EntityRaider entityraider, Raid raid) {
      return entityraider != null && raid != null && raid.i() != null
         ? entityraider.bq() && entityraider.gf() && entityraider.ee() <= 2400 && entityraider.H.q_() == raid.i().q_()
         : false;
   }

   @Nullable
   public Raid a(EntityPlayer entityplayer) {
      if (entityplayer.F_()) {
         return null;
      } else if (this.c.W().b(GameRules.z)) {
         return null;
      } else {
         DimensionManager dimensionmanager = entityplayer.H.q_();
         if (!dimensionmanager.c()) {
            return null;
         } else {
            BlockPosition blockposition = entityplayer.dg();
            List<VillagePlaceRecord> list = this.c.w().c(holder -> holder.a(PoiTypeTags.b), blockposition, 64, VillagePlace.Occupancy.b).toList();
            int i = 0;
            Vec3D vec3d = Vec3D.b;

            for(VillagePlaceRecord villageplacerecord : list) {
               BlockPosition blockposition1 = villageplacerecord.f();
               vec3d = vec3d.b((double)blockposition1.u(), (double)blockposition1.v(), (double)blockposition1.w());
               ++i;
            }

            BlockPosition blockposition2;
            if (i > 0) {
               vec3d = vec3d.a(1.0 / (double)i);
               blockposition2 = BlockPosition.a(vec3d);
            } else {
               blockposition2 = blockposition;
            }

            Raid raid = this.a(entityplayer.x(), blockposition2);
            boolean flag = false;
            if (!raid.j()) {
               flag = true;
            } else if (raid.isInProgress() && raid.m() < raid.l()) {
               flag = true;
            } else {
               entityplayer.d(MobEffects.E);
               entityplayer.b.a(new PacketPlayOutEntityStatus(entityplayer, (byte)43));
            }

            if (flag) {
               if (!CraftEventFactory.callRaidTriggerEvent(raid, entityplayer)) {
                  entityplayer.d(MobEffects.E);
                  return null;
               }

               if (!this.b.containsKey(raid.u())) {
                  this.b.put(raid.u(), raid);
               }

               raid.a((EntityHuman)entityplayer);
               entityplayer.b.a(new PacketPlayOutEntityStatus(entityplayer, (byte)43));
               if (!raid.c()) {
                  entityplayer.a(StatisticList.aA);
                  CriterionTriggers.I.a(entityplayer);
               }
            }

            this.b();
            return raid;
         }
      }
   }

   private Raid a(WorldServer worldserver, BlockPosition blockposition) {
      Raid raid = worldserver.c(blockposition);
      return raid != null ? raid : new Raid(this.d(), worldserver, blockposition);
   }

   public static PersistentRaid a(WorldServer worldserver, NBTTagCompound nbttagcompound) {
      PersistentRaid persistentraid = new PersistentRaid(worldserver);
      persistentraid.d = nbttagcompound.h("NextAvailableID");
      persistentraid.e = nbttagcompound.h("Tick");
      NBTTagList nbttaglist = nbttagcompound.c("Raids", 10);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound1 = nbttaglist.a(i);
         Raid raid = new Raid(worldserver, nbttagcompound1);
         persistentraid.b.put(raid.u(), raid);
      }

      return persistentraid;
   }

   @Override
   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("NextAvailableID", this.d);
      nbttagcompound.a("Tick", this.e);
      NBTTagList nbttaglist = new NBTTagList();

      for(Raid raid : this.b.values()) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         raid.a(nbttagcompound1);
         nbttaglist.add(nbttagcompound1);
      }

      nbttagcompound.a("Raids", nbttaglist);
      return nbttagcompound;
   }

   public static String a(Holder<DimensionManager> holder) {
      return holder.a(BuiltinDimensionTypes.c) ? "raids_end" : "raids";
   }

   private int d() {
      return ++this.d;
   }

   @Nullable
   public Raid a(BlockPosition blockposition, int i) {
      Raid raid = null;
      double d0 = (double)i;

      for(Raid raid1 : this.b.values()) {
         double d1 = raid1.t().j(blockposition);
         if (raid1.v() && d1 < d0) {
            raid = raid1;
            d0 = d1;
         }
      }

      return raid;
   }
}
