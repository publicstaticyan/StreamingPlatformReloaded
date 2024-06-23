package streamingplatform.business.interfaces;

import streamingplatform.business.entidades.Cliente;
import streamingplatform.business.entidades.Midia;
import streamingplatform.business.exceptions.MidiaNaoEncontradaException;

public interface IClienteProfissional {
    void assistirMidia(Midia midia, Cliente watcher) throws MidiaNaoEncontradaException;
}
