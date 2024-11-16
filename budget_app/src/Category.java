import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private double budgetedAmount;
    private double sumExpenses;

    private List<Expense> expenses = new ArrayList<Expense>();

    private boolean keepRunning;


    public Category(String name, double sumExpenses,double budgetedAmount) {
        this.name = name;
        this.sumExpenses = sumExpenses;
        this.budgetedAmount = budgetedAmount;
    }

    public String getName() {
        return name;
    }

    public double getBudgetedAmount() {
        return budgetedAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudgetedAmount(double budgetedAmount) {
        this.budgetedAmount = budgetedAmount;
    }

    public double getSumExpenses() {
        sumExpenses = 0.0;
        for (Expense expense : expenses) {
            sumExpenses += expense.getAmount();
        }
        return sumExpenses;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void listExpenses() {
        System.out.println("\nList of expenses for " + name + ":");
        if(expenses.size() > 0) {
            for (Expense expense : expenses) {
                System.out.println("Expense: " + expense.getAmount());
                System.out.println("Date: " + expense.getDate());
                System.out.println("Description: " + expense.getDescription());
            }
        } else {
            System.out.println("No expenses found for this category.");
        }
    }
    public void addExpense() {
        keepRunning = true;
        while(keepRunning){
            Expense expense = new Expense("", 0.0, null, null);
            System.out.print("Enter the amount of the expense: ");
            expense.setAmount(Double.parseDouble(System.console().readLine()));
            System.out.print("Enter the date of the expense: (MM-DD-YYYY) ");
            expense.setDate(System.console().readLine());
            System.out.print("Enter a description of the expense: ");
            expense.setDescription(System.console().readLine());
            expenses.add(expense);
            System.out.print("Would you like to add another expense? (y/n) ");
            String response = System.console().readLine();
            if(response.equals("n")){
                keepRunning = false;
            }
        }
    }

    public void addExistingExpense(Expense expense) {
        expenses.add(expense);
    }
}
