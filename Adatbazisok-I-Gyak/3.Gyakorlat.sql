-- Masolas
CREATE TABLE DOLGOZO AS SELECT * FROM VZOLI.DOLGOZO;

-- ORAI
select d1.DNEV Beosztott, d2.dnev Kozvetlen_Fonoke
from dolgozo d1, dolgozo d2
where d1.FONOKE = d2.DKOD
and length(d1.dnev) = length(d2.DNEV);

-- 1.  Kik azok a dolgozók, akiknek a fizetése nagyobb, mint 2800?
SELECT DNEV, FIZETES FROM DOLGOZO
WHERE FIZETES > 2800;

-- 2.  Kik azok a dolgozók, akik a 10-es vagy a 20-as osztályon dolgoznak?
SELECT DNEV, OAZON FROM DOLGOZO
WHERE OAZON = 10 OR OAZON = 20;

-- 3.  Kik azok, akiknek a jutaléka nagyobb, mint 600?
SELECT DNEV, DKOD, JUTALEK FROM DOLGOZO
WHERE JUTALEK > 600;

-- 4.  Kik azok, akiknek a jutaléka nem nagyobb, mint 600?
SELECT DNEV, DKOD, JUTALEK FROM DOLGOZO
WHERE JUTALEK <= 600;

-- 5.  Kik azok a dolgozók, akiknek a jutaléka ismeretlen (nincs kitöltve, vagyis NULL)?
SELECT DNEV, DKOD, JUTALEK FROM DOLGOZO
MINUS
(SELECT DNEV, DKOD, JUTALEK FROM DOLGOZO
WHERE JUTALEK > 600
UNION
SELECT DNEV, DKOD, JUTALEK FROM DOLGOZO
WHERE JUTALEK <= 600);

-- 6.  Adjuk meg a dolgozók között előforduló foglalkozások neveit.
SELECT DISTINCT FOGLALKOZAS FROM DOLGOZO;

-- 7.  Adjuk meg azoknak a nevét és kétszeres fizetését, akik a 10-es osztályon dolgoznak.
SELECT DNEV, FIZETES*2, OAZON FROM DOLGOZO
WHERE OAZON = 10;

-- 8.  Kik azok a dolgozók, akik 1982.01.01 után léptek be a céghez?
SELECT DNEV, DKOD, BELEPES FROM DOLGOZO
WHERE BELEPES >= TO_DATE('1982.01.01','yyyy.mm.dd');

-- 9.  Kik azok, akiknek nincs főnöke?
SELECT DNEV, FOGLALKOZAS FROM DOLGOZO
WHERE FONOKE IS NULL;

-- 10. Kik azok a dolgozók, akiknek a nevében van 'A' betű?
SELECT DNEV FROM DOLGOZO
WHERE UPPER(DNEV) LIKE '%A%';

-- 11. Kik azok a dolgozók, akiknek a nevében van két 'L' betű?
SELECT DNEV FROM DOLGOZO
WHERE UPPER(DNEV) LIKE '%L%L%';

-- 12. Kik azok a dolgozók, akiknek a fizetése 2000 és 3000 között van?
(SELECT DNEV, FIZETES FROM DOLGOZO
WHERE FIZETES < 3000)
INTERSECT
(SELECT DNEV, FIZETES FROM DOLGOZO
WHERE FIZETES > 2000);

-- 13. Adjuk meg a dolgozók adatait fizetés szerint növekvő sorrendben.
SELECT DNEV, FIZETES FROM DOLGOZO
ORDER BY FIZETES ASC;

-- 14. Adjuk meg a dolgozók adatait fizetés szerint csökkenő, azon belül név szerinti sorrendben.
SELECT DNEV, FIZETES FROM DOLGOZO
ORDER BY FIZETES DESC, DNEV ASC;

-- 15. Kik azok a dolgozók, akiknek a főnöke KING? (egyelőre leolvasva a képernyőről)
SELECT D1.DNEV FROM DOLGOZO D1, DOLGOZO D2
WHERE D1.FONOKE = D2.DKOD AND D2.DNEV = 'KING';

-- Masolas FIZETES
CREATE TABLE Fiz_kategoria  AS SELECT * FROM VZOLI.Fiz_kategoria ;

-- Fogalmam sincs ez mi a rák
SELECT DNEV, KATEGORIA FROM DOLGOZO, FIZ_KATEGORIA
WHERE FIZETES BETWEEN ALSO AND FELSO;

