package net.minecraft.server.rcon;

import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.ICommandListener;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.phys.Vec2F;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.command.CommandSender;

public class RemoteControlCommandListener implements ICommandListener {
   private static final String b = "Rcon";
   private static final IChatBaseComponent c = IChatBaseComponent.b("Rcon");
   private final StringBuffer d = new StringBuffer();
   private final MinecraftServer e;

   public RemoteControlCommandListener(MinecraftServer minecraftserver) {
      this.e = minecraftserver;
   }

   public void e() {
      this.d.setLength(0);
   }

   public String f() {
      return this.d.toString();
   }

   public CommandListenerWrapper g() {
      WorldServer worldserver = this.e.D();
      return new CommandListenerWrapper(this, Vec3D.a(worldserver.Q()), Vec2F.a, worldserver, 4, "Rcon", c, this.e, null);
   }

   public void sendMessage(String message) {
      this.d.append(message);
   }

   @Override
   public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
      return this.e.remoteConsole;
   }

   @Override
   public void a(IChatBaseComponent ichatbasecomponent) {
      this.d.append(ichatbasecomponent.getString());
   }

   @Override
   public boolean d_() {
      return true;
   }

   @Override
   public boolean j_() {
      return true;
   }

   @Override
   public boolean M_() {
      return this.e.k();
   }
}
