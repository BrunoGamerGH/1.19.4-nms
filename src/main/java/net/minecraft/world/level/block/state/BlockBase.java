package net.minecraft.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.BlockAccessAir;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.EnumBlockSupport;
import net.minecraft.world.level.block.EnumRenderType;
import net.minecraft.world.level.block.ITileEntity;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialMapColor;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameterSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.spigotmc.AsyncCatcher;

public abstract class BlockBase implements FeatureElement {
   protected static final EnumDirection[] aE = new EnumDirection[]{
      EnumDirection.e, EnumDirection.f, EnumDirection.c, EnumDirection.d, EnumDirection.a, EnumDirection.b
   };
   protected final Material aF;
   protected final boolean aG;
   protected final float aH;
   protected final boolean aI;
   protected final SoundEffectType aJ;
   protected final float aK;
   protected final float aL;
   protected final float aM;
   protected final boolean aN;
   protected final FeatureFlagSet aO;
   protected final BlockBase.Info aP;
   @Nullable
   protected MinecraftKey aQ;

   public BlockBase(BlockBase.Info blockbase_info) {
      this.aF = blockbase_info.a;
      this.aG = blockbase_info.c;
      this.aQ = blockbase_info.m;
      this.aH = blockbase_info.f;
      this.aI = blockbase_info.i;
      this.aJ = blockbase_info.d;
      this.aK = blockbase_info.j;
      this.aL = blockbase_info.k;
      this.aM = blockbase_info.l;
      this.aN = blockbase_info.w;
      this.aO = blockbase_info.x;
      this.aP = blockbase_info;
   }

   @Deprecated
   public void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, int i, int j) {
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      switch(pathmode) {
         case a:
            return !iblockdata.r(iblockaccess, blockposition);
         case b:
            return iblockaccess.b_(blockposition).a(TagsFluid.a);
         case c:
            return !iblockdata.r(iblockaccess, blockposition);
         default:
            return false;
      }
   }

   @Deprecated
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      return iblockdata;
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, IBlockData iblockdata1, EnumDirection enumdirection) {
      return false;
   }

   @Deprecated
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      PacketDebug.a(world, blockposition);
   }

   @Deprecated
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      AsyncCatcher.catchOp("block onPlace");
   }

   @Deprecated
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      AsyncCatcher.catchOp("block remove");
      if (iblockdata.q() && !iblockdata.a(iblockdata1.b())) {
         world.n(blockposition);
      }
   }

   @Deprecated
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      return EnumInteractionResult.d;
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, World world, BlockPosition blockposition, int i, int j) {
      return false;
   }

   @Deprecated
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Deprecated
   public boolean g_(IBlockData iblockdata) {
      return false;
   }

   @Deprecated
   public boolean f_(IBlockData iblockdata) {
      return false;
   }

   @Deprecated
   public EnumPistonReaction d(IBlockData iblockdata) {
      return this.aF.g();
   }

   @Deprecated
   public Fluid c_(IBlockData iblockdata) {
      return FluidTypes.a.g();
   }

   @Deprecated
   public boolean d_(IBlockData iblockdata) {
      return false;
   }

   public float am_() {
      return 0.25F;
   }

   public float ap_() {
      return 0.2F;
   }

   @Override
   public FeatureFlagSet m() {
      return this.aO;
   }

   @Deprecated
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata;
   }

   @Deprecated
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata;
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      return this.aF.e() && (blockactioncontext.n().b() || !blockactioncontext.n().a(this.k()));
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, FluidType fluidtype) {
      return this.aF.e() || !this.aF.b();
   }

   @Deprecated
   public List<ItemStack> a(IBlockData iblockdata, LootTableInfo.Builder loottableinfo_builder) {
      MinecraftKey minecraftkey = this.s();
      if (minecraftkey == LootTables.a) {
         return Collections.emptyList();
      } else {
         LootTableInfo loottableinfo = loottableinfo_builder.a(LootContextParameters.g, iblockdata).a(LootContextParameterSets.m);
         WorldServer worldserver = loottableinfo.c();
         LootTable loottable = worldserver.n().aH().a(minecraftkey);
         return loottable.a(loottableinfo);
      }
   }

   @Deprecated
   public long a(IBlockData iblockdata, BlockPosition blockposition) {
      return MathHelper.a(blockposition);
   }

   @Deprecated
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.j(iblockaccess, blockposition);
   }

   @Deprecated
   public VoxelShape b_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return this.c(iblockdata, iblockaccess, blockposition, VoxelShapeCollision.a());
   }

   @Deprecated
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return VoxelShapes.a();
   }

   @Deprecated
   public int g(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.i(iblockaccess, blockposition) ? iblockaccess.L() : (iblockdata.a(iblockaccess, blockposition) ? 0 : 1);
   }

   @Nullable
   @Deprecated
   public ITileInventory b(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return null;
   }

   @Deprecated
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return true;
   }

   @Deprecated
   public float b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.r(iblockaccess, blockposition) ? 0.2F : 1.0F;
   }

   @Deprecated
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return 0;
   }

   @Deprecated
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return VoxelShapes.b();
   }

   @Deprecated
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.aG ? iblockdata.j(iblockaccess, blockposition) : VoxelShapes.a();
   }

   @Deprecated
   public boolean a_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return Block.a(iblockdata.k(iblockaccess, blockposition));
   }

   @Deprecated
   public boolean h(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return Block.a(iblockdata.c(iblockaccess, blockposition));
   }

   @Deprecated
   public VoxelShape b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.c(iblockdata, iblockaccess, blockposition, voxelshapecollision);
   }

   @Deprecated
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      this.a(iblockdata, worldserver, blockposition, randomsource);
   }

   @Deprecated
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
   }

   @Deprecated
   public float a(IBlockData iblockdata, EntityHuman entityhuman, IBlockAccess iblockaccess, BlockPosition blockposition) {
      float f = iblockdata.h(iblockaccess, blockposition);
      if (f == -1.0F) {
         return 0.0F;
      } else {
         int i = entityhuman.d(iblockdata) ? 30 : 100;
         return entityhuman.c(iblockdata) / f / (float)i;
      }
   }

   @Deprecated
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
   }

   @Deprecated
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, EntityHuman entityhuman) {
   }

   @Deprecated
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return 0;
   }

   @Deprecated
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
   }

   @Deprecated
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return 0;
   }

   public final MinecraftKey s() {
      if (this.aQ == null) {
         MinecraftKey minecraftkey = BuiltInRegistries.f.b(this.q());
         this.aQ = minecraftkey.d("blocks/");
      }

      return this.aQ;
   }

   @Deprecated
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
   }

   public abstract Item k();

   protected abstract Block q();

   public MaterialMapColor t() {
      return this.aP.b.apply(this.q().o());
   }

   public float u() {
      return this.aP.g;
   }

   public abstract static class BlockData extends IBlockDataHolder<Block, IBlockData> {
      private final int b;
      private final boolean g;
      private final boolean h;
      private final Material i;
      private final MaterialMapColor j;
      public final float k;
      private final boolean l;
      private final boolean m;
      private final BlockBase.f n;
      private final BlockBase.f o;
      private final BlockBase.f p;
      private final BlockBase.f q;
      private final BlockBase.f r;
      private final Optional<BlockBase.b> s;
      private final boolean t;
      @Nullable
      protected BlockBase.BlockData.Cache a;
      private Fluid u = FluidTypes.a.g();
      private boolean v;

      protected BlockData(Block block, ImmutableMap<IBlockState<?>, Comparable<?>> immutablemap, MapCodec<IBlockData> mapcodec) {
         super(block, immutablemap, mapcodec);
         BlockBase.Info blockbase_info = block.aP;
         this.b = blockbase_info.e.applyAsInt(this.u());
         this.g = block.g_(this.u());
         this.h = blockbase_info.o;
         this.i = blockbase_info.a;
         this.j = blockbase_info.b.apply(this.u());
         this.k = blockbase_info.g;
         this.l = blockbase_info.h;
         this.m = blockbase_info.n;
         this.n = blockbase_info.r;
         this.o = blockbase_info.s;
         this.p = blockbase_info.t;
         this.q = blockbase_info.u;
         this.r = blockbase_info.v;
         this.s = blockbase_info.y;
         this.t = blockbase_info.p;
      }

      public void a() {
         this.u = this.e.c_(this.u());
         this.v = this.e.e_(this.u());
         if (!this.b().p()) {
            this.a = new BlockBase.BlockData.Cache(this.u());
         }
      }

      public Block b() {
         return this.e;
      }

      public Holder<Block> c() {
         return this.e.r();
      }

      public Material d() {
         return this.i;
      }

      public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EntityTypes<?> entitytypes) {
         return this.b().aP.q.test(this.u(), iblockaccess, blockposition, entitytypes);
      }

      public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.a != null ? this.a.g : this.b().c(this.u(), iblockaccess, blockposition);
      }

      public int b(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.a != null ? this.a.h : this.b().g(this.u(), iblockaccess, blockposition);
      }

      public VoxelShape a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
         return this.a != null && this.a.i != null ? this.a.i[enumdirection.ordinal()] : VoxelShapes.a(this.c(iblockaccess, blockposition), enumdirection);
      }

      public VoxelShape c(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.b().f(this.u(), iblockaccess, blockposition);
      }

      public boolean e() {
         return this.a == null || this.a.c;
      }

      public boolean f() {
         return this.g;
      }

      public int g() {
         return this.b;
      }

      public boolean h() {
         return this.h;
      }

      public MaterialMapColor d(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.j;
      }

      public IBlockData a(EnumBlockRotation enumblockrotation) {
         return this.b().a(this.u(), enumblockrotation);
      }

      public IBlockData a(EnumBlockMirror enumblockmirror) {
         return this.b().a(this.u(), enumblockmirror);
      }

      public EnumRenderType i() {
         return this.b().b_(this.u());
      }

      public boolean e(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.r.test(this.u(), iblockaccess, blockposition);
      }

      public float f(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.b().b(this.u(), iblockaccess, blockposition);
      }

      public boolean g(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.n.test(this.u(), iblockaccess, blockposition);
      }

      public boolean j() {
         return this.b().f_(this.u());
      }

      public int b(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
         return this.b().a(this.u(), iblockaccess, blockposition, enumdirection);
      }

      public boolean k() {
         return this.b().d_(this.u());
      }

      public int a(World world, BlockPosition blockposition) {
         return this.b().a(this.u(), world, blockposition);
      }

      public float h(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.k;
      }

      public float a(EntityHuman entityhuman, IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.b().a(this.u(), entityhuman, iblockaccess, blockposition);
      }

      public int c(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
         return this.b().b(this.u(), iblockaccess, blockposition, enumdirection);
      }

      public EnumPistonReaction l() {
         return this.b().d(this.u());
      }

      public boolean i(IBlockAccess iblockaccess, BlockPosition blockposition) {
         if (this.a != null) {
            return this.a.a;
         } else {
            IBlockData iblockdata = this.u();
            return iblockdata.m() ? Block.a(iblockdata.c(iblockaccess, blockposition)) : false;
         }
      }

      public boolean m() {
         return this.m;
      }

      public boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
         return this.b().a(this.u(), iblockdata, enumdirection);
      }

      public VoxelShape j(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.a(iblockaccess, blockposition, VoxelShapeCollision.a());
      }

      public VoxelShape a(IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
         return this.b().a(this.u(), iblockaccess, blockposition, voxelshapecollision);
      }

      public VoxelShape k(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.a != null ? this.a.b : this.b(iblockaccess, blockposition, VoxelShapeCollision.a());
      }

      public VoxelShape b(IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
         return this.b().c(this.u(), iblockaccess, blockposition, voxelshapecollision);
      }

      public VoxelShape l(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.b().b_(this.u(), iblockaccess, blockposition);
      }

      public VoxelShape c(IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
         return this.b().b(this.u(), iblockaccess, blockposition, voxelshapecollision);
      }

      public VoxelShape m(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.b().a(this.u(), iblockaccess, blockposition);
      }

      public final boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, Entity entity) {
         return this.a(iblockaccess, blockposition, entity, EnumDirection.b);
      }

      public final boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, Entity entity, EnumDirection enumdirection) {
         return Block.a(this.b(iblockaccess, blockposition, VoxelShapeCollision.a(entity)), enumdirection);
      }

      public Vec3D n(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.s.<Vec3D>map(blockbase_b -> blockbase_b.evaluate(this.u(), iblockaccess, blockposition)).orElse(Vec3D.b);
      }

      public boolean n() {
         return !this.s.isEmpty();
      }

      public boolean a(World world, BlockPosition blockposition, int i, int j) {
         return this.b().a(this.u(), world, blockposition, i, j);
      }

      @Deprecated
      public void a(World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
         this.b().a(this.u(), world, blockposition, block, blockposition1, flag);
      }

      public final void a(GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
         this.a(generatoraccess, blockposition, i, 512);
      }

      public final void a(GeneratorAccess generatoraccess, BlockPosition blockposition, int i, int j) {
         this.b();
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(EnumDirection enumdirection : BlockBase.aE) {
            blockposition_mutableblockposition.a(blockposition, enumdirection);
            generatoraccess.a(enumdirection.g(), this.u(), blockposition_mutableblockposition, blockposition, i, j);
         }
      }

      public final void b(GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
         this.b(generatoraccess, blockposition, i, 512);
      }

      public void b(GeneratorAccess generatoraccess, BlockPosition blockposition, int i, int j) {
         this.b().a(this.u(), generatoraccess, blockposition, i, j);
      }

      public void a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
         this.b().b(this.u(), world, blockposition, iblockdata, flag);
      }

      public void b(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
         this.b().a(this.u(), world, blockposition, iblockdata, flag);
      }

      public void a(WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
         this.b().a(this.u(), worldserver, blockposition, randomsource);
      }

      public void b(WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
         this.b().b(this.u(), worldserver, blockposition, randomsource);
      }

      public void a(World world, BlockPosition blockposition, Entity entity) {
         this.b().a(this.u(), world, blockposition, entity);
      }

      public void a(WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
         this.b().a(this.u(), worldserver, blockposition, itemstack, flag);
      }

      public List<ItemStack> a(LootTableInfo.Builder loottableinfo_builder) {
         return this.b().a(this.u(), loottableinfo_builder);
      }

      public EnumInteractionResult a(World world, EntityHuman entityhuman, EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock) {
         return this.b().a(this.u(), world, movingobjectpositionblock.a(), entityhuman, enumhand, movingobjectpositionblock);
      }

      public void a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
         this.b().a(this.u(), world, blockposition, entityhuman);
      }

      public boolean o(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.o.test(this.u(), iblockaccess, blockposition);
      }

      public boolean p(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.p.test(this.u(), iblockaccess, blockposition);
      }

      public IBlockData a(
         EnumDirection enumdirection, IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, BlockPosition blockposition1
      ) {
         return this.b().a(this.u(), enumdirection, iblockdata, generatoraccess, blockposition, blockposition1);
      }

      public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
         return this.b().a(this.u(), iblockaccess, blockposition, pathmode);
      }

      public boolean a(BlockActionContext blockactioncontext) {
         return this.b().a(this.u(), blockactioncontext);
      }

      public boolean a(FluidType fluidtype) {
         return this.b().a(this.u(), fluidtype);
      }

      public boolean o() {
         return this.d().e();
      }

      public boolean a(IWorldReader iworldreader, BlockPosition blockposition) {
         return this.b().a(this.u(), iworldreader, blockposition);
      }

      public boolean q(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.q.test(this.u(), iblockaccess, blockposition);
      }

      @Nullable
      public ITileInventory b(World world, BlockPosition blockposition) {
         return this.b().b(this.u(), world, blockposition);
      }

      public boolean a(TagKey<Block> tagkey) {
         return this.b().r().a(tagkey);
      }

      public boolean a(TagKey<Block> tagkey, Predicate<BlockBase.BlockData> predicate) {
         return this.a(tagkey) && predicate.test(this);
      }

      public boolean a(HolderSet<Block> holderset) {
         return holderset.a(this.b().r());
      }

      public Stream<TagKey<Block>> p() {
         return this.b().r().c();
      }

      public boolean q() {
         return this.b() instanceof ITileEntity;
      }

      @Nullable
      public <T extends TileEntity> BlockEntityTicker<T> a(World world, TileEntityTypes<T> tileentitytypes) {
         return this.b() instanceof ITileEntity ? ((ITileEntity)this.b()).a(world, this.u(), tileentitytypes) : null;
      }

      public boolean a(Block block) {
         return this.b() == block;
      }

      public Fluid r() {
         return this.u;
      }

      public boolean s() {
         return this.v;
      }

      public long a(BlockPosition blockposition) {
         return this.b().a(this.u(), blockposition);
      }

      public SoundEffectType t() {
         return this.b().m(this.u());
      }

      public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
         this.b().a(world, iblockdata, movingobjectpositionblock, iprojectile);
      }

      public boolean d(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
         return this.a(iblockaccess, blockposition, enumdirection, EnumBlockSupport.a);
      }

      public boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection, EnumBlockSupport enumblocksupport) {
         return this.a != null ? this.a.a(enumdirection, enumblocksupport) : enumblocksupport.a(this.u(), iblockaccess, blockposition, enumdirection);
      }

      public boolean r(IBlockAccess iblockaccess, BlockPosition blockposition) {
         return this.a != null ? this.a.d : this.b().a_(this.u(), iblockaccess, blockposition);
      }

      protected abstract IBlockData u();

      public boolean v() {
         return this.l;
      }

      public boolean w() {
         return this.t;
      }

      private static final class Cache {
         private static final EnumDirection[] e = EnumDirection.values();
         private static final int f = EnumBlockSupport.values().length;
         protected final boolean a;
         final boolean g;
         final int h;
         @Nullable
         final VoxelShape[] i;
         protected final VoxelShape b;
         protected final boolean c;
         private final boolean[] j;
         protected final boolean d;

         Cache(IBlockData iblockdata) {
            Block block = iblockdata.b();
            this.a = iblockdata.i(BlockAccessAir.a, BlockPosition.b);
            this.g = block.c(iblockdata, BlockAccessAir.a, BlockPosition.b);
            this.h = block.g(iblockdata, BlockAccessAir.a, BlockPosition.b);
            if (!iblockdata.m()) {
               this.i = null;
            } else {
               this.i = new VoxelShape[e.length];
               VoxelShape voxelshape = block.f(iblockdata, BlockAccessAir.a, BlockPosition.b);

               for(EnumDirection enumdirection : e) {
                  this.i[enumdirection.ordinal()] = VoxelShapes.a(voxelshape, enumdirection);
               }
            }

            this.b = block.c(iblockdata, BlockAccessAir.a, BlockPosition.b, VoxelShapeCollision.a());
            if (!this.b.b() && iblockdata.n()) {
               throw new IllegalStateException(
                  String.format(
                     Locale.ROOT,
                     "%s has a collision shape and an offset type, but is not marked as dynamicShape in its properties.",
                     BuiltInRegistries.f.b(block)
                  )
               );
            } else {
               this.c = Arrays.stream(EnumDirection.EnumAxis.values())
                  .anyMatch(enumdirection_enumaxis -> this.b.b(enumdirection_enumaxis) < 0.0 || this.b.c(enumdirection_enumaxis) > 1.0);
               this.j = new boolean[e.length * f];

               for(EnumDirection enumdirection1 : e) {
                  for(EnumBlockSupport enumblocksupport : EnumBlockSupport.values()) {
                     this.j[b(enumdirection1, enumblocksupport)] = enumblocksupport.a(iblockdata, BlockAccessAir.a, BlockPosition.b, enumdirection1);
                  }
               }

               this.d = Block.a(iblockdata.k(BlockAccessAir.a, BlockPosition.b));
            }
         }

         public boolean a(EnumDirection enumdirection, EnumBlockSupport enumblocksupport) {
            return this.j[b(enumdirection, enumblocksupport)];
         }

         private static int b(EnumDirection enumdirection, EnumBlockSupport enumblocksupport) {
            return enumdirection.ordinal() * f + enumblocksupport.ordinal();
         }
      }
   }

   public static enum EnumRandomOffset {
      a,
      b,
      c;
   }

   public static class Info {
      Material a;
      Function<IBlockData, MaterialMapColor> b;
      boolean c = true;
      SoundEffectType d = SoundEffectType.e;
      ToIntFunction<IBlockData> e = iblockdata -> 0;
      float f;
      float g;
      boolean h;
      boolean i;
      float j = 0.6F;
      float k = 1.0F;
      float l = 1.0F;
      MinecraftKey m;
      boolean n = true;
      boolean o;
      boolean p = true;
      BlockBase.e<EntityTypes<?>> q = (iblockdata, iblockaccess, blockposition, entitytypes) -> iblockdata.d(iblockaccess, blockposition, EnumDirection.b)
            && iblockdata.g() < 14;
      BlockBase.f r = (iblockdata, iblockaccess, blockposition) -> iblockdata.d().f() && iblockdata.r(iblockaccess, blockposition);
      BlockBase.f s = (iblockdata, iblockaccess, blockposition) -> this.a.c() && iblockdata.r(iblockaccess, blockposition);
      BlockBase.f t = this.s;
      BlockBase.f u = (iblockdata, iblockaccess, blockposition) -> false;
      BlockBase.f v = (iblockdata, iblockaccess, blockposition) -> false;
      boolean w;
      FeatureFlagSet x = FeatureFlags.f;
      Optional<BlockBase.b> y = Optional.empty();

      private Info(Material material, MaterialMapColor materialmapcolor) {
         this(material, iblockdata -> materialmapcolor);
      }

      private Info(Material material, Function<IBlockData, MaterialMapColor> function) {
         this.a = material;
         this.b = function;
      }

      public static BlockBase.Info a(Material material) {
         return a(material, material.h());
      }

      public static BlockBase.Info a(Material material, EnumColor enumcolor) {
         return a(material, enumcolor.e());
      }

      public static BlockBase.Info a(Material material, MaterialMapColor materialmapcolor) {
         return new BlockBase.Info(material, materialmapcolor);
      }

      public static BlockBase.Info a(Material material, Function<IBlockData, MaterialMapColor> function) {
         return new BlockBase.Info(material, function);
      }

      public static BlockBase.Info a(BlockBase blockbase) {
         BlockBase.Info blockbase_info = new BlockBase.Info(blockbase.aF, blockbase.aP.b);
         blockbase_info.a = blockbase.aP.a;
         blockbase_info.g = blockbase.aP.g;
         blockbase_info.f = blockbase.aP.f;
         blockbase_info.c = blockbase.aP.c;
         blockbase_info.i = blockbase.aP.i;
         blockbase_info.e = blockbase.aP.e;
         blockbase_info.b = blockbase.aP.b;
         blockbase_info.d = blockbase.aP.d;
         blockbase_info.j = blockbase.aP.j;
         blockbase_info.k = blockbase.aP.k;
         blockbase_info.w = blockbase.aP.w;
         blockbase_info.n = blockbase.aP.n;
         blockbase_info.o = blockbase.aP.o;
         blockbase_info.h = blockbase.aP.h;
         blockbase_info.y = blockbase.aP.y;
         blockbase_info.p = blockbase.aP.p;
         blockbase_info.x = blockbase.aP.x;
         return blockbase_info;
      }

      public BlockBase.Info a() {
         this.c = false;
         this.n = false;
         return this;
      }

      public BlockBase.Info b() {
         this.n = false;
         return this;
      }

      public BlockBase.Info a(float f) {
         this.j = f;
         return this;
      }

      public BlockBase.Info b(float f) {
         this.k = f;
         return this;
      }

      public BlockBase.Info c(float f) {
         this.l = f;
         return this;
      }

      public BlockBase.Info a(SoundEffectType soundeffecttype) {
         this.d = soundeffecttype;
         return this;
      }

      public BlockBase.Info a(ToIntFunction<IBlockData> tointfunction) {
         this.e = tointfunction;
         return this;
      }

      public BlockBase.Info a(float f, float f1) {
         return this.e(f).f(f1);
      }

      public BlockBase.Info c() {
         return this.d(0.0F);
      }

      public BlockBase.Info d(float f) {
         this.a(f, f);
         return this;
      }

      public BlockBase.Info d() {
         this.i = true;
         return this;
      }

      public BlockBase.Info e() {
         this.w = true;
         return this;
      }

      public BlockBase.Info f() {
         this.m = LootTables.a;
         return this;
      }

      public BlockBase.Info a(Block block) {
         this.m = block.s();
         return this;
      }

      public BlockBase.Info g() {
         this.o = true;
         return this;
      }

      public BlockBase.Info a(BlockBase.e<EntityTypes<?>> blockbase_e) {
         this.q = blockbase_e;
         return this;
      }

      public BlockBase.Info a(BlockBase.f blockbase_f) {
         this.r = blockbase_f;
         return this;
      }

      public BlockBase.Info b(BlockBase.f blockbase_f) {
         this.s = blockbase_f;
         return this;
      }

      public BlockBase.Info c(BlockBase.f blockbase_f) {
         this.t = blockbase_f;
         return this;
      }

      public BlockBase.Info d(BlockBase.f blockbase_f) {
         this.u = blockbase_f;
         return this;
      }

      public BlockBase.Info e(BlockBase.f blockbase_f) {
         this.v = blockbase_f;
         return this;
      }

      public BlockBase.Info h() {
         this.h = true;
         return this;
      }

      public BlockBase.Info a(MaterialMapColor materialmapcolor) {
         this.b = iblockdata -> materialmapcolor;
         return this;
      }

      public BlockBase.Info e(float f) {
         this.g = f;
         return this;
      }

      public BlockBase.Info f(float f) {
         this.f = Math.max(0.0F, f);
         return this;
      }

      public BlockBase.Info a(BlockBase.EnumRandomOffset blockbase_enumrandomoffset) {
         switch(blockbase_enumrandomoffset) {
            case b:
               this.y = Optional.of((iblockdata, iblockaccess, blockposition) -> {
                  Block block = iblockdata.b();
                  long i = MathHelper.b(blockposition.u(), 0, blockposition.w());
                  float f = block.am_();
                  double d0 = MathHelper.a(((double)((float)(i & 15L) / 15.0F) - 0.5) * 0.5, (double)(-f), (double)f);
                  double d1 = MathHelper.a(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5) * 0.5, (double)(-f), (double)f);
                  return new Vec3D(d0, 0.0, d1);
               });
               break;
            case c:
               this.y = Optional.of((iblockdata, iblockaccess, blockposition) -> {
                  Block block = iblockdata.b();
                  long i = MathHelper.b(blockposition.u(), 0, blockposition.w());
                  double d0 = ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0) * (double)block.ap_();
                  float f = block.am_();
                  double d1 = MathHelper.a(((double)((float)(i & 15L) / 15.0F) - 0.5) * 0.5, (double)(-f), (double)f);
                  double d2 = MathHelper.a(((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5) * 0.5, (double)(-f), (double)f);
                  return new Vec3D(d1, d0, d2);
               });
               break;
            default:
               this.y = Optional.empty();
         }

         return this;
      }

      public BlockBase.Info i() {
         this.p = false;
         return this;
      }

      public BlockBase.Info a(FeatureFlag... afeatureflag) {
         this.x = FeatureFlags.d.a(afeatureflag);
         return this;
      }
   }

   public interface b {
      Vec3D evaluate(IBlockData var1, IBlockAccess var2, BlockPosition var3);
   }

   public interface e<A> {
      boolean test(IBlockData var1, IBlockAccess var2, BlockPosition var3, A var4);
   }

   public interface f {
      boolean test(IBlockData var1, IBlockAccess var2, BlockPosition var3);
   }
}
