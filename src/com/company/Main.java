package com.company;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
    }

    public static void Ejercicio1() {

        Cifrado cifrado = new Cifrado();
        Scanner scanner = new Scanner(System.in);
        int key1 = 1024;

        System.out.println("Introduce texto :  ");
        String texto = scanner.nextLine();
        KeyPair keyPair = Cifrado.randomGenerate(key1);
        keyPair.getPrivate();
        keyPair.getPublic();

        Cifrado.randomGenerate(key1);

        final byte[] encrypt = Cifrado.encryptData(texto.getBytes(), keyPair.getPublic());
        final byte[] desencrypt = Cifrado.decryptData(encrypt, keyPair.getPrivate());

        System.out.println("Text encrypt: " + new String(encrypt));
        System.out.println("Text desencrypt: " + new String(desencrypt));

    }

    public static void Ejercicio2() throws Exception {

        Cifrado cifrado = new Cifrado();
        Scanner scanner = new Scanner(System.in);
        String password = "1234";
        KeyStore loadKeyStore = Cifrado.loadKeyStore("/home/daniel/Escritorio/dani.ks", password);

        System.out.println("Tipos de keystore: " + loadKeyStore.getType());
        System.out.println("Medida del almacenamiento: " + loadKeyStore.size());
        Enumeration enumeration = loadKeyStore.aliases();

        while (enumeration.hasMoreElements()) {
            System.out.println("alias: " + enumeration.nextElement());
        }
        System.out.print("Pon un alias? ");
        String alias = scanner.next();
        System.out.println("Certificado : " + loadKeyStore.getCertificate(alias));

        SecretKey secretKey = Cifrado.keygenKeyGeneration(128);
        KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(password.toCharArray());
        loadKeyStore.setEntry("key1", secretKeyEntry, protectionParameter);
        loadKeyStore.store(new FileOutputStream("/home/daniel/Escritorio/dani.ks"), "usuari".toCharArray());


    }
    public static void Ejercicio3() throws FileNotFoundException, CertificateException {

        String documento = ("/home/daniel/Escritorio/jordi.cer");

        try {
            PublicKey publicKey = Cifrado.getPublicKey(documento);
            System.out.println(publicKey);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("No hay nada");
        }

    }

    public static void Ejercicio4() {

        String ksFile = "/home/daniel/Escritorio/dani.ks";
        String alias = "lamevaclaum9";
        String password = "1234";

        try {
            KeyStore keyStore = Cifrado.loadKeyStore(ksFile, password);
            PublicKey publicKey = Cifrado.getPublicKey(keyStore, alias, password);
            System.out.println(publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void Ejercicio5() {

        KeyPair keyPair = Cifrado.randomGenerate(1024);

        final byte[] firma = Cifrado.signData("prueba".getBytes(), keyPair.getPrivate());
        System.out.println(new String(firma));
    }

    public static void Ejercicio6() {

        KeyPair keyPair= Cifrado.randomGenerate(1024);

        final byte[] texto = "Prueba".getBytes();
        final byte[] firma = Cifrado.signData(texto, keyPair.getPrivate());

    }

    public static void Parte2() throws IOException {

        Path path = Paths.get("/home/daniel/Descargas/textamagat");
        byte[] textenbytes = Files.readAllBytes(path);
        File file = new File("/home/daniel/Escritorio/clausA4.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader br =  new BufferedReader(fileReader);
        String line = br.readLine();
        while(line != null ) {
            try {
                SecretKey skey = Cifrado.keygenKeyGeneration(128);
                System.out.println("Password: " + line);
                System.out.println("Desencryptado: ");
                break;
            } catch (Exception e) {
                System.out.println("Error! "+line+"");
                line = br.readLine();
            }
        }
    }
}
