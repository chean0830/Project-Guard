package com.projectguard.backend.registry;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 등기부등본 PDF는 서브셋 임베딩 폰트 + Identity-H 인코딩을 쓰는 경우가 많아
 * 범용 도구(poppler 등)는 한글 추출에 실패한다. PDFBox는 ToUnicode CMap을 정상적으로
 * 해석해 추출 가능함을 샘플로 확인했다 (docs/결정사항.md 8번 참고).
 */
@Component
public class RegistryPdfTextExtractor {

    public String extractText(byte[] pdfBytes) throws IOException {
        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        }
    }
}
