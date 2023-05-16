package org.bukkit.craftbukkit.v1_19_R3.entity;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.core.Position;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerChunkMap;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAreaEffectCloud;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityFlying;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.ambient.EntityAmbient;
import net.minecraft.world.entity.ambient.EntityBat;
import net.minecraft.world.entity.animal.EntityAnimal;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.animal.EntityChicken;
import net.minecraft.world.entity.animal.EntityCod;
import net.minecraft.world.entity.animal.EntityCow;
import net.minecraft.world.entity.animal.EntityDolphin;
import net.minecraft.world.entity.animal.EntityFish;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.entity.animal.EntityGolem;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntityMushroomCow;
import net.minecraft.world.entity.animal.EntityOcelot;
import net.minecraft.world.entity.animal.EntityPanda;
import net.minecraft.world.entity.animal.EntityParrot;
import net.minecraft.world.entity.animal.EntityPig;
import net.minecraft.world.entity.animal.EntityPolarBear;
import net.minecraft.world.entity.animal.EntityPufferFish;
import net.minecraft.world.entity.animal.EntityRabbit;
import net.minecraft.world.entity.animal.EntitySalmon;
import net.minecraft.world.entity.animal.EntitySheep;
import net.minecraft.world.entity.animal.EntitySnowman;
import net.minecraft.world.entity.animal.EntitySquid;
import net.minecraft.world.entity.animal.EntityTropicalFish;
import net.minecraft.world.entity.animal.EntityTurtle;
import net.minecraft.world.entity.animal.EntityWaterAnimal;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.EntityHorse;
import net.minecraft.world.entity.animal.horse.EntityHorseAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseChestedAbstract;
import net.minecraft.world.entity.animal.horse.EntityHorseDonkey;
import net.minecraft.world.entity.animal.horse.EntityHorseMule;
import net.minecraft.world.entity.animal.horse.EntityHorseSkeleton;
import net.minecraft.world.entity.animal.horse.EntityHorseZombie;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.animal.horse.EntityLlamaTrader;
import net.minecraft.world.entity.animal.sniffer.Sniffer;
import net.minecraft.world.entity.boss.EntityComplexPart;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderCrystal;
import net.minecraft.world.entity.boss.enderdragon.EntityEnderDragon;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.decoration.EntityHanging;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.entity.decoration.EntityPainting;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.monster.EntityBlaze;
import net.minecraft.world.entity.monster.EntityCaveSpider;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.monster.EntityDrowned;
import net.minecraft.world.entity.monster.EntityEnderman;
import net.minecraft.world.entity.monster.EntityEndermite;
import net.minecraft.world.entity.monster.EntityEvoker;
import net.minecraft.world.entity.monster.EntityGhast;
import net.minecraft.world.entity.monster.EntityGiantZombie;
import net.minecraft.world.entity.monster.EntityGuardian;
import net.minecraft.world.entity.monster.EntityGuardianElder;
import net.minecraft.world.entity.monster.EntityIllagerAbstract;
import net.minecraft.world.entity.monster.EntityIllagerIllusioner;
import net.minecraft.world.entity.monster.EntityIllagerWizard;
import net.minecraft.world.entity.monster.EntityMagmaCube;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.EntityPhantom;
import net.minecraft.world.entity.monster.EntityPigZombie;
import net.minecraft.world.entity.monster.EntityPillager;
import net.minecraft.world.entity.monster.EntityRavager;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.monster.EntitySilverfish;
import net.minecraft.world.entity.monster.EntitySkeleton;
import net.minecraft.world.entity.monster.EntitySkeletonAbstract;
import net.minecraft.world.entity.monster.EntitySkeletonStray;
import net.minecraft.world.entity.monster.EntitySkeletonWither;
import net.minecraft.world.entity.monster.EntitySlime;
import net.minecraft.world.entity.monster.EntitySpider;
import net.minecraft.world.entity.monster.EntityStrider;
import net.minecraft.world.entity.monster.EntityVex;
import net.minecraft.world.entity.monster.EntityVindicator;
import net.minecraft.world.entity.monster.EntityWitch;
import net.minecraft.world.entity.monster.EntityZoglin;
import net.minecraft.world.entity.monster.EntityZombie;
import net.minecraft.world.entity.monster.EntityZombieHusk;
import net.minecraft.world.entity.monster.EntityZombieVillager;
import net.minecraft.world.entity.monster.hoglin.EntityHoglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglin;
import net.minecraft.world.entity.monster.piglin.EntityPiglinAbstract;
import net.minecraft.world.entity.monster.piglin.EntityPiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.npc.EntityVillagerTrader;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityArrow;
import net.minecraft.world.entity.projectile.EntityDragonFireball;
import net.minecraft.world.entity.projectile.EntityEgg;
import net.minecraft.world.entity.projectile.EntityEnderPearl;
import net.minecraft.world.entity.projectile.EntityEnderSignal;
import net.minecraft.world.entity.projectile.EntityEvokerFangs;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.EntityFireworks;
import net.minecraft.world.entity.projectile.EntityFishingHook;
import net.minecraft.world.entity.projectile.EntityLargeFireball;
import net.minecraft.world.entity.projectile.EntityLlamaSpit;
import net.minecraft.world.entity.projectile.EntityPotion;
import net.minecraft.world.entity.projectile.EntityProjectile;
import net.minecraft.world.entity.projectile.EntityShulkerBullet;
import net.minecraft.world.entity.projectile.EntitySmallFireball;
import net.minecraft.world.entity.projectile.EntitySnowball;
import net.minecraft.world.entity.projectile.EntitySpectralArrow;
import net.minecraft.world.entity.projectile.EntityThrownExpBottle;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.EntityTippedArrow;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.entity.vehicle.EntityMinecartAbstract;
import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import net.minecraft.world.entity.vehicle.EntityMinecartCommandBlock;
import net.minecraft.world.entity.vehicle.EntityMinecartFurnace;
import net.minecraft.world.entity.vehicle.EntityMinecartHopper;
import net.minecraft.world.entity.vehicle.EntityMinecartMobSpawner;
import net.minecraft.world.entity.vehicle.EntityMinecartRideable;
import net.minecraft.world.entity.vehicle.EntityMinecartTNT;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftSound;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R3.persistence.CraftPersistentDataTypeRegistry;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftSpawnCategory;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftVector;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.entity.Entity.Spigot;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.spigotmc.AsyncCatcher;

public abstract class CraftEntity implements org.bukkit.entity.Entity {
   private static PermissibleBase perm;
   private static final CraftPersistentDataTypeRegistry DATA_TYPE_REGISTRY = new CraftPersistentDataTypeRegistry();
   protected final CraftServer server;
   protected Entity entity;
   private EntityDamageEvent lastDamageEvent;
   private final CraftPersistentDataContainer persistentDataContainer = new CraftPersistentDataContainer(DATA_TYPE_REGISTRY);
   private final Spigot spigot = new Spigot() {
      public void sendMessage(BaseComponent component) {
      }

      public void sendMessage(BaseComponent... components) {
      }

      public void sendMessage(UUID sender, BaseComponent... components) {
      }

      public void sendMessage(UUID sender, BaseComponent component) {
      }
   };

   public CraftEntity(CraftServer server, Entity entity) {
      this.server = server;
      this.entity = entity;
   }

   public static CraftEntity getEntity(CraftServer server, Entity entity) {
      if (entity instanceof EntityLiving) {
         if (entity instanceof EntityHuman) {
            if (entity instanceof EntityPlayer) {
               return new CraftPlayer(server, (EntityPlayer)entity);
            }

            return new CraftHumanEntity(server, (EntityHuman)entity);
         }

         if (entity instanceof EntityWaterAnimal) {
            if (entity instanceof EntitySquid) {
               if (entity instanceof GlowSquid) {
                  return new CraftGlowSquid(server, (GlowSquid)entity);
               }

               return new CraftSquid(server, (EntitySquid)entity);
            }

            if (entity instanceof EntityFish) {
               if (entity instanceof EntityCod) {
                  return new CraftCod(server, (EntityCod)entity);
               }

               if (entity instanceof EntityPufferFish) {
                  return new CraftPufferFish(server, (EntityPufferFish)entity);
               }

               if (entity instanceof EntitySalmon) {
                  return new CraftSalmon(server, (EntitySalmon)entity);
               }

               if (entity instanceof EntityTropicalFish) {
                  return new CraftTropicalFish(server, (EntityTropicalFish)entity);
               }

               if (entity instanceof Tadpole) {
                  return new CraftTadpole(server, (Tadpole)entity);
               }

               return new CraftFish(server, (EntityFish)entity);
            }

            if (entity instanceof EntityDolphin) {
               return new CraftDolphin(server, (EntityDolphin)entity);
            }

            return new CraftWaterMob(server, (EntityWaterAnimal)entity);
         }

         if (!(entity instanceof EntityCreature)) {
            if (entity instanceof EntitySlime) {
               if (entity instanceof EntityMagmaCube) {
                  return new CraftMagmaCube(server, (EntityMagmaCube)entity);
               }

               return new CraftSlime(server, (EntitySlime)entity);
            }

            if (entity instanceof EntityFlying) {
               if (entity instanceof EntityGhast) {
                  return new CraftGhast(server, (EntityGhast)entity);
               }

               if (entity instanceof EntityPhantom) {
                  return new CraftPhantom(server, (EntityPhantom)entity);
               }

               return new CraftFlying(server, (EntityFlying)entity);
            }

            if (entity instanceof EntityEnderDragon) {
               return new CraftEnderDragon(server, (EntityEnderDragon)entity);
            }

            if (entity instanceof EntityAmbient) {
               if (entity instanceof EntityBat) {
                  return new CraftBat(server, (EntityBat)entity);
               }

               return new CraftAmbient(server, (EntityAmbient)entity);
            }

            if (entity instanceof EntityArmorStand) {
               return new CraftArmorStand(server, (EntityArmorStand)entity);
            }

            return new CraftLivingEntity(server, (EntityLiving)entity);
         }

         if (entity instanceof EntityAnimal) {
            if (entity instanceof EntityChicken) {
               return new CraftChicken(server, (EntityChicken)entity);
            }

            if (entity instanceof EntityCow) {
               if (entity instanceof EntityMushroomCow) {
                  return new CraftMushroomCow(server, (EntityMushroomCow)entity);
               }

               return new CraftCow(server, (EntityCow)entity);
            }

            if (entity instanceof EntityPig) {
               return new CraftPig(server, (EntityPig)entity);
            }

            if (entity instanceof EntityTameableAnimal) {
               if (entity instanceof EntityWolf) {
                  return new CraftWolf(server, (EntityWolf)entity);
               }

               if (entity instanceof EntityCat) {
                  return new CraftCat(server, (EntityCat)entity);
               }

               if (entity instanceof EntityParrot) {
                  return new CraftParrot(server, (EntityParrot)entity);
               }
            } else {
               if (entity instanceof EntitySheep) {
                  return new CraftSheep(server, (EntitySheep)entity);
               }

               if (!(entity instanceof EntityHorseAbstract)) {
                  if (entity instanceof EntityRabbit) {
                     return new CraftRabbit(server, (EntityRabbit)entity);
                  }

                  if (entity instanceof EntityPolarBear) {
                     return new CraftPolarBear(server, (EntityPolarBear)entity);
                  }

                  if (entity instanceof EntityTurtle) {
                     return new CraftTurtle(server, (EntityTurtle)entity);
                  }

                  if (entity instanceof EntityOcelot) {
                     return new CraftOcelot(server, (EntityOcelot)entity);
                  }

                  if (entity instanceof EntityPanda) {
                     return new CraftPanda(server, (EntityPanda)entity);
                  }

                  if (entity instanceof EntityFox) {
                     return new CraftFox(server, (EntityFox)entity);
                  }

                  if (entity instanceof EntityBee) {
                     return new CraftBee(server, (EntityBee)entity);
                  }

                  if (entity instanceof EntityHoglin) {
                     return new CraftHoglin(server, (EntityHoglin)entity);
                  }

                  if (entity instanceof EntityStrider) {
                     return new CraftStrider(server, (EntityStrider)entity);
                  }

                  if (entity instanceof Axolotl) {
                     return new CraftAxolotl(server, (Axolotl)entity);
                  }

                  if (entity instanceof Goat) {
                     return new CraftGoat(server, (Goat)entity);
                  }

                  if (entity instanceof Frog) {
                     return new CraftFrog(server, (Frog)entity);
                  }

                  if (entity instanceof Sniffer) {
                     return new CraftSniffer(server, (Sniffer)entity);
                  }

                  return new CraftAnimals(server, (EntityAnimal)entity);
               }

               if (entity instanceof EntityHorseChestedAbstract) {
                  if (entity instanceof EntityHorseDonkey) {
                     return new CraftDonkey(server, (EntityHorseDonkey)entity);
                  }

                  if (entity instanceof EntityHorseMule) {
                     return new CraftMule(server, (EntityHorseMule)entity);
                  }

                  if (entity instanceof EntityLlamaTrader) {
                     return new CraftTraderLlama(server, (EntityLlamaTrader)entity);
                  }

                  if (entity instanceof EntityLlama) {
                     return new CraftLlama(server, (EntityLlama)entity);
                  }
               } else {
                  if (entity instanceof EntityHorse) {
                     return new CraftHorse(server, (EntityHorse)entity);
                  }

                  if (entity instanceof EntityHorseSkeleton) {
                     return new CraftSkeletonHorse(server, (EntityHorseSkeleton)entity);
                  }

                  if (entity instanceof EntityHorseZombie) {
                     return new CraftZombieHorse(server, (EntityHorseZombie)entity);
                  }

                  if (entity instanceof Camel) {
                     return new CraftCamel(server, (Camel)entity);
                  }
               }
            }
         } else if (entity instanceof EntityMonster) {
            if (entity instanceof EntityZombie) {
               if (entity instanceof EntityPigZombie) {
                  return new CraftPigZombie(server, (EntityPigZombie)entity);
               }

               if (entity instanceof EntityZombieHusk) {
                  return new CraftHusk(server, (EntityZombieHusk)entity);
               }

               if (entity instanceof EntityZombieVillager) {
                  return new CraftVillagerZombie(server, (EntityZombieVillager)entity);
               }

               if (entity instanceof EntityDrowned) {
                  return new CraftDrowned(server, (EntityDrowned)entity);
               }

               return new CraftZombie(server, (EntityZombie)entity);
            }

            if (entity instanceof EntityCreeper) {
               return new CraftCreeper(server, (EntityCreeper)entity);
            }

            if (entity instanceof EntityEnderman) {
               return new CraftEnderman(server, (EntityEnderman)entity);
            }

            if (entity instanceof EntitySilverfish) {
               return new CraftSilverfish(server, (EntitySilverfish)entity);
            }

            if (entity instanceof EntityGiantZombie) {
               return new CraftGiant(server, (EntityGiantZombie)entity);
            }

            if (!(entity instanceof EntitySkeletonAbstract)) {
               if (entity instanceof EntityBlaze) {
                  return new CraftBlaze(server, (EntityBlaze)entity);
               }

               if (entity instanceof EntityWitch) {
                  return new CraftWitch(server, (EntityWitch)entity);
               }

               if (entity instanceof EntityWither) {
                  return new CraftWither(server, (EntityWither)entity);
               }

               if (entity instanceof EntitySpider) {
                  if (entity instanceof EntityCaveSpider) {
                     return new CraftCaveSpider(server, (EntityCaveSpider)entity);
                  }

                  return new CraftSpider(server, (EntitySpider)entity);
               }

               if (entity instanceof EntityEndermite) {
                  return new CraftEndermite(server, (EntityEndermite)entity);
               }

               if (entity instanceof EntityGuardian) {
                  if (entity instanceof EntityGuardianElder) {
                     return new CraftElderGuardian(server, (EntityGuardianElder)entity);
                  }

                  return new CraftGuardian(server, (EntityGuardian)entity);
               }

               if (entity instanceof EntityVex) {
                  return new CraftVex(server, (EntityVex)entity);
               }

               if (entity instanceof EntityIllagerAbstract) {
                  if (entity instanceof EntityIllagerWizard) {
                     if (entity instanceof EntityEvoker) {
                        return new CraftEvoker(server, (EntityEvoker)entity);
                     }

                     if (entity instanceof EntityIllagerIllusioner) {
                        return new CraftIllusioner(server, (EntityIllagerIllusioner)entity);
                     }

                     return new CraftSpellcaster(server, (EntityIllagerWizard)entity);
                  }

                  if (entity instanceof EntityVindicator) {
                     return new CraftVindicator(server, (EntityVindicator)entity);
                  }

                  if (entity instanceof EntityPillager) {
                     return new CraftPillager(server, (EntityPillager)entity);
                  }

                  return new CraftIllager(server, (EntityIllagerAbstract)entity);
               }

               if (entity instanceof EntityRavager) {
                  return new CraftRavager(server, (EntityRavager)entity);
               }

               if (entity instanceof EntityPiglinAbstract) {
                  if (entity instanceof EntityPiglin) {
                     return new CraftPiglin(server, (EntityPiglin)entity);
                  }

                  if (entity instanceof EntityPiglinBrute) {
                     return new CraftPiglinBrute(server, (EntityPiglinBrute)entity);
                  }

                  return new CraftPiglinAbstract(server, (EntityPiglinAbstract)entity);
               }

               if (entity instanceof EntityZoglin) {
                  return new CraftZoglin(server, (EntityZoglin)entity);
               }

               if (entity instanceof Warden) {
                  return new CraftWarden(server, (Warden)entity);
               }

               return new CraftMonster(server, (EntityMonster)entity);
            }

            if (entity instanceof EntitySkeletonStray) {
               return new CraftStray(server, (EntitySkeletonStray)entity);
            }

            if (entity instanceof EntitySkeletonWither) {
               return new CraftWitherSkeleton(server, (EntitySkeletonWither)entity);
            }

            if (entity instanceof EntitySkeleton) {
               return new CraftSkeleton(server, (EntitySkeleton)entity);
            }
         } else {
            if (!(entity instanceof EntityGolem)) {
               if (entity instanceof EntityVillagerAbstract) {
                  if (entity instanceof EntityVillager) {
                     return new CraftVillager(server, (EntityVillager)entity);
                  }

                  if (entity instanceof EntityVillagerTrader) {
                     return new CraftWanderingTrader(server, (EntityVillagerTrader)entity);
                  }

                  return new CraftAbstractVillager(server, (EntityVillagerAbstract)entity);
               }

               if (entity instanceof Allay) {
                  return new CraftAllay(server, (Allay)entity);
               }

               return new CraftCreature(server, (EntityCreature)entity);
            }

            if (entity instanceof EntitySnowman) {
               return new CraftSnowman(server, (EntitySnowman)entity);
            }

            if (entity instanceof EntityIronGolem) {
               return new CraftIronGolem(server, (EntityIronGolem)entity);
            }

            if (entity instanceof EntityShulker) {
               return new CraftShulker(server, (EntityShulker)entity);
            }
         }
      } else {
         if (entity instanceof EntityComplexPart part) {
            if (part.b instanceof EntityEnderDragon) {
               return new CraftEnderDragonPart(server, (EntityComplexPart)entity);
            }

            return new CraftComplexPart(server, (EntityComplexPart)entity);
         }

         if (entity instanceof EntityExperienceOrb) {
            return new CraftExperienceOrb(server, (EntityExperienceOrb)entity);
         }

         if (entity instanceof EntityTippedArrow) {
            return new CraftTippedArrow(server, (EntityTippedArrow)entity);
         }

         if (entity instanceof EntitySpectralArrow) {
            return new CraftSpectralArrow(server, (EntitySpectralArrow)entity);
         }

         if (entity instanceof EntityArrow) {
            if (entity instanceof EntityThrownTrident) {
               return new CraftTrident(server, (EntityThrownTrident)entity);
            }

            return new CraftArrow(server, (EntityArrow)entity);
         }

         if (entity instanceof EntityBoat) {
            if (entity instanceof ChestBoat) {
               return new CraftChestBoat(server, (ChestBoat)entity);
            }

            return new CraftBoat(server, (EntityBoat)entity);
         }

         if (entity instanceof EntityProjectile) {
            if (entity instanceof EntityEgg) {
               return new CraftEgg(server, (EntityEgg)entity);
            }

            if (entity instanceof EntitySnowball) {
               return new CraftSnowball(server, (EntitySnowball)entity);
            }

            if (entity instanceof EntityPotion) {
               return new CraftThrownPotion(server, (EntityPotion)entity);
            }

            if (entity instanceof EntityEnderPearl) {
               return new CraftEnderPearl(server, (EntityEnderPearl)entity);
            }

            if (entity instanceof EntityThrownExpBottle) {
               return new CraftThrownExpBottle(server, (EntityThrownExpBottle)entity);
            }
         } else {
            if (entity instanceof EntityFallingBlock) {
               return new CraftFallingBlock(server, (EntityFallingBlock)entity);
            }

            if (entity instanceof EntityFireball) {
               if (entity instanceof EntitySmallFireball) {
                  return new CraftSmallFireball(server, (EntitySmallFireball)entity);
               }

               if (entity instanceof EntityLargeFireball) {
                  return new CraftLargeFireball(server, (EntityLargeFireball)entity);
               }

               if (entity instanceof EntityWitherSkull) {
                  return new CraftWitherSkull(server, (EntityWitherSkull)entity);
               }

               if (entity instanceof EntityDragonFireball) {
                  return new CraftDragonFireball(server, (EntityDragonFireball)entity);
               }

               return new CraftFireball(server, (EntityFireball)entity);
            }

            if (entity instanceof EntityEnderSignal) {
               return new CraftEnderSignal(server, (EntityEnderSignal)entity);
            }

            if (entity instanceof EntityEnderCrystal) {
               return new CraftEnderCrystal(server, (EntityEnderCrystal)entity);
            }

            if (entity instanceof EntityFishingHook) {
               return new CraftFishHook(server, (EntityFishingHook)entity);
            }

            if (entity instanceof EntityItem) {
               return new CraftItem(server, (EntityItem)entity);
            }

            if (entity instanceof EntityLightning) {
               return new CraftLightningStrike(server, (EntityLightning)entity);
            }

            if (entity instanceof EntityMinecartAbstract) {
               if (entity instanceof EntityMinecartFurnace) {
                  return new CraftMinecartFurnace(server, (EntityMinecartFurnace)entity);
               }

               if (entity instanceof EntityMinecartChest) {
                  return new CraftMinecartChest(server, (EntityMinecartChest)entity);
               }

               if (entity instanceof EntityMinecartTNT) {
                  return new CraftMinecartTNT(server, (EntityMinecartTNT)entity);
               }

               if (entity instanceof EntityMinecartHopper) {
                  return new CraftMinecartHopper(server, (EntityMinecartHopper)entity);
               }

               if (entity instanceof EntityMinecartMobSpawner) {
                  return new CraftMinecartMobSpawner(server, (EntityMinecartMobSpawner)entity);
               }

               if (entity instanceof EntityMinecartRideable) {
                  return new CraftMinecartRideable(server, (EntityMinecartRideable)entity);
               }

               if (entity instanceof EntityMinecartCommandBlock) {
                  return new CraftMinecartCommand(server, (EntityMinecartCommandBlock)entity);
               }
            } else {
               if (entity instanceof EntityHanging) {
                  if (entity instanceof EntityPainting) {
                     return new CraftPainting(server, (EntityPainting)entity);
                  }

                  if (entity instanceof EntityItemFrame) {
                     if (entity instanceof GlowItemFrame) {
                        return new CraftGlowItemFrame(server, (GlowItemFrame)entity);
                     }

                     return new CraftItemFrame(server, (EntityItemFrame)entity);
                  }

                  if (entity instanceof EntityLeash) {
                     return new CraftLeash(server, (EntityLeash)entity);
                  }

                  return new CraftHanging(server, (EntityHanging)entity);
               }

               if (entity instanceof EntityTNTPrimed) {
                  return new CraftTNTPrimed(server, (EntityTNTPrimed)entity);
               }

               if (entity instanceof EntityFireworks) {
                  return new CraftFirework(server, (EntityFireworks)entity);
               }

               if (entity instanceof EntityShulkerBullet) {
                  return new CraftShulkerBullet(server, (EntityShulkerBullet)entity);
               }

               if (entity instanceof EntityAreaEffectCloud) {
                  return new CraftAreaEffectCloud(server, (EntityAreaEffectCloud)entity);
               }

               if (entity instanceof EntityEvokerFangs) {
                  return new CraftEvokerFangs(server, (EntityEvokerFangs)entity);
               }

               if (entity instanceof EntityLlamaSpit) {
                  return new CraftLlamaSpit(server, (EntityLlamaSpit)entity);
               }

               if (entity instanceof Marker) {
                  return new CraftMarker(server, (Marker)entity);
               }

               if (entity instanceof Interaction) {
                  return new CraftInteraction(server, (Interaction)entity);
               }

               if (entity instanceof Display) {
                  if (entity instanceof Display.BlockDisplay) {
                     return new CraftBlockDisplay(server, (Display.BlockDisplay)entity);
                  }

                  if (entity instanceof Display.ItemDisplay) {
                     return new CraftItemDisplay(server, (Display.ItemDisplay)entity);
                  }

                  if (entity instanceof Display.TextDisplay) {
                     return new CraftTextDisplay(server, (Display.TextDisplay)entity);
                  }

                  return new CraftDisplay(server, (Display)entity);
               }
            }
         }
      }

      throw new AssertionError("Unknown entity " + (entity == null ? null : entity.getClass()));
   }

   public Location getLocation() {
      return new Location(this.getWorld(), this.entity.dl(), this.entity.dn(), this.entity.dr(), this.entity.getBukkitYaw(), this.entity.dy());
   }

   public Location getLocation(Location loc) {
      if (loc != null) {
         loc.setWorld(this.getWorld());
         loc.setX(this.entity.dl());
         loc.setY(this.entity.dn());
         loc.setZ(this.entity.dr());
         loc.setYaw(this.entity.getBukkitYaw());
         loc.setPitch(this.entity.dy());
      }

      return loc;
   }

   public Vector getVelocity() {
      return CraftVector.toBukkit(this.entity.dj());
   }

   public void setVelocity(Vector velocity) {
      Preconditions.checkArgument(velocity != null, "velocity");
      velocity.checkFinite();
      this.entity.f(CraftVector.toNMS(velocity));
      this.entity.S = true;
   }

   public double getHeight() {
      return (double)this.getHandle().dd();
   }

   public double getWidth() {
      return (double)this.getHandle().dc();
   }

   public BoundingBox getBoundingBox() {
      AxisAlignedBB bb = this.getHandle().cD();
      return new BoundingBox(bb.a, bb.b, bb.c, bb.d, bb.e, bb.f);
   }

   public boolean isOnGround() {
      return this.entity instanceof EntityArrow ? ((EntityArrow)this.entity).b : this.entity.ax();
   }

   public boolean isInWater() {
      return this.entity.aT();
   }

   public World getWorld() {
      return this.entity.H.getWorld();
   }

   public void setRotation(float yaw, float pitch) {
      NumberConversions.checkFinite(pitch, "pitch not finite");
      NumberConversions.checkFinite(yaw, "yaw not finite");
      yaw = Location.normalizeYaw(yaw);
      pitch = Location.normalizePitch(pitch);
      this.entity.f(yaw);
      this.entity.e(pitch);
      this.entity.L = yaw;
      this.entity.M = pitch;
      this.entity.r(yaw);
   }

   public boolean teleport(Location location) {
      return this.teleport(location, TeleportCause.PLUGIN);
   }

   public boolean teleport(Location location, TeleportCause cause) {
      Preconditions.checkArgument(location != null, "location cannot be null");
      location.checkFinite();
      if (!this.entity.bM() && !this.entity.dB()) {
         this.entity.bz();
         if (location.getWorld() != null && !location.getWorld().equals(this.getWorld())) {
            Preconditions.checkState(!this.entity.generation, "Cannot teleport entity to an other world during world generation");
            this.entity.teleportTo(((CraftWorld)location.getWorld()).getHandle(), new Position(location.getX(), location.getY(), location.getZ()));
            return true;
         } else {
            this.entity.a(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
            this.entity.r(location.getYaw());
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean teleport(org.bukkit.entity.Entity destination) {
      return this.teleport(destination.getLocation());
   }

   public boolean teleport(org.bukkit.entity.Entity destination, TeleportCause cause) {
      return this.teleport(destination.getLocation(), cause);
   }

   public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
      Preconditions.checkState(!this.entity.generation, "Cannot get nearby entities during world generation");
      AsyncCatcher.catchOp("getNearbyEntities");
      List<Entity> notchEntityList = this.entity.H.a(this.entity, this.entity.cD().c(x, y, z), Predicates.alwaysTrue());
      List<org.bukkit.entity.Entity> bukkitEntityList = new ArrayList(notchEntityList.size());

      for(Entity e : notchEntityList) {
         bukkitEntityList.add(e.getBukkitEntity());
      }

      return bukkitEntityList;
   }

   public int getEntityId() {
      return this.entity.af();
   }

   public int getFireTicks() {
      return this.entity.aK;
   }

   public int getMaxFireTicks() {
      return this.entity.cY();
   }

   public void setFireTicks(int ticks) {
      this.entity.aK = ticks;
   }

   public void setVisualFire(boolean fire) {
      this.getHandle().bi = fire;
   }

   public boolean isVisualFire() {
      return this.getHandle().bi;
   }

   public int getFreezeTicks() {
      return this.getHandle().ce();
   }

   public int getMaxFreezeTicks() {
      return this.getHandle().ch();
   }

   public void setFreezeTicks(int ticks) {
      Preconditions.checkArgument(ticks >= 0, "Ticks cannot be less than 0");
      this.getHandle().j(ticks);
   }

   public boolean isFrozen() {
      return this.getHandle().cg();
   }

   public void remove() {
      this.entity.ai();
   }

   public boolean isDead() {
      return !this.entity.bq();
   }

   public boolean isValid() {
      return this.entity.bq() && this.entity.valid && this.entity.isChunkLoaded();
   }

   public Server getServer() {
      return this.server;
   }

   public boolean isPersistent() {
      return this.entity.persist;
   }

   public void setPersistent(boolean persistent) {
      this.entity.persist = persistent;
   }

   public Vector getMomentum() {
      return this.getVelocity();
   }

   public void setMomentum(Vector value) {
      this.setVelocity(value);
   }

   public org.bukkit.entity.Entity getPassenger() {
      return this.isEmpty() ? null : ((Entity)this.getHandle().r.get(0)).getBukkitEntity();
   }

   public boolean setPassenger(org.bukkit.entity.Entity passenger) {
      Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
      if (passenger instanceof CraftEntity) {
         this.eject();
         return ((CraftEntity)passenger).getHandle().k(this.getHandle());
      } else {
         return false;
      }
   }

   public List<org.bukkit.entity.Entity> getPassengers() {
      return Lists.newArrayList(Lists.transform(this.getHandle().r, new Function<Entity, org.bukkit.entity.Entity>() {
         public org.bukkit.entity.Entity apply(Entity input) {
            return input.getBukkitEntity();
         }
      }));
   }

   public boolean addPassenger(org.bukkit.entity.Entity passenger) {
      Preconditions.checkArgument(passenger != null, "passenger == null");
      Preconditions.checkArgument(!this.equals(passenger), "Entity cannot ride itself.");
      return ((CraftEntity)passenger).getHandle().a(this.getHandle(), true);
   }

   public boolean removePassenger(org.bukkit.entity.Entity passenger) {
      Preconditions.checkArgument(passenger != null, "passenger == null");
      ((CraftEntity)passenger).getHandle().bz();
      return true;
   }

   public boolean isEmpty() {
      return !this.getHandle().bM();
   }

   public boolean eject() {
      if (this.isEmpty()) {
         return false;
      } else {
         this.getHandle().bx();
         return true;
      }
   }

   public float getFallDistance() {
      return this.getHandle().aa;
   }

   public void setFallDistance(float distance) {
      this.getHandle().aa = distance;
   }

   public void setLastDamageCause(EntityDamageEvent event) {
      this.lastDamageEvent = event;
   }

   public EntityDamageEvent getLastDamageCause() {
      return this.lastDamageEvent;
   }

   public UUID getUniqueId() {
      return this.getHandle().cs();
   }

   public int getTicksLived() {
      return this.getHandle().ag;
   }

   public void setTicksLived(int value) {
      if (value <= 0) {
         throw new IllegalArgumentException("Age must be at least 1 tick");
      } else {
         this.getHandle().ag = value;
      }
   }

   public Entity getHandle() {
      return this.entity;
   }

   public void playEffect(EntityEffect type) {
      Preconditions.checkArgument(type != null, "type");
      Preconditions.checkState(!this.entity.generation, "Cannot play effect during world generation");
      if (type.getApplicable().isInstance(this)) {
         this.getHandle().H.a(this.getHandle(), type.getData());
      }
   }

   public Sound getSwimSound() {
      return CraftSound.getBukkit(this.getHandle().getSwimSound0());
   }

   public Sound getSwimSplashSound() {
      return CraftSound.getBukkit(this.getHandle().getSwimSplashSound0());
   }

   public Sound getSwimHighSpeedSplashSound() {
      return CraftSound.getBukkit(this.getHandle().getSwimHighSpeedSplashSound0());
   }

   public void setHandle(Entity entity) {
      this.entity = entity;
   }

   @Override
   public String toString() {
      return "CraftEntity{id=" + this.getEntityId() + 125;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CraftEntity other = (CraftEntity)obj;
         return this.getEntityId() == other.getEntityId();
      }
   }

   @Override
   public int hashCode() {
      int hash = 7;
      return 29 * hash + this.getEntityId();
   }

   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
      this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
   }

   public List<MetadataValue> getMetadata(String metadataKey) {
      return this.server.getEntityMetadata().getMetadata(this, metadataKey);
   }

   public boolean hasMetadata(String metadataKey) {
      return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
   }

   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
      this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
   }

   public boolean isInsideVehicle() {
      return this.getHandle().bL();
   }

   public boolean leaveVehicle() {
      if (!this.isInsideVehicle()) {
         return false;
      } else {
         this.getHandle().bz();
         return true;
      }
   }

   public org.bukkit.entity.Entity getVehicle() {
      return !this.isInsideVehicle() ? null : this.getHandle().cV().getBukkitEntity();
   }

   public void setCustomName(String name) {
      if (name != null && name.length() > 256) {
         name = name.substring(0, 256);
      }

      this.getHandle().b(CraftChatMessage.fromStringOrNull(name));
   }

   public String getCustomName() {
      IChatBaseComponent name = this.getHandle().ab();
      return name == null ? null : CraftChatMessage.fromComponent(name);
   }

   public void setCustomNameVisible(boolean flag) {
      this.getHandle().n(flag);
   }

   public boolean isCustomNameVisible() {
      return this.getHandle().cx();
   }

   public void setVisibleByDefault(boolean visible) {
      if (this.getHandle().visibleByDefault != visible) {
         if (visible) {
            for(Player player : this.server.getOnlinePlayers()) {
               ((CraftPlayer)player).resetAndShowEntity(this);
            }
         } else {
            for(Player player : this.server.getOnlinePlayers()) {
               ((CraftPlayer)player).resetAndHideEntity(this);
            }
         }

         this.getHandle().visibleByDefault = visible;
      }
   }

   public boolean isVisibleByDefault() {
      return this.getHandle().visibleByDefault;
   }

   public void sendMessage(String message) {
   }

   public void sendMessage(String... messages) {
   }

   public void sendMessage(UUID sender, String message) {
      this.sendMessage(message);
   }

   public void sendMessage(UUID sender, String... messages) {
      this.sendMessage(messages);
   }

   public String getName() {
      return CraftChatMessage.fromComponent(this.getHandle().Z());
   }

   public boolean isPermissionSet(String name) {
      return getPermissibleBase().isPermissionSet(name);
   }

   public boolean isPermissionSet(Permission perm) {
      return getPermissibleBase().isPermissionSet(perm);
   }

   public boolean hasPermission(String name) {
      return getPermissibleBase().hasPermission(name);
   }

   public boolean hasPermission(Permission perm) {
      return getPermissibleBase().hasPermission(perm);
   }

   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
      return getPermissibleBase().addAttachment(plugin, name, value);
   }

   public PermissionAttachment addAttachment(Plugin plugin) {
      return getPermissibleBase().addAttachment(plugin);
   }

   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
      return getPermissibleBase().addAttachment(plugin, name, value, ticks);
   }

   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
      return getPermissibleBase().addAttachment(plugin, ticks);
   }

   public void removeAttachment(PermissionAttachment attachment) {
      getPermissibleBase().removeAttachment(attachment);
   }

   public void recalculatePermissions() {
      getPermissibleBase().recalculatePermissions();
   }

   public Set<PermissionAttachmentInfo> getEffectivePermissions() {
      return getPermissibleBase().getEffectivePermissions();
   }

   public boolean isOp() {
      return getPermissibleBase().isOp();
   }

   public void setOp(boolean value) {
      getPermissibleBase().setOp(value);
   }

   public void setGlowing(boolean flag) {
      this.getHandle().i(flag);
   }

   public boolean isGlowing() {
      return this.getHandle().bZ();
   }

   public void setInvulnerable(boolean flag) {
      this.getHandle().m(flag);
   }

   public boolean isInvulnerable() {
      return this.getHandle().b(this.getHandle().dG().n());
   }

   public boolean isSilent() {
      return this.getHandle().aO();
   }

   public void setSilent(boolean flag) {
      this.getHandle().d(flag);
   }

   public boolean hasGravity() {
      return !this.getHandle().aP();
   }

   public void setGravity(boolean gravity) {
      this.getHandle().e(!gravity);
   }

   public int getPortalCooldown() {
      return this.getHandle().aY;
   }

   public void setPortalCooldown(int cooldown) {
      this.getHandle().aY = cooldown;
   }

   public Set<String> getScoreboardTags() {
      return this.getHandle().ag();
   }

   public boolean addScoreboardTag(String tag) {
      return this.getHandle().a(tag);
   }

   public boolean removeScoreboardTag(String tag) {
      return this.getHandle().b(tag);
   }

   public PistonMoveReaction getPistonMoveReaction() {
      return PistonMoveReaction.getById(this.getHandle().C_().ordinal());
   }

   public BlockFace getFacing() {
      return CraftBlock.notchToBlockFace(this.getHandle().cB());
   }

   public CraftPersistentDataContainer getPersistentDataContainer() {
      return this.persistentDataContainer;
   }

   public Pose getPose() {
      return Pose.values()[this.getHandle().al().ordinal()];
   }

   public SpawnCategory getSpawnCategory() {
      return CraftSpawnCategory.toBukkit(this.getHandle().ae().f());
   }

   public void storeBukkitValues(NBTTagCompound c) {
      if (!this.persistentDataContainer.isEmpty()) {
         c.a("BukkitValues", this.persistentDataContainer.toTagCompound());
      }
   }

   public void readBukkitValues(NBTTagCompound c) {
      NBTBase base = c.c("BukkitValues");
      if (base instanceof NBTTagCompound) {
         this.persistentDataContainer.putAll((NBTTagCompound)base);
      }
   }

   protected NBTTagCompound save() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.a("id", this.getHandle().bp());
      this.getHandle().f(nbttagcompound);
      return nbttagcompound;
   }

   protected void update() {
      if (this.getHandle().bq()) {
         WorldServer world = ((CraftWorld)this.getWorld()).getHandle();
         PlayerChunkMap.EntityTracker entityTracker = (PlayerChunkMap.EntityTracker)world.k().a.L.get(this.getEntityId());
         if (entityTracker != null) {
            entityTracker.a(this.getHandle().S());
         }
      }
   }

   private static PermissibleBase getPermissibleBase() {
      if (perm == null) {
         perm = new PermissibleBase(new ServerOperator() {
            public boolean isOp() {
               return false;
            }

            public void setOp(boolean value) {
            }
         });
      }

      return perm;
   }

   public Spigot spigot() {
      return this.spigot;
   }
}
