package br.com.engine.core;

import br.com.engine.configuration.LoaderConfiguration;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Program implements Executable {
    private Window window;

    public Program initialize () {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        LoaderConfiguration.loadConfig();
        return this;
    }

    @Override
    public void execute() {
        new Thread(() -> {
            GL.createCapabilities();
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            window = Component.getInstance(Window.class);

            while (!window.isClosed()) {
                update();
                render();
                clear();
                glfwPollEvents();
            }
        }).run();

        finalizeProgram();
    }

    public void render() {
        System.out.println("render");
    }

    public  void update () {
        System.out.println("update");
    }

    public  void clear () {
        System.out.println("clear");
        window.clear();
    }

    public void finalizeProgram () {
        window.destroy();
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}