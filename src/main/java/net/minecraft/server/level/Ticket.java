package net.minecraft.server.level;

import java.util.Objects;

public final class Ticket<T> implements Comparable<Ticket<?>> {
   private final TicketType<T> a;
   private final int b;
   public final T c;
   private long d;

   protected Ticket(TicketType<T> var0, int var1, T var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public int a(Ticket<?> var0) {
      int var1 = Integer.compare(this.b, var0.b);
      if (var1 != 0) {
         return var1;
      } else {
         int var2 = Integer.compare(System.identityHashCode(this.a), System.identityHashCode(var0.a));
         return var2 != 0 ? var2 : this.a.a().compare(this.c, var0.c);
      }
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (!(var0 instanceof Ticket)) {
         return false;
      } else {
         Ticket<?> var1 = (Ticket)var0;
         return this.b == var1.b && Objects.equals(this.a, var1.a) && Objects.equals(this.c, var1.c);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c);
   }

   @Override
   public String toString() {
      return "Ticket[" + this.a + " " + this.b + " (" + this.c + ")] at " + this.d;
   }

   public TicketType<T> a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   protected void a(long var0) {
      this.d = var0;
   }

   protected boolean b(long var0) {
      long var2 = this.a.b();
      return var2 != 0L && var0 - this.d > var2;
   }
}
