package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class GameTestHarnessSequence {
   final GameTestHarnessInfo a;
   private final List<GameTestHarnessEvent> b = Lists.newArrayList();
   private long c;

   GameTestHarnessSequence(GameTestHarnessInfo var0) {
      this.a = var0;
      this.c = var0.p();
   }

   public GameTestHarnessSequence a(Runnable var0) {
      this.b.add(GameTestHarnessEvent.a(var0));
      return this;
   }

   public GameTestHarnessSequence a(long var0, Runnable var2) {
      this.b.add(GameTestHarnessEvent.a(var0, var2));
      return this;
   }

   public GameTestHarnessSequence a(int var0) {
      return this.a(var0, () -> {
      });
   }

   public GameTestHarnessSequence b(Runnable var0) {
      this.b.add(GameTestHarnessEvent.a(() -> this.c(var0)));
      return this;
   }

   public GameTestHarnessSequence a(int var0, Runnable var1) {
      this.b.add(GameTestHarnessEvent.a(() -> {
         if (this.a.p() < this.c + (long)var0) {
            throw new GameTestHarnessAssertion("Waiting");
         } else {
            this.c(var1);
         }
      }));
      return this;
   }

   public GameTestHarnessSequence b(int var0, Runnable var1) {
      this.b.add(GameTestHarnessEvent.a(() -> {
         if (this.a.p() < this.c + (long)var0) {
            this.c(var1);
            throw new GameTestHarnessAssertion("Waiting");
         }
      }));
      return this;
   }

   public void a() {
      this.b.add(GameTestHarnessEvent.a(this.a::m));
   }

   public void a(Supplier<Exception> var0) {
      this.b.add(GameTestHarnessEvent.a(() -> this.a.a(var0.get())));
   }

   public GameTestHarnessSequence.a b() {
      GameTestHarnessSequence.a var0 = new GameTestHarnessSequence.a();
      this.b.add(GameTestHarnessEvent.a(() -> var0.a(this.a.p())));
      return var0;
   }

   public void a(long var0) {
      try {
         this.c(var0);
      } catch (GameTestHarnessAssertion var4) {
      }
   }

   public void b(long var0) {
      try {
         this.c(var0);
      } catch (GameTestHarnessAssertion var4) {
         this.a.a(var4);
      }
   }

   private void c(Runnable var0) {
      try {
         var0.run();
      } catch (GameTestHarnessAssertion var3) {
         this.a.a(var3);
      }
   }

   private void c(long var0) {
      Iterator<GameTestHarnessEvent> var2 = this.b.iterator();

      while(var2.hasNext()) {
         GameTestHarnessEvent var3 = var2.next();
         var3.b.run();
         var2.remove();
         long var4 = var0 - this.c;
         long var6 = this.c;
         this.c = var0;
         if (var3.a != null && var3.a != var4) {
            this.a.a(new GameTestHarnessAssertion("Succeeded in invalid tick: expected " + (var6 + var3.a) + ", but current tick is " + var0));
            break;
         }
      }
   }

   public class a {
      private static final long b = -1L;
      private long c = -1L;

      void a(long var0) {
         if (this.c != -1L) {
            throw new IllegalStateException("Condition already triggered at " + this.c);
         } else {
            this.c = var0;
         }
      }

      public void a() {
         long var0 = GameTestHarnessSequence.this.a.p();
         if (this.c != var0) {
            if (this.c == -1L) {
               throw new GameTestHarnessAssertion("Condition not triggered (t=" + var0 + ")");
            } else {
               throw new GameTestHarnessAssertion("Condition triggered at " + this.c + ", (t=" + var0 + ")");
            }
         }
      }
   }
}
