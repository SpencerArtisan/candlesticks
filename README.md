Candlesticks
============
A command line application for creating ASCI Gantt charts.

Trips may be added, adjusted and visualised. For example:

```
< Sailing > 
< Edinburgh > 
< Wales walk > 
< Jurassic coast > 
       ¦██ Belgium  ¦            ¦            ¦             ¦            ¦            ¦  
       ¦      ████ Sicily        ¦            ¦             ¦            ¦            ¦  
       ¦            ¦ █ Si birthday           ¦             ¦            ¦            ¦  
       ¦            ¦       ██ Shropshire     ¦             ¦            ¦            ¦  
       ¦            ¦            ¦███ Cheshire¦             ¦            ¦            ¦  
       ¦            ¦            ¦      ██ Cornwall         ¦            ¦            ¦  
       ¦            ¦            ¦            ███████████ Music tour     ¦            ¦  
       ¦            ¦            ¦            ¦             ████ Ireland ¦            ¦  
       ¦            ¦            ¦            ¦             ¦         ██████ Dollomites  
       ¦            ¦            ¦            ¦             ¦            ¦     ██████████ France
 ┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──
 18 25 1  8  15 22 29 6  13 20 27 3  10 17 24 1  8  15 22 29 5  12 19 26 2  9  16 23 30 7 
 Mar    Apr          May          Jun          Jul           Aug          Sep          Oct   
```

Usage
-----
This program uses Clojure CLI tool alone. No Boot or Leiningen.

To run

```clj -m candlesticks.core [command]```

To test

```clj -M:test -i test/candlesticks/[test file]```

The batch file ```autotest.sh``` can be used to automatically run tests on file changes. This relies on having fswatch installed.
