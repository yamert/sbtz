# TZ
## Задание
Приложение предоставляет следующий API для работы с графом:
- Обход графа в ширину с заданной стартовой вершиной и глубиной
- Возвращаемое значение – все вершины полученного подграфа и связи этих вершин

## Сборка и запуск
```sbt
sbt clean compile test
```
## Компоненты
- [src/main/scala/sbtz/loader](src/main/scala/sbtz/loader) Загрузчик и парсер данных
- [src/main/scala/sbtz/engine](src/main/scala/sbtz/engine) Движок

## Тесты
### Тесты загрузки и парсинга данных
- [src/test/scala/sbtz/loader/LoadedDataSpec.scala](src/test/scala/sbtz/loader/LoadedDataSpec.scala) Тест загрузки строк из файла
- [src/test/scala/sbtz/loader/DataEdgeSpec.scala](src/test/scala/sbtz/loader/DataEdgeSpec.scala) Тест парсинга строк в связи

### Тесты движка
- [src/test/scala/sbtz/engine/EngineDataSpec.scala](src/test/scala/sbtz/engine/EngineDataSpec.scala) Тест данных внутри движка
- [src/test/scala/sbtz/engine/EngineSpec.scala](src/test/scala/sbtz/engine/EngineSpec.scala) Тест выборки подмножества графа
