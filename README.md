Проект можно запустить из ide (в моем случае использовалась idea)
или же выполнить команду mvn package в терминале в папке проекта. 

Появится папка target в ней будет jar,
можно скопировать его в любую другую директорию, и в терминале выполнить команду:

java -jar parsing_csv-1.0-SNAPSHOT.jar <путь к файлу>.

Результирующий файл появится в той же директории под название result.csv