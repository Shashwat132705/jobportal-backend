
package com.jobportal.jobportal.service;
import java.util.Set;
import com.jobportal.jobportal.Job;
import com.jobportal.jobportal.exception.ResourceNotFoundException;
import com.jobportal.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.jobportal.jobportal.dto.JobPageResponse;
import com.jobportal.jobportal.dto.JobResponseDTO;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        )
                );
    }


    public Job updateJob(Long id, Job updatedJob) {

        // 1. Find the existing job
        Job existingJob = getJobById(id);

        // 2. Update its fields
        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setCompany(updatedJob.getCompany());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setDescription(updatedJob.getDescription());

        // 3. Save and return the updated job
        return jobRepository.save(existingJob);
    }



    public void deleteJob(Long id) {
        getJobById(id);
        jobRepository.deleteById(id);
    }

    public List<Job> searchJobs(String keyword) {
        return jobRepository
                .findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(
                        keyword, keyword);
    }

    public List<Job> searchByCompany(String company) {
        return jobRepository
                .findByCompanyContainingIgnoreCase(company);
    }


    public Page<Job> getJobsPaginated(
            int page, int size, String sortBy, String direction) {
        if (page < 0) {
            throw new IllegalArgumentException("Page cannot be negative");
        }
        if (size < 1 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }


        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException(
                    "Direction must be asc or desc");
        }

        Set<String> allowedFields =
                Set.of("title", "company", "location");

        if (!allowedFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field");
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return jobRepository.findAll(pageable);
    }


    public JobPageResponse getJobsPageResponse(
            int page, int size, String sortBy, String direction) {

        Page<Job> jobs = getJobsPaginated(
                page, size, sortBy, direction);

        List<JobResponseDTO> jobDTOs = jobs.getContent()
                .stream()
                .map(job -> new JobResponseDTO(
                        job.getId(),
                        job.getTitle(),
                        job.getCompany(),
                        job.getLocation(),
                        job.getDescription()
                ))
                .collect(Collectors.toList());

        return new JobPageResponse(
                jobDTOs,
                jobs.getNumber(),
                jobs.getSize(),
                jobs.getTotalElements(),
                jobs.getTotalPages(),
                jobs.isLast()
        );
    }




}

