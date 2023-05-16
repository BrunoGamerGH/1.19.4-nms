package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.ContainerUtil;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.IInventory;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.monster.EntityShulker;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityShulkerBox;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.storage.loot.LootTableInfo;
import net.minecraft.world.level.storage.loot.parameters.LootContextParameters;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockShulkerBox extends BlockTileEntity {
   private static final float c = 1.0F;
   private static final VoxelShape d = Block.a(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape e = Block.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape g = Block.a(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape h = Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape i = Block.a(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private static final Map<EnumDirection, VoxelShape> j = SystemUtils.a(Maps.newEnumMap(EnumDirection.class), var0 -> {
      var0.put(EnumDirection.c, h);
      var0.put(EnumDirection.f, g);
      var0.put(EnumDirection.d, i);
      var0.put(EnumDirection.e, f);
      var0.put(EnumDirection.b, d);
      var0.put(EnumDirection.a, e);
   });
   public static final BlockStateEnum<EnumDirection> a = BlockDirectional.a;
   public static final MinecraftKey b = new MinecraftKey("contents");
   @Nullable
   public final EnumColor k;

   public BlockShulkerBox(@Nullable EnumColor var0, BlockBase.Info var1) {
      super(var1);
      this.k = var0;
      this.k(this.D.b().a(a, EnumDirection.b));
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityShulkerBox(this.k, var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var2, TileEntityTypes.x, TileEntityShulkerBox::a);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.b;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else if (var3.F_()) {
         return EnumInteractionResult.b;
      } else {
         TileEntity var6 = var1.c_(var2);
         if (var6 instanceof TileEntityShulkerBox var7) {
            if (a(var0, var1, var2, var7)) {
               var3.a(var7);
               var3.a(StatisticList.aq);
               PiglinAI.a(var3, true);
            }

            return EnumInteractionResult.b;
         } else {
            return EnumInteractionResult.d;
         }
      }
   }

   private static boolean a(IBlockData var0, World var1, BlockPosition var2, TileEntityShulkerBox var3) {
      if (var3.i() != TileEntityShulkerBox.AnimationPhase.a) {
         return true;
      } else {
         AxisAlignedBB var4 = EntityShulker.a(var0.c(a), 0.0F, 0.5F).a(var2).h(1.0E-6);
         return var1.b(var4);
      }
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(a, var0.k());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityHuman var3) {
      TileEntity var4 = var0.c_(var1);
      if (var4 instanceof TileEntityShulkerBox var5) {
         if (!var0.B && var3.f() && !var5.aa_()) {
            ItemStack var6 = b(this.b());
            var4.e(var6);
            if (var5.aa()) {
               var6.a(var5.ab());
            }

            EntityItem var7 = new EntityItem(var0, (double)var1.u() + 0.5, (double)var1.v() + 0.5, (double)var1.w() + 0.5, var6);
            var7.k();
            var0.b(var7);
         } else {
            var5.e(var3);
         }
      }

      super.a(var0, var1, var2, var3);
   }

   @Override
   public List<ItemStack> a(IBlockData var0, LootTableInfo.Builder var1) {
      TileEntity var2 = var1.b(LootContextParameters.h);
      if (var2 instanceof TileEntityShulkerBox var3) {
         var1 = var1.a(b, (var1x, var2x) -> {
            for(int var3x = 0; var3x < var3.b(); ++var3x) {
               var2x.accept(var3.a(var3x));
            }
         });
      }

      return super.a(var0, var1);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityShulkerBox) {
            ((TileEntityShulkerBox)var5).a(var4.x());
         }
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof TileEntityShulkerBox) {
            var1.c(var2, var0.b());
         }

         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public void a(ItemStack var0, @Nullable IBlockAccess var1, List<IChatBaseComponent> var2, TooltipFlag var3) {
      super.a(var0, var1, var2, var3);
      NBTTagCompound var4 = ItemBlock.a(var0);
      if (var4 != null) {
         if (var4.b("LootTable", 8)) {
            var2.add(IChatBaseComponent.b("???????"));
         }

         if (var4.b("Items", 9)) {
            NonNullList<ItemStack> var5 = NonNullList.a(27, ItemStack.b);
            ContainerUtil.b(var4, var5);
            int var6 = 0;
            int var7 = 0;

            for(ItemStack var9 : var5) {
               if (!var9.b()) {
                  ++var7;
                  if (var6 <= 4) {
                     ++var6;
                     IChatMutableComponent var10 = var9.x().e();
                     var10.f(" x").f(String.valueOf(var9.K()));
                     var2.add(var10);
                  }
               }
            }

            if (var7 - var6 > 0) {
               var2.add(IChatBaseComponent.a("container.shulkerBox.more", var7 - var6).a(EnumChatFormat.u));
            }
         }
      }
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }

   @Override
   public VoxelShape b_(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      TileEntity var3 = var1.c_(var2);
      if (var3 instanceof TileEntityShulkerBox var4 && !var4.v()) {
         return j.get(var0.c(a).g());
      }

      return VoxelShapes.b();
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      TileEntity var4 = var1.c_(var2);
      return var4 instanceof TileEntityShulkerBox ? VoxelShapes.a(((TileEntityShulkerBox)var4).a(var0)) : VoxelShapes.b();
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return Container.b((IInventory)var1.c_(var2));
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      ItemStack var3 = super.a(var0, var1, var2);
      var0.a(var1, TileEntityTypes.x).ifPresent(var1x -> var1x.e(var3));
      return var3;
   }

   @Nullable
   public static EnumColor b(Item var0) {
      return a(Block.a(var0));
   }

   @Nullable
   public static EnumColor a(Block var0) {
      return var0 instanceof BlockShulkerBox ? ((BlockShulkerBox)var0).b() : null;
   }

   public static Block a(@Nullable EnumColor var0) {
      if (var0 == null) {
         return Blocks.kM;
      } else {
         switch(var0) {
            case a:
               return Blocks.kN;
            case b:
               return Blocks.kO;
            case c:
               return Blocks.kP;
            case d:
               return Blocks.kQ;
            case e:
               return Blocks.kR;
            case f:
               return Blocks.kS;
            case g:
               return Blocks.kT;
            case h:
               return Blocks.kU;
            case i:
               return Blocks.kV;
            case j:
               return Blocks.kW;
            case k:
            default:
               return Blocks.kX;
            case l:
               return Blocks.kY;
            case m:
               return Blocks.kZ;
            case n:
               return Blocks.la;
            case o:
               return Blocks.lb;
            case p:
               return Blocks.lc;
         }
      }
   }

   @Nullable
   public EnumColor b() {
      return this.k;
   }

   public static ItemStack b(@Nullable EnumColor var0) {
      return new ItemStack(a(var0));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }
}
