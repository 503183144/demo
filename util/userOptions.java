package demo;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import demo.serviceLayer.Service;
import org.json.JSONObject;

import java.util.Scanner;

public class UserOptions {

    public static void askUser() {
        Service helper = new Service();

        System.out.println(
                "Welcome to the GE Login window.\n" +
                        "Please choose one of the options:\n" +
                        "1. Add a new user\n" +
                        "2. Authenticate a user and display its buckets\n"
        );

        int choice = 0;

        Scanner sc = new Scanner(System.in);
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException exp) {
            System.out.println("Please choose an option by number (ex: 1 or 2).\n");
            System.exit(1);
        }
        switch (choice) {
            case 1:
                CreateUser(helper);
                break;
            case 2:
                ValidateUser(helper);
                break;
            default:
                System.out.println("Valid choices are 1, 2, or 3.");
        }
    }

    private static void CreateUser(Service helper) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter a username: ");
        String username = sc.nextLine();
        System.out.println("Please enter a password: ");
        String password = sc.nextLine();
        System.out.println("Please enter an email: ");
        String email = sc.nextLine();

        boolean success = helper.SignUpUser(username, password, email);
        if (success) {
            System.out.println("User added.");
            System.out.println("Enter your validation code on email link: ");

            String code = sc.nextLine();
            helper.VerifyAccessCode(username, code);
            System.out.println("User verification succeeded.");
        } else {
            System.out.println("User creation failed.");
        }
    }

    private static void ValidateUser(Service helper) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the username: ");
        String username = sc.nextLine();

        System.out.println("Please enter the password: ");
        String password = sc.nextLine();

        String result = helper.ValidateUser(username, password);
        if (result != null) {
            System.out.println("User is authenticated: " + result);
        } else {
            System.out.println("Username/password is invalid.");
        }

        JSONObject payload = CognitoJWTParser.getPayload(result);
        String provider = payload.get("iss").toString().replace("https://", "");

        Credentials credentials = helper.GetCredentials(provider, result);
        ListBuckets(credentials);
    }

    private static void ListBuckets(Credentials credentials) {
        BasicSessionCredentials awsCreds = new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretKey(), credentials.getSessionToken());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();
        for (Bucket bucket : s3Client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
    }
}
