package com.example.Pokedex;

import java.util.ArrayList;
import java.util.List;

public class PokemonSimple extends Pokemon{
    private int level;
    private List<Type> types;
    private List<PokemonEvolution> evolutions;
    private List<Ability> abilities;

    public PokemonSimple(int _level, String _name) {
        super(_name);
        this.level = _level;
        this.types = new ArrayList<>();
        this.evolutions = new ArrayList<>();
        this.abilities = new ArrayList<>();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void addTypes(Type type) {
        this.types.add(type);
    }

    public List<PokemonEvolution> getEvolutions() {
        return evolutions;
    }

    public void setEvolutions(PokemonEvolution evolution) {
        this.evolutions.add(evolution);
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability ability) {
        this.abilities.add(ability);
    }
}
