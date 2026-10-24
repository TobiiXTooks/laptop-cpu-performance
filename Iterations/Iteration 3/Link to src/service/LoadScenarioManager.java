package service;

import model.LoadScenario;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Verwaltet und validiert die Lastszenarien der Iteration 3.
 */
public class LoadScenarioManager {

    private final Map<String, LoadScenario> scenarios;

    public LoadScenarioManager() {
        this.scenarios = new LinkedHashMap<>();

        addScenario(new LoadScenario("IDLE", "Leerlauf", 15.0));
        addScenario(new LoadScenario("OFFICE", "Office", 35.0));
        addScenario(new LoadScenario("MULTIMEDIA", "Multimedia", 55.0));
        addScenario(new LoadScenario("GAMING", "Gaming", 85.0));
        addScenario(new LoadScenario("STRESS_TEST", "Stresstest", 100.0));
    }

    public List<LoadScenario> listScenarios() {
        return new ArrayList<>(scenarios.values());
    }

    public boolean isValidScenario(String scenarioKey) {
        if (scenarioKey == null || scenarioKey.isBlank()) {
            return false;
        }

        return scenarios.containsKey(normalizeKey(scenarioKey));
    }

    public LoadScenario getScenario(String scenarioKey) {
        if (scenarioKey == null || scenarioKey.isBlank()) {
            throw new IllegalArgumentException("Es muss ein Lastszenario angegeben werden.");
        }

        LoadScenario scenario = scenarios.get(normalizeKey(scenarioKey));
        if (scenario == null) {
            throw new IllegalArgumentException("Das Lastszenario ist unbekannt: " + scenarioKey);
        }

        return scenario;
    }

    private void addScenario(LoadScenario scenario) {
        scenarios.put(scenario.getKey(), scenario);
    }

    private String normalizeKey(String key) {
        return key.trim().toUpperCase();
    }
}
