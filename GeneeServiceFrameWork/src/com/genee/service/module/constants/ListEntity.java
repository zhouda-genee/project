package com.genee.service.module.constants;

import java.util.ArrayList;
import java.util.List;

public class ListEntity {
	public ListEntity() {
	};

	public ListEntity(List<MapEntity> maplist) {
		this.list = maplist;
	};

	public List<MapEntity> list = new ArrayList<MapEntity>();
}