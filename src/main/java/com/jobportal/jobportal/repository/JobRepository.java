
package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByTitleContainingIgnoreCase(String title);

    List<Job> findByCompanyContainingIgnoreCase(String company);

    List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCase(
            String title, String company);
}
