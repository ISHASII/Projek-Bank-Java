import java.io.*;
import java.time.LocalDate;
import java.util.*;

    // Struktur data untuk menyimpan informasi rekening bank
    class BankAccount implements Serializable {
      String accountNumber;
      String accountHolder;
      double balance;

          public BankAccount(String accountNumber, String accountHolder, double balance) {
              this.accountNumber = accountNumber;
              this.accountHolder = accountHolder;
              this.balance = balance;
          }

          public String getAccountNumber() {
            return accountNumber;
          }

          public void setAccountNumber(String accountNumber) {
                if (accountNumber.length() == 6) {
                    this.accountNumber = accountNumber;
                } 

                else {
                    System.out.println("Panjang Nomor Rekening harus 6 karakter.");
                }
          }

          public String getAccountHolder() {
                return accountHolder;
          }

          public void setAccountHolder(String accountHolder) {
                this.accountHolder = accountHolder;
          }

          public double getBalance() {
                return balance;
          }

          public void setBalance(double balance) {
                this.balance = balance;
          }

          // Mengembalikan informasi rekening dalam format string
          public String getAccountInfo() {
            return "Nomor Rekening: " + accountNumber + "\n" +
                "Nama Pemilik: " + accountHolder + "\n" +
                "Jumlah:Rp " + balance + "\n" +
                "---------------------------";
          }
    }

    // Linked list untuk menyimpan daftar rekening bank
    class BankLinkedList {
        Node head;

          class Node {
            BankAccount data;
            Node next;

              Node(BankAccount d) {
                data = d;
                next = null;
              }
          }

          // Menambahkan rekening baru ke dalam linked list
          public void addAccount(BankAccount bankAccount) {
                Node newNode = new Node(bankAccount);
                  if (head == null) {
                    head = newNode;
                  } 
                  else {
                    Node last = head;
                        while (last.next != null) {
                        last = last.next;
                        }
                    last.next = newNode;
                  }
          }

          // Menampilkan semua rekening dalam linked list
          public void displayAccounts() {
              Node current = head;
              while (current != null) {
                System.out.println(" Nomor Rekening: " + current.data.accountNumber);
                System.out.println(" Nama Pemilik: " + current.data.accountHolder);
                System.out.println(" Saldo:Rp " + current.data.balance);
                System.out.println("--------------------------------------------------");
                current = current.next;
              }
          }

          // Mengurutkan rekening berdasarkan saldo menggunakan bubble sort
          public void sortByBalance() {
              if (head == null || head.next == null) {
                return;
              }
                boolean swapped;

                do {
                  swapped = false;
                  Node current = head;
                  Node previous = null;

                    while (current != null && current.next != null) {
                      if (current.data.balance > current.next.data.balance) {
                        if (previous == null) {
                          head = current.next;
                        } else {
                          previous.next = current.next;
                        }
                        Node nextNode = current.next.next;
                        current.next.next = current;
                        current.next = nextNode;
                        swapped = true;
                      }
                    previous = current;
                    current = current.next;
                    }
                } 
                  while (swapped);
        }

          // Mencari rekening berdasarkan nomor rekening
          public BankAccount searchAccount(String accountNumber) {
                Node current = head;
                  while (current != null) {
                    if (current.data.accountNumber.equals(accountNumber)) {
                      return current.data;
                    }
                  current = current.next;
                  }
            return null;
          }

          // Simpan data rekening ke dalam file
          public void writeToFile(String filename) {
                try {
                  PrintWriter writer = new PrintWriter(filename);

                    Node current = head;
                    while (current != null) {
                      BankAccount account = current.data;
                      String accountInfo = account.getAccountInfo();
                      writer.println(accountInfo);

                        current = current.next;
                    }
                writer.close();
                System.out.println("Data Disimpan Kedalam File: " + filename);
               } 
              catch (IOException e) {
                e.printStackTrace();
             }
          }

          // Baca data rekening dari file
          public void readFromFile(String filename) {
                try {
                  File file = new File(filename);
                  Scanner scanner = new Scanner(file);

                      while (scanner.hasNextLine()) {
                        String accountNumber = scanner.nextLine().replace("Nomor Rekening: ", "");
                        String accountHolder = scanner.nextLine().replace("Nama Pemilik: ", "");
                        double balance = Double.parseDouble(scanner.nextLine().replace("Saldo:Rp ", ""));

                        BankAccount account = new BankAccount(accountNumber, accountHolder, balance);
                        addAccount(account);

                        scanner.nextLine(); // Baca baris kosong
                      }
                        scanner.close();
                        System.out.println("Data Dibaca Dari File " + filename);
                } 

                catch (FileNotFoundException e) {
                  System.out.println("Data File Tidak Ditemukan: " + filename);
                }
          }

          // Rekursi untuk menghitung total saldo
          public double calculateTotalBalance(Node current) {
              if (current == null) {
                return 0;
              }
            return current.data.balance + calculateTotalBalance(current.next);
          }

          // Tarik tunai dari rekening
          public void withdraw(String accountNumber, double amount) {
                Node current = head;
                  while (current != null) {
                    if (current.data.accountNumber.equals(accountNumber)) {
                      if (current.data.balance >= amount) {
                        current.data.balance -= amount;
                        System.out.println("Penarikan berhasil dilakukan.");
                        System.out.println("Saldo terkini:Rp " + current.data.balance);
                      }   

                      else {
                        System.out.println("Saldo tidak mencukupi.");
                      }
                      return;
                    }
                    current = current.next;
                }
            System.out.println("Rekening dengan nomor " + accountNumber + " tidak ditemukan.");
          }

          // Setor tunai ke rekening
          public void deposit(String accountNumber, double amount) {
            Node current = head;
                  while (current != null) {
                    if (current.data.accountNumber.equals(accountNumber)) {
                      current.data.balance += amount;
                        System.out.println("Setor tunai berhasil dilakukan.");
                        System.out.println("Saldo terkini:Rp " + current.data.balance);
                      return;
                    }
                    current = current.next;
                  }
                  System.out.println("Rekening dengan nomor " + accountNumber + " tidak ditemukan.");
            }
    }

        public class Bank {
          public static void main(String[] args) {
            BankLinkedList bankLinkedList = new BankLinkedList();
            Scanner scanner = new Scanner(System.in);

            // Menampilkan menu
            int choice = 0;
            while (choice != 6) {
              System.out.println("--------------------------------------------------");
              System.out.println("|              SELAMAT DATANG DI BANK            |");
              System.out.println("|                       MENU                     |");
              System.out.println("--------------------------------------------------");
              System.out.println("| 1. Tambah Rekening                             |");
              System.out.println("| 2. Tampilkan Rekening                          |");
              System.out.println("| 3. Cari Rekening Berdasarkan Nomor             |");
              System.out.println("| 4. Tarik Tunai                                 |");
              System.out.println("| 5. Setor Tunai                                 |");
              System.out.println("| 6. Keluar                                      |");
              System.out.println("--------------------------------------------------");
              System.out.println("|       Deft Valian Exanova & Ilham Saputra      |");
              System.out.println("--------------------------------------------------");
              System.out.print("Pilihan Anda: ");
              choice = scanner.nextInt();
              scanner.nextLine(); // Membersihkan new line
                switch (choice) {
                  
                  case 1:
                    System.out.print("Nomor Rekening (6 karakter): ");
                    String accountNumber = scanner.nextLine();

                      if (accountNumber.length() != 6) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        System.out.println("Panjang Nomor Rekening harus 6 karakter.");
                        break;
                      }

                        System.out.print("Nama: ");
                        String accountHolder = scanner.nextLine();
                        System.out.print("Saldo:Rp ");
                        double balance = scanner.nextDouble();
                        scanner.nextLine();

                        BankAccount newAccount = new BankAccount(accountNumber, accountHolder, balance);
                        bankLinkedList.addAccount(newAccount);

                          System.out.print("\033[H\033[2J");
                          System.out.flush();
                          System.out.println("--------------------------------------------------");
                          System.out.println("|         Rekening berhasil ditambahkan!         |");
                          System.out.println("--------------------------------------------------");
                          break;

                    case 2:
                      System.out.print("\033[H\033[2J");
                      System.out.flush();
                          if (bankLinkedList.head == null) {
                              System.out.println("Tidak ada rekening yang tersimpan.");
                          }

                          else {
                            bankLinkedList.sortByBalance();
                              System.out.println("--------------------------------------------------");
                              System.out.println("|                 Daftar Rekening                |");
                              System.out.println("--------------------------------------------------");
                              bankLinkedList.displayAccounts();
                          }
                          break;

                    case 3:
                      System.out.print("Nomor Rekening yang Dicari: ");
                      String searchAccountNumber = scanner.nextLine();
                          BankAccount searchedAccount = bankLinkedList.searchAccount(searchAccountNumber);
                          if (searchedAccount != null) {
                            System.out.print("\033[H\033[2J");
                            System.out.flush();

                            System.out.println("Pencarian dilakukan pada " + LocalDate.now());
                            System.out.println("Informasi Rekening dengan Nomor " + searchAccountNumber );
                            System.out.println("Pemilik Rekening: " + searchedAccount.accountHolder);
                            System.out.println("Saldo:Rp " + searchedAccount.balance);
                          } 
                          else {
                            System.out.println("Rekening dengan Nomor " + searchAccountNumber + " tidak ditemukan.");
                          }
                        System.out.println("--------------------------------------------------");
                          break;

                    case 4:
                        System.out.print("Nomor Rekening: ");
                        String withdrawAccountNumber = scanner.nextLine();
                        System.out.print("Jumlah Penarikan:Rp ");
                        double withdrawalAmount = scanner.nextDouble();

                          scanner.nextLine(); // Membersihkan new line
                          System.out.print("\033[H\033[2J");
                          System.out.flush();

                          System.out.println("--------------------------------------------------");
                          System.out.println("Penarikan Dilakukan Pada " + LocalDate.now());
                          bankLinkedList.withdraw(withdrawAccountNumber, withdrawalAmount);
                          System.out.println("--------------------------------------------------");
                          break;

                    case 5:
                      System.out.print("Nomor Rekening: ");
                      String depositAccountNumber = scanner.nextLine();
                      System.out.print("Jumlah Setoran:Rp ");
                      double depositAmount = scanner.nextDouble();

                        scanner.nextLine(); // Membersihkan new line
                        System.out.print("\033[H\033[2J");
                        System.out.flush();

                          System.out.println("--------------------------------------------------");
                          System.out.println("Setor Tunai Dilakukan Pada " +LocalDate.now());
                          bankLinkedList.deposit(depositAccountNumber, depositAmount);
                          System.out.println("--------------------------------------------------");
                          break;

                    case 6:
                        // Simpan data rekening ke dalam file sebelum keluar
                        String filename = "DataBank.txt";
                        bankLinkedList.writeToFile(filename);
                        System.out.println("Data disimpan ke dalam file: " + filename + " - " + LocalDate.now());

                        // Baca data rekening dari file
                        bankLinkedList.readFromFile(filename);
                        System.out.println("Daftar Rekening setelah dibaca dari file:");
                        bankLinkedList.displayAccounts();
                        break;

                      default:
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        break;
                }
            }

            scanner.close();
          }
        }