package net.minecraft.server.level;

import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.InteractionResultWrapper;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemBisected;
import net.minecraft.world.item.ItemDebugStick;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.ItemActionContext;
import net.minecraft.world.level.EnumGamemode;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockCake;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.BlockTrapdoor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.slf4j.Logger;

public class PlayerInteractManager {
   private static final Logger a = LogUtils.getLogger();
   protected WorldServer c;
   protected final EntityPlayer d;
   private EnumGamemode b;
   @Nullable
   private EnumGamemode e;
   private boolean f;
   private int g;
   private BlockPosition h;
   private int i;
   private boolean j;
   private BlockPosition k;
   private int l;
   private int m;
   public boolean interactResult = false;
   public boolean firedInteract = false;
   public BlockPosition interactPosition;
   public EnumHand interactHand;
   public ItemStack interactItemStack;

   public PlayerInteractManager(EntityPlayer entityplayer) {
      this.b = EnumGamemode.e;
      this.h = BlockPosition.b;
      this.k = BlockPosition.b;
      this.m = -1;
      this.d = entityplayer;
      this.c = entityplayer.x();
   }

   public boolean a(EnumGamemode enumgamemode) {
      if (enumgamemode == this.b) {
         return false;
      } else {
         PlayerGameModeChangeEvent event = new PlayerGameModeChangeEvent(this.d.getBukkitEntity(), GameMode.getByValue(enumgamemode.a()));
         this.c.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            return false;
         } else {
            this.a(enumgamemode, this.e);
            this.d.w();
            this.d.c.ac().broadcastAll(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.c, this.d), this.d);
            this.c.e();
            return true;
         }
      }
   }

   protected void a(EnumGamemode enumgamemode, @Nullable EnumGamemode enumgamemode1) {
      this.e = enumgamemode1;
      this.b = enumgamemode;
      enumgamemode.a(this.d.fK());
   }

   public EnumGamemode b() {
      return this.b;
   }

   @Nullable
   public EnumGamemode c() {
      return this.e;
   }

   public boolean d() {
      return this.b.h();
   }

   public boolean e() {
      return this.b.g();
   }

   public void a() {
      this.i = MinecraftServer.currentTick;
      if (this.j) {
         IBlockData iblockdata = this.c.a_(this.k);
         if (iblockdata.h()) {
            this.j = false;
         } else {
            float f = this.a(iblockdata, this.k, this.l);
            if (f >= 1.0F) {
               this.j = false;
               this.a(this.k);
            }
         }
      } else if (this.f) {
         IBlockData iblockdata = this.c.a_(this.h);
         if (iblockdata.h()) {
            this.c.a(this.d.af(), this.h, -1);
            this.m = -1;
            this.f = false;
         } else {
            this.a(iblockdata, this.h, this.g);
         }
      }
   }

   private float a(IBlockData iblockdata, BlockPosition blockposition, int i) {
      int j = this.i - i;
      float f = iblockdata.a(this.d, this.d.H, blockposition) * (float)(j + 1);
      int k = (int)(f * 10.0F);
      if (k != this.m) {
         this.c.a(this.d.af(), blockposition, k);
         this.m = k;
      }

      return f;
   }

   private void a(BlockPosition blockposition, boolean flag, int i, String s) {
   }

   public void a(
      BlockPosition blockposition, PacketPlayInBlockDig.EnumPlayerDigType packetplayinblockdig_enumplayerdigtype, EnumDirection enumdirection, int i, int j
   ) {
      if (this.d.bk().g(Vec3D.b(blockposition)) > PlayerConnection.a) {
         this.a(blockposition, false, j, "too far");
      } else if (blockposition.v() >= i) {
         this.d.b.a(new PacketPlayOutBlockChange(blockposition, this.c.a_(blockposition)));
         this.a(blockposition, false, j, "too high");
      } else if (packetplayinblockdig_enumplayerdigtype == PacketPlayInBlockDig.EnumPlayerDigType.a) {
         if (!this.c.a(this.d, blockposition)) {
            CraftEventFactory.callPlayerInteractEvent(this.d, Action.LEFT_CLICK_BLOCK, blockposition, enumdirection, this.d.fJ().f(), EnumHand.a);
            this.d.b.a(new PacketPlayOutBlockChange(blockposition, this.c.a_(blockposition)));
            this.a(blockposition, false, j, "may not interact");
            TileEntity tileentity = this.c.c_(blockposition);
            if (tileentity != null) {
               this.d.b.a(tileentity.h());
            }

            return;
         }

         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(
            this.d, Action.LEFT_CLICK_BLOCK, blockposition, enumdirection, this.d.fJ().f(), EnumHand.a
         );
         if (event.isCancelled()) {
            this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
            TileEntity tileentity = this.c.c_(blockposition);
            if (tileentity != null) {
               this.d.b.a(tileentity.h());
            }

            return;
         }

         if (this.e()) {
            this.a(blockposition, j, "creative destroy");
            return;
         }

         if (this.d.eK().a(Items.uy) && ((ItemDebugStick)Items.uy).a(this.d, this.c.a_(blockposition), this.c, blockposition, false, this.d.eK())) {
            this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
            return;
         }

         if (this.d.a(this.c, blockposition, this.b)) {
            this.d.b.a(new PacketPlayOutBlockChange(blockposition, this.c.a_(blockposition)));
            this.a(blockposition, false, j, "block action restricted");
            return;
         }

         this.g = this.i;
         float f = 1.0F;
         IBlockData iblockdata = this.c.a_(blockposition);
         if (event.useInteractedBlock() == Result.DENY) {
            IBlockData data = this.c.a_(blockposition);
            if (data.b() instanceof BlockDoor) {
               boolean bottom = data.c(BlockDoor.e) == BlockPropertyDoubleBlockHalf.b;
               this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
               this.d.b.a(new PacketPlayOutBlockChange(this.c, bottom ? blockposition.c() : blockposition.d()));
            } else if (data.b() instanceof BlockTrapdoor) {
               this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
            }
         } else if (!iblockdata.h()) {
            iblockdata.a(this.c, blockposition, this.d);
            f = iblockdata.a(this.d, this.d.H, blockposition);
         }

         if (event.useItemInHand() == Result.DENY) {
            if (f > 1.0F) {
               this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
            }

            return;
         }

         BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.d, blockposition, this.d.fJ().f(), f >= 1.0F);
         if (blockEvent.isCancelled()) {
            this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));
            return;
         }

         if (blockEvent.getInstaBreak()) {
            f = 2.0F;
         }

         if (!iblockdata.h() && f >= 1.0F) {
            this.a(blockposition, j, "insta mine");
         } else {
            if (this.f) {
               this.d.b.a(new PacketPlayOutBlockChange(this.h, this.c.a_(this.h)));
               this.a(blockposition, false, j, "abort destroying since another started (client insta mine, server disagreed)");
            }

            this.f = true;
            this.h = blockposition.i();
            int k = (int)(f * 10.0F);
            this.c.a(this.d.af(), blockposition, k);
            this.a(blockposition, true, j, "actual start of destroying");
            this.m = k;
         }
      } else if (packetplayinblockdig_enumplayerdigtype == PacketPlayInBlockDig.EnumPlayerDigType.c) {
         if (blockposition.equals(this.h)) {
            int l = this.i - this.g;
            IBlockData iblockdata = this.c.a_(blockposition);
            if (!iblockdata.h()) {
               float f1 = iblockdata.a(this.d, this.d.H, blockposition) * (float)(l + 1);
               if (f1 >= 0.7F) {
                  this.f = false;
                  this.c.a(this.d.af(), blockposition, -1);
                  this.a(blockposition, j, "destroyed");
                  return;
               }

               if (!this.j) {
                  this.f = false;
                  this.j = true;
                  this.k = blockposition;
                  this.l = this.g;
               }
            }
         }

         this.a(blockposition, true, j, "stopped destroying");
      } else if (packetplayinblockdig_enumplayerdigtype == PacketPlayInBlockDig.EnumPlayerDigType.b) {
         this.f = false;
         if (!Objects.equals(this.h, blockposition)) {
            a.debug("Mismatch in destroy block pos: {} {}", this.h, blockposition);
            this.c.a(this.d.af(), this.h, -1);
            this.a(blockposition, true, j, "aborted mismatched destroying");
         }

         this.c.a(this.d.af(), blockposition, -1);
         this.a(blockposition, true, j, "aborted destroying");
         CraftEventFactory.callBlockDamageAbortEvent(this.d, blockposition, this.d.fJ().f());
      }
   }

   public void a(BlockPosition blockposition, int i, String s) {
      if (this.a(blockposition)) {
         this.a(blockposition, true, i, s);
      } else {
         this.d.b.a(new PacketPlayOutBlockChange(blockposition, this.c.a_(blockposition)));
         this.a(blockposition, false, i, s);
      }
   }

   public boolean a(BlockPosition blockposition) {
      IBlockData iblockdata = this.c.a_(blockposition);
      Block bblock = CraftBlock.at(this.c, blockposition);
      BlockBreakEvent event = null;
      if (this.d instanceof EntityPlayer) {
         boolean isSwordNoBreak = !this.d.eK().c().a(iblockdata, this.c, blockposition, this.d);
         if (this.c.c_(blockposition) == null && !isSwordNoBreak) {
            PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(blockposition, Blocks.a.o());
            this.d.b.a(packet);
         }

         event = new BlockBreakEvent(bblock, this.d.getBukkitEntity());
         event.setCancelled(isSwordNoBreak);
         IBlockData nmsData = this.c.a_(blockposition);
         net.minecraft.world.level.block.Block nmsBlock = nmsData.b();
         ItemStack itemstack = this.d.c(EnumItemSlot.a);
         if (nmsBlock != null && !event.isCancelled() && !this.e() && this.d.d(nmsBlock.o())) {
            event.setExpToDrop(nmsBlock.getExpDrop(nmsData, this.c, blockposition, itemstack, true));
         }

         this.c.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled()) {
            if (isSwordNoBreak) {
               return false;
            }

            this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition));

            EnumDirection[] var12;
            for(EnumDirection dir : var12 = EnumDirection.values()) {
               this.d.b.a(new PacketPlayOutBlockChange(this.c, blockposition.a(dir)));
            }

            TileEntity tileentity = this.c.c_(blockposition);
            if (tileentity != null) {
               this.d.b.a(tileentity.h());
            }

            return false;
         }
      }

      iblockdata = this.c.a_(blockposition);
      if (iblockdata.h()) {
         return false;
      } else {
         TileEntity tileentity = this.c.c_(blockposition);
         net.minecraft.world.level.block.Block block = iblockdata.b();
         if (block instanceof GameMasterBlock && !this.d.gg()) {
            this.c.a(blockposition, iblockdata, iblockdata, 3);
            return false;
         } else if (this.d.a(this.c, blockposition, this.b)) {
            return false;
         } else {
            BlockState state = bblock.getState();
            this.c.captureDrops = new ArrayList<>();
            block.a(this.c, blockposition, iblockdata, (EntityHuman)this.d);
            boolean flag = this.c.a(blockposition, false);
            if (flag) {
               block.a((GeneratorAccess)this.c, blockposition, iblockdata);
            }

            if (!this.e()) {
               ItemStack itemstack = this.d.eK();
               ItemStack itemstack1 = itemstack.o();
               boolean flag1 = this.d.d(iblockdata);
               itemstack.a(this.c, iblockdata, blockposition, this.d);
               if (flag && flag1 && event.isDropItems()) {
                  block.a(this.c, this.d, blockposition, iblockdata, tileentity, itemstack1);
               }
            }

            if (event.isDropItems()) {
               CraftEventFactory.handleBlockDropItemEvent(bblock, state, this.d, this.c.captureDrops);
            }

            this.c.captureDrops = null;
            if (flag && event != null) {
               iblockdata.b().a(this.c, blockposition, event.getExpToDrop());
            }

            return true;
         }
      }
   }

   public EnumInteractionResult a(EntityPlayer entityplayer, World world, ItemStack itemstack, EnumHand enumhand) {
      if (this.b == EnumGamemode.d) {
         return EnumInteractionResult.d;
      } else if (entityplayer.ge().a(itemstack.c())) {
         return EnumInteractionResult.d;
      } else {
         int i = itemstack.K();
         int j = itemstack.j();
         InteractionResultWrapper<ItemStack> interactionresultwrapper = itemstack.a(world, entityplayer, enumhand);
         ItemStack itemstack1 = interactionresultwrapper.b();
         if (itemstack1 == itemstack && itemstack1.K() == i && itemstack1.q() <= 0 && itemstack1.j() == j) {
            return interactionresultwrapper.a();
         } else if (interactionresultwrapper.a() == EnumInteractionResult.e && itemstack1.q() > 0 && !entityplayer.fe()) {
            return interactionresultwrapper.a();
         } else {
            if (itemstack != itemstack1) {
               entityplayer.a(enumhand, itemstack1);
            }

            if (this.e()) {
               itemstack1.f(i);
               if (itemstack1.h() && itemstack1.j() != j) {
                  itemstack1.b(j);
               }
            }

            if (itemstack1.b()) {
               entityplayer.a(enumhand, ItemStack.b);
            }

            if (!entityplayer.fe()) {
               entityplayer.bO.b();
            }

            return interactionresultwrapper.a();
         }
      }
   }

   public EnumInteractionResult a(
      EntityPlayer entityplayer, World world, ItemStack itemstack, EnumHand enumhand, MovingObjectPositionBlock movingobjectpositionblock
   ) {
      BlockPosition blockposition = movingobjectpositionblock.a();
      IBlockData iblockdata = world.a_(blockposition);
      EnumInteractionResult enuminteractionresult = EnumInteractionResult.d;
      boolean cancelledBlock = false;
      if (!iblockdata.b().a(world.G())) {
         return EnumInteractionResult.e;
      } else {
         if (this.b == EnumGamemode.d) {
            ITileInventory itileinventory = iblockdata.b(world, blockposition);
            cancelledBlock = !(itileinventory instanceof ITileInventory);
         }

         if (entityplayer.ge().a(itemstack.c())) {
            cancelledBlock = true;
         }

         PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(
            entityplayer, Action.RIGHT_CLICK_BLOCK, blockposition, movingobjectpositionblock.b(), itemstack, cancelledBlock, enumhand
         );
         this.firedInteract = true;
         this.interactResult = event.useItemInHand() == Result.DENY;
         this.interactPosition = blockposition.i();
         this.interactHand = enumhand;
         this.interactItemStack = itemstack.o();
         if (event.useInteractedBlock() == Result.DENY) {
            if (iblockdata.b() instanceof BlockDoor) {
               boolean bottom = iblockdata.c(BlockDoor.e) == BlockPropertyDoubleBlockHalf.b;
               entityplayer.b.a(new PacketPlayOutBlockChange(world, bottom ? blockposition.c() : blockposition.d()));
            } else if (iblockdata.b() instanceof BlockCake) {
               entityplayer.getBukkitEntity().sendHealthUpdate();
            } else if (this.interactItemStack.c() instanceof ItemBisected) {
               entityplayer.b.a(new PacketPlayOutBlockChange(world, blockposition.a(movingobjectpositionblock.b()).c()));
               entityplayer.b.a(new PacketPlayOutBlockChange(world, blockposition.c()));
            }

            entityplayer.getBukkitEntity().updateInventory();
            enuminteractionresult = event.useItemInHand() != Result.ALLOW ? EnumInteractionResult.a : EnumInteractionResult.d;
         } else {
            if (this.b == EnumGamemode.d) {
               ITileInventory itileinventory = iblockdata.b(world, blockposition);
               if (itileinventory != null) {
                  entityplayer.a(itileinventory);
                  return EnumInteractionResult.a;
               }

               return EnumInteractionResult.d;
            }

            boolean flag = !entityplayer.eK().b() || !entityplayer.eL().b();
            boolean flag1 = entityplayer.fz() && flag;
            ItemStack itemstack1 = itemstack.o();
            if (!flag1) {
               enuminteractionresult = iblockdata.a(world, entityplayer, enumhand, movingobjectpositionblock);
               if (enuminteractionresult.a()) {
                  CriterionTriggers.M.a(entityplayer, blockposition, itemstack1);
                  return enuminteractionresult;
               }
            }

            if (!itemstack.b() && enuminteractionresult != EnumInteractionResult.a && !this.interactResult) {
               ItemActionContext itemactioncontext = new ItemActionContext(entityplayer, enumhand, movingobjectpositionblock);
               EnumInteractionResult enuminteractionresult1;
               if (this.e()) {
                  int i = itemstack.K();
                  enuminteractionresult1 = itemstack.useOn(itemactioncontext, enumhand);
                  itemstack.f(i);
               } else {
                  enuminteractionresult1 = itemstack.useOn(itemactioncontext, enumhand);
               }

               if (enuminteractionresult1.a()) {
                  CriterionTriggers.M.a(entityplayer, blockposition, itemstack1);
               }

               return enuminteractionresult1;
            }
         }

         return enuminteractionresult;
      }
   }

   public void a(WorldServer worldserver) {
      this.c = worldserver;
   }
}
