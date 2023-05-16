package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.EntityBee;
import net.minecraft.world.entity.boss.wither.EntityWither;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.monster.EntityCreeper;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityWitherSkull;
import net.minecraft.world.entity.vehicle.EntityMinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeehive;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class BlockBeehive extends BlockTileEntity {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateInteger b = BlockProperties.aN;
   public static final int c = 5;
   private static final int d = 3;

   public BlockBeehive(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(0)).a(a, EnumDirection.c));
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return iblockdata.c(b);
   }

   @Override
   public void a(
      World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack
   ) {
      super.a(world, entityhuman, blockposition, iblockdata, tileentity, itemstack);
      if (!world.B && tileentity instanceof TileEntityBeehive tileentitybeehive) {
         if (EnchantmentManager.a(Enchantments.v, itemstack) == 0) {
            tileentitybeehive.a(entityhuman, iblockdata, TileEntityBeehive.ReleaseStatus.c);
            world.c(blockposition, this);
            this.b(world, blockposition);
         }

         CriterionTriggers.K.a((EntityPlayer)entityhuman, iblockdata, itemstack, tileentitybeehive.g());
      }
   }

   private void b(World world, BlockPosition blockposition) {
      List<EntityBee> list = world.a(EntityBee.class, new AxisAlignedBB(blockposition).c(8.0, 6.0, 8.0));
      if (!list.isEmpty()) {
         List<EntityHuman> list1 = world.a(EntityHuman.class, new AxisAlignedBB(blockposition).c(8.0, 6.0, 8.0));
         int i = list1.size();

         for(EntityBee entitybee : list) {
            if (entitybee.P_() == null) {
               entitybee.setTarget(list1.get(world.z.a(i)), TargetReason.CLOSEST_PLAYER, true);
            }
         }
      }
   }

   public static void a(World world, BlockPosition blockposition) {
      a(world, blockposition, new ItemStack(Items.vu, 3));
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      ItemStack itemstack = entityhuman.b(enumhand);
      int i = iblockdata.c(b);
      boolean flag = false;
      if (i >= 5) {
         Item item = itemstack.c();
         if (itemstack.a(Items.rc)) {
            world.a(entityhuman, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.bH, SoundCategory.e, 1.0F, 1.0F);
            a(world, blockposition);
            itemstack.a(1, entityhuman, entityhuman1 -> entityhuman1.d(enumhand));
            flag = true;
            world.a(entityhuman, GameEvent.Q, blockposition);
         } else if (itemstack.a(Items.rs)) {
            itemstack.h(1);
            world.a(entityhuman, entityhuman.dl(), entityhuman.dn(), entityhuman.dr(), SoundEffects.ch, SoundCategory.e, 1.0F, 1.0F);
            if (itemstack.b()) {
               entityhuman.a(enumhand, new ItemStack(Items.vx));
            } else if (!entityhuman.fJ().e(new ItemStack(Items.vx))) {
               entityhuman.a(new ItemStack(Items.vx), false);
            }

            flag = true;
            world.a(entityhuman, GameEvent.A, blockposition);
         }

         if (!world.k_() && flag) {
            entityhuman.b(StatisticList.c.b(item));
         }
      }

      if (flag) {
         if (!BlockCampfire.a(world, blockposition)) {
            if (this.c(world, blockposition)) {
               this.b(world, blockposition);
            }

            this.a(world, iblockdata, blockposition, entityhuman, TileEntityBeehive.ReleaseStatus.c);
         } else {
            this.a(world, iblockdata, blockposition);
         }

         return EnumInteractionResult.a(world.B);
      } else {
         return super.a(iblockdata, world, blockposition, entityhuman, enumhand, movingobjectpositionblock);
      }
   }

   private boolean c(World world, BlockPosition blockposition) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
         return !tileentitybeehive.d();
      } else {
         return false;
      }
   }

   public void a(
      World world,
      IBlockData iblockdata,
      BlockPosition blockposition,
      @Nullable EntityHuman entityhuman,
      TileEntityBeehive.ReleaseStatus tileentitybeehive_releasestatus
   ) {
      this.a(world, iblockdata, blockposition);
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
         tileentitybeehive.a(entityhuman, iblockdata, tileentitybeehive_releasestatus);
      }
   }

   public void a(World world, IBlockData iblockdata, BlockPosition blockposition) {
      world.a(blockposition, iblockdata.a(b, Integer.valueOf(0)), 3);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(b) >= 5) {
         for(int i = 0; i < randomsource.a(1) + 1; ++i) {
            this.a(world, blockposition, iblockdata);
         }
      }
   }

   private void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.r().c() && world.z.i() >= 0.3F) {
         VoxelShape voxelshape = iblockdata.k(world, blockposition);
         double d0 = voxelshape.c(EnumDirection.EnumAxis.b);
         if (d0 >= 1.0 && !iblockdata.a(TagsBlock.al)) {
            double d1 = voxelshape.b(EnumDirection.EnumAxis.b);
            if (d1 > 0.0) {
               this.a(world, blockposition, voxelshape, (double)blockposition.v() + d1 - 0.05);
            } else {
               BlockPosition blockposition1 = blockposition.d();
               IBlockData iblockdata1 = world.a_(blockposition1);
               VoxelShape voxelshape1 = iblockdata1.k(world, blockposition1);
               double d2 = voxelshape1.c(EnumDirection.EnumAxis.b);
               if ((d2 < 1.0 || !iblockdata1.r(world, blockposition1)) && iblockdata1.r().c()) {
                  this.a(world, blockposition, voxelshape, (double)blockposition.v() - 0.05);
               }
            }
         }
      }
   }

   private void a(World world, BlockPosition blockposition, VoxelShape voxelshape, double d0) {
      this.a(
         world,
         (double)blockposition.u() + voxelshape.b(EnumDirection.EnumAxis.a),
         (double)blockposition.u() + voxelshape.c(EnumDirection.EnumAxis.a),
         (double)blockposition.w() + voxelshape.b(EnumDirection.EnumAxis.c),
         (double)blockposition.w() + voxelshape.c(EnumDirection.EnumAxis.c),
         d0
      );
   }

   private void a(World world, double d0, double d1, double d2, double d3, double d4) {
      world.a(Particles.ar, MathHelper.d(world.z.j(), d0, d1), d4, MathHelper.d(world.z.j(), d2, d3), 0.0, 0.0, 0.0);
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.g().g());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b, a);
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityBeehive(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return world.B ? null : a(tileentitytypes, TileEntityTypes.H, TileEntityBeehive::a);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.B && entityhuman.f() && world.W().b(GameRules.g)) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
            ItemStack itemstack = new ItemStack(this);
            int i = iblockdata.c(b);
            boolean flag = !tileentitybeehive.d();
            if (flag || i > 0) {
               if (flag) {
                  NBTTagCompound nbttagcompound = new NBTTagCompound();
                  nbttagcompound.a("Bees", tileentitybeehive.j());
                  ItemBlock.a(itemstack, TileEntityTypes.H, nbttagcompound);
               }

               NBTTagCompound nbttagcompound = new NBTTagCompound();
               nbttagcompound.a("honey_level", i);
               itemstack.a("BlockStateTag", nbttagcompound);
               EntityItem entityitem = new EntityItem(world, (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w(), itemstack);
               entityitem.k();
               world.b(entityitem);
            }
         }
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   @Override
   public List<ItemStack> a(IBlockData iblockdata, LootTableInfo.Builder loottableinfo_builder) {
      Entity entity = loottableinfo_builder.b(LootContextParameters.a);
      if (entity instanceof EntityTNTPrimed
         || entity instanceof EntityCreeper
         || entity instanceof EntityWitherSkull
         || entity instanceof EntityWither
         || entity instanceof EntityMinecartTNT) {
         TileEntity tileentity = loottableinfo_builder.b(LootContextParameters.h);
         if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
            tileentitybeehive.a(null, iblockdata, TileEntityBeehive.ReleaseStatus.c);
         }
      }

      return super.a(iblockdata, loottableinfo_builder);
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      if (generatoraccess.a_(blockposition1).b() instanceof BlockFire) {
         TileEntity tileentity = generatoraccess.c_(blockposition);
         if (tileentity instanceof TileEntityBeehive tileentitybeehive) {
            tileentitybeehive.a(null, iblockdata, TileEntityBeehive.ReleaseStatus.c);
         }
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }
}
