package tn.aminesafi.springplugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.plugin.core.Plugin;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Component;

@Slf4j
@SpringBootApplication
@EnablePluginRegistries(WriterPlugin.class)
public class SpringPluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPluginApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(PluginRegistry<WriterPlugin, String> plugins) {
        return args -> {
            for (var format : "csv;txt;mp4".split(";")) {
                var plugin = plugins.getPluginFor(format);
                plugin.ifPresentOrElse(impl -> impl.write("Hello, Spring plugin!"),
                        () -> log.warn("No implementation was found for [{}]!", format));
            }
        };
    }

}

@Slf4j
@Component
class CsvWriter implements WriterPlugin {

    @Override
    public void write(String message) {
        log.info("writing: CSV: " + message);
    }

    @Override
    public boolean supports(String s) {
        return "csv".equalsIgnoreCase(s);
    }
}


@Slf4j
@Component
class TxtWriter implements WriterPlugin {

    @Override
    public void write(String message) {
        log.info("writing: TXT: " + message);
    }

    @Override
    public boolean supports(String s) {
        return "txt".equalsIgnoreCase(s);
    }
}


interface WriterPlugin extends Plugin<String> {
    void write(String message);
}
