package net.minecraft.nbt;

public abstract class NBTNumber implements NBTBase {
   protected NBTNumber() {
   }

   public abstract long f();

   public abstract int g();

   public abstract short h();

   public abstract byte i();

   public abstract double j();

   public abstract float k();

   public abstract Number l();

   @Override
   public String toString() {
      return this.f_();
   }
}
