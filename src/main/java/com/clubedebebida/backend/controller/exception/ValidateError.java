package com.clubedebebida.backend.controller.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidateError extends StandardError {
    public List<ValidateMessage> menssagens = new ArrayList<ValidateMessage>();

    public List<ValidateMessage> getMenssagens() {
        return menssagens;
    }

    public void addMensagens(String campo, String mensagem){
        menssagens.add(new ValidateMessage(campo, mensagem));
    }
}
