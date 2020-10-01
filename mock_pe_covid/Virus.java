class Virus {
    private String name;
    private double prob;
    Virus(String name, double probabilityOfMutating) {
        this.prob = probabilityOfMutating;
        this.name = name;
    }

    Virus spread(double random) {
        if (random > this.prob) {
            if (this instanceof AlphaCoronavirus) {
                double multiplier = SimulationParameters.VIRUS_MUTATION_PROBABILITY_REDUCTION;
                return new AlphaCoronavirus(this.prob*multiplier);
            }
            if (this instanceof SARS_CoV_2) {
                double multiplier = SimulationParameters.VIRUS_MUTATION_PROBABILITY_REDUCTION;
                return new SARS_CoV_2(this.prob*multiplier);
            }
        }

        if (random <= this.prob) {
            if (this instanceof AlphaCoronavirus) {
               return new SARS_CoV_2(this.prob);
            }
            if (this instanceof SARS_CoV_2) {
               return new BetaCoronavirus();
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s with %.3f probability of mutating",this.name, this.prob);
    }

    String getName() {
        return this.name;
    }
}
