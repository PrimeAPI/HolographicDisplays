/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package me.filoghost.holographicdisplays.api.hologram.line;

import org.jetbrains.annotations.Nullable;

/**
 * @since 1
 */
public interface TextHologramLine extends ClickableHologramLine {

    /**
     * Returns the currently displayed text.
     *
     * @return the currently displayed text.
     * @since 1
     */
    @Nullable String getText();

    /**
     * Sets the displayed text.
     *
     * @param text the new displayed text.
     * @since 1
     */
    void setText(@Nullable String text);

    /**
     * Returns if the displaytext is transparent.
     *
     * @return true if the displaytext is transparent.
     */
    boolean isSneaking();

    /**
     * Sets if the displaytext is transparent.
     *
     * @param sneaking the new sneaking state.
     */
    void setSneaking(boolean sneaking);

}
