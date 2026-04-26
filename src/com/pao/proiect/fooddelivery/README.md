# Proiect Food Delivery - Etapa I

## 1.1 Lista celor 10 acțiuni / interogări
1. Adaugă un client nou în sistem
2. Înregistrează un șofer nou
3. Adaugă un restaurant partener
4. Adaugă preparate în meniul unui restaurant
5. Plasează o comandă nouă
6. Alocă primul șofer disponibil unei comenzi
7. Actualizează statusul unei comenzi (ex: IN_LIVRARE, FINALIZATA)
8. Adaugă o recenzie și recalculează ratingul restaurantului
9. Caută un client după adresa de email (aruncă excepție dacă nu există)
10. Șterge un client din sistem
11. *Extra:* Listează toate restaurantele ordonate descrescător după rating (Top).

## 1.2 Lista celor 8 tipuri de obiecte din domeniu
1. `User` (Clasă abstractă)
2. `Client` (Moștenește User)
3. `Driver` (Moștenește User)
4. `Address` (Clasă imutabilă - compoziție)
5. `Restaurant` (Implementează Comparable)
6. `MenuItem`
7. `Order`
8. `Review`