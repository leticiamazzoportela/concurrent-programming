import java.util.ArrayList;
/**
 * StatesManager
 *
 * @author Letícia Mazzo Portela
 * @since Aug/2020
 */
final class StateDetail {
    private final String stateName;
    private final String stateInitials;
    private final String stateRegion;

    public StateDetail(final String stateName, final String stateInitials, final String stateRegion) {
        this.stateName = stateName;
        this.stateInitials = stateInitials;
        this.stateRegion = stateRegion;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getStateInitials() {
        return this.stateInitials;
    }

    public String getStateRegion() {
        return this.stateRegion;
    }
}

final class State {
    private final ArrayList<StateDetail> states = new ArrayList<StateDetail>() {
        {
            add(new StateDetail("Acre", "AC", "Norte"));
            add(new StateDetail("Alagoas", "AL", "Nordeste"));
            add(new StateDetail("Amapá", "AP", "Norte"));
            add(new StateDetail("Amazonas", "AM", "Norte"));
            add(new StateDetail("Bahia", "BA", "Nordeste"));
            add(new StateDetail("Ceará", "CE", "Nordeste"));
            add(new StateDetail("Distrito Federal", "DF", "Centro-Oeste"));
            add(new StateDetail("Espírito Santo", "ES", "Sudeste"));
            add(new StateDetail("Goiás", "GO", "Centro-Oeste"));
            add(new StateDetail("Maranhão", "MA", "Nordeste"));
            add(new StateDetail("Mato Grosso", "MT", "Centro-Oeste"));
            add(new StateDetail("Mato Grosso do Sul", "MS", "Centro-Oeste"));
            add(new StateDetail("Minas Gerais", "MG", "Sudeste"));
            add(new StateDetail("Pará", "PA", "Norte"));
            add(new StateDetail("Paraíba", "PB", "Nordeste"));
            add(new StateDetail("Paraná", "PR", "Sul"));
            add(new StateDetail("Pernambuco", "PE", "Nordeste"));
            add(new StateDetail("Piauí", "PI", "Nordeste"));
            add(new StateDetail("Rio de Janeiro", "RJ", "Sudeste"));
            add(new StateDetail("Rio Grande do Norte", "RN", "Nordeste"));
            add(new StateDetail("Rio Grande do Sul", "RS", "Sul"));
            add(new StateDetail("Rondônia", "RO", "Norte"));
            add(new StateDetail("Roraima", "RR", "Norte"));
            add(new StateDetail("Santa Catarina", "SC", "Sul"));
            add(new StateDetail("São Paulo", "SP", "Sudeste"));
            add(new StateDetail("Sergipe", "SE", "Nordeste"));
            add(new StateDetail("Tocantins", "TO", "Norte"));
        }
    };

    public void getStateInitials() {
        System.out.println("\n**** State Initials ***\n");

        states.forEach(st -> System.out.print(" * " + st.getStateInitials()));

        System.out.println("\n\n*********\n");
    }

    public void getStateNames() {
        System.out.println("\n**** State Names ***\n");

        states.forEach(st -> System.out.print(" * " + st.getStateName()));

        System.out.println("\n\n*********\n");
    }

    public void getStateByInitials(String initials) {
        System.out.println("\n**** State By Initials ***\n");

        states.stream()
        .filter(st -> st.getStateInitials().equals(initials))
        .forEach(st -> System.out.print(
            " Name: " + st.getStateName() +
            "  Initials: " + st.getStateInitials() +
            "  Region: " + st.getStateRegion()
            )
        );

        System.out.println("\n\n*********\n");
    }

    public void getStateByName(String name) {
        System.out.println("\n**** State By Name ***\n");

        states.stream()
        .filter(st -> st.getStateName().equals(name))
        .forEach(st -> System.out.print(
            " Name: " + st.getStateName() +
            "  Initials: " + st.getStateInitials() +
            "  Region: " + st.getStateRegion()
            )
        );

        System.out.println("\n\n*********\n");
    }

    public void getStatesByRegion(String region) {
        System.out.println("\n**** States By Region: " + region + " ***\n");

        states.stream()
        .filter(st -> st.getStateRegion().equals(region))
        .forEach(st -> System.out.print(" * " + st.getStateName()));

        System.out.println("\n\n*********\n");
    }
}

class StatesManager {
    public static void main(final String[] args) {
        final State st = new State();

        st.getStateInitials();
        st.getStateNames();

        st.getStateByInitials("PR");
        st.getStateByInitials("SP");
        st.getStateByInitials("RS");

        st.getStateByName("Amazonas");
        st.getStateByName("Bahia");
        st.getStateByName("Santa Catarina");

        st.getStatesByRegion("Sul");
        st.getStatesByRegion("Sudeste");
        st.getStatesByRegion("Centro-Oeste");
        st.getStatesByRegion("Nordeste");
        st.getStatesByRegion("Norte");
    }
}