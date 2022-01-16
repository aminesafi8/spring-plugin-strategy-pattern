package tn.aminesafi.springplugin.writers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tn.aminesafi.springplugin.strategy.WriterPlugin;

import static tn.aminesafi.springplugin.constants.Constants.TXT;

@Slf4j
@Component
public class TxtWriter implements WriterPlugin {

    @Override
    public void write(String message) {
        log.info("writing: TXT: [{}]", message);
    }

    @Override
    public boolean supports(String s) {
        return TXT.equalsIgnoreCase(s);
    }
}