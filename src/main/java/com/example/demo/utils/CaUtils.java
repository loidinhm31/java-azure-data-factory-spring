package com.example.demo.utils;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;

import javax.security.auth.x500.X500Principal;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class CaUtils {
    public static void readCaInfo(X509CertificateHolder x509CertificateHolder) throws CertificateException {
        X509Certificate signerCertificate = new JcaX509CertificateConverter()
                .getCertificate(x509CertificateHolder);

        Date validationDate = signerCertificate.getNotBefore();
        Date expirationDate = signerCertificate.getNotAfter();

        Instant currentInstant = Instant.now();

        Duration duration = Duration.between(currentInstant, expirationDate.toInstant());

        // Extract remaining time
        StringBuilder remainingTime = new StringBuilder(String.valueOf(duration.toDays())).append(" days, ")
                .append(duration.toHours() % 24).append(" hours, ")
                .append(duration.toMinutes() % 60).append(" minutes, and ")
                .append(duration.getSeconds() % 60).append(" seconds");


        X500Principal subjectPrincipal = signerCertificate.getSubjectX500Principal();

        // Get the entire Subject DN as a String
        String subjectDnString = subjectPrincipal.getName();

        String[] components = subjectDnString.split(",");
        for (String component : components) {
            String[] keyValue = component.split("=");
            if (keyValue.length == 2) {
                String attributeName = keyValue[0].trim();
                String attributeValue = keyValue[1].trim();

                if (attributeName.equals("CN")) {
                    System.out.println("Issued to: " + attributeValue);
                }
            }
        }

        System.out.println("Certificate Validation Date: " + validationDate);
        System.out.println("Certificate Expiration Date: " + expirationDate);
        System.out.println("Remaining time: " + remainingTime);
    }

    public static void exportCa(X509CertificateHolder x509CertificateHolder, String outputPath) {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            // Get the encoded x509CertificateHolder bytes and write them to the file
            byte[] certificateBytes = x509CertificateHolder.getEncoded();
            fos.write(certificateBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Certificate exported");
        }
    }
}
