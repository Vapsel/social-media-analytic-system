## SAT solver explenation

**Ustalenia:**
- każda oferta i profil użytkownika jest konwertowana do CNF
- każdy parametr jest zapisany jako kategoria
- sprawdzenia dla każdej oferty - obliczeniowo szybko (ułamki sekundy dla tysiąca zmiennych)
- tworzenie formuły dla oferty po czym dokładanie parametrów użytkownika
- ulunione lokalizacji użytkowina muszą być podstawione jako lokalizacja oferty 

**Problemy:**
1. Filtracja po czasie otwarcia obiektu

#### Przykładowa oferta
Lokalizacja: Zakopane  
Rodzaj oferty: Kompleks sportowy  
Sport: narty, rower, wycieczki górne  
Jedzenie: pizza
Parking: brak

#### Przykładowy użytkownik
Lokalizacja: Kraków  
Ulubione lolizacji: -  
Rodzaj oferty: -  
Sport: narty  
Jedzenie: pizza



# 
# Rule engine

- Tworzenie profilu użytkownika uzuppełniając predefiniowane kategorie np.:
	- lokalizacja (gdzie aktualnie mieszka ewentualnie ulibione lokalizacji świata)
	- sport
	- jedzenie
	- interesujące aktywności (gry planszowe)
	- etc.
- Zbudowanie rule engine która na podstawie istniejących profilów w portalach społecznościowych stworzy profil w aplikacji
