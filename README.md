# Soccer Database Interface

## Description
A Java-based interface for managing and querying a comprehensive soccer database for the World Cup. This tool provides a unified platform for managing and querying everything from team details and player statistics to match outcomes and ticket transactions. 

## ER Diagram
![image](https://github.com/kianfattahy/Soccer_Database/assets/94335877/4afa4e71-9c0a-4baa-bcd5-fcb1416df840)

## Example Query Exported to Table
<img width="681" alt="Screenshot 2023-09-10 at 3 10 52 PM" src="https://github.com/kianfattahy/Soccer_Database/assets/94335877/0c159bd7-2f06-4b6d-9559-1b31cfbd0ddd">

### Query Used
```
EXPORT TO './top_goals.csv' OF DEL MODIFIED BY NOCHARDEL SELECT t.country, SUM(CASE
WHEN t.country = m.team1 THEN m.team1Score WHEN t.country = m.team2 THEN m.team2Score ELSE 0
END) AS goals
FROM Team t
LEFT JOIN Match m ON t.country = m.team1 OR t.country = m.team2 WHERE m.round = 'group'
GROUP BY t.country
ORDER BY goals DESC
LIMIT 5;
```

## Features

### Team Management
- Add, update, or retrieve team details including their associated countries, official URLs, and groups.
- Track team members with their date of birth and associated country.

### Match Overview
- Organize matches with details including date, time, length, participating teams, scores, and associated stadiums.
- Record goals scored in matches, noting occurrence, type (penalty or not), time, and the player who scored.
- Assign referees to matches and define their roles.

### Player & Coach Profiles
- Maintain comprehensive player profiles, tracking their shirt number and general playing position.
- Track coaches and their roles within a team.

### Stadium Information
- Keep a record of stadiums with details like location and capacity.

### Referee Details
- Manage referee information, including their experience and origin.

### Ticketing System
- Offer ticketing solutions, recording seat details, pricing, and timing.
- Keep track of clients buying tickets, storing details like email, password, language preference, and address.

### Match Performance Analysis
- Analyze player performance in matches, tracking their play position, start and end time, and any yellow or red cards received.

### Relationship-Driven Insights
- Understand player participation in matches, their roles, and performance metrics.
- Assign referees to specific matches, ensuring regulatory compliance.
- Efficiently manage ticket sales to clients, ensuring optimal seat allocation and transaction tracking.

## Setup & Usage
1. Clone the repository to your local machine.
2. Navigate to the project directory and compile the `Soccer.java` file:
```
javac Soccer.java
```
3. Run the application:
```
java Soccer
```
4. Follow the on-screen prompts to select an operation.
