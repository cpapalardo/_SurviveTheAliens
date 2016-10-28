package com.ep4.survivethealiens.Feign.Request;

import com.ep4.survivethealiens.Model.Jogador;

import feign.Param;
import feign.RequestLine;

/**
 * Created by Carla on 23/10/2016.
 */

public interface JogadorRequests {
    //só coloca as assinaturas de método na interface

    //mapeando
    //Id do RequestLine é o mesmo do @Param, e é usado como parâmetro do getPostagem
    //NESTE CASO é GET. Isso depende de para onde será enviada a requisição. Se for POST, trocar por POST, etc
    @RequestLine("GET Jogadors/{id}/")
    Jogador getPostagem(@Param("id") Integer id);

    @RequestLine("POST Jogador")
    Jogador criarJogador(Jogador jogador);
}
