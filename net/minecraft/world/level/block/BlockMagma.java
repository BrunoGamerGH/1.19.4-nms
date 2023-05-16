package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockMagma extends Block {
   private static final int a = 20;

   public BlockMagma(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
      if (!entity.bP() && entity instanceof EntityLiving && !EnchantmentManager.j((EntityLiving)entity)) {
         CraftEventFactory.blockDamage = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
         entity.a(world.af().e(), 1.0F);
         CraftEventFactory.blockDamage = null;
      }

      super.a(world, blockposition, iblockdata, entity);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      BlockBubbleColumn.b(worldserver, blockposition.c(), iblockdata);
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
      if (enumdirection == EnumDirection.b && iblockdata1.a(Blocks.G)) {
         generatoraccess.a(blockposition, this, 20);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      BlockPosition blockposition1 = blockposition.c();
      if (worldserver.b_(blockposition).a(TagsFluid.a)) {
         worldserver.a(null, blockposition, SoundEffects.hJ, SoundCategory.e, 0.5F, 2.6F + (worldserver.z.i() - worldserver.z.i()) * 0.8F);
         worldserver.a(
            Particles.U, (double)blockposition1.u() + 0.5, (double)blockposition1.v() + 0.25, (double)blockposition1.w() + 0.5, 8, 0.5, 0.25, 0.5, 0.0
         );
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      world.a(blockposition, this, 20);
   }
}
