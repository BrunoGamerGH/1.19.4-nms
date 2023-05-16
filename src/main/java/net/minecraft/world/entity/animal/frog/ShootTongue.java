package net.minecraft.world.entity.animal.frog;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.phys.Vec3D;

public class ShootTongue extends Behavior<Frog> {
   public static final int c = 100;
   public static final int d = 6;
   public static final int e = 10;
   private static final float h = 1.75F;
   private static final float i = 0.75F;
   public static final int f = 100;
   public static final int g = 5;
   private int j;
   private int k;
   private final SoundEffect l;
   private final SoundEffect m;
   private Vec3D n;
   private ShootTongue.a o = ShootTongue.a.d;

   public ShootTongue(SoundEffect var0, SoundEffect var1) {
      super(
         ImmutableMap.of(
            MemoryModuleType.m, MemoryStatus.b, MemoryModuleType.n, MemoryStatus.c, MemoryModuleType.o, MemoryStatus.a, MemoryModuleType.Y, MemoryStatus.b
         ),
         100
      );
      this.l = var0;
      this.m = var1;
   }

   protected boolean a(WorldServer var0, Frog var1) {
      EntityLiving var2 = var1.dH().c(MemoryModuleType.o).get();
      boolean var3 = this.a(var1, var2);
      if (!var3) {
         var1.dH().b(MemoryModuleType.o);
         this.b(var1, var2);
      }

      return var3 && var1.al() != EntityPose.i && Frog.m(var2);
   }

   protected boolean a(WorldServer var0, Frog var1, long var2) {
      return var1.dH().a(MemoryModuleType.o) && this.o != ShootTongue.a.d && !var1.dH().a(MemoryModuleType.Y);
   }

   protected void b(WorldServer var0, Frog var1, long var2) {
      EntityLiving var4 = var1.dH().c(MemoryModuleType.o).get();
      BehaviorUtil.a(var1, var4);
      var1.a((Entity)var4);
      var1.dH().a(MemoryModuleType.m, new MemoryTarget(var4.de(), 2.0F, 0));
      this.k = 10;
      this.o = ShootTongue.a.a;
   }

   protected void c(WorldServer var0, Frog var1, long var2) {
      var1.dH().b(MemoryModuleType.o);
      var1.q();
      var1.b(EntityPose.a);
   }

   private void b(WorldServer var0, Frog var1) {
      var0.a(null, var1, this.m, SoundCategory.g, 2.0F, 1.0F);
      Optional<Entity> var2 = var1.r();
      if (var2.isPresent()) {
         Entity var3 = var2.get();
         if (var3.bq()) {
            var1.z(var3);
            if (!var3.bq()) {
               var3.a(Entity.RemovalReason.a);
            }
         }
      }
   }

   protected void d(WorldServer var0, Frog var1, long var2) {
      EntityLiving var4 = var1.dH().c(MemoryModuleType.o).get();
      var1.a((Entity)var4);
      switch(this.o) {
         case a:
            if (var4.e(var1) < 1.75F) {
               var0.a(null, var1, this.l, SoundCategory.g, 2.0F, 1.0F);
               var1.b(EntityPose.j);
               var4.f(var4.de().a(var1.de()).d().a(0.75));
               this.n = var4.de();
               this.j = 0;
               this.o = ShootTongue.a.b;
            } else if (this.k <= 0) {
               var1.dH().a(MemoryModuleType.m, new MemoryTarget(var4.de(), 2.0F, 0));
               this.k = 10;
            } else {
               --this.k;
            }
            break;
         case b:
            if (this.j++ >= 6) {
               this.o = ShootTongue.a.c;
               this.b(var0, var1);
            }
            break;
         case c:
            if (this.j >= 10) {
               this.o = ShootTongue.a.d;
            } else {
               ++this.j;
            }
         case d:
      }
   }

   private boolean a(Frog var0, EntityLiving var1) {
      PathEntity var2 = var0.G().a(var1, 0);
      return var2 != null && var2.n() < 1.75F;
   }

   private void b(Frog var0, EntityLiving var1) {
      List<UUID> var2 = var0.dH().c(MemoryModuleType.Z).orElseGet(ArrayList::new);
      boolean var3 = !var2.contains(var1.cs());
      if (var2.size() == 5 && var3) {
         var2.remove(0);
      }

      if (var3) {
         var2.add(var1.cs());
      }

      var0.dH().a(MemoryModuleType.Z, var2, 100L);
   }

   static enum a {
      a,
      b,
      c,
      d;
   }
}
