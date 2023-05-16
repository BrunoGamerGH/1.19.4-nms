package net.minecraft.world.level.gameevent;

import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class GameEvent {
   public static final GameEvent a = a("block_activate");
   public static final GameEvent b = a("block_attach");
   public static final GameEvent c = a("block_change");
   public static final GameEvent d = a("block_close");
   public static final GameEvent e = a("block_deactivate");
   public static final GameEvent f = a("block_destroy");
   public static final GameEvent g = a("block_detach");
   public static final GameEvent h = a("block_open");
   public static final GameEvent i = a("block_place");
   public static final GameEvent j = a("container_close");
   public static final GameEvent k = a("container_open");
   public static final GameEvent l = a("dispense_fail");
   public static final GameEvent m = a("drink");
   public static final GameEvent n = a("eat");
   public static final GameEvent o = a("elytra_glide");
   public static final GameEvent p = a("entity_damage");
   public static final GameEvent q = a("entity_die");
   public static final GameEvent r = a("entity_dismount");
   public static final GameEvent s = a("entity_interact");
   public static final GameEvent t = a("entity_mount");
   public static final GameEvent u = a("entity_place");
   public static final GameEvent v = a("entity_roar");
   public static final GameEvent w = a("entity_shake");
   public static final GameEvent x = a("equip");
   public static final GameEvent y = a("explode");
   public static final GameEvent z = a("flap");
   public static final GameEvent A = a("fluid_pickup");
   public static final GameEvent B = a("fluid_place");
   public static final GameEvent C = a("hit_ground");
   public static final GameEvent D = a("instrument_play");
   public static final GameEvent E = a("item_interact_finish");
   public static final GameEvent F = a("item_interact_start");
   public static final GameEvent G = a("jukebox_play", 10);
   public static final GameEvent H = a("jukebox_stop_play", 10);
   public static final GameEvent I = a("lightning_strike");
   public static final GameEvent J = a("note_block_play");
   public static final GameEvent K = a("piston_contract");
   public static final GameEvent L = a("piston_extend");
   public static final GameEvent M = a("prime_fuse");
   public static final GameEvent N = a("projectile_land");
   public static final GameEvent O = a("projectile_shoot");
   public static final GameEvent P = a("sculk_sensor_tendrils_clicking");
   public static final GameEvent Q = a("shear");
   public static final GameEvent R = a("shriek", 32);
   public static final GameEvent S = a("splash");
   public static final GameEvent T = a("step");
   public static final GameEvent U = a("swim");
   public static final GameEvent V = a("teleport");
   public static final int W = 16;
   private final String X;
   private final int Y;
   private final Holder.c<GameEvent> Z = BuiltInRegistries.b.f(this);

   public GameEvent(String var0, int var1) {
      this.X = var0;
      this.Y = var1;
   }

   public String a() {
      return this.X;
   }

   public int b() {
      return this.Y;
   }

   private static GameEvent a(String var0) {
      return a(var0, 16);
   }

   private static GameEvent a(String var0, int var1) {
      return IRegistry.a(BuiltInRegistries.b, var0, new GameEvent(var0, var1));
   }

   @Override
   public String toString() {
      return "Game Event{ " + this.X + " , " + this.Y + "}";
   }

   @Deprecated
   public Holder.c<GameEvent> c() {
      return this.Z;
   }

   public boolean a(TagKey<GameEvent> var0) {
      return this.Z.a(var0);
   }

   public static record a(@Nullable Entity sourceEntity, @Nullable IBlockData affectedState) {
      @Nullable
      private final Entity a;
      @Nullable
      private final IBlockData b;

      public a(@Nullable Entity var0, @Nullable IBlockData var1) {
         this.a = var0;
         this.b = var1;
      }

      public static GameEvent.a a(@Nullable Entity var0) {
         return new GameEvent.a(var0, null);
      }

      public static GameEvent.a a(@Nullable IBlockData var0) {
         return new GameEvent.a(null, var0);
      }

      public static GameEvent.a a(@Nullable Entity var0, @Nullable IBlockData var1) {
         return new GameEvent.a(var0, var1);
      }
   }

   public static final class b implements Comparable<GameEvent.b> {
      private final GameEvent a;
      private final Vec3D b;
      private final GameEvent.a c;
      private final GameEventListener d;
      private final double e;

      public b(GameEvent var0, Vec3D var1, GameEvent.a var2, GameEventListener var3, Vec3D var4) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var1.g(var4);
      }

      public int a(GameEvent.b var0) {
         return Double.compare(this.e, var0.e);
      }

      public GameEvent a() {
         return this.a;
      }

      public Vec3D b() {
         return this.b;
      }

      public GameEvent.a c() {
         return this.c;
      }

      public GameEventListener d() {
         return this.d;
      }
   }
}
