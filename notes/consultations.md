# Social media analytic system

### Pytania do dokumentacji:
1. **Szczegółowy opis przepływu czynności projektu** (diagram aktywności głównego przypadku użycia).

2. **Najpierw przygotowania dokumentacji, potem implementacja?**   
Lepiej wstępnie określić i na bieżąco zmieniać/dopasowywać.Tak.


3. Przykładowe przypadki użycia:
	- Logowanie administratora biura podróży (użytkownika w portalach)
	- Stworzenie profilu użytkownika (zapisanie w bazie) 
	- Tworzenie oferty turystycznej (czy szukanie w internecie, jakaś zewnętrzna baza)
	- Dopasowanie ofert dla użytkownika (sortowanie według trafności). Możliwość dodatkowych parametrów do wyszukiwania?

### Pytania do implementacji
1. **Czy użytkownik ma wprowadzać login hasło do kont w portalach społecznościowych?**  
Można spróbować szukać informacji bez autentyfikacji. Może pojawić się problem dwóch osób o tym samym imieniu i nazwisku, ukryte profile użytkowników, mniej informacji. Do przeanalizowania strony, udostępniające taką informację (parser HTML może być do napisania).


2. **Najpierw implementacja podstawowej wersji - potem szczegóły**  
Po kaławkach.


3. **Wykorzystanie konkretnych narzędzi? Solwery? (napiszę własny prosty Rule Engine)**  
Opisać (zakodować) w postaci formuły logicznej - sat solvery. Spwrawdzić co jest spełnione - to proponujemy. Problem zapisu - ma być w postaci *CNF*.  
Solvery: Glukoza, Linkelin  
Język dimaks


4. **Tworzenie ofert?**  
Dowolnie. Może być baza

############################################

