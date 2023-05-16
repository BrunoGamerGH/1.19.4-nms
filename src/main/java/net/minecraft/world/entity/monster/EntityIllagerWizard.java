package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.level.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class EntityIllagerWizard extends EntityIllagerAbstract {
   private static final DataWatcherObject<Byte> e = DataWatcher.a(EntityIllagerWizard.class, DataWatcherRegistry.a);
   protected int b;
   private EntityIllagerWizard.Spell bS = EntityIllagerWizard.Spell.a;

   protected EntityIllagerWizard(EntityTypes<? extends EntityIllagerWizard> entitytypes, World world) {
      super(entitytypes, world);
   }

   @Override
   protected void a_() {
      super.a_();
      this.am.a(e, (byte)0);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      this.b = nbttagcompound.h("SpellTicks");
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("SpellTicks", this.b);
   }

   @Override
   public EntityIllagerAbstract.a q() {
      return this.gc() ? EntityIllagerAbstract.a.c : (this.gj() ? EntityIllagerAbstract.a.g : EntityIllagerAbstract.a.a);
   }

   public boolean gc() {
      return this.H.B ? this.am.a(e) > 0 : this.b > 0;
   }

   public void a(EntityIllagerWizard.Spell entityillagerwizard_spell) {
      this.bS = entityillagerwizard_spell;
      this.am.b(e, (byte)entityillagerwizard_spell.h);
   }

   public EntityIllagerWizard.Spell gd() {
      return !this.H.B ? this.bS : EntityIllagerWizard.Spell.a(this.am.a(e));
   }

   @Override
   protected void U() {
      super.U();
      if (this.b > 0) {
         --this.b;
      }
   }

   @Override
   public void l() {
      super.l();
      if (this.H.B && this.gc()) {
         EntityIllagerWizard.Spell entityillagerwizard_spell = this.gd();
         double d0 = entityillagerwizard_spell.i[0];
         double d1 = entityillagerwizard_spell.i[1];
         double d2 = entityillagerwizard_spell.i[2];
         float f = this.aT * (float) (Math.PI / 180.0) + MathHelper.b((float)this.ag * 0.6662F) * 0.25F;
         float f1 = MathHelper.b(f);
         float f2 = MathHelper.a(f);
         this.H.a(Particles.v, this.dl() + (double)f1 * 0.6, this.dn() + 1.8, this.dr() + (double)f2 * 0.6, d0, d1, d2);
         this.H.a(Particles.v, this.dl() - (double)f1 * 0.6, this.dn() + 1.8, this.dr() - (double)f2 * 0.6, d0, d1, d2);
      }
   }

   protected int ge() {
      return this.b;
   }

   protected abstract SoundEffect fS();

   protected abstract class PathfinderGoalCastSpell extends PathfinderGoal {
      protected int b;
      protected int c;

      @Override
      public boolean a() {
         EntityLiving entityliving = EntityIllagerWizard.this.P_();
         return entityliving != null && entityliving.bq() ? (EntityIllagerWizard.this.gc() ? false : EntityIllagerWizard.this.ag >= this.c) : false;
      }

      @Override
      public boolean b() {
         EntityLiving entityliving = EntityIllagerWizard.this.P_();
         return entityliving != null && entityliving.bq() && this.b > 0;
      }

      @Override
      public void c() {
         this.b = this.a(this.n());
         EntityIllagerWizard.this.b = this.h();
         this.c = EntityIllagerWizard.this.ag + this.i();
         SoundEffect soundeffect = this.l();
         if (soundeffect != null) {
            EntityIllagerWizard.this.a(soundeffect, 1.0F, 1.0F);
         }

         EntityIllagerWizard.this.a(this.m());
      }

      @Override
      public void e() {
         --this.b;
         if (this.b == 0) {
            if (!CraftEventFactory.handleEntitySpellCastEvent(EntityIllagerWizard.this, this.m())) {
               return;
            }

            this.k();
            EntityIllagerWizard.this.a(EntityIllagerWizard.this.fS(), 1.0F, 1.0F);
         }
      }

      protected abstract void k();

      protected int n() {
         return 20;
      }

      protected abstract int h();

      protected abstract int i();

      @Nullable
      protected abstract SoundEffect l();

      protected abstract EntityIllagerWizard.Spell m();
   }

   public static enum Spell {
      a(0, 0.0, 0.0, 0.0),
      b(1, 0.7, 0.7, 0.8),
      c(2, 0.4, 0.3, 0.35),
      d(3, 0.7, 0.5, 0.2),
      e(4, 0.3, 0.3, 0.8),
      f(5, 0.1, 0.1, 0.2);

      private static final IntFunction<EntityIllagerWizard.Spell> g = ByIdMap.a(
         entityillagerwizard_spell -> entityillagerwizard_spell.h, values(), ByIdMap.a.a
      );
      final int h;
      final double[] i;

      private Spell(int i, double d0, double d1, double d2) {
         this.h = i;
         this.i = new double[]{d0, d1, d2};
      }

      public static EntityIllagerWizard.Spell a(int i) {
         return g.apply(i);
      }
   }

   protected class b extends PathfinderGoal {
      public b() {
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }

      @Override
      public boolean a() {
         return EntityIllagerWizard.this.ge() > 0;
      }

      @Override
      public void c() {
         super.c();
         EntityIllagerWizard.this.bM.n();
      }

      @Override
      public void d() {
         super.d();
         EntityIllagerWizard.this.a(EntityIllagerWizard.Spell.a);
      }

      @Override
      public void e() {
         if (EntityIllagerWizard.this.P_() != null) {
            EntityIllagerWizard.this.C().a(EntityIllagerWizard.this.P_(), (float)EntityIllagerWizard.this.W(), (float)EntityIllagerWizard.this.V());
         }
      }
   }
}
