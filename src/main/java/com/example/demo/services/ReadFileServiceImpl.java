package com.example.demo.services;

import com.example.demo.utils.CaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.springframework.stereotype.Service;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

@Slf4j
@Service
public class ReadFileServiceImpl {
    public void readMailSignatureCertificate() {
        try (InputStream is = new FileInputStream("D:\\project\\_test.msg");
             MAPIMessage msg = new MAPIMessage(is)) {

            for (AttachmentChunks attachment : msg.getAttachmentFiles()) {
                String attachmentName = attachment.getAttachLongFileName().getValue();

                if (Objects.nonNull(attachmentName) && attachmentName.toLowerCase().contains(".p7m")) {
                    byte[] pkcs7SignatureBytes = attachment.getAttachData().getValue();

                    // Create mime message from byte array
                    Properties props = new Properties();
                    Session session = Session.getDefaultInstance(props, null);
                    MimeMessage mimeMessage = new MimeMessage(session, new ByteArrayInputStream(pkcs7SignatureBytes));

                    MimeMultipart mimeMultipart = (MimeMultipart) mimeMessage.getContent();

                    for (int i = 0; i < mimeMultipart.getCount(); i++) {
                        BodyPart signedDataPart = mimeMultipart.getBodyPart(i); // The signed content
                        byte[] signedDataBytes = getContentBytes(signedDataPart);

                        try {
                            CMSSignedData cmsSignedData = new CMSSignedData(signedDataBytes);

                            Collection<X509CertificateHolder> certificates = cmsSignedData.getCertificates().getMatches(null);

                            for (X509CertificateHolder x509CertificateHolder : certificates) {
                                CaUtils.readCaInfo(x509CertificateHolder);

                                CaUtils.exportCa(x509CertificateHolder, "D:\\project\\signature1.crt");
                            }
                        } catch (CMSException e1) {
                            System.out.println("Cannot read this body part " + i);
                        }
                    }
                }
            }

        } catch (IOException | CertificateException | MessagingException e) {
            log.error("Cannot read this file", e);
        }
    }

    public void readPdfSignatureCertificate() {
        try (InputStream is = new FileInputStream("D:\\project\\file.pdf");
             PDDocument document = PDDocument.load(is)) {
            for (PDSignature signature : document.getSignatureDictionaries()) {
                if (signature.getSubFilter().equals("adbe.pkcs7.detached")) {
                    // Get the signature contents
                    byte[] signatureBytes = signature.getContents();

                    CMSSignedData signedData = new CMSSignedData(signatureBytes);

                    Collection<X509CertificateHolder> certificates = signedData.getCertificates().getMatches(null);

                    for (X509CertificateHolder x509CertificateHolder : certificates) {
                        CaUtils.readCaInfo(x509CertificateHolder);

                        CaUtils.exportCa(x509CertificateHolder, "D:\\project\\signature2.crt");
                    }
                }

            }
        } catch (IOException | CertificateException | CMSException e) {
            log.error("Cannot read this file", e);
        }
    }

    private byte[] getContentBytes(BodyPart bodyPart) throws IOException, MessagingException {
        try (InputStream inputStream = bodyPart.getInputStream()) {
            byte[] contentBytes = new byte[inputStream.available()];
            inputStream.read(contentBytes);
            return contentBytes;
        }
    }
}
