package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.world.level.block.EnumBlockRotation;

public class TemplateRotationArgument extends StringRepresentableArgument<EnumBlockRotation> {
   private TemplateRotationArgument() {
      super(EnumBlockRotation.e, EnumBlockRotation::values);
   }

   public static TemplateRotationArgument a() {
      return new TemplateRotationArgument();
   }

   public static EnumBlockRotation a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (EnumBlockRotation)var0.getArgument(var1, EnumBlockRotation.class);
   }
}
