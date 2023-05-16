package net.minecraft.world.level.gameevent.vibrations;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipBlockStateContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.event.block.BlockReceiveGameEvent;

public class VibrationListener implements GameEventListener {
   @VisibleForTesting
   public static final Object2IntMap<GameEvent> a = Object2IntMaps.unmodifiable(SystemUtils.a(new Object2IntOpenHashMap(), object2intopenhashmap -> {
      object2intopenhashmap.put(GameEvent.T, 1);
      object2intopenhashmap.put(GameEvent.E, 2);
      object2intopenhashmap.put(GameEvent.z, 2);
      object2intopenhashmap.put(GameEvent.U, 3);
      object2intopenhashmap.put(GameEvent.o, 4);
      object2intopenhashmap.put(GameEvent.C, 5);
      object2intopenhashmap.put(GameEvent.V, 5);
      object2intopenhashmap.put(GameEvent.S, 6);
      object2intopenhashmap.put(GameEvent.w, 6);
      object2intopenhashmap.put(GameEvent.c, 6);
      object2intopenhashmap.put(GameEvent.J, 6);
      object2intopenhashmap.put(GameEvent.r, 6);
      object2intopenhashmap.put(GameEvent.O, 7);
      object2intopenhashmap.put(GameEvent.m, 7);
      object2intopenhashmap.put(GameEvent.M, 7);
      object2intopenhashmap.put(GameEvent.t, 7);
      object2intopenhashmap.put(GameEvent.N, 8);
      object2intopenhashmap.put(GameEvent.n, 8);
      object2intopenhashmap.put(GameEvent.s, 8);
      object2intopenhashmap.put(GameEvent.p, 8);
      object2intopenhashmap.put(GameEvent.x, 9);
      object2intopenhashmap.put(GameEvent.Q, 9);
      object2intopenhashmap.put(GameEvent.v, 9);
      object2intopenhashmap.put(GameEvent.d, 10);
      object2intopenhashmap.put(GameEvent.e, 10);
      object2intopenhashmap.put(GameEvent.g, 10);
      object2intopenhashmap.put(GameEvent.l, 10);
      object2intopenhashmap.put(GameEvent.h, 11);
      object2intopenhashmap.put(GameEvent.a, 11);
      object2intopenhashmap.put(GameEvent.b, 11);
      object2intopenhashmap.put(GameEvent.u, 12);
      object2intopenhashmap.put(GameEvent.i, 12);
      object2intopenhashmap.put(GameEvent.B, 12);
      object2intopenhashmap.put(GameEvent.q, 13);
      object2intopenhashmap.put(GameEvent.f, 13);
      object2intopenhashmap.put(GameEvent.A, 13);
      object2intopenhashmap.put(GameEvent.j, 14);
      object2intopenhashmap.put(GameEvent.K, 14);
      object2intopenhashmap.put(GameEvent.L, 15);
      object2intopenhashmap.put(GameEvent.k, 15);
      object2intopenhashmap.put(GameEvent.y, 15);
      object2intopenhashmap.put(GameEvent.I, 15);
      object2intopenhashmap.put(GameEvent.D, 15);
   }));
   protected final PositionSource b;
   protected final int c;
   protected final VibrationListener.a d;
   @Nullable
   protected VibrationInfo e;
   protected int f;
   private final VibrationSelector g;

   public static Codec<VibrationListener> a(VibrationListener.a vibrationlistener_a) {
      return RecordCodecBuilder.create(
         instance -> instance.group(
                  PositionSource.b.fieldOf("source").forGetter(vibrationlistener -> vibrationlistener.b),
                  ExtraCodecs.h.fieldOf("range").forGetter(vibrationlistener -> vibrationlistener.c),
                  VibrationInfo.a.optionalFieldOf("event").forGetter(vibrationlistener -> Optional.ofNullable(vibrationlistener.e)),
                  VibrationSelector.a.fieldOf("selector").forGetter(vibrationlistener -> vibrationlistener.g),
                  ExtraCodecs.h.fieldOf("event_delay").orElse(0).forGetter(vibrationlistener -> vibrationlistener.f)
               )
               .apply(
                  instance,
                  (positionsource, integer, optional, vibrationselector, integer1) -> new VibrationListener(
                        positionsource, integer, vibrationlistener_a, (VibrationInfo)optional.orElse(null), vibrationselector, integer1
                     )
               )
      );
   }

   private VibrationListener(
      PositionSource positionsource,
      int i,
      VibrationListener.a vibrationlistener_a,
      @Nullable VibrationInfo vibrationinfo,
      VibrationSelector vibrationselector,
      int j
   ) {
      this.b = positionsource;
      this.c = i;
      this.d = vibrationlistener_a;
      this.e = vibrationinfo;
      this.f = j;
      this.g = vibrationselector;
   }

   public VibrationListener(PositionSource positionsource, int i, VibrationListener.a vibrationlistener_a) {
      this(positionsource, i, vibrationlistener_a, null, new VibrationSelector(), 0);
   }

   public static int a(GameEvent gameevent) {
      return a.getOrDefault(gameevent, 0);
   }

   public void a(World world) {
      if (world instanceof WorldServer worldserver) {
         if (this.e == null) {
            this.g.a(worldserver.U()).ifPresent(vibrationinfo -> {
               this.e = vibrationinfo;
               Vec3D vec3d = this.e.c();
               this.f = MathHelper.d(this.e.b());
               worldserver.a(new VibrationParticleOption(this.b, this.f), vec3d.c, vec3d.d, vec3d.e, 1, 0.0, 0.0, 0.0, 0.0);
               this.d.f();
               this.g.a();
            });
         }

         if (this.e != null) {
            --this.f;
            if (this.f <= 0) {
               this.f = 0;
               this.d
                  .a(
                     worldserver,
                     this,
                     BlockPosition.a(this.e.c()),
                     this.e.a(),
                     this.e.a(worldserver).orElse(null),
                     this.e.b(worldserver).orElse(null),
                     this.e.b()
                  );
               this.e = null;
            }
         }
      }
   }

   @Override
   public PositionSource a() {
      return this.b;
   }

   @Override
   public int b() {
      return this.c;
   }

   @Override
   public boolean a(WorldServer worldserver, GameEvent gameevent, GameEvent.a gameevent_a, Vec3D vec3d) {
      if (this.e != null) {
         return false;
      } else if (!this.d.a(gameevent, gameevent_a)) {
         return false;
      } else {
         Optional<Vec3D> optional = this.b.a(worldserver);
         if (optional.isEmpty()) {
            return false;
         } else {
            Vec3D vec3d1 = optional.get();
            boolean defaultCancel = !this.d.a(worldserver, this, BlockPosition.a(vec3d), gameevent, gameevent_a);
            Entity entity = gameevent_a.a();
            BlockReceiveGameEvent event = new BlockReceiveGameEvent(
               org.bukkit.GameEvent.getByKey(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.b.b(gameevent))),
               CraftBlock.at(worldserver, BlockPosition.a(vec3d1)),
               entity == null ? null : entity.getBukkitEntity()
            );
            event.setCancelled(defaultCancel);
            worldserver.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return false;
            } else if (a(worldserver, vec3d, vec3d1)) {
               return false;
            } else {
               this.a(worldserver, gameevent, gameevent_a, vec3d, vec3d1);
               return true;
            }
         }
      }
   }

   public void b(WorldServer worldserver, GameEvent gameevent, GameEvent.a gameevent_a, Vec3D vec3d) {
      this.b.a(worldserver).ifPresent(vec3d1 -> this.a(worldserver, gameevent, gameevent_a, vec3d, vec3d1));
   }

   public void a(WorldServer worldserver, GameEvent gameevent, GameEvent.a gameevent_a, Vec3D vec3d, Vec3D vec3d1) {
      this.g.a(new VibrationInfo(gameevent, (float)vec3d.f(vec3d1), vec3d, gameevent_a.a()), worldserver.U());
   }

   private static boolean a(World world, Vec3D vec3d, Vec3D vec3d1) {
      Vec3D vec3d2 = new Vec3D((double)MathHelper.a(vec3d.c) + 0.5, (double)MathHelper.a(vec3d.d) + 0.5, (double)MathHelper.a(vec3d.e) + 0.5);
      Vec3D vec3d3 = new Vec3D((double)MathHelper.a(vec3d1.c) + 0.5, (double)MathHelper.a(vec3d1.d) + 0.5, (double)MathHelper.a(vec3d1.e) + 0.5);

      for(EnumDirection enumdirection : EnumDirection.values()) {
         Vec3D vec3d4 = vec3d2.a(enumdirection, 1.0E-5F);
         if (world.a(new ClipBlockStateContext(vec3d4, vec3d3, iblockdata -> iblockdata.a(TagsBlock.bl))).c() != MovingObjectPosition.EnumMovingObjectType.b) {
            return false;
         }
      }

      return true;
   }

   public interface a {
      default TagKey<GameEvent> a() {
         return GameEventTags.a;
      }

      default boolean w() {
         return false;
      }

      default boolean a(GameEvent gameevent, GameEvent.a gameevent_a) {
         if (!gameevent.a(this.a())) {
            return false;
         } else {
            Entity entity = gameevent_a.a();
            if (entity != null) {
               if (entity.F_()) {
                  return false;
               }

               if (entity.bP() && gameevent.a(GameEventTags.d)) {
                  if (this.w() && entity instanceof EntityPlayer entityplayer) {
                     CriterionTriggers.Y.a(entityplayer);
                  }

                  return false;
               }

               if (entity.aR()) {
                  return false;
               }
            }

            return gameevent_a.b() != null ? !gameevent_a.b().a(TagsBlock.bm) : true;
         }
      }

      boolean a(WorldServer var1, GameEventListener var2, BlockPosition var3, GameEvent var4, GameEvent.a var5);

      void a(WorldServer var1, GameEventListener var2, BlockPosition var3, GameEvent var4, @Nullable Entity var5, @Nullable Entity var6, float var7);

      default void f() {
      }
   }
}
