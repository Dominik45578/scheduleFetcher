ICS Generator API

Mikroserwis umożliwiający generowanie plików **ICS** (kalendarza zajęć) dla wybranego wydziału i grup.

---
### Endpointy API

 1. Generowanie pliku ICS

**Endpoint:**
```
POST http://localhost:12346/api/ics
```

**Opis:**  
Generuje plik ICS z planem zajęć na podstawie wybranego wydziału i grup.

**Przykładowe żądanie:**
```json
{
  "faculty": "TWOJ_ROK",
  "groups": ["GRUPA", "GRUPA", "GRUPA", "..."]
}
```
###  2. Lista dostępnych semestrów

**Endpoint:**
```
GET http://localhost:12346/api/faculty
```

**Opis:**  
Zwraca listę dostępnych semestrów, które można wykorzystać w polu `faculty`. Program zaiwera także semestry obecnie niedostępne .

---

### 3. Lista dostępnych grup

**Endpoint:**
```
GET http://localhost:12346/api/groups
```

**Opis:**  
Zwraca listę wszystkich dostępnych grup możliwych do wykorzystania w polu `groups`.

---
### 4. Podgląd przefiltrowanych zajęć

**Endpoint:**
```
POST http://localhost:12345/api/events/filter
```

**Opis:**  
Zwraca przefiltrowane wydarzenia do podglądu.

---

# **Przykłady:**
```json
{
  "faculty": "iwiks5",
  "groups": ["L5", "LK2", "P2", "W"]
}
```

lub

```json
{
  "faculty": "iwiks3",
  "groups": ["L5", "W"]
}
```

> 🔸 `W` oznacza **Wykład**

---

## Wysyłanie zapytań

Żądania możesz wysyłać:
- bezpośrednio z **IntelliJ**,
- lub za pomocą **Postmana**.

## Jak użyć
- Skopiuj treść odpowiedzi do notatnika i zapisz jako plik ics
  - możesz go zaimportować do każdego kalendarza
  - aby sprawdzić możesz zobaczyć strony ics preview
  - zweryfikuj poprawnośc danych z kalendarzem z strony wydziału
- Treść odpowiedzi powinna wyglądać podobnie 

```ics
BEGIN:VCALENDAR
PRODID:-//Polibuda Scraper//ICS Generator//PL
VERSION:2.0
BEGIN:VEVENT
SUMMARY:Systemy baz danych, W, mgr inż. K. Czajkowski, s. A1, IwIKs5
DTSTART:20251006T180000
DTEND:20251006T193000
RRULE:FREQ=WEEKLY;INTERVAL=1;UNTIL=20260128T010000;
LOCATION:A1
DESCRIPTION:mgr inż. K. Czajkowski - IwIKs5 - W
END:VEVENT
BEGIN:VEVENT
-----
END:VEVENT

.....

END:VCALENDAR
```

  
---

##  Uwagi

- Endpointy są lokalne, więc aplikacja musi być uruchomiona w intelij lub jako kontener w docker
- W przypadku błędu `Bad Request` sprawdź poprawność nazw `faculty` oraz `groups`.

---

**Autor:**  
Dominik Koralik - WIEik - IwIK
