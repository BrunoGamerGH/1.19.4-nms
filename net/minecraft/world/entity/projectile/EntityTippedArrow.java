package net.minecraft.world.entity.projectile;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.core.particles.Particles;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionRegistry;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EntityTippedArrow extends EntityArrow {
   private static final int f = 600;
   private static final int g = -1;
   private static final DataWatcherObject<Integer> h = DataWatcher.a(EntityTippedArrow.class, DataWatcherRegistry.b);
   private static final byte i = 0;
   private PotionRegistry j = Potions.b;
   public final Set<MobEffect> k = Sets.newHashSet();
   private boolean l;

   public EntityTippedArrow(EntityTypes<? extends EntityTippedArrow> entitytypes, World world) {
      super(entitytypes, world);
   }

   public EntityTippedArrow(World world, double d0, double d1, double d2) {
      super(EntityTypes.e, d0, d1, d2, world);
   }

   public EntityTippedArrow(World world, EntityLiving entityliving) {
      super(EntityTypes.e, entityliving, world);
   }

   public void a(ItemStack itemstack) {
      if (itemstack.a(Items.ur)) {
         this.j = PotionUtil.d(itemstack);
         Collection<MobEffect> collection = PotionUtil.b(itemstack);
         if (!collection.isEmpty()) {
            for(MobEffect mobeffect : collection) {
               this.k.add(new MobEffect(mobeffect));
            }
         }

         int i = c(itemstack);
         if (i == -1) {
            this.C();
         } else {
            this.d(i);
         }
      } else if (itemstack.a(Items.nD)) {
         this.j = Potions.b;
         this.k.clear();
         this.am.b(h, -1);
      }
   }

   public static int c(ItemStack itemstack) {
      NBTTagCompound nbttagcompound = itemstack.u();
      return nbttagcompound != null && nbttagcompound.b("CustomPotionColor", 99) ? nbttagcompound.h("CustomPotionColor") : -1;
   }

   private void C() {
      this.l = false;
      if (this.j == Potions.b && this.k.isEmpty()) {
         this.am.b(h, -1);
      } else {
         this.am.b(h, PotionUtil.a(PotionUtil.a(this.j, this.k)));
      }
   }

   public void a(MobEffect mobeffect) {
      this.k.add(mobeffect);
      this.aj().b(h, PotionUtil.a(PotionUtil.a(this.j, this.k)));
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(h, -1);
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B) {
         if (this.b) {
            if (this.c % 5 == 0) {
               this.c(1);
            }
         } else {
            this.c(2);
         }
      } else if (this.b && this.c != 0 && !this.k.isEmpty() && this.c >= 600) {
         this.H.a(this, (byte)0);
         this.j = Potions.b;
         this.k.clear();
         this.am.b(h, -1);
      }
   }

   private void c(int i) {
      int j = this.y();
      if (j != -1 && i > 0) {
         double d0 = (double)(j >> 16 & 0xFF) / 255.0;
         double d1 = (double)(j >> 8 & 0xFF) / 255.0;
         double d2 = (double)(j >> 0 & 0xFF) / 255.0;

         for(int k = 0; k < i; ++k) {
            this.H.a(Particles.v, this.d(0.5), this.do(), this.g(0.5), d0, d1, d2);
         }
      }
   }

   public void refreshEffects() {
      this.aj().b(h, PotionUtil.a(PotionUtil.a(this.j, this.k)));
   }

   public String getPotionType() {
      return BuiltInRegistries.j.b(this.j).toString();
   }

   public void setPotionType(String string) {
      this.j = BuiltInRegistries.j.a(new MinecraftKey(string));
      this.aj().b(h, PotionUtil.a(PotionUtil.a(this.j, this.k)));
   }

   public boolean isTipped() {
      return !this.k.isEmpty() || this.j != Potions.b;
   }

   public int y() {
      return this.am.a(h);
   }

   public void d(int i) {
      this.l = true;
      this.am.b(h, i);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      if (this.j != Potions.b) {
         nbttagcompound.a("Potion", BuiltInRegistries.j.b(this.j).toString());
      }

      if (this.l) {
         nbttagcompound.a("Color", this.y());
      }

      if (!this.k.isEmpty()) {
         NBTTagList nbttaglist = new NBTTagList();

         for(MobEffect mobeffect : this.k) {
            nbttaglist.add(mobeffect.a(new NBTTagCompound()));
         }

         nbttagcompound.a("CustomPotionEffects", nbttaglist);
      }
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.b("Potion", 8)) {
         this.j = PotionUtil.c(nbttagcompound);
      }

      for(MobEffect mobeffect : PotionUtil.b(nbttagcompound)) {
         this.a(mobeffect);
      }

      if (nbttagcompound.b("Color", 99)) {
         this.d(nbttagcompound.h("Color"));
      } else {
         this.C();
      }
   }

   @Override
   protected void a(EntityLiving entityliving) {
      super.a(entityliving);
      Entity entity = this.z();

      for(MobEffect mobeffect : this.j.a()) {
         entityliving.addEffect(
            new MobEffect(mobeffect.c(), Math.max(mobeffect.a(i -> i / 8), 1), mobeffect.e(), mobeffect.f(), mobeffect.g()), entity, Cause.ARROW
         );
      }

      if (!this.k.isEmpty()) {
         for(MobEffect mobeffect : this.k) {
            entityliving.addEffect(mobeffect, entity, Cause.ARROW);
         }
      }
   }

   @Override
   protected ItemStack o() {
      if (this.k.isEmpty() && this.j == Potions.b) {
         return new ItemStack(Items.nD);
      } else {
         ItemStack itemstack = new ItemStack(Items.ur);
         PotionUtil.a(itemstack, this.j);
         PotionUtil.a(itemstack, this.k);
         if (this.l) {
            itemstack.v().a("CustomPotionColor", this.y());
         }

         return itemstack;
      }
   }

   @Override
   public void b(byte b0) {
      if (b0 == 0) {
         int i = this.y();
         if (i != -1) {
            double d0 = (double)(i >> 16 & 0xFF) / 255.0;
            double d1 = (double)(i >> 8 & 0xFF) / 255.0;
            double d2 = (double)(i >> 0 & 0xFF) / 255.0;

            for(int j = 0; j < 20; ++j) {
               this.H.a(Particles.v, this.d(0.5), this.do(), this.g(0.5), d0, d1, d2);
            }
         }
      } else {
         super.b(b0);
      }
   }
}
