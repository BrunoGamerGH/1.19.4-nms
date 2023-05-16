package net.minecraft.world.entity.boss;

import javax.annotation.Nullable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.item.ItemStack;

public class EntityComplexPart extends Entity {
   public final EntityEnderDragon b;
   public final String c;
   private final EntitySize d;

   public EntityComplexPart(EntityEnderDragon var0, String var1, float var2, float var3) {
      super(var0.ae(), var0.H);
      this.d = EntitySize.b(var2, var3);
      this.c_();
      this.b = var0;
      this.c = var1;
   }

   @Override
   protected void a_() {
   }

   @Override
   protected void a(NBTTagCompound var0) {
   }

   @Override
   protected void b(NBTTagCompound var0) {
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Nullable
   @Override
   public ItemStack dt() {
      return this.b.dt();
   }

   @Override
   public boolean a(DamageSource var0, float var1) {
      return this.b(var0) ? false : this.b.a(this, var0, var1);
   }

   @Override
   public boolean q(Entity var0) {
      return this == var0 || this.b == var0;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      throw new UnsupportedOperationException();
   }

   @Override
   public EntitySize a(EntityPose var0) {
      return this.d;
   }

   @Override
   public boolean dE() {
      return false;
   }
}
