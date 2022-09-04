package br.com.engine.configuration;

import br.com.engine.core.Window;
import br.com.engine.core.Instantiable;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoaderConfiguration {
    private static final String PATH = "src/main/resources";
    private static final Gson json = new Gson();
    private static Map<Class<?>, String> parameter = new HashMap<>(0);


    public static final void loadConfig () {
        LoaderConfiguration.loadResource();
        LoaderConfiguration.readParameter();
    }

    /**
     * Add new configuration
     */
    private static void loadResource () {
        parameter.put(Window.class, PATH + "/window.conf");
    }

    private static <T extends Instantiable> void readParameter () {
        parameter.forEach((key, value) -> {
            try (FileReader reader = new FileReader(value, StandardCharsets.UTF_8)) {
                T obj = (T) json.fromJson(reader, key);
                obj.instance(obj);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}