package org.bukkit.craftbukkit.v1_19_R3.util;

import com.mojang.logging.LogQueues;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import jline.console.ConsoleReader;
import org.bukkit.craftbukkit.Main;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Erase;

public class TerminalConsoleWriterThread extends Thread {
   private final ConsoleReader reader;
   private final OutputStream output;

   public TerminalConsoleWriterThread(OutputStream output, ConsoleReader reader) {
      super("TerminalConsoleWriter");
      this.output = output;
      this.reader = reader;
      this.setDaemon(true);
   }

   @Override
   public void run() {
      while(true) {
         String message = LogQueues.getNextLogEvent("TerminalConsole");
         if (message != null) {
            try {
               if (Main.useJline) {
                  this.reader.print(Ansi.ansi().eraseLine(Erase.ALL).toString() + 13);
                  this.reader.flush();
                  this.output.write(message.getBytes());
                  this.output.flush();

                  try {
                     this.reader.drawLine();
                  } catch (Throwable var3) {
                     this.reader.getCursorBuffer().clear();
                  }

                  this.reader.flush();
               } else {
                  this.output.write(message.getBytes());
                  this.output.flush();
               }
            } catch (IOException var4) {
               Logger.getLogger(TerminalConsoleWriterThread.class.getName()).log(Level.SEVERE, null, var4);
            }
         }
      }
   }
}
