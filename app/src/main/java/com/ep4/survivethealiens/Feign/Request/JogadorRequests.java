package com.ep4.survivethealiens.Feign.Request;

import com.ep4.survivethealiens.Model.Credenciais;
import com.ep4.survivethealiens.Model.Jogador;
import com.ep4.survivethealiens.Model.Missao;
import com.ep4.survivethealiens.Model.MissaoJogador;

import java.util.ArrayList;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.codec.StringDecoder;

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

    @RequestLine("POST Jogadors/")
    @Headers("Content-Type: application/json")
    Jogador criarJogador(Jogador jogador);

    @RequestLine("POST Autenticar/creds")
    @Headers("Content-Type: application/json")
    Jogador autenticarJogador(Credenciais creds);
    //Jogador autenticarJogador(String email, String senha);

    @RequestLine("PUT Jogadors/{id}/")
    @Headers("Content-Type: application/json")
    Jogador atualizarJogador(@Param("id") Integer id, Jogador jogador);

    @RequestLine("GET MissaoJogadors/{id}/")
    ArrayList<MissaoJogador> getMissoesById(@Param("id") Integer id);

    @RequestLine("GET Missaos/")
    ArrayList<Missao> getMissoes();
}
