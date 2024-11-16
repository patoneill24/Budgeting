public class App {
    
    private static boolean isRunning = true;
    
    public static String Menu(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Please select an option:");
        System.out.println("1. Create a new budget");
        System.out.println("2. View an existing budget");
        System.out.println("3. Add an expense");
        System.out.println("4. Write to New file");
        System.out.println("5. Save to Existing File");
        System.out.println("6. Load from file");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
        String option = System.console().readLine();
        return option;
    }
    
    public static void main(String[] args) {
        Tracker tracker = new Tracker();
        while(isRunning){
            String option = Menu();    
            switch(option) {
                case "1":
                    System.out.println("Creating a new budget...\n");
                    tracker.createBudget();
                    break;
                case "2":
                    System.out.println("Viewing an existing budget...\n");
                    tracker.viewBudgets();
                    break;
                case "3":
                    System.out.println("Adding an expense...\n");
                    tracker.addExpense();
                    break;
                case "4":
                    System.out.println("Enter the name of the file: ");
                    String filename = System.console().readLine();
                    tracker.WriteToFile(filename);
                    break;
                case "5":
                    System.out.println("Saving to existing file...\n");
                    tracker.saveToFile();
                    break;
                case "6":
                    System.out.println("Loading from file...\n");
                    tracker.loadFromFile();
                    break;
                case "7":
                    System.out.println("Exiting...\n");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
            }
        }
    }
}
