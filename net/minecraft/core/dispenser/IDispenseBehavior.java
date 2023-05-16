package net.minecraft.core.dispenser;

import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.IPosition;
import net.minecraft.core.ISourceBlock;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.ISaddleable;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemBoneMeal;
import net.minecraft.world.item.ItemBucket;
import net.minecraft.world.item.ItemMonsterEgg;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockBeehive;
import net.minecraft.world.level.block.BlockCampfire;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.BlockFireAbstract;
import net.minecraft.world.level.block.BlockPumpkinCarved;
import net.minecraft.world.level.block.BlockRespawnAnchor;
import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.BlockSkull;
import net.minecraft.world.level.block.BlockTNT;
import net.minecraft.world.level.block.BlockWitherSkull;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.IFluidContainer;
import net.minecraft.world.level.block.IFluidSource;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.craftbukkit.v1_19_R3.util.DummyGeneratorAccess;
import org.bukkit.event.block.BlockDispenseArmorEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.util.Vector;
import org.slf4j.Logger;

public interface IDispenseBehavior {
   Logger a = LogUtils.getLogger();
   IDispenseBehavior b = (isourceblock, itemstack) -> itemstack;

   ItemStack dispense(ISourceBlock var1, ItemStack var2);

   static void c() {
      BlockDispenser.a(Items.nD, new DispenseBehaviorProjectile() {
         @Override
         protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
            EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, iposition.a(), iposition.b(), iposition.c());
            entitytippedarrow.d = EntityArrow.PickupStatus.b;
            return entitytippedarrow;
         }
      });
      BlockDispenser.a(Items.ur, new DispenseBehaviorProjectile() {
         @Override
         protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
            EntityTippedArrow entitytippedarrow = new EntityTippedArrow(world, iposition.a(), iposition.b(), iposition.c());
            entitytippedarrow.a(itemstack);
            entitytippedarrow.d = EntityArrow.PickupStatus.b;
            return entitytippedarrow;
         }
      });
      BlockDispenser.a(Items.uq, new DispenseBehaviorProjectile() {
         @Override
         protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
            EntitySpectralArrow entityspectralarrow = new EntitySpectralArrow(world, iposition.a(), iposition.b(), iposition.c());
            entityspectralarrow.d = EntityArrow.PickupStatus.b;
            return entityspectralarrow;
         }
      });
      BlockDispenser.a(Items.pZ, new DispenseBehaviorProjectile() {
         @Override
         protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
            return SystemUtils.a(new EntityEgg(world, iposition.a(), iposition.b(), iposition.c()), entityegg -> entityegg.a(itemstack));
         }
      });
      BlockDispenser.a(Items.pK, new DispenseBehaviorProjectile() {
         @Override
         protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
            return SystemUtils.a(new EntitySnowball(world, iposition.a(), iposition.b(), iposition.c()), entitysnowball -> entitysnowball.a(itemstack));
         }
      });
      BlockDispenser.a(
         Items.ta,
         new DispenseBehaviorProjectile() {
            @Override
            protected IProjectile a(World world, IPosition iposition, ItemStack itemstack) {
               return SystemUtils.a(
                  new EntityThrownExpBottle(world, iposition.a(), iposition.b(), iposition.c()), entitythrownexpbottle -> entitythrownexpbottle.a(itemstack)
               );
            }
   
            @Override
            protected float a() {
               return super.a() * 0.5F;
            }
   
            @Override
            protected float b() {
               return super.b() * 1.25F;
            }
         }
      );
      BlockDispenser.a(Items.up, new IDispenseBehavior() {
         @Override
         public ItemStack dispense(ISourceBlock isourceblock, ItemStack itemstack) {
            return (new DispenseBehaviorProjectile() {
               @Override
               protected IProjectile a(World world, IPosition iposition, ItemStack itemstack1) {
                  return SystemUtils.a(new EntityPotion(world, iposition.a(), iposition.b(), iposition.c()), entitypotion -> entitypotion.a(itemstack1));
               }

               @Override
               protected float a() {
                  return super.a() * 0.5F;
               }

               @Override
               protected float b() {
                  return super.b() * 1.25F;
               }
            }).dispense(isourceblock, itemstack);
         }
      });
      BlockDispenser.a(Items.us, new IDispenseBehavior() {
         @Override
         public ItemStack dispense(ISourceBlock isourceblock, ItemStack itemstack) {
            return (new DispenseBehaviorProjectile() {
               @Override
               protected IProjectile a(World world, IPosition iposition, ItemStack itemstack1) {
                  return SystemUtils.a(new EntityPotion(world, iposition.a(), iposition.b(), iposition.c()), entitypotion -> entitypotion.a(itemstack1));
               }

               @Override
               protected float a() {
                  return super.a() * 0.5F;
               }

               @Override
               protected float b() {
                  return super.b() * 1.25F;
               }
            }).dispense(isourceblock, itemstack);
         }
      });
      DispenseBehaviorItem dispensebehavioritem = new DispenseBehaviorItem() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
            EntityTypes entitytypes = ((ItemMonsterEgg)itemstack.c()).a(itemstack.u());
            WorldServer worldserver = isourceblock.g();
            ItemStack itemstack1 = itemstack.a(1);
            Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
            BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
            if (!BlockDispenser.eventFired) {
               worldserver.getCraftServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
               itemstack.g(1);
               return itemstack;
            } else {
               if (!event.getItem().equals(craftItem)) {
                  itemstack.g(1);
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               try {
                  entitytypes.a(isourceblock.g(), itemstack, null, isourceblock.d().a(enumdirection), EnumMobSpawn.o, enumdirection != EnumDirection.b, false);
               } catch (Exception var12) {
                  a.error("Error while dispensing spawn egg from dispenser at {}", isourceblock.d(), var12);
                  return ItemStack.b;
               }

               isourceblock.g().a(null, GameEvent.u, isourceblock.d());
               return itemstack;
            }
         }
      };

      for(ItemMonsterEgg itemmonsteregg : ItemMonsterEgg.h()) {
         BlockDispenser.a(itemmonsteregg, dispensebehavioritem);
      }

      BlockDispenser.a(Items.tH, new DispenseBehaviorItem() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
            BlockPosition blockposition = isourceblock.d().a(enumdirection);
            WorldServer worldserver = isourceblock.g();
            ItemStack itemstack1 = itemstack.a(1);
            Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
            BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
            if (!BlockDispenser.eventFired) {
               worldserver.getCraftServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
               itemstack.g(1);
               return itemstack;
            } else {
               if (!event.getItem().equals(craftItem)) {
                  itemstack.g(1);
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               Consumer<EntityArmorStand> consumer = EntityTypes.a(entityarmorstandx -> entityarmorstandx.f(enumdirection.p()), worldserver, itemstack, null);
               EntityArmorStand entityarmorstand = EntityTypes.d.a(worldserver, itemstack.u(), consumer, blockposition, EnumMobSpawn.o, false, false);
               return itemstack;
            }
         }
      });
      BlockDispenser.a(Items.mV, new DispenseBehaviorMaybe() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
            List<EntityLiving> list = isourceblock.g().a(EntityLiving.class, new AxisAlignedBB(blockposition), entityliving -> {
               if (!(entityliving instanceof ISaddleable)) {
                  return false;
               } else {
                  ISaddleable isaddleable = (ISaddleable)entityliving;
                  return !isaddleable.i() && isaddleable.g();
               }
            });
            if (!list.isEmpty()) {
               ItemStack itemstack1 = itemstack.a(1);
               World world = isourceblock.g();
               Block block = world.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
               BlockDispenseArmorEvent event = new BlockDispenseArmorEvent(block, craftItem.clone(), (CraftLivingEntity)list.get(0).getBukkitEntity());
               if (!BlockDispenser.eventFired) {
                  world.getCraftServer().getPluginManager().callEvent(event);
               }

               if (event.isCancelled()) {
                  itemstack.g(1);
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     itemstack.g(1);
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != ItemArmor.a) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }

                  ((ISaddleable)list.get(0)).a(SoundCategory.e);
                  this.a(true);
                  return itemstack;
               }
            } else {
               return super.a(isourceblock, itemstack);
            }
         }
      });
      DispenseBehaviorMaybe dispensebehaviormaybe = new DispenseBehaviorMaybe() {
         @Override
         protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));

            for(EntityHorseAbstract entityhorseabstract : isourceblock.g()
               .a(EntityHorseAbstract.class, new AxisAlignedBB(blockposition), entityhorseabstractx -> entityhorseabstractx.bq() && entityhorseabstractx.gB())) {
               if (entityhorseabstract.l(itemstack) && !entityhorseabstract.gC() && entityhorseabstract.gh()) {
                  ItemStack itemstack1 = itemstack.a(1);
                  World world = isourceblock.g();
                  Block block = world.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
                  CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
                  BlockDispenseArmorEvent event = new BlockDispenseArmorEvent(
                     block, craftItem.clone(), (CraftLivingEntity)entityhorseabstract.getBukkitEntity()
                  );
                  if (!BlockDispenser.eventFired) {
                     world.getCraftServer().getPluginManager().callEvent(event);
                  }

                  if (event.isCancelled()) {
                     itemstack.g(1);
                     return itemstack;
                  }

                  if (!event.getItem().equals(craftItem)) {
                     itemstack.g(1);
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != ItemArmor.a) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }

                  entityhorseabstract.a_(401).a(CraftItemStack.asNMSCopy(event.getItem()));
                  this.a(true);
                  return itemstack;
               }
            }

            return super.a(isourceblock, itemstack);
         }
      };
      BlockDispenser.a(Items.tL, dispensebehaviormaybe);
      BlockDispenser.a(Items.tI, dispensebehaviormaybe);
      BlockDispenser.a(Items.tJ, dispensebehaviormaybe);
      BlockDispenser.a(Items.tK, dispensebehaviormaybe);
      BlockDispenser.a(Items.hg, dispensebehaviormaybe);
      BlockDispenser.a(Items.hh, dispensebehaviormaybe);
      BlockDispenser.a(Items.hp, dispensebehaviormaybe);
      BlockDispenser.a(Items.hr, dispensebehaviormaybe);
      BlockDispenser.a(Items.hs, dispensebehaviormaybe);
      BlockDispenser.a(Items.hv, dispensebehaviormaybe);
      BlockDispenser.a(Items.hn, dispensebehaviormaybe);
      BlockDispenser.a(Items.ht, dispensebehaviormaybe);
      BlockDispenser.a(Items.hj, dispensebehaviormaybe);
      BlockDispenser.a(Items.ho, dispensebehaviormaybe);
      BlockDispenser.a(Items.hl, dispensebehaviormaybe);
      BlockDispenser.a(Items.hi, dispensebehaviormaybe);
      BlockDispenser.a(Items.hm, dispensebehaviormaybe);
      BlockDispenser.a(Items.hq, dispensebehaviormaybe);
      BlockDispenser.a(Items.hu, dispensebehaviormaybe);
      BlockDispenser.a(Items.hk, dispensebehaviormaybe);
      BlockDispenser.a(
         Items.ep,
         new DispenseBehaviorMaybe() {
            @Override
            public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
   
               for(EntityHorseChestedAbstract entityhorsechestedabstract : isourceblock.g()
                  .a(
                     EntityHorseChestedAbstract.class,
                     new AxisAlignedBB(blockposition),
                     entityhorsechestedabstractx -> entityhorsechestedabstractx.bq() && !entityhorsechestedabstractx.r()
                  )) {
                  if (entityhorsechestedabstract.gh()) {
                     ItemStack itemstack1 = itemstack.a(1);
                     World world = isourceblock.g();
                     Block block = world.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
                     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
                     BlockDispenseArmorEvent event = new BlockDispenseArmorEvent(
                        block, craftItem.clone(), (CraftLivingEntity)entityhorsechestedabstract.getBukkitEntity()
                     );
                     if (!BlockDispenser.eventFired) {
                        world.getCraftServer().getPluginManager().callEvent(event);
                     }
   
                     if (event.isCancelled()) {
                        return itemstack;
                     }
   
                     if (!event.getItem().equals(craftItem)) {
                        ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                        IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                        if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != ItemArmor.a) {
                           idispensebehavior.dispense(isourceblock, eventStack);
                           return itemstack;
                        }
                     }
   
                     entityhorsechestedabstract.a_(499).a(CraftItemStack.asNMSCopy(event.getItem()));
                     this.a(true);
                     return itemstack;
                  }
               }
   
               return super.a(isourceblock, itemstack);
            }
         }
      );
      BlockDispenser.a(Items.tw, new DispenseBehaviorItem() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
            WorldServer worldserver = isourceblock.g();
            ItemStack itemstack1 = itemstack.a(1);
            Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
            BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(enumdirection.j(), enumdirection.k(), enumdirection.l()));
            if (!BlockDispenser.eventFired) {
               worldserver.getCraftServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
               itemstack.g(1);
               return itemstack;
            } else {
               if (!event.getItem().equals(craftItem)) {
                  itemstack.g(1);
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
               EntityFireworks entityfireworks = new EntityFireworks(isourceblock.g(), itemstack, isourceblock.a(), isourceblock.b(), isourceblock.a(), true);
               IDispenseBehavior.a(isourceblock, entityfireworks, enumdirection);
               entityfireworks.c((double)enumdirection.j(), (double)enumdirection.k(), (double)enumdirection.l(), 0.5F, 1.0F);
               isourceblock.g().b(entityfireworks);
               return itemstack;
            }
         }

         @Override
         protected void a(ISourceBlock isourceblock) {
            isourceblock.g().c(1004, isourceblock.d(), 0);
         }
      });
      BlockDispenser.a(
         Items.tb,
         new DispenseBehaviorItem() {
            @Override
            public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
               IPosition iposition = BlockDispenser.a(isourceblock);
               double d0 = iposition.a() + (double)((float)enumdirection.j() * 0.3F);
               double d1 = iposition.b() + (double)((float)enumdirection.k() * 0.3F);
               double d2 = iposition.c() + (double)((float)enumdirection.l() * 0.3F);
               WorldServer worldserver = isourceblock.g();
               RandomSource randomsource = worldserver.z;
               double d3 = randomsource.a((double)enumdirection.j(), 0.11485000000000001);
               double d4 = randomsource.a((double)enumdirection.k(), 0.11485000000000001);
               double d5 = randomsource.a((double)enumdirection.l(), 0.11485000000000001);
               ItemStack itemstack1 = itemstack.a(1);
               Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
               BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(d3, d4, d5));
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }
   
               if (event.isCancelled()) {
                  itemstack.g(1);
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     itemstack.g(1);
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }
   
                  EntitySmallFireball entitysmallfireball = new EntitySmallFireball(
                     worldserver, d0, d1, d2, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ()
                  );
                  entitysmallfireball.a(itemstack1);
                  entitysmallfireball.projectileSource = new CraftBlockProjectileSource(isourceblock.f());
                  worldserver.b(entitysmallfireball);
                  return itemstack;
               }
            }
   
            @Override
            protected void a(ISourceBlock isourceblock) {
               isourceblock.g().c(1018, isourceblock.d(), 0);
            }
         }
      );
      BlockDispenser.a(Items.ne, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.a));
      BlockDispenser.a(Items.ng, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.b));
      BlockDispenser.a(Items.ni, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.c));
      BlockDispenser.a(Items.nk, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.d));
      BlockDispenser.a(Items.nq, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.g));
      BlockDispenser.a(Items.nm, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.e));
      BlockDispenser.a(Items.no, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.f));
      BlockDispenser.a(Items.ns, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.h));
      BlockDispenser.a(Items.nu, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.i));
      BlockDispenser.a(Items.nf, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.a, true));
      BlockDispenser.a(Items.nh, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.b, true));
      BlockDispenser.a(Items.nj, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.c, true));
      BlockDispenser.a(Items.nl, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.d, true));
      BlockDispenser.a(Items.nr, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.g, true));
      BlockDispenser.a(Items.nn, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.e, true));
      BlockDispenser.a(Items.np, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.f, true));
      BlockDispenser.a(Items.nt, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.h, true));
      BlockDispenser.a(Items.nv, new DispenseBehaviorBoat(EntityBoat.EnumBoatType.i, true));
      DispenseBehaviorItem dispensebehavioritem1 = new DispenseBehaviorItem() {
         private final DispenseBehaviorItem defaultDispenseItemBehavior = new DispenseBehaviorItem();

         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)itemstack.c();
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
            WorldServer worldserver = isourceblock.g();
            int x = blockposition.u();
            int y = blockposition.v();
            int z = blockposition.w();
            IBlockData iblockdata = worldserver.a_(blockposition);
            Material material = iblockdata.d();
            if (worldserver.w(blockposition)
               || !material.b()
               || material.e()
               || dispensiblecontaineritem instanceof ItemBucket
                  && iblockdata.b() instanceof IFluidContainer
                  && ((IFluidContainer)iblockdata.b()).a(worldserver, blockposition, iblockdata, ((ItemBucket)dispensiblecontaineritem).a)) {
               Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
               BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(x, y, z));
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }

               if (event.isCancelled()) {
                  return itemstack;
               }

               if (!event.getItem().equals(craftItem)) {
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               dispensiblecontaineritem = (DispensibleContainerItem)CraftItemStack.asNMSCopy(event.getItem()).c();
            }

            if (dispensiblecontaineritem.a(null, worldserver, blockposition, null)) {
               dispensiblecontaineritem.a(null, worldserver, itemstack, blockposition);
               Item item = Items.pG;
               itemstack.h(1);
               if (itemstack.b()) {
                  itemstack.setItem(Items.pG);
                  itemstack.f(1);
               } else if (isourceblock.<TileEntityDispenser>f().a(new ItemStack(item)) < 0) {
                  this.defaultDispenseItemBehavior.dispense(isourceblock, new ItemStack(item));
               }

               return itemstack;
            } else {
               return this.defaultDispenseItemBehavior.dispense(isourceblock, itemstack);
            }
         }
      };
      BlockDispenser.a(Items.pI, dispensebehavioritem1);
      BlockDispenser.a(Items.pH, dispensebehavioritem1);
      BlockDispenser.a(Items.pJ, dispensebehavioritem1);
      BlockDispenser.a(Items.pO, dispensebehavioritem1);
      BlockDispenser.a(Items.pP, dispensebehavioritem1);
      BlockDispenser.a(Items.pN, dispensebehavioritem1);
      BlockDispenser.a(Items.pQ, dispensebehavioritem1);
      BlockDispenser.a(Items.pR, dispensebehavioritem1);
      BlockDispenser.a(Items.pS, dispensebehavioritem1);
      BlockDispenser.a(
         Items.pG,
         new DispenseBehaviorItem() {
            private final DispenseBehaviorItem c = new DispenseBehaviorItem();
   
            @Override
            public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               WorldServer worldserver = isourceblock.g();
               BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
               IBlockData iblockdata = worldserver.a_(blockposition);
               net.minecraft.world.level.block.Block block = iblockdata.b();
               if (block instanceof IFluidSource) {
                  ItemStack itemstack1 = ((IFluidSource)block).c(DummyGeneratorAccess.INSTANCE, blockposition, iblockdata);
                  if (itemstack1.b()) {
                     return super.a(isourceblock, itemstack);
                  } else {
                     worldserver.a(null, GameEvent.A, blockposition);
                     Item item = itemstack1.c();
                     Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
                     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
                     BlockDispenseEvent event = new BlockDispenseEvent(
                        bukkitBlock, craftItem.clone(), new Vector(blockposition.u(), blockposition.v(), blockposition.w())
                     );
                     if (!BlockDispenser.eventFired) {
                        worldserver.getCraftServer().getPluginManager().callEvent(event);
                     }
   
                     if (event.isCancelled()) {
                        return itemstack;
                     } else {
                        if (!event.getItem().equals(craftItem)) {
                           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                           IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                           if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                              idispensebehavior.dispense(isourceblock, eventStack);
                              return itemstack;
                           }
                        }
   
                        itemstack1 = ((IFluidSource)block).c(worldserver, blockposition, iblockdata);
                        itemstack.h(1);
                        if (itemstack.b()) {
                           return new ItemStack(item);
                        } else {
                           if (isourceblock.<TileEntityDispenser>f().a(new ItemStack(item)) < 0) {
                              this.c.dispense(isourceblock, new ItemStack(item));
                           }
   
                           return itemstack;
                        }
                     }
                  }
               } else {
                  return super.a(isourceblock, itemstack);
               }
            }
         }
      );
      BlockDispenser.a(Items.nA, new DispenseBehaviorMaybe() {
         @Override
         protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            WorldServer worldserver = isourceblock.g();
            Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent event = new BlockDispenseEvent(bukkitBlock, craftItem.clone(), new Vector(0, 0, 0));
            if (!BlockDispenser.eventFired) {
               worldserver.getCraftServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
               return itemstack;
            } else {
               if (!event.getItem().equals(craftItem)) {
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               this.a(true);
               EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
               BlockPosition blockposition = isourceblock.d().a(enumdirection);
               IBlockData iblockdata = worldserver.a_(blockposition);
               if (BlockFireAbstract.a(worldserver, blockposition, enumdirection)) {
                  if (!CraftEventFactory.callBlockIgniteEvent(worldserver, blockposition, isourceblock.d()).isCancelled()) {
                     worldserver.b(blockposition, BlockFireAbstract.a(worldserver, blockposition));
                     worldserver.a(null, GameEvent.i, blockposition);
                  }
               } else if (BlockCampfire.h(iblockdata) || CandleBlock.g(iblockdata) || CandleCakeBlock.g(iblockdata)) {
                  worldserver.b(blockposition, iblockdata.a(BlockProperties.r, Boolean.valueOf(true)));
                  worldserver.a(null, GameEvent.c, blockposition);
               } else if (iblockdata.b() instanceof BlockTNT) {
                  BlockTNT.a(worldserver, blockposition);
                  worldserver.a(blockposition, false);
               } else {
                  this.a(false);
               }

               if (this.a() && itemstack.a(1, worldserver.z, null)) {
                  itemstack.f(0);
               }

               return itemstack;
            }
         }
      });
      BlockDispenser.a(Items.qG, new DispenseBehaviorMaybe() {
         @Override
         protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            this.a(true);
            WorldServer worldserver = isourceblock.g();
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
            Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
            CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(0, 0, 0));
            if (!BlockDispenser.eventFired) {
               worldserver.getCraftServer().getPluginManager().callEvent(event);
            }

            if (event.isCancelled()) {
               return itemstack;
            } else {
               if (!event.getItem().equals(craftItem)) {
                  ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                  IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                  if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                     idispensebehavior.dispense(isourceblock, eventStack);
                     return itemstack;
                  }
               }

               worldserver.captureTreeGeneration = true;
               if (!ItemBoneMeal.a(itemstack, worldserver, blockposition) && !ItemBoneMeal.a(itemstack, worldserver, blockposition, null)) {
                  this.a(false);
               } else if (!worldserver.B) {
                  worldserver.c(1505, blockposition, 0);
               }

               worldserver.captureTreeGeneration = false;
               if (worldserver.capturedBlockStates.size() > 0) {
                  TreeType treeType = BlockSapling.treeType;
                  BlockSapling.treeType = null;
                  Location location = new Location(worldserver.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
                  List<BlockState> blocks = new ArrayList<>(worldserver.capturedBlockStates.values());
                  worldserver.capturedBlockStates.clear();
                  StructureGrowEvent structureEvent = null;
                  if (treeType != null) {
                     structureEvent = new StructureGrowEvent(location, treeType, false, null, blocks);
                     Bukkit.getPluginManager().callEvent(structureEvent);
                  }

                  BlockFertilizeEvent fertilizeEvent = new BlockFertilizeEvent(location.getBlock(), null, blocks);
                  fertilizeEvent.setCancelled(structureEvent != null && structureEvent.isCancelled());
                  Bukkit.getPluginManager().callEvent(fertilizeEvent);
                  if (!fertilizeEvent.isCancelled()) {
                     for(BlockState blockstate : blocks) {
                        blockstate.update(true);
                     }
                  }
               }

               return itemstack;
            }
         }
      });
      BlockDispenser.a(
         Blocks.cj,
         new DispenseBehaviorItem() {
            @Override
            protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               WorldServer worldserver = isourceblock.g();
               BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
               ItemStack itemstack1 = itemstack.a(1);
               Block block = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
               BlockDispenseEvent event = new BlockDispenseEvent(
                  block, craftItem.clone(), new Vector((double)blockposition.u() + 0.5, (double)blockposition.v(), (double)blockposition.w() + 0.5)
               );
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }
   
               if (event.isCancelled()) {
                  itemstack.g(1);
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     itemstack.g(1);
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }
   
                  EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(
                     worldserver, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), null
                  );
                  worldserver.b(entitytntprimed);
                  worldserver.a(null, entitytntprimed.dl(), entitytntprimed.dn(), entitytntprimed.dr(), SoundEffects.xm, SoundCategory.e, 1.0F, 1.0F);
                  worldserver.a(null, GameEvent.u, blockposition);
                  return itemstack;
               }
            }
         }
      );
      DispenseBehaviorMaybe dispensebehaviormaybe1 = new DispenseBehaviorMaybe() {
         @Override
         protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            this.a(ItemArmor.a(isourceblock, itemstack));
            return itemstack;
         }
      };
      BlockDispenser.a(Items.tr, dispensebehaviormaybe1);
      BlockDispenser.a(Items.tq, dispensebehaviormaybe1);
      BlockDispenser.a(Items.ts, dispensebehaviormaybe1);
      BlockDispenser.a(Items.tn, dispensebehaviormaybe1);
      BlockDispenser.a(Items.tt, dispensebehaviormaybe1);
      BlockDispenser.a(Items.tp, dispensebehaviormaybe1);
      BlockDispenser.a(
         Items.to,
         new DispenseBehaviorMaybe() {
            @Override
            protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               WorldServer worldserver = isourceblock.g();
               EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
               BlockPosition blockposition = isourceblock.d().a(enumdirection);
               Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
               BlockDispenseEvent event = new BlockDispenseEvent(
                  bukkitBlock, craftItem.clone(), new Vector(blockposition.u(), blockposition.v(), blockposition.w())
               );
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }
   
               if (event.isCancelled()) {
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }
   
                  if (worldserver.w(blockposition) && BlockWitherSkull.b(worldserver, blockposition, itemstack)) {
                     worldserver.a(blockposition, Blocks.gF.o().a(BlockSkull.b, Integer.valueOf(RotationSegment.a(enumdirection))), 3);
                     worldserver.a(null, GameEvent.i, blockposition);
                     TileEntity tileentity = worldserver.c_(blockposition);
                     if (tileentity instanceof TileEntitySkull) {
                        BlockWitherSkull.a(worldserver, blockposition, (TileEntitySkull)tileentity);
                     }
   
                     itemstack.h(1);
                     this.a(true);
                  } else {
                     this.a(ItemArmor.a(isourceblock, itemstack));
                  }
   
                  return itemstack;
               }
            }
         }
      );
      BlockDispenser.a(
         Blocks.ee,
         new DispenseBehaviorMaybe() {
            @Override
            protected ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               WorldServer worldserver = isourceblock.g();
               BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
               BlockPumpkinCarved blockpumpkincarved = (BlockPumpkinCarved)Blocks.ee;
               Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
               BlockDispenseEvent event = new BlockDispenseEvent(
                  bukkitBlock, craftItem.clone(), new Vector(blockposition.u(), blockposition.v(), blockposition.w())
               );
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }
   
               if (event.isCancelled()) {
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }
   
                  if (worldserver.w(blockposition) && blockpumpkincarved.a(worldserver, blockposition)) {
                     if (!worldserver.B) {
                        worldserver.a(blockposition, blockpumpkincarved.o(), 3);
                        worldserver.a(null, GameEvent.i, blockposition);
                     }
   
                     itemstack.h(1);
                     this.a(true);
                  } else {
                     this.a(ItemArmor.a(isourceblock, itemstack));
                  }
   
                  return itemstack;
               }
            }
         }
      );
      BlockDispenser.a(Blocks.kM.k(), new DispenseBehaviorShulkerBox());

      for(EnumColor enumcolor : EnumColor.values()) {
         BlockDispenser.a(BlockShulkerBox.a(enumcolor).k(), new DispenseBehaviorShulkerBox());
      }

      BlockDispenser.a(
         Items.rs.k(),
         new DispenseBehaviorMaybe() {
            private final DispenseBehaviorItem defaultDispenseItemBehavior = new DispenseBehaviorItem();
   
            private ItemStack takeLiquid(ISourceBlock isourceblock, ItemStack itemstack, ItemStack itemstack1) {
               itemstack.h(1);
               if (itemstack.b()) {
                  isourceblock.g().a(null, GameEvent.A, isourceblock.d());
                  return itemstack1.o();
               } else {
                  if (isourceblock.<TileEntityDispenser>f().a(itemstack1.o()) < 0) {
                     this.defaultDispenseItemBehavior.dispense(isourceblock, itemstack1.o());
                  }
   
                  return itemstack;
               }
            }
   
            @Override
            public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               this.a(false);
               WorldServer worldserver = isourceblock.g();
               BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
               IBlockData iblockdata = worldserver.a_(blockposition);
               Block bukkitBlock = worldserver.getWorld().getBlockAt(isourceblock.d().u(), isourceblock.d().v(), isourceblock.d().w());
               CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
               BlockDispenseEvent event = new BlockDispenseEvent(
                  bukkitBlock, craftItem.clone(), new Vector(blockposition.u(), blockposition.v(), blockposition.w())
               );
               if (!BlockDispenser.eventFired) {
                  worldserver.getCraftServer().getPluginManager().callEvent(event);
               }
   
               if (event.isCancelled()) {
                  return itemstack;
               } else {
                  if (!event.getItem().equals(craftItem)) {
                     ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
                     IDispenseBehavior idispensebehavior = BlockDispenser.c.get(eventStack.c());
                     if (idispensebehavior != IDispenseBehavior.b && idispensebehavior != this) {
                        idispensebehavior.dispense(isourceblock, eventStack);
                        return itemstack;
                     }
                  }
   
                  if (iblockdata.a(
                        TagsBlock.aD, blockbase_blockdata -> blockbase_blockdata.b(BlockBeehive.b) && blockbase_blockdata.b() instanceof BlockBeehive
                     )
                     && iblockdata.c(BlockBeehive.b) >= 5) {
                     ((BlockBeehive)iblockdata.b()).a(worldserver, iblockdata, blockposition, null, TileEntityBeehive.ReleaseStatus.b);
                     this.a(true);
                     return this.takeLiquid(isourceblock, itemstack, new ItemStack(Items.vx));
                  } else if (worldserver.b_(blockposition).a(TagsFluid.a)) {
                     this.a(true);
                     return this.takeLiquid(isourceblock, itemstack, PotionUtil.a(new ItemStack(Items.rr), Potions.c));
                  } else {
                     return super.a(isourceblock, itemstack);
                  }
               }
            }
         }
      );
      BlockDispenser.a(Items.eW, new DispenseBehaviorMaybe() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            EnumDirection enumdirection = isourceblock.e().c(BlockDispenser.a);
            BlockPosition blockposition = isourceblock.d().a(enumdirection);
            WorldServer worldserver = isourceblock.g();
            IBlockData iblockdata = worldserver.a_(blockposition);
            this.a(true);
            if (iblockdata.a(Blocks.ph)) {
               if (iblockdata.c(BlockRespawnAnchor.c) != 4) {
                  BlockRespawnAnchor.a(null, worldserver, blockposition, iblockdata);
                  itemstack.h(1);
               } else {
                  this.a(false);
               }

               return itemstack;
            } else {
               return super.a(isourceblock, itemstack);
            }
         }
      });
      BlockDispenser.a(Items.rc.k(), new DispenseBehaviorShears());
      BlockDispenser.a(Items.vu, new DispenseBehaviorMaybe() {
         @Override
         public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
            BlockPosition blockposition = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
            WorldServer worldserver = isourceblock.g();
            IBlockData iblockdata = worldserver.a_(blockposition);
            Optional<IBlockData> optional = HoneycombItem.b(iblockdata);
            if (optional.isPresent()) {
               worldserver.b(blockposition, optional.get());
               worldserver.c(3003, blockposition, 0);
               itemstack.h(1);
               this.a(true);
               return itemstack;
            } else {
               return super.a(isourceblock, itemstack);
            }
         }
      });
      BlockDispenser.a(
         Items.rr,
         new DispenseBehaviorItem() {
            private final DispenseBehaviorItem defaultDispenseItemBehavior = new DispenseBehaviorItem();
   
            @Override
            public ItemStack a(ISourceBlock isourceblock, ItemStack itemstack) {
               if (PotionUtil.d(itemstack) != Potions.c) {
                  return this.defaultDispenseItemBehavior.dispense(isourceblock, itemstack);
               } else {
                  WorldServer worldserver = isourceblock.g();
                  BlockPosition blockposition = isourceblock.d();
                  BlockPosition blockposition1 = isourceblock.d().a(isourceblock.e().c(BlockDispenser.a));
                  if (!worldserver.a_(blockposition1).a(TagsBlock.bV)) {
                     return this.defaultDispenseItemBehavior.dispense(isourceblock, itemstack);
                  } else {
                     if (!worldserver.B) {
                        for(int k = 0; k < 5; ++k) {
                           worldserver.a(
                              Particles.ai,
                              (double)blockposition.u() + worldserver.z.j(),
                              (double)(blockposition.v() + 1),
                              (double)blockposition.w() + worldserver.z.j(),
                              1,
                              0.0,
                              0.0,
                              0.0,
                              1.0
                           );
                        }
                     }
   
                     worldserver.a(null, blockposition, SoundEffects.cg, SoundCategory.e, 1.0F, 1.0F);
                     worldserver.a(null, GameEvent.B, blockposition);
                     worldserver.b(blockposition1, Blocks.rC.o());
                     return new ItemStack(Items.rs);
                  }
               }
            }
         }
      );
   }

   static void a(ISourceBlock isourceblock, Entity entity, EnumDirection enumdirection) {
      entity.e(
         isourceblock.a() + (double)enumdirection.j() * (0.5000099999997474 - (double)entity.dc() / 2.0),
         isourceblock.b() + (double)enumdirection.k() * (0.5000099999997474 - (double)entity.dd() / 2.0) - (double)entity.dd() / 2.0,
         isourceblock.c() + (double)enumdirection.l() * (0.5000099999997474 - (double)entity.dc() / 2.0)
      );
   }
}
