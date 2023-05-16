package net.minecraft.network.chat;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.arguments.SignedArgument;

public record SignableCommand<S>(List<SignableCommand.a<S>> arguments) {
   private final List<SignableCommand.a<S>> a;

   public SignableCommand(List<SignableCommand.a<S>> var0) {
      this.a = var0;
   }

   public static <S> SignableCommand<S> a(ParseResults<S> var0) {
      String var1 = var0.getReader().getString();
      CommandContextBuilder<S> var2 = var0.getContext();
      CommandContextBuilder<S> var3 = var2;

      List<SignableCommand.a<S>> var4;
      CommandContextBuilder<S> var5;
      for(var4 = a(var1, var2); (var5 = var3.getChild()) != null; var3 = var5) {
         boolean var6 = var5.getRootNode() != var2.getRootNode();
         if (!var6) {
            break;
         }

         var4.addAll(a(var1, var5));
      }

      return new SignableCommand<>(var4);
   }

   private static <S> List<SignableCommand.a<S>> a(String var0, CommandContextBuilder<S> var1) {
      List<SignableCommand.a<S>> var2 = new ArrayList<>();

      for(ParsedCommandNode<S> var4 : var1.getNodes()) {
         CommandNode var6 = var4.getNode();
         if (var6 instanceof ArgumentCommandNode var5 && var5.getType() instanceof SignedArgument) {
            ParsedArgument<S, ?> var6x = (ParsedArgument)var1.getArguments().get(var5.getName());
            if (var6x != null) {
               String var7 = var6x.getRange().get(var0);
               var2.add(new SignableCommand.a<>(var5, var7));
            }
         }
      }

      return var2;
   }

   public static record a<S>(ArgumentCommandNode<S, ?> node, String value) {
      private final ArgumentCommandNode<S, ?> a;
      private final String b;

      public a(ArgumentCommandNode<S, ?> var0, String var1) {
         this.a = var0;
         this.b = var1;
      }

      public String a() {
         return this.a.getName();
      }

      public ArgumentCommandNode<S, ?> b() {
         return this.a;
      }

      public String c() {
         return this.b;
      }
   }
}
