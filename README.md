# Projekt dotyczący wyszukiwania szkół

Bardzo pobieżny opis struktury projektu:
- W folderze `Schoolify` znajduje się główna część naszego projektu = aplikacja serwerowa w Spring Boot.
- W folderze `dbSetup` znajduje się tabela oraz skrypt na podstawie którego można wygenerować bazę danych do której się odwołujemy z serwera.
- W folderze `coordsScript` znajduje się skrypt (w Javie) który posłużył nam do wygenerowana współrzędnych geograficznych szkół na podstawie adresów. (Na razie bardzo nieperfekcyjne, będziemy próbować jeszcze raz)
- W folderze `frontend` znajduje się bardzo wstępny prototyp frontendu do interakcji z serwerem.  



## Funkcjonalności systemu

1. ~~Stworzenie bazy danych na podstawie arkusza Excel udostępnionego przez Ministerstwo Edukacji.~~
2. ~~Komunikacja serwera z bazą danych.~~
3. ~~Wysyłanie RESTowych odpowiedzi na zapytania klienta.~~
4. Przesyłanie zapytań z frontendu. (wysyłanie filtrów ograniczających szkoły).
5. Wyświetlanie odpowiedzi z serwera.
6. Logika biznesowa aplikacji. (m. in. filtrowanie po stronie backendu, wybieranie odpowiednich rekordów z bazy)
7. ~~Geolokalizacja szkół (wyznaczenie współrzędnych na podstawie adresu).~~
8. System wystawiania opinii.
9. Wyświetlanie szkół na mapie.
10. Pełna informacja o szkole po kliknięciu na mapie.

