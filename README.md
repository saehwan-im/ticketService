# ticketService

Ticket Service Coding Challenge
# Design:
This service was implemented based on the assumptions that the front-most row has the best seats and consumers want seat as consecutive as they can. Default size of the venue is 10 X 10 (zero-based index) and each seat hold expires after 10000 milliseconds (10 seconds). These settings can be configured by modifying the com.walmart.ticketService.configuration.TicketServiceConfiguration class. 
Basic data structure of the venue is composed of an array of Row and each Row has an array of Seat. The algorithm for findAndHoldSeats(int numSeats, String customerEmail) is the following. First, get the first row and start traversing from the leftmost seat up to the numSeats requested. If there is any unavailable seat in that range, shift by 1 to the right. Repeat until the starting index is equal to the column span-numSeats in order to avoid indexOutOfBounds exception. If the desired consecutive seats are found, subtract the number of the discovered consecutive seats from the original numSeats requested by the consumer. If the desired consecutive seats are not found, proceed to the next row. If the desired number of seats is not found until the last row, decrement numSeats by 1 and repeat the procedure above by restarting from the first row. If the remaining number of seats to be placed is less than the current desired number of the consecutive seats, look for the consecutive seats with the remaining number of seats. In case there was a change right before holding seats, throw ConflictException and then retry on the current row if the current row has enough available seats. SeatHold object has a field of type Map<Integer, List<Point>> where the key of the map is the row number and the value of the map is a list of the starting index and the ending index. This map is used when reserving the held seats later. Expiration of the seat holds are checked every time before checking the number of available seats. In-memory data structure was used instead of disk-based storage in order to avoid the overhead of accessing the database.

# Future Improvements:
All save methods in repositories are synchronized in order to avoid concurrent modification and overwriting on top of each other. Also, CopyOnWriteArrayList was used for seat holds to prevent ConcurrentModification Exception when deleting expired seat holds. This approach could be replaced with using Locks for better performance.
Also different findAndHoldSeats implementations could be added to be able to find the best seats for various types of venues and performances.

# Build and Deployment:
1.	Download and unzip the project
2.	Open Command Prompt or shell and cd into the directory where mvnw file is.
3.	Run mvnw clean install
4.	Run mvnw spring-boot:run
Default port is 8080 and it can be configured by changing the src/main/resources/application.properties file
If mvnw command doesn't work, please try again with mvn command instead

# Sample Postman Requests:
GET /numSeatsAvailable/ HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Postman-Token: 21456ceb-4321-973d-44a5-922736288820

POST /findAndHoldSeats HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: f95cc150-0d34-695b-d945-756a8631c524

{
	"numSeats": "5",
	"customerEmail": "test@test.ticketService"
}

POST /reserveSeats HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: d7cf02ac-0548-4efe-c1f8-4a198fda57f3

{
	"seatHoldId": "0",
	"customerEmail": "test@test.ticketService"
}
