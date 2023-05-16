package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.BehaviorController;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.phys.Vec3D;

public class AnimalPanic extends Behavior<EntityCreature> {
   private static final int c = 100;
   private static final int d = 120;
   private static final int e = 5;
   private static final int f = 4;
   private static final Predicate<EntityCreature> g = var0 -> var0.ea() != null || var0.dv() || var0.bK();
   private final float h;
   private final Predicate<EntityCreature> i;

   public AnimalPanic(float var0) {
      this(var0, g);
   }

   public AnimalPanic(float var0, Predicate<EntityCreature> var1) {
      super(ImmutableMap.of(MemoryModuleType.Y, MemoryStatus.c, MemoryModuleType.x, MemoryStatus.a), 100, 120);
      this.h = var0;
      this.i = var1;
   }

   protected boolean a(WorldServer var0, EntityCreature var1) {
      return this.i.test(var1);
   }

   protected boolean a(WorldServer var0, EntityCreature var1, long var2) {
      return true;
   }

   protected void b(WorldServer var0, EntityCreature var1, long var2) {
      var1.dH().a(MemoryModuleType.Y, true);
      var1.dH().b(MemoryModuleType.m);
   }

   protected void c(WorldServer var0, EntityCreature var1, long var2) {
      BehaviorController<?> var4 = var1.dH();
      var4.b(MemoryModuleType.Y);
   }

   protected void d(WorldServer var0, EntityCreature var1, long var2) {
      if (var1.G().l()) {
         Vec3D var4 = this.a(var1, var0);
         if (var4 != null) {
            var1.dH().a(MemoryModuleType.m, new MemoryTarget(var4, this.h, 0));
         }
      }
   }

   @Nullable
   private Vec3D a(EntityCreature var0, WorldServer var1) {
      if (var0.bK()) {
         Optional<Vec3D> var2 = this.a((IBlockAccess)var1, (Entity)var0).map(Vec3D::c);
         if (var2.isPresent()) {
            return var2.get();
         }
      }

      return LandRandomPos.a(var0, 5, 4);
   }

   private Optional<BlockPosition> a(IBlockAccess var0, Entity var1) {
      BlockPosition var2 = var1.dg();
      return !var0.a_(var2).k(var0, var2).b() ? Optional.empty() : BlockPosition.a(var2, 5, 1, var1x -> var0.b_(var1x).a(TagsFluid.a));
   }
}
