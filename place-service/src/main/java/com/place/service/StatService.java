package com.place.service;

import com.place.domain.po.Stat;

import java.time.LocalDate;
import java.util.List;

public interface StatService {
    List<Stat> getStat(LocalDate start, LocalDate end, String region);
}
