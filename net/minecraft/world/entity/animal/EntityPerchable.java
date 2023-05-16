package net.minecraft.world.entity.animal;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;

public abstract class EntityPerchable extends EntityTameableAnimal {
   private static final int bV = 100;
   private int bW;

   protected EntityPerchable(EntityTypes<? extends EntityPerchable> var0, World var1) {
      super(var0, var1);
   }

   public boolean b(EntityPlayer var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("id", this.bp());
      this.f(var1);
      if (var0.h(var1)) {
         this.ai();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void l() {
      ++this.bW;
      super.l();
   }

   public boolean gb() {
      return this.bW > 100;
   }
}
