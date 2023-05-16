package net.minecraft.world.level;

import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.UtilColor;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;

public abstract class CommandBlockListenerAbstract implements ICommandListener {
   private static final SimpleDateFormat b = new SimpleDateFormat("HH:mm:ss");
   private static final IChatBaseComponent c = IChatBaseComponent.b("@");
   private long d = -1L;
   private boolean e = true;
   private int f;
   private boolean g = true;
   @Nullable
   private IChatBaseComponent h;
   private String i = "";
   private IChatBaseComponent j = c;

   @Override
   public abstract CommandSender getBukkitSender(CommandListenerWrapper var1);

   public int j() {
      return this.f;
   }

   public void a(int i) {
      this.f = i;
   }

   public IChatBaseComponent k() {
      return this.h == null ? CommonComponents.a : this.h;
   }

   public NBTTagCompound a(NBTTagCompound nbttagcompound) {
      nbttagcompound.a("Command", this.i);
      nbttagcompound.a("SuccessCount", this.f);
      nbttagcompound.a("CustomName", IChatBaseComponent.ChatSerializer.a(this.j));
      nbttagcompound.a("TrackOutput", this.g);
      if (this.h != null && this.g) {
         nbttagcompound.a("LastOutput", IChatBaseComponent.ChatSerializer.a(this.h));
      }

      nbttagcompound.a("UpdateLastExecution", this.e);
      if (this.e && this.d > 0L) {
         nbttagcompound.a("LastExecution", this.d);
      }

      return nbttagcompound;
   }

   public void b(NBTTagCompound nbttagcompound) {
      this.i = nbttagcompound.l("Command");
      this.f = nbttagcompound.h("SuccessCount");
      if (nbttagcompound.b("CustomName", 8)) {
         this.b(IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("CustomName")));
      }

      if (nbttagcompound.b("TrackOutput", 1)) {
         this.g = nbttagcompound.q("TrackOutput");
      }

      if (nbttagcompound.b("LastOutput", 8) && this.g) {
         try {
            this.h = IChatBaseComponent.ChatSerializer.a(nbttagcompound.l("LastOutput"));
         } catch (Throwable var3) {
            this.h = IChatBaseComponent.b(var3.getMessage());
         }
      } else {
         this.h = null;
      }

      if (nbttagcompound.e("UpdateLastExecution")) {
         this.e = nbttagcompound.q("UpdateLastExecution");
      }

      if (this.e && nbttagcompound.e("LastExecution")) {
         this.d = nbttagcompound.i("LastExecution");
      } else {
         this.d = -1L;
      }
   }

   public void a(String s) {
      this.i = s;
      this.f = 0;
   }

   public String l() {
      return this.i;
   }

   public boolean a(World world) {
      if (world.B || world.U() == this.d) {
         return false;
      } else if ("Searge".equalsIgnoreCase(this.i)) {
         this.h = IChatBaseComponent.b("#itzlipofutzli");
         this.f = 1;
         return true;
      } else {
         this.f = 0;
         MinecraftServer minecraftserver = this.e().n();
         if (minecraftserver.o() && !UtilColor.b(this.i)) {
            try {
               this.h = null;
               CommandListenerWrapper commandlistenerwrapper = this.i().a((ResultConsumer<CommandListenerWrapper>)((commandcontext, flag, i) -> {
                  if (flag) {
                     ++this.f;
                  }
               }));
               minecraftserver.aC().dispatchServerCommand(commandlistenerwrapper, this.i);
            } catch (Throwable var6) {
               CrashReport crashreport = CrashReport.a(var6, "Executing command block");
               CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Command to be executed");
               crashreportsystemdetails.a("Command", this::l);
               crashreportsystemdetails.a("Name", () -> this.m().getString());
               throw new ReportedException(crashreport);
            }
         }

         if (this.e) {
            this.d = world.U();
         } else {
            this.d = -1L;
         }

         return true;
      }
   }

   public IChatBaseComponent m() {
      return this.j;
   }

   public void b(@Nullable IChatBaseComponent ichatbasecomponent) {
      if (ichatbasecomponent != null) {
         this.j = ichatbasecomponent;
      } else {
         this.j = c;
      }
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      if (this.g) {
         SimpleDateFormat simpledateformat = b;
         Date date = new Date();
         this.h = IChatBaseComponent.b("[" + simpledateformat.format(date) + "] ").b(ichatbasecomponent);
         this.f();
      }
   }

   public abstract WorldServer e();

   public abstract void f();

   public void c(@Nullable IChatBaseComponent ichatbasecomponent) {
      this.h = ichatbasecomponent;
   }

   public void a(boolean flag) {
      this.g = flag;
   }

   public boolean n() {
      return this.g;
   }

   public EnumInteractionResult a(EntityHuman entityhuman) {
      if (!entityhuman.gg()) {
         return EnumInteractionResult.d;
      } else {
         if (entityhuman.cG().B) {
            entityhuman.a(this);
         }

         return EnumInteractionResult.a(entityhuman.H.B);
      }
   }

   public abstract Vec3D g();

   public abstract CommandListenerWrapper i();

   @Override
   public boolean d_() {
      return this.e().W().b(GameRules.o) && this.g;
   }

   @Override
   public boolean j_() {
      return this.g;
   }

   @Override
   public boolean M_() {
      return this.e().W().b(GameRules.i);
   }
}
