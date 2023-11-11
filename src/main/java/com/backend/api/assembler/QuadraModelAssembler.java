package com.backend.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.QuadraModel;
import com.backend.domain.model.Quadra;

@Component
public class QuadraModelAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public QuadraModel toModel(Quadra quadra) {
		return mapper.map(quadra, QuadraModel.class);
	}
	
	public List<QuadraModel> toCollect(List<Quadra> quadras) {
		return quadras.stream().map(this::toModel).collect(Collectors.toList());
	}

}
