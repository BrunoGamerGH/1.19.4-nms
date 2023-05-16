package net.minecraft.world.level;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentProtection;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class Explosion {
   private static final ExplosionDamageCalculator a = new ExplosionDamageCalculator();
   private static final int b = 16;
   private final boolean c;
   private final Explosion.Effect d;
   private final RandomSource e;
   private final World f;
   private final double g;
   private final double h;
   private final double i;
   @Nullable
   public final Entity j;
   private final float k;
   private final DamageSource l;
   private final ExplosionDamageCalculator m;
   private final ObjectArrayList<BlockPosition> n;
   private final Map<EntityHuman, Vec3D> o;
   public boolean wasCanceled = false;

   public Explosion(World world, @Nullable Entity entity, double d0, double d1, double d2, float f, List<BlockPosition> list) {
      this(world, entity, d0, d1, d2, f, false, Explosion.Effect.c, list);
   }

   public Explosion(
      World world,
      @Nullable Entity entity,
      double d0,
      double d1,
      double d2,
      float f,
      boolean flag,
      Explosion.Effect explosion_effect,
      List<BlockPosition> list
   ) {
      this(world, entity, d0, d1, d2, f, flag, explosion_effect);
      this.n.addAll(list);
   }

   public Explosion(World world, @Nullable Entity entity, double d0, double d1, double d2, float f, boolean flag, Explosion.Effect explosion_effect) {
      this(world, entity, null, null, d0, d1, d2, f, flag, explosion_effect);
   }

   public Explosion(
      World world,
      @Nullable Entity entity,
      @Nullable DamageSource damagesource,
      @Nullable ExplosionDamageCalculator explosiondamagecalculator,
      double d0,
      double d1,
      double d2,
      float f,
      boolean flag,
      Explosion.Effect explosion_effect
   ) {
      this.e = RandomSource.a();
      this.n = new ObjectArrayList();
      this.o = Maps.newHashMap();
      this.f = world;
      this.j = entity;
      this.k = (float)Math.max((double)f, 0.0);
      this.g = d0;
      this.h = d1;
      this.i = d2;
      this.c = flag;
      this.d = explosion_effect;
      this.l = damagesource == null ? world.af().a(this) : damagesource;
      this.m = explosiondamagecalculator == null ? this.a(entity) : explosiondamagecalculator;
   }

   private ExplosionDamageCalculator a(@Nullable Entity entity) {
      return (ExplosionDamageCalculator)(entity == null ? a : new ExplosionDamageCalculatorEntity(entity));
   }

   public static float a(Vec3D vec3d, Entity entity) {
      AxisAlignedBB axisalignedbb = entity.cD();
      double d0 = 1.0 / ((axisalignedbb.d - axisalignedbb.a) * 2.0 + 1.0);
      double d1 = 1.0 / ((axisalignedbb.e - axisalignedbb.b) * 2.0 + 1.0);
      double d2 = 1.0 / ((axisalignedbb.f - axisalignedbb.c) * 2.0 + 1.0);
      double d3 = (1.0 - Math.floor(1.0 / d0) * d0) / 2.0;
      double d4 = (1.0 - Math.floor(1.0 / d2) * d2) / 2.0;
      if (d0 >= 0.0 && d1 >= 0.0 && d2 >= 0.0) {
         int i = 0;
         int j = 0;

         for(double d5 = 0.0; d5 <= 1.0; d5 += d0) {
            for(double d6 = 0.0; d6 <= 1.0; d6 += d1) {
               for(double d7 = 0.0; d7 <= 1.0; d7 += d2) {
                  double d8 = MathHelper.d(d5, axisalignedbb.a, axisalignedbb.d);
                  double d9 = MathHelper.d(d6, axisalignedbb.b, axisalignedbb.e);
                  double d10 = MathHelper.d(d7, axisalignedbb.c, axisalignedbb.f);
                  Vec3D vec3d1 = new Vec3D(d8 + d3, d9, d10 + d4);
                  if (entity.H.a(new RayTrace(vec3d1, vec3d, RayTrace.BlockCollisionOption.a, RayTrace.FluidCollisionOption.a, entity)).c()
                     == MovingObjectPosition.EnumMovingObjectType.a) {
                     ++i;
                  }

                  ++j;
               }
            }
         }

         return (float)i / (float)j;
      } else {
         return 0.0F;
      }
   }

   public void a() {
      if (!(this.k < 0.1F)) {
         this.f.a(this.j, GameEvent.y, new Vec3D(this.g, this.h, this.i));
         Set<BlockPosition> set = Sets.newHashSet();
         boolean flag = true;

         for(int k = 0; k < 16; ++k) {
            for(int i = 0; i < 16; ++i) {
               for(int j = 0; j < 16; ++j) {
                  if (k == 0 || k == 15 || i == 0 || i == 15 || j == 0 || j == 15) {
                     double d0 = (double)((float)k / 15.0F * 2.0F - 1.0F);
                     double d1 = (double)((float)i / 15.0F * 2.0F - 1.0F);
                     double d2 = (double)((float)j / 15.0F * 2.0F - 1.0F);
                     double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                     d0 /= d3;
                     d1 /= d3;
                     d2 /= d3;
                     float f = this.k * (0.7F + this.f.z.i() * 0.6F);
                     double d4 = this.g;
                     double d5 = this.h;
                     double d6 = this.i;

                     for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                        BlockPosition blockposition = BlockPosition.a(d4, d5, d6);
                        IBlockData iblockdata = this.f.a_(blockposition);
                        Fluid fluid = this.f.b_(blockposition);
                        if (!this.f.j(blockposition)) {
                           break;
                        }

                        Optional<Float> optional = this.m.a(this, this.f, blockposition, iblockdata, fluid);
                        if (optional.isPresent()) {
                           f -= (optional.get() + 0.3F) * 0.3F;
                        }

                        if (f > 0.0F && this.m.a(this, this.f, blockposition, iblockdata, f)) {
                           set.add(blockposition);
                        }

                        d4 += d0 * 0.3F;
                        d5 += d1 * 0.3F;
                        d6 += d2 * 0.3F;
                     }
                  }
               }
            }
         }

         this.n.addAll(set);
         float f2 = this.k * 2.0F;
         int i = MathHelper.a(this.g - (double)f2 - 1.0);
         int j = MathHelper.a(this.g + (double)f2 + 1.0);
         int l = MathHelper.a(this.h - (double)f2 - 1.0);
         int i1 = MathHelper.a(this.h + (double)f2 + 1.0);
         int j1 = MathHelper.a(this.i - (double)f2 - 1.0);
         int k1 = MathHelper.a(this.i + (double)f2 + 1.0);
         List<Entity> list = this.f.a_(this.j, new AxisAlignedBB((double)i, (double)l, (double)j1, (double)j, (double)i1, (double)k1));
         Vec3D vec3d = new Vec3D(this.g, this.h, this.i);

         for(int l1 = 0; l1 < list.size(); ++l1) {
            Entity entity = list.get(l1);
            if (!entity.cI()) {
               double d7 = Math.sqrt(entity.e(vec3d)) / (double)f2;
               if (d7 <= 1.0) {
                  double d8 = entity.dl() - this.g;
                  double d9 = (entity instanceof EntityTNTPrimed ? entity.dn() : entity.dp()) - this.h;
                  double d10 = entity.dr() - this.i;
                  double d11 = Math.sqrt(d8 * d8 + d9 * d9 + d10 * d10);
                  if (d11 != 0.0) {
                     d8 /= d11;
                     d9 /= d11;
                     d10 /= d11;
                     double d12 = (double)a(vec3d, entity);
                     double d13 = (1.0 - d7) * d12;
                     if (!(entity instanceof EntityComplexPart)) {
                        CraftEventFactory.entityDamage = this.j;
                        entity.lastDamageCancelled = false;
                        if (entity instanceof EntityEnderDragon) {
                           for(EntityComplexPart entityComplexPart : ((EntityEnderDragon)entity).ce) {
                              double d7part;
                              if (list.contains(entityComplexPart) && (d7part = Math.sqrt(entityComplexPart.e(vec3d)) / (double)f2) <= 1.0) {
                                 double d13part = (1.0 - d7part) * (double)a(vec3d, entityComplexPart);
                                 entityComplexPart.a(this.c(), (float)((int)((d13part * d13part + d13part) / 2.0 * 7.0 * (double)f2 + 1.0)));
                              }
                           }
                        } else {
                           entity.a(this.c(), (float)((int)((d13 * d13 + d13) / 2.0 * 7.0 * (double)f2 + 1.0)));
                        }

                        CraftEventFactory.entityDamage = null;
                        if (!entity.lastDamageCancelled) {
                           double d14;
                           if (entity instanceof EntityLiving entityliving) {
                              d14 = EnchantmentProtection.a(entityliving, d13);
                           } else {
                              d14 = d13;
                           }

                           d8 *= d14;
                           d9 *= d14;
                           d10 *= d14;
                           Vec3D vec3d1 = new Vec3D(d8, d9, d10);
                           entity.f(entity.dj().e(vec3d1));
                           if (entity instanceof EntityHuman entityhuman && !entityhuman.F_() && (!entityhuman.f() || !entityhuman.fK().b)) {
                              this.o.put(entityhuman, vec3d1);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void a(boolean flag) {
      if (this.f.B) {
         this.f.a(this.g, this.h, this.i, SoundEffects.iO, SoundCategory.e, 4.0F, (1.0F + (this.f.z.i() - this.f.z.i()) * 0.2F) * 0.7F, false);
      }

      boolean flag1 = this.b();
      if (flag) {
         if (this.k >= 2.0F && flag1) {
            this.f.a(Particles.w, this.g, this.h, this.i, 1.0, 0.0, 0.0);
         } else {
            this.f.a(Particles.x, this.g, this.h, this.i, 1.0, 0.0, 0.0);
         }
      }

      if (flag1) {
         ObjectArrayList<Pair<ItemStack, BlockPosition>> objectarraylist = new ObjectArrayList();
         boolean flag2 = this.e() instanceof EntityHuman;
         SystemUtils.b(this.n, this.f.z);
         ObjectListIterator objectlistiterator = this.n.iterator();
         org.bukkit.World bworld = this.f.getWorld();
         org.bukkit.entity.Entity explode = this.j == null ? null : this.j.getBukkitEntity();
         Location location = new Location(bworld, this.g, this.h, this.i);
         List<Block> blockList = new ObjectArrayList();

         for(int i1 = this.n.size() - 1; i1 >= 0; --i1) {
            BlockPosition cpos = (BlockPosition)this.n.get(i1);
            Block bblock = bworld.getBlockAt(cpos.u(), cpos.v(), cpos.w());
            if (!bblock.getType().isAir()) {
               blockList.add(bblock);
            }
         }

         boolean cancelled;
         List<Block> bukkitBlocks;
         float yield;
         if (explode != null) {
            EntityExplodeEvent event = new EntityExplodeEvent(explode, location, blockList, this.d == Explosion.Effect.c ? 1.0F / this.k : 1.0F);
            this.f.getCraftServer().getPluginManager().callEvent(event);
            cancelled = event.isCancelled();
            bukkitBlocks = event.blockList();
            yield = event.getYield();
         } else {
            BlockExplodeEvent event = new BlockExplodeEvent(location.getBlock(), blockList, this.d == Explosion.Effect.c ? 1.0F / this.k : 1.0F);
            this.f.getCraftServer().getPluginManager().callEvent(event);
            cancelled = event.isCancelled();
            bukkitBlocks = event.blockList();
            yield = event.getYield();
         }

         this.n.clear();

         for(Block bblock : bukkitBlocks) {
            BlockPosition coords = new BlockPosition(bblock.getX(), bblock.getY(), bblock.getZ());
            this.n.add(coords);
         }

         if (cancelled) {
            this.wasCanceled = true;
            return;
         }

         objectlistiterator = this.n.iterator();

         while(objectlistiterator.hasNext()) {
            BlockPosition blockposition = (BlockPosition)objectlistiterator.next();
            IBlockData iblockdata = this.f.a_(blockposition);
            net.minecraft.world.level.block.Block block = iblockdata.b();
            if (!iblockdata.h()) {
               BlockPosition blockposition1 = blockposition.i();
               this.f.ac().a("explosion_blocks");
               if (block.a(this)) {
                  World world = this.f;
                  if (world instanceof WorldServer worldserver) {
                     TileEntity tileentity = iblockdata.q() ? this.f.c_(blockposition) : null;
                     LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(worldserver)
                        .a(this.f.z)
                        .a(LootContextParameters.f, Vec3D.b(blockposition))
                        .a(LootContextParameters.i, ItemStack.b)
                        .b(LootContextParameters.h, tileentity)
                        .b(LootContextParameters.a, this.j);
                     if (yield < 1.0F) {
                        loottableinfo_builder.a(LootContextParameters.j, 1.0F / yield);
                     }

                     iblockdata.a(worldserver, blockposition, ItemStack.b, flag2);
                     iblockdata.a(loottableinfo_builder).forEach(itemstack -> a(objectarraylist, itemstack, blockposition1));
                  }
               }

               this.f.a(blockposition, Blocks.a.o(), 3);
               block.a(this.f, blockposition, this);
               this.f.ac().c();
            }
         }

         objectlistiterator = objectarraylist.iterator();

         while(objectlistiterator.hasNext()) {
            Pair<ItemStack, BlockPosition> pair = (Pair)objectlistiterator.next();
            net.minecraft.world.level.block.Block.a(this.f, (BlockPosition)pair.getSecond(), (ItemStack)pair.getFirst());
         }
      }

      if (this.c) {
         ObjectListIterator objectlistiterator1 = this.n.iterator();

         while(objectlistiterator1.hasNext()) {
            BlockPosition blockposition2 = (BlockPosition)objectlistiterator1.next();
            if (this.e.a(3) == 0
               && this.f.a_(blockposition2).h()
               && this.f.a_(blockposition2.d()).i(this.f, blockposition2.d())
               && !CraftEventFactory.callBlockIgniteEvent(this.f, blockposition2.u(), blockposition2.v(), blockposition2.w(), this).isCancelled()) {
               this.f.b(blockposition2, BlockFireAbstract.a(this.f, blockposition2));
            }
         }
      }
   }

   public boolean b() {
      return this.d != Explosion.Effect.a;
   }

   private static void a(ObjectArrayList<Pair<ItemStack, BlockPosition>> objectarraylist, ItemStack itemstack, BlockPosition blockposition) {
      if (!itemstack.b()) {
         int i = objectarraylist.size();

         for(int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPosition> pair = (Pair)objectarraylist.get(j);
            ItemStack itemstack1 = (ItemStack)pair.getFirst();
            if (EntityItem.a(itemstack1, itemstack)) {
               ItemStack itemstack2 = EntityItem.a(itemstack1, itemstack, 16);
               objectarraylist.set(j, Pair.of(itemstack2, (BlockPosition)pair.getSecond()));
               if (itemstack.b()) {
                  return;
               }
            }
         }

         objectarraylist.add(Pair.of(itemstack, blockposition));
      }
   }

   public DamageSource c() {
      return this.l;
   }

   public Map<EntityHuman, Vec3D> d() {
      return this.o;
   }

   @Nullable
   public EntityLiving e() {
      if (this.j == null) {
         return null;
      } else {
         Entity entity = this.j;
         if (entity instanceof EntityTNTPrimed entitytntprimed) {
            return entitytntprimed.i();
         } else {
            entity = this.j;
            if (entity instanceof EntityLiving entityliving) {
               return entityliving;
            } else {
               entity = this.j;
               if (entity instanceof IProjectile iprojectile) {
                  entity = iprojectile.v();
                  if (entity instanceof EntityLiving) {
                     return (EntityLiving)entity;
                  }
               }

               return null;
            }
         }
      }
   }

   @Nullable
   public Entity f() {
      return this.j;
   }

   public void g() {
      this.n.clear();
   }

   public List<BlockPosition> h() {
      return this.n;
   }

   public static enum Effect {
      a,
      b,
      c;
   }
}
