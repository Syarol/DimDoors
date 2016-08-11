package com.zixiken.dimdoors.commands;

import com.zixiken.dimdoors.helpers.DungeonHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CommandCreatePocket extends DDCommandBase {
	private static CommandCreatePocket instance = null;
	
	private CommandCreatePocket() {
		super("dd-create", "");
	}
	
	public static CommandCreatePocket instance() {
		if (instance == null)
			instance = new CommandCreatePocket();
		
		return instance;
	}

	@Override
	protected DDCommandResult processCommand(EntityPlayer sender, String[] command) {
		if (command.length > 0) {
			return DDCommandResult.TOO_MANY_ARGUMENTS;
		}
		
		//Place a door leading to a pocket dimension where the player is standing.
		//The pocket dimension will serve as a room for the player to build a dungeon.
		BlockPos pos = new BlockPos((int) sender.posX, (int) sender.posY, (int) sender.posZ);

		DungeonHelper.instance().createCustomDungeonDoor(sender.worldObj, pos);
		
		//Notify the player
		sendChat(sender, "Created a door to a pocket dimension. Please build your dungeon there.");
		
		return DDCommandResult.SUCCESS;
	}
}