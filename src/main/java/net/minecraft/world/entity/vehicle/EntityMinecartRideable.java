package net.minecraft.world.entity.vehicle;

import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class EntityMinecartRideable extends EntityMinecartAbstract {
   public EntityMinecartRideable(EntityTypes<?> var0, World var1) {
      super(var0, var1);
   }

   public EntityMinecartRideable(World var0, double var1, double var3, double var5) {
      super(EntityTypes.an, var0, var1, var3, var5);
   }

   @Override
   public EnumInteractionResult a(EntityHuman var0, EnumHand var1) {
      if (var0.fz()) {
         return EnumInteractionResult.d;
      } else if (this.bM()) {
         return EnumInteractionResult.d;
      } else if (!this.H.B) {
         return var0.k(this) ? EnumInteractionResult.b : EnumInteractionResult.d;
      } else {
         return EnumInteractionResult.a;
      }
   }

   @Override
   protected Item i() {
      return Items.mW;
   }

   @Override
   public void a(int var0, int var1, int var2, boolean var3) {
      if (var3) {
         if (this.bM()) {
            this.bx();
         }

         if (this.q() == 0) {
            this.d(-this.r());
            this.c(10);
            this.a(50.0F);
            this.bj();
         }
      }
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.a;
   }
}
