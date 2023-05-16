package net.minecraft.world.entity.npc;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityPositionTypes;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.ai.village.poi.VillagePlace;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.SpawnerCreature;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.AxisAlignedBB;

public class MobSpawnerCat implements MobSpawner {
   private static final int a = 1200;
   private int b;

   @Override
   public int a(WorldServer var0, boolean var1, boolean var2) {
      if (var2 && var0.W().b(GameRules.e)) {
         --this.b;
         if (this.b > 0) {
            return 0;
         } else {
            this.b = 1200;
            EntityHuman var3 = var0.i();
            if (var3 == null) {
               return 0;
            } else {
               RandomSource var4 = var0.z;
               int var5 = (8 + var4.a(24)) * (var4.h() ? -1 : 1);
               int var6 = (8 + var4.a(24)) * (var4.h() ? -1 : 1);
               BlockPosition var7 = var3.dg().b(var5, 0, var6);
               int var8 = 10;
               if (!var0.b(var7.u() - 10, var7.w() - 10, var7.u() + 10, var7.w() + 10)) {
                  return 0;
               } else {
                  if (SpawnerCreature.a(EntityPositionTypes.Surface.a, var0, var7, EntityTypes.m)) {
                     if (var0.a(var7, 2)) {
                        return this.a(var0, var7);
                     }

                     if (var0.a().a(var7, StructureTags.f).b()) {
                        return this.b(var0, var7);
                     }
                  }

                  return 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private int a(WorldServer var0, BlockPosition var1) {
      int var2 = 48;
      if (var0.w().a(var0x -> var0x.a(PoiTypes.n), var1, 48, VillagePlace.Occupancy.b) > 4L) {
         List<EntityCat> var3 = var0.a(EntityCat.class, new AxisAlignedBB(var1).c(48.0, 8.0, 48.0));
         if (var3.size() < 5) {
            return this.a(var1, var0);
         }
      }

      return 0;
   }

   private int b(WorldServer var0, BlockPosition var1) {
      int var2 = 16;
      List<EntityCat> var3 = var0.a(EntityCat.class, new AxisAlignedBB(var1).c(16.0, 8.0, 16.0));
      return var3.size() < 1 ? this.a(var1, var0) : 0;
   }

   private int a(BlockPosition var0, WorldServer var1) {
      EntityCat var2 = EntityTypes.m.a((World)var1);
      if (var2 == null) {
         return 0;
      } else {
         var2.a(var1, var1.d_(var0), EnumMobSpawn.a, null, null);
         var2.a(var0, 0.0F, 0.0F);
         var1.a_(var2);
         return 1;
      }
   }
}
