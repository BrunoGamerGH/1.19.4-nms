package net.minecraft.network.syncher;

public class DataWatcherObject<T> {
   private final int a;
   private final DataWatcherSerializer<T> b;

   public DataWatcherObject(int var0, DataWatcherSerializer<T> var1) {
      this.a = var0;
      this.b = var1;
   }

   public int a() {
      return this.a;
   }

   public DataWatcherSerializer<T> b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         DataWatcherObject<?> var1 = (DataWatcherObject)var0;
         return this.a == var1.a;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a;
   }

   @Override
   public String toString() {
      return "<entity data: " + this.a + ">";
   }
}
