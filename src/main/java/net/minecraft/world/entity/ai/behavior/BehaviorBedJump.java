package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;

public class BehaviorBedJump extends Behavior<EntityInsentient> {
   private static final int c = 100;
   private static final int d = 3;
   private static final int e = 6;
   private static final int f = 5;
   private final float g;
   @Nullable
   private BlockPosition h;
   private int i;
   private int j;
   private int k;

   public BehaviorBedJump(float var0) {
      super(ImmutableMap.of(MemoryModuleType.w, MemoryStatus.a, MemoryModuleType.m, MemoryStatus.b));
      this.g = var0;
   }

   protected boolean a(WorldServer var0, EntityInsentient var1) {
      return var1.y_() && this.b(var0, var1);
   }

   protected void a(WorldServer var0, EntityInsentient var1, long var2) {
      super.d(var0, var1, var2);
      this.a(var1).ifPresent(var2x -> {
         this.h = var2x;
         this.i = 100;
         this.j = 3 + var0.z.a(4);
         this.k = 0;
         this.a(var1, var2x);
      });
   }

   protected void b(WorldServer var0, EntityInsentient var1, long var2) {
      super.b(var0, var1, var2);
      this.h = null;
      this.i = 0;
      this.j = 0;
      this.k = 0;
   }

   protected boolean c(WorldServer var0, EntityInsentient var1, long var2) {
      return var1.y_() && this.h != null && this.a(var0, this.h) && !this.e(var0, var1) && !this.f(var0, var1);
   }

   @Override
   protected boolean a(long var0) {
      return false;
   }

   protected void d(WorldServer var0, EntityInsentient var1, long var2) {
      if (!this.c(var0, var1)) {
         --this.i;
      } else if (this.k > 0) {
         --this.k;
      } else {
         if (this.d(var0, var1)) {
            var1.E().a();
            --this.j;
            this.k = 5;
         }
      }
   }

   private void a(EntityInsentient var0, BlockPosition var1) {
      var0.dH().a(MemoryModuleType.m, new MemoryTarget(var1, this.g, 0));
   }

   private boolean b(WorldServer var0, EntityInsentient var1) {
      return this.c(var0, var1) || this.a(var1).isPresent();
   }

   private boolean c(WorldServer var0, EntityInsentient var1) {
      BlockPosition var2 = var1.dg();
      BlockPosition var3 = var2.d();
      return this.a(var0, var2) || this.a(var0, var3);
   }

   private boolean d(WorldServer var0, EntityInsentient var1) {
      return this.a(var0, var1.dg());
   }

   private boolean a(WorldServer var0, BlockPosition var1) {
      return var0.a_(var1).a(TagsBlock.Q);
   }

   private Optional<BlockPosition> a(EntityInsentient var0) {
      return var0.dH().c(MemoryModuleType.w);
   }

   private boolean e(WorldServer var0, EntityInsentient var1) {
      return !this.c(var0, var1) && this.i <= 0;
   }

   private boolean f(WorldServer var0, EntityInsentient var1) {
      return this.c(var0, var1) && this.j <= 0;
   }
}
