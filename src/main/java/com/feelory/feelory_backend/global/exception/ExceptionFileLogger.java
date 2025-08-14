package com.feelory.feelory_backend.global.exception;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ExceptionFileLogger {

    private static final String ERROR_LOG_DIR = "data/logs";

    public void writeToFile(Exception exception) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS"));
        String fileName = "error_" + timestamp + ".txt";

        try {
            File dir = new File(ERROR_LOG_DIR);
            if (!dir.exists()) dir.mkdirs();

            File logFile = new File(dir, fileName);

            try (FileWriter writer = new FileWriter(logFile)) {
                writer.write("📌 예외 요약\n");
                writer.write("----------------------------------\n");
                writer.write("🕒 발생 시각: " + timestamp + "\n");
                writer.write("🚨 예외 타입: " + exception.getClass().getName() + "\n");
                writer.write("🧩 메시지: " + exception.getMessage() + "\n");

                StackTraceElement origin = exception.getStackTrace()[0];
                writer.write("📍 발생 위치: " + origin + "\n");

                writer.write("\n🔽 전체 스택트레이스\n");
                writer.write("----------------------------------\n");

                for (StackTraceElement element : exception.getStackTrace()) {
                    writer.write(element.toString() + "\n");
                }
            }

        } catch (IOException e) {
            System.err.println("에러 로그 파일 저장 실패: " + e.getMessage());
        }
    }
}
