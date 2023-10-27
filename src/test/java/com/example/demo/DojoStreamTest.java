package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream()
                .filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        result.forEach((key, jugadores) -> {
            System.out.println("\n");
            System.out.println(key + ": ");
            jugadores.forEach(System.out::println);
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> listPlayer = CsvUtilFile.getPlayers();
        Optional<Player> mejorJugador = listPlayer.stream()
                .filter(player -> "France".equals(player.getNational())
                ).max((player1, player2) -> Integer.compare(player1.getWinners() + player1.getGames()
                        ,player2.getWinners() + player2.getGames()));

        System.out.println(mejorJugador);
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> listPlayer = CsvUtilFile.getPlayers();

        Map<String, List<String>> clubPorNacionalidad = listPlayer.stream()
                .collect(Collectors.groupingBy(
                        player -> player.getNational(),
                        Collectors.mapping(player -> player.getClub(), Collectors.toList())
                ));

        clubPorNacionalidad.forEach((nacionalidad, clubs) -> {
            System.out.println("Nacionaliddad: " + nacionalidad);
            System.out.println("Clubes: " + clubs);
            System.out.println();
        });
    }

    @Test
    void clubConElMejorJugador(){
        List<Player> listPlayer = CsvUtilFile.getPlayers();

        Player mejorPlayer = listPlayer.stream()
                .max((play1, play2) -> Integer.compare(play1.getWinners() + play1.getGames()
                        ,play2.getWinners() + play2.getGames()))
                .orElse(null);

        if(mejorPlayer != null){
            String mejorClub = mejorPlayer.getClub();
            System.out.println("El club con el mejor jugador es: " + mejorClub);
        }
    }

    @Test
    void ElMejorJugador(){
        List<Player> listPlayer = CsvUtilFile.getPlayers();

        Player mejorPlayer = listPlayer.stream()
                .max((play1, play2) -> Integer.compare(play1.getWinners() + play1.getGames()
                        ,play2.getWinners() + play2.getGames()))
                .orElse(null);

        System.out.println("El mejor jugador es: " + mejorPlayer);
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, Optional<Player>> result = list.stream()
                .collect(Collectors.groupingBy(player -> player.getNational(),
                        Collectors.maxBy(Comparator.comparing(Player::getWinners)))
                );
        result.entrySet().forEach(System.out::println);
    }
}
