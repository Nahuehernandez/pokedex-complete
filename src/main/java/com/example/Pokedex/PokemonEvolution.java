package com.example.Pokedex;

import java.util.ArrayList;
import java.util.List;

public class PokemonEvolution extends Pokemon{
    private List<Ability> abilities;

    public PokemonEvolution(String name) {
        super(name);
        this.abilities = new ArrayList<>();
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(Ability ability) {
        this.abilities.add(ability);
    }
}
