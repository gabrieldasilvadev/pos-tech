package br.com.postech.soat.product.adapters.out;

import br.com.postech.soat.product.core.ports.out.LoggerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAdapter implements LoggerPort {
    private final Logger logger;

    public LoggerAdapter(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }
}
