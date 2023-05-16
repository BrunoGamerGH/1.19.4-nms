package net.minecraft.world.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.ai.village.poi.VillagePlaceRecord;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalStrollVillageGolem extends PathfinderGoalRandomStroll {
   private static final int i = 2;
   private static final int j = 32;
   private static final int k = 10;
   private static final int l = 7;

   public PathfinderGoalStrollVillageGolem(EntityCreature var0, double var1) {
      super(var0, var1, 240, false);
   }

   @Nullable
   @Override
   protected Vec3D h() {
      float var1 = this.b.H.z.i();
      if (this.b.H.z.i() < 0.3F) {
         return this.k();
      } else {
         Vec3D var0;
         if (var1 < 0.7F) {
            var0 = this.l();
            if (var0 == null) {
               var0 = this.m();
            }
         } else {
            var0 = this.m();
            if (var0 == null) {
               var0 = this.l();
            }
         }

         return var0 == null ? this.k() : var0;
      }
   }

   @Nullable
   private Vec3D k() {
      return LandRandomPos.a(this.b, 10, 7);
   }

   @Nullable
   private Vec3D l() {
      WorldServer var0 = (WorldServer)this.b.H;
      List<EntityVillager> var1 = var0.a(EntityTypes.bf, this.b.cD().g(32.0), this::a);
      if (var1.isEmpty()) {
         return null;
      } else {
         EntityVillager var2 = var1.get(this.b.H.z.a(var1.size()));
         Vec3D var3 = var2.de();
         return LandRandomPos.a(this.b, 10, 7, var3);
      }
   }

   @Nullable
   private Vec3D m() {
      SectionPosition var0 = this.n();
      if (var0 == null) {
         return null;
      } else {
         BlockPosition var1 = this.a(var0);
         return var1 == null ? null : LandRandomPos.a(this.b, 10, 7, Vec3D.c(var1));
      }
   }

   @Nullable
   private SectionPosition n() {
      WorldServer var0 = (WorldServer)this.b.H;
      List<SectionPosition> var1 = SectionPosition.a(SectionPosition.a(this.b), 2).filter(var1x -> var0.b(var1x) == 0).collect(Collectors.toList());
      return var1.isEmpty() ? null : var1.get(var0.z.a(var1.size()));
   }

   @Nullable
   private BlockPosition a(SectionPosition var0) {
      WorldServer var1 = (WorldServer)this.b.H;
      VillagePlace var2 = var1.w();
      List<BlockPosition> var3 = var2.c(var0x -> true, var0.q(), 8, VillagePlace.Occupancy.b).map(VillagePlaceRecord::f).collect(Collectors.toList());
      return var3.isEmpty() ? null : var3.get(var1.z.a(var3.size()));
   }

   private boolean a(EntityVillager var0) {
      return var0.a(this.b.H.U());
   }
}
