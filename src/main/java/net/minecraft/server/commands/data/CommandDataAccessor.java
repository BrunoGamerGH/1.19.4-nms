package net.minecraft.server.commands.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.ArgumentNBTKey;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.chat.IChatBaseComponent;

public interface CommandDataAccessor {
   void a(NBTTagCompound var1) throws CommandSyntaxException;

   NBTTagCompound a() throws CommandSyntaxException;

   IChatBaseComponent b();

   IChatBaseComponent a(NBTBase var1);

   IChatBaseComponent a(ArgumentNBTKey.g var1, double var2, int var4);
}
