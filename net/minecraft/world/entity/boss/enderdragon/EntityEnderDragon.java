package net.minecraft.world.entity.boss.enderdragon;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherObject;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.attributes.AttributeProvider;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerManager;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonControllerPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.IDragonController;
import net.minecraft.world.entity.monster.IMonster;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.end.EnderDragonBattle;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.WorldGenEndTrophy;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.slf4j.Logger;

public class EntityEnderDragon extends EntityInsentient implements IMonster {
   private static final Logger bX = LogUtils.getLogger();
   public static final DataWatcherObject<Integer> b = DataWatcher.a(EntityEnderDragon.class, DataWatcherRegistry.b);
   private static final PathfinderTargetCondition bY = PathfinderTargetCondition.a().a(64.0);
   private static final int bZ = 200;
   private static final int ca = 400;
   private static final float cb = 0.25F;
   private static final String cc = "DragonDeathTime";
   private static final String cd = "DragonPhase";
   public final double[][] c = new double[64][3];
   public int d = -1;
   public final EntityComplexPart[] ce;
   public final EntityComplexPart e = new EntityComplexPart(this, "head", 1.0F, 1.0F);
   private final EntityComplexPart cf = new EntityComplexPart(this, "neck", 3.0F, 3.0F);
   private final EntityComplexPart cg = new EntityComplexPart(this, "body", 5.0F, 3.0F);
   private final EntityComplexPart ch = new EntityComplexPart(this, "tail", 2.0F, 2.0F);
   private final EntityComplexPart ci = new EntityComplexPart(this, "tail", 2.0F, 2.0F);
   private final EntityComplexPart cj = new EntityComplexPart(this, "tail", 2.0F, 2.0F);
   private final EntityComplexPart ck = new EntityComplexPart(this, "wing", 4.0F, 2.0F);
   private final EntityComplexPart cl = new EntityComplexPart(this, "wing", 4.0F, 2.0F);
   public float bR;
   public float bS;
   public boolean bT;
   public int bU;
   public float bV;
   @Nullable
   public EntityEnderCrystal bW;
   @Nullable
   private final EnderDragonBattle cm;
   private final DragonControllerManager cn;
   private int co = 100;
   private float cp;
   private final PathPoint[] cq = new PathPoint[24];
   private final int[] cr = new int[24];
   private final Path cs = new Path();
   private final Explosion explosionSource;

   public EntityEnderDragon(EntityTypes<? extends EntityEnderDragon> entitytypes, World world) {
      super(EntityTypes.C, world);
      this.ce = new EntityComplexPart[]{this.e, this.cf, this.cg, this.ch, this.ci, this.cj, this.ck, this.cl};
      this.c(this.eE());
      this.ae = true;
      this.as = true;
      if (world instanceof WorldServer) {
         this.cm = ((WorldServer)world).B();
      } else {
         this.cm = null;
      }

      this.cn = new DragonControllerManager(this);
      this.explosionSource = new Explosion(world, this, null, null, Double.NaN, Double.NaN, Double.NaN, Float.NaN, true, Explosion.Effect.b);
   }

   public static AttributeProvider.Builder q() {
      return EntityInsentient.y().a(GenericAttributes.a, 200.0);
   }

   @Override
   public boolean aN() {
      float f = MathHelper.b(this.bS * (float) (Math.PI * 2));
      float f1 = MathHelper.b(this.bR * (float) (Math.PI * 2));
      return f1 <= -0.3F && f >= -0.3F;
   }

   @Override
   public void aM() {
      if (this.H.B && !this.aO()) {
         this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.gS, this.cX(), 5.0F, 0.8F + this.af.i() * 0.3F, false);
      }
   }

   @Override
   protected void a_() {
      super.a_();
      this.aj().a(b, DragonControllerPhase.k.b());
   }

   public double[] a(int i, float f) {
      if (this.ep()) {
         f = 0.0F;
      }

      f = 1.0F - f;
      int j = this.d - i & 63;
      int k = this.d - i - 1 & 63;
      double[] adouble = new double[3];
      double d0 = this.c[j][0];
      double d1 = MathHelper.d(this.c[k][0] - d0);
      adouble[0] = d0 + d1 * (double)f;
      d0 = this.c[j][1];
      d1 = this.c[k][1] - d0;
      adouble[1] = d0 + d1 * (double)f;
      adouble[2] = MathHelper.d((double)f, this.c[j][2], this.c[k][2]);
      return adouble;
   }

   @Override
   public void b_() {
      this.aB();
      if (this.H.B) {
         this.c(this.eo());
         if (!this.aO() && !this.cn.a().a() && --this.co < 0) {
            this.H.a(this.dl(), this.dn(), this.dr(), SoundEffects.gT, this.cX(), 2.5F, 0.8F + this.af.i() * 0.3F, false);
            this.co = 200 + this.af.a(200);
         }
      }

      this.bR = this.bS;
      if (this.ep()) {
         float f1 = (this.af.i() - 0.5F) * 8.0F;
         float f = (this.af.i() - 0.5F) * 4.0F;
         float f2 = (this.af.i() - 0.5F) * 8.0F;
         this.H.a(Particles.x, this.dl() + (double)f1, this.dn() + 2.0 + (double)f, this.dr() + (double)f2, 0.0, 0.0, 0.0);
      } else {
         this.fS();
         Vec3D vec3d = this.dj();
         float f = 0.2F / ((float)vec3d.h() * 10.0F + 1.0F);
         f *= (float)Math.pow(2.0, vec3d.d);
         if (this.cn.a().a()) {
            this.bS += 0.1F;
         } else if (this.bT) {
            this.bS += f * 0.5F;
         } else {
            this.bS += f;
         }

         this.f(MathHelper.g(this.dw()));
         if (this.fK()) {
            this.bS = 0.5F;
         } else {
            if (this.d < 0) {
               for(int i = 0; i < this.c.length; ++i) {
                  this.c[i][0] = (double)this.dw();
                  this.c[i][1] = this.dn();
               }
            }

            if (++this.d == this.c.length) {
               this.d = 0;
            }

            this.c[this.d][0] = (double)this.dw();
            this.c[this.d][1] = this.dn();
            if (this.H.B) {
               if (this.bm > 0) {
                  double d3 = this.dl() + (this.bn - this.dl()) / (double)this.bm;
                  double d0 = this.dn() + (this.bo - this.dn()) / (double)this.bm;
                  double d1 = this.dr() + (this.bp - this.dr()) / (double)this.bm;
                  double d2 = MathHelper.d(this.bq - (double)this.dw());
                  this.f(this.dw() + (float)d2 / (float)this.bm);
                  this.e(this.dy() + (float)(this.br - (double)this.dy()) / (float)this.bm);
                  --this.bm;
                  this.e(d3, d0, d1);
                  this.a(this.dw(), this.dy());
               }

               this.cn.a().b();
            } else {
               IDragonController idragoncontroller = this.cn.a();
               idragoncontroller.c();
               if (this.cn.a() != idragoncontroller) {
                  idragoncontroller = this.cn.a();
                  idragoncontroller.c();
               }

               Vec3D vec3d1 = idragoncontroller.g();
               if (vec3d1 != null && idragoncontroller.i() != DragonControllerPhase.k) {
                  double d0 = vec3d1.c - this.dl();
                  double d1 = vec3d1.d - this.dn();
                  double d2 = vec3d1.e - this.dr();
                  double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                  float f6 = idragoncontroller.f();
                  double d5 = Math.sqrt(d0 * d0 + d2 * d2);
                  if (d5 > 0.0) {
                     d1 = MathHelper.a(d1 / d5, (double)(-f6), (double)f6);
                  }

                  this.f(this.dj().b(0.0, d1 * 0.01, 0.0));
                  this.f(MathHelper.g(this.dw()));
                  Vec3D vec3d2 = vec3d1.a(this.dl(), this.dn(), this.dr()).d();
                  Vec3D vec3d3 = new Vec3D(
                        (double)MathHelper.a(this.dw() * (float) (Math.PI / 180.0)),
                        this.dj().d,
                        (double)(-MathHelper.b(this.dw() * (float) (Math.PI / 180.0)))
                     )
                     .d();
                  float f3 = Math.max(((float)vec3d3.b(vec3d2) + 0.5F) / 1.5F, 0.0F);
                  if (Math.abs(d0) > 1.0E-5F || Math.abs(d2) > 1.0E-5F) {
                     float f4 = MathHelper.a(MathHelper.g(180.0F - (float)MathHelper.d(d0, d2) * (180.0F / (float)Math.PI) - this.dw()), -50.0F, 50.0F);
                     this.bV *= 0.8F;
                     this.bV += f4 * idragoncontroller.h();
                     this.f(this.dw() + this.bV * 0.1F);
                  }

                  float f4 = (float)(2.0 / (d4 + 1.0));
                  float f5 = 0.06F;
                  this.a(0.06F * (f3 * f4 + (1.0F - f4)), new Vec3D(0.0, 0.0, -1.0));
                  if (this.bT) {
                     this.a(EnumMoveType.a, this.dj().a(0.8F));
                  } else {
                     this.a(EnumMoveType.a, this.dj());
                  }

                  Vec3D vec3d4 = this.dj().d();
                  double d6 = 0.8 + 0.15 * (vec3d4.b(vec3d3) + 1.0) / 2.0;
                  this.f(this.dj().d(d6, 0.91F, d6));
               }
            }

            this.aT = this.dw();
            Vec3D[] avec3d = new Vec3D[this.ce.length];

            for(int j = 0; j < this.ce.length; ++j) {
               avec3d[j] = new Vec3D(this.ce[j].dl(), this.ce[j].dn(), this.ce[j].dr());
            }

            float f7 = (float)(this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
            float f8 = MathHelper.b(f7);
            float f9 = MathHelper.a(f7);
            float f10 = this.dw() * (float) (Math.PI / 180.0);
            float f11 = MathHelper.a(f10);
            float f12 = MathHelper.b(f10);
            this.a(this.cg, (double)(f11 * 0.5F), 0.0, (double)(-f12 * 0.5F));
            this.a(this.ck, (double)(f12 * 4.5F), 2.0, (double)(f11 * 4.5F));
            this.a(this.cl, (double)(f12 * -4.5F), 2.0, (double)(f11 * -4.5F));
            if (!this.H.B && this.aJ == 0) {
               this.b(this.H.a(this, this.ck.cD().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), IEntitySelector.e));
               this.b(this.H.a(this, this.cl.cD().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), IEntitySelector.e));
               this.c(this.H.a(this, this.e.cD().g(1.0), IEntitySelector.e));
               this.c(this.H.a(this, this.cf.cD().g(1.0), IEntitySelector.e));
            }

            float f13 = MathHelper.a(this.dw() * (float) (Math.PI / 180.0) - this.bV * 0.01F);
            float f14 = MathHelper.b(this.dw() * (float) (Math.PI / 180.0) - this.bV * 0.01F);
            float f15 = this.fR();
            this.a(this.e, (double)(f13 * 6.5F * f8), (double)(f15 + f9 * 6.5F), (double)(-f14 * 6.5F * f8));
            this.a(this.cf, (double)(f13 * 5.5F * f8), (double)(f15 + f9 * 5.5F), (double)(-f14 * 5.5F * f8));
            double[] adouble = this.a(5, 1.0F);

            for(int k = 0; k < 3; ++k) {
               EntityComplexPart entitycomplexpart = null;
               if (k == 0) {
                  entitycomplexpart = this.ch;
               }

               if (k == 1) {
                  entitycomplexpart = this.ci;
               }

               if (k == 2) {
                  entitycomplexpart = this.cj;
               }

               double[] adouble1 = this.a(12 + k * 2, 1.0F);
               float f16 = this.dw() * (float) (Math.PI / 180.0) + this.i(adouble1[0] - adouble[0]) * (float) (Math.PI / 180.0);
               float f3 = MathHelper.a(f16);
               float f4 = MathHelper.b(f16);
               float f5 = 1.5F;
               float f17 = (float)(k + 1) * 2.0F;
               this.a(
                  entitycomplexpart,
                  (double)(-(f11 * 1.5F + f3 * f17) * f8),
                  adouble1[1] - adouble[1] - (double)((f17 + 1.5F) * f9) + 1.5,
                  (double)((f12 * 1.5F + f4 * f17) * f8)
               );
            }

            if (!this.H.B) {
               this.bT = this.b(this.e.cD()) | this.b(this.cf.cD()) | this.b(this.cg.cD());
               if (this.cm != null) {
                  this.cm.b(this);
               }
            }

            for(int var51 = 0; var51 < this.ce.length; ++var51) {
               this.ce[var51].I = avec3d[var51].c;
               this.ce[var51].J = avec3d[var51].d;
               this.ce[var51].K = avec3d[var51].e;
               this.ce[var51].ab = avec3d[var51].c;
               this.ce[var51].ac = avec3d[var51].d;
               this.ce[var51].ad = avec3d[var51].e;
            }
         }
      }
   }

   private void a(EntityComplexPart entitycomplexpart, double d0, double d1, double d2) {
      entitycomplexpart.e(this.dl() + d0, this.dn() + d1, this.dr() + d2);
   }

   private float fR() {
      if (this.cn.a().a()) {
         return -1.0F;
      } else {
         double[] adouble = this.a(5, 1.0F);
         double[] adouble1 = this.a(0, 1.0F);
         return (float)(adouble[1] - adouble1[1]);
      }
   }

   private void fS() {
      if (this.bW != null) {
         if (this.bW.dB()) {
            this.bW = null;
         } else if (this.ag % 10 == 0 && this.eo() < this.eE()) {
            EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), 1.0, RegainReason.ENDER_CRYSTAL);
            this.H.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               this.c((float)((double)this.eo() + event.getAmount()));
            }
         }
      }

      if (this.af.a(10) == 0) {
         List<EntityEnderCrystal> list = this.H.a(EntityEnderCrystal.class, this.cD().g(32.0));
         EntityEnderCrystal entityendercrystal = null;
         double d0 = Double.MAX_VALUE;

         for(EntityEnderCrystal entityendercrystal1 : list) {
            double d1 = entityendercrystal1.f(this);
            if (d1 < d0) {
               d0 = d1;
               entityendercrystal = entityendercrystal1;
            }
         }

         this.bW = entityendercrystal;
      }
   }

   private void b(List<Entity> list) {
      double d0 = (this.cg.cD().a + this.cg.cD().d) / 2.0;
      double d1 = (this.cg.cD().c + this.cg.cD().f) / 2.0;

      for(Entity entity : list) {
         if (entity instanceof EntityLiving) {
            double d2 = entity.dl() - d0;
            double d3 = entity.dr() - d1;
            double d4 = Math.max(d2 * d2 + d3 * d3, 0.1);
            entity.j(d2 / d4 * 4.0, 0.2F, d3 / d4 * 4.0);
            if (!this.cn.a().a() && ((EntityLiving)entity).eb() < entity.ag - 2) {
               entity.a(this.dG().b((EntityLiving)this), 5.0F);
               this.a(this, entity);
            }
         }
      }
   }

   private void c(List<Entity> list) {
      for(Entity entity : list) {
         if (entity instanceof EntityLiving) {
            entity.a(this.dG().b((EntityLiving)this), 10.0F);
            this.a(this, entity);
         }
      }
   }

   private float i(double d0) {
      return (float)MathHelper.d(d0);
   }

   private boolean b(AxisAlignedBB axisalignedbb) {
      int i = MathHelper.a(axisalignedbb.a);
      int j = MathHelper.a(axisalignedbb.b);
      int k = MathHelper.a(axisalignedbb.c);
      int l = MathHelper.a(axisalignedbb.d);
      int i1 = MathHelper.a(axisalignedbb.e);
      int j1 = MathHelper.a(axisalignedbb.f);
      boolean flag = false;
      boolean flag1 = false;
      List<Block> destroyedBlocks = new ArrayList();

      for(int k1 = i; k1 <= l; ++k1) {
         for(int l1 = j; l1 <= i1; ++l1) {
            for(int i2 = k; i2 <= j1; ++i2) {
               BlockPosition blockposition = new BlockPosition(k1, l1, i2);
               IBlockData iblockdata = this.H.a_(blockposition);
               if (!iblockdata.h() && !iblockdata.a(TagsBlock.aA)) {
                  if (this.H.W().b(GameRules.c) && !iblockdata.a(TagsBlock.az)) {
                     flag1 = true;
                     destroyedBlocks.add(CraftBlock.at(this.H, blockposition));
                  } else {
                     flag = true;
                  }
               }
            }
         }
      }

      if (!flag1) {
         return flag;
      } else {
         org.bukkit.entity.Entity bukkitEntity = this.getBukkitEntity();
         EntityExplodeEvent event = new EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0F);
         bukkitEntity.getServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return flag;
         } else {
            if (event.getYield() == 0.0F) {
               for(Block block : event.blockList()) {
                  this.H.a(new BlockPosition(block.getX(), block.getY(), block.getZ()), false);
               }
            } else {
               for(Block block : event.blockList()) {
                  Material blockId = block.getType();
                  if (!blockId.isAir()) {
                     CraftBlock craftBlock = (CraftBlock)block;
                     BlockPosition blockposition = craftBlock.getPosition();
                     net.minecraft.world.level.block.Block nmsBlock = craftBlock.getNMS().b();
                     if (nmsBlock.a(this.explosionSource)) {
                        TileEntity tileentity = craftBlock.getNMS().q() ? this.H.c_(blockposition) : null;
                        LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder((WorldServer)this.H)
                           .a(this.H.z)
                           .a(LootContextParameters.f, Vec3D.b(blockposition))
                           .a(LootContextParameters.i, ItemStack.b)
                           .a(LootContextParameters.j, 1.0F / event.getYield())
                           .b(LootContextParameters.h, tileentity);
                        craftBlock.getNMS()
                           .a(loottableinfo_builder)
                           .forEach(itemstack -> net.minecraft.world.level.block.Block.a(this.H, blockposition, itemstack));
                        craftBlock.getNMS().a((WorldServer)this.H, blockposition, ItemStack.b, false);
                     }

                     nmsBlock.a(this.H, blockposition, this.explosionSource);
                     this.H.a(blockposition, false);
                  }
               }
            }

            if (flag1) {
               BlockPosition blockposition1 = new BlockPosition(i + this.af.a(l - i + 1), j + this.af.a(i1 - j + 1), k + this.af.a(j1 - k + 1));
               this.H.c(2008, blockposition1, 0);
            }

            return flag;
         }
      }
   }

   public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
      if (this.cn.a().i() == DragonControllerPhase.j) {
         return false;
      } else {
         f = this.cn.a().a(damagesource, f);
         if (entitycomplexpart != this.e) {
            f = f / 4.0F + Math.min(f, 1.0F);
         }

         if (f < 0.01F) {
            return false;
         } else {
            if (damagesource.d() instanceof EntityHuman || damagesource.a(DamageTypeTags.y)) {
               float f1 = this.eo();
               this.g(damagesource, f);
               if (this.ep() && !this.cn.a().a()) {
                  this.c(1.0F);
                  this.cn.a(DragonControllerPhase.j);
               }

               if (this.cn.a().a()) {
                  this.cp = this.cp + f1 - this.eo();
                  if (this.cp > 0.25F * this.eE()) {
                     this.cp = 0.0F;
                     this.cn.a(DragonControllerPhase.e);
                  }
               }
            }

            return true;
         }
      }
   }

   @Override
   public boolean a(DamageSource damagesource, float f) {
      return !this.H.B ? this.a(this.cg, damagesource, f) : false;
   }

   protected boolean g(DamageSource damagesource, float f) {
      return super.a(damagesource, f);
   }

   @Override
   public void ah() {
      this.a(Entity.RemovalReason.a);
      this.a(GameEvent.q);
      if (this.cm != null) {
         this.cm.b(this);
         this.cm.a(this);
      }
   }

   @Override
   public int getExpReward() {
      boolean flag = this.H.W().b(GameRules.f);
      short short0 = 500;
      if (this.cm != null && !this.cm.d()) {
         short0 = 12000;
      }

      return flag ? short0 : 0;
   }

   @Override
   protected void dU() {
      if (this.cm != null) {
         this.cm.b(this);
      }

      ++this.bU;
      if (this.bU >= 180 && this.bU <= 200) {
         float f = (this.af.i() - 0.5F) * 8.0F;
         float f1 = (this.af.i() - 0.5F) * 4.0F;
         float f2 = (this.af.i() - 0.5F) * 8.0F;
         this.H.a(Particles.w, this.dl() + (double)f, this.dn() + 2.0 + (double)f1, this.dr() + (double)f2, 0.0, 0.0, 0.0);
      }

      int short0 = this.expToDrop;
      if (this.H instanceof WorldServer) {
         if (this.bU > 150 && this.bU % 5 == 0) {
            EntityExperienceOrb.a((WorldServer)this.H, this.de(), MathHelper.d((float)short0 * 0.08F));
         }

         if (this.bU == 1 && !this.aO()) {
            int viewDistance = ((WorldServer)this.H).getCraftServer().getViewDistance() * 16;

            for(EntityPlayer player : this.H.n().ac().k) {
               double deltaX = this.dl() - player.dl();
               double deltaZ = this.dr() - player.dr();
               double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
               if (this.H.spigotConfig.dragonDeathSoundRadius <= 0
                  || !(distanceSquared > (double)(this.H.spigotConfig.dragonDeathSoundRadius * this.H.spigotConfig.dragonDeathSoundRadius))) {
                  if (distanceSquared > (double)(viewDistance * viewDistance)) {
                     double deltaLength = Math.sqrt(distanceSquared);
                     double relativeX = player.dl() + deltaX / deltaLength * (double)viewDistance;
                     double relativeZ = player.dr() + deltaZ / deltaLength * (double)viewDistance;
                     player.b.a(new PacketPlayOutWorldEvent(1028, new BlockPosition((int)relativeX, (int)this.dn(), (int)relativeZ), 0, true));
                  } else {
                     player.b.a(new PacketPlayOutWorldEvent(1028, new BlockPosition((int)this.dl(), (int)this.dn(), (int)this.dr()), 0, true));
                  }
               }
            }
         }
      }

      this.a(EnumMoveType.a, new Vec3D(0.0, 0.1F, 0.0));
      if (this.bU == 200 && this.H instanceof WorldServer) {
         EntityExperienceOrb.a((WorldServer)this.H, this.de(), MathHelper.d((float)short0 * 0.2F));
         if (this.cm != null) {
            this.cm.a(this);
         }

         this.a(Entity.RemovalReason.a);
         this.a(GameEvent.q);
      }
   }

   public int r() {
      if (this.cq[0] == null) {
         for(int i = 0; i < 24; ++i) {
            int j = 5;
            int k;
            int l;
            if (i < 12) {
               k = MathHelper.d(60.0F * MathHelper.b(2.0F * ((float) -Math.PI + ((float) (Math.PI / 12)) * (float)i)));
               l = MathHelper.d(60.0F * MathHelper.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)i)));
            } else if (i < 20) {
               int i1 = i - 12;
               k = MathHelper.d(40.0F * MathHelper.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)i1)));
               l = MathHelper.d(40.0F * MathHelper.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)i1)));
               j += 10;
            } else {
               int i1 = i - 20;
               k = MathHelper.d(20.0F * MathHelper.b(2.0F * ((float) -Math.PI + ((float) (Math.PI / 4)) * (float)i1)));
               l = MathHelper.d(20.0F * MathHelper.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)i1)));
            }

            int j1 = Math.max(this.H.m_() + 10, this.H.a(HeightMap.Type.f, new BlockPosition(k, 0, l)).v() + j);
            this.cq[i] = new PathPoint(k, j1, l);
         }

         this.cr[0] = 6146;
         this.cr[1] = 8197;
         this.cr[2] = 8202;
         this.cr[3] = 16404;
         this.cr[4] = 32808;
         this.cr[5] = 32848;
         this.cr[6] = 65696;
         this.cr[7] = 131392;
         this.cr[8] = 131712;
         this.cr[9] = 263424;
         this.cr[10] = 526848;
         this.cr[11] = 525313;
         this.cr[12] = 1581057;
         this.cr[13] = 3166214;
         this.cr[14] = 2138120;
         this.cr[15] = 6373424;
         this.cr[16] = 4358208;
         this.cr[17] = 12910976;
         this.cr[18] = 9044480;
         this.cr[19] = 9706496;
         this.cr[20] = 15216640;
         this.cr[21] = 13688832;
         this.cr[22] = 11763712;
         this.cr[23] = 8257536;
      }

      return this.r(this.dl(), this.dn(), this.dr());
   }

   public int r(double d0, double d1, double d2) {
      float f = 10000.0F;
      int i = 0;
      PathPoint pathpoint = new PathPoint(MathHelper.a(d0), MathHelper.a(d1), MathHelper.a(d2));
      byte b0 = 0;
      if (this.cm == null || this.cm.c() == 0) {
         b0 = 12;
      }

      for(int j = b0; j < 24; ++j) {
         if (this.cq[j] != null) {
            float f1 = this.cq[j].c(pathpoint);
            if (f1 < f) {
               f = f1;
               i = j;
            }
         }
      }

      return i;
   }

   @Nullable
   public PathEntity a(int i, int j, @Nullable PathPoint pathpoint) {
      for(int k = 0; k < 24; ++k) {
         PathPoint pathpoint1 = this.cq[k];
         pathpoint1.i = false;
         pathpoint1.g = 0.0F;
         pathpoint1.e = 0.0F;
         pathpoint1.f = 0.0F;
         pathpoint1.h = null;
         pathpoint1.d = -1;
      }

      PathPoint pathpoint2 = this.cq[i];
      PathPoint pathpoint1 = this.cq[j];
      pathpoint2.e = 0.0F;
      pathpoint2.f = pathpoint2.a(pathpoint1);
      pathpoint2.g = pathpoint2.f;
      this.cs.a();
      this.cs.a(pathpoint2);
      PathPoint pathpoint3 = pathpoint2;
      byte b0 = 0;
      if (this.cm == null || this.cm.c() == 0) {
         b0 = 12;
      }

      while(!this.cs.e()) {
         PathPoint pathpoint4 = this.cs.c();
         if (pathpoint4.equals(pathpoint1)) {
            if (pathpoint != null) {
               pathpoint.h = pathpoint1;
               pathpoint1 = pathpoint;
            }

            return this.a(pathpoint2, pathpoint1);
         }

         if (pathpoint4.a(pathpoint1) < pathpoint3.a(pathpoint1)) {
            pathpoint3 = pathpoint4;
         }

         pathpoint4.i = true;
         int l = 0;

         for(int i1 = 0; i1 < 24; ++i1) {
            if (this.cq[i1] == pathpoint4) {
               l = i1;
               break;
            }
         }

         for(int var15 = b0; var15 < 24; ++var15) {
            if ((this.cr[l] & 1 << var15) > 0) {
               PathPoint pathpoint5 = this.cq[var15];
               if (!pathpoint5.i) {
                  float f = pathpoint4.e + pathpoint4.a(pathpoint5);
                  if (!pathpoint5.c() || f < pathpoint5.e) {
                     pathpoint5.h = pathpoint4;
                     pathpoint5.e = f;
                     pathpoint5.f = pathpoint5.a(pathpoint1);
                     if (pathpoint5.c()) {
                        this.cs.a(pathpoint5, pathpoint5.e + pathpoint5.f);
                     } else {
                        pathpoint5.g = pathpoint5.e + pathpoint5.f;
                        this.cs.a(pathpoint5);
                     }
                  }
               }
            }
         }
      }

      if (pathpoint3 == pathpoint2) {
         return null;
      } else {
         bX.debug("Failed to find path from {} to {}", i, j);
         if (pathpoint != null) {
            pathpoint.h = pathpoint3;
            pathpoint3 = pathpoint;
         }

         return this.a(pathpoint2, pathpoint3);
      }
   }

   private PathEntity a(PathPoint pathpoint, PathPoint pathpoint1) {
      List<PathPoint> list = Lists.newArrayList();
      PathPoint pathpoint2 = pathpoint1;
      list.add(0, pathpoint1);

      while(pathpoint2.h != null) {
         pathpoint2 = pathpoint2.h;
         list.add(0, pathpoint2);
      }

      return new PathEntity(list, new BlockPosition(pathpoint1.a, pathpoint1.b, pathpoint1.c), true);
   }

   @Override
   public void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);
      nbttagcompound.a("DragonPhase", this.cn.a().i().b());
      nbttagcompound.a("DragonDeathTime", this.bU);
      nbttagcompound.a("Bukkit.expToDrop", this.expToDrop);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      super.a(nbttagcompound);
      if (nbttagcompound.e("DragonPhase")) {
         this.cn.a(DragonControllerPhase.a(nbttagcompound.h("DragonPhase")));
      }

      if (nbttagcompound.e("DragonDeathTime")) {
         this.bU = nbttagcompound.h("DragonDeathTime");
      }

      if (nbttagcompound.e("Bukkit.expToDrop")) {
         this.expToDrop = nbttagcompound.h("Bukkit.expToDrop");
      }
   }

   @Override
   public void ds() {
   }

   public EntityComplexPart[] w() {
      return this.ce;
   }

   @Override
   public boolean bm() {
      return false;
   }

   @Override
   public SoundCategory cX() {
      return SoundCategory.f;
   }

   @Override
   protected SoundEffect s() {
      return SoundEffects.gP;
   }

   @Override
   protected SoundEffect d(DamageSource damagesource) {
      return SoundEffects.gU;
   }

   @Override
   protected float eN() {
      return 5.0F;
   }

   public float a(int i, double[] adouble, double[] adouble1) {
      IDragonController idragoncontroller = this.cn.a();
      DragonControllerPhase<? extends IDragonController> dragoncontrollerphase = idragoncontroller.i();
      double d0;
      if (dragoncontrollerphase == DragonControllerPhase.d || dragoncontrollerphase == DragonControllerPhase.e) {
         BlockPosition blockposition = this.H.a(HeightMap.Type.f, WorldGenEndTrophy.e);
         double d1 = Math.max(Math.sqrt(blockposition.b(this.de())) / 4.0, 1.0);
         d0 = (double)i / d1;
      } else if (idragoncontroller.a()) {
         d0 = (double)i;
      } else if (i == 6) {
         d0 = 0.0;
      } else {
         d0 = adouble1[1] - adouble[1];
      }

      return (float)d0;
   }

   public Vec3D B(float f) {
      IDragonController idragoncontroller = this.cn.a();
      DragonControllerPhase<? extends IDragonController> dragoncontrollerphase = idragoncontroller.i();
      Vec3D vec3d;
      if (dragoncontrollerphase == DragonControllerPhase.d || dragoncontrollerphase == DragonControllerPhase.e) {
         BlockPosition blockposition = this.H.a(HeightMap.Type.f, WorldGenEndTrophy.e);
         float f1 = Math.max((float)Math.sqrt(blockposition.b(this.de())) / 4.0F, 1.0F);
         float f3 = 6.0F / f1;
         float f4 = this.dy();
         float f5 = 1.5F;
         this.e(-f3 * 1.5F * 5.0F);
         vec3d = this.j(f);
         this.e(f4);
      } else if (idragoncontroller.a()) {
         float f2 = this.dy();
         float f1 = 1.5F;
         this.e(-45.0F);
         vec3d = this.j(f);
         this.e(f2);
      } else {
         vec3d = this.j(f);
      }

      return vec3d;
   }

   public void a(EntityEnderCrystal entityendercrystal, BlockPosition blockposition, DamageSource damagesource) {
      EntityHuman entityhuman;
      if (damagesource.d() instanceof EntityHuman) {
         entityhuman = (EntityHuman)damagesource.d();
      } else {
         entityhuman = this.H.a(bY, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
      }

      if (entityendercrystal == this.bW) {
         this.a(this.e, this.dG().d(entityendercrystal, entityhuman), 10.0F);
      }

      this.cn.a().a(entityendercrystal, blockposition, damagesource, entityhuman);
   }

   @Override
   public void a(DataWatcherObject<?> datawatcherobject) {
      if (b.equals(datawatcherobject) && this.H.B) {
         this.cn.a(DragonControllerPhase.a(this.aj().a(b)));
      }

      super.a(datawatcherobject);
   }

   public DragonControllerManager fP() {
      return this.cn;
   }

   @Nullable
   public EnderDragonBattle fQ() {
      return this.cm;
   }

   @Override
   public boolean b(MobEffect mobeffect, @Nullable Entity entity) {
      return false;
   }

   @Override
   protected boolean l(Entity entity) {
      return false;
   }

   @Override
   public boolean co() {
      return false;
   }

   @Override
   public void a(PacketPlayOutSpawnEntity packetplayoutspawnentity) {
      super.a(packetplayoutspawnentity);
      EntityComplexPart[] aentitycomplexpart = this.w();

      for(int i = 0; i < aentitycomplexpart.length; ++i) {
         aentitycomplexpart[i].e(i + packetplayoutspawnentity.a());
      }
   }

   @Override
   public boolean c(EntityLiving entityliving) {
      return entityliving.eh();
   }

   @Override
   public double bv() {
      return (double)this.cg.dd();
   }
}
