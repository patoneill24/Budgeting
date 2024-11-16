// Authors: Patrick O'Neill
import java.io.FileWriter;
import java.io.File;
import java.util.Stack;


public class Tracker {
    private Stack<Budget> budgets = new Stack<Budget>();

    public Tracker(){
    }

    public void createBudget(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Enter the month of the budget: ");
        String month = System.console().readLine();
        System.out.println("Enter the year of the budget: ");
        String year = System.console().readLine();
        Stack<Category>categories = new Stack<Category>();
        Budget budget = new Budget(categories, month, year, 0.0, 0.0);
        budgets.push(budget);
        System.out.println("Enter Categories for the budget: ");
        budget.addCategory();
    }

    public void viewBudgets(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int i = 1;
        if(budgets.size() == 0){
            System.out.println("No budgets found.");
            System.console().readLine();
        }else{
            for (Budget budget : budgets) {
                System.out.println(i + ". " + budget.getMonth() + " " + budget.getYear() + ": " + budget.getTotalExpenses() + "/" + budget.getTotalBudgetedAmount());
                i++;
            }
            System.out.print("Enter the number of the budget you want to view: ");
            int selectedBudgetNumber = Integer.parseInt(System.console().readLine());
            if(selectedBudgetNumber > budgets.size()){
                System.out.println("Budget not found.");
                System.console().readLine();
                return;
            }
            Budget selectedBudget = budgets.get(selectedBudgetNumber - 1);
            if(selectedBudget == null){
                System.out.println("Budget not found.");
                System.console().readLine();
                return;
            }
            selectedBudget.viewCategories();
        }
    }

    public void addExpense(){
        int i = 1;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Budget budget = null;
        for (Budget b : budgets) {
            System.out.println(i + ". " +  b.getMonth() + " " + b.getYear());
            i++;
        }
        System.out.print("Enter the number of the budget you want to add an expense to: ");
        int selectedBudgetNumber = Integer.parseInt(System.console().readLine());
        if(selectedBudgetNumber > budgets.size()){
            System.out.println("Budget not found.");
            System.console().readLine();
            return;
        }
        budget = budgets.get(selectedBudgetNumber - 1);
        if(budget == null){
            System.out.println("Budget not found.");
            System.console().readLine();
            return;
        }
        budget.addExpense();
    }

    public void WriteToFile(String filename){
        try{
            FileWriter writer = new FileWriter(filename);
            for (Budget budget : budgets) {
                writer.write("BUDGET" + "||"+budget.getMonth() + "||" + budget.getYear() + "||" + budget.getTotalExpenses() + "||" + budget.getTotalBudgetedAmount()+"\n");
                for (Category category : budget.getCategories()) {
                    writer.write("CATEGORY"+ "||" + category.getName() + "||" + category.getSumExpenses() + "||" + category.getBudgetedAmount()+"\n");
                    for (Expense expense : category.getExpenses()) {
                        writer.write("EXPENSE"+ "||" + expense.getDescription() + "||" + expense.getAmount() + "||" + expense.getDate() + "\n");
                    }
                }
            }
            System.out.println("Successfully wrote to the file: " + filename);
            System.out.print("Press enter to continue...");
            System.console().readLine();
            writer.close();
        }catch(Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void saveToFile(){
        int i = 1;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        File folder = new File("/Users/patrickoneill/Documents/BYU-I/Semesters/Fall-2024/CSE 310/Budgeting");
        File[] listOfFiles = folder.listFiles();
        System.out.println("Enter the name of the file you want to save to: ");
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                System.out.println(i + ". "  + file.getName());
                i++;
            }
        } else {
            System.out.println("The folder is empty or does not exist.");
        }
        System.out.print("Enter the number of the file you want to save to:  ");
        WriteToFile(listOfFiles[Integer.parseInt(System.console().readLine())-1].getName());
        System.console().readLine();
    }

    public void loadFromFile(){
        int i = 1;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        File folder = new File("/Users/patrickoneill/Documents/BYU-I/Semesters/Fall-2024/CSE 310/Budgeting");
        File[] listOfFiles = folder.listFiles();
        System.out.println("Enter the name of the file you want to load: ");
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                System.out.println(i + ". "  + file.getName());
                i++;
            }
        } else {
            System.out.println("The folder is empty or does not exist.");
        }
        System.out.print("Enter the number of the file you want to load:  ");
        try{
            File file = listOfFiles[Integer.parseInt(System.console().readLine())-1];
            java.util.Scanner scanner = new java.util.Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] budgetData = line.split("\\|\\|");
                String lineType = budgetData[0];
                switch(lineType){
                    case("BUDGET"):
                        String month = budgetData[1];
                        String year = budgetData[2];
                        double totalExpenses = Double.parseDouble(budgetData[3]);
                        double totalBudgetedAmount = Double.parseDouble(budgetData[4]);
                        Stack<Category> categories = new Stack<Category>();
                        Budget budget = new Budget(categories, month, year, totalBudgetedAmount, totalExpenses);
                        budgets.push(budget);
                        break;
                    case("CATEGORY"):
                        String categoryName = budgetData[1];
                        double sumExpenses = Double.parseDouble(budgetData[2]);
                        double budgetedAmount = Double.parseDouble(budgetData[3]);
                        Category category = new Category(categoryName, sumExpenses, budgetedAmount);
                        Budget budgetLastAdded = budgets.peek();
                        budgetLastAdded.addExistingCategory(category);
                        break;
                    case("EXPENSE"):
                        String description = budgetData[1];
                        double amount = Double.parseDouble(budgetData[2]);
                        String date = budgetData[3];
                        Stack<Category> categoriesLastAdded = budgets.peek().getCategories();
                        Category categoryLastAdded = categoriesLastAdded.peek();
                        Expense expense = new Expense(categoryLastAdded.getName(), amount, date, description);
                        categoryLastAdded.addExistingExpense(expense);
                        break;
                }           
            }
            scanner.close();
            System.out.println("Successfully loaded from the file: " + file.getName());
            System.out.print("Press enter to continue...");
            System.console().readLine();
        }catch(Exception e){
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.console().readLine();
        }
    }
}
