package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.portal.BlockPortalShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockPortal extends Block {
   public static final BlockStateEnum<EnumDirection.EnumAxis> a = BlockProperties.H;
   protected static final int b = 2;
   protected static final VoxelShape c = Block.a(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape d = Block.a(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);

   public BlockPortal(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.EnumAxis.a));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      switch((EnumDirection.EnumAxis)iblockdata.c(a)) {
         case a:
         case b:
         default:
            return c;
         case c:
            return d;
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.spigotConfig.enableZombiePigmenPortalSpawns
         && worldserver.q_().j()
         && worldserver.W().b(GameRules.e)
         && randomsource.a(2000) < worldserver.ah().a()) {
         while(worldserver.a_(blockposition).a(this)) {
            blockposition = blockposition.d();
         }

         if (worldserver.a_(blockposition).a(worldserver, blockposition, EntityTypes.bs)) {
            Entity entity = EntityTypes.bs.spawn(worldserver, blockposition.c(), EnumMobSpawn.d, SpawnReason.NETHER_PORTAL);
            if (entity != null) {
               entity.aq();
            }
         }
      }
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
      EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.o();
      EnumDirection.EnumAxis enumdirection_enumaxis1 = iblockdata.c(a);
      boolean flag = enumdirection_enumaxis1 != enumdirection_enumaxis && enumdirection_enumaxis.d();
      return !flag && !iblockdata1.a(this) && !new BlockPortalShape(generatoraccess, blockposition, enumdirection_enumaxis1).c()
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (entity.co()) {
         EntityPortalEnterEvent event = new EntityPortalEnterEvent(
            entity.getBukkitEntity(), new Location(world.getWorld(), (double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w())
         );
         world.getCraftServer().getPluginManager().callEvent(event);
         entity.d(blockposition);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (randomsource.a(100) == 0) {
         world.a(
            (double)blockposition.u() + 0.5,
            (double)blockposition.v() + 0.5,
            (double)blockposition.w() + 0.5,
            SoundEffects.sE,
            SoundCategory.e,
            0.5F,
            randomsource.i() * 0.4F + 0.8F,
            false
         );
      }

      for(int i = 0; i < 4; ++i) {
         double d0 = (double)blockposition.u() + randomsource.j();
         double d1 = (double)blockposition.v() + randomsource.j();
         double d2 = (double)blockposition.w() + randomsource.j();
         double d3 = ((double)randomsource.i() - 0.5) * 0.5;
         double d4 = ((double)randomsource.i() - 0.5) * 0.5;
         double d5 = ((double)randomsource.i() - 0.5) * 0.5;
         int j = randomsource.a(2) * 2 - 1;
         if (!world.a_(blockposition.g()).a(this) && !world.a_(blockposition.h()).a(this)) {
            d0 = (double)blockposition.u() + 0.5 + 0.25 * (double)j;
            d3 = (double)(randomsource.i() * 2.0F * (float)j);
         } else {
            d2 = (double)blockposition.w() + 0.5 + 0.25 * (double)j;
            d5 = (double)(randomsource.i() * 2.0F * (float)j);
         }

         world.a(Particles.Z, d0, d1, d2, d3, d4, d5);
      }
   }

   @Override
   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return ItemStack.b;
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      switch(enumblockrotation) {
         case b:
         case d:
            switch((EnumDirection.EnumAxis)iblockdata.c(a)) {
               case a:
                  return iblockdata.a(a, EnumDirection.EnumAxis.c);
               case b:
               default:
                  return iblockdata;
               case c:
                  return iblockdata.a(a, EnumDirection.EnumAxis.a);
            }
         case c:
         default:
            return iblockdata;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }
}
