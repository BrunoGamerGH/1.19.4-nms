package net.minecraft.world.level.gameevent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.event.world.GenericGameEvent;

public class GameEventDispatcher {
   private final WorldServer a;

   public GameEventDispatcher(WorldServer worldserver) {
      this.a = worldserver;
   }

   public void a(GameEvent gameevent, Vec3D vec3d, GameEvent.a gameevent_a) {
      int i = gameevent.b();
      BlockPosition blockposition = BlockPosition.a(vec3d);
      GenericGameEvent event = new GenericGameEvent(
         org.bukkit.GameEvent.getByKey(CraftNamespacedKey.fromMinecraft(BuiltInRegistries.b.b(gameevent))),
         new Location(this.a.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w()),
         gameevent_a.a() == null ? null : gameevent_a.a().getBukkitEntity(),
         i,
         !Bukkit.isPrimaryThread()
      );
      this.a.getCraftServer().getPluginManager().callEvent(event);
      if (!event.isCancelled()) {
         i = event.getRadius();
         int j = SectionPosition.a(blockposition.u() - i);
         int k = SectionPosition.a(blockposition.v() - i);
         int l = SectionPosition.a(blockposition.w() - i);
         int i1 = SectionPosition.a(blockposition.u() + i);
         int j1 = SectionPosition.a(blockposition.v() + i);
         int k1 = SectionPosition.a(blockposition.w() + i);
         List<GameEvent.b> list = new ArrayList<>();
         GameEventListenerRegistry.a gameeventlistenerregistry_a = (gameeventlistener, vec3d1) -> {
            if (gameeventlistener.c() == GameEventListener.a.b) {
               list.add(new GameEvent.b(gameevent, vec3d, gameevent_a, gameeventlistener, vec3d1));
            } else {
               gameeventlistener.a(this.a, gameevent, gameevent_a, vec3d);
            }
         };
         boolean flag = false;

         for(int l1 = j; l1 <= i1; ++l1) {
            for(int i2 = l; i2 <= k1; ++i2) {
               Chunk chunk = this.a.k().a(l1, i2);
               if (chunk != null) {
                  for(int j2 = k; j2 <= j1; ++j2) {
                     flag |= chunk.a(j2).a(gameevent, vec3d, gameevent_a, gameeventlistenerregistry_a);
                  }
               }
            }
         }

         if (!list.isEmpty()) {
            this.a(list);
         }

         if (flag) {
            PacketDebug.a(this.a, gameevent, vec3d);
         }
      }
   }

   private void a(List<GameEvent.b> list) {
      Collections.sort(list);

      for(GameEvent.b gameevent_b : list) {
         GameEventListener gameeventlistener = gameevent_b.d();
         gameeventlistener.a(this.a, gameevent_b.a(), gameevent_b.c(), gameevent_b.b());
      }
   }
}
