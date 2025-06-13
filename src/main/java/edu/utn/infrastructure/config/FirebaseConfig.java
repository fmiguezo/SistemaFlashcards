//package edu.utn.infrastructure.config;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.context.annotation.Configuration;
//import javax.annotation.PostConstruct;
//import java.io.InputStream;
//
//@Configuration
//public class FirebaseConfig {
//
//    @PostConstruct
//    public void init() throws Exception {
//
//        // 1. Se carga la clave de servicio de Firebase
//        InputStream serviceAccount = getClass()
//                .getClassLoader()
//                .getResourceAsStream("service-account.json");
//
//        // 2. Se inicializa la aplicación Firebase con las credenciales
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://flashcard-ea570-default-rtdb.firebaseio.com")
//                .build();
//
//        if (FirebaseApp.getApps().isEmpty()) {
//            FirebaseApp.initializeApp(options);
//            System.out.println("✅ Firebase inicializado correctamente.");
//        }
//    }
//}
