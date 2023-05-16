package net.minecraft.world.level.block.entity;

import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.UUID;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.ChatClickable;
import net.minecraft.network.chat.ChatComponentUtils;
import net.minecraft.network.chat.ChatModifier;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.game.PacketPlayOutTileEntityData;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.FormattedString;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.command.CraftBlockCommandSender;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;

public class TileEntitySign extends TileEntity implements ICommandListener {
   public static final int a = 4;
   private static final int b = 90;
   private static final int c = 10;
   private static final String[] d = new String[]{"Text1", "Text2", "Text3", "Text4"};
   private static final String[] e = new String[]{"FilteredText1", "FilteredText2", "FilteredText3", "FilteredText4"};
   public final IChatBaseComponent[] f = new IChatBaseComponent[]{CommonComponents.a, CommonComponents.a, CommonComponents.a, CommonComponents.a};
   private final IChatBaseComponent[] g = new IChatBaseComponent[]{CommonComponents.a, CommonComponents.a, CommonComponents.a, CommonComponents.a};
   public boolean h = true;
   @Nullable
   private UUID i;
   @Nullable
   private FormattedString[] j;
   private boolean k;
   private EnumColor l = EnumColor.p;
   private boolean m;

   public TileEntitySign(BlockPosition blockposition, IBlockData iblockdata) {
      super(TileEntityTypes.h, blockposition, iblockdata);
   }

   public TileEntitySign(TileEntityTypes tileentitytypes, BlockPosition blockposition, IBlockData iblockdata) {
      super(tileentitytypes, blockposition, iblockdata);
   }

   public int c() {
      return 10;
   }

   public int d() {
      return 90;
   }

   @Override
   protected void b(NBTTagCompound nbttagcompound) {
      super.b(nbttagcompound);

      for(int i = 0; i < 4; ++i) {
         IChatBaseComponent ichatbasecomponent = this.f[i];
         String s = IChatBaseComponent.ChatSerializer.a(ichatbasecomponent);
         nbttagcompound.a(d[i], s);
         IChatBaseComponent ichatbasecomponent1 = this.g[i];
         if (!ichatbasecomponent1.equals(ichatbasecomponent)) {
            nbttagcompound.a(e[i], IChatBaseComponent.ChatSerializer.a(ichatbasecomponent1));
         }
      }

      if (Boolean.getBoolean("convertLegacySigns")) {
         nbttagcompound.a("Bukkit.isConverted", true);
      }

      nbttagcompound.a("Color", this.l.b());
      nbttagcompound.a("GlowingText", this.m);
   }

   @Override
   public void a(NBTTagCompound nbttagcompound) {
      this.h = false;
      super.a(nbttagcompound);
      this.l = EnumColor.a(nbttagcompound.l("Color"), EnumColor.p);
      boolean oldSign = Boolean.getBoolean("convertLegacySigns") && !nbttagcompound.q("Bukkit.isConverted");

      for(int i = 0; i < 4; ++i) {
         String s = nbttagcompound.l(d[i]);
         if (s != null && s.length() > 2048) {
            s = "\"\"";
         }

         if (oldSign) {
            this.f[i] = CraftChatMessage.fromString(s)[0];
         } else {
            IChatBaseComponent ichatbasecomponent = this.a(s);
            this.f[i] = ichatbasecomponent;
            String s1 = e[i];
            if (nbttagcompound.b(s1, 8)) {
               this.g[i] = this.a(nbttagcompound.l(s1));
            } else {
               this.g[i] = ichatbasecomponent;
            }
         }
      }

      this.j = null;
      this.m = nbttagcompound.q("GlowingText");
   }

   private IChatBaseComponent a(String s) {
      IChatBaseComponent ichatbasecomponent = this.b(s);
      if (this.o instanceof WorldServer) {
         try {
            return ChatComponentUtils.a(this.b(null), ichatbasecomponent, null, 0);
         } catch (CommandSyntaxException var4) {
         }
      }

      return ichatbasecomponent;
   }

   private IChatBaseComponent b(String s) {
      try {
         IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.ChatSerializer.a(s);
         if (ichatmutablecomponent != null) {
            return ichatmutablecomponent;
         }
      } catch (JsonParseException var3) {
         return IChatBaseComponent.h();
      } catch (Exception var4) {
      }

      return CommonComponents.a;
   }

   public IChatBaseComponent a(int i, boolean flag) {
      return this.c(flag)[i];
   }

   public void a(int i, IChatBaseComponent ichatbasecomponent) {
      this.a(i, ichatbasecomponent, ichatbasecomponent);
   }

   public void a(int i, IChatBaseComponent ichatbasecomponent, IChatBaseComponent ichatbasecomponent1) {
      this.f[i] = ichatbasecomponent;
      this.g[i] = ichatbasecomponent1;
      this.j = null;
   }

   public FormattedString[] a(boolean flag, Function<IChatBaseComponent, FormattedString> function) {
      if (this.j == null || this.k != flag) {
         this.k = flag;
         this.j = new FormattedString[4];

         for(int i = 0; i < 4; ++i) {
            this.j[i] = function.apply(this.a(i, flag));
         }
      }

      return this.j;
   }

   private IChatBaseComponent[] c(boolean flag) {
      return flag ? this.g : this.f;
   }

   public PacketPlayOutTileEntityData f() {
      return PacketPlayOutTileEntityData.a(this);
   }

   @Override
   public NBTTagCompound aq_() {
      return this.o();
   }

   @Override
   public boolean t() {
      return true;
   }

   public boolean g() {
      return this.h;
   }

   public void a(boolean flag) {
      this.h = flag;
      if (!flag) {
         this.i = null;
      }
   }

   public void a(UUID uuid) {
      this.i = uuid;
   }

   @Nullable
   public UUID i() {
      return this.i;
   }

   public boolean a(EntityHuman entityhuman) {
      for(IChatBaseComponent ichatbasecomponent : this.c(entityhuman.U())) {
         ChatModifier chatmodifier = ichatbasecomponent.a();
         ChatClickable chatclickable = chatmodifier.h();
         if (chatclickable != null && chatclickable.a() == ChatClickable.EnumClickAction.c) {
            return true;
         }
      }

      return false;
   }

   public boolean a(EntityPlayer entityplayer) {
      for(IChatBaseComponent ichatbasecomponent : this.c(entityplayer.U())) {
         ChatModifier chatmodifier = ichatbasecomponent.a();
         ChatClickable chatclickable = chatmodifier.h();
         if (chatclickable != null && chatclickable.a() == ChatClickable.EnumClickAction.c) {
            entityplayer.cH().aC().a(this.b(entityplayer), chatclickable.b());
         }
      }

      return true;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
   }

   @Override
   public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
      return (CommandSender)(wrapper.f() != null ? wrapper.f().getBukkitSender(wrapper) : new CraftBlockCommandSender(wrapper, this));
   }

   @Override
   public boolean d_() {
      return false;
   }

   @Override
   public boolean j_() {
      return false;
   }

   @Override
   public boolean M_() {
      return false;
   }

   public CommandListenerWrapper b(@Nullable EntityPlayer entityplayer) {
      String s = entityplayer == null ? "Sign" : entityplayer.Z().getString();
      Object object = entityplayer == null ? IChatBaseComponent.b("Sign") : entityplayer.G_();
      return new CommandListenerWrapper(this, Vec3D.b(this.p), Vec2F.a, (WorldServer)this.o, 2, s, (IChatBaseComponent)object, this.o.n(), entityplayer);
   }

   public EnumColor j() {
      return this.l;
   }

   public boolean a(EnumColor enumcolor) {
      if (enumcolor != this.j()) {
         this.l = enumcolor;
         this.w();
         return true;
      } else {
         return false;
      }
   }

   public boolean v() {
      return this.m;
   }

   public boolean b(boolean flag) {
      if (this.m != flag) {
         this.m = flag;
         this.w();
         return true;
      } else {
         return false;
      }
   }

   private void w() {
      this.e();
      if (this.o != null) {
         this.o.a(this.p(), this.q(), this.q(), 3);
      }
   }
}
