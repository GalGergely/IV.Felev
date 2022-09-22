-- Órai kötelező
SELECT D1.OAZON, AVG(D1.FIZETES) ATLAG_FIZ, O1.TELEPHELY
FROM DOLGOZO D1, OSZTALY O1
WHERE D1.OAZON=O1.OAZON
GROUP BY D1.OAZON, O1.TELEPHELY
ORDER BY D1.OAZON ASC;

--3. feladatsor
--15.Adjuk meg, hogy milyen napra esett KING belépési dátuma hónapjának első napja. (trunc fv. dátumra)
SELECT to_char (trunc(BELEPES, 'month'),'day')
FROM DOLGOZO
WHERE DNEV='KING';

-- random gyakorlas?! -- jelenlegi datum lekerese
SELECT SYSDATE FROM DUAL;

-- 4. feladatsor
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
SELECT * FROM OSZTALY
WHERE NVL(OAZON, 0) > ALL (SELECT DISTINCT NVL(OAZON, 0)

--16.Adjuk meg azon osztályok nevét és telephelyét, amelyeknek legalább 2 fő 1-es fizetési kategóriájú dolgozója van.


