package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalNearestVillage extends PathfinderGoal {
   private static final int a = 10;
   private final EntityCreature b;
   private final int c;
   @Nullable
   private BlockPosition d;

   public PathfinderGoalNearestVillage(EntityCreature var0, int var1) {
      this.b = var0;
      this.c = b(var1);
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (this.b.bM()) {
         return false;
      } else if (this.b.H.M()) {
         return false;
      } else if (this.b.dZ().a(this.c) != 0) {
         return false;
      } else {
         WorldServer var0 = (WorldServer)this.b.H;
         BlockPosition var1 = this.b.dg();
         if (!var0.a(var1, 6)) {
            return false;
         } else {
            Vec3D var2 = LandRandomPos.a(this.b, 15, 7, var1x -> (double)(-var0.b(SectionPosition.a(var1x))));
            this.d = var2 == null ? null : BlockPosition.a(var2);
            return this.d != null;
         }
      }
   }

   @Override
   public boolean b() {
      return this.d != null && !this.b.G().l() && this.b.G().h().equals(this.d);
   }

   @Override
   public void e() {
      if (this.d != null) {
         NavigationAbstract var0 = this.b.G();
         if (var0.l() && !this.d.a(this.b.de(), 10.0)) {
            Vec3D var1 = Vec3D.c(this.d);
            Vec3D var2 = this.b.de();
            Vec3D var3 = var2.d(var1);
            var1 = var3.a(0.4).e(var1);
            Vec3D var4 = var1.d(var2).d().a(10.0).e(var2);
            BlockPosition var5 = BlockPosition.a(var4);
            var5 = this.b.H.a(HeightMap.Type.f, var5);
            if (!var0.a((double)var5.u(), (double)var5.v(), (double)var5.w(), 1.0)) {
               this.h();
            }
         }
      }
   }

   private void h() {
      RandomSource var0 = this.b.dZ();
      BlockPosition var1 = this.b.H.a(HeightMap.Type.f, this.b.dg().b(-8 + var0.a(16), 0, -8 + var0.a(16)));
      this.b.G().a((double)var1.u(), (double)var1.v(), (double)var1.w(), 1.0);
   }
}
