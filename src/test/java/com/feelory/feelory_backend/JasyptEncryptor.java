package com.feelory.feelory_backend;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class JasyptEncryptor {

    public static void main(String[] args) {
        // 1. 환경변수에서 비밀번호 읽기
        String jasyptEncryptorPassword = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");
        if (jasyptEncryptorPassword == null) {
            System.out.println("환경변수 JASYPT_ENCRYPTOR_PASSWORD가 설정되어 있지 않습니다.");
            return;
        }
        System.out.println("현재 비밀 키 : " + jasyptEncryptorPassword + "\n");

        // 2. 암호화할 값들 배열 (원하는 값 추가 가능)
        String[] secretsToEncrypt = {
                "myPasswordASDF",
                "secretNumberInsert",
        };

        // 3. Encryptor 객체 생성 및 세팅
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasyptEncryptorPassword);
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        encryptor.setIvGenerator(new RandomIvGenerator());

        // 4. 암호화 결과 출력 + 복호화 결과 출력
        for (String secret : secretsToEncrypt) {
            System.out.println("원본 : " + secret);

            String encrypted = encryptor.encrypt(secret);
            System.out.println("암호화 : " + "ENC(" + encrypted + ")");

            String decrypted = encryptor.decrypt(encrypted);
            System.out.println("복호화 : " + decrypted+"\n");
        }
    }
}