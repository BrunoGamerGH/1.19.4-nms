package net.minecraft.network.protocol;

public enum EnumProtocolDirection {
   a,
   b;

   public EnumProtocolDirection a() {
      return this == b ? a : b;
   }
}
