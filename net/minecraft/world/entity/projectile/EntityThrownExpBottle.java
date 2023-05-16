package net.minecraft.world.entity.projectile;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.phys.MovingObjectPosition;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExpBottleEvent;

public class EntityThrownExpBottle extends EntityProjectileThrowable {
   public EntityThrownExpBottle(EntityTypes<? extends EntityThrownExpBottle> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityThrownExpBottle(World world, EntityLiving entityliving) {
      super(EntityTypes.I, entityliving, world);
   }

   public EntityThrownExpBottle(World world, double d0, double d1, double d2) {
      super(EntityTypes.I, d0, d1, d2, world);
   }

   @Override
   protected Item j() {
      return Items.ta;
   }

   @Override
   protected float o() {
      return 0.07F;
   }

   @Override
   protected void a(MovingObjectPosition movingobjectposition) {
      super.a(movingobjectposition);
      if (this.H instanceof WorldServer) {
         int i = 3 + this.H.z.a(5) + this.H.z.a(5);
         ExpBottleEvent event = CraftEventFactory.callExpBottleEvent(this, i);
         i = event.getExperience();
         if (event.getShowEffect()) {
            this.H.c(2002, this.dg(), PotionUtil.a(Potions.c));
         }

         EntityExperienceOrb.a((WorldServer)this.H, this.de(), i);
         this.ai();
      }
   }
}
