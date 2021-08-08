package com.example.DB;

import com.example.Pokedex.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Db {
    //NEW POKEMON
    public void newPokemon(Statement statement, PokemonSimple pokemon){
        String query = "insert into pokedex.pokemon(name_pokemon, level_pokemon) values ('" + pokemon.getName() + "'," + pokemon.getLevel() + ")";
        try {
            ResultSet resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        insertIfItHasTypes(statement, pokemon);
        insertIfItHasAbilities(statement, pokemon);
        insertIfItHasEvolutions(statement, pokemon);
    }

    public void insertIfItHasTypes(Statement statement, PokemonSimple pokemon){
        if(pokemon.getTypes().size() > 0){
            for(int i = 0; i < pokemon.getTypes().size(); i++){
                insertType(statement, pokemon.getTypes().get(i));
            }
            String query = "select * from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and ID_TYPE is null";
            try {
                ResultSet searchID;
                ResultSet resultSet = statement.executeQuery(query);
                int cont = 1;
                while(resultSet.next() && cont <= pokemon.getTypes().size()){
                    searchID = statement.executeQuery("select id_type from pokedex.type_pokemon where description_type = '" + pokemon.getTypes().get(cont).getDescription() + "'");
                    ResultSet newResult = statement.executeQuery("update pokedex.pokemon set id_type = " + searchID.getInt("id_type") + " where id_pokemon = '" + resultSet.getString("id_pokemon") + "'");
                    cont ++;
                }
                if(cont < pokemon.getTypes().size()){
                    ResultSet insert;
                    ResultSet search;
                    String queryInsert;
                    for(int i = cont; i <= pokemon.getTypes().size(); i++) {
                        search = statement.executeQuery("select id_type from pokedex.pokemon where description_type = '" + pokemon.getTypes().get(i).getDescription() + "'");
                        queryInsert = "insert into pokedex.pokemon(name_pokemon, level_pokemon, id_type) values ('" + pokemon.getName() + "'," + pokemon.getLevel() + "," + search.getInt("id_type") + ")";
                        insert = statement.executeQuery(queryInsert);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void insertIfItHasAbilities(Statement statement, PokemonSimple pokemon){
        if(pokemon.getAbilities().size() > 0){
            for(int i = 0; i < pokemon.getAbilities().size(); i++){
                insertAbiility(statement, pokemon.getAbilities().get(i));
            }
            String query = "select * from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and ID_ABILITY is null";
            try {
                ResultSet searchID;
                ResultSet resultSet = statement.executeQuery(query);
                int cont = 1;
                while(resultSet.next() && cont <= pokemon.getAbilities().size()){
                    searchID = statement.executeQuery("select id_ability from pokedex.ability where description_ability = '" + pokemon.getAbilities().get(cont).getDescription() + "'");
                    ResultSet newResult = statement.executeQuery("update pokedex.pokemon set id_ability = '" + searchID.getInt("id_abilty") + "' where id_pokemon = '" + resultSet.getString("id_pokemon") + "'");
                    cont ++;
                }
                if(cont < pokemon.getAbilities().size()){
                    ResultSet insert;
                    ResultSet search;
                    String queryInsert;
                    for(int i = cont; i <= pokemon.getAbilities().size(); i++) {
                        search = statement.executeQuery("select id_ability from pokedex.pokemon where description_ability = '" + pokemon.getAbilities().get(cont).getDescription() + "'");
                        queryInsert = "insert into pokedex.pokemon(name_pokemon, level_pokemon, id_ability) values ('" + pokemon.getName() + "'," + pokemon.getLevel() + "," + search.getInt("id_ability") + ")";
                        insert = statement.executeQuery(queryInsert);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void insertIfItHasEvolutions(Statement statement, PokemonSimple pokemon){
        if(pokemon.getEvolutions().size() > 0){
            for(int i = 0; i < pokemon.getEvolutions().size(); i++){
                insertEvolution(statement, pokemon.getEvolutions().get(i));
            }
            String query = "select * from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and ID_EVOLUTION is null";
            try {
                ResultSet searchID;
                ResultSet resultSet = statement.executeQuery(query);
                int cont = 1;
                while(resultSet.next() && cont <= pokemon.getEvolutions().size()){
                    searchID = statement.executeQuery("select id_evolution from pokedex.evolution where name_evolution = '" + pokemon.getEvolutions().get(cont).getName() + "'");
                    ResultSet newResult = statement.executeQuery("update pokedex.pokemon set id_evolution = '" + searchID.getInt("id_evolution") + "' where id_pokemon = '" + resultSet.getString("id_pokemon") + "'");
                    cont ++;
                }
                if(cont < pokemon.getEvolutions().size()){
                    ResultSet insert;
                    ResultSet search;
                    String queryInsert;
                    for(int i = cont; i <= pokemon.getEvolutions().size(); i++) {
                        search = statement.executeQuery("select id_evolution from pokedex.evolution where name_evolution = '" + pokemon.getEvolutions().get(cont).getName() + "'");
                        queryInsert = "insert into pokedex.pokemon(name_pokemon, level_pokemon, id_evolution) values ('" + pokemon.getName() + "'," + pokemon.getLevel() + "," + search.getInt("id_evolution") + ")";
                        insert = statement.executeQuery(queryInsert);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void insertType(Statement statement, Type type){
        String query = "select count(*) as total from pokedex.type_pokemon where description_type = '" + type.getDescription() +"'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.getInt("total") == 0){
                resultSet = statement.executeQuery("insert into pokedex.type_pokemon(description_type) values ('" + type.getDescription() + "')");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertAbiility(Statement statement, Ability ability){
        String query = "select count(*) as total from pokedex.ability where description_ability = '" + ability.getDescription() +"'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.getInt("total") == 0){
                resultSet = statement.executeQuery("insert into pokedex.ability(description_ability) values ('" + ability.getDescription() + "')");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertEvolution(Statement statement, PokemonEvolution evolution){
        String query = "select count(*) as total from pokedex.evolution where  name_evolution = '" + evolution.getName() +"'";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.getInt("total") == 0){
                resultSet = statement.executeQuery("insert into pokedex.evolution(name_evolution) values ('" + evolution.getName() + "')");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //EDIT POKEMON ---> Doy porcentado que el pokemon existe, chequear eso
    public void modifyName(Statement statement, String oldName, String newName){
        ResultSet resultSet;
        ResultSet update;
        String query = "select * from pokedex.pokemon where name_pokemon = '" + oldName +"'";
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                query = "update pokedex.pokemon set name_pokemon = '" + newName + "' where id_pokemon = " + resultSet.getInt("id_pokemon");
                update = statement.executeQuery(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addNewType(Statement statement, String name, Type type){
        insertType(statement, type);

        ResultSet resultSet;
        String searchId = "select id_type from pokedex.type_pokemon where description_type = '" + type.getDescription() + "'";
        String query = "select count(*) as cantidad from pokedex.pokemon where name_pokemon = '" + name + "' and id_type is null";
        try {
            resultSet = statement.executeQuery(searchId);
            int id_type = resultSet.getInt("id_type");
            resultSet = statement.executeQuery(query);
            if(resultSet.getInt("cantidad") > 0){
                query = "select id_pokemon from pokedex.pokemon where name_pokemon = '" + name + "' and id_type is null limit 1";
                resultSet = statement.executeQuery(query);
                query = "update pokedex.pokemon set id_type = " + id_type + " where id_pokemon = " + resultSet.getInt("id_pokemon");
            }else{
                query = "insert into pokedex.pokemon(name_pokemon, id_type) values ('" + name + "'," + id_type + ")";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeType(Statement statement, String name, Type type){
        ResultSet resultSet;
        String searchId = "select id_type from pokedex.type_pokemon where description_type = '" + type.getDescription() + "'";
        try {
            resultSet = statement.executeQuery(searchId);
            int id_type = resultSet.getInt("id_type");
            String query = "select id_pokemon, id_ability, id_evolution from pokedex.pokemon where name_pokemon = '" + name + "' and id_type = " + id_type;
            resultSet = statement.executeQuery(query);
            if(resultSet.getString("id_ability") == null && resultSet.getString("id_evolution") == null){
                query = "delete from pokedex.pokemon where id_pokemon = " + resultSet.getInt("id_pokemon");
                resultSet = statement.executeQuery(query);
            }else{
                query = "update pokedex.pokemon set id_type = NULL where id_pokemon = " + resultSet.getInt("id_pokemon");
                resultSet = statement.executeQuery(query);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void newEvolution(Statement statement, String name, PokemonEvolution evolution){
        insertEvolution(statement, evolution);

        ResultSet resultSet;
        String searchId = "select id_evolution from pokedex.evolution where name_evolution = '" + evolution.getName() + "'";
        String query = "select count(*) as cantidad from pokedex.pokemon where name_pokemon = '" + name + "' and id_evolution is null";
        try {
            resultSet = statement.executeQuery(searchId);
            int id_evolution = resultSet.getInt("id_evolution");
            resultSet = statement.executeQuery(query);
            if(resultSet.getInt("cantidad") > 0){
                query = "select id_pokemon from pokedex.pokemon where name_pokemon = '" + name + "' and id_evolution is null limit 1";
                resultSet = statement.executeQuery(query);
                query = "update pokedex.pokemon set id_evolution = " + id_evolution + " where id_pokemon = " + resultSet.getInt("id_pokemon");
            }else{
                query = "insert into pokedex.pokemon(name_pokemon, id_evolution) values ('" + name + "'," + id_evolution + ")";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //LIST ALL POKEMONS
    public List<PokemonSimple> listAllPokemons(Statement statement){
        String query = "select name_pokemon from pokedex.pokemon group by name_pokemon";
        List<PokemonSimple> pokemons = new ArrayList<>();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                pokemons.add(generatePokemon(statement, resultSet.getString("name_pokemon")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pokemons;
    }

    public PokemonSimple generatePokemon(Statement statement, String namePokemon){
        ResultSet resultSet;
        PokemonSimple pokemon = new PokemonSimple(0, namePokemon);
        String query = "select level_pokemon from pokedex.pokemon where name_pokemon = '" + namePokemon + "' limit 1";
        try {
            resultSet = statement.executeQuery(query);
            pokemon.setLevel(resultSet.getInt("level_pokemon"));
            getTypes(statement, pokemon);
            getAbilities(statement, pokemon);
            getEvolutions(statement, pokemon);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return pokemon;
    }

    public void getTypes(Statement statement, PokemonSimple pokemon){
        String query = "select id_type from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and id_type is not null";
        ResultSet resultSet;
        ResultSet types;
        int id_type;
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                id_type = resultSet.getInt("id_type");
                query = "select description_type from type_pokemon where id_type = " + id_type;
                types = statement.executeQuery(query);
                Type type = new Type(types.getString("description_type"));
                pokemon.addTypes(type);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getAbilities(Statement statement, PokemonSimple pokemon){
        String query = "select id_ability from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and id_ability is not null";
        ResultSet resultSet;
        ResultSet abilities;
        int id_abilty;
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                id_abilty = resultSet.getInt("id_type");
                query = "select description_abilty from abilities where id_abilty = " + id_abilty;
                abilities = statement.executeQuery(query);
                Ability ability = new Ability(abilities.getString("description_abilty"));
                pokemon.setAbilities(ability);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getEvolutions(Statement statement, PokemonSimple pokemon){
        String query = "select id_evolution from pokedex.pokemon where name_pokemon = '" + pokemon.getName() + "' and id_evolution is not null";
        ResultSet resultSet;
        ResultSet evolutions;
        int id_evolution;
        try {
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                id_evolution = resultSet.getInt("id_type");
                query = "select name_evolution, description_abilty from evolution join abilty on id_abilty = id_abilty where id_evolution = " + id_evolution;
                evolutions = statement.executeQuery(query);
                PokemonEvolution evolution = new PokemonEvolution(evolutions.getString("name_evolution"));
                Ability ability = new Ability(evolutions.getString("description_abilty"));
                evolution.setAbilities(ability);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}