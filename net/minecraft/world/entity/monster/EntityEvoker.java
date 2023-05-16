package net.minecraft.world.entity.monster;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoalAvoidTarget;
import net.minecraft.world.entity.ai.goal.PathfinderGoalFloat;
import net.minecraft.world.entity.ai.goal.PathfinderGoalLookAtPlayer;
import net.minecraft.world.entity.ai.goal.PathfinderGoalRandomStroll;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalHurtByTarget;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.entity.raid.EntityRaider;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityEvoker extends EntityIllagerWizard {
   @Nullable
   private EntitySheep e;

   public EntityEvoker(EntityTypes<? extends EntityEvoker> entitytypes, World world) {
      super(entitytypes, world);
      this.bI = 10;
   }

   @Override
   protected void x() {
      super.x();
      this.bN.a(0, new PathfinderGoalFloat(this));
      this.bN.a(1, new EntityEvoker.b());
      this.bN.a(2, new PathfinderGoalAvoidTarget<>(this, EntityHuman.class, 8.0F, 0.6, 1.0));
      this.bN.a(4, new EntityEvoker.c());
      this.bN.a(5, new EntityEvoker.a());
      this.bN.a(6, new EntityEvoker.d());
      this.bN.a(8, new PathfinderGoalRandomStroll(this, 0.6));
      this.bN.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 3.0F, 1.0F));
      this.bN.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));
      this.bO.a(1, new PathfinderGoalHurtByTarget(this, EntityRaider.class).a());
      this.bO.a(2, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true).c(300));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityVillagerAbstract.class, false).c(300));
      this.bO.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityIronGolem.class, false));
   }

   public static AttributeProvider.Builder r() {
      return EntityMonster.fY().a(GenericAttributes.d, 0.5).a(GenericAttributes.b, 12.0).a(GenericAttributes.a, 24.0);
   }

   @Override
   protected void a_() {
      super.a_();
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
   }

   @Override
   public SoundEffect X_() {
      return SoundEffects.ho;
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
   }

   @Override
   protected void U() {
      super.U();
   }

   @Override
   public boolean p(Entity entity) {
      return entity == null
         ? false
         : (
            entity == this
               ? true
               : (
                  super.p(entity)
                     ? true
                     : (
                        entity instanceof EntityVex
                           ? this.p(((EntityVex)entity).r())
                           : (
                              entity instanceof EntityLiving && ((EntityLiving)entity).eJ() == EnumMonsterType.d
                                 ? this.cb() == null && entity.cb() == null
                                 : false
                           )
                     )
               )
         );
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.hm;
   }

   @Override
   protected SoundEffect x_() {
      return SoundEffects.hp;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.hr;
   }

   void a(@Nullable EntitySheep entitysheep) {
      this.e = entitysheep;
   }

   @Nullable
   EntitySheep gl() {
      return this.e;
   }

   @Override
   protected SoundEffect fS() {
      return SoundEffects.hn;
   }

   @Override
   public void a(int i, boolean flag) {
   }

   private class a extends EntityIllagerWizard.PathfinderGoalCastSpell {
      a() {
      }

      @Override
      protected int h() {
         return 40;
      }

      @Override
      protected int i() {
         return 100;
      }

      @Override
      protected void k() {
         EntityLiving entityliving = EntityEvoker.this.P_();
         double d0 = Math.min(entityliving.dn(), EntityEvoker.this.dn());
         double d1 = Math.max(entityliving.dn(), EntityEvoker.this.dn()) + 1.0;
         float f = (float)MathHelper.d(entityliving.dr() - EntityEvoker.this.dr(), entityliving.dl() - EntityEvoker.this.dl());
         if (EntityEvoker.this.f(entityliving) < 9.0) {
            for(int i = 0; i < 5; ++i) {
               float f1 = f + (float)i * (float) Math.PI * 0.4F;
               this.a(EntityEvoker.this.dl() + (double)MathHelper.b(f1) * 1.5, EntityEvoker.this.dr() + (double)MathHelper.a(f1) * 1.5, d0, d1, f1, 0);
            }

            for(int var11 = 0; var11 < 8; ++var11) {
               float f1 = f + (float)var11 * (float) Math.PI * 2.0F / 8.0F + ((float) (Math.PI * 2.0 / 5.0));
               this.a(EntityEvoker.this.dl() + (double)MathHelper.b(f1) * 2.5, EntityEvoker.this.dr() + (double)MathHelper.a(f1) * 2.5, d0, d1, f1, 3);
            }
         } else {
            for(int i = 0; i < 16; ++i) {
               double d2 = 1.25 * (double)(i + 1);
               int j = 1 * i;
               this.a(EntityEvoker.this.dl() + (double)MathHelper.b(f) * d2, EntityEvoker.this.dr() + (double)MathHelper.a(f) * d2, d0, d1, f, j);
            }
         }
      }

      private void a(double d0, double d1, double d2, double d3, float f, int i) {
         BlockPosition blockposition = BlockPosition.a(d0, d3, d1);
         boolean flag = false;
         double d4 = 0.0;

         do {
            BlockPosition blockposition1 = blockposition.d();
            IBlockData iblockdata = EntityEvoker.this.H.a_(blockposition1);
            if (iblockdata.d(EntityEvoker.this.H, blockposition1, EnumDirection.b)) {
               if (!EntityEvoker.this.H.w(blockposition)) {
                  IBlockData iblockdata1 = EntityEvoker.this.H.a_(blockposition);
                  VoxelShape voxelshape = iblockdata1.k(EntityEvoker.this.H, blockposition);
                  if (!voxelshape.b()) {
                     d4 = voxelshape.c(EnumDirection.EnumAxis.b);
                  }
               }

               flag = true;
               break;
            }

            blockposition = blockposition.d();
         } while(blockposition.v() >= MathHelper.a(d2) - 1);

         if (flag) {
            EntityEvoker.this.H.b(new EntityEvokerFangs(EntityEvoker.this.H, d0, (double)blockposition.v() + d4, d1, f, i, EntityEvoker.this));
         }
      }

      @Override
      protected SoundEffect l() {
         return SoundEffects.hs;
      }

      @Override
      protected EntityIllagerWizard.Spell m() {
         return EntityIllagerWizard.Spell.c;
      }
   }

   private class b extends EntityIllagerWizard.b {
      b() {
      }

      @Override
      public void e() {
         if (EntityEvoker.this.P_() != null) {
            EntityEvoker.this.C().a(EntityEvoker.this.P_(), (float)EntityEvoker.this.W(), (float)EntityEvoker.this.V());
         } else if (EntityEvoker.this.gl() != null) {
            EntityEvoker.this.C().a(EntityEvoker.this.gl(), (float)EntityEvoker.this.W(), (float)EntityEvoker.this.V());
         }
      }
   }

   private class c extends EntityIllagerWizard.PathfinderGoalCastSpell {
      private final PathfinderTargetCondition e = PathfinderTargetCondition.b().a(16.0).d().e();

      c() {
      }

      @Override
      public boolean a() {
         if (!super.a()) {
            return false;
         } else {
            int i = EntityEvoker.this.H.a(EntityVex.class, this.e, EntityEvoker.this, EntityEvoker.this.cD().g(16.0)).size();
            return EntityEvoker.this.af.a(8) + 1 > i;
         }
      }

      @Override
      protected int h() {
         return 100;
      }

      @Override
      protected int i() {
         return 340;
      }

      @Override
      protected void k() {
         WorldServer worldserver = (WorldServer)EntityEvoker.this.H;

         for(int i = 0; i < 3; ++i) {
            BlockPosition blockposition = EntityEvoker.this.dg().b(-2 + EntityEvoker.this.af.a(5), 1, -2 + EntityEvoker.this.af.a(5));
            EntityVex entityvex = EntityTypes.be.a(EntityEvoker.this.H);
            if (entityvex != null) {
               entityvex.a(blockposition, 0.0F, 0.0F);
               entityvex.a(worldserver, EntityEvoker.this.H.d_(blockposition), EnumMobSpawn.f, null, null);
               entityvex.a(EntityEvoker.this);
               entityvex.g(blockposition);
               entityvex.b(20 * (30 + EntityEvoker.this.af.a(90)));
               worldserver.addFreshEntityWithPassengers(entityvex, SpawnReason.SPELL);
            }
         }
      }

      @Override
      protected SoundEffect l() {
         return SoundEffects.ht;
      }

      @Override
      protected EntityIllagerWizard.Spell m() {
         return EntityIllagerWizard.Spell.b;
      }
   }

   public class d extends EntityIllagerWizard.PathfinderGoalCastSpell {
      private final PathfinderTargetCondition e = PathfinderTargetCondition.b().a(16.0).a(entityliving -> ((EntitySheep)entityliving).r() == EnumColor.l);

      @Override
      public boolean a() {
         if (EntityEvoker.this.P_() != null) {
            return false;
         } else if (EntityEvoker.this.gc()) {
            return false;
         } else if (EntityEvoker.this.ag < this.c) {
            return false;
         } else if (!EntityEvoker.this.H.W().b(GameRules.c)) {
            return false;
         } else {
            List<EntitySheep> list = EntityEvoker.this.H.a(EntitySheep.class, this.e, EntityEvoker.this, EntityEvoker.this.cD().c(16.0, 4.0, 16.0));
            if (list.isEmpty()) {
               return false;
            } else {
               EntityEvoker.this.a(list.get(EntityEvoker.this.af.a(list.size())));
               return true;
            }
         }
      }

      @Override
      public boolean b() {
         return EntityEvoker.this.gl() != null && this.b > 0;
      }

      @Override
      public void d() {
         super.d();
         EntityEvoker.this.a(null);
      }

      @Override
      protected void k() {
         EntitySheep entitysheep = EntityEvoker.this.gl();
         if (entitysheep != null && entitysheep.bq()) {
            entitysheep.b(EnumColor.o);
         }
      }

      @Override
      protected int n() {
         return 40;
      }

      @Override
      protected int h() {
         return 60;
      }

      @Override
      protected int i() {
         return 140;
      }

      @Override
      protected SoundEffect l() {
         return SoundEffects.hu;
      }

      @Override
      protected EntityIllagerWizard.Spell m() {
         return EntityIllagerWizard.Spell.d;
      }
   }
}
