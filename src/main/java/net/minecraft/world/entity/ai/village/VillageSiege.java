package net.minecraft.world.entity.ai.village;

import com.mojang.logging.LogUtils;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.MobSpawner;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.slf4j.Logger;

public class VillageSiege implements MobSpawner {
   private static final Logger a = LogUtils.getLogger();
   private boolean b;
   private VillageSiege.State c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;

   public VillageSiege() {
      this.c = VillageSiege.State.c;
   }

   @Override
   public int a(WorldServer worldserver, boolean flag, boolean flag1) {
      if (!worldserver.M() && flag) {
         float f = worldserver.f(0.0F);
         if ((double)f == 0.5) {
            this.c = worldserver.z.a(10) == 0 ? VillageSiege.State.b : VillageSiege.State.c;
         }

         if (this.c == VillageSiege.State.c) {
            return 0;
         } else {
            if (!this.b) {
               if (!this.a(worldserver)) {
                  return 0;
               }

               this.b = true;
            }

            if (this.e > 0) {
               --this.e;
               return 0;
            } else {
               this.e = 2;
               if (this.d > 0) {
                  this.b(worldserver);
                  --this.d;
               } else {
                  this.c = VillageSiege.State.c;
               }

               return 1;
            }
         }
      } else {
         this.c = VillageSiege.State.c;
         this.b = false;
         return 0;
      }
   }

   private boolean a(WorldServer worldserver) {
      for(EntityHuman entityhuman : worldserver.v()) {
         if (!entityhuman.F_()) {
            BlockPosition blockposition = entityhuman.dg();
            if (worldserver.b(blockposition) && !worldserver.v(blockposition).a(BiomeTags.ad)) {
               for(int i = 0; i < 10; ++i) {
                  float f = worldserver.z.i() * (float) (Math.PI * 2);
                  this.f = blockposition.u() + MathHelper.d(MathHelper.b(f) * 32.0F);
                  this.g = blockposition.v();
                  this.h = blockposition.w() + MathHelper.d(MathHelper.a(f) * 32.0F);
                  if (this.a(worldserver, new BlockPosition(this.f, this.g, this.h)) != null) {
                     this.e = 0;
                     this.d = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void b(WorldServer worldserver) {
      Vec3D vec3d = this.a(worldserver, new BlockPosition(this.f, this.g, this.h));
      if (vec3d != null) {
         EntityZombie entityzombie;
         try {
            entityzombie = new EntityZombie(worldserver);
            entityzombie.a(worldserver, worldserver.d_(entityzombie.dg()), EnumMobSpawn.h, null, null);
         } catch (Exception var5) {
            a.warn("Failed to create zombie for village siege at {}", vec3d, var5);
            return;
         }

         entityzombie.b(vec3d.c, vec3d.d, vec3d.e, worldserver.z.i() * 360.0F, 0.0F);
         worldserver.addFreshEntityWithPassengers(entityzombie, SpawnReason.VILLAGE_INVASION);
      }
   }

   @Nullable
   private Vec3D a(WorldServer worldserver, BlockPosition blockposition) {
      for(int i = 0; i < 10; ++i) {
         int j = blockposition.u() + worldserver.z.a(16) - 8;
         int k = blockposition.w() + worldserver.z.a(16) - 8;
         int l = worldserver.a(HeightMap.Type.b, j, k);
         BlockPosition blockposition1 = new BlockPosition(j, l, k);
         if (worldserver.b(blockposition1) && EntityMonster.b(EntityTypes.bp, worldserver, EnumMobSpawn.h, blockposition1, worldserver.z)) {
            return Vec3D.c(blockposition1);
         }
      }

      return null;
   }

   private static enum State {
      a,
      b,
      c;
   }
}
