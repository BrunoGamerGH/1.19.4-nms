package net.minecraft.network.chat.contents;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.commands.arguments.ArgumentEntity;
import net.minecraft.commands.arguments.selector.ArgumentParserSelector;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.ScoreboardObjective;
import net.minecraft.world.scores.ScoreboardScore;

public class ScoreContents implements ComponentContents {
   private static final String b = "*";
   private final String c;
   @Nullable
   private final EntitySelector d;
   private final String e;

   @Nullable
   private static EntitySelector a(String var0) {
      try {
         return new ArgumentParserSelector(new StringReader(var0)).t();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public ScoreContents(String var0, String var1) {
      this.c = var0;
      this.d = a(var0);
      this.e = var1;
   }

   public String a() {
      return this.c;
   }

   @Nullable
   public EntitySelector b() {
      return this.d;
   }

   public String c() {
      return this.e;
   }

   private String a(CommandListenerWrapper var0) throws CommandSyntaxException {
      if (this.d != null) {
         List<? extends Entity> var1 = this.d.b(var0);
         if (!var1.isEmpty()) {
            if (var1.size() != 1) {
               throw ArgumentEntity.a.create();
            }

            return var1.get(0).cu();
         }
      }

      return this.c;
   }

   private String a(String var0, CommandListenerWrapper var1) {
      MinecraftServer var2 = var1.l();
      if (var2 != null) {
         Scoreboard var3 = var2.aF();
         ScoreboardObjective var4 = var3.d(this.e);
         if (var3.b(var0, var4)) {
            ScoreboardScore var5 = var3.c(var0, var4);
            return Integer.toString(var5.b());
         }
      }

      return "";
   }

   @Override
   public IChatMutableComponent a(@Nullable CommandListenerWrapper var0, @Nullable Entity var1, int var2) throws CommandSyntaxException {
      if (var0 == null) {
         return IChatBaseComponent.h();
      } else {
         String var3 = this.a(var0);
         String var4 = var1 != null && var3.equals("*") ? var1.cu() : var3;
         return IChatBaseComponent.b(this.a(var4, var0));
      }
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof ScoreContents var1 && this.c.equals(var1.c) && this.e.equals(var1.e)) {
            return true;
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      int var0 = this.c.hashCode();
      return 31 * var0 + this.e.hashCode();
   }

   @Override
   public String toString() {
      return "score{name='" + this.c + "', objective='" + this.e + "'}";
   }
}
