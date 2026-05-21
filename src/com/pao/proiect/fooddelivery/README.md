# Platformă Food Delivery — Proiect PAO (Etapa I)

Acest proiect reprezintă implementarea unei platforme de tip food delivery, dezvoltată în Java. Arhitectura respectă principiile de Programare Orientată pe Obiecte (OOP), incluzând concepte precum încapsularea, moștenirea, polimorfismul, utilizarea colecțiilor și tratarea excepțiilor custom.

---

## 1. Definirea sistemului

### 1.1 Acțiuni și interogări disponibile
Sistemul permite următoarele fluxuri operaționale:
1. **Adaugă un client nou** în sistem.
2. **Înregistrează un șofer nou** pentru livrări.
3. **Adaugă un restaurant partener** în platformă.
4. **Adaugă preparate** în meniul unui restaurant.
5. **Plasează o comandă nouă** (asociază clientul, restaurantul și produsele dorite).
6. **Alocă primul șofer disponibil** unei comenzi aflate în așteptare.
7. **Actualizează statusul** unei comenzi (ex: `IN_PREPARARE`, `IN_LIVRARE`, `FINALIZATA`).
8. **Adaugă o recenzie** și recalculează automat ratingul restaurantului vizat.
9. **Caută un client** după adresa de email (aruncă excepția custom `UserNotFoundException` dacă nu există).
10. **Șterge un client** din sistem.
* *Extra:* **Listează topul restaurantelor**, ordonate descrescător după rating.

### 1.2 Obiecte de domeniu (Modele)
Arhitectura include următoarele 8 entități principale:
1. `User` — Clasă abstractă de bază pentru utilizatorii platformei.
2. `Client` — Moștenește `User`.
3. `Driver` — Moștenește `User` și conține logica de disponibilitate.
4. `Address` — Clasă complet imutabilă (atribute `final`, fără setteri).
5. `Restaurant` — Implementează interfața `Comparable` pentru sortarea naturală în colecții.
6. `MenuItem` — Reprezintă un produs finit din meniu.
7. `Order` — Modelează o comandă și își calculează automat prețul total.
8. `Review` — Leagă un client de un restaurant, stocând nota și feedback-ul.
---

## 2. Etapa II — Persistență JDBC și Audit

În această etapă, datele au fost migrate din memoria aplicației într-o bază de date relațională (MySQL), respectând următoarele implementări tehnice:

* **Schema SQL (`schema.sql`):** Baza de date conține 7 tabele (`clienti`, `soferi`, `restaurante`, `produse`, `comenzi`, `comenzi_produse`, `recenzii`), interconectate prin 8 chei străine (Foreign Keys), respectând clauzele de `DROP TABLE` și tipul `VARCHAR(36)` pentru ID-urile de tip UUID.
* **Conexiune Singleton:** Conexiunea la baza de date se face printr-o clasă unică ce citește credențialele din fișierul `resources/db.properties`.
* **Repository Pattern (CRUD):** Am implementat interfața generică `Repository<T, ID>` pentru 4 entități: `Client`, `Driver`, `Restaurant` și `Review`. Toate interogările folosesc exclusiv `PreparedStatement` și se închid curat prin blocuri `try-with-resources`.
* **Tranzacții JDBC:** Plasarea unei comenzi (în tabelul `comenzi` și tabelul de legătură `comenzi_produse`) se face printr-o tranzacție explicită (`setAutoCommit(false)`), cu `commit()` în caz de succes și `rollback()` automat la orice excepție (demonstrat în `Main`).
* **Interogări JOIN:** `DeliveryService` expune 3 metode complexe de raportare bazate pe `INNER JOIN` și `LEFT JOIN` (ex: calcularea rating-ului mediu direct din baza de date, istoricul complet al unei comenzi).
* **AuditService:** Implementat ca Singleton thread-safe (folosind `ReentrantLock`). Acesta scrie automat un fișier `audit.csv` în modul *append*, înregistrând numele fiecărei acțiuni efectuate în sistem alături de timestamp-ul exact.