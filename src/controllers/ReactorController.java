/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Reactor;

/**
 * Es el encargado de manejar todo lo que tiene que ver con el reactor
 *
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class ReactorController {

    private Reactor[] reactores;

    public ReactorController(Reactor[] reactores) {
        this.reactores = reactores;
    }

    public String recibirComando(String mensaje) {
        String[] keys = mensaje.split(",");

        String[] nameKey = keys[0].split(":");
        String[] actionKey = keys[1].split(":");
        String[] messageKey = keys[2].split(":");
        String[] valueKey = keys[3].split(":");

        switch (actionKey[1]) {
            case "switch":
                return switchReactor(valueKey[1], getReactor(Integer.parseInt(messageKey[1])));
            case "update":
                return updateCargaReactor(Integer.parseInt(valueKey[1]), getReactor(Integer.parseInt(messageKey[1])));
            case "repair":
                return repairReactor(getReactor(Integer.parseInt(messageKey[1])));
        }

        return null;
    }

    public String switchReactor(String estado, Reactor reactor) {
        String mensaje = "";
        if (reactor.getEstado().equals("DAÑADO") || reactor.getEstado().equals(estado)) {
            mensaje += "code:500,action:switch,value:No se puede realizar esta accion,reactor:"+reactor.getIdentificador();
        } else if (!estado.equals(reactor.getEstado())) {
            reactor.setEstado(estado);
            mensaje += "code:200,action:switch,value:El reactor " + reactor.getIdentificador() + " ha cambiado su estado a " + estado + ",reactor:"+reactor.getIdentificador();
        }
        return mensaje;
    }

    public String updateCargaReactor(int carga, Reactor reactor) {
        String mensaje = "code:500,action:update,value:No se puede realizar esta accion";
        if (carga < 0 || reactor.getEstado().equals("APAGADO") || reactor.getEstado().equals("DAÑADO")) {
            return mensaje;
        }

        reactor.setCarga(carga);
        if (reactor.getCarga() >= 100) {
            reactor.setEstado("DAÑADO");
            mensaje = "code:200,action:update,value:El reactor se ha dañado";
        } else {
            mensaje = "code:200,action:update,value:La carga del reactor ha sido actualizada";
        }

        return mensaje;
    }

    public String repairReactor(Reactor reactor) {
        String mensaje = "code:500,action:repair,value:No se puede realizar esta accion";
        if (reactor.getEstado().equals("DAÑADO")) {
            reactor.setEstado("APAGADO");
            reactor.setCarga(0);
            mensaje = "code:200,action:repair,value:El reactor ha sido reparado";
        }
        return mensaje;
    }

    public Reactor getReactor(int identificador) {
        for (Reactor reactor : reactores) {
            if (reactor.getIdentificador() == identificador) {
                return reactor;
            }
        }
        return null;
    }
}
