package net.minecraft.commands.arguments;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.commands.CommandListenerWrapper;
import net.minecraft.util.INamable;
import net.minecraft.world.level.levelgen.HeightMap;

public class HeightmapTypeArgument extends StringRepresentableArgument<HeightMap.Type> {
   private static final Codec<HeightMap.Type> a = INamable.a(HeightmapTypeArgument::b, var0 -> var0.toLowerCase(Locale.ROOT));

   private static HeightMap.Type[] b() {
      return Arrays.stream(HeightMap.Type.values()).filter(HeightMap.Type::d).toArray(var0 -> new HeightMap.Type[var0]);
   }

   private HeightmapTypeArgument() {
      super(a, HeightmapTypeArgument::b);
   }

   public static HeightmapTypeArgument a() {
      return new HeightmapTypeArgument();
   }

   public static HeightMap.Type a(CommandContext<CommandListenerWrapper> var0, String var1) {
      return (HeightMap.Type)var0.getArgument(var1, HeightMap.Type.class);
   }

   @Override
   protected String a(String var0) {
      return var0.toLowerCase(Locale.ROOT);
   }
}
