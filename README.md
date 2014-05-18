TheBandomeerGame
================
## Git Crash Course
### Софтуер
За да работите с това repository, трябва да инсталирате *itHub for Windows* (който е доста бъгав по мое мнение) или по-добрият вариант - *msysgit* (конзолен клиент за Windows) + *TortoiseGit* (слага едни неща в контекстните менюта, които правят работата малко по-малко неприятна). От друга страна, може да ползвате някаква интеграция на Eclipse с Git, ако има - за това не знам. Аз използвам IntelliJ IDEA и неговата интеграция е добра. Освен това, ако работите на Linux - там май има само конзолен клиент.

Линкове за сваляне:
* GitHub for Windows  - https://windows.github.com/
* msysgit - http://msysgit.github.io/
* TortoiseGit - https://code.google.com/p/tortoisegit/wiki/Download?tm=2
* Инсталация върху Ubuntu:
```
sudo apt-get install libcurl4-gnutls-dev libexpat1-dev gettext libz-dev libssl-dev build-essential
sudo apt-get install git-core
```
### Workflow
* В началото клонирате (_git clone_) това repository в желано от вас място.
* Преди започване на работа (сутрин примерно), изтегляте последната версия (_git pull_)
* Преди качване на промените, отново правите _git pull_. Ако има някакви конфликти между вашата версия и тази на сървъра, които не могат да се разрешат автоматично, трябва да ги оправите ръчно (засега няма да давам подробности за процеса, но не е много труден)
* След справянето с конфликтите - _git merge_ издърпвате отново последната версия - _git pull_. Целта на това е, ако някой е качил нещо преди вас, вашите промени ще покрият неговите. Да знаете, това създава ядове :)
* След като сте взели последната версия, качвате вашата. Процесът е от две стъпки - първо _git commit_, после _git push_. Давате смислен коментар и всичко е качено
* За всяко качване на промени се прави така: _pull_, _commit_, _push_. За да не стават конфликти е добре да качваме по-често (примерно по-често от веднъж дневно), но не прекалено често. По правило се качват неща, които работят и са поне що-годе завършени. Код, който не се компилира, не се качва (чупи кода за всички :D)

## Изисквания към играта
### UI
Ще се пробвам да си поиграя малко с JavaFX. Като цяло идеята е проста - ляв бутон отваря координатите, десен - ако е посетена не прави нищо, ако не - слага флаг, ако вече е флагната - въпрос, ако има въпрос я прави празна - както е в Minesweeper-a на Windows.
### Settings
Добра идея е всички константи, които дефинираме за играта никъде да не се hardcode-ват, а да се изнасят или в самия клас, за който се отнасят, или в един отделен клас Settings. Това ще направи кода по-лесен за допълване и промяна.
### Начален екран
За по-лесно ще е добре да имаме начален екран от самото начало, ако ще и да има само бутон New game и нищо друго. За пълнота по-нататък може да бутнем някакви инструкции. Това ще направи инициализацията на играта по-лесна.
### Основна логика
* Клас ```Cell```
    * Координати (x, y) - за по-лесен достъп, да не бъркаме в полето всеки път
    * Content - няколко предефинирани char или по-добре int константи: числата от 1 до 8, мина (примерно -1), флаг (примерно -2) и въпросителна (примерно -3)
    * ```boolean``` поле, което отчита дали клетката е била посетена + метод за посещаване. Същият тип логика за това дали клетката е мина, флаг или въпросителна - методи за слагане / махане на флаг и за слагане / махане на въпросителна
    * Евентуално, методи за event handlin-a - това е част от UI-я
    * 
* Клас ```Board```, ```MineField```... каквото ще да е там :D
    * Вътрешно - двумерен масив от клетки, (големината и броя мини се задава в конструктора)
    * Публични полета за достъп до ```width```, ```height``` и броя мини
    * Indexer: в Java няма indexer-и, затова се правят два метода - ```get(int x, int y)```, ```set(int x, int y, Cell value)```
    * Метод за посещаване на клетка ```visit(int x, int y)```, който ще прехвърля към същия метод на клетката, която е на дадения индекс
    * Метод за проверка дали играта е спечелена
* Клас ```MineField Extensions``` (static)
    * Може функционалността да бъде част от MineField, но ще стане много претрупано и ще има повече конфликти при промяна
    * В Java няма extension method-и, но може да се направи статичен клас, всеки метод от който приема като първи аргумент променлива от тип ```MineField```
    * Добре е тук да отделим методите за слагане на мините в началото - прави се с random generator и за вземане на броя съседни мини (**Бележка:** една клетка има 8 съседа)
* Клас за връзка на полето с бутони в  UI-я
    * Чертае всяко бутонче в зависимост от състоянието на клетката, която отговаря на координатите му
    * Update-ва UI-я след като нещо се случи
    * Най-трудната част тук е рекурсивното отваряне на съседните клетки (вж. по-долу)
* Таймер, брояч на оставащите мини
    * Това е сравнително тривиално за имплементиране
* Ако остане време - highscores / statistics
    * Идеята е проста - всеки се класира според това колко бързо е решил пъзела
    * Друга идея - както е в Windows, да нямаме различни потребители, а да пазим статистика за това колко броя (и колко %) от игрите са спечелени - това е още по-лесно :)
* Ако остане време - опция за рестарт на последната игра 
    * Може да се пази началното състояние на полето във файл и да се зарежда от него
* Ако остане време - нива (т. е. ```new MineField``` с различни числа) - ако кодът е добре написан, трябва да стане само с този ред код
### Поведение
* След натискане на New Game, полето се запълва с мини по случаен начин и се визуализира. Ако при първото си кликване потребителят уцели мина, се прави повторно запълване, като тук трябва да се изключи полето, на което е кликнато - ако не е така, при второто запълване има шанс пак да се окаже мина.
* При натискане върху някой бутон, се разбира дали клетката е мина и играта евентуално свършва. Ако дотук играта не е загубена, се проверява колко са съседните мини. Ако са 0, рекурсивно се отварят съседните клетки. Рекурсията (DFS най-добре) продължава, докато се отвори област, чиято граница е заета с числа. Прави се проверка дали играта е спечелена (т. е. дали всички неотворени полета са мини)
* При десен клик, текущата клетка се флагва и броячът за оставащите мини намалява. Прави се проверка дали играта е спечелена (може да е метод в MineField). Ако имаме време за statistics / highscores, тук може да променим бройките
* При повторен десен клик на флагната клетка, става въпросителна, броячът се увеличава и това е всичко :)
* * **Бележка: ** Клетка с флаг не се отваря (т. е. кликането върху флагната клетка не прави нищо), клетка с въпросителна може да се отвори
