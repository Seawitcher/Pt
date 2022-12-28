package com.education.service.resolution.impl;

import com.education.entity.Resolution;
import com.education.repository.resolution.ResolutionRepository;
import com.education.service.resolution.ResolutionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;


/**
 * Класс содержащий методы для работы и связи с резолюцией
 */
@AllArgsConstructor
@Service
public class ResolutionServiceImpl implements ResolutionService {
    /**
     * Поле для связи с Resolution
     */
    private ResolutionRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Resolution resolution) {
        repository.saveAndFlush(resolution);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void moveToArchive(Long id) {
        repository.movesToArchive(id, ZonedDateTime.now());
    }

    @Transactional(readOnly = true)
    @Override
    public Resolution findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Resolution> findAllById(Collection<Long> id) {
        return repository.findAllById(id);
    }


    @Transactional(readOnly = true)
    @Override
    public Resolution findByIdNotArchived(Long id) {
        return repository.findByIdAndArchivedDateIsNull(id);
    }


    @Transactional(readOnly = true)
    @Override
    public Collection<Resolution> findAllByIdNotArchived(Collection<Long> id) {
        return repository.findAllByArchivedDateIsNull(id);
    }
}
