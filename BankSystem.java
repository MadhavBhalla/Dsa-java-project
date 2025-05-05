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

    public void printHistory() {
        System.out.println("Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public Transaction[] toArray() {
        return transactions.toArray(new Transaction[0]);
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
}

// Sorting Algorithms
class SortUtils {
    public static void bubbleSort(Transaction[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j].amount > arr[j + 1].amount) {
                    Transaction temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
    }

    public static void mergeSort(Transaction[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(Transaction[] arr, int l, int m, int r) {
        Transaction[] left = Arrays.copyOfRange(arr, l, m + 1);
        Transaction[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;

        while (i < left.length && j < right.length)
            arr[k++] = (left[i].amount <= right[j].amount) ? left[i++] : right[j++];

        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }
}

// Searching Algorithms
class SearchUtils {
    public static int linearSearch(Transaction[] arr, double target) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i].amount == target)
                return i;
        return -1;
    }

    public static int binarySearch(Transaction[] arr, double target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid].amount == target) return mid;
            if (arr[mid].amount < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
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
            System.out.println("\n1. Deposit\n2. Withdraw\n3. Print History\n4. Sort History\n5. Search Transaction\n6. Exit");
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
                    System.out.print("Enter account number: ");
                    int accNo = sc.nextInt();
                    BankAccount acc = bst.search(accNo);
                    if (acc != null) {
                        acc.printDetails();
                        acc.history.printHistory();
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter account number: ");
                    int accNo = sc.nextInt();
                    BankAccount acc = bst.search(accNo);
                    if (acc != null) {
                        Transaction[] arr = acc.history.toArray();
                        System.out.print("Sort using (1. Bubble Sort, 2. Merge Sort): ");
                        int sortChoice = sc.nextInt();
                        if (sortChoice == 1)
                            SortUtils.bubbleSort(arr);
                        else
                            SortUtils.mergeSort(arr, 0, arr.length - 1);

                        System.out.println("Sorted Transactions:");
                        for (Transaction t : arr) System.out.println(t);
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter account number: ");
                    int accNo = sc.nextInt();
                    BankAccount acc = bst.search(accNo);
                    if (acc != null) {
                        Transaction[] arr = acc.history.toArray();
                        SortUtils.mergeSort(arr, 0, arr.length - 1); // binary search needs sorted array

                        System.out.print("Enter amount to search: ");
                        double amt = sc.nextDouble();
                        System.out.print("Search using (1. Linear, 2. Binary): ");
                        int method = sc.nextInt();

                        int result = (method == 1)
                                ? SearchUtils.linearSearch(arr, amt)
                                : SearchUtils.binarySearch(arr, amt);

                        if (result >= 0)
                            System.out.println("Transaction found: " + arr[result]);
                        else
                            System.out.println("Transaction not found.");
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
