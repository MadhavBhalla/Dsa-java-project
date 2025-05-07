import java.util.*;

// Transaction class
class Transaction {
    String type;
    double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type + ": $" + amount;
    }
}

// LinkedList for transaction history
class TransactionHistory {
    LinkedList<Transaction> transactions = new LinkedList<>();

    public void add(Transaction t) {
        transactions.add(t);
    }
}

// Queue for pending transactions
class TransactionQueue {
    Queue<Transaction> queue = new LinkedList<>();

    public void enqueue(Transaction t) {
        queue.offer(t);
    }

    public Transaction dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Bank Account
class BankAccount {
    int accountNumber;
    double balance;
    TransactionHistory history = new TransactionHistory();
    TransactionQueue pendingQueue = new TransactionQueue();

    public BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        Transaction t = new Transaction("Deposit", amount);
        history.add(t);
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }
        balance -= amount;
        Transaction t = new Transaction("Withdraw", amount);
        history.add(t);
    }

    public double getBalance() {
        return balance;
    }

    public void printDetails() {
        System.out.println("Account #" + accountNumber + " | Balance: $" + balance);
    }
}

// Binary Search Tree Node
class BSTNode {
    BankAccount account;
    BSTNode left, right;

    public BSTNode(BankAccount account) {
        this.account = account;
    }
}

// BST for Bank Accounts
class AccountBST {
    BSTNode root;

    public void insert(BankAccount acc) {
        root = insertRec(root, acc);
    }

    private BSTNode insertRec(BSTNode root, BankAccount acc) {
        if (root == null) return new BSTNode(acc);
        if (acc.accountNumber < root.account.accountNumber)
            root.left = insertRec(root.left, acc);
        else
            root.right = insertRec(root.right, acc);
        return root;
    }

    public BankAccount search(int accNo) {
        return searchRec(root, accNo);
    }

    private BankAccount searchRec(BSTNode root, int accNo) {
        if (root == null) return null;
        if (accNo == root.account.accountNumber)
            return root.account;
        return accNo < root.account.accountNumber
                ? searchRec(root.left, accNo)
                : searchRec(root.right, accNo);
    }

    // Method to calculate total balance of all accounts
    public double getTotalBalance() {
        return calculateTotalBalance(root);
    }

    private double calculateTotalBalance(BSTNode node) {
        if (node == null) return 0;
        return node.account.getBalance() 
                + calculateTotalBalance(node.left) 
                + calculateTotalBalance(node.right);
    }
}

// Main Class
public class BankSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountBST bst = new AccountBST();

        System.out.print("Enter number of accounts to create: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter account number and initial balance: ");
            int accNo = sc.nextInt();
            double balance = sc.nextDouble();
            bst.insert(new BankAccount(accNo, balance));
        }

        boolean running = true;
        while (running) {
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Show Total Balance\n4. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter account number: ");
                    int accNo = sc.nextInt();
                    BankAccount acc = bst.search(accNo);
                    if (acc != null) {
                        System.out.print("Enter deposit amount: ");
                        double amount = sc.nextDouble();
                        acc.deposit(amount);
                        System.out.println("Deposited.");
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 2 -> {
                    System.out.print("Enter account number: ");
                    int accNo = sc.nextInt();
                    BankAccount acc = bst.search(accNo);
                    if (acc != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double amount = sc.nextDouble();
                        acc.withdraw(amount);
                        System.out.println("Withdrawn.");
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 3 -> {
                    double totalBalance = bst.getTotalBalance();
                    System.out.println("Total Balance of All Accounts: $" + totalBalance);
                }
                case 4 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}

