package com.github.zarzelcow.legacylwjgl3.implementation.input;

import org.lwjgl.LWJGLException;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author Zarzelcow
 * @created 28/09/2022 - 8:58 PM
 */
public interface MouseImplementation {
    void createMouse();

    void destroyMouse();

    void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons_buffer);

    void readMouse(ByteBuffer readBuffer);

    void setCursorPosition(int x, int y);

    void grabMouse(boolean grab);

    boolean hasWheel();

    int getButtonCount();

    boolean isInsideWindow();

    /** Native cursor handles */
    Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException;

    void destroyCursor(Object cursor_handle);

    /**
     * Function to determine native cursor support
     */
    int getNativeCursorCapabilities();

    /** Method to set the native cursor */
    void setNativeCursor(Object handle) throws LWJGLException;

    /** Method returning the minimum cursor size */
    int getMinCursorSize();

    /** Method returning the maximum cursor size */
    int getMaxCursorSize();
}
