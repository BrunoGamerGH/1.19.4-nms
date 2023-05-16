package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.navigation.NavigationAbstract;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class BehavorMove extends Behavior<EntityInsentient> {
   private static final int c = 40;
   private int d;
   @Nullable
   private PathEntity e;
   @Nullable
   private BlockPosition f;
   private float g;

   public BehavorMove() {
      this(150, 250);
   }

   public BehavorMove(int var0, int var1) {
      super(ImmutableMap.of(MemoryModuleType.E, MemoryStatus.c, MemoryModuleType.t, MemoryStatus.b, MemoryModuleType.m, MemoryStatus.a), var0, var1);
   }

   protected boolean a(WorldServer var0, EntityInsentient var1) {
      if (this.d > 0) {
         --this.d;
         return false;
      } else {
         BehaviorController<?> var2 = var1.dH();
         MemoryTarget var3 = var2.c(MemoryModuleType.m).get();
         boolean var4 = this.a(var1, var3);
         if (!var4 && this.a(var1, var3, var0.U())) {
            this.f = var3.a().b();
            return true;
         } else {
            var2.b(MemoryModuleType.m);
            if (var4) {
               var2.b(MemoryModuleType.E);
            }

            return false;
         }
      }
   }

   protected boolean a(WorldServer var0, EntityInsentient var1, long var2) {
      if (this.e != null && this.f != null) {
         Optional<MemoryTarget> var4 = var1.dH().c(MemoryModuleType.m);
         NavigationAbstract var5 = var1.G();
         return !var5.l() && var4.isPresent() && !this.a(var1, var4.get());
      } else {
         return false;
      }
   }

   protected void b(WorldServer var0, EntityInsentient var1, long var2) {
      if (var1.dH().a(MemoryModuleType.m) && !this.a(var1, var1.dH().c(MemoryModuleType.m).get()) && var1.G().s()) {
         this.d = var0.r_().a(40);
      }

      var1.G().n();
      var1.dH().b(MemoryModuleType.m);
      var1.dH().b(MemoryModuleType.t);
      this.e = null;
   }

   protected void c(WorldServer var0, EntityInsentient var1, long var2) {
      var1.dH().a(MemoryModuleType.t, this.e);
      var1.G().a(this.e, (double)this.g);
   }

   protected void d(WorldServer var0, EntityInsentient var1, long var2) {
      PathEntity var4 = var1.G().j();
      BehaviorController<?> var5 = var1.dH();
      if (this.e != var4) {
         this.e = var4;
         var5.a(MemoryModuleType.t, var4);
      }

      if (var4 != null && this.f != null) {
         MemoryTarget var6 = var5.c(MemoryModuleType.m).get();
         if (var6.a().b().j(this.f) > 4.0 && this.a(var1, var6, var0.U())) {
            this.f = var6.a().b();
            this.c(var0, var1, var2);
         }
      }
   }

   private boolean a(EntityInsentient var0, MemoryTarget var1, long var2) {
      BlockPosition var4 = var1.a().b();
      this.e = var0.G().a(var4, 0);
      this.g = var1.b();
      BehaviorController<?> var5 = var0.dH();
      if (this.a(var0, var1)) {
         var5.b(MemoryModuleType.E);
      } else {
         boolean var6 = this.e != null && this.e.j();
         if (var6) {
            var5.b(MemoryModuleType.E);
         } else if (!var5.a(MemoryModuleType.E)) {
            var5.a(MemoryModuleType.E, var2);
         }

         if (this.e != null) {
            return true;
         }

         Vec3D var7 = DefaultRandomPos.a((EntityCreature)var0, 10, 7, Vec3D.c(var4), (float) (Math.PI / 2));
         if (var7 != null) {
            this.e = var0.G().a(var7.c, var7.d, var7.e, 0);
            return this.e != null;
         }
      }

      return false;
   }

   private boolean a(EntityInsentient var0, MemoryTarget var1) {
      return var1.a().b().k(var0.dg()) <= var1.c();
   }
}
