package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.world.level.block.EnumBlockMirror;

public class TemplateMirrorArgument extends StringRepresentableArgument<EnumBlockMirror> {
   private TemplateMirrorArgument() {
      super(EnumBlockMirror.d, EnumBlockMirror::values);
   }

   public static StringRepresentableArgument<EnumBlockMirror> a() {
      return new TemplateMirrorArgument();
   }

   public static EnumBlockMirror a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (EnumBlockMirror)var0.getArgument(var1, EnumBlockMirror.class);
   }
}
