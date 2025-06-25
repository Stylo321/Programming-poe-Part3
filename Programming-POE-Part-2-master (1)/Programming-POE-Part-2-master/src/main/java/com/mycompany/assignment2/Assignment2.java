



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



package com.mycompany.assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.File;
import javax.swing.JOptionPane;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;



public class Assignment2 {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    
        private static final String FILE_PATH = "messages.json";
    private static int totalMessages = 0;
    private static JSONArray sentMessages = new JSONArray();

    public String registerUser() {
        // First Name
        do {
            firstName = JOptionPane.showInputDialog("Enter your first name:");
        } while (firstName == null || firstName.trim().isEmpty());

        // Last Name
        do {
            lastName = JOptionPane.showInputDialog("Enter your last name:");
        } while (lastName == null || lastName.trim().isEmpty());

        // Username with validation
        do {
            username = JOptionPane.showInputDialog("Enter a username:");
            if (!checkUserName()) {
                JOptionPane.showMessageDialog(null, "❌ Username is not correctly formatted.\n please ensure that your username contains an underscore \n and is no more than 5 characters");
            
            }
        } while (!checkUserName());

        // Password with validation
        do {
            password = getPasswordWithToggle();
            if (!checkPasswordComplexity()) {
                JOptionPane.showMessageDialog(null, "❌ Password is not correctly formatted.\nIt must be at least 8 characters long, contain a capital letter, a number, and a special character.");
            }
            else{
                JOptionPane.showMessageDialog(null, "Password successfully captured");
            }
        } while (!checkPasswordComplexity());

        // Cell number with validation
        do {
            cellNumber = JOptionPane.showInputDialog("Enter your cell number:");
            if (!checkCellPhoneNumber()) {
                JOptionPane.showMessageDialog(null, "❌ Cell number is not correctly formatted.\nIt must start with +27 and be no more than 10 digits after that.");
            }
        } while (!checkCellPhoneNumber());

// After phone number validation
boolean otpVerified = verifyOTP(cellNumber);
while (!otpVerified) {
    cellNumber = JOptionPane.showInputDialog("Enter a new phone number (e.g. +2783123456):");
    if (!checkRecipientCell(cellNumber)) {
        JOptionPane.showMessageDialog(null, "❌ Invalid number. Must start with +27 and be ≤ 13 chars.");
        continue;
    }
    otpVerified = verifyOTP(cellNumber);
}


return "✅ Welcome " +firstName+ "," +lastName+ " you have registered successfully!";

    }

    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity() {
        if (password == null) return false;

        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[A-Z].*");
        boolean number = password.matches(".*\\d.*");
        boolean special = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        return length && capital && number && special;
    }
    
    public static String getPasswordWithToggle() {
    // Create password field
    JPasswordField passwordField = new JPasswordField(15);
    passwordField.setEchoChar('•');

    // Create toggle button (show/hide)
    JButton toggleButton = new JButton("👁️");
    toggleButton.setFocusable(false);
    toggleButton.setMargin(new Insets(0, 5, 0, 5));

    toggleButton.addActionListener(e -> {
        if (passwordField.getEchoChar() == '•') {
            passwordField.setEchoChar((char) 0); // Show password
            toggleButton.setText("🙈");
        } else {
            passwordField.setEchoChar('•'); // Hide password
            toggleButton.setText("👁️");
        }
    });

    // Combine in panel
    JPanel panel = new JPanel(new BorderLayout(5, 5));
    panel.add(passwordField, BorderLayout.CENTER);
    panel.add(toggleButton, BorderLayout.EAST);

    int result = JOptionPane.showConfirmDialog(null, panel, "Enter your password",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        return new String(passwordField.getPassword());
    } else {
        return null;
    }
}

    public boolean checkCellPhoneNumber() {
        return cellNumber != null && cellNumber.startsWith("+27") && cellNumber.length() <= 13;
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        return inputUsername.equals(username) && inputPassword.equals(password);
    }

    public String returnLoginStatus(boolean isLoginSuccessful) {
        if (isLoginSuccessful) {
            return "✅ Welcome " + firstName + " " + lastName + ", it is great to see you again!";
        } else {
            return "❌ Username or password is incorrect.";
        }
    }
    
    
    //************************************************play sound
    public static void playSound(String path) {
    try {
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(new File(path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    } catch (Exception e) {
        System.out.println("Sound error: " + e.getMessage());
    }
}
    public static void showSplashScreen() {
    // Play startup sound
    playSound("C/Users/silol/Downloads/Programming-POE-Part-2-master (1)/Programming-POE-Part-2-master/src/main/java/com/mycompany/mixkit-epic-orchestra-transition-2290.wav");  // 👈 Adjust path if needed

    JWindow splash = new JWindow();

    String splashText = """
      ____  _    _ _      ____ _           _   
     / __ \\| |  | | |    / __ \\ |         | |  
    | |  | | |  | | |   | |  | | |__   ___| |_ 
    | |  | | |  | | |   | |  | | '_ \\ / _ \\ __|
    | |__| | |__| | |___| |__| | | | |  __/ |_ 
     \\___\\_\\\\____/|______\\____/|_| |_|\\___|\\__|
                        
                        
                        
                        
              *****Welcome to QUICKCHAT*****
                                              
    """;

    JTextArea asciiArea = new JTextArea(splashText);
    asciiArea.setFont(new Font("Monospaced", Font.BOLD, 16));
    asciiArea.setBackground(Color.BLACK);
    asciiArea.setForeground(Color.GREEN);
    asciiArea.setEditable(false);

    splash.getContentPane().add(asciiArea);
    splash.setSize(520, 300);
    splash.setLocationRelativeTo(null);
    splash.setOpacity(0f);
    splash.setVisible(true);

    // Fade-in
    for (float i = 0f; i <= 1f; i += 0.05f) {
        splash.setOpacity(i);
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {}
    }

    try {
        Thread.sleep(1000); // Show for 1 sec
    } catch (InterruptedException e) {}

    splash.dispose();
}
    

    

    // MAIN METHOD TO RUN EVERYTHING
    public static void main(String[] args) {
        
       

    
    
        Assignment2 login = new Assignment2();
        JOptionPane.showMessageDialog(null, login.registerUser());
        
         showSplashScreen();

        boolean loggedIn = false;

        while (!loggedIn) {
            String inputUsername = JOptionPane.showInputDialog("Login - Enter your username:");
            String inputPassword = JOptionPane.showInputDialog("Login - Enter your password:");

            boolean loginResult = login.loginUser(inputUsername, inputPassword);
           
            if (loginResult) {
    JOptionPane.showMessageDialog(null, login.returnLoginStatus(true));

    // Show QUICKCHAT Splash
    String quickchatLogo = """
      ____  _    _ _      ____ _           _   
     / __ \\| |  | | |    / __ \\ |         | |  
    | |  | | |  | | |   | |  | | |__   ___| |_ 
    | |  | | |  | | |   | |  | | '_ \\ / _ \\ __|
    | |__| | |__| | |___| |__| | | | |  __/ |_ 
     \\___\\_\\\\____/|______\\____/|_| |_|\\___|\\__|
                                              
    """;

    JTextArea splash = new JTextArea(quickchatLogo);
    splash.setEditable(false);
    splash.setFont(new Font("Monospaced", Font.BOLD, 12));
    JOptionPane.showMessageDialog(null, splash, "💬 QUICKCHAT", JOptionPane.PLAIN_MESSAGE);
}

            if (loginResult) {
                loggedIn = true; // Exit the loop
            } else {
                int retry = JOptionPane.showConfirmDialog(null,
                        "Do you want to try logging in again?",
                        "Login Failed",
                        JOptionPane.YES_NO_OPTION);

                if (retry == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "👋 Exiting application. Goodbye!");
                    System.exit(0);
                }
            }
        }
     
                int maxMessages = 0;
    while (true) {
        String input = JOptionPane.showInputDialog("How many messages do you want to enter?");
        try {
            maxMessages = Integer.parseInt(input);
            if (maxMessages > 0) break;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Please enter a valid number greater than 0.");
        }
    }

    int messageCount = 0;
        loadMessagesFromFile();

while (true) {
    String menu = "WELCOME TO 'Q U I C K E C H A T' \n 📋 Please select an option:\n";
    menu += "1. Send a message" + (messageCount >= maxMessages ? " (Limit reached)" : "") + "\n";
    menu += "2. Show recently sent messages \n";
    menu += "3. Quit";

    String option = JOptionPane.showInputDialog(menu);

    if (option == null) break;

    switch (option) {
        case "1":
            if (messageCount >= maxMessages) {
                JOptionPane.showMessageDialog(null, "⚠️ You have reached your message limit (" + maxMessages + ").");
            } else {
                sendMessage();
                messageCount++;
            }
            break;
          case "2":
    String[] actions = {
        "1. View longest message",
        "2. Search by Message ID",
        "3. Search by Recipient",
        "4. Delete by Hash",
        "5. Full Sent Message Report",
        "Back"
    };
    while (true) {
        String action = (String) JOptionPane.showInputDialog(
            null, "Choose a feature to use:",
            "Advanced Message Features", JOptionPane.PLAIN_MESSAGE, null,
            actions, actions[0]);

        if (action == null || action.equals("Back")) break;

        switch (action) {
            case "1. View longest message": displayLongestMessage(); break;
            case "2. Search by Message ID": searchByMessageID(); break;
            case "3. Search by Recipient": searchByRecipient(); break;
            case "4. Delete by Hash": deleteMenu(); break;
            case "5. Full Sent Message Report": fullReport(); break;
        }
    }
    break;
  

        case "3":
            JOptionPane.showMessageDialog(null, "👋 Goodbye!" );
            System.exit(0);
            break;
        default:
            JOptionPane.showMessageDialog(null, "❌ Invalid option. Please choose 1, 2 or 3.");
    }
}



        JOptionPane.showMessageDialog(null, printMessages() + "\n\nTotal messages sent/stored: " + returnTotalMessages());
    }

    public static void sendMessage() {
        String messageID;
        do {
            messageID = JOptionPane.showInputDialog("Enter Message ID (max 10 chars):");
            if (!checkMessageID(messageID)) {
                JOptionPane.showMessageDialog(null, "❌ Message ID must not exceed 10 characters.");
            }
        } while (!checkMessageID(messageID));

        String recipient;
        do {
            recipient = JOptionPane.showInputDialog("Enter recipient number (must start with +27 and 13 chars max):");
            if (!checkRecipientCell(recipient)) {
                JOptionPane.showMessageDialog(null, "❌ Invalid recipient number. It must start with +27 and be max 13 characters.");
            }
        } while (!checkRecipientCell(recipient));

        String[] msgTypes = {"Short (max 50 chars)", "Long (max 250 chars)"};
int msgType = JOptionPane.showOptionDialog(
    null,
    "What type of message do you want to send?",
    "Choose Message Type",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,
    msgTypes,
    msgTypes[0]
);

int maxLength = (msgType == 0) ? 50 : 250;
String messageText;

while (true) {
    messageText = JOptionPane.showInputDialog("Enter your message (max " + maxLength + " characters):");

    if (messageText == null) {
        JOptionPane.showMessageDialog(null, "🚫 Message entry cancelled.");
        return;
    }

    if (messageText.length() <= maxLength) {
        break;
    } else {
        JOptionPane.showMessageDialog(null, "❌ Message too long!\nYou entered " + messageText.length() + " characters.\nLimit is " + maxLength + ".");
    }
}

String uniqueID = generateUniqueMessageID();
        String[] options = {"Send", "Store", "Discard"};
int choice = JOptionPane.showOptionDialog(
    null,
    "What would you like to do with the message?",
    "Choose Action",
    JOptionPane.DEFAULT_OPTION,
    JOptionPane.INFORMATION_MESSAGE,
    null,
    options,
    options[0]
);


        if (choice == 2) { // Discard
    JOptionPane.showMessageDialog(null, "🚫 Message discarded.");
}else {
            totalMessages++;
            String messageHash = createMessageHash(messageID, totalMessages, messageText);
            int encryptionKey = 5;
            String encryptedMsg = encrypt(messageText, encryptionKey);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = dtf.format(LocalDateTime.now());

            JSONObject messageObj = new JSONObject();
            messageObj.put("messageID", messageID);
            messageObj.put("recipient", recipient);
            messageObj.put("message", encryptedMsg);
            messageObj.put("encrypted", true);
            messageObj.put("hash", messageHash);
            messageObj.put("timestamp", timestamp);
            messageObj.put("status", (choice == 0) ? "Sent" : "Stored");

            sentMessages.put(messageObj);

    if (choice == 0) {
        JOptionPane.showMessageDialog(null, "✅ Message sent!\nHash: " + messageHash);
    } else if (choice == 1) {
        saveMessagesToFile();
        JOptionPane.showMessageDialog(null, "📥 Message stored!\nHash: " + messageHash);
    }
}
    }
public static boolean verifyOTP(String cellNumber) {
    Random rand = new Random();

    while (true) {
        int otp = 10000 + rand.nextInt(90000); // Generate new OTP
        JOptionPane.showMessageDialog(null, "📨 OTP sent to: " + cellNumber + "\n(For testing: " + otp + ")");

        for (int i = 0; i < 3; i++) {
            String input = JOptionPane.showInputDialog("Enter the 5-digit OTP sent to " + cellNumber + ":");

            if (input == null) return false; // Cancelled
            if (input.equals(String.valueOf(otp))) return true;

            JOptionPane.showMessageDialog(null, "❌ Incorrect OTP. Attempts left: " + (2 - i));
        }

        // Basically show options if user fails to enter OTP  3 times
        String[] options = {"Exit App", "Resend OTP", "Change Number"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "You entered the wrong OTP 3 times.\nWhat would you like to do?",
            "OTP Verification Failed",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[1]
        );

        if (choice == 0) { // Exit App
            JOptionPane.showMessageDialog(null, "👋 Exiting the application.");
            System.exit(0);
        } else if (choice == 2) { // Change number
            return false; // Signal that we need to re-enter phone number
        }
        // else choice == 1 → resend OTP loop restarts
    }

}


    public static boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public static boolean checkRecipientCell(String cell) {
        return cell != null && cell.startsWith("+27") && cell.length() <= 13;
    }

    public static String createMessageHash(String id, int msgNum, String message) {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        String idStart = id.length() >= 2 ? id.substring(0, 2) : id;
        return (idStart + ":" + msgNum + ":" + firstWord + lastWord).toUpperCase();
    }

    public static void saveMessagesToFile() {
        JSONObject data = new JSONObject();
        data.put("messages", sentMessages);
        data.put("totalMessages", totalMessages);

        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(data.toString(4));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving messages: " + e.getMessage());
        }
    }
    
    public static void displayLongestMessage() {
    if (sentMessages.length() == 0) {
        JOptionPane.showMessageDialog(null, "No messages found.");
        return;
    }

    JSONObject longest = sentMessages.getJSONObject(0);
    int maxLength = decrypt(longest.getString("message"), 5).length();

    for (int i = 1; i < sentMessages.length(); i++) {
        JSONObject current = sentMessages.getJSONObject(i);
        int length = decrypt(current.getString("message"), 5).length();
        if (length > maxLength) {
            maxLength = length;
            longest = current;
        }
    }

    String details = "📨 Longest Message\n" +
        "Sender ID: " + longest.getString("messageID") + "\n" +
        "Recipient: " + longest.getString("recipient") + "\n" +
        "Status: " + longest.optString("status", "Unknown") + "\n" +
        "Message: " + decrypt(longest.getString("message"), 5);
    
    JOptionPane.showMessageDialog(null, details);
}
public static void searchByMessageID() {
    String searchID = JOptionPane.showInputDialog("Enter Message ID to search:");
    for (int i = 0; i < sentMessages.length(); i++) {
        JSONObject msg = sentMessages.getJSONObject(i);
        if (msg.getString("messageID").equals(searchID)) {
            String decryptedMsg = decrypt(msg.getString("message"), 5);
            JOptionPane.showMessageDialog(null, "📍 Found:\nRecipient: " + msg.getString("recipient") + "\nMessage: " + decryptedMsg);
            return;
        }
    }
    JOptionPane.showMessageDialog(null, "❌ Message ID not found.");
}

public static void searchByRecipient() {
    String target = JOptionPane.showInputDialog("Enter recipient number to find all messages sent to them:");
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < sentMessages.length(); i++) {
        JSONObject msg = sentMessages.getJSONObject(i);
        if (msg.getString("recipient").equals(target)) {
            String decrypted = decrypt(msg.getString("message"), 5);
            result.append("- ").append(decrypted).append("\n");
        }
    }
    if (result.length() == 0) {
        JOptionPane.showMessageDialog(null, "No messages found for that recipient.");
    } else {
        JOptionPane.showMessageDialog(null, "Messages for " + target + ":\n" + result.toString());
    }
}
public static void deleteMenu() {
    String[] options = {"Delete by Hash", "Delete by Recipient", "Delete All", "Cancel"};
    int choice = JOptionPane.showOptionDialog(
        null,
        "Select a delete option:",
        "Delete Menu",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.WARNING_MESSAGE,
        null,
        options,
        options[0]
    );

    switch (choice) {
        case 0: // Delete by Hash
            String hash = JOptionPane.showInputDialog("Enter the message hash to delete:");
            for (int i = 0; i < sentMessages.length(); i++) {
                JSONObject msg = sentMessages.getJSONObject(i);
                if (msg.getString("hash").equalsIgnoreCase(hash)) {
                    sentMessages.remove(i);
                    totalMessages--;
                    saveMessagesToFile();
                    
                    String deletedMsg = decrypt(msg.getString("message"), 5);
JOptionPane.showMessageDialog(null, "🗑️ Message \"" + deletedMsg + "\" has been deleted successfully.");

    
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "❌ Hash not found.");
            break;

        case 1: // Delete by Recipient
            String recipient = JOptionPane.showInputDialog("Enter the recipient number to delete their messages:");
            boolean found = false;
            for (int i = sentMessages.length() - 1; i >= 0; i--) {
                if (sentMessages.getJSONObject(i).getString("recipient").equals(recipient)) {
                    sentMessages.remove(i);
                    totalMessages--;
                    found = true;
                }
            }
            if (found) {
                saveMessagesToFile();
                JOptionPane.showMessageDialog(null, "✅ All messages to " + recipient + " have been deleted.");
            } else {
                JOptionPane.showMessageDialog(null, "❌ No messages found for that recipient.");
            }
            break;

        case 2: // Delete All
            int confirm = JOptionPane.showConfirmDialog(null,
                "⚠️ Are you sure you want to delete ALL messages permanently?",
                "Confirm Delete All",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                sentMessages = new JSONArray();
                totalMessages = 0;
                saveMessagesToFile();
                JOptionPane.showMessageDialog(null, "💣 All messages permanently deleted.");
            }
            break;

        default:
            JOptionPane.showMessageDialog(null, "❌ Delete action cancelled.");
    }
}


    public static void loadMessagesFromFile() {


        File file = new File(FILE_PATH);

        if (file.exists()) {
            try {
                String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                JSONObject data = new JSONObject(content);
                sentMessages = data.getJSONArray("messages");
                totalMessages = data.getInt("totalMessages");
            } catch (IOException | JSONException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Could not load previous messages.\nStarting fresh.");
                sentMessages = new JSONArray();
                totalMessages = 0;
            }
        }
    }
public static void fullReport() {
    if (sentMessages.length() == 0) {
        JOptionPane.showMessageDialog(null, "No messages to report.");
        return;
    }

    StringBuilder report = new StringBuilder("📋 Full Message Report:\n");
    for (int i = 0; i < sentMessages.length(); i++) {
        JSONObject msg = sentMessages.getJSONObject(i);
        report.append("\nMessage #").append(i + 1).append(":\n")

              .append("Message ID: ").append(msg.getString("messageID")).append("\n")
              .append("Recipient: ").append(msg.getString("recipient")).append("\n")
              .append("Hash: ").append(msg.getString("hash")).append("\n")
              
              .append("Status: ").append(msg.optString("status", "Unknown")).append("\n")
              .append("Message: ").append(decrypt(msg.getString("message"), 5)).append("\n");
    }

     JTextArea textArea = new JTextArea(report.toString());
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

    JOptionPane.showMessageDialog(null, scrollPane, "📋 Full Message Report", JOptionPane.INFORMATION_MESSAGE);
}



    public static String encrypt(String message, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            encrypted.append((char)(c ^ key));
        }
        return encrypted.toString();
    }

    public static String decrypt(String encrypted, int key) {
        return encrypt(encrypted, key); // XOR twice = original
    }

    public static String printMessages() {
        StringBuilder sb = new StringBuilder("📬 Sent Messages:\n");
        for (int i = 0; i < sentMessages.length(); i++) {
            JSONObject msg = sentMessages.getJSONObject(i);
            String msgText = msg.optBoolean("encrypted", false)
                ? decrypt(msg.getString("message"), 5)
                : msg.getString("message");
            
            

            sb.append((i + 1) + ". ID: " + msg.getString("messageID")
                    + ", Recipient: " + msg.getString("recipient")
                    + ", Hash: " + msg.getString("hash")
                    + ", Time: " + msg.getString("timestamp")
                    + ", Message: " + msgText + "\n");
        }
        return sb.toString();
    }

   public static String generateUniqueMessageID(){
       Random rand = new Random();
       StringBuilder id = new StringBuilder();
       for (int i = 0; i < 10; i++) {
           id.append(rand.nextInt(10));
       }
       return id.toString();
   }
    public static int returnTotalMessages() {
        return totalMessages;
    }
}
