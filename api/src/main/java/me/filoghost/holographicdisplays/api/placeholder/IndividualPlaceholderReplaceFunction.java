/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.api.placeholder;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Simplified version of {@link IndividualPlaceholder} where the refresh interval in ticks is passed through the
 * registration method
 * {@link HolographicDisplaysAPI#registerIndividualPlaceholder(String, int, IndividualPlaceholderReplaceFunction)} as a
 * constant.
 *
 * @since 1
 */
@FunctionalInterface
public interface IndividualPlaceholderReplaceFunction {

    /**
     * Same as {@link IndividualPlaceholder#getReplacement(Player, String)}.
     *
     * @param player the player that will see the provided replacement
     * @param argument the optional placeholder argument, null if not specified
     * @return the optional placeholder replacement, null to leave the placeholder unreplaced
     * @since 1
     */
    @Nullable String getReplacement(@NotNull Player player, @Nullable String argument);

}
