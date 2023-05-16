package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.Particles;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySkull;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyInstrument;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.block.NotePlayEvent;

public class BlockNote extends Block {
   public static final BlockStateEnum<BlockPropertyInstrument> a = BlockProperties.bf;
   public static final BlockStateBoolean b = BlockProperties.w;
   public static final BlockStateInteger c = BlockProperties.aR;
   public static final int d = 3;

   public BlockNote(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, BlockPropertyInstrument.a).a(c, Integer.valueOf(0)).a(b, Boolean.valueOf(false)));
   }

   private static boolean a(GeneratorAccess generatoraccess) {
      return generatoraccess.G().b(FeatureFlags.c);
   }

   private IBlockData b(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
      if (a(generatoraccess)) {
         IBlockData iblockdata1 = generatoraccess.a_(blockposition.c());
         return iblockdata.a(a, BlockPropertyInstrument.a(iblockdata1).orElseGet(() -> BlockPropertyInstrument.b(generatoraccess.a_(blockposition.d()))));
      } else {
         return iblockdata.a(a, BlockPropertyInstrument.b(generatoraccess.a_(blockposition.d())));
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.b(blockactioncontext.q(), blockactioncontext.a(), this.o());
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
      boolean flag = a(generatoraccess) ? enumdirection.o() == EnumDirection.EnumAxis.b : enumdirection == EnumDirection.a;
      return flag
         ? this.b(generatoraccess, blockposition, iblockdata)
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      boolean flag1 = world.r(blockposition);
      if (flag1 != iblockdata.c(b)) {
         if (flag1) {
            this.a(null, iblockdata, world, blockposition);
            iblockdata = world.a_(blockposition);
         }

         world.a(blockposition, iblockdata.a(b, Boolean.valueOf(flag1)), 3);
      }
   }

   private void a(@Nullable Entity entity, IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (!iblockdata.c(a).e() || world.a_(blockposition.c()).h()) {
         NotePlayEvent event = CraftEventFactory.callNotePlayEvent(world, blockposition, iblockdata.c(a), iblockdata.c(c));
         if (event.isCancelled()) {
            return;
         }

         world.a(blockposition, this, 0, 0);
         world.a(entity, GameEvent.J, blockposition);
      }
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
      if (a(world)) {
         ItemStack itemstack = entityhuman.b(enumhand);
         if (itemstack.a(TagsItem.aF) && movingobjectpositionblock.b() == EnumDirection.b) {
            return EnumInteractionResult.d;
         }
      }

      if (world.B) {
         return EnumInteractionResult.a;
      } else {
         iblockdata = iblockdata.a(c);
         world.a(blockposition, iblockdata, 3);
         this.a(entityhuman, iblockdata, world, blockposition);
         entityhuman.a(StatisticList.ag);
         return EnumInteractionResult.b;
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
      if (!world.B) {
         this.a(entityhuman, iblockdata, world, blockposition);
         entityhuman.a(StatisticList.af);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
      BlockPropertyInstrument blockpropertyinstrument = iblockdata.c(a);
      float f;
      if (blockpropertyinstrument.b()) {
         int k = iblockdata.c(c);
         f = (float)Math.pow(2.0, (double)(k - 12) / 12.0);
         world.a(Particles.X, (double)blockposition.u() + 0.5, (double)blockposition.v() + 1.2, (double)blockposition.w() + 0.5, (double)k / 24.0, 0.0, 0.0);
      } else {
         f = 1.0F;
      }

      Holder holder;
      if (blockpropertyinstrument.d()) {
         MinecraftKey minecraftkey = this.a(world, blockposition);
         if (minecraftkey == null) {
            return false;
         }

         holder = Holder.a(SoundEffect.a(minecraftkey));
      } else {
         holder = blockpropertyinstrument.a();
      }

      world.a(
         null,
         (double)blockposition.u() + 0.5,
         (double)blockposition.v() + 0.5,
         (double)blockposition.w() + 0.5,
         holder,
         SoundCategory.c,
         3.0F,
         f,
         world.z.g()
      );
      return true;
   }

   @Nullable
   private MinecraftKey a(World world, BlockPosition blockposition) {
      TileEntity tileentity = world.c_(blockposition.c());
      return tileentity instanceof TileEntitySkull tileentityskull ? tileentityskull.f() : null;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c);
   }
}
