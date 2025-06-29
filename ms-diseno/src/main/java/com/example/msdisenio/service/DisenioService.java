package com.example.msdisenio.service;

import com.example.msdisenio.dto.DisenioDTO;

import java.util.List;

public interface DisenioService {
    DisenioDTO registrarDisenio(DisenioDTO dto);
    List<DisenioDTO> listarDisenios();
    DisenioDTO obtenerDisenioPorId(Long id);
}
