package net.minecraft.network.chat;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.server.level.EntityPlayer;

@FunctionalInterface
public interface ChatDecorator {
   ChatDecorator a = (var0, var1) -> CompletableFuture.completedFuture(var1);

   CompletableFuture<IChatBaseComponent> decorate(@Nullable EntityPlayer var1, IChatBaseComponent var2);
}
