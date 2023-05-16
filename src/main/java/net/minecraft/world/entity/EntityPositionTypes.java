package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityMushroomCow;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityPolarBear;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.monster.EntityDrowned;
import net.minecraft.world.entity.monster.EntityEndermite;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.monster.EntityMagmaCube;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityMonsterPatrolling;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.monster.EntitySkeletonStray;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.EntityZombieHusk;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.levelgen.HeightMap;

public class EntityPositionTypes {
   private static final Map<EntityTypes<?>, EntityPositionTypes.a> a = Maps.newHashMap();

   private static <T extends EntityInsentient> void a(
      EntityTypes<T> var0, EntityPositionTypes.Surface var1, HeightMap.Type var2, EntityPositionTypes.b<T> var3
   ) {
      EntityPositionTypes.a var4 = a.put(var0, new EntityPositionTypes.a(var2, var1, var3));
      if (var4 != null) {
         throw new IllegalStateException("Duplicate registration for type " + BuiltInRegistries.h.b(var0));
      }
   }

   public static EntityPositionTypes.Surface a(EntityTypes<?> var0) {
      EntityPositionTypes.a var1 = a.get(var0);
      return var1 == null ? EntityPositionTypes.Surface.c : var1.b;
   }

   public static HeightMap.Type b(@Nullable EntityTypes<?> var0) {
      EntityPositionTypes.a var1 = a.get(var0);
      return var1 == null ? HeightMap.Type.f : var1.a;
   }

   public static <T extends Entity> boolean a(EntityTypes<T> var0, WorldAccess var1, EnumMobSpawn var2, BlockPosition var3, RandomSource var4) {
      EntityPositionTypes.a var5 = a.get(var0);
      return var5 == null || var5.c.test(var0, var1, var2, var3, var4);
   }

   static {
      a(EntityTypes.f, EntityPositionTypes.Surface.b, HeightMap.Type.f, Axolotl::a);
      a(EntityTypes.r, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityWaterAnimal::c);
      a(EntityTypes.v, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityWaterAnimal::c);
      a(EntityTypes.y, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityDrowned::a);
      a(EntityTypes.V, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityGuardian::b);
      a(EntityTypes.aB, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityWaterAnimal::c);
      a(EntityTypes.aE, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityWaterAnimal::c);
      a(EntityTypes.aT, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityWaterAnimal::c);
      a(EntityTypes.bc, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityTropicalFish::b);
      a(EntityTypes.g, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityBat::b);
      a(EntityTypes.i, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::c);
      a(EntityTypes.n, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.q, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.t, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.u, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.w, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.E, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.F, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityEndermite::b);
      a(EntityTypes.C, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.O, EntityPositionTypes.Surface.a, HeightMap.Type.f, Frog::c);
      a(EntityTypes.Q, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityGhast::b);
      a(EntityTypes.R, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.T, EntityPositionTypes.Surface.b, HeightMap.Type.f, GlowSquid::a);
      a(EntityTypes.U, EntityPositionTypes.Surface.a, HeightMap.Type.f, Goat::c);
      a(EntityTypes.Y, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.Z, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityZombieHusk::a);
      a(EntityTypes.ac, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.aj, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.al, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMagmaCube::b);
      a(EntityTypes.ao, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMushroomCow::c);
      a(EntityTypes.ap, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.aq, EntityPositionTypes.Surface.a, HeightMap.Type.e, EntityOcelot::c);
      a(EntityTypes.at, EntityPositionTypes.Surface.a, HeightMap.Type.e, EntityParrot::c);
      a(EntityTypes.av, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.W, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityHoglin::c);
      a(EntityTypes.aw, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityPiglin::b);
      a(EntityTypes.ay, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonsterPatrolling::b);
      a(EntityTypes.az, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityPolarBear::c);
      a(EntityTypes.aC, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityRabbit::c);
      a(EntityTypes.aF, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.aI, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntitySilverfish::b);
      a(EntityTypes.aJ, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.aK, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.aL, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntitySlime::c);
      a(EntityTypes.aO, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.aS, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.aU, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntitySkeletonStray::a);
      a(EntityTypes.aV, EntityPositionTypes.Surface.d, HeightMap.Type.f, EntityStrider::c);
      a(EntityTypes.bd, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityTurtle::c);
      a(EntityTypes.bf, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.bj, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bk, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bl, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bn, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityWolf::c);
      a(EntityTypes.bp, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bq, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.bs, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityPigZombie::b);
      a(EntityTypes.br, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.m, EntityPositionTypes.Surface.a, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.A, EntityPositionTypes.Surface.b, HeightMap.Type.f, EntityGuardian::b);
      a(EntityTypes.G, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.N, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityFox::c);
      a(EntityTypes.aa, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.as, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.au, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.aD, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.aG, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.ba, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityAnimal::b);
      a(EntityTypes.be, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bg, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityMonster::b);
      a(EntityTypes.bh, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityInsentient::a);
      a(EntityTypes.bi, EntityPositionTypes.Surface.c, HeightMap.Type.f, EntityInsentient::a);
   }

   public static enum Surface {
      a,
      b,
      c,
      d;
   }

   static class a {
      final HeightMap.Type a;
      final EntityPositionTypes.Surface b;
      final EntityPositionTypes.b<?> c;

      public a(HeightMap.Type var0, EntityPositionTypes.Surface var1, EntityPositionTypes.b<?> var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }

   @FunctionalInterface
   public interface b<T extends Entity> {
      boolean test(EntityTypes<T> var1, WorldAccess var2, EnumMobSpawn var3, BlockPosition var4, RandomSource var5);
   }
}
