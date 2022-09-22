## :fire: Alap koncepció
Az alap játék egy Tower Defense multiplayer játék, amiben kettő kastély van és a játékosok célja, hogy egymás kastélyát elfoglalják/lerombolják. Az nyer, aki hamarabb lerombolja a másik kastélyát. A játék lehet döntetlen is, ha mindkét játékos ugyan abban a körben rombolja le a másik kastélyát. A játéknak van UI-a (User Interface).

## :arrows_counterclockwise: Körök
Egy kör kettő részből áll, a kör egyik felében védekeznek/építenek a játékosok, a második felében támadnak egymásra.

## :tent: Játék terület
A játékterület egy N x M -es pálya, amin valahol elhelyezkedik a két játékos kastélya, amit védeniük kell. Továbbá vannak a pályán terepi akadályok, mint például hegyek, folyók, tavak stb., amelyek nehezítik a játokosok feladatát, ugyanis a támadó egységek nem tudnak rajtuk átmenni. Ezek a terepi akadályok nem kerülhetnek a kastélyok köré.

## :moneybag: Arany – Fizető eszköz
Az arany a játékban a fizetésre felhasznált eszköz. Ezzel lehet venni védekezésre tornyokat és támadásra egységeket. Egy játékos többféleképpen tud aranyat szerezni. Minden kör elején kap mindkettő játékos egy fix összeget, minél több egysége van lent, illetve minél több egységet pusztít el, annál többet kap.

## :tokyo_tower: Tornyok
A tornyok a játékosok védekezési eszközei. Egy tornyot aranyért tud venni minden játékos és nem zárhatja el vele a kastélyhoz vezető utat. (Minimum) 3 féle tornyot tudnak lerakni a játékosok. Minden toronynak más sebzési sebessége van, más az is, hogy mennyit tud sebezni és hogy milyen messze lát el egy torony.

## :bomb: Támadási egységek
A támadási egységekkel tudnak támadni a játékosok. Szintén aranyért tudnak venni ilyen egységeket. Az egységek a pályán automatikusan mozog az ellenséges kastély felé, a lehető legrövidebb úton és a lerakott tornyokat nem támadják meg. Egyszerre egy egység állhat egy pálya helyen, de állhat egy ellenséges egység mellé. Ha rákattintunk bármely egységre, látszódik,
hogy mennyin vannak az adott egységbe. Ha kastély mezőre lép egy egység, akkor megsebzi a kastélyt és a kastély is megsebzi ez egységet, egyszerre.

## :heavy_plus_sign: :bangbang: Extrák
- A tornyokat lehet fejleszteni :arrow_double_up:
- A tornyokat lehet lebontani, ekkor kicsit kevesebb aranyat kap vissza a játékos, mint amennyiért építette a tornyot. :money_with_wings:
- Mentési lehetőség :floppy_disk:
- Navigálható egységek – Ki lehet jelölni, hogy egy adott egység merre menjen a pályán :point_right: :map: 
- Térképszerkesztő: – A felhasználók tudnak saját térképet összeállítani és azon játszani, ezt el is lehet menteni. :map: :pencil2: 
- Barakkok - Itt is megjelenhetnek új egységek, 1 – 5 körig nincsenek barakkok. Fokozatosan nő a számuk a körök haladtával. Nem kerülhet olyan helyre, ami elzárja a kastélyhoz vezető utat :circus_tent: 
- Speciális egységek: 
  - Hegymászók – Át tud menni a pálya „hegy” elemein. :mountain: :runner_tone2: 
  - Úszók – Át tud úszni a pálya „folyó” és „tó” elemein. :swimmer: 
  - Harcosok – Ha olyan mezőre lép, ahol ellenség van, akkor megtámadja azt. :dagger: 
  - Rombolók – Meg tud sebezni/Le tud rombolni tornyokat. :boom: 
