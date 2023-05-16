package net.minecraft.world.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.world.level.World;
import net.minecraft.world.level.material.EnumPistonReaction;

public class Marker extends Entity {
   private static final String b = "data";
   private NBTTagCompound c = new NBTTagCompound();

   public Marker(EntityTypes<?> var0, World var1) {
      super(var0, var1);
      this.ae = true;
   }

   @Override
   public void l() {
   }

   @Override
   protected void a_() {
   }

   @Override
   protected void a(NBTTagCompound var0) {
      this.c = var0.p("data");
   }

   @Override
   protected void b(NBTTagCompound var0) {
      var0.a("data", this.c.h());
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      throw new IllegalStateException("Markers should never be sent");
   }

   @Override
   protected boolean o(Entity var0) {
      return false;
   }

   @Override
   protected boolean bA() {
      return false;
   }

   protected void m(Entity var0) {
      throw new IllegalStateException("Should never addPassenger without checking couldAcceptPassenger()");
   }

   @Override
   public EnumPistonReaction C_() {
      return EnumPistonReaction.d;
   }
}
