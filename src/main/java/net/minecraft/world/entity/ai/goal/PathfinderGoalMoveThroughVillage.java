package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalMoveThroughVillage extends PathfinderGoal {
   protected final EntityCreature a;
   private final double b;
   @Nullable
   private PathEntity c;
   private BlockPosition d;
   private final boolean e;
   private final List<BlockPosition> f = Lists.newArrayList();
   private final int g;
   private final BooleanSupplier h;

   public PathfinderGoalMoveThroughVillage(EntityCreature var0, double var1, boolean var3, int var4, BooleanSupplier var5) {
      this.a = var0;
      this.b = var1;
      this.e = var3;
      this.g = var4;
      this.h = var5;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
      if (!PathfinderGoalUtil.a(var0)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   @Override
   public boolean a() {
      if (!PathfinderGoalUtil.a(this.a)) {
         return false;
      } else {
         this.h();
         if (this.e && this.a.H.M()) {
            return false;
         } else {
            WorldServer var0 = (WorldServer)this.a.H;
            BlockPosition var1 = this.a.dg();
            if (!var0.a(var1, 6)) {
               return false;
            } else {
               Vec3D var2 = LandRandomPos.a(this.a, 15, 7, var2x -> {
                  if (!var0.b(var2x)) {
                     return Double.NEGATIVE_INFINITY;
                  } else {
                     Optional<BlockPosition> var3x = var0.w().d(var0xx -> var0xx.a(PoiTypeTags.b), this::a, var2x, 10, VillagePlace.Occupancy.b);
                     return var3x.<Double>map(var1xx -> -var1xx.j(var1)).orElse(Double.NEGATIVE_INFINITY);
                  }
               });
               if (var2 == null) {
                  return false;
               } else {
                  Optional<BlockPosition> var3 = var0.w().d(var0x -> var0x.a(PoiTypeTags.b), this::a, BlockPosition.a(var2), 10, VillagePlace.Occupancy.b);
                  if (var3.isEmpty()) {
                     return false;
                  } else {
                     this.d = var3.get().i();
                     Navigation var4 = (Navigation)this.a.G();
                     boolean var5 = var4.f();
                     var4.b(this.h.getAsBoolean());
                     this.c = var4.a(this.d, 0);
                     var4.b(var5);
                     if (this.c == null) {
                        Vec3D var6 = DefaultRandomPos.a(this.a, 10, 7, Vec3D.c(this.d), (float) (Math.PI / 2));
                        if (var6 == null) {
                           return false;
                        }

                        var4.b(this.h.getAsBoolean());
                        this.c = this.a.G().a(var6.c, var6.d, var6.e, 0);
                        var4.b(var5);
                        if (this.c == null) {
                           return false;
                        }
                     }

                     for(int var6 = 0; var6 < this.c.e(); ++var6) {
                        PathPoint var7 = this.c.a(var6);
                        BlockPosition var8 = new BlockPosition(var7.a, var7.b + 1, var7.c);
                        if (BlockDoor.a(this.a.H, var8)) {
                           this.c = this.a.G().a((double)var7.a, (double)var7.b, (double)var7.c, 0);
                           break;
                        }
                     }

                     return this.c != null;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean b() {
      if (this.a.G().l()) {
         return false;
      } else {
         return !this.d.a(this.a.de(), (double)(this.a.dc() + (float)this.g));
      }
   }

   @Override
   public void c() {
      this.a.G().a(this.c, this.b);
   }

   @Override
   public void d() {
      if (this.a.G().l() || this.d.a(this.a.de(), (double)this.g)) {
         this.f.add(this.d);
      }
   }

   private boolean a(BlockPosition var0) {
      for(BlockPosition var2 : this.f) {
         if (Objects.equals(var0, var2)) {
            return false;
         }
      }

      return true;
   }

   private void h() {
      if (this.f.size() > 15) {
         this.f.remove(0);
      }
   }
}
