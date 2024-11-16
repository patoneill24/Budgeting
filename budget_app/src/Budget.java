import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Budget {
    // private int id;
    private String month;
    private String year;

    private List<String> categoryNames;

    private Stack<Category> categories;

    private boolean keepRunning;

    private double totalBudgetedAmount;

    private double totalExpenses;

    public Budget(Stack<Category> categories, String month, String year, double totalBudgetedAmount, double totalExpenses) {
        // this.id = id;
        this.month = month;
        this.year = year;
        this.categories = categories;
    }
    
    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    // public void setMonth(String month) {
    //     this.month = month;
    // }

    // public void setYear(String year) {
    //     this.year = year;
    // }

    public void addExistingCategory(Category category) {
        categories.add(category);
    }

    public void addCategory() {
        keepRunning = true;
        categoryNames = new ArrayList<String>();
        while(keepRunning){
            Category category = new Category("", 0.0,0.0);
            System.out.print("Enter the name of the category: ");
            String name = System.console().readLine();
            if(categoryNames.contains(name)){
                System.out.println("Category already exists. Try again.");
                continue;
            }
            category.setName(name);
            categoryNames.add(name);
            System.out.print("Enter the budgeted amount for this category: ");
            category.setBudgetedAmount(Double.parseDouble(System.console().readLine()));
            categories.add(category);
            System.out.print("Would you like to add another category? (y/n) ");
            String response = System.console().readLine();
            if(response.equals("n")){
                keepRunning = false;
            }
        }
    }

    public Stack<Category> getCategories() {
        return categories;
    }

    public double getTotalBudgetedAmount() {
        totalBudgetedAmount = 0.0;
        for (Category category : categories) {
            totalBudgetedAmount += category.getBudgetedAmount();
        }
        return totalBudgetedAmount;
    }

    public double getTotalExpenses() {
        totalExpenses = 0.0;
        for (Category category : categories) {
            totalExpenses += category.getSumExpenses();
        }
        return totalExpenses;
    }

    public void viewCategories() {
        if(categories.size() == 0){
            System.out.println("No categories found.");
        }else{
            for (Category category : categories) {
                System.out.println(category.getName() + "-" + category.getSumExpenses() + "/" + category.getBudgetedAmount());
            }
        }
        System.out.println("Would you like to view expenses for a specific category? (y/n)");
        String response = System.console().readLine();
        if(response.equals("y")){
            viewExpenses();
        }
    }

    public void viewExpenses(){
        int i = 1;
        System.out.println("Here's the list of categories: ");
        for(Category category : categories){
            System.out.println(i + ". " +category.getName());
            i++;
        }
        System.out.println("Enter the number of the category you want to view expenses for: ");
        Category chosenCategory = categories.get(Integer.parseInt(System.console().readLine()) - 1);
        if(chosenCategory == null){
            System.out.println("Category not found.");
            System.console().readLine();
            return;
        }
        chosenCategory.listExpenses();
        System.out.println("Would you like to add an expense to this category? (y/n)");
        String response2 = System.console().readLine();
        if(response2.equals("y")){
            chosenCategory.addExpense();
        }
    }


    public void addExpense() {
        int i = 1;
        for (Category category : categories) {
            System.out.println(i + ". " + category.getName());
            i++;
        }
        System.out.println("Enter the number of the category you want to add an expense to: ");
        Category chosenCategory = categories.get(Integer.parseInt(System.console().readLine()) - 1);
        if(chosenCategory == null){
            System.out.println("Category not found.");
            System.console().readLine();
            return;
        }
        chosenCategory.addExpense();
    }

    
}
