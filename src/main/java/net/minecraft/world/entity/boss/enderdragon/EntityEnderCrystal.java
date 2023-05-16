package net.minecraft.world.entity.boss.enderdragon;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.dimension.end.EnderDragonBattle;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityEnderCrystal extends Entity {
   private static final DataWatcherObject<Optional<BlockPosition>> c = DataWatcher.a(EntityEnderCrystal.class, DataWatcherRegistry.o);
   private static final DataWatcherObject<Boolean> d = DataWatcher.a(EntityEnderCrystal.class, DataWatcherRegistry.k);
   public int b;

   public EntityEnderCrystal(EntityTypes<? extends EntityEnderCrystal> entitytypes, World world) {
      super(entitytypes, world);
      this.F = true;
      this.b = this.af.a(100000);
   }

   public EntityEnderCrystal(World world, double d0, double d1, double d2) {
      this(EntityTypes.B, world);
      this.e(d0, d1, d2);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   protected void a_() {
      this.aj().a(c, Optional.empty());
      this.aj().a(d, true);
   }

   @Override
   public void l() {
      ++this.b;
      if (this.H instanceof WorldServer) {
         BlockPosition blockposition = this.dg();
         if (((WorldServer)this.H).B() != null
            && this.H.a_(blockposition).h()
            && !CraftEventFactory.callBlockIgniteEvent(this.H, blockposition, this).isCancelled()) {
            this.H.b(blockposition, BlockFireAbstract.a(this.H, blockposition));
         }
      }
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      if (this.i() != null) {
         nbttagcompound.a("BeamTarget", GameProfileSerializer.a(this.i()));
      }

      nbttagcompound.a("ShowBottom", this.j());
   }

   @Override
   protected void a(NBTTagCompound nbttagcompound) {
      if (nbttagcompound.b("BeamTarget", 10)) {
         this.a(GameProfileSerializer.b(nbttagcompound.p("BeamTarget")));
      }

      if (nbttagcompound.b("ShowBottom", 1)) {
         this.a(nbttagcompound.q("ShowBottom"));
      }
   }

   @Override
   public boolean bm() {
      return true;
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (damagesource.d() instanceof EntityEnderDragon) {
         return false;
      } else {
         if (!this.dB() && !this.H.B) {
            if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, (double)f, false)) {
               return false;
            }

            this.a(Entity.RemovalReason.a);
            if (!damagesource.a(DamageTypeTags.l)) {
               DamageSource damagesource1 = damagesource.d() != null ? this.dG().d(this, damagesource.d()) : null;
               ExplosionPrimeEvent event = new ExplosionPrimeEvent(this.getBukkitEntity(), 6.0F, false);
               this.H.getCraftServer().getPluginManager().callEvent(event);
               if (event.isCancelled()) {
                  this.dD();
                  return false;
               }

               this.H.a(this, damagesource1, null, this.dl(), this.dn(), this.dr(), event.getRadius(), event.getFire(), World.a.b);
            }

            this.a(damagesource);
         }

         return true;
      }
   }

   @Override
   public void ah() {
      this.a(this.dG().n());
      super.ah();
   }

   private void a(DamageSource damagesource) {
      if (this.H instanceof WorldServer) {
         EnderDragonBattle enderdragonbattle = ((WorldServer)this.H).B();
         if (enderdragonbattle != null) {
            enderdragonbattle.a(this, damagesource);
         }
      }
   }

   public void a(@Nullable BlockPosition blockposition) {
      this.aj().b(c, Optional.ofNullable(blockposition));
   }

   @Nullable
   public BlockPosition i() {
      return this.aj().a(c).orElse(null);
   }

   public void a(boolean flag) {
      this.aj().b(d, flag);
   }

   @Override
   public boolean j() {
      return this.aj().a(d);
   }

   @Override
   public boolean a(double d0) {
      return super.a(d0) || this.i() != null;
   }

   @Override
   public ItemStack dt() {
      return new ItemStack(Items.uh);
   }
}
