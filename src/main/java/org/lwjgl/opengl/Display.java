package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import net.minecraft.client.render.window.GameWindowGLFW;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.system.MemoryUtil;

@SuppressWarnings("unused")
public final class Display {
    @NotNull
    public static final Display INSTANCE = new Display();
    @NotNull
    private static String title = "";
    @NotNull
    private static DisplayMode displayMode = new DisplayMode(640, 480, 24, 60);
    private static long handleKt = -1L;
    private static boolean resizable;
    private static int widthKt;
    private static int heightKt;
    private static int xPos;
    private static int yPos;
    @Nullable
    private static GameWindowGLFW window;
    private static boolean window_resized;
    @Nullable
    private static GLFWWindowSizeCallback sizeCallback;
    private static ByteBuffer @Nullable [] cached_icons = null;

    private Display() {
    }

    @NotNull
    @SuppressWarnings("unused")
    public static String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public static void setTitle(@NotNull String value) {
        Objects.requireNonNull(value);
        title = value;
        if (isCreatedKt()) {
            GLFW.glfwSetWindowTitle(handleKt, value);
        }
    }

    @NotNull
    @SuppressWarnings("unused")
    public static DisplayMode getDisplayMode() {
        return displayMode;
    }

    @SuppressWarnings("unused")
    public static void setDisplayMode(@NotNull DisplayMode displayMode) {
        Objects.requireNonNull(displayMode);
        Display.displayMode = displayMode;
    }

    @SuppressWarnings("unused")
    public static long getHandleKt() {
        return handleKt;
    }

    @SuppressWarnings("unused")
    public static void setHandleKt(long handle) {
        handleKt = handle;
    }

    @SuppressWarnings("unused")
    public static int getWidthKt() {
        return widthKt;
    }

    @SuppressWarnings("unused")
    public static void setWidthKt(int width) {
        widthKt = width;
    }

    @SuppressWarnings("unused")
    public static int getHeightKt() {
        return heightKt;
    }

    @SuppressWarnings("unused")
    public static void setHeightKt(int height) {
        heightKt = height;
    }

    @SuppressWarnings("unused")
    public static int getXPos() {
        return xPos;
    }

    @SuppressWarnings("unused")
    public static void setXPos(int xPos) {
        Display.xPos = xPos;
    }

    @SuppressWarnings("unused")
    public static int getYPos() {
        return yPos;
    }

    @SuppressWarnings("unused")
    public static void setYPos(int yPos) {
        Display.yPos = yPos;
    }

    @Nullable
    @SuppressWarnings("unused")
    public static GameWindowGLFW getWindow() {
        return window;
    }

    @SuppressWarnings("unused")
    public static void setWindow(@Nullable GameWindowGLFW value) {
        window = value;
    }

    @Nullable
    @SuppressWarnings("unused")
    public static DisplayMode getDesktopDisplayMode() {
        DisplayMode[] $this$maxByOrNull$iv = getAvailableDisplayModes();
        DisplayMode var12;
        if ($this$maxByOrNull$iv.length == 0) {
            var12 = null;
        } else {
            DisplayMode maxElem$iv = $this$maxByOrNull$iv[0];
            int lastIndex$iv = ($this$maxByOrNull$iv.length - 1);
            if (lastIndex$iv != 0) {
                DisplayMode it = maxElem$iv;
                int maxValue$iv = it.getWidth() * it.getHeight();
                int i$iv = 1;
                if (i$iv <= lastIndex$iv) {
                    while (true) {
                        DisplayMode e$iv = $this$maxByOrNull$iv[i$iv];
                        int v$iv = it.getWidth() * it.getHeight();
                        if (maxValue$iv < v$iv) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }

                        if (i$iv == lastIndex$iv) {
                            break;
                        }

                        ++i$iv;
                    }
                }

            }
            var12 = maxElem$iv;
        }

        return var12;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public static int setIcon(@NotNull ByteBuffer[] icons) {
        Objects.requireNonNull(icons);
        if (!Arrays.equals(cached_icons, icons)) {
            Collection<ByteBuffer> destination$iv$iv = new ArrayList<>(icons.length);
            int var6 = 0;

            for(int var7 = icons.length; var6 < var7; ++var6) {
                ByteBuffer it = icons[var6];
                ByteBuffer var12 = INSTANCE.cloneByteBuffer(it);
                destination$iv$iv.add(var12);
            }

            cached_icons = destination$iv$iv.toArray(new ByteBuffer[0]);
        }

        if (isCreatedKt()) {
            GLFW.glfwSetWindowIcon(handleKt, INSTANCE.iconsToGLFWBuffer(Objects.requireNonNull(cached_icons)));
            return 1;
        } else {
            return 0;
        }
    }

    private ByteBuffer cloneByteBuffer(ByteBuffer original) {
        ByteBuffer clone = BufferUtils.createByteBuffer(original.capacity());
        int old_position = original.position();
        clone.put(original);
        original.position(old_position);
        clone.flip();
        return clone;
    }

    private GLFWImage.Buffer iconsToGLFWBuffer(ByteBuffer[] icons) {
        GLFWImage.Buffer buffer = GLFWImage.create(icons.length);

        for (ByteBuffer icon : icons) {
            int size = icon.limit() / 4;
            int dimension = (int)Math.sqrt(size);
            try (GLFWImage image = GLFWImage.malloc()) {
                buffer.put(image.set(dimension, dimension, icon));
            }
        }

        buffer.flip();
        return buffer;
    }

    @SuppressWarnings("unused")
    public static void update() {
        window_resized = false;
        GLFW.glfwPollEvents();
        if (Mouse.isCreated()) {
            Mouse.poll();
//            Mouse.updateCursor();
        }

        if (Keyboard.isCreated()) {
            Keyboard.poll();
        }

        GLFW.glfwSwapBuffers(handleKt);
    }

    @SuppressWarnings("unused")
    public static void create(@Nullable PixelFormat pixelFormat) throws LWJGLException {
        GLFWErrorCallback.createPrint(System.err).set();
        boolean var1 = GLFW.glfwInit();
        if (!var1) {
            String var3 = "Unable to initialize GLFW";
            throw new IllegalStateException(var3);
        }
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        handleKt = GLFW.glfwCreateWindow(displayMode.getWidth(), displayMode.getHeight(), title,MemoryUtil.NULL, MemoryUtil.NULL);
        widthKt = displayMode.getWidth();
        heightKt = displayMode.getHeight();
        GLFW.glfwMakeContextCurrent(handleKt);
        GL.createCapabilities();
        sizeCallback = GLFWWindowSizeCallback.create(INSTANCE::resizeCallback);
        GLFW.glfwSetWindowSizeCallback(handleKt, sizeCallback);
        Mouse.create();
        Keyboard.create();
        GLFW.glfwShowWindow(handleKt);
        if (cached_icons != null) {
            setIcon(cached_icons);
        }
    }

    @SuppressWarnings("unused")
    public static void setFullscreen(boolean fullscreen) {
        System.out.println("setFullscreen: " + fullscreen);

        try {
            INSTANCE.resizeCallback(handleKt, displayMode.getWidth(), displayMode.getHeight());
            if (fullscreen) {
                long monitor = GLFW.glfwGetPrimaryMonitor();
                GLFW.glfwSetWindowMonitor(handleKt, monitor, 0, 0, widthKt, heightKt, displayMode.getFrequency());
                xPos = displayMode.getWidth() / 2;
                yPos = displayMode.getHeight() / 2;
            } else {
                xPos -= widthKt / 2;
                yPos -= heightKt / 2;
                GLFW.glfwSetWindowMonitor(handleKt, 0L, xPos, yPos, widthKt, heightKt, -1);
            }

            GLFW.glfwSetWindowSize(handleKt, widthKt, heightKt);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @NotNull
    public static DisplayMode[] getAvailableDisplayModes() {
        long primaryMonitor = GLFW.glfwGetPrimaryMonitor();
        if (primaryMonitor == MemoryUtil.NULL) {
            return new DisplayMode[0];
        } else {
            GLFWVidMode.Buffer modes = GLFW.glfwGetVideoModes(primaryMonitor);
            if (modes == null) {
                throw new IllegalStateException("No video modes found");
            } else {
                List<DisplayMode> displayModes = new ArrayList<>();

                for (GLFWVidMode mode : modes) {
                    displayModes.add(new DisplayMode(mode.width(), mode.height(), mode.redBits() + mode.blueBits() + mode.greenBits(), mode.refreshRate()));
                }

                return new HashSet<>(displayModes).toArray(new DisplayMode[0]);
            }
        }
    }

    private void resizeCallback(long window, int width, int height) {
        if (window == handleKt) {
            window_resized = true;
            widthKt = width;
            heightKt = height;
        }

    }

    @SuppressWarnings("unused")
    public void destroyWindow() {
        // free callbacks
        Objects.requireNonNull(sizeCallback).free();
        Mouse.destroy();
        Keyboard.destroy();
        // Destroy the window
        GLFW.glfwDestroyWindow(handleKt);
    }

    @SuppressWarnings("unused")
    public static long getHandle() {
        return Objects.requireNonNull(getWindow()).window;
    }

    @SuppressWarnings("unused")
    public static int getWidth() {
        return Objects.requireNonNull(getWindow()).getWidthScreenCoords();
    }

    @SuppressWarnings("unused")
    public static int getHeight() {
        return Objects.requireNonNull(getWindow()).getHeightScreenCoords();
    }

    @SuppressWarnings("unused")
    public static boolean isCreated() {
        return Objects.requireNonNull(getWindow()).window != -1L;
    }

    @SuppressWarnings("unused")
    public static void destroy() {
        INSTANCE.destroyWindow();
        GLFW.glfwTerminate();
        GLFWErrorCallback callback = GLFW.glfwSetErrorCallback(null);
        if (callback != null) {
            callback.close();
        }
    }

    @SuppressWarnings("unused")
    public static boolean isCreatedKt() {
        return handleKt != -1L;
    }

    @SuppressWarnings("unused")
    public static boolean isCloseRequested() {
        return GLFW.glfwWindowShouldClose(handleKt);
    }

    @SuppressWarnings("unused")
    public static boolean isActive() {
        return true;
    }

    @SuppressWarnings("unused")
    public static void setResizable(boolean isResizable) {
        resizable = isResizable;
        if (isCreatedKt()) {
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        }
    }

    @SuppressWarnings("unused")
    public static void sync(int fps) {
        Sync.sync(fps);
    }

    @SuppressWarnings("unused")
    public static void setVSyncEnabled(boolean enabled) {
        GLFW.glfwSwapInterval(enabled ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
    }

    @SuppressWarnings("unused")
    public static boolean wasResized() {
        return window_resized;
    }
}
