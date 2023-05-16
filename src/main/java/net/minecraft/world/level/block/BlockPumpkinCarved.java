package net.minecraft.world.level.block;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.animal.EntitySnowman;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.pattern.ShapeDetector;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBlock;
import net.minecraft.world.level.block.state.pattern.ShapeDetectorBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.predicate.MaterialPredicate;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.material.Material;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockPumpkinCarved extends BlockFacingHorizontal implements Equipable {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   @Nullable
   private ShapeDetector b;
   @Nullable
   private ShapeDetector c;
   @Nullable
   private ShapeDetector d;
   @Nullable
   private ShapeDetector e;
   private static final Predicate<IBlockData> f = iblockdata -> iblockdata != null && (iblockdata.a(Blocks.ee) || iblockdata.a(Blocks.ef));

   protected BlockPumpkinCarved(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c));
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b())) {
         this.a(world, blockposition);
      }
   }

   public boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
      return this.b().a(iworldreader, blockposition) != null || this.w().a(iworldreader, blockposition) != null;
   }

   private void a(World world, BlockPosition blockposition) {
      ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection = this.v().a(world, blockposition);
      if (shapedetector_shapedetectorcollection != null) {
         EntitySnowman entitysnowman = EntityTypes.aO.a(world);
         if (entitysnowman != null) {
            a(world, shapedetector_shapedetectorcollection, entitysnowman, shapedetector_shapedetectorcollection.a(0, 2, 0).d());
         }
      } else {
         ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection1 = this.x().a(world, blockposition);
         if (shapedetector_shapedetectorcollection1 != null) {
            EntityIronGolem entityirongolem = EntityTypes.ac.a(world);
            if (entityirongolem != null) {
               entityirongolem.x(true);
               a(world, shapedetector_shapedetectorcollection1, entityirongolem, shapedetector_shapedetectorcollection1.a(1, 2, 0).d());
            }
         }
      }
   }

   private static void a(World world, ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection, Entity entity, BlockPosition blockposition) {
      entity.b((double)blockposition.u() + 0.5, (double)blockposition.v() + 0.05, (double)blockposition.w() + 0.5, 0.0F, 0.0F);
      if (world.addFreshEntity(entity, SpawnReason.BUILD_IRONGOLEM)) {
         a(world, shapedetector_shapedetectorcollection);

         for(EntityPlayer entityplayer : world.a(EntityPlayer.class, entity.cD().g(5.0))) {
            CriterionTriggers.n.a(entityplayer, entity);
         }

         b(world, shapedetector_shapedetectorcollection);
      }
   }

   public static void a(World world, ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection) {
      for(int i = 0; i < shapedetector_shapedetectorcollection.d(); ++i) {
         for(int j = 0; j < shapedetector_shapedetectorcollection.e(); ++j) {
            ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(i, j, 0);
            world.a(shapedetectorblock.d(), Blocks.a.o(), 2);
            world.c(2001, shapedetectorblock.d(), Block.i(shapedetectorblock.a()));
         }
      }
   }

   public static void b(World world, ShapeDetector.ShapeDetectorCollection shapedetector_shapedetectorcollection) {
      for(int i = 0; i < shapedetector_shapedetectorcollection.d(); ++i) {
         for(int j = 0; j < shapedetector_shapedetectorcollection.e(); ++j) {
            ShapeDetectorBlock shapedetectorblock = shapedetector_shapedetectorcollection.a(i, j, 0);
            world.b(shapedetectorblock.d(), Blocks.a);
         }
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.g().g());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   private ShapeDetector b() {
      if (this.b == null) {
         this.b = ShapeDetectorBuilder.a().a(" ", "#", "#").a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.dO))).b();
      }

      return this.b;
   }

   private ShapeDetector v() {
      if (this.c == null) {
         this.c = ShapeDetectorBuilder.a().a("^", "#", "#").a('^', ShapeDetectorBlock.a(f)).a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.dO))).b();
      }

      return this.c;
   }

   private ShapeDetector w() {
      if (this.d == null) {
         this.d = ShapeDetectorBuilder.a()
            .a("~ ~", "###", "~#~")
            .a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.ch)))
            .a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.a)))
            .b();
      }

      return this.d;
   }

   private ShapeDetector x() {
      if (this.e == null) {
         this.e = ShapeDetectorBuilder.a()
            .a("~^~", "###", "~#~")
            .a('^', ShapeDetectorBlock.a(f))
            .a('#', ShapeDetectorBlock.a(BlockStatePredicate.a(Blocks.ch)))
            .a('~', ShapeDetectorBlock.a(MaterialPredicate.a(Material.a)))
            .b();
      }

      return this.e;
   }

   @Override
   public EnumItemSlot g() {
      return EnumItemSlot.f;
   }
}
