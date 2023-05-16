package net.minecraft.world.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.entity.raid.PersistentRaid;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalRaid<T extends EntityRaider> extends PathfinderGoal {
   private static final int a = 20;
   private static final float b = 1.0F;
   private final T c;
   private int d;

   public PathfinderGoalRaid(T var0) {
      this.c = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      return this.c.P_() == null && !this.c.bM() && this.c.gh() && !this.c.gg().a() && !((WorldServer)this.c.H).b(this.c.dg());
   }

   @Override
   public boolean b() {
      return this.c.gh() && !this.c.gg().a() && this.c.H instanceof WorldServer && !((WorldServer)this.c.H).b(this.c.dg());
   }

   @Override
   public void e() {
      if (this.c.gh()) {
         Raid var0 = this.c.gg();
         if (this.c.ag > this.d) {
            this.d = this.c.ag + 20;
            this.a(var0);
         }

         if (!this.c.fP()) {
            Vec3D var1 = DefaultRandomPos.a(this.c, 15, 4, Vec3D.c(var0.t()), (float) (Math.PI / 2));
            if (var1 != null) {
               this.c.G().a(var1.c, var1.d, var1.e, 1.0);
            }
         }
      }
   }

   private void a(Raid var0) {
      if (var0.v()) {
         Set<EntityRaider> var1 = Sets.newHashSet();
         List<EntityRaider> var2 = this.c.H.a(EntityRaider.class, this.c.cD().g(16.0), var1x -> !var1x.gh() && PersistentRaid.a(var1x, var0));
         var1.addAll(var2);

         for(EntityRaider var4 : var1) {
            var0.a(var0.k(), var4, null, true);
         }
      }
   }
}
