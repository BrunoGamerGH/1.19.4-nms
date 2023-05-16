package net.minecraft.world.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.RayTrace;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPosition;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class ItemMonsterEgg extends Item {
   private static final Map<EntityTypes<? extends EntityInsentient>, ItemMonsterEgg> a = Maps.newIdentityHashMap();
   private final int b;
   private final int c;
   private final EntityTypes<?> d;

   public ItemMonsterEgg(EntityTypes<? extends EntityInsentient> entitytypes, int i, int j, Item.Info item_info) {
      super(item_info);
      this.d = entitytypes;
      this.b = i;
      this.c = j;
      a.put(entitytypes, this);
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      World world = itemactioncontext.q();
      if (!(world instanceof WorldServer)) {
         return EnumInteractionResult.a;
      } else {
         ItemStack itemstack = itemactioncontext.n();
         BlockPosition blockposition = itemactioncontext.a();
         EnumDirection enumdirection = itemactioncontext.k();
         IBlockData iblockdata = world.a_(blockposition);
         if (iblockdata.a(Blocks.cs)) {
            TileEntity tileentity = world.c_(blockposition);
            if (tileentity instanceof TileEntityMobSpawner tileentitymobspawner) {
               EntityTypes<?> entitytypes = this.a(itemstack.u());
               tileentitymobspawner.a(entitytypes, world.r_());
               tileentity.e();
               world.a(blockposition, iblockdata, iblockdata, 3);
               world.a(itemactioncontext.o(), GameEvent.c, blockposition);
               itemstack.h(1);
               return EnumInteractionResult.b;
            }
         }

         BlockPosition blockposition1;
         if (iblockdata.k(world, blockposition).b()) {
            blockposition1 = blockposition;
         } else {
            blockposition1 = blockposition.a(enumdirection);
         }

         EntityTypes<?> entitytypes1 = this.a(itemstack.u());
         if (entitytypes1.a(
               (WorldServer)world,
               itemstack,
               itemactioncontext.o(),
               blockposition1,
               EnumMobSpawn.m,
               true,
               !Objects.equals(blockposition, blockposition1) && enumdirection == EnumDirection.b
            )
            != null) {
            itemstack.h(1);
            world.a(itemactioncontext.o(), GameEvent.u, blockposition);
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public InteractionResultWrapper<ItemStack> a(World world, EntityHuman entityhuman, EnumHand enumhand) {
      ItemStack itemstack = entityhuman.b(enumhand);
      MovingObjectPositionBlock movingobjectpositionblock = a(world, entityhuman, RayTrace.FluidCollisionOption.b);
      if (movingobjectpositionblock.c() != MovingObjectPosition.EnumMovingObjectType.b) {
         return InteractionResultWrapper.c(itemstack);
      } else if (!(world instanceof WorldServer)) {
         return InteractionResultWrapper.a(itemstack);
      } else {
         BlockPosition blockposition = movingobjectpositionblock.a();
         if (!(world.a_(blockposition).b() instanceof BlockFluids)) {
            return InteractionResultWrapper.c(itemstack);
         } else if (world.a(entityhuman, blockposition) && entityhuman.a(blockposition, movingobjectpositionblock.b(), itemstack)) {
            EntityTypes<?> entitytypes = this.a(itemstack.u());
            Entity entity = entitytypes.a((WorldServer)world, itemstack, entityhuman, blockposition, EnumMobSpawn.m, false, false);
            if (entity == null) {
               return InteractionResultWrapper.c(itemstack);
            } else {
               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               entityhuman.b(StatisticList.c.b(this));
               world.a(entityhuman, GameEvent.u, entity.de());
               return InteractionResultWrapper.b(itemstack);
            }
         } else {
            return InteractionResultWrapper.d(itemstack);
         }
      }
   }

   public boolean a(@Nullable NBTTagCompound nbttagcompound, EntityTypes<?> entitytypes) {
      return Objects.equals(this.a(nbttagcompound), entitytypes);
   }

   public int a(int i) {
      return i == 0 ? this.b : this.c;
   }

   @Nullable
   public static ItemMonsterEgg a(@Nullable EntityTypes<?> entitytypes) {
      return a.get(entitytypes);
   }

   public static Iterable<ItemMonsterEgg> h() {
      return Iterables.unmodifiableIterable(a.values());
   }

   public EntityTypes<?> a(@Nullable NBTTagCompound nbttagcompound) {
      if (nbttagcompound != null && nbttagcompound.b("EntityTag", 10)) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.p("EntityTag");
         if (nbttagcompound1.b("id", 8)) {
            return EntityTypes.a(nbttagcompound1.l("id")).orElse(this.d);
         }
      }

      return this.d;
   }

   @Override
   public FeatureFlagSet m() {
      return this.d.m();
   }

   public Optional<EntityInsentient> a(
      EntityHuman entityhuman,
      EntityInsentient entityinsentient,
      EntityTypes<? extends EntityInsentient> entitytypes,
      WorldServer worldserver,
      Vec3D vec3d,
      ItemStack itemstack
   ) {
      if (!this.a(itemstack.u(), entitytypes)) {
         return Optional.empty();
      } else {
         Object object;
         if (entityinsentient instanceof EntityAgeable) {
            object = ((EntityAgeable)entityinsentient).a(worldserver, (EntityAgeable)entityinsentient);
         } else {
            object = entitytypes.a((World)worldserver);
         }

         if (object == null) {
            return Optional.empty();
         } else {
            ((EntityInsentient)object).a(true);
            if (!((EntityInsentient)object).y_()) {
               return Optional.empty();
            } else {
               ((EntityInsentient)object).b(vec3d.a(), vec3d.b(), vec3d.c(), 0.0F, 0.0F);
               worldserver.addFreshEntityWithPassengers((Entity)object, SpawnReason.SPAWNER_EGG);
               if (itemstack.z()) {
                  ((EntityInsentient)object).b(itemstack.x());
               }

               if (!entityhuman.fK().d) {
                  itemstack.h(1);
               }

               return Optional.of((EntityInsentient)object);
            }
         }
      }
   }
}
