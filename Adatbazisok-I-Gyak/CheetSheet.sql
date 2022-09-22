--cheet sheet
--SZERET
--1.  Melyek azok a gyümölcsök, amelyeket Micimackó szeret?
SELECT GYUMOLCS
FROM SZERET
WHERE NEV = 'Micimackó';
--π gyumolcs σ nev = 'Micimackó' Szeret


--2.  Melyek azok a gyümölcsök, amelyeket Micimackó nem szeret? (de valaki más igen)
SELECT DISTINCT GYUMOLCS
FROM SZERET
MINUS
SELECT GYUMOLCS
FROM SZERET
WHERE NEV = 'Micimackó';
--π gyumolcs (Szeret) - π gyumolcs σ nev = 'Micimackó' (Szeret)

--3.  Kik szeretik az almát?
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'alma';
--π nev σ gyumolcs = 'alma' Szeret

--4.  Kik nem szeretik a körtét? (de valami mást igen)
SELECT DISTINCT NEV
FROM SZERET
MINUS
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'körte';
--π nev Szeret - π nev σ gyumolcs = 'körte' Szeret

--5.  Kik szeretik vagy a dinnyét vagy a körtét?
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'körte'
UNION
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'dinnye';
--π nev σ gyumolcs='körte' Szeret ∪ π nev σ gyumolcs='dinnye' Szeret


--6.  Kik szeretik az almát is és a körtét is?
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'alma'
INTERSECT
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'körte';
--π nev σ gyumolcs='alma' Szeret ∩ π nev σ gyumolcs='körte' Szeret

--7.  Kik azok, akik szeretik az almát, de nem szeretik a körtét?
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'alma'
INTERSECT
(SELECT DISTINCT NEV
FROM SZERET
MINUS
SELECT NEV
FROM SZERET
WHERE GYUMOLCS = 'körte');
--π nev σ gyumolcs='alma' Szeret ∩ π nev Szeret - π nev σ gyumolcs = 'körte' Szeret

--8.  Kik szeretnek legalább kétféle gyümölcsöt?
select distinct s1.NEV
from SZERET s1, SZERET s2
where s1.NEV=s2.NEV
and s1.GYUMOLCS!=s2.GYUMOLCS;

--8.  Kik szeretnek legalább kétféle gyümölcsöt? - P2
SELECT NEV
FROM SZERET
GROUP BY NEV
HAVING COUNT(NEV)>=2;

--9.  Kik szeretnek legalább háromféle gyümölcsöt?
SELECT DISTINCT s1.NEV
FROM SZERET s1, SZERET s2, SZERET s3
WHERE s1.NEV = s2.NEV AND s1.NEV = s3.NEV AND s2.NEV = s3.NEV
AND s1.GYUMOLCS != s2.GYUMOLCS AND s1.GYUMOLCS != s3.GYUMOLCS AND s2.GYUMOLCS != s3.GYUMOLCS;

--9.  Kik szeretnek legalább háromféle gyümölcsöt? -P2
SELECT NEV
FROM SZERET
GROUP BY NEV
HAVING COUNT(NEV)>=3;

--10. Kik szeretnek legfeljebb kétféle gyümölcsöt?
(SELECT DISTINCT NEV
FROM SZERET)
MINUS
(SELECT DISTINCT s1.NEV
FROM SZERET s1, SZERET s2, SZERET s3
WHERE s1.NEV = s2.NEV AND s1.NEV = s3.NEV AND s2.NEV = s3.NEV
AND s1.GYUMOLCS != s2.GYUMOLCS AND s1.GYUMOLCS != s3.GYUMOLCS AND s2.GYUMOLCS != s3.GYUMOLCS);

--10. Kik szeretnek legfeljebb kétféle gyümölcsöt? -P2
SELECT NEV
FROM SZERET
GROUP BY NEV
HAVING COUNT(NEV)<=2;

--11. Kik szeretnek pontosan kétféle gyümölcsöt?
(SELECT DISTINCT NEV
FROM SZERET)
MINUS
(SELECT DISTINCT s1.NEV
FROM SZERET s1, SZERET s2, SZERET s3
WHERE s1.NEV = s2.NEV AND s1.NEV = s3.NEV AND s2.NEV = s3.NEV
AND s1.GYUMOLCS != s2.GYUMOLCS AND s1.GYUMOLCS != s3.GYUMOLCS AND s2.GYUMOLCS != s3.GYUMOLCS)
INTERSECT
select distinct s1.NEV
from SZERET s1, SZERET s2
where s1.NEV=s2.NEV
and s1.GYUMOLCS!=s2.GYUMOLCS;

--11. Kik szeretnek pontosan kétféle gyümölcsöt? -P2
SELECT NEV
FROM SZERET
GROUP BY NEV
HAVING COUNT(NEV)=2;

--13. Kik azok, akik legalább azokat a gyümölcsöket szeretik, mint Micimackó?
SELECT NEV FROM SZERET
MINUS
SELECT DISTINCT NEV FROM (SELECT * FROM
(SELECT DISTINCT NEV FROM SZERET) , (SELECT DISTINCT GYUMOLCS FROM SZERET WHERE NEV = 'Micimackó')
minus
SELECT * FROM SZERET WHERE NEV != 'Micimackó');


--13. Kik azok, akik legalább azokat a gyümölcsöket szeretik, mint Micimackó?
WITH
     MICIMACKO_GYUMOLCSEI(GYUMOLCS) AS (
        SELECT GYUMOLCS FROM SZERET WHERE NEV = 'Micimackó'),
     TMP(NEV, HANYAT) AS (
        SELECT DISTINCT NEV, COUNT(NEV) AS HANYAT_SZERET FROM SZERET, MICIMACKO_GYUMOLCSEI
        WHERE SZERET.GYUMOLCS = MICIMACKO_GYUMOLCSEI.GYUMOLCS GROUP BY NEV),
     HANYAT_SZERET_MICIMACKO(HANYAT) AS (
         SELECT COUNT(MICIMACKO_GYUMOLCSEI.GYUMOLCS) FROM MICIMACKO_GYUMOLCSEI
     )
SELECT DISTINCT NEV FROM HANYAT_SZERET_MICIMACKO, TMP WHERE TMP.HANYAT >= HANYAT_SZERET_MICIMACKO.HANYAT;

--14. Kik azok, akik legfeljebb azokat a gyümölcsöket szeretik, mint Micimackó?
WITH
     MICIMACKO_GYUMOLCSEI(GYUMOLCS) AS (
        SELECT GYUMOLCS FROM SZERET WHERE NEV = 'Micimackó')
    ,TMP(NEV, HANYAT) AS (
        SELECT DISTINCT NEV, COUNT(NEV) AS HANYAT_SZERET FROM SZERET, MICIMACKO_GYUMOLCSEI
        WHERE SZERET.GYUMOLCS = MICIMACKO_GYUMOLCSEI.GYUMOLCS GROUP BY NEV),
     HANYAT_SZERET_MICIMACKO(HANYAT) AS (
         SELECT COUNT(MICIMACKO_GYUMOLCSEI.GYUMOLCS) FROM MICIMACKO_GYUMOLCSEI
     )
SELECT DISTINCT NEV FROM HANYAT_SZERET_MICIMACKO, TMP WHERE TMP.HANYAT <= HANYAT_SZERET_MICIMACKO.HANYAT;
--15. Kik azok, akik pontosan azokat a gyümölcsöket szeretik, mint Micimackó?
WITH
     MICIMACKO_GYUMOLCSEI(GYUMOLCS) AS (
        SELECT GYUMOLCS FROM SZERET WHERE NEV = 'Micimackó')
    ,TMP(NEV, HANYAT) AS (
        SELECT DISTINCT NEV, COUNT(NEV) AS HANYAT_SZERET FROM SZERET, MICIMACKO_GYUMOLCSEI
        WHERE SZERET.GYUMOLCS = MICIMACKO_GYUMOLCSEI.GYUMOLCS GROUP BY NEV),
     HANYAT_SZERET_MICIMACKO(HANYAT) AS (
         SELECT COUNT(MICIMACKO_GYUMOLCSEI.GYUMOLCS) FROM MICIMACKO_GYUMOLCSEI
     )
SELECT DISTINCT NEV FROM HANYAT_SZERET_MICIMACKO, TMP WHERE TMP.HANYAT = HANYAT_SZERET_MICIMACKO.HANYAT;
--16. Melyek azok a (név,név) párok, akiknek legalább egy gyümölcsben eltér az ízlésük, azaz az  egyik szereti ezt a gyümölcsöt, a másik meg nem?
--17. Melyek azok a (név,név) párok, akiknek pontosan ugyanaz az ízlésük, azaz
--    pontosan  ugyanazokat a gyümölcsöket szeretik?
--18. SZERET(NEV, GYUMOLCS) tábla helyett EVETT(NEV, KG) legyen a relációséma
   -- és azt tartalmazza, hogy ki mennyi gyümölcsöt evett összesen.
   -- Ki ette a legtöbb gyümölcsöt?


--DOLGOZO
--1.  Kik azok a dolgozók, akiknek a fizetése nagyobb, mint 2800?
SELECT DNEV, FIZETES
FROM DOLGOZO
WHERE FIZETES > 2800;
--π dnev, fizetes σ fizetes > 2800 Dolgozo

--2.  Kik azok a dolgozók, akik a 10-es vagy a 20-as osztályon dolgoznak?
SELECT DNEV, OAZON
FROM DOLGOZO
WHERE OAZON = 10 OR OAZON = 20;
--π dnev, oazon σ oazon = 10 ∨ oazon = 20 Dolgozo

--3.  Kik azok, akiknek a jutaléka nagyobb, mint 600?
SELECT DNEV, JUTALEK
FROM DOLGOZO
WHERE JUTALEK > 600;
--π dnev, jutalek σ jutalek > 600 Dolgozo

--4.  Kik azok, akiknek a jutaléka nem nagyobb, mint 600?
SELECT DNEV, JUTALEK
FROM DOLGOZO
WHERE JUTALEK <= 600;
--π dnev, jutalek σ jutalek <= 600 Dolgozo

--5.  Kik azok a dolgozók, akiknek a jutaléka ismeretlen (nincs kitöltve, vagyis NULL)?
SELECT DNEV, JUTALEK
FROM DOLGOZO
WHERE JUTALEK IS NULL;
--π dnev, jutalek σ jutalek = null Dolgozo

--6.  Adjuk meg a dolgozók között előforduló foglalkozások neveit.
SELECT DISTINCT FOGLALKOZAS
FROM DOLGOZO;

--7.  Adjuk meg azoknak a nevét és kétszeres fizetését, akik a 10-es osztályon dolgoznak.
SELECT DNEV, FIZETES*2
FROM DOLGOZO
WHERE OAZON = 10;
--π dnev, fizetes*2 → f2 σ oazon = 10 Dolgozo

--8.  Kik azok a dolgozók, akik 1982.01.01 után léptek be a céghez?
SELECT DNEV -- ez nem mukodik
FROM DOLGOZO
WHERE CONVERT(DATE,'1982.01.01')>CONVERT(DATE,BELEPES);
--π dnev, belepes σ belepes > date('1982-01-01') Dolgozo

--9.  Kik azok, akiknek nincs főnöke?
SELECT DNEV
FROM DOLGOZO
WHERE FONOKE IS NULL;
--π dnev σ fonoke = null Dolgozo

--10. Kik azok a dolgozók, akiknek a nevében van 'A' betű?
SELECT DNEV
FROM DOLGOZO
WHERE DNEV LIKE '%A%';
--π dnev σ dnev like '%A%' Dolgozo


--11. Kik azok a dolgozók, akiknek a nevében van két 'L' betű?
SELECT DNEV
FROM DOLGOZO
WHERE DNEV LIKE '%l%l%';
--π dnev σ dnev like '%L%L%' Dolgozo

--12. Kik azok a dolgozók, akiknek a fizetése 2000 és 3000 között van?
SELECT DNEV, FIZETES
FROM DOLGOZO
WHERE FIZETES BETWEEN 2000 AND 2999;
--π dnev, fizetes σ 2000 <= fizetes AND fizetes <= 2999 Dolgozo

--13. Adjuk meg a dolgozók adatait fizetés szerint növekvő sorrendben.
SELECT *
FROM DOLGOZO
ORDER BY FIZETES ASC; -- ASC = NOVEKVO, DESC = CSOKKENO
--τ fizetes π dnev, fizetes Dolgozo

--14. Adjuk meg a dolgozók adatait fizetés szerint csökkenő, azon belül név szerinti sorrendben.
SELECT *
FROM DOLGOZO
ORDER BY FIZETES DESC, DNEV; -- ASC = NOVEKVO, DESC = CSOKKENO
--τ fizetes desc, dnev π dnev, fizetes Dolgozo

--15. Kik azok a dolgozók, akiknek a főnöke KING? (egyelőre leolvasva a képernyőről)
SELECT D1.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D2.DNEV = 'KING' AND D2.DKOD = D1.FONOKE;
--π d1.dnev σ d2.dnev = 'KING' AND d2.dkod = d1.fonoke (ρ d1 Dolgozo ⨯ ρ d2 Dolgozo)

--16. Kik azok a dolgozók, akiknek a főnöke KING? (nem leolvasva)
SELECT D1.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D2.DNEV = 'KING' AND D2.DKOD = D1.FONOKE;
--π d1.dnev σ d2.dnev = 'KING' AND d2.dkod = d1.fonoke (ρ d1 Dolgozo ⨯ ρ d2 Dolgozo)

--17. Adjuk meg azoknak a főnököknek a nevét, akiknek a foglalkozása nem 'MANAGER'. (dnev)
SELECT DISTINCT D2.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D2.FOGLALKOZAS != 'MANAGER' AND D2.DKOD = D1.FONOKE;

--18. Adjuk meg azokat a dolgozókat, akik többet keresnek a főnöküknél.
SELECT DISTINCT D1.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.FIZETES > D2.FIZETES AND D2.DKOD = D1.FONOKE;

--19. Kik azok a dolgozók, akik főnökének a főnöke KING?
SELECT DISTINCT D1.DNEV
FROM DOLGOZO D1, DOLGOZO D2, DOLGOZO D3
WHERE D3.DNEV = 'KING' AND D2.DKOD = D1.FONOKE AND D2.FONOKE = D3.DKOD;


--20. Kik azok a dolgozók, akik osztályának telephelye DALLAS vagy CHICAGO?
SELECT DNEV , OAZON
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE TELEPHELY = 'DALLAS' OR TELEPHELY = 'CHICAGO';


--21. Kik azok a dolgozók, akik osztályának telephelye nem DALLAS és nem CHICAGO?
SELECT DNEV , OAZON
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE TELEPHELY != 'DALLAS' AND TELEPHELY != 'CHICAGO';

--22. Adjuk meg azoknak a nevét, akiknek a fizetése > 2000 vagy a CHICAGO-i osztályon dolgoznak.
SELECT DNEV
FROM DOLGOZO
WHERE FIZETES > 2000
UNION
SELECT DNEV
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE TELEPHELY = 'CHICAGO';


--23. Melyik osztálynak nincs dolgozója?
SELECT DISTINCT OAZON
FROM OSZTALY
MINUS
SELECT DISTINCT OAZON
FROM DOLGOZO;

--24. Adjuk meg azokat a dolgozókat, akiknek van 2000-nél nagyobb fizetésű beosztottja.
SELECT DISTINCT D2.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.FONOKE = D2.DKOD AND D1.FIZETES > 2000;

--25. Adjuk meg azokat a dolgozókat, akiknek nincs 2000-nél nagyobb fizetésű beosztottja.
SELECT DNEV
FROM DOLGOZO
MINUS
SELECT D2.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.FONOKE = D2.DKOD AND D1.FIZETES > 2000;

--26. Adjuk meg azokat a telephelyeket, ahol van elemző (ANALYST) foglalkozású dolgozó.
SELECT DISTINCT TELEPHELY
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE FOGLALKOZAS = 'ANALYST';

--27. Adjuk meg azokat a telephelyeket, ahol nincs elemző (ANALYST) foglalkozású dolgozó.
SELECT DISTINCT TELEPHELY
FROM OSZTALY
MINUS
SELECT DISTINCT TELEPHELY
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE FOGLALKOZAS = 'ANALYST';
--28. Adjuk meg azoknak a dolgozóknak a nevét, akiknek a legnagyobb a fizetésük.
WITH MAXFIZETES(FIZETES) AS
(SELECT MAX(FIZETES)
FROM DOLGOZO)
SELECT D1.DNEV
FROM DOLGOZO D1 , MAXFIZETES M1
WHERE D1.FIZETES = M1.FIZETES;

--28. Adjuk meg azoknak a dolgozóknak a nevét, akiknek a legnagyobb a fizetésük. -P2
SELECT dnev FROM Dolgozo
MINUS
SELECT d1.dnev
FROM Dolgozo d1, Dolgozo d2
WHERE d1.fizetes < d2.fizetes;

-- ITT VALAMIERT UJRA INUL A SZAMOZAS XDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
--1.  Adjuk meg azon dolgozókat, akik fizetése osztható 15-tel.
SELECT FIZETES
FROM DOLGOZO
WHERE MOD(FIZETES,15) = 0;
--2.  Kik azok a dolgozók, akik 1982.01.01 után léptek be a céghez?
--   (Aktuális dátumformátumot lásd -> SYSDATE fv.)


--3.  Adjuk meg azon dolgozókat, akik nevének második betűje 'A'. (használjuk a substr függvényt)
SELECT DNEV
FROM DOLGOZO
WHERE SUBSTR(DNEV, 2, 1) = 'A';

--4.  Adjuk meg azon dolgozókat, akik nevében van legalább két 'L' betű. (használjuk az instr függvényt)
SELECT DNEV
FROM DOLGOZO
WHERE INSTR(DNEV,'LL') != 0;

--5.  Adjuk meg a dolgozók nevének utolsó három betűjét. (substr fv.)
SELECT SUBSTR(DNEV, -3)
FROM DOLGOZO;


--6.  Adjuk meg azon dolgozókat, akik nevének utolsó előtti betűje 'T'. (substr fv.)
SELECT DNEV
FROM DOLGOZO
WHERE SUBSTR(DNEV, -2, 1) = 'T';

--7.  Adjuk meg a dolgozók fizetéseinek négyzetgyökét két tizedesre, és ennek egészrészét. (round, sqrt, trunc fv-ek)
SELECT FIZETES, ROUND(SQRT(FIZETES), 2) AS BENCE, ROUND(SQRT(FIZETES), 0) AS HISZTIZIK
FROM DOLGOZO;

--8.  Adjuk meg, hogy hány napja dolgozik a cégnél ADAMS és milyen hónapban lépett be. (dátumaritmetika + dátum függvények)


--9.  Adjuk meg azokat a dolgozókat, akik keddi napon léptek be. (to_char fv.)
--   (Vigyázzunk a visszaadott értékkel, és annak hosszával!)

--10. Adjuk meg azokat a (név, főnök) párokat, ahol a két ember neve ugyanannyi betűből áll. (length fv.)
SELECT D1.DNEV, D2.DNEV
FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.FONOKE = D2.DKOD AND LENGTH(D1.DNEV) = LENGTH(D2.DNEV);

--11. Adjuk meg azon dolgozókat, akik az 1-es fizetési kategóriába tartoznak.
SELECT DNEV, FIZETES
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND KATEGORIA = 1;

--12. Adjuk meg azon dolgozókat, akiknek a fizetési kategóriája páros szám. (mod() függvény)
SELECT DNEV, KATEGORIA
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND MOD(KATEGORIA ,2) = 0;

--13. Adjuk meg, hogy hány nap volt KING és JONES belépési dátuma között?


--14. Adjuk meg, hogy milyen napra esett KING belépési dátuma hónapjának utolsó napja. (last_day() függvény)


--15. Adjuk meg, hogy milyen napra esett KING belépési dátuma hónapjának első napja. (trunc fv. dátumra)


--16. Adjuk meg azon dolgozók nevét, akik osztályának nevében van 'C' betű és fizetési kategóriájuk >=4.
SELECT DNEV
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE INSTR(ONEV,'C') != 0
INTERSECT
SELECT DNEV
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND KATEGORIA  >= 4;

--4. gyakorlati feladat a szamozas ismet ujra indul az isten tudja miert
--1.Mekkora a maximális fizetés a dolgozók között?
SELECT MAX(FIZETES)
FROM DOLGOZO;

--2.Mennyi a dolgozók összfizetése?
SELECT SUM(FIZETES)
FROM DOLGOZO;

--3.Mennyi a 20-as osztályon az összfizetes és az átlagfizetés? (Atlag, Össz)
SELECT SUM(FIZETES) OSSZ, AVG(FIZETES) ATLAG
FROM DOLGOZO
WHERE OAZON=10;

--4.Adjuk meg, hogy hány különböző foglalkozás fordul elő a dolgozók között.
SELECT COUNT(DISTINCT FOGLALKOZAS) FOGLALKOZASOK_SZAMA
FROM DOLGOZO;

--5.Hány olyan dolgozó van, akinek a fizetése > 2000?
SELECT COUNT(DKOD) OVER2000
FROM DOLGOZO
WHERE FIZETES>2000;

--6.Adjuk meg osztályonként az átlagfizetést (oazon, atl_fiz).
SELECT OAZON OSZTALYAZON, AVG(FIZETES) ATLAGFIZETES
FROM DOLGOZO
GROUP BY OAZON;

--7.Adjuk meg osztályonként a telephelyet és az átlagfizetést (oazon, telephely, atl_fiz).
SELECT D1.OAZON, O1.TELEPHELY, AVG(D1.FIZETES) ATLAG_FIZ
FROM DOLGOZO D1, OSZTALY O1
WHERE D1.OAZON=O1.OAZON
GROUP BY D1.OAZON, O1.TELEPHELY;

--7. NATURAL JOINNAL?!
SELECT DISTINCT OAZON, TELEPHELY, AVG(FIZETES) ATLGA
FROM DOLGOZO NATURAL JOIN OSZTALY
GROUP BY OAZON, TELEPHELY;

--8.Adjuk meg, hogy az egyes osztályokon hány ember dolgozik. (oazon, mennyi)
SELECT OAZON, COUNT(DKOD) MENNYI
FROM DOLGOZO
GROUP BY OAZON;

--9.Adjuk meg azokra az osztályokra az átlagfizetést, ahol ez nagyobb mint 2000. (oazon, atlag)
SELECT OAZON, AVG(FIZETES) ATLAG
FROM DOLGOZO
HAVING AVG(FIZETES)>2000
GROUP BY OAZON;

--10.Adjuk meg az átlagfizetést azokon az osztályokon, ahol legalább 4-en dolgoznak (oazon, atlag)
SELECT OAZON, AVG(FIZETES) ATLAG, COUNT(DKOD) DOLGOZOK_SZAMA
FROM DOLGOZO
HAVING COUNT(DKOD) > 4
GROUP BY OAZON;

--11.Adjuk meg az átlagfizetést és telephelyet azokon az osztályokon, ahol legalább 4-en dolgoznak. (oazon, telephely, atlag)
SELECT AVG(FIZETES) ATLAG, TELEPHELY, COUNT(DKOD) DOLGOZOK_SZAMA, OAZON
FROM DOLGOZO NATURAL JOIN OSZTALY
HAVING COUNT(DKOD) > 4
GROUP BY OAZON, TELEPHELY;

--12.Adjuk meg azon osztályok nevét és telephelyét, ahol az átlagfizetés nagyobb mint 2000. (onev, telephely)
SELECT ONEV, TELEPHELY, AVG(FIZETES)
FROM DOLGOZO NATURAL JOIN OSZTALY
HAVING AVG(FIZETES) > 2000
GROUP BY ONEV, TELEPHELY;

--13.Adjuk meg azokat a fizetési kategóriákat, amelybe pontosan 3 dolgozó fizetése esik.
SELECT KATEGORIA ,COUNT(DKOD)
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE FIZETES BETWEEN ALSO AND FELSO
HAVING COUNT(DKOD)=3
GROUP BY KATEGORIA
ORDER BY KATEGORIA;

--14.Adjuk meg azokat a fizetési kategóriákat, amelyekbe eső dolgozók mindannyian ugyanazon az osztályon dolgoznak. (kategoria)
SELECT KATEGORIA ,COUNT(DKOD)
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE FIZETES BETWEEN ALSO AND FELSO
GROUP BY KATEGORIA
ORDER BY KATEGORIA;

--15.Adjuk meg azon osztályok nevét és telephelyét, amelyeknek van 1-es fizetési kategóriájú dolgozója. (onev, telephely)
SELECT DISTINCT TELEPHELY
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE OAZON IN (SELECT OAZON
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND KATEGORIA = 1
GROUP BY OAZON);
--ALLEKERDEZES
SELECT OAZON
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND KATEGORIA = 1
GROUP BY OAZON;

--16.Adjuk meg azon osztályok nevét és telephelyét, amelyeknek legalább 2 fő 1-es fizetési kategóriájú dolgozója van.\
SELECT DISTINCT TELEPHELY , ONEV
FROM DOLGOZO NATURAL JOIN OSZTALY
WHERE OAZON IN (SELECT OAZON
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND KATEGORIA = 1
HAVING COUNT(DNEV)>=2
GROUP BY OAZON);

--17. Készítsünk listát a páros és páratlan azonosítójú (dkod) dolgozók számáról. (paritás, szám)
WITH QARRY1 AS (SELECT COUNT(DKOD) AS PAROS FROM DOLGOZO WHERE MOD(DKOD, 2) = 0),
QARRY2 AS (SELECT COUNT(DKOD) AS PARATLAN FROM DOLGOZO WHERE MOD(DKOD, 2) =1)
SELECT PAROS, PARATLAN
FROM QARRY1 NATURAL JOIN QARRY2;

--18. Listázzuk ki foglalkozásonként a dolgozók számát, átlagfizetését (kerekítve) numerikusan és grafikusan is. 200-anként jelenítsünk meg egy '#'-ot. (foglalkozás, szám, átlag, grafika)
SELECT FOGLALKOZAS, COUNT(DKOD), ROUND(AVG(FIZETES),0), rpad('#', round(avg(fizetes)/200), '#')
FROM DOLGOZO
GROUP BY FOGLALKOZAS;

-- proba zh
--5.FELADAT
WITH MAX_FIZ AS (SELECT OAZON, MAX(FIZETES) AS MAXFIZ
FROM DOLGOZO
group by OAZON)
SELECT DNEV, OAZON, FIZETES
FROM DOLGOZO NATURAL JOIN MAX_FIZ
WHERE FIZETES=MAXFIZ;

--6.FELADAT
SELECT S1.NEV
FROM SZERET S1
HAVING COUNT(S1.NEV) = (SELECT COUNT( DISTINCT GYUMOLCS) AS GYUMOLCSOKSZAMA
FROM SZERET)
GROUP BY S1.NEV;

--7.FELADAT
WITH MINIMUMFONOKFIZETES AS (SELECT MIN(D1.FIZETES) AS MINFIZ
FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.DKOD = D2.FONOKE)
SELECT DNEV, FIZETES, KATEGORIA
FROM DOLGOZO NATURAL JOIN FIZ_KATEGORIA , MINIMUMFONOKFIZETES
WHERE (FIZETES BETWEEN ALSO AND FELSO) AND FIZETES = MINFIZ;

--8.FELADAT
WITH ASD AS (SELECT DISTINCT FOGLALKOZAS, ONEV
FROM DOLGOZO NATURAL JOIN OSZTALY
group by ONEV, FOGLALKOZAS)
SELECT FOGLALKOZAS
FROM ASD
HAVING COUNT(FOGLALKOZAS)=1
group by FOGLALKOZAS;

--9.FELADAT
--oazon, oszt_atlag),
WITH ATLAGFIZ_OSZT AS(SELECT OAZON, AVG(FIZETES) AS OSZT_ATLAG
FROM DOLGOZO
group by OAZON),
ATLAG_FIZ AS (SELECT AVG(FIZETES) AS ATLAG FROM DOLGOZO)
SELECT DISTINCT ONEV, OSZT_ATLAG, ATLAG, OSZT_ATLAG-ATLAG AS KULOMBSEG
FROM DOLGOZO D1, OSZTALY O1,ATLAGFIZ_OSZT A1, ATLAG_FIZ A2
WHERE D1.OAZON= O1.OAZON AND D1.OAZON=A1.OAZON;











