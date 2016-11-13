package com.ep4.survivethealiens.Feign.Request;

import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.Model.MissaoJogador;

import java.util.ArrayList;

import feign.Param;
import feign.RequestLine;

/**
 * Created by Carla on 29/10/2016.
 */

public interface MissaoRequests {
    @RequestLine("GET MissaoJogadors/{id}/")
    ArrayList<MissaoJogador> getMissoesById(@Param("id") Integer id);

    @RequestLine("GET MissaoJogador/porJogador")
    ArrayList<MissaoJogador> getMissaoListByJogador(Jogador jogador);
}
