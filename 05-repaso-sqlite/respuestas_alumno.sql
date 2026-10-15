-- Completa cada ejercicio con UNA consulta SELECT (o WITH ... SELECT).
-- No borres las líneas: -- EJERCICIO NN
-- Termina cada consulta con punto y coma.

-- EJERCICIO 01
-- Mostrar los cinco primeros productos por identificador.
SELECT id, nombre, precio FROM productos ORDER BY id LIMIT 5;

-- EJERCICIO 02
-- Ver los cinco vehículos más recientes.
SELECT matricula, marca, modelo, anio FROM vehiculos ORDER BY anio DESC LIMIT 5;

-- EJERCICIO 03
-- Filtrar productos cuyo precio sea mayor de 100.
SELECT id, nombre, precio FROM productos WHERE precio > 100.0 ORDER BY precio DESC LIMIT 8;

-- EJERCICIO 04
-- Localizar vehículos de la marca Toyota.
SELECT matricula, marca, modelo, anio FROM vehiculos WHERE marca LIKE 'Toyota' ORDER BY anio DESC LIMIT 8;

-- EJERCICIO 05
-- Buscar productos cuyo nombre contenga Pro.
SELECT * FROM productos WHERE nombre LIKE '%Pro%' ORDER BY id ASC LIMIT 8;

-- EJERCICIO 06
-- Mostrar productos con precio comprendido entre 20 y 50.
SELECT * FROM productos WHERE precio >= 20 AND precio <= 50 ORDER BY precio ASC LIMIT 8;

-- EJERCICIO 07
-- Listar marcas diferentes sin repetir.
SELECT DISTINCT marca FROM vehiculos ORDER BY marca ASC;

-- EJERCICIO 08
-- Seleccionar vehículos de dos marcas concretas.
SELECT * FROM vehiculos WHERE marca IN ('Ford', 'Toyota') ORDER BY marca ASC, anio DESC LIMIT 8;

-- EJERCICIO 09
-- Contar cuántos productos existen.
SELECT COUNT(*) FROM productos AS 'total_productos';

-- EJERCICIO 10
-- Contar cuántos vehículos están registrados.
SELECT COUNT(*) FROM vehiculos AS 'total_vehiculos';

-- EJERCICIO 11
-- Obtener precio mínimo, máximo y medio.
SELECT MIN(precio) AS 'minimo', MAX(precio) AS 'maximo', ROUND(AVG(precio), 2) AS 'media' FROM productos;

-- EJERCICIO 12
-- Contar las marcas diferentes.
SELECT COUNT(DISTINCT marca) AS 'marcas_diferentes' FROM vehiculos;

-- EJERCICIO 13
-- Calcular la suma de todos los precios del catálogo.
SELECT ROUND(SUM(precio), 2) AS 'suma_precios' FROM productos;

-- EJERCICIO 14
-- Contar productos con precio de al menos 50.
SELECT COUNT(*) AS 'productos_caros' FROM productos WHERE precio >= 50;

-- EJERCICIO 15
-- Contar vehículos de cada marca.
SELECT marca, COUNT(*) AS 'cantidad' FROM vehiculos GROUP BY marca ORDER BY marca ASC;

-- EJERCICIO 16
-- Encontrar los años de fabricación con más vehículos.
SELECT anio, COUNT(*) AS 'cantidad' FROM vehiculos GROUP BY anio ORDER BY cantidad DESC, anio DESC LIMIT 10;

-- EJERCICIO 17
-- Calcular año más antiguo y reciente por marca.
SELECT marca, MIN(anio) AS 'anio_min', MAX(anio) AS 'anio_max'
FROM vehiculos
GROUP BY marca
ORDER BY marca;

-- EJERCICIO 18
-- Contar productos por tramos de precio.
SELECT CASE
    WHEN precio < 25 THEN 'Menos de 25'
    WHEN precio < 75 THEN '25 a menos de 75'
    ELSE '75 o más'
    END AS tramo,
    COUNT(*) AS cantidad
FROM productos
GROUP BY tramo
ORDER BY MIN(precio);

-- EJERCICIO 19
-- Filtrar marcas con al menos cinco vehículos.
SELECT marca, COUNT(*) AS cantidad
FROM vehiculos
GROUP BY marca
HAVING COUNT(*) >= 5
ORDER BY marca;

-- EJERCICIO 20
-- Mostrar años en los que hay al menos tres vehículos.
SELECT anio, COUNT(*) AS cantidad
FROM vehiculos
GROUP BY anio
HAVING COUNT(*) >= 3
ORDER BY cantidad DESC, anio DESC
LIMIT 10;

-- EJERCICIO 21
-- Mostrar el precio medio por familia, usando la primera palabra del nombre.
SELECT SUBSTRING(nombre, 1, INSTR(nombre || ' ', ' ') - 1) AS familia,
    COUNT(*) AS productos,
    ROUND(AVG(precio), 2) AS media
FROM productos
GROUP BY familia
ORDER BY productos DESC, familia
LIMIT 12;

-- EJERCICIO 22
-- Mostrar familias que tengan al menos cuatro productos y precio medio superior a 35.
SELECT substr(nombre, 1, instr(nombre || ' ', ' ') - 1) AS familia,
    COUNT(*) AS productos,
    ROUND(AVG(precio), 2) AS media
FROM productos
GROUP BY familia
HAVING COUNT(*) >= 4 AND AVG(precio) > 35
ORDER BY media DESC;

-- EJERCICIO 23
-- Mostrar productos más caros que la media general.
SELECT id, nombre, precio
FROM productos
WHERE precio > (SELECT AVG(precio) FROM productos)
ORDER BY precio DESC
LIMIT 10;

-- EJERCICIO 24
-- Listar los modelos de los vehículos posteriores al año 2020 agrupados por marca.
SELECT marca, COUNT(*) AS vehiculos_recientes
FROM vehiculos
WHERE anio > 2020
GROUP BY marca
ORDER BY vehiculos_recientes DESC, marca;

-- EJERCICIO 25
-- Detectar los productos empatados con el precio máximo.
SELECT id, nombre, precio
FROM productos
WHERE precio = (SELECT MAX(precio) FROM Productos);

-- EJERCICIO 26
-- Clasificar productos según el precio usando un ranking.
SELECT id, nombre, precio,
    RANK() OVER (ORDER BY precio DESC) AS puesto
FROM productos
ORDER BY puesto
LIMIT 10;

-- EJERCICIO 27
-- Calcular un total acumulado de precios ordenados por identificador.
SELECT id, nombre, precio,
    ROUND(SUM(precio) OVER (ORDER BY id), 2) AS acumulado
FROM productos
ORDER BY id
LIMIT 10;

-- EJERCICIO 28
-- Comparar cada producto con el precio medio de su familia.
-- No tengo ni idea
SELECT ...;

-- EJERCICIO 29
-- Mostrar el vehículo más reciente de cada marca, incluyendo empates.
SELECT matricula, marca, modelo, anio
FROM vehiculos v
WHERE anio = (SELECT MAX(anio) FROM Vehiculos WHERE marca = v.marca)
ORDER BY marca ASC;

-- EJERCICIO 30
-- Resumir por marca el total, año medio y antigüedad, conservando solo marcas con al menos tres vehículos.
SELECT marca,
       COUNT(*) AS total,
       ROUND(AVG(anio), 1) AS anio_medio,
       MIN(anio) AS mas_antiguo,
       MAX(anio) AS mas_reciente
FROM vehiculos
GROUP BY marca
HAVING COUNT(*) >= 3
ORDER BY marca ASC;
