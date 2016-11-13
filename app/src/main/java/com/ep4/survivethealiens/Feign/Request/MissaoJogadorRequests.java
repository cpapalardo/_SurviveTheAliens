package com.ep4.survivethealiens.Feign.Request;

import com.ep4.survivethealiens.Model.Credenciais;
import com.ep4.survivethealiens.Model.MissaoJogador;

import java.util.List;

import feign.Headers;
import feign.RequestLine;

/**
 * Created by Carla on 13/11/2016.
 */

public interface MissaoJogadorRequests {
    @RequestLine("POST MissaoJogadors/")
    @Headers("Content-Type: application/json")
    List<MissaoJogador> obterMissoesPorJogador(int idJogador);
}
