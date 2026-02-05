package com.example.Farm.mapper;

import java.util.List;
import com.example.Farm.dto.request.CrewLeaderRequest;
import com.example.Farm.dto.response.CrewLeaderResponse;
import com.example.Farm.model.CrewLeader;

public final class CrewLeaderMapper {

    private CrewLeaderMapper() {}

    public static CrewLeaderResponse toResponse(CrewLeader leader) {
        if (leader == null) return null;

        return CrewLeaderResponse.builder()
                .crewLeaderId(leader.getPersonId())
                .employeeCode(leader.getEmployeeCode())
                .personId(leader.getPersonId())
                .build();
    }

    public static List<CrewLeaderResponse> toResponseList(List<CrewLeader> list) {
        if (list == null || list.isEmpty()) return List.of();
        return list.stream().map(CrewLeaderMapper::toResponse).toList();
    }

    public static CrewLeader toEntity(CrewLeaderRequest request) {
        if (request == null) return null;
        CrewLeader leader = new CrewLeader();
        apply(request, leader);
        return leader;
    }

    public static void copyToEntity(CrewLeaderRequest request, CrewLeader entity) {
        if (request == null || entity == null) return;
        apply(request, entity);
    }

    private static void apply(CrewLeaderRequest request, CrewLeader leader) {
        if (request.getEmployeeCode() != null) leader.setEmployeeCode(request.getEmployeeCode());
    }
}
