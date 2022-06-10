package me.filoghost.holographicdisplays.plugin.commands.subs;

import me.filoghost.fcommons.Strings;
import me.filoghost.fcommons.command.sub.SubCommandContext;
import me.filoghost.fcommons.command.validation.CommandException;
import me.filoghost.fcommons.command.validation.CommandValidate;
import me.filoghost.holographicdisplays.plugin.commands.HologramCommandManager;
import me.filoghost.holographicdisplays.plugin.commands.HologramSubCommand;
import me.filoghost.holographicdisplays.plugin.commands.InternalHologramEditor;
import me.filoghost.holographicdisplays.plugin.event.InternalHologramChangeEvent;
import me.filoghost.holographicdisplays.plugin.format.ColorScheme;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologram;
import me.filoghost.holographicdisplays.plugin.internal.hologram.InternalHologramLine;
import me.filoghost.holographicdisplays.plugin.internal.hologram.TextInternalHologramLine;
import org.bukkit.command.CommandSender;

public class SneakCommand extends HologramSubCommand {

    private final HologramCommandManager commandManager;
    private final InternalHologramEditor hologramEditor;

    public SneakCommand(HologramCommandManager commandManager, InternalHologramEditor hologramEditor) {
        super("setsneak");
        setMinArgs(3);
        setUsageArgs("<hologram> <lineNumber> <newText>");
        setDescription("Let a line be semi-transparent.");
        this.commandManager = commandManager;
        this.hologramEditor = hologramEditor;
    }

    @Override
    public void execute(CommandSender sender, String[] args, SubCommandContext context) throws CommandException {
        InternalHologram hologram = hologramEditor.getExistingHologram(args[0]);
        boolean newSneakState = Boolean.parseBoolean(args[2]);

        int lineNumber = CommandValidate.parseInteger(args[1]);
        int linesAmount = hologram.getLines().size();

        CommandValidate.check(lineNumber >= 1 && lineNumber <= linesAmount,
                "The line number must be between 1 and " + linesAmount + ".");
        int index = lineNumber - 1;
        InternalHologramLine internalHologramLine = hologram.getLines().get(index);
        CommandValidate.check(internalHologramLine instanceof TextInternalHologramLine, "The line must be a text line.");

        ((TextInternalHologramLine) internalHologramLine).setSneaking(newSneakState);

        hologram.setLine(index, internalHologramLine);
        hologramEditor.saveChanges(hologram, InternalHologramChangeEvent.ChangeType.EDIT_LINES);

        sender.sendMessage(ColorScheme.PRIMARY + "Line " + lineNumber + " changed.");
        commandManager.sendQuickEditCommands(context, hologram);
    }
}
