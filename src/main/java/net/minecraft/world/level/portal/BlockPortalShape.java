package net.minecraft.world.level.portal;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.BlockPortal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftPortalEvent;
import org.bukkit.craftbukkit.v1_19_R3.util.BlockStateListPopulator;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.event.world.PortalCreateEvent.CreateReason;

public class BlockPortalShape {
   private static final int c = 2;
   public static final int a = 21;
   private static final int d = 3;
   public static final int b = 21;
   private static final BlockBase.f e = (iblockdata, iblockaccess, blockposition) -> iblockdata.a(Blocks.cn);
   private static final float f = 4.0F;
   private static final double g = 1.0;
   private final GeneratorAccess h;
   private final EnumDirection.EnumAxis i;
   private final EnumDirection j;
   private int k;
   @Nullable
   private BlockPosition l;
   private int m;
   private final int n;
   BlockStateListPopulator blocks;

   public static Optional<BlockPortalShape> a(GeneratorAccess generatoraccess, BlockPosition blockposition, EnumDirection.EnumAxis enumdirection_enumaxis) {
      return a(generatoraccess, blockposition, blockportalshape -> blockportalshape.a() && blockportalshape.k == 0, enumdirection_enumaxis);
   }

   public static Optional<BlockPortalShape> a(
      GeneratorAccess generatoraccess, BlockPosition blockposition, Predicate<BlockPortalShape> predicate, EnumDirection.EnumAxis enumdirection_enumaxis
   ) {
      Optional<BlockPortalShape> optional = Optional.of(new BlockPortalShape(generatoraccess, blockposition, enumdirection_enumaxis)).filter(predicate);
      if (optional.isPresent()) {
         return optional;
      } else {
         EnumDirection.EnumAxis enumdirection_enumaxis1 = enumdirection_enumaxis == EnumDirection.EnumAxis.a
            ? EnumDirection.EnumAxis.c
            : EnumDirection.EnumAxis.a;
         return Optional.of(new BlockPortalShape(generatoraccess, blockposition, enumdirection_enumaxis1)).filter(predicate);
      }
   }

   public BlockPortalShape(GeneratorAccess generatoraccess, BlockPosition blockposition, EnumDirection.EnumAxis enumdirection_enumaxis) {
      this.blocks = new BlockStateListPopulator(generatoraccess.getMinecraftWorld());
      this.h = generatoraccess;
      this.i = enumdirection_enumaxis;
      this.j = enumdirection_enumaxis == EnumDirection.EnumAxis.a ? EnumDirection.e : EnumDirection.d;
      this.l = this.a(blockposition);
      if (this.l == null) {
         this.l = blockposition;
         this.n = 1;
         this.m = 1;
      } else {
         this.n = this.d();
         if (this.n > 0) {
            this.m = this.e();
         }
      }
   }

   @Nullable
   private BlockPosition a(BlockPosition blockposition) {
      int i = Math.max(this.h.v_(), blockposition.v() - 21);

      while(blockposition.v() > i && a(this.h.a_(blockposition.d()))) {
         blockposition = blockposition.d();
      }

      EnumDirection enumdirection = this.j.g();
      int j = this.a(blockposition, enumdirection) - 1;
      return j < 0 ? null : blockposition.a(enumdirection, j);
   }

   private int d() {
      int i = this.a(this.l, this.j);
      return i >= 2 && i <= 21 ? i : 0;
   }

   private int a(BlockPosition blockposition, EnumDirection enumdirection) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int i = 0; i <= 21; ++i) {
         blockposition_mutableblockposition.g(blockposition).c(enumdirection, i);
         IBlockData iblockdata = this.h.a_(blockposition_mutableblockposition);
         if (!a(iblockdata)) {
            if (e.test(iblockdata, this.h, blockposition_mutableblockposition)) {
               this.blocks.a(blockposition_mutableblockposition, iblockdata, 18);
               return i;
            }
            break;
         }

         IBlockData iblockdata1 = this.h.a_(blockposition_mutableblockposition.c(EnumDirection.a));
         if (!e.test(iblockdata1, this.h, blockposition_mutableblockposition)) {
            break;
         }

         this.blocks.a(blockposition_mutableblockposition, iblockdata1, 18);
      }

      return 0;
   }

   private int e() {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
      int i = this.a(blockposition_mutableblockposition);
      return i >= 3 && i <= 21 && this.a(blockposition_mutableblockposition, i) ? i : 0;
   }

   private boolean a(BlockPosition.MutableBlockPosition blockposition_mutableblockposition, int i) {
      for(int j = 0; j < this.n; ++j) {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition1 = blockposition_mutableblockposition.g(this.l)
            .c(EnumDirection.b, i)
            .c(this.j, j);
         if (!e.test(this.h.a_(blockposition_mutableblockposition1), this.h, blockposition_mutableblockposition1)) {
            return false;
         }

         this.blocks.a(blockposition_mutableblockposition1, this.h.a_(blockposition_mutableblockposition1), 18);
      }

      return true;
   }

   private int a(BlockPosition.MutableBlockPosition blockposition_mutableblockposition) {
      for(int i = 0; i < 21; ++i) {
         blockposition_mutableblockposition.g(this.l).c(EnumDirection.b, i).c(this.j, -1);
         if (!e.test(this.h.a_(blockposition_mutableblockposition), this.h, blockposition_mutableblockposition)) {
            return i;
         }

         blockposition_mutableblockposition.g(this.l).c(EnumDirection.b, i).c(this.j, this.n);
         if (!e.test(this.h.a_(blockposition_mutableblockposition), this.h, blockposition_mutableblockposition)) {
            return i;
         }

         for(int j = 0; j < this.n; ++j) {
            blockposition_mutableblockposition.g(this.l).c(EnumDirection.b, i).c(this.j, j);
            IBlockData iblockdata = this.h.a_(blockposition_mutableblockposition);
            if (!a(iblockdata)) {
               return i;
            }

            if (iblockdata.a(Blocks.ed)) {
               ++this.k;
            }
         }

         this.blocks.a(blockposition_mutableblockposition.g(this.l).c(EnumDirection.b, i).c(this.j, -1), this.h.a_(blockposition_mutableblockposition), 18);
         this.blocks
            .a(blockposition_mutableblockposition.g(this.l).c(EnumDirection.b, i).c(this.j, this.n), this.h.a_(blockposition_mutableblockposition), 18);
      }

      return 21;
   }

   private static boolean a(IBlockData iblockdata) {
      return iblockdata.h() || iblockdata.a(TagsBlock.aH) || iblockdata.a(Blocks.ed);
   }

   public boolean a() {
      return this.l != null && this.n >= 2 && this.n <= 21 && this.m >= 3 && this.m <= 21;
   }

   public boolean createPortalBlocks() {
      World bworld = this.h.getMinecraftWorld().getWorld();
      IBlockData iblockdata = Blocks.ed.o().a(BlockPortal.a, this.i);
      BlockPosition.a(this.l, this.l.a(EnumDirection.b, this.m - 1).a(this.j, this.n - 1))
         .forEach(blockposition -> this.blocks.a(blockposition, iblockdata, 18));
      PortalCreateEvent event = new PortalCreateEvent(this.blocks.getList(), bworld, null, CreateReason.FIRE);
      this.h.getMinecraftWorld().n().server.getPluginManager().callEvent(event);
      if (event.isCancelled()) {
         return false;
      } else {
         BlockPosition.a(this.l, this.l.a(EnumDirection.b, this.m - 1).a(this.j, this.n - 1))
            .forEach(blockposition -> this.h.a(blockposition, iblockdata, 18));
         return true;
      }
   }

   public boolean c() {
      return this.a() && this.k == this.n * this.m;
   }

   public static Vec3D a(BlockUtil.Rectangle blockutil_rectangle, EnumDirection.EnumAxis enumdirection_enumaxis, Vec3D vec3d, EntitySize entitysize) {
      double d0 = (double)blockutil_rectangle.b - (double)entitysize.a;
      double d1 = (double)blockutil_rectangle.c - (double)entitysize.b;
      BlockPosition blockposition = blockutil_rectangle.a;
      double d2;
      if (d0 > 0.0) {
         float f = (float)blockposition.a(enumdirection_enumaxis) + entitysize.a / 2.0F;
         d2 = MathHelper.a(MathHelper.c(vec3d.a(enumdirection_enumaxis) - (double)f, 0.0, d0), 0.0, 1.0);
      } else {
         d2 = 0.5;
      }

      double d3;
      if (d1 > 0.0) {
         EnumDirection.EnumAxis enumdirection_enumaxis1 = EnumDirection.EnumAxis.b;
         d3 = MathHelper.a(MathHelper.c(vec3d.a(enumdirection_enumaxis1) - (double)blockposition.a(enumdirection_enumaxis1), 0.0, d1), 0.0, 1.0);
      } else {
         d3 = 0.0;
      }

      EnumDirection.EnumAxis enumdirection_enumaxis1 = enumdirection_enumaxis == EnumDirection.EnumAxis.a
         ? EnumDirection.EnumAxis.c
         : EnumDirection.EnumAxis.a;
      double d4 = vec3d.a(enumdirection_enumaxis1) - ((double)blockposition.a(enumdirection_enumaxis1) + 0.5);
      return new Vec3D(d2, d3, d4);
   }

   public static ShapeDetectorShape createPortalInfo(
      WorldServer worldserver,
      BlockUtil.Rectangle blockutil_rectangle,
      EnumDirection.EnumAxis enumdirection_enumaxis,
      Vec3D vec3d,
      Entity entity,
      Vec3D vec3d1,
      float f,
      float f1,
      CraftPortalEvent portalEventInfo
   ) {
      BlockPosition blockposition = blockutil_rectangle.a;
      IBlockData iblockdata = worldserver.a_(blockposition);
      EnumDirection.EnumAxis enumdirection_enumaxis1 = iblockdata.d(BlockProperties.H).orElse(EnumDirection.EnumAxis.a);
      double d0 = (double)blockutil_rectangle.b;
      double d1 = (double)blockutil_rectangle.c;
      EntitySize entitysize = entity.a(entity.al());
      int i = enumdirection_enumaxis == enumdirection_enumaxis1 ? 0 : 90;
      Vec3D vec3d2 = enumdirection_enumaxis == enumdirection_enumaxis1 ? vec3d1 : new Vec3D(vec3d1.e, vec3d1.d, -vec3d1.c);
      double d2 = (double)entitysize.a / 2.0 + (d0 - (double)entitysize.a) * vec3d.a();
      double d3 = (d1 - (double)entitysize.b) * vec3d.b();
      double d4 = 0.5 + vec3d.c();
      boolean flag = enumdirection_enumaxis1 == EnumDirection.EnumAxis.a;
      Vec3D vec3d3 = new Vec3D((double)blockposition.u() + (flag ? d2 : d4), (double)blockposition.v() + d3, (double)blockposition.w() + (flag ? d4 : d2));
      Vec3D vec3d4 = a(vec3d3, worldserver, entity, entitysize);
      return new ShapeDetectorShape(vec3d4, vec3d2, f + (float)i, f1, worldserver, portalEventInfo);
   }

   private static Vec3D a(Vec3D vec3d, WorldServer worldserver, Entity entity, EntitySize entitysize) {
      if (entitysize.a <= 4.0F && entitysize.b <= 4.0F) {
         double d0 = (double)entitysize.b / 2.0;
         Vec3D vec3d1 = vec3d.b(0.0, d0, 0.0);
         VoxelShape voxelshape = VoxelShapes.a(AxisAlignedBB.a(vec3d1, (double)entitysize.a, 0.0, (double)entitysize.a).b(0.0, 1.0, 0.0).g(1.0E-6));
         Optional<Vec3D> optional = worldserver.a(entity, voxelshape, vec3d1, (double)entitysize.a, (double)entitysize.b, (double)entitysize.a);
         Optional<Vec3D> optional1 = optional.map(vec3d2 -> vec3d2.a(0.0, d0, 0.0));
         return optional1.orElse(vec3d);
      } else {
         return vec3d;
      }
   }
}
