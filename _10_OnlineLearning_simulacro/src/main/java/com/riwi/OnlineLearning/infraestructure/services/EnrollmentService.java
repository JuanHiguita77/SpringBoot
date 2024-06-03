package com.riwi.OnlineLearning.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.OnlineLearning.api.dto.request.EnrollmentReq;
import com.riwi.OnlineLearning.api.dto.response.EnrollmentResp;
import com.riwi.OnlineLearning.domain.entities.Course;
import com.riwi.OnlineLearning.domain.entities.Enrollment;
import com.riwi.OnlineLearning.domain.entities.User;
import com.riwi.OnlineLearning.domain.repositories.CourseRepository;
import com.riwi.OnlineLearning.domain.repositories.EnrollmentRepository;
import com.riwi.OnlineLearning.domain.repositories.UserRepository;
import com.riwi.OnlineLearning.infraestructure.abstract_services.IEnrollmentService;
import com.riwi.OnlineLearning.utils.enums.SortType;
import com.riwi.OnlineLearning.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService {

    private static final String FIELD_BY_SORT = "enrollmentDate";

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResp create(EnrollmentReq request) {
        Enrollment enrollment = this.requestToEntity(request);
        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public EnrollmentResp update(EnrollmentReq request, Long id) {
        Enrollment enrollment = this.find(id);

        enrollment = this.requestToEntity(request);
        enrollment.setId(id);

        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public void delete(Long id) {
        Enrollment enrollment = this.find(id);
        this.enrollmentRepository.delete(enrollment);
    }

    @Override
    public Page<EnrollmentResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };

        return this.enrollmentRepository.findAll(pagination).map(this::entityToResp);
    }

    @Override
    public List<EnrollmentResp> getCoursesByUserId(Long userId) {
        List<Enrollment> assigments = this.enrollmentRepository.findByUserId(userId);
        return assigments.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResp> getUsersByCourseId(Long courseId) {
        List<Enrollment> assigments = this.enrollmentRepository.findByCourseId(courseId);
        return assigments.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    private EnrollmentResp entityToResp(Enrollment entity) {
        return EnrollmentResp.builder()
                .enrollmentId(entity.getId())
                .user(entity.getUser())
                .course(entity.getCourse())
                .enrollmentDate(entity.getEnrollmentDate())
                .build();
    }

    private Enrollment requestToEntity(EnrollmentReq enrollmentReq) {
        User user = this.userRepository.findById(enrollmentReq.getUserId()).orElseThrow(() -> new BadRequestException("No User found with the supplied ID"));

        Course course = this.courseRepository.findById(enrollmentReq.getCourseId()).orElseThrow(() -> new BadRequestException("No Course found with the supplied ID"));

        return Enrollment.builder()
                .user(user)
                .course(course)
                .build();
    }

    private Enrollment find(Long id) {
        return this.enrollmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No enrollment found with the supplied ID"));
    }
}
