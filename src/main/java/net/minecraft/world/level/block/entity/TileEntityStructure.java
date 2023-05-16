package net.minecraft.world.level.block.entity;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ResourceKeyInvalidException;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.UtilColor;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.block.BlockStructure;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class TileEntityStructure extends TileEntity {
   private static final int d = 5;
   public static final int a = 48;
   public static final int b = 48;
   public static final String c = "author";
   private MinecraftKey e;
   public String f = "";
   public String g = "";
   public BlockPosition h = new BlockPosition(0, 1, 0);
   public BaseBlockPosition i = BaseBlockPosition.g;
   public EnumBlockMirror j = EnumBlockMirror.a;
   public EnumBlockRotation k = EnumBlockRotation.a;
   public BlockPropertyStructureMode l;
   public boolean m = true;
   private boolean n;
   public boolean r;
   public boolean s = true;
   public float t = 1.0F;
   public long u;

   public TileEntityStructure(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.u, var0, var1);
      this.l = var1.c(BlockStructure.a);
   }

   @Override
   protected void b(NBTTagCompound var0) {
      super.b(var0);
      var0.a("name", this.d());
      var0.a("author", this.f);
      var0.a("metadata", this.g);
      var0.a("posX", this.h.u());
      var0.a("posY", this.h.v());
      var0.a("posZ", this.h.w());
      var0.a("sizeX", this.i.u());
      var0.a("sizeY", this.i.v());
      var0.a("sizeZ", this.i.w());
      var0.a("rotation", this.k.toString());
      var0.a("mirror", this.j.toString());
      var0.a("mode", this.l.toString());
      var0.a("ignoreEntities", this.m);
      var0.a("powered", this.n);
      var0.a("showair", this.r);
      var0.a("showboundingbox", this.s);
      var0.a("integrity", this.t);
      var0.a("seed", this.u);
   }

   @Override
   public void a(NBTTagCompound var0) {
      super.a(var0);
      this.a(var0.l("name"));
      this.f = var0.l("author");
      this.g = var0.l("metadata");
      int var1 = MathHelper.a(var0.h("posX"), -48, 48);
      int var2 = MathHelper.a(var0.h("posY"), -48, 48);
      int var3 = MathHelper.a(var0.h("posZ"), -48, 48);
      this.h = new BlockPosition(var1, var2, var3);
      int var4 = MathHelper.a(var0.h("sizeX"), 0, 48);
      int var5 = MathHelper.a(var0.h("sizeY"), 0, 48);
      int var6 = MathHelper.a(var0.h("sizeZ"), 0, 48);
      this.i = new BaseBlockPosition(var4, var5, var6);

      try {
         this.k = EnumBlockRotation.valueOf(var0.l("rotation"));
      } catch (IllegalArgumentException var11) {
         this.k = EnumBlockRotation.a;
      }

      try {
         this.j = EnumBlockMirror.valueOf(var0.l("mirror"));
      } catch (IllegalArgumentException var10) {
         this.j = EnumBlockMirror.a;
      }

      try {
         this.l = BlockPropertyStructureMode.valueOf(var0.l("mode"));
      } catch (IllegalArgumentException var9) {
         this.l = BlockPropertyStructureMode.d;
      }

      this.m = var0.q("ignoreEntities");
      this.n = var0.q("powered");
      this.r = var0.q("showair");
      this.s = var0.q("showboundingbox");
      if (var0.e("integrity")) {
         this.t = var0.j("integrity");
      } else {
         this.t = 1.0F;
      }

      this.u = var0.i("seed");
      this.J();
   }

   private void J() {
      if (this.o != null) {
         BlockPosition var0 = this.p();
         IBlockData var1 = this.o.a_(var0);
         if (var1.a(Blocks.oW)) {
            this.o.a(var0, var1.a(BlockStructure.a, this.l), 2);
         }
      }
   }

   public PacketPlayOutTileEntityData c() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   public boolean a(EntityHuman var0) {
      if (!var0.gg()) {
         return false;
      } else {
         if (var0.cG().B) {
            var0.a(this);
         }

         return true;
      }
   }

   public String d() {
      return this.e == null ? "" : this.e.toString();
   }

   public String f() {
      return this.e == null ? "" : this.e.a();
   }

   public boolean g() {
      return this.e != null;
   }

   public void a(@Nullable String var0) {
      this.a(UtilColor.b(var0) ? null : MinecraftKey.a(var0));
   }

   public void a(@Nullable MinecraftKey var0) {
      this.e = var0;
   }

   public void a(EntityLiving var0) {
      this.f = var0.Z().getString();
   }

   public BlockPosition i() {
      return this.h;
   }

   public void a(BlockPosition var0) {
      this.h = var0;
   }

   public BaseBlockPosition j() {
      return this.i;
   }

   public void a(BaseBlockPosition var0) {
      this.i = var0;
   }

   public EnumBlockMirror v() {
      return this.j;
   }

   public void a(EnumBlockMirror var0) {
      this.j = var0;
   }

   public EnumBlockRotation w() {
      return this.k;
   }

   public void a(EnumBlockRotation var0) {
      this.k = var0;
   }

   public String x() {
      return this.g;
   }

   public void b(String var0) {
      this.g = var0;
   }

   public BlockPropertyStructureMode y() {
      return this.l;
   }

   public void a(BlockPropertyStructureMode var0) {
      this.l = var0;
      IBlockData var1 = this.o.a_(this.p());
      if (var1.a(Blocks.oW)) {
         this.o.a(this.p(), var1.a(BlockStructure.a, var0), 2);
      }
   }

   public boolean z() {
      return this.m;
   }

   public void a(boolean var0) {
      this.m = var0;
   }

   public float A() {
      return this.t;
   }

   public void a(float var0) {
      this.t = var0;
   }

   public long B() {
      return this.u;
   }

   public void a(long var0) {
      this.u = var0;
   }

   public boolean C() {
      if (this.l != BlockPropertyStructureMode.a) {
         return false;
      } else {
         BlockPosition var0 = this.p();
         int var1 = 80;
         BlockPosition var2 = new BlockPosition(var0.u() - 80, this.o.v_(), var0.w() - 80);
         BlockPosition var3 = new BlockPosition(var0.u() + 80, this.o.ai() - 1, var0.w() + 80);
         Stream<BlockPosition> var4 = this.a(var2, var3);
         return a(var0, var4).filter(var1x -> {
            int var2x = var1x.j() - var1x.g();
            int var3x = var1x.k() - var1x.h();
            int var4x = var1x.l() - var1x.i();
            if (var2x > 1 && var3x > 1 && var4x > 1) {
               this.h = new BlockPosition(var1x.g() - var0.u() + 1, var1x.h() - var0.v() + 1, var1x.i() - var0.w() + 1);
               this.i = new BaseBlockPosition(var2x - 1, var3x - 1, var4x - 1);
               this.e();
               IBlockData var5x = this.o.a_(var0);
               this.o.a(var0, var5x, var5x, 3);
               return true;
            } else {
               return false;
            }
         }).isPresent();
      }
   }

   private Stream<BlockPosition> a(BlockPosition var0, BlockPosition var1) {
      return BlockPosition.b(var0, var1)
         .filter(var0x -> this.o.a_(var0x).a(Blocks.oW))
         .map(this.o::c_)
         .filter(var0x -> var0x instanceof TileEntityStructure)
         .map(var0x -> (TileEntityStructure)var0x)
         .filter(var0x -> var0x.l == BlockPropertyStructureMode.c && Objects.equals(this.e, var0x.e))
         .map(TileEntity::p);
   }

   private static Optional<StructureBoundingBox> a(BlockPosition var0, Stream<BlockPosition> var1) {
      Iterator<BlockPosition> var2 = var1.iterator();
      if (!var2.hasNext()) {
         return Optional.empty();
      } else {
         BlockPosition var3 = var2.next();
         StructureBoundingBox var4 = new StructureBoundingBox(var3);
         if (var2.hasNext()) {
            var2.forEachRemaining(var4::a);
         } else {
            var4.a(var0);
         }

         return Optional.of(var4);
      }
   }

   public boolean D() {
      return this.b(true);
   }

   public boolean b(boolean var0) {
      if (this.l == BlockPropertyStructureMode.a && !this.o.B && this.e != null) {
         BlockPosition var1 = this.p().a(this.h);
         WorldServer var2 = (WorldServer)this.o;
         StructureTemplateManager var3 = var2.p();

         DefinedStructure var4;
         try {
            var4 = var3.a(this.e);
         } catch (ResourceKeyInvalidException var8) {
            return false;
         }

         var4.a(this.o, var1, this.i, !this.m, Blocks.kK);
         var4.a(this.f);
         if (var0) {
            try {
               return var3.c(this.e);
            } catch (ResourceKeyInvalidException var7) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean a(WorldServer var0) {
      return this.a(var0, true);
   }

   public static RandomSource b(long var0) {
      return var0 == 0L ? RandomSource.a(SystemUtils.b()) : RandomSource.a(var0);
   }

   public boolean a(WorldServer var0, boolean var1) {
      if (this.l == BlockPropertyStructureMode.b && this.e != null) {
         StructureTemplateManager var2 = var0.p();

         Optional<DefinedStructure> var3;
         try {
            var3 = var2.b(this.e);
         } catch (ResourceKeyInvalidException var6) {
            return false;
         }

         return !var3.isPresent() ? false : this.a(var0, var1, var3.get());
      } else {
         return false;
      }
   }

   public boolean a(WorldServer var0, boolean var1, DefinedStructure var2) {
      BlockPosition var3 = this.p();
      if (!UtilColor.b(var2.b())) {
         this.f = var2.b();
      }

      BaseBlockPosition var4 = var2.a();
      boolean var5 = this.i.equals(var4);
      if (!var5) {
         this.i = var4;
         this.e();
         IBlockData var6 = var0.a_(var3);
         var0.a(var3, var6, var6, 3);
      }

      if (var1 && !var5) {
         return false;
      } else {
         DefinedStructureInfo var6 = new DefinedStructureInfo().a(this.j).a(this.k).a(this.m);
         if (this.t < 1.0F) {
            var6.b().a(new DefinedStructureProcessorRotation(MathHelper.a(this.t, 0.0F, 1.0F))).a(b(this.u));
         }

         BlockPosition var7 = var3.a(this.h);
         var2.a(var0, var7, var7, var6, b(this.u), 2);
         return true;
      }
   }

   public void E() {
      if (this.e != null) {
         WorldServer var0 = (WorldServer)this.o;
         StructureTemplateManager var1 = var0.p();
         var1.d(this.e);
      }
   }

   public boolean F() {
      if (this.l == BlockPropertyStructureMode.b && !this.o.B && this.e != null) {
         WorldServer var0 = (WorldServer)this.o;
         StructureTemplateManager var1 = var0.p();

         try {
            return var1.b(this.e).isPresent();
         } catch (ResourceKeyInvalidException var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean G() {
      return this.n;
   }

   public void c(boolean var0) {
      this.n = var0;
   }

   public boolean H() {
      return this.r;
   }

   public void d(boolean var0) {
      this.r = var0;
   }

   public boolean I() {
      return this.s;
   }

   public void e(boolean var0) {
      this.s = var0;
   }

   public static enum UpdateType {
      a,
      b,
      c,
      d;
   }
}
