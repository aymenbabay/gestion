package com.meta.store.werehouse.repository;

import java.util.List;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Vacation;

public interface VacationRepository  extends BaseRepository<Vacation,Long> {

	List<Vacation> findByCompanyIdAndWorkerId(Long companyId, Long workerId);

}
