package com.projectguard.backend.registry;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RegistryAnalysisService {

    private final RegistryPdfTextExtractor extractor;
    private final RegistryParser parser;

    public RegistryAnalysisService(RegistryPdfTextExtractor extractor, RegistryParser parser) {
        this.extractor = extractor;
        this.parser = parser;
    }

    /**
     * 업로드된 등기부등본 PDF 바이트를 분석한다.
     * 원본 파일은 이 메서드 호출 전후로 디스크에 저장하지 않는다 (비저장 원칙).
     */
    public RegistryAnalysis analyze(byte[] pdfBytes) throws IOException {
        String rawText = extractor.extractText(pdfBytes);
        return parser.parse(rawText);
    }
}
