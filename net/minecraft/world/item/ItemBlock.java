package net.minecraft.world.item;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockShulkerBox;
import net.minecraft.world.level.block.SoundEffectType;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.IBlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockStates;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ItemBlock extends Item {
   public static final String a = "BlockEntityTag";
   public static final String b = "BlockStateTag";
   @Deprecated
   private final Block c;

   public ItemBlock(Block block, Item.Info item_info) {
      super(item_info);
      this.c = block;
   }

   @Override
   public EnumInteractionResult a(ItemActionContext itemactioncontext) {
      EnumInteractionResult enuminteractionresult = this.a(new BlockActionContext(itemactioncontext));
      if (!enuminteractionresult.a() && this.u()) {
         EnumInteractionResult enuminteractionresult1 = this.a(itemactioncontext.q(), itemactioncontext.o(), itemactioncontext.p()).a();
         return enuminteractionresult1 == EnumInteractionResult.b ? EnumInteractionResult.c : enuminteractionresult1;
      } else {
         return enuminteractionresult;
      }
   }

   public EnumInteractionResult a(BlockActionContext blockactioncontext) {
      if (!this.e().a(blockactioncontext.q().G())) {
         return EnumInteractionResult.e;
      } else if (!blockactioncontext.b()) {
         return EnumInteractionResult.e;
      } else {
         BlockActionContext blockactioncontext1 = this.b(blockactioncontext);
         if (blockactioncontext1 == null) {
            return EnumInteractionResult.e;
         } else {
            IBlockData iblockdata = this.c(blockactioncontext1);
            BlockState blockstate = null;
            if (this instanceof PlaceOnWaterBlockItem || this instanceof SolidBucketItem) {
               blockstate = CraftBlockStates.getBlockState(blockactioncontext1.q(), blockactioncontext1.a());
            }

            if (iblockdata == null) {
               return EnumInteractionResult.e;
            } else if (!this.a(blockactioncontext1, iblockdata)) {
               return EnumInteractionResult.e;
            } else {
               BlockPosition blockposition = blockactioncontext1.a();
               World world = blockactioncontext1.q();
               EntityHuman entityhuman = blockactioncontext1.o();
               ItemStack itemstack = blockactioncontext1.n();
               IBlockData iblockdata1 = world.a_(blockposition);
               if (iblockdata1.a(iblockdata.b())) {
                  iblockdata1 = this.a(blockposition, world, itemstack, iblockdata1);
                  this.a(blockposition, world, entityhuman, itemstack, iblockdata1);
                  iblockdata1.b().a(world, blockposition, iblockdata1, entityhuman, itemstack);
                  if (blockstate != null) {
                     BlockPlaceEvent placeEvent = CraftEventFactory.callBlockPlaceEvent(
                        (WorldServer)world, entityhuman, blockactioncontext1.p(), blockstate, blockposition.u(), blockposition.v(), blockposition.w()
                     );
                     if (placeEvent != null && (placeEvent.isCancelled() || !placeEvent.canBuild())) {
                        blockstate.update(true, false);
                        if (this instanceof SolidBucketItem) {
                           ((EntityPlayer)entityhuman).getBukkitEntity().updateInventory();
                        }

                        return EnumInteractionResult.e;
                     }
                  }

                  if (entityhuman instanceof EntityPlayer) {
                     CriterionTriggers.y.a((EntityPlayer)entityhuman, blockposition, itemstack);
                  }
               }

               SoundEffectType soundeffecttype = iblockdata1.t();
               world.a(GameEvent.i, blockposition, GameEvent.a.a(entityhuman, iblockdata1));
               if ((entityhuman == null || !entityhuman.fK().d) && itemstack != ItemStack.b) {
                  itemstack.h(1);
               }

               return EnumInteractionResult.a(world.B);
            }
         }
      }
   }

   protected SoundEffect a(IBlockData iblockdata) {
      return iblockdata.t().e();
   }

   @Nullable
   public BlockActionContext b(BlockActionContext blockactioncontext) {
      return blockactioncontext;
   }

   protected boolean a(BlockPosition blockposition, World world, @Nullable EntityHuman entityhuman, ItemStack itemstack, IBlockData iblockdata) {
      return a(world, entityhuman, blockposition, itemstack);
   }

   @Nullable
   protected IBlockData c(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = this.e().a(blockactioncontext);
      return iblockdata != null && this.b(blockactioncontext, iblockdata) ? iblockdata : null;
   }

   private IBlockData a(BlockPosition blockposition, World world, ItemStack itemstack, IBlockData iblockdata) {
      IBlockData iblockdata1 = iblockdata;
      NBTTagCompound nbttagcompound = itemstack.u();
      if (nbttagcompound != null) {
         NBTTagCompound nbttagcompound1 = nbttagcompound.p("BlockStateTag");
         iblockdata1 = getBlockState(iblockdata, nbttagcompound1);
      }

      if (iblockdata1 != iblockdata) {
         world.a(blockposition, iblockdata1, 2);
      }

      return iblockdata1;
   }

   public static IBlockData getBlockState(IBlockData iblockdata, NBTTagCompound nbttagcompound1) {
      IBlockData iblockdata1 = iblockdata;
      BlockStateList<Block, IBlockData> blockstatelist = iblockdata.b().n();

      for(String s : nbttagcompound1.e()) {
         IBlockState<?> iblockstate = blockstatelist.a(s);
         if (iblockstate != null) {
            String s1 = nbttagcompound1.c(s).f_();
            iblockdata1 = a(iblockdata1, iblockstate, s1);
         }
      }

      return iblockdata1;
   }

   private static <T extends Comparable<T>> IBlockData a(IBlockData iblockdata, IBlockState<T> iblockstate, String s) {
      return iblockstate.b(s).map(comparable -> iblockdata.a(iblockstate, comparable)).orElse(iblockdata);
   }

   protected boolean b(BlockActionContext blockactioncontext, IBlockData iblockdata) {
      EntityHuman entityhuman = blockactioncontext.o();
      VoxelShapeCollision voxelshapecollision = entityhuman == null ? VoxelShapeCollision.a() : VoxelShapeCollision.a(entityhuman);
      boolean defaultReturn = (!this.d() || iblockdata.a((IWorldReader)blockactioncontext.q(), blockactioncontext.a()))
         && blockactioncontext.q().a(iblockdata, blockactioncontext.a(), voxelshapecollision);
      Player player = blockactioncontext.o() instanceof EntityPlayer ? (Player)blockactioncontext.o().getBukkitEntity() : null;
      BlockCanBuildEvent event = new BlockCanBuildEvent(
         CraftBlock.at(blockactioncontext.q(), blockactioncontext.a()), player, CraftBlockData.fromData(iblockdata), defaultReturn
      );
      blockactioncontext.q().getCraftServer().getPluginManager().callEvent(event);
      return event.isBuildable();
   }

   protected boolean d() {
      return true;
   }

   protected boolean a(BlockActionContext blockactioncontext, IBlockData iblockdata) {
      return blockactioncontext.q().a(blockactioncontext.a(), iblockdata, 11);
   }

   public static boolean a(World world, @Nullable EntityHuman entityhuman, BlockPosition blockposition, ItemStack itemstack) {
      MinecraftServer minecraftserver = world.n();
      if (minecraftserver == null) {
         return false;
      } else {
         NBTTagCompound nbttagcompound = a(itemstack);
         if (nbttagcompound != null) {
            TileEntity tileentity = world.c_(blockposition);
            if (tileentity != null) {
               if (!world.B
                  && tileentity.t()
                  && (entityhuman == null || !entityhuman.gg() && (!entityhuman.fK().d || !entityhuman.getBukkitEntity().hasPermission("minecraft.nbt.place")))
                  )
                {
                  return false;
               }

               NBTTagCompound nbttagcompound1 = tileentity.o();
               NBTTagCompound nbttagcompound2 = nbttagcompound1.h();
               nbttagcompound1.a(nbttagcompound);
               if (!nbttagcompound1.equals(nbttagcompound2)) {
                  tileentity.a(nbttagcompound1);
                  tileentity.e();
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   public String a() {
      return this.e().h();
   }

   @Override
   public void a(ItemStack itemstack, @Nullable World world, List<IChatBaseComponent> list, TooltipFlag tooltipflag) {
      super.a(itemstack, world, list, tooltipflag);
      this.e().a(itemstack, world, list, tooltipflag);
   }

   public Block e() {
      return this.c;
   }

   public void a(Map<Block, Item> map, Item item) {
      map.put(this.e(), item);
   }

   @Override
   public boolean ag_() {
      return !(this.c instanceof BlockShulkerBox);
   }

   @Override
   public void a(EntityItem entityitem) {
      if (this.c instanceof BlockShulkerBox) {
         ItemStack itemstack = entityitem.i();
         NBTTagCompound nbttagcompound = a(itemstack);
         if (nbttagcompound != null && nbttagcompound.b("Items", 9)) {
            NBTTagList nbttaglist = nbttagcompound.c("Items", 10);
            Stream<NBTBase> stream = nbttaglist.stream();
            ItemLiquidUtil.a(entityitem, stream.map(NBTTagCompound.class::cast).map(ItemStack::a));
         }
      }
   }

   @Nullable
   public static NBTTagCompound a(ItemStack itemstack) {
      return itemstack.b("BlockEntityTag");
   }

   public static void a(ItemStack itemstack, TileEntityTypes<?> tileentitytypes, NBTTagCompound nbttagcompound) {
      if (nbttagcompound.g()) {
         itemstack.c("BlockEntityTag");
      } else {
         TileEntity.a(nbttagcompound, tileentitytypes);
         itemstack.a("BlockEntityTag", nbttagcompound);
      }
   }

   @Override
   public FeatureFlagSet m() {
      return this.e().m();
   }
}
