package org.example;

import dev.sigstore.ImmutableVerificationOptions;
import dev.sigstore.KeylessSignature;
import dev.sigstore.KeylessVerificationRequest;
import dev.sigstore.KeylessVerifier;
import dev.sigstore.bundle.BundleFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: <artifact> <user> <provider>");
            System.exit(1);
        }

        Path artifactPath = Paths.get(args[0]);
        Path bundlePath = artifactPath.resolveSibling(artifactPath.getFileName().toString() + ".sigstore");

        System.out.printf("""
                Verifying bundle:
                    %s
                  for artifact:
                    %s
                %n""", bundlePath, artifactPath);

        KeylessSignature keylessSignature = BundleFactory.readBundle(Files.newBufferedReader(bundlePath, StandardCharsets.UTF_8));

        ImmutableVerificationOptions options = KeylessVerificationRequest.VerificationOptions.builder()
                .isOnline(true)
                .addCertificateIdentities(
                        KeylessVerificationRequest.CertificateIdentity.builder()
                                .subjectAlternativeName(args[1])
                                .issuer(args[2])
                                .build()
                ).build();

        KeylessVerifier verifier = new KeylessVerifier.Builder().sigstorePublicDefaults().build();
        verifier.verify(artifactPath,
                KeylessVerificationRequest.builder()
                        .keylessSignature(keylessSignature)
                        .verificationOptions(options)
                        .build());

        System.out.println("Bundle passed verification");
    }
}
