package net.minecraft.world.level.gameevent;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;

public class DynamicGameEventListener<T extends GameEventListener> {
   private T a;
   @Nullable
   private SectionPosition b;

   public DynamicGameEventListener(T var0) {
      this.a = var0;
   }

   public void a(WorldServer var0) {
      this.c(var0);
   }

   public void a(T var0, @Nullable World var1) {
      T var2 = this.a;
      if (var2 != var0) {
         if (var1 instanceof WorldServer var3) {
            a(var3, this.b, var1x -> var1x.b(var2));
            a(var3, this.b, var1x -> var1x.a(var0));
         }

         this.a = var0;
      }
   }

   public T a() {
      return this.a;
   }

   public void b(WorldServer var0) {
      a(var0, this.b, var0x -> var0x.b(this.a));
   }

   public void c(WorldServer var0) {
      this.a.a().a(var0).map(SectionPosition::a).ifPresent(var1x -> {
         if (this.b == null || !this.b.equals(var1x)) {
            a(var0, this.b, var0xx -> var0xx.b(this.a));
            this.b = var1x;
            a(var0, this.b, var0xx -> var0xx.a(this.a));
         }
      });
   }

   private static void a(IWorldReader var0, @Nullable SectionPosition var1, Consumer<GameEventListenerRegistry> var2) {
      if (var1 != null) {
         IChunkAccess var3 = var0.a(var1.a(), var1.c(), ChunkStatus.o, false);
         if (var3 != null) {
            var2.accept(var3.a(var1.b()));
         }
      }
   }
}
