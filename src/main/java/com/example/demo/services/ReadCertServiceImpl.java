package com.example.demo.services;

import com.example.demo.models.CertificateInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ReadCertServiceImpl {

    public List<CertificateInformation> readCerts() {
        List<CertificateInformation> certList = new ArrayList<>();

        String folderPath = "D:\\project\\certs";
        File folder = new File(folderPath);
        File[] crtFiles = folder.listFiles((dir, name) -> name.endsWith(".crt"));

        if (Objects.nonNull(crtFiles)) {
            for (File crtFile : crtFiles) {
                try (InputStream resourceStream = new FileInputStream(crtFile)) {
                    CertificateFactory cf = CertificateFactory.getInstance("X.509");
                    X509Certificate cert = (X509Certificate) cf.generateCertificate(resourceStream);

                    Date validationDate = cert.getNotBefore();
                    Date expirationDate = cert.getNotAfter();

                    Instant currentInstant = Instant.now();

                    Duration duration = Duration.between(currentInstant, expirationDate.toInstant());

                    // Extract remaining time
                    StringBuilder remainingTime = new StringBuilder(String.valueOf(duration.toDays())).append(" days, ")
                            .append(duration.toHours() % 24).append(" hours, ")
                            .append(duration.toMinutes() % 60).append(" minutes, and ")
                            .append(duration.getSeconds() % 60).append(" seconds");

                    String issuedTo = getIssuedTo(cert);

                    certList.add(CertificateInformation.builder()
                            .issuedTo(issuedTo)
                            .validationDate(validationDate.toString())
                            .expirationDate(expirationDate.toString())
                            .remainingTime(remainingTime.toString())
                            .build());
                } catch (IOException | CertificateException e) {
                    log.error("Cannot read cert", e);
                }
            }
        }

        return certList;
    }

    private String getIssuedTo(X509Certificate cert) {
        X500Principal subjectPrincipal = cert.getSubjectX500Principal();

        // Get the entire Subject DN as a String
        String subjectDnString = subjectPrincipal.getName();

        String[] components = subjectDnString.split(",");
        for (String component : components) {
            String[] keyValue = component.split("=");
            if (keyValue.length == 2) {
                String attributeName = keyValue[0].trim();
                String attributeValue = keyValue[1].trim();

                if (attributeName.equals("CN")) {
                    return attributeValue;
                }
            }
        }
        return null;
    }
}

