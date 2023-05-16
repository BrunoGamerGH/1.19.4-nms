package net.minecraft.world.entity.vehicle;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.piglin.PiglinAI;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.inventory.ContainerChest;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEvent;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(EntityTypes<? extends EntityMinecartChest> var0, World var1) {
      super(var0, var1);
   }

   public EntityMinecartChest(World var0, double var1, double var3, double var5) {
      super(EntityTypes.p, var1, var3, var5, var0);
   }

   @Override
   protected Item i() {
      return Items.mX;
   }

   @Override
   public int b() {
      return 27;
   }

   @Override
   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.b;
   }

   @Override
   public IBlockData v() {
      return Blocks.cu.o().a(BlockChest.b, EnumDirection.c);
   }

   @Override
   public int x() {
      return 8;
   }

   @Override
   public Container a(int var0, PlayerInventory var1) {
      return ContainerChest.a(var0, var1, this);
   }

   @Override
   public void c(EntityHuman var0) {
      this.H.a(GameEvent.j, this.de(), GameEvent.a.a(var0));
   }

   @Override
   public EnumInteractionResult a(EntityHuman var0, EnumHand var1) {
      EnumInteractionResult var2 = this.c_(var0);
      if (var2.a()) {
         this.a(GameEvent.k, var0);
         PiglinAI.a(var0, true);
      }

      return var2;
   }
}
