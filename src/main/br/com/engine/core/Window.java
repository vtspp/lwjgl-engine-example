package br.com.engine.core;

import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Window extends Component implements Instantiable<Window> {
    private long memoryAddress;
    private int width;
    private int height;
    private String title;
    private boolean resizable;
    private boolean vsyncEnable;
    private boolean closed;

    @Override
    public void instance(Window window) {
        this.memoryAddress = glfwCreateWindow(window.width, window.height, window.title, NULL, NULL);
        glfwMakeContextCurrent(memoryAddress);

        if (resizable)
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        if (vsyncEnable)
            glfwSwapInterval(GLFW_TRUE);

        autoClosed();

        GLFWVidMode monitor = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(memoryAddress, (monitor.width() - this.width) / 2, (monitor.height() - this.height) / 2);

        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwShowWindow(memoryAddress);
        super.register(this);
    }

    public boolean isClosed () {
        return glfwWindowShouldClose(memoryAddress);
    }

    private void autoClosed () {
        glfwSetKeyCallback(memoryAddress, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(memoryAddress, true);
        });
    }

    @Override
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(memoryAddress);
    }

    public void destroy () {
        glfwFreeCallbacks(memoryAddress);
        glfwDestroyWindow(memoryAddress);
        super.destroy();
    }

    @Override
    public String toString() {
        return title;
    }
}