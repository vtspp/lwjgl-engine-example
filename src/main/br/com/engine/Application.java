package br.com.engine;

import br.com.engine.core.Program;

public class Application {
    public static void main(String[] args) {
        new Program()
                .initialize()
                .execute();
    }
}