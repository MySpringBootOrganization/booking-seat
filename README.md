This is just a test to domonstrate the use of muserver.io to build rest api for the purpose of booking seats.

Assume restaurant opens from 12 - 12, time slot 1 is 12:00 PM to 2:00 PM, time slot 2 is 2:00 - 4:00PM etc
Assume no database is available

The owner call the API with standard date format yyyy-MM-dd

Validation has not been implemnted.
Todo: 
reject duplicate booking,
reject invalid date,
reject missing required parameters

Todo:
handle exception with proper error messages


Sample Customer request:
Post: localhost:8080/newbooking
{
	"name": "Sam Wong",
    "tableSize": 8,
    "bookingDate": "2025-04-02",
    "slot": 2

}

Sample Owner request:
Get:    localhost:8080/bookingdate/2025-04-02


Run Program:
mvn clean install
java -jar target/booking-seat-1.0-SNAPSHOT.jar
    

