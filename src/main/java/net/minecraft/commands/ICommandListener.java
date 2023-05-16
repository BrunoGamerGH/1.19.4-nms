package net.minecraft.commands;

import net.minecraft.network.chat.IChatBaseComponent;
import org.bukkit.command.CommandSender;

public interface ICommandListener {
   ICommandListener a = new ICommandListener() {
      @Override
      public void a(IChatBaseComponent ichatbasecomponent) {
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

      @Override
      public CommandSender getBukkitSender(CommandListenerWrapper wrapper) {
         throw new UnsupportedOperationException("Not supported yet.");
      }
   };

   void a(IChatBaseComponent var1);

   boolean d_();

   boolean j_();

   boolean M_();

   default boolean e_() {
      return false;
   }

   CommandSender getBukkitSender(CommandListenerWrapper var1);
}
