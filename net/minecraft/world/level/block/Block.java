package net.minecraft.world.level.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryBlockID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityExperienceOrb;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.event.entity.EntityExhaustionEvent.ExhaustionReason;
import org.slf4j.Logger;

public class Block extends BlockBase implements IMaterial {
   private static final Logger a = LogUtils.getLogger();
   private final Holder.c<Block> b = BuiltInRegistries.f.f(this);
   public static final RegistryBlockID<IBlockData> o = new RegistryBlockID<>();
   private static final LoadingCache<VoxelShape, Boolean> c = CacheBuilder.newBuilder()
      .maximumSize(512L)
      .weakKeys()
      .build(new CacheLoader<VoxelShape, Boolean>() {
         public Boolean load(VoxelShape voxelshape) {
            return !VoxelShapes.c(VoxelShapes.b(), voxelshape, OperatorBoolean.g);
         }
      });
   public static final int p = 1;
   public static final int q = 2;
   public static final int r = 4;
   public static final int s = 8;
   public static final int t = 16;
   public static final int u = 32;
   public static final int v = 64;
   public static final int w = 128;
   public static final int x = 4;
   public static final int y = 3;
   public static final int z = 11;
   public static final float A = -1.0F;
   public static final float B = 0.0F;
   public static final int C = 512;
   protected final BlockStateList<Block, IBlockData> D;
   private IBlockData d;
   @Nullable
   private String e;
   @Nullable
   private Item f;
   private static final int g = 2048;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.a>> h = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.a> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<Block.a>(2048, 0.25F) {
         protected void rehash(int i) {
         }
      };
      object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
      return object2bytelinkedopenhashmap;
   });

   public static int i(@Nullable IBlockData iblockdata) {
      if (iblockdata == null) {
         return 0;
      } else {
         int i = o.a(iblockdata);
         return i == -1 ? 0 : i;
      }
   }

   public static IBlockData a(int i) {
      IBlockData iblockdata = o.a(i);
      return iblockdata == null ? Blocks.a.o() : iblockdata;
   }

   public static Block a(@Nullable Item item) {
      return item instanceof ItemBlock ? ((ItemBlock)item).e() : Blocks.a;
   }

   public static IBlockData a(IBlockData iblockdata, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      VoxelShape voxelshape = VoxelShapes.b(iblockdata.k(generatoraccess, blockposition), iblockdata1.k(generatoraccess, blockposition), OperatorBoolean.c)
         .a((double)blockposition.u(), (double)blockposition.v(), (double)blockposition.w());
      if (voxelshape.b()) {
         return iblockdata1;
      } else {
         for(Entity entity : generatoraccess.a_(null, voxelshape.a())) {
            double d0 = VoxelShapes.a(EnumDirection.EnumAxis.b, entity.cD().d(0.0, 1.0, 0.0), List.of(voxelshape), -1.0);
            entity.c(0.0, 1.0 + d0, 0.0);
         }

         return iblockdata1;
      }
   }

   public static VoxelShape a(double d0, double d1, double d2, double d3, double d4, double d5) {
      return VoxelShapes.a(d0 / 16.0, d1 / 16.0, d2 / 16.0, d3 / 16.0, d4 / 16.0, d5 / 16.0);
   }

   public static IBlockData b(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      IBlockData iblockdata1 = iblockdata;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(EnumDirection enumdirection : aE) {
         blockposition_mutableblockposition.a(blockposition, enumdirection);
         iblockdata1 = iblockdata1.a(
            enumdirection, generatoraccess.a_(blockposition_mutableblockposition), generatoraccess, blockposition, blockposition_mutableblockposition
         );
      }

      return iblockdata1;
   }

   public static void a(IBlockData iblockdata, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, int i) {
      a(iblockdata, iblockdata1, generatoraccess, blockposition, i, 512);
   }

   public static void a(IBlockData iblockdata, IBlockData iblockdata1, GeneratorAccess generatoraccess, BlockPosition blockposition, int i, int j) {
      if (iblockdata1 != iblockdata) {
         if (iblockdata1.h()) {
            if (!generatoraccess.k_()) {
               generatoraccess.a(blockposition, (i & 32) == 0, null, j);
            }
         } else {
            generatoraccess.a(blockposition, iblockdata1, i & -33, j);
         }
      }
   }

   public Block(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      BlockStateList.a<Block, IBlockData> blockstatelist_a = new BlockStateList.a<>(this);
      this.a(blockstatelist_a);
      this.D = blockstatelist_a.a(Block::o, IBlockData::new);
      this.k(this.D.b());
      if (SharedConstants.aO) {
         String s = this.getClass().getSimpleName();
         if (!s.endsWith("Block")) {
            a.error("Block classes should end with Block and {} doesn't.", s);
         }
      }
   }

   public static boolean j(IBlockData iblockdata) {
      return iblockdata.b() instanceof BlockLeaves
         || iblockdata.a(Blocks.hV)
         || iblockdata.a(Blocks.ee)
         || iblockdata.a(Blocks.ef)
         || iblockdata.a(Blocks.eZ)
         || iblockdata.a(Blocks.dU)
         || iblockdata.a(TagsBlock.aO);
   }

   public boolean e_(IBlockData iblockdata) {
      return this.aI;
   }

   public static boolean a(
      IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection, BlockPosition blockposition1
   ) {
      IBlockData iblockdata1 = iblockaccess.a_(blockposition1);
      if (iblockdata.a(iblockdata1, enumdirection)) {
         return false;
      } else if (iblockdata1.m()) {
         Block.a block_a = new Block.a(iblockdata, iblockdata1, enumdirection);
         Object2ByteLinkedOpenHashMap<Block.a> object2bytelinkedopenhashmap = (Object2ByteLinkedOpenHashMap)h.get();
         byte b0 = object2bytelinkedopenhashmap.getAndMoveToFirst(block_a);
         if (b0 != 127) {
            return b0 != 0;
         } else {
            VoxelShape voxelshape = iblockdata.a(iblockaccess, blockposition, enumdirection);
            if (voxelshape.b()) {
               return true;
            } else {
               VoxelShape voxelshape1 = iblockdata1.a(iblockaccess, blockposition1, enumdirection.g());
               boolean flag = VoxelShapes.c(voxelshape, voxelshape1, OperatorBoolean.e);
               if (object2bytelinkedopenhashmap.size() == 2048) {
                  object2bytelinkedopenhashmap.removeLastByte();
               }

               object2bytelinkedopenhashmap.putAndMoveToFirst(block_a, (byte)(flag ? 1 : 0));
               return flag;
            }
         }
      } else {
         return true;
      }
   }

   public static boolean c(IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockaccess.a_(blockposition).a(iblockaccess, blockposition, EnumDirection.b, EnumBlockSupport.c);
   }

   public static boolean a(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection) {
      IBlockData iblockdata = iworldreader.a_(blockposition);
      return enumdirection == EnumDirection.a && iblockdata.a(TagsBlock.aW)
         ? false
         : iblockdata.a(iworldreader, blockposition, enumdirection, EnumBlockSupport.b);
   }

   public static boolean a(VoxelShape voxelshape, EnumDirection enumdirection) {
      VoxelShape voxelshape1 = voxelshape.a(enumdirection);
      return a(voxelshape1);
   }

   public static boolean a(VoxelShape voxelshape) {
      return c.getUnchecked(voxelshape);
   }

   public boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return !a(iblockdata.j(iblockaccess, blockposition)) && iblockdata.r().c();
   }

   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
   }

   public void a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
   }

   public static List<ItemStack> a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, @Nullable TileEntity tileentity) {
      LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(worldserver)
         .a(worldserver.z)
         .a(LootContextParameters.f, Vec3D.b(blockposition))
         .a(LootContextParameters.i, ItemStack.b)
         .b(LootContextParameters.h, tileentity);
      return iblockdata.a(loottableinfo_builder);
   }

   public static List<ItemStack> a(
      IBlockData iblockdata,
      WorldServer worldserver,
      BlockPosition blockposition,
      @Nullable TileEntity tileentity,
      @Nullable Entity entity,
      ItemStack itemstack
   ) {
      LootTableInfo.Builder loottableinfo_builder = new LootTableInfo.Builder(worldserver)
         .a(worldserver.z)
         .a(LootContextParameters.f, Vec3D.b(blockposition))
         .a(LootContextParameters.i, itemstack)
         .b(LootContextParameters.a, entity)
         .b(LootContextParameters.h, tileentity);
      return iblockdata.a(loottableinfo_builder);
   }

   public static void b(IBlockData iblockdata, LootTableInfo.Builder loottableinfo_builder) {
      WorldServer worldserver = loottableinfo_builder.a();
      BlockPosition blockposition = BlockPosition.a(loottableinfo_builder.a(LootContextParameters.f));
      iblockdata.a(loottableinfo_builder).forEach(itemstack -> a(worldserver, blockposition, itemstack));
      iblockdata.a(worldserver, blockposition, ItemStack.b, true);
   }

   public static void c(IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (world instanceof WorldServer) {
         a(iblockdata, (WorldServer)world, blockposition, null).forEach(itemstack -> a(world, blockposition, itemstack));
         iblockdata.a((WorldServer)world, blockposition, ItemStack.b, true);
      }
   }

   public static void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, @Nullable TileEntity tileentity) {
      if (generatoraccess instanceof WorldServer) {
         a(iblockdata, (WorldServer)generatoraccess, blockposition, tileentity)
            .forEach(itemstack -> a((WorldServer)generatoraccess, blockposition, itemstack));
         iblockdata.a((WorldServer)generatoraccess, blockposition, ItemStack.b, true);
      }
   }

   public static void a(IBlockData iblockdata, World world, BlockPosition blockposition, @Nullable TileEntity tileentity, Entity entity, ItemStack itemstack) {
      if (world instanceof WorldServer) {
         a(iblockdata, (WorldServer)world, blockposition, tileentity, entity, itemstack).forEach(itemstack1 -> a(world, blockposition, itemstack1));
         iblockdata.a((WorldServer)world, blockposition, itemstack, true);
      }
   }

   public static void a(World world, BlockPosition blockposition, ItemStack itemstack) {
      double d0 = (double)EntityTypes.ad.l() / 2.0;
      double d1 = (double)blockposition.u() + 0.5 + MathHelper.a(world.z, -0.25, 0.25);
      double d2 = (double)blockposition.v() + 0.5 + MathHelper.a(world.z, -0.25, 0.25) - d0;
      double d3 = (double)blockposition.w() + 0.5 + MathHelper.a(world.z, -0.25, 0.25);
      a(world, () -> new EntityItem(world, d1, d2, d3, itemstack), itemstack);
   }

   public static void a(World world, BlockPosition blockposition, EnumDirection enumdirection, ItemStack itemstack) {
      int i = enumdirection.j();
      int j = enumdirection.k();
      int k = enumdirection.l();
      double d0 = (double)EntityTypes.ad.k() / 2.0;
      double d1 = (double)EntityTypes.ad.l() / 2.0;
      double d2 = (double)blockposition.u() + 0.5 + (i == 0 ? MathHelper.a(world.z, -0.25, 0.25) : (double)i * (0.5 + d0));
      double d3 = (double)blockposition.v() + 0.5 + (j == 0 ? MathHelper.a(world.z, -0.25, 0.25) : (double)j * (0.5 + d1)) - d1;
      double d4 = (double)blockposition.w() + 0.5 + (k == 0 ? MathHelper.a(world.z, -0.25, 0.25) : (double)k * (0.5 + d0));
      double d5 = i == 0 ? MathHelper.a(world.z, -0.1, 0.1) : (double)i * 0.1;
      double d6 = j == 0 ? MathHelper.a(world.z, 0.0, 0.1) : (double)j * 0.1 + 0.1;
      double d7 = k == 0 ? MathHelper.a(world.z, -0.1, 0.1) : (double)k * 0.1;
      a(world, () -> new EntityItem(world, d2, d3, d4, itemstack, d5, d6, d7), itemstack);
   }

   private static void a(World world, Supplier<EntityItem> supplier, ItemStack itemstack) {
      if (!world.B && !itemstack.b() && world.W().b(GameRules.g)) {
         EntityItem entityitem = supplier.get();
         entityitem.k();
         if (world.captureDrops != null) {
            world.captureDrops.add(entityitem);
         } else {
            world.b(entityitem);
         }
      }
   }

   public void a(WorldServer worldserver, BlockPosition blockposition, int i) {
      if (worldserver.W().b(GameRules.g)) {
         EntityExperienceOrb.a(worldserver, Vec3D.b(blockposition), i);
      }
   }

   public float e() {
      return this.aH;
   }

   public void a(World world, BlockPosition blockposition, Explosion explosion) {
   }

   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
   }

   @Nullable
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o();
   }

   public void a(
      World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata, @Nullable TileEntity tileentity, ItemStack itemstack
   ) {
      entityhuman.b(StatisticList.a.b(this));
      entityhuman.causeFoodExhaustion(0.005F, ExhaustionReason.BLOCK_MINED);
      a(iblockdata, world, blockposition, tileentity, entityhuman, itemstack);
   }

   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
   }

   public boolean ao_() {
      return !this.aF.b() && !this.aF.a();
   }

   public IChatMutableComponent f() {
      return IChatBaseComponent.c(this.h());
   }

   public String h() {
      if (this.e == null) {
         this.e = SystemUtils.a("block", BuiltInRegistries.f.b(this));
      }

      return this.e;
   }

   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      entity.a(f, 1.0F, entity.dG().k());
   }

   public void a(IBlockAccess iblockaccess, Entity entity) {
      entity.f(entity.dj().d(1.0, 0.0, 1.0));
   }

   public ItemStack a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      return new ItemStack(this);
   }

   public float i() {
      return this.aK;
   }

   public float j() {
      return this.aL;
   }

   public float l() {
      return this.aM;
   }

   protected void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata) {
      world.a(entityhuman, 2001, blockposition, i(iblockdata));
   }

   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      this.a(world, entityhuman, blockposition, iblockdata);
      if (iblockdata.a(TagsBlock.aT)) {
         PiglinAI.a(entityhuman, false);
      }

      world.a(GameEvent.f, blockposition, GameEvent.a.a(entityhuman, iblockdata));
   }

   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, BiomeBase.Precipitation biomebase_precipitation) {
   }

   public boolean a(Explosion explosion) {
      return true;
   }

   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
   }

   public BlockStateList<Block, IBlockData> n() {
      return this.D;
   }

   protected final void k(IBlockData iblockdata) {
      this.d = iblockdata;
   }

   public final IBlockData o() {
      return this.d;
   }

   public final IBlockData l(IBlockData iblockdata) {
      IBlockData iblockdata1 = this.o();

      for(IBlockState<?> iblockstate : iblockdata.b().n().d()) {
         if (iblockdata1.b(iblockstate)) {
            iblockdata1 = a(iblockdata, iblockdata1, iblockstate);
         }
      }

      return iblockdata1;
   }

   private static <T extends Comparable<T>> IBlockData a(IBlockData iblockdata, IBlockData iblockdata1, IBlockState<T> iblockstate) {
      return iblockdata1.a(iblockstate, iblockdata.c(iblockstate));
   }

   public SoundEffectType m(IBlockData iblockdata) {
      return this.aJ;
   }

   @Override
   public Item k() {
      if (this.f == null) {
         this.f = Item.a(this);
      }

      return this.f;
   }

   public boolean p() {
      return this.aN;
   }

   @Override
   public String toString() {
      return "Block{" + BuiltInRegistries.f.b(this) + "}";
   }

   public void a(ItemStack itemstack, @Nullable IBlockAccess iblockaccess, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
   }

   @Override
   protected Block q() {
      return this;
   }

   protected ImmutableMap<IBlockData, VoxelShape> a(Function<IBlockData, VoxelShape> function) {
      return this.D.a().stream().collect(ImmutableMap.toImmutableMap(Function.identity(), function));
   }

   @Deprecated
   public Holder.c<Block> r() {
      return this.b;
   }

   protected int tryDropExperience(WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, IntProvider intprovider) {
      if (EnchantmentManager.a(Enchantments.v, itemstack) == 0) {
         int i = intprovider.a(worldserver.z);
         if (i > 0) {
            return i;
         }
      }

      return 0;
   }

   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return 0;
   }

   public static float range(float min, float value, float max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static final class a {
      private final IBlockData a;
      private final IBlockData b;
      private final EnumDirection c;

      public a(IBlockData iblockdata, IBlockData iblockdata1, EnumDirection enumdirection) {
         this.a = iblockdata;
         this.b = iblockdata1;
         this.c = enumdirection;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (!(object instanceof Block.a)) {
            return false;
         } else {
            Block.a block_a = (Block.a)object;
            return this.a == block_a.a && this.b == block_a.b && this.c == block_a.c;
         }
      }

      @Override
      public int hashCode() {
         int i = this.a.hashCode();
         i = 31 * i + this.b.hashCode();
         return 31 * i + this.c.hashCode();
      }
   }
}
