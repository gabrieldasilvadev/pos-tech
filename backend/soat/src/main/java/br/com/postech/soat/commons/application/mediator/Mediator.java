package br.com.postech.soat.commons.application.mediator;

import br.com.postech.soat.commons.application.command.Command;

public interface Mediator {

    <C extends Command, R> R send(C command);

    void sendUnit(Command command);
}
