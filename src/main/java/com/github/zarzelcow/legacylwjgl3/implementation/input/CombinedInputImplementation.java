package com.github.zarzelcow.legacylwjgl3.implementation.input;

import org.lwjgl.LWJGLException;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author Zarzelcow
 * @created 28/09/2022 - 3:23 PM
 */
public class CombinedInputImplementation implements InputImplementation {
    private KeyboardImplementation keyboardImpl;
    private MouseImplementation mouseImpl;

    public CombinedInputImplementation(KeyboardImplementation keyboard, MouseImplementation mouse) {
        this.keyboardImpl = keyboard;
        this.mouseImpl = mouse;
    }

    // ~~~~~ KEYBOARD ~~~~~
    @Override
    public void createKeyboard() {
        keyboardImpl.createKeyboard();
    }

    @Override
    public void destroyKeyboard() {
        keyboardImpl.destroyKeyboard();
    }

    @Override
    public void pollKeyboard(ByteBuffer keyDownBuffer) {
        keyboardImpl.pollKeyboard(keyDownBuffer);
    }

    @Override
    public void readKeyboard(ByteBuffer readBuffer) {
        keyboardImpl.readKeyboard(readBuffer);
    }

    // ~~~~~ MOUSE ~~~~~

    @Override
    public void createMouse() {
        mouseImpl.createMouse();
    }

    @Override
    public void destroyMouse() {
        mouseImpl.destroyMouse();
    }

    @Override
    public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons_buffer) {
        mouseImpl.pollMouse(coord_buffer, buttons_buffer);
    }

    @Override
    public void readMouse(ByteBuffer readBuffer) {
        mouseImpl.readMouse(readBuffer);
    }

    @Override
    public void setCursorPosition(int x, int y) {
        mouseImpl.setCursorPosition(x, y);
    }

    @Override
    public void grabMouse(boolean grab) {
        mouseImpl.grabMouse(grab);
    }

    @Override
    public boolean hasWheel() {
        return mouseImpl.hasWheel();
    }

    @Override
    public int getButtonCount() {
        return mouseImpl.getButtonCount();
    }

    @Override
    public boolean isInsideWindow() {
        return mouseImpl.isInsideWindow();
    }

    @Override
    public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
        return mouseImpl.createCursor(width, height, xHotspot, yHotspot, numImages, images, delays);
    }

    @Override
    public void destroyCursor(Object cursor_handle) {
        mouseImpl.destroyCursor(cursor_handle);
    }

    @Override
    public int getNativeCursorCapabilities() {
        return mouseImpl.getNativeCursorCapabilities();
    }

    @Override
    public void setNativeCursor(Object handle) throws LWJGLException {
        mouseImpl.setNativeCursor(handle);
    }

    @Override
    public int getMinCursorSize() {
        return mouseImpl.getMinCursorSize();
    }

    @Override
    public int getMaxCursorSize() {
        return mouseImpl.getMaxCursorSize();
    }
}
