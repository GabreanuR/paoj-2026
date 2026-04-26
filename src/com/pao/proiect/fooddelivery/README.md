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