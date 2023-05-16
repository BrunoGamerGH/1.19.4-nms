package net.minecraft.world.level.block.piston;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.GameProfileSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyPistonType;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class TileEntityPiston extends TileEntity {
   private static final int b = 2;
   private static final double c = 0.01;
   public static final double a = 0.51;
   private IBlockData d = Blocks.a.o();
   private EnumDirection e;
   private boolean f;
   private boolean g;
   private static final ThreadLocal<EnumDirection> h = ThreadLocal.withInitial(() -> null);
   private float i;
   private float j;
   private long k;
   private int l;

   public TileEntityPiston(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.k, var0, var1);
   }

   public TileEntityPiston(BlockPosition var0, IBlockData var1, IBlockData var2, EnumDirection var3, boolean var4, boolean var5) {
      this(var0, var1);
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public boolean c() {
      return this.f;
   }

   public EnumDirection d() {
      return this.e;
   }

   public boolean f() {
      return this.g;
   }

   public float a(float var0) {
      if (var0 > 1.0F) {
         var0 = 1.0F;
      }

      return MathHelper.i(var0, this.j, this.i);
   }

   public float b(float var0) {
      return (float)this.e.j() * this.e(this.a(var0));
   }

   public float c(float var0) {
      return (float)this.e.k() * this.e(this.a(var0));
   }

   public float d(float var0) {
      return (float)this.e.l() * this.e(this.a(var0));
   }

   private float e(float var0) {
      return this.f ? var0 - 1.0F : 1.0F - var0;
   }

   private IBlockData w() {
      return !this.c() && this.f() && this.d.b() instanceof BlockPiston
         ? Blocks.by
            .o()
            .a(BlockPistonExtension.c, Boolean.valueOf(this.i > 0.25F))
            .a(BlockPistonExtension.b, this.d.a(Blocks.bq) ? BlockPropertyPistonType.b : BlockPropertyPistonType.a)
            .a(BlockPistonExtension.a, this.d.c(BlockPiston.a))
         : this.d;
   }

   private static void a(World var0, BlockPosition var1, float var2, TileEntityPiston var3) {
      EnumDirection var4 = var3.g();
      double var5 = (double)(var2 - var3.i);
      VoxelShape var7 = var3.w().k(var0, var1);
      if (!var7.b()) {
         AxisAlignedBB var8 = a(var1, var7.a(), var3);
         List<Entity> var9 = var0.a_(null, PistonUtil.a(var8, var4, var5).b(var8));
         if (!var9.isEmpty()) {
            List<AxisAlignedBB> var10 = var7.d();
            boolean var11 = var3.d.a(Blocks.hU);
            Iterator var12 = var9.iterator();

            while(true) {
               Entity var13;
               while(true) {
                  if (!var12.hasNext()) {
                     return;
                  }

                  var13 = (Entity)var12.next();
                  if (var13.C_() != EnumPistonReaction.d) {
                     if (!var11) {
                        break;
                     }

                     if (!(var13 instanceof EntityPlayer)) {
                        Vec3D var14 = var13.dj();
                        double var15 = var14.c;
                        double var17 = var14.d;
                        double var19 = var14.e;
                        switch(var4.o()) {
                           case a:
                              var15 = (double)var4.j();
                              break;
                           case b:
                              var17 = (double)var4.k();
                              break;
                           case c:
                              var19 = (double)var4.l();
                        }

                        var13.o(var15, var17, var19);
                        break;
                     }
                  }
               }

               double var14 = 0.0;

               for(AxisAlignedBB var17 : var10) {
                  AxisAlignedBB var18 = PistonUtil.a(a(var1, var17, var3), var4, var5);
                  AxisAlignedBB var19 = var13.cD();
                  if (var18.c(var19)) {
                     var14 = Math.max(var14, a(var18, var4, var19));
                     if (var14 >= var5) {
                        break;
                     }
                  }
               }

               if (!(var14 <= 0.0)) {
                  var14 = Math.min(var14, var5) + 0.01;
                  a(var4, var13, var14, var4);
                  if (!var3.f && var3.g) {
                     a(var1, var13, var4, var5);
                  }
               }
            }
         }
      }
   }

   private static void a(EnumDirection var0, Entity var1, double var2, EnumDirection var4) {
      h.set(var0);
      var1.a(EnumMoveType.c, new Vec3D(var2 * (double)var4.j(), var2 * (double)var4.k(), var2 * (double)var4.l()));
      h.set(null);
   }

   private static void b(World var0, BlockPosition var1, float var2, TileEntityPiston var3) {
      if (var3.x()) {
         EnumDirection var4 = var3.g();
         if (var4.o().d()) {
            double var5 = var3.d.k(var0, var1).c(EnumDirection.EnumAxis.b);
            AxisAlignedBB var7 = a(var1, new AxisAlignedBB(0.0, var5, 0.0, 1.0, 1.5000000999999998, 1.0), var3);
            double var8 = (double)(var2 - var3.i);

            for(Entity var12 : var0.a((Entity)null, var7, var1x -> a(var7, var1x))) {
               a(var4, var12, var8, var4);
            }
         }
      }
   }

   private static boolean a(AxisAlignedBB var0, Entity var1) {
      return var1.C_() == EnumPistonReaction.a && var1.ax() && var1.dl() >= var0.a && var1.dl() <= var0.d && var1.dr() >= var0.c && var1.dr() <= var0.f;
   }

   private boolean x() {
      return this.d.a(Blocks.pc);
   }

   public EnumDirection g() {
      return this.f ? this.e : this.e.g();
   }

   private static double a(AxisAlignedBB var0, EnumDirection var1, AxisAlignedBB var2) {
      switch(var1) {
         case f:
            return var0.d - var2.a;
         case e:
            return var2.d - var0.a;
         case b:
         default:
            return var0.e - var2.b;
         case a:
            return var2.e - var0.b;
         case d:
            return var0.f - var2.c;
         case c:
            return var2.f - var0.c;
      }
   }

   private static AxisAlignedBB a(BlockPosition var0, AxisAlignedBB var1, TileEntityPiston var2) {
      double var3 = (double)var2.e(var2.i);
      return var1.d((double)var0.u() + var3 * (double)var2.e.j(), (double)var0.v() + var3 * (double)var2.e.k(), (double)var0.w() + var3 * (double)var2.e.l());
   }

   private static void a(BlockPosition var0, Entity var1, EnumDirection var2, double var3) {
      AxisAlignedBB var5 = var1.cD();
      AxisAlignedBB var6 = VoxelShapes.b().a().a(var0);
      if (var5.c(var6)) {
         EnumDirection var7 = var2.g();
         double var8 = a(var6, var7, var5) + 0.01;
         double var10 = a(var6, var7, var5.a(var6)) + 0.01;
         if (Math.abs(var8 - var10) < 0.01) {
            var8 = Math.min(var8, var3) + 0.01;
            a(var2, var1, var8, var7);
         }
      }
   }

   public IBlockData i() {
      return this.d;
   }

   public void j() {
      if (this.o != null && (this.j < 1.0F || this.o.B)) {
         this.i = 1.0F;
         this.j = this.i;
         this.o.n(this.p);
         this.ar_();
         if (this.o.a_(this.p).a(Blocks.bP)) {
            IBlockData var0;
            if (this.g) {
               var0 = Blocks.a.o();
            } else {
               var0 = Block.b(this.d, this.o, this.p);
            }

            this.o.a(this.p, var0, 3);
            this.o.a(this.p, var0.b(), this.p);
         }
      }
   }

   public static void a(World var0, BlockPosition var1, IBlockData var2, TileEntityPiston var3) {
      var3.k = var0.U();
      var3.j = var3.i;
      if (var3.j >= 1.0F) {
         if (var0.B && var3.l < 5) {
            ++var3.l;
         } else {
            var0.n(var1);
            var3.ar_();
            if (var0.a_(var1).a(Blocks.bP)) {
               IBlockData var4 = Block.b(var3.d, var0, var1);
               if (var4.h()) {
                  var0.a(var1, var3.d, 84);
                  Block.a(var3.d, var4, var0, var1, 3);
               } else {
                  if (var4.b(BlockProperties.C) && var4.c(BlockProperties.C)) {
                     var4 = var4.a(BlockProperties.C, Boolean.valueOf(false));
                  }

                  var0.a(var1, var4, 67);
                  var0.a(var1, var4.b(), var1);
               }
            }
         }
      } else {
         float var4 = var3.i + 0.5F;
         a(var0, var1, var4, var3);
         b(var0, var1, var4, var3);
         var3.i = var4;
         if (var3.i >= 1.0F) {
            var3.i = 1.0F;
         }
      }
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      HolderGetter<Block> var1 = (HolderGetter<Block>)(this.o != null ? this.o.a(Registries.e) : BuiltInRegistries.f.p());
      this.d = GameProfileSerializer.a(var1, var0.p("blockState"));
      this.e = EnumDirection.a(var0.h("facing"));
      this.i = var0.j("progress");
      this.j = this.i;
      this.f = var0.q("extending");
      this.g = var0.q("source");
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("blockState", GameProfileSerializer.a(this.d));
      var0.a("facing", this.e.d());
      var0.a("progress", this.j);
      var0.a("extending", this.f);
      var0.a("source", this.g);
   }

   public VoxelShape a(IBlockAccess var0, BlockPosition var1) {
      VoxelShape var2;
      if (!this.f && this.g && this.d.b() instanceof BlockPiston) {
         var2 = this.d.a(BlockPiston.b, Boolean.valueOf(true)).k(var0, var1);
      } else {
         var2 = VoxelShapes.a();
      }

      EnumDirection var3 = h.get();
      if ((double)this.i < 1.0 && var3 == this.g()) {
         return var2;
      } else {
         IBlockData var4;
         if (this.f()) {
            var4 = Blocks.by.o().a(BlockPistonExtension.a, this.e).a(BlockPistonExtension.c, Boolean.valueOf(this.f != 1.0F - this.i < 0.25F));
         } else {
            var4 = this.d;
         }

         float var5 = this.e(this.i);
         double var6 = (double)((float)this.e.j() * var5);
         double var8 = (double)((float)this.e.k() * var5);
         double var10 = (double)((float)this.e.l() * var5);
         return VoxelShapes.a(var2, var4.k(var0, var1).a(var6, var8, var10));
      }
   }

   public long v() {
      return this.k;
   }

   @Override
   public void a(World var0) {
      super.a(var0);
      if (var0.a(Registries.e).a(this.d.b().r().g()).isEmpty()) {
         this.d = Blocks.a.o();
      }
   }
}
