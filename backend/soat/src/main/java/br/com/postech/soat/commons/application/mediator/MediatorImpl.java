package br.com.postech.soat.commons.application.mediator;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.application.command.UnitCommandHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unchecked")
public class MediatorImpl implements Mediator {

    private final Map<Class<? extends Command>, CommandHandler<?, ?>> handlers = new HashMap<>();

    public MediatorImpl(ApplicationContext context) {
        Map<String, CommandHandler> beans = context.getBeansOfType(CommandHandler.class);
        for (CommandHandler<?, ?> handler : beans.values()) {
            Class<?> commandClass = resolveCommandType(handler);
            handlers.put((Class<? extends Command>) commandClass, handler);
        }
    }

    @Override
    public <C extends Command, R> R send(C command) {
        CommandHandler<C, R> handler = (CommandHandler<C, R>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for command: " + command.getClass().getName());
        }
        return handler.handle(command);
    }

    @Override
    public void sendUnit(Command command) {
        UnitCommandHandler<Command> handler = (UnitCommandHandler<Command>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for command: " + command.getClass().getName());
        }
        handler.handle(command);
    }

    private Class<?> resolveCommandType(CommandHandler<?, ?> handler) {
        ResolvableType type = ResolvableType.forClass(CommandHandler.class, handler.getClass());
        return type.getGeneric(0).resolve();
    }
}
