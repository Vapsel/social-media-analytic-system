package smas.dto;

import lombok.Value;

import java.util.List;

@Value
public class PreferencesResponseDTO {

    String satAnswer;

    List<String> sortedPreferences;
}
