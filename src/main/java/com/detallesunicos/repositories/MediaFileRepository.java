package com.detallesunicos.v1.repositories;

import com.detallesunicos.v1.model.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {}