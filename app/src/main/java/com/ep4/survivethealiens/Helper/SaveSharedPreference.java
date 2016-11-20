package com.ep4.survivethealiens.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.ep4.survivethealiens.Model.Jogador;

/**
 * Created by Carla on 19/11/2016.
 */

public class SaveSharedPreference {
    static final String PREF_USER_EMAIL = "username";
    static final String PREF_USER_SENHA = "password";
    static final String PREF_USER_ID = "id";
    static final String PREF_USER_GENERO = "genero";
    static final String PREF_USER_NOME = "nome";
    static final String PREF_USER_APELIDO = "apelido";
    static final String PREF_USER_HORAS = "horas";
    static final String PREF_USER_KM = "quilometros";
    static final String MISSAO_TEMPO = "tempo";
    static final String MISSAO_DISTANCIA = "distancia";



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setJogador(Context ctx, Jogador jogador)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_EMAIL, jogador.getEmail());
        editor.putString(PREF_USER_APELIDO, jogador.getApelido());
        editor.putString(PREF_USER_GENERO, jogador.getGenero());
        editor.putFloat(PREF_USER_HORAS, jogador.getHorasJogadas());
        editor.putInt(PREF_USER_ID, jogador.getId());
        editor.putFloat(PREF_USER_KM, jogador.getKmCaminhados());
        editor.putString(PREF_USER_NOME, jogador.getNome());
        editor.putString(PREF_USER_SENHA, jogador.getSenha());
        editor.commit();
    }

    public static Jogador getJogador(Context ctx)
    {
        Jogador jogador = new Jogador();
        jogador.setApelido(getSharedPreferences(ctx).getString(PREF_USER_APELIDO, ""));
        jogador.setEmail(getSharedPreferences(ctx).getString(PREF_USER_EMAIL, ""));
        jogador.setGenero(getSharedPreferences(ctx).getString(PREF_USER_GENERO, ""));
        jogador.setHorasJogadas(getSharedPreferences(ctx).getFloat(PREF_USER_HORAS, 0));
        jogador.setId(getSharedPreferences(ctx).getInt(PREF_USER_ID, 0));
        jogador.setKmCaminhados(getSharedPreferences(ctx).getFloat(PREF_USER_KM, 0));
        jogador.setNome(getSharedPreferences(ctx).getString(PREF_USER_NOME, ""));
        jogador.setSenha(getSharedPreferences(ctx).getString(PREF_USER_SENHA, ""));

        return jogador;

        //return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }

    public static int getId(Context ctx)
    {
        int id = getSharedPreferences(ctx).getInt(PREF_USER_ID, 0);
        return id;
    }

    public static float getDistancia(Context ctx){
        return getSharedPreferences(ctx).getFloat(MISSAO_DISTANCIA, 0);
    }

    public static float getTempo(Context ctx){
        return  getSharedPreferences(ctx).getLong(MISSAO_TEMPO, 0);
    }

    public static void setTempoDistancia(Context ctx, float tempo, float distancia){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putFloat(MISSAO_TEMPO, tempo);
        editor.putFloat(MISSAO_DISTANCIA, distancia);
    }

    public static void clearAllData(View v, Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

    public static void clearTempoDistancia(Context ctx){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(MISSAO_DISTANCIA);
        editor.remove(MISSAO_TEMPO);
    }
}
