package br.com.postech.soat.commons.application.mediator;

import br.com.postech.soat.commons.application.command.Command;
import br.com.postech.soat.commons.application.command.CommandHandler;
import br.com.postech.soat.commons.application.command.UnitCommandHandler;
import br.com.postech.soat.commons.application.query.Query;
import br.com.postech.soat.commons.application.query.QueryHandler;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings({"unchecked", "rawtypes"})
public class MediatorImpl implements Mediator {

    private final Map<Class<? extends Command>, CommandHandler<?, ?>> handlers = new HashMap<>();
    private final Map<Class<? extends Query>, QueryHandler<?, ?>> queryHandlers = new HashMap<>();
    private final Map<Class<? extends Command>, UnitCommandHandler<?>> unitCommandHandlers = new HashMap<>();

    public MediatorImpl(ApplicationContext context) {
        Map<String, CommandHandler> beans = context.getBeansOfType(CommandHandler.class);
        for (CommandHandler<?, ?> handler : beans.values()) {
            Class<?> commandClass = resolveCommandType(handler);
            handlers.put((Class<? extends Command>) commandClass, handler);
        }

        context.getBeansOfType(QueryHandler.class).values()
            .forEach(queryHandler ->
                queryHandlers.put((Class<? extends Query>) queryHandler.getClass(), queryHandler));

        context.getBeansOfType(UnitCommandHandler.class)
            .values()
            .forEach(handler ->
                unitCommandHandlers.put((Class<? extends Command>) resolveCommandType(handler), handler));

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
        UnitCommandHandler<Command> unitCommandHandler = (UnitCommandHandler<Command>) unitCommandHandlers.get(command.getClass());
        if (unitCommandHandler == null) {
            throw new IllegalStateException("No unitCommandHandler found for command: " + command.getClass().getName());
        }
        unitCommandHandler.handle(command);
    }

    @Override
    public <Q extends Query, R> R send(Q query) {
        QueryHandler<Q, R> handler = (QueryHandler<Q, R>) queryHandlers.get(query.getClass());

        if (handler == null) {
            throw new IllegalStateException("No handler found for query: " + query.getClass().getName());
        }

        return handler.handle(query);
    }

    private Class<?> resolveCommandType(CommandHandler<?, ?> handler) {
        ResolvableType type = ResolvableType.forClass(CommandHandler.class, handler.getClass());
        return type.getGeneric(0).resolve();
    }

    private Class<?> resolveCommandType(UnitCommandHandler<?> handler) {
        ResolvableType type = ResolvableType.forClass(UnitCommandHandler.class, handler.getClass());
        return type.getGeneric(0).resolve();
    }
}
