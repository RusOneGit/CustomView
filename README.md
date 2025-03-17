# Домашнее задание к занятию «2.1. Custom Views — разработка собственных элементов интерфейса»

## Задача. Smart StatsView
### Описание
На текущий момент наша Custom View принимает в качестве данных доли:
![](https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/diagram.png)

findViewById<StatsView>(R.id.stats).data = listOf(
    0.25F,
    0.25F,
    0.25F,
    0.25F,
)
Что в сумме даёт нам картинку из лекции:
![](https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/diagram.png)

Q: что мы хотим?

A: мы хотим, чтобы StatsView принимала на вход данные, по которым сама рассчитывала проценты:

findViewById<StatsView>(R.id.stats).data = listOf(
    500F,
    500F,
    500F,
    500F,
)
Что должно давать такую же картинку, т. к. SmartStatsView просуммирует все данные и определит, что каждый элемент — это ровно 25 %:
![](https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/dot.png)


### Результат
Опубликуйте изменения в виде Pull Request в вашем проекте на GitHub.


## Задача. Dot
### Описание
В нашей реализации есть один не очень приятный нюанс:

Исправьте реализацию таким образом, чтобы мы из картинки «Как сейчас» получили картинку «Как должно быть».

### Результат
Опубликуйте изменения в виде Pull Request в вашем проекте на GitHub.

# Домашнее задание к занятию «2.2. Animations — создание анимаций. Часть 1»

## Задача. Rotation
### Описание
Необходимо дополнить реализованную на лекции анимацию поворотом на 360 градусов:

<img src="https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/Rotetion.gif" width="200">

Обратите внимание, что текст остаётся неподвижным.

Для решения задачи подумайте:

1. Какой параметр отвечает за прогресс анимации.
2. Как перевести его в градусы.
3. Какой параметр отвечает за начальный сдвиг при отрисовке каждой дуги.
   
## Задача. Sequential
### Описание
Необходимо реализовать параллельное заполнение данными:
![](https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/sequential.png)

## Задача. Bidirectional
### Описание
Предлагаем дополнить параллельное заполнение данными следующим режимом:
![](https://github.com/IrinaAlMakarova/CustomViews-SmartStatsView/blob/main/app/src/main/res/layout/pic/Bidirectional.gif)