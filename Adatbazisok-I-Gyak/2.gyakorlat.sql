--1.  Melyek azok a gyümölcsök, amelyeket Micimackó szeret?
--2.  Melyek azok a gyümölcsök, amelyeket Micimackó nem szeret? (de valaki más igen)
--3.  Kik szeretik az almát?
--4.  Kik nem szeretik a körtét? (de valami mást igen)
--5.  Kik szeretik vagy a dinnyét vagy a körtét?
--6.  Kik szeretik az almát is és a epret is?
--7.  Kik azok, akik szeretik az almát, de nem szeretik a körtét?
------------- eddig volt korábban, lásd feladat1.txt
--8.  Kik szeretnek legalább kétféle gyümölcsöt?
--9.  Kik szeretnek legalább háromféle gyümölcsöt?
--10. Kik szeretnek legfeljebb kétféle gyümölcsöt?
--11. Kik szeretnek pontosan kétféle gyümölcsöt?

--1.Feladat
select distinct gyumolcs --distinct az azt csinalja hogy mindent csak egyszer ad vissza. nincs duplikacio
from SZERET
where nev='Micimackó';

--2.Feladat
select distinct gyumolcs
from SZERET
minus
select distinct gyumolcs --distinct az azt csinalja hogy mindent csak egyszer ad vissza. nincs duplikacio
from SZERET
where nev='Micimackó';

--3.Feladat
select distinct nev
from SZERET
where gyumolcs='alma';

--4.Feladat
select distinct nev
from SZERET
minus
select distinct nev
from SZERET
where gyumolcs='körte';

--5.feladat
select distinct nev
from SZERET
where gyumolcs='dinnye' or gyumolcs='körte';

--6.feladat
(select distinct nev
from SZERET
where gyumolcs='alma')
INTERSECT
(select distinct nev
from SZERET
where gyumolcs='körte');

--7.feladat
(select distinct nev
from SZERET
where gyumolcs='alma')
INTERSECT
(select distinct nev
from SZERET
minus
select distinct nev
from SZERET
where gyumolcs='eper');

--8.feladat
select distinct s1.NEV
from SZERET s1, SZERET s2
where s1.NEV=s2.NEV
and s1.GYUMOLCS!=s2.GYUMOLCS;

--9.feladat
SELECT DISTINCT s1.NEV
FROM SZERET s1, SZERET s2, SZERET s3
WHERE s1.NEV = s2.NEV AND s1.NEV = s3.NEV AND s2.NEV = s3.NEV
AND s1.GYUMOLCS != s2.GYUMOLCS AND s1.GYUMOLCS != s3.GYUMOLCS AND s2.GYUMOLCS != s3.GYUMOLCS;

--10.feladat
(SELECT DISTINCT NEV
FROM SZERET)
MINUS
(SELECT DISTINCT s1.NEV
FROM SZERET s1, SZERET s2, SZERET s3
WHERE s1.NEV = s2.NEV AND s1.NEV = s3.NEV AND s2.NEV = s3.NEV
AND s1.GYUMOLCS != s2.GYUMOLCS AND s1.GYUMOLCS != s3.GYUMOLCS AND s2.GYUMOLCS != s3.GYUMOLCS);

--11.feladat


--Orai feladat
(select distinct nev
from SZERET
where gyumolcs='alma')
INTERSECT
(select distinct nev
from SZERET
where gyumolcs='körte');







