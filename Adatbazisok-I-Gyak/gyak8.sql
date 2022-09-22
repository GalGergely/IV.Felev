----------------------8.Gyakoralt
--1.Feladat
--Írjunk meg egy függvényt, amelyik eldönti egy számról, hogy prím-e. igen/nem -> 1/0
CREATE OR REPLACE FUNCTION prim(n integer) RETURN number IS
begin
    declare
        i       number;
        isprime number;
    begin
        i := 2;
        isprime := 1;
        for i in 2..n / 2
            loop
                if mod(n, i) = 0 then
                    isprime := 0;
                    exit;
                end if;
            end loop;
        return isprime;
    end;
end;
select prim(11)
from dual;

--2.Fealdat
/* Írjunk meg egy procedúrát, amelyik kiírja az n-edik Fibonacchi számot
   fib_1 = 0, fib_2 = 1, fib_3 = 1, fib_4 = 2 ... fib_i = a megelőző kettő összege */

CREATE OR REPLACE PROCEDURE fib(n integer) IS
begin
    declare
        fib_1    number;
        fib_2    number;
        fib_next number;
    begin
        fib_1 := 0;
        fib_2 := 1;
        fib_next := fib_1 + fib_2;
        IF N = 1 THEN
            dbms_output.put_line('1.: ' || 0);
        ELSIF N = 2 THEN
            dbms_output.put_line('1.: ' || 0);
            dbms_output.put_line('2.: ' || 1);
        ELSE
            dbms_output.put_line('1.: ' || 0);
            dbms_output.put_line('2.: ' || 1);
            FOR I IN 3..N
                LOOP
                    dbms_output.put_line(I || '.: ' || fib_next);
                    fib_1 := fib_2;
                    fib_2 := fib_next;
                    fib_next := fib_1 + fib_2;
                END LOOP;
        END IF;
    end;
end;

begin
    fib(10);
end;

--3.Feladat
/* Írjunk meg egy függvényt, amelyik visszaadja két szám legnagyobb közös osztóját */
CREATE OR REPLACE FUNCTION lnko(p1 integer, p2 integer) RETURN number IS
begin
    declare
        lnko number;
    begin
        if p1 > p2 then
            for i in REVERSE p2..2
                loop
                    dbms_output.put_line(i);
                    if p1 mod i = 0 then
                        if p2 mod i = 0 then
                            lnko := i;
                            exit;
                        end if;
                    end if;
                end loop;
            return lnko;
        else
            dbms_output.put_line('asd');
            for i in REVERSE p1..2
                loop
                    dbms_output.put_line(i);
                    if mod(p1, i) = 0 then
                        if mod(p2, i) = 0 then
                            lnko := i;
                        end if;
                    end if;
                end loop;
            return lnko;
        end if;
    end;
end;

SELECT lnko(3570, 7293)
FROM dual;

--4.Feladat
-- Írjunk meg egy függvényt, amelyik visszaadja n faktoriálisát
CREATE OR REPLACE FUNCTION faktor(n integer) RETURN integer IS
begin
    declare
        summering number;
    begin
        summering := 1;
        for i in 1..n
            loop
                summering := summering * i;
            end loop;
        return summering;
    end;
end;

SELECT faktor(10)
FROM dual;

--5.Feladat
CREATE OR REPLACE FUNCTION hanyszor(P1 VARCHAR2, P2 VARCHAR2) RETURN INTEGER IS
BEGIN
    DECLARE
        STR       VARCHAR2(200);
        J         NUMBER;
        COUNTER   NUMBER;
        TYPE VARCHAR_ARRAY IS VARRAY(20) OF VARCHAR2(20);
        STR_ARRAY VARCHAR_ARRAY := VARCHAR_ARRAY();
    BEGIN
        J := 1;
        STR := '';
        FOR I IN 1..LENGTH(P1)
            LOOP
                IF SUBSTR(P1, I, 1) != ' ' THEN
                    STR := STR || SUBSTR(P1, I, 1);
                ELSE
                    STR_ARRAY.extend();
                    STR_ARRAY(J) := STR;
                    J := J + 1;
                    STR := '';
                END IF;
            END LOOP;
        COUNTER := 0;
        FOR I IN 1..STR_ARRAY.COUNT
            LOOP
                IF STR_ARRAY(I) = P2 THEN
                    COUNTER := COUNTER + 1;
                END IF;
            END LOOP;
        RETURN COUNTER;
    END;
END;

SELECT hanyszor('ab c ab ab de ab fg', 'ab')
FROM dual;

-- es mi lenne akkor ha "abcababdeabfg" ben kellene megtalalni minden ab-t
CREATE OR REPLACE FUNCTION hanyszor2(P1 VARCHAR2, P2 VARCHAR2) RETURN INTEGER IS
BEGIN
    DECLARE
        COUNTER NUMBER;
    BEGIN
        COUNTER := 0;
        FOR I IN 1..LENGTH(P1)
            LOOP
                if SUBSTR(P1, I, LENGTH(P2)) = p2 then
                    counter := counter + 1;
                end if;
            end loop;
        return counter;
    END;
END;

SELECT hanyszor('ab c ab ab de ab fg', 'ab')
FROM dual;

--6.Fealdat
/* Írjunk meg egy függvényt, amelyik visszaadja a paraméterként szereplő '+'-szal
   elválasztott számok összegét.*/
CREATE OR REPLACE FUNCTION osszeg(p_char VARCHAR2) RETURN number IS
begin
    declare
        Summary          number;
        NumberInString   VARCHAR2(200);
        IterationCounter number;
        p_char2          VARCHAR2(200);
        NumberOfNumbers  number;

    begin
        p_char2 := p_char;
        NumberInString := '';
        Summary := 0;
        IterationCounter := 1;
        NumberOfNumbers := length(p_char2) - length(replace(p_char2, '+', null));
        FOR j in 1..NumberOfNumbers
            loop
                dbms_output.put_line(j);
                for i in IterationCounter..INSTR(p_char2, '+') - 2
                    loop
                        dbms_output.put_line(i);
                        NumberInString := NumberInString || SUBSTR(p_char2, IterationCounter, I);
                        IterationCounter := IterationCounter + 1;
                    end loop;
                p_char2 := SUBSTR(p_char2, IterationCounter + 3, LENGTH(p_char2) - IterationCounter - 2);
                Summary := Summary + TO_NUMBER(NumberInString);
                IterationCounter := 1;
                NumberInString := '';
                dbms_output.put_line(Summary);
            end loop;
        RETURN Summary;
    end;
end;

SELECT osszeg('1 + 4 + 13 + -1 + 0')
FROM dual;
