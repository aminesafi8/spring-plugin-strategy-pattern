package tn.aminesafi.springplugin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import tn.aminesafi.springplugin.strategy.WriterPlugin;

@Slf4j
@SpringBootApplication
@EnablePluginRegistries(WriterPlugin.class)
public class SpringPluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPluginApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(PluginRegistry<WriterPlugin, String> plugins) {
        return args -> {
            for (var format : "csv;txt;mp4".split(";")) {
                var plugin = plugins.getPluginFor(format);
                plugin.ifPresentOrElse(impl -> impl.write("Hello, Spring plugin!"),
                        () -> log.warn("No implementation was found for [{}]!", format));
            }
        };
    }

}





