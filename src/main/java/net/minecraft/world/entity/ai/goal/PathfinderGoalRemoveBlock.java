package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.core.particles.ParticleParamItem;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.entity.EntityInteractEvent;

public class PathfinderGoalRemoveBlock extends PathfinderGoalGotoTarget {
   private final Block g;
   private final EntityInsentient h;
   private int i;
   private static final int j = 20;

   public PathfinderGoalRemoveBlock(Block block, EntityCreature entitycreature, double d0, int i) {
      super(entitycreature, d0, 24, i);
      this.g = block;
      this.h = entitycreature;
   }

   @Override
   public boolean a() {
      if (!this.h.H.W().b(GameRules.c)) {
         return false;
      } else if (this.c > 0) {
         --this.c;
         return false;
      } else if (this.n()) {
         this.c = b(20);
         return true;
      } else {
         this.c = this.a(this.a);
         return false;
      }
   }

   @Override
   public void d() {
      super.d();
      this.h.aa = 1.0F;
   }

   @Override
   public void c() {
      super.c();
      this.i = 0;
   }

   public void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
   }

   public void a(World world, BlockPosition blockposition) {
   }

   @Override
   public void e() {
      super.e();
      World world = this.h.H;
      BlockPosition blockposition = this.h.dg();
      BlockPosition blockposition1 = this.a(blockposition, world);
      RandomSource randomsource = this.h.dZ();
      if (this.m() && blockposition1 != null) {
         if (this.i > 0) {
            Vec3D vec3d = this.h.dj();
            this.h.o(vec3d.c, 0.3, vec3d.e);
            if (!world.B) {
               double d0 = 0.08;
               ((WorldServer)world)
                  .a(
                     new ParticleParamItem(Particles.Q, new ItemStack(Items.pZ)),
                     (double)blockposition1.u() + 0.5,
                     (double)blockposition1.v() + 0.7,
                     (double)blockposition1.w() + 0.5,
                     3,
                     ((double)randomsource.i() - 0.5) * 0.08,
                     ((double)randomsource.i() - 0.5) * 0.08,
                     ((double)randomsource.i() - 0.5) * 0.08,
                     0.15F
                  );
            }
         }

         if (this.i % 2 == 0) {
            Vec3D vec3d = this.h.dj();
            this.h.o(vec3d.c, -0.3, vec3d.e);
            if (this.i % 6 == 0) {
               this.a((GeneratorAccess)world, this.e);
            }
         }

         if (this.i > 60) {
            EntityInteractEvent event = new EntityInteractEvent(this.h.getBukkitEntity(), CraftBlock.at(world, blockposition1));
            world.getCraftServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
               return;
            }

            world.a(blockposition1, false);
            if (!world.B) {
               for(int i = 0; i < 20; ++i) {
                  double d0 = randomsource.k() * 0.02;
                  double d1 = randomsource.k() * 0.02;
                  double d2 = randomsource.k() * 0.02;
                  ((WorldServer)world)
                     .a(Particles.Y, (double)blockposition1.u() + 0.5, (double)blockposition1.v(), (double)blockposition1.w() + 0.5, 1, d0, d1, d2, 0.15F);
               }

               this.a(world, blockposition1);
            }
         }

         ++this.i;
      }
   }

   @Nullable
   private BlockPosition a(BlockPosition blockposition, IBlockAccess iblockaccess) {
      if (iblockaccess.a_(blockposition).a(this.g)) {
         return blockposition;
      } else {
         BlockPosition[] ablockposition = new BlockPosition[]{
            blockposition.d(), blockposition.g(), blockposition.h(), blockposition.e(), blockposition.f(), blockposition.d().d()
         };

         for(BlockPosition blockposition1 : ablockposition) {
            if (iblockaccess.a_(blockposition1).a(this.g)) {
               return blockposition1;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
      IChunkAccess ichunkaccess = iworldreader.a(SectionPosition.a(blockposition.u()), SectionPosition.a(blockposition.w()), ChunkStatus.o, false);
      return ichunkaccess == null
         ? false
         : ichunkaccess.a_(blockposition).a(this.g) && ichunkaccess.a_(blockposition.c()).h() && ichunkaccess.a_(blockposition.b(2)).h();
   }
}
