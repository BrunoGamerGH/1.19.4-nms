package net.minecraft.world.level.gameevent;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.phys.Vec3D;

public class EuclideanGameEventListenerRegistry implements GameEventListenerRegistry {
   private final List<GameEventListener> b = Lists.newArrayList();
   private final Set<GameEventListener> c = Sets.newHashSet();
   private final List<GameEventListener> d = Lists.newArrayList();
   private boolean e;
   private final WorldServer f;

   public EuclideanGameEventListenerRegistry(WorldServer var0) {
      this.f = var0;
   }

   @Override
   public boolean a() {
      return this.b.isEmpty();
   }

   @Override
   public void a(GameEventListener var0) {
      if (this.e) {
         this.d.add(var0);
      } else {
         this.b.add(var0);
      }

      PacketDebug.a(this.f, var0);
   }

   @Override
   public void b(GameEventListener var0) {
      if (this.e) {
         this.c.add(var0);
      } else {
         this.b.remove(var0);
      }
   }

   @Override
   public boolean a(GameEvent var0, Vec3D var1, GameEvent.a var2, GameEventListenerRegistry.a var3) {
      this.e = true;
      boolean var4 = false;

      try {
         Iterator<GameEventListener> var5 = this.b.iterator();

         while(var5.hasNext()) {
            GameEventListener var6 = var5.next();
            if (this.c.remove(var6)) {
               var5.remove();
            } else {
               Optional<Vec3D> var7 = a(this.f, var1, var6);
               if (var7.isPresent()) {
                  var3.visit(var6, var7.get());
                  var4 = true;
               }
            }
         }
      } finally {
         this.e = false;
      }

      if (!this.d.isEmpty()) {
         this.b.addAll(this.d);
         this.d.clear();
      }

      if (!this.c.isEmpty()) {
         this.b.removeAll(this.c);
         this.c.clear();
      }

      return var4;
   }

   private static Optional<Vec3D> a(WorldServer var0, Vec3D var1, GameEventListener var2) {
      Optional<Vec3D> var3 = var2.a().a(var0);
      if (var3.isEmpty()) {
         return Optional.empty();
      } else {
         double var4 = var3.get().g(var1);
         int var6 = var2.b() * var2.b();
         return var4 > (double)var6 ? Optional.empty() : var3;
      }
   }
}
