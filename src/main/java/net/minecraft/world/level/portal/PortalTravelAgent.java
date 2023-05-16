package net.minecraft.world.level.portal;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceRecord;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceType;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.BlockPortal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.levelgen.HeightMap;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class PortalTravelAgent {
   private static final int a = 3;
   private static final int b = 128;
   private static final int c = 16;
   private static final int d = 5;
   private static final int e = 4;
   private static final int f = 3;
   private static final int g = -1;
   private static final int h = 4;
   private static final int i = -1;
   private static final int j = 3;
   private static final int k = -1;
   private static final int l = 2;
   private static final int m = -1;
   private final WorldServer n;

   public PortalTravelAgent(WorldServer worldserver) {
      this.n = worldserver;
   }

   public Optional<BlockUtil.Rectangle> a(BlockPosition blockposition, boolean flag, WorldBorder worldborder) {
      return this.findPortalAround(blockposition, worldborder, flag ? 16 : 128);
   }

   public Optional<BlockUtil.Rectangle> findPortalAround(BlockPosition blockposition, WorldBorder worldborder, int i) {
      VillagePlace villageplace = this.n.w();
      villageplace.a(this.n, blockposition, i);
      Optional<VillagePlaceRecord> optional = villageplace.b(holder -> holder.a(PoiTypes.r), blockposition, i, VillagePlace.Occupancy.c)
         .filter(villageplacerecord -> worldborder.a(villageplacerecord.f()))
         .sorted(
            Comparator.<VillagePlaceRecord>comparingDouble(villageplacerecord -> villageplacerecord.f().j(blockposition))
               .thenComparingInt(villageplacerecord -> villageplacerecord.f().v())
         )
         .filter(villageplacerecord -> this.n.a_(villageplacerecord.f()).b(BlockProperties.H))
         .findFirst();
      return optional.map(
         villageplacerecord -> {
            BlockPosition blockposition1 = villageplacerecord.f();
            this.n.k().a(TicketType.f, new ChunkCoordIntPair(blockposition1), 3, blockposition1);
            IBlockData iblockdata = this.n.a_(blockposition1);
            return BlockUtil.a(
               blockposition1, iblockdata.c(BlockProperties.H), 21, EnumDirection.EnumAxis.b, 21, blockposition2 -> this.n.a_(blockposition2) == iblockdata
            );
         }
      );
   }

   public Optional<BlockUtil.Rectangle> a(BlockPosition blockposition, EnumDirection.EnumAxis enumdirection_enumaxis) {
      return this.createPortal(blockposition, enumdirection_enumaxis, null, 16);
   }

   public Optional<BlockUtil.Rectangle> createPortal(
      BlockPosition blockposition, EnumDirection.EnumAxis enumdirection_enumaxis, Entity entity, int createRadius
   ) {
      EnumDirection enumdirection = EnumDirection.a(EnumDirection.EnumAxisDirection.a, enumdirection_enumaxis);
      double d0 = -1.0;
      BlockPosition blockposition1 = null;
      double d1 = -1.0;
      BlockPosition blockposition2 = null;
      WorldBorder worldborder = this.n.p_();
      int i = Math.min(this.n.ai(), this.n.v_() + this.n.j()) - 1;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 : BlockPosition.a(
         blockposition, createRadius, EnumDirection.f, EnumDirection.d
      )) {
         int j = Math.min(i, this.n.a(HeightMap.Type.e, blockposition_mutableblockposition1.u(), blockposition_mutableblockposition1.w()));
         boolean flag = true;
         if (worldborder.a(blockposition_mutableblockposition1) && worldborder.a(blockposition_mutableblockposition1.c(enumdirection, 1))) {
            blockposition_mutableblockposition1.c(enumdirection.g(), 1);

            for(int k = j; k >= this.n.v_(); --k) {
               blockposition_mutableblockposition1.q(k);
               if (this.a(blockposition_mutableblockposition1)) {
                  int l = k;

                  while(k > this.n.v_() && this.a(blockposition_mutableblockposition1.c(EnumDirection.a))) {
                     --k;
                  }

                  if (k + 4 <= i) {
                     int i1 = l - k;
                     if (i1 <= 0 || i1 >= 3) {
                        blockposition_mutableblockposition1.q(k);
                        if (this.a(blockposition_mutableblockposition1, blockposition_mutableblockposition, enumdirection, 0)) {
                           double d2 = blockposition.j(blockposition_mutableblockposition1);
                           if (this.a(blockposition_mutableblockposition1, blockposition_mutableblockposition, enumdirection, -1)
                              && this.a(blockposition_mutableblockposition1, blockposition_mutableblockposition, enumdirection, 1)
                              && (d0 == -1.0 || d0 > d2)) {
                              d0 = d2;
                              blockposition1 = blockposition_mutableblockposition1.i();
                           }

                           if (d0 == -1.0 && (d1 == -1.0 || d1 > d2)) {
                              d1 = d2;
                              blockposition2 = blockposition_mutableblockposition1.i();
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (d0 == -1.0 && d1 != -1.0) {
         blockposition1 = blockposition2;
         d0 = d1;
      }

      BlockStateListPopulator blockList = new BlockStateListPopulator(this.n);
      if (d0 == -1.0) {
         int j1 = Math.max(this.n.v_() - -1, 70);
         int k1 = i - 9;
         if (k1 < j1) {
            return Optional.empty();
         }

         blockposition1 = new BlockPosition(blockposition.u(), MathHelper.a(blockposition.v(), j1, k1), blockposition.w()).i();
         EnumDirection enumdirection1 = enumdirection.h();
         if (!worldborder.a(blockposition1)) {
            return Optional.empty();
         }

         for(int l1 = -1; l1 < 2; ++l1) {
            for(int k = 0; k < 2; ++k) {
               for(int l = -1; l < 3; ++l) {
                  IBlockData iblockdata = l < 0 ? Blocks.cn.o() : Blocks.a.o();
                  blockposition_mutableblockposition.a(
                     blockposition1, k * enumdirection.j() + l1 * enumdirection1.j(), l, k * enumdirection.l() + l1 * enumdirection1.l()
                  );
                  blockList.a(blockposition_mutableblockposition, iblockdata, 3);
               }
            }
         }
      }

      for(int j1 = -1; j1 < 3; ++j1) {
         for(int k1 = -1; k1 < 4; ++k1) {
            if (j1 == -1 || j1 == 2 || k1 == -1 || k1 == 3) {
               blockposition_mutableblockposition.a(blockposition1, j1 * enumdirection.j(), k1, j1 * enumdirection.l());
               blockList.a(blockposition_mutableblockposition, Blocks.cn.o(), 3);
            }
         }
      }

      IBlockData iblockdata1 = Blocks.ed.o().a(BlockPortal.a, enumdirection_enumaxis);

      for(int k1 = 0; k1 < 2; ++k1) {
         for(int j = 0; j < 3; ++j) {
            blockposition_mutableblockposition.a(blockposition1, k1 * enumdirection.j(), j, k1 * enumdirection.l());
            blockList.a(blockposition_mutableblockposition, iblockdata1, 18);
         }
      }

      World bworld = this.n.getWorld();
      PortalCreateEvent event = new PortalCreateEvent(blockList.getList(), bworld, entity == null ? null : entity.getBukkitEntity(), CreateReason.NETHER_PAIR);
      this.n.getCraftServer().getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         return Optional.empty();
      } else {
         blockList.updateList();
         return Optional.of(new BlockUtil.Rectangle(blockposition1.i(), 2, 3));
      }
   }

   private boolean a(BlockPosition.MutableBlockPosition blockposition_mutableblockposition) {
      IBlockData iblockdata = this.n.a_(blockposition_mutableblockposition);
      return iblockdata.o() && iblockdata.r().c();
   }

   private boolean a(BlockPosition blockposition, BlockPosition.MutableBlockPosition blockposition_mutableblockposition, EnumDirection enumdirection, int i) {
      EnumDirection enumdirection1 = enumdirection.h();

      for(int j = -1; j < 3; ++j) {
         for(int k = -1; k < 4; ++k) {
            blockposition_mutableblockposition.a(
               blockposition, enumdirection.j() * j + enumdirection1.j() * i, k, enumdirection.l() * j + enumdirection1.l() * i
            );
            if (k < 0 && !this.n.a_(blockposition_mutableblockposition).d().b()) {
               return false;
            }

            if (k >= 0 && !this.a(blockposition_mutableblockposition)) {
               return false;
            }
         }
      }

      return true;
   }
}
