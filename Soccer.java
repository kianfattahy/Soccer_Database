import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
public class Soccer {
    public static Scanner scanner = new Scanner(System.in);
    public static void printStartMsg(){

        System.out.print("Soccer Main Menu\n" +
                "   1. List information of matches of a country\n" +
                "   2. Insert initial player information for a match\n" +
                "   3. List ticket buyer information for a stadium\n" +
                "   4. Exit application\n" +
                "Please Enter Your Option: ");
    }
    public static void listCountryMatchInfo(Statement stm) {

        System.out.print("Please Enter Country: ");
        String country = scanner.next();
        scanner.nextLine();

        try {
            String query = "SELECT Match.team1, Match.team2, Match.round, Match.team1Score, Match.team2Score, Match.date, COUNT(Ticket.mid) as ticketsSold " +
                    "FROM Match " +
                    "LEFT JOIN Ticket ON Ticket.mid = Match.mid " +
                    "WHERE Match.team1 = '" + country + "' OR Match.team2 = '" + country + "'" +
                    "GROUP BY Match.team1, Match.team2, Match.round, Match.team1Score, Match.team2Score, Match.date";
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                String team1 = rs.getString("team1");
                String team2 = rs.getString("team2");
                String round = rs.getString("round");
                String team1Score = rs.getString("team1Score");
                String team2Score = rs.getString("team2Score");
                Date date = rs.getDate("date");
                int ticketsSold = rs.getInt("ticketsSold");
                System.out.printf("%-15s%-15s%-15s%-10s%-5s%-10s%-10s%-10d\n", team1, team2, date, round, "", team1Score, team2Score, ticketsSold);
            }

            // close result set
            rs.close();

            System.out.print("Press [A] to find matches of another country, or [P] to go to the previous menu ");
            String input = scanner.next();
            scanner.nextLine();

            while (!(input.equals("A")) && !(input.equals("P"))) {
                System.out.print("Invalid input, please try again: ");
                input = scanner.next();
                scanner.nextLine();
            }

            if (input.equals("A")) {
                listCountryMatchInfo(stm);
            }

        }
        catch (SQLException e){
            System.out.println("Sql error, try again");
            System.out.println(e);
            listTicketInfo(stm);
        }
    }
    public static void listTicketInfo(Statement stm){

        // retrieve stadium name
        System.out.print("Please enter stadium name: ");
        String stadiumName = scanner.nextLine();

        // retrieve ticket information given stadium name
        try {

            String sql = "SELECT DISTINCT c.cid, c.name, c.email, t.mid, m.date, s.location " +
                    "FROM Client c " +
                    "JOIN buys b ON c.cid = b.cid " +
                    "JOIN Ticket t ON b.mid = t.mid AND b.seat = t.seat " +
                    "JOIN Match m ON t.mid = m.mid " +
                    "JOIN Stadium s ON m.sname = s.sname " +
                    "WHERE s.sname = '" + stadiumName + "'";


            ResultSet rs = stm.executeQuery(sql);

            System.out.printf("%-20s%-30s%-15s%-30s%-10s\n", "Customer Name", "Customer Email", "Match Id", "Location", "Date");

            while (rs.next()){
                String customerName = rs.getString("name");
                String customerEmail = rs.getString("email");
                String location = rs.getString("location");
                Date date = rs.getDate("date");
                String matchId = rs.getString("mid");
                System.out.printf("%-20s%-30s%-15s%-30s%-10s\n", customerName, customerEmail, matchId, location, date);
            }

            // close result set
            rs.close();

            System.out.print("Press [A] to find ticket info of another stadium, or [P] to go to the previous menu: ");
            String input = scanner.next();
            scanner.nextLine();

            while (!(input.equals("A")) && !(input.equals("P"))){
                System.out.print("Invalid input, please try again: ");
                input = scanner.next();
                scanner.nextLine();
            }

            if (input.equals("A")){
                listTicketInfo(stm);
            }

        }
        catch (SQLException e){
            System.out.println("Sql error, try again");
            System.out.println(e);
            listTicketInfo(stm);
        }



    }
    public static void insertInitialPlayerInformation(Connection con){
        try {
            //show all matches occurring in the next 3 days
            LocalDate currentDate = LocalDate.now();
            LocalDate threeDaysFromNow = currentDate.plusDays(3);
            Date currentSqlDate = Date.valueOf(currentDate);
            Date laterSqlDate = Date.valueOf(threeDaysFromNow);

            String sql = "SELECT m.mid, t1.country AS team1, t2.country AS team2, m.date, m.round " +
                    "FROM Match m " +
                    "INNER JOIN Team t1 ON m.team1 = t1.country AND t1.country IS NOT NULL " +
                    "INNER JOIN Team t2 ON m.team2 = t2.country AND t2.country IS NOT NULL " +
                    "WHERE m.date >= ? " +
                    "AND m.date <= ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setDate(1, currentSqlDate);
            statement.setDate(2, laterSqlDate);

            ResultSet rs = statement.executeQuery();
            //Process the result set
            while (rs.next()) {
                int mid = rs.getInt("MID");
                String team1 = rs.getString("TEAM1");
                String team2 = rs.getString("TEAM2");
                Date date = rs.getDate("DATE");
                String round = rs.getString("ROUND");
                System.out.println(mid + " | " + team1 + " vs " + team2 + " | " + date + " | " + round);
            }
            rs.close();

            //now prompt to choose match identifier and country
            //Connection conn2 = DriverManager.getConnection(url, your_userid, your_password);
            System.out.println("Insert match identifier: (Enter [P] to go back to the previous menu)");
            String mid = scanner.nextLine();
            if (mid.equals("P")){
                return;
            }
            System.out.println("Insert country: (Enter [P] to go back to the previous menu)");
            String country = scanner.nextLine();
            if (country.equals("P")){
                return;
            }

            String sql2 = "SELECT TEAMMEM.name, Player.shirtNum, plays.pos, plays.start, plays.end, plays.ycards, plays.rcard " +
                    "FROM Player " +
                    "INNER JOIN TeamMem ON Player.tid = TeamMem.tid " +
                    "INNER JOIN plays ON plays.tid = TeamMem.tid " +
                    "WHERE plays.mid = ? AND TeamMem.country = ?";
            PreparedStatement statement2 = con.prepareStatement(sql2);
            statement2.setString(1, mid);
            statement2.setString(2, country);

            ResultSet rs2 = statement2.executeQuery();
            //Process the result set
            System.out.println("\nPlayers from " + country + " already selected:");
            while (rs2.next()) {
                String name = rs2.getString("NAME");
                String shirtnum = rs2.getString("SHIRTNUM");
                String pos = rs2.getString("POS");
                String start = rs2.getString("START");
                String end = rs2.getString("END");
                String yCards = rs2.getString("YCARDS");
                String rCard = rs2.getString("RCARD");
                System.out.println(name + " | " + shirtnum +  " | " + pos + " | " + "start: " + start + " | " + "end: " + end + " | " + "yellow cards: " + yCards + " | " + "red card: " + rCard);
            }
            rs2.close();
            statement2.close();


            //now show the players not yet selected
            System.out.println("\nPossible players from " + country + " not yet selected:");
            String sql3 = "SELECT TeamMem.tid, TeamMem.name, Player.shirtNum, Player.GENPOS " +
                    "FROM Player " +
                    "INNER JOIN TeamMem ON Player.tid = TeamMem.tid " +
                    "WHERE TeamMem.country = ? " +
                    "EXCEPT " +
                    "SELECT TeamMem.tid, TeamMem.name, Player.shirtNum, Player.GENPOS " +
                    "FROM Player " +
                    "INNER JOIN TeamMem ON Player.tid = TeamMem.tid " +
                    "INNER JOIN plays ON plays.tid = TeamMem.tid " +
                    "WHERE plays.mid = ? AND TeamMem.country = ?";
            PreparedStatement statement3 = con.prepareStatement(sql3);
            statement3.setString(1, country);
            statement3.setString(2, mid);
            statement3.setString(3, country);
            ResultSet rs3 = statement3.executeQuery();
            ArrayList<Integer> tids = new ArrayList<>();
            int num = 1;
            while (rs3.next()){
                Integer tid = rs3.getInt("TID");
                String name = rs3.getString("NAME");
                String shirtNum = rs3.getString("SHIRTNUM");
                String genPos = rs3.getString("GENPOS");

                tids.add(tid);
                System.out.println(num++ + ") " + name + " | " + shirtNum + " | " + genPos);
            }
            rs3.close();
            statement3.close();


            System.out.println("Enter the list number of the player you want to insert or [P] to go to the previous menu.");
            String input = scanner.nextLine();
            if (input.equals("P")){
                return;
            }
            Integer tidToInsert = tids.get(Integer.valueOf(input) - 1);

            String countSize = "SELECT COUNT(*) AS num_players " +
                    "FROM plays p " +
                    "INNER JOIN TeamMem tm ON p.tid = tm.tid " +
                    "WHERE p.mid = ? AND tm.country = ?";
            String count = "SELECT COUNT(*) AS current "+
                    "FROM plays p";

            PreparedStatement statement6 = con.prepareStatement(countSize);
            statement6.setString(1, mid);
            statement6.setString(2,country);
            ResultSet rs6 = statement6.executeQuery();
            rs6.next();
            PreparedStatement statement4 = con.prepareStatement(count);
            ResultSet rs4 = statement4.executeQuery();
            rs4.next();
            int currentNumPlayers = rs6.getInt(1);
            int playIdToInsert = rs4.getInt(1) + 1;
            if (currentNumPlayers >= 3){
                System.out.println("A maximum amount of players have already been selected for this match");
                return;
            }
            statement4.close();
            rs4.close();


            String insertPlays = "INSERT INTO plays (playId, pos, end, start, ycards, rcard, tid, mid) " +
                    "VALUES (?,?, NULL, '00:00:00', 0, 0, ?, ?)";
            System.out.println("Please indicate the position that the player will have: ");
            String posToInsert = scanner.nextLine();
            PreparedStatement statement5 = con.prepareStatement(insertPlays);
            statement5.setInt(1, playIdToInsert);
            statement5.setString(2, posToInsert);
            statement5.setInt(3, tidToInsert);
            statement5.setInt(4, Integer.valueOf(mid));
            statement5.executeUpdate();
            insertInitialPlayerInformation(con);
            statement5.close();
        }


        catch (SQLException e)
        {
            System.out.println("Code: " + e.getErrorCode() + "  sqlState: " + e.getSQLState());
            System.out.println(e);
        }

    }
    public static void processChoice(Connection con, Statement stm, String choice) throws SQLException{
        boolean break_var = false;
        while (true) {
            switch (choice) {
                case "1" : {
                    listCountryMatchInfo(stm);
                    printStartMsg();
                    choice = scanner.nextLine();
                    break;
                }
                case "2" : {
                    insertInitialPlayerInformation(con);
                    printStartMsg();
                    choice = scanner.nextLine();
                    break;
                }
                case "3" : {
                    listTicketInfo(stm);
                    printStartMsg();
                    choice = scanner.nextLine();
                    break;
                }
                case "4" : {
                    // Finally but importantly close the statement and connection
                    stm.close();
                    con.close();
                    // Also make sure to close the scanner
                    scanner.close();
                    System.out.println("Application closed");
                    break_var = true;
                    break;
                }
                default : {
                    System.out.print("Invalid choice, please try again: ");
                    choice = scanner.nextLine();
                    processChoice(con, stm, choice);
                    break;
                }
            }
            if (break_var){
                break;
            }
        }
    }
    public static void main(String[] args) throws SQLException {

        // Register the driver. You must register the driver before you can use it.
        try {DriverManager.registerDriver (new com.ibm.db2.jcc.DB2Driver());}
        catch (Exception cnfe){ System.out.println("Class not found");}

        //REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = "cs421g219";
        String your_password = "Z4VbUw3pq219";
        // This is the url you must use for DB2.
        String url = "jdbc:db2://winter2023-comp421.cs.mcgill.ca:50000/cs421";

        Connection con = DriverManager.getConnection(url,your_userid,your_password);
        Statement stm = con.createStatement();

        // Soccer Menu
        printStartMsg();
        String choice = scanner.nextLine();
        processChoice(con, stm, choice);

    }
}