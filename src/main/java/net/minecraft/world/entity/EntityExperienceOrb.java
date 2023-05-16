package net.minecraft.world.entity;

import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketListenerPlayOut;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntityExperienceOrb;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.World;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.player.PlayerItemMendEvent;

public class EntityExperienceOrb extends Entity {
   private static final int b = 6000;
   private static final int c = 20;
   private static final int d = 8;
   private static final int e = 40;
   private static final double f = 0.5;
   private int g;
   private int h = 5;
   public int i;
   private int j = 1;
   private EntityHuman k;

   public EntityExperienceOrb(World world, double d0, double d1, double d2, int i) {
      this(EntityTypes.J, world);
      this.e(d0, d1, d2);
      this.f((float)(this.af.j() * 360.0));
      this.o((this.af.j() * 0.2F - 0.1F) * 2.0, this.af.j() * 0.2 * 2.0, (this.af.j() * 0.2F - 0.1F) * 2.0);
      this.i = i;
   }

   public EntityExperienceOrb(EntityTypes<? extends EntityExperienceOrb> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected Entity.MovementEmission aQ() {
      return Entity.MovementEmission.a;
   }

   @Override
   protected void a_() {
   }

   @Override
   public void l() {
      super.l();
      EntityHuman prevTarget = this.k;
      this.I = this.dl();
      this.J = this.dn();
      this.K = this.dr();
      if (this.a(TagsFluid.a)) {
         this.o();
      } else if (!this.aP()) {
         this.f(this.dj().b(0.0, -0.03, 0.0));
      }

      if (this.H.b_(this.dg()).a(TagsFluid.b)) {
         this.o((double)((this.af.i() - this.af.i()) * 0.2F), 0.2F, (double)((this.af.i() - this.af.i()) * 0.2F));
      }

      if (!this.H.b(this.cD())) {
         this.m(this.dl(), (this.cD().b + this.cD().e) / 2.0, this.dr());
      }

      if (this.ag % 20 == 1) {
         this.k();
      }

      if (this.k != null && (this.k.F_() || this.k.ep())) {
         this.k = null;
      }

      boolean cancelled = false;
      if (this.k != prevTarget) {
         EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(
            this, this.k, this.k != null ? TargetReason.CLOSEST_PLAYER : TargetReason.FORGOT_TARGET
         );
         EntityLiving target = event.getTarget() == null ? null : ((CraftLivingEntity)event.getTarget()).getHandle();
         cancelled = event.isCancelled();
         if (cancelled) {
            this.k = prevTarget;
         } else {
            this.k = target instanceof EntityHuman ? (EntityHuman)target : null;
         }
      }

      if (this.k != null && !cancelled) {
         Vec3D vec3d = new Vec3D(this.k.dl() - this.dl(), this.k.dn() + (double)this.k.cE() / 2.0 - this.dn(), this.k.dr() - this.dr());
         double d0 = vec3d.g();
         if (d0 < 64.0) {
            double d1 = 1.0 - Math.sqrt(d0) / 8.0;
            this.f(this.dj().e(vec3d.d().a(d1 * d1 * 0.1)));
         }
      }

      this.a(EnumMoveType.a, this.dj());
      float f = 0.98F;
      if (this.N) {
         f = this.H.a_(BlockPosition.a(this.dl(), this.dn() - 1.0, this.dr())).b().i() * 0.98F;
      }

      this.f(this.dj().d((double)f, 0.98, (double)f));
      if (this.N) {
         this.f(this.dj().d(1.0, -0.9, 1.0));
      }

      ++this.g;
      if (this.g >= 6000) {
         this.ai();
      }
   }

   private void k() {
      if (this.k == null || this.k.f(this) > 64.0) {
         this.k = this.H.a(this, 8.0);
      }

      if (this.H instanceof WorldServer) {
         for(EntityExperienceOrb entityexperienceorb : this.H.a(EntityTypeTest.a(EntityExperienceOrb.class), this.cD().g(0.5), this::a)) {
            this.b(entityexperienceorb);
         }
      }
   }

   public static void a(WorldServer worldserver, Vec3D vec3d, int i) {
      while(i > 0) {
         int j = b(i);
         i -= j;
         if (!b(worldserver, vec3d, j)) {
            worldserver.b(new EntityExperienceOrb(worldserver, vec3d.a(), vec3d.b(), vec3d.c(), j));
         }
      }
   }

   private static boolean b(WorldServer worldserver, Vec3D vec3d, int i) {
      AxisAlignedBB axisalignedbb = AxisAlignedBB.a(vec3d, 1.0, 1.0, 1.0);
      int j = worldserver.r_().a(40);
      List<EntityExperienceOrb> list = worldserver.a(
         EntityTypeTest.a(EntityExperienceOrb.class), axisalignedbb, entityexperienceorbx -> a(entityexperienceorbx, j, i)
      );
      if (!list.isEmpty()) {
         EntityExperienceOrb entityexperienceorb = list.get(0);
         ++entityexperienceorb.j;
         entityexperienceorb.g = 0;
         return true;
      } else {
         return false;
      }
   }

   private boolean a(EntityExperienceOrb entityexperienceorb) {
      return entityexperienceorb != this && a(entityexperienceorb, this.af(), this.i);
   }

   private static boolean a(EntityExperienceOrb entityexperienceorb, int i, int j) {
      return !entityexperienceorb.dB() && (entityexperienceorb.af() - i) % 40 == 0 && entityexperienceorb.i == j;
   }

   private void b(EntityExperienceOrb entityexperienceorb) {
      this.j += entityexperienceorb.j;
      this.g = Math.min(this.g, entityexperienceorb.g);
      entityexperienceorb.ai();
   }

   private void o() {
      Vec3D vec3d = this.dj();
      this.o(vec3d.c * 0.99F, Math.min(vec3d.d + 5.0E-4F, 0.06F), vec3d.e * 0.99F);
   }

   @Override
   protected void bb() {
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      if (this.b(damagesource)) {
         return false;
      } else if (this.H.B) {
         return true;
      } else {
         this.bj();
         this.h = (int)((float)this.h - f);
         if (this.h <= 0) {
            this.ai();
         }

         return true;
      }
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Health", (short)this.h);
      nbttagcompound.a("Age", (short)this.g);
      nbttagcompound.a("Value", (short)this.i);
      nbttagcompound.a("Count", this.j);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.h = nbttagcompound.g("Health");
      this.g = nbttagcompound.g("Age");
      this.i = nbttagcompound.g("Value");
      this.j = Math.max(nbttagcompound.h("Count"), 1);
   }

   @Override
   public void b_(EntityHuman entityhuman) {
      if (!this.H.B && entityhuman.bU == 0) {
         entityhuman.bU = 2;
         entityhuman.a(this, 1);
         int i = this.a(entityhuman, this.i);
         if (i > 0) {
            entityhuman.d(CraftEventFactory.callPlayerExpChangeEvent(entityhuman, i).getAmount());
         }

         --this.j;
         if (this.j == 0) {
            this.ai();
         }
      }
   }

   private int a(EntityHuman entityhuman, int i) {
      Entry<EnumItemSlot, ItemStack> entry = EnchantmentManager.a(Enchantments.L, entityhuman, ItemStack::i);
      if (entry != null) {
         ItemStack itemstack = entry.getValue();
         int j = Math.min(this.d(this.i), itemstack.j());
         PlayerItemMendEvent event = CraftEventFactory.callPlayerItemMendEvent(entityhuman, this, itemstack, entry.getKey(), j);
         j = event.getRepairAmount();
         if (event.isCancelled()) {
            return i;
         } else {
            itemstack.b(itemstack.j() - j);
            int k = i - this.c(j);
            this.i = k;
            return k > 0 ? this.a(entityhuman, k) : 0;
         }
      } else {
         return i;
      }
   }

   private int c(int i) {
      return i / 2;
   }

   private int d(int i) {
      return i * 2;
   }

   public int i() {
      return this.i;
   }

   public int j() {
      return this.i >= 2477
         ? 10
         : (
            this.i >= 1237
               ? 9
               : (
                  this.i >= 617
                     ? 8
                     : (
                        this.i >= 307
                           ? 7
                           : (this.i >= 149 ? 6 : (this.i >= 73 ? 5 : (this.i >= 37 ? 4 : (this.i >= 17 ? 3 : (this.i >= 7 ? 2 : (this.i >= 3 ? 1 : 0))))))
                     )
               )
         );
   }

   public static int b(int i) {
      if (i > 162670129) {
         return i - 100000;
      } else if (i > 81335063) {
         return 81335063;
      } else if (i > 40667527) {
         return 40667527;
      } else if (i > 20333759) {
         return 20333759;
      } else if (i > 10166857) {
         return 10166857;
      } else if (i > 5083423) {
         return 5083423;
      } else if (i > 2541701) {
         return 2541701;
      } else if (i > 1270849) {
         return 1270849;
      } else if (i > 635413) {
         return 635413;
      } else if (i > 317701) {
         return 317701;
      } else if (i > 158849) {
         return 158849;
      } else if (i > 79423) {
         return 79423;
      } else if (i > 39709) {
         return 39709;
      } else if (i > 19853) {
         return 19853;
      } else if (i > 9923) {
         return 9923;
      } else if (i > 4957) {
         return 4957;
      } else {
         return i >= 2477
            ? 2477
            : (
               i >= 1237
                  ? 1237
                  : (i >= 617 ? 617 : (i >= 307 ? 307 : (i >= 149 ? 149 : (i >= 73 ? 73 : (i >= 37 ? 37 : (i >= 17 ? 17 : (i >= 7 ? 7 : (i >= 3 ? 3 : 1))))))))
            );
      }
   }

   @Override
   public boolean cl() {
      return false;
   }

   @Override
   public Packet<PacketListenerPlayOut> S() {
      return new PacketPlayOutSpawnEntityExperienceOrb(this);
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.i;
   }
}
