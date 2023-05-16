package net.minecraft.gametest.framework;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockLectern;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import org.apache.commons.lang3.exception.ExceptionUtils;

class ReportGameListener implements GameTestHarnessListener {
   private final GameTestHarnessInfo c;
   private final GameTestHarnessTicker d;
   private final BlockPosition e;
   int a;
   int b;

   public ReportGameListener(GameTestHarnessInfo var0, GameTestHarnessTicker var1, BlockPosition var2) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.a = 0;
      this.b = 0;
   }

   @Override
   public void a(GameTestHarnessInfo var0) {
      a(this.c, Blocks.eq);
      ++this.a;
   }

   @Override
   public void b(GameTestHarnessInfo var0) {
      ++this.b;
      if (!var0.x()) {
         a(var0, var0.c() + " passed! (" + var0.l() + "ms)");
      } else {
         if (this.b >= var0.z()) {
            a(var0, var0 + " passed " + this.b + " times of " + this.a + " attempts.");
         } else {
            a(this.c.g(), EnumChatFormat.k, "Flaky test " + this.c + " succeeded, attempt: " + this.a + " successes: " + this.b);
            this.a();
         }
      }
   }

   @Override
   public void c(GameTestHarnessInfo var0) {
      if (!var0.x()) {
         a(var0, var0.n());
      } else {
         GameTestHarnessTestFunction var1 = this.c.v();
         String var2 = "Flaky test " + this.c + " failed, attempt: " + this.a + "/" + var1.i();
         if (var1.j() > 1) {
            var2 = var2 + ", successes: " + this.b + " (" + var1.j() + " required)";
         }

         a(this.c.g(), EnumChatFormat.o, var2);
         if (var0.y() - this.a + this.b >= var0.z()) {
            this.a();
         } else {
            a(var0, new ExhaustedAttemptsException(this.a, this.b, var0));
         }
      }
   }

   public static void a(GameTestHarnessInfo var0, String var1) {
      a(var0, Blocks.en);
      b(var0, var1);
   }

   private static void b(GameTestHarnessInfo var0, String var1) {
      a(var0.g(), EnumChatFormat.k, var1);
      GlobalTestReporter.b(var0);
   }

   protected static void a(GameTestHarnessInfo var0, Throwable var1) {
      a(var0, var0.r() ? Blocks.ew : Blocks.ej);
      c(var0, SystemUtils.c(var1));
      b(var0, var1);
   }

   protected static void b(GameTestHarnessInfo var0, Throwable var1) {
      String var2 = var1.getMessage() + (var1.getCause() == null ? "" : " cause: " + SystemUtils.c(var1.getCause()));
      String var3 = (var0.r() ? "" : "(optional) ") + var0.c() + " failed! " + var2;
      a(var0.g(), var0.r() ? EnumChatFormat.m : EnumChatFormat.o, var3);
      Throwable var4 = (Throwable)MoreObjects.firstNonNull(ExceptionUtils.getRootCause(var1), var1);
      if (var4 instanceof GameTestHarnessAssertionPosition var5) {
         a(var0.g(), var5.c(), var5.a());
      }

      GlobalTestReporter.a(var0);
   }

   private void a() {
      this.c.o();
      GameTestHarnessInfo var0 = new GameTestHarnessInfo(this.c.v(), this.c.u(), this.c.g());
      var0.a();
      this.d.a(var0);
      var0.a(this);
      var0.a(this.e, 2);
   }

   protected static void a(GameTestHarnessInfo var0, Block var1) {
      WorldServer var2 = var0.g();
      BlockPosition var3 = var0.d();
      BlockPosition var4 = new BlockPosition(-1, -1, -1);
      BlockPosition var5 = DefinedStructure.a(var3.a(var4), EnumBlockMirror.a, var0.u(), var3);
      var2.b(var5, Blocks.fN.o().a(var0.u()));
      BlockPosition var6 = var5.b(0, 1, 0);
      var2.b(var6, var1.o());

      for(int var7 = -1; var7 <= 1; ++var7) {
         for(int var8 = -1; var8 <= 1; ++var8) {
            BlockPosition var9 = var5.b(var7, -1, var8);
            var2.b(var9, Blocks.ch.o());
         }
      }
   }

   private static void c(GameTestHarnessInfo var0, String var1) {
      WorldServer var2 = var0.g();
      BlockPosition var3 = var0.d();
      BlockPosition var4 = new BlockPosition(-1, 1, -1);
      BlockPosition var5 = DefinedStructure.a(var3.a(var4), EnumBlockMirror.a, var0.u(), var3);
      var2.b(var5, Blocks.nW.o().a(var0.u()));
      IBlockData var6 = var2.a_(var5);
      ItemStack var7 = a(var0.c(), var0.r(), var1);
      BlockLectern.a(null, var2, var5, var6, var7);
   }

   private static ItemStack a(String var0, boolean var1, String var2) {
      ItemStack var3 = new ItemStack(Items.tc);
      NBTTagList var4 = new NBTTagList();
      StringBuffer var5 = new StringBuffer();
      Arrays.stream(var0.split("\\.")).forEach(var1x -> var5.append(var1x).append('\n'));
      if (!var1) {
         var5.append("(optional)\n");
      }

      var5.append("-------------------\n");
      var4.add(NBTTagString.a(var5 + var2));
      var3.a("pages", var4);
      return var3;
   }

   protected static void a(WorldServer var0, EnumChatFormat var1, String var2) {
      var0.a(var0x -> true).forEach(var2x -> var2x.a(IChatBaseComponent.b(var2).a(var1)));
   }

   private static void a(WorldServer var0, BlockPosition var1, String var2) {
      PacketDebug.a(var0, var1, var2, -2130771968, Integer.MAX_VALUE);
   }
}
