package org.bukkit.craftbukkit.v1_19_R3.projectiles;

import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.SourceBlock;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityEnderPearl;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntityProjectile;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.potion.CraftPotionUtil;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LingeringPotion;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.TippedArrow;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.util.Vector;

public class CraftBlockProjectileSource implements BlockProjectileSource {
   private final TileEntityDispenser dispenserBlock;

   public CraftBlockProjectileSource(TileEntityDispenser dispenserBlock) {
      this.dispenserBlock = dispenserBlock;
   }

   public Block getBlock() {
      return this.dispenserBlock.k().getWorld().getBlockAt(this.dispenserBlock.p().u(), this.dispenserBlock.p().v(), this.dispenserBlock.p().w());
   }

   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
      return this.launchProjectile(projectile, null);
   }

   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
      Validate.isTrue(this.getBlock().getType() == Material.DISPENSER, "Block is no longer dispenser");
      SourceBlock isourceblock = new SourceBlock((WorldServer)this.dispenserBlock.k(), this.dispenserBlock.p());
      IPosition iposition = BlockDispenser.a(isourceblock);
      EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
      World world = this.dispenserBlock.k();
      Entity launch = null;
      if (Snowball.class.isAssignableFrom(projectile)) {
         launch = new EntitySnowball(world, iposition.a(), iposition.b(), iposition.c());
      } else if (Egg.class.isAssignableFrom(projectile)) {
         launch = new EntityEgg(world, iposition.a(), iposition.b(), iposition.c());
      } else if (EnderPearl.class.isAssignableFrom(projectile)) {
         launch = new EntityEnderPearl(world, null);
         launch.e(iposition.a(), iposition.b(), iposition.c());
      } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
         launch = new EntityThrownExpBottle(world, iposition.a(), iposition.b(), iposition.c());
      } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
         if (LingeringPotion.class.isAssignableFrom(projectile)) {
            launch = new EntityPotion(world, iposition.a(), iposition.b(), iposition.c());
            ((EntityPotion)launch).a(CraftItemStack.asNMSCopy(new ItemStack(Material.LINGERING_POTION, 1)));
         } else {
            launch = new EntityPotion(world, iposition.a(), iposition.b(), iposition.c());
            ((EntityPotion)launch).a(CraftItemStack.asNMSCopy(new ItemStack(Material.SPLASH_POTION, 1)));
         }
      } else if (AbstractArrow.class.isAssignableFrom(projectile)) {
         if (TippedArrow.class.isAssignableFrom(projectile)) {
            launch = new EntityTippedArrow(world, iposition.a(), iposition.b(), iposition.c());
            ((EntityTippedArrow)launch).setPotionType(CraftPotionUtil.fromBukkit(new PotionData(PotionType.WATER, false, false)));
         } else if (SpectralArrow.class.isAssignableFrom(projectile)) {
            launch = new EntitySpectralArrow(world, iposition.a(), iposition.b(), iposition.c());
         } else {
            launch = new EntityTippedArrow(world, iposition.a(), iposition.b(), iposition.c());
         }

         ((EntityArrow)launch).d = EntityArrow.PickupStatus.b;
         ((EntityArrow)launch).projectileSource = this;
      } else if (Fireball.class.isAssignableFrom(projectile)) {
         double d0 = iposition.a() + (double)((float)enumdirection.j() * 0.3F);
         double d1 = iposition.b() + (double)((float)enumdirection.k() * 0.3F);
         double d2 = iposition.c() + (double)((float)enumdirection.l() * 0.3F);
         RandomSource random = world.z;
         double d3 = random.k() * 0.05 + (double)enumdirection.j();
         double d4 = random.k() * 0.05 + (double)enumdirection.k();
         double d5 = random.k() * 0.05 + (double)enumdirection.l();
         if (SmallFireball.class.isAssignableFrom(projectile)) {
            launch = new EntitySmallFireball(world, null, d0, d1, d2);
         } else if (WitherSkull.class.isAssignableFrom(projectile)) {
            launch = EntityTypes.bm.a(world);
            launch.e(d0, d1, d2);
            double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            ((EntityFireball)launch).b = d3 / d6 * 0.1;
            ((EntityFireball)launch).c = d4 / d6 * 0.1;
            ((EntityFireball)launch).d = d5 / d6 * 0.1;
         } else {
            launch = EntityTypes.ag.a(world);
            launch.e(d0, d1, d2);
            double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
            ((EntityFireball)launch).b = d3 / d6 * 0.1;
            ((EntityFireball)launch).c = d4 / d6 * 0.1;
            ((EntityFireball)launch).d = d5 / d6 * 0.1;
         }

         ((EntityFireball)launch).projectileSource = this;
      }

      Validate.notNull(launch, "Projectile not supported");
      if (launch instanceof IProjectile) {
         if (launch instanceof EntityProjectile) {
            ((EntityProjectile)launch).projectileSource = this;
         }

         float a = 6.0F;
         float b = 1.1F;
         if (launch instanceof EntityPotion || launch instanceof ThrownExpBottle) {
            a *= 0.5F;
            b *= 1.25F;
         }

         ((IProjectile)launch).c((double)enumdirection.j(), (double)((float)enumdirection.k() + 0.1F), (double)enumdirection.l(), b, a);
      }

      if (velocity != null) {
         ((Projectile)launch.getBukkitEntity()).setVelocity(velocity);
      }

      world.b(launch);
      return (T)launch.getBukkitEntity();
   }
}
