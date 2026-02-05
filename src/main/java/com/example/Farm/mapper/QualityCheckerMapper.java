package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.QualityCheckerRequest;
import com.example.Farm.dto.response.QualityCheckerResponse;
import com.example.Farm.model.QualityChecker;

public final class QualityCheckerMapper {

    private QualityCheckerMapper() {}

    public static QualityCheckerResponse toResponse(QualityChecker checker) {
        if (checker == null) return null;

        return QualityCheckerResponse.builder()
                .qualityCheckerId(checker.getPersonId())
                .certificationLevel(checker.getCertificationLevel())
                .build();
    }

    public static List<QualityCheckerResponse> toResponseList(List<QualityChecker> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(QualityCheckerMapper::toResponse).toList();
    }

    public static QualityChecker toEntity(QualityCheckerRequest request) {
        if (request == null) return null;
        QualityChecker checker = new QualityChecker();
        apply(request, checker);
        return checker;
    }

    public static void copyToEntity(QualityCheckerRequest request, QualityChecker entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(QualityCheckerRequest request, QualityChecker checker) {
        if (request.getCertificationLevel() != null) 
            checker.setCertificationLevel(request.getCertificationLevel());
    }
}
