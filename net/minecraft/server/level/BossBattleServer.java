package net.minecraft.server.level;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketPlayOutBoss;
import net.minecraft.util.MathHelper;
import net.minecraft.world.BossBattle;

public class BossBattleServer extends BossBattle {
   private final Set<EntityPlayer> h = Sets.newHashSet();
   private final Set<EntityPlayer> i = Collections.unmodifiableSet(this.h);
   public boolean j = true;

   public BossBattleServer(IChatBaseComponent var0, BossBattle.BarColor var1, BossBattle.BarStyle var2) {
      super(MathHelper.a(), var0, var1, var2);
   }

   @Override
   public void a(float var0) {
      if (var0 != this.b) {
         super.a(var0);
         this.a(PacketPlayOutBoss::b);
      }
   }

   @Override
   public void a(BossBattle.BarColor var0) {
      if (var0 != this.c) {
         super.a(var0);
         this.a(PacketPlayOutBoss::d);
      }
   }

   @Override
   public void a(BossBattle.BarStyle var0) {
      if (var0 != this.d) {
         super.a(var0);
         this.a(PacketPlayOutBoss::d);
      }
   }

   @Override
   public BossBattle a(boolean var0) {
      if (var0 != this.e) {
         super.a(var0);
         this.a(PacketPlayOutBoss::e);
      }

      return this;
   }

   @Override
   public BossBattle b(boolean var0) {
      if (var0 != this.f) {
         super.b(var0);
         this.a(PacketPlayOutBoss::e);
      }

      return this;
   }

   @Override
   public BossBattle c(boolean var0) {
      if (var0 != this.g) {
         super.c(var0);
         this.a(PacketPlayOutBoss::e);
      }

      return this;
   }

   @Override
   public void a(IChatBaseComponent var0) {
      if (!Objects.equal(var0, this.a)) {
         super.a(var0);
         this.a(PacketPlayOutBoss::c);
      }
   }

   public void a(Function<BossBattle, PacketPlayOutBoss> var0) {
      if (this.j) {
         PacketPlayOutBoss var1 = var0.apply(this);

         for(EntityPlayer var3 : this.h) {
            var3.b.a(var1);
         }
      }
   }

   public void a(EntityPlayer var0) {
      if (this.h.add(var0) && this.j) {
         var0.b.a(PacketPlayOutBoss.a(this));
      }
   }

   public void b(EntityPlayer var0) {
      if (this.h.remove(var0) && this.j) {
         var0.b.a(PacketPlayOutBoss.a(this.i()));
      }
   }

   public void b() {
      if (!this.h.isEmpty()) {
         for(EntityPlayer var1 : Lists.newArrayList(this.h)) {
            this.b(var1);
         }
      }
   }

   public boolean g() {
      return this.j;
   }

   public void d(boolean var0) {
      if (var0 != this.j) {
         this.j = var0;

         for(EntityPlayer var2 : this.h) {
            var2.b.a(var0 ? PacketPlayOutBoss.a(this) : PacketPlayOutBoss.a(this.i()));
         }
      }
   }

   public Collection<EntityPlayer> h() {
      return this.i;
   }
}
