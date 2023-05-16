package net.minecraft.util.thread;

import com.google.common.collect.Queues;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;

public interface PairedQueue<T, F> {
   @Nullable
   F a();

   boolean a(T var1);

   boolean b();

   int c();

   public static final class a implements PairedQueue<PairedQueue.b, Runnable> {
      private final Queue<Runnable>[] a;
      private final AtomicInteger b = new AtomicInteger();

      public a(int var0) {
         this.a = new Queue[var0];

         for(int var1 = 0; var1 < var0; ++var1) {
            this.a[var1] = Queues.newConcurrentLinkedQueue();
         }
      }

      @Nullable
      public Runnable d() {
         for(Queue<Runnable> var3 : this.a) {
            Runnable var4 = var3.poll();
            if (var4 != null) {
               this.b.decrementAndGet();
               return var4;
            }
         }

         return null;
      }

      public boolean a(PairedQueue.b var0) {
         int var1 = var0.a;
         if (var1 < this.a.length && var1 >= 0) {
            this.a[var1].add(var0);
            this.b.incrementAndGet();
            return true;
         } else {
            throw new IndexOutOfBoundsException(String.format(Locale.ROOT, "Priority %d not supported. Expected range [0-%d]", var1, this.a.length - 1));
         }
      }

      @Override
      public boolean b() {
         return this.b.get() == 0;
      }

      @Override
      public int c() {
         return this.b.get();
      }
   }

   public static final class b implements Runnable {
      final int a;
      private final Runnable b;

      public b(int var0, Runnable var1) {
         this.a = var0;
         this.b = var1;
      }

      @Override
      public void run() {
         this.b.run();
      }

      public int a() {
         return this.a;
      }
   }

   public static final class c<T> implements PairedQueue<T, T> {
      private final Queue<T> a;

      public c(Queue<T> var0) {
         this.a = var0;
      }

      @Nullable
      @Override
      public T a() {
         return this.a.poll();
      }

      @Override
      public boolean a(T var0) {
         return this.a.add(var0);
      }

      @Override
      public boolean b() {
         return this.a.isEmpty();
      }

      @Override
      public int c() {
         return this.a.size();
      }
   }
}
