# Лямбды

## Ссылки

* [Метод flatMap()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#flatMap-java.util.function.Function-) — преобразует стрим стримов в один плоский стрим. В качестве аргумента принимает лямбду, преобразующую текущий элемент в поток.
* [Метод toArray()](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#toArray-java.util.function.IntFunction-) — возвращает массив, содержащий элементы потока.

## main/java/exercise/App.java

## Задачи

Создайте класс `App` с публичным статическим методом `enlargeArrayImage()`. Метод принимает в качестве аргумента изображение в виде двумерного массива строк и возвращает двумерный массив, увеличенный в два раза (и по горизонтали и по вертикали).

```java
String[][] image = {
    {"*", "*", "*", "*"},
    {"*", " ", " ", "*"},
    {"*", " ", " ", "*"},
    {"*", "*", "*", "*"},
};
System.out.println(Arrays.deepToString(image)); // =>
// [
//     [*, *, *, *],
//     [*,  ,  , *],
//     [*,  ,  , *],
//     [*, *, *, *],
// ]

String[][] enlargedImage = App.enlargeArrayImage(image);
System.out.println(Arrays.deepToString(enlargedImage)); // =>

// [
//     [*, *, *, *, *, *, *, *],
//     [*, *, *, *, *, *, *, *],
//     [*, *,  ,  ,  ,  , *, *],
//     [*, *,  ,  ,  ,  , *, *],
//     [*, *,  ,  ,  ,  , *, *],
//     [*, *,  ,  ,  ,  , *, *],
//     [*, *, *, *, *, *, *, *],
//     [*, *, *, *, *, *, *, *],
// ]
```

### Самостоятельная работа

* В файле *main/java/exercise/Sorter.java* определите класс `Sorter` с публичным статическим методом `takeOldestMans()`. Метод принимает в качестве аргумента список `List` пользователей. Каждый пользователь представлен словарем `Map` со строковыми ключами и значениями.

  ```java
  Map<String, String> user = Map.of(
      "name", "Andrey Belov",
      "birthday", "1980-11-23",
      "gender", "male"
  );
  System.out.println(user); // => {name=Andrey Belov, gender=male, birthday=1980-11-23}
  ```

  Метод должен вернуть список с именами мужчин, отсортированный по дате рождения (самый взрослый пользователь должен быть в начале списка).

  ```java
  List<Map<String, String>> users = List.of(
      Map.of("name", "Vladimir Nikolaev", "birthday", "1990-12-27", "gender", "male"),
      Map.of("name", "Andrey Petrov", "birthday", "1989-11-23", "gender", "male"),
      Map.of("name", "Anna Sidorova", "birthday", "1996-09-09", "gender", "female"),
      Map.of("name", "John Smith", "birthday", "1989-03-11", "gender", "male"),
      Map.of("name", "Vanessa Vulf", "birthday", "1985-11-16", "gender", "female"),
      Map.of("name", "Alice Lucas", "birthday", "1986-01-01", "gender", "female"),
      Map.of("name", "Elsa Oscar", "birthday", "1970-03-10", "gender", "female")
  );

  List<String> men = Sorter.takeOldestMan
  
  
  
  
  s(users);
  System.out.println(men); // ["John Smith", "Andrey Petrov", "Vladimir Nikolaev"]
  ```

* Напишите тесты для этого метода в файле *test/java/exercise/SorterTest.java*

### Подсказки

* Для преобразования потока в массив строк потребуется передать в метод `toArray()` ссылку на конструктор массива: `String[][]::new`
